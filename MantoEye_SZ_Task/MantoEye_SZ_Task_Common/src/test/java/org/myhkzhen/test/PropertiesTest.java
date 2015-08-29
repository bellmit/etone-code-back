/**
 *  org.myhkzhen.test.MantoEye_SZ_Task_Common 
 */
package org.myhkzhen.test;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.myhkzhen.util.properties.PropertiesUtil;

import com.etone.mantoeye.analyse.util.constant.Constant;

/**
 * @author Wu Zhenzhen
 * @version May 16, 2012 4:51:36 PM
 */
public class PropertiesTest {

	public static void main(String[] args) {
		
		int i = 3257;
		System.out.println(3257*1.0/1024);
		
		long len = FileUtils.sizeOf(new File("e:\\test.txt"));
		System.out.println(len);
		System.out.println(len*1.0/1024);

//		System.out.println(FileUtils.sizeOf(new File("test")));
//
//		// String fileName =
//		// PropertiesUtil.getConfigProperties(StringUtils.join(
//		// new String[]{"fileName"}, 1), StringUtils.join(
//		// Constant.APP_HOME, Constant.SEPARATOR_CHAR,
//		// Constant.CONFIG_PATH, "uidatatrans.properties"));
//
//		String config_file = StringUtils.join(new String[]{Constant.APP_HOME,
//				Constant.SEPARATOR_CHAR, Constant.CONFIG_PATH,
//				"uidatatrans.properties"});
//
//		System.out.println(config_file);
//		System.out.println("---------------------------");
//		String fn = StringUtils.join(new String[]{"fileName"}, 1);
//		System.out.println(fn);
//		System.out.println("===999==========");
//		String tempUsersTableNames = PropertiesUtil.getConfigProperties(
//				fn.toString(), config_file);
//		System.out.println(tempUsersTableNames);
//		System.out.println("=============");
//		// System.out.println(StringUtils.containsIgnoreCase("log=1", "log="));
//		// System.out.println(StringUtils.containsIgnoreCase(
//		// StringUtils.trimToEmpty("  "), "log="));
//
//		// for (int i = 0; i < 20; i++) {
//		// // final int ii = i;
//		// // Thread t = new Thread(new Runnable() {
//		// //
//		// // public void run() {
//		// LogClient.writeLog("state=306777;" + i);
//		// try {
//		// Thread.sleep(3000);
//		// } catch (InterruptedException e) {
//		// }
//		// // }
//		// // });
//		// // t.start();
//		// }
	}
}
