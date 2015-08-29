/**
 *  com.etone.mantoeye.analyse.service.job.common.MantoEye_SZ_Task_Common 
 */
package com.etone.mantoeye.analyse.service.job.common;

import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.quartz.JobExecutionException;

/**
 * @author Wu Zhenzhen
 * @version Apr 28, 2012 11:45:19 AM
 */
@Ignore
public class ExecCommonTaskJobTest {

	@Before
	public void before() {
		CreateHourTaskJob job = new CreateHourTaskJob();
		try {
			job.execute(null);
		} catch (JobExecutionException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	/**
	 * Test method for
	 * {@link com.etone.mantoeye.analyse.service.job.common.ExecCommonTaskJob#execute(org.quartz.JobExecutionContext)}
	 * .
	 */
	@Test
	public void testExecute() {
		ExecCommonTaskJob job = new ExecCommonTaskJob();
		try {
			job.execute(null);
		} catch (JobExecutionException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
}
