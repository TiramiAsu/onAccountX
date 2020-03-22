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
import com.onaccountx.mvc.model.dao.AccountDAO;
import com.onaccountx.mvc.model.entity.Account;
import com.onaccountx.mvc.service.AccountService;
import com.onaccountx.restful.ResponseREST;
import com.onaccountx.restful.bean.AccountRESTBean;
import com.onaccountx.utils.JsonUtils;
import com.onaccountx.utils.db.Operate;

/**
 * <pre>
 * [實作 AccountService] 2020-03-20 16:44
 * </pre>
 * 
 * @author TiramiAsu (Email)
 */
@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountDAO accountDAO;

	public void setMemberDAO(AccountDAO accountDAO) {
		this.accountDAO = accountDAO;
	}

	@Override
	@Transactional
	public boolean create(Account bean) {
		bean.setTimeBuild(new Date());
		bean.setTimeModify(new Date());
		return accountDAO.save(bean);
	}

	@Override
	@Transactional
	public Account find(Long id) {
		return accountDAO.find(id);
	}

	@Override
	@Transactional
	public boolean update(Account bean) {
		return accountDAO.save(bean);
	}

	@Override
	@Transactional
	public boolean delete(Long id) {
		return accountDAO.remove(accountDAO.find(id));
	}

	@Override
	@Transactional
	public List<Account> query() {

		List<Account> accountList = null;

		/* Initial value */
		/* Check */
		/* Search Condition */

		try {
			accountList = accountDAO.findAll().stream()
					.sorted((o1, o2) -> o2.getId().compareTo(o1.getId()))
//					.peek(System.out::println)
					.collect(Collectors.toList());
			if (accountList == null) {
				throw new Exception(">>> Accounts Query Failed <<<");
			}
		} catch (Exception e) {
			accountList = null;
			e.printStackTrace();
		}
		return accountList;
	}

	@Override
	@Transactional
	public ResponseREST queryREST(Object json) {

		JSONObject jsonObject = JsonUtils.parseAttributes("account", json);
		List<AccountRESTBean> outputJson = new ArrayList<>();
		Map<String, Object> conds = new HashMap<String, Object>();

		/* initial value */

		String jAccount = "";
		Integer jStatus = -1;

		/* check */

		jAccount = (jsonObject.get(Account._ACCOUNT) == null) ? jAccount : jsonObject.get(Account._ACCOUNT) + "";
		jStatus = (jsonObject.get(Account._STATUS) == null) ? jStatus : Integer.parseInt(jsonObject.get(Account._STATUS) + "");

		/* Search Condition */

		List<Account> accountList = new ArrayList<>();
		SearchResult<Object> sr = new SearchResult<Object>();

		if (jAccount != null && !jAccount.equals("")) {
			conds.put(Account._ACCOUNT, Operate.like("%" + jAccount + "%"));
		}

		if (jStatus != -1) {
			conds.put(Account._STATUS, jStatus);
		}

		ResponseREST responseMeg = null;

		try {
			sr = accountDAO.query(Account._ID, true, conds); // 以 "id" 為順序

			/* choose output data */

			for (Object obj : sr.getResult()) {
				accountList.add((Account) obj);
			}

			for (Account account : accountList) {
				AccountRESTBean bean = new AccountRESTBean();

				bean.setId(account.getId())
					.setAccount(account.getAccount())
					.setStatus(account.getStatus())
					.setErrorTimes(account.getErrorTime())
					.setTimeLast(account.getTimeLast())
					.setTimeModify(account.getTimeModify());

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
