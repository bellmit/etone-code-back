/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.space.moniter.config.SpaceMoniterConfig.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-9-22 下午04:14:40 
 *   LastChange: 2013-9-22 下午04:14:40 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.space.moniter.config;

import org.dom4j.Node;

import z.z.w.project.common.config.Global;
import z.z.w.project.util.common.DataTools;
import z.z.w.project.util.common.DateTools;
import z.z.w.project.util.common.LogTools;
import z.z.w.project.util.common.XmlTools;

/**
 * z.z.w.project.space.moniter.config.SpaceMoniterConfig.java
 */
public abstract class SpaceMoniterConfig extends Global {

	/**
	 * <br>
	 * Created on: 2013-9-22 下午04:14:46
	 * 
	 * @param xmlConfName
	 * @param xmlConfProName
	 * @return
	 */
	public static String getConfigValue(String xmlConfName,
			String xmlConfProName) {

		try {
			openXml();

			Node node = XmlTools.getSingleNode(xmlConfName);

			String localPath = XmlTools
					.getSingleAttrValue(node, xmlConfProName);

			return localPath;
		} catch (Exception e) {
			LogTools.getLogger(SpaceMoniterConfig.class)
					.error("When [{} - {} --> Exception ] ,  \n  MASSAGE : {} \n  CAUSE :{} \n  CLASS : {}",
							new Object[] { "getConfigValue", "",
									e.getMessage(), e.getCause(), e.getClass() });
			return null;
		}

	}

	/**
	 * <br>
	 * Created on: 2013-9-22 下午04:14:52
	 * 
	 * @param xmlConfName
	 * @param xmlConfProTimeName
	 * @return
	 */
	public static long getLongTime(String xmlConfName, String xmlConfProTimeName) {
		String timeStr = getConfigValue(xmlConfName, xmlConfProTimeName);
		if (!DataTools.isEmpty(timeStr))
			return (DateTools.getTimeLongByString(timeStr));
		return 0;
	}

}
