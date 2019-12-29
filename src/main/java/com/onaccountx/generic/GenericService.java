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

	public void create(T bean);
	public void update(T bean);
	public void delete(Long id);
	public T find(Long id);
	public List<T> query(String hql);
	
}
