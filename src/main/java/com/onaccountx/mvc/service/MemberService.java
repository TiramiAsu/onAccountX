/*
 * 
 * 
 * 
 * 
 * 
 * &copy;TiramiAsu
 * 
 */
package com.onaccountx.mvc.service;

import java.util.List;

import org.json.simple.JSONObject;

import com.onaccountx.generic.GenericService;
import com.onaccountx.mvc.model.entity.Member;
import com.onaccountx.restful.bean.MemberRESTBean;

/**
 * <pre>
 * [會員 Service 定義] 2019-12-24 16:55
 * </pre>
 * 
 * @author TiramiAsu (Email)
 */
public interface MemberService extends GenericService<Member> {
	
	public static final String NAME = "txp_memberService";

	public void create(JSONObject jsonObject);
	public void update(JSONObject jsonObject);
	public void delete(JSONObject jsonObject);
	public MemberRESTBean find(JSONObject jsonObject);
	public List<MemberRESTBean> query(JSONObject jsonObject);

}
