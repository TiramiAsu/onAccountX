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

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * <pre>
 * [日記帳] 2020-04-07 22:39
 * </pre>
 * 
 * @author TiramiAsu (Email)
 */
@Entity
@Table(name = "acc_journals")
public class Journal implements Serializable {

	private static final long serialVersionUID = -5565819749879625656L;

	public final static String _JSON_NAME = "journal";
	public final static String _ID = "id";
	public final static String _TIME_DATE = "timeDate";
	public final static String _DEBIT = "debit";
	public final static String _CREDIT = "credit";
	public final static String _AMOUNT = "amount";
	public final static String _ITEM = "item";
	public final static String _PLACE = "place";
	public final static String _WHO = "who";
	public final static String _ACCOUNT_ID = "accountId";
	public final static String _TIME_BUILD = "timeBuild";
	public final static String _TIME_MODIFY = "timeModify";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "time_date", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date timeDate;

	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinColumn(name = "debit",referencedColumnName="id", nullable = false)
	private Subject debit;

	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinColumn(name = "credit",referencedColumnName="id", nullable = false)
	private Subject credit;

	@Column(name = "amount", nullable = false)
	private Integer amount;

	@Column(name = "item", length = 1023, nullable = false)
	private String item;

	@Column(name = "place", length = 255)
	private String place;

	@Column(name = "who", length = 255)
	private String who;

	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinColumn(name = "a_id", referencedColumnName = "id", nullable = false)
	private Account account;

	@Column(name = "time_build", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date timeBuild;

	@Column(name = "time_modify", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date timeModify;

	public Journal() {}

	public Journal(Date timeDate, Subject debit, Subject credit, Integer amount, String item,
			String place, String who, Account account) {
		this.timeDate = timeDate;
		this.debit = debit;
		this.credit = credit;
		this.amount = amount;
		this.item = item;
		this.place = place;
		this.who = who;
		this.account = account;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getTimeDate() {
		return timeDate;
	}

	public void setTimeDate(Date timeDate) {
		this.timeDate = timeDate;
	}

	public Subject getDebit() {
		return debit;
	}

	public void setDebit(Subject debit) {
		this.debit = debit;
	}

	public Subject getCredit() {
		return credit;
	}

	public void setCredit(Subject credit) {
		this.credit = credit;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getWho() {
		return who;
	}

	public void setWho(String who) {
		this.who = who;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
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
		return "Journal [id=" + id +
				", timeDate=" + timeDate +
				", amount=" + amount +
				", item=" + item +
				", place=" + place +
				", who=" + who +
				", timeBuild=" + timeBuild +
				", timeModify=" + timeModify +
				"]";
	}
}
