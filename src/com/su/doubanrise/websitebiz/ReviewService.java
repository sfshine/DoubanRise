package com.su.doubanrise.websitebiz;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.Source;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Xml;

import com.su.doubanrise.api.bean.Review;
import com.su.doubanrise.overall.Constant;
import com.su.doubanrise.util.FileUtil;
import com.su.doubanrise.util.HttpUtil;
import com.su.doubanrise.util.MLog;
import com.su.doubanrise.util.Str;

public class ReviewService {

	public List<Review> getReviews(String url) {
		List<Review> reviews = new ArrayList<Review>();
		Review review = null;

		String path = Constant.ISPATH + Str.md5("getReviews");

		// if (url.contains("book")) {
		path = Constant.ISPATH + Str.md5(url);
		// } else if (url.contains("movie")) {
		// path = Constant.ISPATH + "temp5";
		// } else if (url.contains("music")) {
		// path = Constant.ISPATH + "temp6";
		// }
		InputStream is = FileUtil.getIS(path);
		if (is == null) {
			String result = HttpUtil.get(url, null);
			is = new ByteArrayInputStream(result.getBytes());
			FileUtil.saveIS(is, path);
			MLog.e("============net");
		}
		is = FileUtil.getIS(path);

		XmlPullParser parser = Xml.newPullParser();
		try {
			parser.setInput(is, "UTF-8");

			int eventType = parser.getEventType();
			while (eventType != XmlPullParser.END_DOCUMENT) {
				switch (eventType) {
				case XmlPullParser.START_TAG:
					String tag = parser.getName();
					if (tag.equalsIgnoreCase("item")) {
						review = new Review();
					} else if (review != null) {
						if (tag.equalsIgnoreCase("title")) {
							review.setTitle(parser.nextText());
							// wordItem.setWordStr(parser.nextText());
						} else if (tag.equalsIgnoreCase("link")) {
							String link = parser.nextText();
							review.setLink(link);
							link = link.substring(0, link.length() - 1);
							String id = link
									.substring(link.lastIndexOf("/") + 1);
							review.setId(id);
							// wordItem.setTransStr(parser.nextText());
						} else if (tag.equalsIgnoreCase("description")) {
							review.setDescription(parser.nextText());
						} else if (tag.equalsIgnoreCase("encoded")) {
							String image = parser.nextText();
							image = image.substring(image.indexOf("src=") + 5,
									image.lastIndexOf("style=") - 2);
							MLog.e(image);
							review.setImage(image);

						} else if (tag.equalsIgnoreCase("creator")) {
							review.setCreator(parser.nextText());
						} else if (tag.equalsIgnoreCase("pubDate")) {
							review.setPubDate(parser.nextText());
						}
					}
					break;
				case XmlPullParser.END_TAG:
					if (parser.getName().equalsIgnoreCase("item")
							&& review != null) {
						reviews.add(review);
						review = null;
					}
					break;
				}
				// 如果xml没有结束，则导航到下一个item节点
				eventType = parser.next();
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Collections.shuffle(reviews);
		return reviews;

	}

	private static String reviewUrl = "http://www.douban.com/review/";

	// 获取评论全文
	public static Review getReviewContentAndComments(Review review)
			throws Exception {
		HttpGet request = new HttpGet(reviewUrl + review.getId() + "/");
		HttpClient httpClient = new DefaultHttpClient();
		HttpResponse response = httpClient.execute(request);
		if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			Source source = new Source(response.getEntity().getContent());
			Element contentDivElement = source.getElementById("content");
			for (Element e : contentDivElement.getAllElements("img")) {
				if ("pil".equals(e.getAttributeValue("class"))) {
					review.setAuthorImageUrl(e.getAttributeValue("src"));

				} else if ("rating".equals(e.getAttributeValue("class"))) {
					String rating = e.getAttributeValue("alt");
					rating = rating.substring(0, 1);
					MLog.e(rating);
					float rat = Float.parseFloat(rating);
					review.setRating(rat);
					break;
				}
			}

			for (Element e : contentDivElement.getAllElements("span")) {

				if ("v:itemreviewed".equals(e.getAttributeValue("property"))) {
					String title = e.getContent().toString();
					review.setSubjecttitle(title);

				} else {
					if ("v:description".equals(e.getAttributeValue("property"))) {
						String content = e.getContent().toString();
						review.setContent(content);
						break;

					}
				}

			}

			Element commentsDiv = source.getElementById("comments");
			if (commentsDiv != null) {
				String comments = commentsDiv.getContent().toString();
				review.setComments(comments);
			}
		}
		return review;
	}

}
