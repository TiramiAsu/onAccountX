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

import com.onaccountx.mvc.model.dao.AccountDAO;
import com.onaccountx.mvc.model.dao.MemberDAO;
import com.onaccountx.mvc.model.dao.impl.AccountDAOImpl;
import com.onaccountx.mvc.model.dao.impl.MemberDAOImpl;
import com.onaccountx.mvc.model.entity.Account;
import com.onaccountx.mvc.model.entity.Member;
import com.onaccountx.utils.JUnitUtils;
import com.onaccountx.utils.tools.SimulateRequest;

/**
 * <pre>
 * [測試帳號 Servlet] 2019-12-19 13:57
 * </pre>
 * 
 * @author TiramiAsu (Email)
 */
@SuppressWarnings({"rawtypes", "static-access"})
class AccountServletTest {
	
	AccountDAO accountDAO = new AccountDAOImpl();
	MemberDAO memberDAO = new MemberDAOImpl();
	
	// 模擬 Request 請求
	static SimulateRequest req = new JUnitUtils().getSimulateRequest();

	@Test
	final void testUiAdd() throws Exception {
		List<Member> members = memberDAO.query();
		assertNotNull(members);
		assertEquals(String.valueOf(ServletParameters.Dispatcher.UI_AccountServlet_ADD.getUrl()),
				"./WEB-INF/jsp/AccountAdd.jsp");
	}

	@Test
	final void testAdd() {
		// Request
		req.setAttribute("account", "asd");
		req.setAttribute("password", "zzxxcc");
		req.setAttribute("memberId", 1);
		
		// Servlet
		String account = req.getParameter(Account._ACCOUNT);
		String password = req.getParameter(Account._PASSWORD);
		Long id = Long.parseLong(req.getParameter(Account._MEMBER_ID));

		// 輸入參數不可為 null 或空字串
		assertNotNull(account);
		assertNotNull(password);
		assertNotNull(id);
		assertNotEquals("", account);
		assertNotEquals("", password);
		assertNotEquals("", id);
	}

	@Test
	final void testUiEdit() {
		assertEquals(String.valueOf(ServletParameters.Dispatcher.UI_AccountServlet_EDIT.getUrl()),
				"./WEB-INF/jsp/AccountAdd.jsp");
		assertEquals(String.valueOf(ServletParameters.Redirect.UI_Account.getUrl()),
				"./WEB-INF/jsp/Account.jsp");
	}

	@Test
	final void testEdit() {
		// 模擬 Request 中參數
		req.setAttribute("account", "vans");
		req.setAttribute("password", "9999");
		req.setAttribute("memberId", "4");
		
		// 進入 Servlet
		String account = req.getParameter(Account._ACCOUNT);
		String password = req.getParameter(Account._PASSWORD);
		Long memberId = Long.parseLong(req.getParameter(Account._MEMBER_ID));
		
		// 輸入參數不可為 null 或空字串
		assertNotNull(account);
		assertNotNull(password);
		assertNotNull(memberId);
		assertNotEquals("", account);
		assertNotEquals("", password);
		assertNotEquals("", memberId);
	}

	@Test
	final void testRemove() {
		// 模擬 Request 中參數
		req.setAttribute("id", 2);
		
		// 進入 Servlet
		Long id = Long.parseLong(req.getParameter(Account._ID));
		
		assertTrue(2L == id);
	}

	@Test
	final void testSearch() throws Exception {
		List<Account> list = accountDAO.query();
		assertNotNull(list);
		assertEquals(String.valueOf(ServletParameters.Dispatcher.UI_AccountServlet_SEARCH.getUrl()),
				"./WEB-INF/jsp/Account.jsp");
	}

}
