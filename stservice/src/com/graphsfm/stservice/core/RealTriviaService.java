package com.graphsfm.stservice.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

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
	public List<Question> getNextQuestions(long userid, int limit) {
		PersistenceManager pm = pmp.get();
		
		log.info("getNextQuestions: pm = " + pm.toString());

		long lastQuestionId = 0;
		User u = userService.getUser(userid);
		if (u != null)
			lastQuestionId = u.getLastQuestionId();

		Map<String, Object> params = null;
		Query q = pm.newQuery(Question.class);
		q.setFilter("state == " + QuestionState.NEW.name() + " || state == "
				+ QuestionState.OPEN.name());
		if (lastQuestionId > 0) {
			params = addParam(params, "lastQuestionId", lastQuestionId);
			q.setFilter("id > lastQuestionId");
			q.declareParameters("long lastQuestionId");
		}
		q.setOrdering("id asc");
		q.setRange(0, limit);

		return (List<Question>) q.executeWithMap(params);
	}

	private Map<String, Object> addParam(Map<String, Object> params,
			String name, Object value) {
		if (params == null)
			params = new HashMap<String, Object>();
		params.put(name, value);
		return params;
	}

	@Override
	public void addQuestion(long userid, String text) {
		Question q = new Question(text, userid);
		PersistenceManager pm = pmp.get();
		pm.makePersistent(q);

		log.info("addQuestion: pm = " + pm.toString());
	}

	@Override
	public void saveAnswer(long userid, long questionId, String text) {
		// PersistenceManager pm = pmp.get();
		// Question q = pm.getObjectById(Question.class, userid);

		// Question contains answer stats.
		// User contains answer log. (question id, canonicalized answer
		// text).
	}
}
