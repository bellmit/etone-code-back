/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.test.cache.vo.SuperpositionPackagesVo.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-10-3 下午01:29:54 
 *   LastChange: 2013-10-3 下午01:29:54 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.test.cache.vo;

/**
 * z.z.w.project.test.cache.vo.SuperpositionPackagesVo.java
 */
public class SuperpositionPackagesVo {

	/**
	 * <superpositionPackages name="5元套餐" fee="5" dataTraffic="30" />
	 * <superpositionPackages name="10元套餐" fee="10" dataTraffic="70" />
	 * <superpositionPackages name="20元套餐" fee="20" dataTraffic="150" />
	 * <superpositionPackages name="30元套餐" fee="30" dataTraffic="280" />
	 * <superpositionPackages name="50元套餐" fee="50" dataTraffic="500" />
	 * <superpositionPackages name="100元套餐" fee="100" dataTraffic="2048" />
	 */

	private String packagesName;
	private float fee;
	private float dataTraffic;

	/**
	 * <br>
	 * Created on: 2013-10-3 下午01:30:38
	 */
	public SuperpositionPackagesVo() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SuperpositionPackagesVo [packagesName=" + packagesName
				+ ", fee=" + fee + ", dataTraffic=" + dataTraffic + "]";
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
		result = prime * result + Float.floatToIntBits(dataTraffic);
		result = prime * result + Float.floatToIntBits(fee);
		result = prime * result
				+ ((packagesName == null) ? 0 : packagesName.hashCode());
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
		SuperpositionPackagesVo other = (SuperpositionPackagesVo) obj;
		if (Float.floatToIntBits(dataTraffic) != Float
				.floatToIntBits(other.dataTraffic))
			return false;
		if (Float.floatToIntBits(fee) != Float.floatToIntBits(other.fee))
			return false;
		if (packagesName == null) {
			if (other.packagesName != null)
				return false;
		} else if (!packagesName.equals(other.packagesName))
			return false;
		return true;
	}

	/**
	 * <br>
	 * Created on: 2013-10-3 下午01:30:44
	 * 
	 * @return the packagesName
	 */
	public String getPackagesName() {
		return packagesName;
	}

	/**
	 * <br>
	 * Created on: 2013-10-3 下午01:30:44
	 * 
	 * @param packagesName
	 *            the packagesName to set
	 */
	public void setPackagesName(String packagesName) {
		this.packagesName = packagesName;
	}

	/**
	 * <br>
	 * Created on: 2013-10-3 下午01:30:44
	 * 
	 * @return the fee
	 */
	public float getFee() {
		return fee;
	}

	/**
	 * <br>
	 * Created on: 2013-10-3 下午01:30:44
	 * 
	 * @param fee
	 *            the fee to set
	 */
	public void setFee(float fee) {
		this.fee = fee;
	}

	/**
	 * <br>
	 * Created on: 2013-10-3 下午01:30:44
	 * 
	 * @return the dataTraffic
	 */
	public float getDataTraffic() {
		return dataTraffic;
	}

	/**
	 * <br>
	 * Created on: 2013-10-3 下午01:30:44
	 * 
	 * @param dataTraffic
	 *            the dataTraffic to set
	 */
	public void setDataTraffic(float dataTraffic) {
		this.dataTraffic = dataTraffic;
	}

}
