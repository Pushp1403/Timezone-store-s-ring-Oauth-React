import React from "react";
import clsx from "clsx";
import PropTypes from "prop-types";
import { makeStyles } from "@material-ui/styles";
import { connect } from "react-redux";
import {
  Card,
  CardHeader,
  CardContent,
  Divider,
  Grid,
  TextField
} from "@material-ui/core";

const useStyles = makeStyles(() => ({
  root: {}
}));

const AccountDetails = props => {
  const { className, user, ...rest } = props;

  const classes = useStyles();

  return (
    <Card {...rest} className={clsx(classes.root, className)}>
      <CardHeader
        subheader="The information can not be edited"
        title="Profile"
      />
      <Divider />
      {user && user.firstName ? (
        <CardContent>
          <Grid container spacing={3}>
            <Grid item md={6} xs={12}>
              <TextField
                fullWidth
                label="First name"
                margin="dense"
                name="firstName"
                required
                value={user.firstName}
                variant="outlined"
                disabled
              />
            </Grid>
            <Grid item md={6} xs={12}>
              <TextField
                fullWidth
                label="Last name"
                margin="dense"
                name="lastName"
                required
                value={user.lastName}
                variant="outlined"
                disabled
              />
            </Grid>
            <Grid item md={6} xs={12}>
              <TextField
                fullWidth
                label="Email Address"
                margin="dense"
                name="email"
                required
                value={user.emailId}
                variant="outlined"
                disabled
              />
            </Grid>
            <Grid item md={6} xs={12}>
              <TextField
                fullWidth
                label="User Role"
                margin="dense"
                name="role"
                type="number"
                value={user.authorities[0].role}
                variant="outlined"
                disabled
              />
            </Grid>
          </Grid>
        </CardContent>
      ) : (
        <></>
      )}
    </Card>
  );
};

AccountDetails.propTypes = {
  className: PropTypes.string
};

const mapStateToProps = state => {
  return {
    user: state.authReducer.loggeIdUser
  };
};

export default connect(mapStateToProps)(AccountDetails);
