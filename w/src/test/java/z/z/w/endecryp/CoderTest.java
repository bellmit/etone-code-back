/**************************************************************************
 * <pre>
 *     FileName: z.z.w.endecryp.CoderTest.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-8-22 下午12:33:39 
 *   LastChange: 2013-8-22 下午12:33:39 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.endecryp;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.math.BigInteger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import z.z.w.log.LogUtil;

/**
 * z.z.w.endecryp.CoderTest.java
 */
public class CoderTest {

	private String source = "简单加密";
	private byte[] inputData = null;

	@Before
	public void before() {
		LogUtil.getLogger(this.getClass()).info("原文 : [{}].", source);
		inputData = source.getBytes();
	}

	/**
	 * Test method for
	 * {@link z.z.w.endecryp.Coder#decryptBASE64(java.lang.String)}.
	 */
	@Test
	public void testDecryptBASE64() {
		try {
			String code = Coder.encryptBASE64(inputData);
			LogUtil.getLogger(getClass()).info("BASE64加密后: [{}]", code);

			byte[] output = Coder.decryptBASE64(code);
			String outputStr = new String(output);
			LogUtil.getLogger(getClass()).info("BASE64解密后: [{}]", outputStr);

			// 验证BASE64加密解密一致性
			assertEquals(source, outputStr);

			LogUtil.getLogger(getClass()).info("");

		} catch (Exception e) {
			fail("MASSAGE : " + e.getMessage() + "\nCAUSE : " + e.getCause()
					+ "\nCLASS : " + e.getClass());
		}
	}

	/**
	 * Test method for {@link z.z.w.endecryp.Coder#encryptMD5(byte[])}.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testEncryptMD5() {
		try {
			// 验证MD5对于同一内容加密是否一致
			assertArrayEquals(Coder.encryptMD5(inputData),
					Coder.encryptMD5(inputData));
			LogUtil.getLogger(getClass()).info("");

		} catch (Exception e) {
			fail("MASSAGE : " + e.getMessage() + "\nCAUSE : " + e.getCause()
					+ "\nCLASS : " + e.getClass());
		}
	}

	/**
	 * Test method for {@link z.z.w.endecryp.Coder#encryptSHA(byte[])}.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testEncryptSHA() {
		try {
			// 验证SHA对于同一内容加密是否一致
			assertArrayEquals(Coder.encryptSHA(inputData),
					Coder.encryptSHA(inputData));
			LogUtil.getLogger(getClass()).info("");

		} catch (Exception e) {
			fail("MASSAGE : " + e.getMessage() + "\nCAUSE : " + e.getCause()
					+ "\nCLASS : " + e.getClass());
		}
	}

	/**
	 * Test method for {@link z.z.w.endecryp.Coder#initMacKey()}.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testInitMacKey() {
		try {
			// String key = "愛してろ";
			String key = Coder.initMacKey();
			LogUtil.getLogger(getClass()).info("Mac密钥: [{}]", key);

			String coder = new String(Coder.encryptHMAC(inputData, key));
			LogUtil.getLogger(getClass()).info("密文:[{}]", coder);

			// 验证HMAC对于同一内容，同一密钥加密是否一致
			assertArrayEquals(Coder.encryptHMAC(inputData, key),
					Coder.encryptHMAC(inputData, key));
			LogUtil.getLogger(getClass()).info("");

		} catch (Exception e) {
			fail("MASSAGE : " + e.getMessage() + "\nCAUSE : " + e.getCause()
					+ "\nCLASS : " + e.getClass());
		}
	}

	@After
	public void after() {
		try {
			BigInteger md5 = new BigInteger(Coder.encryptMD5(inputData));
			LogUtil.getLogger(getClass()).info("MD5:[{}]", md5.toString(16));

			BigInteger sha = new BigInteger(Coder.encryptSHA(inputData));
			LogUtil.getLogger(getClass()).info("SHA:[{}]", sha.toString(32));

			BigInteger mac = new BigInteger(
					Coder.encryptHMAC(inputData, source));
			LogUtil.getLogger(getClass()).info("HMAC:[{}]", mac.toString(16));
			LogUtil.getLogger(getClass()).info("");

		} catch (Exception e) {
			fail("MASSAGE : " + e.getMessage() + "\nCAUSE : " + e.getCause()
					+ "\nCLASS : " + e.getClass());
		}
	}
}
