package br.com.joston.msbrasillocalidades.v1.util.cache.memory;

import br.com.joston.msbrasillocalidades.v1.util.cache.Cache;
import br.com.joston.msbrasillocalidades.v1.util.cache.CacheData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class CacheMemoryTest {
    private Cache cache;

    @BeforeEach
    void setUp() {
        cache = new CacheMemory();
    }

    @Test
    void shouldStoreAndReturnTheSameData(){
        String key = "cacheKey";
        List<String> data = Arrays.asList("a","b","c");
        CacheData cacheData = cache.get(key);
        cacheData.set(data,LocalDateTime.now().plusDays(1));
        assertEquals(data,cacheData.value());
    }

    @Test
    void testForCacheDataExpired(){
        CacheData cacheData = cache.get("key");
        cacheData.set("cache data",LocalDateTime.now().minusMinutes(1));
        assertFalse(cacheData.isValid(),"Should return invalid because the time cache is expired!");
    }
    @Test
    void testForCacheDataNonExpired(){
        CacheData cacheData = cache.get("key");
        cacheData.set("cache data",LocalDateTime.now().plusDays(1));
        assertTrue(cacheData.isValid(),"Should return valid because the time cache isn't expired!");
    }
    @ParameterizedTest
    @MethodSource("provideArgsForTestEmptyCacheData")
    void testForEmptyCacheData(Object data){
        CacheData cacheData = cache.get("key");
        cacheData.set(data,LocalDateTime.now().plusDays(1));
        assertFalse(cacheData.isValid(),"Should return invalid because object data is empty!");
    }

    @Test
    void testWhenCacheDataIsNull(){
        CacheData cacheData = cache.get("key");
        cacheData.set(null,LocalDateTime.now().plusDays(1));
        assertFalse(cacheData.isValid(),"Should is invalid because cache data is null!");
    }

    static Stream<Arguments> provideArgsForTestEmptyCacheData(){
        return Stream.of(
                Arguments.of(new ArrayList<>()),
                Arguments.of(new HashMap<>()),
                Arguments.of(Collections.EMPTY_LIST),
                Arguments.of(new String[3]),
                Arguments.of(""),
                Arguments.of(new CacheMemory.CacheDataImpl(null,null))
        );
    }
}