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

import z.z.w.common.DataTools;
import z.z.w.db.upgrade.exec.CacheDBFactory;
import z.z.w.db.upgrade.param.ConnParam;
import z.z.w.db.upgrade.param.MysqlParam;
import z.z.w.project.dbop.config.DbConfig;
import z.z.w.project.util.common.LogTools;

/**
 * z.z.w.project.dbop.UpdateCgiDataToMysql.java
 */
public class UpdateCgiDataToMysql extends CacheDBFactory implements Runnable {
	static {
		ConnParam connParam = new MysqlParam();

		/*********************************************************************/
		connParam.setUser(DbConfig.getDBUser());
		connParam.setPassword(DbConfig.getDBPassword());
		connParam.setPort(DbConfig.getDBPort());
		connParam.setServer(DbConfig.getDBServer());
		connParam.setDatabase(DbConfig.getDBDataBase());
		/*********************************************************************/

		initDBConnectionPool(connParam);
	}
	private List<String> dataList = null;

	/**
	 * <br>
	 * Created on: 2013-8-21 上午01:28:01
	 */
	public UpdateCgiDataToMysql() {
		super();
		dataList = new ArrayList<String>();

		new Thread(new Runnable() {

			public void run() {
				updateCache();
			}
		}).start();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	public void run() {

		try {
			waitCacheUpdated();

			print();

		} catch (Exception e) {
			LogTools.getLogger(getClass())
					.error("When [{} - {} --> Exception ] ,  \n  MASSAGE : {} \n  CAUSE :{} \n  CLASS : {}",
							new Object[] { "UpdateCgiDataToMysql", "run()",
									e.getMessage(), e.getCause(), e.getClass() });
		} finally {
			dataList.clear();
			dataList = null;
		}
	}

	/**
	 * <br>
	 * Created on: 2013-8-21 上午01:34:33
	 */
	private void print() {

		Iterator<String> it = dataList.iterator();
		while (it.hasNext()) {
			String str = it.next();
			System.out.println(str);
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
			String sql = " select tt.intTownId,td.intDistrictId,td.intCityId,vcTown,vcDistrict from tbdistrict td inner join tbtown tt on td.intDistrictId = tt. intDistrictId ";
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
					// tt.intTownId,td.intDistrictId,td.intCityId,vcTown,vcDistrict
					StringBuffer sb = new StringBuffer(1000);
					sb.append(rs.getInt("tt.intTownId")).append(",");
					sb.append(rs.getInt("td.intDistrictId")).append(",");
					sb.append(rs.getInt("td.intCityId")).append(",");
					sb.append(rs.getString("vcDistrict"));
					sb.append(rs.getString("vcTown"));

					dataList.add(sb.toString());
				}

				rsGetFlag = true;

				rs.close();
				rs = null;
			}

			LogTools.getLogger(getClass())
					.debug("Select tbdistrict & tbtown update complete, dataList size :[{}].",
							dataList.size());
			return true;
		} catch (Exception e) {
			LogTools.getLogger(getClass())
					.error("When [{} - {} --> Exception ] ,  \n  MASSAGE : {} \n  CAUSE :{} \n  CLASS : {}",
							new Object[] { "select tbdistrict & tbtown",
									"getCache()", e.getMessage(), e.getCause(),
									e.getClass() });
			LogTools.getLogger(getClass()).warn(
					"Select tbdistrict & tbtown cache,return false!");
			return false;
		} finally {
			clearWork();
		}
	}

}
