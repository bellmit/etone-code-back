/**
 * z.z.w.common.FileWriteOperInfo.java
 */
package z.z.w.common;

/**
 * @author Wu Zhenzhen
 * @version Apr 19, 2013 6:51:14 PM
 * @deprecated please use<code>z.z.w.common.FileOperUtil</code>
 */
public class FileWriteOperInfo {

	/**
	 * <br>
	 * Created on: Apr 19, 2013 6:52:53 PM
	 */
	public FileWriteOperInfo() {
		super();
	}

	private long size = 0;
	private long operTime = 0;

	private FileWriteOper fileWriteOper = null;

	/**
	 * <br>
	 * Created on: Dec 13, 2012 4:07:28 PM
	 */
	public void close() {

		if (!DataTools.isEmpty(this.fileWriteOper))
			this.fileWriteOper.closeFileWriter();
		this.fileWriteOper = null;

	}

	/**
	 * <br>
	 * Created on: Apr 19, 2013 6:52:50 PM
	 * 
	 * @return the size
	 */
	public long getSize() {
		return size;
	}

	/**
	 * <br>
	 * Created on: Apr 19, 2013 6:52:50 PM
	 * 
	 * @param size
	 *            the size to set
	 */
	public void setSize(long size) {
		this.size = size;
	}

	/**
	 * <br>
	 * Created on: Apr 19, 2013 6:52:50 PM
	 * 
	 * @param size
	 *            the size to set
	 */
	public void addSize(long size) {
		this.size += size;
	}

	/**
	 * <br>
	 * Created on: Apr 19, 2013 6:52:50 PM
	 * 
	 * @return the operTime
	 */
	public long getOperTime() {
		return operTime;
	}

	/**
	 * <br>
	 * Created on: Apr 19, 2013 6:52:50 PM
	 * 
	 * @param operTime
	 *            the operTime to set
	 */
	public void setOperTime(long operTime) {
		this.operTime = operTime;
	}

	/**
	 * <br>
	 * Created on: Apr 19, 2013 6:52:50 PM
	 * 
	 * @return the fileWriteOper
	 */
	public FileWriteOper getFileWriteOper() {
		return fileWriteOper;
	}

	/**
	 * <br>
	 * Created on: Apr 19, 2013 6:52:50 PM
	 * 
	 * @param fileWriteOper
	 *            the fileWriteOper to set
	 */
	public void setFileWriteOper(FileWriteOper fileWriteOper) {
		this.fileWriteOper = fileWriteOper;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "FileWriteOperInfo [size=" + size + ", operTime=" + operTime
				+ ", fileWriteOper=" + fileWriteOper + "]";
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
				+ ((fileWriteOper == null) ? 0 : fileWriteOper.hashCode());
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
		FileWriteOperInfo other = (FileWriteOperInfo) obj;
		if (fileWriteOper == null) {
			if (other.fileWriteOper != null)
				return false;
		} else if (!fileWriteOper.equals(other.fileWriteOper))
			return false;
		if (operTime != other.operTime)
			return false;
		if (size != other.size)
			return false;
		return true;
	}

}
