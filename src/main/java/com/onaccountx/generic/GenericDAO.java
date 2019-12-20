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

	public void create(T bean);
	public void update(T bean);
	public void delete(T bean);
	public T find(Class<? extends T> clazz, Long id);
	public List<T> query(String hql);
	
}
