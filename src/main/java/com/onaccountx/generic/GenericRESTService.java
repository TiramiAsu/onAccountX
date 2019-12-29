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

import java.io.InputStream;

import javax.ws.rs.core.Response;

/**
 * <pre>
 * [通用 REST Service 定義] 2019-12-24 23:11
 * </pre>
 * 
 * @author TiramiAsu (Email)
 */
public interface GenericRESTService {

	public Response createREST(InputStream in);
	public Response updateREST(InputStream in);
	public Response deleteREST(InputStream in);
	public Response findREST(InputStream in);
	public Response queryREST(InputStream in);
	
}
