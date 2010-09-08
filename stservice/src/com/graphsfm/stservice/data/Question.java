package com.graphsfm.stservice.data;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class Question {
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;

	@Persistent
	private String text;

	@Persistent
	private long userId;

	@Persistent
	private Date creationDate;
	
	@Persistent
	private String state;
	
	public Question() {
	}

	public Question(String text, long userId) {
		super();
		this.text = text;
		this.userId = userId;
		this.creationDate = new Date();
		this.state = QuestionState.NEW.name();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public QuestionState getQuestionState() {
		return QuestionState.valueOf(state);
	}

	public String getState() {
		return state;
	}
	
	public void setState(String state) {
		this.state = state;
	}
}
