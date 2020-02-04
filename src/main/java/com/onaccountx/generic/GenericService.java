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
	public void create(T bean);
	public List<T> query();
	public T find(Long id);
	public void update(Long id, T bean);
	public void delete(Long id);
	
	// JSON
	public List<T> query(Object json);
	
}
