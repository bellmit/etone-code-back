/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.test.model.view.TreeGridSystemData.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-11-4 下午04:14:13 
 *   LastChange: 2013-11-4 下午04:14:13 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.test.model.view;

import java.io.Serializable;
import java.util.Date;

/**
 * z.z.w.project.test.model.view.TreeGridSystemData.java
 */
public class TreeGridSystemData implements Serializable {

	/**
	 * <br>
	 * Created on: 2013-11-5 下午11:16:49
	 */
	private static final long serialVersionUID = 2132710899852009943L;

	private int id;
	private Date dataDate;
	private String shouzhi;
	private String jiezhi;
	private String leixing;
	private String xingzhi;
	private Float money;
	private String note;

	private int page;
	private int rows;
	private String order = "ase";
	private String sort = "dataDate";

	/**
	 * <br>
	 * Created on: 2013-11-5 下午11:15:51
	 */
	public TreeGridSystemData() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TreeGridSystemData [id=" + id + ", dataDate=" + dataDate
				+ ", shouzhi=" + shouzhi + ", jiezhi=" + jiezhi + ", leixing="
				+ leixing + ", xingzhi=" + xingzhi + ", money=" + money
				+ ", note=" + note + ", page=" + page + ", rows=" + rows
				+ ", order=" + order + ", sort=" + sort + "]";
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
		result = prime * result + ((order == null) ? 0 : order.hashCode());
		result = prime * result + page;
		result = prime * result + rows;
		result = prime * result + ((shouzhi == null) ? 0 : shouzhi.hashCode());
		result = prime * result + ((sort == null) ? 0 : sort.hashCode());
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
		TreeGridSystemData other = (TreeGridSystemData) obj;
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
		if (order == null) {
			if (other.order != null)
				return false;
		} else if (!order.equals(other.order))
			return false;
		if (page != other.page)
			return false;
		if (rows != other.rows)
			return false;
		if (shouzhi == null) {
			if (other.shouzhi != null)
				return false;
		} else if (!shouzhi.equals(other.shouzhi))
			return false;
		if (sort == null) {
			if (other.sort != null)
				return false;
		} else if (!sort.equals(other.sort))
			return false;
		if (xingzhi == null) {
			if (other.xingzhi != null)
				return false;
		} else if (!xingzhi.equals(other.xingzhi))
			return false;
		return true;
	}

	/**
	 * <br>
	 * Created on: 2013-11-5 下午11:15:59
	 * 
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * <br>
	 * Created on: 2013-11-5 下午11:15:59
	 * 
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * <br>
	 * Created on: 2013-11-5 下午11:15:59
	 * 
	 * @return the dataDate
	 */
	public Date getDataDate() {
		return dataDate;
	}

	/**
	 * <br>
	 * Created on: 2013-11-5 下午11:15:59
	 * 
	 * @param dataDate
	 *            the dataDate to set
	 */
	public void setDataDate(Date dataDate) {
		this.dataDate = dataDate;
	}

	/**
	 * <br>
	 * Created on: 2013-11-5 下午11:15:59
	 * 
	 * @return the shouzhi
	 */
	public String getShouzhi() {
		return shouzhi;
	}

	/**
	 * <br>
	 * Created on: 2013-11-5 下午11:15:59
	 * 
	 * @param shouzhi
	 *            the shouzhi to set
	 */
	public void setShouzhi(String shouzhi) {
		this.shouzhi = shouzhi;
	}

	/**
	 * <br>
	 * Created on: 2013-11-5 下午11:15:59
	 * 
	 * @return the jiezhi
	 */
	public String getJiezhi() {
		return jiezhi;
	}

	/**
	 * <br>
	 * Created on: 2013-11-5 下午11:15:59
	 * 
	 * @param jiezhi
	 *            the jiezhi to set
	 */
	public void setJiezhi(String jiezhi) {
		this.jiezhi = jiezhi;
	}

	/**
	 * <br>
	 * Created on: 2013-11-5 下午11:15:59
	 * 
	 * @return the leixing
	 */
	public String getLeixing() {
		return leixing;
	}

	/**
	 * <br>
	 * Created on: 2013-11-5 下午11:15:59
	 * 
	 * @param leixing
	 *            the leixing to set
	 */
	public void setLeixing(String leixing) {
		this.leixing = leixing;
	}

	/**
	 * <br>
	 * Created on: 2013-11-5 下午11:15:59
	 * 
	 * @return the xingzhi
	 */
	public String getXingzhi() {
		return xingzhi;
	}

	/**
	 * <br>
	 * Created on: 2013-11-5 下午11:15:59
	 * 
	 * @param xingzhi
	 *            the xingzhi to set
	 */
	public void setXingzhi(String xingzhi) {
		this.xingzhi = xingzhi;
	}

	/**
	 * <br>
	 * Created on: 2013-11-5 下午11:15:59
	 * 
	 * @return the money
	 */
	public Float getMoney() {
		return money;
	}

	/**
	 * <br>
	 * Created on: 2013-11-5 下午11:15:59
	 * 
	 * @param money
	 *            the money to set
	 */
	public void setMoney(Float money) {
		this.money = money;
	}

	/**
	 * <br>
	 * Created on: 2013-11-5 下午11:15:59
	 * 
	 * @return the note
	 */
	public String getNote() {
		return note;
	}

	/**
	 * <br>
	 * Created on: 2013-11-5 下午11:15:59
	 * 
	 * @param note
	 *            the note to set
	 */
	public void setNote(String note) {
		this.note = note;
	}

	/**
	 * <br>
	 * Created on: 2013-11-5 下午11:15:59
	 * 
	 * @return the page
	 */
	public int getPage() {
		return page;
	}

	/**
	 * <br>
	 * Created on: 2013-11-5 下午11:15:59
	 * 
	 * @param page
	 *            the page to set
	 */
	public void setPage(int page) {
		this.page = page;
	}

	/**
	 * <br>
	 * Created on: 2013-11-5 下午11:15:59
	 * 
	 * @return the rows
	 */
	public int getRows() {
		return rows;
	}

	/**
	 * <br>
	 * Created on: 2013-11-5 下午11:15:59
	 * 
	 * @param rows
	 *            the rows to set
	 */
	public void setRows(int rows) {
		this.rows = rows;
	}

	/**
	 * <br>
	 * Created on: 2013-11-5 下午11:15:59
	 * 
	 * @return the order
	 */
	public String getOrder() {
		return order;
	}

	/**
	 * <br>
	 * Created on: 2013-11-5 下午11:15:59
	 * 
	 * @param order
	 *            the order to set
	 */
	public void setOrder(String order) {
		this.order = order;
	}

	/**
	 * <br>
	 * Created on: 2013-11-5 下午11:15:59
	 * 
	 * @return the sort
	 */
	public String getSort() {
		return sort;
	}

	/**
	 * <br>
	 * Created on: 2013-11-5 下午11:15:59
	 * 
	 * @param sort
	 *            the sort to set
	 */
	public void setSort(String sort) {
		this.sort = sort;
	}

}
