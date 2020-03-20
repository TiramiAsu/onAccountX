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
import com.onaccountx.mvc.model.dao.SubjectDAO;
import com.onaccountx.mvc.model.entity.Subject;
import com.onaccountx.mvc.service.SubjectService;
import com.onaccountx.restful.ResponseREST;
import com.onaccountx.restful.bean.SubjectRESTBean;
import com.onaccountx.utils.JsonUtils;
import com.onaccountx.utils.db.Operate;

/**
 * <pre>
 * [實作 SubjectService] 2020-03-20 00:18
 * </pre>
 * 
 * @author TiramiAsu (Email)
 */
@Service
public class SubjectServiceImpl implements SubjectService {

	@Autowired
	private SubjectDAO subjectDAO;

	public void setAccCashDAO(SubjectDAO subjectDAO) {
		this.subjectDAO = subjectDAO;
	}

	@Override
	@Transactional
	public boolean create(Subject bean) {
		bean.setTimeBuild(new Date());
		bean.setTimeModify(new Date());
		return subjectDAO.save(bean);
	}

	@Override
	@Transactional
	public Subject find(Long id) {
		return subjectDAO.find(id);
	}

	@Override
	@Transactional
	public boolean update(Subject bean) {
		bean.setTimeModify(new Date());
		return subjectDAO.save(bean);
	}

	@Override
	@Transactional
	public boolean delete(Long id) {
		return subjectDAO.remove(subjectDAO.find(id));
	}

	@Override
	@Transactional
	public List<Subject> query() {

		List<Subject> subjectList = null;

		/* Initial value */
		/* Check */
		/* Search Condition */

		try {
			subjectList = subjectDAO.findAll().stream()
					.sorted((o1, o2) -> o1.getId().compareTo(o2.getId()))
//					.peek(System.out::println)
					.collect(Collectors.toList());
			if (subjectList == null) {
				throw new Exception(">>> Subject Query Failed <<<");
			}
		} catch (Exception e) {
			subjectList = null;
			e.printStackTrace();
		}
		return subjectList;
	}

	@Override
	@Transactional
	public ResponseREST queryREST(Object json) {

		JSONObject jsonObject = JsonUtils.parseAttributes("subject", json);
		List<SubjectRESTBean> outputJson = new ArrayList<>();
		Map<String, Object> conds = new HashMap<String, Object>();

		/* initial value */

		Long jId = -1L;
		String jCode = "";
		String jName = "";

		/* check */

		jId = (jsonObject.get(Subject._ID) == null) ? jId : Long.parseLong(jsonObject.get(Subject._ID) + "");
		jCode = (jsonObject.get(Subject._CODE) == null) ? jCode : jsonObject.get(Subject._CODE) + "";
		jName = (jsonObject.get(Subject._NAME) == null) ? jName : jsonObject.get(Subject._NAME) + "";

		/* Search Condition */

		List<Subject> subjectList = new ArrayList<>();
		SearchResult<Object> sr = new SearchResult<Object>();

		if (jId != -1L) {
			conds.put(Subject._ID, jId);
		}

		if (jCode != null && !jCode.equals("")) {
			conds.put(Subject._CODE, Operate.like("%" + jCode + "%"));
		}

		if (jName != null && !jName.equals("")) {
			conds.put(Subject._NAME, Operate.like("%" + jName + "%"));
		}

		ResponseREST responseMeg = null;

		try {
			sr = subjectDAO.query(Subject._CODE, false, conds);

			/* choose output data */

			for (Object obj : sr.getResult()) {
				subjectList.add((Subject) obj);
			}

			for (Subject subject : subjectList) {
				SubjectRESTBean bean = new SubjectRESTBean();

				bean.setId(subject.getId());
				bean.setCode(subject.getCode());
				bean.setName(subject.getName());
				bean.setTimeModify(subject.getTimeModify());

				outputJson.add(bean);
			}
			responseMeg = new ResponseREST(OK).setData(outputJson);
		} catch (Exception e) {
			responseMeg = new ResponseREST(ERROR_DATABASE).setData(outputJson);
			e.printStackTrace();
		}
		return responseMeg;
	}
}
