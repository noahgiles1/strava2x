package com.noah.strava2x.strava;

import jakarta.servlet.http.HttpServletResponse;
import java.util.Map;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StravaWebHook {
  
  private static final String VERIFY_TOKEN = "STRAVA";
  
  @PostMapping("/webhook")
  public void webhook() {
    System.out.println("Post Event received:");
  }

  @GetMapping("/webhook")
  public ResponseEntity<Map<String, String>> webhookGet(
      @RequestParam("hub.mode") String mode,
      @RequestParam("hub.verify_token") String token,
      @RequestParam("hub.challenge") String challenge) {

    if (mode != null && token != null) {
      if ("subscribe".equals(mode) && VERIFY_TOKEN.equals(token)) {
        System.out.println("WEBHOOK_VERIFIED");
        return ResponseEntity.ok(Map.of("hub.challenge", challenge));
      } else {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
      }
    }
    return ResponseEntity.badRequest().build();
  }

}
