package com.toptal.pushpendra.models;

public class PagingModel {

	private int pageSize = 20;
	private int nextPage = 0;
	private String sortingDirection = "ASC";
	public String sortBy;

	public PagingModel(String pageSize, String nextPage, String sortingDirection, String sortBy) {
		super();
		this.pageSize = Integer.parseInt(pageSize);
		this.nextPage = Integer.parseInt(nextPage);
		this.sortingDirection = sortingDirection;
		this.sortBy = sortBy;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getNextPage() {
		return nextPage;
	}

	public void setNextPage(int currentPage) {
		this.nextPage = currentPage;
	}

	public String getSortingDirection() {
		return sortingDirection;
	}

	public void setSortingDirection(String sortingDirection) {
		this.sortingDirection = sortingDirection;
	}

	public String getSortBy() {
		return sortBy;
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

}
