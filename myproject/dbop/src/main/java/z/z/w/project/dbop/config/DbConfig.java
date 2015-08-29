/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.dbop.config.DbConfig.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-8-21 上午12:39:51 
 *   LastChange: 2013-8-21 上午12:39:51 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.dbop.config;

import z.z.w.common.DESCodec;
import z.z.w.db.upgrade.exec.CacheDBFactory;
import z.z.w.env.Config;
import z.z.w.project.main.config.Global;

/**
 * z.z.w.project.dbop.config.DbConfig.java
 */
public class DbConfig extends Global {

	private static final Config dbConf = new Config(Global.DB_CFG_FILE);

	/**
	 * <br>
	 * Created on: 2013-8-21 上午12:39:51
	 */
	private DbConfig() {
	}

	/**
	 * <br>
	 * Created on: 2013-7-28 下午10:13:37
	 * 
	 * @return
	 */
	public static String getDBUser() {
		return getDBConfig().getString("user");
	}

	/**
	 * <br>
	 * Created on: 2013-7-28 下午10:14:01
	 * 
	 * @return
	 */
	private static Config getDBConfig() {
		dbConf.setRootKey("db");
		return dbConf;
	}

	/**
	 * <br>
	 * Created on: 2013-7-28 下午10:16:52
	 * 
	 * @return
	 */
	public static String getDBPassword() {
		String passwd = getDBConfig().getString("passwd");
		boolean descodec = getDBConfig().getBoolean("passwd.encode");
		if (!descodec)
			return passwd;
		else {
			return DESCodec.decrypt(passwd, CacheDBFactory.DB_CONN_POOL_NAME);
		}
	}

	/**
	 * <br>
	 * Created on: 2013-7-28 下午10:17:05
	 * 
	 * @return
	 */
	public static int getDBPort() {
		return getDBConfig().getInt("port");
	}

	/**
	 * <br>
	 * Created on: 2013-7-28 下午10:17:19
	 * 
	 * @return
	 */
	public static String getDBServer() {
		return getDBConfig().getString("server");
	}

	/**
	 * <br>
	 * Created on: 2013-7-28 下午10:17:38
	 * 
	 * @return
	 */
	public static String getDBDataBase() {
		return getDBConfig().getString("database");
	}

}
