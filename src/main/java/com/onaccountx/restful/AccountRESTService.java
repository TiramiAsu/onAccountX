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
import com.onaccountx.mvc.model.entity.Account;
import com.onaccountx.mvc.service.AccountService;
import com.onaccountx.mvc.service.MemberService;
import com.onaccountx.restful.bean.AccountRESTBean;
import com.onaccountx.utils.SpringUtils;

/**
 * <pre>
 * [Account REST Service] 2020-03-20 17:44
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
@Path("/account")
public class AccountRESTService implements GenericRESTService {

	@Autowired
	AccountService accountService;

	@Autowired
	MemberService memberService;
	
	private void enableService() {
		accountService = (accountService == null) ? SpringUtils.getBean(AccountService.class) : accountService;
		memberService = (memberService == null) ? SpringUtils.getBean(MemberService.class) : memberService;
	}

	@POST
	@Path("/validate/user")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response queryRESTvalidate(InputStream is) {

		/** Enable Service*/

		enableService();

		/** JWT Authenticate */

		/** Data */
		List<Account> accountList = null;
		AccountRESTBean restBean = null;

		JSONObject jsonObj = toJsonObj(is);

		if (jsonObj == null) {
			return new ResponseREST()
				.setStatusCode(ERROR_PARSE)
				.build();
		}

		// Handle
		restBean = mapAccountEntity(jsonObj);
		accountList = accountService.query();

		if (restBean == null) {
			return new ResponseREST(ERROR_PARSE).build();
		}

		// Response
		if (accountList == null) {
			return new ResponseREST(ERROR_DATABASE).build();
		} else {
			String accountName = restBean.getAccount();
			Account account = accountList.stream()
				.filter(acc -> acc.getAccount().equals(accountName))
				.findFirst()
				.get();
			Map<String, Object> restEntity = new GenericRESTBean()
				.put("id", account.getId())
				.put("account", account.getAccount())
				.put("password", account.getPassword().equals(restBean.getPassword()) ? "pass" : "fail")
				.build();
			return new ResponseREST(SUCCESS)
				.setData(restEntity)
				.build();
		}
	}

	@GET
	@Path("/list/account")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response queryRESTmin() {

		// Data
		List<Account> accountList = null;
		List<Map<String, Object>> beanList = new ArrayList<>();
		
		// Service Enable
		enableService();
		
		// Authenticate User
		// TODO Json Web Token -> Filter
		
		// Handle
		accountList = accountService.query();
		
		// Response
		if (accountList == null) {
			return new ResponseREST(ERROR_DATABASE).build();
		} else {
			for (Account acc : accountList) {
				Map<String, Object> restBean = new GenericRESTBean()
						.put("id", acc.getId())
						.put("account", acc.getAccount())
						.build();
				beanList.add(restBean);
			}
			return new ResponseREST(SUCCESS)
					.setData(beanList)
					.build();
		}
	}

	@GET
	@Override
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response queryREST() {
		
		// Data
		List<Account> accountList = null;
		List<Map<String, Object>> beanList = new ArrayList<>();
		
		// Service Enable
		enableService();
		
		// Authenticate User
		// TODO Json Web Token -> Filter
		
		// Handle
		accountList = accountService.query();
		
		// Response
		if (accountList == null) {
			return new ResponseREST(ERROR_DATABASE).build();
		} else {
			for (Account acc : accountList) {
				Map<String, Object> restBean = new GenericRESTBean()
						.put("id", acc.getId())
						.put("account", acc.getAccount())
						.put("memberId", acc.getMember().getId())
						.put("status", acc.getStatus())
						.put("timeLast", acc.getTimeLast())
						.put("timeModify", acc.getTimeModify().getTime())
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
				.entity(accountService.queryREST(jsonObject))
				.build();
	}

	@GET
	@Path("/{id}")
	@Override
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response findREST(@PathParam("id") String id) {
		
		// Data
		Account account = null;
		AccountRESTBean bean = null;
		
		// Service Enable
		enableService();
		
		// Authenticate User
		// TODO Json Web Token -> Filter
		
		// Handle
		account = accountService.find(Long.parseLong(id));
		
		// Response
		if (account == null) {
			return new ResponseREST(ERROR_DATABASE).build();
		} else {
				bean = new AccountRESTBean();
				bean.setId(account.getId())
					.setAccount(account.getAccount())
					.setStatus(account.getStatus())
					.setTimeLast(account.getTimeLast())
					.setTimeModify(account.getTimeModify());
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
		AccountRESTBean restBean = null;
		Account account;

		// Data

		if (jsonObj == null) {
			return new ResponseREST(ERROR_INPUT).build();
		}

		// Service Enable
		enableService();

		// Authenticate User
		// TODO Json Web Token -> Filter

		// Handle
		restBean = mapAccountEntity(jsonObj);

		if (restBean == null) {
			return new ResponseREST(ERROR_PARSE).build();
		}

		account = new Account(restBean.getAccount(),
				restBean.getPassword(),
				restBean.getStatus(),
				restBean.getErrorTimes(),
				memberService.find(restBean.getMemberId()));
		try {
			accountService.create(account);
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
		AccountRESTBean restBean = null;
		Account account;

		// Data

		if (jsonObj == null) {
			return new ResponseREST(ERROR_INPUT).build();
		}

		// Service Enable
		enableService();

		// Authenticate User
		// TODO Json Web Token -> Filter

		// Handle
		restBean = mapAccountEntity(jsonObj);

		if (restBean == null) {
			return new ResponseREST(ERROR_PARSE).build();
		}

		try {
			account = accountService.find(Long.parseLong(id));
			account.setAccount(restBean.getAccount() == null ? account.getAccount() : restBean.getAccount());
			account.setPassword(restBean.getPassword() == null ? account.getPassword() : restBean.getPassword());
			account.setStatus(restBean.getStatus() == 0 ? account.getStatus() : restBean.getStatus());
			account.setErrorTimes(restBean.getErrorTimes() == 0 ? account.getErrorTimes() : restBean.getErrorTimes());
			account.setTimeModify(new Date());
			account.setMember(memberService.find(restBean.getMemberId()));
			accountService.update(account);
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
			accountService.delete(Long.parseLong(id));
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseREST(ERROR_DATABASE).build();
		}
		// Response
		return new ResponseREST(SUCCESS).build();
	}

	/** private method */
	
	// JSONObject 映射 RESTBean
	private AccountRESTBean mapAccountEntity(JSONObject jsonObj) {
		AccountRESTBean restBean = null;
		ObjectMapper mapper = new ObjectMapper();
		String data = null;

		if (jsonObj == null) {
			return null;
		}

		try {
			data = jsonObj.get(Account._JSON_NAME).toString();
			// System.out.println(data);
			if (data == null || data.length() == 0) {
				return null;
			}

			restBean = mapper.readValue(JsonSanitizer.sanitize(data), AccountRESTBean.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return restBean;
	}
}
