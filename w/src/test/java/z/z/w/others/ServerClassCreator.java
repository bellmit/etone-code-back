/**
 * com.etone.nadap.server.global.ServerClassCreator.java
 */
package z.z.w.others;

/**
 * @author Wu Zhenzhen
 * @version Dec 3, 2012 3:27:14 PM
 */
public class ServerClassCreator {
	private String className;
	private String serverName;

	/**
	 * <br>
	 * Created on: Dec 3, 2012 3:27:20 PM
	 * 
	 * @return the className
	 */
	public String getClassName() {
		return className;
	}

	/**
	 * <br>
	 * Created on: Dec 3, 2012 3:27:20 PM
	 * 
	 * @param className
	 *            the className to set
	 */
	public void setClassName(String className) {
		this.className = className;
	}

	/**
	 * <br>
	 * Created on: Dec 3, 2012 3:27:20 PM
	 * 
	 * @return the serverName
	 */
	public String getServerName() {
		return serverName;
	}

	/**
	 * <br>
	 * Created on: Dec 3, 2012 3:27:20 PM
	 * 
	 * @param serverName
	 *            the serverName to set
	 */
	public void setServerName(String serverName) {
		this.serverName = serverName;
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
		ServerClassCreator other = (ServerClassCreator) obj;
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
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ServerClassCreator [className=" + className + ", serverName="
				+ serverName + "]";
	}

	/**
	 * <br>
	 * Created on: Dec 3, 2012 3:27:38 PM
	 */
	public ServerClassCreator() {
		super();
	}
}
