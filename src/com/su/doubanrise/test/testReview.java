package com.su.doubanrise.test;

import junit.framework.TestCase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.su.doubanrise.util.HttpUtil;
import com.su.doubanrise.websitebiz.MovieService;

public class testReview extends TestCase {
	void testgetReviewContentAndComments() {

	}

	public void getDoubanNewMovies() throws Exception {
		MovieService.getDoubanNewMovies();
	}

}
