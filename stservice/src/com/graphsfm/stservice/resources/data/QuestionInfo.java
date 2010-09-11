package com.graphsfm.stservice.resources.data;

import javax.xml.bind.annotation.XmlRootElement;

import com.graphsfm.stservice.data.Question;
import com.graphsfm.stservice.data.User;

@XmlRootElement
public class QuestionInfo {
	private Question question;
	private User sender;
	private String[] hints;
	private int[] counts;
	
	public QuestionInfo() {
	}

	public QuestionInfo(Question q, User sender) {
		this.question = q;
		this.sender = sender;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public User getSender() {
		return sender;
	}

	public void setSender(User sender) {
		this.sender = sender;
	}

	public String[] getHints() {
		return hints;
	}

	public void setHints(String[] hints) {
		this.hints = hints;
	}

	public int[] getCounts() {
		return counts;
	}

	public void setCounts(int[] counts) {
		this.counts = counts;
	}
}
