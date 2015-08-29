/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.read.file.ServiceRuleReader.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-8-20 下午08:28:28 
 *   LastChange: 2013-8-20 下午08:28:28 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.read.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import z.z.w.common.DataTools;
import z.z.w.common.FileTools;
import z.z.w.common.ThreadTools;
import z.z.w.project.read.file.config.FileReaderConfig;
import z.z.w.project.read.file.vo.FileReader;
import z.z.w.project.util.common.LogTools;
import z.z.w.vo.Field;

/**
 * z.z.w.project.read.file.ServiceRuleReader.java
 */
public class ServiceRuleReader implements Runnable {

	/** Current server class simple name : server name */
	private final static String SERVER_NAME = ServiceRuleReader.class.getName();

	/** Server config */
	private final static String XML_CONF_NAME = "//server/adapter/type[@class='"
			+ SERVER_NAME + "']";

	private FileReader fileReader = null;

	/************************************************************/
	private String localPath = "";
	private String backPath = "";

	private String filePrefix = "";
	private String fileSuffix = "";

	private String fieldSplit = ",";

	private boolean isDelete = false;
	private boolean isBackup = false;

	private int startRow = 0;

	private long readInterval = 1000 * 10;

	private List<Field> fieldList = null;

	private int cdrLen = 10;

	/************************************************************/

	/**
	 * <br>
	 * Created on: 2013-8-20 下午08:28:28
	 */
	public ServiceRuleReader() {
		super();
		init();
	}

	/**
	 * <br>
	 * Created on: 2013-8-20 下午08:43:46
	 */
	private void init() {
		updateConfig();
	}

	/**
	 * <br>
	 * Created on: 2013-8-20 下午09:06:05
	 */
	private void updateConfig() {
		fileReader = FileReaderConfig.getFileReader(XML_CONF_NAME);

		if (DataTools.isEmpty(fileReader)) {
			LogTools.getLogger(getClass()).warn(
					"FileReader config is null,return");
			return;
		}

		setPropValue();

		setCdrIndex();
	}

	/**
	 * <br>
	 * Created on: 2013-8-20 下午09:28:00
	 */
	private void setCdrIndex() {
		int maxFieldListIndex = fieldList.size() - 1;
		Field field = fieldList.get(maxFieldListIndex);
		int maxFieldIndex = field.getIndex();
		if (maxFieldIndex >= cdrLen)
			cdrLen = maxFieldIndex + 1;
	}

	/**
	 * <br>
	 * Created on: 2013-8-20 下午09:00:25
	 */
	private void setPropValue() {
		this.localPath = this.fileReader.getLocalPath();
		this.backPath = this.fileReader.getBackPath();
		this.filePrefix = this.fileReader.getFilePrefix();
		this.fileSuffix = this.fileReader.getFileSuffix();
		this.fieldSplit = this.fileReader.getFieldSplit();
		this.isDelete = this.fileReader.isDelete();
		this.isBackup = this.fileReader.isBackup();
		this.startRow = this.fileReader.getStartRow();
		this.readInterval = this.fileReader.getReadInterval();
		this.fieldList = this.fileReader.getFieldList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	public void run() {

		while (true) {

			try {

				if (DataTools.isEmpty(fileReader))
					continue;

				Collection<File> fileList = getFileList();

				if (DataTools.isEmpty(fileList)) {
					LogTools.getLogger(getClass())
							.warn("Local path [{}] havn't files,wait next time check.",
									this.localPath);
					continue;
				}

				LogTools.getLogger(getClass()).info(
						"Get local source file list size : [{}].",
						fileList.size());

				List<File> list = ((List<File>) fileList);

				// start anaylse file
				anaylseFiles(list);

			} catch (Exception e) {
				LogTools.getLogger(getClass())
						.error("When [{} - {} --> Exception ] ,  \n  MASSAGE : {} \n  CAUSE :{} \n  CLASS : {}",
								new Object[] { "ServiceRuleReader", "run()",
										e.getMessage(), e.getCause(),
										e.getClass() });
			} finally {
				ThreadTools.sleep(this.readInterval);
				updateConfig();
			}
		}
	}

	/**
	 * <br>
	 * Created on: 2013-8-20 下午09:12:25
	 * 
	 * @param list
	 */
	private void anaylseFiles(List<File> list) {

		Iterator<File> it = list.iterator();

		while (it.hasNext()) {
			File file = it.next();

			if (DataTools.isEmpty(file) || !file.exists())
				continue;

			final long startTime = System.currentTimeMillis();

			// file size
			final long fileLen = FileUtils.sizeOf(file);

			final String fileName = file.getAbsolutePath();

			try {

				LogTools.getLogger(getClass()).info(
						"Start read & anaylse file :[{}].", fileName);

				if (fileLen == 0) {
					deleteFile(file);
					continue;
				}

				backFile(file);

				// read file content
				readFileContentToCache(file, fileName);

				// check del
				deleteFile(file);

				file = null;
			} catch (Exception e) {
				LogTools.getLogger(getClass())
						.error("When [{} - {} --> Exception ] ,  \n  MASSAGE : {} \n  CAUSE :{} \n  CLASS : {}",
								new Object[] { "ServiceRuleReader",
										"anaylseFiles()", e.getMessage(),
										e.getCause(), e.getClass() });
			} finally {
				file = null;

				long useTime = System.currentTimeMillis() - startTime;
				LogTools.getLogger(getClass())
						.info("Load source file [{}] complete, size :[{}]MB, use times :[{}]s.",
								new Object[] {
										fileName,
										DataTools
												.nmFormatToFloat(
														"%3.2f",
														((fileLen * 1.0 / 1024 / 1024))),
										(DataTools.nmFormatToFloat("%3.2f",
												(useTime * 1.0 / 1000))) });

			}

		}

		list.clear();
		list = null;
	}

	/**
	 * <br>
	 * Created on: 2013-8-20 下午09:20:21
	 * 
	 * @param file
	 * @param fileName
	 */
	private void readFileContentToCache(File file, String fileName) {
		BufferedReader input = null;

		try {

			input = new BufferedReader(new java.io.FileReader(file));
			String text = "";
			int index = 0;
			while (!DataTools.isEmpty((text = input.readLine()))) {

				if (index++ < this.startRow)
					continue;

				// construct obj instance
				if (!constructObjInstance(StringUtils.trimToEmpty(text),
						fileName))
					continue;

			}

			input.close();
			input = null;
		} catch (Exception e) {
			LogTools.getLogger(getClass())
					.error("When [{} - {} --> Exception ] ,  \n  MASSAGE : {} \n  CAUSE :{} \n  CLASS : {}",
							new Object[] { "ServiceRuleReader",
									"readFileContentToCache()", e.getMessage(),
									e.getCause(), e.getClass() });
		} finally {
			if (!DataTools.isEmpty(input)) {
				try {
					input.close();
					input = null;
				} catch (IOException e) {
				} finally {
					input = null;
				}

			}
		}
	}

	/**
	 * <br>
	 * Created on: 2013-8-20 下午09:23:35
	 * 
	 * @param trimToEmpty
	 * @param fileName
	 * @return
	 */
	private boolean constructObjInstance(String text, String fileName) {

		String[] cdrDataArr = new String[cdrLen];
		Arrays.fill(cdrDataArr, "");

		boolean constructFlag = constructCdrData(cdrDataArr, text);

		if (!constructFlag) {
			// source data format error moniter
			LogTools.getLogger(getClass()).debug(
					"Data error format : [{} - [{}]].", text, fileName);
			return false;
		}

		// TODO -- 2013-8-20 21:35:52 具體實現
		return true;
	}

	/**
	 * <br>
	 * Created on: 2013-8-20 下午09:29:03
	 * 
	 * @param cdrDataArr
	 * @param text
	 * @return
	 */
	private boolean constructCdrData(String[] cdrDataArr, String text) {
		String[] dataArr = text.split(fieldSplit, -1);
		if (dataArr.length != 4)
			return false;

		try {
			Iterator<Field> it = fieldList.iterator();
			while (it.hasNext()) {
				Field field = it.next();
				try {
					int idx = field.getSrcindex();
					if (idx != -1)
						cdrDataArr[field.getIndex()] = dataArr[idx];
				} catch (Exception e) {
					cdrDataArr = null;
					break;
				}
			}
		} finally {
			dataArr = null;
			cdrDataArr = null;
		}

		return true;
	}

	/**
	 * <br>
	 * Created on: 2013-8-20 下午09:14:20
	 * 
	 * @param file
	 */
	private void backFile(File file) {
		if (isBackup) {
			File destFile = new File(this.backPath + file.getName());
			try {
				FileTools.copyFile(file, destFile);
			} catch (IOException e) {
				LogTools.getLogger(getClass()).warn(
						"File [{}] backup to [{}] failed.",
						file.getAbsolutePath(), this.backPath);
			}
		}
	}

	/**
	 * <br>
	 * Created on: 2013-8-20 下午09:13:31
	 * 
	 * @param file
	 */
	private void deleteFile(File file) {
		if (isDelete)
			if (!FileTools.delFile(file))
				LogTools.getLogger(getClass()).warn("File [{}] delete failed.",
						file.getAbsolutePath());
	}

	/**
	 * <br>
	 * Created on: 2013-8-20 下午09:07:33
	 * 
	 * @return
	 */
	private Collection<File> getFileList() {

		String nameFix = filePrefix + "*" + fileSuffix;

		File path = FileTools.getFilePath(localPath);
		if (DataTools.isEmpty(path))
			return null;

		LogTools.getLogger(getClass()).info("Check source data file : [{}{}].",
				localPath, nameFix);

		Collection<File> fileList = null;
		try {
			fileList = FileTools.getWildcardAndNewerFiles(localPath, nameFix,
					0, false);
		} catch (Exception e) {
			LogTools.getLogger(getClass())
					.error("When [{} - {} --> Exception ] ,  \n  MASSAGE : {} \n  CAUSE :{} \n  CLASS : {}",
							new Object[] { "Read localpath get files",
									"getFileList()", e.getMessage(),
									e.getCause(), e.getClass() });
		}

		return fileList;
	}

}
