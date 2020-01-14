package com.toptal.pushpendra.controllers;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.toptal.pushpendra.apiresults.ApiError;
import com.toptal.pushpendra.apiresults.ApiSuccessResponse;
import com.toptal.pushpendra.models.TimeZoneBO;
import com.toptal.pushpendra.services.ITimeZoneService;
import com.toptal.pushpendra.utilities.Constants;
import com.toptal.pushpendra.utilities.TimeZoneDetailsValidationUtils;

@RestController
@RequestMapping(value="/api/timeZone/**")
@Secured(value = {Constants.ROLE_USER, Constants.ROLE_ADMIN})
public class UserTimeZoneController extends BaseController{
	
	private final Logger logger = LoggerFactory.getLogger(UserTimeZoneController.class);
	
	@Autowired
	private ITimeZoneService timeZoneService;
	
	@Autowired
	private TimeZoneDetailsValidationUtils validationUtils;
	
	@ResponseBody
	@RequestMapping(value = "/createNewTimeZone", method = RequestMethod.POST)
	public ResponseEntity<?> createNewTimeZone(@RequestBody TimeZoneBO tzEntry){
		
		//validate the supplied timezone details
		ApiError validation = validationUtils.validateTimeZoneDetails(tzEntry);
		if(null != validation) {
			return buildErrorResponse(validation, validation.getStatus());
		}		
		tzEntry = timeZoneService.createNewTimeZone(tzEntry);
		logger.info("Timezone Created successfully");
		return buildSuccessResponse(tzEntry);
	}
	
	@ResponseBody
	@RequestMapping(value = "/updateTimeZone", method = RequestMethod.PUT)
	public ResponseEntity<?> updateTimeZone(@RequestBody TimeZoneBO tzEntry){
		if(null == tzEntry.getTimeZoneId())
			return buildErrorResponse(Constants.TIMEZONE_DOES_NOT_EXIST, HttpStatus.NOT_FOUND);
		//validate the supplied timezone information
		ApiError validation = validationUtils.validateTimeZoneDetails(tzEntry);
		if(null != validation) {
			return buildErrorResponse(validation, validation.getStatus());
		}
		tzEntry = timeZoneService.updateTimeZoneInfo(tzEntry);
		logger.info("Timezone updated successfully");
		return buildSuccessResponse(tzEntry);
	}
	
	@ResponseBody
	@RequestMapping(value="/deleteTimeZone/{timeZoneId}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteTimeZone(@PathVariable Integer timeZoneId){
		timeZoneService.deleteTimeZone(timeZoneId);
		logger.info("Timezone Deleted successfully");
		return buildSuccessResponse(new ApiSuccessResponse(Constants.TIMEZONE_DELETED));
	}
	
	@ResponseBody
	@GetMapping(value = "/retrievetimezones")
	public ResponseEntity<?> retrieveAllTimeZones(@RequestParam Map<String, String> requestParams){
		List<TimeZoneBO> timeZones = timeZoneService.getAllTimeZones(requestParams);
		logger.info("Timezones loaded successfully");
		return buildSuccessResponse(timeZones);
	}
	

}
