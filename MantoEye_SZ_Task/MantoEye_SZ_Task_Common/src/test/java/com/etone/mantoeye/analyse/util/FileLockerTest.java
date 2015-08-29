/**
 *  com.etone.mantoeye.analyse.util.MantoEye_SZ_Task_Common 
 */
package com.etone.mantoeye.analyse.util;

import java.io.File;

import org.junit.Test;
import org.myhkzhen.util.properties.PropertiesUtil;

/**
 * @author Wu Zhenzhen
 * @version Apr 25, 2012 4:40:32 PM
 */
public class FileLockerTest {

	/**
	 * Test method for
	 * {@link com.etone.mantoeye.analyse.util.FileLocker#tryLock()}.
	 */
	@Test
	public void testTryLock() {
		String file = PropertiesUtil.getConfigProperties("export_lock_file",
				"../../conf/config.properties");

		System.out.println(file);
		File resources = new File(file);
		FileLocker locker = new FileLocker(resources);
		if (locker.tryLock()) {

		}
	}

}
