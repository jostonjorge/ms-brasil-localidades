package br.com.joston.msbrasillocalidades.v1.service.ibge.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class IBGEUfDto {
    private Integer id;
    private String nome;
    private String sigla;
}
