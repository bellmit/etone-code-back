/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.util.common.JsonToolsTest.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-10-4 下午02:41:43 
 *   LastChange: 2013-10-4 下午02:41:43 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.util.common;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import z.z.w.project.other.test.MsisdnInfo;
import z.z.w.project.other.test.Packages;

/**
 * z.z.w.project.util.common.JsonToolsTest.java
 */
public class JsonToolsTest {

	/**
	 * Test method for
	 * {@link z.z.w.project.util.common.JsonTools#mapToJson(java.util.Map)}.
	 */
	@Test
	public void testMapToJson() {
		try {

			Map<String, Float> map = new HashMap<String, Float>();
			map.put("fee-1", 2.9f);
			map.put("fee-2", 2.6f);
			map.put("fee-3", 2.8f);
			map.put("fee-4", 12.9f);

			System.out.println(JsonTools.mapToJson(map));

		} catch (Exception e) {
			fail("MASSAGE : " + e.getMessage() + "\nCAUSE : " + e.getCause()
					+ "\nCLASS : " + e.getClass());
		}
	}

	/**
	 * Test method for
	 * {@link z.z.w.project.util.common.JsonTools#jsonToMap(java.lang.String)}.
	 */
	@Test
	public void testJsonToMap() {
		try {
			String jsonString = "{\"fee-4\":12.9,\"fee-3\":2.8,\"fee-2\":2.6,\"fee-1\":2.9}";
			Map<?, ?> map = JsonTools.jsonToMap(jsonString);
			Set<?> set = map.keySet();
			Iterator<?> it = set.iterator();
			while (it.hasNext()) {
				Object obj = it.next();
				System.out.println(obj + "----" + map.get(obj));
			}

		} catch (Exception e) {
			fail("MASSAGE : " + e.getMessage() + "\nCAUSE : " + e.getCause()
					+ "\nCLASS : " + e.getClass());
		}
	}

	/**
	 * Test method for
	 * {@link z.z.w.project.util.common.JsonTools#entityToJson(java.lang.Object)}
	 * .
	 */
	@Test
	public void testEntityToJson() {
		try {

			Packages entity = new Packages();
			entity.setUserMsisdn("13622856209");
			entity.setCurrentPackagesId("HY_10000000000195");
			List<String> recommendationPackagesNameList = new ArrayList<String>();
			recommendationPackagesNameList.add("全球通88元套餐疊加200元套餐");
			recommendationPackagesNameList.add("全球通188元套餐疊加20元套餐");
			entity.setRecommendationPackagesNameList(recommendationPackagesNameList);
			List<MsisdnInfo> msisdnInfoList = new ArrayList<MsisdnInfo>();
			MsisdnInfo e = new MsisdnInfo();
			// GPRS_PRCT_CD,MONTH_CD,GPRS_FLUX,ACT_CALL,FLUX_FEE,CALL_FEE
			e.setMONTH_CD("201308");
			e.setGPRS_FLUX(23.45);
			e.setACT_CALL(34.67);
			e.setFLUX_FEE(2356.3);
			e.setCALL_FEE(345.55);
			msisdnInfoList.add(e);

			e.setMONTH_CD("201309");
			e.setGPRS_FLUX(233.45);
			e.setACT_CALL(934.67);
			e.setFLUX_FEE(203.3);
			e.setCALL_FEE(45.55);
			msisdnInfoList.add(e);

			e.setMONTH_CD("201310");
			e.setGPRS_FLUX(423.5);
			e.setACT_CALL(3.67);
			e.setFLUX_FEE(26.3);
			e.setCALL_FEE(34.5);
			msisdnInfoList.add(e);
			entity.setMsisdnInfoList(msisdnInfoList);

			System.out.println(JsonTools.entityToJson(entity));

		} catch (Exception e) {
			fail("MASSAGE : " + e.getMessage() + "\nCAUSE : " + e.getCause()
					+ "\nCLASS : " + e.getClass());
		}
	}

	/**
	 * Test method for
	 * {@link z.z.w.project.util.common.JsonTools#jsonToEntity(java.lang.String, org.codehaus.jackson.type.TypeReference)}
	 * .
	 */
	@Test
	public void testJsonToEntity() {
		try {

		} catch (Exception e) {
			fail("MASSAGE : " + e.getMessage() + "\nCAUSE : " + e.getCause()
					+ "\nCLASS : " + e.getClass());
		}
	}

	/**
	 * Test method for
	 * {@link z.z.w.project.util.common.JsonTools#jsonToJsonNode(java.lang.String)}
	 * .
	 */
	@Test
	public void testJsonToJsonNode() {
		try {

		} catch (Exception e) {
			fail("MASSAGE : " + e.getMessage() + "\nCAUSE : " + e.getCause()
					+ "\nCLASS : " + e.getClass());
		}
	}

	/**
	 * Test method for
	 * {@link z.z.w.project.util.common.JsonTools#toJsonNode(java.util.Map)}.
	 */
	@Test
	public void testToJsonNode() {
		try {

		} catch (Exception e) {
			fail("MASSAGE : " + e.getMessage() + "\nCAUSE : " + e.getCause()
					+ "\nCLASS : " + e.getClass());
		}
	}

}
