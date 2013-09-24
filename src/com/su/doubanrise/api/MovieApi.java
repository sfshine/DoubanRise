package com.su.doubanrise.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.nfc.tech.IsoDep;

import com.su.doubanrise.api.bean.Movie;
import com.su.doubanrise.api.bean.Movie;
import com.su.doubanrise.util.HttpUtil;
import com.su.doubanrise.util.MLog;

public class MovieApi {
	static MovieApi movieApi;

	public MovieApi() {
	}

	static public MovieApi getMovieApi() {
		if (movieApi == null) {
			movieApi = new MovieApi();
		}
		return movieApi;
	}

	public List<Movie> searchAllMovie(String word, String start, String count) {
		List<Movie> movies = new ArrayList<Movie>();
		movies = searchMovie(word, "", start, count);
		List<Movie> tagmovies = searchMovie("", word, start, count);
		// Movie idbook = getMovieById(word);
		// movies.add(idbook);
		Movie isbnbook = getMoiveByImdb(word);
		if (isbnbook != null && null != isbnbook.getTitle()) {
			movies.add(isbnbook);
		}

		if (tagmovies != null) {
			movies.addAll(tagmovies);
		}

		return movies;
	}

	public List<Movie> searchMovie(String word, String tag, String start,
			String count) {
		String url = "https://api.douban.com/v2/movie/search";
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("q", word);
		map.put("tag", "");
		map.put("start", start);
		map.put("count", count);
		String result = HttpUtil.get(url, map);
		try {

			JSONObject jsonObject = new JSONObject(result);
			JSONArray jsonArray = jsonObject.getJSONArray("movies");
			List<Movie> movies = new ArrayList<Movie>();
			for (int i = 0; i < jsonArray.length(); i++) {
				String smovie = jsonArray.getJSONObject(i).toString();
				MLog.e(smovie);
				Movie movie = parseMovie(smovie);
				if (null != movie) {
					movies.add(movie);
				}
			}

			return movies;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;

	}

	public Movie getMoiveByImdb(String imdb) {
		String url = "https://api.douban.com/v2/movie/imdb/" + imdb;
		String result = HttpUtil.get(url, null);
		return parseMovie(result);

	}

	public Movie getMovieById(String movie_id) {

		// {
		// "rating":{"max":10,"average":"0.0","numRaters":20,"min":0},
		// "author":[{"name":"陈凯歌 Kaige Chen"}],
		// "alt_title":"霸王别姬 \/ 再见，我的妾",
		// "image":"http:\/\/img1.douban.com\/spic\/s11317185.jpg",
		// "title":"霸王别姬",
		// "mobile_link":"http:\/\/m.douban.com\/movie\/subject\/10000002\/",
		// "summary":"段小楼（张丰毅）与程蝶衣（张国荣）是一对打小一起长大的师兄弟，两人一个演生，一个饰旦，一向配合天衣无缝，尤其一出《霸王别姬》，更是誉满京城，为此，两人约定合演一辈子《霸王别姬》。但两人对戏剧与人生关系的理解有本质不同，段小楼深知戏非人生，程蝶衣则是人戏不分。\n段小楼在认为该成家立业之时迎娶了名妓菊仙（巩俐），致使程蝶衣认定菊仙是可耻的第三者，使段小楼做了叛徒，自此，三人围绕一出《霸王别姬》生出的爱恨情仇战开始随着时代风云的变迁不断升级，终酿成悲剧。",
		// "attrs":
		// {
		// "language":["汉语普通话"],
		// "pubdate":["1993-01-01(香港)"],
		// "title":["霸王别姬"],
		// "country":["中国大陆","香港"],
		// "writer":["李碧华 Lillian Lee","芦苇 Wei Lu"],
		// "director":["陈凯歌 Kaige Chen"],
		// "cast":["张国荣 Leslie Cheung","张丰毅 Fengyi Zhang","巩俐 Li Gong","葛优 You Ge","英达 Da Ying","蒋雯丽 Wenli Jiang","吴大维 David Wu","吕齐 Qi Lv","雷汉 Han Lei","尹治 Zhi Yin","马明威 Mingwei Ma"],
		// "movie_duration":["171分钟"],
		// "year":["1993"],
		// "movie_type":["剧情","爱情","战争","同性","音乐"]
		// },
		// "alt":"http:\/\/movie.douban.com\/movie\/10000002",
		// "id":10000002,
		// "tags":[
		// {"count":20,"name":"剧情"},
		// {"count":20,"name":"爱情"},
		// {"count":20,"name":"战争"},
		// {"count":20,"name":"同性"},
		// {"count":20,"name":"音乐"}
		// ]
		// }
		String url = "https://api.douban.com/v2/movie/" + movie_id;
		String result = HttpUtil.get(url, null);
		return parseMovie(result);

	}

	Movie parseMovie(String json) {
		try {
			JSONObject jsonObject = new JSONObject(json);

			String id = jsonObject.optString("id");
			if (id.contains("http")) {
				id = id.substring(id.lastIndexOf("/") + 1);

			}
			String title = jsonObject.optString("title");
			float rating = 0;
			String mrating = jsonObject.optJSONObject("rating").optString(
					"average");
			rating = Float.parseFloat(mrating) / 2;
			String image = jsonObject.optString("image");

			String author = jsonObject.optJSONArray("author").getJSONObject(0)
					.optString("name");

			String summary = jsonObject.optString("summary");
			jsonObject = jsonObject.getJSONObject("attrs");
			String language = jsonObject.optString("language");
			String pubdate = jsonObject.optString("pubdate");
			String country = jsonObject.optString("country");
			String cast = jsonObject.optString("cast");
			String movie_duration = jsonObject.optString("movie_duration");
			String year = jsonObject.optString("year");
			String movie_type = jsonObject.optString("movie_type");
			Movie movie = new Movie(id, rating, image, title, author, summary,
					language, pubdate, country, cast, movie_duration, year,
					movie_type);
			MLog.e(movie.toString());
			return movie;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
}
