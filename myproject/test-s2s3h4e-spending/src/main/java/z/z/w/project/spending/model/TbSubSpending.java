/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.test.model.TbSubSpending.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-11-8 下午03:28:11 
 *   LastChange: 2013-11-8 下午03:28:11 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.spending.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * z.z.w.project.test.model.TbSubSpending.java
 */
@Entity
public class TbSubSpending implements Serializable {

	/**
	 * <br>
	 * Created on: 2013-11-8 下午03:36:35
	 */
	private static final long serialVersionUID = 2205236874032014837L;
	private Long nmSubSpendId;

	private TbSpending tbSpending = null;

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
		result = prime * result + ((nmSubSpendId == null) ? 0 : nmSubSpendId.hashCode());
		result = prime * result + ((tbSpending == null) ? 0 : tbSpending.hashCode());
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
		TbSubSpending other = (TbSubSpending) obj;
		if (nmSubSpendId == null) {
			if (other.nmSubSpendId != null)
				return false;
		} else if (!nmSubSpendId.equals(other.nmSubSpendId))
			return false;
		if (tbSpending == null) {
			if (other.tbSpending != null)
				return false;
		} else if (!tbSpending.equals(other.tbSpending))
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
	 * Created on: 2013-11-8 下午09:15:29
	 */
	public TbSubSpending() {
		super();
	}

	/**
	 * <br>
	 * Created on: 2013-11-8 下午09:15:37
	 * 
	 * @return the nmSubSpendId
	 */
	@Id
	@GeneratedValue
	@Column(insertable = false, unique = true)
	public Long getNmSubSpendId() {
		return nmSubSpendId;
	}

	/**
	 * 數據關係在多的一方。數據庫表中也是在多的一方。 <br>
	 * 多的一方主導關係 <br>
	 * cascade = CascadeType.ALL級聯操作 <br>
	 * 默認多的一方可以檢索出一的一方<br>
	 * fetch = FetchType.EAGER默認是EAGER的 <br>
	 * <br>
	 * Created on: 2013-11-8 下午09:42:28
	 * 
	 * @return the tbSpending
	 */
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "nmSpendId", nullable = false)
	public TbSpending getTbSpending() {
		return tbSpending;
	}

	/**
	 * <br>
	 * Created on: 2013-11-8 下午09:15:37
	 * 
	 * @return the vcNote
	 */
	@Column
	public String getVcNote() {
		return vcNote;
	}

	/**
	 * <br>
	 * Created on: 2013-11-8 下午09:15:37
	 * 
	 * @param nmSubSpendId
	 *            the nmSubSpendId to set
	 */
	public void setNmSubSpendId(Long nmSubSpendId) {
		this.nmSubSpendId = nmSubSpendId;
	}

	/**
	 * <br>
	 * Created on: 2013-11-8 下午09:42:28
	 * 
	 * @param tbSpending
	 *            the tbSpending to set
	 */
	public void setTbSpending(TbSpending tbSpending) {
		this.tbSpending = tbSpending;
	}

	/**
	 * <br>
	 * Created on: 2013-11-8 下午09:15:37
	 * 
	 * @param vcNote
	 *            the vcNote to set
	 */
	public void setVcNote(String vcNote) {
		this.vcNote = vcNote;
	}

}
