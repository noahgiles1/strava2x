package com.noah.strava2x.strava;

import com.noah.strava2x.strava.auth.OAuthService;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class StravaWebClient {

  private static final String BASE_URL = "https://www.strava.com/api/v3/";

  private final OAuthService oAuthService;
  private final WebClient webClient;

  public StravaWebClient(final OAuthService oAuthService) {
    this.oAuthService = oAuthService;
    this.webClient = WebClient.builder()
        .baseUrl(BASE_URL)
        .defaultHeader("Authorization", "Bearer "+ oAuthService.getAccessToken())
        .build();
  }
  
  public <T> Mono<T> get(String path, Class<T> responseType) {
    return webClient.get()
        .uri(path)
        .headers(headers -> {
          headers.setBearerAuth(oAuthService.getAccessToken());
        })
        .retrieve()
        .bodyToMono(responseType);
  }
  
  public <T, R> Mono<R> post(String path, T body, Class<R> responseType) {
    return webClient.post()
        .uri(path)
        .headers(headers -> {
          headers.setBearerAuth(oAuthService.getAccessToken());
        })
        .body(BodyInserters.fromValue(body))
        .retrieve()
        .bodyToMono(responseType);
  }


}
