package com.numan.journalapp.service;

import com.numan.journalapp.dto.WeatherResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

  @Autowired
  private RestTemplate restTemplate;

  private static final String apiKey = "22e812b80502db446c71962a2bfde5c0";
  private static final String API = "https://api.weatherstack.com/current?access_key=API_KEY&query=CITY";


  public WeatherResponseDTO getWeather(String city) {
    String finalApi = API.replace("CITY", city).replace("API_KEY", apiKey);
    ResponseEntity<WeatherResponseDTO> responseEntity = restTemplate.exchange(finalApi, HttpMethod.GET, null, WeatherResponseDTO.class);
    return responseEntity.getBody();

  }


}
