package com.graphsfm.android;


import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Timer;
import java.util.TimerTask;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class AnswerActivity extends Activity {
    /** Called when the activity is first created. */
	
	MediaClip mMediaClip;
	Dialog mdialog;
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
    	super.onCreate(savedInstanceState);
        
        setContentView(R.layout.game_answer);
        
		final Button skipbutton = (Button) findViewById(R.id.skip);
		skipbutton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	Intent intent = new Intent();
            	
                setResult(RESULT_OK, intent);
                finish();	            	
            }
		});
		//skipbutton.setOnClickListener ();
		final Button nextbutton = (Button) findViewById(R.id.next);
		nextbutton.setOnClickListener (new OnClickListener() {
            public void onClick(View v) {
            	TextView t = (TextView) findViewById( R.id.answer);
            	String answer = t.getText().toString();
            	String band = MediaClipIterator.currentMediaClip.getBandName();
            	 Pattern p = Pattern.compile(band);
            	 Matcher m = p.matcher(answer);
            	 boolean b = m.matches();
             	
            	if (b) { 
            		
            		showAnswerResult(1);
            		} else
            			showAnswerResult(0);
            	
            	Timer myTimer = new Timer();
        		myTimer.schedule(new TimerTask() {
        			@Override
        			public void run() {
        				mdialog.dismiss();
        				Intent intent = new Intent();
        				intent.putExtra("score", 45);
        				setResult(RESULT_OK, intent);
        				finish();
        			}

        		}, 0, 1500);
            	
                
                
                	            	
            	
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
 	    if( b == 1) {
			image.setImageResource(R.drawable.correct);
    		text.setText("That is correct ! The band is " + MediaClipIterator.currentMediaClip.getBandName());
		}
		else {
			image.setImageResource(R.drawable.wrong);
			text.setText("Better luck next time.. The band was " + MediaClipIterator.currentMediaClip.getBandName());
    	}

		//toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		//toast.setDuration(Toast.LENGTH_LONG);
    	mdialog = builder.create();

 	    return mdialog;
    }
}