/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.test.model.VeiwRSS.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-11-9 下午09:52:53 
 *   LastChange: 2013-11-9 下午09:52:53 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.test.model;

/**
 * z.z.w.project.test.model.VeiwRSS.java
 */
public class VeiwRSS {

	private String vcRNote;
	private String vcSNote;
	private String vcSSNote;

	/**
	 * <br>
	 * Created on: 2013-11-9 下午09:54:33
	 */
	public VeiwRSS() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "VeiwRSS [vcRNote=" + vcRNote + ", vcSNote=" + vcSNote
				+ ", vcSSNote=" + vcSSNote + "]";
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
		result = prime * result + ((vcRNote == null) ? 0 : vcRNote.hashCode());
		result = prime * result + ((vcSNote == null) ? 0 : vcSNote.hashCode());
		result = prime * result
				+ ((vcSSNote == null) ? 0 : vcSSNote.hashCode());
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
		VeiwRSS other = (VeiwRSS) obj;
		if (vcRNote == null) {
			if (other.vcRNote != null)
				return false;
		} else if (!vcRNote.equals(other.vcRNote))
			return false;
		if (vcSNote == null) {
			if (other.vcSNote != null)
				return false;
		} else if (!vcSNote.equals(other.vcSNote))
			return false;
		if (vcSSNote == null) {
			if (other.vcSSNote != null)
				return false;
		} else if (!vcSSNote.equals(other.vcSSNote))
			return false;
		return true;
	}

	/**
	 * <br>
	 * Created on: 2013-11-9 下午09:54:40
	 * 
	 * @return the vcRNote
	 */
	public String getVcRNote() {
		return vcRNote;
	}

	/**
	 * <br>
	 * Created on: 2013-11-9 下午09:54:40
	 * 
	 * @return the vcSNote
	 */
	public String getVcSNote() {
		return vcSNote;
	}

	/**
	 * <br>
	 * Created on: 2013-11-9 下午09:54:40
	 * 
	 * @return the vcSSNote
	 */
	public String getVcSSNote() {
		return vcSSNote;
	}

	/**
	 * <br>
	 * Created on: 2013-11-9 下午09:54:40
	 * 
	 * @param vcRNote
	 *            the vcRNote to set
	 */
	public void setVcRNote(String vcRNote) {
		this.vcRNote = vcRNote;
	}

	/**
	 * <br>
	 * Created on: 2013-11-9 下午09:54:40
	 * 
	 * @param vcSNote
	 *            the vcSNote to set
	 */
	public void setVcSNote(String vcSNote) {
		this.vcSNote = vcSNote;
	}

	/**
	 * <br>
	 * Created on: 2013-11-9 下午09:54:40
	 * 
	 * @param vcSSNote
	 *            the vcSSNote to set
	 */
	public void setVcSSNote(String vcSSNote) {
		this.vcSSNote = vcSSNote;
	}

}
