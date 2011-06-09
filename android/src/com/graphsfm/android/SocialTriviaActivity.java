package com.graphsfm.android;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton;
import android.widget.ViewFlipper;
import android.os.CountDownTimer;

public class SocialTriviaActivity extends Activity {
  private MediaPlayer mediaPlayer;
  static final int ANSWER_QUESTION = 4;
  ViewFlipper mviewFlipper;
  CountDownTimer mtimer;
  

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.game);
    GlobalState.getInstance().getTracker().trackPageView("page1");
         
    EarbugDB.open(getApplicationContext());

    
    mediaPlayer = new MediaPlayer();

    try {
      showFirstView();

    } catch (Exception e1) {
      Log.e(this.getClass().getName(),  e1.getMessage());
      Toast.makeText(this, e1.getMessage(), Toast.LENGTH_SHORT).show();
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
    GlobalState.getInstance().getTracker().stop();
  }

  boolean musicStarted = false;

  protected void showFirstView() {
    

    mediaPlayer.reset();
    try {
      mediaPlayer.setDataSource(
            MediaClipIterator.getInstance().getCurrentMediaClip().getLocation());
	    mediaPlayer.prepare();
	
  	} catch (Exception e) {
  		// TODO Auto-generated catch block
  	  Log.e(this.getClass().getName(),  e.getMessage());
  		e.printStackTrace();
  	}

    TextView timer = (TextView) findViewById(R.id.timer);
    timer.setText("");
    TextView t = (TextView) findViewById(R.id.score);

    t.setText("Score :" + GlobalState.getInstance().getScore());

    Context context = getApplicationContext();
    CharSequence text = "Get Ready!";
    int duration = Toast.LENGTH_LONG;

    final Toast toast = Toast.makeText(context, text, duration);
    toast.show();

    Button button = (Button) findViewById(R.id.button);
    button.setOnClickListener(new OnClickListener() {

      @Override
      public void onClick(View v) {
        GlobalState.getInstance().getTracker().trackEvent("Clicks", // Category
            "Button", // Action
            "stopmusic", // Label
            77); // Value
        
        mediaPlayer.pause();
        mtimer.cancel();

        showSecondView();
      }

    });

    final TextView mTextField = (TextView) findViewById(R.id.timer);
    mtimer = new CountDownTimer(32000, 1000) {
      public void onTick(long millisUntilFinished) {
        if ((millisUntilFinished / 1000 <= 30) ) {
          toast.cancel();
          mediaPlayer.start();
          CompoundButton b = (CompoundButton) findViewById(R.id.button);
          b.setChecked(true);
          
        }
        long l = 30000 - millisUntilFinished;
        mTextField.setText("Time: " + Long.toString(l / 1000));
      }

      public void onFinish() {
        // When count down is done turn off the music
        mediaPlayer.pause();
        
        showSecondView();
      }
    }.start();
  }
  
  private int ANSWER_ACTIVITY_REQUEST_CODE = 4;
  private void showSecondView() {
    Intent intent = new Intent(getApplicationContext(),AnswerActivity.class);
    startActivityForResult(intent, ANSWER_ACTIVITY_REQUEST_CODE);
  }
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {

      try {
          MediaClipIterator.getInstance().forward();
          showFirstView();
      } catch (Exception e) {
        e.printStackTrace();
      
      }
    }



}
