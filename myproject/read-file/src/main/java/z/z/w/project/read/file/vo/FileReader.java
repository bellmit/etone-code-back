/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.read.file.vo.FileReader.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-8-20 下午08:30:35 
 *   LastChange: 2013-8-20 下午08:30:35 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.read.file.vo;

import java.util.List;

import z.z.w.vo.AdapterType;
import z.z.w.vo.Field;

/**
 * z.z.w.project.read.file.vo.FileReader.java
 */
public class FileReader extends AdapterType {

	private String localPath = "";
	private String backPath = "";

	private String filePrefix = "";
	private String fileSuffix = "";

	private String fieldSplit = ",";

	private boolean delete = false;
	private boolean backup = false;

	private int startRow = 0;

	private long readInterval = 1000 * 10;

	private List<Field> fieldList = null;

	/**
	 * <br>
	 * Created on: 2013-8-20 下午09:16:05
	 */
	public FileReader() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "FileReader [localPath=" + localPath + ", backPath=" + backPath
				+ ", filePrefix=" + filePrefix + ", fileSuffix=" + fileSuffix
				+ ", fieldSplit=" + fieldSplit + ", delete=" + delete
				+ ", backup=" + backup + ", startRow=" + startRow
				+ ", readInterval=" + readInterval + ", fieldList=" + fieldList
				+ "]";
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
				+ ((backPath == null) ? 0 : backPath.hashCode());
		result = prime * result + (backup ? 1231 : 1237);
		result = prime * result + (delete ? 1231 : 1237);
		result = prime * result
				+ ((fieldList == null) ? 0 : fieldList.hashCode());
		result = prime * result
				+ ((fieldSplit == null) ? 0 : fieldSplit.hashCode());
		result = prime * result
				+ ((filePrefix == null) ? 0 : filePrefix.hashCode());
		result = prime * result
				+ ((fileSuffix == null) ? 0 : fileSuffix.hashCode());
		result = prime * result
				+ ((localPath == null) ? 0 : localPath.hashCode());
		result = prime * result + (int) (readInterval ^ (readInterval >>> 32));
		result = prime * result + startRow;
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
		FileReader other = (FileReader) obj;
		if (backPath == null) {
			if (other.backPath != null)
				return false;
		} else if (!backPath.equals(other.backPath))
			return false;
		if (backup != other.backup)
			return false;
		if (delete != other.delete)
			return false;
		if (fieldList == null) {
			if (other.fieldList != null)
				return false;
		} else if (!fieldList.equals(other.fieldList))
			return false;
		if (fieldSplit == null) {
			if (other.fieldSplit != null)
				return false;
		} else if (!fieldSplit.equals(other.fieldSplit))
			return false;
		if (filePrefix == null) {
			if (other.filePrefix != null)
				return false;
		} else if (!filePrefix.equals(other.filePrefix))
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
		if (readInterval != other.readInterval)
			return false;
		if (startRow != other.startRow)
			return false;
		return true;
	}

	/**
	 * <br>
	 * Created on: 2013-8-20 下午09:16:10
	 * 
	 * @return the localPath
	 */
	public String getLocalPath() {
		return localPath;
	}

	/**
	 * <br>
	 * Created on: 2013-8-20 下午09:16:10
	 * 
	 * @param localPath
	 *            the localPath to set
	 */
	public void setLocalPath(String localPath) {
		this.localPath = localPath;
	}

	/**
	 * <br>
	 * Created on: 2013-8-20 下午09:16:10
	 * 
	 * @return the backPath
	 */
	public String getBackPath() {
		return backPath;
	}

	/**
	 * <br>
	 * Created on: 2013-8-20 下午09:16:10
	 * 
	 * @param backPath
	 *            the backPath to set
	 */
	public void setBackPath(String backPath) {
		this.backPath = backPath;
	}

	/**
	 * <br>
	 * Created on: 2013-8-20 下午09:16:10
	 * 
	 * @return the filePrefix
	 */
	public String getFilePrefix() {
		return filePrefix;
	}

	/**
	 * <br>
	 * Created on: 2013-8-20 下午09:16:10
	 * 
	 * @param filePrefix
	 *            the filePrefix to set
	 */
	public void setFilePrefix(String filePrefix) {
		this.filePrefix = filePrefix;
	}

	/**
	 * <br>
	 * Created on: 2013-8-20 下午09:16:10
	 * 
	 * @return the fileSuffix
	 */
	public String getFileSuffix() {
		return fileSuffix;
	}

	/**
	 * <br>
	 * Created on: 2013-8-20 下午09:16:10
	 * 
	 * @param fileSuffix
	 *            the fileSuffix to set
	 */
	public void setFileSuffix(String fileSuffix) {
		this.fileSuffix = fileSuffix;
	}

	/**
	 * <br>
	 * Created on: 2013-8-20 下午09:16:10
	 * 
	 * @return the fieldSplit
	 */
	public String getFieldSplit() {
		return fieldSplit;
	}

	/**
	 * <br>
	 * Created on: 2013-8-20 下午09:16:10
	 * 
	 * @param fieldSplit
	 *            the fieldSplit to set
	 */
	public void setFieldSplit(String fieldSplit) {
		this.fieldSplit = fieldSplit;
	}

	/**
	 * <br>
	 * Created on: 2013-8-20 下午09:16:10
	 * 
	 * @return the delete
	 */
	public boolean isDelete() {
		return delete;
	}

	/**
	 * <br>
	 * Created on: 2013-8-20 下午09:16:10
	 * 
	 * @param delete
	 *            the delete to set
	 */
	public void setDelete(boolean delete) {
		this.delete = delete;
	}

	/**
	 * <br>
	 * Created on: 2013-8-20 下午09:16:10
	 * 
	 * @return the backup
	 */
	public boolean isBackup() {
		return backup;
	}

	/**
	 * <br>
	 * Created on: 2013-8-20 下午09:16:10
	 * 
	 * @param backup
	 *            the backup to set
	 */
	public void setBackup(boolean backup) {
		this.backup = backup;
	}

	/**
	 * <br>
	 * Created on: 2013-8-20 下午09:16:10
	 * 
	 * @return the startRow
	 */
	public int getStartRow() {
		return startRow;
	}

	/**
	 * <br>
	 * Created on: 2013-8-20 下午09:16:10
	 * 
	 * @param startRow
	 *            the startRow to set
	 */
	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	/**
	 * <br>
	 * Created on: 2013-8-20 下午09:16:10
	 * 
	 * @return the readInterval
	 */
	public long getReadInterval() {
		return readInterval;
	}

	/**
	 * <br>
	 * Created on: 2013-8-20 下午09:16:10
	 * 
	 * @param readInterval
	 *            the readInterval to set
	 */
	public void setReadInterval(long readInterval) {
		this.readInterval = readInterval;
	}

	/**
	 * <br>
	 * Created on: 2013-8-20 下午09:16:10
	 * 
	 * @return the fieldList
	 */
	public List<Field> getFieldList() {
		return fieldList;
	}

	/**
	 * <br>
	 * Created on: 2013-8-20 下午09:16:10
	 * 
	 * @param fieldList
	 *            the fieldList to set
	 */
	public void setFieldList(List<Field> fieldList) {
		this.fieldList = fieldList;
	}

}
