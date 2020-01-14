import React from "react";
import PropTypes from "prop-types";
import clsx from "clsx";
import moment from "moment";
import { makeStyles } from "@material-ui/styles";
import { connect } from "react-redux";
import { uploadPicture } from "./../../../../redux/actions/userActions/userActions";
import {
  Card,
  CardActions,
  CardContent,
  Avatar,
  Typography,
  Divider,
  Button
} from "@material-ui/core";
import { toast } from "react-toastify";

const useStyles = makeStyles(theme => ({
  root: {},
  details: {
    display: "flex"
  },
  avatar: {
    marginLeft: "auto",
    height: 110,
    width: 100,
    flexShrink: 0,
    flexGrow: 0
  },
  progress: {
    marginTop: theme.spacing(2)
  },
  uploadButton: {
    marginRight: theme.spacing(2)
  }
}));

const AccountProfile = props => {
  const { className, user, uploadPicture, mapDispatchToProps, ...rest } = props;
  const classes = useStyles();
  const file = React.createRef();

  const handleProfilePictureUpload = event => {
    let extension = file.current.files[0].name.split(".");
    if (
      ["jpg", "jpeg", "png"].indexOf(
        extension[extension.length - 1].toLowerCase()
      ) < 0
    ) {
      toast("Supported file formats are: JPG, JPEG or PNG");
      return;
    }
    uploadPicture(file.current.files[0])
      .then(res => {
        toast("Profile picture updated succesfully");
      })
      .catch(error => {
        console.log(error);
        toast("failed");
      });
  };

  return (
    <Card {...rest} className={clsx(classes.root, className)}>
      {user && user.firstName ? (
        <CardContent>
          <div className={classes.details}>
            <div>
              <Typography gutterBottom variant="h2">
                {user.firstName} {user.lastName}
              </Typography>
              <Typography
                className={classes.locationText}
                color="textSecondary"
                variant="body1"
              >
                {user.emailId}
              </Typography>
              <Typography
                className={classes.dateText}
                color="textSecondary"
                variant="body1"
              >
                {moment().format("hh:mm A")}
              </Typography>
            </div>
            <Avatar className={classes.avatar} src={user.profilePictureUrl} />
          </div>
        </CardContent>
      ) : (
        <></>
      )}

      <Divider />
      <CardActions>
        <Button
          className={classes.uploadButton}
          color="primary"
          variant="text"
          onClick={() => {
            document.getElementById("file_upload").click();
          }}
        >
          Update Profile Picture
          <input
            type="file"
            style={{ display: "none" }}
            id="file_upload"
            ref={file}
            onChange={handleProfilePictureUpload}
          />
        </Button>
      </CardActions>
    </Card>
  );
};

AccountProfile.propTypes = {
  className: PropTypes.string
};

const mapDispatchToProps = {
  uploadPicture: uploadPicture
};

const mapStateToProps = state => {
  return {
    user: state.authReducer.loggeIdUser
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(AccountProfile);
