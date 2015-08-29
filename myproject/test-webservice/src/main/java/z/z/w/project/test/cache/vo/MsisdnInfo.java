/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.test.cache.vo.MsisdnInfo.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-10-2 下午08:53:43 
 *   LastChange: 2013-10-2 下午08:53:43 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.test.cache.vo;

/**
 * z.z.w.project.test.cache.vo.MsisdnInfo.java
 */
public class MsisdnInfo extends HistoryMsisdnInfo {

	// USR_NBR,MONTH_CD,BRND_NAME,ARUP,BASE_GPRS,GPRS_FLUX,ACT_CALL,FLUX_FEE,CALL_FEE,GPRS_PRCT_CD,ADD_GRPS_CD_1,ADD_GRPS_CD_2,ADD_GRPS_CD_3
	private String USR_NBR;
	private String BRND_NAME;
	private double ARUP;
	private String GPRS_PRCT_CD;
	private String ADD_GRPS_CD_1;
	private String ADD_GRPS_CD_2;
	private String ADD_GRPS_CD_3;

	/**
	 * <br>
	 * Created on: 2013-10-3 下午02:20:10
	 */
	public MsisdnInfo() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MsisdnInfo [USR_NBR=" + USR_NBR + ", BRND_NAME=" + BRND_NAME
				+ ", ARUP=" + ARUP + ", GPRS_PRCT_CD=" + GPRS_PRCT_CD
				+ ", ADD_GRPS_CD_1=" + ADD_GRPS_CD_1 + ", ADD_GRPS_CD_2="
				+ ADD_GRPS_CD_2 + ", ADD_GRPS_CD_3=" + ADD_GRPS_CD_3 + "]";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((ADD_GRPS_CD_1 == null) ? 0 : ADD_GRPS_CD_1.hashCode());
		result = prime * result
				+ ((ADD_GRPS_CD_2 == null) ? 0 : ADD_GRPS_CD_2.hashCode());
		result = prime * result
				+ ((ADD_GRPS_CD_3 == null) ? 0 : ADD_GRPS_CD_3.hashCode());
		long temp;
		temp = Double.doubleToLongBits(ARUP);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result
				+ ((BRND_NAME == null) ? 0 : BRND_NAME.hashCode());
		result = prime * result
				+ ((GPRS_PRCT_CD == null) ? 0 : GPRS_PRCT_CD.hashCode());
		result = prime * result + ((USR_NBR == null) ? 0 : USR_NBR.hashCode());
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
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		MsisdnInfo other = (MsisdnInfo) obj;
		if (ADD_GRPS_CD_1 == null) {
			if (other.ADD_GRPS_CD_1 != null)
				return false;
		} else if (!ADD_GRPS_CD_1.equals(other.ADD_GRPS_CD_1))
			return false;
		if (ADD_GRPS_CD_2 == null) {
			if (other.ADD_GRPS_CD_2 != null)
				return false;
		} else if (!ADD_GRPS_CD_2.equals(other.ADD_GRPS_CD_2))
			return false;
		if (ADD_GRPS_CD_3 == null) {
			if (other.ADD_GRPS_CD_3 != null)
				return false;
		} else if (!ADD_GRPS_CD_3.equals(other.ADD_GRPS_CD_3))
			return false;
		if (Double.doubleToLongBits(ARUP) != Double
				.doubleToLongBits(other.ARUP))
			return false;
		if (BRND_NAME == null) {
			if (other.BRND_NAME != null)
				return false;
		} else if (!BRND_NAME.equals(other.BRND_NAME))
			return false;
		if (GPRS_PRCT_CD == null) {
			if (other.GPRS_PRCT_CD != null)
				return false;
		} else if (!GPRS_PRCT_CD.equals(other.GPRS_PRCT_CD))
			return false;
		if (USR_NBR == null) {
			if (other.USR_NBR != null)
				return false;
		} else if (!USR_NBR.equals(other.USR_NBR))
			return false;
		return true;
	}

	/**
	 * <br>
	 * Created on: 2013-10-4 下午03:11:26
	 * 
	 * @return the uSR_NBR
	 */
	public String getUSR_NBR() {
		return USR_NBR;
	}

	/**
	 * <br>
	 * Created on: 2013-10-4 下午03:11:26
	 * 
	 * @param uSR_NBR
	 *            the uSR_NBR to set
	 */
	public void setUSR_NBR(String uSR_NBR) {
		USR_NBR = uSR_NBR;
	}

	/**
	 * <br>
	 * Created on: 2013-10-4 下午03:11:26
	 * 
	 * @return the bRND_NAME
	 */
	public String getBRND_NAME() {
		return BRND_NAME;
	}

	/**
	 * <br>
	 * Created on: 2013-10-4 下午03:11:26
	 * 
	 * @param bRND_NAME
	 *            the bRND_NAME to set
	 */
	public void setBRND_NAME(String bRND_NAME) {
		BRND_NAME = bRND_NAME;
	}

	/**
	 * <br>
	 * Created on: 2013-10-4 下午03:11:26
	 * 
	 * @return the aRUP
	 */
	public double getARUP() {
		return ARUP;
	}

	/**
	 * <br>
	 * Created on: 2013-10-4 下午03:11:26
	 * 
	 * @param aRUP
	 *            the aRUP to set
	 */
	public void setARUP(double aRUP) {
		ARUP = aRUP;
	}

	/**
	 * <br>
	 * Created on: 2013-10-4 下午03:11:26
	 * 
	 * @return the gPRS_PRCT_CD
	 */
	public String getGPRS_PRCT_CD() {
		return GPRS_PRCT_CD;
	}

	/**
	 * <br>
	 * Created on: 2013-10-4 下午03:11:26
	 * 
	 * @param gPRS_PRCT_CD
	 *            the gPRS_PRCT_CD to set
	 */
	public void setGPRS_PRCT_CD(String gPRS_PRCT_CD) {
		GPRS_PRCT_CD = gPRS_PRCT_CD;
	}

	/**
	 * <br>
	 * Created on: 2013-10-4 下午03:11:26
	 * 
	 * @return the aDD_GRPS_CD_1
	 */
	public String getADD_GRPS_CD_1() {
		return ADD_GRPS_CD_1;
	}

	/**
	 * <br>
	 * Created on: 2013-10-4 下午03:11:26
	 * 
	 * @param aDD_GRPS_CD_1
	 *            the aDD_GRPS_CD_1 to set
	 */
	public void setADD_GRPS_CD_1(String aDD_GRPS_CD_1) {
		ADD_GRPS_CD_1 = aDD_GRPS_CD_1;
	}

	/**
	 * <br>
	 * Created on: 2013-10-4 下午03:11:26
	 * 
	 * @return the aDD_GRPS_CD_2
	 */
	public String getADD_GRPS_CD_2() {
		return ADD_GRPS_CD_2;
	}

	/**
	 * <br>
	 * Created on: 2013-10-4 下午03:11:26
	 * 
	 * @param aDD_GRPS_CD_2
	 *            the aDD_GRPS_CD_2 to set
	 */
	public void setADD_GRPS_CD_2(String aDD_GRPS_CD_2) {
		ADD_GRPS_CD_2 = aDD_GRPS_CD_2;
	}

	/**
	 * <br>
	 * Created on: 2013-10-4 下午03:11:26
	 * 
	 * @return the aDD_GRPS_CD_3
	 */
	public String getADD_GRPS_CD_3() {
		return ADD_GRPS_CD_3;
	}

	/**
	 * <br>
	 * Created on: 2013-10-4 下午03:11:26
	 * 
	 * @param aDD_GRPS_CD_3
	 *            the aDD_GRPS_CD_3 to set
	 */
	public void setADD_GRPS_CD_3(String aDD_GRPS_CD_3) {
		ADD_GRPS_CD_3 = aDD_GRPS_CD_3;
	}

}
