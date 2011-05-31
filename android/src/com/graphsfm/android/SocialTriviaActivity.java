package com.graphsfm.android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import android.os.CountDownTimer;
import com.google.android.apps.analytics.GoogleAnalyticsTracker;

public class SocialTriviaActivity extends Activity {
  private MediaPlayer mediaPlayer;
  private Score score = new Score();
  static final int ANSWER_QUESTION = 4;
  private Context mcontext;
  CountDownTimer mtimer;
  
  GoogleAnalyticsTracker tracker; // TODO: move this to SplashActivity

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.game);
    
    // TODO: move the google analytics initialization to SplashActivity
    // and share the tracker singleton instance.
    tracker = GoogleAnalyticsTracker.getInstance();
    tracker.start("UA-5092970-7", 20, this);
    
    EarbugDB.open(getApplicationContext());

    mcontext = this;
    mediaPlayer = new MediaPlayer();

    try {
      showView();

      Button button = (Button) findViewById(R.id.button);
      button.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View v) {
          tracker.trackEvent("Clicks", // Category
              "Button", // Action
              "stopmusic", // Label
              77); // Value

          musicStarted = false;
          mediaPlayer.pause();
          mtimer.cancel();
          ToggleButton b = (ToggleButton) findViewById(R.id.button);
          b.setChecked(false);

          Intent myIntent = new Intent(v.getContext(), AnswerActivity.class);
          myIntent.putExtra("score", score.getScore());
          startActivityForResult(myIntent, ANSWER_QUESTION);

        }

      });
    } catch (Exception e1) {
      Toast.makeText(this, e1.getMessage(), Toast.LENGTH_SHORT).show();
      e1.printStackTrace();
      return;
    }
  }

  @Override
  protected void onStop() {
    super.onStop();
    MediaClipIterator.getInstance().savestate();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    
    EarbugDB.getInstance().close();
    
    // Stop the tracker when it is no longer needed.
    tracker.stop();
  }

  boolean musicStarted = false;

  protected void showView() throws Exception {
    tracker.trackPageView("/play1");

    mediaPlayer.reset();
    mediaPlayer.setDataSource(MediaClipIterator.getInstance()
        .getCurrentMediaClip().getLocation());
    mediaPlayer.prepare();

    TextView timer = (TextView) findViewById(R.id.timer);
    timer.setText("");
    TextView t = (TextView) findViewById(R.id.score);

    t.setText("Score :" + score.getScore());

    Context context = getApplicationContext();
    CharSequence text = "Get Ready!";
    int duration = Toast.LENGTH_LONG;

    final Toast toast = Toast.makeText(context, text, duration);
    toast.show();

    final TextView mTextField = (TextView) findViewById(R.id.timer);
    mtimer = new CountDownTimer(34000, 1000) {
      public void onTick(long millisUntilFinished) {
        if ((millisUntilFinished / 1000 <= 30) && !musicStarted) {
          toast.cancel();
          mediaPlayer.start();
          TextView t = (TextView) findViewById(R.id.hint_id);
          t.setText(R.string.hint_txt1);
          ToggleButton b = (ToggleButton) findViewById(R.id.button);
          b.setChecked(true);
          musicStarted = true;
        }
        mTextField.setText(Long.toString(millisUntilFinished / 1000));
      }

      public void onFinish() {
        // When count down is done turn off the music
        mediaPlayer.pause();
        musicStarted = false;
        ToggleButton b = (ToggleButton) findViewById(R.id.button);
        b.setChecked(false);
        Intent myIntent = new Intent(getApplicationContext(),
            AnswerActivity.class);
        myIntent.putExtra("score", score.getScore());
        startActivityForResult(myIntent, ANSWER_QUESTION);
        // mTextField.setText("done!");
      }
    }.start();
  }

  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == ANSWER_QUESTION) {
      if (resultCode == RESULT_OK) {
        // A contact was picked. Here we will just dis play it
        // to the user.
        if (data.hasExtra("score")) {
          score.setScore(data.getExtras().getInt("score"));
        }
      }
      try {
        MediaClipIterator.getInstance().forward();
        showView();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}
