/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.test.action.DemoUserAction.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-11-2 下午07:42:36 
 *   LastChange: 2013-11-2 下午07:42:36 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.test.action;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;

import z.z.w.project.test.model.TestUser;
import z.z.w.project.test.service.DemoUserService;
import z.z.w.project.util.common.LogTools;

/**
 * z.z.w.project.test.action.DemoUserAction.java
 */
@Action
public class DemoUserAction extends BaseAction<TestUser> {

	/**
	 * <br>
	 * Created on: 2013-11-3 下午03:29:53
	 */
	private static final long serialVersionUID = 3256129858056238290L;
	private DemoUserService demoUserService;
	private TestUser testUser = new TestUser();

	/**
	 * 
	 * <br>
	 * Created on: 2013-11-2 下午08:56:44
	 */
	public void showUsers() {
		LogTools.getLogger(getClass()).debug("---------showUsers----------");
		demoUserService.queryAllUser();
	}

	/**
	 * 
	 * <br>
	 * Created on: 2013-11-2 下午08:56:41
	 */
	public void add() {
		LogTools.getLogger(getClass()).debug("{}", testUser);
		demoUserService.addUser(testUser);
	}

	/**
	 * <br>
	 * Created on: 2013-11-3 下午01:38:40
	 * 
	 * @return the demoUserService
	 */
	public DemoUserService getDemoUserService() {
		return demoUserService;
	}

	/**
	 * <br>
	 * Created on: 2013-11-3 下午01:38:40
	 * 
	 * @param demoUserService
	 *            the demoUserService to set
	 */
	@Resource
	public void setDemoUserService(DemoUserService demoUserService) {
		this.demoUserService = demoUserService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see z.z.w.project.test.action.BaseAction#getModel()
	 */
	@Override
	public TestUser getModel() {
		return testUser;
	}
}
