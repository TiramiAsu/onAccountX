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
import com.onaccountx.mvc.model.entity.Subject;
import com.onaccountx.mvc.service.SubjectService;
import com.onaccountx.restful.bean.SubjectRESTBean;
import com.onaccountx.utils.SpringUtils;

/**
 * <pre>
 * [Subject REST Service] 2020-03-20 00:43
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
@Path("/subject")
public class SubjectRESTService implements GenericRESTService {

	@Autowired
	SubjectService subjectService;
	
	public void setMemberService(SubjectService subjectService) {
		this.subjectService = subjectService;
	}
	
	private void enableService() {
		subjectService = (subjectService == null) ? SpringUtils.getBean(SubjectService.class) : subjectService;
	}

	@GET
	@Path("/list/name")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response queryRESTmin() {

		// Data
		List<Subject> subjectList = null;
		List<Map<String, Object>> beanList = new ArrayList<>();
		
		// Service Enable
		enableService();
		
		// Authenticate User
		// TODO Json Web Token -> Filter
		
		// Handle
		subjectList = subjectService.query();
		
		// Response
		if (subjectList == null) {
			return new ResponseREST(ERROR_DATABASE).build();
		} else {
			for (Subject subject : subjectList) {
				/* - 代號 1 -> 大分類代號
				 * - 代號 1-1 -> 中分類代號
				 * - 代號 1-1-1 -> 小分類代號
				 */
				if (subject.getCode().length() > 5) { // 不可被選擇
					Map<String, Object> restBean = new GenericRESTBean()
							.put(Subject._ID, subject.getId())
							.put(Subject._CODE, subject.getCode())
							.put(Subject._NAME, subject.getName())
							.build();
					beanList.add(restBean);
				}
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
		List<Subject> subjectList = null;
		List<Map<String, Object>> beanList = new ArrayList<>();
		
		// Service Enable
		enableService();
		
		// Authenticate User
		// TODO Json Web Token -> Filter
		
		// Handle
		subjectList = subjectService.query();
		
		// Response
		if (subjectList == null) {
			return new ResponseREST(ERROR_DATABASE).build();
		} else {
			for (Subject sj : subjectList) {
				Map<String, Object> restBean = new GenericRESTBean()
						.put("id", sj.getId())
						.put("code", sj.getCode())
						.put("name", sj.getName())
						.put("timeModify", sj.getTimeModify().getTime())
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
				.entity(subjectService.queryREST(jsonObject))
				.build();
	}

	@GET
	@Path("/{id}")
	@Override
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response findREST(@PathParam("id") String id) {
		
		// Data
		Subject subject = null;
		SubjectRESTBean bean = null;
		
		// Service Enable
		enableService();
		
		// Authenticate User
		// TODO Json Web Token -> Filter
		
		// Handle
		subject = subjectService.find(Long.parseLong(id));
		
		// Response
		if (subject == null) {
			return new ResponseREST(ERROR_DATABASE).build();
		} else {
				bean = new SubjectRESTBean();
				bean.setId(subject.getId())
					.setCode(subject.getCode())
					.setName(subject.getName())
					.setTimeModify(subject.getTimeModify());
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
		SubjectRESTBean bean = null;
		Subject subject;

		// Data

		if (jsonObj == null) {
			return new ResponseREST(ERROR_INPUT).build();
		}

		// Service Enable
		enableService();

		// Authenticate User
		// TODO Json Web Token -> Filter

		// Handle
		bean = mapSubjectBean(jsonObj);

		if (bean == null) {
			return new ResponseREST(ERROR_PARSE).build();
		}

		subject = new Subject(bean.getCode(), bean.getName());

		try {
			subjectService.create(subject);
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
		SubjectRESTBean bean = null;
		Subject subject;

		// Data

		if (jsonObj == null) {
			return new ResponseREST(ERROR_INPUT).build();
		}

		// Service Enable
		enableService();

		// Authenticate User
		// TODO Json Web Token -> Filter

		// Handle
		bean = mapSubjectBean(jsonObj);

		if (bean == null) {
			return new ResponseREST(ERROR_PARSE).build();
		}

		try {
			subject = subjectService.find(Long.parseLong(id));
			subject.setCode(bean.getCode() == null ? subject.getCode() : bean.getCode());
			subject.setName(bean.getName() == null ? subject.getName() : bean.getName());
			subject.setTimeModify(new Date());
			subjectService.update(subject);
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
			subjectService.delete(Long.parseLong(id));
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseREST(ERROR_DATABASE).build();
		}
		// Response
		return new ResponseREST(SUCCESS).build();
	}

	/** private method */
	
	// JSONObject 映射 RESTBean
	private SubjectRESTBean mapSubjectBean(JSONObject jsonObj) {
		SubjectRESTBean retBean = null;
		ObjectMapper mapper = new ObjectMapper();
		String data = null;

		if (jsonObj == null) {
			return null;
		}

		try {
			data = jsonObj.get(Subject._JSON_NAME).toString();
			// System.out.println(data);
			if (data == null || data.length() == 0) {
				return null;
			}

			retBean = mapper.readValue(JsonSanitizer.sanitize(data), SubjectRESTBean.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retBean;
	}
}
