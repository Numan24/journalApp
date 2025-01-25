package com.numan.journalapp.service;

import com.numan.journalapp.cached.AppCache;
import com.numan.journalapp.dto.WeatherResponseDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

  private RestTemplate restTemplate;
  private AppCache appCache;
  private RedisService redisService;

  WeatherService(RestTemplate restTemplate, AppCache appCache, RedisService redisService) {
    this.restTemplate = restTemplate;
    this.appCache = appCache;
    this.redisService = redisService;

  }

  @Value("${weather.api.key}")
  private String apiKey;


  public WeatherResponseDTO getWeather(final String city) {
    WeatherResponseDTO weatherResponseDTO = redisService.getFromRedis("weather_of" + city, WeatherResponseDTO.class);
    if (weatherResponseDTO != null) {
      return weatherResponseDTO;
    }
    String finalApi = appCache.AppConfigCache.get(AppCache.AppConfigKeys.WEATHER_API.name()).replace("<city>", city).replace("<apiKey>", apiKey);
    ResponseEntity<WeatherResponseDTO> responseEntity = restTemplate.exchange(finalApi, HttpMethod.GET, null, WeatherResponseDTO.class);
    if (responseEntity.hasBody()) {
      redisService.setInRedis("weather_of" + city, responseEntity.getBody(), 3000L);
    }
    return responseEntity.getBody();

  }


}
