import { timezoneActions } from "./../../../utilities/constants";
import timezoneService from "./../../../api/timeZoneApi/timezoneService";

export function timeZonesLoadedSuccessFully(timezones) {
  return { type: timezoneActions.TIMEZONES_LOAD_SUCCESS, timezones };
}

export function timezoneDeletedSuccessfully(timezoneId) {
  return { type: timezoneActions.TIMEZONE_DELETED_SUCCESS, timezoneId };
}

export function timezoneSavedSuccessFully(timezone) {
  return { type: timezoneActions.TIMEZONE_CREATED_SUCCESS, timezone };
}

export function timezoneUpdatedSuccessfully(timezone) {
  return { type: timezoneActions.TIMEZONE_UPDATED_SUCCESS, timezone };
}

export function loadTimezones(filters) {
  return async function(dispatch, getState) {
    return timezoneService
      .loadTimezones(filters)
      .then(results => {
        dispatch(timeZonesLoadedSuccessFully(results.data));
      })
      .catch(error => {
        throw error;
      });
  };
}

export function deleteTimezone(timezoneId) {
  return async function(dispatch, getState) {
    return timezoneService
      .deleteTimezone(timezoneId)
      .then(results => {
        dispatch(timezoneDeletedSuccessfully(timezoneId));
      })
      .catch(error => {
        throw error;
      });
  };
}

export function saveTimezone(timezone) {
  const serviceFunc = timezone.timeZoneId
    ? timezoneService.updateTimezone
    : timezoneService.saveTimezone;

  const actionFunc = timezone.timeZoneId
    ? timezoneUpdatedSuccessfully
    : timezoneSavedSuccessFully;
  return async function(dispatch, getState) {
    return serviceFunc(timezone)
      .then(result => {
        dispatch(actionFunc(result.data));
      })
      .catch(error => {
        throw error;
      });
  };
}
