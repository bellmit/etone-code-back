/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.common.config.Global.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-9-22 下午03:58:19 
 *   LastChange: 2013-9-22 下午03:58:19 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.common.config;

import org.dom4j.Node;

import z.z.w.project.util.common.DateTools;
import z.z.w.project.util.common.LogTools;
import z.z.w.project.util.common.XmlTools;
import z.z.w.project.util.env.Config;
import z.z.w.project.util.file.FileTools;

/**
 * z.z.w.project.common.config.Global.java
 */
public abstract class Global {
	public static final Config CONFIG = new Config();

	public static final String LOG_CFG_FILE = FileTools.replaceProHome(CONFIG
			.getString("log4j-cfg"));

	public static final String LOCK_FILE = FileTools.replaceProHome(CONFIG
			.getString("lock-file"));

	public static final String SPRING_XML = FileTools.replaceProHome(CONFIG
			.getString("spring-xml"));

	private static final String SERVER_CFG_FILE = FileTools
			.replaceProHome(CONFIG.getString("server-xml"));

	private static boolean isServerXmlOpen = false;

	/** db config */
	public static final String DB_CFG_FILE = FileTools.replaceProHome(CONFIG
			.getString("jdbc-cfg"));

	/**
	 * 
	 * <br>
	 * Created on: 2013-9-22 下午04:16:08
	 */
	public static void openXml() {
		if (!isServerXmlOpen)
			if (XmlTools.openXml(SERVER_CFG_FILE))
				isServerXmlOpen = true;
			else
				isServerXmlOpen = false;
	}

	/**
	 * <br>
	 * Created on: 2013-9-22 下午04:52:23
	 * 
	 * @param nodeName
	 * @param nodeAttrName
	 * @param defaultValue
	 * @return
	 */
	public static long getTimeInterval(String nodeName, String nodeAttrName,
			int defaultValue) {
		try {
			openXml();

			Node node = XmlTools.getSingleNode(nodeName);

			String intervalStr = XmlTools
					.getSingleAttrValue(node, nodeAttrName);

			long interval = DateTools.getTimeLongByString(intervalStr);

			return interval;
		} catch (Exception e) {
			LogTools.getLogger(Global.class)
					.warn("When [{} - {} --> Exception ] ,  \n  MASSAGE : {} \n  CAUSE :{} \n  CLASS : {}",
							new Object[] { "getTimeInterval", "",
									e.getMessage(), e.getCause(), e.getClass() });
			return defaultValue;
		}

	}

	/**
	 * <br>
	 * Created on: 2013-11-7 下午09:47:21
	 * 
	 * @param simpleName
	 * @return
	 */
	public static String getLockFile(String simpleName) {
		return Global.LOCK_FILE + "_" + simpleName;
	}
}
