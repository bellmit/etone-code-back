/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.test.vo.ExportData.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-11-7 下午03:00:16 
 *   LastChange: 2013-11-7 下午03:00:16 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.test.vo;

/**
 * z.z.w.project.test.vo.ExportData.java
 */
public class ExportData extends DBData {

	private String exportOption;

	/**
	 * <br>
	 * Created on: 2013-11-7 下午04:34:51
	 */
	public ExportData() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ExportData [exportOption=" + exportOption + ", toString()="
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
				+ ((exportOption == null) ? 0 : exportOption.hashCode());
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
		ExportData other = (ExportData) obj;
		if (exportOption == null) {
			if (other.exportOption != null)
				return false;
		} else if (!exportOption.equals(other.exportOption))
			return false;
		return true;
	}

	/**
	 * <br>
	 * Created on: 2013-11-7 下午04:34:57
	 * 
	 * @return the exportOption
	 */
	public String getExportOption() {
		return exportOption;
	}

	/**
	 * <br>
	 * Created on: 2013-11-7 下午04:34:57
	 * 
	 * @param exportOption
	 *            the exportOption to set
	 */
	public void setExportOption(String exportOption) {
		this.exportOption = exportOption;
	}

}
