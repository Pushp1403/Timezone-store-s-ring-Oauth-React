import React, { useState } from "react";
import PropTypes from "prop-types";
import clsx from "clsx";
import { makeStyles } from "@material-ui/styles";
import { Button } from "@material-ui/core";
import { SearchInput } from "../../../../components";
import UserDetail from "../../UserDetail";
import UserRegistrationRequest from "../../../../dataModels/userRegistrationRequest";
import InviteUser from "../../../InviteUser";
import { connect } from "react-redux";

const useStyles = makeStyles(theme => ({
  root: {},
  row: {
    height: "42px",
    display: "flex",
    alignItems: "center",
    marginTop: theme.spacing(1)
  },
  spacer: {
    flexGrow: 1
  },
  smallspacer: {
    flexGrow: 0.1
  },
  importButton: {
    marginRight: theme.spacing(1)
  },
  exportButton: {
    marginRight: theme.spacing(1)
  },
  searchInput: {
    marginRight: theme.spacing(1)
  }
}));

const UsersToolbar = props => {
  const {
    className,
    handleSearchChange,
    deleteSelectedUsers,
    loggedInUser,
    ...rest
  } = props;

  const classes = useStyles();
  const [open, setOpen] = useState(false);
  const [user, setUser] = useState(new UserRegistrationRequest());
  const [invitationBoxOpen, setInvitationBoxOpen] = useState(false);

  const handleChange = event => {
    setUser({ ...user, [event.target.name]: event.target.value });
  };

  const handleClose = event => {
    setOpen(false);
  };

  const addNewUser = event => {
    setOpen(true);
  };

  const handleInvitationBoxClose = event => {
    setInvitationBoxOpen(false);
  };

  const openInvitationBox = event => {
    setInvitationBoxOpen(true);
  };

  return (
    <div {...rest} className={clsx(classes.root, className)}>
      <div className={classes.row}>
        <SearchInput
          className={classes.searchInput}
          placeholder="Search user"
          onChange={handleSearchChange}
          name="firstName"
        />
        <span className={classes.spacer} />
        <Button color="primary" variant="contained" onClick={addNewUser}>
          Add New user
        </Button>
        <span className={classes.smallspacer} />
        <Button
          color="secondary"
          variant="contained"
          onClick={deleteSelectedUsers}
        >
          Deleted Selected Users
        </Button>
        <span className={classes.smallspacer} />
        {loggedInUser && loggedInUser.authorities[0].role === "ROLE_ADMIN" ? (
          <Button
            color="secondary"
            variant="contained"
            onClick={openInvitationBox}
          >
            Invite A User
          </Button>
        ) : (
          <></>
        )}
      </div>
      <UserDetail
        user={user}
        open={open}
        handleClose={handleClose}
        handleChange={handleChange}
      ></UserDetail>
      <InviteUser
        open={invitationBoxOpen}
        handleClose={handleInvitationBoxClose}
      ></InviteUser>
    </div>
  );
};

UsersToolbar.propTypes = {
  className: PropTypes.string
};

const mapStateToProps = state => {
  return {
    loggedInUser: state.authReducer.loggeIdUser
  };
};

export default connect(mapStateToProps)(UsersToolbar);
