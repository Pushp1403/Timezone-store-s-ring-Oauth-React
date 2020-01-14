package com.toptal.pushpendra.models;

import java.io.Serializable;

public class JwtTokenRequest implements Serializable {

	private static final long serialVersionUID = 8740763298458782028L;
	private String username;
	private String password;

	public JwtTokenRequest(String userName, String password) {
		super();
		this.username = userName;
		this.password = password;
	}

	public JwtTokenRequest() {
		super();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String userName) {
		this.username = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
