import React from "react";
import PeopleIcon from "@material-ui/icons/People";
import AccountBoxIcon from "@material-ui/icons/AccountBox";
import Watch from "@material-ui/icons/Watch";

const USER_API_BASE_PATH = "/api/user/";
const TIMEZONE_API_BASE_PATH = "/api/timeZone/";
const AUTH_API_BASE_PATH = "/auth/";
const EMAIL_INVITATION_API_BASE_PATH = "/api/invite/";
const COMMON_ACTIVITIES_API_BASE_PATH = "/api/common/";

//Actions
export const authActions = {
  REGISTER_USER_SUCCESS: "REGISTER_USER_SUCCESS",
  LOAD_USER: "LOAD_USER"
};

export const timezoneActions = {
  TIMEZONES_LOAD_SUCCESS: "TIMEZONES_LOAD_SUCCESS",
  TIMEZONE_CREATED_SUCCESS: "TIMEZONE_CREATED_SUCCESS",
  TIMEZONE_DELETED_SUCCESS: "TIMEZONE_DELETED_SUCCESS",
  TIMEZONE_UPDATED_SUCCESS: "TIMEZONE_UPDATED_SUCCESS"
};

export const userActions = {
  USER_PROFILE_PICTURE_UPLOAD_SUCCESS: "USER_PROFILE_PICTURE_UPLOAD_SUCCESS",
  USERS_LOADED_SUCCESSFULLY: "USERS_LOADED_SUCCESSFULLY",
  USER_UPDATED_SUCCESSFULLY: "USER_UPDATED_SUCCESSFULLY",
  USER_CREATED_SUCCESSFULLY: "USER_CREATED_SUCCESSFULLY",
  USER_DELETED_SUCCESSFULLY: "USER_DELETED_SUCCESSFULLY",
  USER_LOGGED_OUT_SUCCESSFULLY: "USER_LOGGED_OUT_SUCCESSFULLY",
  LOGGED_IN_USER_UPDATED: "LOGGED_IN_USER_UPDATED"
};

//Misc
export const USER_NAME_SESSION_ATTRIBUTE_NAME = "authenticatedUser";
export const JWT_TOKEN = "TOKEN";

//Auth API End points
export const AUTHENTICATE = AUTH_API_BASE_PATH + "authenticate";
export const REGISTER_USER = AUTH_API_BASE_PATH + "registerUser";
export const EMAIL_VERIFICATION = AUTH_API_BASE_PATH + "emailverification";

//Common activities API End points
export const LOAD_LOGGED_IN_USER =
  COMMON_ACTIVITIES_API_BASE_PATH + "loadUserDetails";
export const UPLOAD_IMAGE = COMMON_ACTIVITIES_API_BASE_PATH + "uploadImage";
export const LOGOUT = COMMON_ACTIVITIES_API_BASE_PATH + "logout";

//User API End points
export const UPDATE_USER = USER_API_BASE_PATH + "updateUser";
export const DELETE_USER = USER_API_BASE_PATH + "deleteUser";
export const RETRIEVE_USERS = USER_API_BASE_PATH + "getUsers";
export const CREATE_NEW_USER = USER_API_BASE_PATH + "addNewUser";

//Timezone API End points
export const CREATE_TIMEZONE = TIMEZONE_API_BASE_PATH + "createNewTimeZone";
export const UPDATE_TIMEZONE = TIMEZONE_API_BASE_PATH + "updateTimeZone";
export const DELETE_TIMEZONE = TIMEZONE_API_BASE_PATH + "deleteTimeZone";
export const RETRIEVE_TIMEZONES = TIMEZONE_API_BASE_PATH + "retrievetimezones";

//Email Invitation API End points
export const SEND_INVITATION =
  EMAIL_INVITATION_API_BASE_PATH + "emailInvitation";

export const API_BASE_URL = "http://localhost:8080";
export const OAUTH2_REDIRECT_URI = "http://localhost:3000/oauth2/redirect";

export const GOOGLE_AUTH_URL =
  API_BASE_URL + "/oauth2/authorize/google?redirect_uri=" + OAUTH2_REDIRECT_URI;
export const FACEBOOK_AUTH_URL =
  API_BASE_URL +
  "/oauth2/authorize/facebook?redirect_uri=" +
  OAUTH2_REDIRECT_URI;
export const GITHUB_AUTH_URL =
  API_BASE_URL + "/oauth2/authorize/github?redirect_uri=" + OAUTH2_REDIRECT_URI;

//Roles
export const ROLE_USER = "ROLE_USER";

//Pages

export const pages = [
  {
    title: "TimeZones",
    href: "/timezones",
    icon: <Watch />,
    role: ["ROLE_USER", "ROLE_ADMIN"]
  },
  {
    title: "Users",
    href: "/users",
    icon: <PeopleIcon />,
    role: ["ROLE_ADMIN", "ROLE_USER_MANAGER"]
  },
  {
    title: "Account",
    href: "/account",
    icon: <AccountBoxIcon />,
    role: ["ROLE_ADMIN", "ROLE_USER_MANAGER", "ROLE_USER"]
  }
];
