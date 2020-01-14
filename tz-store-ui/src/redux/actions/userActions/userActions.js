import { userActions } from "../../../utilities/constants";
import userService from "../../../api/userApi/userService";
import authenticationService from "../../../api/authApi/authenticationService";

export function imageUploadedSuccessFully(url) {
  return { type: userActions.USER_PROFILE_PICTURE_UPLOAD_SUCCESS, url };
}

export function usersLoadedSuccessfully(users) {
  return { type: userActions.USERS_LOADED_SUCCESSFULLY, users };
}

export function userDeletedSuccessfully(userId) {
  return { type: userActions.USER_DELETED_SUCCESSFULLY, userId };
}

export function userAddedSucesFully(user) {
  return { type: userActions.USER_CREATED_SUCCESSFULLY, user };
}

export function userloggedOutSuccessfully() {
  return { type: userActions.USER_LOGGED_OUT_SUCCESSFULLY, loggedInUser: {} };
}

export function userDetailsUpdatedSuccessfully(user) {
  return { type: userActions.USER_UPDATED_SUCCESSFULLY, user };
}

export function loggedInUserUpdated(user) {
  return { type: userActions.LOGGED_IN_USER_UPDATED, user };
}

export function uploadPicture(file) {
  return async function(dispatch, getState) {
    return userService
      .uploadPicture(file)
      .then(res => {
        console.log(res);
        dispatch(
          imageUploadedSuccessFully(res.data.apisuccessresponse.message)
        );
      })
      .catch(error => {
        throw error;
      });
  };
}

export function retrieveAllUsers(userFilters) {
  return async function(dispatch, getState) {
    return userService
      .retrieveAllUsers(userFilters)
      .then(res => {
        console.log(res);
        dispatch(usersLoadedSuccessfully(res.data));
      })
      .catch(error => {
        throw error;
      });
  };
}

export function deleteUser(userId) {
  return async function(dispatch, getState) {
    return userService
      .deleteUser(userId)
      .then(res => {
        dispatch(userDeletedSuccessfully(userId));
      })
      .catch(error => {
        throw error;
      });
  };
}

export function addNewUser(user) {
  return async function(dispatch, getState) {
    return userService
      .createUser(user)
      .then(res => {
        dispatch(userAddedSucesFully(res.data));
      })
      .catch(error => {
        throw error;
      });
  };
}

export function updateUser(user) {
  return async function(dispatch) {
    return userService
      .updateUser(user)
      .then(res => {
        dispatch(userDetailsUpdatedSuccessfully(res.data));
        if (authenticationService.getLoggedInUserName() === user.username) {
          dispatch(loggedInUserUpdated(res.data));
        }
      })
      .catch(err => {
        throw err;
      });
  };
}

export function logout() {
  return async function(dispatch) {
    return userService
      .logout()
      .then(() => {
        dispatch(userloggedOutSuccessfully());
      })
      .catch(error => {
        throw error;
      });
  };
}

export function inviteUser(emailId) {
  return userService.inviteUser(emailId);
}
