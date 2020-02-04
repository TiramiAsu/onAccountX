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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onaccountx.mvc.model.dao.MemberDAO;
import com.onaccountx.mvc.model.entity.Member;
import com.onaccountx.mvc.service.MemberService;

/**
 * <pre>
 * [會員 Service 實作] 2019-12-24 23:45
 * </pre>
 * 
 * @author TiramiAsu (Email)
 */
@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	private MemberDAO memberDAO;
	
	public void setMemberDAO(MemberDAO memberDAO) {
		this.memberDAO = memberDAO;
	}
	
	@Override
	public void create(Member bean) {
		try {
			memberDAO.create(bean);
		} catch (Exception e) {
			System.err.println(">>> Create Failed <<<");
			System.err.println(e.getMessage());
		}
	}
	
	@Override
	public List<Member> query() {
		
		List<Member> memberList = null;
		
		/* Initial value */
		/* Check */
		/* Search Condition */
		
		try {
			memberList = memberDAO.query()
					.stream()
					.sorted((o1, o2) -> o2.getId().compareTo(o1.getId()))
//					.peek(System.out::println)
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
	public void update(Long id, Member bean) {
		try {
			memberDAO.update(id, bean);
		} catch (Exception e) {
			System.err.println(">>> Update Failed <<<");
			System.err.println(e.getMessage());
		}
	}

	@Override
	public void delete(Long id) {
		try {
			if (!memberDAO.find(Member.class, id).equals(null)) {
				memberDAO.delete(id);
			}
		} catch (Exception e) {
			System.err.println(">>> Delete Failed <<<");
		}
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
			memberList = memberDAO.query()
					.stream()
					.sorted((o1, o2) -> o2.getId().compareTo(o1.getId()))
//					.peek(System.out::println)
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
