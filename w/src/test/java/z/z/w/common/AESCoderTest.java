/**************************************************************************
 * <pre>
 *     FileName: z.z.w.common.AESCoderTest.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-9-10 下午02:33:02 
 *   LastChange: 2013-9-10 下午02:33:02 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.common;

import org.junit.Ignore;
import org.junit.Test;

/**
 * z.z.w.common.AESCoderTest.java
 */
public class AESCoderTest {

	/**
	 * Test method for
	 * {@link z.z.w.common.AESCoder#encrypt(byte[], java.security.Key)}.
	 * 
	 * @throws Exception
	 */
	@Test
	@Ignore
	public void testEncryptByteArrayKey() throws Exception {

		byte[] keyByte = AESCoder.initSecretKey();
		System.out.println(showByteArray(keyByte));
		System.out.println("                    初始密匙 ： "
				+ AESCoder.parseByte2HexStr(keyByte));

		byte[] data = "DBA".getBytes();
		byte[] key = AESCoder.encrypt(data, keyByte);
		// System.out.println(showByteArray(key));
		System.out.println("一級加密生成密匙 ：" + AESCoder.parseByte2HexStr(key));

		byte[] finRs = AESCoder.encrypt(data, key);
		// System.out.println(showByteArray(finRs));
		System.out.println("二級加密生成結果 ： " + AESCoder.parseByte2HexStr(finRs));

		byte[] deRs = AESCoder.decrypt(finRs, key);
		System.out.println(showByteArray(data) + " -- " + new String(deRs));
		System.out.println(showByteArray(deRs));
		System.out.println("                              解密 ： "
				+ AESCoder.parseByte2HexStr(deRs));

	}

	@Test
	public void testDecrypt() throws Exception {

		byte[] keyByte = AESCoder
				.parseHexStr2Byte("90A37BB1B514BAE156708C2901EFA9BC");
		// .parseHexStr2Byte("03637EE2928AA0589B582B98C187027F");
		// System.out.println(showByteArray(keyByte));
		System.out.println("                    初始密匙 ： "
				+ AESCoder.parseByte2HexStr(keyByte));

		// byte[] key = AESCoder.encrypt("DBA".getBytes(), keyByte);
		byte[] key = AESCoder.encrypt("DB_CONN_POOL".getBytes(), keyByte);
		// System.out.println(showByteArray(key));
		System.out.println("一級加密生成密匙 ：" + AESCoder.parseByte2HexStr(key));

		byte[] data = "ANDROIDAPP.3G.CN".getBytes();
		System.out
				.println("                         加密前 ： " + new String(data));
		byte[] finRs = AESCoder.encrypt(data, key);
		// System.out.println(showByteArray(finRs));
		System.out.println("二級加密生成結果 ： " + AESCoder.parseByte2HexStr(finRs));

		byte[] deRs = AESCoder.decrypt(finRs, key);
		// System.out.println(showByteArray(deRs));
		System.out.println("                              解密 ： "
				+ AESCoder.parseByte2HexStr(deRs) + " ==> " + new String(deRs));

	}

	/**
	 * <br>
	 * Created on: 2013-9-10 下午03:09:45
	 * 
	 * @param data
	 * @return
	 */
	private String showByteArray(byte[] data) {
		if (null == data) {
			return null;
		}

		StringBuilder sb = new StringBuilder("{");

		for (byte b : data) {
			sb.append(Integer.toBinaryString(b)).append(",");
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append("}");

		return sb.toString();
	}
}
