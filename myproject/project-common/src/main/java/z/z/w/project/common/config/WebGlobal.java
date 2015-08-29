/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.common.config.WebGlobal.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-10-8 下午10:54:46 
 *   LastChange: 2013-10-8 下午10:54:46 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.common.config;

import org.dom4j.Node;

import z.z.w.project.util.common.DateTools;
import z.z.w.project.util.common.LogTools;
import z.z.w.project.util.common.XmlTools;
import z.z.w.project.util.env.WebConfig;

/**
 * z.z.w.project.common.config.WebGlobal.java
 */
public abstract class WebGlobal {
	public static final WebConfig CONFIG = new WebConfig();

	// FileTools.class.getClassLoader().getResource(fileName).getPath();
	public static final String LOG_CFG_FILE = (CONFIG.getString("log4j-cfg"));

	private static final String SERVER_CFG_FILE = WebConfig.class
			.getClassLoader().getResource(CONFIG.getString("server-xml"))
			.getPath();

	private static boolean isServerXmlOpen = false;

	/** db config */
	public static final String DB_CFG_FILE = (CONFIG.getString("jdbc-cfg"));

	/**
	 * 
	 * <br>
	 * Created on: 2013-9-22 下午04:16:08
	 */
	public static void openXml() {
		if (!isServerXmlOpen)
			if (XmlTools.openXml(SERVER_CFG_FILE)) {
				isServerXmlOpen = true;
				LogTools.getLogger(WebGlobal.class).debug(
						"SERVER_CFG_FILE : [{}]", SERVER_CFG_FILE);
			} else
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
			LogTools.getLogger(WebGlobal.class)
					.warn("When [{} - {} --> Exception ] ,  \n  MASSAGE : {} \n  CAUSE :{} \n  CLASS : {}",
							new Object[] { "getTimeInterval", "",
									e.getMessage(), e.getCause(), e.getClass() });
			return defaultValue;
		}

	}
}
