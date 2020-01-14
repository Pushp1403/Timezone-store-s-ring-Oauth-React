import React, { useState, useEffect } from "react";
import { makeStyles } from "@material-ui/styles";
import { UsersToolbar, UsersTable } from "./components";
import { connect } from "react-redux";
import {
  retrieveAllUsers,
  deleteUser
} from "../../redux/actions/userActions/userActions";
import UserFilterModel from "../../dataModels/userFilterModel";
import { toast } from "react-toastify";

const useStyles = makeStyles(theme => ({
  root: {
    padding: theme.spacing(3)
  },
  content: {
    marginTop: theme.spacing(2)
  }
}));

const UserList = props => {
  const classes = useStyles();
  const { users, retrieveAllUsers, deleteUser } = props;
  const [selectedUsers, setSelectedUsers] = useState([]);
  const [userFilterModel, setUserFilterModel] = useState(new UserFilterModel());

  useEffect(() => {
    retrieveAllUsers(userFilterModel).catch(error => {
      console.log(error);
    });
  }, [retrieveAllUsers, userFilterModel]);

  const handleSearchChange = event => {
    setUserFilterModel({
      ...userFilterModel,
      [event.target.name]: event.target.value
    });
  };

  const deleteSelectedUsers = event => {
    selectedUsers.forEach(user => {
      deleteUser(user);
    });
  };

  const deleteSingleUser = (event, user) => {
    deleteUser(user.username)
      .then(res => {
        toast("User Deleted");
      })
      .catch(err => {
        console.log(err);
        toast("Internal Server error");
      });
  };

  const handlePagination = (nextPage, pageSize) => {
    setUserFilterModel({ ...userFilterModel, nextPage, pageSize });
  };

  return (
    <div className={classes.root}>
      <UsersToolbar
        userFilterModel={userFilterModel}
        handleSearchChange={handleSearchChange}
        deleteSelectedUsers={deleteSelectedUsers}
      />
      <div className={classes.content}>
        <UsersTable
          users={users}
          selectedUsers={selectedUsers}
          deleteSingleUser={deleteSingleUser}
          setSelectedUsers={setSelectedUsers}
          handlePagination={handlePagination}
        />
      </div>
    </div>
  );
};

const mapDispatchToProps = {
  retrieveAllUsers: retrieveAllUsers,
  deleteUser: deleteUser
};

const mapStateToProps = state => {
  return {
    users: state.userReducers
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(UserList);
