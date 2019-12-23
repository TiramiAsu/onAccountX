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

/**
 * <pre>
 * [判斷工具] 2019-12-22 00:39
 * </pre>
 * 
 * @author TiramiAsu (Email)
 */
public class JudgeUtils {
	
	public static boolean isNotNull(Object... objs) {
		for (Object obj : objs) {
			if (obj.equals(null)) {
				return false;
			}
		}
		return true;
	}
	
	public static boolean isNotEmptyString(Object... objs) {
		for (Object obj : objs) {
			if (obj.equals("")) {
				return false;
			}
		}
		return true;
	}
	
	public static boolean isNotNullOrEmptyString(Object... objs) {
		return isNotEmptyString(objs) || isNotNull(objs);
	}
	
}
