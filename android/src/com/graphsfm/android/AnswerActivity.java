package com.graphsfm.android;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class AnswerActivity extends Activity {
    /** Called when the activity is first created. */
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
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
            	 Pattern p = Pattern.compile("beatles");
            	 Matcher m = p.matcher(answer);
            	 boolean b = m.matches();
             	Intent intent = new Intent();
            	if (b) 
            		{
            		intent.putExtra("score", 45);
            		}
                setResult(RESULT_OK, intent);
                finish();	            	
            	
            }    		  
		});
    }
	        
			
}