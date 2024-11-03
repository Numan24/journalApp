package com.numan.journalapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;

@Data
public class WeatherResponseDTO {
  private Location location;
  private Current current;

  @Data
 public class Current {

   private int temperature;
   private ArrayList<String> weatherDescriptions;
   @JsonProperty("windSpeed")
   private int windSpeed;
   private int precip;
   private int humidity;
   private int feelslike;

 }

   public class Location{
     private String name;
     private String country;
     private String region;
     private String localtime;
  }

}
