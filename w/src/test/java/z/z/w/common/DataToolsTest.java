/**
 * z.z.w.common.DataToolsTest.java
 */
package z.z.w.common;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

/**
 * @author Wu Zhenzhen
 * @version Nov 21, 2012 2:51:35 PM
 */
public class DataToolsTest {

	/**
	 * Test method for
	 * {@link z.z.w.common.DataTools#getSubstringCount(java.lang.String, java.lang.String)}
	 * .
	 */
	@Test
	public void testGetSubstringCount() {
		System.out
				.println(DataTools
						.getSubstringCount(
								"3|145|1258504908000|1258504998000|25|13928534722|121.14.79.158",
								"|"));
		System.out.println(DataTools.getSubstringCount("3|145|", "|"));
		System.out.println(DataTools.getSubstringCount("3|145", "|"));
		System.out.println(DataTools.getSubstringCount("145", "|"));
	}

	/**
	 * Test method for
	 * {@link z.z.w.common.DataTools#hasChinese(java.lang.String)}.
	 */
	@Test
	public void testHasChinese() {
		System.out.println(DataTools.hasChinese("測試3234"));
		System.out.println(DataTools.hasChinese("3234"));
		System.out.println(DataTools.hasChinese("測3234"));
		System.out.println(DataTools.hasChinese("23ljdskljf4"));
		System.out.println(DataTools.hasChinese("lkjf哈哈富士康的3234"));

	}

	/**
	 * Test method for
	 * {@link z.z.w.common.DataTools#nmFormatToInt(int, java.lang.Object)}.
	 */
	@Test
	public void testNmFormatToIntIntObject() {
		System.out.println(DataTools.nmFormatToInt(4, 5));
	}

	/**
	 * Test method for
	 * {@link z.z.w.common.DataTools#nmFormatToInt(java.lang.Object)}.
	 */
	@Test
	public void testNmFormatToIntObject() {
		System.out.println(DataTools.nmFormatToInt(34));
	}

	/**
	 * Test method for
	 * {@link z.z.w.common.DataTools#nmFormatToInt(java.lang.String, java.lang.Object)}
	 * .
	 */
	@Test
	public void testNmFormatToIntStringObject() {
		System.out.println(DataTools.nmFormatToInt("%04d", 23));
	}

	/**
	 * Test method for
	 * {@link z.z.w.common.DataTools#nmFormatToFloat(int, int, java.lang.Object)}
	 * .
	 */
	@Test
	public void testNmFormatToFloatIntIntObject() {
		System.out.println(DataTools.nmFormatToFloat(5, 2, 23.4));
		System.out.println(DataTools.nmFormatToFloat(5, 2, 23.498));
		System.out.println(DataTools.nmFormatToFloat(7, 2, 23));
		System.out.println(DataTools.nmFormatToFloat(7, 2, 23.9));
		System.out.println(DataTools.nmFormatToFloat(7, 2, 293.1));
	}

	/**
	 * Test method for
	 * {@link z.z.w.common.DataTools#nmFormatToFloat(java.lang.Object)}.
	 */
	@Test
	public void testNmFormatToFloatObject() {
		System.out.println(DataTools.nmFormatToFloat(23.5));
	}

	/**
	 * Test method for
	 * {@link z.z.w.common.DataTools#nmFormatToFloat(java.lang.String, java.lang.Object)}
	 * .
	 */
	@Test
	public void testNmFormatToFloatStringObject() {
		System.out.println(DataTools.nmFormatToFloat("%06.2f", 34.678));
	}

	/**
	 * Test method for {@link z.z.w.common.DataTools#isEmpty(java.lang.String)}.
	 */
	@Test
	public void testIsEmptyString() {
		System.out.println(DataTools.isEmpty("dfd"));
		System.out.println(DataTools.isEmpty(""));
		System.out.println(DataTools.isEmpty(" dfd "));
	}

	/**
	 * Test method for
	 * {@link z.z.w.common.DataTools#trimToEmpty(java.lang.String)}.
	 */
	@Test
	public void testTrimToEmpty() {
		System.out.println(DataTools.trimToEmpty(" dfkslj "));
		System.out.println(DataTools.trimToEmpty("jfdksl dfkslj "));
		System.out.println(DataTools.trimToEmpty(" dfkslj fkldsj"));
	}

	/**
	 * Test method for {@link z.z.w.common.DataTools#isEmpty(java.io.File)}.
	 */
	@Test
	public void testIsEmptyFile() {
		System.out.println(DataTools.isEmpty(new File("test")));
		System.out.println(DataTools.isEmpty(new File(
				"E:\\workspace_util\\w\\pom.xml")));
	}

	/**
	 * Test method for
	 * {@link z.z.w.common.DataTools#isEmpty(java.lang.Object[])}.
	 */
	@Test
	public void testIsEmptyObjectArray() {
		System.out.println(DataTools.isEmpty(new Object[] { 4, 5, 4 }));
		System.out.println(DataTools.isEmpty(new Object[] {}));
	}

	/**
	 * Test method for {@link z.z.w.common.DataTools#isEmpty(java.lang.Object)}.
	 */
	@Test
	public void testIsEmptyObject() {
		System.out.println(DataTools.isEmpty(5));
	}

	/**
	 * Test method for {@link z.z.w.common.DataTools#isEmpty(java.util.Map)}.
	 */
	@Test
	public void testIsEmptyMapOfQQ() {
		System.out.println(DataTools.isEmpty(new HashMap<Integer, Integer>()));
	}

	/**
	 * Test method for
	 * {@link z.z.w.common.DataTools#isEmpty(java.util.Collection)}.
	 */
	@Test
	public void testIsEmptyCollectionOfQ() {
		System.out.println(DataTools.isEmpty(new ArrayList<String>()));
		Set<String> set = new HashSet<String>();
		set.add("fjdsk");
		System.out.println(DataTools.isEmpty(set));
	}

	@Test
	public void testGetCharByHexStr() {
		System.out.println(DataTools.getCharByHexStr("\\x2c"));
		System.out.println(DataTools.getCharByHexStr("\\x0d"));
		System.out.println(DataTools.getCharByHexStr("\\x12"));
		System.out.println(DataTools.getCharByHexStr("\\xcd"));
	}
}
