package com.noah.strava2x.strava;

import com.noah.strava2x.strava.auth.OAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestControllers {
  
  private final OAuthService oAuthService;
  private final StravaService stravaService;
  
  @PostMapping("/refresh_token")
  public void refreshToken() {
    oAuthService.refreshAccessToken();
  }
  
  @GetMapping("/activities")
  public void getActivities() {
    long activityId = 14274088408L;
    stravaService.getActivities();
  }

  @GetMapping("/activity")
  public void getActivity() {
    long activityId = 14274088408L;
    stravaService.getActivity(activityId);
  }
  

}
