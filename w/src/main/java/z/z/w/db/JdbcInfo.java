/**
 * z.z.w.db.JdbcInfo.java
 */
package z.z.w.db;

import z.z.w.common.DESCodec;
import z.z.w.common.DataTools;
import z.z.w.env.Config;

/**
 * @author Wu Zhenzhen
 * @version Nov 21, 2012 5:21:43 PM
 * @deprecated 2013-8-19 11:16:03 使用<code>z.z.w.db.upgrade</code>包下新的數據庫連接方式
 */
public abstract class JdbcInfo {

	// SYBASE/ORACLE/MSSQL/JTDSSQL/MYSQL
	public static final String DB_SYBASE = "SYBASE";
	public static final String DB_ORACLE = "ORACLE";
	public static final String DB_MSSQL = "MSSQL";
	public static final String DB_JTDSSQL = "JTDSSQL";
	public static final String DB_MYSQL = "MYSQL";

	protected int version = 0;
	protected String driver = "<Driver>";
	protected String database = "<Database>";
	protected String user = "<User>";
	protected String password = "<Password>";
	protected String server = "<Server>";
	protected int port = 0;
	protected String charset = null;

	private String cipherKey = "";

	/**
	 * Get JDBC connection instance <br>
	 * Created on: Nov 22, 2012 9:50:52 AM
	 * 
	 * @param name
	 * @return
	 */
	public static JdbcInfo getInstance(String name) {

		name = name.toUpperCase();
		if (name.equals("SYBASE"))
			return new SybaseJDBCInfo();
		else if (name.equals("ORACLE"))
			return new OracleJDBCInfo();
		else if (name.equals("MSSQL"))
			return new MsSQLJDBCInfo();
		else if (name.equals("JTDSSQL"))
			return new MsSQLJDBCInfo();
		else if (name.equals("MYSQL"))
			return new MySQLJDBCInfo();
		return null;

	}

	/**
	 * Set JDBC info from config <br>
	 * 
	 * 如果密碼加密，請先設置密匙 <code>setCipherKey(cipherKey)</code> <br>
	 * Created on: Nov 22, 2012 9:52:03 AM
	 * 
	 * @param cfg
	 */
	public void loadFrom(Config cfg) {

		setServer(cfg.getString("server"));
		setPort(cfg.getInt("port"));
		setDatabase(cfg.getString("db"));
		setCharset(cfg.getString("charset"));
		setUser(cfg.getString("user"));
		String passwd = cfg.getString("passwd");

		if ((cfg.getBoolean("passwd.encode")) && !DataTools.isEmpty(passwd)) {
			passwd = DESCodec.decrypt(passwd, cipherKey);
		}

		setPassword(passwd);
	}

	/**
	 * return connecton url <br>
	 * Created on: Nov 22, 2012 10:28:40 AM
	 * 
	 * @return
	 */
	public String getConnectionURL() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "JdbcInfo [version=" + version + ", driver=" + driver
				+ ", database=" + database + ", user=" + user + ", password="
				+ password + ", server=" + server + ", port=" + port
				+ ", charset=" + charset + "]";
	}

	/**
	 * <br>
	 * Created on: Nov 22, 2012 10:00:51 AM
	 * 
	 * @return the version
	 */
	public int getVersion() {
		return version;
	}

	/**
	 * <br>
	 * Created on: Nov 22, 2012 10:00:51 AM
	 * 
	 * @param version
	 *            the version to set
	 */
	protected void setVersion(int version) {
		this.version = version;
	}

	/**
	 * <br>
	 * Created on: Nov 22, 2012 10:00:51 AM
	 * 
	 * @return the driver
	 */
	public String getDriver() {
		return driver;
	}

	/**
	 * <br>
	 * Created on: Nov 22, 2012 10:00:51 AM
	 * 
	 * @param driver
	 *            the driver to set
	 */
	protected void setDriver(String driver) {
		this.driver = driver;
	}

	/**
	 * <br>
	 * Created on: Nov 22, 2012 10:00:51 AM
	 * 
	 * @return the database
	 */
	public String getDatabase() {
		return database;
	}

	/**
	 * <br>
	 * Created on: Nov 22, 2012 10:00:51 AM
	 * 
	 * @param database
	 *            the database to set
	 */
	protected void setDatabase(String database) {
		this.database = database;
	}

	/**
	 * <br>
	 * Created on: Nov 22, 2012 10:00:51 AM
	 * 
	 * @return the user
	 */
	public String getUser() {
		return user;
	}

	/**
	 * <br>
	 * Created on: Nov 22, 2012 10:00:51 AM
	 * 
	 * @param user
	 *            the user to set
	 */
	protected void setUser(String user) {
		this.user = user;
	}

	/**
	 * <br>
	 * Created on: Nov 22, 2012 10:00:51 AM
	 * 
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * <br>
	 * Created on: Nov 22, 2012 10:00:51 AM
	 * 
	 * @param password
	 *            the password to set
	 */
	protected void setPassword(String password) {
		this.password = password;
	}

	/**
	 * <br>
	 * Created on: Nov 22, 2012 10:00:51 AM
	 * 
	 * @return the server
	 */
	public String getServer() {
		return server;
	}

	/**
	 * <br>
	 * Created on: Nov 22, 2012 10:00:51 AM
	 * 
	 * @param server
	 *            the server to set
	 */
	protected void setServer(String server) {
		this.server = server;
	}

	/**
	 * <br>
	 * Created on: Nov 22, 2012 10:00:51 AM
	 * 
	 * @return the port
	 */
	public int getPort() {
		return port;
	}

	/**
	 * <br>
	 * Created on: Nov 22, 2012 10:00:51 AM
	 * 
	 * @param port
	 *            the port to set
	 */
	protected void setPort(int port) {
		this.port = port;
	}

	/**
	 * <br>
	 * Created on: Nov 22, 2012 10:00:51 AM
	 * 
	 * @return the charset
	 */
	public String getCharset() {
		return charset;
	}

	/**
	 * <br>
	 * Created on: Nov 22, 2012 10:00:51 AM
	 * 
	 * @param charset
	 *            the charset to set
	 */
	protected void setCharset(String charset) {
		this.charset = charset;
	}

	/**
	 * <br>
	 * Created on: Apr 11, 2013 1:43:45 PM
	 * 
	 * @param cipherKey
	 *            the cipherKey to set
	 */
	public void setCipherKey(String cipherKey) {
		this.cipherKey = cipherKey;
	}

}
