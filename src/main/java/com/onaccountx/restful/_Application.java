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

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;

/**
 * <pre>
 * [應用 JAX-RS] 2019-12-26 10:16
 * - 
 * </pre>
 * 
 * @author TiramiAsu (Email)
 */
@ApplicationPath("/srv")
public final class _Application extends ResourceConfig {
	public _Application() {
		packages("com.onaccountx.restful");
	}
}
