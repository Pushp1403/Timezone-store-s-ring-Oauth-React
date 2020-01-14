import React from "react";
import { Grid, Button } from "@material-ui/core";
import { Facebook as FacebookIcon, Google as GoogleIcon } from "../../icons";
import { makeStyles } from "@material-ui/styles";
import { FACEBOOK_AUTH_URL, GOOGLE_AUTH_URL } from "../../utilities/constants";

const useStyles = makeStyles(theme => ({
  logoImage: {
    marginLeft: theme.spacing(4)
  },

  socialButtons: {
    marginTop: theme.spacing(3)
  },
  socialIcon: {
    marginRight: theme.spacing(1)
  }
}));

const SocialButtons = props => {
  const classes = useStyles();

  return (
    <Grid className={classes.socialButtons} container spacing={1}>
      <Grid item>
        <Button
          color="primary"
          size="large"
          variant="contained"
          href={FACEBOOK_AUTH_URL}
        >
          <FacebookIcon className={classes.socialIcon} />
          Login with Facebook
        </Button>
      </Grid>
      <Grid item>
        <Button size="large" variant="contained" href={GOOGLE_AUTH_URL}>
          <GoogleIcon className={classes.socialIcon} />
          Login with Google
        </Button>
      </Grid>
    </Grid>
  );
};

export default SocialButtons;
