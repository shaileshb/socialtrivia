package com.graphsfm.android;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class TabLayoutActivity extends TabActivity {
	TabHost mTabHost;
	FrameLayout mFrameLayout;

	private Intent getGamePlayActivity() {
		Intent intent = new Intent();
		intent.setClass(this, SocialTriviaActivity.class);
		return intent;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
//		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.tabmain);
		mTabHost = getTabHost();

		TabSpec tabSpec1 = mTabHost.newTabSpec("play_tab");
		tabSpec1.setContent(getGamePlayActivity());
		tabSpec1.setIndicator("Play");
		mTabHost.addTab(tabSpec1);

		TabSpec tabSpec2 = mTabHost.newTabSpec("my_music_tab");
		tabSpec2.setIndicator("My Music");
		tabSpec2.setContent(R.id.textview2);
		mTabHost.addTab(tabSpec2);

		TabSpec tabSpec3 = mTabHost.newTabSpec("pref_tab");
		tabSpec3.setIndicator("Preferences");
		tabSpec3.setContent(R.id.textview3);
		mTabHost.addTab(tabSpec3);

		mTabHost.setCurrentTab(0);
	}
}
