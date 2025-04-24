package com.noah.strava2x.strava.dto;

import lombok.Data;

@Data
public class TokenResponse {
  private String access_token;
  private String token_type;
  private String refresh_token;
  private Long expires_in;
  private Long expires_at;
}
