package br.com.joston.msbrasillocalidades.v1.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class EstadoDto {
    private String sigla;
    private String nome;
}
