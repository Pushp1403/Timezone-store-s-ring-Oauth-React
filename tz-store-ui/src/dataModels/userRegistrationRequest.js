import LoginRequest from "./loginRequest";
import Authority from "./authority";

export default class UserRegistrationRequest extends LoginRequest {
  constructor(
    username,
    password,
    confirmPassword,
    firstName,
    lastName,
    emailId,
    authority
  ) {
    super("", "");
    this.firstName = "";
    this.lastName = "";
    this.emailId = "";
    this.authority = "USER";
    this.confirmPassword = "";
    this.provider = "";
    this.authorities = [new Authority()];
  }
}
