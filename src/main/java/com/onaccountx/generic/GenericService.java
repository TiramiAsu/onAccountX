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
 * [通用 Service 定義] 2019-12-24 16:28
 * </pre>
 * 
 * @author TiramiAsu (Email)
 */
public interface GenericService<T> {

	// Basic
	public List<T> query(String hql);
	public T find(Long id);
	public void delete(Long id);
	public void create(T bean);
	public void update(T bean);
	
	// JSON
	public List<T> query();
	public List<T> query(Object json);
	
}
