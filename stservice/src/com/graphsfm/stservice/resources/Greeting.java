package com.graphsfm.stservice.resources;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Greeting {
	private String text;
	private String recipient;
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getRecipient() {
		return recipient;
	}
	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}
}
