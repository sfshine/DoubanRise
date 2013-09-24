package com.su.doubanrise.test;

import android.app.Activity;
import android.content.Context;
import android.test.AndroidTestCase;

import com.su.doubanrise.api.BookApi;
import com.su.doubanrise.api.Douban;
import com.su.doubanrise.api.TmsgApi;
import com.su.doubanrise.api.UserApi;
import com.su.doubanrise.util.DataStoreUtil;
import com.su.doubanrise.websitebiz.BookService;

public class testDoubanBook extends AndroidTestCase {
	Context context = getContext();
	Douban douban = new Douban((Activity) this.getContext(),
			"73f27e95b2a4a61d5bd77bc117edf257");

	public void testsearchAllBook() {
		// douban.searchAllBook("100252535", 0, 30);

	}

	public void testUserApi() {

		UserApi userApi = new UserApi();
		userApi.getMyinfo();
		// userApi.getUserByName("sfshine");
		// userApi.searchUser("shine", 0, 30);

	}

	public void testdeletenote() {
		douban.deleteNote("20383605");

	}

	public void testAddTmsg() {

		douban.addNewTmsg("对于豆瓣这种只要艺术不要技术的公司 还是死掉算了把。。");

	}

	public void testGetBooks() throws Exception {
		BookService.getDoubanNewBooks();
	}

	//
	// public void testGetBookByid() throws Exception {
	// BookApi bookApi = new BookApi();
	// bookApi.getBookById("1002256");
	// }
	//
	// public void testSearchBooks() {
	// BookApi bookApi = new BookApi();
	// bookApi.searchBook("如果", 0, 30);
	// }
	//
	// public void testGetFavBook() throws Exception {
	// BookApi bookApi = new BookApi();
	// bookApi.getFavBooks("sfshine");
	// }
	//
	public void testGetNotes() throws Exception {
		BookApi bookApi = new BookApi();
		bookApi.getNotes("20383605");
	}

	//
	public void testGetNote() throws Exception {

		douban.getNote("23200501");
	}

	public void writeNote() {
		douban.writeNote("1002256", "交谈中请勿轻信汇款、中奖信息、陌生电话，勿使用外挂软件。", 1, "",
				"test");
	}

	public void getNotesByUser() throws Exception {
		douban.getNotesByUser("sfshine");
		// bookApi.getNotesByUser("sfshine");
	}

	public void testfavABook() throws Exception {
		douban.favABook("20172275", "wish", "private", "private");
	}

	public void testGetFavBookById() throws Exception {
		BookApi bookApi = new BookApi();
		bookApi.getFavBookById("20389192");
	}

}
