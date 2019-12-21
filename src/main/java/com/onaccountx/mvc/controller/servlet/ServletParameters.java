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

/**
 * <pre>
 * [Servlet 參數] 2019-12-21 15:36
 * - TODO 之後修改到 RESTService 中
 * </pre>
 * 
 * @author TiramiAsu (Email)
 */
public class ServletParameters {
	
	public static enum Dispatcher {
		UI_MemberServlet_ADD("./WEB-INF/jsp/MemberAdd.jsp"),
		UI_MemberServlet_SEARCH("./WEB-INF/jsp/Member.jsp"),
		UI_MemberServlet_EDIT("./WEB-INF/jsp/MemberAdd.jsp"); // 與 ADD 同 path
		
		private String url;
		
		private Dispatcher(String url) {
			this.url = url;
		}

		public String getUrl() {
			return url;
		}
		
	}
	
	public static enum Redirect {
		UI_Member("./WEB-INF/jsp/Member.jsp");
		
		private String url;
		
		private Redirect(String url) {
			this.url = url;
		}

		public String getUrl() {
			return url;
		}
		
	}
	
}
