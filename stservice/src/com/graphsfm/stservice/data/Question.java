package com.graphsfm.stservice.data;

import java.io.Serializable;

public class Question implements Serializable {
	private static final long serialVersionUID = 1L;
	private long id;
	private String text;
	private long userId;
	private long creationDate;
	
	public Question() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
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

	public long getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(long creationDate) {
		this.creationDate = creationDate;
	}
}
