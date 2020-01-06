/*
 * 
 * 
 * 
 * 
 * 
 * &copy;TiramiAsu
 * 
 */
package com.onaccountx.restful.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * <pre>
 * [會員 REST Bean] 2019-12-24 23:19
 * </pre>
 * 
 * @author TiramiAsu (Email)
 */
public class MemberRESTBean implements Serializable {

	private static final long serialVersionUID = 6210808508968509212L;
	
	private Long id;
	private String name;
	private String email;
	private String phone;
	private Date timeModify;
	private Date timeBuild;

	public MemberRESTBean() {}

	public Long getId() {
		return id;
	}

	public MemberRESTBean setId(Long id) {
		this.id = id;
		return this;
	}

	public String getName() {
		return name;
	}

	public MemberRESTBean setName(String name) {
		this.name = name;
		return this;
	}

	public String getEmail() {
		return email;
	}

	public MemberRESTBean setEmail(String email) {
		this.email = email;
		return this;
	}

	public String getPhone() {
		return phone;
	}

	public MemberRESTBean setPhone(String phone) {
		this.phone = phone;
		return this;
	}

	public Date getTimeModify() {
		return timeModify;
	}

	public MemberRESTBean setTimeModify(Date timeModify) {
		this.timeModify = timeModify;
		return this;
	}

	public Date getTimeBuild() {
		return timeBuild;
	}

	public MemberRESTBean setTimeBuild(Date timeBuild) {
		this.timeBuild = timeBuild;
		return this;
	}
	
}
