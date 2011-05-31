package com.graphsfm.android;

import android.content.Context;
import android.content.SharedPreferences;

public class EarbugDB {
  public static final String PREFS_NAME = "earbugdb";
  private static EarbugDB INSTANCE = null;
  private Context context = null;
  
  private EarbugDB(Context applicationContext) {
    this.context = applicationContext;
  }

  public static void open(Context applicationContext) {
    if (INSTANCE == null) {
      INSTANCE = new EarbugDB(applicationContext);
    }
  }
  
  public void close() {
    INSTANCE = null;
    context = null;
  }
  
  public static EarbugDB getInstance() {
    return INSTANCE;
  }
  
  public int getOffset() {
    return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        .getInt("offset", 0);
  }
  
  public void storeOffset(int offset) {
    SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
    SharedPreferences.Editor editor = settings.edit();
    editor.putInt("offset", offset);
    editor.commit();
  }
}
