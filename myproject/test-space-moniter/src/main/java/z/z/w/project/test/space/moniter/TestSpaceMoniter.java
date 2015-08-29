/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.test.space.moniter.TestSpaceMoniter.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-8-23 上午11:33:07 
 *   LastChange: 2013-8-23 上午11:33:07 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.test.space.moniter;

import java.io.File;

import z.z.w.common.DataTools;
import z.z.w.common.ThreadTools;
import z.z.w.project.test.space.moniter.config.TestSpaceMoniterConfig;
import z.z.w.project.util.common.LogTools;

/**
 * z.z.w.project.test.space.moniter.TestSpaceMoniter.java
 */
public class TestSpaceMoniter implements Runnable {
	/** Current server class simple name : server name */
	private final static String SERVER_NAME = TestSpaceMoniter.class.getName();

	/** Server config */
	private final static String XML_CONF_NAME = "//server/adapter/type[@class='"
			+ SERVER_NAME + "']";

	private String localPath = "";

	private String usableSpace = "20g";

	private char unit = 'g';

	/**
	 * <br>
	 * Created on: 2013-8-23 上午11:33:07
	 */
	public TestSpaceMoniter() {
		super();
		init();
	}

	/**
	 * <br>
	 * Created on: 2013-8-23 上午11:34:12
	 */
	private void init() {
		localPath = TestSpaceMoniterConfig.getLocalPath(XML_CONF_NAME);
		usableSpace = TestSpaceMoniterConfig.getUsableSpace(XML_CONF_NAME);
		if (DataTools.isEmpty(localPath))
			return;

		unit = usableSpace.charAt(usableSpace.length() - 1);
		LogTools.getLogger(getClass()).debug("Usable space:[{}],unit:[{}].",
				usableSpace, unit);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	public void run() {

		while (true) {
			try {
				File file = new File(localPath);

				if (DataTools.isEmpty(file)) {
					LogTools.getLogger(getClass()).warn(
							"File system : [{}] is not exists!");
					continue;
				}

				long ss = file.getUsableSpace();
				String spaceSize = DataTools.formatDataSize(ss);
				LogTools.getLogger(getClass()).info(
						"Usable space:[{}], config usable space:[{}].",
						spaceSize, usableSpace);
				LogTools.getLogger(getClass()).info(
						"Free space:[{}], config usable space:[{}].",
						DataTools.formatDataSize(file.getFreeSpace()),
						usableSpace);
				String totalSize = DataTools.formatDataSize(file
						.getTotalSpace());
				LogTools.getLogger(getClass()).info(
						"Total driver space size:[{}].", totalSize);

				spaceSize = DataTools.formatDataSize(ss, unit);
				LogTools.getLogger(getClass()).info(
						"Usable space:[{}], config usable space:[{}].",
						spaceSize, usableSpace);

				if (spaceSize.compareTo(usableSpace) > 0)
					continue;

				// delete old files
				LogTools.getLogger(getClass()).warn(
						"Usable space is small.delete old files!");

			} catch (Exception e) {
				LogTools.getLogger(getClass())
						.error("When [{} - {} --> Exception ] ,  \n  MASSAGE : {} \n  CAUSE :{} \n  CLASS : {}",
								new Object[] { "TestSpaceMoniter", "run()",
										e.getMessage(), e.getCause(),
										e.getClass() });
			} finally {
				ThreadTools.sleepSeconds(10);
			}
		}

	}

}
