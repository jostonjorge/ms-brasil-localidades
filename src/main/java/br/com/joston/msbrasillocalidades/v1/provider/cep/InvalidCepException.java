package br.com.joston.msbrasillocalidades.v1.provider.cep;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST,reason = "Cep inválido!")
public class InvalidCepException extends RuntimeException{
    String cep;
    InvalidCepException(String message, String cep){
        super(message);
        this.cep = cep;
    }
}
