package com.toptal.pushpendra.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class TimeZone extends BaseEntity implements Serializable{
	

	private static final long serialVersionUID = 1555781376037298611L;

	@Id
	@GeneratedValue
	private int timeZoneId;
	
	@Column
	private String username;
	
	@Column
	private String name;
	
	@Column
	private String city;
	
	@Column
	private float differenceFromGMT;
	
	@Column
	private String timeZoneRegion;
	

	public String getTimeZoneRegion() {
		return timeZoneRegion;
	}

	public void setTimeZoneRegion(String timeZoneRegion) {
		this.timeZoneRegion = timeZoneRegion;
	}

	public int getTimeZoneId() {
		return timeZoneId;
	}

	public void setTimeZoneId(int timeZoneId) {
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

	public float getDifferenceFromGMT() {
		return differenceFromGMT;
	}

	public void setDifferenceFromGMT(float differenceFromGMT) {
		this.differenceFromGMT = differenceFromGMT;
	}
	

}
