package com.numan.journalapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeatherResponseDTO {
  private Location location;
  private Current current;

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public class Current {

    private int temperature;
    private ArrayList<String> weatherDescriptions;
    @JsonProperty("windSpeed")
    private int windSpeed;
    @JsonProperty("precip")
    private int precIp;
    private int humidity;
    @JsonProperty("feelslike")
    private int feelsLike;

  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public class Location {
    private String name;
    private String country;
    private String region;
    private String localtime;
  }

}
