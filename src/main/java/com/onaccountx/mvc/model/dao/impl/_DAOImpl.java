/*
 * 
 * 
 * 
 * 
 * 
 * &copy;TiramiAsu
 * 
 */
package com.onaccountx.mvc.model.dao.impl;

import java.util.Map;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.googlecode.genericdao.dao.hibernate.GenericDAO;
import com.googlecode.genericdao.dao.hibernate.GenericDAOImpl;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;
import com.onaccountx.mvc.model.dao._DAO;
import com.onaccountx.utils.db.SearchUtils;

/**
 * <pre>
 * [實作 DAO] 2020-02-21 16:28
 * - 配合 google DAO 設定 sessionFactory 及常用的 condition query 方法
 * </pre>
 * 
 * @author TiramiAsu (Email)
 */
@Repository
public class _DAOImpl<T> extends GenericDAOImpl<T, Long> implements _DAO<T>, GenericDAO<T, Long> {

	@Autowired
	@Override
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	private Class<T> entity;

	@Override
	public SearchResult<Object> query(String sort, boolean asc, Map<String, Object> conditions, int page, int count)
			throws Exception {

		Search search = SearchUtils.buildSearchCondition(new Search(entity), conditions);

		if (sort != null)
			search.addSort(sort, asc);

		if (count > 0) {
			search.setMaxResults(count); // a.k.a. results per page
			search.setPage(page);
		}
		return super.searchAndCount(search);
	}

	@Override
	public SearchResult<Object> query(String sort, boolean asc, Map<String, Object> conditions) throws Exception {

		Search search = SearchUtils.buildSearchCondition(new Search(entity), conditions);

		if (sort != null) {
			search.addSort(sort, asc);
		}
		return super.searchAndCount(search);
	}
}
