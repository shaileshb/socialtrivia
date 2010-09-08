package com.graphsfm.stservice.guice;

import javax.jdo.PersistenceManager;

import com.google.inject.Provider;
import com.google.inject.servlet.RequestScoped;
import com.graphsfm.stservice.data.PMF;

@RequestScoped
public class PersistenceManagerProvider implements Provider<PersistenceManager> {
	private PersistenceManager pm = null;

	@Override
	public PersistenceManager get() {
		if (pm == null)
			pm = PMF.get().getPersistenceManager();
		return pm;
	}
}
