import PagingModel from "./pagingModel";

class UserFilterModel extends PagingModel {
  constructor(username, firstName, lastName, emailId, nextPage, pageSize) {
    super(nextPage, pageSize);
    this.username = username;
    this.firstName = firstName;
    this.lastName = lastName;
    this.emailId = emailId;
  }
}

export default UserFilterModel;
