/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.test.writefile.WriterOperator.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-9-5 上午09:57:07 
 *   LastChange: 2013-9-5 上午09:57:07 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.test.writefile;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import z.z.w.common.DataTools;
import z.z.w.common.DateTools;
import z.z.w.common.FileOperUtil;
import z.z.w.common.FileTools;
import z.z.w.project.main.config.Global;
import z.z.w.project.test.writefile.vo.FileWritor;
import z.z.w.project.test.writefile.vo.LimitInfo;
import z.z.w.project.util.common.LogTools;

/**
 * z.z.w.project.test.writefile.WriterOperator.java
 */
public class WriterOperator {

	private final static String WRITTING_FILE_SUFFIX = ".csv";
	private final static int CHECK_DELAY_SIZE = Global.getDelaySizeMax();
	private final static int CHECK_DELAY_TIME_MAX = Global.getDelayTimeMax();
	private final static String FILE_TITLE_2G = "小时,IMSI,IMEI,LAC,CI,业务类型,服务器域名,端口,User_Agent_Main,时长(ms),流量(KB)";
	private final static String FILE_TITLE_3G = "小时,IMSI,IMEI,LAC,SAC,业务类型,服务器域名,端口,User_Agent_Main,时长(ms),流量(KB)";

	private final FileWritor fileWritor;
	protected char fieldSplit = ((char) 44);
	protected char rowSplit = ((char) 13);
	protected String localPath = "";
	private String fileSuffix = ".cvs";
	private long delayTime = 26666;
	private int buffSize = 182828;

	private Map<String, LimitInfo> fileInfoMap = null;
	private int checkDelaySize = 0;
	private String fileKeyData = "";
	private String intNetType = "";

	/**
	 * <br>
	 * Created on: 2013-9-5 上午11:44:43
	 * 
	 * @param fileKeyData
	 *            the fileKeyData to set
	 */
	public void setFileKeyData(String fileKeyData) {
		this.fileKeyData = getStringByCDRType(fileKeyData);
	}

	/**
	 * <br>
	 * Created on: 2013-9-5 下午02:04:14
	 * 
	 * @param fileKeyData2
	 * @return
	 */
	private String getStringByCDRType(String fileName) {
		if (intNetType.equals("1")) {
			fileName = "3G" + fileName + "0000";
		} else {
			fileName = "2G" + fileName + "0000";
		}

		return fileName;
	}

	/**
	 * <br>
	 * Created on: 2013-9-5 上午11:44:43
	 * 
	 * @param intNetType
	 *            the intNetType to set
	 */
	public void setIntNetType(String intNetType) {
		this.intNetType = intNetType;
	}

	/**
	 * <br>
	 * Created on: 2013-9-5 上午10:17:18
	 * 
	 * @param fileWritor
	 */
	public WriterOperator(FileWritor fileWritor) {
		super();
		this.fileWritor = fileWritor;
		init();
	}

	/**
	 * <br>
	 * Created on: 2013-9-5 上午10:17:27
	 */
	private void init() {
		setPropValue();

		renameOldFiles();
	}

	/**
	 * <br>
	 * Created on: 2013-9-5 上午10:23:38
	 */
	private void renameOldFiles() {
		Collection<File> fileList = getFileList();
		if (DataTools.isEmpty(fileList))
			return;

		Iterator<File> it = fileList.iterator();
		while (it.hasNext()) {
			File file = it.next();
			File dest = new File(file.getAbsolutePath().replace(
					WRITTING_FILE_SUFFIX, fileSuffix));

			renameOldFile(file, dest);
		}
	}

	/**
	 * <br>
	 * Created on: 2013-9-5 上午10:25:33
	 * 
	 * @param file
	 * @param dest
	 */
	private void renameOldFile(File file, File dest) {
		try {
			FileTools.moveFile(file, dest);
		} catch (Exception e) {
			FileTools.delFile(file);
		}
	}

	/**
	 * <br>
	 * Created on: 2013-9-5 上午10:24:15
	 * 
	 * @return
	 */
	private Collection<File> getFileList() {
		File path = FileTools.getFilePath(localPath);
		if (DataTools.isEmpty(path))
			return null;

		LogTools.getLogger(getClass()).info(
				"Rename old file path : [{}], name fix : [*{}].", localPath,
				WRITTING_FILE_SUFFIX);

		Collection<File> fileList = null;

		try {

			fileList = FileTools.getSuffixAndNewerFiles(localPath,
					WRITTING_FILE_SUFFIX, 0, false);

		} catch (Exception e) {
			LogTools.getLogger(getClass())
					.error("When [{} - {}] , error : \n MASSAGE : {} \n CAUSE :{} \n CLASS : {}\n",
							new Object[] {
									this.getClass().getName(),
									" Read rename old file:getFileList() --> Exception() ",
									e.getMessage(), e.getCause(), e.getClass() });
		}

		return fileList;
	}

	/**
	 * <br>
	 * Created on: 2013-9-5 上午10:18:09
	 */
	private void setPropValue() {
		fileInfoMap = new HashMap<String, LimitInfo>();
		setSpilt();
		localPath = fileWritor.getLocalPath();
		fileSuffix = fileWritor.getFileSuffix();
		delayTime = fileWritor.getDelayTime();
		buffSize = fileWritor.getBuffSize();

	}

	/**
	 * <br>
	 * Created on: 2013-9-5 上午10:20:38
	 */
	private void setSpilt() {
		String fs = fileWritor.getFieldSplit();
		String rs = fileWritor.getRowSplit();

		/**************************************************************/
		try {
			fieldSplit = DataTools.getCharByHexStr(fs);
		} catch (Exception e) {
			fieldSplit = 44;
		}
		try {
			rowSplit = DataTools.getCharByHexStr(rs);
		} catch (Exception e) {
			rowSplit = 10;
		}
		/**************************************************************/
	}

	/**
	 * <br>
	 * Created on: 2013-9-5 上午11:30:10
	 * 
	 * @param finData
	 */
	public void writeDataToFile(String finData) {
		try {

			if (DataTools.isEmpty(finData))
				return;

			checkFlush();

			LimitInfo limitInfo = getLimitInfo();

			if (DataTools.isEmpty(limitInfo))
				return;

			writeDataToFlush(finData, limitInfo);

		} catch (Exception e) {
			LogTools.getLogger(getClass())
					.error("When [{} - {}] , error : \n MASSAGE : {} \n CAUSE :{} \n CLASS : {}\n",
							new Object[] { this.getClass().getName(),
									" write data oper --> writeFile() ",
									e.getMessage(), e.getCause(), e.getClass() });
		} finally {
		}
	}

	/**
	 * <br>
	 * Created on: 2013-9-5 上午11:48:00
	 * 
	 * @param finData
	 * @param limitInfo
	 */
	private void writeDataToFlush(String finData, LimitInfo limitInfo) {
		try {
			limitInfo.getFileOperUtil().writeString(finData);
			limitInfo.addSize(1);
		} catch (Exception e) {
			LogTools.getLogger(getClass())
					.error("When [{} - {}] , error : \n MASSAGE : {} \n CAUSE :{} \n CLASS : {}\n",
							new Object[] {
									this.getClass().getName(),
									" write data to flush --> writeDataToFlush() ",
									e.getMessage(), e.getCause(), e.getClass() });
			limitInfo.setFileOperUtil(null);
		}
	}

	/**
	 * <br>
	 * Created on: 2013-9-5 上午11:34:39
	 * 
	 * @return
	 */
	private LimitInfo getLimitInfo() {
		try {
			if (!fileInfoMap.containsKey(fileKeyData)) {
				return newLimitInfoInstance();
			}

			LimitInfo limitInfo = fileInfoMap.get(fileKeyData);

			if (DataTools.isEmpty(limitInfo.getFileOperUtil())) {
				initLimitInfo(limitInfo);
			}

			return limitInfo;
		} catch (Exception e) {
			LogTools.getLogger(getClass())
					.error("When [{} - {}] , error : \n MASSAGE : {} \n CAUSE :{} \n CLASS : {}\n",
							new Object[] { this.getClass().getName(),
									"create LimitInfo --> getLimitInfo() ",
									e.getMessage(), e.getCause(), e.getClass() });
			return null;
		}
	}

	/**
	 * <br>
	 * Created on: 2013-9-5 上午11:37:42
	 * 
	 * @return
	 */
	private LimitInfo newLimitInfoInstance() {
		try {
			LimitInfo limitInfo = new LimitInfo();
			initLimitInfo(limitInfo);
			String title = "";
			if (intNetType.equals("1")) {
				title = FILE_TITLE_3G + rowSplit;
			} else /* if (intNetType.equals(ReceiverConfig.CDR_TYPE_2G_NUM)) */{
				title = FILE_TITLE_2G + rowSplit;
			}

			writeDataToFlush(title, limitInfo);

			return limitInfo;
		} catch (Exception e) {
			LogTools.getLogger(getClass())
					.error("When [{} - {}] , error : \n MASSAGE : {} \n CAUSE :{} \n CLASS : {}\n",
							new Object[] {
									this.getClass().getName(),
									"ftp@Override LimitInfo --> newLimitInfoInstance() ",
									e.getMessage(), e.getCause(), e.getClass() });
			return null;
		}
	}

	/**
	 * <br>
	 * Created on: 2013-9-5 上午11:38:02
	 * 
	 * @param limitInfo
	 */
	private void initLimitInfo(LimitInfo limitInfo) {
		try {
			limitInfo.setSize(0);
			limitInfo.setDataTime(System.currentTimeMillis());
			limitInfo.setFileOperUtil(createFileOperUtil());

			fileInfoMap.put(fileKeyData, limitInfo);
		} catch (Exception e) {
			LogTools.getLogger(getClass())
					.error("When [{} - {}] , error : \n MASSAGE : {} \n CAUSE :{} \n CLASS : {}\n",
							new Object[] { this.getClass().getName(),
									"create LimitInfo --> initLimitInfo() ",
									e.getMessage(), e.getCause(), e.getClass() });
		}
	}

	/**
	 * <br>
	 * Created on: 2013-9-5 上午11:38:13
	 * 
	 * @return
	 */
	private FileOperUtil createFileOperUtil() {
		FileOperUtil fileOperUtil = null;
		try {
			String fileName = getFileName();
			fileOperUtil = FileOperUtil.getInstance(fileName);
			fileOperUtil.createFile();
			fileOperUtil.openFileWriter("utf-8");

		} catch (Exception e) {
			LogTools.getLogger(getClass())
					.error("When [{} - {}] , error : \n MASSAGE : {} \n CAUSE :{} \n CLASS : {}\n",
							new Object[] {
									this.getClass().getName(),
									" create file oper util instance --> createFileOperUtil() ",
									e.getMessage(), e.getCause(), e.getClass() });
		}

		return fileOperUtil;
	}

	/**
	 * <br>
	 * Created on: 2013-9-5 上午11:38:24
	 * 
	 * @return
	 */
	private String getFileName() {
		String fileName = getBaseName();
		fileName = FileTools.formatPath(localPath) + fileName;
		return fileName;
	}

	/**
	 * <br>
	 * Created on: 2013-9-5 上午11:38:59
	 * 
	 * @return
	 */
	private String getBaseName() {
		return fileKeyData + WRITTING_FILE_SUFFIX;
	}

	/**
	 * <br>
	 * Created on: 2013-9-5 上午11:34:06
	 */
	private void checkFlush() {
		try {

			if (checkDelaySize++ > CHECK_DELAY_SIZE) {
				checkDelaySize = 0;

				long currentDateTime = DateTools.getCurrentDateToLong();

				Set<String> keySet = fileInfoMap.keySet();
				Iterator<String> it = keySet.iterator();

				while (it.hasNext()) {
					try {

						String dataTimeStr = it.next();
						LimitInfo limitInfo = fileInfoMap.get(dataTimeStr);

						// max size check & file delay check
						long interval = (currentDateTime - limitInfo
								.getDataTime());

						LogTools.getLogger(getClass())
								.debug("Time Key : [{}],[{}>{}({})]==[{}>{}||{}>{}({})]--[{}==last file : {}].",
										new Object[] {
												dataTimeStr,
												interval,
												CHECK_DELAY_TIME_MAX,
												(interval > CHECK_DELAY_TIME_MAX),
												interval,
												delayTime,
												limitInfo.getSize(),
												buffSize,
												((interval > delayTime) || limitInfo
														.getSize() > buffSize),
												((DataTools.isEmpty(limitInfo)) ? "limitInfo is null."
														: (DataTools
																.isEmpty(limitInfo
																		.getFileOperUtil())) ? "fileOperUtil is null."
																: limitInfo
																		.getFileOperUtil()
																		.getFileName()),
												limitInfo.getLastFileName() });

						if (interval > CHECK_DELAY_TIME_MAX) {

							String lastFileName = "";

							FileOperUtil fileOperUtil = limitInfo
									.getFileOperUtil();

							if (!DataTools.isEmpty(fileOperUtil))
								lastFileName = fileOperUtil.getFileName();

							if (DataTools.isEmpty(lastFileName))
								lastFileName = limitInfo.getLastFileName();

							// if (!DataTools.isEmpty(lastFileName))
							// renameFinFile(lastFileName);

							LogTools.getLogger(getClass())
									.info("Remove delay time data map limitInfo ,fileName:[[{}]-[{}]].",
											new Object[] { lastFileName,
													dataTimeStr });

							it.remove();

							// if (!checkPathFreeSpace())
							// return false;

							continue;
						}

						int liSize = limitInfo.getSize();
						if ((interval > delayTime) || liSize > buffSize) {

							FileOperUtil fileOperUtil = limitInfo
									.getFileOperUtil();
							if ((limitInfo.getDataTime() != 0)
									&& !DataTools.isEmpty(fileOperUtil)) {

								renameFinFileNameAndFlushFinFile(fileOperUtil,
										limitInfo);

								limitInfo.setFileOperUtil(null);
								limitInfo.setSize(0);

							}

						}

					} catch (Exception e) {
						LogTools.getLogger(getClass())
								.error("When [{} - {}] , error : \n MASSAGE : {} \n CAUSE :{} \n CLASS : {}\n",
										new Object[] {
												this.getClass().getName(),
												"check flush time & flush size --> checkFlush() ",
												e.getMessage(), e.getCause(),
												e.getClass() });
					}

				}
			}
			// return true;
		} catch (Exception e) {
			LogTools.getLogger(getClass())
					.error("When [{} - {}] , error : \n MASSAGE : {} \n CAUSE :{} \n CLASS : {}\n",
							new Object[] { this.getClass().getName(),
									"checkFlush --> checkFlush() ",
									e.getMessage(), e.getCause(), e.getClass() });
		} finally {
			// ThreadTools.sleep(10);
		}
	}

	/**
	 * <br>
	 * Created on: 2013-9-5 上午11:49:14
	 * 
	 * @param fileOperUtil
	 * @param limitInfo
	 */
	private void renameFinFileNameAndFlushFinFile(FileOperUtil fileOperUtil,
			LimitInfo limitInfo) {
		try {
			limitInfo
					.setLastFileName(limitInfo.getFileOperUtil().getFileName());
			fileOperUtil.closeFileWriter();
		} catch (Exception e) {
			LogTools.getLogger(getClass())
					.error("When [{} -{}] , error : \n MASSAGE : {} \n CAUSE :{} \n CLASS : {}\n",
							new Object[] {
									this.getClass().getName(),
									"flush the file writer  --> writerFlush() ",
									e.getMessage(), e.getCause(), e.getClass() });
		}
	}

	/**
	 * <br>
	 * Created on: 2013-9-5 上午11:48:15
	 * 
	 * @param fileName
	 */
	// private void renameFinFile(String fileName) {
	// File file = new File(FileTools.formatPath(localPath) + fileName);
	//
	// if (!file.exists())
	// return;
	//
	// File dest = new File(FileTools.formatPath(localPath)
	// + fileName.replace(WRITTING_FILE_SUFFIX, fileSuffix));
	//
	// try {
	// FileTools.moveFile(file, dest);
	// } catch (Exception e) {
	// try {
	// FileTools.delFile(dest);
	// FileTools.moveFile(file, dest);
	// } catch (Exception e1) {
	// LogTools.getLogger(getClass())
	// .error("When [{} - {}] , error : \n MASSAGE : {} \n CAUSE :{} \n CLASS : {}\n",
	// new Object[] {
	// this.getClass().getName(),
	// "delete old fin file error --> renameFinFile() ",
	// e.getMessage(), e.getCause(),
	// e.getClass() });
	// }
	// }
	// }

}
