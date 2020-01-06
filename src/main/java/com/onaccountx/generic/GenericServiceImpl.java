/*
 * 
 * 
 * 
 * 
 * 
 * &copy;TiramiAsu
 * 
 */
package com.onaccountx.generic;

import java.util.List;

/**
 * <pre>
 * [通用 Service 實作] 2019-12-24 16:51
 * - 暫時停用(無法用泛型設定自定義通用實作)
 * </pre>
 * 
 * @author TiramiAsu (Email)
 */
public abstract class GenericServiceImpl<T extends Object, DAO extends GenericDAOImpl<T>> implements GenericService<T> {
	
	DAO dao;
	Class<T> tClass;
	
	@Override
	public List<T> query(String hql) {
		return dao.query(hql);
	}

	@Override
	public T find(Long id) {
		return dao.find(tClass, id);
	}

	@Override
	public void delete(Long id) {
		T t = dao.find(tClass, id);
		dao.delete(t);
	}

	@Override
	public void create(T bean) {
		dao.create(bean);
	}

	@Override
	public void update(T bean) {
		dao.update(bean);
	}

}