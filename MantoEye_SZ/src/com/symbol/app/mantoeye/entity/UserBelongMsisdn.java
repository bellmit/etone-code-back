/**
 * com.symbol.app.mantoeye.bean.UserBelongMsisdn.java
 */
package com.symbol.app.mantoeye.entity;

/**
 * @author Wu Zhenzhen
 * @version 2013-10-17 上午11:54:17
 */
public class UserBelongMsisdn {
	private String nmMsisdn;

	/**
	 * <br>
	 * Created on: 2013-10-17 上午11:54:32
	 */
	public UserBelongMsisdn() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "UserBelongMsisdn [nmMsisdn=" + nmMsisdn + "]";
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
				+ ((nmMsisdn == null) ? 0 : nmMsisdn.hashCode());
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
		UserBelongMsisdn other = (UserBelongMsisdn) obj;
		if (nmMsisdn == null) {
			if (other.nmMsisdn != null)
				return false;
		} else if (!nmMsisdn.equals(other.nmMsisdn))
			return false;
		return true;
	}

	/**
	 * <br>
	 * Created on: 2013-10-17 上午11:54:37
	 * 
	 * @return the nmMsisdn
	 */
	public String getNmMsisdn() {
		return nmMsisdn;
	}

	/**
	 * <br>
	 * Created on: 2013-10-17 上午11:54:37
	 * 
	 * @param nmMsisdn
	 *            the nmMsisdn to set
	 */
	public void setNmMsisdn(String nmMsisdn) {
		this.nmMsisdn = nmMsisdn;
	}

}
