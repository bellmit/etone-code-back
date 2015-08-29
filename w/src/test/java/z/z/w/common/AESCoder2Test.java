/**************************************************************************
 * <pre>
 *     FileName: z.z.w.common.AESCoder2Test.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-9-11 下午02:09:55 
 *   LastChange: 2013-9-11 下午02:09:55 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.common;

import org.junit.Test;

/**
 * z.z.w.common.AESCoder2Test.java
 */
public class AESCoder2Test {

	/**
	 * Test method for
	 * {@link z.z.w.common.AESCoder2#encrypt(java.lang.String, java.lang.String)}
	 * .
	 */
	@Test
	public void testEncrypt() {
		try {
			String source = "站在云端，敲下键盘，望着通往世界另一头的那扇窗，只为做那读懂0和1的人。。";
			System.out.println("原文：" + source);

			String key = AESCoder2.initkey();
			System.out.println("密钥：" + key);

			String encryptData = AESCoder2.encrypt(source, key);
			System.out.println("加密：" + encryptData);

			String decryptData = AESCoder2.decrypt(encryptData, key);
			System.out.println("解密: " + decryptData);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
