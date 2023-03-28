package br.com.joston.msbrasillocalidades.v1.util.cache;

import java.time.LocalDateTime;

public enum CacheTime {
    SECOND,
    MINUTE,
    HOUR,
    DAY;

    public LocalDateTime perform(int number){
        LocalDateTime time = LocalDateTime.now();
        switch (this){
            case DAY:
                time = time.plusDays(number);
                break;
            case HOUR:
                time = time.plusHours(number);
                break;
            case MINUTE:
                time = time.plusMinutes(number);
                break;
            case SECOND:
                time = time.plusSeconds(number);
                break;
        }
        return time;
    }
}
