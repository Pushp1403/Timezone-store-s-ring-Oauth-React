package com.toptal.pushpendra.models;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class JwtUserDetails extends BaseModelDetail implements UserDetails {

	private static final long serialVersionUID = -7482314997573752450L;

	private String username;
	private String firstName;
	private String lastName;
	private String password;
	private String emailId;
	private boolean emailVerified;
	private boolean locked;
	private String lockedDueTo;
	private String provider;
	private String profilePictureUrl;
	private List<AuthorityBO> authorities;

	public JwtUserDetails() {
		super();
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public String getProfilePictureUrl() {
		return profilePictureUrl;
	}

	public void setProfilePictureUrl(String profilePictureUrl) {
		this.profilePictureUrl = profilePictureUrl;
	}

	public boolean isEmailVerified() {
		return emailVerified;
	}

	public void setEmailVerified(boolean emailVerified) {
		this.emailVerified = emailVerified;
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public String getLockedDueTo() {
		return lockedDueTo;
	}

	public void setLockedDueTo(String lockedDueTo) {
		this.lockedDueTo = lockedDueTo;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public List<AuthorityBO> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(List<AuthorityBO> authorities) {
		this.authorities = authorities;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	@JsonIgnore
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	@JsonIgnore
	public boolean isAccountNonLocked() {
		return !this.locked;
	}

	@Override
	@JsonIgnore
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	@JsonIgnore
	public boolean isEnabled() {
		return this.emailVerified;
	}

}
