import React from "react";
import PlacesAutocomplete, {
  geocodeByAddress,
  getLatLng
} from "react-places-autocomplete";
import TextField from "@material-ui/core/TextField";
import MenuItem from "@material-ui/core/MenuItem";
import Paper from "@material-ui/core/Paper";

var timezone = require("node-google-timezone");
timezone.key("AIzaSyCn0KjUApSKUtbuOdC5Ea5dD_aJBWpImiw");
export default class AddressAutocomplete extends React.Component {
  constructor(props) {
    super(props);
    this.state = { city: props.timezone.city };
  }

  handleChange = city => {
    this.setState({ city });
  };

  handleSelect = address => {
    const timeZoneLoaded = (err, tz) => {
      console.log(tz.raw_response);
      var d = new Date(tz.local_timestamp * 1000);
      console.log(
        d.toDateString() + " - " + d.getHours() + ":" + d.getMinutes()
      );
      this.props.addressChangeHandler({
        differenceFromGMT: tz.raw_response.rawOffset,
        city: address,
        timeZoneRegion: tz.raw_response.timeZoneId
      });
    };
    geocodeByAddress(address)
      .then(results => getLatLng(results[0]))
      .then(latLng => {
        timezone.data(
          latLng.lat,
          latLng.lng,
          Math.floor(Date.now() / 1000),
          timeZoneLoaded
        );
      })
      .catch(error => console.error("Error", error));
  };

  render() {
    return (
      <PlacesAutocomplete
        value={this.state.city}
        onChange={this.handleChange}
        onSelect={this.handleSelect}
      >
        {({ getInputProps, suggestions, getSuggestionItemProps, loading }) => (
          <div>
            <TextField
              {...getInputProps({
                className: "location-search-input",
                variant: "outlined",
                name: "address",
                label: "Search Places ...",
                fullWidth: true,
                required: true
              })}
            />
            <div className="autocomplete-dropdown-container">
              {loading && <div>Loading...</div>}
              {suggestions.map(suggestion => {
                const className = suggestion.active
                  ? "suggestion-item--active"
                  : "suggestion-item";
                // inline style for demonstration purpose
                const style = suggestion.active
                  ? { backgroundColor: "#fafafa", cursor: "pointer" }
                  : { backgroundColor: "#ffffff", cursor: "pointer" };
                return (
                  <Paper square>
                    <MenuItem
                      key={suggestion.description}
                      component="div"
                      {...getSuggestionItemProps(suggestion, {
                        className,
                        style
                      })}
                    >
                      {suggestion.description}
                    </MenuItem>
                  </Paper>
                );
              })}
            </div>
          </div>
        )}
      </PlacesAutocomplete>
    );
  }
}
