/**
 *  com.etone.mantoeye.analyse.service.job.specific.MantoEye_SZ_Task_Specific 
 */
package com.etone.mantoeye.analyse.specific;

import org.myhkzhen.util.log.Logger;
import org.myhkzhen.util.log.LoggerFactory;

/**
 * 特定JOB功能實現接口
 * 
 * @author Wu Zhenzhen
 * @version May 22, 2012 3:55:24 PM
 */
public interface ISpecificTaskJob {

	static final Logger logger = LoggerFactory
			.getLogger(ISpecificTaskJob.class);

	/**
	 * 用戶自定義功能執行方法 <br>
	 * Created on: May 22, 2012 3:56:11 PM
	 * 
	 */
	public void execute();

}
