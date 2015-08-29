/**
 * z.z.w.common.FileInfos.java
 */
package z.z.w.common;

/**
 * @author Wu Zhenzhen
 * @version Mar 26, 2013 10:23:00 AM
 */
public class FileInfos {

	private String filePath;
	private String fileBakPath;

	private String remotePath = ""; 
	private String localPath = "";

	private String filePrefix;
	private String fileSuffix;
	private String fieldSplit;
	private String lineSplit;
	private int fieldIndex;
	private boolean fileIsBak;
	private boolean fileIsDel;

	private int maxSize;

	/**
	 * <br>
	 * Created on: Mar 26, 2013 10:24:00 AM
	 */
	public FileInfos() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "FileInfos [filePath=" + filePath + ", fileBakPath="
				+ fileBakPath + ", remotePath=" + remotePath + ", localPath="
				+ localPath + ", filePrefix=" + filePrefix + ", fileSuffix="
				+ fileSuffix + ", fieldSplit=" + fieldSplit + ", lineSplit="
				+ lineSplit + ", fieldIndex=" + fieldIndex + ", fileIsBak="
				+ fileIsBak + ", fileIsDel=" + fileIsDel + ", maxSize="
				+ maxSize + "]";
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
		result = prime * result + fieldIndex;
		result = prime * result
				+ ((fieldSplit == null) ? 0 : fieldSplit.hashCode());
		result = prime * result
				+ ((fileBakPath == null) ? 0 : fileBakPath.hashCode());
		result = prime * result + (fileIsBak ? 1231 : 1237);
		result = prime * result + (fileIsDel ? 1231 : 1237);
		result = prime * result
				+ ((filePath == null) ? 0 : filePath.hashCode());
		result = prime * result
				+ ((filePrefix == null) ? 0 : filePrefix.hashCode());
		result = prime * result
				+ ((fileSuffix == null) ? 0 : fileSuffix.hashCode());
		result = prime * result
				+ ((lineSplit == null) ? 0 : lineSplit.hashCode());
		result = prime * result
				+ ((localPath == null) ? 0 : localPath.hashCode());
		result = prime * result + maxSize;
		result = prime * result
				+ ((remotePath == null) ? 0 : remotePath.hashCode());
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
		FileInfos other = (FileInfos) obj;
		if (fieldIndex != other.fieldIndex)
			return false;
		if (fieldSplit == null) {
			if (other.fieldSplit != null)
				return false;
		} else if (!fieldSplit.equals(other.fieldSplit))
			return false;
		if (fileBakPath == null) {
			if (other.fileBakPath != null)
				return false;
		} else if (!fileBakPath.equals(other.fileBakPath))
			return false;
		if (fileIsBak != other.fileIsBak)
			return false;
		if (fileIsDel != other.fileIsDel)
			return false;
		if (filePath == null) {
			if (other.filePath != null)
				return false;
		} else if (!filePath.equals(other.filePath))
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
		if (lineSplit == null) {
			if (other.lineSplit != null)
				return false;
		} else if (!lineSplit.equals(other.lineSplit))
			return false;
		if (localPath == null) {
			if (other.localPath != null)
				return false;
		} else if (!localPath.equals(other.localPath))
			return false;
		if (maxSize != other.maxSize)
			return false;
		if (remotePath == null) {
			if (other.remotePath != null)
				return false;
		} else if (!remotePath.equals(other.remotePath))
			return false;
		return true;
	}

	/**
	 * <br>
	 * Created on: Mar 26, 2013 10:24:06 AM
	 * 
	 * @return the filePath
	 */
	public String getFilePath() {
		return filePath;
	}

	/**
	 * <br>
	 * Created on: Mar 26, 2013 10:24:06 AM
	 * 
	 * @param filePath
	 *            the filePath to set
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	/**
	 * <br>
	 * Created on: Mar 26, 2013 10:24:06 AM
	 * 
	 * @return the fileBakPath
	 */
	public String getFileBakPath() {
		return fileBakPath;
	}

	/**
	 * <br>
	 * Created on: Mar 26, 2013 10:24:06 AM
	 * 
	 * @param fileBakPath
	 *            the fileBakPath to set
	 */
	public void setFileBakPath(String fileBakPath) {
		this.fileBakPath = fileBakPath;
	}

	/**
	 * <br>
	 * Created on: Mar 26, 2013 10:24:06 AM
	 * 
	 * @return the remotePath
	 */
	public String getRemotePath() {
		return remotePath;
	}

	/**
	 * <br>
	 * Created on: Mar 26, 2013 10:24:06 AM
	 * 
	 * @param remotePath
	 *            the remotePath to set
	 */
	public void setRemotePath(String remotePath) {
		this.remotePath = remotePath;
	}

	/**
	 * <br>
	 * Created on: Mar 26, 2013 10:24:06 AM
	 * 
	 * @return the localPath
	 */
	public String getLocalPath() {
		return localPath;
	}

	/**
	 * <br>
	 * Created on: Mar 26, 2013 10:24:06 AM
	 * 
	 * @param localPath
	 *            the localPath to set
	 */
	public void setLocalPath(String localPath) {
		this.localPath = localPath;
	}

	/**
	 * <br>
	 * Created on: Mar 26, 2013 10:24:06 AM
	 * 
	 * @return the filePrefix
	 */
	public String getFilePrefix() {
		return filePrefix;
	}

	/**
	 * <br>
	 * Created on: Mar 26, 2013 10:24:06 AM
	 * 
	 * @param filePrefix
	 *            the filePrefix to set
	 */
	public void setFilePrefix(String filePrefix) {
		this.filePrefix = filePrefix;
	}

	/**
	 * <br>
	 * Created on: Mar 26, 2013 10:24:06 AM
	 * 
	 * @return the fileSuffix
	 */
	public String getFileSuffix() {
		return fileSuffix;
	}

	/**
	 * <br>
	 * Created on: Mar 26, 2013 10:24:06 AM
	 * 
	 * @param fileSuffix
	 *            the fileSuffix to set
	 */
	public void setFileSuffix(String fileSuffix) {
		this.fileSuffix = fileSuffix;
	}

	/**
	 * <br>
	 * Created on: Mar 26, 2013 10:24:06 AM
	 * 
	 * @return the fieldSplit
	 */
	public String getFieldSplit() {
		return fieldSplit;
	}

	/**
	 * <br>
	 * Created on: Mar 26, 2013 10:24:06 AM
	 * 
	 * @param fieldSplit
	 *            the fieldSplit to set
	 */
	public void setFieldSplit(String fieldSplit) {
		this.fieldSplit = fieldSplit;
	}

	/**
	 * <br>
	 * Created on: Mar 26, 2013 10:24:06 AM
	 * 
	 * @return the lineSplit
	 */
	public String getLineSplit() {
		return lineSplit;
	}

	/**
	 * <br>
	 * Created on: Mar 26, 2013 10:24:06 AM
	 * 
	 * @param lineSplit
	 *            the lineSplit to set
	 */
	public void setLineSplit(String lineSplit) {
		this.lineSplit = lineSplit;
	}

	/**
	 * <br>
	 * Created on: Mar 26, 2013 10:24:06 AM
	 * 
	 * @return the fieldIndex
	 */
	public int getFieldIndex() {
		return fieldIndex;
	}

	/**
	 * <br>
	 * Created on: Mar 26, 2013 10:24:06 AM
	 * 
	 * @param fieldIndex
	 *            the fieldIndex to set
	 */
	public void setFieldIndex(int fieldIndex) {
		this.fieldIndex = fieldIndex;
	}

	/**
	 * <br>
	 * Created on: Mar 26, 2013 10:24:06 AM
	 * 
	 * @return the fileIsBak
	 */
	public boolean isFileIsBak() {
		return fileIsBak;
	}

	/**
	 * <br>
	 * Created on: Mar 26, 2013 10:24:06 AM
	 * 
	 * @param fileIsBak
	 *            the fileIsBak to set
	 */
	public void setFileIsBak(boolean fileIsBak) {
		this.fileIsBak = fileIsBak;
	}

	/**
	 * <br>
	 * Created on: Mar 26, 2013 10:24:06 AM
	 * 
	 * @return the fileIsDel
	 */
	public boolean isFileIsDel() {
		return fileIsDel;
	}

	/**
	 * <br>
	 * Created on: Mar 26, 2013 10:24:06 AM
	 * 
	 * @param fileIsDel
	 *            the fileIsDel to set
	 */
	public void setFileIsDel(boolean fileIsDel) {
		this.fileIsDel = fileIsDel;
	}

	/**
	 * <br>
	 * Created on: Mar 26, 2013 10:24:06 AM
	 * 
	 * @return the maxSize
	 */
	public int getMaxSize() {
		return maxSize;
	}

	/**
	 * <br>
	 * Created on: Mar 26, 2013 10:24:06 AM
	 * 
	 * @param maxSize
	 *            the maxSize to set
	 */
	public void setMaxSize(int maxSize) {
		this.maxSize = maxSize;
	}

}
