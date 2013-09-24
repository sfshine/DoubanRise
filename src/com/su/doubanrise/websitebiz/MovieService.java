package com.su.doubanrise.websitebiz;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.Source;

import android.util.Log;

import com.google.gson.JsonArray;
import com.su.doubanrise.api.bean.Cinema;
import com.su.doubanrise.api.bean.District;
import com.su.doubanrise.api.bean.Review;
import com.su.doubanrise.api.bean.Subject;
import com.su.doubanrise.overall.Constant;
import com.su.doubanrise.util.FileUtil;
import com.su.doubanrise.util.HttpUtil;
import com.su.doubanrise.util.MLog;
import com.su.doubanrise.util.Str;

public class MovieService {
	private static String movieUrl = "http://api.douban.com/movie/subject/";

	// 获取豆瓣新书
	public static List<Subject> getDoubanNewMovies() throws Exception {

		String bookispath = Constant.ISPATH + Str.md5("getDoubanNewMovies()");

		String result = FileUtil.getString(bookispath);

		if (result == null) {
			result = HttpUtil.get("http://movie.douban.com/chart");
			FileUtil.saveString(bookispath, result);

		}

		Source source = new Source(result);

		List<Element> tables = source.getAllElements("table");

		List<Subject> movies = new ArrayList<Subject>();
		for (Element e : tables) {
			if ("100%".equals(e.getAttributeValue("width"))) {
				Element a = e.getFirstElement("a");
				String id = a.getAttributeValue("href");

				Element img = e.getFirstElement("img");

				Element ep = e.getFirstElement("p");

				List<Element> espans = e.getAllElements("span");
				float star = 0;
				for (Element espan : espans) {
					if ("rating_nums".equals(espan.getAttributeValue("class"))) {
						String sstar = espan.getTextExtractor().toString();
						star = Float.parseFloat(sstar);
						break;
					}
				}

				id = id.substring(0, id.length() - 1);
				id = id.substring(id.lastIndexOf("/") + 1);
				String imageurl = img.getAttributeValue("src");
				String title = img.getAttributeValue("alt");
				String description = ep.getTextExtractor().toString();

				Subject movie = new Subject();
				movie.setId(id);
				movie.setImgUrl(imageurl);
				movie.setTitle(title);
				movie.setDescription(description);
				movie.setRating(star / 2);
				movies.add(movie);
				// MLog.e(star + id);

			}

		}

		Collections.shuffle(movies);// 随机更改list中的排列顺序

		return movies;
	}

	// http://movie.douban.com/j/movie/cinemas?subject=4212172&city=118221&district=129756&latLng=&cinema=&date=2012-12-22&limit=30&start=0&keyword=&onlybookable=
	// 完整
	private static String CINEMAINFOURL = "http://movie.douban.com/j/movie/cinemas";

	// ?subject=10540066&city=118221&date=2012-12-22&limit=30&start=0&district=129756

	public static List<Cinema> getCinemaInfo(String subject, String date,
			String district) {

		String bookispath = Constant.ISPATH
				+ Str.md5("getCinemaInfo" + subject + date + district);
		String result = FileUtil.getString(bookispath);

		if (result == null) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("subject", subject);
			map.put("city", "118221");
			map.put("date", date);
			map.put("district", district);
			result = HttpUtil.get(CINEMAINFOURL, map);
			FileUtil.saveString(bookispath, result);

		}
		JSONObject jsonObject;
		if (Constant.DISTRICTS == null || Constant.DISTRICTS.size() == 0) {

			try {
				jsonObject = new JSONObject(result);
				JSONArray jsonArray = jsonObject.getJSONArray("districts");
				List<District> districts = new ArrayList<District>();

				for (int i = 0; i < jsonArray.length(); i++) {
					jsonObject = jsonArray.getJSONObject(i);
					String id = jsonObject.getString("id");
					String name = jsonObject.getString("name");
					District bdistrict = new District(id, name);
					districts.add(bdistrict);
				}
				Constant.DISTRICTS = districts;
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

		try {

			jsonObject = new JSONObject(result);
			JSONArray jsonArray = jsonObject.getJSONArray("cinemas");
			List<Cinema> cinemas = new ArrayList<Cinema>();
			for (int i = 0; i < jsonArray.length(); i++) {
				jsonObject = jsonArray.getJSONObject(i);
				String name = jsonObject.getString("name");
				JSONArray sjsy = jsonObject.getJSONArray("schedules");
				String price = "";
				String timelines = "";

				if (sjsy.length() != 0) {
					price = sjsy.getJSONObject(0).getString("price");
					JSONArray timelinesjry = sjsy.getJSONObject(0)
							.getJSONArray("timelines");

					for (int j = 0; j < timelinesjry.length(); j++) {
						JSONObject jsonObject2 = timelinesjry.getJSONObject(j);
						timelines += jsonObject2.getString("time") + " ";
					}
				}

				String latLng = jsonObject.getString("latLng");
				String address = jsonObject.getString("address");
				String phone = jsonObject.getString("phone");
				String id = jsonObject.getString("id");

				Cinema cinema = new Cinema(name, price, timelines, latLng,
						address, phone, id);
				cinemas.add(cinema);
			}

			return cinemas;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	public static List<Review> getShortReview(String movie_id, String sort) {
		String url = "http://movie.douban.com/subject/" + movie_id
				+ "/comments?sort=" + sort;

		String bookispath = Constant.ISPATH
				+ Str.md5("getShortReview" + movie_id + sort);
		
		try {
			String result = FileUtil.getString(bookispath);

			if (result == null) {
				result = HttpUtil.get(url);
				FileUtil.saveString(bookispath, result);

			}

			Source source = new Source(result);
			List<Element> divs = source.getAllElements("div");
			List<Review> reviews = new ArrayList<Review>();
			for (Element e : divs) {
				if ("clearfix subject-comment-item".equals(e
						.getAttributeValue("class"))) {

					Element a = e.getFirstElement("a");
					String creator = a.getAttributeValue("title");
					String authorImageUrl = a.getFirstElement("img")
							.getAttributeValue("src");
					Element p = e.getFirstElement("p");
					String content = p.getTextExtractor().toString();
					Review review = new Review();
					review.setCreator(creator);
					review.setAuthorImageUrl(authorImageUrl);
					review.setContent(content);
					reviews.add(review);
					MLog.e(review.getCreator());

				}
			}

			return reviews;

		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;

	}

}
