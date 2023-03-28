package br.com.joston.msbrasillocalidades.v1.service.ibge;

import br.com.joston.msbrasillocalidades.v1.dto.EstadoDto;
import br.com.joston.msbrasillocalidades.v1.exception.RetrieveLocalidadeException;
import br.com.joston.msbrasillocalidades.v1.service.ibge.dto.IBGEUfDto;
import br.com.joston.msbrasillocalidades.v1.util.cache.Cache;
import br.com.joston.msbrasillocalidades.v1.util.cache.CacheData;
import br.com.joston.msbrasillocalidades.v1.util.cache.memory.CacheMemory;
import br.com.joston.msbrasillocalidades.v1.util.cache.CacheTime;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service("RetrieveUfServiceV1")
@Slf4j
public class RetrieveUfService {
    private final static String URL_IBGE_RETRIEVE_UF = "https://servicodados.ibge.gov.br/api/v1/localidades/estados?orderBy=nome";
    private final Cache cache;
    private final ObjectMapper objectMapper;

    RetrieveUfService(CacheMemory cacheMemory, ObjectMapper objectMapper){
        this.cache = cacheMemory;
        this.objectMapper = objectMapper;
    }
    public List<EstadoDto> retrieveAll(){
        CacheData cacheData = cache.get("ufs");
        if(cacheData.isValid()){
            return cacheData.value();
        }
        cacheData.set(getEstados(),CacheTime.DAY.perform(1));
        return cacheData.value();
    }

    private List<EstadoDto> getEstados(){
        String exceptionMessage = "Um erro inesperado ocorreu ao obter a lista de estados!";
        try {
            HttpResponse<String> response = Unirest.get(URL_IBGE_RETRIEVE_UF)
                    .header("accept", MediaType.APPLICATION_JSON_VALUE)
                    .asString();
            if(response.getStatus() > 299){
                log.error("O servidor não retornou status OK! URL:{} STATUS: {} BODY: {}",URL_IBGE_RETRIEVE_UF,response.getStatus(), response.getBody());
                throw new RetrieveLocalidadeException(exceptionMessage);
            }
            List<IBGEUfDto> result = objectMapper.readValue(response.getBody(), new TypeReference<>(){});
            return toOutputDto(result);
        } catch (UnirestException e) {
            log.error("Erro ao executar o client http",e);
            throw new RetrieveLocalidadeException(exceptionMessage);
        } catch (JsonProcessingException e) {
            log.error("Erro ao mappear o objeto de resposta", e);
            throw new RetrieveLocalidadeException(exceptionMessage);
        }
    }

    private List<EstadoDto> toOutputDto(List<IBGEUfDto> from){
        return from.stream().map(uf -> EstadoDto.builder()
                .sigla(uf.getSigla())
                .nome(uf.getNome())
                .build()).collect(Collectors.toList());
    }
}
