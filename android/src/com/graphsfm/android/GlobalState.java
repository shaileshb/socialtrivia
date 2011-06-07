package com.graphsfm.android;

import com.google.android.apps.analytics.GoogleAnalyticsTracker;
import android.content.Context;
public class GlobalState {

  GoogleAnalyticsTracker tracker; // TODO: move this to SplashActivity
  Context context;
  Score score; 
  private static GlobalState INSTANCE = null;
  
  private GlobalState(Context c) {
    this.context = c;
    tracker = GoogleAnalyticsTracker.getInstance();
    tracker.start("UA-5092970-7", 20, c);
    score = new Score();
  }
  public static void open(Context applicationContext) {
    if (INSTANCE == null) {
      INSTANCE = new GlobalState(applicationContext);
      
    }
  }
  
  public void close() {
    INSTANCE = null;
    context = null;
  }
  
  public static GlobalState getInstance() {
      
    return INSTANCE;
  }

  
  
  
  public  GoogleAnalyticsTracker getTracker() {
    return tracker;
  }
  
  public Score  getScore() {
    return score;
  }
  
}
