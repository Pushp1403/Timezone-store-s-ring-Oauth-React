import React, { useState, useEffect } from "react";
import { makeStyles } from "@material-ui/styles";
import { TimezoneTable, TimezoneToolbar } from "./component";
import { connect } from "react-redux";
import {
  loadTimezones,
  deleteTimezone
} from "./../../redux/actions/timeZoneActions/timeZoneActions";
import { toast } from "react-toastify";
import FilterModel from "../../dataModels/timezoneFilterModel";

const useStyles = makeStyles(theme => ({
  root: {
    padding: theme.spacing(3)
  },
  content: {
    marginTop: theme.spacing(2)
  }
}));

const TimezoneList = props => {
  const { loadTimezones, loggedInUser, deleteTimezone } = props;

  const createNewFilterModel = () => {
    const filterModel = new FilterModel();
    filterModel.username =
      loggedInUser.authorities[0].role === "ROLE_ADMIN"
        ? null
        : loggedInUser.username;
    return filterModel;
  };

  const [timezoneFilter, setTimezoneFilter] = useState(createNewFilterModel());
  const [selectedTimezones, setSelectedTimezones] = useState([]);

  const classes = useStyles();

  useEffect(() => {
    if (loggedInUser && loggedInUser.username) {
      loadTimezones(timezoneFilter).catch(error => {
        console.log(error);
        toast("failed");
      });
    }
  }, [loadTimezones, loggedInUser, timezoneFilter]);

  const handleSearchChange = event => {
    setTimezoneFilter({
      ...timezoneFilter,
      [event.target.name]: event.target.value
    });
  };

  const deleteSelected = event => {
    selectedTimezones.forEach(id => {
      deleteTimezone(id).then(() => {
        toast("Deleted");
      });
    });
  };

  const deleteSingleRow = (event, id) => {
    deleteTimezone(id).then(() => {
      toast("Deleted");
    });
  };

  const handlePagination = (nextPage, pageSize) => {
    setTimezoneFilter({ ...timezoneFilter, nextPage, pageSize });
  };

  return (
    <div className={classes.root}>
      <TimezoneToolbar
        timezoneFilter={timezoneFilter}
        handleSearchChange={handleSearchChange}
        deleteSelected={deleteSelected}
      />
      <div className={classes.content}>
        <TimezoneTable
          selectedTimezones={selectedTimezones}
          setSelectedTimezones={setSelectedTimezones}
          deleteSingleRow={deleteSingleRow}
          handlePagination={handlePagination}
        />
      </div>
    </div>
  );
};

const mapDispatchToProps = {
  loadTimezones: loadTimezones,
  deleteTimezone: deleteTimezone
};

const mapStatetoProps = state => {
  return {
    loggedInUser: state.authReducer.loggeIdUser
  };
};

export default connect(mapStatetoProps, mapDispatchToProps)(TimezoneList);
