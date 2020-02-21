/*
 * 
 * 
 * 
 * 
 * 
 * &copy;TiramiAsu
 * 
 */
package com.onaccountx.mvc.model.dao;

import java.util.Map;

import com.googlecode.genericdao.dao.hibernate.GenericDAO;
import com.googlecode.genericdao.search.SearchResult;
import com.onaccountx.mvc.model.entity.Member;

/**
 * <pre>
 * [會員 DAO 定義] 2019-12-18 23:11
 * </pre>
 * 
 * @author TiramiAsu (Email)
 */
public interface MemberDAO extends GenericDAO<Member, Long> {
	SearchResult<Object> query(String sort, boolean asc, Map<String, Object> conditions, int page, int count) throws Exception;
	SearchResult<Object> query(String sort, boolean asc, Map<String, Object> conditions)  throws Exception;
}
