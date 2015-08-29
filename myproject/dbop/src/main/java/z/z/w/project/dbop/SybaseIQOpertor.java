/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.dbop.UpdateCgiDataToMysql.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-8-21 上午01:28:01 
 *   LastChange: 2013-8-21 上午01:28:01 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.dbop;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import z.z.w.common.AESCoder;
import z.z.w.common.DataTools;
import z.z.w.common.DateTools;
import z.z.w.db.upgrade.exec.CacheDBFactory;
import z.z.w.db.upgrade.param.ConnParam;
import z.z.w.db.upgrade.param.SybaseParam;
import z.z.w.project.dbop.config.DbConfig;
import z.z.w.project.dbop.vo.ServerVo;
import z.z.w.project.util.common.LogTools;

/**
 * z.z.w.project.dbop.SybaseIQOpertor.java
 */
public class SybaseIQOpertor extends CacheDBFactory implements Runnable {

	// 初始密匙 03637EE2928AA0589B582B98C187027F
	private static final byte[] INIT_KEY = AESCoder
			.parseHexStr2Byte("03637EE2928AA0589B582B98C187027F");
	// private static final byte[] INIT_KEY = AESCoder.initSecretKey();
	private static byte[] ENCRYPT_KEY = new byte[0];

	private static ExecutorService service = Executors.newFixedThreadPool(6);
	private static int index = 1;

	static {
		ConnParam connParam = new SybaseParam();

		/*********************************************************************/
		connParam.setUser(DbConfig.getDBUser());
		connParam.setPassword(DbConfig.getDBPassword());
		connParam.setPort(DbConfig.getDBPort());
		connParam.setServer(DbConfig.getDBServer());
		connParam.setDatabase(DbConfig.getDBDataBase());
		/*********************************************************************/

		initDBConnectionPool(connParam);
	}
	private List<ServerVo> list = null;

	/**
	 * <br>
	 * Created on: 2013-8-21 上午01:28:01
	 */
	public SybaseIQOpertor() {
		super();

		setKey();

		list = new ArrayList<ServerVo>();

		new Thread(new Runnable() {

			public void run() {
				updateCache();
			}
		}).start();
	}

	/**
	 * <br>
	 * Created on: 2013-9-11 上午09:55:38
	 */
	private void setKey() {
		try {
			ENCRYPT_KEY = AESCoder.encrypt(
					CacheDBFactory.DB_CONN_POOL_NAME.getBytes(), INIT_KEY);

			// 921C138A6BAD3E8567BABDFAB3FBFCA6
			LogTools.getLogger(getClass()).info("INIT_KEY : [{}]",
					AESCoder.parseByte2HexStr(INIT_KEY));
			LogTools.getLogger(getClass()).info("ENCRYPT_KEY : [{}]",
					AESCoder.parseByte2HexStr(ENCRYPT_KEY));
		} catch (Exception e) {
			LogTools.getLogger(getClass())
					.error("When [{} - {} --> Exception ] ,  \n  MASSAGE : {} \n  CAUSE :{} \n  CLASS : {}",
							new Object[] { "SybaseIQOpertor", "setKey()",
									e.getMessage(), e.getCause(), e.getClass() });
			LogTools.getLogger(getClass()).warn(
					"Set key error , programme is exit.");
			System.exit(1);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	public void run() {

		try {
			waitCacheUpdated();

			execute();

		} catch (Exception e) {
			LogTools.getLogger(getClass())
					.error("When [{} - {} --> Exception ] ,  \n  MASSAGE : {} \n  CAUSE :{} \n  CLASS : {}",
							new Object[] { "SybaseIQOpertor", "run()",
									e.getMessage(), e.getCause(), e.getClass() });
		} finally {
			list.clear();
			list = null;
		}
	}

	/**
	 * <br>
	 * Created on: 2013-8-21 上午01:34:33
	 */
	private void execute() {

		long startTime = DateTools.getCurrentDateToLong();

		try {

			if (DataTools.isEmpty(list))
				return;

			// debug print
			LogTools.getLogger(getClass()).debug(
					"EDProEntity list size :[{}].", list.size());

			// encry product
			encryptProduct();

			if (DataTools.isEmpty(list))
				return;

			LogTools.getLogger(getClass()).debug(
					"EDProEntity encry list size :[{}].", list.size());

			// update new product def
			updateNewProductDef();

		} finally {

			// clear work
			clearWork();

			long useTime = DateTools.getCurrentDateToLong() - startTime;
			LogTools.getLogger(getClass()).info(
					"Load base data catch complete,use times :[{}]min.",
					(useTime / 1000 / 60));
		}
	}

	/**
	 * <br>
	 * Created on: 2013-9-10 上午11:36:13
	 */
	private void updateNewProductDef() {
		try {

			final int len = list.size();

			Iterator<ServerVo> it = list.iterator();
			while (it.hasNext()) {
				try {
					ServerVo tb = it.next();

					// TODO -- 2013-8-15 14:51:51 業務規則加密測試表
					String sql = "update tbServiceRule_2013090915 set vcIp = '"
							+ tb.getVcIp() + "' , vcUrl = '" + tb.getVcUrl()
							+ "'  where vcsUrl = '" + tb.getVcsUrl()
							+ "' and vcsIp = '" + tb.getVcsIp() + "' ";

					final String ssql = sql;

					// if (DataTools.isEmpty(service))
					// service = Executors.newFixedThreadPool(6);
					//
					// service.execute(new Runnable() {
					//
					// public void run() {
					long startTime = DateTools.getCurrentDateToLong();
					try {
						if (!setConnection())
							return;
						LogTools.getLogger(getClass())
								.info("SQL : [{}].", ssql);
						pstmt = conn.prepareStatement(ssql);
						// TODO -- 2013-9-11 9:53:51 test
						pstmt.execute();
					} catch (Exception e) {
						LogTools.getLogger(getClass())
								.error("When [{} - {}] , error : \n MASSAGE : {} \n CAUSE :{} \n CLASS : {}\n",
										new Object[] {
												this.getClass().getName(),
												" inner --> Exception() ",
												e.getMessage(), e.getCause(),
												e.getClass() });
					} finally {
						long useTime = DateTools.getCurrentDateToLong()
								- startTime;
						LogTools.getLogger(getClass()).info(
								"Update [[{}]-{}],use times :[{}]ms.",
								new Object[] { index++, len, (useTime) });
					}
					// }
					// });
					//
					// ThreadTools.sleep(6);

				} catch (Exception e) {
					LogTools.getLogger(getClass())
							.error("When [{} - {}] , error : \n MASSAGE : {} \n CAUSE :{} \n CLASS : {}\n",
									new Object[] { this.getClass().getName(),
											" outter --> Exception() ",
											e.getMessage(), e.getCause(),
											e.getClass() });
				}

			}

		} catch (Exception e) {
			LogTools.getLogger(getClass())
					.error("When [{} - {}] , error : \n MASSAGE : {} \n CAUSE :{} \n CLASS : {}\n",
							new Object[] { this.getClass().getName(),
									" fin outter --> Exception() ",
									e.getMessage(), e.getCause(), e.getClass() });
		} finally {
			service.shutdown();
			clearWork();
		}
	}

	/**
	 * <br>
	 * Created on: 2013-9-10 上午11:31:41
	 */
	private void encryptProduct() {
		Iterator<ServerVo> it = list.iterator();
		while (it.hasNext()) {
			try {
				ServerVo tb = it.next();

				/** encrypt url */
				String encryUrl = AESCoder.parseByte2HexStr(AESCoder.encrypt(tb
						.getVcUrl().getBytes(), ENCRYPT_KEY));
				if (!DataTools.isEmpty(encryUrl))
					tb.setVcUrl(encryUrl);

				/** encrypt server ip */
				String encryServerIp = AESCoder.parseByte2HexStr(AESCoder
						.encrypt(tb.getVcIp().getBytes(), ENCRYPT_KEY));
				if (!DataTools.isEmpty(encryServerIp))
					tb.setVcIp(encryServerIp);

				/******************************************************************************************/
				/** encrypt url */
				// String ENCRYPT_KEY = DESCodec.encrypt(tb.getVcUrl(),
				// CacheDBFactory.DB_CONN_POOL_NAME);
				// String encryUrl = DESCodec.encrypt(tb.getVcUrl(),
				// ENCRYPT_KEY);
				// if (!DataTools.isEmpty(encryUrl))
				// tb.setVcUrl(encryUrl);
				/** encrypt server ip */
				// ENCRYPT_KEY = DESCodec.encrypt(tb.getVcIp(),
				// CacheDBFactory.DB_CONN_POOL_NAME);
				// String encryServerIp = DESCodec.encrypt(tb.getVcIp(),
				// ENCRYPT_KEY);
				// if (!DataTools.isEmpty(encryServerIp))
				// tb.setVcIp(encryServerIp);
				/******************************************************************************************/

			} catch (Exception e) {
				LogTools.getLogger(getClass())
						.error("When [{} - {}] , error : \n MASSAGE : {} \n CAUSE :{} \n CLASS : {}\n",
								new Object[] { this.getClass().getName(),
										" encryptProduct --> Exception() ",
										e.getMessage(), e.getCause(),
										e.getClass() });
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see z.z.w.db.upgrade.exec.CacheDBFactory#initPreparedStatement()
	 */
	@Override
	protected boolean initPreparedStatement() {
		try {
			String sql = " commit;select vcIp,vcUrl,vcsIp,vcsUrl from tbServiceRule_2013090915 ";
			LogTools.getLogger(getClass()).debug("SQL --> [{}].", sql);
			pstmt = conn.prepareStatement(sql);
			return true;
		} catch (Exception e) {
			LogTools.getLogger(getClass())
					.error("When [{} - {} --> Exception ] ,  \n  MASSAGE : {} \n  CAUSE :{} \n  CLASS : {}",
							new Object[] { "select tbdistrict & tbtown",
									"initPreparedStatement()", e.getMessage(),
									e.getCause(), e.getClass() });
			LogTools.getLogger(getClass()).warn(
					"Init select tbdistrict & tbtown ps error!return false;");
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see z.z.w.db.upgrade.exec.CacheDBFactory#getCache()
	 */
	@Override
	protected boolean getCache() {

		try {

			rs = pstmt.executeQuery();

			if (!DataTools.isEmpty(rs)) {

				while (rs.next()) {
					// vcIp,vcUrl,vcsIp,vcsUrl
					ServerVo vo = new ServerVo();
					vo.setVcIp(rs.getString("vcIp"));
					vo.setVcsIp(rs.getString("vcsIp"));
					vo.setVcsUrl(rs.getString("vcsUrl"));
					vo.setVcUrl(rs.getString("vcUrl"));

					list.add(vo);
				}

				rsGetFlag = true;

				rs.close();
				rs = null;
			}

			LogTools.getLogger(getClass())
					.debug("Select tbServiceRule_2013090915 complete, dataList size :[{}].",
							list.size());
			return true;
		} catch (Exception e) {
			LogTools.getLogger(getClass())
					.error("When [{} - {} --> Exception ] ,  \n  MASSAGE : {} \n  CAUSE :{} \n  CLASS : {}",
							new Object[] { "select tbdistrict & tbtown",
									"getCache()", e.getMessage(), e.getCause(),
									e.getClass() });
			LogTools.getLogger(getClass()).warn(
					"Select tbServiceRule_2013090915 cache,return false!");
			return false;
		} finally {
			clearWork();
		}
	}

}
