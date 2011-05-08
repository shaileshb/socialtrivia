package com.graphsfm.stservice.core;

import java.util.Date;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.google.inject.Inject;
import com.graphsfm.stservice.data.MediaClip;
import com.graphsfm.stservice.data.User;
import com.graphsfm.stservice.guice.PersistenceManagerProvider;


public class MediaClipServiceImpl implements MediaClipService {
	@Inject
	private PersistenceManagerProvider pmp;

	@SuppressWarnings("unchecked")
	@Override
	public List<MediaClip> getMediaClip(int offset, int limit) {
		PersistenceManager pm = pmp.get();
		Query q = pm.newQuery(MediaClip.class);
		q.setRange(offset, limit);
		return (List<MediaClip>) q.execute();
	}

	@Override
	public void addMediaClip(String location,String artistName) {
		
		MediaClip u = new MediaClip(location, artistName);
		PersistenceManager pm = pmp.get();
		pm.makePersistent(u);
		
		
		

	}
}
