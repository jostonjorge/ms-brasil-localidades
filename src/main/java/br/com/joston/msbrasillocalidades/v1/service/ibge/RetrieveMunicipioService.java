package br.com.joston.msbrasillocalidades.v1.service.ibge;

import br.com.joston.msbrasillocalidades.v1.dto.MunicipioDto;
import br.com.joston.msbrasillocalidades.v1.exception.RetrieveLocalidadeException;
import br.com.joston.msbrasillocalidades.v1.service.ibge.dto.IBGEMunicipioDto;
import br.com.joston.msbrasillocalidades.v1.service.ibge.dto.IBGEMunicipioNiveladoDto;
import br.com.joston.msbrasillocalidades.v1.util.cache.Cache;
import br.com.joston.msbrasillocalidades.v1.util.cache.CacheData;
import br.com.joston.msbrasillocalidades.v1.util.cache.CacheTime;
import br.com.joston.msbrasillocalidades.v1.util.cache.memory.CacheMemory;
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

@Service("RetrieveMunicipioServiceV1")
@Slf4j
public class RetrieveMunicipioService {

    private final static String URL_IBGE_TODOS_MUNICIPIOS= "https://servicodados.ibge.gov.br/api/v1/localidades/municipios?orderBy=nome&view=nivelado";
    private final static String URL_IBGE_MUNICIPIOS_POR_UF = "https://servicodados.ibge.gov.br/api/v1/localidades/estados/{uf}/municipios?orderBy=nome";
    private final Cache cache;
    private final ObjectMapper objectMapper;

    RetrieveMunicipioService(CacheMemory cache, ObjectMapper objectMapper){
        this.cache = cache;
        this.objectMapper = objectMapper;
    }
    public List<MunicipioDto> retrieveByUf(String siglaEstado){
        String cacheKey = "municipios-"+siglaEstado;
        CacheData cacheData = cache.get(cacheKey);
        if(cacheData.isValid()){
            return cacheData.value();
        }
        cacheData.set(findByUf(siglaEstado),CacheTime.DAY.perform(1));
        return cacheData.value();
    }
    public List<MunicipioDto> retrieveAll(){
        String cacheKey = "municipios";
        CacheData cacheData = cache.get(cacheKey);
        if(cacheData.isValid()){
            return cacheData.value();
        }
        cacheData.set(findAll(),CacheTime.DAY.perform(1));
        return cacheData.value();
    }

    private List<String> findByUf(String siglaEstado){
        String exceptionMessage = "Um erro inesperado ocorreu ao obter a lista de municípios!";
        try {
            String url = URL_IBGE_MUNICIPIOS_POR_UF.replace("{uf}",siglaEstado);
            HttpResponse<String> response = Unirest.get(url)
                    .header("accept", MediaType.APPLICATION_JSON_VALUE)
                    .asString();
            if(response.getStatus() > 299){
                log.error("O servidor não retornou status OK! URL:{} STATUS: {} BODY: {}",URL_IBGE_MUNICIPIOS_POR_UF,response.getStatus(), response.getBody());
                throw new RetrieveLocalidadeException(exceptionMessage);
            }
            List<IBGEMunicipioDto> result = objectMapper.readValue(response.getBody(), new TypeReference<>(){});
            return result.stream().map(IBGEMunicipioDto::getNome).collect(Collectors.toList());
        } catch (UnirestException e) {
            log.error("Erro ao executar o client http",e);
            throw new RetrieveLocalidadeException(exceptionMessage);
        } catch (JsonProcessingException e) {
            log.error("Erro ao mappear o objeto de resposta", e);
            throw new RetrieveLocalidadeException(exceptionMessage);
        }
    }
    private List<MunicipioDto> findAll(){
        String exceptionMessage = "Um erro inesperado ocorreu ao obter a lista de municípios!";
        try {
            HttpResponse<String> response = Unirest.get(URL_IBGE_TODOS_MUNICIPIOS)
                    .header("accept", MediaType.APPLICATION_JSON_VALUE)
                    .asString();
            if(response.getStatus() > 299){
                log.error("O servidor não retornou status OK! STATUS: {} BODY: {}",response.getStatus(), response.getBody());
                throw new RetrieveLocalidadeException(exceptionMessage);
            }
            List<IBGEMunicipioNiveladoDto> result = objectMapper.readValue(response.getBody(), new TypeReference<>() {});
            return toOutputDto(result);
        } catch (UnirestException e) {
            log.error("Erro ao executar o client http",e);
            throw new RetrieveLocalidadeException(exceptionMessage);
        } catch (JsonProcessingException e) {
            log.error("Erro ao mappear o objeto de resposta", e);
            throw new RetrieveLocalidadeException(exceptionMessage);
        }
    }

    private List<MunicipioDto> toOutputDto(List<IBGEMunicipioNiveladoDto> from){
        return from.stream().map(municipio -> MunicipioDto.builder()
                .uf(municipio.getUf())
                .nome(municipio.getNome())
                .build()).collect(Collectors.toList());
    }
}
