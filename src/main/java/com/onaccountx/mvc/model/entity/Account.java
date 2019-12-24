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
 * [帳號] 2019-12-11 15:26
 * </pre>
 * 
 * @author  TiramiAsu (Email)
 */
@Entity
@Table(name="accounts")
public class Account implements Serializable {
	
	private static final long serialVersionUID = -9210792137073570228L;
	
	public final static int VALUE_ENABLE = Account.STATUS.ENABLE.value;
	public final static int VALUE_LOCK = Account.STATUS.LOCK.value;
	public final static int VALUE_DISABLE = Account.STATUS.DISABLE.value;
	public final static String TEXT_ENABLE = Account.STATUS.ENABLE.text;
	public final static String TEXT_LOCK = Account.STATUS.LOCK.text;
	public final static String TEXT_DISABLE = Account.STATUS.DISABLE.text;

	public final static String _ENTITY_NAME = "Account";
	public final static String _ID = "id";
	public final static String _ACCOUNT = "account";
	public final static String _PASSWORD = "password";
	public final static String _STATUS = "status";
	public final static String _ERROR_TIMES = "errorTimes";
	public final static String _TIME_BUILD = "timeBuild";
	public final static String _TIME_LAST = "timeLast";
	public final static String _TIME_MODIFY = "timeModify";
	public final static String _MEMBER_ID = "memberId";
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "account")
	private String account;

	@Column(name = "password")
	private String password;

	@Column(name = "status")
	private int status;

	@Column(name = "error_times")
	private int errorTimes;

	@Column(name = "time_build")
	@Temporal(TemporalType.TIMESTAMP)
	private Date timeBuild;

	@Column(name = "time_last")
	@Temporal(TemporalType.TIMESTAMP)
	private Date timeLast;

	@Column(name = "time_modify")
	@Temporal(TemporalType.TIMESTAMP)
	private Date timeModify;

	@Column(name = "m_id")
	private Long memberId;
	
	public Account() {}

	public Account(String account, String password, int status,
			int errorTimes, Date timeBuild, Date timeLast, Date timeModify,
			Long memberId) {
		this.account = account;
		this.password = password;
		this.status = status;
		this.errorTimes = errorTimes;
		this.timeBuild = timeBuild;
		this.timeLast = timeLast;
		this.timeModify = timeModify;
		this.memberId = memberId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getErrorTime() {
		return errorTimes;
	}

	public void setErrorTime(int errorTimes) {
		this.errorTimes = errorTimes;
	}

	public Date getTimeBuild() {
		return timeBuild;
	}

	public void setTimeBuild(Date timeBuild) {
		this.timeBuild = timeBuild;
	}

	public Date getTimeLast() {
		return timeLast;
	}

	public void setTimeLast(Date timeLast) {
		this.timeLast = timeLast;
	}

	public Date getTimeModify() {
		return timeModify;
	}

	public void setTimeModify(Date timeModify) {
		this.timeModify = timeModify;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	
	@Override
	public String toString() {
		return "Account [id=" + id
				+ ", account=" + account
				+ ", password=" + password
				+ ", status=" + status
				+ ", errorTimes=" + errorTimes
				+ ", timeBuild=" + timeBuild
				+ ", timeLast=" + timeLast
				+ ", timeModify=" + timeModify
				+ ", memberId=" + memberId + "]";
	}
	
	private enum STATUS {
		DISABLE(0, "停用"), ENABLE(1, "啟用中"), LOCK(4, "已鎖定");
		
		private int value;
		private String text;
		
		private STATUS(int value, String text) {
			this.value = value;
			this.text = text;
		}
		
	}

}
