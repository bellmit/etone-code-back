/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.spending.service.ResourceRoteServiceTest.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-11-12 下午02:47:14 
 *   LastChange: 2013-11-12 下午02:47:14 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.spending.service;

import static org.junit.Assert.fail;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * z.z.w.project.spending.service.ResourceRoteServiceTest.java
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring.xml" })
public class ResourceRoteServiceTest {

	private ResourceRoteService resourceRoteService;

	/**
	 * <br>
	 * Created on: 2013-11-12 下午02:48:05
	 * 
	 * @return the resourceRoteService
	 */
	public ResourceRoteService getResourceRoteService() {
		return resourceRoteService;
	}

	/**
	 * <br>
	 * Created on: 2013-11-12 下午02:48:05
	 * 
	 * @param resourceRoteService
	 *            the resourceRoteService to set
	 */
	@Resource
	public void setResourceRoteService(ResourceRoteService resourceRoteService) {
		this.resourceRoteService = resourceRoteService;
	}

	/**
	 * Test method for
	 * {@link z.z.w.project.spending.service.ResourceRoteService#initResourceRote()}
	 * .
	 */
	@Test
	public void testInitResourceRote() {
		try {
			resourceRoteService.initResourceRote();
		} catch (Exception e) {
			fail("MASSAGE : " + e.getMessage() + "\nCAUSE : " + e.getCause()
					+ "\nCLASS : " + e.getClass());
		}
	}

}
