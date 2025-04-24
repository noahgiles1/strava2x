package com.noah.strava2x.strava.auth;

import java.util.Map;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class OAuthClient {

  private static final String BASE_URL = "https://www.strava.com/oauth";

  private final WebClient webClient;

  public OAuthClient() {
    this.webClient = WebClient.builder()
        .baseUrl(BASE_URL)
        .build();
  }

  public <T, R> Mono<R> post(String path, Class<R> responseType, Map<String, String> params) {
    return webClient.post()
        .uri(uriBuilder -> {
          uriBuilder.path(path);
          params.forEach(uriBuilder::queryParam);
          return uriBuilder.build();
        })
        .retrieve()
        .bodyToMono(responseType);
  }

}
