package com.numan.journalapp.controller;


import com.numan.journalapp.dto.LoginDto;
import com.numan.journalapp.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {

  private final LoginService loginService;

  @PostMapping
  public ResponseEntity login(@RequestBody @Validated LoginDto loginDto) throws BadRequestException {
    String token = loginService.authenticate(loginDto.getUsername(), loginDto.getPassword());
    return ResponseEntity.ok().body("token: " + token);

  }


}
