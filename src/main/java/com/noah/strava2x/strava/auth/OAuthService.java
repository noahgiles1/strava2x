package com.noah.strava2x.strava.auth;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.noah.strava2x.strava.dto.TokenResponse;
import com.noah.strava2x.strava.dto.RefreshTokenRequest;
import jakarta.annotation.PostConstruct;
import java.time.Instant;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class OAuthService {
  
  @Value("${strava2x.strava.client-id}")
  private String clientId;
  @Value("${strava2x.strava.client-secret}")
  private String clientSecret;
  @Value("${strava2x.strava.refresh-token}")
  private String refreshToken;
  private String accessToken;
  private Instant expiresAt;
  
  private final OAuthClient oAuthClient;
  private final ObjectMapper objectMapper;
  
  public String getAccessToken() {
    if (expiresAt == null || expiresAt.isBefore(Instant.now())) {
      refreshAccessToken();
    }
    return accessToken;
  }
  
  public void refreshAccessToken() {
    RefreshTokenRequest request = new RefreshTokenRequest();
    request.setClient_id(clientId);
    request.setClient_secret(clientSecret);
    request.setRefresh_token(refreshToken);
    TokenResponse tokenResponse = oAuthClient.post(
        "/token",
        TokenResponse.class,
        objectMapper.convertValue(request, new TypeReference<>() {
        })
        )
        .onErrorResume(e -> {
          // TODO handle properly : ?queue?email?
          System.out.println("Error refreshing access token: " + e.getMessage());
          return Mono.empty();
        }) 
        .block();
    assert tokenResponse != null;
    accessToken = tokenResponse.getAccess_token();
    expiresAt = Instant.now().plusSeconds(tokenResponse.getExpires_in());
  }
}
