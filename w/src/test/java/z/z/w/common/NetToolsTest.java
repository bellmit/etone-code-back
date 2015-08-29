/**
 * z.z.w.common.NetToolsTest.java
 */
package z.z.w.common;

import org.junit.Test;

/**
 * @author Wu Zhenzhen
 * @version 2013-8-21 上午11:18:25
 */
public class NetToolsTest {

	/**
	 * Test method for {@link z.z.w.common.NetTools#getMacAddressList()}.
	 */
	@Test
	public void testGetMacAddressList() {
		for (String mac : NetTools.getMacAddressList()) {
			System.out.println(mac);
		}
		System.out.println("------------------------------");
	}

	/**
	 * Test method for {@link z.z.w.common.NetTools#getWinMacAddressList()}.
	 */
	@Test
	public void testGetWinMacAddressList() {
		for (String mac : NetTools.getWinMacAddressList()) {
			System.out.println(mac);
		}
	}

}
