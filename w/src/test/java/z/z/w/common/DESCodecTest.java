/**
 * z.z.w.common.DESCodecTest.java
 */
package z.z.w.common;

/**
 * @author Wu Zhenzhen
 * @version Apr 12, 2013 12:49:44 PM
 */
public class DESCodecTest {

	public static void main(String[] args) {

		/**
		 * <pre>
		 * 佛山業務翻譯服務器MAC地址
		 * [00:25:90:C7:76:C2].E7l/WYFgLCWF+yeV4bUpcYmDEVqbCr56
		 * [00:25:90:C7:76:C3].E7l/WYFgLCWF+yeV4bUpce8C5akf/fdd
		 * MYSQL數據庫密碼
		 * ^yhnbgt&u
		 * iq數據庫密碼
		 * SQL
		 *  KEY_STORE_PATH = "/usr/include/etl.keystore";
		 * 	 KEY_STORE_PASSWD = "&*(iu78jhyGhyp0W#$Edf";
		 * 	 ALIAS_PASSWD = "&*(iu78jhyGhyp0W#$Edf";
		 * 	 ALIAS = "com.etone.etl";
		 * 	 CERTIFICATE_FILE = CommonUtils
		 * 			.replaceProHome("$PRO_HOME/etc/etl.cer");
		 * 
		 * 	 MAC_ADDRESS_1 = "00:25:90:C7:76:C2"
		 * 			.toLowerCase();
		 * 	 MAC_ADDRESS_2 = "00:25:90:C7:76:C3"
		 * 			.toLowerCase();
		 * </pre>
		 */
		String source = "SQL";
		// String source = "00:25:90:C7:76:C3".toLowerCase();
		System.out.println("原文: " + source);

		String key = "CACHE_DB_POOL";
		// String key = DESCodec.initkey();
		System.out.println("密钥: " + key);

		String encryptData = DESCodec.encrypt(source, key);
		System.out.println("加密: " + encryptData);

		String decryptData = DESCodec.decrypt(encryptData, key);
		System.out.println("解密: " + decryptData);
	}

}
