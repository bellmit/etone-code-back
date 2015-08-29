/**************************************************************************
 * <pre>
 *     FileName: z.z.w.common.AESCoder.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-9-10 下午02:24:09 
 *   LastChange: 2013-9-10 下午02:24:09 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.common;

import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.net.util.Base64;

/**
 * z.z.w.common.AESCoder.java
 */
public abstract class AESCoder {
	/**
	 * 
	 * 密钥算法
	 */
	private static final String KEY_ALGORITHM = "AES";
	private static final String DEFAULT_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";

	/**
	 * 初始化密钥 <br>
	 * Created on: 2013-9-10 下午02:25:43
	 * 
	 * @return byte[] 密钥
	 */
	public static byte[] initSecretKey() {
		// 返回生成指定算法的秘密密钥的 KeyGenerator 对象
		KeyGenerator kg = null;
		try {
			kg = KeyGenerator.getInstance(KEY_ALGORITHM);
			// 初始化此密钥生成器，使其具有确定的密钥大小
			// AES 要求密钥长度为 128
			kg.init(128);
			// 生成一个密钥
			SecretKey secretKey = kg.generateKey();
			return (secretKey.getEncoded());
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
	}

	/**
	 * 转换密钥 <br>
	 * Created on: 2013-9-10 下午02:27:00
	 * 
	 * @param key
	 *            二进制密钥
	 * @return 密钥
	 */
	public static Key toKey(byte[] key) {
		try {
			// 生成密钥
			return new SecretKeySpec(key, KEY_ALGORITHM);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 加密 <br>
	 * Created on: 2013-9-10 下午02:28:07
	 * 
	 * @param data
	 *            待加密数据
	 * @param key
	 *            密钥
	 * @return 加密数据
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] data, Key key) throws Exception {
		return encrypt(data, key, DEFAULT_CIPHER_ALGORITHM);
	}

	/**
	 * 加密 <br>
	 * Created on: 2013-9-10 下午02:28:07
	 * 
	 * @param data
	 *            待加密数据
	 * @param key
	 *            密钥
	 * @return 加密数据
	 * @throws Exception
	 */
	public static String encryptStr(String data, String key) throws Exception {
		return Base64.encodeBase64String(encrypt(data.getBytes(),
				Base64.decodeBase64(key), DEFAULT_CIPHER_ALGORITHM));
	}

	/**
	 * 加密 <br>
	 * Created on: 2013-9-10 下午02:28:07
	 * 
	 * @param data
	 *            待加密数据
	 * @param key
	 *            密钥
	 * @return 加密数据
	 * @throws Exception
	 */
	public static byte[] encrypt(String data, String key) throws Exception {
		return (encrypt(data.getBytes(), Base64.decodeBase64(key),
				DEFAULT_CIPHER_ALGORITHM));
	}

	/**
	 * 加密 <br>
	 * Created on: 2013-9-10 下午02:28:45
	 * 
	 * @param data
	 *            待加密数据
	 * @param key
	 *            密钥
	 * @return 加密数据
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] data, byte[] key) throws Exception {
		return encrypt(data, key, DEFAULT_CIPHER_ALGORITHM);
	}

	/**
	 * 加密 <br>
	 * Created on: 2013-9-10 下午02:29:26
	 * 
	 * @param data
	 *            待加密数据
	 * @param key
	 *            密钥
	 * @param cipherAlgorithm
	 *            加密算法/工作模式/填充方式
	 * @return 加密数据
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] data, byte[] key, String cipherAlgorithm)
			throws Exception {
		// 还原密钥
		Key k = toKey(key);
		return encrypt(data, k, cipherAlgorithm);
	}

	/**
	 * 加密 <br>
	 * Created on: 2013-9-10 下午02:29:58
	 * 
	 * @param data
	 *            待加密数据
	 * @param key
	 *            密钥
	 * @param cipherAlgorithm
	 *            加密算法/工作模式/填充方式
	 * @return 加密数据
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] data, Key key, String cipherAlgorithm)
			throws Exception {
		// 实例化
		Cipher cipher = Cipher.getInstance(cipherAlgorithm);
		// 使用密钥初始化，设置为加密模式
		cipher.init(Cipher.ENCRYPT_MODE, key);
		// 执行操作
		return cipher.doFinal(data);
	}

	/**
	 * 解密 <br>
	 * Created on: 2013-9-10 下午02:30:28
	 * 
	 * @param data
	 *            待解密数据
	 * @param key
	 *            二进制密钥
	 * @return 解密数据
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] data, byte[] key) throws Exception {
		return decrypt(data, key, DEFAULT_CIPHER_ALGORITHM);
	}

	/**
	 * 解密 <br>
	 * Created on: 2013-9-10 下午02:30:57
	 * 
	 * @param data
	 *            待解密数据
	 * @param key
	 *            二进制密钥
	 * @return 解密数据
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] data, Key key) throws Exception {
		return decrypt(data, key, DEFAULT_CIPHER_ALGORITHM);
	}

	/**
	 * 解密 <br>
	 * Created on: 2013-9-10 下午02:31:14
	 * 
	 * @param data
	 *            待解密数据
	 * @param key
	 *            二进制密钥
	 * @param cipherAlgorithm
	 *            加密算法/工作模式/填充方式
	 * @return 解密数据
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] data, byte[] key, String cipherAlgorithm)
			throws Exception {
		// 还原密钥
		Key k = toKey(key);
		return decrypt(data, k, cipherAlgorithm);
	}

	/**
	 * 解密 <br>
	 * Created on: 2013-9-10 下午02:31:50
	 * 
	 * @param data
	 *            待解密数据
	 * @param key
	 *            二进制密钥
	 * @param cipherAlgorithm
	 *            加密算法/工作模式/填充方式
	 * @return 解密数据
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] data, Key key, String cipherAlgorithm)
			throws Exception {
		// 实例化
		Cipher cipher = Cipher.getInstance(cipherAlgorithm);
		// 使用密钥初始化，设置为解密模式
		cipher.init(Cipher.DECRYPT_MODE, key);
		// 执行操作
		return cipher.doFinal(data);
	}

	/**
	 * 将16进制转换为二进制
	 * 
	 * @param hexStr
	 * @return
	 */
	public static byte[] parseHexStr2Byte(String hexStr) {
		if (hexStr.length() < 1)
			return null;
		byte[] result = new byte[hexStr.length() / 2];
		for (int i = 0; i < hexStr.length() / 2; i++) {
			int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2),
					16);
			result[i] = (byte) (high * 16 + low);
		}
		return result;
	}

	/**
	 * 将二进制转换成16进制
	 * 
	 * @param buf
	 * @return
	 */
	public static String parseByte2HexStr(byte buf[]) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buf.length; i++) {
			String hex = Integer.toHexString(buf[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}

}
