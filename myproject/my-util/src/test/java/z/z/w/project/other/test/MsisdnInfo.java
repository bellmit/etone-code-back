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
package z.z.w.project.other.test;

/**
 * z.z.w.project.test.cache.vo.MsisdnInfo.java
 */
public class MsisdnInfo {

	// USR_NBR,MONTH_CD,BRND_NAME,ARUP,BASE_GPRS,GPRS_FLUX,ACT_CALL,FLUX_FEE,CALL_FEE,GPRS_PRCT_CD,ADD_GRPS_CD_1,ADD_GRPS_CD_2,ADD_GRPS_CD_3
	private String MONTH_CD;
	private double GPRS_FLUX;
	private double ACT_CALL;
	private double FLUX_FEE;
	private double CALL_FEE;

	/**
	 * <br>
	 * Created on: 2013-10-4 下午03:05:38
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
		return "MsisdnInfo [MONTH_CD=" + MONTH_CD + ", GPRS_FLUX=" + GPRS_FLUX
				+ ", ACT_CALL=" + ACT_CALL + ", FLUX_FEE=" + FLUX_FEE
				+ ", CALL_FEE=" + CALL_FEE + "]";
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
		long temp;
		temp = Double.doubleToLongBits(ACT_CALL);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(CALL_FEE);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(FLUX_FEE);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(GPRS_FLUX);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result
				+ ((MONTH_CD == null) ? 0 : MONTH_CD.hashCode());
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
		MsisdnInfo other = (MsisdnInfo) obj;
		if (Double.doubleToLongBits(ACT_CALL) != Double
				.doubleToLongBits(other.ACT_CALL))
			return false;
		if (Double.doubleToLongBits(CALL_FEE) != Double
				.doubleToLongBits(other.CALL_FEE))
			return false;
		if (Double.doubleToLongBits(FLUX_FEE) != Double
				.doubleToLongBits(other.FLUX_FEE))
			return false;
		if (Double.doubleToLongBits(GPRS_FLUX) != Double
				.doubleToLongBits(other.GPRS_FLUX))
			return false;
		if (MONTH_CD == null) {
			if (other.MONTH_CD != null)
				return false;
		} else if (!MONTH_CD.equals(other.MONTH_CD))
			return false;
		return true;
	}

	/**
	 * <br>
	 * Created on: 2013-10-4 下午03:05:44
	 * 
	 * @return the mONTH_CD
	 */
	public String getMONTH_CD() {
		return MONTH_CD;
	}

	/**
	 * <br>
	 * Created on: 2013-10-4 下午03:05:44
	 * 
	 * @param mONTH_CD
	 *            the mONTH_CD to set
	 */
	public void setMONTH_CD(String mONTH_CD) {
		MONTH_CD = mONTH_CD;
	}

	/**
	 * <br>
	 * Created on: 2013-10-4 下午03:05:44
	 * 
	 * @return the gPRS_FLUX
	 */
	public double getGPRS_FLUX() {
		return GPRS_FLUX;
	}

	/**
	 * <br>
	 * Created on: 2013-10-4 下午03:05:44
	 * 
	 * @param gPRS_FLUX
	 *            the gPRS_FLUX to set
	 */
	public void setGPRS_FLUX(double gPRS_FLUX) {
		GPRS_FLUX = gPRS_FLUX;
	}

	/**
	 * <br>
	 * Created on: 2013-10-4 下午03:05:44
	 * 
	 * @return the aCT_CALL
	 */
	public double getACT_CALL() {
		return ACT_CALL;
	}

	/**
	 * <br>
	 * Created on: 2013-10-4 下午03:05:44
	 * 
	 * @param aCT_CALL
	 *            the aCT_CALL to set
	 */
	public void setACT_CALL(double aCT_CALL) {
		ACT_CALL = aCT_CALL;
	}

	/**
	 * <br>
	 * Created on: 2013-10-4 下午03:05:44
	 * 
	 * @return the fLUX_FEE
	 */
	public double getFLUX_FEE() {
		return FLUX_FEE;
	}

	/**
	 * <br>
	 * Created on: 2013-10-4 下午03:05:44
	 * 
	 * @param fLUX_FEE
	 *            the fLUX_FEE to set
	 */
	public void setFLUX_FEE(double fLUX_FEE) {
		FLUX_FEE = fLUX_FEE;
	}

	/**
	 * <br>
	 * Created on: 2013-10-4 下午03:05:44
	 * 
	 * @return the cALL_FEE
	 */
	public double getCALL_FEE() {
		return CALL_FEE;
	}

	/**
	 * <br>
	 * Created on: 2013-10-4 下午03:05:44
	 * 
	 * @param cALL_FEE
	 *            the cALL_FEE to set
	 */
	public void setCALL_FEE(double cALL_FEE) {
		CALL_FEE = cALL_FEE;
	}

}
