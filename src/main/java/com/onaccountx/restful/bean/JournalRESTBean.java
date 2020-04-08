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
 * [日記帳 REST Bean] 2020-04-08 00:14
 * </pre>
 * 
 * @author TiramiAsu (Email)
 */
public class JournalRESTBean implements Serializable {

	private static final long serialVersionUID = -4394426847073531076L;

	private Long id;
	private Long timeDate;
	private Long debitId;  // subject id
	private Long creditId; // subject id
	private Integer amount;
	private String item;
	private String place;
	private String who;
	private Long aId;    // account id
	private Date timeModify;

	public JournalRESTBean() {}

	public JournalRESTBean(Long id, Long timeDate, Long debit, Long credit, Integer amount,
			String item, String place, String who, Long aId, Date timeModify) {
		this.id = id;
		this.timeDate = timeDate;
		this.debitId = debit;
		this.creditId = credit;
		this.amount = amount;
		this.item = item;
		this.place = place;
		this.who = who;
		this.aId = aId;
		this.timeModify = timeModify;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTimeDate() {
		return timeDate;
	}

	public void setTimeDate(Long timeDate) {
		this.timeDate = timeDate;
	}

	public Long getDebitId() {
		return debitId;
	}

	public void setDebitId(Long debit) {
		this.debitId = debit;
	}

	public Long getCreditId() {
		return creditId;
	}

	public void setCreditId(Long credit) {
		this.creditId = credit;
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

	public Long getAId() {
		return aId;
	}

	public void setAId(Long aId) {
		this.aId = aId;
	}

	public Date getTimeModify() {
		return timeModify;
	}

	public void setTimeModify(Date timeModify) {
		this.timeModify = timeModify;
	}

	@Override
	public String toString() {
		return "JournalRESTBean [id=" + id +
				", timeDate=" + timeDate +
				", debitId=" + debitId +
				", creditId=" + creditId +
				", amount=" + amount +
				", item=" + item +
				", place=" + place +
				", who=" + who +
				", aId=" + aId +
				", timeModify=" + timeModify +
				"]";
	}
}
