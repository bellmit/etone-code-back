/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.read.file.config.FileReaderConfig.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-8-20 下午08:51:49 
 *   LastChange: 2013-8-20 下午08:51:49 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.read.file.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.math.NumberUtils;
import org.dom4j.Node;

import z.z.w.common.DataTools;
import z.z.w.common.DateTools;
import z.z.w.common.FileTools;
import z.z.w.common.XmlTools;
import z.z.w.project.main.config.Global;
import z.z.w.project.read.file.vo.FileReader;
import z.z.w.project.util.common.LogTools;
import z.z.w.vo.Field;

/**
 * z.z.w.project.read.file.config.FileReaderConfig.java
 */
public class FileReaderConfig extends Global {

	/**
	 * <br>
	 * Created on: 2013-8-20 下午08:51:49
	 */
	private FileReaderConfig() {
		super();
	}

	/**
	 * <br>
	 * Created on: 2013-8-20 下午08:52:04
	 * 
	 * @param xmlConfName
	 * @return
	 */
	public static FileReader getFileReader(String xmlConfName) {
		try {

			openXml();

			Node node = XmlTools.getSingleNode(xmlConfName);

			/**
			 * <pre>
			 * 			<type name="ServiceRuleReader" class="z.z.w.project.read.file.ServiceRuleReader"
			 * 			localPath="etc/data" backPath="etc/data" filePrefix="tbServiceRule" fileSuffix=".csv"
			 * 			fieldSplit="\x2c" delete="0" backup="0" startRow="0" readInterval="10h">
			 * 
			 * 			<field index="0" name="intSubServId" srcindex="0" />
			 * 			<field index="1" name="vcSubServ" srcindex="1" />
			 * 			<field index="2" name="vcIp" srcindex="2" />
			 * 			<field index="3" name="vcUrl" srcindex="3" />
			 * 
			 * 		</type>
			 * </pre>
			 */

			FileReader fileReader = new FileReader();
			fileReader.setLocalPath(FileTools.replaceProHome(XmlTools
					.getSingleAttrValue(node, "localPath")));
			fileReader.setBackPath(FileTools.replaceProHome(XmlTools
					.getSingleAttrValue(node, "backPath")));

			fileReader.setFilePrefix(XmlTools.getSingleAttrValue(node,
					"filePrefix"));
			fileReader.setFileSuffix(XmlTools.getSingleAttrValue(node,
					"fileSuffix"));
			fileReader.setFieldSplit(XmlTools.getSingleAttrValue(node,
					"fieldSplit"));

			fileReader.setDelete(Global.getBooleanNodeValue(node, "delete"));
			if (DataTools.isEmpty(fileReader.getBackPath()))
				fileReader.setBackup(false);
			else
				fileReader
						.setBackup(Global.getBooleanNodeValue(node, "backup"));

			fileReader.setStartRow(NumberUtils.toInt(
					XmlTools.getSingleAttrValue(node, "startRow"), 0));

			String intervalStr = XmlTools.getSingleAttrValue(node,
					"readInterval");
			long interval = DateTools.getTimeLongByString(intervalStr);
			fileReader.setReadInterval(interval);

			List<Field> fieldList = getFieldList(xmlConfName + "/field");
			if (DataTools.isEmpty(fieldList))
				throw new NullPointerException(
						"FileReader field config is null,return null!");

			sortField(fieldList);

			fileReader.setFieldList(fieldList);

			closeXml();

			return fileReader;

		} catch (Exception e) {
			LogTools.getLogger(FileReaderConfig.class)
					.error("When [{} - {} --> Exception ] ,  \n  MASSAGE : {} \n  CAUSE :{} \n  CLASS : {}",
							new Object[] { "getFileReader", "", e.getMessage(),
									e.getCause(), e.getClass() });
			return null;
		}
	}

	/**
	 * <br>
	 * Created on: 2013-8-20 下午08:57:20
	 * 
	 * @param fieldList
	 */
	private static void sortField(List<Field> fieldList) {
		Collections.sort(fieldList, new Comparator<Field>() {

			public int compare(Field o1, Field o2) {
				return o1.getIndex().compareTo(o2.getIndex());
			}
		});
	}

	/**
	 * <br>
	 * Created on: 2013-8-20 下午08:56:11
	 * 
	 * @param xmlConfField
	 * @return
	 */
	private static List<Field> getFieldList(String xmlConfField) {
		List<Field> list = new ArrayList<Field>();

		Iterator<Node> it = XmlTools.getNodeList(xmlConfField).iterator();
		while (it.hasNext()) {

			Node node = it.next();
			/**
			 * <field index="3" name="vcUrl" srcindex="3" />
			 */
			try {

				Field field = new Field();
				field.setIndex(NumberUtils.toInt(
						XmlTools.getSingleAttrValue(node, "index"), -1));
				field.setName(XmlTools.getSingleAttrValue(node, "name"));
				field.setSrcindex(NumberUtils.toInt(
						XmlTools.getSingleAttrValue(node, "srcindex"), -1));

				list.add(field);

			} catch (Exception e) {
				LogTools.getLogger(FileReaderConfig.class)
						.error("When [{} - {} --> Exception ] ,  \n  MASSAGE : {} \n  CAUSE :{} \n  CLASS : {}",
								new Object[] { "getFieldList", "",
										e.getMessage(), e.getCause(),
										e.getClass() });
				continue;
			}
		}

		return list;
	}

}
