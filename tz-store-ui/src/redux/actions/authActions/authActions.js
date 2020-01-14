import userService from "../../../api/userApi/userService";
import {
  authActions,
  USER_NAME_SESSION_ATTRIBUTE_NAME
} from "../../../utilities/constants";
import authenticationService from "../../../api/authApi/authenticationService";

export function registerNewUserSuccess(registeredUser) {
  return { type: authActions.REGISTER_USER_SUCCESS, registeredUser };
}

export function loggedInuserDetailsLoaded(user) {
  return { type: authActions.LOAD_USER, user };
}

export function authenticateUser(user) {
  return async function(dispatch, getState) {
    return userService
      .authenticateUser(user)
      .then(results => {
        authenticationService.registerSuccessfulLogin(
          user.username,
          results.data.token
        );
        dispatch(loggedInuserDetailsLoaded(results.data.details));
      })
      .catch(error => {
        throw error;
      });
  };
}

export function registerNewUser(user) {
  return async function(dispatch, getState) {
    return userService
      .registerUser(user)
      .then(res => {
        dispatch(registerNewUserSuccess(res.data));
      })
      .catch(error => {
        throw error;
      });
  };
}
export function loadCurrentlyLoggedInUser() {
  return async function(dispatch, getState) {
    return userService
      .loadUserByEmailId(localStorage.getItem(USER_NAME_SESSION_ATTRIBUTE_NAME))
      .then(res => {
        dispatch(loggedInuserDetailsLoaded(res.data));
      })
      .catch(error => {
        throw error;
      });
  };
}
