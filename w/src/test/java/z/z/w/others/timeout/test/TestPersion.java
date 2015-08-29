/**
 * z.z.w.test.TestPersion.java
 */
package z.z.w.others.timeout.test;

import java.util.Date;

/**
 * @author Wu Zhenzhen
 * @version Apr 16, 2013 11:02:10 AM
 */
public class TestPersion {

	private String test;
	private int id;
	private Date date;

	/**
	 * <br>
	 * Created on: Apr 16, 2013 11:02:32 AM
	 */
	public TestPersion() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TestPersion [test=" + test + ", id=" + id + ", date=" + date
				+ "]";
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
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + id;
		result = prime * result + ((test == null) ? 0 : test.hashCode());
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
		TestPersion other = (TestPersion) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (id != other.id)
			return false;
		if (test == null) {
			if (other.test != null)
				return false;
		} else if (!test.equals(other.test))
			return false;
		return true;
	}

	/**
	 * <br>
	 * Created on: Apr 16, 2013 11:02:37 AM
	 * 
	 * @return the test
	 */
	public String getTest() {
		return test;
	}

	/**
	 * <br>
	 * Created on: Apr 16, 2013 11:02:37 AM
	 * 
	 * @param test
	 *            the test to set
	 */
	public void setTest(String test) {
		this.test = test;
	}

	/**
	 * <br>
	 * Created on: Apr 16, 2013 11:02:37 AM
	 * 
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * <br>
	 * Created on: Apr 16, 2013 11:02:37 AM
	 * 
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * <br>
	 * Created on: Apr 16, 2013 11:02:37 AM
	 * 
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * <br>
	 * Created on: Apr 16, 2013 11:02:37 AM
	 * 
	 * @param date
	 *            the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

}
