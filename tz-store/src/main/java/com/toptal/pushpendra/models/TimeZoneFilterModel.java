package com.toptal.pushpendra.models;

import java.util.Map;

import com.toptal.pushpendra.utilities.SortingDirection;

public class TimeZoneFilterModel extends PagingModel {

	private Integer timeZoneId;
	private String username;
	private String city;
	private String name;
	private Float differenceFromGMT;
	private UserFilterModel userFilterModel;

	public TimeZoneFilterModel(String pageSize, String nextPage, String sortingDirection, String sortBy,
			Integer timeZoneId, String username, String city, Float differenceFromGMT, UserFilterModel userFilterModel,
			String name) {
		super(pageSize, nextPage, sortingDirection, sortBy);
		this.timeZoneId = timeZoneId;
		this.username = username;
		this.city = city;
		this.differenceFromGMT = differenceFromGMT;
		this.userFilterModel = userFilterModel;
		this.name = name;
	}

	public TimeZoneFilterModel(Map<String, String> requestParams) {
		super(requestParams.getOrDefault("pageSize", "20"), requestParams.getOrDefault("nextPage", "0"),
				requestParams.getOrDefault("sortingDirection", SortingDirection.ASC.getValue()),
				requestParams.get("sortBy"));
		this.timeZoneId = requestParams.getOrDefault("timeZoneId", "-1").equals("-1") ? null
				: Integer.parseInt(requestParams.get("timeZoneId"));
		this.username = requestParams.get("username");
		this.city = requestParams.get("city");
		this.differenceFromGMT = requestParams.getOrDefault("differenceFromGMT", "-1").equals("-1") ? null
				: Float.parseFloat(requestParams.get("differenceFromGMT"));
		this.name = requestParams.get("name");
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getTimeZoneId() {
		return timeZoneId;
	}

	public void setTimeZoneId(Integer timeZoneId) {
		this.timeZoneId = timeZoneId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Float getDifferenceFromGMT() {
		return differenceFromGMT;
	}

	public void setDifferenceFromGMT(Float differenceFromGMT) {
		this.differenceFromGMT = differenceFromGMT;
	}

	public UserFilterModel getUserFilterModel() {
		return userFilterModel;
	}

	public void setUserFilterModel(UserFilterModel userFilterModel) {
		this.userFilterModel = userFilterModel;
	}

}
