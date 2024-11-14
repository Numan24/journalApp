package com.numan.journalapp.service;

import com.numan.journalapp.dto.WeatherResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

  @Autowired
  private RestTemplate restTemplate;

  @Value("${weather.api.key}")
  private String apiKey;
  private static final String API = "https://api.weatherstack.com/current?access_key=API_KEY&query=CITY";


  public WeatherResponseDTO getWeather(String city) {
    String finalApi = API.replace("CITY", city).replace("API_KEY", apiKey);
    ResponseEntity<WeatherResponseDTO> responseEntity = restTemplate.exchange(finalApi, HttpMethod.GET, null, WeatherResponseDTO.class);
    return responseEntity.getBody();

  }


}
