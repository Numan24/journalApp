package com.numan.journalapp.service;

import com.numan.journalapp.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

  private final AuthenticationManager authManager;

  public String authenticate(String username, String password) throws BadRequestException {
    Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    if (authentication.isAuthenticated()) {
      String jwtToken = JwtUtil.generateToken(username);
      return jwtToken;
    }
    throw new BadRequestException("User not Found");
  }

}
