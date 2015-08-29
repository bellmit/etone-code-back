/**
 * com.etone.nadap.server.buss.dto.LoadSourceDataDto.java
 */
package z.z.w.others;


/**
 * @author Wu Zhenzhen
 * @version Dec 3, 2012 3:33:14 PM
 */
public class LoadSourceDataFromFileDto extends ServerClassCreator {
	private String fileDir;
	private String filePrefix;
	private String fileSuffix;
	private String fieldSplit;
	private String recordSplit;
	private int scanInterval;
	private long maxQueueSize;
	private boolean fileBak;
	private boolean fileDel;

	/**
	 * <br>
	 * Created on: Dec 3, 2012 4:36:21 PM
	 */
	public LoadSourceDataFromFileDto() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "LoadSourceDataDto [fileDir=" + fileDir + ", filePrefix="
				+ filePrefix + ", fileSuffix=" + fileSuffix + ", fieldSplit="
				+ fieldSplit + ", recordSplit=" + recordSplit
				+ ", scanInterval=" + scanInterval + ", maxQueueSize="
				+ maxQueueSize + ", fileBak=" + fileBak + ", fileDel="
				+ fileDel + ", toString()=" + super.toString() + "]";
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
				+ ((fieldSplit == null) ? 0 : fieldSplit.hashCode());
		result = prime * result + (fileBak ? 1231 : 1237);
		result = prime * result + (fileDel ? 1231 : 1237);
		result = prime * result + ((fileDir == null) ? 0 : fileDir.hashCode());
		result = prime * result
				+ ((filePrefix == null) ? 0 : filePrefix.hashCode());
		result = prime * result
				+ ((fileSuffix == null) ? 0 : fileSuffix.hashCode());
		result = prime * result + (int) (maxQueueSize ^ (maxQueueSize >>> 32));
		result = prime * result
				+ ((recordSplit == null) ? 0 : recordSplit.hashCode());
		result = prime * result + scanInterval;
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
		LoadSourceDataFromFileDto other = (LoadSourceDataFromFileDto) obj;
		if (fieldSplit == null) {
			if (other.fieldSplit != null)
				return false;
		} else if (!fieldSplit.equals(other.fieldSplit))
			return false;
		if (fileBak != other.fileBak)
			return false;
		if (fileDel != other.fileDel)
			return false;
		if (fileDir == null) {
			if (other.fileDir != null)
				return false;
		} else if (!fileDir.equals(other.fileDir))
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
		if (maxQueueSize != other.maxQueueSize)
			return false;
		if (recordSplit == null) {
			if (other.recordSplit != null)
				return false;
		} else if (!recordSplit.equals(other.recordSplit))
			return false;
		if (scanInterval != other.scanInterval)
			return false;
		return true;
	}

	/**
	 * <br>
	 * Created on: Dec 3, 2012 4:36:30 PM
	 * 
	 * @return the fileDir
	 */
	public String getFileDir() {
		return fileDir;
	}

	/**
	 * <br>
	 * Created on: Dec 3, 2012 4:36:30 PM
	 * 
	 * @param fileDir
	 *            the fileDir to set
	 */
	public void setFileDir(String fileDir) {
		this.fileDir = fileDir;
	}

	/**
	 * <br>
	 * Created on: Dec 3, 2012 4:36:30 PM
	 * 
	 * @return the filePrefix
	 */
	public String getFilePrefix() {
		return filePrefix;
	}

	/**
	 * <br>
	 * Created on: Dec 3, 2012 4:36:30 PM
	 * 
	 * @param filePrefix
	 *            the filePrefix to set
	 */
	public void setFilePrefix(String filePrefix) {
		this.filePrefix = filePrefix;
	}

	/**
	 * <br>
	 * Created on: Dec 3, 2012 4:36:30 PM
	 * 
	 * @return the fileSuffix
	 */
	public String getFileSuffix() {
		return fileSuffix;
	}

	/**
	 * <br>
	 * Created on: Dec 3, 2012 4:36:30 PM
	 * 
	 * @param fileSuffix
	 *            the fileSuffix to set
	 */
	public void setFileSuffix(String fileSuffix) {
		this.fileSuffix = fileSuffix;
	}

	/**
	 * <br>
	 * Created on: Dec 3, 2012 4:36:30 PM
	 * 
	 * @return the fieldSplit
	 */
	public String getFieldSplit() {
		return fieldSplit;
	}

	/**
	 * <br>
	 * Created on: Dec 3, 2012 4:36:30 PM
	 * 
	 * @param fieldSplit
	 *            the fieldSplit to set
	 */
	public void setFieldSplit(String fieldSplit) {
		this.fieldSplit = fieldSplit;
	}

	/**
	 * <br>
	 * Created on: Dec 3, 2012 4:36:30 PM
	 * 
	 * @return the recordSplit
	 */
	public String getRecordSplit() {
		return recordSplit;
	}

	/**
	 * <br>
	 * Created on: Dec 3, 2012 4:36:30 PM
	 * 
	 * @param recordSplit
	 *            the recordSplit to set
	 */
	public void setRecordSplit(String recordSplit) {
		this.recordSplit = recordSplit;
	}

	/**
	 * <br>
	 * Created on: Dec 3, 2012 4:36:30 PM
	 * 
	 * @return the scanInterval
	 */
	public int getScanInterval() {
		return scanInterval;
	}

	/**
	 * <br>
	 * Created on: Dec 3, 2012 4:36:30 PM
	 * 
	 * @param scanInterval
	 *            the scanInterval to set
	 */
	public void setScanInterval(int scanInterval) {
		this.scanInterval = scanInterval;
	}

	/**
	 * <br>
	 * Created on: Dec 3, 2012 4:36:30 PM
	 * 
	 * @return the maxQueueSize
	 */
	public long getMaxQueueSize() {
		return maxQueueSize;
	}

	/**
	 * <br>
	 * Created on: Dec 3, 2012 4:36:30 PM
	 * 
	 * @param maxQueueSize
	 *            the maxQueueSize to set
	 */
	public void setMaxQueueSize(long maxQueueSize) {
		this.maxQueueSize = maxQueueSize;
	}

	/**
	 * <br>
	 * Created on: Dec 3, 2012 4:36:30 PM
	 * 
	 * @return the fileBak
	 */
	public boolean isFileBak() {
		return fileBak;
	}

	/**
	 * <br>
	 * Created on: Dec 3, 2012 4:36:30 PM
	 * 
	 * @param fileBak
	 *            the fileBak to set
	 */
	public void setFileBak(boolean fileBak) {
		this.fileBak = fileBak;
	}

	/**
	 * <br>
	 * Created on: Dec 3, 2012 4:36:30 PM
	 * 
	 * @return the fileDel
	 */
	public boolean isFileDel() {
		return fileDel;
	}

	/**
	 * <br>
	 * Created on: Dec 3, 2012 4:36:30 PM
	 * 
	 * @param fileDel
	 *            the fileDel to set
	 */
	public void setFileDel(boolean fileDel) {
		this.fileDel = fileDel;
	}

}
