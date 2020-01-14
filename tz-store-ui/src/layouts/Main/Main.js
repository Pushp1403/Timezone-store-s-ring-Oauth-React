import React, { useState, useEffect } from "react";
import PropTypes from "prop-types";
import clsx from "clsx";
import { makeStyles, useTheme } from "@material-ui/styles";
import { useMediaQuery } from "@material-ui/core";
import { connect } from "react-redux";
import { Sidebar, Topbar } from "./components";
import { loadCurrentlyLoggedInUser } from "../../redux/actions/authActions/authActions";
import { toast } from "react-toastify";
import { withRouter, Redirect } from "react-router-dom";
import authenticationService from "../../api/authApi/authenticationService";

const useStyles = makeStyles(theme => ({
  root: {
    paddingTop: 56,
    height: "100%",
    [theme.breakpoints.up("sm")]: {
      paddingTop: 64
    }
  },
  shiftContent: {
    paddingLeft: 240
  },
  content: {
    height: "100%"
  }
}));

const Main = props => {
  const { children, loggeIdUser, loadCurrentlyLoggedInUser } = props;

  const classes = useStyles();
  const theme = useTheme();
  const isDesktop = useMediaQuery(theme.breakpoints.up("lg"), {
    defaultMatches: true
  });

  const [openSidebar, setOpenSidebar] = useState(false);

  const handleSidebarOpen = () => {
    setOpenSidebar(true);
  };

  const handleSidebarClose = () => {
    setOpenSidebar(false);
  };

  useEffect(() => {
    loadCurrentlyLoggedInUser().catch(error => {
      console.log(error);
      toast(error);
    });
  }, [loadCurrentlyLoggedInUser]);

  const shouldOpenSidebar = isDesktop ? true : openSidebar;

  return (
    <div
      className={clsx({
        [classes.root]: true,
        [classes.shiftContent]: isDesktop
      })}
    >
      {loggeIdUser && loggeIdUser.username ? (
        <>
          {authenticationService.hasAccess(
            props.history.location.pathname,
            loggeIdUser.authorities[0].role
          ) ? (
            <>
              <Topbar onSidebarOpen={handleSidebarOpen} user={loggeIdUser} />
              <Sidebar
                onClose={handleSidebarClose}
                open={shouldOpenSidebar}
                variant={isDesktop ? "persistent" : "temporary"}
                user={loggeIdUser}
              />
              <main className={classes.content}>{children}</main>
            </>
          ) : (
            <>
              {loggeIdUser &&
              loggeIdUser.authorities[0].role === "ROLE_USER_MANAGER" ? (
                <Redirect to="/users"></Redirect>
              ) : (
                <Redirect to="/timezones"></Redirect>
              )}
            </>
          )}
        </>
      ) : (
        <></>
      )}
    </div>
  );
};

Main.propTypes = {
  children: PropTypes.node
};

const mapStateToProps = state => {
  return { loggeIdUser: state.authReducer.loggeIdUser };
};

const mapDispatchToProps = {
  loadCurrentlyLoggedInUser: loadCurrentlyLoggedInUser
};

export default connect(mapStateToProps, mapDispatchToProps)(withRouter(Main));
