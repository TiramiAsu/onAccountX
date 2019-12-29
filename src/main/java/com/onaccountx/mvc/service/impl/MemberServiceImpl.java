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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.json.simple.JSONObject;

import com.onaccountx.generic.GenericServiceImpl;
import com.onaccountx.mvc.model.dao.MemberDAO;
import com.onaccountx.mvc.model.dao.impl.MemberDAOImpl;
import com.onaccountx.mvc.model.entity.Member;
import com.onaccountx.mvc.service.MemberService;
import com.onaccountx.restful.bean.MemberRESTBean;

/**
 * <pre>
 * [會員 Service 實作] 2019-12-24 23:45
 * </pre>
 * 
 * @author TiramiAsu (Email)
 */
public class MemberServiceImpl extends GenericServiceImpl<Member, MemberDAOImpl> implements MemberService {

	private MemberDAO memberDAO  = new MemberDAOImpl();
	
	@Override
	public void create(JSONObject jsonObject) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(JSONObject jsonObject) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(JSONObject jsonObject) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public MemberRESTBean find(JSONObject jsonObject) {
		// TODO Auto-generated method stub
		return null;
	}
	
public List<MemberRESTBean> query(JSONObject jsonObject) {
		
		List<MemberRESTBean> responseData = new ArrayList<>();
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
			for (Member m : memberList) {
				MemberRESTBean bean = new MemberRESTBean();
				bean.setId(m.getId());
				bean.setName(m.getName());
				bean.setEmail(m.getEmail());
				bean.setPhone(m.getPhone());
				responseData.add(bean);
			}
		} catch (Exception e) {
			responseData = null;
			e.printStackTrace();
		}
		return responseData;
	}

}
