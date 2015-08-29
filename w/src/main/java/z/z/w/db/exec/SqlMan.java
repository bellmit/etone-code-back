/**
 * z.z.w.db.exec.SqlMan.java
 */
package z.z.w.db.exec;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.naming.NamingException;

import z.z.w.db.JdbcInfo;

/**
 * @author Wu Zhenzhen
 * @version Nov 22, 2012 10:11:16 AM
 * @deprecated 2013-8-19 11:16:03 使用<code>z.z.w.db.upgrade</code>包下新的數據庫連接方式
 */
public class SqlMan implements Sqlable {

	private int defBatchTop = 200;
	private int deep = 3;

	private Connection conn = null;
	private Connector connector = null;

	private Statement updateStatement = null;
	private Statement[] statements = null;
	private Statement[] roStatements = null;
	private Collection<Statement> dyStatementList = null;

	public SqlMan() {
		this(3);
	}

	public SqlMan(String dsn) {
		this(dsn, 3);
	}

	public SqlMan(int deep) {
		this(null, deep);
	}

	public SqlMan(String dsn, int deep) {
		this.deep = deep;

		this.dyStatementList = new ArrayList<Statement>();

		this.statements = new Statement[this.deep];
		this.roStatements = new Statement[this.deep];

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see z.z.w.db.exec.Sqlable#createConnection(java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	public void createConnection(String driver, String url, String user,
			String pwd) throws SQLException {
		try {
			close();
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, pwd);
		} catch (ClassNotFoundException e) {
			System.out.println("    JDBC load class createConnection ["
					+ driver + "] error ! PORGRAMME EXIT.");
			System.out.println("MASSAGE : " + e.getMessage() + "\nCAUSE : "
					+ e.getCause() + "\nCLASS : " + e.getClass());
			System.exit(1);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see z.z.w.db.exec.Sqlable#createConnection(java.lang.String)
	 */
	public void createConnection(String dsn) throws SQLException {
		try {
			close();
			connector = new Connector();
			conn = connector.getConnection(dsn);
		} catch (NamingException e) {
			System.out.println("    JDBC load class createConnection [" + dsn
					+ "] error ! PORGRAMME EXIT.");
			System.out.println("MASSAGE : " + e.getMessage() + "\nCAUSE : "
					+ e.getCause() + "\nCLASS : " + e.getClass());
			System.exit(1);
		}
	}

	/**
	 * Get connection from JDBCINFO <br>
	 * Created on: Nov 22, 2012 10:26:15 AM
	 * 
	 * @param info
	 * @throws SQLException
	 */
	public void createConnection(JdbcInfo info) throws SQLException {
		createConnection(info.getDriver(), info.getConnectionURL(),
				info.getUser(), info.getPassword());
	}

	/**
	 * return PreparedStatement instance <br>
	 * Created on: Nov 22, 2012 10:32:06 AM
	 * 
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	public PreparedStatement prepareStatement(String sql) throws SQLException {

		PreparedStatement stmt = null;
		stmt = conn.prepareStatement(sql);
		dyStatementList.add(stmt);

		return stmt;
	}

	/**
	 * return CallableStatement instance <br>
	 * Created on: Nov 22, 2012 10:32:55 AM
	 * 
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	public CallableStatement prepareCall(String sql) throws SQLException {
		CallableStatement stmt = null;
		stmt = conn.prepareCall(sql);
		dyStatementList.add(stmt);

		return stmt;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see z.z.w.db.exec.Sqlable#executeUpdate(java.lang.String)
	 */
	public boolean executeUpdate(String strSQL) throws SQLException {
		if (null == conn) {
			return false;
		}

		if (null == updateStatement) {
			updateStatement = conn.createStatement();
		}
		return updateStatement.executeUpdate(strSQL) > 0;
	}

	/**
	 * exec query sql <br>
	 * Created on: Nov 22, 2012 10:34:28 AM
	 * 
	 * @param index
	 * @param strSQL
	 * @return
	 * @throws SQLException
	 */
	public ResultSet executeQuery(int index, String strSQL) throws SQLException {
		if (null == conn)
			return null;

		if (null == statements[index - 1])
			statements[index - 1] = conn.createStatement();

		return statements[index - 1].executeQuery(strSQL);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see z.z.w.db.exec.Sqlable#submitBatch(java.util.Collection)
	 */
	public boolean submitBatch(Collection<String> sqlList) throws SQLException {
		return submitBatch(sqlList, defBatchTop);
	}

	/**
	 * submit batch sql <br>
	 * Created on: Nov 22, 2012 10:42:19 AM
	 * 
	 * @param sqlList
	 * @param batch_top
	 * @return
	 * @throws SQLException
	 */
	public boolean submitBatch(Collection<String> sqlList, int batch_top)
			throws SQLException {
		boolean result = false;
		int[] updateCount = null;

		if (null == updateStatement)
			updateStatement = conn.createStatement();
		updateStatement.clearBatch();

		// 添加SQL语句序列
		int batch_couter = 0;

		Iterator<String> it = sqlList.iterator();
		int i = 0;
		while (it.hasNext()) {
			String sql = it.next();
			updateStatement.addBatch(sql);
			batch_couter++;
			if ((batch_couter == batch_top) || (i == (sqlList.size() - 1))) {
				// 执行批量SQL语句
				if (null != updateStatement) {
					updateCount = null;
					updateCount = updateStatement.executeBatch();
					// 如果执行成功
					if ((null != updateCount)
							&& (updateCount.length == batch_couter)) {

						conn.commit();
						result = true;
					} else {
						conn.rollback();
						result = false;
					}
				}
				updateStatement.clearBatch();
				batch_couter = 0;
			}
			i++;
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see z.z.w.db.exec.Sqlable#initBatch()
	 */
	public boolean initBatch() throws SQLException {
		if (null == conn)
			return false;
		conn.setAutoCommit(false);
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see z.z.w.db.exec.Sqlable#executeQueryReadOnly(java.lang.String)
	 */
	public ResultSet executeQueryReadOnly(String strSQL) throws SQLException {
		return executeQueryReadOnly(1, strSQL);
	}

	/**
	 * exec query read only <br>
	 * Created on: Nov 22, 2012 10:36:27 AM
	 * 
	 * @param index
	 * @param strSQL
	 * @return
	 * @throws SQLException
	 */
	public ResultSet executeQueryReadOnly(int index, String strSQL)
			throws SQLException {
		if (null == conn)
			return null;

		if (null == roStatements[index - 1])
			roStatements[index - 1] = conn.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

		return roStatements[index - 1].executeQuery(strSQL);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see z.z.w.db.exec.Sqlable#executeQuery(java.lang.String)
	 */
	public ResultSet executeQuery(String strSQL) throws SQLException {
		return executeQuery(1, strSQL);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see z.z.w.db.exec.Sqlable#close()
	 */
	public void close() {

		try {
			if (null != updateStatement) {
				updateStatement.close();
				updateStatement = null;
			}
		} catch (SQLException e) {
		} finally {
			updateStatement = null;
		}

		Statement stmt = null;
		for (int i = statements.length - 1; i >= 0; i--) {
			try {
				stmt = statements[i];
				if (null != stmt) {
					stmt.close();
					stmt = null;
				}
			} catch (SQLException e) {
			} finally {
				stmt = null;
			}
		}

		// roStatements
		for (int i = roStatements.length - 1; i >= 0; i--) {
			try {
				stmt = roStatements[i];
				if (null != stmt) {
					stmt.close();
					stmt = null;
				}
			} catch (SQLException e) {
			} finally {
				stmt = null;
			}
		}

		// dyStatementList
		Iterator<Statement> it = dyStatementList.iterator();
		while (it.hasNext()) {
			try {
				stmt = it.next();
				if (null != stmt) {
					stmt.close();
					stmt = null;
				}
			} catch (SQLException e) {
			} finally {
				stmt = null;
			}
		}

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
	public boolean isConnected() throws SQLException {
		return (null != conn) && (!conn.isClosed());
	}

	/**
	 * <br>
	 * Created on: Nov 22, 2012 11:22:07 AM
	 * 
	 * @return the dyStatementList
	 */
	protected Collection<Statement> getDyStatementList() {
		return dyStatementList;
	}

}
