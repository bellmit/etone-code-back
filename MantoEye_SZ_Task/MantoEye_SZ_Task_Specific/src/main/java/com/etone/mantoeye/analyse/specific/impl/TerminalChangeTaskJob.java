/**
 *  com.etone.mantoeye.analyse.specific.impl.MantoEye_SZ_Task_Specific 
 */
package com.etone.mantoeye.analyse.specific.impl;

import com.etone.mantoeye.analyse.specific.ISpecificTaskJob;

/**
 * @author Wu Zhenzhen
 * @version May 22, 2012 4:01:04 PM
 */
public class TerminalChangeTaskJob implements ISpecificTaskJob {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.etone.mantoeye.analyse.specific.ISpecificTaskJob#execute()
	 */
	public void execute() {
		logger.info("[SpecificTaskJob] -- Exec Terminal Change Job.");
	}

}
