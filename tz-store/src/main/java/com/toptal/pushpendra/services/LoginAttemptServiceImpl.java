package com.toptal.pushpendra.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.toptal.pushpendra.entities.LoginAttempts;
import com.toptal.pushpendra.mappers.LoginAttemptMapper;
import com.toptal.pushpendra.models.LoginAttemptBO;
import com.toptal.pushpendra.repositories.ILoginAttemptsRepository;

@Service
public class LoginAttemptServiceImpl implements ILoginAttemptService {

	@Autowired
	private ILoginAttemptsRepository repository;
	
	@Autowired
	private LoginAttemptMapper mapper;
	
	@Override
	public LoginAttemptBO getAttemptByUserName(String username) {
		LoginAttempts attempts = repository.getOne(username);
		return mapper.toLoginAttemptBO(attempts);
	}

	@Override
	public void updateCounter(String username, int i) {
		LoginAttempts attempt = getOrCreateEntity(username);
		attempt.setCounter(i);
		repository.save(attempt);
	}

	@Override
	public LoginAttemptBO getOrCreateLoginAttempt(String username) {
		LoginAttempts attempt = getOrCreateEntity(username);
		return mapper.toLoginAttemptBO(attempt);
	}

	private LoginAttempts getOrCreateEntity(String username) {
		boolean exist = repository.existsById(username);
		LoginAttempts attempt = null;
		if(!exist) {
			attempt = new LoginAttempts();
			attempt.setUsername(username);
			attempt.setCounter(0);
			repository.save(attempt);
		}else {
			attempt = repository.getOne(username);
		}
		return attempt;
	}

}
