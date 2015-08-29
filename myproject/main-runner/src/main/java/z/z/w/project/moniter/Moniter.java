/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.moniter.Moniter.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-8-20 下午08:06:30 
 *   LastChange: 2013-8-20 下午08:06:30 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.moniter;

import z.z.w.common.ThreadTools;
import z.z.w.project.main.config.Global;
import z.z.w.project.util.common.LogTools;

/**
 * z.z.w.project.moniter.Moniter.java
 */
public class Moniter implements Runnable {

	/** Current server class simple name : server name */
	private final static String SERVER_NAME = Moniter.class.getName();

	/** Server config */
	private final static String XML_CONF_NAME = "//server/adapter/type[@class='"
			+ SERVER_NAME + "']";

	private final String NODE_TIME_INTERVAL_ATTR_NAME = "timeInterval";

	private final String THREAD_MAIN = "main";
	private final String THREAD_POOL = "pool";

	protected final long timeInteval;

	/**
	 * <br>
	 * Created on: 2013-8-20 下午08:06:30
	 */
	public Moniter() {
		super();
		this.timeInteval = Global.getTimeInterval(XML_CONF_NAME,
				NODE_TIME_INTERVAL_ATTR_NAME, (1000 * 60));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		while (true) {
			try {
				LogTools.getLogger(getClass())
						.info(" --------------------- Moniter Start ---------------------");
				// jvm moniter
				getMemoryStatus();

				// thread pool status moniter
				getThreadPoolStatus();

				LogTools.getLogger(getClass())
						.info(" --------------------- Moniter end -----------------------");
			} catch (RuntimeException e) {
				LogTools.getLogger(getClass())
						.warn("When [{} - {} --> RuntimeException ] ,  \n  MASSAGE : {} \n  CAUSE :{} \n  CLASS : {}",
								new Object[] { "moniter", "run()",
										e.getMessage(), e.getCause(),
										e.getClass() });
			} finally {
				ThreadTools.sleep(timeInteval);
			}
		}
	}

	/**
	 * <br>
	 * Created on: 2013-8-23 下午02:03:10
	 */
	private void getThreadPoolStatus() {

		Thread[] threadArr = ThreadTools.findAllThreads();

		for (Thread t : threadArr) {

			if (!t.getThreadGroup().getName().equalsIgnoreCase(THREAD_MAIN))
				continue;

			if (!t.getName().startsWith(THREAD_POOL))
				continue;

			LogTools.getLogger(getClass())
					.info("Status:[{}],Thread:[{}],ThreadGroup:[{}],isInterrupted:[{}],isDaemon:[{}],isAlive:[{}],getPriority:[{}].",
							new Object[] { t.getState(), t.getName(),
									t.getThreadGroup().getName(),
									// 中斷
									t.isInterrupted(),
									// 守護
									t.isDaemon(),
									// 存活
									t.isAlive(),
									// 優先級
									t.getPriority() });
		}

		LogTools.getLogger(getClass()).info("");
	}

	/**
	 * <br>
	 * Created on: 2013-8-20 下午08:07:36
	 */
	protected void getMemoryStatus() {
		int maxMemory = (int) (Runtime.getRuntime().maxMemory() / (1024 * 1024));
		int totalMemory = (int) (Runtime.getRuntime().totalMemory() / (1024 * 1024));
		int freeMemory = (int) (Runtime.getRuntime().freeMemory() / (1024 * 1024));

		LogTools.getLogger(getClass()).info("");
		LogTools.getLogger(getClass()).info(
				"Current system maxMemory :[ {} ]M.", maxMemory);
		LogTools.getLogger(getClass()).info(
				"Current system totalMemory :[ {} ]M.", totalMemory);
		LogTools.getLogger(getClass()).info(
				"Current system freeMemory :[ {} ]M.", freeMemory);
		LogTools.getLogger(getClass()).info("Useing system memory :[ {} ]M.",
				(totalMemory - freeMemory));
		LogTools.getLogger(getClass()).info("");
	}

}
