/**************************************************************************
 *     FileName: ServerConfigVo.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-06-17 00:04:46
 *   LastChange: 2013-06-17 00:04:46
 *      History:
 **************************************************************************/
package z.z.w.vo;

/**
 * 
 * @author Wu Zhenzhen
 * @version 2013-8-20 下午07:42:56
 * @deprecated <code>z.z.w.vo.ServerAdapter</code>
 */
public class ServerConfigVo {

	private String serverName;
	private String className = "";
	private int threadNum;

	/**
	 * <br>
	 * Created on: May 31, 2013 11:18:50 AM
	 */
	public ServerConfigVo() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ServerConfigVo [serverName=" + serverName + ", className="
				+ className + ", threadNum=" + threadNum + "]";
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
		result = prime * result
				+ ((serverName == null) ? 0 : serverName.hashCode());
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
		ServerConfigVo other = (ServerConfigVo) obj;
		if (className == null) {
			if (other.className != null)
				return false;
		} else if (!className.equals(other.className))
			return false;
		if (serverName == null) {
			if (other.serverName != null)
				return false;
		} else if (!serverName.equals(other.serverName))
			return false;
		if (threadNum != other.threadNum)
			return false;
		return true;
	}

	/**
	 * <br>
	 * Created on: May 31, 2013 11:18:57 AM
	 * 
	 * @return the serverName
	 */
	public String getServerName() {
		return serverName;
	}

	/**
	 * <br>
	 * Created on: May 31, 2013 11:18:57 AM
	 * 
	 * @param serverName
	 *            the serverName to set
	 */
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	/**
	 * <br>
	 * Created on: May 31, 2013 11:18:57 AM
	 * 
	 * @return the className
	 */
	public String getClassName() {
		return className;
	}

	/**
	 * <br>
	 * Created on: May 31, 2013 11:18:57 AM
	 * 
	 * @param className
	 *            the className to set
	 */
	public void setClassName(String className) {
		this.className = className;
	}

	/**
	 * <br>
	 * Created on: May 31, 2013 11:18:57 AM
	 * 
	 * @return the threadNum
	 */
	public int getThreadNum() {
		return threadNum;
	}

	/**
	 * <br>
	 * Created on: May 31, 2013 11:18:57 AM
	 * 
	 * @param threadNum
	 *            the threadNum to set
	 */
	public void setThreadNum(int threadNum) {
		this.threadNum = threadNum;
	}

}
