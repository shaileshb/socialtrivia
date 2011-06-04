package com.graphsfm.android;

import java.util.ArrayList;

import android.util.Log;

public class MediaClipIterator {
  private static MediaClipIterator INSTANCE = new MediaClipIterator();
  private ArrayList<MediaClip> mediaclips = null;
  private int offset = 0;

  private MediaClipIterator() {
    try {
      mediaclips = RestClient.connect("http://www.earbuzilla.com/mediaclip/yearlyhit/yearlyhit.json");
    } catch (Exception e) {
      Log.w(this.getClass().getSimpleName(), "Could not fetch media clips", e);
    }
    offset = EarbugDB.getInstance().getOffset();
  }

  public static MediaClipIterator getInstance() {
    return INSTANCE;
  }

  public MediaClip getCurrentMediaClip() {
    return mediaclips.get(offset);
  }

  public void forward() {
    offset++;
    
    // TODO: This is okay for now.. but need better logic for
    // paginating through the content from the server.
    if (offset >= mediaclips.size()) {
      offset = 0;
    }
  }

  public void savestate() {
    EarbugDB.getInstance().storeOffset(offset);
  }
}
