/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.main.config.Global.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-8-20 下午07:36:43 
 *   LastChange: 2013-8-20 下午07:36:43 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.main.config;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.math.NumberUtils;
import org.dom4j.DocumentException;
import org.dom4j.Node;

import z.z.w.common.DataTools;
import z.z.w.common.DateTools;
import z.z.w.common.FileTools;
import z.z.w.common.XmlTools;
import z.z.w.env.Config;
import z.z.w.project.util.common.LogTools;
import z.z.w.vo.AdapterType;
import z.z.w.vo.ServerAdapter;

/**
 * z.z.w.project.main.config.Global.java
 */
public class Global {

	private static final Config config = new Config();

	public static final String LOG_CFG_FILE = FileTools.replaceProHome(config
			.getString("log4j-cfg"));

	public static final String LOCK_FILE = FileTools.replaceProHome(config
			.getString("lock-file"));

	public static final String CERTIFICATE_FILE = FileTools
			.replaceProHome(config.getString("cer-file"));

	public static final String SERVER_CFG_FILE = FileTools
			.replaceProHome(config.getString("server-xml"));

	/** db config */
	public static final String DB_CFG_FILE = FileTools.replaceProHome(config
			.getString("jdbc-cfg"));

	/**
	 * <br>
	 * Created on: 2013-6-26 下午08:41:37
	 */
	protected static void openXml() throws DocumentException {
		XmlTools.openXml(SERVER_CFG_FILE);
	}

	/**
	 * <br>
	 * Created on: 2013-6-26 下午08:42:13
	 */
	protected static void closeXml() {
		// XmlTools.closeXml();
	}

	/**
	 * 
	 * <br>
	 * Created on: 2013-7-28 上午11:54:55
	 * 
	 * @param node
	 * @param nodeName
	 * @return
	 */
	public static boolean getBooleanNodeValue(Node node, String nodeName) {
		String tagValue = XmlTools.getSingleAttrValue(node, nodeName);
		return ((tagValue.equalsIgnoreCase("on") || tagValue
				.equalsIgnoreCase("1")) ? true : false);
	}

	/**
	 * <br>
	 * Created on: 2013-7-28 下午01:20:03
	 * 
	 * @param className
	 * @return
	 */
	public static Class<?> getServerClass(String className) {
		try {
			return (Class.forName(className));
		} catch (Exception e) {
			LogTools.getLogger(Global.class)
					.warn("When [{} - {} --> Exception ] ,  \n  MASSAGE : {} \n  CAUSE :{} \n  CLASS : {}",
							new Object[] { "getServerClass", "",
									e.getMessage(), e.getCause(), e.getClass() });
			return null;
		}
	}

	/**
	 * <br>
	 * Created on: 2013-7-28 下午01:20:34
	 * 
	 * @param clazz
	 * @return
	 */
	public static Object getInterfaceInstance(Class<?> clazz) {
		try {
			return clazz.newInstance();
		} catch (Exception e) {
			LogTools.getLogger(Global.class)
					.warn("When [{} - {} --> Exception ] ,  \n  MASSAGE : {} \n  CAUSE :{} \n  CLASS : {}",
							new Object[] { "getInterfaceInstance", "",
									e.getMessage(), e.getCause(), e.getClass() });
			return null;
		}
	}

	/**
	 * 
	 * <br>
	 * Created on: 2013-7-29 下午08:51:18
	 * 
	 * @param clazz
	 * @param methodName
	 * @param parameterTypes
	 * @return
	 */
	public static Method getDeclaredMethod(Class<?> clazz, String methodName,
			Class<?>... parameterTypes) {
		try {
			return clazz.getDeclaredMethod(methodName, parameterTypes);
		} catch (Exception e) {
			LogTools.getLogger(Global.class)
					.warn("When [{} - {} --> Exception ] ,  \n  MASSAGE : {} \n  CAUSE :{} \n  CLASS : {}",
							new Object[] { "getDeclaredMethod", "",
									e.getMessage(), e.getCause(), e.getClass() });
			return null;
		}
	}

	/**
	 * 
	 * <br>
	 * Created on: 2013-7-29 下午08:53:20
	 * 
	 * @param method
	 * @param clazz
	 * @param parameters
	 * @return
	 */
	public static Object invoke(Method method, Class<?> clazz,
			Object... parameters) {
		try {
			return method.invoke(clazz, parameters);
		} catch (Exception e) {
			LogTools.getLogger(Global.class)
					.warn("When [{} - {} --> Exception ] ,  \n  MASSAGE : {} \n  CAUSE :{} \n  CLASS : {}",
							new Object[] { "invoke", "", e.getMessage(),
									e.getCause(), e.getClass() });
			return null;
		}
	}

	/**
	 * <br>
	 * Created on: 2013-7-28 上午11:53:35
	 * 
	 * @param nodeName
	 * @param nodeAttrName
	 * @param i
	 * @return
	 */
	public static long getTimeInterval(String nodeName, String nodeAttrName,
			long defaultValue) {
		try {
			openXml();

			Node node = XmlTools.getSingleNode(nodeName);

			String intervalStr = XmlTools
					.getSingleAttrValue(node, nodeAttrName);

			long interval = DateTools.getTimeLongByString(intervalStr);

			closeXml();

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
	 * Created on: 2013-8-20 下午07:36:43
	 */
	protected Global() {
		super();
	}

	/**
	 * <br>
	 * Created on: 2013-8-20 下午07:46:15
	 * 
	 * @param xmlServerAdapter
	 * @return
	 */
	public static List<ServerAdapter> getServerAdapterList(
			String xmlServerAdapter) {

		try {

			openXml();

			List<ServerAdapter> list = new ArrayList<ServerAdapter>();

			Iterator<Node> it = XmlTools.getNodeList(xmlServerAdapter)
					.iterator();
			while (it.hasNext()) {

				Node node = it.next();
				/***
				 * <adapter name="" priority="4" threadNum="2">
				 */
				try {

					ServerAdapter serverAdapter = new ServerAdapter();

					serverAdapter.setName(XmlTools.getSingleAttrValue(node,
							"name"));
					serverAdapter.setThreadNum(NumberUtils.toInt(
							XmlTools.getSingleAttrValue(node, "threadNum"), 1));
					serverAdapter.setPriority(NumberUtils.toInt(
							XmlTools.getSingleAttrValue(node, "priority"), 1));

					List<AdapterType> adapterTypeList = getAdapterTypeList(xmlServerAdapter
							+ "[@name='" + serverAdapter.getName() + "']/type");

					if (DataTools.isEmpty(adapterTypeList))
						continue;

					serverAdapter.setAdapterTypeList(adapterTypeList);

					list.add(serverAdapter);

				} catch (Exception e) {
					continue;
				}
			}

			sortServerAdapter(list);

			closeXml();

			return list;
		} catch (Exception e) {
			LogTools.getLogger(Global.class)
					.warn("When [{} - {} --> Exception ] ,  \n  MASSAGE : {} \n  CAUSE :{} \n  CLASS : {}",
							new Object[] { "getServerAdapterList", "",
									e.getMessage(), e.getCause(), e.getClass() });
			return null;
		}
	}

	/**
	 * <br>
	 * Created on: 2013-8-20 下午07:51:10
	 * 
	 * @param list
	 */
	private static void sortServerAdapter(List<ServerAdapter> list) {
		Collections.sort(list, new Comparator<ServerAdapter>() {

			public int compare(ServerAdapter o1, ServerAdapter o2) {
				return o1.getPriority().compareTo((o2.getPriority()));
			}

		});
	}

	/**
	 * <br>
	 * Created on: 2013-8-20 下午07:50:13
	 * 
	 * @param xmlAdapterType
	 * @return
	 */
	private static List<AdapterType> getAdapterTypeList(String xmlAdapterType) {
		List<AdapterType> list = new ArrayList<AdapterType>();

		Iterator<Node> it = XmlTools.getNodeList(xmlAdapterType).iterator();
		while (it.hasNext()) {

			Node node = it.next();
			/***
			 * <type name="" class=""
			 */
			try {

				AdapterType serverAdapter = new AdapterType();

				serverAdapter
						.setName(XmlTools.getSingleAttrValue(node, "name"));
				serverAdapter.setClassName(XmlTools.getSingleAttrValue(node,
						"class"));

				list.add(serverAdapter);

			} catch (Exception e) {
				LogTools.getLogger(Global.class)
						.warn("When [{} - {} --> Exception ] ,  \n  MASSAGE : {} \n  CAUSE :{} \n  CLASS : {}",
								new Object[] { "getAdapterTypeList", "",
										e.getMessage(), e.getCause(),
										e.getClass() });
				continue;
			}
		}

		return list;
	}

	/**
	 * <br>
	 * Created on: 2013-9-5 上午09:55:35
	 * 
	 * @return
	 */
	public static int getDelaySizeMax() {
		try {
			openXml();

			final String nodeName = "//server/global";
			Node node = XmlTools.getSingleNode(nodeName);

			int interval = NumberUtils.toInt(
					XmlTools.getSingleAttrValue(node, "dataDelaySize"), 10000);

			closeXml();

			return interval;
		} catch (Exception e) {
			LogTools.getLogger(Global.class).warn("{}", e.getMessage());
			return 10000;
		}
	}

	/**
	 * <br>
	 * Created on: 2013-9-5 上午09:55:50
	 * 
	 * @return
	 */
	public static int getDelayTimeMax() {
		try {
			openXml();

			final String nodeName = "//server/global";
			Node node = XmlTools.getSingleNode(nodeName);

			int interval = NumberUtils.toInt(
					XmlTools.getSingleAttrValue(node, "dataDelayTime"),
					1000 * 60 * 6);

			closeXml();

			return interval;
		} catch (Exception e) {
			LogTools.getLogger(Global.class).warn("{}", e.getMessage());
			return 1000 * 60 * 6;
		}
	}

}
