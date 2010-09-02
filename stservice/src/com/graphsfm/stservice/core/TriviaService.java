package com.graphsfm.stservice.core;

import java.util.List;

import com.graphsfm.stservice.data.Question;

public interface TriviaService {
	public List<Question> getNextQuestions(long userid, int limit);
	public void addQuestion(long userid, String text);
	public void saveAnswer(long userid, long questionId, String text);
}
