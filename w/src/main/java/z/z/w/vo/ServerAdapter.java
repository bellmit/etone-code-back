/**
 * z.z.w.vo.ServerAdapter.java
 */
package z.z.w.vo;

import java.util.List;

/**
 * @author Wu Zhenzhen
 * @version 2013-8-20 下午07:43:44
 */
public class ServerAdapter {
	private String name = "";
	private Integer priority = -1;
	private int threadNum = 1;

	private List<AdapterType> adapterTypeList = null;

	/**
	 * <br>
	 * Created on: 2013-8-20 下午07:44:47
	 */
	public ServerAdapter() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ServerAdapter [name=" + name + ", priority=" + priority
				+ ", threadNum=" + threadNum + ", adapterTypeList="
				+ adapterTypeList + "]";
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
				+ ((adapterTypeList == null) ? 0 : adapterTypeList.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((priority == null) ? 0 : priority.hashCode());
		result = prime * result + threadNum;
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
		ServerAdapter other = (ServerAdapter) obj;
		if (adapterTypeList == null) {
			if (other.adapterTypeList != null)
				return false;
		} else if (!adapterTypeList.equals(other.adapterTypeList))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (priority == null) {
			if (other.priority != null)
				return false;
		} else if (!priority.equals(other.priority))
			return false;
		if (threadNum != other.threadNum)
			return false;
		return true;
	}

	/**
	 * <br>
	 * Created on: 2013-8-20 下午07:44:53
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * <br>
	 * Created on: 2013-8-20 下午07:44:53
	 * 
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * <br>
	 * Created on: 2013-8-20 下午07:44:53
	 * 
	 * @return the priority
	 */
	public Integer getPriority() {
		return priority;
	}

	/**
	 * <br>
	 * Created on: 2013-8-20 下午07:44:53
	 * 
	 * @param priority
	 *            the priority to set
	 */
	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	/**
	 * <br>
	 * Created on: 2013-8-20 下午07:44:53
	 * 
	 * @return the threadNum
	 */
	public int getThreadNum() {
		return threadNum;
	}

	/**
	 * <br>
	 * Created on: 2013-8-20 下午07:44:53
	 * 
	 * @param threadNum
	 *            the threadNum to set
	 */
	public void setThreadNum(int threadNum) {
		this.threadNum = threadNum;
	}

	/**
	 * <br>
	 * Created on: 2013-8-20 下午07:44:53
	 * 
	 * @return the adapterTypeList
	 */
	public List<AdapterType> getAdapterTypeList() {
		return adapterTypeList;
	}

	/**
	 * <br>
	 * Created on: 2013-8-20 下午07:44:53
	 * 
	 * @param adapterTypeList
	 *            the adapterTypeList to set
	 */
	public void setAdapterTypeList(List<AdapterType> adapterTypeList) {
		this.adapterTypeList = adapterTypeList;
	}

}
