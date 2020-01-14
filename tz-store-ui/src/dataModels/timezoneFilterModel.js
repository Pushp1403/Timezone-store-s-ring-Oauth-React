import PagingModel from "./pagingModel";

class TimezoneFilterModel extends PagingModel {
  constructor(
    timeZoneId,
    username,
    city,
    name,
    differenceFromGMT,
    nextPage,
    pageSize
  ) {
    super(nextPage, pageSize);
    this.timeZoneId = timeZoneId;
    this.username = username;
    this.city = city;
    this.name = name;
    this.differenceFromGMT = differenceFromGMT;
  }
}

export default TimezoneFilterModel;
