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
import com.onaccountx.mvc.service.CashAccountService;
import com.onaccountx.restful.bean.CashAccountRESTBean;
import com.onaccountx.utils.SpringUtils;

/**
 * <pre>
 * [AccCash REST Service] 2020-03-18 15:40
 * 
 * [Process]
 * // Data
 * // Service Enable
 * // Authenticate User
 * // Handle
 * // Response
 * </pre>
 * 
 * @author TiramiAsu (Email)
 */
@Service
@Path("/cashaccount")
public class CashAccountRESTService implements GenericRESTService {

	@Autowired
	CashAccountService cashAccountService;
	
	private void enableService() {
		cashAccountService = (cashAccountService == null) ? SpringUtils.getBean(CashAccountService.class) : cashAccountService;
	}

	@GET
	@Override
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response queryREST() {
		
		// Data
		List<CashAccount> cashAccList = null;
		List<Map<String, Object>> beanList = new ArrayList<>();
		
		// Service Enable
		enableService();
		
		// Authenticate User
		// TODO Json Web Token -> Filter
		
		// Handle
		cashAccList = cashAccountService.query();
		
		// Response
		if (cashAccList == null) {
			return new ResponseREST(ERROR_DATABASE).build();
		} else {
			for (CashAccount ac : cashAccList) {
				Map<String, Object> restBean = new GenericRESTBean()
						.put(CashAccount._ID, ac.getId())
						.put(CashAccount._INCREASE, ac.getIncrease())
						.put(CashAccount._REDUCE, ac.getReduce())
						.put(CashAccount._JOURNAL_ID, ac.getJournalId())
						.put(CashAccount._TIME_MODIFY, ac.getTimeModify().getTime())
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
				.entity(cashAccountService.queryREST(jsonObject))
				.build();
	}

	@GET
	@Path("/{id}")
	@Override
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response findREST(@PathParam("id") String id) {
		
		// Data
		CashAccount cashAcc = null;
		CashAccountRESTBean bean = null;
		
		// Service Enable
		enableService();
		
		// Authenticate User
		// TODO Json Web Token -> Filter
		
		// Handle
		cashAcc = cashAccountService.find(Long.parseLong(id));
		
		// Response
		if (cashAcc == null) {
			return new ResponseREST(ERROR_DATABASE).build();
		} else {
				bean = new CashAccountRESTBean();
				bean.setId(cashAcc.getId())
					.setIncrease(cashAcc.getIncrease())
					.setReduce(cashAcc.getReduce())
					.setJournalId(cashAcc.getJournalId())
					.setTimeModify(cashAcc.getTimeModify());
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
		CashAccountRESTBean bean = null;
		CashAccount cashAcc;

		// Data

		if (jsonObj == null) {
			return new ResponseREST(ERROR_INPUT).build();
		}

		// Service Enable
		enableService();

		// Authenticate User
		// TODO Json Web Token -> Filter

		// Handle
		bean = mapCashAccBean(jsonObj);

		if (bean == null) {
			return new ResponseREST(ERROR_PARSE).build();
		}

		cashAcc = new CashAccount(bean.getIncrease(), bean.getReduce(), bean.getJournalId());

		try {
			cashAccountService.create(cashAcc);
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
		CashAccountRESTBean bean = null;
		CashAccount accCash;

		// Data

		if (jsonObj == null) {
			return new ResponseREST(ERROR_INPUT).build();
		}

		// Service Enable
		enableService();

		// Authenticate User
		// TODO Json Web Token -> Filter

		// Handle
		bean = mapCashAccBean(jsonObj);

		if (bean == null) {
			return new ResponseREST(ERROR_PARSE).build();
		}

		try {
			accCash = cashAccountService.find(Long.parseLong(id));
			accCash.setIncrease(bean.getIncrease() == null ? accCash.getIncrease() : bean.getIncrease());
			accCash.setReduce(bean.getReduce() == null ? accCash.getReduce() : bean.getReduce());
			accCash.setJournalId(bean.getJournalId() == null ? accCash.getJournalId() : bean.getJournalId());
			accCash.setTimeModify(new Date());
			cashAccountService.update(accCash);
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
			cashAccountService.delete(Long.parseLong(id));
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseREST(ERROR_DATABASE).build();
		}
		// Response
		return new ResponseREST(SUCCESS).build();
	}

	/** private method */
	
	// JSONObject 映射 RESTBean
	private CashAccountRESTBean mapCashAccBean(JSONObject jsonObj) {
		CashAccountRESTBean retBean = null;
		ObjectMapper mapper = new ObjectMapper();
		String data = null;

		if (jsonObj == null) {
			return null;
		}

		try {
			data = jsonObj.get(CashAccount._JSON_NAME).toString();
			// System.out.println(data);
			if (data == null || data.length() == 0) {
				return null;
			}

			retBean = mapper.readValue(JsonSanitizer.sanitize(data), CashAccountRESTBean.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retBean;
	}
}
