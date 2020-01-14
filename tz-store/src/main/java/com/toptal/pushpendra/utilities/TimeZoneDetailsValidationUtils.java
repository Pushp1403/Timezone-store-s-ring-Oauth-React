package com.toptal.pushpendra.utilities;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.toptal.pushpendra.apiresults.ApiError;
import com.toptal.pushpendra.apiresults.ApiSubError;
import com.toptal.pushpendra.apiresults.ApiValidationError;
import com.toptal.pushpendra.entities.TimeZone;
import com.toptal.pushpendra.models.TimeZoneBO;

@Component
public class TimeZoneDetailsValidationUtils {
	
	public ApiError validateTimeZoneDetails(TimeZoneBO timeZoneBO) {
		ApiError error = null;
		List<ApiSubError> errors = new ArrayList<>();
		if(null == timeZoneBO.getCity() || timeZoneBO.getCity().trim().equals(Constants.BLANK_STRING)) 
			errors.add(createFieldError(Constants.TIMEZONE_CITY, timeZoneBO.getCity()));
		
		if(null == timeZoneBO.getName() || timeZoneBO.getName().trim().equals(Constants.BLANK_STRING)) 
			errors.add(createFieldError(Constants.TIMEZONE_NAME, timeZoneBO.getName()));
			
		if(null == timeZoneBO.getDifferenceFromGMT()) 
			errors.add(createFieldError(Constants.TIMEZONE_DIFF, timeZoneBO.getDifferenceFromGMT()));
		
		if(!errors.isEmpty()) {
			error = new ApiError(HttpStatus.UNPROCESSABLE_ENTITY, Constants.MISSING_INFORMATION);
			error.setSubErrors(errors);
		}
			
		return error;
		
	}
	
	private ApiValidationError createFieldError(String field, Object value) {
		return new ApiValidationError(TimeZone.class.getName(), field, value, field+Constants.MANDATORY);
	}

}
