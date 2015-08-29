/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.util.common.NetTools.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-9-11 下午04:04:10 
 *   LastChange: 2013-9-11 下午04:04:10 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.util.common;

import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * z.z.w.project.util.common.NetTools.java
 */
public abstract class NetTools {

	/**
	 * 
	 * <br>
	 * Created on: 2013-9-11 下午04:04:25
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
						sb.append(DataTools.parseByteToHex(b));
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

}
