import React, { useState } from "react";
import PropTypes from "prop-types";
import clsx from "clsx";
import { makeStyles } from "@material-ui/styles";
import { Button } from "@material-ui/core";
import TimezoneDetails from "./../../NewTimezone/TimezoneDetails";
import { SearchInput } from "../../../../components";
import Timezone from "../../../../dataModels/timeZone";

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
  searchInput: {
    marginRight: theme.spacing(1)
  }
}));

const TimezoneToolbar = props => {
  const {
    className,
    timezoneFilter,
    handleSearchChange,
    deleteSelected,
    ...rest
  } = props;

  const classes = useStyles();
  const [open, setOpen] = useState(false);
  const [timezone, setTimezone] = useState(new Timezone("", "", "", ""));

  const handleClose = event => {
    setOpen(false);
    setTimezone(new Timezone("", "", "", ""));
  };

  const openAddEditTimezone = event => {
    event.preventDefault();
    setOpen(true);
  };

  const handleChange = event => {
    setTimezone({ ...timezone, [event.target.name]: event.target.value });
  };

  const addressChangeHandler = changes => {
    setTimezone({ ...timezone, ...changes });
    console.log(changes);
  };

  return (
    <div {...rest} className={clsx(classes.root, className)}>
      <div className={classes.row}>
        <SearchInput
          className={classes.searchInput}
          placeholder="Search Timezone"
          name="name"
          onChange={handleSearchChange}
        />
        <span className={classes.spacer} />
        <Button
          color="primary"
          variant="contained"
          onClick={openAddEditTimezone}
        >
          Add New Timezone
        </Button>
        <span className={classes.smallspacer} />

        <Button color="secondary" variant="contained" onClick={deleteSelected}>
          Delete Selected Timezones{" "}
        </Button>
        <TimezoneDetails
          open={open}
          handleChange={handleChange}
          handleClose={handleClose}
          addressChangeHandler={addressChangeHandler}
          timezone={timezone}
        />
      </div>
    </div>
  );
};

TimezoneToolbar.propTypes = {
  className: PropTypes.string
};

export default TimezoneToolbar;
