/**
 * z.z.w.common.XmlToolsTest.java
 */
package z.z.w.common;

import java.util.Iterator;
import java.util.List;

import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.junit.Before;
import org.junit.Test;

import z.z.w.env.Config;

/**
 * @author Wu Zhenzhen
 * @version Dec 1, 2012 6:47:17 PM
 */
public class XmlToolsTest {

	/**
	 * <br>
	 * Created on: Dec 1, 2012 6:47:17 PM
	 * 
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() {
		Config conf = new Config();
		conf.setRootKey("common.util");
		try {
			XmlTools.openXml(FileTools.replaceProHome(conf
					.getString("xml-file")));
		} catch (DocumentException e) {

		}
	}

	/**
	 * Test method for
	 * {@link z.z.w.common.XmlTools#getSingleNode(java.lang.String)}.
	 */
	@Test
	public void testGetSingleNode() {
		System.out.println(XmlTools.getSingleNode("//server/transformer")
				.valueOf("@class"));
	}

	/**
	 * Test method for
	 * {@link z.z.w.common.XmlTools#getSingleAttrValue(org.dom4j.Node, java.lang.String)}
	 * .
	 */
	@Test
	public void testGetSingleAttrValueNodeString() {
		// System.out.println(XmlTools.getSingleAttrValue(
		// XmlTools.getSingleNode("//server/handler"), "class"));
		System.out.println(XmlTools.getSingleAttrValue(
				XmlTools.getSingleNode("//server/handler"), "cass"));
	}

	/**
	 * Test method for
	 * {@link z.z.w.common.XmlTools#getSingleAttrValue(java.lang.String, java.lang.String)}
	 * .
	 */
	@Test
	public void testGetSingleAttrValueStringString() {
		System.out.println(XmlTools.getSingleAttrValue("//server/transformer",
				"class"));
	}

	/**
	 * Test method for
	 * {@link z.z.w.common.XmlTools#getChildNodeList(java.lang.String)}.
	 */
	@Test
	public void testGetChildNodeList() {
		// List<Node> list = XmlTools
		// .getChildNodeList("//server/loadTable/loader");
		// Iterator<Node> it = list.iterator();
		// while (it.hasNext()) {
		// Node node = it.next();
		// System.out.println(XmlTools.getSingleAttrValue(node, "class"));
		// System.out.println(XmlTools.getSingleAttrValue(node, "name"));
		// System.out.println("------------------------------------------");
		// }

		String xmlNodeName = "//server/handler/adapter";
		// print(xmlNodeName);
		// // decode source data
		// xmlNodeName = "//server/decode/adapter";
		// print(xmlNodeName);
		// load source data
		xmlNodeName = "//server";
		print(xmlNodeName);
	}

	/**
	 * <br>
	 * Created on: Dec 2, 2012 6:14:14 PM
	 * 
	 * @param xmlNodeName
	 */
	private void print(String xmlNodeName) {

		Iterator<Node> it = XmlTools.getNodeList(xmlNodeName).iterator();
		while (it.hasNext()) {
			Element element = (Element) it.next();
			// int index = element.attributeCount();
			// for (int i = 0; i < index; i++) {
			// System.out.println(element.attribute(i).getName() + " -- "
			// + element.attribute(i).getValue());
			// // creator.setClassName(className)
			// }

			List<?> list = element.elements();
			Iterator<?> i2 = list.iterator();
			while (i2.hasNext()) {
				Element el = (Element) i2.next();
				String xmlChildNodeName = xmlNodeName + "/" + el.getName();

				Iterator<Node> it3 = XmlTools.getNodeList(xmlChildNodeName)
						.iterator();
				while (it3.hasNext()) {
					Element el3 = (Element) it3.next();
					int iex = el3.attributeCount();
					for (int i = 0; i < iex; i++) {
						System.out.println("    ==="
								+ el3.attribute(i).getName() + " -- "
								+ el3.attribute(i).getValue());
					}
				}
			}
		}
	}

	@Test
	public void testPrint() {
		String xmlNodeName = "//server/loader/adapter[@name='adapterLoaderName1']/socket";

		Iterator<Node> it = XmlTools.getNodeList(xmlNodeName).iterator();

		while (it.hasNext()) {

			Node node = it.next();
			System.out.println(XmlTools.getSingleAttrValue(node, "port"));
			System.out.println(XmlTools.getSingleAttrValue(node, "serverIp"));
		}

	}

	@Test
	public void testContentPrint() {
		String xmlNodeName = "//server/system[@name='ServerSystem']/nadap";
		Iterator<Node> it = XmlTools.getNodeList(xmlNodeName).iterator();
		while (it.hasNext()) {
			Node node = it.next();
			System.out.println(node.getText());
		}

		System.out.println(XmlTools.getSingleNode(xmlNodeName).getText());

	}

	@Test
	public void testContentTwoPrint() {
		String xmlNodeName = "//server/handler/adapter[@name='MatchDataWriteDBServer']/jobFire/tactics";
		/**
		 * <tactics name="tzsz_keyword_search_SZ"
		 * filePath="/home/mysql/wzz/data/" dataSqlId="exportKeyWordsDatas"
		 * type="1" /> <tactics name="tzst_net_cntn_acss_SZ"
		 * filePath="/home/mysql/wzz/data/" dataSqlId="exportNetContentDatas"
		 * type="2" />
		 */
		Iterator<Node> it = XmlTools.getNodeList(xmlNodeName).iterator();
		while (it.hasNext()) {
			Node node = it.next();
			String name = XmlTools.getSingleAttrValue(node, "name");
			String filePath = XmlTools.getSingleAttrValue(node, "filePath");
			String dataSqlId = XmlTools.getSingleAttrValue(node, "dataSqlId");
			String type = XmlTools.getSingleAttrValue(node, "type");
			System.out.println(name);
			System.out.println(filePath);
			System.out.println(dataSqlId);
			System.out.println(type);
			System.out.println("------------------");
		}

		System.out.println(XmlTools.getSingleNode(xmlNodeName).getText());

	}
}
