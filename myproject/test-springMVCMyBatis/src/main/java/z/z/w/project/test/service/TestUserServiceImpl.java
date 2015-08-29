/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.test.service.TestUserService.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-11-2 下午02:26:32 
 *   LastChange: 2013-11-2 下午02:26:32 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.test.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import z.z.w.project.test.dao.TestUserDao;
import z.z.w.project.test.entity.TestUser;

/**
 * z.z.w.project.test.service.TestUserService.java
 */
@Service
public class TestUserServiceImpl {

	private TestUserDao testUserDao;

	/**
	 * 
	 * <br>
	 * Created on: 2013-11-2 下午02:26:13
	 * 
	 * @param testUser
	 * @return
	 */
	public int addUser(TestUser testUser) {
		return testUserDao.insert(testUser);
	}

	/**
	 * 
	 * <br>
	 * Created on: 2013-11-2 下午02:26:09
	 * 
	 * @return
	 */
	public List<TestUser> queryAllUsers() {
		return testUserDao.selectAll();
	}

	/**
	 * <br>
	 * Created on: 2013-11-2 下午02:26:52
	 * 
	 * @return the testUserDao
	 */
	public TestUserDao getTestUserDao() {
		return testUserDao;
	}

	/**
	 * <br>
	 * Created on: 2013-11-2 下午02:26:52
	 * 
	 * @param testUserDao
	 *            the testUserDao to set
	 */
	@Resource
	public void setTestUserDao(TestUserDao testUserDao) {
		this.testUserDao = testUserDao;
	}

	/**
	 * <br>
	 * Created on: 2013-11-2 下午04:02:37
	 * 
	 * @param id
	 * @return
	 */
	public TestUser queryUesrById(String id) {
		return testUserDao.selectUserById(id);
	}
}
