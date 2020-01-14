package com.toptal.pushpendra.mappers;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.stereotype.Component;

import com.toptal.pushpendra.entities.Authority;
import com.toptal.pushpendra.entities.EmailRequest;
import com.toptal.pushpendra.entities.User;
import com.toptal.pushpendra.entities.UserDetail;
import com.toptal.pushpendra.models.AuthorityBO;
import com.toptal.pushpendra.models.FacebookOAuth2UserInfo;
import com.toptal.pushpendra.models.GoogleOAuth2UserInfo;
import com.toptal.pushpendra.models.JwtUserDetails;
import com.toptal.pushpendra.models.OAuth2UserInfo;
import com.toptal.pushpendra.models.UserFilterModel;
import com.toptal.pushpendra.utilities.AuthProvider;
import com.toptal.pushpendra.utilities.Constants;
import com.toptal.pushpendra.utilities.EmailRequestType;

@Component
public class UserDetailsMapper {

	public JwtUserDetails userEntityToJwtUserDetails(User user) {
		JwtUserDetails jwtUserdetails = new JwtUserDetails();
		jwtUserdetails.setUsername(user.getUsername());
		jwtUserdetails.setAuthorities(this.authorityEntitytoAuthorityBo(user.getRoles()));
		this.populateWithUserDetails(jwtUserdetails, user.getUserDetails());
		if (user.isLocked()) {
			jwtUserdetails.setLocked(true);
			jwtUserdetails.setLockedDueTo(user.getLoginAttempt().getReason());
		}
		jwtUserdetails.setEmailVerified(user.isEmailVerified());
		jwtUserdetails.setPassword(user.getPassword());
		jwtUserdetails.setProvider(user.getProvider());
		return jwtUserdetails;
	}

	public void populateWithUserDetails(JwtUserDetails jwtUserdetails, UserDetail userDetails) {
		jwtUserdetails.setUsername(userDetails.getUsername());
		jwtUserdetails.setFirstName(userDetails.getFirstName());
		jwtUserdetails.setLastName(userDetails.getLastName());
		jwtUserdetails.setEmailId(userDetails.getEmailId());
		jwtUserdetails.setCreatedAt(userDetails.getCreatedAt());
		jwtUserdetails.setCreatedBy(userDetails.getCreatedBy());
		jwtUserdetails.setUpdatedAt(userDetails.getUpdatedAt());
		jwtUserdetails.setUpdatedBy(userDetails.getUpdatedBy());
		jwtUserdetails.setProfilePictureUrl(userDetails.getProfilePictureUrl());
	}

	public List<AuthorityBO> authorityEntitytoAuthorityBo(List<Authority> roles) {
		List<AuthorityBO> authorities = roles.stream().map(role -> {
			return new AuthorityBO(role.getAuthority(), role.getUsername(), role.getId());
		}).collect(Collectors.toList());
		return authorities;
	}

	public User jwtUserDetailsToUserEntity(JwtUserDetails jwtUserDetails) {
		User user = new User();
		user.setEmailVerified(jwtUserDetails.isEmailVerified());
		user.setLocked(jwtUserDetails.isLocked());
		user.setUsername(jwtUserDetails.getUsername());
		if (jwtUserDetails.getProvider().equals(AuthProvider.local.toString()))
			user.setPassword(encoder().encode(jwtUserDetails.getPassword()));
		user.setUserDetails(this.jwtUserDetailsToEntity(jwtUserDetails));
		user.setRoles(this.authorityBoToAuthorityEntity(jwtUserDetails.getAuthorities()));
		user.setEmailId(jwtUserDetails.getEmailId());
		user.setProvider(jwtUserDetails.getProvider());
		return user;
	}

	public EmailRequest createEmailVerificationRequest(JwtUserDetails jwtUserDetails) {
		EmailRequest emailRequest = new EmailRequest();
		emailRequest.setUsername(jwtUserDetails.getUsername());
		emailRequest.setEmailId(jwtUserDetails.getEmailId());
		emailRequest.setCreatedAt(new Timestamp(new Date().getTime()));
		emailRequest.setToken(encoder().encode(jwtUserDetails.getEmailId()));
		emailRequest.setRequestType(EmailRequestType.VERIFICATION.toString());
		emailRequest.setCreatedBy(Constants.SYSTEM_USER);
		return emailRequest;
	}

	public List<Authority> authorityBoToAuthorityEntity(List<AuthorityBO> authorities) {
		return authorities.stream().map(auth -> {
			return toAuthorityEntity(auth);
		}).collect(Collectors.toList());
	}

	public Authority toAuthorityEntity(AuthorityBO auth) {
		Authority authority = new Authority();
		authority.setAuthority(
				auth.getAuthority().startsWith("ROLE") ? auth.getAuthority() : "ROLE_" + auth.getAuthority());
		authority.setUsername(auth.getUsername());
		return authority;
	}

	public UserDetail jwtUserDetailsToEntity(JwtUserDetails jwtUserDetails) {
		UserDetail details = new UserDetail();
		details.setFirstName(jwtUserDetails.getFirstName());
		details.setLastName(jwtUserDetails.getLastName());
		details.setEmailId(jwtUserDetails.getEmailId());
		details.setUsername(jwtUserDetails.getUsername());
		details.setCreatedAt(jwtUserDetails.getCreatedAt());
		details.setCreatedBy(jwtUserDetails.getProvider().equals(AuthProvider.local.toString())
				? SecurityContextHolder.getContext().getAuthentication().getName()
				: Constants.SYSTEM_USER);
		details.setProfilePictureUrl(jwtUserDetails.getProfilePictureUrl());
		return details;
	}

	public UserFilterModel createFilterModel(Map<String, String> requestParams) {
		UserFilterModel model = new UserFilterModel(requestParams);
		return model;
	}

	private BCryptPasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

	public EmailRequest createEmailInvitationRequest(String email) {
		EmailRequest request = new EmailRequest();
		request.setCreatedAt(new Timestamp(new Date().getTime()));
		request.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
		request.setEmailId(email);
		request.setRequestType(EmailRequestType.INVITATION.toString());
		request.setToken(encoder().encode(email));
		return request;
	}

	public boolean needUpdate(JwtUserDetails user, OAuth2UserInfo oAuth2UserInfo) {
		return !user.getProfilePictureUrl().trim().equals(oAuth2UserInfo.getImageUrl())
				|| !user.getFirstName().equals(oAuth2UserInfo.getFirstName())
				|| !user.getLastName().equals(oAuth2UserInfo.getLastName());
	}

	public JwtUserDetails oAuthUserToJwtUser(JwtUserDetails user, OAuth2UserInfo oAuth2UserInfo) {
		user.setProfilePictureUrl(oAuth2UserInfo.getImageUrl());
		user.setFirstName(oAuth2UserInfo.getFirstName());
		user.setLastName(oAuth2UserInfo.getLastName());
		user.setUsername(oAuth2UserInfo.getId());
		return user;
	}

	public JwtUserDetails oAuthUserToJwtUser(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo) {
		JwtUserDetails user = new JwtUserDetails();
		user.setUsername(oAuth2UserInfo.getId());
		user.setFirstName(oAuth2UserInfo.getFirstName());
		user.setLastName(oAuth2UserInfo.getLastName());
		user.setCreatedAt(new Timestamp(new Date().getTime()));
		user.setCreatedBy(Constants.SYSTEM_USER);
		user.setEmailId(oAuth2UserInfo.getEmail());
		user.setEmailVerified(true);
		if (GoogleOAuth2UserInfo.class.isInstance(oAuth2UserInfo)) {
			user.setProvider(AuthProvider.google.toString());
		} else if (FacebookOAuth2UserInfo.class.isInstance(oAuth2UserInfo)) {
			user.setProvider(AuthProvider.facebook.toString());
		} else {
			user.setProvider(AuthProvider.github.toString());
		}
		user.setProfilePictureUrl(oAuth2UserInfo.getImageUrl());
		user.setAuthorities(this.getDefaultAuthorities(oAuth2UserInfo.getId()));
		return user;
	}

	private List<AuthorityBO> getDefaultAuthorities(String username) {
		List<AuthorityBO> authorities = new ArrayList<AuthorityBO>();
		AuthorityBO authorityBO = new AuthorityBO();
		authorityBO.setUsername(username);
		authorityBO.setRole(Constants.ROLE_USER);
		authorities.add(authorityBO);
		return authorities;
	}

}
