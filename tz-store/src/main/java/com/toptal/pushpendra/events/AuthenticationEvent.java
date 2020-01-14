package com.toptal.pushpendra.events;

import org.springframework.context.ApplicationEvent;

import com.toptal.pushpendra.models.JwtTokenRequest;
import com.toptal.pushpendra.utilities.AuthEventType;

public class AuthenticationEvent extends ApplicationEvent {
	
	private static final long serialVersionUID = -9079068844217097622L;
	private JwtTokenRequest request;
	private AuthEventType eventType;

	public AuthenticationEvent(JwtTokenRequest request, AuthEventType eventType) {
		super(request);
		this.request = request;
		this.eventType = eventType;
	}

	public JwtTokenRequest getRequest() {
		return request;
	}


	public AuthEventType getEventType() {
		return eventType;
	}

}
