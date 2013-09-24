package com.su.doubanrise.api.bean;

import java.io.Serializable;

public class Cinema implements Serializable {

	String name;
	String price;

	String timelines;
	String latLng;
	String address;
	String phone;
	String id;

	public Cinema(String name, String price, String timelines, String latLng,
			String address, String phone, String id) {
		super();
		this.name = name;
		this.price = price;

		this.timelines = timelines;
		this.latLng = latLng;
		this.address = address;
		this.phone = phone;
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getTimelines() {
		return timelines;
	}

	public void setTimelines(String timelines) {
		this.timelines = timelines;
	}

	public String getLatLng() {
		return latLng;
	}

	public void setLatLng(String latLng) {
		this.latLng = latLng;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	// "address": "澳门路88号百丽广场三层",
	// "maxPrice": "",
	// "widgetUrl":
	// "http://site.douban.com/112036/widget/movie_schedule/2829418/date/20121222/",
	// "phone": "0532-66061155 / 0532-66061156",
	// "latLng": "36.059604,120.394964",
	// "weekday": "12.22 周六",
	// "schedules": [
	// {
	// "comment": "",
	// "throughs": [ ],
	// "language": "普通话",
	// "price": "33",
	// "source": 5,
	// "version": "数字",
	// "duration": "120",
	// "timelines": [
	// {
	// "url": "",
	// "source": 5,
	// "id": "17952592",
	// "time": "11:50"
	// },
	// {
	// "url": "",
	// "source": 5,
	// "id": "17952593",
	// "time": "13:30"
	// },
	// {
	// "url": "",
	// "source": 5,
	// "id": "17952594",
	// "time": "14:00"
	// },
	// {
	// "url": "",
	// "source": 5,
	// "id": "17952595",
	// "time": "14:20"
	// },
	// {
	// "url": "http://movie.douban.com/ticket/17774405/",
	// "source": 5,
	// "id": "17774405",
	// "time": "16:10"
	// },
	// {
	// "url": "http://movie.douban.com/ticket/17774406/",
	// "source": 5,
	// "id": "17774406",
	// "time": "16:30"
	// },
	// {
	// "url": "http://movie.douban.com/ticket/17952596/",
	// "source": 5,
	// "id": "17952596",
	// "time": "19:40"
	// },
	// {
	// "url": "http://movie.douban.com/ticket/17952597/",
	// "source": 5,
	// "id": "17952597",
	// "time": "21:50"
	// }
	// ]
	// }
	// ],
	// "follow": false,
	// "minPrice": "33",
	// "id": "112036",
	// "name": "青岛百老汇影城"
	// },

}
