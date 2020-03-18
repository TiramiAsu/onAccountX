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
public class AccCashRESTBean implements Serializable {

	private static final long serialVersionUID = -3857232576005492446L;

	private Long id;
	private Integer increase;
	private Integer reduce;
	private Date timeModify;
	private Long jId;

	public AccCashRESTBean() {}

	public AccCashRESTBean(Integer increase, Integer reduce, Date timeModify, Long jId) {
		this.increase = increase;
		this.reduce = reduce;
		this.timeModify = timeModify;
		this.jId = jId;
	}

	public Long getId() {
		return id;
	}

	public AccCashRESTBean setId(Long id) {
		this.id = id;
		return this;
	}

	public Integer getIncrease() {
		return increase;
	}

	public AccCashRESTBean setIncrease(Integer increase) {
		this.increase = increase;
		return this;
	}

	public Integer getReduce() {
		return reduce;
	}

	public AccCashRESTBean setReduce(Integer reduce) {
		this.reduce = reduce;
		return this;
	}

	public Date getTimeModify() {
		return timeModify;
	}

	public AccCashRESTBean setTimeModify(Date timeModify) {
		this.timeModify = timeModify;
		return this;
	}

	public Long getJId() {
		return jId;
	}

	public AccCashRESTBean setJId(Long jId) {
		this.jId = jId;
		return this;
	}

	@Override
	public String toString() {
		return "AccCashRESTBean [id=" + id +
				", increase=" + increase +
				", reduce=" + reduce +
				", timeModify=" + timeModify +
				", jId=" + jId +
				"]";
	}
}
