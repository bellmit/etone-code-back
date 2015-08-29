/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.util.endecryp.AESCoder.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-9-12 上午11:02:17 
 *   LastChange: 2013-9-12 上午11:02:17 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.util.endecryp;

import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * z.z.w.project.util.endecryp.AESCoder.java
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
	private static Key toKey(byte[] key) {
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

}
