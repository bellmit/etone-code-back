/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.util.endecryp.AESCoderTest.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-9-12 上午11:06:05 
 *   LastChange: 2013-9-12 上午11:06:05 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.util.endecryp;

import static org.junit.Assert.fail;

import org.junit.Test;

import z.z.w.project.util.common.DataTools;

/**
 * z.z.w.project.util.endecryp.AESCoderTest.java
 */
public class AESCoderTest {

	/**
	 * Test method for
	 * {@link z.z.w.project.util.endecryp.AESCoder#encrypt(byte[], byte[])}.
	 */
	@Test
	public void testEncryptByteArrayByteArray() {
		try {
			byte[] keyByte = DataTools
			// .parseHexStr2Byte("90A37BB1B514BAE156708C2901EFA9BC");
					.parseHexStr2Byte("03637EE2928AA0589B582B98C187027F");
			// System.out.println(showByteArray(keyByte));
			System.out.println("                    初始密匙 ： "
					+ DataTools.parseByte2HexStr(keyByte));

			// byte[] key = AESCoder.encrypt("DBA".getBytes(), keyByte);
			byte[] key = AESCoder.encrypt("DB_CONN_POOL".getBytes(), keyByte);
			// System.out.println(showByteArray(key));
			System.out.println("一級加密生成密匙 ：" + DataTools.parseByte2HexStr(key));

			// byte[] data = "  ".getBytes();
			byte[] data = "ANDROIDAPP.3G.CN".getBytes();
			System.out.println("                         加密前 ： "
					+ new String(data));
			byte[] finRs = AESCoder.encrypt(data, key);
			// System.out.println(showByteArray(finRs));
			// finRs = DataTools
			// .parseHexStr2Byte("");
			System.out.println("二級加密生成結果 ： "
					+ DataTools.parseByte2HexStr(finRs));
			byte[] deRs = AESCoder.decrypt(finRs, key);
			// System.out.println(showByteArray(deRs));
			// print(deRs);
			System.out.println("                              解密 ： "
					+ DataTools.parseByte2HexStr(deRs) + " ==> "
					+ new String(deRs));
		} catch (Exception e) {
			fail("MASSAGE : " + e.getMessage() + "\nCAUSE : " + e.getCause()
					+ "\nCLASS : " + e.getClass());
		}
	}

	/**
	 * <br>
	 * Created on: 2013-9-12 下午01:51:52
	 * 
	 * @param deRs
	 * @return
	 */
	// private void print(byte[] deRs) {
	// System.out.println("======================");
	// for (byte b : deRs) {
	// System.out.println(DataTools.parseByteToHex(b));
	// }
	// System.out.println("======================");
	// }

	/**
	 * <br>
	 * Created on: 2013-9-12 下午01:49:57
	 * 
	 * @param data
	 * @return
	 */
	// private String showByteArray(byte[] data) {
	// if (null == data) {
	// return null;
	// }
	//
	// StringBuilder sb = new StringBuilder("{");
	//
	// for (byte b : data) {
	// sb.append(Integer.toBinaryString(b)).append(",");
	// }
	// // sb.deleteCharAt(sb.length() - 1);
	// sb.append("}");
	//
	// return sb.toString();
	// }
}
