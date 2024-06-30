package com.aivle.carekids.domain.user.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class RedisUtils {

    private final StringRedisTemplate template;

    public String getData(String key) {
        ValueOperations<String, String> valueOperations = template.opsForValue();
        return valueOperations.get(key);
    }

    public boolean existData(String key) {
        return Boolean.TRUE.equals(template.hasKey(key));
    }

    public void setDataExpire(String key, String value, long duration) {
        ValueOperations<String, String> valueOperations = template.opsForValue();
        Duration expireDuration = Duration.ofSeconds(duration);
        valueOperations.set(key, value, expireDuration);
    }

    public String getDataExpire(String key){
        if (Boolean.FALSE.equals(template.hasKey(key))) {return null;}

        Long expirationSeconds = template.getExpire(key, TimeUnit.SECONDS);

        long minutes = TimeUnit.SECONDS.toMinutes(expirationSeconds);
        Long seconds = expirationSeconds - TimeUnit.MINUTES.toSeconds(minutes);
        return String.format("%d분 %d초", minutes, seconds);
    }

    public void deleteData(String key) {
        template.delete(key);
    }
}
