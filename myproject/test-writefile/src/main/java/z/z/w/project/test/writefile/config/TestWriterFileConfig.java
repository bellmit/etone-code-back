/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.test.writefile.config.TestWriterFileConfig.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-9-5 上午10:12:19 
 *   LastChange: 2013-9-5 上午10:12:19 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.test.writefile.config;

import org.apache.commons.lang3.math.NumberUtils;
import org.dom4j.Node;

import z.z.w.common.FileTools;
import z.z.w.common.XmlTools;
import z.z.w.log.LogUtil;
import z.z.w.project.main.config.Global;
import z.z.w.project.test.writefile.vo.FileWritor;

/**
 * z.z.w.project.test.writefile.config.TestWriterFileConfig.java
 */
public abstract class TestWriterFileConfig extends Global {

	/**
	 * <br>
	 * Created on: 2013-9-5 上午10:12:19
	 */
	private TestWriterFileConfig() {
	}

	/**
	 * <br>
	 * Created on: 2013-9-5 上午10:12:30
	 * 
	 * @param xmlConfName
	 * @return
	 */
	public static FileWritor getFileWritor(String xmlConfName) {
		try {
			openXml();

			Node node = XmlTools.getSingleNode(xmlConfName);

			FileWritor fileWritor = new FileWritor();
			fileWritor.setClassName(XmlTools.getSingleAttrValue(node, "class"));
			fileWritor.setName(XmlTools.getSingleAttrValue(node, "name"));

			fileWritor.setLocalPath(FileTools.replaceProHome(XmlTools
					.getSingleAttrValue(node, "local")));
			fileWritor.setFileSuffix(XmlTools.getSingleAttrValue(node,
					"fileSuffix"));
			fileWritor.setFieldSplit(XmlTools.getSingleAttrValue(node,
					"fieldSplit"));
			fileWritor.setRowSplit(XmlTools
					.getSingleAttrValue(node, "rowSplit"));
			fileWritor.setDelayTime(NumberUtils.toLong(
					XmlTools.getSingleAttrValue(node, "delayTime"), 26666));
			fileWritor.setBuffSize(NumberUtils.toInt(
					XmlTools.getSingleAttrValue(node, "buffSize"), 182828));

			closeXml();

			return fileWritor;
		} catch (Exception e) {
			LogUtil.getLogger(TestWriterFileConfig.class)
					.warn("When [{} - {} --> Exception ] ,  \n  MASSAGE : {} \n  CAUSE :{} \n  CLASS : {}",
							new Object[] { "getFileWritor", xmlConfName,
									e.getMessage(), e.getCause(), e.getClass() });
			return null;
		}
	}

}
