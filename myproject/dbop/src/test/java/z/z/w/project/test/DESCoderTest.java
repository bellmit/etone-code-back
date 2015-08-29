/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.test.DESCoderTest.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-9-10 上午11:16:11 
 *   LastChange: 2013-9-10 上午11:16:11 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.test;

import org.junit.Test;

import z.z.w.common.DESCodec;
import z.z.w.db.upgrade.exec.CacheDBFactory;

/**
 * z.z.w.project.test.DESCoderTest.java
 */
public class DESCoderTest {

	@Test
	public void testDESCoder() {
		String source = "/Tn&3KU)t0[`{^@M`]UI";
		System.out.println("原文: " + source);

		String key = CacheDBFactory.DB_CONN_POOL_NAME;
		// String key = DESCodec.initkey();
		System.out.println("密钥: " + key);

		String encryptData = DESCodec.encrypt(source, key);
		System.out.println("加密: " + encryptData);

		// String decryptData = DESCodec.decrypt(encryptData, key);
		// System.out.println("解密: " + decryptData);

		System.out.println("==============================");

		key = encryptData;
		encryptData = DESCodec.encrypt(source, key);
		System.out.println("加密: " + encryptData);

		String decryptData = DESCodec.decrypt(encryptData, key);
		System.out.println("解密: " + decryptData);
	}
}
