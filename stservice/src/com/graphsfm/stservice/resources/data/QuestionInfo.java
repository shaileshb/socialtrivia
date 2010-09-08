package com.graphsfm.stservice.resources.data;

import javax.xml.bind.annotation.XmlRootElement;

import com.graphsfm.stservice.data.Question;

@XmlRootElement
public class QuestionInfo {
	private Question question;
	private String[] hints;
	private int[] counts;
	
	public QuestionInfo() {
	}

	public QuestionInfo(Question q) {
		this.question = q;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
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
