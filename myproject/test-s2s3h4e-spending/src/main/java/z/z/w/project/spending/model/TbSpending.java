/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.test.model.TbSpending.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-11-8 下午09:39:54 
 *   LastChange: 2013-11-8 下午09:39:54 
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
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * z.z.w.project.test.model.TbSpending.java
 */
@Entity
public class TbSpending implements Serializable {

	/**
	 * <br>
	 * Created on: 2013-11-8 下午09:41:10
	 */
	private static final long serialVersionUID = -1801694955167496003L;
	private Long nmSpendId;
	private TbReveExpen tbReveExpen;

	/**
	 * 不能重寫此屬性hashcode，否則hibernate級聯操作會發生棧溢出異常
	 */
	private Set<TbSubSpending> tbSubSpendings = new HashSet<TbSubSpending>(0);

	private String vcNote;

	/**
	 * <br>
	 * Created on: 2013-11-8 下午09:40:44
	 */
	public TbSpending() {
		super();
	}

	/**
	 * <br>
	 * Created on: 2013-11-21 下午04:28:51
	 * 
	 * @param nmSpendId
	 * @param tbReveExpen
	 * @param vcNote
	 */
	public TbSpending(Long nmSpendId, TbReveExpen tbReveExpen, String vcNote) {
		super();
		this.nmSpendId = nmSpendId;
		this.tbReveExpen = tbReveExpen;
		this.vcNote = vcNote;
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
		TbSpending other = (TbSpending) obj;
		if (nmSpendId == null) {
			if (other.nmSpendId != null)
				return false;
		} else if (!nmSpendId.equals(other.nmSpendId))
			return false;
		if (tbReveExpen == null) {
			if (other.tbReveExpen != null)
				return false;
		} else if (!tbReveExpen.equals(other.tbReveExpen))
			return false;
		if (tbSubSpendings == null) {
			if (other.tbSubSpendings != null)
				return false;
		} else if (!tbSubSpendings.equals(other.tbSubSpendings))
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
	 * Created on: 2013-11-8 下午09:40:51
	 * 
	 * @return the nmSpendId
	 */
	@Id
	@GeneratedValue
	@Column(insertable = false, unique = true)
	public Long getNmSpendId() {
		return nmSpendId;
	}

	/**
	 * <br>
	 * Created on: 2013-11-9 下午08:46:36
	 * 
	 * @return the tbReveExpen
	 */
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "chReveExpenId", nullable = false)
	public TbReveExpen getTbReveExpen() {
		return tbReveExpen;
	}

	/**
	 * mappedBy被TbSubSpending類中的tbSpending屬性影射了，不用再管理這裡的關聯設置了 <br>
	 * 只要有雙向關聯，mappedBy屬性必設置 <br>
	 * LAZY加載，取一的一方時，不加載出多的一方<br>
	 * fetch = FetchType.LAZY默認是LAZY的 <br>
	 * Created on: 2013-11-8 下午09:40:51
	 * 
	 * @return the tbSubSpendings
	 */
	@OneToMany(mappedBy = "tbSpending", cascade = CascadeType.ALL)
	public Set<TbSubSpending> getTbSubSpendings() {
		return tbSubSpendings;
	}

	/**
	 * <br>
	 * Created on: 2013-11-8 下午09:40:51
	 * 
	 * @return the vcNote
	 */
	@Column(nullable = false)
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
		result = prime * result + ((nmSpendId == null) ? 0 : nmSpendId.hashCode());
		result = prime * result + ((tbReveExpen == null) ? 0 : tbReveExpen.hashCode());
		result = prime * result + ((tbSubSpendings == null) ? 0 : tbSubSpendings.hashCode());
		result = prime * result + ((vcNote == null) ? 0 : vcNote.hashCode());
		return result;
	}

	/**
	 * <br>
	 * Created on: 2013-11-8 下午09:40:51
	 * 
	 * @param nmSpendId
	 *            the nmSpendId to set
	 */
	public void setNmSpendId(Long nmSpendId) {
		this.nmSpendId = nmSpendId;
	}

	/**
	 * <br>
	 * Created on: 2013-11-9 下午08:46:36
	 * 
	 * @param tbReveExpen
	 *            the tbReveExpen to set
	 */
	public void setTbReveExpen(TbReveExpen tbReveExpen) {
		this.tbReveExpen = tbReveExpen;
	}

	/**
	 * <br>
	 * Created on: 2013-11-8 下午09:40:51
	 * 
	 * @param tbSubSpendings
	 *            the tbSubSpendings to set
	 */
	public void setTbSubSpendings(Set<TbSubSpending> tbSubSpendings) {
		this.tbSubSpendings = tbSubSpendings;
	}

	/**
	 * <br>
	 * Created on: 2013-11-8 下午09:40:51
	 * 
	 * @param vcNote
	 *            the vcNote to set
	 */
	public void setVcNote(String vcNote) {
		this.vcNote = vcNote;
	}

}
