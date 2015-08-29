/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.test.space.moniter.config.TestSpaceMoniterConfig.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-8-23 上午11:34:37 
 *   LastChange: 2013-8-23 上午11:34:37 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.test.space.moniter.config;

import org.dom4j.Node;

import z.z.w.common.XmlTools;
import z.z.w.project.main.config.Global;
import z.z.w.project.util.common.LogTools;

/**
 * z.z.w.project.test.space.moniter.config.TestSpaceMoniterConfig.java
 */
public abstract class TestSpaceMoniterConfig extends Global {

	/**
	 * <br>
	 * Created on: 2013-8-23 上午11:34:37
	 */
	private TestSpaceMoniterConfig() {
	}

	/**
	 * <br>
	 * Created on: 2013-8-23 上午11:35:08
	 * 
	 * @param xmlConfName
	 * @return
	 */
	public static String getLocalPath(String xmlConfName) {
		try {

			openXml();

			Node node = XmlTools.getSingleNode(xmlConfName);

			/**
			 * <pre>
			 * 			<adapter name="TestSpaceMoniter" priority="10">
			 * 		<type name="TestSpaceMoniter" class="TestSpaceMoniter" local="etc/gn" />
			 * 	</adapter>
			 * 		</type>
			 * </pre>
			 */

			String localPath = XmlTools.getSingleAttrValue(node, "local");

			closeXml();

			return localPath;

		} catch (Exception e) {
			LogTools.getLogger(TestSpaceMoniterConfig.class)
					.error("When [{} - {} --> Exception ] ,  \n  MASSAGE : {} \n  CAUSE :{} \n  CLASS : {}",
							new Object[] { "getLocalPath", "", e.getMessage(),
									e.getCause(), e.getClass() });
			return null;
		}
	}

	/**
	 * <br>
	 * Created on: 2013-8-26 下午04:31:31
	 * 
	 * @param xmlConfName
	 * @return
	 */
	public static String getUsableSpace(String xmlConfName) {
		try {

			openXml();

			Node node = XmlTools.getSingleNode(xmlConfName);

			/**
			 * <pre>
			 * 			<adapter name="TestSpaceMoniter" priority="10">
			 * 		<type name="TestSpaceMoniter" class="TestSpaceMoniter" local="etc/gn" usableSpace="20G" />
			 * 	</adapter>
			 * 		</type>
			 * </pre>
			 */

			String usableSpace = XmlTools.getSingleAttrValue(node,
					"usableSpace");

			closeXml();

			return usableSpace;

		} catch (Exception e) {
			LogTools.getLogger(TestSpaceMoniterConfig.class)
					.error("When [{} - {} --> Exception ] ,  \n  MASSAGE : {} \n  CAUSE :{} \n  CLASS : {}",
							new Object[] { "getUsableSpace", "",
									e.getMessage(), e.getCause(), e.getClass() });
			return null;
		}
	}
}
