package com.graphsfm.stservice.core;

import java.util.Date;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.google.appengine.api.datastore.Key;
import com.google.inject.Inject;
import com.graphsfm.stservice.data.Question;
import com.graphsfm.stservice.data.User;
import com.graphsfm.stservice.guice.PersistenceManagerProvider;

public class UserServiceImpl implements UserService {
	private static Logger log = Logger.getLogger(UserServiceImpl.class.getName());
	
	private PersistenceManagerProvider pmp;
	private TriviaService triviaService;
	
	@Inject
	protected void setPMP(PersistenceManagerProvider pmp) {
		this.pmp = pmp;
	}
	
	@Inject
	protected void setTriviaService(TriviaService triviaService) {
		this.triviaService = triviaService;
	}

	@Override
	public User getUser(String uid) {
		if (uid == null || uid.length() == 0)
			return null;
		PersistenceManager pm = pmp.get();
		Query q = pm.newQuery(User.class);
		q.setFilter("uid == keyvalue");
		q.declareParameters("String keyvalue");
		q.setUnique(true);
		return (User) q.execute(uid);
	}
	
	@Override
	public User createUser() {
		User u = new User();
		PersistenceManager pm = pmp.get();
		pm.makePersistent(u);
		
		u.setUid(Long.toString(System.currentTimeMillis() / 1000) + "-" + u.getId().getId());
		u.setLastCheckinTime(new Date());
		log.info("Created user: " + u);
		
		return u;
	}

	@Override
	public User getUserById(Key userfk) {
		PersistenceManager pm = pmp.get();
		return (User) pm.getObjectById(User.class, userfk);
	}

	@Override
	public void saveAnswer(String uid, String qid, String answer) {
		User u = getUser(uid);
		Question q = triviaService.getQuestion(qid);
		u.setLastQuestionKey(q.getId());
		u.addResponse(q, answer);
	}
}
