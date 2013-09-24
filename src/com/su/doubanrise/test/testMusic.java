package com.su.doubanrise.test;

import android.test.AndroidTestCase;

import com.su.doubanrise.api.MusicApi;
import com.su.doubanrise.websitebiz.MusicService;

public class testMusic extends AndroidTestCase {
	// public void testgetMusicById() {
	// new MusicApi().searchAllMusic("红尘客栈", "0", "10");
	// }

	public void testGetAlbums() throws Exception {
		MusicService.getDoubanNewAlbums();
	}

//	public void testgetAlbumById() throws Exception {
//		MusicService.getAlbumById("20434006");
//	}
}
