package com.graphsfm.android;

import java.util.ArrayList;

import android.app.ListActivity;
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

public class MyMusicListActivity extends ListActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);

	  	   getMyMusic();
		  setListAdapter(new MyMusicAdapter(this, R.layout.myanswers_row, mymusic));
	
	  }
	ArrayList<MyMusic> mymusic;
	private void getMyMusic(){
        	mymusic = new ArrayList<MyMusic>();
            MyMusic o1 = new MyMusic();
            o1.setBandName("Band baja");
            o1.setSongName("PeePani");
            o1.setResult(true);
            mymusic.add(o1);
            MyMusic o2 = new MyMusic();
            o2.setBandName("Band baja1");
            o2.setSongName("PeePani1");
            o2.setResult(false);
            mymusic.add(o2);
      }
}
