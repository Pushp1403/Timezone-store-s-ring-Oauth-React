import React from "react";
import { Route, Redirect } from "react-router-dom";
import PropTypes from "prop-types";
import authenticationService from "../../api/authApi/authenticationService";

const RouteWithLayout = props => {
  const { layout: Layout, component: Component, ...rest } = props;

  return (
    <Route
      {...rest}
      render={matchProps => (
        <Layout>
          {authenticationService.getLoggedInUserName() ? (
            <Component {...matchProps} />
          ) : (
            <Redirect to={{ pathname: "/", state: { from: props.location } }} />
          )}
        </Layout>
      )}
    />
  );
};

RouteWithLayout.propTypes = {
  component: PropTypes.any.isRequired,
  layout: PropTypes.any.isRequired,
  path: PropTypes.string
};

export default RouteWithLayout;
