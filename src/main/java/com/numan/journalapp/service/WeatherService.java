package com.numan.journalapp.service;

import com.numan.journalapp.cached.AppCache;
import com.numan.journalapp.dto.WeatherResponseDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

  private RestTemplate restTemplate;
  private AppCache appCache;

  WeatherService(RestTemplate restTemplate, AppCache appCache) {
    this.restTemplate = restTemplate;
    this.appCache = appCache;

  }

  @Value("${weather.api.key}")
  private String apiKey;


  public WeatherResponseDTO getWeather(String city) {
    String finalApi = appCache.AppConfigCache.get(AppCache.AppConfigKeys.WEATHER_API.name()).replace("<city>", city).replace("<apiKey>", apiKey);
    ResponseEntity<WeatherResponseDTO> responseEntity = restTemplate.exchange(finalApi, HttpMethod.GET, null, WeatherResponseDTO.class);
    return responseEntity.getBody();

  }


}
