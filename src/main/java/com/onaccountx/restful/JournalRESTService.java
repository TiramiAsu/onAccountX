/*
 * 
 * 
 * 
 * 
 * 
 * &copy;TiramiAsu
 * 
 */
package com.onaccountx.restful;

import static com.onaccountx.utils.JsonUtils.toJsonObj;
import static com.onaccountx.utils.ResponseUtils.ERROR_DATABASE;
import static com.onaccountx.utils.ResponseUtils.ERROR_INPUT;
import static com.onaccountx.utils.ResponseUtils.ERROR_PARSE;
import static com.onaccountx.utils.ResponseUtils.SUCCESS;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.json.JsonSanitizer;
import com.onaccountx.generic.GenericRESTBean;
import com.onaccountx.generic.GenericRESTService;
import com.onaccountx.mvc.model.entity.CashAccount;
import com.onaccountx.mvc.model.entity.Journal;
import com.onaccountx.mvc.service.AccountService;
import com.onaccountx.mvc.service.CashAccountService;
import com.onaccountx.mvc.service.JournalService;
import com.onaccountx.mvc.service.SubjectService;
import com.onaccountx.restful.bean.JournalRESTBean;
import com.onaccountx.utils.SpringUtils;

/**
 * <pre>
 * [Journal REST Service] 2020-04-08 00:19
 * </pre>
 * 
 * @author TiramiAsu (Email)
 */
@Service
@Path("/journal")
public class JournalRESTService implements GenericRESTService {

	private String _CASH_CODE = "1-9-1";

	@Autowired
	JournalService journalService;

	@Autowired
	SubjectService subjectService;

	@Autowired
	AccountService accountService;

	@Autowired
	CashAccountService cashAccountService;

	private void enableService() {
		journalService = (journalService == null) ? SpringUtils.getBean(JournalService.class) : journalService;
		subjectService = (subjectService == null) ? SpringUtils.getBean(SubjectService.class) : subjectService;
		accountService = (accountService == null) ? SpringUtils.getBean(AccountService.class) : accountService;
		cashAccountService = (cashAccountService == null) ? SpringUtils.getBean(CashAccountService.class) : cashAccountService;
	}

	@GET
	@Override
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response queryREST() {
		
		// Data
		List<Journal> journalList = null;
		List<Map<String, Object>> beanList = new ArrayList<>();
		
		// Service Enable
		enableService();
		
		// Authenticate User
		// TODO Json Web Token -> Filter
		
		// Handle
		journalList = journalService.query();
		
		// Response
		if (journalList == null) {
			return new ResponseREST(ERROR_DATABASE).build();
		} else {
			for (Journal journal : journalList) {
				Map<String, Object> restBean = new GenericRESTBean()
						.put(Journal._ID, journal.getId())
						.put(Journal._TIME_DATE, journal.getTimeDate().getTime())
						.put(Journal._DEBIT, journal.getDebit().getId())
						.put(Journal._CREDIT, journal.getCredit().getId())
						.put(Journal._AMOUNT, journal.getAmount())
						.put(Journal._ITEM, journal.getItem())
						.put(Journal._PLACE, journal.getPlace())
						.put(Journal._WHO, journal.getWho())
						.put(Journal._TIME_BUILD, journal.getTimeBuild().getTime())
						.put(Journal._TIME_MODIFY, journal.getTimeModify().getTime())
						.build();
				beanList.add(restBean);
			}
			return new ResponseREST(SUCCESS)
					.setData(beanList)
					.build();
		}
	}

	@POST
	@Override
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response queryREST(InputStream is) {

		/** Enable Service*/

		enableService();

		/** JWT Authenticate */
		/** Data */

		JSONObject jsonObject = toJsonObj(is);

		if (jsonObject == null) {
			return new ResponseREST()
					.setStatusCode(ERROR_PARSE)
					.build();
		}

		return Response.status(200)
				.entity(journalService.queryREST(jsonObject))
				.build();
	}

	@GET
	@Path("/{id}")
	@Override
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response findREST(@PathParam("id") String id) {
		
		// Data
		Journal journal = null;
		JournalRESTBean bean = null;
		
		// Service Enable
		enableService();
		
		// Authenticate User
		// TODO Json Web Token -> Filter
		
		// Handle
		journal = journalService.find(Long.parseLong(id));
		
		// Response
		if (journal == null) {
			return new ResponseREST(ERROR_DATABASE).build();
		} else {
			bean = new JournalRESTBean(
					journal.getId(),
					journal.getTimeDate().getTime(),
					journal.getDebit().getId(),
					journal.getCredit().getId(),
					journal.getAmount(),
					journal.getItem(),
					journal.getPlace(),
					journal.getWho(),
					journal.getAccount().getId(),
					journal.getTimeModify());
			return new ResponseREST(SUCCESS)
					.setData(bean)
					.build();
		}
	}

	@PUT
	@Override
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createREST(InputStream in) {

		JSONObject jsonObj = toJsonObj(in);
		JournalRESTBean restBean = null;
		Journal journal;
		CashAccount cashAccount = null;

		// Data

		if (jsonObj == null) {
			return new ResponseREST(ERROR_INPUT).build();
		}

		// Service Enable
		enableService();

		// Authenticate User
		// TODO Json Web Token -> Filter

		// Handle
		restBean = mapJournalEntity(jsonObj);

		if (restBean == null) {
			return new ResponseREST(ERROR_PARSE).build();
		}

		journal = new Journal(
				new Date(restBean.getTimeDate()),
				subjectService.find(restBean.getDebit()),
				subjectService.find(restBean.getCredit()),
				restBean.getAmount(),
				restBean.getItem(),
				restBean.getPlace(),
				restBean.getWho(),
				accountService.find(restBean.getAccountId()));
		try {
			boolean b1 = journal.getDebit().getCode().equals(_CASH_CODE);
			boolean b2 = journal.getCredit().getCode().equals(_CASH_CODE);
			System.out.println(" debit: " + journal.getDebit().getCode());
			System.out.println("credit: " + journal.getCredit().getCode());
			// increase 或 reduce 有 "現金" -> 則被現金簿記錄
			if (b1 || b2) {
				cashAccount = new CashAccount(
					journal,
					restBean.getIncrease(),
					restBean.getReduce(),
					new Date(),
					new Date());
				journal.setCashAccount(cashAccount);
			}
			journalService.create(journal);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseREST(ERROR_DATABASE).build();
		}

		// Response
		return new ResponseREST(SUCCESS).build();
	}

	@PUT
	@Path("/{id}")
	@Override
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateREST(InputStream in, @PathParam("id") String id) {

		JSONObject jsonObj = toJsonObj(in);
		JournalRESTBean restBean = null;
		Journal journal;
		CashAccount cashAccount;

		// Data

		if (jsonObj == null) {
			return new ResponseREST(ERROR_INPUT).build();
		}

		// Service Enable
		enableService();

		// Authenticate User
		// TODO Json Web Token -> Filter

		// Handle
		restBean = mapJournalEntity(jsonObj);

		if (restBean == null) {
			return new ResponseREST(ERROR_PARSE).build();
		}

		try {
			journal = journalService.find(Long.parseLong(id));
			Journal newJournal = new Journal(
				restBean.getTimeDate() == null ? journal.getTimeDate() : new Date(restBean.getTimeDate()),
				restBean.getDebit() == null ? journal.getDebit() : subjectService.find(restBean.getDebit()),
				restBean.getCredit() == null ? journal.getCredit() : subjectService.find(restBean.getCredit()),
				restBean.getAmount() == null ? journal.getAmount() : restBean.getAmount(),
				restBean.getItem() == null ? journal.getItem() : restBean.getItem(),
				restBean.getPlace() == null ? journal.getPlace() : restBean.getPlace(),
				restBean.getWho() == null ? journal.getWho() : restBean.getWho(),
				restBean.getAccountId() == null ? journal.getAccount() : accountService.find(restBean.getAccountId()));

			boolean b1 = newJournal.getDebit().getCode().equals(_CASH_CODE);
			boolean b2 = newJournal.getCredit().getCode().equals(_CASH_CODE);
			// increase 或 reduce 有 "現金" -> 則被現金簿記錄
			if (b1 || b2) {
				cashAccount = cashAccountService.find(journal.getId());
				CashAccount newCashAccount = new CashAccount(
					newJournal,
					restBean.getIncrease() == null ? cashAccount.getIncrease() : restBean.getIncrease(),
					restBean.getReduce() == null ? cashAccount.getReduce() : restBean.getReduce(),
					new Date(),
					new Date());
				newJournal.setCashAccount(newCashAccount);
			}
			journalService.create(newJournal);
			journalService.delete(journal.getId());
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseREST(ERROR_DATABASE).build();
		}
		// Response
		return new ResponseREST(SUCCESS).build();
	}

	@DELETE
	@Path("/{id}")
	@Override
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteREST(@PathParam("id") String id) {

		// Data

		// Service Enable
		enableService();

		// Authenticate User
		// TODO Json Web Token -> Filter

		// Handle

		try {
			journalService.delete(Long.parseLong(id));
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseREST(ERROR_DATABASE).build();
		}
		// Response
		return new ResponseREST(SUCCESS).build();
	}

	/** private method */
	
	// JSONObject 映射 RESTBean
	private JournalRESTBean mapJournalEntity(JSONObject jsonObj) {
		JournalRESTBean restBean = null;
		ObjectMapper mapper = new ObjectMapper();
		String data = null;

		if (jsonObj == null) {
			return null;
		}

		try {
			data = jsonObj.get(Journal._JSON_NAME).toString();
			// System.out.println(data);
			if (data == null || data.length() == 0) {
				return null;
			}

			restBean = mapper.readValue(JsonSanitizer.sanitize(data), JournalRESTBean.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return restBean;
	}
}
