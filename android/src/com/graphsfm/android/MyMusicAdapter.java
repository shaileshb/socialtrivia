package com.graphsfm.android;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.ViewGroup;

public class MyMusicAdapter extends ArrayAdapter<MyMusic>{
	  private ArrayList<MyMusic> items;
	  
	  public MyMusicAdapter(Context context, int textViewResourceId, ArrayList<MyMusic> items) {
          super(context, textViewResourceId, items);
          this.items = items;
  }
	  
	  @Override
      public View getView(int position, View convertView, ViewGroup parent) {
              View v = convertView;
              if (v == null) {
                  LayoutInflater vi = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                  v = vi.inflate(R.layout.myanswers_row, null);
              }
              MyMusic mymusic = items.get(position);
              if (mymusic != null) {
                      TextView tt = (TextView) v.findViewById(R.id.songname);
                      TextView bt = (TextView) v.findViewById(R.id.bandname);
                      if (tt != null) {
                            tt.setText( mymusic.getSongName());                            }
                      if(bt != null){
                            bt.setText( mymusic.getBandName());
                      }
                      ImageView iv = (ImageView) v.findViewById(R.id.result_icon_small);
                      if( mymusic.isResult())
                    	  iv.setImageResource(R.drawable.correct_small);
                      else
                    	  iv.setImageResource(R.drawable.wrong_small);
              }
              return v;
      }
}
