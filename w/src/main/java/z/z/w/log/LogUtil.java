/**
 * z.z.w.log.LogUtil.java
 */
package z.z.w.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.util.StatusPrinter;

/**
 * @author Wu Zhenzhen
 * @version May 30, 2013 9:21:40 AM
 */
public final class LogUtil {

	/**
	 * 
	 * <br>
	 * Created on: May 30, 2013 9:58:08 AM
	 * 
	 * @param clazz
	 * @return
	 */
	public static Logger getLogger(Class<?> clazz) {
		return LoggerFactory.getLogger(clazz);
	}

	/**
	 * 
	 * <br>
	 * Created on: 2013-7-19 下午03:25:10
	 * 
	 * @param logBackXml
	 */
	public static void setLogBackDomConfig(String logBackXml) {
		LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();

		try {
			JoranConfigurator configurator = new JoranConfigurator();
			lc.reset();
			configurator.setContext(lc);
			configurator.doConfigure(logBackXml);
		} catch (JoranException je) {
			StatusPrinter.print(lc.getStatusManager());
		}
	}

	/**
	 * 
	 * <br>
	 * Created on: 2013-7-19 下午02:46:27
	 * 
	 * @param log4jXml
	 */
	// public static void setLog4JDomConfig(String log4jXml) {
	// DOMConfigurator.configure(log4jXml);
	// }
}
