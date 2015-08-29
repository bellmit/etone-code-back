/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.spending.model.RSSSVo.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-11-17 下午09:46:28 
 *   LastChange: 2013-11-17 下午09:46:28 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.spending.model;

import java.io.Serializable;

/**
 * z.z.w.project.spending.model.RSSSVo.java
 */
public class RSSSVo implements Serializable {
	/**
	 * <br>
	 * Created on: 2013-11-18 上午10:07:01
	 */
	private static final long serialVersionUID = 6967097289674562160L;
	private char chReveExpenId;
	private Long nmSpendId;
	private Long nmSubSpendId = -1l;

	private char positiveOrNegative;
	private String vcRNote;
	private String vcSNote;
	private String vcSSNote;

	/**
	 * <br>
	 * Created on: 2013-11-18 下午03:06:04
	 */
	public RSSSVo() {
		super();
	}

	/**
	 * <br>
	 * Created on: 2013-11-22 下午04:14:41
	 * 
	 * @param vcRNote
	 * @param vcSNote
	 * @param vcSSNote
	 * @param chReveExpenId
	 * @param nmSpendId
	 * @param nmSubSpendId
	 * @param positiveOrNegative
	 */
	public RSSSVo(String vcRNote, String vcSNote, String vcSSNote, char chReveExpenId, Long nmSpendId, Long nmSubSpendId, char positiveOrNegative) {
		super();
		this.vcRNote = vcRNote;
		this.vcSNote = vcSNote;
		this.vcSSNote = vcSSNote;
		this.chReveExpenId = chReveExpenId;
		this.nmSpendId = nmSpendId;
		this.nmSubSpendId = nmSubSpendId;
		this.positiveOrNegative = positiveOrNegative;
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
		RSSSVo other = (RSSSVo) obj;
		if (chReveExpenId != other.chReveExpenId)
			return false;
		if (nmSpendId == null) {
			if (other.nmSpendId != null)
				return false;
		} else if (!nmSpendId.equals(other.nmSpendId))
			return false;
		if (nmSubSpendId == null) {
			if (other.nmSubSpendId != null)
				return false;
		} else if (!nmSubSpendId.equals(other.nmSubSpendId))
			return false;
		if (positiveOrNegative != other.positiveOrNegative)
			return false;
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
	 * Created on: 2013-11-22 下午04:15:10
	 * 
	 * @return the chReveExpenId
	 */
	public char getChReveExpenId() {
		return chReveExpenId;
	}

	/**
	 * <br>
	 * Created on: 2013-11-18 下午03:06:11
	 * 
	 * @return the nmSpendId
	 */
	public Long getNmSpendId() {
		return nmSpendId;
	}

	/**
	 * <br>
	 * Created on: 2013-11-18 下午03:06:11
	 * 
	 * @return the nmSubSpendId
	 */
	public Long getNmSubSpendId() {
		return nmSubSpendId;
	}

	/**
	 * <br>
	 * Created on: 2013-11-21 下午02:03:35
	 * 
	 * @return the positiveOrNegative
	 */
	public char getPositiveOrNegative() {
		return positiveOrNegative;
	}

	/**
	 * <br>
	 * Created on: 2013-11-18 下午03:06:11
	 * 
	 * @return the vcRNote
	 */
	public String getVcRNote() {
		return vcRNote;
	}

	/**
	 * <br>
	 * Created on: 2013-11-18 下午03:06:11
	 * 
	 * @return the vcSNote
	 */
	public String getVcSNote() {
		return vcSNote;
	}

	/**
	 * <br>
	 * Created on: 2013-11-18 下午03:06:11
	 * 
	 * @return the vcSSNote
	 */
	public String getVcSSNote() {
		return vcSSNote;
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
		result = prime * result + chReveExpenId;
		result = prime * result + ((nmSpendId == null) ? 0 : nmSpendId.hashCode());
		result = prime * result + ((nmSubSpendId == null) ? 0 : nmSubSpendId.hashCode());
		result = prime * result + positiveOrNegative;
		result = prime * result + ((vcRNote == null) ? 0 : vcRNote.hashCode());
		result = prime * result + ((vcSNote == null) ? 0 : vcSNote.hashCode());
		result = prime * result + ((vcSSNote == null) ? 0 : vcSSNote.hashCode());
		return result;
	}

	/**
	 * <br>
	 * Created on: 2013-11-22 下午04:15:10
	 * 
	 * @param chReveExpenId
	 *            the chReveExpenId to set
	 */
	public void setChReveExpenId(char chReveExpenId) {
		this.chReveExpenId = chReveExpenId;
	}

	/**
	 * <br>
	 * Created on: 2013-11-18 下午03:06:11
	 * 
	 * @param nmSpendId
	 *            the nmSpendId to set
	 */
	public void setNmSpendId(Long nmSpendId) {
		this.nmSpendId = nmSpendId;
	}

	/**
	 * <br>
	 * Created on: 2013-11-18 下午03:06:11
	 * 
	 * @param nmSubSpendId
	 *            the nmSubSpendId to set
	 */
	public void setNmSubSpendId(Long nmSubSpendId) {
		this.nmSubSpendId = nmSubSpendId;
	}

	/**
	 * <br>
	 * Created on: 2013-11-21 下午02:03:35
	 * 
	 * @param positiveOrNegative
	 *            the positiveOrNegative to set
	 */
	public void setPositiveOrNegative(char positiveOrNegative) {
		this.positiveOrNegative = positiveOrNegative;
	}

	/**
	 * <br>
	 * Created on: 2013-11-18 下午03:06:11
	 * 
	 * @param vcRNote
	 *            the vcRNote to set
	 */
	public void setVcRNote(String vcRNote) {
		this.vcRNote = vcRNote;
	}

	/**
	 * <br>
	 * Created on: 2013-11-18 下午03:06:11
	 * 
	 * @param vcSNote
	 *            the vcSNote to set
	 */
	public void setVcSNote(String vcSNote) {
		this.vcSNote = vcSNote;
	}

	/**
	 * <br>
	 * Created on: 2013-11-18 下午03:06:11
	 * 
	 * @param vcSSNote
	 *            the vcSSNote to set
	 */
	public void setVcSSNote(String vcSSNote) {
		this.vcSSNote = vcSSNote;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "RSSSVo [vcRNote=" + vcRNote + ", vcSNote=" + vcSNote + ", vcSSNote=" + vcSSNote + ", chReveExpenId=" + chReveExpenId + ", nmSpendId=" + nmSpendId + ", nmSubSpendId=" + nmSubSpendId + ", positiveOrNegative=" + positiveOrNegative + "]";
	}

}
