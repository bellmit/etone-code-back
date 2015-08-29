/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.util.common.XmlToolsTest.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-9-12 上午11:33:56 
 *   LastChange: 2013-9-12 上午11:33:56 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.util.common;

import java.util.Iterator;

import org.dom4j.Node;
import org.junit.Before;
import org.junit.Test;

import z.z.w.project.util.env.Config;
import z.z.w.project.util.file.FileTools;

/**
 * z.z.w.project.util.common.XmlToolsTest.java
 */
public class XmlToolsTest {

	private static final Config conf = new Config();
	private static String xmlFile = "";

	@Before
	public void before() {
		conf.setRootKey("common.cfg");
		xmlFile = FileTools.replaceProHome(conf.getString("xml-file"));
	}

	/**
	 * Test method for
	 * {@link z.z.w.project.util.common.XmlTools#getSingleNode(java.lang.String)}
	 * .
	 */
	@Test
	public void testGetSingleNode() {

		if (!XmlTools.openXml(xmlFile)) {
			System.out.println("Read xml config file [" + xmlFile + "] error.");
			return;
		}

		System.out.println(XmlTools.getSingleNode("//server/adapter").valueOf(
				"@name"));

		System.out.println(XmlTools.getSingleAttrValue(
				XmlTools.getSingleNode("//server/adapter"), "priority"));

		System.out.println(XmlTools.getSingleAttrValue(
				XmlTools.getSingleNode("//server/adapter"), "priorityTest"));

		XmlTools.closeXml();
	}

	/**
	 * Test method for
	 * {@link z.z.w.project.util.common.XmlTools#getNodeList(java.lang.String)}.
	 */
	@Test
	public void testGetNodeList() {

		if (!XmlTools.openXml(xmlFile)) {
			System.out.println("Read xml config file [" + xmlFile + "] error.");
			return;
		}

		String xmlNodeName = "//server/adapter/type";

		Iterator<Node> it = XmlTools.getNodeList(xmlNodeName).iterator();

		while (it.hasNext()) {

			Node node = it.next();
			System.out.println(XmlTools.getSingleAttrValue(node, "name"));
			System.out.println(XmlTools.getSingleAttrValue(node, "class"));
			System.out.println(DataTools.trimToEmpty(XmlTools
					.getSingleNodeText(node)));
			System.out.println("---------------------------");
		}

		XmlTools.closeXml();
	}

}
