package br.com.joston.msbrasillocalidades.v1.util.cache;

import java.time.LocalDateTime;

public interface CacheData {
    boolean isValid();
    <T> T value();
    void set(Object value, LocalDateTime expires);
}
