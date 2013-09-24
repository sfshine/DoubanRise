package com.su.doubanrise.api.bean;

public class FavBook {

	String status;
	String comment;
	String updated;
	Book book;
	String id;
	String user_id;
	String book_id;
	String tags;

	@Override
	public String toString() {
		return "FavBook [status=" + status + ", comment=" + comment
				+ ", updated=" + updated + ", book=" + book + ", id=" + id
				+ ", user_id=" + user_id + ", book_id=" + book_id + ", tags="
				+ tags + "]";
	}

	public FavBook(String status, String comment, String updated, Book book,
			String id, String user_id, String book_id, String tags) {
		super();
		this.status = status;
		this.comment = comment;
		this.updated = updated;
		this.book = book;
		this.id = id;
		this.user_id = user_id;
		this.book_id = book_id;
		this.tags = tags;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getUpdated() {
		return updated;
	}

	public void setUpdated(String updated) {
		this.updated = updated;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getBook_id() {
		return book_id;
	}

	public void setBook_id(String book_id) {
		this.book_id = book_id;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

}
