package com.su.doubanrise.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.su.doubanrise.api.bean.Book;
import com.su.doubanrise.api.bean.DNote;
import com.su.doubanrise.api.bean.FavBook;
import com.su.doubanrise.api.bean.Mail;
import com.su.doubanrise.api.bean.Movie;
import com.su.doubanrise.api.bean.Music;
import com.su.doubanrise.api.bean.Note;
import com.su.doubanrise.api.bean.SendMail;
import com.su.doubanrise.api.bean.User;
import com.su.doubanrise.util.HttpUtil;
import com.su.doubanrise.util.MLog;

import android.R.integer;
import android.content.Context;

/**
 * 这是一个代理类 负责api的所有调度
 * 
 * @author sfshine
 * 
 */
public class Douban {
	static Douban douban;
	static String AUTHURL = "https://www.douban.com/service/auth2/auth?client_id=";
	static String TOKENURL = "https://www.douban.com/service/auth2/token";
	public static String APIKEY = "0d46a6de1288ffe627d3f49a379affb2";// 你的key
	static String SECRET = "1561455f95367475";// 你的secret
	static String REDIRECT_URI = "http://shine.sinaapp.com";// 你的回调地址
	static private String ACCESSTOKEN = "";
	private Context context;

	public Douban(Context context, String access_token) {
		super();
		ACCESSTOKEN = access_token;
		this.context = context;
	}

	public static Douban getDouban(Context context, String access_token) {
		if (douban == null) {
			douban = new Douban(context, access_token);
			HttpUtil.initAfterAuth();
		}
		return douban;

	}

	public static Douban getDouban() {
		return douban;
	}

	static public String getAccessToken() {
		return ACCESSTOKEN;
	}

	/**
	 * MusicApi
	 */
	public Music getMusicById(String music_id) {
		return MusicApi.getMusicApi().getMusicById(music_id);

	}

	public List<Music> searchAllMusic(String word, String start, String count) {
		return MusicApi.getMusicApi().searchAllMusic(word, start, count);

	}

	/**
	 * 日志Api
	 * 
	 * @return
	 */
	public String deleteDNote(DNote dNote) {
		return DNoteApi.getDNoteApi().deleteDNote(dNote);

	}

	public String modDNote(DNote dNote) {
		return DNoteApi.getDNoteApi().modDNote(dNote);

	}

	public String writeDNote(DNote dNote) {
		return DNoteApi.getDNoteApi().writeDNote(dNote);

	}

	public List<DNote> getDNotes(String id) {

		return DNoteApi.getDNoteApi().getDNotes(id, "text");

	}

	/**
	 * 邮件Api
	 */

	public List<User> getUserList() {
		return MailApi.getMailApi().getUserList();

	}

	public String sendMail(SendMail sendMail) {
		return MailApi.getMailApi().sendMail(sendMail);
	}

	public List<Mail> getInbox() {

		return MailApi.getMailApi().getInbox();

	}

	public List<Mail> getOutbox() {

		return MailApi.getMailApi().getOutbox();

	}

	public List<Mail> getUnread() {

		return MailApi.getMailApi().getUnread();

	}

	/**
	 * moive 操作
	 */

	public List<Movie> searchAllMovie(String word, String start, String count) {
		return MovieApi.getMovieApi().searchAllMovie(word, start, count);

	}

	public Movie getMovieById(String movie_id) {
		return MovieApi.getMovieApi().getMovieById(movie_id);

	}

	public Movie getMoiveByImdb(String imdb) {
		return MovieApi.getMovieApi().getMoiveByImdb(imdb);

	}

	/**
	 * Book操作
	 */

	public Note writeNote(String book_id, String content, int page,
			String chapter, String privacy) {

		return BookApi.getBookApi().writeNote(book_id, content, page, chapter,
				privacy);

	}

	public FavBook favABook(String id, String status, String comment,
			String privacy) {

		return BookApi.getBookApi().favABook(id, status, comment, privacy);

	}

	public boolean deleteNote(String note_id) {
		return BookApi.getBookApi().deleteNote(note_id);

	}

	/**
	 * get one of a note by id
	 */

	public Note getNote(String id) {

		return BookApi.getBookApi().getNote(id);

	}

	public List<Note> getNotesByUser(String name) {

		return BookApi.getBookApi().getNotesByUser(name);
	}

	public List<Note> getNotes(String book_id) {

		return BookApi.getBookApi().getNotes(book_id);
	}

	public List<FavBook> getFavBooks(String name) {

		return BookApi.getBookApi().getFavBooks(name);

	}

	public Book getBookById(String id) {

		return BookApi.getBookApi().getBookById(id);

	}

	public Book getBookByISBN(String isbn) {

		return BookApi.getBookApi().getBookByISBN(isbn);

	}

	public List<Book> searchBook(String q, int start, int count) {
		return BookApi.getBookApi().searchBook(q, "", start, count);
	}

	public List<Book> searchAllBook(String word, String start, String count) {
		return BookApi.getBookApi().searchAllBook(word, start, count);
	}

	/**
	 * User 操作
	 * 
	 * @return
	 */

	public User getMyinfo() {
		return new UserApi().getMyinfo();
	}

	public User getUserByName(String name) {
		return new UserApi().getUserByName(name);
	}

	public List<User> searchUser(String q, int start, int count) {
		return new UserApi().searchUser(q, start, count);
	}

	/**
	 * 广播操作
	 */
	public boolean addNewTmsg(String tmsg) {
		return new TmsgApi().addNewTmsg(tmsg);
	}

	public boolean addNewTmsg(String tmsg, String imgs, String rec_title,
			String rec_url, String rec_desc) {
		return new TmsgApi().addNewTmsg(tmsg, imgs, rec_title, rec_url,
				rec_desc);

	}

}
