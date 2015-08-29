/**
 * z.z.w.db.upgrade.exec.CacheDBFactoryTest.java
 */
package z.z.w.db.upgrade.exec;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import z.z.w.common.DateTools;
import z.z.w.db.upgrade.param.ConnParam;
import z.z.w.db.upgrade.param.MysqlParam;
import z.z.w.log.LogUtil;

/**
 * @author Wu Zhenzhen
 * @version 2013-8-19 下午04:46:31
 */
public class CacheDBFactoryTest extends CacheDBFactory {

	static {
		ConnParam connParam = new MysqlParam();
		// ConnParam connParam = new SybaseParam();

		/*********************************************************************/
		// TODO -- 2013-8-19 11:32:58 使用時修改數據庫連接信息
		// connParam.setUser("DBA");
		// connParam.setPassword("SQL");
		// connParam.setPort(2688);
		// connParam.setServer("localhost");
		// connParam.setDatabase("localhost");

		connParam.setUser("root");
		connParam.setPassword("sql");
		connParam.setPort(3306);
		connParam.setServer("localhost");
		connParam.setDatabase("fs");

		// connParam.setUser("root");
		// connParam.setPassword("^yhnbgt&u");
		// connParam.setPort(3306);
		// connParam.setServer("193.193.193.197");
		// connParam.setDatabase("product");
		/*********************************************************************/

		CacheDBFactoryTest.initDBConnectionPool(connParam);
	}

	@Before
	public void before() {
		// new Thread(new Runnable() {
		//
		// public void run() {
		// updateCache();
		// }
		// }).start();
	}

	/**
	 * Test method for
	 * {@link z.z.w.db.upgrade.exec.CacheDBFactory#updateCache()}.
	 */
	@Test
	public void testUpdateCache() {

		List<String> list = new ArrayList<String>();

		setValue(list);

		int len = list.size();
		for (int i = 0; i < len; i++) {

			// LogUtil.getLogger(getClass()).debug("{} --- {}.",
			// arr[i].hashCode(), arr[i]);

			long startTime = DateTools.getCurrentDateToLong();
			try {

				if (!setConnection())
					continue;

				// String sql =
				// "commit;insert into tbtown(intTownId,vcTown,intDistrictId ) values("
				// + arr[i].hashCode() + ",'" + arr[i] + "',1234557); ";
				String str = list.get(i);
				String sql = "commit;insert into tbtown(intTownId,intDistrictId,vcTown ) values("
						+ str.split(",", -1)[1].hashCode() + str + " ); ";

				LogUtil.getLogger(getClass()).debug("Sql : [{}].", sql);

				pstmt = conn.prepareStatement(sql);
				// pstmt.execute();

			} catch (SQLException e) {
				LogUtil.getLogger(getClass())
						.error("When [{} - {}] , error : \n MASSAGE : {} \n CAUSE :{} \n CLASS : {}\n",
								new Object[] { this.getClass().getName(),
										" testUpdateCache --> SQLException() ",
										e.getMessage(), e.getCause(),
										e.getClass() });
			} finally {
				clearWork();
				long useTime = DateTools.getCurrentDateToLong() - startTime;
				LogUtil.getLogger(getClass()).info(
						"Execute [[{}]-{}],use times :[{}]ms.",
						new Object[] { (i + 1), len, (useTime) });
			}

		}
	}

	/**
	 * <br>
	 * Created on: 2013-8-20 下午02:52:44
	 * 
	 * @param list
	 */
	private void setValue(List<String> list) {
		list.add("986857,'城区'");
		list.add("986857,'东方'");
		list.add("986857,'鸿业'");
		list.add("986857,'季华'");
		list.add("986857,'金沙'");
		list.add("986857,'澜石'");
		list.add("986857,'朗宝'");
		list.add("986857,'岭南'");
		list.add("986857,'南庄'");
		list.add("986857,'清水'");
		list.add("986857,'区季'");
		list.add("986857,'区石'");
		list.add("986857,'石湾'");
		list.add("986857,'文华'");
		list.add("986857,'新张'");
		list.add("986857,'张槎'");
		list.add("986857,'祖庙'");
		list.add("895892,'路应'");
		list.add("1254966,'富湾'");
		list.add("1254966,'更合'");
		list.add("1254966,'更楼'");
		list.add("1254966,'合水'");
		list.add("1254966,'河江'");
		list.add("1254966,'荷城'");
		list.add("1254966,'明城'");
		list.add("1254966,'人和'");
		list.add("1254966,'三洲'");
		list.add("1254966,'西安'");
		list.add("1254966,'新圩'");
		list.add("1254966,'杨和'");
		list.add("1254966,'杨梅'");
		list.add("689408,'大沥'");
		list.add("689408,'丹灶'");
		list.add("689408,'官窑'");
		list.add("689408,'广珠'");
		list.add("689408,'桂城'");
		list.add("689408,'和顺'");
		list.add("689408,'黄岐'");
		list.add("689408,'金沙'");
		list.add("689408,'九江'");
		list.add("689408,'里水'");
		list.add("689408,'罗村'");
		list.add("689408,'平洲'");
		list.add("689408,'沙头'");
		list.add("689408,'狮山'");
		list.add("689408,'松岗'");
		list.add("689408,'西樵'");
		list.add("689408,'小塘'");
		list.add("689408,'盐步'");
		list.add("646987,'白坭'");
		list.add("646987,'大塘'");
		list.add("646987,'范湖'");
		list.add("646987,'河口'");
		list.add("646987,'金本'");
		list.add("646987,'迳口'");
		list.add("646987,'乐平'");
		list.add("646987,'六和'");
		list.add("646987,'芦苞'");
		list.add("646987,'南边'");
		list.add("646987,'南山'");
		list.add("646987,'青岐'");
		list.add("646987,'区乐'");
		list.add("646987,'西南'");
		list.add("646987,'云东'");
		list.add("1234557,'北滘'");
		list.add("1234557,'陈村'");
		list.add("1234557,'大良'");
		list.add("1234557,'均安'");
		list.add("1234557,'乐从'");
		list.add("1234557,'勒流'");
		list.add("1234557,'龙江'");
		list.add("1234557,'伦教'");
		list.add("1234557,'容桂'");
		list.add("1234557,'杏坛'");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see z.z.w.db.upgrade.exec.CacheDBFactory#initPreparedStatement()
	 */
	@Override
	protected boolean initPreparedStatement() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see z.z.w.db.upgrade.exec.CacheDBFactory#getCache()
	 */
	@Override
	protected boolean getCache() {
		return false;
	}

}
