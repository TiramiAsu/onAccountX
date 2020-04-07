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
 * [會計科目] 2020-03-20 00:06
 * </pre>
 * 
 * @author TiramiAsu (Email)
 */
@Entity
@Table(name = "acc_subjects")
public class Subject implements Serializable {

	private static final long serialVersionUID = -1106428086213980306L;

	public final static String _JSON_NAME = "subject";
	public final static String _ID = "id";
	public final static String _CODE = "code";
	public final static String _NAME = "name";
	public final static String _TIME_MODIFY = "timeModify";
	public final static String _TIME_BUILD = "timeBuild";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "code", length = 31, nullable = false, unique = true)
	private String code;

	@Column(name = "name", length = 63, nullable = false)
	private String name;

	@Column(name = "time_build", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date timeBuild;

	@Column(name = "time_modify", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date timeModify;

	public Subject() {}

	public Subject(String code, String name) {
		this.code = code;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getTimeBuild() {
		return timeBuild;
	}

	public void setTimeBuild(Date timeBuild) {
		this.timeBuild = timeBuild;
	}

	public Date getTimeModify() {
		return timeModify;
	}

	public void setTimeModify(Date timeModify) {
		this.timeModify = timeModify;
	}

	@Override
	public String toString() {
		return "Subjects [id=" + id +
				", code=" + code +
				", name=" + name +
				", timeBuild=" + timeBuild +
				", timeModify=" + timeModify +
				"]";
	}
}
