/*
 * 
 * 
 * 
 * 
 * 
 * &copy;TiramiAsu
 * 
 */
package com.onaccountx.mvc.controller.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.onaccountx.generic.DatabaseManager;

/**
 * <pre>
 * [PostgreSQL JDBC] 2019-12-04 10:31
 * </pre>
 * 
 * @author  TiramiAsu (Email)
 */
public class PostgreSQLManager implements DatabaseManager {

	public final String JDBC_DRIVER = "org.postgresql.Driver";
	public final String JDBC_URL = "jdbc:postgresql://127.0.0.1:5432/onaccountx";
	public final String JDBC_USER = "postgres";
	public final String JDBC_PASS = "postgres";
	
	private static DatabaseManager driver = new PostgreSQLManager();
	
	private PostgreSQLManager () {}
	
	public static DatabaseManager create() {
		return driver;
	}
	
	public static DatabaseManager getInstance() {
		return driver;
	}
	
	@Override
	public Connection getConnection() throws ClassNotFoundException, SQLException {
		Connection conn = null;
		Class.forName(JDBC_DRIVER); // ClassNotFoundException
		conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS); // SQLException
		return conn;
	}

}
