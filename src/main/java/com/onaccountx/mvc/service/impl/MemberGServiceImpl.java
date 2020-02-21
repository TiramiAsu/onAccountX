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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.genericdao.search.SearchResult;
import com.onaccountx.mvc.model.dao.MemberGDAO;
import com.onaccountx.mvc.model.entity.MemberG;
import com.onaccountx.mvc.service.MemberGService;
import com.onaccountx.restful.ResponseREST;
import com.onaccountx.restful.bean.MemberGRESTBean;
import com.onaccountx.utils.ResponseUtils;
import com.onaccountx.utils.db.Operate;

/**
 * <pre>
 * [實作 MemberService] 2020-02-21 12:12
 * </pre>
 * 
 * @author TiramiAsu (Email)
 */
@Service
public class MemberGServiceImpl implements MemberGService {

	@Autowired
	private MemberGDAO memberDAO;

	@Override
	@Transactional
	public boolean create(MemberG bean) {
		bean.setTimeBuild(new Date());
		bean.setTimeModify(new Date());
		return memberDAO.save(bean);
	}

	@Override
	@Transactional
	public MemberG find(Long id) {
		return memberDAO.find(id);
	}

	@Override
	@Transactional
	public boolean update(Long id, MemberG bean) {
		MemberG member = memberDAO.find(id);
		bean.setId(member.getId());
		bean.setTimeBuild(member.getTimeBuild());
		bean.setTimeModify(new Date());
		return memberDAO.save(bean);
	}

	@Override
	@Transactional
	public boolean delete(Long id) {
		return memberDAO.remove(memberDAO.find(id));
	}

	@Override
	@Transactional
	public List<MemberG> query() {
		
		List<MemberG> memberList = null;
		
		/* Initial value */
		/* Check */
		/* Search Condition */
		
		try {
			memberList = memberDAO.findAll()
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
	@Transactional
	public ResponseREST queryREST(Object json) {

		JSONObject jsonObject = (JSONObject) json;
		List<MemberGRESTBean> outputJson = new ArrayList<>();
		Map<String, Object> conds = new HashMap<String, Object>();

		/* initial value */

		Long jId = 0L;
		String jName = "";
		String jEmail = "";
		String jPhone = "";

		/* check */

		jId = (jsonObject.get(MemberG._ID) == null) ? jId : (long) jsonObject.get(MemberG._ID);
		jName = (jsonObject.get(MemberG._NAME) == null) ? jName : (String) jsonObject.get(MemberG._NAME);
		jEmail = (jsonObject.get(MemberG._EMAIL) == null) ? jEmail : (String) jsonObject.get(MemberG._EMAIL);
		jPhone = (jsonObject.get(MemberG._PHONE) == null) ? jPhone : (String) jsonObject.get(MemberG._PHONE);

		/* Search Condition */

		List<MemberG> members = new ArrayList<>();
		SearchResult<Object> sr = new SearchResult<Object>();

		if (jName != null && !jName.equals("")) {
			// conds 會轉成 SQL 再去 DB SELECT
			conds.put(MemberG._NAME, Operate.like("%" + jName + "%")); // 模糊查詢
		}

		if (jEmail != null && !jEmail.equals("")) {
			conds.put(MemberG._EMAIL, Operate.like("%" + jEmail + "%")); // 模糊查詢
		}

		if (jPhone != null && !jPhone.equals("")) {
			conds.put(MemberG._PHONE, Operate.like("%" + jPhone + "%")); // 模糊查詢
		}

		// 以 "id" 為順序
		ResponseREST responseMeg = null;
		try {
			sr = memberDAO.query(MemberG._ID, true, conds);

			/* choose output data */

			for (Object obj : sr.getResult()) {
				members.add((MemberG) obj);
			}

			for (MemberG member : members) {
				MemberGRESTBean bean = new MemberGRESTBean();

				bean.setId(member.getId());
				bean.setName(member.getName());
				bean.setEmail(member.getEmail());
				bean.setPhone(member.getPhone());
				bean.setTimeModify(member.getTimeModify());

				System.out.println(bean.toString());
				outputJson.add(bean);
			}
			responseMeg = new ResponseREST(ResponseUtils.OK).setData(outputJson);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (!responseMeg.equals(null)) ? responseMeg : new ResponseREST(ResponseUtils.ERROR_DATABASE).setData(outputJson);
	}
}
