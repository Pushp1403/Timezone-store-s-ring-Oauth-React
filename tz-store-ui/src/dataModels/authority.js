class Authority {
  constructor(authority, username) {
    this.username = username ? username : "";
    this.role = authority ? authority : "";
  }
}

export default Authority;
