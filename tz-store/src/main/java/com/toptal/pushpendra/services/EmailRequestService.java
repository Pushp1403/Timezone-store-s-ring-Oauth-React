package com.toptal.pushpendra.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.toptal.pushpendra.entities.EmailRequest;
import com.toptal.pushpendra.entities.User;
import com.toptal.pushpendra.exceptions.BadRequestException;
import com.toptal.pushpendra.exceptions.UserAlreadyExistException;
import com.toptal.pushpendra.mappers.UserDetailsMapper;
import com.toptal.pushpendra.models.JwtUserDetails;
import com.toptal.pushpendra.repositories.IEmailRequestRepository;
import com.toptal.pushpendra.repositories.IUserRepository;
import com.toptal.pushpendra.utilities.Constants;

@Service
public class EmailRequestService implements IEmailRequestService {
	
	@Autowired
	private UserDetailsMapper mapper;
	
	@Autowired
	private IEmailRequestRepository emailRequestRepository;
	
	@Autowired
	private IUserRepository userRepository;

	@Override
	public void createEmailRequest(JwtUserDetails details) {
		EmailRequest request = mapper.createEmailVerificationRequest(details);
		emailRequestRepository.save(request);
	}

	@Override
	@Transactional
	public void verifyEmail(String accessToken) {
		EmailRequest request = findRequest(accessToken);
		User user = userRepository.getOne(request.getUsername());
		user.setEmailVerified(true);
		userRepository.save(user);
	}
	
	private EmailRequest findRequest(String accessToken) {
		EmailRequest request = emailRequestRepository.findByToken(accessToken);
		if(null == request)
			throw new BadRequestException(Constants.INVALID_CREDENTIALS);
		return request;
	}

	@Override
	public void createEmailInvitationRequestyEmail(String email) {
		boolean userExists = userRepository.existsByEmailId(email);
		if(userExists)
			throw new UserAlreadyExistException(Constants.USER_ALREADY_EXISTS);
		EmailRequest request = mapper.createEmailInvitationRequest(email);
		emailRequestRepository.save(request);
	}

}
