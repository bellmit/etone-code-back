/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.test.cache.BaseMsisdnInfoCache.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-10-2 下午05:44:15 
 *   LastChange: 2013-10-2 下午05:44:15 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.test.cache;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import z.z.w.db.upgrade.exec.CacheDBFactory;
import z.z.w.db.upgrade.param.ConnParam;
import z.z.w.db.upgrade.param.SybaseParam;
import z.z.w.log.LogUtil;
import z.z.w.project.test.cache.vo.BasePackagesVo;
import z.z.w.project.test.cache.vo.MsisdnInfo;
import z.z.w.project.test.cache.vo.SuperpositionPackagesVo;
import z.z.w.project.test.config.SRConfig;
import z.z.w.project.util.common.DataTools;
import z.z.w.project.util.common.DateTools;
import z.z.w.project.util.common.LogTools;
import z.z.w.project.util.common.ThreadTools;
import z.z.w.project.util.lru.LRUMapCache;

/**
 * z.z.w.project.test.cache.BaseMsisdnInfoCache.java
 */
public class BaseMsisdnInfoCache extends CacheDBFactory {

	private static BaseMsisdnInfoCache instance = null;
	private static final byte[] LOCK = new byte[0];
	private static final byte[] BASE_LOCK = new byte[0];
	private static final byte[] SUPER_LOCK = new byte[0];
	private static final int CACHE_SIZE = 800000;

	private static final LRUMapCache<String, List<MsisdnInfo>> lruMapCache = new LRUMapCache<String, List<MsisdnInfo>>(
			CACHE_SIZE);

	private static final List<BasePackagesVo> basePackagesList = new ArrayList<BasePackagesVo>();
	private static final List<SuperpositionPackagesVo> superpositionPackagesList = new ArrayList<SuperpositionPackagesVo>();
	private static final Map<String, Float> tmpMap = new HashMap<String, Float>();

	static {

		ConnParam connParam = new SybaseParam();

		/*********************************************************************/
		connParam.setUser(SRConfig.getDBUser());
		connParam.setPassword(SRConfig.getDBPassword());
		connParam.setPort(SRConfig.getDBPort());
		connParam.setServer(SRConfig.getDBServer());
		connParam.setDatabase(SRConfig.getDBDataBase());
		/*********************************************************************/

		initDBConnectionPool(connParam);
	}

	/**
	 * <br>
	 * Created on: 2013-10-2 下午05:45:14
	 * 
	 * @return the instance
	 */
	public static BaseMsisdnInfoCache getInstance() {

		synchronized (LOCK) {
			if (DataTools.isEmpty(instance))
				instance = new BaseMsisdnInfoCache();
			return instance;
		}
	}

	/**
	 * <br>
	 * Created on: 2013-10-2 下午05:44:15
	 */
	private BaseMsisdnInfoCache() {
		super();
		initInfos();
	}

	/**
	 * <br>
	 * Created on: 2013-10-3 下午01:34:57
	 */
	private void initInfos() {
		updateMsisdnInfo();
		getBasePackagesInfo();
	}

	/**
	 * <br>
	 * Created on: 2013-10-3 下午01:34:20
	 */
	private void getBasePackagesInfo() {
		// Thread thread = new Thread(new Runnable() {
		//
		// public void run() {
		// while (true) {
		long startTime = DateTools.getCurrentDateToLong();
		try {

			// "//subscription/packages"
			List<BasePackagesVo> bList = SRConfig.getBasePackagesVoList();
			updateBasePackagesList(bList);

			// "//subscription/superpositionPackages"
			List<SuperpositionPackagesVo> sList = SRConfig
					.getSuperpositionPackagesVoList();
			updateSuperpositionPackagesList(sList);

		} catch (Exception e) {
			LogTools.getLogger(getClass())
					.error("When [{} - {}] ,  \n  MASSAGE : {} \n  CAUSE :{} \n  CLASS : {}",
							new Object[] { this.getClass().getName(),
									"init packages infos --> Exception() ",
									e.getMessage(), e.getCause(), e.getClass() });

		} finally {
			LogTools.getLogger(getClass()).info(
					"Init packages infos,use time : [{}]ms.",
					((DateTools.getCurrentDateToLong() - startTime)));
			// ThreadTools.sleepMinuts(60);
		}
		// }
		// }
		// });
		//
		// thread.start();
	}

	/**
	 * <br>
	 * Created on: 2013-10-3 下午01:59:26
	 * 
	 * @param sList
	 */
	private void updateSuperpositionPackagesList(
			List<SuperpositionPackagesVo> sList) {
		synchronized (SUPER_LOCK) {
			superpositionPackagesList.clear();
			superpositionPackagesList.addAll(sList);
		}
	}

	/**
	 * <br>
	 * Created on: 2013-10-3 下午01:58:22
	 * 
	 * @param bList
	 */
	private void updateBasePackagesList(List<BasePackagesVo> bList) {
		synchronized (BASE_LOCK) {
			basePackagesList.clear();
			basePackagesList.addAll(bList);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see z.z.w.db.upgrade.exec.CacheDBFactory#initPreparedStatement()
	 */
	@Override
	protected boolean initPreparedStatement() {
		/**********************************************************
		 * base data cache :msisdn infos
		 **********************************************************/
		try {
			StringBuffer sql = new StringBuffer(
					" commit;select USR_NBR,MONTH_CD,BRND_NAME,ARUP,GPRS_FLUX,ACT_CALL,FLUX_FEE,");
			sql.append("CALL_FEE,GPRS_PRCT_CD,ADD_GRPS_CD_1,ADD_GRPS_CD_2,ADD_GRPS_CD_3 ");
			sql.append("from  BASE_FACE_INF where BRND_NAME = '全球通' and datepart(YY,getDate()) =  convert(integer,substring(MONTH_CD,1,4)) ");
			sql.append(" and datepart(MM,getDate()) <= 3+ convert(integer,substring(MONTH_CD,5))");
			LogTools.getLogger(getClass()).debug("SQL --> [{}].",
					sql.toString());
			pstmt = conn.prepareStatement(sql.toString());
			return true;
		} catch (Exception e) {
			LogTools.getLogger(getClass())
					.error("When [{} - {}] ,  \n  MASSAGE : {} \n  CAUSE :{} \n  CLASS : {}",
							new Object[] {
									this.getClass().getName(),
									"init msisdn infos cache ps --> Exception()",
									e.getMessage(), e.getCause(), e.getClass() });
			LogTools.getLogger(getClass()).warn(
					"Init msisdn info cache ps error!return false;");
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

				Map<String, List<MsisdnInfo>> cacheMapTmp = new HashMap<String, List<MsisdnInfo>>();

				while (rs.next()) {
					// USR_NBR,MONTH_CD,BRND_NAME,ARUP,BASE_GPRS,GPRS_FLUX,ACT_CALL,
					// FLUX_FEE,CALL_FEE,GPRS_PRCT_CD,ADD_GRPS_CD_1,ADD_GRPS_CD_2,ADD_GRPS_CD_3
					List<MsisdnInfo> list = null;

					String msisdn = rs.getString("USR_NBR");
					if (cacheMapTmp.containsKey(msisdn))
						list = cacheMapTmp.get(msisdn);
					else
						list = new ArrayList<MsisdnInfo>();

					// '13829300097','201307','全球通',1465.14,'',14.28,7.00,15.00,2.40,'','','',''

					MsisdnInfo mi = new MsisdnInfo();
					mi.setUSR_NBR(msisdn);
					mi.setMONTH_CD(rs.getString("MONTH_CD"));
					mi.setBRND_NAME(rs.getString("BRND_NAME"));
					mi.setARUP(rs.getDouble("ARUP"));
					mi.setGPRS_FLUX(rs.getDouble("GPRS_FLUX"));
					mi.setACT_CALL(rs.getDouble("ACT_CALL"));
					mi.setFLUX_FEE(rs.getDouble("FLUX_FEE"));
					mi.setCALL_FEE(rs.getDouble("CALL_FEE"));
					mi.setGPRS_PRCT_CD(rs.getString("GPRS_PRCT_CD"));
					mi.setADD_GRPS_CD_1(rs.getString("ADD_GRPS_CD_1"));
					mi.setADD_GRPS_CD_2(rs.getString("ADD_GRPS_CD_2"));
					mi.setADD_GRPS_CD_3(rs.getString("ADD_GRPS_CD_3"));

					list.add(mi);
					cacheMapTmp.put(msisdn, list);

				}

				rsGetFlag = false;

				updateCache(cacheMapTmp);

				rsGetFlag = true;

				rs.close();
				rs = null;
			}

			LogUtil.getLogger(getClass())
					.debug("Msisdn info cache update complete, MsisdnIMSIToSegCacheMap size :[{}].",
							lruMapCache.size());
			return true;
		} catch (Exception e) {
			LogTools.getLogger(getClass())
					.error("When [{} - {}] ,  \n  MASSAGE : {} \n  CAUSE :{} \n  CLASS : {}",
							new Object[] { this.getClass().getName(),
									"get imsi to msisdn cache --> Exception()",
									e.getMessage(), e.getCause(), e.getClass() });
			LogTools.getLogger(getClass()).warn(
					"Get msisdn info cache,return false!");
			return false;
		} finally {
			clearWork();
		}
	}

	/**
	 * <br>
	 * Created on: 2013-10-2 下午09:23:17
	 * 
	 * @param cacheMapTmp
	 */
	private void updateCache(Map<String, List<MsisdnInfo>> cacheMapTmp) {
		lruMapCache.clear();
		lruMapCache.putAll(cacheMapTmp);
	}

	/**
	 * <br>
	 * Created on: 2013-10-2 下午06:41:41
	 */
	public void updateMsisdnInfo() {

		Thread thread = new Thread(new Runnable() {

			public void run() {
				while (true) {

					long startTime = DateTools.getCurrentDateToLong();

					try {

						LogTools.getLogger(getClass()).info(
								"Start update msisdn info cache map ...");

						if (!setConnection())
							continue;

						if (!initPreparedStatement())
							continue;

						if (!getCache())
							continue;

					} catch (Exception e) {
						LogTools.getLogger(getClass())
								.error("When [{} - {}] ,  \n  MASSAGE : {} \n  CAUSE :{} \n  CLASS : {}",
										new Object[] {
												this.getClass().getName(),
												"update cache ps --> Exception() ",
												e.getMessage(), e.getCause(),
												e.getClass() });

					} finally {
						LogTools.getLogger(getClass())
								.info("Update msisdn info cache map,use time : [{}]ms.",
										((DateTools.getCurrentDateToLong() - startTime)));

						while (true) {
							if (DateTools.getDay() == 1
									&& DateTools.getHour() == 3)
								break;
							else
								ThreadTools.sleepMinuts(60);
						}

					}
				}
			}
		});

		thread.start();
	}

	/**
	 * <br>
	 * Created on: 2013-10-2 下午09:24:14
	 * 
	 * @param userMsisdn
	 * @return
	 */
	public synchronized List<MsisdnInfo> getMsisdnInfo(String userMsisdn) {
		return lruMapCache.get(userMsisdn);
	}

	/**
	 * <br>
	 * Created on: 2013-10-3 下午02:51:17
	 * 
	 * @param gPRS_FLUX_tmp
	 * @param aCT_CALL_tmp
	 * @return
	 */
	public float getSubscriptionRecommendFee(float gPRS_FLUX_tmp,
			float aCT_CALL_tmp) {

		try {
			tmpMap.clear();

			// List<BasePackagesVo> basePackagesList = new
			// List<SuperpositionPackagesVo> superpositionPackagesList = new

			putTmpMapValue(gPRS_FLUX_tmp, aCT_CALL_tmp);

			List<Map.Entry<String, Float>> listData = sortMapFee();

			// print(listData);

			return listData.get(0).getValue();
		} catch (Exception e) {
			LogTools.getLogger(getClass())
					.error("When [{} - {}] ,  \n  MASSAGE : {} \n  CAUSE :{} \n  CLASS : {}",
							new Object[] {
									this.getClass().getName(),
									"getSubscriptionRecommendFee --> Exception() ",
									e.getMessage(), e.getCause(), e.getClass() });
			return -1;
		}
	}

	/**
	 * <br>
	 * Created on: 2013-10-3 下午03:30:41
	 */
	// private void print(List<Map.Entry<String, Float>> listData) {
	// // for (Map.Entry<String, Double> entry : tmpMap.entrySet()) {
	// // LogTools.getLogger(getClass()).debug("[{}]---[{}].",
	// // entry.getKey(), entry.getValue());
	// // }
	// LogTools.getLogger(getClass())
	// .debug("=========================================================================");
	// for (Map.Entry<String, Float> entity : listData) {
	// LogTools.getLogger(getClass()).debug("SORT --> [{}]---[{}].",
	// entity.getKey(), entity.getValue());
	// }
	// }

	/**
	 * <br>
	 * Created on: 2013-10-3 下午03:27:35
	 * 
	 * @param gPRS_FLUX_tmp
	 * @param aCT_CALL_tmp
	 */
	private void putTmpMapValue(float gPRS_FLUX_tmp, float aCT_CALL_tmp) {

		Iterator<BasePackagesVo> it = basePackagesList.iterator();
		while (it.hasNext()) {
			BasePackagesVo bvo = it.next();
			Iterator<SuperpositionPackagesVo> itt = superpositionPackagesList
					.iterator();

			StringBuffer pName = new StringBuffer(bvo.getPackagesName());
			pName.append(bvo.getFee()).append("元套餐,叠加");

			float totalFee = 0.0f;
			float actCall = bvo.getFee();
			float packagesCallerFee = bvo.getInnerPackagesCaller();
			float perMin = bvo.getOuterPackagesPerMin();

			if (aCT_CALL_tmp > packagesCallerFee)
				actCall += (aCT_CALL_tmp - packagesCallerFee) * perMin;

			float packagesDataTraffic = bvo.getInnerPackagesDataTraffic();
			float perMega = bvo.getOuterPackagesPermega();

			while (itt.hasNext()) {
				SuperpositionPackagesVo svo = itt.next();

				float actCallSupper = actCall + svo.getFee();
				float gprsFlux = 0.0f;

				float supperDataTraffic = svo.getDataTraffic();

				float tmpFlux = (gPRS_FLUX_tmp - packagesDataTraffic - supperDataTraffic);
				if ((tmpFlux > 0))
					gprsFlux = tmpFlux * perMega;

				BigDecimal bigDec = new BigDecimal((actCallSupper + gprsFlux));
				totalFee = bigDec.setScale(3, BigDecimal.ROUND_HALF_UP)
						.floatValue();

				String subRecName = pName.toString() + svo.getPackagesName();
				tmpMap.put(subRecName, totalFee);

				LogTools.getLogger(getClass()).debug(
						"Put tmp map --> subRecName : [{}], totalFee :[{}].",
						subRecName, totalFee);
			}
		}
	}

	/**
	 * <br>
	 * Created on: 2013-10-3 下午03:27:06
	 */
	private List<Map.Entry<String, Float>> sortMapFee() {
		List<Map.Entry<String, Float>> listData = new ArrayList<Map.Entry<String, Float>>(
				tmpMap.entrySet());
		// 排序
		Collections.sort(listData, new Comparator<Map.Entry<String, Float>>() {
			public int compare(Map.Entry<String, Float> o1,
					Map.Entry<String, Float> o2) {
				return (int) ((o1.getValue() - o2.getValue()) * 1000);
			}
		});

		return listData;
	}

	/**
	 * <br>
	 * Created on: 2013-10-3 下午07:32:30
	 * 
	 * @param subFee
	 * @return
	 */
	public List<String> getSubscriptionRecommendName(float subFee) {
		List<String> subFeeNameList = new ArrayList<String>();
		for (Map.Entry<String, Float> entry : tmpMap.entrySet()) {
			if (entry.getValue().equals(subFee))
				subFeeNameList.add(entry.getKey());
		}

		return subFeeNameList;
	}

}
