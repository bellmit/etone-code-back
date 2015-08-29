/**
 * z.z.w.db.OracleJDBCInfo.java
 */
package z.z.w.db;

/**
 * @author Wu Zhenzhen
 * @version Nov 21, 2012 5:28:30 PM
 * @deprecated 2013-8-19 11:16:03 使用<code>z.z.w.db.upgrade</code>包下新的數據庫連接方式
 */
public class OracleJDBCInfo extends JdbcInfo {

	/**
	 * <br>
	 * Created on: Nov 21, 2012 5:28:30 PM
	 */
	public OracleJDBCInfo() {
		setDriver("oracle.jdbc.driver.OracleDriver");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see z.z.w.db.JdbcInfo#getConnectionURL()
	 */
	@Override
	public String getConnectionURL() {
		return "jdbc:oracle:thin:@" + getServer() + ":" + getPort() + ":"
				+ getDatabase();
	}
}
