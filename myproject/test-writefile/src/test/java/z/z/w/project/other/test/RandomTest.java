/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.other.test.RandomTest.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-9-5 上午10:48:43 
 *   LastChange: 2013-9-5 上午10:48:43 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.other.test;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

import z.z.w.common.ThreadTools;

/**
 * z.z.w.project.other.test.RandomTest.java
 */
public class RandomTest {

	@Test
	public void testRandom() {

		while (true) {
			// System.out.println(RandomStringUtils.random(8, false, true));
			// System.out.println(RandomStringUtils.randomAlphabetic(3));
			// System.out.println(RandomStringUtils.randomAlphanumeric(4));
			System.out.println(RandomStringUtils.randomAscii(20));
			System.out.println(RandomStringUtils.randomAlphanumeric(20));
			// System.out.println(RandomStringUtils.randomAlphanumeric(3) + "."
			// + RandomStringUtils.randomAlphanumeric(6) + "."
			// + RandomStringUtils.randomAlphanumeric(8) + "."
			// + RandomStringUtils.randomAlphanumeric(3));
			System.out.println("--------------------------------------------");
			ThreadTools.sleep(200);
		}

	}
}
