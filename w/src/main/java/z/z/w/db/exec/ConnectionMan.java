/**
 * z.z.w.db.exec.ConnectionMan.java
 */
package z.z.w.db.exec;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.naming.NamingException;

import z.z.w.db.JdbcInfo;

/**
 * @author Wu Zhenzhen
 * @version Dec 11, 2012 9:34:00 PM
 * @deprecated 2013-8-19 11:16:03 使用<code>z.z.w.db.upgrade</code>包下新的數據庫連接方式
 */
public class ConnectionMan {

	private static Connection conn = null;
	private static Connector connector = null;

	/**
	 * <br>
	 * Created on: Dec 11, 2012 9:34:00 PM
	 */
	private ConnectionMan() {
		super();
	}

	/**
	 * Get connection from JDBCINFO <br>
	 * Created on: Dec 11, 2012 9:52:05 PM
	 * 
	 * @param info
	 * @throws SQLException
	 */
	public static Connection createConnection(JdbcInfo info)
			throws SQLException {
		return createConnection(info.getDriver(), info.getConnectionURL(),
				info.getUser(), info.getPassword());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see z.z.w.db.exec.Sqlable#createConnection(java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String)
	 */
	public static Connection createConnection(String driver, String url,
			String user, String pwd) throws SQLException {
		try {
			close();
			Class.forName(driver);
			return conn = DriverManager.getConnection(url, user, pwd);
		} catch (ClassNotFoundException e) {
			System.out.println("    JDBC load class createConnection ["
					+ driver + "] error ! PORGRAMME EXIT.");
			System.out.println("MASSAGE : " + e.getMessage() + "\nCAUSE : "
					+ e.getCause() + "\nCLASS : " + e.getClass());
			System.exit(1);
		}

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see z.z.w.db.exec.Sqlable#createConnection(java.lang.String)
	 */
	public static Connection createConnection(String dsn) throws SQLException {
		try {
			close();
			connector = new Connector();
			return conn = connector.getConnection(dsn);
		} catch (NamingException e) {
			System.out.println("    JDBC load class createConnection [" + dsn
					+ "] error ! PORGRAMME EXIT.");
			System.out.println("MASSAGE : " + e.getMessage() + "\nCAUSE : "
					+ e.getCause() + "\nCLASS : " + e.getClass());
			System.exit(1);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see z.z.w.db.exec.Sqlable#close()
	 */
	public static void close() {

		// 关闭连接
		try {
			if (null != conn) {
				if (null != connector) {
					connector.close();
					connector = null;
				} else {
					conn.close();
					conn = null;
				}
			}
		} catch (SQLException e) {
		} catch (NamingException e) {
		} finally {
			connector = null;
			conn = null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see z.z.w.db.exec.Sqlable#isConnected()
	 */
	public static boolean isConnected() throws SQLException {
		return (null != conn) && (!conn.isClosed());
	}

}
