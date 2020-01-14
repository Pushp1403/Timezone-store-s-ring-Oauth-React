import axios from "axios";

export default axios.create({
  baseURL: "http://localhost:8080/",
  responseType: "json",
  headers: localStorage.getItem("TOKEN")
    ? { Authorization: "Bearer " + localStorage.getItem("TOKEN") }
    : {}
});
