package com.graphsfm.stservice.core;

import java.util.List;

import com.graphsfm.stservice.data.MediaClip;

public interface MediaClipService {
	public List<MediaClip> getMediaClip(int offset, int limit);
	public void addMediaClip(String location, String artistName);
}
