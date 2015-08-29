/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.test.vo.DBData.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-11-7 下午04:34:30 
 *   LastChange: 2013-11-7 下午04:34:30 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.test.vo;

/**
 * z.z.w.project.test.vo.DBData.java
 */
public class DBData {
	private String tableName;
	private String columns;

	/**
	 * <br>
	 * Created on: 2013-11-7 下午04:34:34
	 */
	public DBData() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DBData [tableName=" + tableName + ", columns=" + columns + "]";
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
		result = prime * result + ((columns == null) ? 0 : columns.hashCode());
		result = prime * result
				+ ((tableName == null) ? 0 : tableName.hashCode());
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
		DBData other = (DBData) obj;
		if (columns == null) {
			if (other.columns != null)
				return false;
		} else if (!columns.equals(other.columns))
			return false;
		if (tableName == null) {
			if (other.tableName != null)
				return false;
		} else if (!tableName.equals(other.tableName))
			return false;
		return true;
	}

	/**
	 * <br>
	 * Created on: 2013-11-7 下午04:34:39
	 * 
	 * @return the tableName
	 */
	public String getTableName() {
		return tableName;
	}

	/**
	 * <br>
	 * Created on: 2013-11-7 下午04:34:39
	 * 
	 * @param tableName
	 *            the tableName to set
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	/**
	 * <br>
	 * Created on: 2013-11-7 下午04:34:39
	 * 
	 * @return the columns
	 */
	public String getColumns() {
		return columns;
	}

	/**
	 * <br>
	 * Created on: 2013-11-7 下午04:34:39
	 * 
	 * @param columns
	 *            the columns to set
	 */
	public void setColumns(String columns) {
		this.columns = columns;
	}

}
