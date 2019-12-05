/*
 * 
 * 
 * 
 * 
 * 
 * &copy;TiramiAsu
 * 
 */
package v1.mvc.dao.jdbc.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;

/**
 * <pre>
 * [Test] 2019-12-04 11:01
 * </pre>
 * 
 * @author  TiramiAsu (Email)
 */
class PostgreSQLManagerTest {
	
	public final String JDBC_DRIVER = "org.postgresql.Driver";
	public final String JDBC_URL = "jdbc:postgresql://127.0.0.1:5432/onaccountx";
	public final String JDBC_USER = "postgres";
	public final String JDBC_PASS = "postgres";

	@Test
	final void testGetConnection() throws ClassNotFoundException, SQLException {
		Connection actual = null;
		Class.forName(JDBC_DRIVER); // ClassNotFoundException
		actual  = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS); // SQLException
		assertNotNull(actual);
	}

}
