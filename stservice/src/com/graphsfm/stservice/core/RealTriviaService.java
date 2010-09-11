package com.graphsfm.stservice.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.google.appengine.api.datastore.Key;
import com.google.inject.Inject;
import com.graphsfm.stservice.data.Question;
import com.graphsfm.stservice.data.QuestionState;
import com.graphsfm.stservice.data.User;
import com.graphsfm.stservice.guice.PersistenceManagerProvider;

public class RealTriviaService implements TriviaService {
	private static Logger log = Logger.getLogger(RealTriviaService.class.getName());

	@Inject
	private PersistenceManagerProvider pmp;

	@Inject
	private UserService userService;

	@SuppressWarnings("unchecked")
	@Override
	public List<Question> getNextQuestions(String uid, int limit) {
		PersistenceManager pm = pmp.get();
		
		log.info("getNextQuestions: pm = " + pm.toString());

		Key lastQuestionId = null;
		User u = userService.getUser(uid);
		if (u != null)
			lastQuestionId = u.getLastQuestionKey();

		Map<String, Object> params = null;
		Query q = pm.newQuery(Question.class);
		q.setFilter("state == " + QuestionState.NEW.name() + " || state == "
				+ QuestionState.OPEN.name());
		if (lastQuestionId != null) {
			params = addQueryParam(params, "lastQuestionId", lastQuestionId);
			q.setFilter("id > lastQuestionId");
			q.declareParameters("long lastQuestionId");
		}
		q.setOrdering("id asc");
		q.setRange(0, limit);

		return (List<Question>) q.executeWithMap(params);
	}

	private Map<String, Object> addQueryParam(Map<String, Object> params,
			String name, Object value) {
		if (params == null)
			params = new HashMap<String, Object>();
		params.put(name, value);
		return params;
	}

	@Override
	public void addQuestion(String uid, String text) {
		User u = userService.getUser(uid);
		if (u == null)
			return;
		
		Question q = new Question(text, u.getId());
		PersistenceManager pm = pmp.get();
		pm.makePersistent(q);
		q.setQid(Long.toString(System.currentTimeMillis() / 1000) + "-" + q.getId().getId());
	}
	
	public Question getQuestion(String qid) {
		if (qid == null || qid.length() == 0)
			return null;
		PersistenceManager pm = pmp.get();
		Query q = pm.newQuery(Question.class);
		q.setFilter("qid == keyvalue");
		q.declareParameters("String keyvalue");
		q.setUnique(true);
		return (Question) q.execute(qid);
	}
	
	@Override
	public void saveAnswer(String uid, String qid, String text) {
		Question q = getQuestion(qid);
		q.addResponse(text);
	}
}
