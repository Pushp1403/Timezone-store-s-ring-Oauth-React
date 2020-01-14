import API from "./../apiConfig/baseService";
import * as constants from "./../../utilities/constants";

class TimezoneService {
  loadTimezones(filters) {
    return API.get(constants.RETRIEVE_TIMEZONES, {
      params: { ...filters }
    });
  }

  deleteTimezone(timezoneId) {
    return API.delete(constants.DELETE_TIMEZONE + "/" + timezoneId);
  }

  saveTimezone(timezone) {
    return API.post(constants.CREATE_TIMEZONE, timezone);
  }

  updateTimezone(timezone) {
    return API.put(constants.UPDATE_TIMEZONE, timezone);
  }
}

export default new TimezoneService();
