/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.dbop.SybaseIqOpertor_LiuHuaYangTest.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-11-19 下午05:03:20 
 *   LastChange: 2013-11-19 下午05:03:20 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.dbop;

import static org.junit.Assert.*;

import org.junit.Test;

import z.z.w.project.SuperClass;

/**
 * z.z.w.project.dbop.SybaseIqOpertor_LiuHuaYangTest.java
 */
public class SybaseIqOpertor_LiuHuaYangTest extends SuperClass {

	/**
	 * Test method for {@link z.z.w.project.dbop.SybaseIqOpertor_LiuHuaYang#initPreparedStatement()}.
	 */
	@Test
	public void testInitPreparedStatement() {
		try {

			SybaseIqOpertor_LiuHuaYang server = new SybaseIqOpertor_LiuHuaYang();
			server.run();

		} catch (Exception e) {
			fail("MASSAGE : " + e.getMessage() + "\nCAUSE : " + e.getCause() + "\nCLASS : " + e.getClass());
		}
	}

}
