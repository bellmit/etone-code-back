/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.test.config.SRConfig.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-10-2 下午05:53:14 
 *   LastChange: 2013-10-2 下午05:53:14 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.test.config;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.math.NumberUtils;
import org.dom4j.Node;

import z.z.w.common.DESCodec;
import z.z.w.db.upgrade.exec.CacheDBFactory;
import z.z.w.project.common.config.WebGlobal;
import z.z.w.project.test.cache.vo.BasePackagesVo;
import z.z.w.project.test.cache.vo.SuperpositionPackagesVo;
import z.z.w.project.util.common.LogTools;
import z.z.w.project.util.common.XmlTools;
import z.z.w.project.util.env.WebConfig;

/**
 * z.z.w.project.test.config.SRConfig.java
 */
public abstract class SRConfig extends WebGlobal {

	// static {
	// Global.CONFIG
	// .setFileName("D:\\03_workspace\\myproject\\myproject\\test-webservice\\src\\main\\resources\\global.cfg");
	// }

	private static final WebConfig dbConf = new WebConfig(WebGlobal.DB_CFG_FILE);

	/**
	 * <br>
	 * Created on: 2013-8-21 上午12:39:51
	 */
	private SRConfig() {
	}

	/**
	 * <br>
	 * Created on: 2013-7-28 下午10:13:37
	 * 
	 * @return
	 */
	public static String getDBUser() {
		return getDBConfig().getString("user");
	}

	/**
	 * <br>
	 * Created on: 2013-7-28 下午10:14:01
	 * 
	 * @return
	 */
	private static WebConfig getDBConfig() {
		dbConf.setRootKey("db");
		return dbConf;
	}

	/**
	 * <br>
	 * Created on: 2013-7-28 下午10:16:52
	 * 
	 * @return
	 */
	public static String getDBPassword() {
		String passwd = getDBConfig().getString("passwd");
		boolean descodec = getDBConfig().getBoolean("passwd.encode");
		if (!descodec)
			return passwd;
		else {
			return DESCodec.decrypt(passwd, CacheDBFactory.DB_CONN_POOL_NAME);
		}
	}

	/**
	 * <br>
	 * Created on: 2013-7-28 下午10:17:05
	 * 
	 * @return
	 */
	public static int getDBPort() {
		return getDBConfig().getInt("port");
	}

	/**
	 * <br>
	 * Created on: 2013-7-28 下午10:17:19
	 * 
	 * @return
	 */
	public static String getDBServer() {
		return getDBConfig().getString("server");
	}

	/**
	 * <br>
	 * Created on: 2013-7-28 下午10:17:38
	 * 
	 * @return
	 */
	public static String getDBDataBase() {
		return getDBConfig().getString("database");
	}

	/**
	 * <br>
	 * Created on: 2013-10-3 下午01:41:39
	 * 
	 * @return
	 */
	public static List<BasePackagesVo> getBasePackagesVoList() {

		try {
			openXml();

			List<BasePackagesVo> list = new ArrayList<BasePackagesVo>();

			String xmlConfName = "//subscription/packages";
			Iterator<Node> itt = XmlTools.getNodeList(xmlConfName).iterator();
			while (itt.hasNext()) {
				try {
					Node nodei = itt.next();

					String packagesName = XmlTools.getSingleAttrValue(nodei,
							"name");

					String xmlConfNameConf = xmlConfName + "[@name='"
							+ packagesName + "']/grades";

					Iterator<Node> it = XmlTools.getNodeList(xmlConfNameConf)
							.iterator();

					while (it.hasNext()) {

						Node node = it.next();

						/**
						 * <packages name="全球通上网套餐">
						 * 
						 * <grades fee="58" caller="50" called="free"
						 * dataTraffic="200" buss="来电显示" permin="0.25"
						 * permega="0.5" />
						 * 
						 * <grades fee="88" caller="200" called="free"
						 * dataTraffic="300" buss="来电显示" permin="0.19"
						 * permega="0.5" />
						 * 
						 * <grades fee="128" caller="420" called="free"
						 * dataTraffic="400" buss="来电显示" permin="0.19"
						 * permega="0.5" />
						 */
						try {
							BasePackagesVo vo = new BasePackagesVo();
							vo.setPackagesName(packagesName);
							vo.setFee(NumberUtils.toFloat(XmlTools
									.getSingleAttrValue(node, "fee")));
							vo.setInnerPackagesCaller(NumberUtils
									.toFloat(XmlTools.getSingleAttrValue(node,
											"caller")));
							vo.setInnerPackagesCalled(NumberUtils
									.toFloat(XmlTools.getSingleAttrValue(node,
											"called")));
							vo.setInnerPackagesDataTraffic(NumberUtils
									.toFloat(XmlTools.getSingleAttrValue(node,
											"dataTraffic")));
							vo.setBuss(XmlTools
									.getSingleAttrValue(node, "buss"));
							vo.setOuterPackagesPerMin(NumberUtils
									.toFloat(XmlTools.getSingleAttrValue(node,
											"permin")));
							vo.setOuterPackagesPermega(NumberUtils
									.toFloat(XmlTools.getSingleAttrValue(node,
											"permega")));

							list.add(vo);

						} catch (Exception e) {
							continue;
						}

					}

				} catch (Exception e) {
					continue;
				}

			}

			return list;
		} catch (Exception e) {
			LogTools.getLogger(SRConfig.class)
					.error("When [{} - {} --> Exception ] ,  \n  MASSAGE : {} \n  CAUSE :{} \n  CLASS : {}",
							new Object[] { "getBasePackagesVoList", "",
									e.getMessage(), e.getCause(), e.getClass() });
			return null;
		}

	}

	/**
	 * <br>
	 * Created on: 2013-10-3 下午01:41:45
	 * 
	 * @return
	 */
	public static List<SuperpositionPackagesVo> getSuperpositionPackagesVoList() {
		try {
			openXml();

			String xmlConfName = "//subscription/superpositionPackages";
			List<SuperpositionPackagesVo> list = new ArrayList<SuperpositionPackagesVo>();
			Iterator<Node> it = XmlTools.getNodeList(xmlConfName).iterator();
			while (it.hasNext()) {

				Node node = it.next();
				try {
					/**
					 * <superpositionPackages name="5元套餐" fee="5"
					 * dataTraffic="30" /> <superpositionPackages name="10元套餐"
					 * fee="10" dataTraffic="70" />
					 */

					SuperpositionPackagesVo vo = new SuperpositionPackagesVo();
					vo.setPackagesName(XmlTools
							.getSingleAttrValue(node, "name"));
					vo.setFee(NumberUtils.toFloat(XmlTools.getSingleAttrValue(
							node, "fee")));
					vo.setDataTraffic(NumberUtils.toFloat(XmlTools
							.getSingleAttrValue(node, "dataTraffic")));

					list.add(vo);

				} catch (Exception e) {
					continue;
				}

			}

			return list;
		} catch (Exception e) {
			LogTools.getLogger(SRConfig.class)
					.error("When [{} - {} --> Exception ] ,  \n  MASSAGE : {} \n  CAUSE :{} \n  CLASS : {}",
							new Object[] { "getSuperpositionPackagesVoList",
									"", e.getMessage(), e.getCause(),
									e.getClass() });
			return null;
		}
	}
}
