/**
 * z.z.w.db.upgrade.param.ConnParam.java
 */
package z.z.w.db.upgrade.param;

import java.io.Serializable;

/**
 * @author Wu Zhenzhen
 * @version 2013-7-15 上午11:52:42
 */
public abstract class ConnParam implements Serializable {
	/**
	 * <br>
	 * Created on: 2013-7-15 上午11:53:28
	 */
	private static final long serialVersionUID = 3876050178551981905L;
	private String driver = null; // 数据库驱动程序
	private String url = null; // 数据连接的URL
	private String server = null; // 数据库名称
	private String database = null;
	private String user = null; // 数据库用户名
	private String password = null; // 数据库密码
	private int port = 0;

	private int minConnection = 5; // 初始化连接数
	private int maxConnection = 30; // 最大连接数
	private long timeoutValue = 600000; // 连接的最大空闲时间
	private long waitTime = 30000; // 取连接的时候如果没有可用连接最大的等待时间

	/**
	 * <br>
	 * Created on: 2013-7-15 上午11:53:12
	 */
	public ConnParam() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ConnParam [driver=" + driver + ", url=" + url + ", server="
				+ server + ", database=" + database + ", user=" + user
				+ ", password=" + password + ", port=" + port
				+ ", minConnection=" + minConnection + ", maxConnection="
				+ maxConnection + ", timeoutValue=" + timeoutValue
				+ ", waitTime=" + waitTime + "]";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((database == null) ? 0 : database.hashCode());
		result = prime * result + ((driver == null) ? 0 : driver.hashCode());
		result = prime * result + maxConnection;
		result = prime * result + minConnection;
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
		result = prime * result + port;
		result = prime * result + ((server == null) ? 0 : server.hashCode());
		result = prime * result + (int) (timeoutValue ^ (timeoutValue >>> 32));
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		result = prime * result + (int) (waitTime ^ (waitTime >>> 32));
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ConnParam other = (ConnParam) obj;
		if (database == null) {
			if (other.database != null)
				return false;
		} else if (!database.equals(other.database))
			return false;
		if (driver == null) {
			if (other.driver != null)
				return false;
		} else if (!driver.equals(other.driver))
			return false;
		if (maxConnection != other.maxConnection)
			return false;
		if (minConnection != other.minConnection)
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (port != other.port)
			return false;
		if (server == null) {
			if (other.server != null)
				return false;
		} else if (!server.equals(other.server))
			return false;
		if (timeoutValue != other.timeoutValue)
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		if (waitTime != other.waitTime)
			return false;
		return true;
	}

	/**
	 * <br>
	 * Created on: 2013-7-15 上午11:53:18
	 * 
	 * @return the driver
	 */
	public String getDriver() {
		return driver;
	}

	/**
	 * <br>
	 * Created on: 2013-7-15 上午11:53:18
	 * 
	 * @param driver
	 *            the driver to set
	 */
	public void setDriver(String driver) {
		this.driver = driver;
	}

	/**
	 * <br>
	 * Created on: 2013-7-15 上午11:53:18
	 * 
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * <br>
	 * Created on: 2013-7-15 上午11:53:18
	 * 
	 * @param url
	 *            the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * <br>
	 * Created on: 2013-7-15 上午11:53:18
	 * 
	 * @return the server
	 */
	public String getServer() {
		return server;
	}

	/**
	 * <br>
	 * Created on: 2013-7-15 上午11:53:18
	 * 
	 * @param server
	 *            the server to set
	 */
	public void setServer(String server) {
		this.server = server;
	}

	/**
	 * <br>
	 * Created on: 2013-7-15 上午11:53:18
	 * 
	 * @return the database
	 */
	public String getDatabase() {
		return database;
	}

	/**
	 * <br>
	 * Created on: 2013-7-15 上午11:53:18
	 * 
	 * @param database
	 *            the database to set
	 */
	public void setDatabase(String database) {
		this.database = database;
	}

	/**
	 * <br>
	 * Created on: 2013-7-15 上午11:53:18
	 * 
	 * @return the user
	 */
	public String getUser() {
		return user;
	}

	/**
	 * <br>
	 * Created on: 2013-7-15 上午11:53:18
	 * 
	 * @param user
	 *            the user to set
	 */
	public void setUser(String user) {
		this.user = user;
	}

	/**
	 * <br>
	 * Created on: 2013-7-15 上午11:53:18
	 * 
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * <br>
	 * Created on: 2013-7-15 上午11:53:18
	 * 
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * <br>
	 * Created on: 2013-7-15 上午11:53:18
	 * 
	 * @return the port
	 */
	public int getPort() {
		return port;
	}

	/**
	 * <br>
	 * Created on: 2013-7-15 上午11:53:18
	 * 
	 * @param port
	 *            the port to set
	 */
	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * <br>
	 * Created on: 2013-7-15 上午11:53:18
	 * 
	 * @return the minConnection
	 */
	public int getMinConnection() {
		return minConnection;
	}

	/**
	 * <br>
	 * Created on: 2013-7-15 上午11:53:18
	 * 
	 * @param minConnection
	 *            the minConnection to set
	 */
	public void setMinConnection(int minConnection) {
		this.minConnection = minConnection;
	}

	/**
	 * <br>
	 * Created on: 2013-7-15 上午11:53:18
	 * 
	 * @return the maxConnection
	 */
	public int getMaxConnection() {
		return maxConnection;
	}

	/**
	 * <br>
	 * Created on: 2013-7-15 上午11:53:18
	 * 
	 * @param maxConnection
	 *            the maxConnection to set
	 */
	public void setMaxConnection(int maxConnection) {
		this.maxConnection = maxConnection;
	}

	/**
	 * <br>
	 * Created on: 2013-7-15 上午11:53:18
	 * 
	 * @return the timeoutValue
	 */
	public long getTimeoutValue() {
		return timeoutValue;
	}

	/**
	 * <br>
	 * Created on: 2013-7-15 上午11:53:18
	 * 
	 * @param timeoutValue
	 *            the timeoutValue to set
	 */
	public void setTimeoutValue(long timeoutValue) {
		this.timeoutValue = timeoutValue;
	}

	/**
	 * <br>
	 * Created on: 2013-7-15 上午11:53:18
	 * 
	 * @return the waitTime
	 */
	public long getWaitTime() {
		return waitTime;
	}

	/**
	 * <br>
	 * Created on: 2013-7-15 上午11:53:18
	 * 
	 * @param waitTime
	 *            the waitTime to set
	 */
	public void setWaitTime(long waitTime) {
		this.waitTime = waitTime;
	}

}
