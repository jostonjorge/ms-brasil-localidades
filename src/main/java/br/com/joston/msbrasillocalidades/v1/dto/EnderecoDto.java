package br.com.joston.msbrasillocalidades.v1.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class EnderecoDto {
    private String logradouro;
    private String bairro;
    private String cidade;
    private String uf;
    private String cep;
}
