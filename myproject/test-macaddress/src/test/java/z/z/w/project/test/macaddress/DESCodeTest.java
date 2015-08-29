/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.test.macaddress.DESCodeTest.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-8-22 上午10:14:58 
 *   LastChange: 2013-8-22 上午10:14:58 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.test.macaddress;

import org.junit.Test;

import z.z.w.common.DESCodec;

/**
 * z.z.w.project.test.macaddress.DESCodeTest.java
 */
public class DESCodeTest {

	@Test
	public void testDESCode() {
		String source = "10-BF-48-47-F7-6F";
		System.out.println("原文: " + source);

		String key = "";
		// String key = DESCodec.initkey();
		System.out.println("密钥: " + key);

		String encryptData = DESCodec.encrypt(source, key);
		System.out.println("加密: " + encryptData);

		String decryptData = DESCodec.decrypt(encryptData, key);
		System.out.println("解密: " + decryptData);
	}
}
