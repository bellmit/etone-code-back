/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.common.config.InitPro.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-11-8 上午01:43:58 
 *   LastChange: 2013-11-8 上午01:43:58 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.common.config;

import java.io.File;

import z.z.w.project.util.common.DataTools;
import z.z.w.project.util.common.LogTools;
import z.z.w.project.util.file.FileTools;

/**
 * z.z.w.project.common.config.InitPro.java
 */
public abstract class InitPro {
	static {
		// load log4j config xml
		if (DataTools.isEmpty(Global.LOG_CFG_FILE)
				|| !(new File(Global.LOG_CFG_FILE).exists())) {

			System.out.println("LOG4J CONFIG [" + Global.LOG_CFG_FILE
					+ "] NOT EXIST. PROGREAM EXIT!");
			System.exit(0);
		}

		LogTools.setLogBackDomConfig(Global.LOG_CFG_FILE);
	}

	static {
		// check Only one instance exec.
		if (!FileTools
				.tryLock(Global.getLockFile(InitPro.class.getSimpleName()))) {
			LogTools.getLogger(InitPro.class).warn(
					"Another instance is running!");
			System.exit(0);
		}
	}

	/**
	 * <br>
	 * Created on: 2013-11-8 上午01:43:58
	 */
	protected InitPro() {
		super();
	}

}
