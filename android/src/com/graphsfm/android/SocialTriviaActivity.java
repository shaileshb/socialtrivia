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
	/** Called when the activity is first created. */
	private MediaPlayer m_mediaPlayer;
	private MediaClipIterator mediaclipiterator;
	private Score score = new Score();
	static final int ANSWER_QUESTION = 4;
	private Context mcontext;
	CountDownTimer mtimer;
	GoogleAnalyticsTracker tracker;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tracker = GoogleAnalyticsTracker.getInstance();
        tracker.start("UA-5092970-7", 20, this);

        setContentView(R.layout.game);
        mcontext = this;  
        
        m_mediaPlayer = new MediaPlayer();
        
        try {
				
				mediaclipiterator = new MediaClipIterator(this);
				// Convert the JSONArray to object array
 				showView();
				
	        
 				final Button button = (Button) findViewById(R.id.button);

				button.setOnClickListener(new OnClickListener() {
		        	
		        	 @Override
		             public void onClick(View v) {
		               tracker.trackEvent(
		                   "Clicks",  // Category
		                   "Button",  // Action
		                   "stopmusic", // Label
		                   77);       // Value
		        	 	
		               		musicStarted = false;
		    		        m_mediaPlayer.pause();
		            	    mtimer.cancel();
		            	    ToggleButton b = (ToggleButton) findViewById(R.id.button);
		            	    b.setChecked(false);
    
		            	    Intent myIntent = new Intent(v.getContext(), AnswerActivity.class);
		            	    myIntent.putExtra("score", score.getScore());
		                    startActivityForResult(myIntent, ANSWER_QUESTION);
		            	    
		            	    		            	
		            }
		            
	        
        });
        }
			 catch (Exception e1) {
				Toast.makeText(this, e1.getMessage(), Toast.LENGTH_SHORT).show();
				// TODO Auto-generated catch block
				e1.printStackTrace();
				return;
		}
		       
    }
	@Override
    protected void onStop(){
       super.onStop();
       mediaclipiterator.savestate();
	}
	
	@Override
	  protected void onDestroy() {
	    super.onDestroy();
	    // Stop the tracker when it is no longer needed.
	    tracker.stop();
	  }

	boolean musicStarted = false;
	protected void showView() throws Exception {
		tracker.trackPageView("/play1");
		
		m_mediaPlayer.reset();
		m_mediaPlayer.setDataSource(MediaClipIterator.currentMediaClip.getLocation());
		m_mediaPlayer.prepare();
		
		TextView timer = (TextView)findViewById(R.id.timer);
		timer.setText("");
		TextView t = (TextView) findViewById(R.id.score);

		t.setText("Score :" + score.getScore());
        
		Context context = getApplicationContext();
		CharSequence text = "Get Ready!";
		int duration = Toast.LENGTH_LONG;

		final Toast toast = Toast.makeText(context, text, duration);
		toast.show();
		
	    final TextView mTextField = (TextView)findViewById(R.id.timer);
	    mtimer = new CountDownTimer(34000, 1000) {

	        public void onTick(long millisUntilFinished) {
	        	if ((millisUntilFinished/1000 <= 30) && ! musicStarted ) {
	        		toast.cancel();
            		m_mediaPlayer.start();
            		TextView t =(TextView)findViewById(R.id.hint_id); 
            	    t.setText(R.string.hint_txt1);
            	    ToggleButton b = (ToggleButton) findViewById(R.id.button);
            	    b.setChecked(true);
            	    musicStarted = true;
	        	}
	            mTextField.setText( Long.toString( millisUntilFinished / 1000));
	        }

	        public void onFinish() {
	        	
	        	// When count down is done turn off the music
	        	m_mediaPlayer.pause();
	        	musicStarted = false;
        	    ToggleButton b = (ToggleButton) findViewById(R.id.button);
        	    b.setChecked(false);
        	    Intent myIntent = new Intent(mcontext, AnswerActivity.class);
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
					mediaclipiterator.next();	
					showView();
				} catch (Exception e) {
					e.printStackTrace();
				
				}
			}
	}

}