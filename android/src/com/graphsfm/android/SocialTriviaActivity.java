package com.graphsfm.android;

import android.app.Activity;
import android.os.Bundle;

public class SocialTriviaActivity extends Activity
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setTitle("Social Trivia");
        setContentView(R.layout.main);
    }
}
