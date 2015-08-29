/**
 * z.z.w.db.exec.Connector.java
 */
package z.z.w.db.exec;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * @author Wu Zhenzhen
 * @version Nov 22, 2012 10:19:12 AM
 * @deprecated 2013-8-19 11:16:03 使用<code>z.z.w.db.upgrade</code>包下新的數據庫連接方式
 */

public class Connector {

	private Connection conn = null;
	private InitialContext context = null;

	/**
	 * <br>
	 * Created on: Nov 22, 2012 10:19:20 AM
	 */
	public Connector() {
		super();
	}

	/**
	 * Get connection from dsn <br>
	 * Created on: Nov 22, 2012 10:20:29 AM
	 * 
	 * @param dsn
	 * @return
	 * @throws NamingException
	 * @throws SQLException
	 */
	public Connection getConnection(String dsn) throws NamingException,
			SQLException {
		context = new InitialContext();
		// Lookup the data source
		DataSource ds = (DataSource) context.lookup(dsn);
		// Get the connection from the data source
		conn = ds.getConnection();
		return conn;
	}

	/**
	 * close connection <br>
	 * Created on: Nov 22, 2012 10:48:06 AM
	 * 
	 * @throws SQLException
	 * @throws NamingException
	 */
	public void close() throws SQLException, NamingException {

		if (null != conn) {
			conn.close();
			conn = null;
		}
		if (null != context) {
			context.close();
			context = null;
		}

	}

}
