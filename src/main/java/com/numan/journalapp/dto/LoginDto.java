package com.numan.journalapp.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginDto {

  @NotBlank
  private String username;
  @NotBlank
  private String password;

}
