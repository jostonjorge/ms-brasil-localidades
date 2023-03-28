package br.com.joston.msbrasillocalidades.v1.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class MunicipioDto {
    String nome;
    String uf;
}
