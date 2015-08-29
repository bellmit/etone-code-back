/**
 * z.z.w.common.ThreadTools.java
 */
package z.z.w.common;

/**
 * Thread tools
 * 
 * @author Wu Zhenzhen
 * @version Nov 21, 2012 5:17:37 PM
 */
public final class ThreadTools {

	/**
	 * <br>
	 * Created on: Nov 21, 2012 5:17:37 PM
	 */
	private ThreadTools() {

	}

	/**
	 * Thread sleep sec <br>
	 * Created on: Nov 21, 2012 5:19:20 PM
	 * 
	 * @param sec
	 */
	public static void sleepSeconds(int sec) {
		sleep(sec * 1000);
	}

	/**
	 * Thread sleep min <br>
	 * Created on: Nov 21, 2012 5:18:43 PM
	 * 
	 * @param min
	 */
	public static void sleepMinuts(int min) {
		sleep(min * 1000 * 60);
	}

	/**
	 * Thread sleep millis <br>
	 * Created on: Nov 21, 2012 5:18:07 PM
	 * 
	 * @param millis
	 */
	public static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
		}
	}

	/**
	 * <br>
	 * Created on: 2013-8-23 下午02:09:53
	 * 
	 * @return
	 */
	public static Thread[] findAllThreads() {

		ThreadGroup group = Thread.currentThread().getThreadGroup();
		ThreadGroup topGroup = group;
		// traverse the ThreadGroup tree to the top
		while (group != null) {
			topGroup = group;
			group = group.getParent();
		}
		// Create a destination array that is about
		// twice as big as needed to be very confident
		// that none are clipped.
		int estimatedSize = topGroup.activeCount() * 2;
		Thread[] slackList = new Thread[estimatedSize];
		// Load the thread references into the oversized
		// array. The actual number of threads loaded
		// is returned.
		int actualSize = topGroup.enumerate(slackList);
		// copy into a list that is the exact size
		Thread[] list = new Thread[actualSize];
		System.arraycopy(slackList, 0, list, 0, actualSize);
		return list;
	}

}
