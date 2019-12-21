/*
 * 
 * 
 * 
 * 
 * 
 * &copy;TiramiAsu
 * 
 */
package com.onaccountx.mvc.controller.servlet;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * [Servlet 參數] 2019-12-21 15:36
 * - TODO 之後修改到 RESTService 中
 * </pre>
 * 
 * @author TiramiAsu (Email)
 */
public class ServletParameters {

	public static Map<String, Object> dispatcherURL = new HashMap<>();
	
	{
		dispatcherURL.put(Dispatcher.UI_MemberServlet_ADD.name, Dispatcher.UI_MemberServlet_ADD.url);
		dispatcherURL.put(Dispatcher.UI_MemberServlet_SEARCH.name, Dispatcher.UI_MemberServlet_SEARCH.url);
	}
	
	public static enum Dispatcher {
		UI_MemberServlet_ADD("UI_member_ADD", "./WEB-INF/jsp/MemberAdd.jsp"),
		UI_MemberServlet_SEARCH("UI_member_SEARCH", "./WEB-INF/jsp/Member.jsp");
		
		private String name;
		private String url;
		
		private Dispatcher(String name, String url) {
			this.name = name;
			this.url = url;
		}

		public String getName() {
			return name;
		}

		public String getUrl() {
			return url;
		}
		
	}
	
}
