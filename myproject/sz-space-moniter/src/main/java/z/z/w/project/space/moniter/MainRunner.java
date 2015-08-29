/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.space.moniter.MainRunner.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-9-22 下午03:53:00 
 *   LastChange: 2013-9-22 下午03:53:00 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.space.moniter;

import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import z.z.w.project.common.config.Global;
import z.z.w.project.common.moniter.Moniter;
import z.z.w.project.space.moniter.config.SpaceMoniterConfig;
import z.z.w.project.util.common.DataTools;
import z.z.w.project.util.common.DateTools;
import z.z.w.project.util.common.LogTools;
import z.z.w.project.util.common.ThreadTools;
import z.z.w.project.util.file.FileTools;

/**
 * z.z.w.project.space.moniter.MainRunner.java
 */
public class MainRunner {
	static {
		// load log4j config xml
		if (DataTools.isEmpty(Global.LOG_CFG_FILE)
				|| !(new File(Global.LOG_CFG_FILE).exists())) {

			System.out.println("LOG4J CONFIG [" + Global.LOG_CFG_FILE
					+ "] NOT EXIST. PROGREAM EXIT!");
			System.exit(0);
		}

		LogTools.setLogBackDomConfig(Global.LOG_CFG_FILE);
	}

	static {
		// check Only one instance exec.
		if (!FileTools.tryLock(Global.LOCK_FILE)) {
			LogTools.getLogger(MainRunner.class).warn(
					"Another instance is running!");
			System.exit(0);
		}
	}

	/** Current server class simple name : server name */
	private final static String SERVER_NAME = MainRunner.class.getName();

	/** Server config */
	private final static String XML_CONF_NAME = "//server/adapter/type[@class='"
			+ SERVER_NAME + "']";

	/**
	 * <pre>
	 * <type name="SZSpaceMoniter" class="z.z.w.project.space.moniter.MainRunner"
	 * 			checkpath="D:/09_study/media/TAKARAZUKA" deletetime="1h" fileSuffix=".dat"
	 * 			timedelay="30s" filePrefix="ftbGnAppData" />
	 * </pre>
	 */

	private final static String XML_CONF_PRO_LOCAL_NAME = "checkpath";
	private final static String XML_CONF_PRO_PREFIX_NAME = "filePrefix";
	private final static String XML_CONF_PRO_SUFFIX_NAME = "fileSuffix";
	private final static String XML_CONF_PRO_TIME_NAME = "deletetime";
	private final static String XML_CONF_PRO_TIME_DELATY_NAME = "timedelay";

	private static int minSpaceSize = 10;
	private static final char UNIT = 'G';
	private static long newerTime = (8 * 60 * 60 * 1000);
	private long sleep = 1000 * 60 * 2;

	private String localPath = "";
	private String filePrefix = "";
	private String fileSuffix = "";

	/**
	 * <br>
	 * Created on: 2013-9-22 下午03:53:00
	 */
	public MainRunner() {
		super();
		init();

		(new Moniter()).start();

	}

	/**
	 * <br>
	 * Created on: 2013-9-22 下午04:08:44
	 */
	private void init() {
		localPath = SpaceMoniterConfig.getConfigValue(XML_CONF_NAME,
				XML_CONF_PRO_LOCAL_NAME);
		if (DataTools.isEmpty(localPath))
			return;

		filePrefix = SpaceMoniterConfig.getConfigValue(XML_CONF_NAME,
				XML_CONF_PRO_PREFIX_NAME);
		fileSuffix = SpaceMoniterConfig.getConfigValue(XML_CONF_NAME,
				XML_CONF_PRO_SUFFIX_NAME);

		newerTime = SpaceMoniterConfig.getLongTime(XML_CONF_NAME,
				XML_CONF_PRO_TIME_NAME);

		sleep = SpaceMoniterConfig.getLongTime(XML_CONF_NAME,
				XML_CONF_PRO_TIME_DELATY_NAME);

		LogTools.getLogger(getClass()).info("Start mointer : [{}].", localPath);
	}

	/**
	 * <br>
	 * Created on: 2013-9-22 下午03:53:00
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		MainRunner main = new MainRunner();
		main.startWork();
	}

	/**
	 * <br>
	 * Created on: 2013-9-22 下午04:08:28
	 */
	private void startWork() {

		while (true) {

			try {

				long freeSpace = FileTools.freeSpaceGB(localPath);

				LogTools.getLogger(getClass())
						.info("Current [{}] usable space :[{}{}],MIN_USEABLE_SPACE :[{}{}].",
								new Object[] { localPath, freeSpace, UNIT,
										minSpaceSize, UNIT });

				if (freeSpace > minSpaceSize)
					continue;

				LogTools.getLogger(getClass())
						.warn("File path :[{}],useable space :[{}{}], space over min useable space({}{}),start delete old files.",
								new Object[] { localPath, freeSpace, UNIT,
										minSpaceSize, UNIT });

				deleteOldFiles();
			} catch (Exception e) {
				LogTools.getLogger(getClass())
						.error("When [{} - {} --> Exception ] ,  \n  MASSAGE : {} \n  CAUSE :{} \n  CLASS : {}",
								new Object[] { "SpaceMoniter", "startWork()",
										e.getMessage(), e.getCause(),
										e.getClass() });
			} finally {
				ThreadTools.sleep(sleep);
			}
		}
	}

	/**
	 * <br>
	 * Created on: 2013-9-22 下午04:28:31
	 */
	private void deleteOldFiles() {
		try {
			Collection<File> fileList = getFileList();

			if (DataTools.isEmpty(fileList)) {
				LogTools.getLogger(getClass()).info(
						"Local path [{}] havn't files,wait next time check.",
						localPath);
				return;
			}

			LogTools.getLogger(getClass()).info(
					"Get local source file list size : [{}].", fileList.size());

			List<File> list = ((List<File>) fileList);

			// 按時間順序排序文件
			sortFiles(list);

			// start anaylse file
			anaylseFiles(list);

			if (DataTools.isEmpty(fileList))
				return;

			fileList.clear();
			fileList = null;

		} catch (Exception e) {
			LogTools.getLogger(getClass())
					.error("When [{} - {} --> Exception ] ,  \n  MASSAGE : {} \n  CAUSE :{} \n  CLASS : {}",
							new Object[] { "SpaceMoniter", "deleteOldFiles()",
									e.getMessage(), e.getCause(), e.getClass() });
		}

	}

	/**
	 * <br>
	 * Created on: 2013-9-22 下午04:36:20
	 * 
	 * @param list
	 */
	private void sortFiles(List<File> list) {
		Collections.sort(list, new Comparator<File>() {
			public int compare(File o1, File o2) {
				// gnApp_2013092216_117.dat
				String o1n = o1.getName();
				// 3G20130718180000.csv
				String o2n = o2.getName();

				return o1n.compareTo(o2n);
			}

		});
	}

	/**
	 * <br>
	 * Created on: 2013-9-22 下午04:35:48
	 * 
	 * @param list
	 */
	private void anaylseFiles(List<File> list) {
		Iterator<File> it = list.iterator();

		while (it.hasNext()) {
			File file = it.next();

			if (DataTools.isEmpty(file))
				continue;

			final String fileName = file.getAbsolutePath();

			try {

				if (!FileTools.delFile(file))
					LogTools.getLogger(getClass()).warn(
							"Detele old file :[{}] error!",
							file.getAbsoluteFile());

				file = null;
			} catch (Exception e) {
				LogTools.getLogger(getClass())
						.error("When [{} - {}] ,  \n  MASSAGE : {} \n  CAUSE :{} \n  CLASS : {}",
								new Object[] { getClass().getName(),
										"anaylseFiles() --> Exception()",
										e.getMessage(), e.getCause(),
										e.getClass() });
			} finally {
				file = null;
				LogTools.getLogger(getClass()).info("File : [{}] deleted!",
						fileName);
			}

		}

		list.clear();
		list = null;

	}

	/**
	 * <br>
	 * Created on: 2013-9-22 下午04:32:10
	 * 
	 * @return
	 */
	private Collection<File> getFileList() {

		String nameFix = filePrefix + "*" + fileSuffix;

		File path = FileTools.getFilePath(localPath);
		if (DataTools.isEmpty(path))
			return null;

		LogTools.getLogger(getClass()).info(
				"Check source data file : [{}/{}].", localPath, nameFix);

		long oldTime = DateTools.getCurrentDateToLong() - newerTime;
		LogTools.getLogger(getClass()).info("Old files time check : [{}=={}]",
				newerTime, oldTime);

		Collection<File> fileList = null;
		try {
			fileList = FileTools.getWildcardAndAgeFilesIncludeSubDirectory(
					localPath, nameFix, oldTime, true);
		} catch (Exception e) {
			LogTools.getLogger(getClass())
					.error("When [{} - {}] ,  \n  MASSAGE : {} \n  CAUSE :{} \n  CLASS : {}",
							new Object[] { getClass().getName(),
									"Read old files --> Exception()",
									e.getMessage(), e.getCause(), e.getClass() });
		}

		return fileList;

	}

}
