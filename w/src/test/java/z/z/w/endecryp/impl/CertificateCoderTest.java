/**************************************************************************
 * <pre>
 *     FileName: z.z.w.endecryp.impl.CertificateCoderTest.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-8-22 下午01:00:33 
 *   LastChange: 2013-8-22 下午01:00:33 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.endecryp.impl;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Date;

import org.apache.commons.codec.binary.Hex;
import org.junit.Test;

import z.z.w.endecryp.Coder;
import z.z.w.others.endecryp.CertificateCoder;

/**
 * z.z.w.endecryp.impl.CertificateCoderTest.java
 */
public class CertificateCoderTest {

	private String certificatePath = "d:\\global.cer";
	private String keyStorePath = "d:\\zzw.keystore";
	private String keyStorePassword = "1qaz2wsx";
	private String aliasPassword = "2wsx3edc";
	private String alias = "z.z.w";

	private String inputStr = "10-BF-48-47-F7-6F";

	/**
	 * Test method for
	 * {@link z.z.w.endecryp.impl.CertificateCoder#encryptByPrivateKey(byte[], java.lang.String, java.lang.String, java.lang.String, java.lang.String)}
	 * .
	 */
	@Test
	public void testEncryptByPrivateKey() {
		try {
			System.err.println("私钥加密——公钥解密");

			byte[] data = inputStr.getBytes();

			byte[] encodedData = CertificateCoder.encryptByPrivateKey(data,
					keyStorePath, keyStorePassword, alias, aliasPassword);

			byte[] decodedData = CertificateCoder.decryptByPublicKey(
					encodedData, certificatePath);

			String outputStr = new String(decodedData);
			System.err.println("加密前: " + inputStr + "\n\r" + "解密后: "
					+ outputStr);
			assertEquals(inputStr, outputStr);

			System.err.println("私钥签名——公钥验证签名");
			// 产生签名
			byte[] sign = CertificateCoder.sign(encodedData, keyStorePath,
					alias, keyStorePassword, aliasPassword);
			System.err.println("签名:\r" + Hex.encodeHexString(sign));

			// 验证签名
			boolean status = CertificateCoder.verify(encodedData, sign,
					certificatePath);
			System.err.println("状态:\r" + status);
			assertTrue(status);
		} catch (Exception e) {
			fail("MASSAGE : " + e.getMessage() + "\nCAUSE : " + e.getCause()
					+ "\nCLASS : " + e.getClass());
		}
	}

	/**
	 * Test method for
	 * {@link z.z.w.endecryp.impl.CertificateCoder#decryptByPrivateKey(byte[], java.lang.String, java.lang.String, java.lang.String, java.lang.String)}
	 * .
	 */
	@Test
	public void testDecryptByPrivateKey() {
		try {
			System.err.println("公钥加密——私钥解密");

			byte[] data = inputStr.getBytes();

			// 加密
			byte[] encrypt = CertificateCoder.encryptByPublicKey(data,
					certificatePath);

			// 解密
			byte[] decrypt = CertificateCoder.decryptByPrivateKey(encrypt,
					keyStorePath, alias, keyStorePassword, aliasPassword);
			String outputStr = new String(decrypt);

			System.err.println("加密前: " + inputStr + "\n" + "解密后: " + outputStr);

			// 验证数据一致
			assertArrayEquals(data, decrypt);

			// 验证证书有效
			assertTrue(CertificateCoder.verifyCertificate(certificatePath));
		} catch (Exception e) {
			fail("MASSAGE : " + e.getMessage() + "\nCAUSE : " + e.getCause()
					+ "\nCLASS : " + e.getClass());
		}
	}

	/**
	 * Test method for
	 * {@link z.z.w.endecryp.impl.CertificateCoder#verifyCertificate(java.util.Date, java.lang.String)}
	 * .
	 */
	@Test
	public void testVerifyCertificateDateString() {
		try {
			System.err.println("密钥库证书有效期验证");
			boolean status = CertificateCoder.verifyCertificate(new Date(),
					keyStorePath, keyStorePassword, alias);
			System.err.println("证书状态:\r" + status);
			assertTrue(status);
		} catch (Exception e) {
			fail("MASSAGE : " + e.getMessage() + "\nCAUSE : " + e.getCause()
					+ "\nCLASS : " + e.getClass());
		}
	}

}
