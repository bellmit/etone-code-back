/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.test.dao.DemoUserDao.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-11-2 下午07:57:50 
 *   LastChange: 2013-11-2 下午07:57:50 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.test.dao;

import org.springframework.stereotype.Repository;

/**
 * z.z.w.project.test.dao.DemoUserDao.java
 */
@Repository
public class DemoUserDao<TestUser> extends BaseDao<TestUser> {

	/**
	 * <br>
	 * Created on: 2013-11-3 下午07:55:27
	 */
	private static final long serialVersionUID = 7035567740444413118L;

	/**
	 * <br>
	 * Created on: 2013-11-3 下午02:22:52
	 * 
	 * @param testUser
	 * @return
	 */
	public void insert(TestUser t) {
		this.saveOrUpdate(t);
	}

}
