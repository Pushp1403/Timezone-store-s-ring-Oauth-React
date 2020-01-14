import React, { useState } from "react";
import Button from "@material-ui/core/Button";
import TextField from "@material-ui/core/TextField";
import Dialog from "@material-ui/core/Dialog";
import DialogActions from "@material-ui/core/DialogActions";
import DialogContent from "@material-ui/core/DialogContent";
import DialogTitle from "@material-ui/core/DialogTitle";
import Grid from "@material-ui/core/Grid";
import { inviteUser } from "../../redux/actions/userActions/userActions";
import { toast } from "react-toastify";

function EmailInvitation(props) {
  const { open, handleClose } = props;
  const [email, setEmail] = useState("");

  const handleChange = event => {
    setEmail(event.target.value);
  };

  const handleBoxClose = event => {
    setEmail("");
    handleClose(event);
  };

  const handleSubmit = event => {
    event.preventDefault();
    inviteUser(email)
      .then(res => {
        setEmail("");
        handleClose();
        toast(res.data.apisuccessresponse.message);
      })
      .catch(error => {
        toast(error.response.data.apierror.message);
      });
  };

  return (
    <Dialog open={open} onClose={handleClose}>
      <DialogTitle id="form-dialog-title">Send an invite</DialogTitle>
      <form onSubmit={handleSubmit}>
        <DialogContent>
          <Grid container spacing={2}>
            <Grid item xs={12} sm={12}>
              <TextField
                variant="outlined"
                name="Email Id"
                style={{ width: "500px" }}
                value={email}
                label="Email ID"
                onChange={handleChange}
                fullWidth
                required
              />
            </Grid>
          </Grid>
        </DialogContent>
        <DialogActions>
          <Button type="Submit" color="primary" variant="contained">
            Send Invite
          </Button>
          <Button onClick={handleBoxClose} color="default" variant="contained">
            Close
          </Button>
        </DialogActions>
      </form>
    </Dialog>
  );
}

export default EmailInvitation;
