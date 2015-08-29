/**
 * com.etone.mantoeye.analyse.domain.HiTechFairElexconDataEntity.java
 */
package com.etone.mantoeye.analyse.domain;

/**
 * @author Wu Zhenzhen
 * @version Nov 15, 2012 4:50:19 PM
 */
public class HiTechFairElexconDataEntity {

	private String dataTime;
	private String bussName;
	private String vcCgi;
	private long totalUser;
	private long totalFlush;
	/**
	 * <br>
	 * Created on: Nov 15, 2012 4:51:13 PM
	 * 
	 * @return the dataTime
	 */
	public String getDataTime() {
		return dataTime;
	}
	/**
	 * <br>
	 * Created on: Nov 15, 2012 4:51:13 PM
	 * 
	 * @param dataTime
	 *            the dataTime to set
	 */
	public void setDataTime(String dataTime) {
		this.dataTime = dataTime;
	}
	/**
	 * <br>
	 * Created on: Nov 15, 2012 4:51:13 PM
	 * 
	 * @return the bussName
	 */
	public String getBussName() {
		return bussName;
	}
	/**
	 * <br>
	 * Created on: Nov 15, 2012 4:51:13 PM
	 * 
	 * @param bussName
	 *            the bussName to set
	 */
	public void setBussName(String bussName) {
		this.bussName = bussName;
	}
	/**
	 * <br>
	 * Created on: Nov 15, 2012 4:51:13 PM
	 * 
	 * @return the vcCgi
	 */
	public String getVcCgi() {
		return vcCgi;
	}
	/**
	 * <br>
	 * Created on: Nov 15, 2012 4:51:13 PM
	 * 
	 * @param vcCgi
	 *            the vcCgi to set
	 */
	public void setVcCgi(String vcCgi) {
		this.vcCgi = vcCgi;
	}
	/**
	 * <br>
	 * Created on: Nov 15, 2012 4:51:13 PM
	 * 
	 * @return the totalUser
	 */
	public long getTotalUser() {
		return totalUser;
	}
	/**
	 * <br>
	 * Created on: Nov 15, 2012 4:51:13 PM
	 * 
	 * @param totalUser
	 *            the totalUser to set
	 */
	public void setTotalUser(long totalUser) {
		this.totalUser = totalUser;
	}
	/**
	 * <br>
	 * Created on: Nov 15, 2012 4:51:13 PM
	 * 
	 * @return the totalFlush
	 */
	public long getTotalFlush() {
		return totalFlush;
	}
	/**
	 * <br>
	 * Created on: Nov 15, 2012 4:51:13 PM
	 * 
	 * @param totalFlush
	 *            the totalFlush to set
	 */
	public void setTotalFlush(long totalFlush) {
		this.totalFlush = totalFlush;
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
		result = prime * result
				+ ((bussName == null) ? 0 : bussName.hashCode());
		result = prime * result
				+ ((dataTime == null) ? 0 : dataTime.hashCode());
		result = prime * result + (int) (totalFlush ^ (totalFlush >>> 32));
		result = prime * result + (int) (totalUser ^ (totalUser >>> 32));
		result = prime * result + ((vcCgi == null) ? 0 : vcCgi.hashCode());
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
		HiTechFairElexconDataEntity other = (HiTechFairElexconDataEntity) obj;
		if (bussName == null) {
			if (other.bussName != null)
				return false;
		} else if (!bussName.equals(other.bussName))
			return false;
		if (dataTime == null) {
			if (other.dataTime != null)
				return false;
		} else if (!dataTime.equals(other.dataTime))
			return false;
		if (totalFlush != other.totalFlush)
			return false;
		if (totalUser != other.totalUser)
			return false;
		if (vcCgi == null) {
			if (other.vcCgi != null)
				return false;
		} else if (!vcCgi.equals(other.vcCgi))
			return false;
		return true;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "HiTechFairElexconDataEntity [dataTime=" + dataTime
				+ ", bussName=" + bussName + ", vcCgi=" + vcCgi
				+ ", totalUser=" + totalUser + ", totalFlush=" + totalFlush
				+ "]";
	}
	/**
	 * <br>
	 * Created on: Nov 15, 2012 4:51:19 PM
	 * 
	 * Created by: Wu Zhenzhen
	 * 
	 * @param dataTime
	 * @param bussName
	 * @param vcCgi
	 * @param totalUser
	 * @param totalFlush
	 */
	public HiTechFairElexconDataEntity(String dataTime, String bussName,
			String vcCgi, long totalUser, long totalFlush) {
		this.dataTime = dataTime;
		this.bussName = bussName;
		this.vcCgi = vcCgi;
		this.totalUser = totalUser;
		this.totalFlush = totalFlush;
	}
	/**
	 * <br>
	 * Created on: Nov 15, 2012 4:51:21 PM
	 * 
	 * Created by: Wu Zhenzhen
	 * 
	 */
	public HiTechFairElexconDataEntity() {
		// TODO Nov 15, 20124:51:21 PM HiTechFairElexconDataEntity constructor
		super();
	}

}
