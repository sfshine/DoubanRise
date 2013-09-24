package com.su.doubanrise.test;

import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.test.AndroidTestCase;
import android.test.mock.MockApplication;

import com.su.doubanrise.api.BookApi;
import com.su.doubanrise.api.Douban;
import com.su.doubanrise.api.MovieApi;
import com.su.doubanrise.api.TmsgApi;
import com.su.doubanrise.api.UserApi;
import com.su.doubanrise.util.DataStoreUtil;
import com.su.doubanrise.util.MLog;
import com.su.doubanrise.websitebiz.BookService;
import com.su.doubanrise.websitebiz.MovieService;

public class testDoubanMovie extends AndroidTestCase {

	// public void testgetMovieReview() {
	// MovieService.getCinemaInfo("10540066", "2012-12-22");
	//
	// }

	// public void testgetMovieShotReview() {
	// MovieService.getShortReview("10540066", "2012-12-22");
	//
	// }

	public void testsearchMovie() {
		new MovieApi().searchAllMovie("2012", 0 + "", 10 + "");

	}

}
