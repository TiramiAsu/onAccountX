/*
 * 
 * 
 * 
 * 
 * 
 * &copy;TiramiAsu
 * 
 */
package com.onaccountx.utils.tools;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * [模擬 Request] 2019-12-21 14:21
 * </pre>
 * 
 * @author TiramiAsu (Email)
 */
public class SimulateRequest {

	static Map<String, Object> req = new HashMap<>();

	public SimulateRequest() {}
	
	public static String getParameter(String key) {
		return req.get(key) + "";
	}
	
	public static void setAttribute(String key, Object value) {
		req.put(key, value);
	}
	
}
