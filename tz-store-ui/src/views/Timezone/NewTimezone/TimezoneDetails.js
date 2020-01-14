import React from "react";
import Button from "@material-ui/core/Button";
import TextField from "@material-ui/core/TextField";
import Dialog from "@material-ui/core/Dialog";
import DialogActions from "@material-ui/core/DialogActions";
import DialogContent from "@material-ui/core/DialogContent";
import DialogTitle from "@material-ui/core/DialogTitle";
import Grid from "@material-ui/core/Grid";
import AddressAutocomplete from "./AddressAutocomplete";
import {
  saveTimezone,
  deleteTimezone
} from "../../../redux/actions/timeZoneActions/timeZoneActions";
import { connect } from "react-redux";
import { toast } from "react-toastify";

function TimezoneDetails(props) {
  const {
    timezone,
    open,
    handleClose,
    handleChange,
    addressChangeHandler,
    saveTimezone,
    deleteTimezone
  } = props;

  const handleSubmit = event => {
    event.preventDefault();
    saveTimezone(timezone)
      .then(res => {
        toast("Timezone created successfully.");
        handleClose(event);
      })
      .catch(error => {
        console.log(error);
        toast("failed");
      });
  };

  const handleDelete = event => {
    deleteTimezone(timezone.timeZoneId)
      .then(res => {
        toast("Timezone deleted successfully");
      })
      .error(err => {
        console.log(err);
        toast(err);
      });
  };

  return (
    <Dialog open={open} onClose={handleClose}>
      <DialogTitle id="form-dialog-title">Timezone Details</DialogTitle>
      <form onSubmit={handleSubmit}>
        <DialogContent>
          <Grid container spacing={2}>
            <Grid item xs={12} sm={12}>
              <TextField
                autoFocus
                variant="outlined"
                name="name"
                value={timezone.name}
                onChange={handleChange}
                label="Timezone name"
                type="text"
                fullWidth
                required
              />
            </Grid>
            <Grid item xs={12} sm={12}>
              <AddressAutocomplete
                timezone={timezone}
                addressChangeHandler={addressChangeHandler}
              ></AddressAutocomplete>
            </Grid>
            <Grid item xs={12} sm={12}>
              <TextField
                variant="outlined"
                name="differenceFromGMT"
                value={timezone.differenceFromGMT}
                label="Difference From GMT"
                onChange={handleChange}
                fullWidth
                required
                disabled
              />
            </Grid>
            <Grid item xs={12} sm={12}>
              <TextField
                variant="outlined"
                name="timeZoneRegion"
                value={timezone.timeZoneRegion}
                label="Timezone ID"
                onChange={handleChange}
                fullWidth
                required
                disabled
              />
            </Grid>
          </Grid>
        </DialogContent>
        <DialogActions>
          <ActionPannel
            timezone={timezone}
            handleDelete={handleDelete}
          ></ActionPannel>
          <Button onClick={handleClose} color="default" variant="contained">
            Close
          </Button>
        </DialogActions>
      </form>
    </Dialog>
  );
}

function ActionPannel({ timezone, handleDelete }) {
  return (
    <>
      <Button type="Submit" color="primary" variant="contained">
        Submit
      </Button>
    </>
  );
}

function mapStateToProps(state) {
  return {};
}

const mapDispatchToProps = {
  deleteTimezone: deleteTimezone,
  saveTimezone: saveTimezone
};

export default connect(mapStateToProps, mapDispatchToProps)(TimezoneDetails);
