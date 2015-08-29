/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.test.cache.vo.PackagesVo.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-10-3 下午01:26:59 
 *   LastChange: 2013-10-3 下午01:26:59 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.test.cache.vo;

/**
 * z.z.w.project.test.cache.vo.PackagesVo.java
 */
public class BasePackagesVo {
	/**
	 * <packages name="全球通上网套餐"> <grades fee="58"> <innerPackages caller="50"
	 * called="free" dataTraffic="200" buss="来电显示" /> <outerPackages
	 * permin="0.25" permega="0.5" /> </grades>
	 */

	private String packagesName;
	private float fee;
	private float innerPackagesCaller;
	private float innerPackagesCalled;
	private float innerPackagesDataTraffic;
	private String buss;

	private float outerPackagesPerMin;
	private float outerPackagesPermega;

	/**
	 * <br>
	 * Created on: 2013-10-3 下午01:28:59
	 */
	public BasePackagesVo() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PackagesVo [packagesName=" + packagesName + ", fee=" + fee
				+ ", innerPackagesCaller=" + innerPackagesCaller
				+ ", innerPackagesCalled=" + innerPackagesCalled
				+ ", innerPackagesDataTraffic=" + innerPackagesDataTraffic
				+ ", buss=" + buss + ", outerPackagesPerMin="
				+ outerPackagesPerMin + ", outerPackagesPermega="
				+ outerPackagesPermega + "]";
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
		result = prime * result + ((buss == null) ? 0 : buss.hashCode());
		result = prime * result + Float.floatToIntBits(fee);
		result = prime * result + Float.floatToIntBits(innerPackagesCalled);
		result = prime * result + Float.floatToIntBits(innerPackagesCaller);
		result = prime * result
				+ Float.floatToIntBits(innerPackagesDataTraffic);
		result = prime * result + Float.floatToIntBits(outerPackagesPerMin);
		result = prime * result + Float.floatToIntBits(outerPackagesPermega);
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
		BasePackagesVo other = (BasePackagesVo) obj;
		if (buss == null) {
			if (other.buss != null)
				return false;
		} else if (!buss.equals(other.buss))
			return false;
		if (Float.floatToIntBits(fee) != Float.floatToIntBits(other.fee))
			return false;
		if (Float.floatToIntBits(innerPackagesCalled) != Float
				.floatToIntBits(other.innerPackagesCalled))
			return false;
		if (Float.floatToIntBits(innerPackagesCaller) != Float
				.floatToIntBits(other.innerPackagesCaller))
			return false;
		if (Float.floatToIntBits(innerPackagesDataTraffic) != Float
				.floatToIntBits(other.innerPackagesDataTraffic))
			return false;
		if (Float.floatToIntBits(outerPackagesPerMin) != Float
				.floatToIntBits(other.outerPackagesPerMin))
			return false;
		if (Float.floatToIntBits(outerPackagesPermega) != Float
				.floatToIntBits(other.outerPackagesPermega))
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
	 * Created on: 2013-10-3 下午01:29:05
	 * 
	 * @return the packagesName
	 */
	public String getPackagesName() {
		return packagesName;
	}

	/**
	 * <br>
	 * Created on: 2013-10-3 下午01:29:05
	 * 
	 * @param packagesName
	 *            the packagesName to set
	 */
	public void setPackagesName(String packagesName) {
		this.packagesName = packagesName;
	}

	/**
	 * <br>
	 * Created on: 2013-10-3 下午01:29:05
	 * 
	 * @return the fee
	 */
	public float getFee() {
		return fee;
	}

	/**
	 * <br>
	 * Created on: 2013-10-3 下午01:29:05
	 * 
	 * @param fee
	 *            the fee to set
	 */
	public void setFee(float fee) {
		this.fee = fee;
	}

	/**
	 * <br>
	 * Created on: 2013-10-3 下午01:29:05
	 * 
	 * @return the innerPackagesCaller
	 */
	public float getInnerPackagesCaller() {
		return innerPackagesCaller;
	}

	/**
	 * <br>
	 * Created on: 2013-10-3 下午01:29:05
	 * 
	 * @param innerPackagesCaller
	 *            the innerPackagesCaller to set
	 */
	public void setInnerPackagesCaller(float innerPackagesCaller) {
		this.innerPackagesCaller = innerPackagesCaller;
	}

	/**
	 * <br>
	 * Created on: 2013-10-3 下午01:29:05
	 * 
	 * @return the innerPackagesCalled
	 */
	public float getInnerPackagesCalled() {
		return innerPackagesCalled;
	}

	/**
	 * <br>
	 * Created on: 2013-10-3 下午01:29:05
	 * 
	 * @param innerPackagesCalled
	 *            the innerPackagesCalled to set
	 */
	public void setInnerPackagesCalled(float innerPackagesCalled) {
		this.innerPackagesCalled = innerPackagesCalled;
	}

	/**
	 * <br>
	 * Created on: 2013-10-3 下午01:29:05
	 * 
	 * @return the innerPackagesDataTraffic
	 */
	public float getInnerPackagesDataTraffic() {
		return innerPackagesDataTraffic;
	}

	/**
	 * <br>
	 * Created on: 2013-10-3 下午01:29:05
	 * 
	 * @param innerPackagesDataTraffic
	 *            the innerPackagesDataTraffic to set
	 */
	public void setInnerPackagesDataTraffic(float innerPackagesDataTraffic) {
		this.innerPackagesDataTraffic = innerPackagesDataTraffic;
	}

	/**
	 * <br>
	 * Created on: 2013-10-3 下午01:29:05
	 * 
	 * @return the buss
	 */
	public String getBuss() {
		return buss;
	}

	/**
	 * <br>
	 * Created on: 2013-10-3 下午01:29:05
	 * 
	 * @param buss
	 *            the buss to set
	 */
	public void setBuss(String buss) {
		this.buss = buss;
	}

	/**
	 * <br>
	 * Created on: 2013-10-3 下午01:29:05
	 * 
	 * @return the outerPackagesPerMin
	 */
	public float getOuterPackagesPerMin() {
		return outerPackagesPerMin;
	}

	/**
	 * <br>
	 * Created on: 2013-10-3 下午01:29:05
	 * 
	 * @param outerPackagesPerMin
	 *            the outerPackagesPerMin to set
	 */
	public void setOuterPackagesPerMin(float outerPackagesPerMin) {
		this.outerPackagesPerMin = outerPackagesPerMin;
	}

	/**
	 * <br>
	 * Created on: 2013-10-3 下午01:29:05
	 * 
	 * @return the outerPackagesPermega
	 */
	public float getOuterPackagesPermega() {
		return outerPackagesPermega;
	}

	/**
	 * <br>
	 * Created on: 2013-10-3 下午01:29:05
	 * 
	 * @param outerPackagesPermega
	 *            the outerPackagesPermega to set
	 */
	public void setOuterPackagesPermega(float outerPackagesPermega) {
		this.outerPackagesPermega = outerPackagesPermega;
	}

}
