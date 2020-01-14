import React, { useState } from "react";
import { Link as RouterLink, withRouter } from "react-router-dom";
import PropTypes from "prop-types";
import { makeStyles } from "@material-ui/styles";
import { Grid, Button, TextField, Link, Typography } from "@material-ui/core";
import MessagePannel from "../../utilities/MessagePannel";
import SocialButtons from "./../SocialButtons";

const useStyles = makeStyles(theme => ({
  root: {
    backgroundColor: theme.palette.background.default,
    height: "100%"
  },
  grid: {
    height: "100%"
  },
  quoteContainer: {
    [theme.breakpoints.down("md")]: {
      display: "none"
    }
  },
  quote: {
    backgroundColor: theme.palette.neutral,
    height: "100%",
    display: "flex",
    justifyContent: "center",
    alignItems: "center",
    backgroundImage: "url(/images/auth.jpg)",
    backgroundSize: "cover",
    backgroundRepeat: "no-repeat",
    backgroundPosition: "center"
  },
  quoteInner: {
    textAlign: "center",
    flexBasis: "600px"
  },
  quoteText: {
    color: theme.palette.white,
    fontWeight: 300
  },
  name: {
    marginTop: theme.spacing(3),
    color: theme.palette.white
  },
  bio: {
    color: theme.palette.white
  },
  contentContainer: {},
  content: {
    height: "100%",
    display: "flex"
  },
  contentHeader: {
    display: "flex",
    alignItems: "center",
    paddingTop: theme.spacing(5),
    paddingBototm: theme.spacing(2),
    paddingLeft: theme.spacing(2),
    paddingRight: theme.spacing(2)
  },
  logoImage: {
    marginLeft: theme.spacing(4)
  },
  contentBody: {
    flexGrow: 1,
    display: "flex",
    alignItems: "center",
    [theme.breakpoints.down("md")]: {
      justifyContent: "center"
    }
  },
  form: {
    paddingLeft: 100,
    paddingRight: 100,
    paddingBottom: 125,
    flexBasis: 700,
    [theme.breakpoints.down("sm")]: {
      paddingLeft: theme.spacing(2),
      paddingRight: theme.spacing(2)
    }
  },
  title: {
    marginTop: theme.spacing(3)
  },
  socialButtons: {
    marginTop: theme.spacing(3)
  },
  socialIcon: {
    marginRight: theme.spacing(1)
  },
  sugestion: {
    marginTop: theme.spacing(2)
  },
  textField: {
    marginTop: theme.spacing(2)
  },
  signInButton: {
    margin: theme.spacing(2, 0)
  },
  margin: {
    margin: theme.spacing(1)
  }
}));

const SignIn = props => {
  const { handleSignIn, handleChange, user, locationState, error } = props;
  const [open, setOpen] = useState(true);
  const classes = useStyles();

  function onClose() {
    setOpen(false);
  }

  function LocationMessage() {
    let type = "";
    let message = "";
    if (
      locationState &&
      locationState.state &&
      locationState.state.errorMessage
    ) {
      type = "error";
      message = locationState.state.errorMessage;
    }
    if (
      locationState &&
      locationState.state &&
      locationState.state.successMessage
    ) {
      type = "success";
      message = locationState.state.successMessage;
    }
    if (type && message) {
      return (
        <MessagePannel
          variant={type}
          className={classes.margin}
          message={message}
          open={open}
          onClose={onClose}
        />
      );
    }
    return <></>;
  }

  function Message() {
    let type = "";
    let message = "";

    if (error && error.message) {
      type = "error";
      message = error.message;
    }

    if (type && message) {
      return (
        <MessagePannel
          variant={type}
          className={classes.margin}
          message={message}
          open={open}
          onClose={onClose}
        />
      );
    }
    return <></>;
  }

  return (
    <div className={classes.root}>
      <Grid className={classes.grid} container spacing={1}>
        <Grid item xs={12} sm={5}>
          <img
            src="/images/blue.jpg"
            alt="timezones"
            width="100%"
            height="103%"
          ></img>
        </Grid>
        <Grid className={classes.content} item xs={7}>
          <div className={classes.content}>
            <div className={classes.contentBody}>
              <form className={classes.form} onSubmit={handleSignIn}>
                <Typography className={classes.title} variant="h2">
                  Sign in
                </Typography>
                <Typography color="textSecondary" gutterBottom>
                  Sign in with social media
                </Typography>
                <SocialButtons handleSignIn={handleSignIn} />
                <Typography
                  align="center"
                  className={classes.sugestion}
                  color="textSecondary"
                  variant="body1"
                >
                  or login with email address
                </Typography>
                <LocationMessage></LocationMessage>
                <Message></Message>
                <TextField
                  className={classes.textField}
                  fullWidth
                  label="Username"
                  name="username"
                  onChange={handleChange}
                  type="text"
                  variant="outlined"
                  value={user.username}
                  required={true}
                />
                <TextField
                  className={classes.textField}
                  fullWidth
                  label="Password"
                  name="password"
                  onChange={handleChange}
                  type="password"
                  variant="outlined"
                  value={user.password}
                  required={true}
                />
                <Button
                  className={classes.signInButton}
                  color="primary"
                  fullWidth
                  size="large"
                  type="submit"
                  variant="contained"
                >
                  Sign in now
                </Button>
                <Typography color="textSecondary" variant="body1">
                  Don't have an account?{" "}
                  <Link component={RouterLink} to="/signup" variant="h6">
                    Sign up
                  </Link>
                </Typography>
              </form>
            </div>
          </div>
        </Grid>
      </Grid>
    </div>
  );
};

SignIn.propTypes = {
  history: PropTypes.object
};

export default withRouter(SignIn);
