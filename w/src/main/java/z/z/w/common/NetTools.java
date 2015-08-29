/**
 * z.z.w.common.NetTools.java
 */
package z.z.w.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import z.z.w.env.Env;

/**
 * @author Wu Zhenzhen
 * @version 2013-8-21 上午11:12:55
 */
public class NetTools {

	/**
	 * <br>
	 * Created on: 2013-8-21 上午11:12:55
	 */
	private NetTools() {
	}

	/**
	 * Get mac address by NetworkInterface <br>
	 * Created on: 2013-8-21 上午11:16:49
	 * 
	 * @return
	 */
	public static List<String> getMacAddressList() {
		List<String> list = new ArrayList<String>();
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
			list = null;
		} finally {
			netInterfaces = null;
		}

		return list;
	}

	/**
	 * <br>
	 * Created on: 2013-8-21 上午11:13:40
	 * 
	 * @param b
	 * @return
	 */
	private static String toHexByte(byte b) {
		StringBuffer sb = new StringBuffer(3);
		int v = b & 0xFF;
		String hv = Integer.toHexString(v);
		if (hv.length() < 2) {
			sb.append(0);
		}
		sb.append(hv);
		return sb.toString();
	}

	/**
	 * Get mac address by os system info and runtime <br>
	 * Created on: 2013-8-21 上午11:16:28
	 * 
	 * @return
	 */
	public static List<String> getWinMacAddressList() {

		List<String> list = new ArrayList<String>();

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
			list = null;
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

		return list;
	}

}
