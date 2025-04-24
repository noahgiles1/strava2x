package com.noah.strava2x.strava.dto;

import lombok.Data;

@Data
public class RefreshTokenRequest {
  private String client_id;
  private String client_secret;
  private String refresh_token;
  private String grant_type = "refresh_token";
}
