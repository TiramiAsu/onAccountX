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

import com.onaccountx.mvc.model.entity.Member;

/**
 * <pre>
 * [帳號 REST Bean] 2020-03-20 16:46
 * </pre>
 * 
 * @author TiramiAsu (Email)
 */
public class AccountRESTBean implements Serializable {

	private static final long serialVersionUID = -624006359694822928L;

	private Long id;
	private String account;
	private String password;
	private int status;
	private int errorTimes;
	private Date timeLast;
	private Date timeModify;
	private Long memberId;

	public AccountRESTBean() {}

	public AccountRESTBean(Long id, String account, String password, int status, int errorTimes,
			Date timeLast, Date timeModify, Long memberId) {
		this.id = id;
		this.account = account;
		this.password = password;
		this.status = status;
		this.errorTimes = errorTimes;
		this.timeLast = timeLast;
		this.timeModify = timeModify;
		this.memberId = memberId;
	}

	public Long getId() {
		return id;
	}

	public AccountRESTBean setId(Long id) {
		this.id = id;
		return this;
	}

	public String getAccount() {
		return account;
	}

	public AccountRESTBean setAccount(String account) {
		this.account = account;
		return this;
	}

	public String getPassword() {
		return password;
	}

	public AccountRESTBean setPassword(String password) {
		this.password = password;
		return this;
	}

	public int getStatus() {
		return status;
	}

	public AccountRESTBean setStatus(int status) {
		this.status = status;
		return this;
	}

	public int getErrorTimes() {
		return errorTimes;
	}

	public AccountRESTBean setErrorTimes(int errorTimes) {
		this.errorTimes = errorTimes;
		return this;
	}

	public Date getTimeLast() {
		return timeLast;
	}

	public AccountRESTBean setTimeLast(Date timeLast) {
		this.timeLast = timeLast;
		return this;
	}

	public Date getTimeModify() {
		return timeModify;
	}

	public AccountRESTBean setTimeModify(Date timeModify) {
		this.timeModify = timeModify;
		return this;
	}

	public Long getMemberId() {
		return memberId;
	}

	public AccountRESTBean setMember(Long memberId) {
		this.memberId = memberId;
		return this;
	}

	@Override
	public String toString() {
		return "AccountRESTBean [id=" + id +
				", account=" + account +
				", password=" + password +
				", status=" + status +
				", errorTimes=" + errorTimes +
				", timeLast=" + timeLast +
				", timeModify=" + timeModify +
				", memberId=" + memberId +
				"]";
	}
}
