package com.toptal.pushpendra.utilities;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.toptal.pushpendra.apiresults.ApiError;
import com.toptal.pushpendra.apiresults.ApiSubError;
import com.toptal.pushpendra.apiresults.ApiValidationError;
import com.toptal.pushpendra.entities.User;
import com.toptal.pushpendra.models.JwtTokenRequest;
import com.toptal.pushpendra.models.JwtUserDetails;

@Component
public class UserDetailsValidationUtils {

	public ApiError validateUserRegistrationDetails(JwtUserDetails user) {
		ApiError error = null;
		if (user.getFirstName() == null || user.getFirstName().trim().equals(Constants.BLANK_STRING)
				|| user.getLastName() == null || user.getLastName().trim().equals(Constants.BLANK_STRING)
				|| user.getPassword() == null || user.getPassword().trim().equals(Constants.BLANK_STRING)
				|| user.getUsername() == null || user.getUsername().trim().equals(Constants.BLANK_STRING)
				|| user.getEmailId() == null || user.getEmailId().trim().equals(Constants.BLANK_STRING)) {
			error = new ApiError(HttpStatus.UNPROCESSABLE_ENTITY, Constants.MISSING_INFORMATION);
			List<ApiSubError> errors = new ArrayList<>();
			if (user.getPassword() == null || user.getPassword().trim().equals(Constants.BLANK_STRING))
				errors.add(createFieldError(Constants.PASSWORD, user.getPassword()));
			if (user.getUsername() == null || user.getUsername().trim().equals(Constants.BLANK_STRING))
				errors.add(createFieldError(Constants.USER_NAME, user.getPassword()));
			validateUserPersonalDetails(user, errors);
			error.setSubErrors(errors);
		}
		return error;
	}

	private void validateUserPersonalDetails(JwtUserDetails user, List<ApiSubError> errors) {
		if (user.getFirstName() == null || user.getFirstName().trim().equals(Constants.BLANK_STRING))
			errors.add(createFieldError(Constants.FIRST_NAME, user.getFirstName()));
		if (user.getLastName() == null || user.getLastName().trim().equals(Constants.BLANK_STRING))
			errors.add(createFieldError(Constants.LAST_NAME, user.getLastName()));
		if (user.getEmailId() == null || user.getEmailId().trim().equals(Constants.BLANK_STRING))
			errors.add(createFieldError(Constants.EMAIL_ADDRESS, user.getEmailId()));

	}

	private ApiValidationError createFieldError(String field, Object value) {
		return new ApiValidationError(User.class.getName(), field, value, field + Constants.MANDATORY);
	}

	public ApiError validateLoginRequestDetails(JwtTokenRequest request) {
		List<ApiSubError> errors = new ArrayList<>();
		ApiError error = null;
		if (null == request.getUsername() || request.getUsername().trim().equals(Constants.BLANK_STRING))
			errors.add(createFieldError(Constants.USER_NAME, request.getUsername()));
		if (null == request.getPassword() || request.getPassword().trim().equals(Constants.BLANK_STRING))
			errors.add(createFieldError(Constants.PASSWORD, request.getPassword()));
		if (!errors.isEmpty()) {
			error = new ApiError(HttpStatus.UNPROCESSABLE_ENTITY, Constants.MISSING_INFORMATION);
			error.setSubErrors(errors);
		}
		return error;
	}

	public ApiError validateUpdatesForExistingUser(JwtUserDetails user) {
		List<ApiSubError> errors = new ArrayList<>();
		ApiError error = null;
		validateUserPersonalDetails(user, errors);
		if (!errors.isEmpty()) {
			error = new ApiError(HttpStatus.UNPROCESSABLE_ENTITY, Constants.MISSING_INFORMATION);
			error.setSubErrors(errors);
		}
		return error;
	}

}
