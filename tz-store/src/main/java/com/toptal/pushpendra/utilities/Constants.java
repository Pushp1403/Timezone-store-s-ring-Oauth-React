package com.toptal.pushpendra.utilities;

public class Constants {

	public static final String INTERNAL_SERVER_ERROR = "Internal Server Error";
	public static final String BLANK_STRING = "";
	public static final String COMMA = ", ";
	public static final String ROLE_USER_MANAGER = "ROLE_USER_MANAGER";
	public static final String ROLE_ADMIN = "ROLE_ADMIN";
	public static final String ROLE_USER = "ROLE_USER";
	public static final String USER_DISABLED = "User Disabled";
	public static final String INVALID_CREDENTIALS = "Invalid Credentials";
	public static final String NEED_JWT_TOKEN = "You would need to provide JWT token to access this resource";
	public static final String JWT_TOKEN_BEARER = "Bearer ";
	public static final String USER_ALREADY_EXISTS = "User Already Exists with given username/emailId";
	public static final String JWT_TOKEN_UNABLE_TO_GET_USERNAME = "JWT_TOKEN_UNABLE_TO_GET_USERNAME";
	public static final String JWT_TOKEN_EXPIRED = "JWT_TOKEN_EXPIRED";
	public static final String JWT_TOKEN_DOES_NOT_START_WITH_BEARER_STRING = "JWT_TOKEN_DOES_NOT_START_WITH_BEARER_STRING";
	public static final String USER_NOT_EXIST = "User Does not exist. Please Register User first";

	public static final String MISSING_INFORMATION = "Missing required information";
	public static final String ADDRESS = "Address";
	public static final String DESCRIPTION = "Description";
	public static final String FLOOR_AREA = "Floor Area";
	public static final String LATTITUDE = "Lattitude";
	public static final String LONGITUDE = "Longitude";
	public static final String NAME = "Name";
	public static final String NUMBER_OF_ROOMS = "Number of Rooms";
	public static final String PRICE = "Price";

	public static final String FIRST_NAME = "First Name";
	public static final String LAST_NAME = "Last Name";
	public static final String USER_NAME = "User Name";
	public static final String PASSWORD = "Password";
	public static final String EMAIL_ADDRESS = "Email Address";

	// TimeZone Error Messages
	public static final String TIMEZONE_CITY = "Timezone For City";
	public static final String TIMEZONE_NAME = "Name For Timezone";
	public static final String TIMEZONE_DIFF = "Diference to GMT";
	public static final String TIMEZONE_DOES_NOT_EXIST = "TimeZone Doesn't Exist";
	public static final String EMAIL_VERIFICATION_PENDING = "Email Verification pending.Please verify your email";

	public static final String ACCOUNT_LOCKED = "Account locked";
	public static final String MANDATORY = " is mandatory";
	public static final String SYSTEM_USER = "SYSTEM";
	public static final String TEMPLATE_PATH = "/mailtemplates/";
	public static final String SINGLE_SPACE = " ";

	public static final String BASE_URL = "http://localhost:8080";
	public static final String SIGNUP_URL = "http://localhost:3000/signup";
	public static final String VERIFICATION_END_POINT = "/auth/emailverification";
	public static final String QUESTION_MARK = "?";
	public static final String ACCESS_TOKEN = "accessToken";
	public static final String EQUAL_SIGN = "=";
	public static final String INVITATION_END_POINT = "/api/invite/emailInvitation";

	public static final String VERIFICATION_SUBJECT = "Please verify your account";
	public static final String INVITATION_SUBJECT = "Hurray !! Invitation to join TzStore";
	public static final String INVITATION_SENT = "Invitation sent successfully";
	public static final String VERIFICATION_COMPLETE = "Email Verification completed. Please proceed to login.";
	public static final String USER_DELETED = "User deleted successfully";
	public static final String TIMEZONE_DELETED = "Timezone entry deleted successfully";
	public static final String REGISTRATION_SUCCESSFUL = "User registered successfully. Please verify your email to proceed";
	public static final Object VERIFICATION_FAILED = "Email Verification failed. Retry.";
	public static final String LOGOUT_DONE = "You are logged out";
	public static final String INACTIVE_USER_SESSION = "No Active user session";
	public static final String USER_ALREADY_LOGGED_OUT = "User has been logged out. Please login again to continue";
	public static final String NOT_ALLOWED = "Not allowed to register a user";
	public static final String CAN_NOT_UPDATE_PASSWORD = "You are not allowed to change username/password for existing user";

}
