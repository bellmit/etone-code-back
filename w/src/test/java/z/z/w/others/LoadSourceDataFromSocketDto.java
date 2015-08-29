/**
 * z.z.w.others.LoadSourceDataFromSocketDto.java
 */
package z.z.w.others;

/**
 * @author Wu Zhenzhen
 * @version Dec 3, 2012 7:08:08 PM
 */
public class LoadSourceDataFromSocketDto extends ServerClassCreator {

	private String socketServer ;
	private int socketPort;
	/**
	 * <br> Created on: Dec 3, 2012 7:08:27 PM 
	 */
	public LoadSourceDataFromSocketDto() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "LoadSourceDataFromSocketDto [socketServer=" + socketServer
				+ ", socketPort=" + socketPort + ", toString()="
				+ super.toString() + "]";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + socketPort;
		result = prime * result
				+ ((socketServer == null) ? 0 : socketServer.hashCode());
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
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		LoadSourceDataFromSocketDto other = (LoadSourceDataFromSocketDto) obj;
		if (socketPort != other.socketPort)
			return false;
		if (socketServer == null) {
			if (other.socketServer != null)
				return false;
		} else if (!socketServer.equals(other.socketServer))
			return false;
		return true;
	}

	/**
	 * <br>
	 * Created on: Dec 3, 2012 7:08:44 PM
	 * 
	 * @return the socketServer
	 */
	public String getSocketServer() {
		return socketServer;
	}

	/**
	 * <br>
	 * Created on: Dec 3, 2012 7:08:44 PM
	 * 
	 * @param socketServer
	 *            the socketServer to set
	 */
	public void setSocketServer(String socketServer) {
		this.socketServer = socketServer;
	}

	/**
	 * <br>
	 * Created on: Dec 3, 2012 7:08:44 PM
	 * 
	 * @return the socketPort
	 */
	public int getSocketPort() {
		return socketPort;
	}

	/**
	 * <br>
	 * Created on: Dec 3, 2012 7:08:44 PM
	 * 
	 * @param socketPort
	 *            the socketPort to set
	 */
	public void setSocketPort(int socketPort) {
		this.socketPort = socketPort;
	}
}
