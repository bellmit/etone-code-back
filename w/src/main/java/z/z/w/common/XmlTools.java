/**
 * z.z.w.common.XmlTools.java
 */
package z.z.w.common;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

/**
 * @author Wu Zhenzhen
 * @version Dec 1, 2012 5:00:59 PM
 */
public class XmlTools {

	private static Document doc = null;

	/**
	 * <br>
	 * Created on: Dec 1, 2012 5:00:59 PM
	 */
	private XmlTools() {
	}

	/**
	 * Open xml file <br>
	 * Created on: Dec 1, 2012 6:34:13 PM
	 * 
	 * @param fileName
	 * @throws DocumentException
	 */
	public static void openXml(String fileName) throws DocumentException {
		SAXReader reader = new SAXReader();
		doc = reader.read(fileName);
	}

	/**
	 * 
	 * Get xml node <br>
	 * Created on: Dec 1, 2012 6:34:08 PM
	 * 
	 * @param nodeName
	 * @return
	 */
	public static Node getSingleNode(String nodeName) {
		return doc.selectSingleNode(nodeName);
	}

	/**
	 * Get xml root node element <br>
	 * Created on: Dec 2, 2012 6:05:57 PM
	 * 
	 * @return
	 */
	public static Node getRootNode() {
		return doc.getRootElement();
	}

	/**
	 * Get xml node attribute value (屬性名不存在，值為空)<br>
	 * Created on: Dec 1, 2012 6:36:15 PM
	 * 
	 * @param node
	 * @param attributeName
	 * @return
	 */
	public static String getSingleAttrValue(Node node, String attributeName) {
		return DataTools.trimToEmpty(node.valueOf("@" + attributeName));
	}

	/**
	 * Get xml node attribute value <br>
	 * Created on: Dec 1, 2012 6:34:46 PM
	 * 
	 * @param nodeName
	 * @param attributeName
	 * @return
	 */
	public static String getSingleAttrValue(String nodeName,
			String attributeName) {
		return DataTools.trimToEmpty(doc.selectSingleNode(nodeName).valueOf(
				"@" + attributeName));
	}

	/**
	 * Get child nodes <br>
	 * Created on: Dec 1, 2012 6:44:19 PM
	 * 
	 * @param nodeName
	 * @return
	 */
	public static List<Node> getNodeList(String nodeName) {
		List<Node> list = new ArrayList<Node>();
		Iterator<?> it = doc.selectNodes(nodeName).iterator();
		while (it.hasNext()) {
			Node node = (Node) it.next();
			list.add(node);
		} 

		return list;
	}

	/**
	 * clear doc <br>
	 * Created on: Dec 1, 2012 7:09:49 PM
	 */
	public static void closeXml() {
		doc = null;
	}

}
