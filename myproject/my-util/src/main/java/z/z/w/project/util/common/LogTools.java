/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.util.common.LogTools.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-9-11 下午04:17:17 
 *   LastChange: 2013-9-11 下午04:17:17 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.util.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.util.StatusPrinter;

/**
 * 
 * z.z.w.project.util.common.LogTools.java
 */
public abstract class LogTools {
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
}
