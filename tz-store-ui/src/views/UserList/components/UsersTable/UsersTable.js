import React, { useState } from "react";
import clsx from "clsx";
import PropTypes from "prop-types";
import PerfectScrollbar from "react-perfect-scrollbar";
import { makeStyles } from "@material-ui/styles";
import Delete from "@material-ui/icons/Delete";
import Edit from "@material-ui/icons/Edit";
import {
  Card,
  CardActions,
  CardContent,
  Avatar,
  Checkbox,
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableRow,
  Typography,
  TablePagination,
  IconButton
} from "@material-ui/core";
import UserDetail from "../../UserDetail";
import { getInitials } from "../../../../helpers";
import UserRegistrationRequest from "../../../../dataModels/userRegistrationRequest";
import { connect } from "react-redux";

const useStyles = makeStyles(theme => ({
  root: {},
  content: {
    padding: 0
  },
  nameContainer: {
    display: "flex",
    alignItems: "center"
  },
  avatar: {
    marginRight: theme.spacing(2)
  },
  actions: {
    justifyContent: "flex-end"
  }
}));

const UsersTable = props => {
  const {
    className,
    users,
    selectedUsers,
    setSelectedUsers,
    loggedInUser,
    deleteSingleUser,
    handlePagination,
    ...rest
  } = props;

  const classes = useStyles();

  const [rowsPerPage, setRowsPerPage] = useState(20);
  const [page, setPage] = useState(0);
  const [open, setOpen] = useState(false);
  const [user, setUser] = useState(new UserRegistrationRequest());

  const handleChange = event => {
    setUser({ ...user, [event.target.name]: event.target.value });
  };

  const handleClose = event => {
    setOpen(false);
  };

  const handleSelectAll = event => {
    const { users } = props;

    let selectedUsers;

    if (event.target.checked) {
      selectedUsers = users.map(user => user.username);
    } else {
      selectedUsers = [];
    }

    setSelectedUsers(selectedUsers);
  };

  const handleSelectOne = (event, id) => {
    const selectedIndex = selectedUsers.indexOf(id);
    let newSelectedUsers = [];

    if (selectedIndex === -1) {
      newSelectedUsers = newSelectedUsers.concat(selectedUsers, id);
    } else if (selectedIndex === 0) {
      newSelectedUsers = newSelectedUsers.concat(selectedUsers.slice(1));
    } else if (selectedIndex === selectedUsers.length - 1) {
      newSelectedUsers = newSelectedUsers.concat(selectedUsers.slice(0, -1));
    } else if (selectedIndex > 0) {
      newSelectedUsers = newSelectedUsers.concat(
        selectedUsers.slice(0, selectedIndex),
        selectedUsers.slice(selectedIndex + 1)
      );
    }

    setSelectedUsers(newSelectedUsers);
  };

  const handlePageChange = (event, page) => {
    setPage(page);
    handlePagination(page, rowsPerPage);
  };

  const handleRowsPerPageChange = event => {
    setRowsPerPage(event.target.value);
    handlePagination(page, rowsPerPage);
  };

  const editUser = (event, user) => {
    let newUser = { ...user, authority: user.authorities[0].role };
    setUser(newUser);
    setOpen(true);
  };

  return (
    <Card {...rest} className={clsx(classes.root, className)}>
      <CardContent className={classes.content}>
        <PerfectScrollbar>
          {users.length > 0 ? (
            <div>
              <Table>
                <TableHead>
                  <TableRow>
                    <TableCell padding="checkbox">
                      <Checkbox
                        checked={selectedUsers.length === users.length}
                        color="primary"
                        indeterminate={
                          selectedUsers.length > 0 &&
                          selectedUsers.length < users.length
                        }
                        onChange={handleSelectAll}
                      />
                    </TableCell>
                    <TableCell>Name</TableCell>
                    <TableCell>Email</TableCell>
                    <TableCell>Account Status</TableCell>
                    <TableCell>Email Verification</TableCell>
                    <TableCell>Edit</TableCell>
                    <TableCell>Delete</TableCell>
                  </TableRow>
                </TableHead>
                <TableBody>
                  {users.slice(0, rowsPerPage).map(user => (
                    <TableRow
                      className={classes.tableRow}
                      hover
                      key={user.username}
                      selected={selectedUsers.indexOf(user.username) !== -1}
                    >
                      <TableCell padding="checkbox">
                        <Checkbox
                          checked={selectedUsers.indexOf(user.username) !== -1}
                          color="primary"
                          onChange={event =>
                            handleSelectOne(event, user.username)
                          }
                          value="true"
                        />
                      </TableCell>
                      <TableCell>
                        <div className={classes.nameContainer}>
                          <Avatar
                            className={classes.avatar}
                            src={user.profilePictureUrl}
                          >
                            {getInitials(user.firstName + " " + user.lastName)}
                          </Avatar>
                          <Typography variant="body1">
                            {user.firstName} {user.lastName}
                          </Typography>
                        </div>
                      </TableCell>
                      <TableCell>{user.emailId}</TableCell>
                      <TableCell>{user.locked ? "Locked" : "Active"}</TableCell>
                      <TableCell>
                        {user.emailVerified ? "Complete" : "Pending"}
                      </TableCell>
                      <TableCell>
                        <IconButton onClick={event => editUser(event, user)}>
                          <Edit />
                        </IconButton>
                      </TableCell>
                      <TableCell>
                        {user.username === loggedInUser.username ? (
                          <></>
                        ) : (
                          <IconButton
                            onClick={event => deleteSingleUser(event, user)}
                          >
                            <Delete />{" "}
                          </IconButton>
                        )}
                      </TableCell>
                    </TableRow>
                  ))}
                </TableBody>
              </Table>
            </div>
          ) : (
            <></>
          )}
        </PerfectScrollbar>
      </CardContent>
      <CardActions className={classes.actions}>
        <TablePagination
          component="div"
          count={users.length}
          onChangePage={handlePageChange}
          onChangeRowsPerPage={handleRowsPerPageChange}
          page={page}
          rowsPerPage={rowsPerPage}
          rowsPerPageOptions={[5, 10, 20]}
        />
      </CardActions>
      <UserDetail
        user={user}
        open={open}
        handleClose={handleClose}
        handleChange={handleChange}
      ></UserDetail>
    </Card>
  );
};

UsersTable.propTypes = {
  className: PropTypes.string,
  users: PropTypes.array.isRequired
};

const mapStateToProps = state => {
  return {
    loggedInUser: state.authReducer.loggeIdUser
  };
};

export default connect(mapStateToProps)(UsersTable);
