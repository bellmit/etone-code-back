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
package z.z.w.project.test.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
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

	private int intReveExpenId;

	private Set<TbSpending> tbSpendings = new HashSet<TbSpending>();
	private String vcNote;

	/**
	 * <br>
	 * Created on: 2013-11-9 下午08:41:50
	 */
	public TbReveExpen() {
		super();
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
		if (intReveExpenId != other.intReveExpenId)
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
	 * Created on: 2013-11-9 下午08:41:38
	 * 
	 * @return the intReveExpenId
	 */
	@Id
	@GeneratedValue
	public int getIntReveExpenId() {
		return intReveExpenId;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + intReveExpenId;
		result = prime * result + ((vcNote == null) ? 0 : vcNote.hashCode());
		return result;
	}

	/**
	 * <br>
	 * Created on: 2013-11-9 下午08:41:38
	 * 
	 * @param intReveExpenId
	 *            the intReveExpenId to set
	 */
	public void setIntReveExpenId(int intReveExpenId) {
		this.intReveExpenId = intReveExpenId;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TbReveExpen [intReveExpenId=" + intReveExpenId + ", vcNote="
				+ vcNote + "]";
	}
}
