/**
 * z.z.w.common.DESCodecTest.java
 */
package z.z.w.common;

/**
 * @author Wu Zhenzhen
 * @version Apr 12, 2013 12:49:44 PM
 */
public class DESCodecTest_197 {

	public static void main(String[] args) {

		/**
		 * <pre>
		 * 佛山業務翻譯服務器MAC地址
		 * [00-21-5e-52-20-b6].73nEOQ/jRXrcvwcKtJ0hXQuId54DsxrN
		 * [00-21-5e-52-20-b4].73nEOQ/jRXrcvwcKtJ0hXVXh1am1I2J9
		 * MYSQL數據庫密碼
		 * ^yhnbgt&u
		 *  KEY_STORE_PATH = "/usr/include/etl.keystore";
		 * 	 KEY_STORE_PASSWD = "&*(iu78jhyGhyp0W#$Edf";
		 * 	 ALIAS_PASSWD = "&*(iu78jhyGhyp0W#$Edf";
		 * 	 ALIAS = "com.etone.etl";
		 * 	 CERTIFICATE_FILE = CommonUtils
		 * 			.replaceProHome("$PRO_HOME/etc/etl.cer");
		 * 
		 * 	 MAC_ADDRESS_1 = "00-21-5E-52-20-B4"
		 * 			.toLowerCase();
		 * 	 MAC_ADDRESS_2 = "00-21-5E-52-20-B6"
		 * 			.toLowerCase();
		 * </pre>
		 */
		String source = "00-21-5E-52-20-B6".toLowerCase();
		System.out.println("原文: " + source);

		String key = "KEY_STORE_PATH";
		// String key = DESCodec.initkey();
		System.out.println("密钥: " + key);

		String encryptData = DESCodec.encrypt(source, key);
		System.out.println("加密: " + encryptData);

		String decryptData = DESCodec.decrypt(encryptData, key);
		System.out.println("解密: " + decryptData);
	}

}
