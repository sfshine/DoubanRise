package com.su.doubanrise.api;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.Source;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.su.doubanrise.api.bean.Mail;
import com.su.doubanrise.api.bean.SendMail;
import com.su.doubanrise.api.bean.Subject;
import com.su.doubanrise.api.bean.User;
import com.su.doubanrise.overall.Constant;
import com.su.doubanrise.util.HttpUtil;
import com.su.doubanrise.util.MLog;

public class MailApi {
	static MailApi mailApi;

	static public MailApi getMailApi() {
		if (mailApi == null) {
			mailApi = new MailApi();
		}
		return mailApi;
	}

	Gson gson = new Gson();

	/**
	 * 邮件Api
	 */
	public List<User> getUserList() {

		return new TmsgApi().getFollowing("sfshine");

	}

	// /**
	// * 注意这里使用的html解析
	// *
	// * @return
	// */
	// public List<User> getUserList() {
	// String url = "http://www.douban.com/doumail/choose";
	// String result = HttpUtil.get(url, null);
	// Source source = new Source(result);
	// List<Element> divs = source.getAllElements("div");
	//
	// for (Element e : divs) {
	// if ("indent".equals(e.getAttributeValue("class"))) {
	// List<Element> as = source.getAllElements("a");
	// for (Element a : as) {
	// String id = a.getAttributeValue("href");
	// id = id.substring(id.indexOf("to=") + 3);
	// User user = new User();
	// user.setId(id);
	// Element img = a.getFirstElement("img");
	// String image = img.getAttributeValue("src");
	// user.setAvatar(image);
	// MLog.e(user.toString());
	// }
	//
	// }
	// break;
	// }

	// List<Element> childs = e.getChildElements();
	//
	// if (childs.size() == 2) {
	//
	// if ("detail-frame".equals(childs.get(0).getAttributeValue(
	// "class"))) {
	// Element contents = childs.get(0);
	// Element otherinfo = childs.get(1);
	//
	// String id = otherinfo.getAttributeValue("href");
	// Subject book = new Subject();
	// String img = otherinfo.getChildElements().get(0)
	// .getAttributeValue("src");
	// id = id.substring(0, id.length() - 1);
	// id = id.substring(id.lastIndexOf("/") + 1);
	// book.setId(id);
	// book.setUrl(bookUrl + id);
	// book.setImgUrl(img);
	// book.setTitle(contents.getChildElements().get(0)
	// .getTextExtractor().toString());
	// book.setDescription(contents.getChildElements().get(1)
	// .getTextExtractor().toString());
	// book.setSummary(contents.getChildElements().get(2)
	// .getTextExtractor().toString());
	//
	// // 新书获取不到评分，设为-1
	// book.setRating(-1f);
	// book.setType(Subject.BOOK);
	//
	// books.add(book);
	// }
	//
	// }

	// return null;
	//
	// }

	public String sendMail(SendMail sendMail) {

		// title 豆邮标题 必填字段
		// content 豆邮正文 必填字段
		// receiver_id 接收邮件的用户id 必填字段
		// captcha_token 系统验证码 token 选填字段
		// captcha_string 用户输入验证码 选填字段
		String url = "https://api.douban.com/v2/doumails";
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("title", sendMail.getTitle());
		map.put("content", sendMail.getContent());
		map.put("receiver_id", sendMail.getReceiver_id());
		map.put("captcha_token", sendMail.getCaptcha_token());
		map.put("captcha_string", sendMail.getCaptcha_string());
		String result = HttpUtil.post(url, map);
		return result;

	}

	public List<Mail> getInbox() {
		String url = "https://api.douban.com/v2/doumail/inbox";
		return getMails(url);

	}

	public List<Mail> getOutbox() {
		String url = "https://api.douban.com/v2/doumail/outbox";
		return getMails(url);

	}

	public List<Mail> getUnread() {
		String url = "https://api.douban.com/v2/doumail/inbox/unread";
		return getMails(url);

	}

	private List<Mail> getMails(String url) {
		String result = HttpUtil.get(url, null);
		List<Mail> mails;
		try {
			JSONObject jsonObject = new JSONObject(result);
			String json = jsonObject.getString("mails");
			Type listType = new TypeToken<List<Mail>>() {
			}.getType();
			mails = gson.fromJson(json, listType);
			MLog.e(mails.get(0).getContent());
			return mails;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
