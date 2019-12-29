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

	private static final long serialVersionUID = 8074757872370969828L;
	
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

	public void setId(Long id) {
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
	
}
