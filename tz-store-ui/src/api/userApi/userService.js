import API from "../apiConfig/baseService";
import * as constants from "./../../utilities/constants";

class UserService {
  registerUser(userRegistrationRequest) {
    return API.post(constants.REGISTER_USER, userRegistrationRequest);
  }

  authenticateUser(user) {
    return API.post(constants.AUTHENTICATE, user);
  }

  createUser(user) {
    return API.post(constants.CREATE_NEW_USER, user);
  }

  updateUser(user) {
    return API.put(constants.UPDATE_USER + "/" + user.username, user);
  }

  loadUserByEmailId(emailId) {
    return API.get(constants.LOAD_LOGGED_IN_USER, {
      params: {
        username: emailId
      }
    });
  }

  retrieveAllUsers(userFilter) {
    return API.get(constants.RETRIEVE_USERS, { params: { ...userFilter } });
  }

  deleteUser(username) {
    return API.delete(constants.DELETE_USER + "/" + username);
  }

  inviteUser(emailId) {
    return API.post(constants.SEND_INVITATION + "/" + emailId);
  }

  uploadPicture(file) {
    const data = new FormData();
    data.append("file", file);
    return API.post(constants.UPLOAD_IMAGE, data);
  }

  logout() {
    return API.post(constants.LOGOUT);
  }
}

export default new UserService();
