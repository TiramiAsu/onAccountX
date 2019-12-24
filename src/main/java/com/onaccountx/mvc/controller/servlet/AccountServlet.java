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

import static com.onaccountx.utils.JudgeUtils.isNotNull;
import static com.onaccountx.utils.JudgeUtils.isNotNullOrEmptyString;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.onaccountx.mvc.model.dao.AccountDAO;
import com.onaccountx.mvc.model.dao.MemberDAO;
import com.onaccountx.mvc.model.dao.impl.AccountDAOImpl;
import com.onaccountx.mvc.model.dao.impl.MemberDAOImpl;
import com.onaccountx.mvc.model.entity.Account;
import com.onaccountx.mvc.model.entity.Member;

/**
 * <pre>
 * [帳號 Servlet] 2019-12-19 08:24
 * </pre>
 * 
 * @author TiramiAsu (Email)
 */
@WebServlet("/account")
public class AccountServlet extends HttpServlet {
	
	private static final long serialVersionUID = 3691354769236137407L;
	
	private AccountDAO accountDAO = new AccountDAOImpl();
	private MemberDAO memberDAO = new MemberDAOImpl();

	protected void doHandle(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Filter
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		
		String action = req.getParameter("action");
		
		switch (action) {
		case "uiAdd": // button Add
			uiAdd(req, resp);
			break;
		case "uiEdit": // button edit
			uiEdit(req, resp);
			break;
		case "add":
			add(req, resp);
			break;
		case "edit":
			edit(req, resp);
			break;
		case "remove":
			remove(req, resp);
			break;
		case "search":
			search(req, resp);
			break;
		default:
		}
	}
	
	protected void uiAdd(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		List<Member> members = memberDAO.query(" SELECT m FROM " + Member._ENTITY_NAME + " m ");
		req.setAttribute("memberList", members);
		
		req.getRequestDispatcher(String.valueOf(ServletParameters.Dispatcher.UI_AccountServlet_ADD.getUrl()))
		   .forward(req, resp);
	}
	
	protected void uiEdit(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try {
			Long id = Long.parseLong(req.getParameter(Account._ID));
			if (isNotNullOrEmptyString(id)) {
				Account account = accountDAO.find(Account.class, id);
				Member member = memberDAO.find(Member.class, account.getMemberId());
				List<Member> members = memberDAO.query(" SELECT m FROM " + Member._ENTITY_NAME + " m ");
				if (isNotNull(account, member, members)) {
					req.setAttribute("account", account);
					req.setAttribute("member", member);
					req.setAttribute("memberList", members);
					req.getRequestDispatcher(String.valueOf(ServletParameters.Dispatcher.UI_AccountServlet_EDIT.getUrl()))
					.forward(req, resp);
				} else {
					throw new Exception(">>> Not Found Account by ID: " + id + " <<<");
				}
			} else {
				throw new Exception(">>> id is Null <<<");
			}
		} catch (Exception e) {
			e.printStackTrace();
			// 重導至所有列表
			resp.sendRedirect(String.valueOf(ServletParameters.Redirect.UI_Account.getUrl()));
		}
	}
	
	protected void add(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try {
			String account = req.getParameter(Account._ACCOUNT);
			String password = req.getParameter(Account._PASSWORD);
			Long memberId = Long.parseLong(req.getParameter(Account._MEMBER_ID));
			
			if (isNotNullOrEmptyString(account, password, memberId)) {
				Account acc = new Account(account, password, Account.VALUE_ENABLE, 0,
						new Date(), new Date(), new Date(), memberId);
				accountDAO.create(acc);
				req.setAttribute("msg", "\"" + acc.getAccount() + "\" add Success!!");
			} else {
				throw new Exception(">>> Some attribute is Null <<<");
			}
		} catch(Exception e) {
			req.setAttribute("msg", "Failed to add Account, Please try again.");
		}
		search(req, resp);
	}
	
	protected void edit(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try {
			Long id = Long.parseLong(req.getParameter(Account._ID));
			Long memberId = Long.parseLong(req.getParameter(Account._MEMBER_ID));
			String account = req.getParameter(Account._ACCOUNT);
			String password = req.getParameter(Account._PASSWORD);
			
			if (isNotNullOrEmptyString(id, memberId, account, password)) {
				Account acc = accountDAO.find(Account.class, id);
				if (isNotNull(acc)) {
					acc.setAccount(account);
					acc.setPassword(password);
					acc.setMemberId(memberId);
					acc.setTimeModify(new Date());
					accountDAO.update(acc);
					req.setAttribute("msg", "\"" + acc.getAccount() + "\" edit Success!!");
				} else {
					throw new Exception(">>> Not Found Account by ID: " + id + " <<<");
				}
			} else {
				throw new Exception(">>> Some attribute is Null <<<");
			}
		} catch (Exception e) {
			req.setAttribute("msg", "Failed to update Account, Please check it and try again.");
		}
		search(req, resp);
	}
	
	protected void remove(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try {
			Long id = Long.parseLong(req.getParameter(Account._ID));
			
			if (isNotNullOrEmptyString(id)) {
				Account account = accountDAO.find(Account.class, id);
				if (isNotNull(account)) {
					accountDAO.delete(account);
					req.setAttribute("msg", "\"" + account.getAccount() + "\" remove Success!!");
				} else {
					throw new Exception(">>> Not Found Account by ID: " + id + " <<<");
				}
			} else {
				throw new Exception(">>> id is Null <<<");
			}
		} catch(Exception e) {
			e.printStackTrace();
			req.setAttribute("msg", "Failed to remove Account, Please try again.");
		}
		search(req, resp);
	}
	
	protected void search(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		List<Account> accountList = accountDAO.query(" from " + Account._ENTITY_NAME + " ")
				.stream()
				.sorted((o1, o2) -> o2.getId().compareTo(o1.getId()))
//				.peek(System.out::println)
				.collect(Collectors.toList());
		req.setAttribute("accountList", accountList);
		req.setAttribute("memberList", memberDAO.query(" from " + Member._ENTITY_NAME + " "));
		req.getRequestDispatcher(String.valueOf(ServletParameters.Dispatcher.UI_AccountServlet_SEARCH.getUrl()))
		   .forward(req, resp);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doHandle(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doHandle(req, resp);
	}
	
}
