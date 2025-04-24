package com.noah.strava2x.strava;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StravaService {
  
  private final StravaWebClient stravaWebClient;
  
  public void getActivity(long activityId) {
    Object activity = 
        stravaWebClient.get("/activities/" + activityId, Object.class).block();
    System.out.println(activity);
  }
  
  public void getActivities() {
    Object activity =
        stravaWebClient.get("/athlete/activities", Object.class).block();
    System.out.println(activity);
  }

}
