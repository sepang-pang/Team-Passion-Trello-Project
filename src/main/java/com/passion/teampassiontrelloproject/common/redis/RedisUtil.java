package com.passion.teampassiontrelloproject.common.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@Slf4j(topic = "RedisUtil 관련 로그")
public class RedisUtil {

    private final StringRedisTemplate redisTemplate;

    @Autowired
    public RedisUtil(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }


    // 블랙리스트에 토큰 추가
    public void setBlackList(String key, Object value, long expirationTimeSeconds) {
        log.info("블랙리스트에 토큰 추가");
        redisTemplate.opsForValue().set(key, (String) value, expirationTimeSeconds, TimeUnit.SECONDS);
    }

    // 블랙리스트에 토큰 존재 여부 확인
    public boolean hasKeyBlackList(String key) {
        log.info("블랙리스트에 토큰 존재 여부 확인");
        return redisTemplate.hasKey(key);
    }
}
