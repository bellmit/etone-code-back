/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.test.rs.HelloService.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-10-9 下午05:35:32 
 *   LastChange: 2013-10-9 下午05:35:32 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.test.rs;

import z.z.w.project.util.common.DataTools;
import z.z.w.project.util.common.LogTools;

/**
 * z.z.w.project.test.rs.HelloService.java
 */
public class HelloService {

	/**
	 * <br>
	 * Created on: 2013-10-9 下午05:37:12
	 */
	public HelloService() {
		super();
		LogTools.getLogger(getClass()).debug(
				"----HelloService constructor------");
	}

	/**
	 * 
	 * <br>
	 * Created on: 2013-10-9 下午05:38:46
	 * 
	 * @param userName
	 * @return
	 */
	public String sayHello(String userName) {
		if (DataTools.isEmpty(userName))
			userName = "NONE";
		return "Hello : " + userName;
	}

}
