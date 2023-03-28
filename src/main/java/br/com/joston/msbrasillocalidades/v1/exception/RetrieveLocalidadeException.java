package br.com.joston.msbrasillocalidades.v1.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.EXPECTATION_FAILED,reason = "Um erro inesperado ocorreu ao tentar obter as informações!")
public class RetrieveLocalidadeException extends RuntimeException{
    public RetrieveLocalidadeException(String message){
        super(message);
    }
}
