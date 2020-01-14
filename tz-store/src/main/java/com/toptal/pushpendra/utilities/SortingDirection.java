package com.toptal.pushpendra.utilities;

public enum SortingDirection {	
	ASC ("ASC"),
	DESC ("DESC")
	;
	
	private final String direction;
	
	SortingDirection(String direction){
		this.direction = direction;
	}
	
	public String getValue() {
		return this.direction;
	}
	
}
