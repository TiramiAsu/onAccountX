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

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.onaccountx.mvc.model.dao.MemberDAO;
import com.onaccountx.mvc.model.dao.impl.MemberDAOImpl;
import com.onaccountx.mvc.model.entity.Member;

/**
 * <pre>
 * [會員 Servlet] 2019-12-18 23:29
 * </pre>
 * 
 * @author TiramiAsu (Email)
 */
@WebServlet("/member")
public class MemberServlet extends HttpServlet {
	
	private static final long serialVersionUID = -6430683612930412987L;
	
	private MemberDAO memberDAO = new MemberDAOImpl();
	
	protected void doHandle(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		// TODO 之後改用 Filter
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		
		String action = req.getParameter("action");
		
		switch (action) {
		case "uiAdd": // button add
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
		}
	}

	protected void uiAdd(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.getRequestDispatcher(String.valueOf(ServletParameters.Dispatcher.UI_MemberServlet_ADD.getUrl()))
		   .forward(req, resp);
	}
	
	protected void uiEdit(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
	}
	
	protected void add(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try {
			String name = req.getParameter(Member._NAME);
			String email = req.getParameter(Member._EMAIL);
			String phone = req.getParameter(Member._PHONE);
			
			Member member = new Member(name, email, phone, new Date(), new Date());
			
			memberDAO.create(member);
			req.setAttribute("msg", "\"" + member.getName() + "\" add Success!!");
		} catch(Exception e) {
			req.setAttribute("msg", "Failed to add Member, Please try again.");
		}
		resp.getWriter().print(req.getAttribute("msg"));
	}
	
	protected void edit(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
	}
	
	protected void remove(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
	}
	
	protected void search(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setAttribute("memberList", memberDAO.query(" from " + Member._ENTITY_NAME + " "));
		req.getRequestDispatcher(String.valueOf(ServletParameters.Dispatcher.UI_MemberServlet_SEARCH.getUrl()))
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
