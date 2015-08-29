/**************************************************************************
 *     FileName: TestLoggerFile.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-06-16 19:16:37
 *   LastChange: 2013-06-16 19:16:37
 *      History:
 **************************************************************************/
package test.test.log;

import org.junit.Test;

import z.z.w.log.LogUtil;

public class TestLoggerFile {

	/**
	 * <br>
	 * Created on: May 30, 2013 9:50:47 AM
	 */
	public TestLoggerFile() {

		LogUtil.getLogger(this.getClass()).info("info...constructor..");
		LogUtil.getLogger(this.getClass()).error("error..constructor...");
		LogUtil.getLogger(this.getClass()).debug("debug..constructor...");
		LogUtil.getLogger(this.getClass()).warn("warn...constructor..");
	}

	/**
	 * <br>
	 * Created on: May 30, 2013 9:51:21 AM
	 */
	@Test
	public void test() {
		LogUtil.getLogger(this.getClass()).info("info...test()..");
		LogUtil.getLogger(this.getClass()).error("error.test()....");
		LogUtil.getLogger(this.getClass()).debug("debug..test()...");
		LogUtil.getLogger(this.getClass()).warn("warn..test()...");
	}

}
