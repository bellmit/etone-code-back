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

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.math.NumberUtils;

import z.z.w.common.DataTools;
import z.z.w.common.DateTools;
import z.z.w.db.upgrade.exec.CacheDBFactory;
import z.z.w.db.upgrade.param.ConnParam;
import z.z.w.db.upgrade.param.MysqlParam;
import z.z.w.project.dbop.config.DbConfig;
import z.z.w.project.util.common.LogTools;

/**
 * z.z.w.project.dbop.UpdateDataToMysql.java
 */
public class UpdateDataToMysql extends CacheDBFactory implements Runnable {

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
	private List<String> dataList2 = null;
	private List<String> dataList3 = null;
	PreparedStatement pstmt2 = null;

	/**
	 * <br>
	 * Created on: 2013-8-20 下午09:40:54
	 */
	public UpdateDataToMysql() {
		super();
		dataList = new ArrayList<String>();
		dataList2 = new ArrayList<String>();
		dataList3 = new ArrayList<String>();
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

			if (DataTools.isEmpty(dataList2)) {
				LogTools.getLogger(getClass()).warn(
						"File content is null,return.");
				return;
			}

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
	 * Created on: 2013-8-21 上午02:01:22
	 */
	// private void updateDBData2() {
	// try {
	// conn.setAutoCommit(false);
	//
	// int len = dataList.size();
	// for (int i = 0; i < len; i++) {
	// String str = dataList.get(i);
	// String[] strArr = str.split(",", -1);
	//
	// String vcCellEnName = strArr[0];
	// String vcCgi = strArr[1];
	// String intLac = strArr[2];
	// String intCI = strArr[3];
	// String vcCellChName = strArr[4];
	// int intCgiInfoId = vcCellChName.hashCode();
	// intCgiInfoId = ((intCgiInfoId < 0) ? -intCgiInfoId
	// : intCgiInfoId);
	//
	// // dataList.add("FV1WWB7,460-00-10289-11047,10289,11047,顺德杏坛综治信访维稳中心M7");
	// // intCgiInfoId,vcCellEnName,vcCgi,intLac,intCI,vcCellChName
	// pstmt.setInt(1, intCgiInfoId);
	// pstmt.setString(2, vcCellEnName);
	// pstmt.setString(3, vcCgi);
	// pstmt.setString(4, intLac);
	// pstmt.setString(5, intCI);
	// pstmt.setString(6, vcCellChName);
	//
	// pstmt.addBatch();
	//
	// }
	//
	// pstmt.executeBatch();
	// conn.commit();
	// } catch (Exception e) {
	// LogTools.getLogger(getClass())
	// .error("When [{} - {} --> Exception ] ,  \n  MASSAGE : {} \n  CAUSE :{} \n  CLASS : {}",
	// new Object[] { "ServiceRuleReader",
	// "updateDBData()", e.getMessage(),
	// e.getCause(), e.getClass() });
	// }
	// }

	/**
	 * <br>
	 * Created on: 2013-8-21 上午03:43:46
	 */
	private void updateDBData4() {
		try {
			long startTime = DateTools.getCurrentDateToLong();
			setNewValue();
			LogTools.getLogger(getClass()).info(
					"Update [{}] cache map,use time : [{}]ms.",
					getClass().getSimpleName(),
					((DateTools.getCurrentDateToLong() - startTime)));

			conn.setAutoCommit(false);

			int len = dataList3.size();
			for (int i = 0; i < len; i++) {
				String str = dataList3.get(i);

				/*
				 * //
				 * " insert into tbmicroareamapping(intMicroAreaId,intLac,intCi ) value(?,?,?);"
				 * ;
				 */
				String[] strArr = str.split(",", -1);

				String intLac = strArr[1];
				String intCi = strArr[2];
				String intMicroAreaId = strArr[0];

				pstmt.setInt(1, NumberUtils.toInt(intMicroAreaId));
				pstmt.setInt(2, NumberUtils.toInt(intLac));
				pstmt.setInt(3, NumberUtils.toInt(intCi));

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
	 * Created on: 2013-8-21 上午03:47:58
	 */
	private void setNewValue() {
		int len = dataList.size();
		int len2 = dataList2.size();
		for (int i = 0; i < len; i++) {
			try {
				String str = dataList.get(i);

				/*
				 * dataList.add("季华园地铁站,FN8JHZ1");
				 */
				String[] strArr = str.split(",", -1);

				String vcMicroArea = strArr[0];
				String vcCellEnName = strArr[1].trim();

				int intMicroAreaId = vcMicroArea.hashCode();
				intMicroAreaId = ((intMicroAreaId < 0) ? -intMicroAreaId
						: intMicroAreaId);

				for (int j = 0; j < len2; j++) {
					try {
						String str2 = dataList2.get(j);

						String[] strArr2 = str2.split(",", -1);

						String vcCellEnName2 = strArr2[2];

						if (!vcCellEnName.equalsIgnoreCase(vcCellEnName2)) {
							LogTools.getLogger(getClass()).debug(
									"UNMATCH[{}-{}=={}--{}]",
									new Object[] { vcCellEnName, vcCellEnName2,
											i, j });
							continue;
						}

						String intLac = strArr2[0];
						String intCI = strArr2[1];
						// String sql =
						// " insert into tbmicroareamapping(intMicroAreaId,intLac,intCi ) value(?,?,?);";
						StringBuffer sb = new StringBuffer();
						sb.append(intMicroAreaId).append(",");
						sb.append(intLac).append(",");
						sb.append(intCI);

						LogTools.getLogger(getClass()).debug(
								"MATCH : {} --{}=={}",
								new Object[] { sb.toString(), i, j });
						dataList3.add(sb.toString());

						break;
					} catch (Exception e) {
						LogTools.getLogger(getClass())
								.error("When [{} - {} --> Exception ] ,  \n  MASSAGE : {} \n  CAUSE :{} \n  CLASS : {}",
										new Object[] {
												"ServiceRuleReader -- inner",
												"setNewValue()",
												e.getMessage(), e.getCause(),
												e.getClass() });
					}

				}
			} catch (Exception e) {
				LogTools.getLogger(getClass())
						.error("When [{} - {} --> Exception ] ,  \n  MASSAGE : {} \n  CAUSE :{} \n  CLASS : {}",
								new Object[] { "ServiceRuleReader -- outer",
										"setNewValue()", e.getMessage(),
										e.getCause(), e.getClass() });
			}

		}
	}

	/**
	 * <br>
	 * Created on: 2013-8-20 下午11:01:26
	 */
	private void setDataList() {
		dataList.add("河宕村,FN8HYZ8");
		dataList.add("河宕村,FN8HYZ9");
		dataList.add("河宕村,FN8FSS8");
		dataList.add("河宕村,FN8FSS9");
		dataList.add("鄱村,FN1SWC7");
		dataList.add("鄱村,FN8ZJY8");
		dataList.add("里水大梅村(大麦村),FN8THY9");
		dataList.add("黎涌下村,FN7LCG3");
		dataList.add("黎冲东街村,FN7LCG7");
		dataList.add("深村,FN8GMX7");
		dataList.add("深村,FN8TXC8");
		dataList.add("深村永红村,FN7SCN1");
		dataList.add("深村永红村,FN7SCN7");
		dataList.add("深村永红村,FN7SCN8");
		dataList.add("北西村,FN8TXH7");
		dataList.add("北西村,FN8HJD9");
		dataList.add("北西村,FN7SCN3");
		dataList.add("北西村,FN7SCN9");
		dataList.add("绿景村,FN8JLL3");
		dataList.add("华远村,FN8GMX3");
		dataList.add("华远村,FN8GMX9");
		dataList.add("华远村,FN8FSZ1");
		dataList.add("华远村,FN8FSZ7");
		dataList.add("新虹村,FN8QXJ9");
		dataList.add("永心村,FN8DFL7");
		dataList.add("简村（成凤里村）,FF1JXL8");
		dataList.add("下朗英三村,FN6QYG8");
		dataList.add("江边村,FN6DLA7");
		dataList.add("江边村,FN6JBB8");
		dataList.add("江边村,FN6JBB9");
		dataList.add("江边村,FN6JBB7");
		dataList.add("里水村、大麦村（里水村）,FN8FCC1");
		dataList.add("里水村、大麦村（里水村）,FN8FCC7");
		dataList.add("里水村、大麦村（里水村）,FN8FSZ3");
		dataList.add("里水村、大麦村（里水村）,FN8FSZ9");
		dataList.add("双冲村,FR3GTW2");
		dataList.add("双冲村,FR3GMF2");
		dataList.add("王家,FR3WHB9");
		dataList.add("何族,FR3HZU1");
		dataList.add("棠吉,FR3TMC1");
		dataList.add("勤学,FR3QXC1");
		dataList.add("勤学,FR3QXC3");
		dataList.add("海口,FR3MZH2");
		dataList.add("棠美,FR3WHB8");
		dataList.add("棠美,FR3TMC2");
		dataList.add("官当,FR3GDN1");
		dataList.add("官当,FR3HZU2");
		dataList.add("罗俊,FR3HLX3");
		dataList.add("罗俊,FR3HLX2");
		dataList.add("西头,FR3LDG1");
		dataList.add("张学,FR3HLY1");
		dataList.add("张学,FR3LDG2");
		dataList.add("乐庆村,FG2GSC1");
		dataList.add("东约村,FG2LGX8");
		dataList.add("南约村,FG2NYE2");
		dataList.add("北约,FG2JTX8");
		dataList.add("北约,FG2NYE3");
		dataList.add("北约,FG2LGD2");
		dataList.add("北约,FG2LGD1");
		dataList.add("西约,FG2LGX9");
		dataList.add("西约,FG2GYY1");
		dataList.add("西约,FG2GYY7");
		dataList.add("谭边村,FF2SDM9");
		dataList.add("谭边村,FF2XYC2");
		dataList.add("谭边村,FF2DTT3");
		dataList.add("安定新村,FF2YHG8");
		dataList.add("安定新村,FF2LXI7");
		dataList.add("安定新村,FO3QFL9");
		dataList.add("夏教村,FO3XJO7");
		dataList.add("夏教村,FO3XJO3");
		dataList.add("夏教村,FO3XJO9");
		dataList.add("良溪村,FF2LXI2");
		dataList.add("良溪村,FO3QFL8");
		dataList.add("良溪村,FO3XJO1");
		dataList.add("聚龙村,FO3XBD3");
		dataList.add("聚龙村,FO3XBD9");
		dataList.add("江左村,FG1PZU3");
		dataList.add("江左村,FG1PZU9");
		dataList.add("平西,FG1PNN3");
		dataList.add("平西,FG1PNN9");
		dataList.add("平西,FG1YYW8");
		dataList.add("南平村,FG1PCC8");
		dataList.add("南平村,FG1PXC3");
		dataList.add("南平村,FG1PXC9");
		dataList.add("南平村,FG1PCW3");
		dataList.add("南平村,FG1PCW9");
		dataList.add("平北六村,FG1PSC9");
		dataList.add("平北六村,FG1PCC1");
		dataList.add("平北六村,FG1PCC7");
		dataList.add("平北六村,FG1LSQ1");
		dataList.add("平北六村,FG1LSQ7");
		dataList.add("平东,FG1PZM7");
		dataList.add("平南,FG1PNN7");
		dataList.add("平南,FG1PNN1");
		dataList.add("平南,FG1YYW7");
		dataList.add("平南,FG1YQ29");
		dataList.add("上海村,FG1PXI8");
		dataList.add("上海村,FG1PXI3");
		dataList.add("教表,FJ1NFC7");
		dataList.add("教表,FJ1QJB4");
		dataList.add("教表,FJ1QJB8");
		dataList.add("教表,FJ1QJB2");
		dataList.add("大同新村,FJ1HLL1");
		dataList.add("大同新村,FJ1HLL7");
		dataList.add("大同新村,FJ1WUJ9");
		dataList.add("大同新村,FJ1BCH9");
		dataList.add("大同新村,FJ1HCQ8");
		dataList.add("南井康乐村,FJ1YSC9");
		dataList.add("南井康乐村,FJ1HDG8");
		dataList.add("南井康乐村,FJ1HDG2");
		dataList.add("南井康乐村,FJ1YBY2");
		dataList.add("南井康乐村,FJ1YBY8");
		dataList.add("虎榜村,FJ1HBZ2");
		dataList.add("虎榜村,FJ1HBZ9");
		dataList.add("虎榜村,FJ1HBZ3");
		dataList.add("虎榜村,FJ1HBZ7");
		dataList.add("虎榜村,FJ1HBZ1");
		dataList.add("新苑村,FJ1YBY9");
		dataList.add("新苑村,FJ1YBG1");
		dataList.add("聚龙村,FJ1YJL3");
		dataList.add("聚龙村,FJ1YJL8");
		dataList.add("聚龙村,FJ1YSC1");
		dataList.add("聚龙村,FJ1YSC7");
		dataList.add("永平村,FJ1YXL8");
		dataList.add("永平村,FO3YJC8");
		dataList.add("回龙村,FO3YHL3");
		dataList.add("回龙村,FO3YHL2");
		dataList.add("回龙村,FO3YHL1");
		dataList.add("联安,FO3LAG7");
		dataList.add("联安,FO3LAG9");
		dataList.add("联安,FO3YLA1");
		dataList.add("联安,FO3YLA7");
		dataList.add("横江,FJ1HLL2");
		dataList.add("横江,FJ1HLL8");
		dataList.add("横江,FJ1YHJ7");
		dataList.add("横江,FJ1YHJ1");
		dataList.add("平地,FK1PDZ2");
		dataList.add("平地,FK1PDZ8");
		dataList.add("河西,FO3HXH1");
		dataList.add("河西,FO3HXH7");
		dataList.add("河西,FO3BXG3");
		dataList.add("河西,FO3BXG9");
		dataList.add("湾溪,FO3WXI1");
		dataList.add("湾溪,FO3WXI3");
		dataList.add("湾溪,FO3WXI9");
		dataList.add("湾溪,FO3WXI7");
		dataList.add("上白坭下白坭,FJ1SBN3");
		dataList.add("上白坭下白坭,FJ1SBN4");
		dataList.add("上白坭下白坭,FJ1FZC1");
		dataList.add("上白坭下白坭,FJ1FZC7");
		dataList.add("凤鸣,FG1NSL1");
		dataList.add("凤鸣,FG1NSL7");
		dataList.add("凤鸣,FG1PZJ2");
		dataList.add("凤鸣,FG1PZJ8");
		dataList.add("深涌,FG1BZC2");
		dataList.add("深涌,FG1BZC8");
		dataList.add("五斗村,FG1YQG2");
		dataList.add("五斗村,FG1YQG8");
		dataList.add("夏南二村,FG1XNZ3");
		dataList.add("夏南二村,FG1XNZ8");
		dataList.add("夏南二村,FG1XNZ2");
		dataList.add("夏南二村,FG1XNZ9");
		dataList.add("夏南一村,FO3XJO2");
		dataList.add("夏南一村,FO3XJO8");
		dataList.add("九潭村,FO8JTQ8");
		dataList.add("沥西村,FO9XPC7");
		dataList.add("颜峰村,FO4YFG1");
		dataList.add("颜峰村,FO4YFG7");
		dataList.add("颜峰村,FO4DQC7");
		dataList.add("颜峰村,FO4QSN2");
		dataList.add("颜峰村,FO4QSN8");
		dataList.add("联窖村,FJ2FYC8");
		dataList.add("联星村,FN6LXS2");
		dataList.add("联星村,FN6LXS1");
		dataList.add("大塱新村,FO8XLA2");
		dataList.add("大塱新村,FO8LHE9");
		dataList.add("大塱新村,FO8LHQ7");
		dataList.add("大塱新村,FO8LHQ8");
		dataList.add("北房村,FN6LTE2");
		dataList.add("北房村,FN6LTE8");
		dataList.add("北房村,FN6LXS3");
		dataList.add("下柏村,FO8LXP3");
		dataList.add("下柏村,FO8LXP9");
		dataList.add("上柏村,FO8YHU3");
		dataList.add("上柏村,FO8YHU9");
		dataList.add("李边村,FO4SCQ3");
		dataList.add("李边村,FO4SCQ9");
		dataList.add("李边村,FO4SOG2");
		dataList.add("李边村,FO4SOG8");
		dataList.add("塘涌村,FO4SNQ9");
		dataList.add("塘涌村,FO4SCQ1");
		dataList.add("塘涌村,FO4SCQ7");
		dataList.add("联表村,FO4SLB2");
		dataList.add("联表村,FO4SLB8");
		dataList.add("联表村,FO4SOG7");
		dataList.add("联表村,FO4SOG1");
		dataList.add("龙头村,FO2SLT1");
		dataList.add("龙头村,FO2SLT7");
		dataList.add("新兴村,FK1LZF2");
		dataList.add("杨堂新村,FF1GYN1");
		dataList.add("杨堂新村,FG2LJD9");
		dataList.add("杨堂新村,FF1GYN7");
		dataList.add("鲤鱼沙新村,FG2GSC9");
		dataList.add("邓岗村,FP7XST3");
		dataList.add("邓岗村,FP7XST2");
		dataList.add("邓岗村,FP7XST1");
		dataList.add("伏户村,FP7FUH1");
		dataList.add("高丰村,FP7GFG2");
		dataList.add("高丰村,FP7GFG8");
		dataList.add("高丰村,FP7GFG7");
		dataList.add("高丰村,FP7GFG1");
		dataList.add("鲁村,FP7XGC9");
		dataList.add("鲁村,FP7XGC8");
		dataList.add("鲁村,FP7XGC7");
		dataList.add("大良大围村,FC1XLY3");
		dataList.add("大良大围村,FC1XLY2");
		dataList.add("大良大围村,FC1SYX2");
		dataList.add("大良大围村,FC1SYX1");
		dataList.add("大良五沙村,FM3WSQ2");
		dataList.add("大良五沙村,FM3WSC7");
		dataList.add("大良五沙村,FM3WSC9");
		dataList.add("大良五沙村,FM3WSC8");
		dataList.add("大良云路村,FC1HGE8");
		dataList.add("大良云路村,FC1HAL2");
		dataList.add("大良云路村,FC1YSL2");
		dataList.add("大良云路村,FC1YSL3");

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
		if (!initPstmtSelet())
			return false;

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
			String sql = " insert into tbmicroareamapping(intMicroAreaId,intLac,intCi ) value(?,?,?);";
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

			rs = pstmt2.executeQuery();

			if (!DataTools.isEmpty(rs)) {

				while (rs.next()) {
					// select intLac,intCI,vcCellEnName from tbcgiinfo
					StringBuffer sb = new StringBuffer(1000);
					sb.append(rs.getInt("intLac")).append(",");
					sb.append(rs.getInt("intCI")).append(",");
					sb.append(rs.getString("vcCellEnName").trim());

					dataList2.add(sb.toString());
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see z.z.w.db.upgrade.exec.CacheDBFactory#clearPreparedStatement()
	 */
	@Override
	protected void clearPreparedStatement() {
	}

}
