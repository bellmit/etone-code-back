/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.test.model.TbReveExpen.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-11-9 下午08:40:49 
 *   LastChange: 2013-11-9 下午08:40:49 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.spending.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * z.z.w.project.test.model.TbReveExpen.java
 */
@Entity
public class TbReveExpen implements Serializable {

	/**
	 * <br>
	 * Created on: 2013-11-9 下午08:41:33
	 */
	private static final long serialVersionUID = -5931158254276210474L;

	private char chReveExpenId;

	private char positiveOrNegative;

	private Set<TbSpending> tbSpendings = new HashSet<TbSpending>();
	private String vcNote;

	/**
	 * <br>
	 * Created on: 2013-11-9 下午08:41:50
	 */
	public TbReveExpen() {
		super();
	}

	/**
	 * <br>
	 * Created on: 2013-11-22 下午04:09:48
	 * 
	 * @return the chReveExpenId
	 */
	@Id
	@Column(unique = true, nullable = false, updatable = false)
	public char getChReveExpenId() {
		return chReveExpenId;
	}

	/**
	 * <br>
	 * Created on: 2013-11-22 下午04:09:48
	 * 
	 * @param chReveExpenId
	 *            the chReveExpenId to set
	 */
	public void setChReveExpenId(char chReveExpenId) {
		this.chReveExpenId = chReveExpenId;
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
		result = prime * result + positiveOrNegative;
		result = prime * result + ((tbSpendings == null) ? 0 : tbSpendings.hashCode());
		result = prime * result + ((vcNote == null) ? 0 : vcNote.hashCode());
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
		TbReveExpen other = (TbReveExpen) obj;
		if (chReveExpenId != other.chReveExpenId)
			return false;
		if (positiveOrNegative != other.positiveOrNegative)
			return false;
		if (tbSpendings == null) {
			if (other.tbSpendings != null)
				return false;
		} else if (!tbSpendings.equals(other.tbSpendings))
			return false;
		if (vcNote == null) {
			if (other.vcNote != null)
				return false;
		} else if (!vcNote.equals(other.vcNote))
			return false;
		return true;
	}

	/**
	 * <br>
	 * Created on: 2013-11-21 上午11:54:12
	 * 
	 * @return the positiveOrNegative
	 */
	@Column(nullable = false, unique = true)
	public char getPositiveOrNegative() {
		return positiveOrNegative;
	}

	/**
	 * <br>
	 * Created on: 2013-11-9 下午08:45:05
	 * 
	 * @return the tbSpendings
	 */
	@OneToMany(mappedBy = "tbReveExpen", cascade = CascadeType.ALL)
	public Set<TbSpending> getTbSpendings() {
		return tbSpendings;
	}

	/**
	 * <br>
	 * Created on: 2013-11-9 下午08:41:42
	 * 
	 * @return the vcNote
	 */
	@Column(unique = true, nullable = false)
	public String getVcNote() {
		return vcNote;
	}

	/**
	 * <br>
	 * Created on: 2013-11-21 上午11:54:12
	 * 
	 * @param positiveOrNegative
	 *            the positiveOrNegative to set
	 */
	public void setPositiveOrNegative(char positiveOrNegative) {
		this.positiveOrNegative = positiveOrNegative;
	}

	/**
	 * <br>
	 * Created on: 2013-11-9 下午08:45:05
	 * 
	 * @param tbSpendings
	 *            the tbSpendings to set
	 */
	public void setTbSpendings(Set<TbSpending> tbSpendings) {
		this.tbSpendings = tbSpendings;
	}

	/**
	 * <br>
	 * Created on: 2013-11-9 下午08:41:42
	 * 
	 * @param vcNote
	 *            the vcNote to set
	 */
	public void setVcNote(String vcNote) {
		this.vcNote = vcNote;
	}

}
