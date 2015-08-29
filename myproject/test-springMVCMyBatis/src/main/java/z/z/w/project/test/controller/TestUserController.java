/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.test.controller.TestUserController.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-11-2 下午03:31:41 
 *   LastChange: 2013-11-2 下午03:31:41 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.test.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import z.z.w.project.test.entity.TestUser;
import z.z.w.project.test.service.TestUserServiceImpl;
import z.z.w.project.util.common.LogTools;

import com.alibaba.fastjson.JSON;

/**
 * z.z.w.project.test.controller.TestUserController.java
 */
@Controller
@RequestMapping("/demo")
public class TestUserController {

	private TestUserServiceImpl testUserServiceImpl;

	@RequestMapping("/showUsers")
	public String showUsers(Model model) {
		List<TestUser> list = testUserServiceImpl.queryAllUsers();
		model.addAttribute("testUserList", list);
		LogTools.getLogger(getClass()).debug("show users : [{}]",
				JSON.toJSONString(list));
		return "showUsers";
	}

	@RequestMapping("/{id}/showUser")
	public String showUsers(@PathVariable String id, Model model) {
		TestUser testUser = testUserServiceImpl.queryUesrById(id);
		model.addAttribute("testUser", testUser);
		return "showUsers";
	}

	/**
	 * <br>
	 * Created on: 2013-11-2 下午03:48:01
	 * 
	 * @return the testUserServiceImpl
	 */
	public TestUserServiceImpl getTestUserServiceImpl() {
		return testUserServiceImpl;
	}

	/**
	 * <br>
	 * Created on: 2013-11-2 下午03:48:01
	 * 
	 * @param testUserServiceImpl
	 *            the testUserServiceImpl to set
	 */
	@Resource
	public void setTestUserServiceImpl(TestUserServiceImpl testUserServiceImpl) {
		this.testUserServiceImpl = testUserServiceImpl;
	}
}
