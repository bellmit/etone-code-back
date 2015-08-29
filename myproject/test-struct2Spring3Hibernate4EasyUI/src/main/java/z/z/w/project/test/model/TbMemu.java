/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.test.model.TbMemu.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-11-3 下午12:26:56 
 *   LastChange: 2013-11-3 下午12:26:56 
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
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * z.z.w.project.test.model.TbMemu.java
 */

@Entity
@Table(name = "tbmemu", schema = "")
public class TbMemu implements Serializable {

	/**
	 * <br>
	 * Created on: 2013-11-3 下午01:55:32
	 */
	private static final long serialVersionUID = -545024478553319808L;
	private String id = "0";
	private int intMonth = 0;
	private int intYear = 0;
	private TbMemu tbMemu = null;
	private Set<TbMemu> tbMemus = new HashSet<TbMemu>(0);
	private String vcTitle = "";

	private String vcUrl = "";

	/**
	 * <br>
	 * Created on: 2013-11-3 下午01:50:41
	 */
	public TbMemu() {
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
		TbMemu other = (TbMemu) obj;
		if (id != other.id)
			return false;
		if (intMonth != other.intMonth)
			return false;
		if (intYear != other.intYear)
			return false;
		if (tbMemu == null) {
			if (other.tbMemu != null)
				return false;
		} else if (!tbMemu.equals(other.tbMemu))
			return false;
		if (tbMemus == null) {
			if (other.tbMemus != null)
				return false;
		} else if (!tbMemus.equals(other.tbMemus))
			return false;
		if (vcTitle == null) {
			if (other.vcTitle != null)
				return false;
		} else if (!vcTitle.equals(other.vcTitle))
			return false;
		if (vcUrl == null) {
			if (other.vcUrl != null)
				return false;
		} else if (!vcUrl.equals(other.vcUrl))
			return false;
		return true;
	}

	/**
	 * <br>
	 * Created on: 2013-11-3 下午03:48:00
	 * 
	 * @return the id
	 */
	@Id
	@Column
	public String getId() {
		return id;
	}

	/**
	 * <br>
	 * Created on: 2013-11-3 下午01:50:47
	 * 
	 * @return the intMonth
	 */
	@Column
	public int getIntMonth() {
		return intMonth;
	}

	/**
	 * <br>
	 * Created on: 2013-11-3 下午01:50:47
	 * 
	 * @return the intYear
	 */
	@Column
	public int getIntYear() {
		return intYear;
	}

	/**
	 * <br>
	 * Created on: 2013-11-3 下午01:50:47
	 * 
	 * @return the tbMemu
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PID")
	public TbMemu getTbMemu() {
		return tbMemu;
	}

	/**
	 * <br>
	 * Created on: 2013-11-3 下午01:50:47
	 * 
	 * @return the tbMemus
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "tbMemu")
	public Set<TbMemu> getTbMemus() {
		return tbMemus;
	}

	/**
	 * <br>
	 * Created on: 2013-11-3 下午01:50:47
	 * 
	 * @return the vcTitle
	 */
	@Column
	public String getVcTitle() {
		return vcTitle;
	}

	/**
	 * <br>
	 * Created on: 2013-11-3 下午01:50:47
	 * 
	 * @return the vcUrl
	 */
	@Column
	public String getVcUrl() {
		return vcUrl;
	}

	/**
	 * <br>
	 * Created on: 2013-11-3 下午03:48:00
	 * 
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * <br>
	 * Created on: 2013-11-3 下午01:50:47
	 * 
	 * @param intMonth
	 *            the intMonth to set
	 */
	public void setIntMonth(int intMonth) {
		this.intMonth = intMonth;
	}

	/**
	 * <br>
	 * Created on: 2013-11-3 下午01:50:47
	 * 
	 * @param intYear
	 *            the intYear to set
	 */
	public void setIntYear(int intYear) {
		this.intYear = intYear;
	}

	/**
	 * <br>
	 * Created on: 2013-11-3 下午01:50:47
	 * 
	 * @param tbMemu
	 *            the tbMemu to set
	 */
	public void setTbMemu(TbMemu tbMemu) {
		this.tbMemu = tbMemu;
	}

	/**
	 * <br>
	 * Created on: 2013-11-3 下午01:50:47
	 * 
	 * @param tbMemus
	 *            the tbMemus to set
	 */
	public void setTbMemus(Set<TbMemu> tbMemus) {
		this.tbMemus = tbMemus;
	}

	/**
	 * <br>
	 * Created on: 2013-11-3 下午01:50:47
	 * 
	 * @param vcTitle
	 *            the vcTitle to set
	 */
	public void setVcTitle(String vcTitle) {
		this.vcTitle = vcTitle;
	}

	/**
	 * <br>
	 * Created on: 2013-11-3 下午01:50:47
	 * 
	 * @param vcUrl
	 *            the vcUrl to set
	 */
	public void setVcUrl(String vcUrl) {
		this.vcUrl = vcUrl;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TbMemu [id=" + id + ", tbMemu=" + tbMemu + ", vcUrl=" + vcUrl
				+ ", vcTitle=" + vcTitle + ", intYear=" + intYear
				+ ", intMonth=" + intMonth + ", tbMemus=" + tbMemus + "]";
	}

}
