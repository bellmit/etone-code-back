/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.test.service.DemoUserServiceImplTest.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-11-2 下午08:57:17 
 *   LastChange: 2013-11-2 下午08:57:17 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.test.service;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import z.z.w.project.test.model.TestUser;

/**
 * z.z.w.project.test.service.DemoUserServiceImplTest.java
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring.xml",
		"classpath:spring-hibernate.xml" })
public class DemoUserServiceImplTest {

	private DemoUserService demoUserServiceImpl;

	/**
	 * Test method for
	 * {@link z.z.w.project.test.service.DemoUserService#addUser(z.z.w.project.test.model.TestUser)}
	 * .
	 */
	@Test
	public void testAddUser() {
		TestUser testUser = new TestUser();
		testUser.setName("Anran Kei");
		demoUserServiceImpl.addUser(testUser);
	}

	/**
	 * <br>
	 * Created on: 2013-11-2 下午08:57:32
	 * 
	 * @return the demoUserServiceImpl
	 */
	public DemoUserService getDemoUserServiceImpl() {
		return demoUserServiceImpl;
	}

	/**
	 * <br>
	 * Created on: 2013-11-2 下午08:57:32
	 * 
	 * @param demoUserServiceImpl
	 *            the demoUserServiceImpl to set
	 */
	@Resource
	public void setDemoUserServiceImpl(DemoUserService demoUserServiceImpl) {
		this.demoUserServiceImpl = demoUserServiceImpl;
	}

}
