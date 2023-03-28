package br.com.joston.msbrasillocalidades.v1.provider.cep;

import br.com.joston.msbrasillocalidades.v1.dto.EnderecoDto;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class ConsultaCepTest {
    private final ConsultaCep consultaCep = cep -> EnderecoDto.builder().build();

    @ParameterizedTest
    @ValueSource(strings = {"","1","12345","123456789"})
    void testInvalidCepCase(String cep) {
        assertThrows(InvalidCepException.class,()-> consultaCep.validate(cep),"Should be throws a InvalidaCepException!");
    }
}