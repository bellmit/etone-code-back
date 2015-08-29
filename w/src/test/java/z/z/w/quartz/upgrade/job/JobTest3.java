/**
 * z.z.w.quartz.upgrade.job.JobTest.java
 */
package z.z.w.quartz.upgrade.job;

import java.util.Random;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;

import z.z.w.common.ThreadTools;

/**
 * @author Wu Zhenzhen
 * @version Dec 24, 2012 11:36:22 AM
 */
@SuppressWarnings("deprecation")
public class JobTest3 implements StatefulJob {
	/**
	 * <br>
	 * Created on: Dec 24, 2012 11:36:22 AM
	 */
	public JobTest3() {
		// System.out.println(" -- JobTest --" +
		// Thread.currentThread().getName());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
	 */
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		// System.out.print(this.getClass().getSimpleName() + "--");
		//
		// System.out
		// .println(" getFireTime : "
		// + DateTools.getParseDateToStr(context.getFireTime())
		// + " getNextFireTime： "
		// + ((!DataTools.isEmpty(context.getNextFireTime())) ? DateTools
		// .getParseDateToStr(context.getNextFireTime())
		// : "NULL")
		// + " getPreviousFireTime： "
		// + ((!DataTools.isEmpty(context.getPreviousFireTime())) ? DateTools
		// .getParseDateToStr(context
		// .getPreviousFireTime()) : "NULL"));

		Random rand = new Random(System.currentTimeMillis());
		int index = (rand.nextInt(9999) % (9999 - 1 + 1) + 1);
		// System.out.println(":[" + (index * 1.0 / 1000) + "]s.");
		ThreadTools.sleep(index);

		// System.out.println(DateTools.getCurrentDateStr() + " END use time :["
		// + (index * 1.0 / 1000) + "]s.- TestQuartzJob --");

	}
}
