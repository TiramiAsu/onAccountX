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

import org.springframework.jdbc.core.JdbcTemplate;

/**
 * <pre>
 * [通用 DAO 定義] 2019-12-18 23:05
 * - 使用 JdbcTemplate, 配合 RowMapper&lt;T&gt;
 * </pre>
 * 
 * @author TiramiAsu (Email)
 */
public interface GenericDAO<T> {

	public void create(T bean) throws Exception;

	public List<T> query() throws Exception;

	public T find(Class<T> clazz, Long id) throws Exception;

	public void update(Long id, T bean) throws Exception;

	public void delete(Long id) throws Exception;

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate);

}
