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
 * - JSONParser > com.googlecode.json-simple | json-simple | 1.1.1
 * - ObjectMapper > org.codehaus.jackson | jackson-mapper-asl | 1.9.13
 * - JsonSanitizer > com.mikesamuel | json-sanitizer | 1.2.0
 * </pre>
 * 
 * @author TiramiAsu (Email)
 */
public interface GenericRESTService {
	
	// GET  | ../service
	public Response queryREST();
	
	// POST | ../service
	public Response queryREST(InputStream in);
	
	// GET | ../service/{id}
	public Response findREST(String id);
	
	// PUT  | ../service
	public Response createREST(InputStream in);
	
	// PUT  | ../service/{id}
	public Response updateREST(InputStream in, String id);
	
	// DELETE | ../service/{id}
	public Response deleteREST(String id);
	
}
