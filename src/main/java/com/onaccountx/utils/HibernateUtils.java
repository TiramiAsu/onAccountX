/*
 * 
 * 
 * 
 * 
 * 
 * &copy;TiramiAsu
 * 
 */
package com.onaccountx.utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * <pre>
 * [Hibernate 工具類] 2019-12-18 15:29
 * </pre>
 * 
 * @author  TiramiAsu (Email)
 */
public class HibernateUtils {

	private static final SessionFactory sessionFactory;
	
	static {
		try {
			sessionFactory = new Configuration()
					.configure("hibernate.cfg.xml").buildSessionFactory();
		} catch (Throwable ex) {
			throw new ExceptionInInitializerError(ex);
		}
	}
	
	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	public static Session getSession() {
		return sessionFactory.openSession();
	}
	
}
