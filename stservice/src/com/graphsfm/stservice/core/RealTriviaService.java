package com.graphsfm.stservice.core;

import java.util.List;

import com.graphsfm.stservice.data.Question;

public class RealTriviaService implements TriviaService {
	@Override
	public List<Question> getNextQuestions(long userid, int limit) {
		return null;
	}

	@Override
	public void addQuestion(long userid, String text) {
	}

	@Override
	public void saveAnswer(long userid, long questionId, String text) {
	}
}
