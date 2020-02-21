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

import static com.onaccountx.utils.ResponseUtils.ERROR_DATABASE;
import static com.onaccountx.utils.ResponseUtils.OK;

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
import com.onaccountx.mvc.model.dao.MemberDAO;
import com.onaccountx.mvc.model.entity.Member;
import com.onaccountx.mvc.service.MemberService;
import com.onaccountx.restful.ResponseREST;
import com.onaccountx.restful.bean.MemberRESTBean;
import com.onaccountx.utils.db.Operate;

/**
 * <pre>
 * [實作 MemberService] 2019-12-24 23:45
 * </pre>
 * 
 * @author TiramiAsu (Email)
 */
@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	private MemberDAO memberDAO;

	@Override
	@Transactional
	public boolean create(Member bean) {
		bean.setTimeBuild(new Date());
		bean.setTimeModify(new Date());
		return memberDAO.save(bean);
	}

	@Override
	@Transactional
	public Member find(Long id) {
		return memberDAO.find(id);
	}

	@Override
	@Transactional
	public boolean update(Long id, Member bean) {
		Member member = memberDAO.find(id);
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
	public List<Member> query() {

		List<Member> memberList = null;

		/* Initial value */
		/* Check */
		/* Search Condition */

		try {
			memberList = memberDAO.findAll().stream().sorted((o1, o2) -> o2.getId().compareTo(o1.getId()))
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
		List<MemberRESTBean> outputJson = new ArrayList<>();
		Map<String, Object> conds = new HashMap<String, Object>();

		/* initial value */

		Long jId = 0L;
		String jName = "";
		String jEmail = "";
		String jPhone = "";

		/* check */

		jId = (jsonObject.get(Member._ID) == null) ? jId : (long) jsonObject.get(Member._ID);
		jName = (jsonObject.get(Member._NAME) == null) ? jName : (String) jsonObject.get(Member._NAME);
		jEmail = (jsonObject.get(Member._EMAIL) == null) ? jEmail : (String) jsonObject.get(Member._EMAIL);
		jPhone = (jsonObject.get(Member._PHONE) == null) ? jPhone : (String) jsonObject.get(Member._PHONE);

		/* Search Condition */

		List<Member> members = new ArrayList<>();
		SearchResult<Object> sr = new SearchResult<Object>();

		if (jName != null && !jName.equals("")) {
			// conds 會轉成 SQL 再去 DB SELECT
			conds.put(Member._NAME, Operate.like("%" + jName + "%")); // 模糊查詢
		}

		if (jEmail != null && !jEmail.equals("")) {
			conds.put(Member._EMAIL, Operate.like("%" + jEmail + "%")); // 模糊查詢
		}

		if (jPhone != null && !jPhone.equals("")) {
			conds.put(Member._PHONE, Operate.like("%" + jPhone + "%")); // 模糊查詢
		}

		// 以 "id" 為順序
		ResponseREST responseMeg = null;
		try {
			sr = memberDAO.query(Member._ID, true, conds);

			/* choose output data */

			for (Object obj : sr.getResult()) {
				members.add((Member) obj);
			}

			for (Member member : members) {
				MemberRESTBean bean = new MemberRESTBean();

				bean.setId(member.getId());
				bean.setName(member.getName());
				bean.setEmail(member.getEmail());
				bean.setPhone(member.getPhone());
				bean.setTimeModify(member.getTimeModify());

				System.out.println(bean.toString());
				outputJson.add(bean);
			}
			responseMeg = new ResponseREST(OK).setData(outputJson);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (!responseMeg.equals(null)) ? responseMeg
				: new ResponseREST(ERROR_DATABASE).setData(outputJson);
	}
}
