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

import com.googlecode.genericdao.search.SearchResult;

/**
 * <pre>
 * [定義 DAO] 2019-12-18 23:05
 * </pre>
 * 
 * @author TiramiAsu (Email)
 */
public interface _DAO<T> {
	SearchResult<Object> query(String sort, boolean asc, Map<String, Object> conditions, int page, int count) throws Exception;
	SearchResult<Object> query(String sort, boolean asc, Map<String, Object> conditions) throws Exception;
}
