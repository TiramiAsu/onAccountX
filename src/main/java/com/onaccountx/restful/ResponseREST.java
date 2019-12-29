/*
 * 
 * 
 * 
 * 
 * 
 * &copy;TiramiAsu
 * 
 */
package com.onaccountx.restful;

import java.io.Serializable;

import javax.ws.rs.core.Response;

import com.onaccountx.utils.ResponseUtils;

import static com.onaccountx.utils.ResponseUtils.*;

/**
 * <pre>
 * [自定義回應] 2019-12-24 16:32
 * </pre>
 * 
 * @author TiramiAsu (Email)
 */
public class ResponseREST implements Serializable {

	private static final long serialVersionUID = 7701162400511270132L;
	
	private int statusCode;
	private String message;
	private Object data;
	private int count;
	private String token;
	
	public ResponseREST() {}

	public ResponseREST(int statusCode) {
		this.statusCode = statusCode;
		this.message = ResponseUtils.getMessage(statusCode);
	}
	
	public Response build() {
		return Response.status(OK)
				.entity(this)
				.build();
	}

	public ResponseREST setStatusCode(int statusCode) {
		this.statusCode = statusCode;
		this.message = ResponseUtils.getMessage(statusCode);
		return this;
	}
	
//	public ResponseREST setMessage(String message) {
//		this.message = message;
//		return this;
//	}
	
	public ResponseREST setData(Object data) {
		this.data = data;
		return this;
	}
	
	public ResponseREST setCount(int count) {
		this.count = count;
		return this;
	}
	
	public ResponseREST setToken(String token) {
		this.token = token;
		return this;
	}
	
	public int getStatusCode() {
		return statusCode;
	}
	
	public String getMessage() {
		return message;
	}
	
	public Object getData() {
		return data;
	}
	
	public int getCount() {
		return count;
	}
	
	public String getToken() {
		return token;
	}
	
	// Sample
	public void sample() {
		// 直接設定
		new ResponseREST(SUCCESS).build();
		
		// 另外設定 -> 需回應不同狀態時(try-catch)
		new ResponseREST().setStatusCode(ERROR_DATABASE).build();
		
		// 若有 entity 需要回傳
		Object entity = new Object();
		new ResponseREST(SUCCESS).setData(entity).build();
	}
	
}
