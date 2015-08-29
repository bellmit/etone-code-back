/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.util.env.Env.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-9-10 上午10:23:06 
 *   LastChange: 2013-9-10 上午10:23:06 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.util.env;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * z.z.w.project.util.env.Env.java
 */
public abstract class Env {
	private static ArrayList<Pattern> os_patterns = new ArrayList<Pattern>();
	private static int m_os = -1;

	private static final int OS_WINDOWS = 0;
	private static final int OS_MACOS = 1;
	private static final int OS_UNIX = 2;

	/**
	 * 初始化OS分类
	 */
	static {
		os_patterns.add(Pattern.compile("windows|os/2|netware"));
		os_patterns.add(Pattern.compile("mac os|macos"));
		os_patterns.add(Pattern
				.compile("unix|linux|solaris|mpe/ix|hp-ux|aix|freebsd|irix"));
	}

	/**
	 * 
	 * <br>
	 * Created on: Nov 29, 2012 4:40:35 PM
	 * 
	 * @return
	 */
	public static int getPlatform() {
		if (m_os == -1) {
			String osName = System.getProperty("os.name").toLowerCase();

			for (int i = 0; i < os_patterns.size(); i++) {
				Matcher m = os_patterns.get(i).matcher(osName);
				if (m.lookingAt() == true) {
					m_os = i;
					return m_os;
				}
			}

			m_os = OS_UNIX;
			return m_os;
		}
		return m_os;
	}

	/**
	 * <br>
	 * Created on: Nov 29, 2012 4:45:28 PM
	 * 
	 * @return the osWindows
	 */
	public static int getOsWindows() {
		return OS_WINDOWS;
	}

	/**
	 * <br>
	 * Created on: Nov 29, 2012 4:45:28 PM
	 * 
	 * @return the osMacos
	 */
	public static int getOsMacos() {
		return OS_MACOS;
	}

	/**
	 * <br>
	 * Created on: Nov 29, 2012 4:45:28 PM
	 * 
	 * @return the osUnix
	 */
	public static int getOsUnix() {
		return OS_UNIX;
	}
}
