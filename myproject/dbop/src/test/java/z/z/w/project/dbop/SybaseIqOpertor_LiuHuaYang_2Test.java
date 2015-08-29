/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.dbop.SybaseIqOpertor_LiuHuaYang_2Test.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-11-19 下午05:03:12 
 *   LastChange: 2013-11-19 下午05:03:12 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.dbop;

import static org.junit.Assert.*;

import org.junit.Test;

import z.z.w.project.SuperClass;

/**
 * z.z.w.project.dbop.SybaseIqOpertor_LiuHuaYang_2Test.java
 */
public class SybaseIqOpertor_LiuHuaYang_2Test extends SuperClass {

	/**
	 * Test method for {@link z.z.w.project.dbop.SybaseIqOpertor_LiuHuaYang_2#initPreparedStatement()}.
	 */
	@Test
	public void testInitPreparedStatement() {
		try {

			SybaseIqOpertor_LiuHuaYang_2 server = new SybaseIqOpertor_LiuHuaYang_2();
			server.run();

		} catch (Exception e) {
			fail("MASSAGE : " + e.getMessage() + "\nCAUSE : " + e.getCause() + "\nCLASS : " + e.getClass());
		}
	}

}
