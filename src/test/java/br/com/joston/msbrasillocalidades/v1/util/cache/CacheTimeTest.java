package br.com.joston.msbrasillocalidades.v1.util.cache;

import org.junit.jupiter.api.Test;

import java.time.*;

import static org.junit.jupiter.api.Assertions.*;

class CacheTimeTest {

    @Test
    void testPerform() {
        LocalDateTime now = LocalDateTime.now();
        ZoneOffset zone = ZoneOffset.UTC;
        assertEquals(1,Duration.between(now.toInstant(zone),CacheTime.DAY.perform(1).toInstant(zone)).toDays());
        assertEquals(1,Duration.between(now.toInstant(zone),CacheTime.HOUR.perform(1).toInstant(zone)).toHours());
        assertEquals(1,Duration.between(now.toInstant(zone),CacheTime.MINUTE.perform(1).toInstant(zone)).toMinutes());
        assertEquals(1,Duration.between(now.toInstant(zone),CacheTime.SECOND.perform(1).toInstant(zone)).toSeconds());
    }
}