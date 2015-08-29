/**
 * z.z.w.vo.Field.java
 */
package z.z.w.vo;

/**
 * @author Wu Zhenzhen
 * @version 2013-8-20 下午08:33:37
 */
public class Field {
	private Integer index;
	private String name;
	private Integer srcindex;

	/**
	 * <br>
	 * Created on: 2013-8-20 下午08:33:44
	 */
	public Field() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Field [index=" + index + ", name=" + name + ", srcindex="
				+ srcindex + "]";
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
		result = prime * result + ((index == null) ? 0 : index.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((srcindex == null) ? 0 : srcindex.hashCode());
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
		Field other = (Field) obj;
		if (index == null) {
			if (other.index != null)
				return false;
		} else if (!index.equals(other.index))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (srcindex == null) {
			if (other.srcindex != null)
				return false;
		} else if (!srcindex.equals(other.srcindex))
			return false;
		return true;
	}

	/**
	 * <br>
	 * Created on: 2013-8-20 下午08:33:50
	 * 
	 * @return the index
	 */
	public Integer getIndex() {
		return index;
	}

	/**
	 * <br>
	 * Created on: 2013-8-20 下午08:33:50
	 * 
	 * @param index
	 *            the index to set
	 */
	public void setIndex(Integer index) {
		this.index = index;
	}

	/**
	 * <br>
	 * Created on: 2013-8-20 下午08:33:50
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * <br>
	 * Created on: 2013-8-20 下午08:33:50
	 * 
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * <br>
	 * Created on: 2013-8-20 下午08:33:50
	 * 
	 * @return the srcindex
	 */
	public Integer getSrcindex() {
		return srcindex;
	}

	/**
	 * <br>
	 * Created on: 2013-8-20 下午08:33:50
	 * 
	 * @param srcindex
	 *            the srcindex to set
	 */
	public void setSrcindex(Integer srcindex) {
		this.srcindex = srcindex;
	}

}
