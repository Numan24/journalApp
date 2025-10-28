package com.numan.journalapp.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

  private static final String SECRET_KEY = "your-256-bit-secret"; // Replace with your secret key

  public static String generateToken(String username) {
    Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    return Jwts.builder()
      .subject(username)
      .issuedAt(new Date(System.currentTimeMillis()))
      .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours
      .signWith(key)
      .compact();
  }


}
