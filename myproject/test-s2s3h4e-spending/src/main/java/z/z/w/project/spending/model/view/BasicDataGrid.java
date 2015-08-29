/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.spending.model.view.BasicDataGrid.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-11-12 下午04:57:52 
 *   LastChange: 2013-11-12 下午04:57:52 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.spending.model.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import z.z.w.project.spending.common.Json;

/**
 * z.z.w.project.spending.model.view.BasicDataGrid.java
 */
public class BasicDataGrid<T> extends Json implements Serializable {

	/**
	 * <br>
	 * Created on: 2013-11-13 上午11:45:34
	 */
	private static final long serialVersionUID = -4566102307770921297L;
	private long total = 0L;
	private List<T> rows = new ArrayList<T>();
	private List<T> footer = null;

	/**
	 * <br>
	 * Created on: 2013-11-13 上午11:58:04
	 */
	public BasicDataGrid() {
		super();
	}

	/**
	 * <br>
	 * Created on: 2013-11-20 下午02:51:15
	 * 
	 * @return the footer
	 */
	public List<T> getFooter() {
		return footer;
	}

	/**
	 * <br>
	 * Created on: 2013-11-20 下午02:51:15
	 * 
	 * @param footer
	 *            the footer to set
	 */
	public void setFooter(List<T> footer) {
		this.footer = footer;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BasicDataGrid [total=" + total + ", rows=" + rows + ", footer=" + footer + "]";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((footer == null) ? 0 : footer.hashCode());
		result = prime * result + ((rows == null) ? 0 : rows.hashCode());
		result = prime * result + (int) (total ^ (total >>> 32));
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		BasicDataGrid other = (BasicDataGrid) obj;
		if (footer == null) {
			if (other.footer != null)
				return false;
		} else if (!footer.equals(other.footer))
			return false;
		if (rows == null) {
			if (other.rows != null)
				return false;
		} else if (!rows.equals(other.rows))
			return false;
		if (total != other.total)
			return false;
		return true;
	}

	/**
	 * <br>
	 * Created on: 2013-11-13 上午11:58:16
	 * 
	 * @return the total
	 */
	public long getTotal() {
		return total;
	}

	/**
	 * <br>
	 * Created on: 2013-11-13 上午11:58:16
	 * 
	 * @return the rows
	 */
	public List<T> getRows() {
		return rows;
	}

	/**
	 * <br>
	 * Created on: 2013-11-13 上午11:58:16
	 * 
	 * @param total
	 *            the total to set
	 */
	public void setTotal(long total) {
		this.total = total;
	}

	/**
	 * <br>
	 * Created on: 2013-11-13 上午11:58:16
	 * 
	 * @param rows
	 *            the rows to set
	 */
	public void setRows(List<T> rows) {
		this.rows = rows;
	}

}
