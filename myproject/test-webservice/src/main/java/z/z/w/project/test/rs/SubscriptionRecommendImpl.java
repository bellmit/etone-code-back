/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.test.rs.SubscriptionRecommendImpl.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-10-2 下午05:25:01 
 *   LastChange: 2013-10-2 下午05:25:01 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.test.rs;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import z.z.w.project.test.cache.BaseMsisdnInfoCache;
import z.z.w.project.test.cache.vo.HistoryMsisdnInfo;
import z.z.w.project.test.cache.vo.MsisdnInfo;
import z.z.w.project.test.cache.vo.RecommendationInfo;
import z.z.w.project.util.common.DataTools;
import z.z.w.project.util.common.JsonTools;
import z.z.w.project.util.common.LogTools;

/**
 * 
 * z.z.w.project.test.rs.SubscriptionRecommendImpl.java
 */
public class SubscriptionRecommendImpl implements ISubscriptionRecommend {

	private static final BaseMsisdnInfoCache baseMsisdnInfoCache = BaseMsisdnInfoCache
			.getInstance();

	private static final String ERROR_CODE = "{\"userMsisdn\":\"ERROR\"}";

	private String USR_NBR = "";

	private List<MsisdnInfo> list = null;

	// 當前套餐ID
	private String currentPackagesId = null;

	// 構造最終json格式的歷史數據列表
	private List<HistoryMsisdnInfo> msisdnInfoList = null;

	/**
	 * <br>
	 * Created on: 2013-10-2 下午05:25:01
	 */
	public SubscriptionRecommendImpl() {
		super();
		init();
	}

	/**
	 * <br>
	 * Created on: 2013-10-4 下午03:19:10
	 */
	private void init() {
		this.USR_NBR = null;
		this.list = null;
		this.currentPackagesId = null;
		this.msisdnInfoList = new ArrayList<HistoryMsisdnInfo>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see z.z.w.project.test.rs.ISubscriptionRecommend#getUserMsisdnInfos()
	 */
	public synchronized String getUserMsisdnInfos(String userMsisdn) {

		try {
			this.USR_NBR = userMsisdn;

			LogTools.getLogger(getClass()).debug("Get msisdn : [{}] info...",
					USR_NBR);

			list = baseMsisdnInfoCache.getMsisdnInfo(this.USR_NBR);

			if (DataTools.isEmpty(list))
				return ERROR_CODE;

			sortList();

			String subscriptionRecommend = getSubscriptionRecommend();

			LogTools.getLogger(getClass()).debug(
					"Get subscriptionRecommend : [{}].", subscriptionRecommend);

			return subscriptionRecommend;
		} catch (Exception e) {
			LogTools.getLogger(getClass())
					.error("When [{} - {}] ,  \n  MASSAGE : {} \n  CAUSE :{} \n  CLASS : {}",
							new Object[] { this.getClass().getName(),
									"getUserMsisdnInfos --> Exception() ",
									e.getMessage(), e.getCause(), e.getClass() });

			return ERROR_CODE;
		} finally {

		}
	}

	/**
	 * <br>
	 * Created on: 2013-10-3 下午02:25:22
	 * 
	 * @param list
	 */
	private void sortList() {

		Collections.sort(list, new Comparator<MsisdnInfo>() {
			public int compare(MsisdnInfo o1, MsisdnInfo o2) {
				return o1.getMONTH_CD().compareToIgnoreCase(o2.getMONTH_CD());
			}

		});
	}

	/**
	 * <br>
	 * Created on: 2013-10-3 下午02:06:43
	 * 
	 * @param list
	 * @return
	 */
	private String getSubscriptionRecommend() {

		// Map<String, Double> fluxMap = new HashMap<String, Double>();
		// Map<String, Double> actMap = new HashMap<String, Double>();

		float GPRS_FLUX_tmp = -1;
		float ACT_CALL_tmp = -1;

		Iterator<MsisdnInfo> it = list.iterator();
		while (it.hasNext()) {
			MsisdnInfo mi = it.next();
			// 號碼，--- 月份 ，---品牌 ，--arup，--流量 ，---主叫時長
			// USR_NBR,MONTH_CD,BRND_NAME,ARUP,GPRS_FLUX,ACT_CALL,
			// 流量費用,通話費用 ,基本GPRS編碼，疊加套餐編碼1,2,3
			// FLUX_FEE,CALL_FEE,GPRS_PRCT_CD,ADD_GRPS_CD_1,ADD_GRPS_CD_2,ADD_GRPS_CD_3

			LogTools.getLogger(getClass()).debug("[{}].", mi.toString());

			GPRS_FLUX_tmp = getUserValue(GPRS_FLUX_tmp, mi.getGPRS_FLUX());
			ACT_CALL_tmp = getUserValue(ACT_CALL_tmp, mi.getACT_CALL());

			// other infos
			currentPackagesId = mi.getGPRS_PRCT_CD();

			HistoryMsisdnInfo info = getHistoryMsisdnInfo(mi);
			msisdnInfoList.add(info);

		}

		int size = list.size();
		GPRS_FLUX_tmp = GPRS_FLUX_tmp / size;
		ACT_CALL_tmp = ACT_CALL_tmp / size;

		LogTools.getLogger(getClass()).debug(
				"Average GPRS_FLUX:[{}],Average ACT_CALL:[{}].", GPRS_FLUX_tmp,
				ACT_CALL_tmp);

		float subFee = baseMsisdnInfoCache.getSubscriptionRecommendFee(
				GPRS_FLUX_tmp, ACT_CALL_tmp);

		if (subFee == -1)
			return ERROR_CODE;

		LogTools.getLogger(getClass()).debug("SUB FEE : [{}].", subFee);

		if (!compareOldPackages(subFee))
			return getFinalSubRec(null);

		List<String> subFeeNameList = baseMsisdnInfoCache
				.getSubscriptionRecommendName(subFee);

		if (DataTools.isEmpty(subFeeNameList))
			return ERROR_CODE;

		return getFinalSubRec(subFeeNameList);

	}

	/**
	 * <br>
	 * Created on: 2013-10-4 下午03:17:02
	 * 
	 * @param mi
	 * @return
	 */
	private HistoryMsisdnInfo getHistoryMsisdnInfo(MsisdnInfo mi) {
		HistoryMsisdnInfo info = new HistoryMsisdnInfo();
		info.setACT_CALL(mi.getACT_CALL());
		info.setCALL_FEE(mi.getCALL_FEE());
		info.setFLUX_FEE(mi.getFLUX_FEE());
		info.setGPRS_FLUX(mi.getGPRS_FLUX());
		info.setMONTH_CD(mi.getMONTH_CD());
		return info;
	}

	/**
	 * <br>
	 * Created on: 2013-10-4 下午03:14:56
	 * 
	 * @param valueTmp
	 * @param value
	 * @return
	 */
	private float getUserValue(float valueTmp, double value) {
		BigDecimal bigDec = new BigDecimal(value);
		if (valueTmp == -1)
			valueTmp = bigDec.setScale(3, BigDecimal.ROUND_HALF_UP)
					.floatValue();
		else
			valueTmp += value;

		return valueTmp;
	}

	/**
	 * <br>
	 * Created on: 2013-10-4 下午01:34:53
	 * 
	 * @param list
	 * @param subFee
	 * @return
	 */
	private boolean compareOldPackages(float subFee) {
		try {
			// TODO -- 2013-10-4 14:26:12 比較推薦套餐與過去三個月話務消費情況
			// Iterator<MsisdnInfo> it = list.iterator();
			// while (it.hasNext()) {
			// MsisdnInfo mi = it.next();
			// // 號碼，--- 月份 ，---品牌 ，--arup，--流量 ，---主叫時長
			// // USR_NBR,MONTH_CD,BRND_NAME,ARUP,GPRS_FLUX,ACT_CALL,
			// // 流量費用,通話費用 ,基本GPRS編碼，疊加套餐編碼1,2,3
			// FLUX_FEE,CALL_FEE,GPRS_PRCT_CD,ADD_GRPS_CD_1,ADD_GRPS_CD_2,ADD_GRPS_CD_3
			//
			// LogTools.getLogger(getClass()).debug("[{}].", mi.toString());
			//
			// double GPRS_FLUX = mi.getGPRS_FLUX();
			// BigDecimal bigDec = new BigDecimal(GPRS_FLUX);
			//
			// }
			return true;
		} catch (Exception e) {
			LogTools.getLogger(getClass())
					.error("When [{} - {}] ,  \n  MASSAGE : {} \n  CAUSE :{} \n  CLASS : {}",
							new Object[] { this.getClass().getName(),
									"compareOldPackages --> Exception() ",
									e.getMessage(), e.getCause(), e.getClass() });
			return false;
		}
	}

	/**
	 * <br>
	 * Created on: 2013-10-3 下午07:35:49
	 * 
	 * @param list
	 * @param subFeeNameList
	 * @return
	 */
	private String getFinalSubRec(List<String> subFeeNameList) {
		try {

			RecommendationInfo info = new RecommendationInfo();
			info.setUserMsisdn(this.USR_NBR);
			info.setCurrentPackagesId(this.currentPackagesId);
			info.setMsisdnInfoList(msisdnInfoList);
			if (!DataTools.isEmpty(subFeeNameList))
				info.setRecommendationPackagesNameList(subFeeNameList);

			return JsonTools.entityToJson(info);
		} catch (Exception e) {
			LogTools.getLogger(getClass())
					.error("When [{} - {}] ,  \n  MASSAGE : {} \n  CAUSE :{} \n  CLASS : {}",
							new Object[] { this.getClass().getName(),
									"getFinalSubRec --> Exception() ",
									e.getMessage(), e.getCause(), e.getClass() });
			return ERROR_CODE;
		}
	}
}
