package com.su.doubanrise.api.bean;

import java.io.Serializable;

public class Book implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String id;
	String title;
	String author_intro;
	String tags;

	String price;

	String pages;
	String publisher;

	String isbn13;
	String author;
	String summary;
	String pubdate;
	String image;
	float average;

	public Book(String id, String title, String author_intro, String tags,
			String price, String pages, String publisher, String isbn13,
			String author, String summary, String pubdate, String image,
			float average) {
		super();
		this.id = id;
		this.title = title;
		this.author_intro = author_intro;
		this.tags = tags;
		this.price = price;
		this.pages = pages;
		this.publisher = publisher;
		this.isbn13 = isbn13;
		this.author = author;
		this.summary = summary;
		this.pubdate = pubdate;
		this.image = image;
		this.average = average;
	}

	public float getAverage() {
		return average;
	}

	public void setAverage(float average) {
		this.average = average;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor_intro() {
		return author_intro;
	}

	public void setAuthor_intro(String author_intro) {
		this.author_intro = author_intro;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getPages() {
		return pages;
	}

	public void setPages(String pages) {
		this.pages = pages;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getIsbn13() {
		return isbn13;
	}

	public void setIsbn13(String isbn13) {
		this.isbn13 = isbn13;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getPubdate() {
		return pubdate;
	}

	public void setPubdate(String pubdate) {
		this.pubdate = pubdate;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

}
