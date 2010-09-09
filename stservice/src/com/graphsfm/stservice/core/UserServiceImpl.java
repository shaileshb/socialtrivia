package com.graphsfm.stservice.core;

import java.util.Date;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.google.inject.Inject;
import com.graphsfm.stservice.data.User;
import com.graphsfm.stservice.guice.PersistenceManagerProvider;

public class UserServiceImpl implements UserService {
	private static Logger log = Logger.getLogger(UserServiceImpl.class.getName());
	
	@Inject
	private PersistenceManagerProvider pmp;

	@Override
	public User getUser(String userkey) {
		if (userkey == null || userkey.length() == 0)
			return null;
		PersistenceManager pm = pmp.get();
		Query q = pm.newQuery(User.class);
		q.setFilter("userkey == keyvalue");
		q.declareParameters("String keyvalue");
		q.setUnique(true);
		return (User) q.execute(userkey);
	}
	
	@Override
	public User createUser() {
		User u = new User();
		PersistenceManager pm = pmp.get();
		pm.makePersistent(u);
		
		u.setUserkey(Long.toString(System.currentTimeMillis() / 1000) + "-" + u.getId());
		u.setLastCheckinTime(new Date());
		log.info("Created user: " + u);
		
		return u;
	}
}
