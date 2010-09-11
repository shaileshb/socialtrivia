package com.graphsfm.stservice.data;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.xml.bind.annotation.XmlTransient;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable
public class Question {
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key id;

	@Persistent
	private String qid; // APIs use this value as the primary user identifier

	@Persistent
	private String text;

	@Persistent
	private Key userfk;

	@Persistent
	private Date creationDate;

	@Persistent
	private String state;

	@Persistent
	Set<QuestionResponse> responses;

	public Question() {
	}

	public Question(String text, Key userfk) {
		super();
		this.text = text;
		this.userfk = userfk;
		this.creationDate = new Date();
		this.state = QuestionState.NEW.name();
	}

	@XmlTransient
	public Key getId() {
		return id;
	}

	public void setId(Key id) {
		this.id = id;
	}

	public String getQid() {
		return qid;
	}

	public void setQid(String qid) {
		this.qid = qid;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@XmlTransient
	public Key getUserfk() {
		return userfk;
	}

	public void setUserfk(Key userfk) {
		this.userfk = userfk;
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

	public Set<QuestionResponse> getResponses() {
		return responses;
	}

	public void setResponses(Set<QuestionResponse> responses) {
		this.responses = responses;
	}

	public void addResponse(String response) {
		if (getResponses() == null) {
			setResponses(new HashSet<QuestionResponse>());
		}
		for (QuestionResponse qr : getResponses()) {
			if (qr.getText().equals(response)) {
				qr.setCount(qr.getCount() + 1);
				return;
			}
		}
		getResponses().add(new QuestionResponse(response));
	}
}
