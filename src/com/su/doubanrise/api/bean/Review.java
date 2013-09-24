package com.su.doubanrise.api.bean;

import java.io.Serializable;

public class Review implements Serializable {
	private static final long serialVersionUID = 1L;
	private String title;
	private String link;
	private String description;
	private String image;
	private String creator;
	private String pubDate;
	private String id;
	private String authorImageUrl;
	private String subjecttitle;
	private float rating;
	// private String url;
	//
	// private String authorId;

	// private float rating;
	// private Subject subject;
	private String content;

	private String comments;

	//
	// private boolean self;

	public String getTitle() {
		return title;
	}

	public float getRating() {
		return rating;
	}

	public void setRating(float rating) {
		this.rating = rating;
	}

	public String getSubjecttitle() {
		return subjecttitle;
	}

	public void setSubjecttitle(String subjecttitle) {
		this.subjecttitle = subjecttitle;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAuthorImageUrl() {
		return authorImageUrl;
	}

	public void setAuthorImageUrl(String authorImageUrl) {
		this.authorImageUrl = authorImageUrl;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getPubDate() {
		return pubDate;
	}

	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	// <item>
	// <title>
	// 冷战与中国的现代化 (评论: 全球冷战)
	// </title>
	// <link>
	// http://book.douban.com/review/5699283/
	// </link>
	// <description>
	// <![CDATA[
	// danyboy评论: 全球冷战 (http://book.douban.com/subject/10781496/)
	// 评价: 力荐
	//
	// 一、冷战与第三世界的现代化
	//
	// 冷战，首先是美苏两大集团之间“国际关系”的一种形式，地点主要在欧洲。所以，即使想在冷战研究的领域另辟蹊径，也难以脱离美苏之争的语境。20世纪90年代以来，关于冷战的学术著作大抵如此，但颇令人感到不足的是，多数著作尽管极尽客观，但难以脱离褒美贬苏的成见，倘若再没有什么史实的猛料，这类冷战史就会很乏味。文安立这本《全球冷战》，并不是对褒贬美苏唱反调，而是跳出诛绝褒贬，甚至跳出国际关...
	// ]]>
	// </description>
	// <content:encoded>
	// <![CDATA[
	//
	// <img src="http://img3.douban.com/mpic/s10286126.jpg"
	// style="float:right;margin-left:16px"/><a
	// href="http://www.douban.com/people/danyboy/">danyboy</a>评论: <a
	// href="http://book.douban.com/subject/10781496//">全球冷战</a><br/>
	// <br/>评价: 力荐<br/><br/>
	// ]]>
	// </content:encoded>
	// <dc:creator>
	// danyboy
	// </dc:creator>
	// <pubDate>
	// Sat, 15 Dec 2012 08:42:11 GMT
	// </pubDate>
	// <guid isPermaLink="true">
	// http://book.douban.com/review/5699283/
	// </guid>
	// </item>
}
