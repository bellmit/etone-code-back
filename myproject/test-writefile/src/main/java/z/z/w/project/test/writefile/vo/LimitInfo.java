/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.test.writefile.vo.LimitInfo.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-9-5 上午10:19:36 
 *   LastChange: 2013-9-5 上午10:19:36 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.test.writefile.vo;

import z.z.w.common.FileOperUtil;

/**
 * z.z.w.project.test.writefile.vo.LimitInfo.java
 */
public class LimitInfo {
	private int size = 0;
	private long dataTime = 0;
	private FileOperUtil fileOperUtil = null;

	private String lastFileName = "";

	/**
	 * 
	 * <br>
	 * Created on: 2013-9-5 上午10:20:21
	 * 
	 * @param size
	 */
	public void addSize(int size) {
		this.size += size;
	}

	/**
	 * <br>
	 * Created on: 2013-9-5 上午10:20:06
	 */
	public LimitInfo() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "LimitInfo [size=" + size + ", dataTime=" + dataTime
				+ ", fileOperUtil=" + fileOperUtil + ", lastFileName="
				+ lastFileName + "]";
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
		result = prime * result + (int) (dataTime ^ (dataTime >>> 32));
		result = prime * result
				+ ((fileOperUtil == null) ? 0 : fileOperUtil.hashCode());
		result = prime * result
				+ ((lastFileName == null) ? 0 : lastFileName.hashCode());
		result = prime * result + size;
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
		LimitInfo other = (LimitInfo) obj;
		if (dataTime != other.dataTime)
			return false;
		if (fileOperUtil == null) {
			if (other.fileOperUtil != null)
				return false;
		} else if (!fileOperUtil.equals(other.fileOperUtil))
			return false;
		if (lastFileName == null) {
			if (other.lastFileName != null)
				return false;
		} else if (!lastFileName.equals(other.lastFileName))
			return false;
		if (size != other.size)
			return false;
		return true;
	}

	/**
	 * <br>
	 * Created on: 2013-9-5 上午10:20:13
	 * 
	 * @return the size
	 */
	public int getSize() {
		return size;
	}

	/**
	 * <br>
	 * Created on: 2013-9-5 上午10:20:13
	 * 
	 * @param size
	 *            the size to set
	 */
	public void setSize(int size) {
		this.size = size;
	}

	/**
	 * <br>
	 * Created on: 2013-9-5 上午10:20:13
	 * 
	 * @return the dataTime
	 */
	public long getDataTime() {
		return dataTime;
	}

	/**
	 * <br>
	 * Created on: 2013-9-5 上午10:20:13
	 * 
	 * @param dataTime
	 *            the dataTime to set
	 */
	public void setDataTime(long dataTime) {
		this.dataTime = dataTime;
	}

	/**
	 * <br>
	 * Created on: 2013-9-5 上午10:20:13
	 * 
	 * @return the fileOperUtil
	 */
	public FileOperUtil getFileOperUtil() {
		return fileOperUtil;
	}

	/**
	 * <br>
	 * Created on: 2013-9-5 上午10:20:13
	 * 
	 * @param fileOperUtil
	 *            the fileOperUtil to set
	 */
	public void setFileOperUtil(FileOperUtil fileOperUtil) {
		this.fileOperUtil = fileOperUtil;
	}

	/**
	 * <br>
	 * Created on: 2013-9-5 上午10:20:13
	 * 
	 * @return the lastFileName
	 */
	public String getLastFileName() {
		return lastFileName;
	}

	/**
	 * <br>
	 * Created on: 2013-9-5 上午10:20:13
	 * 
	 * @param lastFileName
	 *            the lastFileName to set
	 */
	public void setLastFileName(String lastFileName) {
		this.lastFileName = lastFileName;
	}

}
