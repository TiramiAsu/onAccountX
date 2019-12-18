/*
 * 
 * 
 * 
 * 
 * 
 * &copy;TiramiAsu
 * 
 */
package com.onaccountx.mvc.model.entity;

import java.util.Date;

/**
 * <pre>
 * [Member] 2019-12-06 00:24
 * </pre>
 * 
 * @author  TiramiAsu (Email)
 */
public class Member {

	public final static String _ENTITY_NAME = "member";
	public final static String _ID = "id";
	public final static String _NAME = "name";
	public final static String _EMAIL = "email";
	public final static String _PHONE = "phone";
	public final static String _TIME_MODIFY = "timeModify";
	public final static String _TIME_BUILD = "timeBuild";
	
	private int id;
	
	private String name;
	
	private String email;
	
	private String phone;
	
	private Date timeModify;
	
	private Date timeBuild;
	
	public Member() {}
	
	public Member(String name, String email, String phone,
			Date timeModify, Date timeBuild) {
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.timeModify = timeModify;
		this.timeBuild = timeBuild;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getTimeModify() {
		return timeModify;
	}

	public void setTimeModify(Date timeModify) {
		this.timeModify = timeModify;
	}

	public Date getTimeBuild() {
		return timeBuild;
	}

	public void setTimeBuild(Date timeBuild) {
		this.timeBuild = timeBuild;
	}

	@Override
	public String toString() {
		return "Member [id=" + id
				+ ", name=" + name
				+ ", email=" + email
				+ ", phone=" + phone
				+ ", timeModify=" + timeModify
				+ ", timeBuild=" + timeBuild + "]";
	}
	
}
