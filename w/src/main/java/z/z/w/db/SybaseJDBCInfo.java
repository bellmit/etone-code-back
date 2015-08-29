/**
 * z.z.w.db.SybaseJDBCInfo.java
 */
package z.z.w.db;

/**
 * @author Wu Zhenzhen
 * @version Nov 21, 2012 5:24:07 PM
 * @deprecated 2013-8-19 11:16:03 使用<code>z.z.w.db.upgrade</code>包下新的數據庫連接方式
 */
public class SybaseJDBCInfo extends JdbcInfo {

	/**
	 * SybaseJDBCInfo constructor <br>
	 * Created on: Nov 21, 2012 5:29:14 PM
	 */
	public SybaseJDBCInfo() {
		setDriver("com.sybase.jdbc3.jdbc.SybDriver");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see z.z.w.db.JdbcInfo#getConnectionURL()
	 */
	@Override
	public String getConnectionURL() {
		return "jdbc:sybase:Tds:" + getServer() + ":" + getPort() + "/"
				+ getDatabase();
	}
}
