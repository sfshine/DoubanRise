package com.su.doubanrise.api.bean;

import java.io.Serializable;

public class Movie implements Serializable {
	String id;
	float rating;
	String image;
	String title;
	String author;
	String summary;
	String language;
	String pubdate;
	String country;
	String cast;
	String movie_duration;
	String year;
	String movie_type;

	public Movie(String id, float rating, String image, String title,
			String author, String summary, String language, String pubdate,
			String country, String cast, String movie_duration, String year,
			String movie_type) {
		super();
		this.id = id;
		this.rating = rating;
		this.image = image;
		this.title = title;
		this.author = author;
		this.summary = summary;
		this.language = language;
		this.pubdate = pubdate;
		this.country = country;
		this.cast = cast;
		this.movie_duration = movie_duration;
		this.year = year;
		this.movie_type = movie_type;
	}

	@Override
	public String toString() {
		return "Movie [id=" + id + ", rating=" + rating + ", image=" + image
				+ ", title=" + title + ", author=" + author + ", summary="
				+ summary + ", language=" + language + ", pubdate=" + pubdate
				+ ", country=" + country + ", cast=" + cast
				+ ", movie_duration=" + movie_duration + ", year=" + year
				+ ", movie_type=" + movie_type + "]";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public float getRating() {
		return rating;
	}

	public void setRating(float rating) {
		this.rating = rating;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getPubdate() {
		return pubdate;
	}

	public void setPubdate(String pubdate) {
		this.pubdate = pubdate;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCast() {
		return cast;
	}

	public void setCast(String cast) {
		this.cast = cast;
	}

	public String getMovie_duration() {
		return movie_duration;
	}

	public void setMovie_duration(String movie_duration) {
		this.movie_duration = movie_duration;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMovie_type() {
		return movie_type;
	}

	public void setMovie_type(String movie_type) {
		this.movie_type = movie_type;
	}

}
