/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.test.model.view.DataGrid.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-11-5 下午10:25:17 
 *   LastChange: 2013-11-5 下午10:25:17 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.test.model.view;

import java.util.ArrayList;
import java.util.List;

/**
 * z.z.w.project.test.model.view.DataGrid.java
 */
public class DataGrid<T> {

	private int total = 0;
	private List<T> rows = new ArrayList<T>();

	/**
	 * <br>
	 * Created on: 2013-11-5 下午10:26:05
	 */
	public DataGrid() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DataGrid [total=" + total + ", rows=" + rows + "]";
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
		result = prime * result + ((rows == null) ? 0 : rows.hashCode());
		result = prime * result + total;
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DataGrid<T> other = (DataGrid<T>) obj;
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
	 * Created on: 2013-11-5 下午10:26:10
	 * 
	 * @return the total
	 */
	public int getTotal() {
		return total;
	}

	/**
	 * <br>
	 * Created on: 2013-11-5 下午10:26:10
	 * 
	 * @param total
	 *            the total to set
	 */
	public void setTotal(int total) {
		this.total = total;
	}

	/**
	 * <br>
	 * Created on: 2013-11-5 下午10:26:10
	 * 
	 * @return the rows
	 */
	public List<T> getRows() {
		return rows;
	}

	/**
	 * <br>
	 * Created on: 2013-11-5 下午10:26:10
	 * 
	 * @param rows
	 *            the rows to set
	 */
	public void setRows(List<T> rows) {
		this.rows = rows;
	}

}
