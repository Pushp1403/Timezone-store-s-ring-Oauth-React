package com.toptal.pushpendra.models;

import java.io.Serializable;

public class JwtTokenResponse implements Serializable {
	private static final long serialVersionUID = -6977115103101052147L;

	private final String token;
	private JwtUserDetails details;

	public JwtTokenResponse(String token, JwtUserDetails details) {
		super();
		details.setPassword(null);
		this.token = token;
		this.details = details;
	}

	public JwtUserDetails getDetails() {
		return details;
	}

	public void setDetails(JwtUserDetails details) {
		this.details = details;
	}

	public JwtTokenResponse(String token) {
		this.token = token;
	}

	public String getToken() {
		return this.token;
	}
}
