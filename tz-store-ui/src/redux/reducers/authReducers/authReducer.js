import { authActions, userActions } from "../../../utilities/constants";
import produce from "immer";

export default function userReducer(
  state = { registeredUser: {}, users: [], loggeIdUser: {} },
  action
) {
  switch (action.type) {
    case authActions.REGISTER_USER_SUCCESS:
      return produce(state, draftState => {
        draftState.registeredUser = action.registeredUser;
      });
    case authActions.LOAD_USER:
      return produce(state, draftState => {
        draftState.loggeIdUser = action.user;
      });
    case userActions.USER_PROFILE_PICTURE_UPLOAD_SUCCESS:
      return produce(state, draftState => {
        draftState.loggeIdUser.profilePictureUrl = action.url;
      });
    case userActions.USER_LOGGED_OUT_SUCCESSFULLY:
      return produce(state, draft => {
        draft.loggeIdUser = action.loggedInUser;
      });
    case userActions.LOGGED_IN_USER_UPDATED:
      return produce(state, draft => {
        draft.loggeIdUser = action.user;
      });
    default:
      return state;
  }
}
