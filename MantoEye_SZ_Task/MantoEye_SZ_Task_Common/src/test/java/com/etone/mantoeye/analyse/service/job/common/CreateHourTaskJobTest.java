/**
 *  com.etone.mantoeye.analyse.service.job.common.MantoEye_SZ_Task_Common 
 */
package com.etone.mantoeye.analyse.service.job.common;

import static org.junit.Assert.*;

import org.junit.Test;
import org.quartz.JobExecutionException;

/**
 * @author Wu Zhenzhen
 * @version Apr 28, 2012 11:38:21 AM
 */
public class CreateHourTaskJobTest {

	/**
	 * Test method for
	 * {@link com.etone.mantoeye.analyse.service.job.common.CreateHourTaskJob#execute(org.quartz.JobExecutionContext)}
	 * .
	 */
	@Test
	public void testExecute() {
		CreateHourTaskJob job = new CreateHourTaskJob();
		try {
			job.execute(null);
		} catch (JobExecutionException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

}
