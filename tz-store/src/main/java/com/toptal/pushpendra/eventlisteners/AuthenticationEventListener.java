package com.toptal.pushpendra.eventlisteners;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.toptal.pushpendra.events.AuthenticationEvent;
import com.toptal.pushpendra.models.JwtTokenRequest;
import com.toptal.pushpendra.models.LoginAttemptBO;
import com.toptal.pushpendra.services.ILoginAttemptService;
import com.toptal.pushpendra.services.IUserDetailService;
import com.toptal.pushpendra.utilities.AuthEventType;

@Component
public class AuthenticationEventListener {
	
	private final Logger logger = LoggerFactory.getLogger(AuthenticationEventListener.class);
	
	@Autowired
	private ILoginAttemptService loginAttemptService;
	
	@Autowired
	private IUserDetailService userDetailService;
	
	@EventListener
	public void handleAuthenticationEvent(AuthenticationEvent event) {
		if(event.getEventType().equals(AuthEventType.SUCCESS))
			handleSuccessFullAuthentication(event.getRequest());
		else
			handleFailedAuthentication(event.getRequest());			
	}

	@Transactional
	private void handleFailedAuthentication(JwtTokenRequest request) {
		logger.debug("Login failed, regestering failed login");
		LoginAttemptBO attemptBO = loginAttemptService.getOrCreateLoginAttempt(request.getUsername());
		if(attemptBO.getCounter() == 2) {
			userDetailService.updateLockStatus(request.getUsername(), true);
		}
		loginAttemptService.updateCounter(request.getUsername(), attemptBO.getCounter()+1);
	}

	@Transactional
	private void handleSuccessFullAuthentication(JwtTokenRequest request) {
		logger.debug("Login successfull, resetting login attempts counter");
		loginAttemptService.updateCounter(request.getUsername(), 0);
		userDetailService.updateLockStatus(request.getUsername(), false);
	}
	
	

}
