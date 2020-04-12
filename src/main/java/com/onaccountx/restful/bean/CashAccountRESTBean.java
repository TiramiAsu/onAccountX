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
 * [現金帳 REST Bean] 2020-03-18 15:19
 * </pre>
 * 
 * @author TiramiAsu (Email)
 */
public class CashAccountRESTBean implements Serializable {

	private static final long serialVersionUID = -3857232576005492446L;

	private Long id;
	private Integer increase;
	private Integer reduce;
	private Date timeModify;
	private Long journalId;

	public CashAccountRESTBean() {}

	public CashAccountRESTBean(Integer increase, Integer reduce, Date timeModify, Long journalId) {
		this.increase = increase;
		this.reduce = reduce;
		this.timeModify = timeModify;
		this.journalId = journalId;
	}

	public Long getId() {
		return id;
	}

	public CashAccountRESTBean setId(Long id) {
		this.id = id;
		return this;
	}

	public Integer getIncrease() {
		return increase;
	}

	public CashAccountRESTBean setIncrease(Integer increase) {
		this.increase = increase;
		return this;
	}

	public Integer getReduce() {
		return reduce;
	}

	public CashAccountRESTBean setReduce(Integer reduce) {
		this.reduce = reduce;
		return this;
	}

	public Date getTimeModify() {
		return timeModify;
	}

	public CashAccountRESTBean setTimeModify(Date timeModify) {
		this.timeModify = timeModify;
		return this;
	}

	public Long getJournalId() {
		return journalId;
	}

	public CashAccountRESTBean setJournalId(Long journalId) {
		this.journalId = journalId;
		return this;
	}

	@Override
	public String toString() {
		return "CashAccountRESTBean [id=" + id +
				", increase=" + increase +
				", reduce=" + reduce +
				", timeModify=" + timeModify +
				", journalId=" + journalId +
				"]";
	}
}
