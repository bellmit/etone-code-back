/**
 * z.z.w.othertest.PBECoderTest.java
 */
package z.z.w.others.endecryp;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * @author Wu Zhenzhen
 * @version 2013-8-22 上午11:32:01
 */
public class PBECoderTest {

	/**
	 * Test method for {@link z.z.w.others.endecryp.PBECoder#initSalt()}.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testInitSalt() throws Exception {
		String inputStr = "abc";
		System.err.println("原文: " + inputStr);
		byte[] input = inputStr.getBytes();

		String pwd = "efg";
		System.err.println("密码: " + pwd);

		byte[] salt = PBECoder.initSalt();

		byte[] data = PBECoder.encrypt(input, pwd, salt);

		System.err.println("加密后: " + PBECoder.encryptBASE64(data));

		byte[] output = PBECoder.decrypt(data, pwd, salt);
		String outputStr = new String(output);

		System.err.println("解密后: " + outputStr);
		assertEquals(inputStr, outputStr);
	}

}
