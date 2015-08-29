/**
 * z.z.w.quartz.upgrade.QuartzServiceUpgrade.java
 */
package z.z.w.quartz.upgrade;

import java.text.ParseException;
import java.util.List;

import org.quartz.CronScheduleBuilder;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;

import z.z.w.quartz.SchedulerSingleton;

/**
 * @author Wu Zhenzhen
 * @version Dec 24, 2012 11:13:49 AM
 */
public abstract class QuartzServiceUpgrade {

	// private static final String SERVER_NAME = QuartzServiceUpgrade.class
	// .getSimpleName();

	private static final String QUARTZ_TRIGGER = "_TRIGGER";
	private static final String QUARTZ_JOB = "_JOB";
	private static final String QUARTZ_GROUP = "_GROUP";

	private final Scheduler scheduler = SchedulerSingleton.getInstance();

	protected abstract void scheduleJob();

	/**
	 * <br>
	 * Created on: Dec 24, 2012 11:15:13 AM
	 */
	public QuartzServiceUpgrade() {
		super();
	}

	/**
	 * 
	 * <br>
	 * Created on: Dec 24, 2012 11:16:02 AM
	 * 
	 * @param jobClass
	 * @throws SchedulerException
	 */
	protected void callSchedulerJob(Class<? extends Job> jobClass)
			throws SchedulerException {

		String classSimpleName = jobClass.getSimpleName();
		String triggerName = classSimpleName + QUARTZ_TRIGGER;
		String jobName = classSimpleName + QUARTZ_JOB;
		String groupName = classSimpleName + QUARTZ_GROUP;

		Trigger trigger = TriggerBuilder
				.newTrigger()
				.withIdentity(triggerName, groupName)
				.startNow()
				.withSchedule(
						SimpleScheduleBuilder.simpleSchedule().withRepeatCount(
								0)).build();

		JobDetail execJob = JobBuilder.newJob(jobClass)
				.withIdentity(jobName, groupName).build();

		scheduler.scheduleJob(execJob, trigger);
	}

	/**
	 * 
	 * <br>
	 * Created on: Apr 16, 2013 8:27:05 PM
	 * 
	 * @param classSimpleName
	 * @param jobClass
	 * @throws SchedulerException
	 */
	protected void callSchedulerJob(String classSimpleName,
			Class<? extends Job> jobClass) throws SchedulerException {

		String triggerName = classSimpleName + QUARTZ_TRIGGER;
		String jobName = classSimpleName + QUARTZ_JOB;
		String groupName = classSimpleName + QUARTZ_GROUP;

		Trigger trigger = TriggerBuilder
				.newTrigger()
				.withIdentity(triggerName, groupName)
				.startNow()
				.withSchedule(
						SimpleScheduleBuilder.simpleSchedule().withRepeatCount(
								0)).build();

		JobDetail execJob = JobBuilder.newJob(jobClass)
				.withIdentity(jobName, groupName).build();

		scheduler.scheduleJob(execJob, trigger);
	}

	/**
	 * call reschedular job <br>
	 * Created on: Mar 26, 2013 9:16:12 PM
	 * 
	 * @param classSimpleName
	 * @throws SchedulerException
	 */
	protected void callReSchedulerJob(String classSimpleName)
			throws SchedulerException {

		String triggerName = classSimpleName + QUARTZ_TRIGGER;
		String groupName = classSimpleName + QUARTZ_GROUP;

		Trigger trigger = TriggerBuilder
				.newTrigger()
				.withIdentity(triggerName, groupName)
				.startNow()
				.withSchedule(
						SimpleScheduleBuilder.simpleSchedule().withRepeatCount(
								0)).build();

		TriggerKey triggerKey = new TriggerKey(triggerName, groupName);

		scheduler.rescheduleJob(triggerKey, trigger);

	}

	/**
	 * 
	 * <br>
	 * Created on: Dec 20, 2012 5:00:59 PM
	 * 
	 * @param jobClass
	 *            exec task class
	 * @param cronSchedule
	 *            cron expression [sec] [min] [hour] [day] [month] [week] [year]
	 *            ex:(0/5 * * * * ?)
	 * @throws ParseException
	 * @throws SchedulerException
	 */
	protected void callSchedulerJob(Class<? extends Job> jobClass,
			String cronSchedule) throws ParseException, SchedulerException {

		String classSimpleName = jobClass.getSimpleName();
		String triggerName = classSimpleName + QUARTZ_TRIGGER;
		String jobName = classSimpleName + QUARTZ_JOB;
		String groupName = classSimpleName + QUARTZ_GROUP;

		Trigger trigger = TriggerBuilder.newTrigger()
				.withIdentity(triggerName, groupName).startNow()
				.withSchedule(CronScheduleBuilder.cronSchedule(cronSchedule))
				.build();

		JobDetail execJob = JobBuilder.newJob(jobClass)
				.withIdentity(jobName, groupName).build();

		scheduler.scheduleJob(execJob, trigger);

	}

	/**
	 * 
	 * <br>
	 * Created on: Apr 16, 2013 8:27:40 PM
	 * 
	 * @param classSimpleName
	 * @param jobClass
	 * @param cronSchedule
	 * @throws ParseException
	 * @throws SchedulerException
	 */
	protected void callSchedulerJob(String classSimpleName,
			Class<? extends Job> jobClass, String cronSchedule)
			throws ParseException, SchedulerException {

		String triggerName = classSimpleName + QUARTZ_TRIGGER;
		String jobName = classSimpleName + QUARTZ_JOB;
		String groupName = classSimpleName + QUARTZ_GROUP;

		Trigger trigger = TriggerBuilder.newTrigger()
				.withIdentity(triggerName, groupName).startNow()
				.withSchedule(CronScheduleBuilder.cronSchedule(cronSchedule))
				.build();

		JobDetail execJob = JobBuilder.newJob(jobClass)
				.withIdentity(jobName, groupName).build();

		scheduler.scheduleJob(execJob, trigger);

	}

	/**
	 * call reschedular job <br>
	 * Created on: Mar 26, 2013 9:17:31 PM
	 * 
	 * @param classSimpleName
	 * @param cronSchedule
	 *            cron expression [sec] [min] [hour] [day] [month] [week] [year]
	 *            ex:(0/5 * * * * ?)
	 * @throws ParseException
	 * @throws SchedulerException
	 */
	protected void callReSchedulerJob(String classSimpleName,
			String cronSchedule) throws ParseException, SchedulerException {

		String triggerName = classSimpleName + QUARTZ_TRIGGER;
		String groupName = classSimpleName + QUARTZ_GROUP;

		Trigger trigger = TriggerBuilder.newTrigger()
				.withIdentity(triggerName, groupName).startNow()
				.withSchedule(CronScheduleBuilder.cronSchedule(cronSchedule))
				.build();

		TriggerKey triggerKey = new TriggerKey(triggerName, groupName);

		scheduler.rescheduleJob(triggerKey, trigger);

	}

	/**
	 * 
	 * <br>
	 * Created on: Dec 25, 2012 12:03:54 PM
	 * 
	 * @param jobClass
	 * @param minute
	 * @throws SchedulerException
	 */
	protected void callSchedulerJob(Class<? extends Job> jobClass, int minute)
			throws SchedulerException {

		String classSimpleName = jobClass.getSimpleName();
		String triggerName = classSimpleName + QUARTZ_TRIGGER;
		String jobName = classSimpleName + QUARTZ_JOB;
		String groupName = classSimpleName + QUARTZ_GROUP;

		SimpleScheduleBuilder.simpleSchedule().withIntervalInMinutes(minute);
		Trigger trigger = TriggerBuilder
				.newTrigger()
				.withIdentity(triggerName, groupName)
				.startNow()
				.withSchedule(
						SimpleScheduleBuilder.repeatMinutelyForever(minute))
				.build();

		JobDetail execJob = JobBuilder.newJob(jobClass)
				.withIdentity(jobName, groupName).build();

		scheduler.scheduleJob(execJob, trigger);
	}

	/**
	 * 
	 * <br>
	 * Created on: Apr 16, 2013 8:27:58 PM
	 * 
	 * @param classSimpleName
	 * @param jobClass
	 * @param minute
	 * @throws SchedulerException
	 */
	protected void callSchedulerJob(String classSimpleName,
			Class<? extends Job> jobClass, int minute)
			throws SchedulerException {

		String triggerName = classSimpleName + QUARTZ_TRIGGER;
		String jobName = classSimpleName + QUARTZ_JOB;
		String groupName = classSimpleName + QUARTZ_GROUP;

		SimpleScheduleBuilder.simpleSchedule().withIntervalInMinutes(minute);
		Trigger trigger = TriggerBuilder
				.newTrigger()
				.withIdentity(triggerName, groupName)
				.startNow()
				.withSchedule(
						SimpleScheduleBuilder.repeatMinutelyForever(minute))
				.build();

		JobDetail execJob = JobBuilder.newJob(jobClass)
				.withIdentity(jobName, groupName).build();

		scheduler.scheduleJob(execJob, trigger);
	}

	/**
	 * call reschedular job <br>
	 * Created on: Mar 26, 2013 9:18:19 PM
	 * 
	 * @param classSimpleName
	 * @param minute
	 * @throws SchedulerException
	 */
	protected void callReSchedulerJob(String classSimpleName, int minute)
			throws SchedulerException {

		String triggerName = classSimpleName + QUARTZ_TRIGGER;
		String groupName = classSimpleName + QUARTZ_GROUP;

		SimpleScheduleBuilder.simpleSchedule().withIntervalInMinutes(minute);
		Trigger trigger = TriggerBuilder
				.newTrigger()
				.withIdentity(triggerName, groupName)
				.startNow()
				.withSchedule(
						SimpleScheduleBuilder.repeatMinutelyForever(minute))
				.build();

		TriggerKey triggerKey = new TriggerKey(triggerName, groupName);

		scheduler.rescheduleJob(triggerKey, trigger);
	}

	/**
	 * 
	 * <br>
	 * Created on: Dec 25, 2012 4:21:17 PM
	 * 
	 * @throws SchedulerException
	 */
	protected void scheduleJobStart() throws SchedulerException {
		scheduler.start();
	}

	/**
	 * 
	 * <br>
	 * Created on: Dec 25, 2012 4:21:50 PM
	 * 
	 * @throws SchedulerException
	 */
	protected void scheduleJobDown() throws SchedulerException {
		scheduler.shutdown(true);
	}

	/**
	 * Get all execute jobs <br>
	 * Created on: Mar 26, 2013 9:14:44 PM
	 * 
	 * @return
	 * @throws SchedulerException
	 */
	protected List<String> getJobGroupNames() throws SchedulerException {
		return scheduler.getJobGroupNames();
	}

}
