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

/**
 * <pre>
 * [狀態碼] 2019-12-27 14:37
 * - 新增後, 於 ResponseUtils 中設定參數
 * </pre>
 * 
 * @author TiramiAsu (Email)
 */
public enum StatusCode {
	
	/** Generic Status */
	
	// 2: Success
	Okey(200, "OK"),
	
	// 4: Client Error
	Unauthorized(401, "Unauthorized"),
	
	// 5: Server Error
	Connect_Timeout(599, "Connect Timeout Error"),
	
	/** Customize Status */
	// 2: Success
	Success_Access(200, "Access Success"),
	
	// 5: Server Error
	Error_Database(595, "Database Error"),
	Error_Input(596, "Input Error"),
	Error_Parse(597, "Parse Error"),
	Error_Internal(598, "Internal Error"),
	
	// 9: Other Error
	Error_Unknown(999, "Unknown Error");
	
	int code;
	String msg;
	
	private StatusCode(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}
	
}
