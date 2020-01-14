class Timezone {
  constructor(username, name, city, differenceFromGMT, timeZoneRegion) {
    this.username = username ? username : "";
    this.name = name ? name : "";
    this.city = city ? city : "";
    this.differenceFromGMT = differenceFromGMT ? differenceFromGMT : "";
    this.timeZoneRegion = timeZoneRegion ? timeZoneRegion : "";
  }
}

export default Timezone;
