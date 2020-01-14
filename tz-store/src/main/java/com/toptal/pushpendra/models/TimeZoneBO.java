package com.toptal.pushpendra.models;

public class TimeZoneBO extends BaseModelDetail {
	
	private Integer timeZoneId;
	private String username;
	private String name;
	private String city;
	private Float differenceFromGMT;
	private String timeZoneRegion;
	
	public String getTimeZoneRegion() {
		return timeZoneRegion;
	}
	public void setTimeZoneRegion(String timeZoneRegion) {
		this.timeZoneRegion = timeZoneRegion;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	
	
	

}
