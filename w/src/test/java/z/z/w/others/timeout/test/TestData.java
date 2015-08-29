/**
 * z.z.w.others.TestData.java
 */
package z.z.w.others.timeout.test;

/**
 * @author Wu Zhenzhen
 * @version Mar 21, 2013 11:38:47 AM
 */
public class TestData {
	private long endTime = 0;

	/**
	 * <br>
	 * Created on: Mar 21, 2013 12:14:00 PM
	 */
	public TestData() {
		super();
	}

	/**
	 * <br>
	 * Created on: Mar 21, 2013 12:14:04 PM
	 * 
	 * @return the endTime
	 */
	public long getEndTime() {
		return endTime;
	}

	/**
	 * <br>
	 * Created on: Mar 21, 2013 12:14:04 PM
	 * 
	 * @param endTime
	 *            the endTime to set
	 */
	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TestData [endTime=" + endTime + "]";
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
		result = prime * result + (int) (endTime ^ (endTime >>> 32));
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
		TestData other = (TestData) obj;
		if (endTime != other.endTime)
			return false;
		return true;
	}

}
