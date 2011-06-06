package com.graphsfm.android;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton;
import android.widget.ViewFlipper;
import android.os.CountDownTimer;
import com.google.android.apps.analytics.GoogleAnalyticsTracker;

public class SocialTriviaActivity extends Activity {
  private MediaPlayer mediaPlayer;
  private Score score = new Score();
  static final int ANSWER_QUESTION = 4;
  ViewFlipper mviewFlipper;
  CountDownTimer mtimer;
  
  GoogleAnalyticsTracker tracker; // TODO: move this to SplashActivity

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.game);
     
    mviewFlipper = (ViewFlipper) findViewById(R.id.viewflipper);
    
    // TODO: move the google analytics initialization to SplashActivity
    // and share the tracker singleton instance.
    tracker = GoogleAnalyticsTracker.getInstance();
    tracker.start("UA-5092970-7", 20, this);
    
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
  	  Log.e(this.getClass().getName(),  e.getMessage());
  		e.printStackTrace();
  	}

    TextView timer = (TextView) findViewById(R.id.timer);
    timer.setText("");
    TextView t = (TextView) findViewById(R.id.score);

    t.setText("Score :" + Integer.toString(Score.getScore()));

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
        
        mediaPlayer.pause();
        mtimer.cancel();

        showSecondView();
      }

    });

    final TextView mTextField = (TextView) findViewById(R.id.timer);
    mtimer = new CountDownTimer(34000, 1000) {
      public void onTick(long millisUntilFinished) {
        if ((millisUntilFinished / 1000 <= 30) && !musicStarted) {
          toast.cancel();
          mediaPlayer.start();
          CompoundButton b = (CompoundButton) findViewById(R.id.button);
          b.setChecked(true);
          musicStarted = true;
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

  protected void showSecondView()   {
    
    tracker.trackPageView("/play2");
    
      mviewFlipper.showNext();

      Button reply_button = (Button) findViewById(R.id.replay);
      TextView scoreTxtView = (TextView) findViewById(R.id.score1);
      scoreTxtView.setText("Score :" + Integer.toString(Score.getScore()));

      reply_button.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View v) {
          if( musicStarted == false) {
            tracker.trackEvent("Clicks", // Category
                "Button", // Action
                "replay", // Label
                78); // Value
  
            musicStarted = true;
            mediaPlayer.seekTo(0);
            mediaPlayer.start();
          } else {
            
          }
            
        }
      });
      
      Button skipbutton = (Button) findViewById(R.id.skip);
      skipbutton.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
            MediaClipIterator.getInstance().forward();
            mviewFlipper.showNext();
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
          score.addScore(10);
          if (answer.equalsIgnoreCase(artist)) {
            showDialog(1);
          } else {
            showDialog(0);
          }

          new Handler().postDelayed(new Runnable() {
              @Override
              public void run() {
                  mdialog.dismiss();
                  MediaClipIterator.getInstance().forward();
                  mviewFlipper.showNext();
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

	    mdialog = builder.create();
	    return mdialog;
	  }


}
