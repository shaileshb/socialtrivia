package com.graphsfm.stservice.core;

import java.util.List;

import javax.jdo.PersistenceManager;

import com.graphsfm.stservice.data.PMF;
import com.graphsfm.stservice.data.Question;

public class RealTriviaService implements TriviaService {
	@Override
	public List<Question> getNextQuestions(long userid, int limit) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			@SuppressWarnings("unchecked")
			List<Question> list = (List<Question>) pm.newQuery("select from " + Question.class.getName()).execute();
			pm.detachCopyAll(list);
			return list;
		}
		finally {
			pm.close();
		}
	}

	@Override
	public void addQuestion(long userid, String text) {
		Question q = new Question(text, userid, System.currentTimeMillis());
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			pm.makePersistent(q);
		}
		finally {
			pm.close();
		}
	}

	@Override
	public void saveAnswer(long userid, long questionId, String text) {
	}
}
