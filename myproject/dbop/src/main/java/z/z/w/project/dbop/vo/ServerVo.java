/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.dbop.ServerVo.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-9-10 上午11:22:43 
 *   LastChange: 2013-9-10 上午11:22:43 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.dbop.vo;

/**
 * z.z.w.project.dbop.ServerVo.java
 */
public class ServerVo {

	// vcIp,vcUrl,vcsIp,vcsUrl
	private String vcIp;
	private String vcsIp;
	private String vcUrl;
	private String vcsUrl;

	/**
	 * <br>
	 * Created on: 2013-9-10 上午11:23:17
	 */
	public ServerVo() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ServerVo [vcIp=" + vcIp + ", vcsIp=" + vcsIp + ", vcUrl="
				+ vcUrl + ", vcsUrl=" + vcsUrl + "]";
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
		result = prime * result + ((vcIp == null) ? 0 : vcIp.hashCode());
		result = prime * result + ((vcUrl == null) ? 0 : vcUrl.hashCode());
		result = prime * result + ((vcsIp == null) ? 0 : vcsIp.hashCode());
		result = prime * result + ((vcsUrl == null) ? 0 : vcsUrl.hashCode());
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
		ServerVo other = (ServerVo) obj;
		if (vcIp == null) {
			if (other.vcIp != null)
				return false;
		} else if (!vcIp.equals(other.vcIp))
			return false;
		if (vcUrl == null) {
			if (other.vcUrl != null)
				return false;
		} else if (!vcUrl.equals(other.vcUrl))
			return false;
		if (vcsIp == null) {
			if (other.vcsIp != null)
				return false;
		} else if (!vcsIp.equals(other.vcsIp))
			return false;
		if (vcsUrl == null) {
			if (other.vcsUrl != null)
				return false;
		} else if (!vcsUrl.equals(other.vcsUrl))
			return false;
		return true;
	}

	/**
	 * <br>
	 * Created on: 2013-9-10 上午11:23:22
	 * 
	 * @return the vcIp
	 */
	public String getVcIp() {
		return vcIp;
	}

	/**
	 * <br>
	 * Created on: 2013-9-10 上午11:23:22
	 * 
	 * @param vcIp
	 *            the vcIp to set
	 */
	public void setVcIp(String vcIp) {
		this.vcIp = vcIp;
	}

	/**
	 * <br>
	 * Created on: 2013-9-10 上午11:23:22
	 * 
	 * @return the vcsIp
	 */
	public String getVcsIp() {
		return vcsIp;
	}

	/**
	 * <br>
	 * Created on: 2013-9-10 上午11:23:22
	 * 
	 * @param vcsIp
	 *            the vcsIp to set
	 */
	public void setVcsIp(String vcsIp) {
		this.vcsIp = vcsIp;
	}

	/**
	 * <br>
	 * Created on: 2013-9-10 上午11:23:22
	 * 
	 * @return the vcUrl
	 */
	public String getVcUrl() {
		return vcUrl;
	}

	/**
	 * <br>
	 * Created on: 2013-9-10 上午11:23:22
	 * 
	 * @param vcUrl
	 *            the vcUrl to set
	 */
	public void setVcUrl(String vcUrl) {
		this.vcUrl = vcUrl;
	}

	/**
	 * <br>
	 * Created on: 2013-9-10 上午11:23:22
	 * 
	 * @return the vcsUrl
	 */
	public String getVcsUrl() {
		return vcsUrl;
	}

	/**
	 * <br>
	 * Created on: 2013-9-10 上午11:23:22
	 * 
	 * @param vcsUrl
	 *            the vcsUrl to set
	 */
	public void setVcsUrl(String vcsUrl) {
		this.vcsUrl = vcsUrl;
	}

}
