import { timezoneActions } from "../../../utilities/constants";
import { produce } from "immer";

export default function timezoneReducer(state = [], action) {
  switch (action.type) {
    case timezoneActions.TIMEZONES_LOAD_SUCCESS:
      state = [];
      return produce(state, draftState => {
        action.timezones.forEach(tz => {
          draftState.push(tz);
        });
      });
    case timezoneActions.TIMEZONE_CREATED_SUCCESS:
      return produce(state, draftState => {
        draftState.push(action.timezone);
      });
    case timezoneActions.TIMEZONE_DELETED_SUCCESS:
      return produce(state, draftState => {
        draftState.splice(
          draftState.findIndex(tz => tz.timeZoneId === action.timezoneId),
          1
        );
      });
    case timezoneActions.TIMEZONE_UPDATED_SUCCESS:
      return produce(state, draftState => {
        let update =
          draftState[
            draftState.findIndex(
              tz => tz.timeZoneId === action.timezone.timeZoneId
            )
          ];
        update.name = action.timezone.name;
        update.city = action.timezone.city;
        update.differenceFromGMT = action.timezone.differenceFromGMT;
        update.timeZoneRegion = action.timezone.timeZoneRegion;
        draftState[
          draftState.findIndex(
            tz => tz.timeZoneId === action.timezone.timeZoneId
          )
        ] = update;
      });
    default:
      return state;
  }
}
