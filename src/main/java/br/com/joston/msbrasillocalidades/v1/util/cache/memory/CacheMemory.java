package br.com.joston.msbrasillocalidades.v1.util.cache.memory;

import br.com.joston.msbrasillocalidades.v1.util.NullChecker;
import br.com.joston.msbrasillocalidades.v1.util.cache.Cache;
import br.com.joston.msbrasillocalidades.v1.util.cache.CacheData;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component("CacheMemoryV1")
public class CacheMemory implements Cache {
    private final Map<String,CacheData> cache = new HashMap<>();

    @Override
    public CacheData get(String key) {
        if(!cache.containsKey(key)){
            cache.put(key,new CacheDataImpl(null,LocalDateTime.now()));
        }
        return cache.get(key);
    }

    static class CacheDataImpl implements CacheData{
        private Object data;
        private LocalDateTime expires;
        public CacheDataImpl(Object data,LocalDateTime expires){
            this.data = data;
            this.expires = expires;
        }

        private boolean expired(){
            return expires.isBefore(LocalDateTime.now());
        }

        private boolean isEmpty(){
            if(data == null){
                return true;
            }
            if(data instanceof String){
                return ((String) data).length() == 0;
            }
            if(data instanceof List){
                return ((List<?>) data).isEmpty();
            }
            if(data instanceof Map){
                return ((Map<?, ?>) data).isEmpty();
            }
            return NullChecker.allGettersIsNull(data);
        }

        public boolean isValid(){
            return !expired() && !isEmpty();
        }

        @Override
        @SuppressWarnings("unchecked")
        public <T> T value() {
            return (T) data;
        }

        @Override
        public void set(Object value, LocalDateTime expires) {
            this.data = value;
            this.expires = expires;
        }
    }
}
