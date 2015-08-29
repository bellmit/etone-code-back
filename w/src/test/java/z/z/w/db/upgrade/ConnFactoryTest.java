/**
 * z.z.w.db.upgrade.ConnFactoryTest.java
 */
package z.z.w.db.upgrade;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.NameAlreadyBoundException;
import javax.sql.DataSource;

import org.junit.BeforeClass;
import org.junit.Test;

import z.z.w.common.DataTools;
import z.z.w.common.ThreadTools;
import z.z.w.db.upgrade.param.ConnParam;
import z.z.w.db.upgrade.param.SybaseParam;
import z.z.w.log.LogUtil;

/**
 * @author Wu Zhenzhen
 * @version 2013-8-19 上午11:18:20
 */
public class ConnFactoryTest {
	private static final int RE_CONNECTOIN_DB_POOL_TIMES = 9;
	private static int connPoolCounter = 0;
	private static DataSource ds = null;
	private static final String CACHE_DB_POOL_NAME = "ETL_CACHE_POOL";
	protected Connection conn = null;

	@BeforeClass
	public static void beforeClass() {
		initDBConnectionPool();
	}

	/**
	 * <br>
	 * Created on: 2013-8-19 上午11:18:50
	 */
	private static boolean initDBConnectionPool() {
		ConnParam connParam = new SybaseParam();

		connParam.setUser("DBA");
		connParam.setPassword("sql");
		connParam.setPort(2688);
		connParam.setServer("192.168.8.239");
		connParam.setDatabase("asiqssmp");

		try {
			ConnFactory.bind(CACHE_DB_POOL_NAME, connParam);
			return true;
		} catch (NameAlreadyBoundException e) {
			LogUtil.getLogger(ConnFactoryTest.class).info(
					"Bind database : [{}] complete.", CACHE_DB_POOL_NAME);
			return true;
		} catch (Exception e) {
			LogUtil.getLogger(ConnFactoryTest.class)
					.error("When [{} - {}] ,  \n  MASSAGE : {} \n  CAUSE :{} \n  CLASS : {}",
							new Object[] { ConnFactoryTest.class.getName(),
									" Bind connection --> Exception() ",
									e.getMessage(), e.getCause(), e.getClass() });

			if (connPoolCounter++ > RE_CONNECTOIN_DB_POOL_TIMES) {
				LogUtil.getLogger(ConnFactoryTest.class)
						.warn("Create db connection pool error more then 10 times,System.exit(1).");
				System.exit(1);
			}

			LogUtil.getLogger(ConnFactoryTest.class)
					.warn("Create db connection pool error! after 6s reconnection [{}] ...",
							connPoolCounter);

			ThreadTools.sleepSeconds(6);

			return initDBConnectionPool();
		}
	}

	/**
	 * Test method for
	 * {@link z.z.w.db.upgrade.ConnFactory#lookup(java.lang.String)}.
	 */
	@Test
	public void testLookup() {
		init();
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
	 * <br>
	 * Created on: 2013-8-9 下午07:21:04
	 */
	private boolean init() {
		try {
			if (DataTools.isEmpty(ds)) {
				ds = ConnFactory.lookup(CACHE_DB_POOL_NAME);
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

}
