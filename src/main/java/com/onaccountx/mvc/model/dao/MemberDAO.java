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
import com.onaccountx.mvc.model.entity.Member;

/**
 * <pre>
 * [定義 MemberDAO] 2019-12-18 23:11
 * </pre>
 * 
 * @author TiramiAsu (Email)
 */
public interface MemberDAO extends _DAO<Member>, GenericDAO<Member, Long> {}
