package com.graphsfm.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;

public class SplashActivity extends Activity {
  private static final int SPLASH_TIME = 3000;
  private boolean isActive = true;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.splash);

    // TODO: This is a good place to kick off initialization
    // and wait for it to complete. For now - using a 3 second
    // idle loop to simulate initialization.

    new Handler().postDelayed(new Runnable() {
      @Override
      public void run() {
        finishSplashActivity();
      }
    }, SPLASH_TIME);
  }

  @Override
  public boolean onTouchEvent(MotionEvent event) {
    if (getResources().getBoolean(R.bool.testsplash)) {
      finishSplashActivity();
    }
    return true;
  }

  public void onPause() {
    super.onPause();
    isActive = false;
  }

  private void finishSplashActivity() {
    if (isActive) {
      finish();
      Intent myIntent = new Intent(getApplicationContext(),
          SocialTriviaActivity.class);
      startActivity(myIntent);
    } else {
      Log.i(getClass().getSimpleName(),
          "ignoring finishSplashActivity(). isActive = false");
    }
  }
}
