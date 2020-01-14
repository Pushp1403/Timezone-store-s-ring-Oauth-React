import React from "react";
import Button from "@material-ui/core/Button";
import TextField from "@material-ui/core/TextField";
import Dialog from "@material-ui/core/Dialog";
import DialogActions from "@material-ui/core/DialogActions";
import DialogContent from "@material-ui/core/DialogContent";
import DialogTitle from "@material-ui/core/DialogTitle";
import Grid from "@material-ui/core/Grid";
import { connect } from "react-redux";
import { toast } from "react-toastify";
import Select from "@material-ui/core/Select";
import MenuItem from "@material-ui/core/MenuItem";
import {
  addNewUser,
  updateUser
} from "../../../redux/actions/userActions/userActions";
import { makeStyles } from "@material-ui/styles";
import Authority from "../../../dataModels/authority";

const useStyles = makeStyles(theme => ({
  textField: {
    marginTop: theme.spacing(2)
  },
  content: {
    height: "100%",
    display: "flex",
    flexDirection: "column"
  }
}));

function UserDetails(props) {
  const {
    user,
    open,
    handleClose,
    handleChange,
    addNewUser,
    updateUser
  } = props;
  const classes = useStyles();

  const handleSubmit = event => {
    event.preventDefault();
    const userTosave = {
      ...user,
      authorities: [new Authority(user.authority, user.username)],
      username: user.username
    };

    if (
      userTosave.password &&
      userTosave.password !== userTosave.confirmPassword
    ) {
      toast("Password mismatch");
      return;
    }

    if (userTosave.provider) {
      updateUser(userTosave)
        .then(res => {
          toast("User updated successfully");
          handleClose();
        })
        .catch(error => {
          console.log(error);
          toast(error.response.data.apierror.message);
        });
    } else {
      userTosave.provider = "local";
      addNewUser(userTosave)
        .then(res => {
          toast("User Saved Successfully");
          handleClose();
        })
        .catch(err => {
          console.log(err);
          toast(err.response.data.apierror.message);
        });
    }
  };

  return (
    <Dialog open={open} onClose={handleClose}>
      <DialogTitle>User Details</DialogTitle>
      <form onSubmit={handleSubmit}>
        <DialogContent>
          <Grid className={classes.content} container spacing={2}>
            <form onSubmit={handleSubmit}>
              <Grid item xs={12} sm={12}>
                <TextField
                  fullWidth
                  label="User name"
                  name="username"
                  onChange={handleChange}
                  type="text"
                  variant="outlined"
                  value={user.username}
                  required
                  className={classes.textField}
                  disabled={user.provider}
                />
              </Grid>
              <Grid container spacing={1}>
                <Grid item xs={12} sm={6}>
                  <TextField
                    fullWidth
                    label="First name"
                    name="firstName"
                    onChange={handleChange}
                    type="text"
                    variant="outlined"
                    value={user.firstName}
                    required
                    className={classes.textField}
                  />
                </Grid>
                <Grid item xs={12} sm={6}>
                  <TextField
                    fullWidth
                    label="Last name"
                    name="lastName"
                    onChange={handleChange}
                    type="text"
                    variant="outlined"
                    value={user.lastName}
                    required
                    className={classes.textField}
                  />
                </Grid>
              </Grid>
              <Grid item xs={12} sm={12}>
                <TextField
                  fullWidth
                  label="Email address"
                  name="emailId"
                  onChange={handleChange}
                  type="text"
                  variant="outlined"
                  value={user.emailId}
                  required
                  className={classes.textField}
                />
              </Grid>
              <Grid item xs={12} sm={12}>
                <TextField
                  fullWidth
                  label="Password"
                  name="password"
                  onChange={handleChange}
                  type="password"
                  variant="outlined"
                  value={user.password}
                  className={classes.textField}
                  disabled={user.provider && user.provider !== "local"}
                />
              </Grid>
              <Grid item xs={12} sm={12}>
                <TextField
                  fullWidth
                  label="Confirm Password"
                  name="confirmPassword"
                  onChange={handleChange}
                  type="password"
                  variant="outlined"
                  value={user.confirmPassword}
                  className={classes.textField}
                  disabled={user.provider && user.provider !== "local"}
                />
              </Grid>
              <Grid item xs={12} sm={12}>
                <Select
                  fullWidth
                  value={user.authority}
                  onChange={handleChange}
                  name="authority"
                  variant="outlined"
                  required
                  className={classes.textField}
                >
                  <MenuItem value={"ROLE_USER"}>USER</MenuItem>
                  <MenuItem value={"ROLE_USER_MANAGER"}>USER_MANAGER</MenuItem>
                  <MenuItem value={"ROLE_ADMIN"}>ADMIN</MenuItem>
                </Select>
              </Grid>
              {user.provider ? (
                <Grid item xs={12} sm={12}>
                  <Select
                    fullWidth
                    value={user.locked}
                    onChange={handleChange}
                    name="locked"
                    variant="outlined"
                    required
                    className={classes.textField}
                  >
                    <MenuItem value={false}>Active</MenuItem>
                    <MenuItem value={true}>Locked</MenuItem>
                  </Select>
                </Grid>
              ) : (
                <></>
              )}
            </form>
          </Grid>
        </DialogContent>
        <DialogActions>
          <Button type="Submit" color="primary" variant="contained">
            Submit
          </Button>
          <Button onClick={handleClose} color="default" variant="contained">
            Close
          </Button>
        </DialogActions>
      </form>
    </Dialog>
  );
}

function mapStateToProps(state) {
  return {};
}

const mapDispatchToProps = {
  addNewUser: addNewUser,
  updateUser: updateUser
};

export default connect(mapStateToProps, mapDispatchToProps)(UserDetails);
