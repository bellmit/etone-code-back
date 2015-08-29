/**
 * z.z.w.db.upgrade.exec.CacheDBFactory.java
 */
package z.z.w.db.upgrade.exec;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.NameAlreadyBoundException;
import javax.sql.DataSource;

import z.z.w.common.DataTools;
import z.z.w.common.DateTools;
import z.z.w.common.ThreadTools;
import z.z.w.db.upgrade.ConnFactory;
import z.z.w.db.upgrade.param.ConnParam;
import z.z.w.log.LogUtil;

/**
 * @author Wu Zhenzhen
 * @version 2013-8-19 上午11:23:32
 */
public abstract class CacheDBFactory {

	public static final String DB_CONN_POOL_NAME = "DB_CONN_POOL";
	private static final int RE_CONNECTOIN_DB_POOL_TIMES = 9;
	private static int connPoolCounter = 0;

	private static DataSource ds = null;
	protected Connection conn = null;

	protected PreparedStatement pstmt = null;
	protected ResultSet rs = null;

	protected boolean rsGetFlag = false;

	protected abstract boolean initPreparedStatement();

	protected abstract boolean getCache();

	/**
	 * <br>
	 * Created on: 2013-8-9 下午04:25:53
	 */
	public static boolean initDBConnectionPool(ConnParam connParam) {

		try {
			ConnFactory.bind(DB_CONN_POOL_NAME, connParam);
			return true;
		} catch (NameAlreadyBoundException e) {
			LogUtil.getLogger(CacheDBFactory.class).info(
					"Bind database : [{}] complete.", DB_CONN_POOL_NAME);
			return true;
		} catch (Exception e) {
			LogUtil.getLogger(CacheDBFactory.class)
					.error("When [{} - {}] ,  \n  MASSAGE : {} \n  CAUSE :{} \n  CLASS : {}",
							new Object[] { CacheDBFactory.class.getName(),
									" Bind connection --> Exception() ",
									e.getMessage(), e.getCause(), e.getClass() });

			if (connPoolCounter++ > RE_CONNECTOIN_DB_POOL_TIMES) {
				LogUtil.getLogger(CacheDBFactory.class)
						.warn("Create db connection pool error more then 10 times,System.exit(1).");
				System.exit(1);
			}

			LogUtil.getLogger(CacheDBFactory.class)
					.warn("Create db connection pool error! after 6s reconnection [{}] ...",
							connPoolCounter);

			ThreadTools.sleepSeconds(6);

			return initDBConnectionPool(connParam);
		}
	}

	/**
	 * <br>
	 * Created on: 2013-8-19 上午11:23:32
	 */
	public CacheDBFactory() {
		super();
		init();
	}

	/**
	 * <br>
	 * Created on: 2013-8-9 下午07:21:04
	 */
	private boolean init() {
		try {
			if (DataTools.isEmpty(ds)) {
				ds = ConnFactory.lookup(DB_CONN_POOL_NAME);
				LogUtil.getLogger(getClass()).info("Geted dataSource ...");
			}
			return true;
		} catch (Exception e) {
			LogUtil.getLogger(getClass())
					.error("When [{} - {}] , \n ERROR : MASSAGE : {} \n         CAUSE :{} \n         CLASS : {}\n",
							new Object[] { this.getClass().getName(),
									"get data source --> Exception()",
									e.getMessage(), e.getCause(), e.getClass() });

			LogUtil.getLogger(getClass()).warn(
					"Init dataSource error! 3s reinit ...");
			ThreadTools.sleepSeconds(3);

			return init();
		}
	}

	/**
	 * <br>
	 * Created on: 2013-8-9 下午07:28:39
	 * 
	 * @return
	 */
	protected boolean setConnection() {
		try {
			if (DataTools.isEmpty(conn))
				conn = ds.getConnection();
			return true;
		} catch (SQLException e) {
			LogUtil.getLogger(getClass())
					.error("When [{} - {}] , \n       : MASSAGE : {} \n         CAUSE :{} \n         CLASS : {}\n",
							new Object[] { this.getClass().getName(),
									"get db connection --> Exception()",
									e.getMessage(), e.getCause(), e.getClass() });

			return false;
		}
	}

	/**
	 * 
	 * <br>
	 * Created on: 2013-8-9 下午07:25:54
	 */
	protected void waitCacheUpdated() {

		while (true) {
			if (rsGetFlag)
				break;
			else {
				LogUtil.getLogger(getClass()).warn(
						"Cache update flag :[{}-[{}]].", rsGetFlag,
						getClass().getSimpleName());
				ThreadTools.sleep(300);
			}
		}
	}

	/**
	 * 
	 * <br>
	 * Created on: 2013-8-9 下午07:34:01
	 */
	protected void updateCache() {
		while (true) {

			long startTime = DateTools.getCurrentDateToLong();

			try {

				LogUtil.getLogger(getClass()).info(
						"Start update [{}] cache map ...",
						getClass().getSimpleName());

				if (!setConnection())
					continue;

				if (!initPreparedStatement())
					continue;

				if (!getCache())
					continue;

			} catch (Exception e) {
				LogUtil.getLogger(getClass())
						.error("When [{} - {}] ,  \n  MASSAGE : {} \n  CAUSE :{} \n  CLASS : {}",
								new Object[] { this.getClass().getName(),
										"update cache ps --> Exception() ",
										e.getMessage(), e.getCause(),
										e.getClass() });

			} finally {
				LogUtil.getLogger(getClass()).info(
						"Update [{}] cache map,use time : [{}]ms.",
						getClass().getSimpleName(),
						((DateTools.getCurrentDateToLong() - startTime)));

				ThreadTools.sleep(1000 * 60 * 60 * 3);

			}
		}
	}

	/**
	 * 
	 * <br>
	 * Created on: 2013-8-9 下午07:26:25
	 */
	protected void clearWork() {
		clearResultSet();
		clearPreparedStatement();
		clearConnection();
	}

	/**
	 * <br>
	 * Created on: 2013-8-9 下午07:26:35
	 */
	protected void clearConnection() {
		try {
			if (!DataTools.isEmpty(conn)) {
				conn.close();
				conn = null;
			}
		} catch (SQLException e) {
		} finally {
			conn = null;
		}
	}

	/**
	 * <br>
	 * Created on: 2013-8-9 下午07:26:33
	 */
	protected void clearPreparedStatement() {
		try {
			if (!DataTools.isEmpty(this.pstmt)) {
				this.pstmt.close();
				this.pstmt = null;
			}
		} catch (SQLException e) {
		} finally {
			this.pstmt = null;
		}
	}

	/**
	 * <br>
	 * Created on: 2013-8-9 下午07:26:29
	 */
	protected void clearResultSet() {
		try {
			if (!DataTools.isEmpty(rs)) {
				rs.close();
				rs = null;
			}
		} catch (SQLException e) {
		} finally {
			rs = null;
		}
	}
}
