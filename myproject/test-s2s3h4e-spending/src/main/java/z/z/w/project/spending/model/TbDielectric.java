/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.test.model.TbDielectric.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-11-12 上午09:51:06 
 *   LastChange: 2013-11-12 上午09:51:06 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.spending.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * z.z.w.project.test.model.TbDielectric.java
 */
@Entity
public class TbDielectric implements Serializable {

	/**
	 * <br>
	 * Created on: 2013-11-12 上午09:52:46
	 */
	private static final long serialVersionUID = 7903420522157445603L;
	private int intDielectricId;
	private float nmBalances;
	private String vcNote;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + intDielectricId;
		result = prime * result + Float.floatToIntBits(nmBalances);
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
		TbDielectric other = (TbDielectric) obj;
		if (intDielectricId != other.intDielectricId)
			return false;
		if (Float.floatToIntBits(nmBalances) != Float.floatToIntBits(other.nmBalances))
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
	 * Created on: 2013-11-12 上午09:51:51
	 * 
	 * @return the intDielectricId
	 */
	@Id
	@GeneratedValue
	@Column(insertable = false, unique = true)
	public int getIntDielectricId() {
		return intDielectricId;
	}

	/**
	 * <br>
	 * Created on: 2013-11-12 上午10:43:11
	 * 
	 * @return the nmBalances
	 */
	@Column(nullable = false)
	public float getNmBalances() {
		return nmBalances;
	}

	/**
	 * <br>
	 * Created on: 2013-11-12 上午09:51:51
	 * 
	 * @return the vcNote
	 */
	@Column(unique = true, nullable = false)
	public String getVcNote() {
		return vcNote;
	}

	/**
	 * <br>
	 * Created on: 2013-11-12 上午09:51:51
	 * 
	 * @param intDielectricId
	 *            the intDielectricId to set
	 */
	public void setIntDielectricId(int intDielectricId) {
		this.intDielectricId = intDielectricId;
	}

	/**
	 * <br>
	 * Created on: 2013-11-12 上午10:43:11
	 * 
	 * @param nmBalances
	 *            the nmBalances to set
	 */
	public void setNmBalances(float nmBalances) {
		this.nmBalances = nmBalances;
	}

	/**
	 * <br>
	 * Created on: 2013-11-12 上午09:51:51
	 * 
	 * @param vcNote
	 *            the vcNote to set
	 */
	public void setVcNote(String vcNote) {
		this.vcNote = vcNote;
	}

}
