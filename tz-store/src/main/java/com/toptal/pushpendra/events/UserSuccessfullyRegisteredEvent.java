package com.toptal.pushpendra.events;

import org.springframework.context.ApplicationEvent;

import com.toptal.pushpendra.models.JwtUserDetails;

public class UserSuccessfullyRegisteredEvent extends ApplicationEvent {

	private static final long serialVersionUID = 6980657368325548112L;
	private JwtUserDetails details;

	public UserSuccessfullyRegisteredEvent(JwtUserDetails source) {
		super(source);
		this.details = source;
	}

	public JwtUserDetails getDetails() {
		return details;
	}

}
