/**
 * z.z.w.othertest.TestCode.java
 */
package z.z.w.others.endecryp;

import java.security.MessageDigest;

/**
 * @author Wu Zhenzhen
 * @version 2013-8-22 上午10:40:36
 */
public class TestCode {

	public byte[] encrypt(byte[] obj) {
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			// MessageDigest sha = MessageDigest.getInstance("SHA");
			md5.update(obj);
			return md5.digest();
		} catch (Exception e) {
			return null;
		}
	}

}