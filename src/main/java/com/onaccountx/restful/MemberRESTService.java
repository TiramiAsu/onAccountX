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

import com.google.json.JsonSanitizer;
import com.onaccountx.generic.GenericRESTService;
import com.onaccountx.mvc.model.entity.Member;
import com.onaccountx.mvc.service.MemberService;
import com.onaccountx.mvc.service.impl.MemberServiceImpl;
import com.onaccountx.restful.bean.MemberRESTBean;

/**
 * <pre>
 * [會員 REST Service] 2019-12-24 21:33
 * - JSONParser > com.googlecode.json-simple | json-simple | 1.1.1
 * - ObjectMapper > org.codehaus.jackson | jackson-mapper-asl | 1.9.13
 * - JsonSanitizer > com.mikesamuel | json-sanitizer | 1.2.0
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
@Path("/member")
public class MemberRESTService implements GenericRESTService {

	@Autowired
	MemberService memberService;

	@GET
	@Override
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response queryREST() {
		
		// Data
		List<Member> memberList = null;
		List<MemberRESTBean> beanList = new ArrayList<>();
		
		// Service Enable
//		memberService = (memberService == null) ? SpringUtils.getBean(MemberService.NAME) : memberService;
		memberService = (memberService == null) ? new MemberServiceImpl() : memberService;
		
		// Authenticate User
		// TODO Json Web Token -> Filter
		
		// Handle
		memberList = memberService.query();
		
		// Response
		if (memberList == null) {
			return new ResponseREST(ERROR_DATABASE).build();
		} else {
			for (Member m : memberList) {
				MemberRESTBean bean = new MemberRESTBean();
				bean.setId(m.getId())
					.setName(m.getName())
					.setEmail(m.getEmail())
					.setPhone(m.getPhone())
					.setTimeModify(m.getTimeModify());
				beanList.add(bean);
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
	public Response queryREST(InputStream in) {
		// TODO Auto-generated method stub
		return null;
	}

	@GET
	@Path("/{id}")
	@Override
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response findREST(@PathParam("id") String id) {
		
		// Data
		Member member = null;
		MemberRESTBean bean = null;
		
		// Service Enable
//		memberService = (memberService == null) ? SpringUtils.getBean(MemberService.NAME) : memberService;
		memberService = (memberService == null) ? new MemberServiceImpl() : memberService;
		
		// Authenticate User
		// TODO Json Web Token -> Filter
		
		// Handle
		member = memberService.find(Long.parseLong(id));
		
		// Response
		if (member == null) {
			return new ResponseREST(ERROR_DATABASE).build();
		} else {
				bean = new MemberRESTBean();
				bean.setId(member.getId())
					.setName(member.getName())
					.setEmail(member.getEmail())
					.setPhone(member.getPhone())
					.setTimeModify(member.getTimeModify());
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
		MemberRESTBean bean = null;
		Member member;

		// Data

		if (jsonObj == null) {
			return new ResponseREST(ERROR_INPUT).build();
		}

		// Service Enable
//		memberService = (memberService == null) ? SpringUtils.getBean(MemberService.NAME) : memberService;
		memberService = (memberService == null) ? new MemberServiceImpl() : memberService;

		// Authenticate User
		// TODO Json Web Token -> Filter

		// Handle
		bean = mapMemberBean(jsonObj);

		if (bean == null) {
			return new ResponseREST(ERROR_PARSE).build();
		}

		member = new Member(bean.getName(), bean.getEmail(), bean.getPhone(),
				new Date(), new Date());

		try {
			memberService.create(member);
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
		MemberRESTBean bean = null;
		Member member;

		// Data

		if (jsonObj == null) {
			return new ResponseREST(ERROR_INPUT).build();
		}

		// Service Enable
//		memberService = (memberService == null) ? SpringUtils.getBean(MemberService.NAME) : memberService;
		memberService = (memberService == null) ? new MemberServiceImpl() : memberService;

		// Authenticate User
		// TODO Json Web Token -> Filter

		// Handle
		bean = mapMemberBean(jsonObj);

		if (bean == null) {
			return new ResponseREST(ERROR_PARSE).build();
		}

		try {
			member = memberService.find(Long.parseLong(id));
			member.setName(bean.getName() == null ? member.getName() : bean.getName());
			member.setEmail(bean.getEmail() == null ? member.getEmail() : bean.getEmail());
			member.setPhone(bean.getPhone() == null ? member.getPhone() : bean.getPhone());
			member.setTimeModify(new Date());
			memberService.update(member);
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
//		memberService = (memberService == null) ? SpringUtils.getBean(MemberService.NAME) : memberService;
		memberService = (memberService == null) ? new MemberServiceImpl() : memberService;

		// Authenticate User
		// TODO Json Web Token -> Filter

		// Handle

		try {
			memberService.delete(Long.parseLong(id));
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseREST(ERROR_DATABASE).build();
		}
		// Response
		return new ResponseREST(SUCCESS).build();
	}

	/** private method */
	
	// JSONObject 映射 RESTBean
	private MemberRESTBean mapMemberBean(JSONObject jsonObj) {
		MemberRESTBean retBean = null;
		ObjectMapper mapper = new ObjectMapper();
		String data = null;

		if (jsonObj == null) {
			return null;
		}

		try {
			data = jsonObj.get(Member._ENTITY_NAME).toString();
			System.out.println(data);
			if (data == null || data.length() == 0) {
				return null;
			}

			retBean = mapper.readValue(JsonSanitizer.sanitize(data), MemberRESTBean.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retBean;
	}

}
