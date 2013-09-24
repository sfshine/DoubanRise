package com.su.doubanrise.api.bean;

/**
 * 行政区的bean
 * 
 * @author sfshine
 * 
 */
public class District {
	String id;
	String name;

	public District(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
