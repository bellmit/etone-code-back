/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.test.dao.TestUserDao.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-11-2 下午02:24:06 
 *   LastChange: 2013-11-2 下午02:24:06 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.test.dao;

import java.util.List;

import z.z.w.project.test.entity.TestUser;

/**
 * z.z.w.project.test.dao.TestUserDao.java
 */
public interface TestUserDao {

	/**
	 * 
	 * <br>
	 * Created on: 2013-11-2 下午02:26:13
	 * 
	 * @param testUser
	 * @return
	 */
	public int insert(TestUser testUser);

	/**
	 * 
	 * <br>
	 * Created on: 2013-11-2 下午02:26:09
	 * 
	 * @return
	 */
	public List<TestUser> selectAll();

	/**
	 * <br>
	 * Created on: 2013-11-2 下午04:03:24
	 * 
	 * @param id
	 * @return
	 */
	public TestUser selectUserById(String id);
}
