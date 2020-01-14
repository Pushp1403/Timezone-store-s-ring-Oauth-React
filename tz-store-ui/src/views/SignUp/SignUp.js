import React from "react";
import { Link as RouterLink, withRouter } from "react-router-dom";
import PropTypes from "prop-types";
import { makeStyles } from "@material-ui/styles";
import { Grid, Button, TextField, Link, Typography } from "@material-ui/core";
import Select from "@material-ui/core/Select";
import MenuItem from "@material-ui/core/MenuItem";

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
    display: "flex",
    flexDirection: "column"
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
  textField: {
    marginTop: theme.spacing(2)
  },
  policy: {
    marginTop: theme.spacing(1),
    display: "flex",
    alignItems: "center"
  },
  policyCheckbox: {
    marginLeft: "-14px"
  },
  signUpButton: {
    margin: theme.spacing(2, 0)
  },
  sugestion: {
    marginTop: theme.spacing(2)
  }
}));

const SignUp = props => {
  const { handleSignup, handleChange, user } = props;

  const classes = useStyles();

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
        <Grid className={classes.content} item xs={12} sm={7}>
          <div className={classes.content}>
            <div className={classes.contentBody}>
              <form className={classes.form} onSubmit={handleSignup}>
                <Typography className={classes.title} variant="h2">
                  Sign Up
                </Typography>
                <Typography color="textSecondary" gutterBottom>
                  sign up with email address
                </Typography>

                <TextField
                  className={classes.textField}
                  fullWidth
                  label="Username"
                  name="username"
                  onChange={handleChange}
                  type="text"
                  variant="outlined"
                  value={user.username}
                  required
                />
                <Grid container spacing={2}>
                  <Grid item xs={12} sm={6}>
                    <TextField
                      className={classes.textField}
                      fullWidth
                      label="First name"
                      name="firstName"
                      onChange={handleChange}
                      type="text"
                      variant="outlined"
                      value={user.firstName}
                      required={true}
                    />
                  </Grid>
                  <Grid item xs={12} sm={6}>
                    <TextField
                      className={classes.textField}
                      fullWidth
                      label="Last name"
                      name="lastName"
                      onChange={handleChange}
                      type="text"
                      variant="outlined"
                      value={user.lastName}
                      required
                    />
                  </Grid>
                </Grid>
                <TextField
                  className={classes.textField}
                  fullWidth
                  label="Email address"
                  name="emailId"
                  onChange={handleChange}
                  type="text"
                  variant="outlined"
                  value={user.emailId}
                  required
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
                  required
                />
                <TextField
                  className={classes.textField}
                  fullWidth
                  label="Confirm Password"
                  name="confirmPassword"
                  onChange={handleChange}
                  type="password"
                  variant="outlined"
                  value={user.confirmPassword}
                  required
                />
                <Select
                  className={classes.textField}
                  fullWidth
                  value={user.authority}
                  onChange={handleChange}
                  name="authority"
                  variant="outlined"
                  required
                >
                  <MenuItem value={"USER"} selected={true}>
                    USER
                  </MenuItem>
                  <MenuItem value={"USER_MANAGER"}>USER_MANAGER</MenuItem>
                  <MenuItem value={"ADMIN"}>ADMIN</MenuItem>
                </Select>

                <Button
                  className={classes.signUpButton}
                  color="primary"
                  fullWidth
                  size="large"
                  type="submit"
                  variant="contained"
                >
                  Sign up now
                </Button>
                <Typography color="textSecondary" variant="body1">
                  Have an account?{" "}
                  <Link component={RouterLink} to="/signin" variant="h6">
                    Sign in
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

SignUp.propTypes = {
  history: PropTypes.object
};

export default withRouter(SignUp);
