import React, { useState } from "react";
import SignUp from "./SignUp";
import UserRegistrationRequest from "../../dataModels/userRegistrationRequest";
import { connect } from "react-redux";
import { registerNewUser } from "./../../redux/actions/authActions/authActions";
import { ROLE_USER } from "../../utilities/constants";
import { toast } from "react-toastify";

const SignupContainer = props => {
  const { history, registerNewUser } = props;
  const [userRegistrationRequest, setUserRegistrationRequest] = useState(
    new UserRegistrationRequest()
  );

  const handleChange = event => {
    setUserRegistrationRequest({
      ...userRegistrationRequest,
      [event.target.name]: event.target.value
    });
  };

  const handleSignup = event => {
    event.preventDefault();
    if (
      userRegistrationRequest.password !==
      userRegistrationRequest.confirmPassword
    ) {
      toast("Password Doesn't match");
      return;
    }
    userRegistrationRequest.authorities[0].role =
      userRegistrationRequest.authority;
    userRegistrationRequest.authorities[0].username =
      userRegistrationRequest.username;
    userRegistrationRequest.provider = "local";
    registerNewUser(userRegistrationRequest)
      .then(res => {
        history.push("/");
        toast(
          "User registration successfull. Please verify you email id to continue."
        );
      })
      .catch(error => {
        if (error && error.response)
          toast(error.response.data.apierror.message);
      });
  };

  return (
    <SignUp
      handleChange={handleChange}
      handleSignup={handleSignup}
      user={userRegistrationRequest}
    ></SignUp>
  );
};

const mapStateToProps = state => {
  return {};
};

const mapDispatchToProp = {
  registerNewUser: registerNewUser
};

export default connect(mapStateToProps, mapDispatchToProp)(SignupContainer);
