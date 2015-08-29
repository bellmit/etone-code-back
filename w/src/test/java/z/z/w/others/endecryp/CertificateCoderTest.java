/**
 * z.z.w.othertest.CertificateCoderTest.java
 */
package z.z.w.others.endecryp;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.apache.commons.codec.binary.Hex;
import org.junit.Test;

/**
 * @author Wu Zhenzhen
 * @version 2013-8-22 上午11:47:41
 */
public class CertificateCoderTest {
	private String certificatePath = "d:\\etl.cer";
	private String keyStorePath = "d:\\etl.keystore";
	private String keyStorePassword = "123456";
	private String aliasPassword = "123456";
	private String alias = "com.etone.etl";

	@Test
	public void test() throws Exception {
		System.err.println("公钥加密——私钥解密");
		String inputStr = "Ceritifcate";
		byte[] data = inputStr.getBytes();

		byte[] encrypt = CertificateCoder.encryptByPublicKey(data,
				certificatePath);

		byte[] decrypt = CertificateCoder.decryptByPrivateKey(encrypt,
				keyStorePath, alias, keyStorePassword, aliasPassword);
		String outputStr = new String(decrypt);

		System.err.println("加密前: " + inputStr + "\n\r" + "解密后: " + outputStr);

		// 验证数据一致
		assertArrayEquals(data, decrypt);

		// 验证证书有效
		assertTrue(CertificateCoder.verifyCertificate(certificatePath));

	}

	@Test
	public void testSign() throws Exception {
		System.err.println("私钥加密——公钥解密");

		String inputStr = "sign";
		byte[] data = inputStr.getBytes();

		byte[] encodedData = CertificateCoder.encryptByPrivateKey(data,
				keyStorePath, keyStorePassword, alias, aliasPassword);

		byte[] decodedData = CertificateCoder.decryptByPublicKey(encodedData,
				certificatePath);

		String outputStr = new String(decodedData);
		System.err.println("加密前: " + inputStr + "\n\r" + "解密后: " + outputStr);
		assertEquals(inputStr, outputStr);

		System.err.println("私钥签名——公钥验证签名");
		// 产生签名
		byte[] sign = CertificateCoder.sign(encodedData, keyStorePath, alias,
				keyStorePassword, aliasPassword);
		System.err.println("签名:\r" + Hex.encodeHexString(sign));

		// 验证签名
		boolean status = CertificateCoder.verify(encodedData, sign,
				certificatePath);
		System.err.println("状态:\r" + status);
		assertTrue(status);
	}

	@Test
	public void testVerify() throws Exception {
		System.err.println("密钥库证书有效期验证");
		boolean status = CertificateCoder.verifyCertificate(new Date(),
				keyStorePath, keyStorePassword, alias);
		System.err.println("证书状态:\r" + status);
		assertTrue(status);
	}
}
