/**
 * z.z.w.db.MySQLJDBCInfo.java
 */
package z.z.w.db;

import z.z.w.common.DataTools;

/**
 * @author Wu Zhenzhen
 * @version Nov 21, 2012 5:31:19 PM
 * @deprecated 2013-8-19 11:16:03 使用<code>z.z.w.db.upgrade</code>包下新的數據庫連接方式
 */
public class MySQLJDBCInfo extends JdbcInfo {

	/**
	 * <br>
	 * Created on: Nov 21, 2012 5:31:20 PM
	 */
	public MySQLJDBCInfo() {
		setDriver("org.gjt.mm.mysql.Driver");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see z.z.w.db.JdbcInfo#getConnectionURL()
	 */
	@Override
	public String getConnectionURL() {

		if (DataTools.isEmpty(getCharset()))
			return "jdbc:mysql://" + getServer() + "/" + getDatabase()
					+ "?user=" + getUser() + "&password=" + getPassword()
					+ "&useUnicode=true";
		return "jdbc:mysql://" + getServer() + "/" + getDatabase() + "?user="
				+ getUser() + "&password=" + getPassword()
				+ "&useUnicode=true&characterEncoding=" + getCharset();
	}
}
