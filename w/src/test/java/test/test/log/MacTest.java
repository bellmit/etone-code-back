/**
 * test.test.log.MacTest.java
 */
package test.test.log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.BeforeClass;
import org.junit.Test;

import z.z.w.common.DataTools;
import z.z.w.env.Env;

/**
 * @author Wu Zhenzhen
 * @version 2013-8-21 上午10:29:34
 */
public class MacTest {

	private static List<String> list = new ArrayList<String>();

	@BeforeClass
	public static void beforeClass() {

		Enumeration<NetworkInterface> netInterfaces = null;
		try {
			netInterfaces = NetworkInterface.getNetworkInterfaces();
			while (netInterfaces.hasMoreElements()) {
				NetworkInterface ni = (NetworkInterface) netInterfaces
						.nextElement();
				byte[] mac = ni.getHardwareAddress();
				StringBuilder sb = new StringBuilder();
				if (!DataTools.isEmpty(mac)) {
					for (byte b : mac) {
						sb.append(toHexByte(b));
						sb.append("-");
					}

					if (sb.length() > 1)
						sb.deleteCharAt(sb.length() - 1);
					
					if (sb.length() > 1)
						list.add(sb.toString());
				}
			}
		} catch (Exception e) {
//			list = null;
		} finally {
			netInterfaces = null;
		}

		// getMacAddress();

	}

	/**
	 * <br>
	 * Created on: 2013-8-21 上午11:08:27
	 * 
	 * @param b
	 * @return
	 */
	private static String toHexByte(byte b) {
		StringBuffer sb = new StringBuffer(3);
		int v = b & 0xFF;
		String hv = Integer.toHexString(v);
		sb.append(hv);
		if (hv.length() < 2) {
			sb.append(0);
		}
		return sb.toString();
	}

	/**
	 * <br>
	 * Created on: 2013-8-21 上午10:59:29
	 */
	private static void getMacAddress() {
		BufferedReader br = null;
		Process process = null;
		Pattern macPattern = null;
		Matcher macMatcher = null;

		try {

			if (Env.getPlatform() == Env.getOsWindows())
				process = Runtime.getRuntime().exec("cmd /c ipconfig /all");
			else if (Env.getPlatform() == Env.getOsUnix())
				process = Runtime.getRuntime().exec("ifconfig");

			br = new BufferedReader(new InputStreamReader(
					process.getInputStream()));

			String line = null;

			macPattern = Pattern
					.compile("([0-9A-Fa-f]{2})(-[0-9A-Fa-f]{2}){5}");
			boolean result = false;

			while ((line = br.readLine()) != null) {
				if (DataTools.isEmpty(line))
					continue;
				macMatcher = macPattern.matcher(line);
				result = macMatcher.find();
				if (result)
					list.add(macMatcher.group(0));

			}
			br.close();
			br = null;
			macMatcher = null;
			macPattern = null;
		} catch (IOException e) {
		} finally {
			try {
				if (!DataTools.isEmpty(br)) {
					br.close();
					br = null;
				}
				macMatcher = null;
				macPattern = null;
			} catch (IOException e) {
			} finally {
				br = null;
				macMatcher = null;
				macPattern = null;
			}

		}
	}

	@Test
	public void testMacPrint() {

		for (String mac : list) {
			System.out.println(mac);
		}
	}
}
