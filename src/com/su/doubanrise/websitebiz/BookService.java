package com.su.doubanrise.websitebiz;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.Source;

import android.util.Log;

import com.su.doubanrise.api.bean.Subject;
import com.su.doubanrise.overall.Constant;
import com.su.doubanrise.util.FileUtil;
import com.su.doubanrise.util.HttpUtil;
import com.su.doubanrise.util.MLog;
import com.su.doubanrise.util.Str;

public class BookService {
	private static String bookUrl = "http://api.douban.com/book/subject/";

	// 获取豆瓣新书
	public static List<Subject> getDoubanNewBooks() throws Exception {
		List<Subject> books = new ArrayList<Subject>();
		String bookispath = Constant.ISPATH + Str.md5("getDoubanNewBooks()");

		String result = FileUtil.getString(bookispath);

		if (result == null) {
			result = HttpUtil.get("http://book.douban.com/latest");
			FileUtil.saveString(bookispath, result);

		}

		Source source = new Source(result);

		List<Element> divs = source.getAllElements("li");

		for (Element e : divs) {
			List<Element> childs = e.getChildElements();

			if (childs.size() == 2) {

				if ("detail-frame".equals(childs.get(0).getAttributeValue(
						"class"))) {
					Element contents = childs.get(0);
					Element otherinfo = childs.get(1);

					String id = otherinfo.getAttributeValue("href");
					Subject book = new Subject();
					String img = otherinfo.getChildElements().get(0)
							.getAttributeValue("src");
					id = id.substring(0, id.length() - 1);
					id = id.substring(id.lastIndexOf("/") + 1);
					book.setId(id);
					book.setUrl(bookUrl + id);
					book.setImgUrl(img);
					book.setTitle(contents.getChildElements().get(0)
							.getTextExtractor().toString());
					book.setDescription(contents.getChildElements().get(1)
							.getTextExtractor().toString());
					book.setSummary(contents.getChildElements().get(2)
							.getTextExtractor().toString());

					// 新书获取不到评分，设为-1
					book.setRating(-1f);
					book.setType(Subject.BOOK);

					books.add(book);
				}

			}

		}

		Collections.shuffle(books);

		return books;
	}
}
