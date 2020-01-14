package com.toptal.pushpendra.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class UserToken {

	@Id
	private int tokenId;
	public int getTokenId() {
		return tokenId;
	}
	public void setTokenId(int tokenId) {
		this.tokenId = tokenId;
	}
	private String username;
	private String token;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
	
}
