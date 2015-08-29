/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.test.cache.vo.RecommendationInfo.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-10-4 下午03:21:15 
 *   LastChange: 2013-10-4 下午03:21:15 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.test.cache.vo;

import java.util.List;

/**
 * z.z.w.project.test.cache.vo.RecommendationInfo.java
 */
public class RecommendationInfo {
	// GPRS_PRCT_CD,MONTH_CD,GPRS_FLUX,ACT_CALL,FLUX_FEE,CALL_FEE
	// 用戶號碼
	private String userMsisdn;
	// 當前套餐ID
	private String currentPackagesId;
	// 推薦套餐ID
	// private String recommendationPackagesID;
	// 推薦套餐名稱
	private List<String> recommendationPackagesNameList;
	// 歷史消費情況
	private List<HistoryMsisdnInfo> msisdnInfoList = null;

	/**
	 * <br>
	 * Created on: 2013-10-4 下午03:21:29
	 */
	public RecommendationInfo() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "RecommendationInfo [userMsisdn=" + userMsisdn
				+ ", currentPackagesId=" + currentPackagesId
				+ ", recommendationPackagesNameList="
				+ recommendationPackagesNameList + ", msisdnInfoList="
				+ msisdnInfoList + "]";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((currentPackagesId == null) ? 0 : currentPackagesId
						.hashCode());
		result = prime * result
				+ ((msisdnInfoList == null) ? 0 : msisdnInfoList.hashCode());
		result = prime
				* result
				+ ((recommendationPackagesNameList == null) ? 0
						: recommendationPackagesNameList.hashCode());
		result = prime * result
				+ ((userMsisdn == null) ? 0 : userMsisdn.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RecommendationInfo other = (RecommendationInfo) obj;
		if (currentPackagesId == null) {
			if (other.currentPackagesId != null)
				return false;
		} else if (!currentPackagesId.equals(other.currentPackagesId))
			return false;
		if (msisdnInfoList == null) {
			if (other.msisdnInfoList != null)
				return false;
		} else if (!msisdnInfoList.equals(other.msisdnInfoList))
			return false;
		if (recommendationPackagesNameList == null) {
			if (other.recommendationPackagesNameList != null)
				return false;
		} else if (!recommendationPackagesNameList
				.equals(other.recommendationPackagesNameList))
			return false;
		if (userMsisdn == null) {
			if (other.userMsisdn != null)
				return false;
		} else if (!userMsisdn.equals(other.userMsisdn))
			return false;
		return true;
	}

	/**
	 * <br>
	 * Created on: 2013-10-4 下午03:21:35
	 * 
	 * @return the userMsisdn
	 */
	public String getUserMsisdn() {
		return userMsisdn;
	}

	/**
	 * <br>
	 * Created on: 2013-10-4 下午03:21:35
	 * 
	 * @param userMsisdn
	 *            the userMsisdn to set
	 */
	public void setUserMsisdn(String userMsisdn) {
		this.userMsisdn = userMsisdn;
	}

	/**
	 * <br>
	 * Created on: 2013-10-4 下午03:21:35
	 * 
	 * @return the currentPackagesId
	 */
	public String getCurrentPackagesId() {
		return currentPackagesId;
	}

	/**
	 * <br>
	 * Created on: 2013-10-4 下午03:21:35
	 * 
	 * @param currentPackagesId
	 *            the currentPackagesId to set
	 */
	public void setCurrentPackagesId(String currentPackagesId) {
		this.currentPackagesId = currentPackagesId;
	}

	/**
	 * <br>
	 * Created on: 2013-10-4 下午03:21:35
	 * 
	 * @return the recommendationPackagesNameList
	 */
	public List<String> getRecommendationPackagesNameList() {
		return recommendationPackagesNameList;
	}

	/**
	 * <br>
	 * Created on: 2013-10-4 下午03:21:35
	 * 
	 * @param recommendationPackagesNameList
	 *            the recommendationPackagesNameList to set
	 */
	public void setRecommendationPackagesNameList(
			List<String> recommendationPackagesNameList) {
		this.recommendationPackagesNameList = recommendationPackagesNameList;
	}

	/**
	 * <br>
	 * Created on: 2013-10-4 下午03:21:35
	 * 
	 * @return the msisdnInfoList
	 */
	public List<HistoryMsisdnInfo> getMsisdnInfoList() {
		return msisdnInfoList;
	}

	/**
	 * <br>
	 * Created on: 2013-10-4 下午03:21:35
	 * 
	 * @param msisdnInfoList
	 *            the msisdnInfoList to set
	 */
	public void setMsisdnInfoList(List<HistoryMsisdnInfo> msisdnInfoList) {
		this.msisdnInfoList = msisdnInfoList;
	}

}
