package com.toptal.pushpendra.services;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.toptal.pushpendra.models.JwtUserDetails;

@Service
@Transactional
public interface IEmailRequestService {

	void createEmailRequest(JwtUserDetails details);

	void verifyEmail(String accessToken);

	void createEmailInvitationRequestyEmail(String email);

}
