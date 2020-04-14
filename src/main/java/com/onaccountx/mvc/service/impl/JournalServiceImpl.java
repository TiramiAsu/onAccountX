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
import com.onaccountx.generic.GenericRESTBean;
import com.onaccountx.mvc.model.dao.CashAccountDAO;
import com.onaccountx.mvc.model.dao.JournalDAO;
import com.onaccountx.mvc.model.dao.SubjectDAO;
import com.onaccountx.mvc.model.entity.CashAccount;
import com.onaccountx.mvc.model.entity.Journal;
import com.onaccountx.mvc.service.JournalService;
import com.onaccountx.restful.ResponseREST;
import com.onaccountx.utils.JsonUtils;
import com.onaccountx.utils.db.Operate;

/**
 * <pre>
 * [實作 JournalService] 2020-04-07 23:10
 * </pre>
 * 
 * @author TiramiAsu (Email)
 */
@Service
public class JournalServiceImpl implements JournalService {

	@Autowired
	private JournalDAO journalDAO;

	@Autowired
	private SubjectDAO subjectDAO;

	@Autowired
	private CashAccountDAO cashAccountDAO;

	@Override
	@Transactional
	public boolean create(Journal bean) {
		bean.setTimeBuild(new Date());
		bean.setTimeModify(new Date());
		return journalDAO.save(bean);
	}

	@Override
	@Transactional
	public Journal find(Long id) {
		return journalDAO.find(id);
	}

	@Override
	@Transactional
	public boolean update(Journal bean) {
		return journalDAO.save(bean);
	}

	@Override
	@Transactional
	public boolean delete(Long id) {
		return journalDAO.remove(journalDAO.find(id));
	}

	@Override
	@Transactional
	public List<Journal> query() {

		List<Journal> journalList = null;

		/* Initial value */
		/* Check */
		/* Search Condition */

		try {
			journalList = journalDAO.findAll().stream()
					.sorted((o1, o2) -> o2.getId().compareTo(o1.getId()))
//					.peek(System.out::println)
					.collect(Collectors.toList());
			if (journalList == null) {
				throw new Exception(">>> Journals Query Failed <<<");
			}
		} catch (Exception e) {
			journalList = null;
			e.printStackTrace();
		}
		return journalList;
	}

	@Override
	@Transactional
	public ResponseREST queryREST(Object json) {

		JSONObject jsonObject = JsonUtils.parseAttributes(Journal._JSON_NAME, json);
		List<Map<String, Object>> outputJson = new ArrayList<>();
		Map<String, Object> conds = new HashMap<String, Object>();

		/* initial value */

		Long jTimeDate = 0L;
		Long jTimeDateEnd = 0L;
		Long jDebitId = -1L;
		Long jCreditId = -1L;
		String jItem = "";
		String jPlace = "";
		String jWho = "";

		/* check */

		jTimeDate = (jsonObject.get(Journal._TIME_DATE) == null) ? jTimeDate : Long.parseLong(jsonObject.get(Journal._TIME_DATE) + "");
		jTimeDateEnd = (jsonObject.get("timeDateEnd") == null) ? jTimeDateEnd : Long.parseLong(jsonObject.get("timeDateEnd") + "");
		jDebitId = (jsonObject.get(Journal._DEBIT) == null) ? jDebitId : Long.parseLong(jsonObject.get(Journal._DEBIT) + "");
		jCreditId = (jsonObject.get(Journal._CREDIT) == null) ? jCreditId : Long.parseLong(jsonObject.get(Journal._CREDIT) + "");
		jItem = (jsonObject.get(Journal._ITEM) == null) ? jItem : jsonObject.get(Journal._ITEM) + "";
		jPlace = (jsonObject.get(Journal._PLACE) == null) ? jPlace : jsonObject.get(Journal._PLACE) + "";
		jWho = (jsonObject.get(Journal._WHO) == null) ? jWho : jsonObject.get(Journal._WHO) + "";

		/* Search Condition */

		List<Journal> journalList = new ArrayList<>();
		SearchResult<Object> sr = new SearchResult<Object>();

//		System.out.println(jTimeDate);
//		System.out.println(jTimeDateEnd + "\n------------");

		if (jTimeDate != 0 && jTimeDateEnd == 0) {
			conds.put(Journal._TIME_DATE, Operate.greaterEqual(new Date(jTimeDate)));
		}

		if (jTimeDate != 0 && jTimeDateEnd != 0) {
			conds.put(Journal._TIME_DATE,
					Operate.equalRange(new Date(jTimeDate), new Date(jTimeDateEnd)));
		}

		if (jTimeDate == 0 && jTimeDateEnd != 0) {
			conds.put(Journal._TIME_DATE, Operate.lessEqual(new Date(jTimeDateEnd)));
		}

		if (jDebitId != -1L) {
			conds.put(Journal._DEBIT, subjectDAO.find(jDebitId));
		}

		if (jCreditId != -1L) {
			conds.put(Journal._CREDIT, subjectDAO.find(jCreditId));
		}

		if (jItem != null && !jItem.equals("")) {
			conds.put(Journal._ITEM, Operate.like("%" + jItem + "%"));
		}

		if (jPlace != null && !jPlace.equals("")) {
			conds.put(Journal._PLACE, Operate.like("%" + jPlace + "%"));
		}

		if (jWho != null && !jWho.equals("")) {
			conds.put(Journal._WHO, Operate.like("%" + jWho + "%"));
		}

		ResponseREST responseMeg = null;

		try {
			sr = journalDAO.query(Journal._TIME_DATE, true, conds); // 以 "timeDate" 為順序

			/* choose output data */

			for (Object obj : sr.getResult()) {
				journalList.add((Journal) obj);
			}

			for (Journal journal : journalList) {
				CashAccount cashAccount = null;
				Map<String, Object> restBean = new GenericRESTBean()
						.put(Journal._ID, journal.getId())
						.put(Journal._TIME_DATE, journal.getTimeDate().getTime())
						.put(Journal._DEBIT, journal.getDebit().getId())
						.put(Journal._CREDIT, journal.getCredit().getId())
						.put(Journal._AMOUNT, journal.getAmount())
						.put(Journal._ITEM, journal.getItem())
						.put(Journal._PLACE, journal.getPlace())
						.put(Journal._WHO, journal.getWho())
						.put(Journal._ACCOUNT_ID, journal.getAccount().getId())
						.put(Journal._TIME_MODIFY, journal.getTimeModify().getTime())
						.build();
				try {
					cashAccount = cashAccountDAO.find(journal.getId());
					restBean.put(CashAccount._INCREASE, cashAccount.equals(null) ? 0 : cashAccount.getIncrease());
					restBean.put(CashAccount._REDUCE, cashAccount.equals(null) ? 0 : cashAccount.getReduce());
				} catch (Exception e) {
					// 現金簿不存在 -> 代表只有日記簿紀錄
				}

				outputJson.add(restBean);
			}
			responseMeg = new ResponseREST(OK).setData(outputJson);
		} catch (Exception e) {
			responseMeg = new ResponseREST(ERROR_DATABASE).setData(outputJson);
			e.printStackTrace();
		}
		return responseMeg;
	}
}
