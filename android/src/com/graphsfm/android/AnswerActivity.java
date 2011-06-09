package com.graphsfm.android;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.media.MediaPlayer;

public class AnswerActivity extends Activity {
  private Dialog mdialog;
  private boolean musicStarted;
  
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.game_answer);
    final MediaPlayer mediaPlayer = new MediaPlayer();
    try {
        mediaPlayer.setDataSource(
            MediaClipIterator.getInstance().getCurrentMediaClip().getLocation());
        mediaPlayer.prepare();
    }catch (Exception e) {
      Log.e(this.getClass().getName(),  e.getMessage());
    }

    Button reply_button = (Button) findViewById(R.id.replay);
    TextView scoreTxtView = (TextView) findViewById(R.id.score1);
    scoreTxtView.setText("Score :" + GlobalState.getInstance().getScore());

    reply_button.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        if( musicStarted == false) {
          GlobalState.getInstance().getTracker().trackEvent("Clicks", // Category
              "Button", // Action
              "replay", // Label
              78); // Value

          musicStarted = true;
          mediaPlayer.seekTo(0);
          mediaPlayer.start();
        } else {
          mediaPlayer.stop();
          musicStarted = false;
        }
          
      }
    });

    Button skipbutton = (Button) findViewById(R.id.skip);
    skipbutton.setOnClickListener(new OnClickListener() {
      public void onClick(View v) {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        mediaPlayer.stop();
        finish();
      }
    });

    Button nextbutton = (Button) findViewById(R.id.next);
    nextbutton.setOnClickListener(new OnClickListener() {
      public void onClick(View v) {
        TextView t = (TextView) findViewById(R.id.answer);
        String answer = t.getText().toString().toLowerCase();
        String artist = MediaClipIterator.getInstance().getCurrentMediaClip()
            .getBandName().toLowerCase();
        if( artist.indexOf(answer) >= 0 )
        {
          GlobalState.getInstance().getScore().addScore(100);
          showAnswerResult(1);
        } else {
          showAnswerResult(0);
        }

        Timer myTimer = new Timer();
        myTimer.schedule(new TimerTask() {
          @Override
          public void run() {
            mdialog.dismiss();
            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
            
            mediaPlayer.stop();
            finish();
          }
        }, 2000);
      }
    });
  }

  private void showAnswerResult(int b) {
    showDialog(b);
  }

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