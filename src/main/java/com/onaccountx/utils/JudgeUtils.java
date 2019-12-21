/*
 * 
 * 
 * 
 * 
 * 
 * &copy;TiramiAsu
 * 
 */
package com.onaccountx.utils;

import java.util.Map;

/**
 * <pre>
 * [判斷工具] 2019-12-22 00:39
 * </pre>
 * 
 * @author TiramiAsu (Email)
 */
public class JudgeUtils {
	
	public static boolean isNull(Object... objs) {
		for (Object obj : objs) {
			if (obj.equals(null)) {
				return false;
			}
		}
		return true;
	}
	
	public static boolean isEmptyString(Object... objs) {
		for (Object obj : objs) {
			if (obj.equals("")) {
				return false;
			}
		}
		return true;
	}
	
	public static boolean isNullOrEmptyString(Object... objs) {
		for (Object obj : objs) {
			if (obj.equals("") || obj.equals(null)) {
				return false;
			}
		}
		return true;
	}
	
}
