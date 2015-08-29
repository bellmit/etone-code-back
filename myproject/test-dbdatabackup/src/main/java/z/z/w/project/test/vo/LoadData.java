/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.test.vo.LoadData.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-11-7 下午04:35:20 
 *   LastChange: 2013-11-7 下午04:35:20 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.test.vo;

/**
 * z.z.w.project.test.vo.LoadData.java
 */
public class LoadData extends DBData {

	private String filePath;

	/**
	 * <br>
	 * Created on: 2013-11-7 下午04:35:30
	 */
	public LoadData() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "LoadData [filePath=" + filePath + ", toString()="
				+ super.toString() + "]";
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
		result = prime * result
				+ ((filePath == null) ? 0 : filePath.hashCode());
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
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		LoadData other = (LoadData) obj;
		if (filePath == null) {
			if (other.filePath != null)
				return false;
		} else if (!filePath.equals(other.filePath))
			return false;
		return true;
	}

	/**
	 * <br>
	 * Created on: 2013-11-7 下午04:35:35
	 * 
	 * @return the filePath
	 */
	public String getFilePath() {
		return filePath;
	}

	/**
	 * <br>
	 * Created on: 2013-11-7 下午04:35:35
	 * 
	 * @param filePath
	 *            the filePath to set
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

}
