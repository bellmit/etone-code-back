/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.test.vo.TbCgiInfo.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2014-1-24 下午05:16:01 
 *   LastChange: 2014-1-24 下午05:16:01 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.test.vo;

/**
 * z.z.w.project.test.vo.TbCgiInfo.java
 */
public class TbCgiInfo {
	// intLac,intCI,vcCellEnName,vcCellChName

	private int intLac;
	private int intCI;
	private String vcCellChName;
	private String vcCellEnName;

	/**
	 * <br>
	 * Created on: 2014-1-24 下午05:17:18
	 */
	public TbCgiInfo() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TbCgiInfo [intLac=" + intLac + ", intCI=" + intCI + ", vcCellChName=" + vcCellChName + ", vcCellEnName=" + vcCellEnName + "]";
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
		result = prime * result + intCI;
		result = prime * result + intLac;
		result = prime * result + ((vcCellChName == null) ? 0 : vcCellChName.hashCode());
		result = prime * result + ((vcCellEnName == null) ? 0 : vcCellEnName.hashCode());
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
		TbCgiInfo other = (TbCgiInfo) obj;
		if (intCI != other.intCI)
			return false;
		if (intLac != other.intLac)
			return false;
		if (vcCellChName == null) {
			if (other.vcCellChName != null)
				return false;
		} else if (!vcCellChName.equals(other.vcCellChName))
			return false;
		if (vcCellEnName == null) {
			if (other.vcCellEnName != null)
				return false;
		} else if (!vcCellEnName.equals(other.vcCellEnName))
			return false;
		return true;
	}

	/**
	 * <br>
	 * Created on: 2014-1-24 下午05:17:23
	 * 
	 * @return the intLac
	 */
	public int getIntLac() {
		return intLac;
	}

	/**
	 * <br>
	 * Created on: 2014-1-24 下午05:17:23
	 * 
	 * @param intLac
	 *            the intLac to set
	 */
	public void setIntLac(int intLac) {
		this.intLac = intLac;
	}

	/**
	 * <br>
	 * Created on: 2014-1-24 下午05:17:23
	 * 
	 * @return the intCI
	 */
	public int getIntCI() {
		return intCI;
	}

	/**
	 * <br>
	 * Created on: 2014-1-24 下午05:17:23
	 * 
	 * @param intCI
	 *            the intCI to set
	 */
	public void setIntCI(int intCI) {
		this.intCI = intCI;
	}

	/**
	 * <br>
	 * Created on: 2014-1-24 下午05:17:23
	 * 
	 * @return the vcCellChName
	 */
	public String getVcCellChName() {
		return vcCellChName;
	}

	/**
	 * <br>
	 * Created on: 2014-1-24 下午05:17:23
	 * 
	 * @param vcCellChName
	 *            the vcCellChName to set
	 */
	public void setVcCellChName(String vcCellChName) {
		this.vcCellChName = vcCellChName;
	}

	/**
	 * <br>
	 * Created on: 2014-1-24 下午05:17:23
	 * 
	 * @return the vcCellEnName
	 */
	public String getVcCellEnName() {
		return vcCellEnName;
	}

	/**
	 * <br>
	 * Created on: 2014-1-24 下午05:17:23
	 * 
	 * @param vcCellEnName
	 *            the vcCellEnName to set
	 */
	public void setVcCellEnName(String vcCellEnName) {
		this.vcCellEnName = vcCellEnName;
	}

}
