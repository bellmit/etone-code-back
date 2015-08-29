/**
 * z.z.w.env.ConfigTest.java
 */
package z.z.w.env;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Wu Zhenzhen
 * @version Nov 21, 2012 4:32:30 PM
 */
public class ConfigTest {

	private Config conf = null;

	/**
	 * <br>
	 * Created on: Nov 21, 2012 4:32:30 PM
	 * 
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		// conf = new Config("E:\\workspace_util\\w\\etc\\project.cfg");
		conf = new Config();
		conf.setRootKey("common");
		// conf.setRootKey("common.util");

		System.out.println(conf.toString());
	}

	/**
	 * <br>
	 * Created on: Nov 21, 2012 4:32:30 PM
	 * 
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		conf = null;
	}

	/**
	 * Test method for {@link z.z.w.env.Config#getString(java.lang.String)}.
	 */
	@Test
	public void testGetString() {
		System.out.println(conf.getString("string-test"));
	}

	/**
	 * Test method for {@link z.z.w.env.Config#getLong(java.lang.String)}.
	 */
	@Test
	public void testGetLong() {
		System.out.println(((conf.getLong("long-test")) == -1 ? "ERROR" : (conf
				.getLong("long-test"))));

		System.out
				.println(((conf.getLong("long-test")) == -1 ? "ERROR" : "OK"));

		System.out.println(conf.getLong("long-test"));
	}

	/**
	 * Test method for {@link z.z.w.env.Config#getInt(java.lang.String)}.
	 */
	@Test
	public void testGetInt() {
		System.out.println(((conf.getInt("int-test")) == -1 ? "ERROR" : (conf
				.getInt("int-test"))));

		System.out.println(((conf.getInt("int-test")) == -1 ? "ERROR" : "OK"));

		System.out.println(conf.getInt("intd-test"));
		System.out.println(conf.getInt("int-test"));
	}

	/**
	 * Test method for {@link z.z.w.env.Config#getFloat(java.lang.String)}.
	 */
	@Test
	public void testGetFloat() {
		System.out.println(((conf.getFloat("float-test")) == -1.0 ? "ERROR"
				: (conf.getFloat("float-test"))));

		System.out.println(((conf.getFloat("float-test")) == -1.0 ? "ERROR"
				: "OK"));

		System.out.println(conf.getFloat("float-test"));
	}

	/**
	 * Test method for {@link z.z.w.env.Config#getDouble(java.lang.String)}.
	 */
	@Test
	public void testGetDouble() {
		System.out.println(((conf.getDouble("double-test")) == -1.0 ? "ERROR"
				: (conf.getDouble("double-test"))));

		System.out.println(((conf.getDouble("double-test")) == -1.0 ? "ERROR"
				: "OK"));

		System.out.println(conf.getDouble("double-test"));
	}

	/**
	 * Test method for {@link z.z.w.env.Config#getBoolean(java.lang.String)}.
	 */
	@Test
	public void testGetBoolean() {
		System.out.println(conf.getBoolean("boolean-test"));
	}

}
