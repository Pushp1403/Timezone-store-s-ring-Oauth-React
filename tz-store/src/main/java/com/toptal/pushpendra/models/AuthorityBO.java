package com.toptal.pushpendra.models;

import java.io.Serializable;

import org.springframework.security.core.GrantedAuthority;

public class AuthorityBO implements Serializable, GrantedAuthority {

	private static final long serialVersionUID = 4402868197766009610L;

	private String role;
	private String username;
	private Long id;

	public AuthorityBO() {
		super();
	}

	public AuthorityBO(String role, String username, Long id) {
		super();
		this.role = role;
		this.username = username;
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String getAuthority() {
		return this.role;
	}

}
