/**
 * z.z.w.quartz.upgrade.job.JobTest2.java
 */
package z.z.w.quartz.upgrade.job;

import java.util.Random;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import z.z.w.common.ThreadTools;

/**
 * @author Wu Zhenzhen
 * @version Dec 25, 2012 11:03:38 AM
 */
public class JobTest22 implements Job {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
	 */
	public void execute(JobExecutionContext context)
			throws JobExecutionException {

		while (true) {

			if (Moniter.isStop) {
				// System.out.println("  ---  break; TestThread--- "
				// + Thread.currentThread().getName());
				break;
			}

			Random rand = new Random(System.currentTimeMillis());
			int index = (rand.nextInt(9999) % (9999 - 1 + 1) + 1);
			// System.out.println("---:[" + (index * 1.0 / 1000) + "]s.");
			ThreadTools.sleep(index);

			// System.out.println(DateTools.getCurrentDateStr()
			// + " END use time :[" + (index * 1.0 / 1000)
			// + "]s.- TestThread --" + Thread.currentThread().getName());
		}

		// System.out
		// .println(" ---------------------Thread Test break while(true)------- TestThread --"
		// + Thread.currentThread().getName());
	}
}
