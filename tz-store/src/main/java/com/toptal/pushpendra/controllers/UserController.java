package com.toptal.pushpendra.controllers;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.toptal.pushpendra.apiresults.ApiError;
import com.toptal.pushpendra.apiresults.ApiSuccessResponse;
import com.toptal.pushpendra.events.UserSuccessfullyRegisteredEvent;
import com.toptal.pushpendra.models.JwtUserDetails;
import com.toptal.pushpendra.services.IUserDetailService;
import com.toptal.pushpendra.utilities.Constants;
import com.toptal.pushpendra.utilities.UserDetailsValidationUtils;

@RestController
@RequestMapping(value = "/api/user/**")
@Secured(value = { Constants.ROLE_USER_MANAGER, Constants.ROLE_ADMIN })
public class UserController extends BaseController {
	
	private final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserDetailsValidationUtils validationUtils;

	@Autowired
	private IUserDetailService userDetailService;

	@Autowired
	private ApplicationEventPublisher eventPublisher;

	@ResponseBody
	@RequestMapping(value = "/updateUser/{username}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateUserDetails(@PathVariable String username, @RequestBody JwtUserDetails user) {
		// Check if all the details have been provided
		JwtUserDetails existingUser = userDetailService.loadUserByUsername(username);
		ApiError errors = null;
		if (null == existingUser)
			return buildErrorResponse(new ApiError(HttpStatus.NOT_FOUND, Constants.USER_NOT_EXIST),
					HttpStatus.NOT_FOUND);
		else
			errors = validationUtils.validateUpdatesForExistingUser(user);
		if (null != errors)
			return buildErrorResponse(errors, errors.getStatus());
		user.setUsername(existingUser.getUsername());
		JwtUserDetails updatedUser = userDetailService.updateUserDetails(user);
		logger.info("User details updated succesully");
		return buildSuccessResponse(updatedUser);
	}

	@ResponseBody
	@RequestMapping(value = "/deleteUser/{username}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteUser(@PathVariable String username) {
		userDetailService.deleteUser(username);
		logger.info("Deleted successfully "+username);
		return buildSuccessResponse(new ApiSuccessResponse(Constants.USER_DELETED));
	}

	@ResponseBody
	@RequestMapping(value = "/getUsers", method = RequestMethod.GET)
	public ResponseEntity<?> rerieveUsers(@RequestParam Map<String, String> requestParams) {
		List<JwtUserDetails> users = userDetailService.getFilteredResults(requestParams);
		return buildSuccessResponse(users);
	}

	@ResponseBody
	@PostMapping(value = "/addNewUser")
	public ResponseEntity<?> addNewUser(@RequestBody JwtUserDetails user) {
		ApiError errors = validationUtils.validateUserRegistrationDetails(user);
		if (null != errors)
			return buildErrorResponse(errors, errors.getStatus());

		// Check if user already exists
		try {
			JwtUserDetails details = userDetailService.loadUserByUsername(user.getUsername());
			if (null != details)
				return buildErrorResponse(new ApiError(HttpStatus.CONFLICT, Constants.USER_ALREADY_EXISTS),
						HttpStatus.CONFLICT);
		} catch (UsernameNotFoundException e) {
			if (null != userDetailService.findByEmailId(user.getEmailId()).get().getEmailId())
				return buildErrorResponse(new ApiError(HttpStatus.CONFLICT, Constants.USER_ALREADY_EXISTS),
						HttpStatus.CONFLICT);
		}
		JwtUserDetails details = this.userDetailService.registerNewUser(user);
		eventPublisher.publishEvent(new UserSuccessfullyRegisteredEvent(details));
		logger.info("user added successfully");
		return buildSuccessResponse(details);
	}

}
