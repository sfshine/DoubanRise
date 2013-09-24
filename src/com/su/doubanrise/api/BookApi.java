package com.su.doubanrise.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONObject;

import android.R.bool;

import com.google.gson.JsonArray;
import com.su.doubanrise.api.bean.Book;
import com.su.doubanrise.api.bean.FavBook;
import com.su.doubanrise.api.bean.Note;
import com.su.doubanrise.util.HttpUtil;
import com.su.doubanrise.util.MLog;

public class BookApi {
	static BookApi bookApi;

	public BookApi() {
	}

	static public BookApi getBookApi() {
		if (bookApi == null) {
			bookApi = new BookApi();
		}
		return bookApi;
	}

	public boolean deleteNote(String note_id) {
		String url = "https://api.douban.com/v2/book/annotation/" + note_id;
		try {

			if (HttpUtil.delete(url)) {
				MLog.e("true");
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;

	}

	// https://api.douban.com/v2/book/:id/annotations
	/**
	 * write a note content 笔记内容 必填，需多于15字 page 页码 页码或章节名选填其一，最多6位正整数 chapter
	 * 章节名 页码或章节名选填其一，最多100字 privacy 隐私设置 选填，值为'private'为设置成仅自己可见，其他默认为公开
	 * 
	 * 
	 */

	public Note writeNote(String book_id, String content, int page,
			String chapter, String privacy) {
		String url = "https://api.douban.com/v2/book/" + book_id
				+ "/annotations";
		HashMap<String, String> map = new HashMap<String, String>();

		map.put("content", content);
		map.put("page", (int) Math.random() * 100 + 1 + "");
		// map.put("page", page);
		// map.put("chapter", chapter);
		map.put("chapter", content.substring(0, content.length() - 10));
		map.put("privacy", privacy);

		String result = HttpUtil.post(url, map);
		try {

			JSONObject jsonObject = new JSONObject(result);
			chapter = jsonObject.optString("chapter");
			String author_name = jsonObject.getJSONObject("author_user")
					.optString("name");
			String abstract_ = jsonObject.optString("abstract");
			String time = jsonObject.optString("time");
			content = jsonObject.optString("content");

			String id = jsonObject.optString("id");
			String page_no = jsonObject.optString("page_no");
			Note note = new Note(chapter, author_name, abstract_, time,
					content, id, page_no);
			note.setContent(content);

			return note;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	/**
	 * get one of a note by id
	 */
	public Note getNote(String id) {
		String url = "https://api.douban.com/v2/book/annotation/" + id;
		String result = HttpUtil.get(url, null);
		try {

			JSONObject jsonObject = new JSONObject(result);
			String chapter = jsonObject.optString("chapter");
			String author_name = jsonObject.getJSONObject("author_user")
					.optString("name");
			String abstract_ = jsonObject.optString("abstract");

			String content = jsonObject.optString("content");
			String time = jsonObject.optString("time");
			String n_id = jsonObject.optString("id");
			String page_no = jsonObject.optString("page_no");
			Note note = new Note(chapter, author_name, abstract_, time,
					content, id, page_no);
			note.setContent(content);

			return note;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;

	}

	/**
	 * add a book to user's collection
	 * 
	 * 
	 * 参数 意义 备注 status 收藏状态 必填（想读：wish 在读：reading 读过：read） tags 收藏标签字符串 选填，用空格分隔
	 * comment 短评文本 选填，最多350字 privacy 隐私设置 选填，值为'private'为设置成仅自己可见，其他默认为公开
	 * rating 星评 选填，数字1～5为合法值，其他信息默认为不评星
	 */
	public FavBook favABook(String id, String status, String comment,
			String privacy) {
		String url = "https://api.douban.com/v2/book/" + id + "/collection";

		HashMap<String, String> map = new HashMap<String, String>();
		map.put("status", status);
		map.put("comment", comment);
		map.put("privacy", privacy);
		map.put("rating", "1");

		String result = HttpUtil.post(url, map);
		try {
			JSONObject jsonObject = new JSONObject(result);

			status = jsonObject.optString("status");
			comment = jsonObject.optString("comment");
			String updated = jsonObject.optString("updated");
			id = jsonObject.optString("id");
			String user_id = jsonObject.optString("user_id");
			String book_id = jsonObject.optString("book_id");
			String tags = "";
			Book book = parseJsonObject(jsonObject.getJSONObject("book"));
			FavBook favBook = new FavBook(status, comment, updated, book, id,
					user_id, book_id, tags);

			return favBook;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * get all notes of a user
	 */
	// https://api.douban.com/v2/book/user/sfshine/annotations
	/**
	 * get all notes of specific book
	 * 
	 * format 返回content字段格式 选填（编辑伪标签格式：text, HTML格式：html），默认为text order 排序
	 * 选填（最新笔记：collect, 按有用程度：rank, 按页码先后：page），默认为rank page 按页码过滤 选填
	 */
	public List<Note> getNotesByUser(String name) {
		String url = "https://api.douban.com/v2/book/user/" + name
				+ "/annotations";
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("format", "html");
		map.put("order", "rank");
		String result = HttpUtil.get(url, map);
		try {
			List<Note> notes = new ArrayList<Note>();
			JSONObject jsonObject = new JSONObject(result);
			JSONArray jsonArray = jsonObject.getJSONArray("annotations");
			for (int i = 0; i < jsonArray.length(); i++) {
				jsonObject = jsonArray.getJSONObject(i);

				String chapter = jsonObject.optString("chapter");
				String author_name = jsonObject.getJSONObject("author_user")
						.optString("name");
				String abstract_ = jsonObject.optString("abstract");
				String time = jsonObject.optString("time");
				String id = jsonObject.optString("id");
				String page_no = jsonObject.optString("page_no");
				Note note = new Note(chapter, author_name, abstract_, time, "",
						id, page_no);
				notes.add(note);
			}

			return notes;

		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	/**
	 * get all notes of specific book
	 * 
	 * format 返回content字段格式 选填（编辑伪标签格式：text, HTML格式：html），默认为text order 排序
	 * 选填（最新笔记：collect, 按有用程度：rank, 按页码先后：page），默认为rank page 按页码过滤 选填
	 */
	public List<Note> getNotes(String book_id) {
		String url = "https://api.douban.com/v2/book/" + book_id
				+ "/annotations";
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("format", "html");
		map.put("order", "rank");
		String result = HttpUtil.get(url, map);
		try {
			List<Note> notes = new ArrayList<Note>();
			JSONObject jsonObject = new JSONObject(result);
			JSONArray jsonArray = jsonObject.getJSONArray("annotations");
			for (int i = 0; i < jsonArray.length(); i++) {
				jsonObject = jsonArray.getJSONObject(i);

				String chapter = jsonObject.optString("chapter");
				String author_name = jsonObject.getJSONObject("author_user")
						.optString("name");
				String abstract_ = jsonObject.optString("abstract");
				String time = jsonObject.optString("time");
				String id = jsonObject.optString("id");
				String page_no = jsonObject.optString("page_no");
				Note note = new Note(chapter, author_name, abstract_, time, "",
						id, page_no);
				notes.add(note);
			}

			return notes;

		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	/**
	 * collected books lists
	 * 
	 * @param name
	 * @return
	 */
	public List<FavBook> getFavBooks(String name) {
		String url = "https://api.douban.com/v2/book/user/" + name
				+ "/collections";
		String result = HttpUtil.get(url, null);

		try {
			JSONObject jsonObject = new JSONObject(result);
			JSONArray jsonArray = jsonObject.getJSONArray("collections");
			List<FavBook> favBooks = new ArrayList<FavBook>();
			for (int i = 0; i < jsonArray.length(); i++) {
				jsonObject = jsonArray.getJSONObject(i);
				String status = jsonObject.optString("status");
				String comment = jsonObject.optString("comment");
				String updated = jsonObject.optString("updated");
				String id = jsonObject.optString("id");
				String user_id = jsonObject.optString("user_id");
				String book_id = jsonObject.optString("book_id");
				String tags = jsonObject.optString("tags");
				Book book = parseJsonObject(jsonObject.getJSONObject("book"));
				FavBook favBook = new FavBook(status, comment, updated, book,
						id, user_id, book_id, tags);
				favBooks.add(favBook);
			}

			return favBooks;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;

	}

	public List<Book> searchAllBook(String word, String start, String count) {
		List<Book> books = new ArrayList<Book>();
		books = searchBook(word, "", 0, 30);
		List<Book> tagbooks = searchBook("", word, 0, 30);
		Book idbook = getBookById(word);
		Book isbnbook = getBookByISBN(word);
		if (tagbooks != null) {
			books.addAll(tagbooks);
		}

		books.add(idbook);
		books.add(isbnbook);

		return books;
	}

	public Book getBookById(String id) {
		String url = "https://api.douban.com/v2/book/" + id;
		String result = HttpUtil.get(url, null);
		Book book = parseJsonStr(result);
		return book;

	}

	public Book getBookByISBN(String isbn) {
		String url = "https://api.douban.com/v2/book/isbn/" + isbn;
		String result = HttpUtil.get(url, null);
		Book book = parseJsonStr(result);
		return book;

	}

	public List<Book> searchBook(String q, String tag, int start, int count) {

		String url = "https://api.douban.com/v2/book/search";
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("q", q);
		map.put("tag", "");
		map.put("start", start + "");
		map.put("count", count + "");
		String result = HttpUtil.get(url, map);
		try {

			JSONObject jsonObject = new JSONObject(result);
			JSONArray jsonArray = jsonObject.getJSONArray("books");
			List<Book> books = new ArrayList<Book>();
			for (int i = 0; i < jsonArray.length(); i++) {
				String sbook = jsonArray.getJSONObject(i).toString();
				Book book = parseJsonStr(sbook);
				if (null != book) {
					books.add(parseJsonStr(sbook));
				}

			}

			return books;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;

	}

	private Book parseJsonStr(String json) {
		try {
			JSONObject jsonObject = new JSONObject(json);
			String id = jsonObject.optString("id");
			String title = jsonObject.optString("title");
			String price = jsonObject.optString("price");
			String pages = jsonObject.optString("pages");
			String publisher = jsonObject.optString("publisher");

			String summary = jsonObject.optString("summary");
			String pubdate = jsonObject.optString("pubdate");
			String image = jsonObject.optString("image");
			String tags = jsonObject.optJSONArray("tags").toString();
			String author = jsonObject.optJSONArray("author").toString();
			float average = 0;
			if (json.contains("average")) {
				average = (float) jsonObject.optJSONObject("rating").optDouble(
						"average");
			}

			String isbn13 = jsonObject.optString("isbn13");

			String author_intro = jsonObject.optString("author_intro");

			Book book = new Book(id, title, author_intro, tags, price, pages,
					publisher, isbn13, author, summary, pubdate, image, average);
			return book;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private Book parseJsonObject(JSONObject jsonObject) {
		try {

			String id = jsonObject.optString("id");
			String title = jsonObject.optString("title");
			String price = jsonObject.optString("price");
			String pages = jsonObject.optString("pages");
			String publisher = jsonObject.optString("publisher");

			// String summary = jsonObject.optString("summary");
			String pubdate = jsonObject.optString("pubdate");
			String image = jsonObject.optString("image");
			String tags = jsonObject.getJSONArray("tags").toString();
			String author = jsonObject.getJSONArray("author").toString();

			// String average = jsonObject.getJSONObject("rating").optString(
			// "average");
			String isbn13 = jsonObject.optString("isbn13");

			String author_intro = jsonObject.optString("author_intro");

			Book book = new Book(id, title, author_intro, tags, price, pages,
					publisher, isbn13, author, "", pubdate, image, 0);
			return book;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * get one of collected books by book id, the books must be one of the
	 * user's cllected books
	 */
	public FavBook getFavBookById(String book_id) {
		String url = "https://api.douban.com/v2/book/" + book_id
				+ "/collection";
		String result = HttpUtil.get(url, null);
		try {
			JSONObject jsonObject = new JSONObject(result);

			String status = jsonObject.optString("status");
			String comment = jsonObject.optString("comment");
			String updated = jsonObject.optString("updated");
			String id = jsonObject.optString("id");
			String user_id = jsonObject.optString("user_id");
			String tags = jsonObject.optString("tags");
			Book book = parseJsonObject(jsonObject.getJSONObject("book"));
			FavBook favBook = new FavBook(status, comment, updated, book, id,
					user_id, book_id, tags);

			return favBook;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;

	}
}
