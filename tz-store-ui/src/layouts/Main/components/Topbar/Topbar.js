import React from "react";
import { Link as RouterLink, withRouter } from "react-router-dom";
import clsx from "clsx";
import PropTypes from "prop-types";
import { makeStyles } from "@material-ui/styles";
import {
  AppBar,
  Toolbar,
  Hidden,
  IconButton,
  Typography
} from "@material-ui/core";
import MenuIcon from "@material-ui/icons/Menu";
import InputIcon from "@material-ui/icons/Input";
import { toast } from "react-toastify";
import authenticationService from "../../../../api/authApi/authenticationService";
import { logout } from "../../../../redux/actions/userActions/userActions";
import { connect } from "react-redux";
const useStyles = makeStyles(theme => ({
  root: {
    boxShadow: "none"
  },
  title: {
    flexGrow: 1,
    color: "white"
  },
  flexGrow: {
    flexGrow: 1
  },
  signOutButton: {
    marginLeft: theme.spacing(1)
  },
  menuButton: {
    marginRight: 10
  }
}));

const Topbar = props => {
  const { className, onSidebarOpen, logout, history, ...rest } = props;

  const classes = useStyles();

  const performLogout = event => {
    logout()
      .then(res => {
        authenticationService.logout();
        history.push("/");
        toast("Logged out successfully");
      })
      .catch(error => {
        console.log(error);
        toast("error");
      });
  };

  return (
    <AppBar {...rest} className={clsx(classes.root, className)}>
      <Toolbar>
        <RouterLink to="/">
          <img
            alt="Logo"
            src="/images/logos/logo.png"
            width={40}
            height={50}
            className={classes.menuButton}
          />
        </RouterLink>
        <Typography variant="h3" className={classes.title}>
          TZ-Store
        </Typography>
        <div className={classes.flexGrow} />
        <Hidden mdDown>
          <IconButton
            className={classes.signOutButton}
            color="inherit"
            onClick={performLogout}
          >
            <InputIcon />
          </IconButton>
        </Hidden>
        <Hidden lgUp>
          <IconButton color="inherit" onClick={onSidebarOpen}>
            <MenuIcon />
          </IconButton>
        </Hidden>
      </Toolbar>
    </AppBar>
  );
};

Topbar.propTypes = {
  className: PropTypes.string,
  onSidebarOpen: PropTypes.func
};

const mapDispatchToProps = {
  logout: logout
};

const mapStateToProps = state => {
  return {};
};

export default connect(mapStateToProps, mapDispatchToProps)(withRouter(Topbar));
