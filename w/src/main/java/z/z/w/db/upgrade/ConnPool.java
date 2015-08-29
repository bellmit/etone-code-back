/**
 * z.z.w.db.upgrade.ConnPool.java
 */
package z.z.w.db.upgrade;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.sql.DataSource;

import z.z.w.db.upgrade.param.ConnParam;

/**
 * @author Wu Zhenzhen
 * @version 2013-7-15 下午02:14:11
 */
public class ConnPool implements DataSource {
	/**
	 * <br>
	 * Created on: 2013-7-15 下午02:18:16
	 * 
	 * @param connParam
	 */
	public ConnPool(ConnParam connParam) {
		super();
		this.connParam = connParam;
	}

	/* 数据库连接参数 */
	private ConnParam connParam = null;

	/* 数据库连接管理Proxy */
	private List<ConnHandler> handlers = new ArrayList<ConnHandler>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.sql.CommonDataSource#getLogWriter()
	 */
	public PrintWriter getLogWriter() throws SQLException {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.sql.CommonDataSource#setLogWriter(java.io.PrintWriter)
	 */
	public void setLogWriter(PrintWriter out) throws SQLException {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.sql.CommonDataSource#setLoginTimeout(int)
	 */
	public void setLoginTimeout(int seconds) throws SQLException {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.sql.CommonDataSource#getLoginTimeout()
	 */
	public int getLoginTimeout() throws SQLException {
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.sql.Wrapper#unwrap(java.lang.Class)
	 */
	public <T> T unwrap(Class<T> iface) throws SQLException {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.sql.Wrapper#isWrapperFor(java.lang.Class)
	 */
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.sql.DataSource#getConnection()
	 */
	public Connection getConnection() throws SQLException {
		return getConnection(connParam.getUser(), connParam.getPassword());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.sql.DataSource#getConnection(java.lang.String,
	 * java.lang.String)
	 */
	public Connection getConnection(String username, String password)
			throws SQLException {
		// 首先从连接池中找出空闲的对象
		Connection conn = getFreeConnection(0);
		if (conn == null) {
			// 判断是否超过最大连接数,如果超过最大连接数
			// 则等待一定时间查看是否有空闲连接,否则抛出异常告诉用户无可用连接
			if (getConnectionCount() >= connParam.getMaxConnection()) {
				conn = getFreeConnection(connParam.getWaitTime());
			} else {
				// 没有超过连接数，重新获取一个数据库的连接
				connParam.setUser(username);
				connParam.setPassword(password);
				Connection conn2 = DriverManager.getConnection(
						connParam.getUrl(), username, password);

				// 代理将要返回的连接对象
				ConnHandler handler = new ConnHandler(conn2, true);
				synchronized (handlers) {
					handlers.add(handler);
				}
				conn = handler.getConnection();
			}
		}
		return conn;
	}

	private int getConnectionCount() {
		return handlers.size();
	}

	/**
	 * 从连接池中取一个空闲的连接
	 * 
	 * @param nTimeout
	 *            如果该参数值为0则没有连接时只是返回一个null 否则的话等待nTimeout毫秒看是否还有空闲连接，如果没有抛出异常
	 * @return Connection
	 * @throws SQLException
	 */
	protected synchronized Connection getFreeConnection(long nTimeout)
			throws SQLException {
		Connection conn = null;
		// System.out.println("current z.z.w.db.upgrade lanny.pool size : " +
		// handlers.size());
		Iterator<ConnHandler> iter = handlers.iterator();
		while (iter.hasNext()) {
			ConnHandler handler = iter.next();
			if (!handler.isInUse()) {
				conn = handler.getConnection();
				handler.setInUse(true);
				break;
			}
		}
		if (conn == null && nTimeout > 0) {
			// 等待nTimeout毫秒以便看是否有空闲连接
			try {
				Thread.sleep(nTimeout);
			} catch (Exception e) {
			}
			conn = getFreeConnection(0);
			if (conn == null) {
				throw new SQLException("没有可用的数据库连接");
			}
		}
		return conn;
	}

	/**
	 * 关闭该连接池中的所有数据库连接
	 * 
	 * @return int 返回被关闭连接的个数
	 * @throws SQLException
	 */
	public int close() throws SQLException {
		int count = 0;
		SQLException excp = null;
		Iterator<ConnHandler> iter = handlers.iterator();
		while (iter.hasNext()) {
			try {
				(iter.next()).close();
				count++;
			} catch (Exception e) {
				if (e instanceof SQLException)
					excp = (SQLException) e;
			}
		}
		if (excp != null)
			throw excp;
		return count;
	}

	public void initConnection() throws SQLException, ClassNotFoundException {
		Class.forName(connParam.getDriver());
		for (int i = 0; i < connParam.getMinConnection(); i++) {
			Connection conn = DriverManager.getConnection(connParam.getUrl(),
					connParam.getUser(), connParam.getPassword());

			// 代理将要返回的连接对象
			ConnHandler handler = new ConnHandler(conn, false);
			synchronized (handlers) {
				handlers.add(handler);
			}
		}
	}

	public void stop() {
		Iterator<ConnHandler> iter = handlers.iterator();
		while (iter.hasNext()) {
			ConnHandler handler = iter.next();
			handler.setInUse(false);
		}
	}
}
