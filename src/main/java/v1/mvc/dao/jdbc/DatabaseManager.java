/*
 * 
 * 
 * 
 * 
 * 
 * &copy;TiramiAsu
 * 
 */
package v1.mvc.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * <pre>
 * [Database Manager] 2019-12-04 09:53
 * 1 public final String JDBC_DRIVER
 *   public final String JDBC_URL
 *   public final String JDBC_USER
 *   public final String JDBC_PASS
 *   static DatabaseManager driver = new (instance of DatabaseManager);
 * </pre>
 * 
 * @author  TiramiAsu (Email)
 */
public interface DatabaseManager {

	/**
	 * <pre>
	 * [Database Connection] 2019-12-04 10:16
	 * 1 Class.forName(JDBC_DRIVER); // jdbc 3.0
	 * 2 conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);
	 * </pre>
	 * 
	 * @return conn
	 */
	public Connection getConnection() throws ClassNotFoundException, SQLException;
	
	/**
	 * <pre>
	 * [Close Connection] 2019-12-04 10:19
	 * </pre>
	 */
	default void close(Connection conn) throws SQLException {
		if(conn != null) { conn.close(); }
	};
	
	/**
	 * <pre>
	 * [Close Statement(SQL)] 2019-12-04 10:23
	 * </pre>
	 */
	default void close(Statement st) throws SQLException {
		if(st != null) { st.close(); }
	}
	
	/**
	 * <pre>
	 * [Close PreparedStatement(SQL)] 2019-12-04 10:24
	 * </pre>
	 */
	default void close(PreparedStatement ps) throws SQLException {
		if(ps != null) { ps.close(); }
	}
	
	/**
	 * <pre>
	 * [Close ResultSet] 2019-12-04 10:25
	 * </pre>
	 */
	default void close(ResultSet rs) throws SQLException {
		if(rs != null) { rs.close(); }
	}
	
}
