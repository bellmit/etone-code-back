/**
 * z.z.w.quartz.upgrade.job.TestThread.java
 */
package z.z.w.quartz.upgrade.job;

import java.util.Random;

import z.z.w.common.DateTools;
import z.z.w.common.ThreadTools;

/**
 * @author Wu Zhenzhen
 * @version Dec 25, 2012 11:04:00 AM
 */
public class TestThread implements Runnable {
	private int i = 0;

	/**
	 * <br>
	 * Created on: Dec 25, 2012 11:10:32 AM
	 */
	public TestThread(int i) {
		this.i = i;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		while (true) {

			if (Moniter.isStop) {
				// System.out.println("  ---  break; TestThread " + i + "--- "
				// + Thread.currentThread().getName());
				// System.out.println(" before remove : " + Moniter.size()
				// + " -- " + Thread.currentThread().getName());
				Moniter.remove(this);
				// System.out.println(" after remove : " + Moniter.size() +
				// " -- "
				// + Thread.currentThread().getName());
				break;
			}

			Random rand = new Random(System.currentTimeMillis());
			int index = (rand.nextInt(9999) % (9999 - 1 + 1) + 1);
			// System.out.println("---:[" + (index * 1.0 / 1000) + "]s.");
			ThreadTools.sleep(index);

			System.out.println(DateTools.getCurrentDateStr()
					+ " END use time :[" + (index * 1.0 / 1000)
					+ "]s.- TestThread --" + i + " --- "
					+ Thread.currentThread().getName());
		}

		System.out.println(" ---------------------Thread Test " + i
				+ " break while(true)-------------------------");

	}
}
