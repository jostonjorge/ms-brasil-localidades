package br.com.joston.msbrasillocalidades.v1.util;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class StringUtilsTest {

    @ParameterizedTest
    @MethodSource("provideArgsForOnlyNumbers")
    void testOnlyNumbers(String expected,String charSequence){
        assertEquals(expected,StringUtils.onlyNumbers(charSequence),"Should return only numbers on string");
    }

    static Stream<Arguments> provideArgsForOnlyNumbers(){
        return Stream.of(
                Arguments.of("1234","abc1234efg"),
                Arguments.of("1234","1234"),
                Arguments.of("","abcde"),
                Arguments.of("12345","1@2#$3,-+=4%Ç5_/\\()*^=[]{}~:;.,!'\"`´<>")
        );
    }

}