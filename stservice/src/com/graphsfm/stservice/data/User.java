package com.graphsfm.stservice.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.google.appengine.api.datastore.Key;

@XmlRootElement
@PersistenceCapable
public class User {
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key id;

	@Persistent
	private String uid; // APIs use this value as the primary user id

	@Persistent
	private String nick;

	@Persistent
	private long lastQuestionId;

	@Persistent
	private Date lastCheckinTime;
	
	@Persistent
	private List<UserResponse> responses;
	
	public User() {
	}

	@XmlTransient
	public Key getId() {
		return id;
	}

	public void setId(Key id) {
		this.id = id;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public long getLastQuestionId() {
		return lastQuestionId;
	}

	public void setLastQuestionId(long lastQuestionId) {
		this.lastQuestionId = lastQuestionId;
	}

	public Date getLastCheckinTime() {
		return lastCheckinTime;
	}

	public void setLastCheckinTime(Date lastCheckinTime) {
		this.lastCheckinTime = lastCheckinTime;
	}

	public List<UserResponse> getResponses() {
		return responses;
	}

	public void setResponses(List<UserResponse> responses) {
		this.responses = responses;
	}

	public void addResponse(Question q, String answer) {
		if (responses == null)
			responses = new ArrayList<UserResponse>();
		responses.add(new UserResponse(q.getId(), answer));
	}
}
