package br.com.joston.msbrasillocalidades.v1.service.ibge.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IBGEMunicipioNiveladoDto {
    @JsonProperty("municipio-nome")
    private String nome;
    @JsonProperty("UF-sigla")
    private String uf;
}
