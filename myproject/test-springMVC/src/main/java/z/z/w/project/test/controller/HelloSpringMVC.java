/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.test.controller.HelloSpringMVC.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-10-30 上午11:18:40 
 *   LastChange: 2013-10-30 上午11:18:40 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * z.z.w.project.test.controller.HelloSpringMVC.java
 */
@Controller
public class HelloSpringMVC {

	@RequestMapping({ "/hello", "/" })
	public String hello(String name, Model model) {
		// 傳值必不可少是可以加上@RequestParam
		// public String hello(@RequestParam("username") String name) {
		// view接收返回值
		model.addAttribute("username", name);
		return "hello";
	}
}
