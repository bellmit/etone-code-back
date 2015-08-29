/**
 * z.z.w.db.upgrade.ConnHandler.java
 */
package z.z.w.db.upgrade;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Wu Zhenzhen
 * @version 2013-7-15 上午11:57:11
 */
public class ConnHandler implements InvocationHandler {

	private final static String CLOSE_METHOD_NAME = "close";
	private Connection conn = null;
	// 数据库的忙状态
	private boolean inUse = false;
	// 用户最后一次访问该连接方法的时间
	private long lastAccessTime = System.currentTimeMillis();

	/**
	 * <br>
	 * Created on: 2013-7-15 上午11:58:02
	 * 
	 * @param conn
	 * @param inUse
	 */
	public ConnHandler(Connection conn, boolean inUse) {
		super();
		this.conn = conn;
		this.inUse = inUse;
	}

	/**
	 * <br>
	 * Created on: 2013-7-15 上午11:58:21
	 * 
	 * @return the conn
	 */
	public Connection getConnection() {
		// 返回数据库连接conn的接管类，以便截住close方法

		// return conn;

		// TODO -- 2013-8-21 0:01:09 無法連接mysql數據庫
		return (Connection) Proxy.newProxyInstance(conn.getClass()
				.getClassLoader(), conn.getClass().getInterfaces(), this);
	}

	/**
	 * 该方法真正的关闭了数据库的连接 <br>
	 * Created on: 2013-7-15 上午11:59:30
	 * 
	 * @throws SQLException
	 */
	public void close() throws SQLException {
		// 由于类属性conn是没有被接管的连接，因此一旦调用close方法后就直接关闭连接
		conn.close();
	}

	/**
	 * <br>
	 * Created on: 2013-7-15 上午11:59:44
	 * 
	 * @return the inUse
	 */
	public boolean isInUse() {
		return inUse;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.reflect.InvocationHandler#invoke(java.lang.Object,
	 * java.lang.reflect.Method, java.lang.Object[])
	 */
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		Object obj = null;
		// 判断是否调用了close的方法，如果调用close方法则把连接置为无用状态
		if (CLOSE_METHOD_NAME.equals(method.getName())) {
			setInUse(false);
		} else {
			obj = method.invoke(conn, args);
		}

		// 设置最后一次访问时间，以便及时清除超时的连接
		lastAccessTime = System.currentTimeMillis();
		return obj;
	}

	/**
	 * <br>
	 * Created on: 2013-7-15 下午12:00:07
	 * 
	 * @return the lastAccessTime
	 */
	public long getLastAccessTime() {
		return lastAccessTime;
	}

	/**
	 * <br>
	 * Created on: 2013-7-15 下午12:00:07
	 * 
	 * @param inUse
	 *            the inUse to set
	 */
	public void setInUse(boolean inUse) {
		this.inUse = inUse;
	}

}
