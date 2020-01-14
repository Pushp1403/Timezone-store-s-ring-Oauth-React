package com.toptal.pushpendra.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.toptal.pushpendra.entities.EmailRequest;

@Repository
public interface IEmailRequestRepository extends JpaRepository<EmailRequest, Integer> {

	EmailRequest findByToken(String accessToken);

	EmailRequest findByEmailId(String emailId);

}
