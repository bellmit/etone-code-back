/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.test.macaddress.TestMacAddress.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-8-21 上午11:27:45 
 *   LastChange: 2013-8-21 上午11:27:45 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.test.macaddress;

import z.z.w.common.NetTools;
import z.z.w.endecryp.impl.CertificateCoder;
import z.z.w.project.main.config.Global;
import z.z.w.project.util.common.LogTools;

/**
 * z.z.w.project.test.macaddress.TestMacAddress.java
 */
public class TestMacAddress implements Runnable {

	private byte[] sign = null;
	public static String keyStorePath = "/opt/zzw.keystore";
	public static String keyStorePassword = "1qaz2wsx";
	private String aliasPassword = "2wsx3edc";
	public static String alias = "z.z.w";

	/**
	 * <br>
	 * Created on: 2013-8-21 上午11:27:45
	 */
	public TestMacAddress() {
		super();

		try {
			byte[] encodedData = CertificateCoder.encryptByPrivateKey(
					"10-BF-48-47-F7-6F".toLowerCase().getBytes(), keyStorePath,
					keyStorePassword, alias, aliasPassword);

			sign = CertificateCoder.sign(encodedData, keyStorePath, alias,
					keyStorePassword, aliasPassword);

		} catch (Exception e) {
			LogTools.getLogger(getClass())
					.error("When [{} - {} --> Exception ] ,  \n  MASSAGE : {} \n  CAUSE :{} \n  CLASS : {}",
							new Object[] { "TestMacAddress", "run()",
									e.getMessage(), e.getCause(), e.getClass() });
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	public void run() {

		try {
			boolean isOk = false;
			for (String macAddress : NetTools.getMacAddressList()) {
				LogTools.getLogger(getClass()).info(
						"Mac getMacAddressList --> [{}].", macAddress);
				try {
					byte[] encodedData = CertificateCoder.encryptByPrivateKey(
							macAddress.getBytes(), keyStorePath,
							keyStorePassword, alias, aliasPassword);

					// 验证签名
					boolean status = CertificateCoder.verify(encodedData, sign,
							Global.CERTIFICATE_FILE);

					if (status) {
						isOk = true;
						break;
					}

				} catch (Exception e) {
					LogTools.getLogger(getClass())
							.error("When [{} - {} --> Exception ] ,  \n  MASSAGE : {} \n  CAUSE :{} \n  CLASS : {}",
									new Object[] { "TestMacAddress", "run()",
											e.getMessage(), e.getCause(),
											e.getClass() });
				}
			}

			if (isOk)
				LogTools.getLogger(getClass()).info("Mac address is ok!");
			else {
				LogTools.getLogger(getClass()).info("Mac address is fail!");
				System.exit(0);
			}

		} catch (Exception e) {
			LogTools.getLogger(getClass())
					.error("When [{} - {} --> Exception ] ,  \n  MASSAGE : {} \n  CAUSE :{} \n  CLASS : {}",
							new Object[] { "TestMacAddress", "run()",
									e.getMessage(), e.getCause(), e.getClass() });
		}
	}

}
