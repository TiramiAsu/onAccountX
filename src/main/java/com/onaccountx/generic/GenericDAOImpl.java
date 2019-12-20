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

import org.hibernate.Session;

import com.onaccountx.utils.HibernateUtils;

/**
 * <pre>
 * [通用 DAO 實作] 2019-12-19 08:34
 * </pre>
 * 
 * @author TiramiAsu (Email)
 */
public class GenericDAOImpl<T> implements GenericDAO<T> {
	
	@Override
	public void create(T bean) {
		Session session = HibernateUtils.getSession();
		try {
			session.beginTransaction();
			session.save(bean);
			session.getTransaction().commit();
		} catch (Exception e) {
			// 交易錯誤, 恢復交易前狀態
			session.getTransaction().rollback();
		} finally {
			session.close();
		}
	}

	@Override
	public void update(T bean) {
		Session session = HibernateUtils.getSession();
		try {
			session.beginTransaction();
			session.update(bean);
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
		} finally {
			session.close();
		}
	}

	@Override
	public void delete(T bean) {
		Session session = HibernateUtils.getSession();
		try {
			session.beginTransaction();
			session.delete(bean);
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
		} finally {
			session.close();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public T find(Class<? extends T> clazz, Long id) {
		Session session = HibernateUtils.getSession();
		try {
			session.beginTransaction();
			return (T)session.get(clazz, id);
		} finally {
			session.getTransaction().commit();
			session.close();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> query(String hql) {
		Session session = HibernateUtils.getSession();
		try {
			session.beginTransaction();
			return session.createQuery(hql).list();
		} catch (Exception e) {
			System.out.println(">>> HQL Error, Query Failed <<<");
		} finally {
			session.getTransaction().commit();
			session.close();
		}
		return null;
	}

}
