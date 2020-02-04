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

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * <pre>
 * [會員] 2019-12-06 00:24
 * </pre>
 * 
 * @author TiramiAsu (Email)
 */
@Entity
@Table(name = "members")
public class Member implements Serializable {

	private static final long serialVersionUID = 3063969525003747477L;

	public final static String _ENTITY_NAME = "Member";
	public final static String _ID = "id";
	public final static String _NAME = "name";
	public final static String _EMAIL = "email";
	public final static String _PHONE = "phone";
	public final static String _TIME_MODIFY = "timeModify";
	public final static String _TIME_BUILD = "timeBuild";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "email")
	private String email;

	@Column(name = "phone")
	private String phone;

	@Column(name = "time_modify")
	@Temporal(TemporalType.TIMESTAMP)
	private Date timeModify;

	@Column(name = "time_build")
	@Temporal(TemporalType.TIMESTAMP)
	private Date timeBuild;

	public Member() {}

	public Member(String name, String email, String phone, Date timeModify, Date timeBuild) {
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.timeModify = timeModify;
		this.timeBuild = timeBuild;
	}

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

	@Override
	public String toString() {
		return "Member [id=" + id +
				", name=" + name +
				", email=" + email +
				", phone=" + phone +
				", timeModify=" + timeModify +
				", timeBuild=" + timeBuild +
				"]";
	}

}
