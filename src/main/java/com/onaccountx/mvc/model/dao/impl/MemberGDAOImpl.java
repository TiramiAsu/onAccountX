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

import com.googlecode.genericdao.dao.hibernate.GenericDAOImpl;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;
import com.onaccountx.mvc.model.dao.MemberGDAO;
import com.onaccountx.mvc.model.entity.MemberG;
import com.onaccountx.utils.db.SearchUtils;

/**
 * <pre>
 * [實作 MemberDAO] 2020-02-21 10:39
 * </pre>
 * 
 * @author TiramiAsu (Email)
 */
@Repository
public class MemberGDAOImpl extends GenericDAOImpl<MemberG, Long> implements MemberGDAO {

	@Autowired
	@Override
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Override
	public SearchResult<Object> query(String sort, boolean asc, Map<String, Object> conditions, int page, int count)
			throws Exception {

		Search search = SearchUtils.buildSearchCondition(new Search(MemberG.class), conditions);

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

		Search search = SearchUtils.buildSearchCondition(new Search(MemberG.class), conditions);

		if (sort != null) {
			search.addSort(sort, asc);
		}
		return super.searchAndCount(search);
	}
}
