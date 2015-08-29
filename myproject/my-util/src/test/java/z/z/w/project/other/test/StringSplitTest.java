/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.other.test.StringSplitTest.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-9-12 下午03:36:15 
 *   LastChange: 2013-9-12 下午03:36:15 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.other.test;

import org.junit.Test;

/**
 * z.z.w.project.other.test.StringSplitTest.java
 */
public class StringSplitTest {

	@Test
	public void testStringSplit() {
		String str = "*.csv";
		String[] strArr = str.split("\\*", -1);
		print(str, strArr);

		str = "ftp*.csv";
		strArr = str.split("\\*", -1);
		print(str, strArr);
		str = "ftp*.csv*";
		strArr = str.split("\\*", -1);
		print(str, strArr);

		str = "ftp*";
		strArr = str.split("\\*", -1);
		print(str, strArr);
		
		str = "*";
		strArr = str.split("\\*", -1);
		print(str, strArr);

		str = "ftp*ftp*csv";
		strArr = str.split("\\*", -1);
		print(str, strArr);

		str = "ftp";
		strArr = str.split("\\*", -1);
		print(str, strArr);
	}

	private void print(String str, String[] strArr) {

		System.out.print(str + " : ");
		for (int i = 0; i < strArr.length; i++) {
			System.out.print(strArr[i] + "--");
		}
		System.out.println(strArr.length);
	}

}
