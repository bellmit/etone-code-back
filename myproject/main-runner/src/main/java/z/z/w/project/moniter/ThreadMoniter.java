/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.moniter.ThreadMoniter.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-8-23 下午02:56:57 
 *   LastChange: 2013-8-23 下午02:56:57 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.moniter;

import java.util.concurrent.atomic.AtomicInteger;

import z.z.w.project.util.common.LogTools;

/**
 * z.z.w.project.moniter.ThreadMoniter.java
 */
public class ThreadMoniter extends Thread {

	static final AtomicInteger poolNumber = new AtomicInteger(1);
	final ThreadGroup group;
	final AtomicInteger threadNumber = new AtomicInteger(1);
	final String namePrefix;

	private String threadName = "";
	private Runnable target = null;

	/**
	 * <br>
	 * Created on: 2013-8-23 下午02:57:35
	 * 
	 * @param threaName
	 * @param target
	 */
	public ThreadMoniter(String threadName, Runnable target) {
		super();
		this.threadName = threadName;
		this.target = target;

		SecurityManager s = System.getSecurityManager();
		group = (s != null) ? s.getThreadGroup() : Thread.currentThread()
				.getThreadGroup();
		namePrefix = "pool-" + poolNumber.getAndIncrement() + "-thread-";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		try {
			LogTools.getLogger(getClass()).info(
					"Server thread : [{}] started ...", threadName);

			Thread t = new Thread(group, target, namePrefix
					+ threadNumber.getAndIncrement(), 0);

			t.start();
		} catch (Exception e) {
			LogTools.getLogger(getClass())
					.warn("When [{} - {} --> Exception ] ,  \n  MASSAGE : {} \n  CAUSE :{} \n  CLASS : {}",
							new Object[] { "ThreadMoniter:run()",
									this.threadName, e.getMessage(),
									e.getCause(), e.getClass() });
		} finally {
			LogTools.getLogger(getClass()).info("Thread :[{}] is interrupted!",
					this.threadName);
		}
	}
}
