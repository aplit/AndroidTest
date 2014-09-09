package com.kistaffing.androidtest.models;

public class Movie {
	private long id;
	private String title;
	private String year;
	private String runtime;
	private String imageUrl;

	public Movie(long id, String title, String year, String runtime, String imageUrl) {
		this.id = id;
		this.title = title;
		this.year = year;
		this.runtime = runtime;
		this.imageUrl = imageUrl;
	}

	public long getId() {
		return this.id;
	}

	public String getTitle() {
		return this.title;
	}

	public String getYear() {
		return this.year;
	}

	public String getRuntime() {
		return this.runtime;
	}

	public String getImageUrl() {
		return this.imageUrl;
	}
}

