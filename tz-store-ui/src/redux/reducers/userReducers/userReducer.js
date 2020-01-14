import { userActions } from "../../../utilities/constants";
import { produce } from "immer";

export default function userReducers(state = [], action) {
  switch (action.type) {
    case userActions.USERS_LOADED_SUCCESSFULLY:
      state = [];
      return produce(state, draftState => {
        action.users.forEach(element => {
          draftState.push(element);
        });
      });
    case userActions.USER_DELETED_SUCCESSFULLY:
      return produce(state, draftState => {
        draftState.splice(
          draftState.findIndex(user => user.username === action.userId),
          1
        );
      });
    case userActions.USER_CREATED_SUCCESSFULLY:
      return produce(state, draftState => {
        draftState.push(action.user);
      });
    case userActions.USER_UPDATED_SUCCESSFULLY:
      return produce(state, draft => {
        draft[draft.findIndex(user => user.username === action.user.username)] =
          action.user;
      });
    default:
      return state;
  }
}
