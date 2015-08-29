/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.test.MainRunner.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-11-8 上午11:36:18 
 *   LastChange: 2013-11-8 上午11:36:18 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.test;

import z.z.w.project.common.config.InitPro;
import z.z.w.project.util.common.LogTools;

/**
 * z.z.w.project.test.MainRunner.java
 */
public final class MainRunner extends InitPro {

	/**
	 * <br>
	 * Created on: 2013-11-8 上午11:36:18
	 */
	private MainRunner() {

	}

	/**
	 * 
	 * <br>
	 * Created on: 2013-11-8 上午11:36:35
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		MainRunner runner = new MainRunner();
		runner.startWork();
	}

	/**
	 * <br>
	 * Created on: 2013-11-8 上午11:36:55
	 */
	private void startWork() {
		try {

		} catch (Exception e) {
			LogTools.getLogger(getClass())
					.error("When [{} - {} --> Exception ] ,  \n  MASSAGE : {} \n  CAUSE :{} \n  CLASS : {}",
							new Object[] { "MainRunner", "startWork()",
									e.getMessage(), e.getCause(), e.getClass() });
		}
	}

}
