package com.toptal.pushpendra.services;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.toptal.pushpendra.models.LoginAttemptBO;

@Service
@Transactional
public interface ILoginAttemptService {

	LoginAttemptBO getAttemptByUserName(String username);

	void updateCounter(String username, int i);

	LoginAttemptBO getOrCreateLoginAttempt(String username);

}
