/**
 * z.z.w.quartz.upgrade.ServerTest.java
 */
package z.z.w.quartz.upgrade;

import java.text.ParseException;

import org.quartz.SchedulerException;

import z.z.w.quartz.upgrade.job.JobTest2;

/**
 * @author Wu Zhenzhen
 * @version Dec 24, 2012 11:33:29 AM
 */
public class ServerTest extends QuartzServiceUpgrade {

	/**
	 * <br>
	 * Created on: Dec 24, 2012 11:35:12 AM
	 */
	public ServerTest() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see z.z.w.quartz.upgrade.QuartzServiceUpgrade#scheduleJob()
	 */
	@Override
	protected void scheduleJob() {
		try {
			// callSchedulerJob(JobTest22.class);
			// callSchedulerJob(JobTest2.class);
			// callSchedulerJob(JobTest2.class, 3);
			// callSchedulerJob(JobTest3.class, "0/5 * * * * ?");
			callSchedulerJob(JobTest2.class, "0/5 * * * * ?");
			// callSchedulerJob(JobTest.class, "0/3 * * * * ?");
		} catch (SchedulerException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	/**
	 * <br>
	 * Created on: Dec 24, 2012 11:33:48 AM
	 * 
	 * @throws SchedulerException
	 */
	public void start() throws SchedulerException {
		scheduleJobStart();
	}

	public void stop() {
		try {
			scheduleJobDown();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
}
