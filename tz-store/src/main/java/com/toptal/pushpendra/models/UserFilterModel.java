package com.toptal.pushpendra.models;

import java.util.Map;

import com.toptal.pushpendra.utilities.SortingDirection;

public class UserFilterModel extends PagingModel {

	private String username;
	private String firstName;
	private String lastName;
	private String emailId;

	public UserFilterModel(Map<String, String> requestParams) {
		super(requestParams.getOrDefault("pageSize", "20"), requestParams.getOrDefault("nextPage", "0"),
				requestParams.getOrDefault("sortingDirection", SortingDirection.ASC.getValue()),
				requestParams.get("sortBy"));
		this.username = requestParams.get("username");
		this.firstName = requestParams.get("firstName");
		this.lastName = requestParams.get("lastName");
		this.emailId = requestParams.get("emailId");
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

}
