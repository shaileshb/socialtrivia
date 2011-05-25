package com.graphsfm.android;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import android.os.CountDownTimer;

public class SocialTriviaActivity extends Activity {
	/** Called when the activity is first created. */
	private MediaPlayer m_mediaPlayer;
	private MediaClipIterator mediaclipiterator;
	private Score score = new Score();
	static final int ANSWER_QUESTION = 4;
	private Context mcontext;
	CountDownTimer mtimer;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);
        mcontext = this;  
        
        m_mediaPlayer = new MediaPlayer();
        
        try {
				
				mediaclipiterator = new MediaClipIterator(this);
				// Convert the JSONArray to object array
 				showView();
				
	        
				final Button button = (Button) findViewById(R.id.button);
	        
				button.setOnClickListener(new OnClickListener() {
		        	Boolean mediastatus = false;
		        	
		            public void onClick(View v) {
		                // Perform action on clicks
		            	if( ! mediastatus) {
		            		m_mediaPlayer.start();
		            		TextView t =(TextView)findViewById(R.id.hint_id); 
		            	    t.setText(R.string.hint_txt1);
		            	    final TextView mTextField = (TextView)findViewById(R.id.timer);
		            	    
		            	    mtimer = new CountDownTimer(30000, 1000) {

		            	        public void onTick(long millisUntilFinished) {
		            	            mTextField.setText( Long.toString( millisUntilFinished / 1000));
		            	        }

		            	        public void onFinish() {
		            	           // mTextField.setText("done!");
		            	        }
		            	     }.start();

		            	}else { 
		            	    m_mediaPlayer.pause();
		            	    mtimer.cancel();
		            	    
		            	    Intent myIntent = new Intent(v.getContext(), AnswerActivity.class);
		            	    myIntent.putExtra("score", score.getScore());
		                    startActivityForResult(myIntent, ANSWER_QUESTION);
		            	    
		            	    		            	
		            }
		            mediastatus = !mediastatus; // toggle the value
	        }});
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
	
	protected void showView() throws Exception {
		
		m_mediaPlayer.reset();
		m_mediaPlayer.setDataSource(MediaClipIterator.currentMediaClip.getLocation());
		m_mediaPlayer.prepare();
		
		TextView timer = (TextView)findViewById(R.id.timer);
		timer.setText("");
		TextView t = (TextView) findViewById(R.id.score);

		t.setText("Score :" + score.getScore());

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