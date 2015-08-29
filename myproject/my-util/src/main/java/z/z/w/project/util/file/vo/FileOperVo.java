/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.util.file.FileOperVo.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-9-10 上午09:57:59 
 *   LastChange: 2013-9-10 上午09:57:59 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.util.file.vo;

/**
 * z.z.w.project.util.file.FileOperVo.java
 */
public class FileOperVo {

	private String remotePath = "";
	private String localPath = "";
	private String bakPath = "";

	private String filePrefix = "";
	private String fileSuffix = "";
	private String fieldSplit = "";
	private String lineSplit = "";

	private boolean fileIsBak = true;
	private boolean fileIsDel = false;

	/**
	 * <br>
	 * Created on: 2013-9-10 上午10:00:09
	 */
	public FileOperVo() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "FileOperVo [remotePath=" + remotePath + ", localPath="
				+ localPath + ", bakPath=" + bakPath + ", filePrefix="
				+ filePrefix + ", fileSuffix=" + fileSuffix + ", fieldSplit="
				+ fieldSplit + ", lineSplit=" + lineSplit + ", fileIsBak="
				+ fileIsBak + ", fileIsDel=" + fileIsDel + "]";
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
		result = prime * result + ((bakPath == null) ? 0 : bakPath.hashCode());
		result = prime * result
				+ ((fieldSplit == null) ? 0 : fieldSplit.hashCode());
		result = prime * result + (fileIsBak ? 1231 : 1237);
		result = prime * result + (fileIsDel ? 1231 : 1237);
		result = prime * result
				+ ((filePrefix == null) ? 0 : filePrefix.hashCode());
		result = prime * result
				+ ((fileSuffix == null) ? 0 : fileSuffix.hashCode());
		result = prime * result
				+ ((lineSplit == null) ? 0 : lineSplit.hashCode());
		result = prime * result
				+ ((localPath == null) ? 0 : localPath.hashCode());
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
		FileOperVo other = (FileOperVo) obj;
		if (bakPath == null) {
			if (other.bakPath != null)
				return false;
		} else if (!bakPath.equals(other.bakPath))
			return false;
		if (fieldSplit == null) {
			if (other.fieldSplit != null)
				return false;
		} else if (!fieldSplit.equals(other.fieldSplit))
			return false;
		if (fileIsBak != other.fileIsBak)
			return false;
		if (fileIsDel != other.fileIsDel)
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
		if (remotePath == null) {
			if (other.remotePath != null)
				return false;
		} else if (!remotePath.equals(other.remotePath))
			return false;
		return true;
	}

	/**
	 * <br>
	 * Created on: 2013-9-10 上午10:00:16
	 * 
	 * @return the remotePath
	 */
	public String getRemotePath() {
		return remotePath;
	}

	/**
	 * <br>
	 * Created on: 2013-9-10 上午10:00:16
	 * 
	 * @param remotePath
	 *            the remotePath to set
	 */
	public void setRemotePath(String remotePath) {
		this.remotePath = remotePath;
	}

	/**
	 * <br>
	 * Created on: 2013-9-10 上午10:00:16
	 * 
	 * @return the localPath
	 */
	public String getLocalPath() {
		return localPath;
	}

	/**
	 * <br>
	 * Created on: 2013-9-10 上午10:00:16
	 * 
	 * @param localPath
	 *            the localPath to set
	 */
	public void setLocalPath(String localPath) {
		this.localPath = localPath;
	}

	/**
	 * <br>
	 * Created on: 2013-9-10 上午10:00:16
	 * 
	 * @return the bakPath
	 */
	public String getBakPath() {
		return bakPath;
	}

	/**
	 * <br>
	 * Created on: 2013-9-10 上午10:00:16
	 * 
	 * @param bakPath
	 *            the bakPath to set
	 */
	public void setBakPath(String bakPath) {
		this.bakPath = bakPath;
	}

	/**
	 * <br>
	 * Created on: 2013-9-10 上午10:00:16
	 * 
	 * @return the filePrefix
	 */
	public String getFilePrefix() {
		return filePrefix;
	}

	/**
	 * <br>
	 * Created on: 2013-9-10 上午10:00:16
	 * 
	 * @param filePrefix
	 *            the filePrefix to set
	 */
	public void setFilePrefix(String filePrefix) {
		this.filePrefix = filePrefix;
	}

	/**
	 * <br>
	 * Created on: 2013-9-10 上午10:00:16
	 * 
	 * @return the fileSuffix
	 */
	public String getFileSuffix() {
		return fileSuffix;
	}

	/**
	 * <br>
	 * Created on: 2013-9-10 上午10:00:16
	 * 
	 * @param fileSuffix
	 *            the fileSuffix to set
	 */
	public void setFileSuffix(String fileSuffix) {
		this.fileSuffix = fileSuffix;
	}

	/**
	 * <br>
	 * Created on: 2013-9-10 上午10:00:16
	 * 
	 * @return the fieldSplit
	 */
	public String getFieldSplit() {
		return fieldSplit;
	}

	/**
	 * <br>
	 * Created on: 2013-9-10 上午10:00:16
	 * 
	 * @param fieldSplit
	 *            the fieldSplit to set
	 */
	public void setFieldSplit(String fieldSplit) {
		this.fieldSplit = fieldSplit;
	}

	/**
	 * <br>
	 * Created on: 2013-9-10 上午10:00:16
	 * 
	 * @return the lineSplit
	 */
	public String getLineSplit() {
		return lineSplit;
	}

	/**
	 * <br>
	 * Created on: 2013-9-10 上午10:00:16
	 * 
	 * @param lineSplit
	 *            the lineSplit to set
	 */
	public void setLineSplit(String lineSplit) {
		this.lineSplit = lineSplit;
	}

	/**
	 * <br>
	 * Created on: 2013-9-10 上午10:00:16
	 * 
	 * @return the fileIsBak
	 */
	public boolean isFileIsBak() {
		return fileIsBak;
	}

	/**
	 * <br>
	 * Created on: 2013-9-10 上午10:00:16
	 * 
	 * @param fileIsBak
	 *            the fileIsBak to set
	 */
	public void setFileIsBak(boolean fileIsBak) {
		this.fileIsBak = fileIsBak;
	}

	/**
	 * <br>
	 * Created on: 2013-9-10 上午10:00:16
	 * 
	 * @return the fileIsDel
	 */
	public boolean isFileIsDel() {
		return fileIsDel;
	}

	/**
	 * <br>
	 * Created on: 2013-9-10 上午10:00:16
	 * 
	 * @param fileIsDel
	 *            the fileIsDel to set
	 */
	public void setFileIsDel(boolean fileIsDel) {
		this.fileIsDel = fileIsDel;
	}

}
