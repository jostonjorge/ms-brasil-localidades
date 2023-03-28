package br.com.joston.msbrasillocalidades.v1.provider.cep;

import br.com.joston.msbrasillocalidades.v1.dto.EnderecoDto;
import br.com.joston.msbrasillocalidades.v1.util.StringUtils;

public interface ConsultaCep {
    EnderecoDto execute(String cep);
    default void validate(String cep) throws InvalidCepException{
        if(cep == null){
            throw new InvalidCepException("Informe um cep válido!","<vazio>");
        }
        if(StringUtils.onlyNumbers(cep).length() != 8){
            throw new InvalidCepException("O CEP deve conter 8 digitos!",cep);
        }
    }
}
