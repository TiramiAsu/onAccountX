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
import com.onaccountx.mvc.model.entity.MemberG;

/**
 * <pre>
 * [TODO] 2020-02-21 10:36
 * </pre>
 * 
 * @author TiramiAsu (Email)
 */
public interface MemberGDAO extends GenericDAO<MemberG, Long>{
	SearchResult<Object> query(String sort, boolean asc, Map<String, Object> conditions, int page, int count) throws Exception;
	SearchResult<Object> query(String sort, boolean asc, Map<String, Object> conditions)  throws Exception;
}
