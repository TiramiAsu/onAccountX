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

import static com.onaccountx.utils.ResponseUtils.*;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.json.JsonSanitizer;
import com.onaccountx.generic.GenericRESTService;
import com.onaccountx.mvc.model.entity.Member;
import com.onaccountx.mvc.service.MemberService;
import com.onaccountx.mvc.service.impl.MemberServiceImpl;
import com.onaccountx.restful.bean.MemberRESTBean;
import com.onaccountx.utils.SpringUtils;

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

	@POST
	@Path("/create")
	@Override
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createREST(InputStream in) {
		
		JSONObject jsonObj = toJsonObj(in);
		MemberRESTBean bean = null;
		Member member = new Member();
		
		// Data
		
		if (jsonObj == null) {
			return new ResponseREST(ERROR_INPUT).build();
		}
		
		// Service Enable
		memberService = (memberService == null) ? SpringUtils.getBean(MemberService.NAME) : memberService;
		
		// Authenticate User
		// TODO Json Web Token -> Filter
		
		// Handle
		bean = mapMemberBean(jsonObj);
		
		if (bean == null) {
			return new ResponseREST(ERROR_PARSE).build();
		}
		
		member.setName(bean.getName());
		member.setEmail(bean.getEmail());
		member.setPhone(bean.getPhone());
		
		try {
			memberService.create(member);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseREST(ERROR_DATABASE).build();
		}
		// Response
		return new ResponseREST(SUCCESS).build();
	}

	@Override
	public Response updateREST(InputStream in) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response deleteREST(InputStream in) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response findREST(InputStream in) {
		// TODO Auto-generated method stub
		return null;
	}

	@POST
	@Path("/query")
	@Override
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response queryREST(InputStream in) {
		
		// Data
		JSONObject jsonObject = toJsonObj(in);
		List<MemberRESTBean> list = null;
		
		// Service Enable
		memberService = (memberService == null) ? new MemberServiceImpl() : memberService;
		
		// Authenticate User
		// TODO Json Web Token -> Filter
		
		// Handle
		if (jsonObject == null) {
			return new ResponseREST(ERROR_INPUT).build();
		} else {
			list = memberService.query(jsonObject);
		}
		
		// Response
		if (list == null) {
			return new ResponseREST(ERROR_DATABASE).build();
		} else {
			return new ResponseREST(SUCCESS)
					.setData(list)
					.build();
		}
	}
	
	/** private methods */
	
	// InputStream 轉 JSONObject
	private JSONObject toJsonObj(InputStream in) {
		JSONObject jsonObj = null;
		JSONParser parser = new JSONParser();
		try {
			jsonObj = (JSONObject)parser.parse(new BufferedReader(new InputStreamReader(in)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonObj;
	}
	
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
