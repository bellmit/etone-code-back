/**
 * z.z.w.db.JdbcInfoTest.java
 */
package z.z.w.db;

import org.junit.Test;

import z.z.w.env.Config;

/**
 * @author Wu Zhenzhen
 * @version Nov 22, 2012 9:53:41 AM
 */
public class JdbcInfoTest {

	/**
	 * Test method for {@link z.z.w.db.JdbcInfo#loadFrom(z.z.w.env.Config)}.
	 */
	@Test
	public void testLoadFrom() {
		Config conf = new Config("etc/jdbc.cfg");
		conf.setRootKey("db.sybase");
		JdbcInfo info = JdbcInfo.getInstance("sybase");
		info.loadFrom(conf);
		System.out.println(info.toString());
		System.out.println("--------------------------------");
		info = JdbcInfo.getInstance("mysql");
		conf.setRootKey("db.mysql");
		info.loadFrom(conf);
		System.out.println(info.toString());
	}

}
