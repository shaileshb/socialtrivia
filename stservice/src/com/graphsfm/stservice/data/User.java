package com.graphsfm.stservice.data;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@PersistenceCapable
public class User {
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;

	@Persistent
	private String userkey; // APIs use this value as the primary user
							// identifier

	@Persistent
	private String nick;

	@Persistent
	private long lastQuestionId;

	@Persistent
	private Date lastCheckinTime;

	public User() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserkey() {
		return userkey;
	}

	public void setUserkey(String userkey) {
		this.userkey = userkey;
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

	@Override
	public String toString() {
		return "User [id=" + id + ", userkey=" + userkey + ", nick=" + nick
				+ ", lastQuestionId=" + lastQuestionId + ", lastCheckinTime="
				+ lastCheckinTime + "]";
	}
}
