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
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * <pre>
 * [Report Journal] 2020-04-23 15:54
 * </pre>
 * 
 * @author TiramiAsu (Email)
 */
@Entity
@Table(name = "report_journal")
public class ReportJournal implements Serializable {

	private static final long serialVersionUID = 1668394944654619149L;

	public final static String _JSON_NAME = "reportJournal";
	public final static String _ID = "id";
	public final static String _DEBIT = "debit";
	public final static String _INCREASE = "increase";
	public final static String _REDUSE = "reduce";
	public final static String _TIME_DATE = "timeDate";

	@Id
	@Column(name = "id")
	private Long id;

	@Column(name = "code")
	private String code;

	@Column(name = "name")
	private String name;

	@Column(name = "increase")
	private Integer increase;

	@Column(name = "reduce")
	private Integer reduce;

	@Column(name = "amount")
	private Integer amount;

	@Column(name = "time_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date timeDate;

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

	public Integer getIncrease() {
		return increase;
	}

	public void setIncrease(Integer increase) {
		this.increase = increase;
	}

	public Integer getReduce() {
		return reduce;
	}

	public void setReduce(Integer reduce) {
		this.reduce = reduce;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Date getTimeDate() {
		return timeDate;
	}

	public void setTimeDate(Date timeDate) {
		this.timeDate = timeDate;
	}

	@Override
	public String toString() {
		return "ReportJournal [id=" + id +
				", code=" + code +
				", name=" + name +
				", increase=" + increase +
				", reduce=" + reduce +
				", amount=" + amount +
				", timeDate=" + timeDate +
				"]";
	}
}
