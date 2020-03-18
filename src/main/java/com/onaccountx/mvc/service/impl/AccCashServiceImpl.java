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
import com.onaccountx.mvc.model.dao.AccCashDAO;
import com.onaccountx.mvc.model.entity.AccCash;
import com.onaccountx.mvc.service.AccCashService;
import com.onaccountx.restful.ResponseREST;
import com.onaccountx.restful.bean.AccCashRESTBean;
import com.onaccountx.utils.JsonUtils;

/**
 * <pre>
 * [實作 AccCashService] 2020-03-18 15:17
 * </pre>
 * 
 * @author TiramiAsu (Email)
 */
@Service
public class AccCashServiceImpl implements AccCashService {

	@Autowired
	private AccCashDAO accCashDAO;

	public void setAccCashDAO(AccCashDAO accCashDAO) {
		this.accCashDAO = accCashDAO;
	}

	@Override
	@Transactional
	public boolean create(AccCash bean) {
		bean.setTimeBuild(new Date());
		bean.setTimeModify(new Date());
		return accCashDAO.save(bean);
	}

	@Override
	@Transactional
	public AccCash find(Long id) {
		return accCashDAO.find(id);
	}

	@Override
	@Transactional
	public boolean update(AccCash bean) {
		return accCashDAO.save(bean);
	}

	@Override
	@Transactional
	public boolean delete(Long id) {
		return accCashDAO.remove(accCashDAO.find(id));
	}

	@Override
	@Transactional
	public List<AccCash> query() {

		List<AccCash> accCashList = null;

		/* Initial value */
		/* Check */
		/* Search Condition */

		try {
			accCashList = accCashDAO.findAll().stream()
					.sorted((o1, o2) -> o2.getId()
					.compareTo(o1.getId()))
//					.peek(System.out::println)
					.collect(Collectors.toList());
			if (accCashList == null) {
				throw new Exception(">>> AccCash Query Failed <<<");
			}
		} catch (Exception e) {
			accCashList = null;
			e.printStackTrace();
		}
		return accCashList;
	}

	@Override
	@Transactional
	public ResponseREST queryREST(Object json) {

		JSONObject jsonObject = JsonUtils.parseAttributes("accCash", json);
		List<AccCashRESTBean> outputJson = new ArrayList<>();
		Map<String, Object> conds = new HashMap<String, Object>();

		/* initial value */

		Long jJId = -1L;

		/* check */

		jJId = (jsonObject.get(AccCash._JOURNAL_ID) == null) ? jJId : Long.parseLong(jsonObject.get(AccCash._JOURNAL_ID) + "");

		/* Search Condition */

		List<AccCash> accCashes = new ArrayList<>();
		SearchResult<Object> sr = new SearchResult<Object>();

		if (jJId != -1L) {
			conds.put(AccCash._JOURNAL_ID, jJId);
		}

		// 以 "id" 為順序
		ResponseREST responseMeg = null;

		try {
			sr = accCashDAO.query(AccCash._ID, true, conds);

			/* choose output data */

			for (Object obj : sr.getResult()) {
				accCashes.add((AccCash) obj);
			}

			for (AccCash accCash : accCashes) {
				AccCashRESTBean bean = new AccCashRESTBean();

				bean.setId(accCash.getId());
				bean.setIncrease(accCash.getIncrease());
				bean.setReduce(accCash.getReduce());
				bean.setJId(accCash.getJId());
				bean.setTimeModify(accCash.getTimeModify());

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
