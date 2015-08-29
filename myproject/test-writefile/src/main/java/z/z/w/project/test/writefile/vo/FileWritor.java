/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.test.writefile.vo.FileWritor.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-9-5 上午10:04:14 
 *   LastChange: 2013-9-5 上午10:04:14 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.test.writefile.vo;

import z.z.w.vo.AdapterType;

/**
 * z.z.w.project.test.writefile.vo.FileWritor.java
 */
public class FileWritor extends AdapterType {

	/**
	 * <type name="TestWriterFile"
	 * class="z.z.w.project.test.writefile.TestWriterFile" local="etc/gn"
	 * fieldSplit="\x2c" rowSplit="\x0a" fileSuffix=".csv" delayTime="26666"
	 * buffSize="182828" />
	 */
	private String localPath = "";
	private String fieldSplit = ",";
	private String rowSplit = "\n";
	private String fileSuffix = ".dat";
	private long delayTime = 26666;
	private int buffSize = 182828;

	/**
	 * <br>
	 * Created on: 2013-9-5 上午10:09:10
	 */
	public FileWritor() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "FileWritor [localPath=" + localPath + ", fieldSplit="
				+ fieldSplit + ", rowSplit=" + rowSplit + ", fileSuffix="
				+ fileSuffix + ", delayTime=" + delayTime + ", buffSize="
				+ buffSize + "]";
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
		result = prime * result + buffSize;
		result = prime * result + (int) (delayTime ^ (delayTime >>> 32));
		result = prime * result
				+ ((fieldSplit == null) ? 0 : fieldSplit.hashCode());
		result = prime * result
				+ ((fileSuffix == null) ? 0 : fileSuffix.hashCode());
		result = prime * result
				+ ((localPath == null) ? 0 : localPath.hashCode());
		result = prime * result
				+ ((rowSplit == null) ? 0 : rowSplit.hashCode());
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
		FileWritor other = (FileWritor) obj;
		if (buffSize != other.buffSize)
			return false;
		if (delayTime != other.delayTime)
			return false;
		if (fieldSplit == null) {
			if (other.fieldSplit != null)
				return false;
		} else if (!fieldSplit.equals(other.fieldSplit))
			return false;
		if (fileSuffix == null) {
			if (other.fileSuffix != null)
				return false;
		} else if (!fileSuffix.equals(other.fileSuffix))
			return false;
		if (localPath == null) {
			if (other.localPath != null)
				return false;
		} else if (!localPath.equals(other.localPath))
			return false;
		if (rowSplit == null) {
			if (other.rowSplit != null)
				return false;
		} else if (!rowSplit.equals(other.rowSplit))
			return false;
		return true;
	}

	/**
	 * <br>
	 * Created on: 2013-9-5 上午10:09:16
	 * 
	 * @return the localPath
	 */
	public String getLocalPath() {
		return localPath;
	}

	/**
	 * <br>
	 * Created on: 2013-9-5 上午10:09:16
	 * 
	 * @param localPath
	 *            the localPath to set
	 */
	public void setLocalPath(String localPath) {
		this.localPath = localPath;
	}

	/**
	 * <br>
	 * Created on: 2013-9-5 上午10:09:16
	 * 
	 * @return the fieldSplit
	 */
	public String getFieldSplit() {
		return fieldSplit;
	}

	/**
	 * <br>
	 * Created on: 2013-9-5 上午10:09:16
	 * 
	 * @param fieldSplit
	 *            the fieldSplit to set
	 */
	public void setFieldSplit(String fieldSplit) {
		this.fieldSplit = fieldSplit;
	}

	/**
	 * <br>
	 * Created on: 2013-9-5 上午10:09:16
	 * 
	 * @return the rowSplit
	 */
	public String getRowSplit() {
		return rowSplit;
	}

	/**
	 * <br>
	 * Created on: 2013-9-5 上午10:09:16
	 * 
	 * @param rowSplit
	 *            the rowSplit to set
	 */
	public void setRowSplit(String rowSplit) {
		this.rowSplit = rowSplit;
	}

	/**
	 * <br>
	 * Created on: 2013-9-5 上午10:09:16
	 * 
	 * @return the fileSuffix
	 */
	public String getFileSuffix() {
		return fileSuffix;
	}

	/**
	 * <br>
	 * Created on: 2013-9-5 上午10:09:16
	 * 
	 * @param fileSuffix
	 *            the fileSuffix to set
	 */
	public void setFileSuffix(String fileSuffix) {
		this.fileSuffix = fileSuffix;
	}

	/**
	 * <br>
	 * Created on: 2013-9-5 上午10:09:16
	 * 
	 * @return the delayTime
	 */
	public long getDelayTime() {
		return delayTime;
	}

	/**
	 * <br>
	 * Created on: 2013-9-5 上午10:09:16
	 * 
	 * @param delayTime
	 *            the delayTime to set
	 */
	public void setDelayTime(long delayTime) {
		this.delayTime = delayTime;
	}

	/**
	 * <br>
	 * Created on: 2013-9-5 上午10:09:16
	 * 
	 * @return the buffSize
	 */
	public int getBuffSize() {
		return buffSize;
	}

	/**
	 * <br>
	 * Created on: 2013-9-5 上午10:09:16
	 * 
	 * @param buffSize
	 *            the buffSize to set
	 */
	public void setBuffSize(int buffSize) {
		this.buffSize = buffSize;
	}

}
