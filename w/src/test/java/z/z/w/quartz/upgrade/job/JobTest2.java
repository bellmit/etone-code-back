/**
 * z.z.w.quartz.upgrade.job.JobTest2.java
 */
package z.z.w.quartz.upgrade.job;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import z.z.w.common.DataTools;

/**
 * @author Wu Zhenzhen
 * @version Dec 25, 2012 11:03:38 AM
 */
public class JobTest2 implements Job {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
	 */
	public void execute(JobExecutionContext context)
			throws JobExecutionException {

		int threadNum = 5;
		ExecutorService threadPool = TestSt.get(threadNum);

		if (DataTools.isEmpty(threadPool))
			return;

		while (threadNum-- > 0) {
			Runnable thread = new TestThread(threadNum);
			threadPool.execute((thread));

			Moniter.add(thread);

		}

		// System.out.println("BEFORE ---- isShutdown : "
		// + threadPool.isShutdown() + "   isTerminated :"
		// + threadPool.isTerminated());

		threadPool.shutdown();

		// System.out.println("AFTER --- isShutdown : " +
		// threadPool.isShutdown()
		// + "   isTerminated :" + threadPool.isTerminated());

		threadPool = null;
	}

}
