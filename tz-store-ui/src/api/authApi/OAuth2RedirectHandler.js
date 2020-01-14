import React, { Component } from "react";
import { Redirect } from "react-router-dom";
import authenticationService from "../../api/authApi/authenticationService";

class OAuth2RedirectHandler extends Component {
  getUrlParameter(name) {
    name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
    var regex = new RegExp("[\\?&]" + name + "=([^&#]*)");

    var results = regex.exec(this.props.location.search);
    return results === null
      ? ""
      : decodeURIComponent(results[1].replace(/\+/g, " "));
  }

  render() {
    const token = this.getUrlParameter("token");
    const username = this.getUrlParameter("username");
    const error = this.getUrlParameter("error");
    const success = this.getUrlParameter("success");

    if (token) {
      authenticationService.registerSuccessfulLogin(username, token);
      return (
        <Redirect
          to={{
            pathname: "/timezones",
            state: { from: this.props.location }
          }}
        />
      );
    } else {
      return (
        <Redirect
          to={{
            pathname: "/signin",
            state: {
              from: this.props.location,
              errorMessage: error,
              successMessage: success
            }
          }}
        />
      );
    }
  }
}

export default OAuth2RedirectHandler;
