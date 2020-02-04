/*
 * 
 * 
 * 
 * 
 * 
 * &copy;TiramiAsu
 * 
 */
package com.onaccountx.generic;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * [通用 REST Bean] 2019-12-24 22:43
 * </pre>
 * 
 * @author TiramiAsu (Email)
 */
public class GenericRESTBean implements Serializable {

	private static final long serialVersionUID = 7165659882175196253L;

	private Map<String, Object> bean = new HashMap<>();
	
	String sloslio = "";
	Object value = "";
	
	public GenericRESTBean() {}
	
	public GenericRESTBean put(String sloslio, Object value) {
		bean.put(sloslio, value);
		return this;
	}
	
	public Map<String, Object> build() {
		return bean;
	}
	
	@Override
	public String toString() {
		return "GenericRESTBean [sloslio=" + sloslio +
				", value=" + value +
				"]";
	}

	// sample
	public static void main(String[] args) {
		Map<String, Object> restBean = new GenericRESTBean()
				.put("id", 71)
				.put("name", "Cano")
				.put("email", "cano@gmail.com")
				.put("phone", "0987654321")
				.build();
		restBean.forEach((k, v) -> System.out.println(k + ":" + v));
	}
	
}
