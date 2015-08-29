/**
 * z.z.w.log.TestLog.java
 */
package z.z.w.log;

import test.test.log.TestLoggerFile;

/**
 * @author Wu Zhenzhen
 * @version May 30, 2013 9:27:34 AM
 */
public class TestLog {

	// static {
	// DOMConfigurator.configure("etc/log4j.xml");
	// }

	public static void main(String[] args) {
		LogUtil.setLogBackDomConfig("etc/logback.xml");
		LogUtil.getLogger(TestLog.class).info("info.....");
		LogUtil.getLogger(TestLog.class).error("error.....");
		LogUtil.getLogger(TestLog.class).debug("debug.....");
		LogUtil.getLogger(TestLog.class).warn("warn.....");
		TestLoggerFile file = new TestLoggerFile();
		file.test();
	}
}
