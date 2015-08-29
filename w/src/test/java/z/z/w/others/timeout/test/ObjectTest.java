/**
 * z.z.w.test.ObjectTest.java
 */
package z.z.w.others.timeout.test;

/**
 * @author Wu Zhenzhen
 * @version Apr 16, 2013 10:57:09 AM
 */
public class ObjectTest {

	/**
	 * <br>
	 * Created on: Apr 16, 2013 10:57:09 AM
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		TestPersion tp = new TestPersion();

		int maxMemory = (int) (Runtime.getRuntime().maxMemory() / (1024 * 1024));
		int totalMemory = (int) (Runtime.getRuntime().totalMemory() / (1024 * 1024));
		int freeMemory = (int) (Runtime.getRuntime().freeMemory() / (1024 * 1024));

		System.out.println("Current system maxMemory : " + maxMemory);
		System.out.println("Current system totalMemory : " + totalMemory);
		System.out.println("Current system freeMemory : " + freeMemory);
		System.out.println(totalMemory - freeMemory);

	}
}
