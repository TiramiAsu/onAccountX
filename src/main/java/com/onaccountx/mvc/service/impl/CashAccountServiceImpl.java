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
import com.onaccountx.mvc.model.dao.CashAccountDAO;
import com.onaccountx.mvc.model.entity.CashAccount;
import com.onaccountx.mvc.service.CashAccountService;
import com.onaccountx.restful.ResponseREST;
import com.onaccountx.restful.bean.CashAccountRESTBean;
import com.onaccountx.utils.JsonUtils;

/**
 * <pre>
 * [實作 CashAccountService] 2020-03-18 15:17
 * </pre>
 * 
 * @author TiramiAsu (Email)
 */
@Service
public class CashAccountServiceImpl implements CashAccountService {

	@Autowired
	private CashAccountDAO cashAccountDAO;

	@Override
	@Transactional
	public boolean create(CashAccount bean) {
		bean.setTimeBuild(new Date());
		bean.setTimeModify(new Date());
		return cashAccountDAO.save(bean);
	}

	@Override
	@Transactional
	public CashAccount find(Long id) {
		return cashAccountDAO.find(id);
	}

	@Override
	@Transactional
	public boolean update(CashAccount bean) {
		return cashAccountDAO.save(bean);
	}

	@Override
	@Transactional
	public boolean delete(Long id) {
		return cashAccountDAO.remove(cashAccountDAO.find(id));
	}

	@Override
	@Transactional
	public List<CashAccount> query() {

		List<CashAccount> accCashList = null;

		/* Initial value */
		/* Check */
		/* Search Condition */

		try {
			accCashList = cashAccountDAO.findAll().stream()
					.sorted((o1, o2) -> o2.getJournal().getId().compareTo(o1.getJournal().getId()))
//					.peek(System.out::println)
					.collect(Collectors.toList());
			if (accCashList == null) {
				throw new Exception(">>> CashAccount Query Failed <<<");
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

		JSONObject jsonObject = JsonUtils.parseAttributes(CashAccount._JSON_NAME, json);
		List<CashAccountRESTBean> outputJson = new ArrayList<>();
		Map<String, Object> conds = new HashMap<String, Object>();

		/* initial value */

		Long jJId = -1L;

		/* check */

		jJId = (jsonObject.get(CashAccount._ID) == null) ? jJId : Long.parseLong(jsonObject.get(CashAccount._ID) + "");

		/* Search Condition */

		List<CashAccount> cashAccounts = new ArrayList<>();
		SearchResult<Object> sr = new SearchResult<Object>();

		if (jJId != -1L) {
			conds.put(CashAccount._ID, jJId);
		}

		// 以 "id" 為順序
		ResponseREST responseMeg = null;

		try {
			sr = cashAccountDAO.query(CashAccount._ID, true, conds);

			/* choose output data */

			for (Object obj : sr.getResult()) {
				cashAccounts.add((CashAccount) obj);
			}

			for (CashAccount cashAcc : cashAccounts) {
				CashAccountRESTBean bean = new CashAccountRESTBean();

				bean.setIncrease(cashAcc.getIncrease());
				bean.setReduce(cashAcc.getReduce());
				bean.setJournalId(cashAcc.getJournal().getId());
				bean.setTimeModify(cashAcc.getTimeModify());

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
