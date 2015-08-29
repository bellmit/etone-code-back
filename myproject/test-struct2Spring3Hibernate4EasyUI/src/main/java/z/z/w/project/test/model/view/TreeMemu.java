/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.test.model.view.VeiwMemu.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-11-3 下午05:11:54 
 *   LastChange: 2013-11-3 下午05:11:54 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.test.model.view;

import java.io.Serializable;
import java.util.Map;

/**
 * z.z.w.project.test.model.view.VeiwMemu.java
 */
public class TreeMemu implements Serializable {

	/**
	 * <br>
	 * Created on: 2013-11-3 下午05:12:04
	 */
	private static final long serialVersionUID = 7681609875212178768L;

	private String id;

	private String vcTitle;
	private String state;

	private String pid;

	private Map<String, Object> attributes;

	/**
	 * <br>
	 * Created on: 2013-11-3 下午10:54:28
	 */
	public TreeMemu() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TreeMemu [id=" + id + ", vcTitle=" + vcTitle + ", state="
				+ state + ", pid=" + pid + ", attributes=" + attributes + "]";
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
				+ ((attributes == null) ? 0 : attributes.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((pid == null) ? 0 : pid.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result + ((vcTitle == null) ? 0 : vcTitle.hashCode());
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
		TreeMemu other = (TreeMemu) obj;
		if (attributes == null) {
			if (other.attributes != null)
				return false;
		} else if (!attributes.equals(other.attributes))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (pid == null) {
			if (other.pid != null)
				return false;
		} else if (!pid.equals(other.pid))
			return false;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		if (vcTitle == null) {
			if (other.vcTitle != null)
				return false;
		} else if (!vcTitle.equals(other.vcTitle))
			return false;
		return true;
	}

	/**
	 * <br>
	 * Created on: 2013-11-3 下午10:54:38
	 * 
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * <br>
	 * Created on: 2013-11-3 下午10:54:38
	 * 
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * <br>
	 * Created on: 2013-11-3 下午10:54:38
	 * 
	 * @return the vcTitle
	 */
	public String getVcTitle() {
		return vcTitle;
	}

	/**
	 * <br>
	 * Created on: 2013-11-3 下午10:54:38
	 * 
	 * @param vcTitle
	 *            the vcTitle to set
	 */
	public void setVcTitle(String vcTitle) {
		this.vcTitle = vcTitle;
	}

	/**
	 * <br>
	 * Created on: 2013-11-3 下午10:54:38
	 * 
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * <br>
	 * Created on: 2013-11-3 下午10:54:38
	 * 
	 * @param state
	 *            the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * <br>
	 * Created on: 2013-11-3 下午10:54:38
	 * 
	 * @return the pid
	 */
	public String getPid() {
		return pid;
	}

	/**
	 * <br>
	 * Created on: 2013-11-3 下午10:54:38
	 * 
	 * @param pid
	 *            the pid to set
	 */
	public void setPid(String pid) {
		this.pid = pid;
	}

	/**
	 * <br>
	 * Created on: 2013-11-3 下午10:54:38
	 * 
	 * @return the attributes
	 */
	public Map<String, Object> getAttributes() {
		return attributes;
	}

	/**
	 * <br>
	 * Created on: 2013-11-3 下午10:54:38
	 * 
	 * @param attributes
	 *            the attributes to set
	 */
	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

}
