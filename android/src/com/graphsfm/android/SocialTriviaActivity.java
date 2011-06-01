package com.graphsfm.android;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.ViewFlipper;
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
      showFirstView();

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

  protected void showFirstView() {
    tracker.trackPageView("/play1");

    mediaPlayer.reset();
    try {
		mediaPlayer.setDataSource(MediaClipIterator.getInstance()
		    .getCurrentMediaClip().getLocation());
	    mediaPlayer.prepare();
	
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

    TextView timer = (TextView) findViewById(R.id.timer);
    timer.setText("");
    TextView t = (TextView) findViewById(R.id.score);

    t.setText("Score :" + score.getScore());

    Context context = getApplicationContext();
    CharSequence text = "Get Ready!";
    int duration = Toast.LENGTH_LONG;

    final Toast toast = Toast.makeText(context, text, duration);
    toast.show();

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

        showSecondView();
      }

    });

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
        
        showSecondView();
      }
    }.start();
  }

  protected void showSecondView()   {
      final ViewFlipper vf = (ViewFlipper) findViewById(R.id.viewflipper);
      vf.showNext();

      Button skipbutton = (Button) findViewById(R.id.skip);
      skipbutton.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
          //Intent intent = new Intent();
          //setResult(RESULT_OK, intent);
          //finish();
        	vf.showNext();
            showFirstView();
        }
      });

      Button nextbutton = (Button) findViewById(R.id.next);
      nextbutton.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
          TextView t = (TextView) findViewById(R.id.answer);
          String answer = t.getText().toString();
          String artist = MediaClipIterator.getInstance().getCurrentMediaClip()
              .getBandName();
          if (answer.equalsIgnoreCase(artist)) {
            showDialog(1);
          } else {
            showDialog(0);
          }

          new Handler().postDelayed(new Runnable() {
              @Override
              public void run() {
                  mdialog.dismiss();
                  vf.showNext();
                  showFirstView();
             }
            }, 1500);

        }
      });

  }
  Dialog mdialog;
  
  protected Dialog onCreateDialog(int b) {
	    AlertDialog.Builder builder;
	    Context mContext = this;
	    LayoutInflater inflater = getLayoutInflater();
	    View layout = inflater.inflate(R.layout.answer_feedback,
	        (ViewGroup) findViewById(R.id.answer_feedback_layout));

	    builder = new AlertDialog.Builder(mContext);
	    builder.setView(layout);

	    ImageView image = (ImageView) layout.findViewById(R.id.feedback_id);
	    TextView text = (TextView) layout.findViewById(R.id.feedback_txt_id);
	    if (b == 1) {
	      image.setImageResource(R.drawable.correct);
	      text
	          .setText("That is correct ! The band is "
	              + MediaClipIterator.getInstance().getCurrentMediaClip()
	                  .getBandName());
	    } else {
	      image.setImageResource(R.drawable.wrong);
	      text
	          .setText("Better luck next time.. The band was "
	              + MediaClipIterator.getInstance().getCurrentMediaClip()
	                  .getBandName());
	    }

	    // toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
	    // toast.setDuration(Toast.LENGTH_LONG);
	    mdialog = builder.create();

	    return mdialog;
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
        showFirstView();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}
