/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.test.controller.UserController.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-10-30 上午11:46:27 
 *   LastChange: 2013-10-30 上午11:46:27 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.test.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import z.z.w.project.test.model.User;

/**
 * z.z.w.project.test.controller.UserController.java
 */
@Controller
@RequestMapping("/user")
public class UserController {

	private Map<String, User> userMap = new HashMap<String, User>();

	/**
	 * <br>
	 * Created on: 2013-10-30 上午11:46:27
	 */
	public UserController() {
		User user = new User();
		user.setId(1);
		user.setName("name1");
		userMap.put("name1", user);
		user = new User();
		user.setId(2);
		user.setName("name2");
		userMap.put("name2", user);
		user = new User();
		user.setId(3);
		user.setName("name3");
		userMap.put("name3", user);
	}

	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public String list(Model model) {
		model.addAttribute("userMap", userMap);
		return "user/list";
	}

}
