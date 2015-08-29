package z.z.w.product.fs;

/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.SuperClass.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-8-20 下午08:23:22 
 *   LastChange: 2013-8-20 下午08:23:22 
 *      History:
 * </pre>
 **************************************************************************/

import java.io.File;

import z.z.w.common.DataTools;
import z.z.w.log.LogUtil;
import z.z.w.project.main.config.Global;

/**
 * z.z.w.project.SuperClass.java
 */
public class SuperClass {

	static {
		// load log4j config xml
		if (DataTools.isEmpty(Global.LOG_CFG_FILE)
				|| !(new File(Global.LOG_CFG_FILE).exists())) {

			System.out.println("LOG4J CONFIG [" + Global.LOG_CFG_FILE
					+ "] NOT EXIST. PROGREAM EXIT!");
			System.exit(0);
		}

		LogUtil.setLogBackDomConfig(Global.LOG_CFG_FILE);
	}
}
