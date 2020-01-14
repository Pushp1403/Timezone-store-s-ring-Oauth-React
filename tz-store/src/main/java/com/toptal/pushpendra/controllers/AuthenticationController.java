package com.toptal.pushpendra.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.toptal.pushpendra.apiresults.ApiError;
import com.toptal.pushpendra.config.AppProperties;
import com.toptal.pushpendra.events.AuthenticationEvent;
import com.toptal.pushpendra.events.UserSuccessfullyRegisteredEvent;
import com.toptal.pushpendra.models.JwtTokenRequest;
import com.toptal.pushpendra.models.JwtTokenResponse;
import com.toptal.pushpendra.models.JwtUserDetails;
import com.toptal.pushpendra.security.JwtTokenUtil;
import com.toptal.pushpendra.services.IEmailRequestService;
import com.toptal.pushpendra.services.IUserDetailService;
import com.toptal.pushpendra.utilities.AuthEventType;
import com.toptal.pushpendra.utilities.Constants;
import com.toptal.pushpendra.utilities.UserDetailsValidationUtils;

@RestController
@RequestMapping(value = "/auth")
public class AuthenticationController extends BaseController {

	private final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

	@Autowired
	private IUserDetailService userDetailsService;

	@Autowired
	private UserDetailsValidationUtils validationUtils;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private IEmailRequestService emailRequestService;

	@Autowired
	private JwtTokenUtil tokenUtil;

	@Autowired
	private ApplicationEventPublisher eventPublisher;

	@Autowired
	private AppProperties properties;

	@ResponseBody
	@PostMapping(value = "/authenticate")
	public ResponseEntity<?> authenticateUser(@RequestBody JwtTokenRequest request) {
		// Validate if all the details are present
		ApiError validation = validationUtils.validateLoginRequestDetails(request);
		if (null != validation)
			return buildErrorResponse(validation, validation.getStatus());
		JwtUserDetails user = userDetailsService.loadUserByUsername(request.getUsername());

		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
		} catch (BadCredentialsException e) {
			eventPublisher.publishEvent(new AuthenticationEvent(request, AuthEventType.FAILED));
			throw e;
		}

		eventPublisher.publishEvent(new AuthenticationEvent(request, AuthEventType.SUCCESS));

		final String token = tokenUtil.generateToken(user);
		userDetailsService.saveAuthToken(user.getUsername(), token);
		return buildSuccessResponse(new JwtTokenResponse(token, user));

	}

	@ResponseBody
	@RequestMapping(value = "/registerUser", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> registerNewUser(@RequestBody JwtUserDetails user) {
		// Check if all the details have been provided
		ApiError errors = validationUtils.validateUserRegistrationDetails(user);
		if (null != errors) {
			logger.debug(Constants.MISSING_INFORMATION);
			return buildErrorResponse(errors, errors.getStatus());
		}
		// Ensure this endpoint is used only by un registered users rest every one has
		// to use add new user if has entitlements.

		if (!(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)) {
			logger.debug(Constants.NOT_ALLOWED);
			return buildErrorResponse(new ApiError(HttpStatus.METHOD_NOT_ALLOWED, Constants.NOT_ALLOWED),
					HttpStatus.METHOD_NOT_ALLOWED);
		}
		// Check if user already exists
		try {
			JwtUserDetails details = userDetailsService.loadUserByUsername(user.getUsername());
			if (null != details) {
				logger.debug(Constants.USER_ALREADY_EXISTS);
				return buildErrorResponse(new ApiError(HttpStatus.CONFLICT, Constants.USER_ALREADY_EXISTS),
						HttpStatus.CONFLICT);
			}
		} catch (UsernameNotFoundException e) {
			if (null != userDetailsService.findByEmailId(user.getEmailId()).get().getEmailId()) {
				logger.debug(Constants.USER_ALREADY_EXISTS);
				return buildErrorResponse(new ApiError(HttpStatus.CONFLICT, Constants.USER_ALREADY_EXISTS),
						HttpStatus.CONFLICT);
			}
		}
		JwtUserDetails details = this.userDetailsService.registerNewUser(user);
		eventPublisher.publishEvent(new UserSuccessfullyRegisteredEvent(details));
		return buildSuccessResponse(details);
	}

	@ResponseBody
	@GetMapping(value = "/emailverification")
	public void verifyEmail(@RequestParam String accessToken, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String baseURI = properties.getOauth2().getAuthorizedRedirectUris().get(0);
		String redirectUri = null;
		try {
			emailRequestService.verifyEmail(accessToken);
			redirectUri = UriComponentsBuilder.fromUriString(baseURI)
					.queryParam("success", Constants.VERIFICATION_COMPLETE).build().toUriString();
			logger.info(Constants.VERIFICATION_COMPLETE);
		} catch (Exception e) {
			redirectUri = UriComponentsBuilder.fromUriString(baseURI).queryParam("error", Constants.VERIFICATION_FAILED)
					.build().toUriString();
		}
		response.sendRedirect(redirectUri);
	}

}
