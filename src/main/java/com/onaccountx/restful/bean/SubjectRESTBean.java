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
 * [會計科目 REST Bean] 2020-03-20 00:19
 * </pre>
 * 
 * @author TiramiAsu (Email)
 */
public class SubjectRESTBean implements Serializable {

	private static final long serialVersionUID = 4417650665431206088L;

	private Long id;
	private String code;
	private String name;
	private Date timeModify;

	public SubjectRESTBean() {}

	public SubjectRESTBean(String code, String name, Date timeModify) {
		this.code = code;
		this.name = name;
		this.timeModify = timeModify;
	}

	public Long getId() {
		return id;
	}

	public SubjectRESTBean setId(Long id) {
		this.id = id;
		return this;
	}

	public String getCode() {
		return code;
	}

	public SubjectRESTBean setCode(String code) {
		this.code = code;
		return this;
	}

	public String getName() {
		return name;
	}

	public SubjectRESTBean setName(String name) {
		this.name = name;
		return this;
	}

	public Date getTimeModify() {
		return timeModify;
	}

	public SubjectRESTBean setTimeModify(Date timeModify) {
		this.timeModify = timeModify;
		return this;
	}

	@Override
	public String toString() {
		return "SubjectRESTBean [id=" + id +
				", code=" + code +
				", name=" + name +
				", timeModify=" + timeModify +
				"]";
	}
}
