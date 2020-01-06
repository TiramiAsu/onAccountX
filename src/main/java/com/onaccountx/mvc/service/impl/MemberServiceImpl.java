/*
 * 
 * 
 * 
 * 
 * 
 * &copy;TiramiAsu
 * 
 */
package com.onaccountx.mvc.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.json.simple.JSONObject;

import com.onaccountx.mvc.model.dao.MemberDAO;
import com.onaccountx.mvc.model.dao.impl.MemberDAOImpl;
import com.onaccountx.mvc.model.entity.Member;
import com.onaccountx.mvc.service.MemberService;

/**
 * <pre>
 * [會員 Service 實作] 2019-12-24 23:45
 * </pre>
 * 
 * @author TiramiAsu (Email)
 */
public class MemberServiceImpl implements MemberService {

	private MemberDAO memberDAO = new MemberDAOImpl();
	
	@Override
	public List<Member> query(String hql) {
		List<Member> list = null;
		try {
			list = memberDAO.query(hql);
		} catch (Exception e) {
			System.err.println(">>> Query Failed, HQL Error <<<");
		}
		return list;
	}

	@Override
	public Member find(Long id) {
		Member member = null;
		try {
			member = memberDAO.find(Member.class, id);
		} catch (Exception e) {
			System.err.println(">>> Find Failed <<<");
		}
		return member;
	}

	@Override
	public void delete(Long id) {
		Member member = null;
		try {
			member = memberDAO.find(Member.class, id);
			memberDAO.delete(member);
		} catch (Exception e) {
			System.err.println(">>> Delete Failed <<<");
		}
	}
	
	@Override
	public void create(Member bean) {
		try {
			memberDAO.create(bean);
		} catch (Exception e) {
			System.err.println(">>> Create Failed <<<");
		}
	}

	@Override
	public void update(Member bean) {
		try {
			memberDAO.update(bean);
		} catch (Exception e) {
			System.err.println(">>> Update Failed <<<");
		}
	}
	
	@Override
	public List<Member> query() {
		
		List<Member> memberList = null;
		
		/* Initial value */
		/* Check */
		/* Search Condition */
		
		try {
			memberList = memberDAO.query(" from " + Member._ENTITY_NAME + " ")
					.stream()
					.sorted((o1, o2) -> o2.getId().compareTo(o1.getId()))
					.peek(System.out::println)
					.collect(Collectors.toList());
			if (memberList == null) {
				throw new Exception(">>> Members Query Failed <<<");
			}
		} catch (Exception e) {
			memberList = null;
			e.printStackTrace();
		}
		return memberList;
	}
	
	@Override
	public List<Member> query(Object jsonObj) {
		
		JSONObject jsonObject = (JSONObject)jsonObj;
		List<Member> memberList = null;
		
		/* Initial value */
		
		Long jId = 0L;
		String jName = "";
		String jEmail = "";
		String jPhone = "";
		
		/* Check */
		
		jId = (jsonObject.get(Member._ID) == null) ? jId : Long.parseLong("" + jsonObject.get(Member._ID));
		jName = (jsonObject.get(Member._NAME) == null) ? jName : "" + jsonObject.get(Member._NAME);
		jEmail = (jsonObject.get(Member._EMAIL) == null) ? jEmail : "" + jsonObject.get(Member._EMAIL);
		jPhone = (jsonObject.get(Member._PHONE) == null) ? jPhone : "" + jsonObject.get(Member._PHONE);

		/* Search Condition */
		
		try {
			memberList = memberDAO.query(" from " + Member._ENTITY_NAME + " ")
					.stream()
					.sorted((o1, o2) -> o2.getId().compareTo(o1.getId()))
					.peek(System.out::println)
					.collect(Collectors.toList());
			if (memberList == null) {
				throw new Exception(">>> Members Query Failed <<<");
			}
		} catch (Exception e) {
			memberList = null;
			e.printStackTrace();
		}
		return memberList;
	}





}
