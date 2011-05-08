package com.graphsfm.stservice.data;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.google.appengine.api.datastore.Key;

@XmlRootElement
@PersistenceCapable
public class MediaClip {
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key id;
	
	@Persistent
	private String location;
	
	@Persistent
	private String artistName;
	
	public MediaClip(String location, String artistName) {
		this.setLocation(location);
		this.setArtistName(artistName);
	}

	@XmlTransient
	public Key getId() {
		return id;
	}

	public void setId(Key id) {
		this.id = id;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getLocation() {
		return location;
	}

	public void setArtistName(String artistName) {
		this.artistName = artistName;
	}

	public String getArtistName() {
		return artistName;
	}
}
