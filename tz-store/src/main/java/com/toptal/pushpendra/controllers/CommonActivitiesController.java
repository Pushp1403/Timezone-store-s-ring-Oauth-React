package com.toptal.pushpendra.controllers;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.toptal.pushpendra.apiresults.ApiError;
import com.toptal.pushpendra.apiresults.ApiSuccessResponse;
import com.toptal.pushpendra.models.JwtUserDetails;
import com.toptal.pushpendra.security.JwtTokenUtil;
import com.toptal.pushpendra.services.FileUploadService;
import com.toptal.pushpendra.services.IUserDetailService;
import com.toptal.pushpendra.utilities.Constants;

import io.jsonwebtoken.ExpiredJwtException;

@Controller
@RequestMapping(value = "/api/common/**")
public class CommonActivitiesController extends BaseController {

	private final Logger logger = LoggerFactory.getLogger(CommonActivitiesController.class);

	@Autowired
	private FileUploadService fileUploadService;

	@Value("${jwt.http.request.header}")
	private String tokenHeader;

	@Autowired
	private JwtTokenUtil tokenUtil;

	@Autowired
	private IUserDetailService userDetailsService;

	@PostMapping("/uploadImage")
	public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
		logger.info("uploading profile picture: " + file.getName());
		String updateURI = fileUploadService.storeFile(file);
		return buildSuccessResponse(new ApiSuccessResponse(updateURI));
	}

	@PostMapping("/logout")
	public ResponseEntity<?> logout(HttpServletRequest request) {
		String username = null;
		final String requestTokenHeader = request.getHeader(tokenHeader);
		String jwtToken = requestTokenHeader.substring(7);
		if (null == jwtToken || jwtToken.equals(Constants.BLANK_STRING))
			return buildErrorResponse(new ApiError(HttpStatus.BAD_REQUEST, Constants.JWT_TOKEN_EXPIRED),
					HttpStatus.BAD_REQUEST);
		try {
			username = tokenUtil.getUsernameFromToken(jwtToken);
		} catch (IllegalArgumentException e) {
			return buildErrorResponse(new ApiError(HttpStatus.BAD_REQUEST, Constants.INACTIVE_USER_SESSION),
					HttpStatus.BAD_REQUEST);
		} catch (ExpiredJwtException e) {
			return buildErrorResponse(new ApiError(HttpStatus.BAD_REQUEST, Constants.JWT_TOKEN_EXPIRED),
					HttpStatus.BAD_REQUEST);
		}
		userDetailsService.deleteToken(username, jwtToken);
		logger.info("Logout Completed for user " + username);
		return buildSuccessResponse(new ApiSuccessResponse(Constants.LOGOUT_DONE));
	}

	@ResponseBody
	@GetMapping(value = "/loadUserDetails")
	public ResponseEntity<?> loadUserDetails(@RequestParam String username) {
		JwtUserDetails authUser = (JwtUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		if (!authUser.getUsername().equals(username))
			return buildErrorResponse(new ApiError(HttpStatus.FORBIDDEN), HttpStatus.FORBIDDEN);
		JwtUserDetails user = userDetailsService.loadUserByUsername(username);
		logger.info("Users loaded successfully");
		return buildSuccessResponse(user);
	}

}
