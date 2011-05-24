package com.graphsfm.android;

import java.util.ArrayList;
import java.util.Iterator;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.Context;
public class MediaClipIterator  implements Iterator {

	ArrayList<MediaClip> mediaclips;
	Activity mactivity;
	int offset;
	public static final String PREFS_NAME = "earbuzilla";

	MediaClipIterator(Activity activity) throws Exception {
		mediaclips =  RestClient.connect("http://192.168.0.4/mediaclip.json");
		
		mactivity = activity;
		SharedPreferences settings = activity.getSharedPreferences(PREFS_NAME, 0);

		//SharedPreferences settings = activity.getPreferences(Context.MODE_PRIVATE);
	    offset = settings.getInt( "offset", 0);
	}
	@Override
	public boolean hasNext() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public Object next() {
		// TODO Auto-generated method stub
		if( mediaclips.size() <= offset)
				offset = 0;
		else
				offset = offset + 1;
		return mediaclips.get(offset);
	}
	@Override
	public void remove() {
		// TODO Auto-generated method stub
		
	}
	public void savestate() {
		// TODO Auto-generated method stub
		SharedPreferences settings = mactivity.getSharedPreferences(PREFS_NAME, 0);
	      SharedPreferences.Editor editor = settings.edit();
	      editor.putInt("offset", offset);

	      // Commit the edits!
	      editor.commit();

	}
    
}
