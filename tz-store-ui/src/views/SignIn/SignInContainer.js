import React, { useState } from "react";
import SignIn from "./SignIn";
import { authenticateUser } from "../../redux/actions/authActions/authActions";
import { connect } from "react-redux";
import LoginRequest from "./../../dataModels/loginRequest";

const SignInContainer = props => {
  const [loginRequest, setLoginRequest] = useState(new LoginRequest("", ""));
  const [error, setError] = useState({
    message: "",
    status: ""
  });
  const { history, registeredUser, authenticateUser, location } = props;

  const handleSignIn = event => {
    event.preventDefault();
    authenticateUser(loginRequest)
      .then(result => {
        history.push("/users");
        console.log(result);
      })
      .catch(error => {
        console.log(error);
        setError(error.response.data.apierror);
      });
  };

  const handleChange = event => {
    setLoginRequest({
      ...loginRequest,
      [event.target.name]: event.target.value
    });
  };

  return (
    <>
      <SignIn
        handleSignIn={handleSignIn}
        user={loginRequest}
        handleChange={handleChange}
        error={error}
        locationState={location}
      ></SignIn>
    </>
  );
};

const mapStateToProps = state => {
  return {
    registeredUser: state.authReducer.registeredUser
  };
};

const mapDispatchToProps = {
  authenticateUser: authenticateUser
};

export default connect(mapStateToProps, mapDispatchToProps)(SignInContainer);
