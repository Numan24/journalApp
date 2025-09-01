package com.numan.journalapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableRedisRepositories
public class RedisConfig {

  @Bean
  public JedisConnectionFactory connectionFactory() {
    RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
    configuration.setHostName( "localhost");
    configuration.setPort(6379);
    //configuration.setUsername("default");
    //configuration.setPassword("yJd7cvU2cLyOCBBbrVPtudiuCNZcXQac");
    return new JedisConnectionFactory(configuration);

  }

  @Bean
  public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory) {
    RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
    redisTemplate.setConnectionFactory(connectionFactory());
    redisTemplate.setKeySerializer(new StringRedisSerializer());
    redisTemplate.setValueSerializer(new StringRedisSerializer());
    return redisTemplate;
  }


}
