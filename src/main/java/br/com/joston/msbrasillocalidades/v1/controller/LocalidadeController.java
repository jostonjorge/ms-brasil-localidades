package br.com.joston.msbrasillocalidades.v1.controller;

import br.com.joston.msbrasillocalidades.v1.dto.EnderecoDto;
import br.com.joston.msbrasillocalidades.v1.dto.EstadoDto;
import br.com.joston.msbrasillocalidades.v1.dto.MunicipioDto;
import br.com.joston.msbrasillocalidades.v1.service.ConsultaCepService;
import br.com.joston.msbrasillocalidades.v1.service.ibge.RetrieveMunicipioService;
import br.com.joston.msbrasillocalidades.v1.service.ibge.RetrieveUfService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1")
@AllArgsConstructor
public class LocalidadeController {
    private final RetrieveUfService ufService;
    private final RetrieveMunicipioService municipioService;
    private final ConsultaCepService consultaCepService;

    @GetMapping("/estados")
    @ResponseBody
    public List<EstadoDto> retrieveUf(){
        return ufService.retrieveAll();
    }

    @GetMapping("/estados/{uf}/municipios")
    @ResponseBody
    public List<MunicipioDto> retrieveMunicipiosByUf(@PathVariable("uf") String uf){
        return municipioService.retrieveByUf(uf);
    }

    @GetMapping("/municipios")
    @ResponseBody
    public List<MunicipioDto> retrieveMunicipios(){
        return municipioService.retrieveAll();
    }

    @GetMapping("/cep/{cep}")
    @ResponseBody
    public EnderecoDto consultaCep(@PathVariable("cep") String cep){
        return consultaCepService.consultaCep(cep);
    }
}
