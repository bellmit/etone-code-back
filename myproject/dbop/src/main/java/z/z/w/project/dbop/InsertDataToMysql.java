/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.dbop.UpdateDataToMysql.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-8-20 下午09:40:54 
 *   LastChange: 2013-8-20 下午09:40:54 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.dbop;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.math.NumberUtils;

import z.z.w.common.DateTools;
import z.z.w.db.upgrade.exec.CacheDBFactory;
import z.z.w.db.upgrade.param.ConnParam;
import z.z.w.db.upgrade.param.MysqlParam;
import z.z.w.log.LogUtil;
import z.z.w.project.dbop.config.DbConfig;
import z.z.w.project.util.common.DataTools;
import z.z.w.project.util.common.LogTools;

/**
 * z.z.w.project.dbop.UpdateDataToMysql.java
 */
public class InsertDataToMysql extends CacheDBFactory implements Runnable {

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
	PreparedStatement pstmt2 = null;

	/**
	 * <br>
	 * Created on: 2013-8-20 下午09:40:54
	 */
	public InsertDataToMysql() {
		super();
		dataList = new ArrayList<String>();
		setDataList();

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

			// if (DataTools.isEmpty(dataList2)) {
			// LogTools.getLogger(getClass()).warn(
			// "File content is null,return.");
			// return;
			// }

			// updateDBData();
			// updateDBData2();
			// updateDBData3();
			updateDBData4();

		} catch (Exception e) {
			LogTools.getLogger(getClass())
					.error("When [{} - {} --> Exception ] ,  \n  MASSAGE : {} \n  CAUSE :{} \n  CLASS : {}",
							new Object[] { "ServiceRuleReader", "run()",
									e.getMessage(), e.getCause(), e.getClass() });
		} finally {
			dataList.clear();
			dataList = null;
		}
	}

	/**
	 * <br>
	 * Created on: 2013-8-21 上午03:43:46
	 */
	private void updateDBData4() {
		try {
			long startTime = DateTools.getCurrentDateToLong();
			LogTools.getLogger(getClass()).info(
					"Update [{}] cache map,use time : [{}]ms.",
					getClass().getSimpleName(),
					((DateTools.getCurrentDateToLong() - startTime)));

			conn.setAutoCommit(false);

			int len = dataList.size();
			for (int i = 0; i < len; i++) {
				String str = dataList.get(i);

				/*
				 * // "INSERT INTO
				 * `epmp_tbservicerule`(`intRuleId`,`intSubServId
				 * `,`vcSubServ`,`vcIp`,`vcUrl`) VALUE (?,?,?,?,?); ;
				 */
				String[] strArr = str.split(",", -1);

				pstmt.setInt(1, NumberUtils.toInt(strArr[0]));
				pstmt.setInt(2, NumberUtils.toInt(strArr[1]));
				pstmt.setString(3, (strArr[2]));
				pstmt.setString(4, (strArr[3]));
				pstmt.setString(5, strArr[4]);

				pstmt.addBatch();

			}

			pstmt.executeBatch();
			conn.commit();
		} catch (Exception e) {
			LogTools.getLogger(getClass())
					.error("When [{} - {} --> Exception ] ,  \n  MASSAGE : {} \n  CAUSE :{} \n  CLASS : {}",
							new Object[] { "ServiceRuleReader",
									"updateDBData()", e.getMessage(),
									e.getCause(), e.getClass() });
		}
	}

	/**
	 * <br>
	 * Created on: 2013-8-20 下午11:01:26
	 */
	private void setDataList() {
		BufferedReader input = null;

		try {

			File file = new File("etc/epmp_tbServiceRule.csv");
			input = new BufferedReader(new FileReader(file));
			String text = "";
			while (!DataTools.isEmpty((text = input.readLine()))) {
				dataList.add(text);
			}

			input.close();
			input = null;
		} catch (Exception e) {
			LogUtil.getLogger(getClass())
					.error("When [{} - {}] ,  \n  MASSAGE : {} \n  CAUSE :{} \n  CLASS : {}",
							new Object[] { getClass().getName(),
									"readFileContentToCache --> Exception()",
									e.getMessage(), e.getCause(), e.getClass() });
		} finally {
			if (!DataTools.isEmpty(input)) {
				try {
					input.close();
					input = null;
				} catch (IOException e) {
				} finally {
					input = null;
				}

			}
		}
	}

	/**
	 * <br>
	 * Created on: 2013-8-21 上午02:21:26
	 */
	private void updateDBData3() {
		try {
			conn.setAutoCommit(false);

			int len = dataList.size();
			for (int i = 0; i < len; i++) {
				String str = dataList.get(i);

				/*
				 * sb.append(rs.getInt("tt.intTownId")).append(",");
				 * sb.append(rs.getInt("td.intDistrictId")).append(",");
				 * sb.append(rs.getInt("td.intCityId")).append(",");
				 * sb.append(rs.getString("vcDistrict"));
				 * sb.append(rs.getString("vcTown"));
				 */
				String[] strArr = str.split(",", -1);

				String intTownId = strArr[0];
				String intDistrictId = strArr[1];
				String intCityId = strArr[2];
				String vcDistrictAndVcTown = strArr[3];

				// UPDATE tbcgiinfo intTownId = ? ,intDistrictId = ?,intCityId =
				// ? where SUBSTRING(vcCellChName,1,4) = ?;

				pstmt.setInt(1, NumberUtils.toInt(intTownId));
				pstmt.setInt(2, NumberUtils.toInt(intDistrictId));
				pstmt.setInt(3, NumberUtils.toInt(intCityId));
				pstmt.setString(4, vcDistrictAndVcTown);

				pstmt.addBatch();

			}

			pstmt.executeBatch();
			conn.commit();
		} catch (Exception e) {
			LogTools.getLogger(getClass())
					.error("When [{} - {} --> Exception ] ,  \n  MASSAGE : {} \n  CAUSE :{} \n  CLASS : {}",
							new Object[] { "ServiceRuleReader",
									"updateDBData()", e.getMessage(),
									e.getCause(), e.getClass() });
		}
	}

	/**
	 * <br>
	 * Created on: 2013-8-20 下午09:52:49
	 */
	private void updateDBData() {

		try {
			conn.setAutoCommit(false);

			int len = dataList.size();
			for (int i = 0; i < len; i++) {

				String str = dataList.get(i);
				// dataList.add("河宕村,9600,10868");

				String[] strArr = str.split(",", -1);
				String intLac = strArr[1];
				String intCi = strArr[2];
				String vcMicroArea = strArr[0];
				int intMicroAreaId = vcMicroArea.hashCode();
				intMicroAreaId = ((intMicroAreaId < 0) ? -intMicroAreaId
						: intMicroAreaId);

				// bmicroareamapping(intMicroAreaId,intLac,intCi )

				pstmt.setInt(1, intMicroAreaId);
				pstmt.setInt(2, NumberUtils.toInt(intLac));
				pstmt.setInt(3, NumberUtils.toInt(intCi));

				// pstmt.setInt(1, intTownId);
				// pstmt.setInt(2, intDistrictId);
				// pstmt.setString(3, vcTown);

				pstmt.addBatch();

			}

			pstmt.executeBatch();
			conn.commit();
		} catch (Exception e) {
			LogTools.getLogger(getClass())
					.error("When [{} - {} --> Exception ] ,  \n  MASSAGE : {} \n  CAUSE :{} \n  CLASS : {}",
							new Object[] { "ServiceRuleReader",
									"updateDBData()", e.getMessage(),
									e.getCause(), e.getClass() });
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see z.z.w.db.upgrade.exec.CacheDBFactory#clearConnection()
	 */
	@Override
	protected void clearConnection() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see z.z.w.db.upgrade.exec.CacheDBFactory#initPreparedStatement()
	 */
	@Override
	protected boolean initPreparedStatement() {

		// if (!initPstmtUpdate())
		// return false;
		//
		// if (!initPstmtSelet())
		// return false;

		return initPstmtInsert();
	}

	/**
	 * <br>
	 * Created on: 2013-8-21 上午02:20:07
	 * 
	 * @return
	 */
	private boolean initPstmtInsert() {
		try {
			String sql = " INSERT INTO epmp_tbservicerule(intRuleId,intSubServId,vcSubServ,vcIp,vcUrl) VALUE (?,?,?,?,?);";
			LogTools.getLogger(getClass()).debug("SQL --> [{}].", sql);
			pstmt = conn.prepareStatement(sql);
			return true;
		} catch (Exception e) {
			LogTools.getLogger(getClass())
					.error("When [{} - {} --> Exception ] ,  \n  MASSAGE : {} \n  CAUSE :{} \n  CLASS : {}",
							new Object[] { "insert into tbtown",
									"initPreparedStatement()", e.getMessage(),
									e.getCause(), e.getClass() });
			LogTools.getLogger(getClass()).warn(
					"Init insert into tbtown ps error!return false;");
			return false;
		}
	}

	/**
	 * <br>
	 * Created on: 2013-8-21 上午02:19:05
	 * 
	 * @return
	 */
	private boolean initPstmtSelet() {
		try {
			String sql = " select intLac,intCI,vcCellEnName from tbcgiinfo group by intLac,intCI,vcCellEnName";
			LogTools.getLogger(getClass()).debug("SQL --> [{}].", sql);
			pstmt2 = conn.prepareStatement(sql);
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

	/**
	 * <br>
	 * Created on: 2013-8-21 上午02:12:04
	 * 
	 * @return
	 */
	private boolean initPstmtUpdate() {
		try {

			String sql = " UPDATE tbcgiinfo set intTownId = ? ,intDistrictId = ?,intCityId  = ? where SUBSTRING(vcCellChName,1,4) = ?;";
			LogTools.getLogger(getClass()).debug("SQL --> [{}].", sql);
			pstmt = conn.prepareStatement(sql);
			return true;
		} catch (Exception e) {
			LogTools.getLogger(getClass())
					.error("When [{} - {} --> Exception ] ,  \n  MASSAGE : {} \n  CAUSE :{} \n  CLASS : {}",
							new Object[] { "insert into tbtown",
									"initPreparedStatement()", e.getMessage(),
									e.getCause(), e.getClass() });
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

			// rs = pstmt2.executeQuery();
			//
			// if (!DataTools.isEmpty(rs)) {
			//
			// while (rs.next()) {
			// // select intLac,intCI,vcCellEnName from tbcgiinfo
			// StringBuffer sb = new StringBuffer(1000);
			// sb.append(rs.getInt("intLac")).append(",");
			// sb.append(rs.getInt("intCI")).append(",");
			// sb.append(rs.getString("vcCellEnName").trim());
			//
			// dataList2.add(sb.toString());
			// }
			//
			rsGetFlag = true;
			//
			// rs.close();
			// rs = null;
			// }
			//
			// LogTools.getLogger(getClass())
			// .debug("Select tbdistrict & tbtown update complete, dataList size :[{}].",
			// dataList.size());
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see z.z.w.db.upgrade.exec.CacheDBFactory#clearPreparedStatement()
	 */
	@Override
	protected void clearPreparedStatement() {
	}

}
