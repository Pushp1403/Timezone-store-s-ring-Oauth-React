package com.toptal.pushpendra.events;

import org.springframework.context.ApplicationEvent;

public class EmailInvitaionEvent extends ApplicationEvent {

	private static final long serialVersionUID = -4934296463667156930L;

	private String emailId;

	public EmailInvitaionEvent(String emailId) {
		super(emailId);
		this.emailId = emailId;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

}
