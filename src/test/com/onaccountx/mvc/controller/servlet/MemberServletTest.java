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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.onaccountx.mvc.model.dao.MemberDAO;
import com.onaccountx.mvc.model.dao.impl.MemberDAOImpl;
import com.onaccountx.mvc.model.entity.Member;
import com.onaccountx.utils.JUnitUtils;
import com.onaccountx.utils.tools.SimulateRequest;

/**
 * <pre>
 * [測試會員 Servlet] 2019-12-19 00:01
 * </pre>
 * 
 * @author TiramiAsu (Email)
 */
@SuppressWarnings({"rawtypes", "static-access"})
class MemberServletTest {

	MemberDAO memberDAO = new MemberDAOImpl();
	
	// 模擬 Request 請求
	SimulateRequest req = new JUnitUtils().getSimulateRequest();
	
	@Test
	final void testUiAdd() {
		assertEquals(String.valueOf(ServletParameters.Dispatcher.UI_MemberServlet_ADD.getUrl()),
				"./WEB-INF/jsp/MemberAdd.jsp");
	}

	@Test
	final void testUiEdit() {
		// 要轉發
		assertEquals(String.valueOf(ServletParameters.Dispatcher.UI_MemberServlet_EDIT.getUrl()),
				"./WEB-INF/jsp/MemberAdd.jsp");
		// 要重定向
		assertEquals(String.valueOf(ServletParameters.Redirect.UI_Member.getUrl()),
				"./WEB-INF/jsp/Member.jsp");
	}

	@Test
	final void testAdd() {
		// 模擬 Request 中參數
		req.setAttribute("name", "asd");
		req.setAttribute("email", "zxc@gmail.com");
		req.setAttribute("phone", "zzxxcc");
		
		// 進入 Servlet
		String name = req.getParameter(Member._NAME);
		String email = req.getParameter(Member._EMAIL);
		String phone = req.getParameter(Member._PHONE);

		// 輸入參數不可為 null 或空字串
		assertNotNull(name);
		assertNotNull(email);
		assertNotNull(phone);
		assertNotEquals("", name);
		assertNotEquals("", email);
		assertNotEquals("", phone);
	}

	@Test
	final void testEdit() {
		// 模擬 Request 中參數
		req.setAttribute("id", 4);
		req.setAttribute("name", "sandy");
		req.setAttribute("email", "sandy@gmail.com");
		req.setAttribute("phone", "0987654987");
		
		// 進入 Servlet
		Long id = Long.parseLong(req.getParameter(Member._ID));
		String name = req.getParameter(Member._NAME);
		String email = req.getParameter(Member._EMAIL);
		String phone = req.getParameter(Member._PHONE);
		
		// 輸入參數不可為 null 或空字串
		assertNotNull(id);
		assertNotNull(name);
		assertNotNull(email);
		assertNotNull(phone);
		assertNotEquals("", id);
		assertNotEquals("", name);
		assertNotEquals("", email);
		assertNotEquals("", phone);
	}

	@Test
	final void testRemove() {
		// 模擬 Request 中參數
		req.setAttribute("id", 2);
		
		// 進入 Servlet
		Long id = Long.parseLong(req.getParameter(Member._ID));
		
		assertTrue(2L == id);
	}
	
	@Test
	final void testSearch() throws Exception {
		List<Member> list = memberDAO.query();
		// list.stream().forEach(System.out::println);
		assertNotNull(list);
		assertEquals(String.valueOf(ServletParameters.Dispatcher.UI_MemberServlet_SEARCH.getUrl()),
				"./WEB-INF/jsp/Member.jsp");
	}

}
