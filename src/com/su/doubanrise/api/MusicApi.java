package com.su.doubanrise.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.su.doubanrise.api.bean.Movie;
import com.su.doubanrise.api.bean.Music;
import com.su.doubanrise.api.bean.Music;
import com.su.doubanrise.util.HttpUtil;
import com.su.doubanrise.util.MLog;

public class MusicApi {

	static MusicApi musicApi;

	public MusicApi() {
	}

	static public MusicApi getMusicApi() {
		if (musicApi == null) {
			musicApi = new MusicApi();
		}
		return musicApi;
	}

	public List<Music> searchAllMusic(String word, String start, String count) {
		List<Music> movies = new ArrayList<Music>();
		movies = searchMusic(word, "", start, count);
		List<Music> tagmovies = searchMusic("", word, start, count);

		if (tagmovies != null) {
			movies.addAll(tagmovies);
		}
		// Music idmusic = getMusicById(word);
		// if (idmusic != null) {
		// movies.add(idmusic);
		// }
		return movies;
	}

	public List<Music> searchMusic(String word, String tag, String start,
			String count) {
		String url = "https://api.douban.com/v2/music/search";
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("q", word);
		map.put("tag", tag);
		map.put("start", start);
		map.put("count", count);
		String result = HttpUtil.get(url, map);
		try {

			JSONObject jsonObject = new JSONObject(result);
			JSONArray jsonArray = jsonObject.getJSONArray("musics");
			List<Music> musics = new ArrayList<Music>();
			for (int i = 0; i < jsonArray.length(); i++) {
				String smovie = jsonArray.getJSONObject(i).toString();
				MLog.e(smovie);
				Music movie = parseMusic(smovie);
				if (null != movie) {
					musics.add(movie);
				}
			}
			for (Music music : musics) {
				MLog.e(music.getTitle());
			}

			return musics;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	public Music getMusicById(String music_id) {

		// {
		// "id":10000037,
		// "title":"我只在乎你",
		// "alt":"http:\/\/music.douban.com\/music\/10000037",
		// "author":[{"name":"邓丽君"}],
		// "alt_title":"留聲經典復刻版",
		// "tags":[
		// {"count":20,"name":"经典"},
		// {"count":20,"name":"邓丽君"}
		// ],
		// "summary":
		// "邓丽君在1987年推出的唱片专集《我只在乎你》中，有三首歌的词作者是“桃丽莎”。其实，桃丽莎即是邓丽君自己（英文名TERESA的中译）。根据我手中的资料，邓丽君作的词并不多，虽然她确曾向媒体表示“最大的心愿是出一张一脚踢的唱片”——即由自己包办下全部的词曲和制作，但是因意外去世而没能实现。但是，在此专集中竟有三首之多，不能不令人关注。大体上说，这三首歌具有两种风格，一为写实，一为浪漫。《非龙非彲》以现代汉语与古汉语混合，歌词的意境悲凉，心态哀痛，而且隐含着非比寻常的寓意，笔者愿在此写出来就教于方家。",
		// "image":"http:\/\/img1.douban.com\/spic\/s11185741.jpg",
		// "mobile_link":"http:\/\/m.douban.com\/music\/subject\/10000037\/",
		// "attrs":{
		// "publisher":["环球"],
		// "singer":["邓丽君"],
		// "discs":["1"],
		// "pubdate":["1987-01-02"],
		// "title":["我只在乎你"],
		// "media":["CD"],
		// "tracks":["01. 酒醉的探戈\n02. 像故事般温柔\n03. 命运之川\n04. 爱人\n05. 午夜微风\n06. 夏日圣诞\n07. 非龙非彲\n08. 不着痕迹\n09. 心路过黄昏\n10. 我只在乎你"],
		// "version":["专辑"]
		// },
		// rating":{"max":10,"average":"0.0","numRaters":20,"min":0},
		// }
		String url = "https://api.douban.com/v2/music/" + music_id;
		String result = HttpUtil.get(url, null);

		return parseMusic(result);

	}

	private Music parseMusic(String result) {
		try {

			JSONObject jsonObject = new JSONObject(result);
			String id = jsonObject.optString("id");
			String title = jsonObject.optString("title");
			JSONArray arrtauhtor = jsonObject.optJSONArray("author");
			String author = "演唱：";
			for (int i = 0; i < arrtauhtor.length(); i++) {
				author += arrtauhtor.getJSONObject(i).getString("name") + ",";
			}

			String tags = "标签：";
			JSONArray tagsarr = jsonObject.optJSONArray("tags");
			for (int i = 0; i < tagsarr.length(); i++) {
				tags += tagsarr.optJSONObject(i).optString("name");
			}
			String summary = jsonObject.optString("summary");
			String image = jsonObject.optString("image");
			String publisher = jsonObject.optString("publisher");
			String singer = trimothers(jsonObject.optString("singer"));
			String pubdate = trimothers(jsonObject.optString("pubdate"));

			String tracks = trimothers(jsonObject.optString("tracks"));
			float rating = (float) jsonObject.optJSONObject("rating")
					.optDouble("average") / 2;
			Music music = new Music(id, title, author, tags, summary, image,
					publisher, singer, pubdate, tracks, rating);

			return music;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	String trimothers(String str) {
		str = str.replaceAll("\\[", "");
		str = str.replaceAll("\\]", "");
		str = str.replaceAll("\"", "");
		return str;

	}
}
