package br.com.joston.msbrasillocalidades.v1.service;

import br.com.joston.msbrasillocalidades.v1.dto.EnderecoDto;
import br.com.joston.msbrasillocalidades.v1.provider.cep.ConsultaCep;
import br.com.joston.msbrasillocalidades.v1.provider.cep.correios.CorreiosConsultaCep;
import br.com.joston.msbrasillocalidades.v1.util.StringUtils;
import br.com.joston.msbrasillocalidades.v1.util.cache.Cache;
import br.com.joston.msbrasillocalidades.v1.util.cache.CacheData;
import br.com.joston.msbrasillocalidades.v1.util.cache.CacheTime;
import br.com.joston.msbrasillocalidades.v1.util.cache.memory.CacheMemory;
import org.springframework.stereotype.Service;

@Service("ConsultaCepServiceV1")
public class ConsultaCepService {
    private final ConsultaCep correiosConsultaCep;
    private final Cache cache;

    public ConsultaCepService(CorreiosConsultaCep correiosConsultaCep, CacheMemory cache) {
        this.correiosConsultaCep = correiosConsultaCep;
        this.cache = cache;
    }

    public EnderecoDto consultaCep(String cep){
        correiosConsultaCep.validate(cep);
        CacheData cacheData = cache.get(StringUtils.onlyNumbers(cep));
        if(cacheData.isValid()){
            return cacheData.value();
        }
        EnderecoDto data = correiosConsultaCep.execute(cep);
        cacheData.set(data, CacheTime.DAY.perform(1));
        return cacheData.value();
    }
}
