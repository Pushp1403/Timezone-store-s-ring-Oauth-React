package com.toptal.pushpendra.controllers;

import java.io.IOException;

import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.toptal.pushpendra.apiresults.ApiError;
import com.toptal.pushpendra.apiresults.ApiSuccessResponse;
import com.toptal.pushpendra.events.EmailInvitaionEvent;
import com.toptal.pushpendra.exceptions.UserAlreadyExistException;
import com.toptal.pushpendra.services.IEmailRequestService;
import com.toptal.pushpendra.utilities.Constants;

import freemarker.core.ParseException;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;

@Controller
@RequestMapping(value = "/api/invite/**")
public class EmailInvitationController extends BaseController {
	
	private final Logger logger = LoggerFactory.getLogger(EmailInvitationController.class);

	@Autowired
	private IEmailRequestService emailRequestService;
	
	@Autowired
	private ApplicationEventPublisher publisher;

	@ResponseBody
	@PostMapping(value = "/emailInvitation/{email}")
	@Secured(Constants.ROLE_ADMIN)
	public ResponseEntity<?> createEmailInvitation(@PathVariable String email) throws TemplateNotFoundException,
			MalformedTemplateNameException, ParseException, IOException, TemplateException, MessagingException {
		try {
			emailRequestService.createEmailInvitationRequestyEmail(email);
			logger.info("Email request created");
		} catch (UserAlreadyExistException e) {
			return buildErrorResponse(new ApiError(HttpStatus.CONFLICT, Constants.USER_ALREADY_EXISTS),
					HttpStatus.CONFLICT);
		}
		publisher.publishEvent(new EmailInvitaionEvent(email));
		return buildSuccessResponse(new ApiSuccessResponse(Constants.INVITATION_SENT));
	}

}
