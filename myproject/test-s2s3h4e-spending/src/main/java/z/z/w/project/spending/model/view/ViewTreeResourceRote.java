/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.spending.model.view.ViewTreeResourceRote.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-11-12 下午03:50:37 
 *   LastChange: 2013-11-12 下午03:50:37 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.spending.model.view;

import java.util.Map;

/**
 * z.z.w.project.spending.model.view.ViewTreeResourceRote.java
 */
public class ViewTreeResourceRote {

	private long id;
	private String vcNote;
	private String vcRotePage;
	private long intPRRId;

	private Map<String, Object> attributes;

	/**
	 * <br>
	 * Created on: 2013-11-12 下午04:05:10
	 */
	public ViewTreeResourceRote() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ViewTreeResourceRote [id=" + id + ", vcNote=" + vcNote
				+ ", vcRotePage=" + vcRotePage + ", intPRRId=" + intPRRId
				+ ", attributes=" + attributes + "]";
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
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + (int) (intPRRId ^ (intPRRId >>> 32));
		result = prime * result + ((vcNote == null) ? 0 : vcNote.hashCode());
		result = prime * result
				+ ((vcRotePage == null) ? 0 : vcRotePage.hashCode());
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
		ViewTreeResourceRote other = (ViewTreeResourceRote) obj;
		if (attributes == null) {
			if (other.attributes != null)
				return false;
		} else if (!attributes.equals(other.attributes))
			return false;
		if (id != other.id)
			return false;
		if (intPRRId != other.intPRRId)
			return false;
		if (vcNote == null) {
			if (other.vcNote != null)
				return false;
		} else if (!vcNote.equals(other.vcNote))
			return false;
		if (vcRotePage == null) {
			if (other.vcRotePage != null)
				return false;
		} else if (!vcRotePage.equals(other.vcRotePage))
			return false;
		return true;
	}

	/**
	 * <br>
	 * Created on: 2013-11-12 下午04:05:14
	 * 
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * <br>
	 * Created on: 2013-11-12 下午04:05:14
	 * 
	 * @return the vcNote
	 */
	public String getVcNote() {
		return vcNote;
	}

	/**
	 * <br>
	 * Created on: 2013-11-12 下午04:05:14
	 * 
	 * @return the vcRotePage
	 */
	public String getVcRotePage() {
		return vcRotePage;
	}

	/**
	 * <br>
	 * Created on: 2013-11-12 下午04:05:15
	 * 
	 * @return the intPRRId
	 */
	public long getIntPRRId() {
		return intPRRId;
	}

	/**
	 * <br>
	 * Created on: 2013-11-12 下午04:05:15
	 * 
	 * @return the attributes
	 */
	public Map<String, Object> getAttributes() {
		return attributes;
	}

	/**
	 * <br>
	 * Created on: 2013-11-12 下午04:05:15
	 * 
	 * @param id
	 *            the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * <br>
	 * Created on: 2013-11-12 下午04:05:15
	 * 
	 * @param vcNote
	 *            the vcNote to set
	 */
	public void setVcNote(String vcNote) {
		this.vcNote = vcNote;
	}

	/**
	 * <br>
	 * Created on: 2013-11-12 下午04:05:15
	 * 
	 * @param vcRotePage
	 *            the vcRotePage to set
	 */
	public void setVcRotePage(String vcRotePage) {
		this.vcRotePage = vcRotePage;
	}

	/**
	 * <br>
	 * Created on: 2013-11-12 下午04:05:15
	 * 
	 * @param intPRRId
	 *            the intPRRId to set
	 */
	public void setIntPRRId(long intPRRId) {
		this.intPRRId = intPRRId;
	}

	/**
	 * <br>
	 * Created on: 2013-11-12 下午04:05:15
	 * 
	 * @param attributes
	 *            the attributes to set
	 */
	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

}
