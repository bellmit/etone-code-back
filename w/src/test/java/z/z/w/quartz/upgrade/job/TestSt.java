/**
 * z.z.w.quartz.upgrade.job.TestSt.java
 */
package z.z.w.quartz.upgrade.job;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Wu Zhenzhen
 * @version Apr 16, 2013 3:08:08 PM
 */
public class TestSt {
	private static int log = 0;

	private TestSt() {
	}

	public static ExecutorService get(int threadNum) {
		System.out.println(log);
		System.out.println(threadNum);
		if (log != threadNum) {
			ExecutorService s = Executors.newFixedThreadPool(threadNum);
			log = threadNum;
			return s;
		}

		return null;

	}
}
