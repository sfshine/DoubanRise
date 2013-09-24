package com.su.doubanrise.websitebiz;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.Source;

import com.su.doubanrise.api.bean.Album;
import com.su.doubanrise.api.bean.Subject;
import com.su.doubanrise.overall.Constant;
import com.su.doubanrise.util.FileUtil;
import com.su.doubanrise.util.HttpUtil;
import com.su.doubanrise.util.MLog;
import com.su.doubanrise.util.Str;

public class MusicService {
	public static String getAlbumById(String a_id) throws Exception {
		String url = "http://music.douban.com/subject/" + a_id;
		String bookispath = Constant.ISPATH + Str.md5("getAlbumById()" + a_id);

		String result = FileUtil.getString(bookispath);

		if (result == null) {
			result = HttpUtil.get(url);
			FileUtil.saveString(bookispath, result);

		}

		Source source = new Source(result);
		List<Element> spans = source.getAllElements("span");
		for (Element span : spans) {
			String summary = "";

			if ("all hidden".equals(span.getAttributeValue("class"))) {

				summary = span.getTextExtractor().toString();
				return summary;
			}
			if ("v:summary".equals(span.getAttributeValue("property"))) {
				summary = span.getTextExtractor().toString();
				return summary;
			}

		}
		return null;
	}

	public static List<Subject> getDoubanNewAlbums() throws Exception {
		List<Subject> albums = new ArrayList<Subject>();
		String bookispath = Constant.ISPATH + Str.md5("getDoubanNewAlbums()");
		String result = FileUtil.getString(bookispath);

		if (result == null) {
			result = HttpUtil.get("http://music.douban.com/");
			FileUtil.saveString(bookispath, result);

		}

		Source source = new Source(result);

		List<Element> uls = source.getAllElements("ul");

		for (Element e : uls) {
			if ("newcontent1".equals(e.getAttributeValue("id"))) {
				List<Element> lis = e.getAllElements("li");

				for (Element li : lis) {

					String id = li.getFirstElement("a").getAttributeValue(
							"href");
					id = id.substring(0, id.length() - 1);
					id = id.substring(id.lastIndexOf("/") + 1);
					String image = li.getFirstElement("img").getAttributeValue(
							"src");
					String name = li.getFirstElement("h3").getFirstElement("a")
							.getTextExtractor().toString();

					// <li class="clearfix">
					// <div class="img"><a onclick="moreurl(this, {from:'new'})"
					// href="http://music.douban.com/subject/20434006/"><img
					// width="100%"
					// src="http://img3.douban.com/spic/s24479613.jpg"/></a></div>

					// <div class="intro">
					// <h3>1. <a onclick="moreurl(this, {from:'new'})"
					// href="http://music.douban.com/subject/20434006/">十二新作</a></h3>
					//
					// 周杰伦<br/>
					// <div class="star clearfix"><span
					// class="allstar40"></span>7.7</div>

					// </div>
					// </li>
					String singer = li.getTextExtractor().toString();
					String sratting = "0";
					List<Element> divs = li.getAllElements("div");
					for (Element div : divs) {
						if ("intro".equals(div.getAttributeValue("class"))) {
							singer = div.toString().trim();
							singer = singer.substring(
									singer.lastIndexOf("</h3>") + 5,
									singer.lastIndexOf("<br/>"));

						}
						if ("star clearfix".equals(div
								.getAttributeValue("class"))) {
							sratting = div.getTextExtractor().toString();

						}
					}

					Subject subject = new Subject();
					subject.setId(id);
					subject.setImgUrl(image);
					subject.setDescription("演唱：" + singer.trim());
					subject.setTitle(name);
					subject.setRating(Float.parseFloat(sratting) / 2);
					albums.add(subject);

				}

				return albums;

			}

		}

		return albums;
	}
}
