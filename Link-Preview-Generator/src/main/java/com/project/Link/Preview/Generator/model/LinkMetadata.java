package com.project.Link.Preview.Generator.model;

import java.time.LocalDateTime;

public class LinkMetadata {
	
	private String url;
	private String title;
	private String description;
	private String imageUrl;
	private LocalDateTime lastFetched;
	
	public LinkMetadata() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public LinkMetadata(String url, String title, String description, String imageUrl, LocalDateTime lastFetched) {
		super();
		this.url = url;
		this.title = title;
		this.description = description;
		this.imageUrl = imageUrl;
		this.lastFetched = lastFetched;
	}


	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public LocalDateTime getLastFetched() {
		return lastFetched;
	}
	public void setLastFetched(LocalDateTime lastFetched) {
		this.lastFetched = lastFetched;
	}

}
