/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.test.service.DemoUserService.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-11-2 下午07:55:12 
 *   LastChange: 2013-11-2 下午07:55:12 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.test.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import z.z.w.project.test.dao.DemoUserDao;
import z.z.w.project.test.model.TestUser;
import z.z.w.project.util.common.DataTools;
import z.z.w.project.util.common.LogTools;

/**
 * z.z.w.project.test.service.DemoUserService.java
 */
@Service
public class DemoUserService {

	private DemoUserDao<TestUser> demoUserDao;

	/**
	 * <br>
	 * Created on: 2013-11-2 下午07:55:51
	 */
	public void queryAllUser() {
		LogTools.getLogger(getClass()).debug("---------queryAllUser----------");
		List<TestUser> list = demoUserDao.findData("from TestUser", null);
		if (!DataTools.isEmpty(list))
			for (TestUser tu : list) {
				LogTools.getLogger(getClass()).debug("{}", tu.toString());
			}
	}

	public void addUser(TestUser testUser) {
		demoUserDao.save(testUser);
	}

	/**
	 * <br>
	 * Created on: 2013-11-3 下午02:25:53
	 * 
	 * @return the demoUserDao
	 */
	public DemoUserDao<TestUser> getDemoUserDao() {
		return demoUserDao;
	}

	/**
	 * <br>
	 * Created on: 2013-11-3 下午02:25:53
	 * 
	 * @param demoUserDao
	 *            the demoUserDao to set
	 */
	@Resource
	public void setDemoUserDao(DemoUserDao<TestUser> demoUserDao) {
		this.demoUserDao = demoUserDao;
	}

}
