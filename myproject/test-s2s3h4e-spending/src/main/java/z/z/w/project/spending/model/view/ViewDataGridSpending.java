/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.spending.model.view.ViewDataGridSpending.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-11-12 下午04:47:23 
 *   LastChange: 2013-11-12 下午04:47:23 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.spending.model.view;

import java.io.Serializable;

/**
 * z.z.w.project.spending.model.view.ViewDataGridSpending.java
 */
public class ViewDataGridSpending implements Serializable {

	/**
	 * <br>
	 * Created on: 2013-11-12 下午04:55:18
	 */
	private static final long serialVersionUID = -8400250707330723436L;

	private int intDielectricId;

	private char chReveExpenId = ' ';

	private float nmBalances;
	private long nmSpendId;
	private long nmSubSpendId;
	private String order;

	private int page;

	private char positiveOrNegative = ' ';

	private int rows;

	private String rsssId;

	private String sort;

	private String vcNote;

	private String vcRNote;

	private String vcSNote;

	private String vcSSNote;

	/**
	 * <br>
	 * Created on: 2013-11-13 上午10:56:24
	 */
	public ViewDataGridSpending() {
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
		ViewDataGridSpending other = (ViewDataGridSpending) obj;
		if (chReveExpenId != other.chReveExpenId)
			return false;
		if (intDielectricId != other.intDielectricId)
			return false;
		if (Float.floatToIntBits(nmBalances) != Float.floatToIntBits(other.nmBalances))
			return false;
		if (nmSpendId != other.nmSpendId)
			return false;
		if (nmSubSpendId != other.nmSubSpendId)
			return false;
		if (order == null) {
			if (other.order != null)
				return false;
		} else if (!order.equals(other.order))
			return false;
		if (page != other.page)
			return false;
		if (positiveOrNegative != other.positiveOrNegative)
			return false;
		if (rows != other.rows)
			return false;
		if (rsssId == null) {
			if (other.rsssId != null)
				return false;
		} else if (!rsssId.equals(other.rsssId))
			return false;
		if (sort == null) {
			if (other.sort != null)
				return false;
		} else if (!sort.equals(other.sort))
			return false;
		if (vcNote == null) {
			if (other.vcNote != null)
				return false;
		} else if (!vcNote.equals(other.vcNote))
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
	 * Created on: 2013-11-13 上午10:56:29
	 * 
	 * @return the intDielectricId
	 */
	public int getIntDielectricId() {
		return intDielectricId;
	}

	/**
	 * <br>
	 * Created on: 2013-11-13 上午10:56:29
	 * 
	 * @return the nmBalances
	 */
	public float getNmBalances() {
		return nmBalances;
	}

	/**
	 * <br>
	 * Created on: 2013-11-17 下午09:35:58
	 * 
	 * @return the nmSpendId
	 */
	public long getNmSpendId() {
		return nmSpendId;
	}

	/**
	 * <br>
	 * Created on: 2013-11-17 下午09:35:58
	 * 
	 * @return the nmSubSpendId
	 */
	public long getNmSubSpendId() {
		return nmSubSpendId;
	}

	/**
	 * <br>
	 * Created on: 2013-11-13 上午10:56:29
	 * 
	 * @return the order
	 */
	public String getOrder() {
		return order;
	}

	/**
	 * <br>
	 * Created on: 2013-11-13 上午10:56:29
	 * 
	 * @return the page
	 */
	public int getPage() {
		return page;
	}

	/**
	 * <br>
	 * Created on: 2013-11-21 上午11:54:47
	 * 
	 * @return the positiveOrNegative
	 */
	public char getPositiveOrNegative() {
		return positiveOrNegative;
	}

	/**
	 * <br>
	 * Created on: 2013-11-13 上午10:56:29
	 * 
	 * @return the rows
	 */
	public int getRows() {
		return rows;
	}

	/**
	 * <br>
	 * Created on: 2013-11-18 下午03:06:40
	 * 
	 * @return the rsssId
	 */
	public String getRsssId() {
		return rsssId;
	}

	/**
	 * <br>
	 * Created on: 2013-11-13 上午10:56:29
	 * 
	 * @return the sort
	 */
	public String getSort() {
		return sort;
	}

	/**
	 * <br>
	 * Created on: 2013-11-13 上午10:56:29
	 * 
	 * @return the vcNote
	 */
	public String getVcNote() {
		return vcNote;
	}

	/**
	 * <br>
	 * Created on: 2013-11-17 下午09:24:03
	 * 
	 * @return the vcRNote
	 */
	public String getVcRNote() {
		return vcRNote;
	}

	/**
	 * <br>
	 * Created on: 2013-11-17 下午09:24:03
	 * 
	 * @return the vcSNote
	 */
	public String getVcSNote() {
		return vcSNote;
	}

	/**
	 * <br>
	 * Created on: 2013-11-17 下午09:24:03
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
		result = prime * result + intDielectricId;
		result = prime * result + Float.floatToIntBits(nmBalances);
		result = prime * result + (int) (nmSpendId ^ (nmSpendId >>> 32));
		result = prime * result + (int) (nmSubSpendId ^ (nmSubSpendId >>> 32));
		result = prime * result + ((order == null) ? 0 : order.hashCode());
		result = prime * result + page;
		result = prime * result + positiveOrNegative;
		result = prime * result + rows;
		result = prime * result + ((rsssId == null) ? 0 : rsssId.hashCode());
		result = prime * result + ((sort == null) ? 0 : sort.hashCode());
		result = prime * result + ((vcNote == null) ? 0 : vcNote.hashCode());
		result = prime * result + ((vcRNote == null) ? 0 : vcRNote.hashCode());
		result = prime * result + ((vcSNote == null) ? 0 : vcSNote.hashCode());
		result = prime * result + ((vcSSNote == null) ? 0 : vcSSNote.hashCode());
		return result;
	}

	/**
	 * <br>
	 * Created on: 2013-11-13 上午10:56:29
	 * 
	 * @param intDielectricId
	 *            the intDielectricId to set
	 */
	public void setIntDielectricId(int intDielectricId) {
		this.intDielectricId = intDielectricId;
	}

	/**
	 * <br>
	 * Created on: 2013-11-13 上午10:56:29
	 * 
	 * @param nmBalances
	 *            the nmBalances to set
	 */
	public void setNmBalances(float nmBalances) {
		this.nmBalances = nmBalances;
	}

	/**
	 * <br>
	 * Created on: 2013-11-17 下午09:35:58
	 * 
	 * @param nmSpendId
	 *            the nmSpendId to set
	 */
	public void setNmSpendId(long nmSpendId) {
		this.nmSpendId = nmSpendId;
	}

	/**
	 * <br>
	 * Created on: 2013-11-17 下午09:35:58
	 * 
	 * @param nmSubSpendId
	 *            the nmSubSpendId to set
	 */
	public void setNmSubSpendId(long nmSubSpendId) {
		this.nmSubSpendId = nmSubSpendId;
	}

	/**
	 * <br>
	 * Created on: 2013-11-13 上午10:56:29
	 * 
	 * @param order
	 *            the order to set
	 */
	public void setOrder(String order) {
		this.order = order;
	}

	/**
	 * <br>
	 * Created on: 2013-11-13 上午10:56:29
	 * 
	 * @param page
	 *            the page to set
	 */
	public void setPage(int page) {
		this.page = page;
	}

	/**
	 * <br>
	 * Created on: 2013-11-21 上午11:54:47
	 * 
	 * @param positiveOrNegative
	 *            the positiveOrNegative to set
	 */
	public void setPositiveOrNegative(char positiveOrNegative) {
		this.positiveOrNegative = positiveOrNegative;
	}

	/**
	 * <br>
	 * Created on: 2013-11-13 上午10:56:29
	 * 
	 * @param rows
	 *            the rows to set
	 */
	public void setRows(int rows) {
		this.rows = rows;
	}

	/**
	 * <br>
	 * Created on: 2013-11-18 下午03:06:40
	 * 
	 * @param rsssId
	 *            the rsssId to set
	 */
	public void setRsssId(String rsssId) {
		this.rsssId = rsssId;
	}

	/**
	 * <br>
	 * Created on: 2013-11-13 上午10:56:29
	 * 
	 * @param sort
	 *            the sort to set
	 */
	public void setSort(String sort) {
		this.sort = sort;
	}

	/**
	 * <br>
	 * Created on: 2013-11-13 上午10:56:29
	 * 
	 * @param vcNote
	 *            the vcNote to set
	 */
	public void setVcNote(String vcNote) {
		this.vcNote = vcNote;
	}

	/**
	 * <br>
	 * Created on: 2013-11-17 下午09:24:03
	 * 
	 * @param vcRNote
	 *            the vcRNote to set
	 */
	public void setVcRNote(String vcRNote) {
		this.vcRNote = vcRNote;
	}

	/**
	 * <br>
	 * Created on: 2013-11-17 下午09:24:03
	 * 
	 * @param vcSNote
	 *            the vcSNote to set
	 */
	public void setVcSNote(String vcSNote) {
		this.vcSNote = vcSNote;
	}

	/**
	 * <br>
	 * Created on: 2013-11-17 下午09:24:03
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
		return "ViewDataGridSpending [intDielectricId=" + intDielectricId + ", chReveExpenId=" + chReveExpenId + ", nmBalances=" + nmBalances + ", nmSpendId=" + nmSpendId + ", nmSubSpendId=" + nmSubSpendId + ", order=" + order + ", page=" + page + ", positiveOrNegative=" + positiveOrNegative + ", rows=" + rows + ", rsssId=" + rsssId + ", sort=" + sort + ", vcNote=" + vcNote + ", vcRNote=" + vcRNote + ", vcSNote=" + vcSNote + ", vcSSNote=" + vcSSNote + "]";
	}

	/**
	 * <br>
	 * Created on: 2013-11-22 下午04:12:51
	 * 
	 * @return the chReveExpenId
	 */
	public char getChReveExpenId() {
		return chReveExpenId;
	}

	/**
	 * <br>
	 * Created on: 2013-11-22 下午04:12:51
	 * 
	 * @param chReveExpenId
	 *            the chReveExpenId to set
	 */
	public void setChReveExpenId(char chReveExpenId) {
		this.chReveExpenId = chReveExpenId;
	}

}
