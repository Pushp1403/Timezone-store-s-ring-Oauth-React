package com.toptal.pushpendra.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.lang.Nullable;

@Entity
@Table(name = "Users")
public class User implements Serializable {

	private static final long serialVersionUID = 2744261772925381766L;
	@Id
	private String username;
	private String password;
	private boolean emailVerified;
	private boolean locked;
	private String emailId;
	private String provider;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = UserDetail.class)
	@JoinColumn(name = "username")
	private UserDetail userDetails;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, targetEntity = Authority.class)
	private List<Authority> roles;

	@Nullable
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, targetEntity = TimeZone.class, orphanRemoval = true)
	@JoinColumn(name = "username")
	private List<TimeZone> timeZones;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, targetEntity = LoginAttempts.class)
	@JoinColumn(name = "username")
	private LoginAttempts loginAttempt;

	public LoginAttempts getLoginAttempt() {
		return loginAttempt;
	}

	public void setLoginAttempt(LoginAttempts loginAttempt) {
		this.loginAttempt = loginAttempt;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String authProvider) {
		this.provider = authProvider;
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public List<TimeZone> getTimeZones() {
		return timeZones;
	}

	public void setTimeZones(List<TimeZone> timeZones) {
		this.timeZones = timeZones;
	}

	public UserDetail getUserDetails() {
		return userDetails;
	}

	public void setUserDetails(UserDetail userDetails) {
		this.userDetails = userDetails;
	}

	public List<Authority> getRoles() {
		return roles;
	}

	public void setRoles(List<Authority> roles) {
		this.roles = roles;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEmailVerified() {
		return emailVerified;
	}

	public void setEmailVerified(boolean emailVerified) {
		this.emailVerified = emailVerified;
	}

}
