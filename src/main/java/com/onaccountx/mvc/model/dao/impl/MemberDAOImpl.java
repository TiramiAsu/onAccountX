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
import com.onaccountx.mvc.model.dao.MemberDAO;
import com.onaccountx.mvc.model.entity.Member;
import com.onaccountx.utils.db.SearchUtils;

/**
 * <pre>
 * [會員 DAO 實作] 2019-12-18 23:13
 * </pre>
 * 
 * @author TiramiAsu (Email)
 */
@Repository
public class MemberDAOImpl extends GenericDAOImpl<Member, Long> implements MemberDAO {

	@Autowired
	@Override
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Override
	public SearchResult<Object> query(String sort, boolean asc, Map<String, Object> conditions, int page, int count)
			throws Exception {

		Search search = SearchUtils.buildSearchCondition(new Search(Member.class), conditions);

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

		Search search = SearchUtils.buildSearchCondition(new Search(Member.class), conditions);

		if (sort != null) {
			search.addSort(sort, asc);
		}
		return super.searchAndCount(search);
	}
}
