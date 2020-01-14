import React, { useState } from "react";
import clsx from "clsx";
import PropTypes from "prop-types";
import PerfectScrollbar from "react-perfect-scrollbar";
import { makeStyles } from "@material-ui/styles";
import { connect } from "react-redux";
import Delete from "@material-ui/icons/Delete";
import Edit from "@material-ui/icons/Edit";
import TimezoneDetails from "../../NewTimezone/TimezoneDetails";

import {
  Card,
  CardActions,
  CardContent,
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
import Timezone from "../../../../dataModels/timeZone";

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
    timezones,
    selectedTimezones,
    setSelectedTimezones,
    deleteSingleRow,
    handlePagination,
    ...rest
  } = props;

  const classes = useStyles();

  const [rowsPerPage, setRowsPerPage] = useState(20);
  const [page, setPage] = useState(0);
  const [open, setOpen] = useState(false);
  const [tempTimezone, setTempTimezone] = useState(new Timezone());

  const handleSelectAll = event => {
    let selectedTimezones;
    if (event.target.checked) {
      selectedTimezones = timezones.map(timezone => timezone.timeZoneId);
    } else {
      selectedTimezones = [];
    }
    setSelectedTimezones(selectedTimezones);
  };

  const handleSelectOne = (event, id) => {
    const selectedIndex = selectedTimezones.indexOf(id);
    let newSelectedTimezones = [];

    if (selectedIndex === -1) {
      newSelectedTimezones = newSelectedTimezones.concat(selectedTimezones, id);
    } else if (selectedIndex === 0) {
      newSelectedTimezones = newSelectedTimezones.concat(
        selectedTimezones.slice(1)
      );
    } else if (selectedIndex === selectedTimezones.length - 1) {
      newSelectedTimezones = newSelectedTimezones.concat(
        selectedTimezones.slice(0, -1)
      );
    } else if (selectedIndex > 0) {
      newSelectedTimezones = newSelectedTimezones.concat(
        selectedTimezones.slice(0, selectedIndex),
        selectedTimezones.slice(selectedIndex + 1)
      );
    }

    setSelectedTimezones(newSelectedTimezones);
  };

  const handlePageChange = (event, page) => {
    setPage(page);
    handlePagination(page, rowsPerPage);
  };

  const handleRowsPerPageChange = event => {
    setRowsPerPage(event.target.value);
    handlePagination(page, rowsPerPage);
  };

  const handleClose = event => {
    setOpen(false);
  };

  const handleChange = event => {
    setTempTimezone({
      ...tempTimezone,
      [event.target.name]: event.target.value
    });
  };

  const addressChangeHandler = changes => {
    setTempTimezone({ ...tempTimezone, ...changes });
    console.log(changes);
  };

  const editTimeZone = (event, timezone) => {
    setTempTimezone(timezone);
    setOpen(true);
  };

  return (
    <Card {...rest} className={clsx(classes.root, className)}>
      <CardContent className={classes.content}>
        <PerfectScrollbar>
          <div>
            {timezones.length === 0 ? <></> : <div></div>}
            <Table>
              <TableHead>
                <TableRow>
                  <TableCell padding="checkbox">
                    <Checkbox
                      checked={selectedTimezones.length === timezones.length}
                      color="primary"
                      indeterminate={
                        selectedTimezones.length > 0 &&
                        selectedTimezones.length < timezones.length
                      }
                      onChange={handleSelectAll}
                    />
                  </TableCell>
                  <TableCell>Name</TableCell>
                  <TableCell style={{ width: "30%" }}>City</TableCell>
                  <TableCell>GMT Offset</TableCell>
                  <TableCell>Browser Offset</TableCell>
                  <TableCell>Time in Timezone</TableCell>
                  <TableCell>Edit</TableCell>
                  <TableCell>Delete</TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {timezones.slice(0, rowsPerPage).map(timezone => (
                  <TableRow
                    className={classes.tableRow}
                    hover
                    key={timezone.timeZoneId}
                    selected={
                      selectedTimezones.indexOf(timezone.timeZoneId) !== -1
                    }
                  >
                    <TableCell padding="checkbox">
                      <Checkbox
                        checked={
                          selectedTimezones.indexOf(timezone.timeZoneId) !== -1
                        }
                        color="primary"
                        onChange={event =>
                          handleSelectOne(event, timezone.timeZoneId)
                        }
                        value="true"
                      />
                    </TableCell>
                    <TableCell>
                      <div className={classes.nameContainer}>
                        <Typography variant="body1">{timezone.name}</Typography>
                      </div>
                    </TableCell>
                    <TableCell>{timezone.city}</TableCell>
                    <TableCell>{timezone.differenceFromGMT}</TableCell>
                    <TableCell>
                      {timezone.differenceFromGMT -
                        new Date().getTimezoneOffset() * 60}
                    </TableCell>
                    <TableCell>
                      {new Date().toLocaleString("en-US", {
                        timeZone: timezone.timeZoneRegion
                      })}
                    </TableCell>
                    <TableCell>
                      <IconButton
                        onClick={event => {
                          editTimeZone(event, timezone);
                        }}
                      >
                        <Edit></Edit>
                      </IconButton>
                    </TableCell>
                    <TableCell>
                      <IconButton
                        onClick={event =>
                          deleteSingleRow(event, timezone.timeZoneId)
                        }
                      >
                        <Delete></Delete>
                      </IconButton>
                    </TableCell>
                  </TableRow>
                ))}
              </TableBody>
            </Table>
          </div>
        </PerfectScrollbar>
      </CardContent>
      <CardActions className={classes.actions}>
        <TablePagination
          component="div"
          count={timezones.length}
          onChangePage={handlePageChange}
          onChangeRowsPerPage={handleRowsPerPageChange}
          page={page}
          rowsPerPage={rowsPerPage}
          rowsPerPageOptions={[5, 10, 20]}
        />
      </CardActions>
      <TimezoneDetails
        open={open}
        handleChange={handleChange}
        handleClose={handleClose}
        addressChangeHandler={addressChangeHandler}
        timezone={tempTimezone}
      />
    </Card>
  );
};

UsersTable.propTypes = {
  className: PropTypes.string
};

function mapStatetoProps(state) {
  return {
    timezones: state.timezoneReducer
  };
}

export default connect(mapStatetoProps)(UsersTable);
