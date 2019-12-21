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
import static org.junit.jupiter.api.Assertions.fail;

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
		// 測試轉發路徑是否正確
		assertEquals(String.valueOf(ServletParameters.Dispatcher.UI_MemberServlet_ADD.getUrl()),
				"./WEB-INF/jsp/MemberAdd.jsp");
	}

	@Test
	final void testUiEdit() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	final void testAdd() {
		// 模擬 Request 中參數
		req.setAttribute("id", 1);
		req.setAttribute("name", "asd");
		req.setAttribute("email", "zxc@gmail.com");
		req.setAttribute("phone", "zzxxcc");
		
		// 進入 Servlet
		Long id = Long.parseLong(req.getParameter(Member._ID));
		String name = req.getParameter(Member._NAME);
		String email = req.getParameter(Member._EMAIL);
		String phone = req.getParameter(Member._PHONE);
		
		assertTrue((1L == id) &&
				("asd".equals(name)) &&
				("zxc@gmail.com".equals(email)) &&
				("zzxxcc".equals(phone)));
	}

	@Test
	final void testEdit() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	final void testRemove() {
		fail("Not yet implemented"); // TODO
	}
	
	@Test
	final void testSearch() {
		List<Member> list = memberDAO.query(" from " + Member._ENTITY_NAME + " ");
		// list.stream().forEach(System.out::println);
		assertNotNull(list);
		assertEquals(String.valueOf(ServletParameters.Dispatcher.UI_MemberServlet_SEARCH.getUrl()),
				"./WEB-INF/jsp/Member.jsp");
	}

}
