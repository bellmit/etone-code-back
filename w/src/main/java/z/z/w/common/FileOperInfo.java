/**
 * z.z.w.common.FileOperInfo.java
 */
package z.z.w.common;

/**
 * @author Wu Zhenzhen
 * @version Dec 13, 2012 2:44:25 PM
 * @deprecated please use<code>z.z.w.common.FileOperUtil</code>
 */
public class FileOperInfo {

	private long size = 0;
	private long operTime = 0;
	private FileOperator fileOperator = null;
	private FileOperaUpgrade fileOperaUpgrade = null;

	/**
	 * <br>
	 * Created on: Dec 13, 2012 4:05:01 PM
	 */ 
	public FileOperInfo() {
		super();
	}

	/**
	 * <br>
	 * Created on: Dec 13, 2012 4:05:07 PM
	 * 
	 * @return the size
	 */
	public long getSize() {
		return size;
	}

	/**
	 * <br>
	 * Created on: Dec 13, 2012 4:05:07 PM
	 * 
	 * @param size
	 *            the size to set
	 */
	public void setSize(long size) {
		this.size = size;
	}

	/**
	 * 
	 * <br>
	 * Created on: Dec 13, 2012 4:05:36 PM
	 * 
	 * @param size
	 */
	public void addSize(long size) {
		this.size += size;
	}

	/**
	 * <br>
	 * Created on: Dec 13, 2012 4:05:07 PM
	 * 
	 * @return the operTime
	 */
	public long getOperTime() {
		return operTime;
	}

	/**
	 * <br>
	 * Created on: Dec 13, 2012 4:05:07 PM
	 * 
	 * @param operTime
	 *            the operTime to set
	 */
	public void setOperTime(long operTime) {
		this.operTime = operTime;
	}

	/**
	 * <br>
	 * Created on: Dec 13, 2012 4:05:07 PM
	 * 
	 * @return the fileOperator
	 */
	public FileOperator getFileOperator() {
		return fileOperator;
	}

	/**
	 * <br>
	 * Created on: Dec 13, 2012 4:05:07 PM
	 * 
	 * @param fileOperator
	 *            the fileOperator to set
	 */
	public void setFileOperator(FileOperator fileOperator) {
		this.fileOperator = fileOperator;
	}

	/**
	 * <br>
	 * Created on: Dec 13, 2012 4:05:08 PM
	 * 
	 * @return the fileOperaUpgrade
	 */
	public FileOperaUpgrade getFileOperaUpgrade() {
		return fileOperaUpgrade;
	}

	/**
	 * <br>
	 * Created on: Dec 13, 2012 4:05:08 PM
	 * 
	 * @param fileOperaUpgrade
	 *            the fileOperaUpgrade to set
	 */
	public void setFileOperaUpgrade(FileOperaUpgrade fileOperaUpgrade) {
		this.fileOperaUpgrade = fileOperaUpgrade;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "FileOperInfo [size=" + size + ", operTime=" + operTime
				+ ", fileOperator=" + fileOperator + ", fileOperaUpgrade="
				+ fileOperaUpgrade + "]";
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
		result = prime
				* result
				+ ((fileOperaUpgrade == null) ? 0 : fileOperaUpgrade.hashCode());
		result = prime * result
				+ ((fileOperator == null) ? 0 : fileOperator.hashCode());
		result = prime * result + (int) (operTime ^ (operTime >>> 32));
		result = prime * result + (int) (size ^ (size >>> 32));
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
		FileOperInfo other = (FileOperInfo) obj;
		if (fileOperaUpgrade == null) {
			if (other.fileOperaUpgrade != null)
				return false;
		} else if (!fileOperaUpgrade.equals(other.fileOperaUpgrade))
			return false;
		if (fileOperator == null) {
			if (other.fileOperator != null)
				return false;
		} else if (!fileOperator.equals(other.fileOperator))
			return false;
		if (operTime != other.operTime)
			return false;
		if (size != other.size)
			return false;
		return true;
	}

	/**
	 * <br>
	 * Created on: Dec 13, 2012 4:07:28 PM
	 */
	public void close() {

		if (!DataTools.isEmpty(this.fileOperator))
			this.fileOperator.close();
		this.fileOperator = null;

		if (!DataTools.isEmpty(this.fileOperaUpgrade))
			this.fileOperaUpgrade.close();
		this.fileOperaUpgrade = null;

	}

}
