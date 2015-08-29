/**
 * z.z.w.vo.AdapterType.java
 */
package z.z.w.vo;

/**
 * @author Wu Zhenzhen
 * @version 2013-8-20 下午07:44:18
 */
public class AdapterType {
	private String name = "";
	private String className = "";

	/**
	 * <br>
	 * Created on: 2013-8-20 下午07:44:28
	 */
	public AdapterType() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AdapterType [name=" + name + ", className=" + className + "]";
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
				+ ((className == null) ? 0 : className.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		AdapterType other = (AdapterType) obj;
		if (className == null) {
			if (other.className != null)
				return false;
		} else if (!className.equals(other.className))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	/**
	 * <br>
	 * Created on: 2013-8-20 下午07:44:35
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * <br>
	 * Created on: 2013-8-20 下午07:44:35
	 * 
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * <br>
	 * Created on: 2013-8-20 下午07:44:35
	 * 
	 * @return the className
	 */
	public String getClassName() {
		return className;
	}

	/**
	 * <br>
	 * Created on: 2013-8-20 下午07:44:35
	 * 
	 * @param className
	 *            the className to set
	 */
	public void setClassName(String className) {
		this.className = className;
	}

}
