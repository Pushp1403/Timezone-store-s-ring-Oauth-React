package com.toptal.pushpendra.eventlisteners;

import java.io.IOException;

import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.toptal.pushpendra.events.EmailInvitaionEvent;
import com.toptal.pushpendra.events.UserSuccessfullyRegisteredEvent;
import com.toptal.pushpendra.services.IEmailRequestService;
import com.toptal.pushpendra.services.MailService;

import freemarker.template.TemplateException;

@Component
public class UserRegistrationSuccessEventListner {
	
	private final Logger logger = LoggerFactory.getLogger(UserRegistrationSuccessEventListner.class);
	
	@Autowired
	private IEmailRequestService emailRequestService;
	
	@Autowired
	private MailService mailService;
	
	@Async
	@EventListener
	public void handleRegistration(UserSuccessfullyRegisteredEvent event) throws Exception {
		emailRequestService.createEmailRequest(event.getDetails());
		try {
			mailService.sendRegistratiionVerificationEmail(event.getDetails());
			logger.info("Email verification request sent");
		} catch (IOException | TemplateException | MessagingException e) {
			throw e;
		}
	}
	
	@Async
	@EventListener
	public void sendEmailInvitation(EmailInvitaionEvent event) {
		try {
			mailService.sendInvitationEmail(event.getEmailId());
			logger.info("Email Invitation request sent");
		} catch (IOException | TemplateException | MessagingException e) {
			logger.error(e.getMessage());
		}
	}

}
