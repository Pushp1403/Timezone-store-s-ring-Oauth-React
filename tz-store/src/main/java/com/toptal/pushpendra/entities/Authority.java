package com.toptal.pushpendra.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Authority implements Serializable {

	private static final long serialVersionUID = -8543898506829193690L;

	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "USERNAME")
	private String username;

	@Column(name = "AUTHORITY")
	private String authority;

	public Authority(long id, String username, String authority) {
		this.id = id;
		this.username = username;
		this.authority = authority;
	}

	public Authority() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

}
