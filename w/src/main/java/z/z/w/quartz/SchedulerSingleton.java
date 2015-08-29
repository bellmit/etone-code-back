/**
 * z.z.w.quartz.SchedulerSingleton.java
 */
package z.z.w.quartz;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

import z.z.w.common.DataTools;

/**
 * @author Wu Zhenzhen
 * @version Dec 19, 2012 5:19:00 PM
 */
public class SchedulerSingleton {

	private static Scheduler instance = null;
	private static final byte[] lock = new byte[0];

	/**
	 * <br>
	 * Created on: Dec 19, 2012 5:19:01 PM
	 */
	private SchedulerSingleton() {
	}

	/**
	 * <br>
	 * Created on: Dec 19, 2012 5:19:27 PM
	 * 
	 * @return the instance
	 */
	public static Scheduler getInstance() {
		synchronized (lock) {
			if (DataTools.isEmpty(instance))
				try {
					instance = StdSchedulerFactory.getDefaultScheduler();
				} catch (SchedulerException e) {
				}
			return instance;
		}
	}

}
