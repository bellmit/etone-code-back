/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.test.service.ReveExpenServiceTest.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-11-9 下午09:04:38 
 *   LastChange: 2013-11-9 下午09:04:38 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.test.service;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import z.z.w.project.test.SuperClass;

/**
 * z.z.w.project.test.service.ReveExpenServiceTest.java
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:etc/spring.xml" })
public class ReveExpenServiceTest extends SuperClass {
	private ReveExpenService reveExpenService;

	@Test
	public void testAdd() {

	}

	/**
	 * <br>
	 * Created on: 2013-11-9 下午09:05:18
	 * 
	 * @return the reveExpenService
	 */
	public ReveExpenService getReveExpenService() {
		return reveExpenService;
	}

	/**
	 * <br>
	 * Created on: 2013-11-9 下午09:05:18
	 * 
	 * @param reveExpenService
	 *            the reveExpenService to set
	 */
	@Resource
	public void setReveExpenService(ReveExpenService reveExpenService) {
		this.reveExpenService = reveExpenService;
	}

}
