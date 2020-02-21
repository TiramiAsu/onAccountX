/*
 * 
 * 
 * 
 * 
 * 
 * &cOperatey;TiramiAsu
 * 
 */
package com.onaccountx.utils.db;

import java.io.Serializable;
import java.util.List;

/**
 * <pre>
 * [DB 操作] 2020-02-21 10:56
 * </pre>
 * 
 * @author TiramiAsu (Email)
 */
public class Operate implements Serializable {

	private static final long serialVersionUID = 7197450238185118059L;

	public final static int TYPE_LIKE = 1;
	public final static int TYPE_FILTER = 2;
	public final static int TYPE_GreaterEqual = 3;
	public final static int TYPE_LessEqual = 4;
	public final static int TYPE_NOT_EQUAL = 5;
	public final static int TYPE_OR = 6;
	public final static int TYPE_EqualRange = 7;
	public final static int TYPE_NOT_NULL = 8;
	public final static int TYPE_NULL = 9;

	private int operateType;
	private Object value;
	private Object valueStart;
	private Object valueEnd;
	private List<Operate> orList;

	private Operate(int operateType) {
		this.operateType = operateType;
	}

	private Operate(int operateType, Object value) {
		this.operateType = operateType;
		this.value = value;
	}

	private Operate(int operateType, Object valueStart, Object valueEnd) {
		this.operateType = operateType;
		this.valueStart = valueStart;
		this.valueEnd = valueEnd;
	}

	public static Operate like(Object value) {
		return new Operate(Operate.TYPE_LIKE, value);
	}

	public static Operate filter(Object value) {
		return new Operate(Operate.TYPE_FILTER, value);
	}

	public static Operate equalRange(Object valueStart, Object valueEnd) {
		return new Operate(Operate.TYPE_EqualRange, valueStart, valueEnd);
	}

	public static Operate greaterEqual(Object value) {
		return new Operate(Operate.TYPE_GreaterEqual, value);
	}

	public static Operate lessEqual(Object value) {
		return new Operate(Operate.TYPE_LessEqual, value);
	}

	public static Operate notEqual(Object value) {
		return new Operate(Operate.TYPE_NOT_EQUAL, value);
	}

	public static Operate notNull() {
		return new Operate(Operate.TYPE_NOT_NULL);
	}

	public static Operate isNull() {
		return new Operate(Operate.TYPE_NULL);
	}

	public static Operate or(Object value) {
		return new Operate(Operate.TYPE_OR, value);
	}

	public void or(Operate Operate) {
		this.orList.add(Operate);
	}

	public int getOperateType() {
		return operateType;
	}

	public void setOperateType(int operateType) {
		this.operateType = operateType;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public Object getValueStart() {
		return valueStart;
	}

	public void setValueStart(Object valueStart) {
		this.valueStart = valueStart;
	}

	public Object getValueEnd() {
		return valueEnd;
	}

	public void setValueEnd(Object valueEnd) {
		this.valueEnd = valueEnd;
	}

	public List<Operate> getOrList() {
		return orList;
	}

	public void setOrList(List<Operate> orList) {
		this.orList = orList;
	}
}
