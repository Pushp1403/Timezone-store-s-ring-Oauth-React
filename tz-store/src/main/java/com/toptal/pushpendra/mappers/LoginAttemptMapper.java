package com.toptal.pushpendra.mappers;

import org.springframework.stereotype.Component;

import com.toptal.pushpendra.entities.LoginAttempts;
import com.toptal.pushpendra.models.LoginAttemptBO;

@Component
public class LoginAttemptMapper {

	public LoginAttemptBO toLoginAttemptBO(LoginAttempts attempts) {
		LoginAttemptBO attemptBO = new LoginAttemptBO();
		attemptBO.setCounter(attempts.getCounter());
		attemptBO.setUsername(attempts.getUsername());
		return attemptBO;
	}

}
