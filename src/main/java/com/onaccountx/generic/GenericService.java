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

import com.onaccountx.restful.ResponseREST;

/**
 * <pre>
 * [定義 GenericService ] 2019-12-24 16:28
 * </pre>
 * 
 * @author TiramiAsu (Email)
 */
public interface GenericService<T> {

	// Basic
	public boolean create(T bean);
	public List<T> query();
	public T find(Long id);
	public boolean update(Long id, T bean);
	public boolean delete(Long id);

	// JSON
	ResponseREST queryREST(Object json);
}
