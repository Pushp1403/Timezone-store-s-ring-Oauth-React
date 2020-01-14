import { combineReducers } from "redux";
import authReducer from "./authReducers/authReducer";
import timezoneReducer from "./timezoneReducers/timezoneReducer";
import userReducers from "./userReducers/userReducer";

const rootReducer = combineReducers({
  authReducer,
  timezoneReducer,
  userReducers
});

export default rootReducer;
