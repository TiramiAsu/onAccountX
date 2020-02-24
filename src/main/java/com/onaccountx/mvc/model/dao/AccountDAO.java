/*
 * 
 * 
 * 
 * 
 * 
 * &copy;TiramiAsu
 * 
 */
package com.onaccountx.mvc.model.dao;

import com.googlecode.genericdao.dao.hibernate.GenericDAO;
import com.onaccountx.mvc.model.entity.Account;

/**
 * <pre>
 * [定義 AccountDAO] 2019-12-19 08:30
 * </pre>
 * 
 * @author TiramiAsu (Email)
 */
public interface AccountDAO extends _DAO<Account>, GenericDAO<Account, Long> {
}
