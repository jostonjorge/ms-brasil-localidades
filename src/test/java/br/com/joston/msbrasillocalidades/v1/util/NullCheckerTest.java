package br.com.joston.msbrasillocalidades.v1.util;

import lombok.Data;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

class NullCheckerTest {
    @Test
    void testAllGettersIsNullTrueCase(){
        assertTrue(NullChecker.allGettersIsNull(new ObjectTest()));
    }
    @Test
    void testAllGettersIsNullFalseCase(){
        ObjectTest clazz = new ObjectTest();
        clazz.setFoo("abc");
        assertFalse(NullChecker.allGettersIsNull(clazz));
    }
}

@Data
class ObjectTest {
    String foo;
    Integer bar;
    List<Object> collection;
}