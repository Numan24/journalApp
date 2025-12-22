package com.numan.journalapp.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
@Slf4j
public class JwtUtil {

  // TODO: Store secret key securely later
  private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor("your-256-bit-secret".getBytes(StandardCharsets.UTF_8)); // Replace with your secret key

  private JwtUtil() { // Prevent instantiation and subclassing and reflection
    throw new IllegalStateException("Utility class cannot be instantiated");
  }

  public static String generateToken(String username) {
    return Jwts.builder()
      .subject(username)
      .issuedAt(new Date(System.currentTimeMillis()))
      .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours
      .signWith(SECRET_KEY)
      .compact();
  }

  private Claims extractAllClaims(String token) {
    return Jwts
      .parser()
      .verifyWith(SECRET_KEY)
      .build()
      .parseSignedClaims(token)
      .getPayload();
  }

  public String extractUsername(String token) {
    return extractAllClaims(token).getSubject();
  }

  public boolean validateToken(String token, String username) {
    try {
      return extractUsername(token).equals(username) && !isTokenExpired(token);

    } catch (ExpiredJwtException e) {
      log.error("Token expired: {}", e.getMessage());
    } catch (SecurityException e) {
      log.error("Invalid JWT signature: {}", e.getMessage());
    } catch (MalformedJwtException e) {
      log.error("Invalid JWT token: {}", e.getMessage());
    } catch (JwtException e) {
      log.error("JWT error: {}", e.getMessage());
    }

    return false;

}

  private boolean isTokenExpired(String token) {
    return extractAllClaims(token).getExpiration().before(new Date());
  }

  }
