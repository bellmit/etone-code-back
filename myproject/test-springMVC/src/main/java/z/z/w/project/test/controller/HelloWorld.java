/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.test.controller.HelloWorld.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-10-30 上午10:57:28 
 *   LastChange: 2013-10-30 上午10:57:28 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.test.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 * z.z.w.project.test.controller.HelloWorld.java
 */
public class HelloWorld extends AbstractController {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.mvc.AbstractController#handleRequestInternal
	 * (javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView("helloworld");
	}

}
