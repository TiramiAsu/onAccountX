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
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * <pre>
 * [現金帳] 2020-03-18 14:54
 * </pre>
 * 
 * @author TiramiAsu (Email)
 */
@Entity
@Table(name = "acc_cash")
public class CashAccount implements Serializable {

	private static final long serialVersionUID = -3225417063836407455L;

	public final static String _JSON_NAME = "cashAccount";
	public final static String _ID = "journalId";
	public final static String _INCREASE = "increase";
	public final static String _REDUCE = "reduce";
	public final static String _TIME_MODIFY = "timeModify";
	public final static String _TIME_BUILD = "timeBuild";

	@Id
	@OneToOne
	@JoinColumn(name = "journal_id",
	foreignKey = @ForeignKey(
			name = "journal_fk",
			value = ConstraintMode.CONSTRAINT))
	private Journal journal;

	@Column(name = "increase", nullable = false)
	private Integer increase;

	@Column(name = "reduce", nullable = false)
	private Integer reduce;

	@Column(name = "time_modify", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date timeModify;

	@Column(name = "time_build", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date timeBuild;

	public CashAccount() {}

	public CashAccount(Journal journal, Integer increase, Integer reduce) {
		this.journal = journal;
		this.increase = increase;
		this.reduce = reduce;
	}

	public CashAccount(Journal journal, Integer increase, Integer reduce, Date timeModify, Date timeBuild) {
		this.journal = journal;
		this.increase = increase;
		this.reduce = reduce;
		this.timeModify = timeModify;
		this.timeBuild = timeBuild;
	}

	public Journal getJournal() {
		return journal;
	}

	public void setJournal(Journal journal) {
		this.journal = journal;
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
		return "CashAccount [increase=" + increase +
				", reduce=" + reduce +
				", timeModify=" + timeModify +
				", timeBuild=" + timeBuild +
				"]";
	}
}
