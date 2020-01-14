import React from "react";
import { Switch, Redirect } from "react-router-dom";
import { Route } from "react-router-dom";

import { RouteWithLayout, SecuredRouteWithLayout } from "./components";
import { Main as MainLayout, Minimal as MinimalLayout } from "./layouts";
import {
  UserList as UserListView,
  Account as AccountView,
  SignIn as SignInView,
  SignUp as SignUpView,
  NotFound as NotFoundView,
  TimezoneList as TimezoneListView
} from "./views";

import { OAuth2RedirectHandler } from "./api/authApi";

const Routes = () => {
  return (
    <Switch>
      <Redirect exact from="/" to="/signin" />
      <Route path="/oauth2/redirect" component={OAuth2RedirectHandler}></Route>
      <RouteWithLayout
        component={SignInView}
        exact
        layout={MinimalLayout}
        path="/signin"
      />
      <RouteWithLayout
        component={SignUpView}
        exact
        layout={MinimalLayout}
        path="/signup"
      />
      <SecuredRouteWithLayout
        component={UserListView}
        exact
        layout={MainLayout}
        path="/users"
      />
      <SecuredRouteWithLayout
        component={TimezoneListView}
        exact
        layout={MainLayout}
        path="/timezones"
      />
      <SecuredRouteWithLayout
        component={AccountView}
        exact
        layout={MainLayout}
        path="/account"
      />
      <RouteWithLayout
        component={NotFoundView}
        exact
        layout={MinimalLayout}
        path="/not-found"
      />
      <Redirect to="/not-found" />
    </Switch>
  );
};

export default Routes;
