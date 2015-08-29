/**
 * z.z.w.quartz.upgrade.MainTest.java
 */
package z.z.w.quartz.upgrade;

import org.quartz.SchedulerException;

import z.z.w.quartz.upgrade.job.Moniter;

/**
 * @author Wu Zhenzhen
 * @version Dec 24, 2012 11:04:08 AM
 */
public class MainTest {

	/**
	 * <br>
	 * Created on: Dec 24, 2012 11:04:08 AM
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			ServerTest test = new ServerTest();
			test.scheduleJob();
			test.start();

			Moniter.setTest(test);

			Thread t = new Thread(new Moniter());
			t.start();

		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
}
