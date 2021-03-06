package com.graphsfm.stservice.core;

import java.util.List;
import java.util.Set;

import com.graphsfm.stservice.data.Question;

public interface TriviaService {
	public List<Question> getNextQuestions(String userkey, int limit);
	public Question getQuestion(String qid);
	public void addQuestion(String userkey, String text, Set<String> options);
	public void saveAnswer(String uid, String qid, String text);
}
