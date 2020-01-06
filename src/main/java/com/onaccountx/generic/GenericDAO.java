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
 * [通用 DAO 定義] 2019-12-18 23:05
 * </pre>
 * 
 * @author TiramiAsu (Email)
 */
public interface GenericDAO<T> {

	public List<T> query(String hql) throws Exception;
	public T find(Class<T> clazz, Long id) throws Exception;
	public void delete(T bean) throws Exception;
	public void create(T bean) throws Exception;
	public void update(T bean) throws Exception;
	
}
