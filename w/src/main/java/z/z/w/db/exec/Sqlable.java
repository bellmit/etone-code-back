/**
 * z.z.w.db.exec.Sqlable.java
 */
package z.z.w.db.exec;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

/**
 * @author Wu Zhenzhen
 * @version Nov 22, 2012 10:03:38 AM
 * @deprecated 2013-8-19 11:16:03 使用<code>z.z.w.db.upgrade</code>包下新的數據庫連接方式
 */
public interface Sqlable {

	/**
	 * create connection <br>
	 * Created on: Nov 22, 2012 10:15:16 AM
	 * 
	 * @param driver
	 * @param url
	 * @param user
	 * @param pwd
	 * @throws SQLException
	 */
	public void createConnection(String driver, String url, String user,
			String pwd) throws SQLException;

	/**
	 * create connection by dsn <br>
	 * Created on: Nov 22, 2012 10:05:31 AM
	 * 
	 * @param dsn
	 * @throws SQLException
	 */
	public void createConnection(String dsn) throws SQLException;

	/**
	 * execute update <br>
	 * Created on: Nov 22, 2012 10:05:54 AM
	 * 
	 * @param strSQL
	 * @return
	 * @throws SQLException
	 */
	public boolean executeUpdate(String strSQL) throws SQLException;

	/**
	 * submit batch sql list <br>
	 * Created on: Nov 22, 2012 10:08:32 AM
	 * 
	 * @param sqlList
	 * @return
	 */
	public boolean submitBatch(Collection<String> sqlList) throws SQLException;

	/**
	 * init batch <br>
	 * Created on: Nov 22, 2012 10:07:36 AM
	 * 
	 * @return
	 * @throws SQLException
	 */
	public boolean initBatch() throws SQLException;

	/**
	 * read only query result set <br>
	 * Created on: Nov 22, 2012 10:07:07 AM
	 * 
	 * @param strSQL
	 * @return
	 * @throws SQLException
	 */
	public ResultSet executeQueryReadOnly(String strSQL) throws SQLException;

	/**
	 * 
	 * Get query result set <br>
	 * Created on: Nov 22, 2012 10:06:39 AM
	 * 
	 * @param strSQL
	 * @return
	 * @throws SQLException
	 */
	public ResultSet executeQuery(String strSQL) throws SQLException;

	/**
	 * close connection <br>
	 * Created on: Nov 22, 2012 10:10:24 AM
	 */
	public void close();

	/**
	 * check connection <br>
	 * Created on: Nov 22, 2012 10:10:40 AM
	 * 
	 * @return
	 * @throws SQLException
	 */
	public boolean isConnected() throws SQLException;
}
