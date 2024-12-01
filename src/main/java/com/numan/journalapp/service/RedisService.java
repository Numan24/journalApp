package com.numan.journalapp.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class RedisService {

  private static final ObjectMapper objectMapper = new ObjectMapper();

  @Autowired
  private RedisTemplate<String, String> redisTemplate;


  public <T> T getFromRedis(String key, Class<T> entityClass) {

    try {
      Object o = redisTemplate.opsForValue().get(key);
      return objectMapper.readValue(o.toString(), entityClass);
    } catch (Exception exception) {
      log.error("error happened", exception);
      return null;
    }
  }

  public void setInRedis(String key, Object o, Long ttl) {

    try {
      String json = objectMapper.writeValueAsString(o);
      redisTemplate.opsForValue().set(key, json, ttl, TimeUnit.SECONDS);
    } catch (Exception exception) {
      log.error("error happened", exception);
    }
  }


}
