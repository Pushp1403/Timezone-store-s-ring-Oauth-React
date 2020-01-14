import API from "./../apiConfig";
import {
  AUTHENTICATE,
  USER_NAME_SESSION_ATTRIBUTE_NAME,
  JWT_TOKEN,
  pages
} from "./../../utilities/constants";

const service = (function authService() {
  let instance;
  const AuthenticationService = {
    executeJwtAuthenticationService: function(user) {
      if (localStorage.getItem(JWT_TOKEN)) {
        localStorage.removeItem(JWT_TOKEN);
        AuthenticationService.removeAxiosHeaders();
      }
      return API.post(`${AUTHENTICATE}`, {
        username: user.username,
        password: user.password
      });
    },

    registerSuccessfulLogin: function(username, token) {
      localStorage.setItem(USER_NAME_SESSION_ATTRIBUTE_NAME, username);
      localStorage.setItem(JWT_TOKEN, token);
      this.setupAxiosInterceptors();
    },

    createJWTToken: function() {
      return "Bearer " + localStorage.getItem(JWT_TOKEN);
    },

    logout: function() {
      localStorage.removeItem(USER_NAME_SESSION_ATTRIBUTE_NAME);
      localStorage.removeItem(JWT_TOKEN);
      this.removeAxiosHeaders();
    },

    isUserLoggedIn: function() {
      let user = localStorage.getItem(USER_NAME_SESSION_ATTRIBUTE_NAME);
      if (user === null) return false;
      return true;
    },

    getLoggedInUserName: function() {
      let user = localStorage.getItem(USER_NAME_SESSION_ATTRIBUTE_NAME);
      if (user === null) return "";
      return user;
    },

    setupAxiosInterceptors: function() {
      API.interceptors.request.use(config => {
        if (this.isUserLoggedIn()) {
          config.headers.authorization = this.createJWTToken();
        }
        return config;
      });
    },

    removeAxiosHeaders: function() {
      API.interceptors.request.use(config => {
        delete config.headers.authorization;
        return config;
      });
    },

    hasAccess: function(path, role) {
      let allowedPages = pages.filter(page => page.href === path);
      return allowedPages[0].role.indexOf(role) >= 0;
    }
  };

  function getInstance() {
    if (instance) {
      return instance;
    } else {
      instance = AuthenticationService;
      return instance;
    }
  }

  return getInstance;
})();

export default service();
