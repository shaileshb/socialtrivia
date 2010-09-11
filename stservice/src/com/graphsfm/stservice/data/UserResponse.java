package com.graphsfm.stservice.data;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable
public class UserResponse {
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key id;

	@Persistent
	private Key questionfk;

	@Persistent
	private String text;
	
	@Persistent
	private Date timestamp;

	public UserResponse() {
	}

	public UserResponse(Key questionfk, String answer) {
		this.questionfk = questionfk;
		this.text = answer;
		this.timestamp = new Date();
	}

	public Key getId() {
		return id;
	}

	public void setId(Key id) {
		this.id = id;
	}

	public Key getQuestionfk() {
		return questionfk;
	}

	public void setQuestionfk(Key questionfk) {
		this.questionfk = questionfk;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
}
