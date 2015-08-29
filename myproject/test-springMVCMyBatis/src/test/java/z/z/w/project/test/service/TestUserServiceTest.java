/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.test.service.TestUserServiceTest.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-11-2 下午02:39:55 
 *   LastChange: 2013-11-2 下午02:39:55 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.test.service;

import static org.junit.Assert.fail;

import javax.annotation.Resource;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import z.z.w.project.test.entity.TestUser;

/**
 * z.z.w.project.test.service.TestUserServiceTest.java
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/spring.xml",
		"classpath:/spring-mybatis.xml" })
public class TestUserServiceTest {

	private TestUserServiceImpl testUserServiceImpl;

	/**
	 * <br>
	 * Created on: 2013-11-2 下午03:06:13
	 * 
	 * @return the testUserServiceImpl
	 */
	public TestUserServiceImpl getTestUserServiceImpl() {
		return testUserServiceImpl;
	}

	/**
	 * <br>
	 * Created on: 2013-11-2 下午03:06:13
	 * 
	 * @param testUserServiceImpl
	 *            the testUserServiceImpl to set
	 */
	@Resource
	public void setTestUserServiceImpl(TestUserServiceImpl testUserServiceImpl) {
		this.testUserServiceImpl = testUserServiceImpl;
	}

	/**
	 * Test method for
	 * {@link z.z.w.project.test.service.TestUserServiceImpl#addUser(z.z.w.project.test.entity.TestUser)}
	 * .
	 */
	@Test
	public void testAddUser() {
		TestUser testUser = new TestUser();
		testUser.setName("吳貞貞");
		System.out.println(testUserServiceImpl.addUser(testUser));
	}

	/**
	 * Test method for
	 * {@link z.z.w.project.test.service.TestUserServiceImpl#queryAllUsers()}.
	 */
	@Test
	@Ignore
	public void testQueryAllUsers() {
		fail("Not yet implemented");
	}

}
