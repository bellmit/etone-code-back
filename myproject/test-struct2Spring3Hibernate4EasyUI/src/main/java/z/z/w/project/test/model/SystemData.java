/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.test.model.SystemData.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-11-4 下午04:18:29 
 *   LastChange: 2013-11-4 下午04:18:29 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.test.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * z.z.w.project.test.model.SystemData.java
 */
@Entity
@Table(name = "systemdata", schema = "")
public class SystemData {

	private int id;
	private Date dataDate;
	private String shouzhi;
	private String jiezhi;
	private String leixing;
	private String xingzhi;
	private Float money;
	private String note;

	/**
	 * <br>
	 * Created on: 2013-11-4 下午08:23:45
	 */
	public SystemData() {
		super();
	}

	/**
	 * <br>
	 * Created on: 2013-11-4 下午08:25:12
	 * 
	 * @return the id
	 */
	@Id
	@Column
	public int getId() {
		return id;
	}

	/**
	 * <br>
	 * Created on: 2013-11-4 下午08:25:12
	 * 
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * <br>
	 * Created on: 2013-11-4 下午08:25:12
	 * 
	 * @return the shouzhi
	 */
	public String getShouzhi() {
		return shouzhi;
	}

	/**
	 * <br>
	 * Created on: 2013-11-4 下午08:25:12
	 * 
	 * @param shouzhi
	 *            the shouzhi to set
	 */
	public void setShouzhi(String shouzhi) {
		this.shouzhi = shouzhi;
	}

	/**
	 * <br>
	 * Created on: 2013-11-4 下午08:25:12
	 * 
	 * @return the jiezhi
	 */
	public String getJiezhi() {
		return jiezhi;
	}

	/**
	 * <br>
	 * Created on: 2013-11-4 下午08:25:12
	 * 
	 * @param jiezhi
	 *            the jiezhi to set
	 */
	public void setJiezhi(String jiezhi) {
		this.jiezhi = jiezhi;
	}

	/**
	 * <br>
	 * Created on: 2013-11-4 下午08:25:12
	 * 
	 * @return the leixing
	 */
	public String getLeixing() {
		return leixing;
	}

	/**
	 * <br>
	 * Created on: 2013-11-4 下午08:25:12
	 * 
	 * @param leixing
	 *            the leixing to set
	 */
	public void setLeixing(String leixing) {
		this.leixing = leixing;
	}

	/**
	 * <br>
	 * Created on: 2013-11-4 下午08:25:12
	 * 
	 * @return the xingzhi
	 */
	public String getXingzhi() {
		return xingzhi;
	}

	/**
	 * <br>
	 * Created on: 2013-11-4 下午08:25:12
	 * 
	 * @param xingzhi
	 *            the xingzhi to set
	 */
	public void setXingzhi(String xingzhi) {
		this.xingzhi = xingzhi;
	}

	/**
	 * <br>
	 * Created on: 2013-11-4 下午08:25:12
	 * 
	 * @return the money
	 */
	public Float getMoney() {
		return money;
	}

	/**
	 * <br>
	 * Created on: 2013-11-4 下午08:25:12
	 * 
	 * @param money
	 *            the money to set
	 */
	public void setMoney(Float money) {
		this.money = money;
	}

	/**
	 * <br>
	 * Created on: 2013-11-4 下午08:25:12
	 * 
	 * @return the note
	 */
	public String getNote() {
		return note;
	}

	/**
	 * <br>
	 * Created on: 2013-11-4 下午08:25:12
	 * 
	 * @param note
	 *            the note to set
	 */
	public void setNote(String note) {
		this.note = note;
	}

	/**
	 * <br>
	 * Created on: 2013-11-4 下午08:33:32
	 * 
	 * @return the dataDate
	 */
	public Date getDataDate() {
		return dataDate;
	}

	/**
	 * <br>
	 * Created on: 2013-11-4 下午08:33:32
	 * 
	 * @param dataDate
	 *            the dataDate to set
	 */
	public void setDataDate(Date dataDate) {
		this.dataDate = dataDate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SystemData [id=" + id + ", dataDate=" + dataDate + ", shouzhi="
				+ shouzhi + ", jiezhi=" + jiezhi + ", leixing=" + leixing
				+ ", xingzhi=" + xingzhi + ", money=" + money + ", note="
				+ note + "]";
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
				+ ((dataDate == null) ? 0 : dataDate.hashCode());
		result = prime * result + id;
		result = prime * result + ((jiezhi == null) ? 0 : jiezhi.hashCode());
		result = prime * result + ((leixing == null) ? 0 : leixing.hashCode());
		result = prime * result + ((money == null) ? 0 : money.hashCode());
		result = prime * result + ((note == null) ? 0 : note.hashCode());
		result = prime * result + ((shouzhi == null) ? 0 : shouzhi.hashCode());
		result = prime * result + ((xingzhi == null) ? 0 : xingzhi.hashCode());
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
		SystemData other = (SystemData) obj;
		if (dataDate == null) {
			if (other.dataDate != null)
				return false;
		} else if (!dataDate.equals(other.dataDate))
			return false;
		if (id != other.id)
			return false;
		if (jiezhi == null) {
			if (other.jiezhi != null)
				return false;
		} else if (!jiezhi.equals(other.jiezhi))
			return false;
		if (leixing == null) {
			if (other.leixing != null)
				return false;
		} else if (!leixing.equals(other.leixing))
			return false;
		if (money == null) {
			if (other.money != null)
				return false;
		} else if (!money.equals(other.money))
			return false;
		if (note == null) {
			if (other.note != null)
				return false;
		} else if (!note.equals(other.note))
			return false;
		if (shouzhi == null) {
			if (other.shouzhi != null)
				return false;
		} else if (!shouzhi.equals(other.shouzhi))
			return false;
		if (xingzhi == null) {
			if (other.xingzhi != null)
				return false;
		} else if (!xingzhi.equals(other.xingzhi))
			return false;
		return true;
	}

}
