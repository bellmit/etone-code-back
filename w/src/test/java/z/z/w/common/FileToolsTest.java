/**
 * z.z.w.common.FileToolsTest.java
 */
package z.z.w.common;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import org.junit.Test;

/**
 * @author Wu Zhenzhen
 * @version Nov 21, 2012 4:49:36 PM
 */
public class FileToolsTest {

	/**
	 * Test method for
	 * {@link z.z.w.common.FileTools#getPathFreeSpaceMb(java.lang.String)}.
	 */
	@Test
	public void testGetPathFreeSpaceMb() {
		try {
			long mb = FileTools.getPathFreeSpaceMb("G:\\");
			System.out.println("Free space : "
					+ ((mb > 1024) ? ((mb / 1024) + "GB") : (mb + "MB")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Test method for
	 * {@link z.z.w.common.FileTools#replaceProHomeToEmpty(java.lang.String)}.
	 */
	@Test
	public void testReplaceProHome() {
		System.out.println(FileTools.replaceProHome("etc/project.cfg"));
		System.out.println(FileTools
				.replaceProHome("$PRO_HOME/etc/project.cfg"));
	}

	/**
	 * Test method for
	 * {@link z.z.w.common.FileTools#formatPath(java.lang.String)}.
	 */
	@Test
	public void testFormatPath() {
		System.out.println(FileTools.formatPath("/test"));
		System.out.println(FileTools.formatPath("/test/"));
		System.out.println(FileTools.formatPath("/test\\"));
	}

	/**
	 * Test method for
	 * {@link z.z.w.common.FileTools#unFormatPath(java.lang.String)}.
	 */
	@Test
	public void testUnFormatPath() {
		System.out.println(FileTools.unFormatPath("/test\\"));
		System.out.println(FileTools.unFormatPath("/test"));
		System.out.println(FileTools.unFormatPath("/test/"));
	}

	/**
	 * Test method for {@link z.z.w.common.FileTools#tryLock(java.lang.String)}.
	 */
	@Test
	public void testTryLock() {
		System.out.println(FileTools.tryLock("etc/lock.l"));
	}

	/**
	 * Test method for
	 * {@link z.z.w.common.FileTools#getPrefixAndNewerFiles(String filePath, String nameFix, long newerTime, boolean isAllFile)}
	 * .
	 */
	@Test
	public void testgetPrefixAndNewerFiles() {

		// String filePath = "E:\\workspace_shenzhen\\keyword\\etc\\data\\";
		// String filePath = "D:\\03_workspace\\util\\w\\lib\\";
		String filePath = FileTools.replaceProHome("$PRO_HOME/lib");
		System.out.println(filePath);
		String nameFix = ("etl*.jar");
		long newerTime = (DateTools.getCurrentDateToLong() - 1 * 60 * 60 * 1000);
		newerTime = 0;
		// IOFileFilter wildcardFileFilter = new PrefixFileFilter(nameFix);
		// IOFileFilter ageFileFilter = new AgeFileFilter(newerTime, false);
		// IOFileFilter andFilter = FileFilterUtils.and(wildcardFileFilter,
		// ageFileFilter);

		// Collection<File> fileList = FileUtils.listFiles((new File(filePath)),
		// andFilter, TrueFileFilter.INSTANCE);

		// if (DataTools.isEmpty(fileList)) {
		// System.out.println("---------++++++++++==---------");
		// }

		Collection<File> fileList = FileTools.getWildcardAndNewerFiles(
				filePath, nameFix, newerTime, false);

		if (DataTools.isEmpty(fileList)) {
			System.out.println("-----------------");
		} else {
			for (File file : fileList) {
				System.out.println(file.getAbsolutePath());
			}
		}

	}

	// @Test
	// public void testGetFileDiskSpace() {
	// System.out.println(FileTools.getFileDiskSpace("E:\03-music"));
	// }
}
