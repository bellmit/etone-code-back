/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.util.file.FileTools.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-9-10 上午10:17:04 
 *   LastChange: 2013-9-10 上午10:17:04 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.util.file;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.AgeFileFilter;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.PrefixFileFilter;
import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.commons.io.filefilter.WildcardFileFilter;

import z.z.w.project.util.common.DataTools;
import z.z.w.project.util.env.Env;

/**
 * z.z.w.project.util.file.FileTools.java
 */
public abstract class FileTools {

	/**
	 * replace fileName $PRO_HOME to basedir <br>
	 * Created on: 2013-9-10 上午10:30:46
	 * 
	 * @param fileName
	 * @return
	 */
	public static String replaceProHome(String fileName) {

		String basedir = System.getProperty("basedir");

		if (DataTools.isEmpty(basedir)) {
			return fileName.replaceAll("\\$PRO_HOME/", "");
		}

		if (Env.getPlatform() == Env.getOsWindows())
			basedir = replaceWinSepaToUnix(basedir);

		return fileName.replaceAll("\\$PRO_HOME", basedir);
	}

	/**
	 * 
	 * <br>
	 * Created on: 2013-9-10 上午10:22:41
	 * 
	 * @param path
	 * @return
	 */
	public static String replaceWinSepaToUnix(String path) {
		return path.replaceAll("\\\\", "/");
	}

	/**
	 * 將 包結構中的 . 解析成文件路徑格式的 / <br>
	 * Created on: 2013-9-10 上午10:22:34
	 * 
	 * @param path
	 * @return
	 */
	public static String replacePointToLine(String path) {
		return path.replaceAll("\\.", "/");
	}

	/**
	 * 將文件路徑/格式轉換成全路徑類名格式 <br>
	 * Created on: 2013-9-10 上午10:22:25
	 * 
	 * @param path
	 * @return
	 */
	public static String replaceLineToPoint(String path) {
		return path.replaceAll("\\/", ".");
	}

	/**
	 * Give path end add separator <br>
	 * Created on: 2013-9-10 上午10:22:15
	 * 
	 * @param path
	 * @return
	 */
	public static String addPathSeparator(String path) {

		if ((!path.endsWith(File.separator)) && (!path.endsWith("/"))) {
			return (path + replaceWinSepaToUnix(File.separator));
		}

		return path;
	}

	/**
	 * Give path end del separator <br>
	 * Created on: 2013-9-10 上午10:22:06
	 * 
	 * @param path
	 * @return
	 */
	public static String delPathSeparator(String path) {

		if (path.endsWith(File.separator) || path.endsWith("/")) {
			return (path.substring(0, (path.length() - 1)));
		}

		return path;
	}

	/**
	 * Check system exec only one pro <br>
	 * Created on: 2013-9-10 上午10:21:58
	 * 
	 * @param fileName
	 * @return
	 */
	public static boolean tryLock(String fileName) {
		/**
		 * A token representing a lock on a region of a file.
		 */
		FileLock lock = null;

		/**
		 * A channel for reading, writing, mapping, and manipulating a file.
		 */
		FileChannel channel = null;

		/**
		 * Instances of this class support both reading and writing to a random
		 * access file.
		 */
		RandomAccessFile randomAccessFile = null;

		try {
			randomAccessFile = new RandomAccessFile(fileName, "rw");
			if (null != randomAccessFile) {
				channel = randomAccessFile.getChannel();
			}

			if (null != channel) {
				lock = channel.tryLock();
			}

			return (null != lock && lock.isValid());
		} catch (Exception e) {
			System.out.println("    Project lock file [" + fileName
					+ "] error ! PORGRAMME EXIT.");
			System.out.println("MASSAGE : " + e.getMessage() + "\nCAUSE : "
					+ e.getCause() + "\nCLASS : " + e.getClass());
			System.exit(1);
		}

		return false;
	}

	/**
	 * 
	 * <br>
	 * Created on: 2013-9-10 上午10:24:16
	 * 
	 * @param filePath
	 * @return
	 */
	public static File getFilePath(String filePath) {
		File path = new File(filePath);

		if (!path.exists())
			if (!path.mkdirs())
				return null;

		return path;
	}

	/**
	 * 
	 * <br>
	 * Created on: 2013-9-10 上午10:30:36
	 * 
	 * @param file
	 * @return
	 */
	public static boolean delFile(File file) {
		return FileUtils.deleteQuietly(file);
	}

	/**
	 * 
	 * <br>
	 * Created on: 2013-9-10 上午10:30:31
	 * 
	 * @param srcFile
	 * @param destFile
	 * @return
	 */
	public static boolean moveFile(File srcFile, File destFile) {
		try {
			FileUtils.moveFile(srcFile, destFile);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 
	 * <br>
	 * Created on: 2013-9-10 上午10:30:26
	 * 
	 * @param srcFile
	 * @param destFile
	 * @return
	 */
	public static boolean copyFile(File srcFile, File destFile) {
		try {
			FileUtils.copyFile(srcFile, destFile);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * getWildcardAndAgeFilesIncludeSubDirectory <br>
	 * Created on: 2013-9-10 上午10:43:33
	 * 
	 * @param filePath
	 * @param nameFix
	 * @param compareTime
	 * @param olderFile
	 * @return
	 */
	public static Collection<File> getWildcardAndAgeFilesIncludeSubDirectory(
			String filePath, String nameFix, long compareTime, boolean olderFile) {

		IOFileFilter wildcardFileFilter = new WildcardFileFilter(nameFix);
		IOFileFilter ageFileFilter = new AgeFileFilter(compareTime, olderFile);
		IOFileFilter andFilter = FileFilterUtils.and(wildcardFileFilter,
				ageFileFilter);

		return (FileUtils.listFiles((new File(filePath)), andFilter,
				TrueFileFilter.TRUE));

	}

	/**
	 * 
	 * <br>
	 * Created on: 2013-9-10 上午10:52:33
	 * 
	 * @param filePath
	 * @param nameFix
	 * @param compareTime
	 * @param olderFile
	 * @return
	 */
	public static Collection<File> getWildcardAndAgeFiles(String filePath,
			String nameFix, long compareTime, boolean olderFile) {

		IOFileFilter wildcardFileFilter = new WildcardFileFilter(nameFix);
		IOFileFilter ageFileFilter = new AgeFileFilter(compareTime, olderFile);
		IOFileFilter andFilter = FileFilterUtils.and(wildcardFileFilter,
				ageFileFilter);

		return (FileUtils.listFiles((new File(filePath)), andFilter, null));
	}

	/**
	 * getPrefixAndAgeFilesIncludeSubDirectory <br>
	 * Created on: 2013-9-10 上午10:44:45
	 * 
	 * @param filePath
	 * @param nameFix
	 * @param compareTime
	 * @param olderFile
	 * @return
	 */
	public static Collection<File> getPrefixAndAgeFilesIncludeSubDirectory(
			String filePath, String nameFix, long compareTime, boolean olderFile) {

		IOFileFilter prefixFileFilter = new PrefixFileFilter(nameFix);
		IOFileFilter ageFileFilter = new AgeFileFilter(compareTime, olderFile);
		IOFileFilter andFilter = FileFilterUtils.and(prefixFileFilter,
				ageFileFilter);

		return FileUtils.listFiles((new File(filePath)), andFilter,
				TrueFileFilter.TRUE);
	}

	/**
	 * 
	 * <br>
	 * Created on: 2013-9-10 上午10:57:42
	 * 
	 * @param filePath
	 * @param nameFix
	 * @param compareTime
	 * @param olderFile
	 * @return
	 */
	public static Collection<File> getPrefixAndAgeFiles(String filePath,
			String nameFix, long compareTime, boolean olderFile) {

		IOFileFilter prefixFileFilter = new PrefixFileFilter(nameFix);
		IOFileFilter ageFileFilter = new AgeFileFilter(compareTime, olderFile);
		IOFileFilter andFilter = FileFilterUtils.and(prefixFileFilter,
				ageFileFilter);

		return FileUtils.listFiles((new File(filePath)), andFilter, null);
	}

	/**
	 * getSuffixAndAgeFilesIncludeSubDirectory <br>
	 * Created on: 2013-9-10 上午10:45:43
	 * 
	 * @param filePath
	 * @param nameFix
	 * @param compareTime
	 * @param olderFile
	 * @return
	 */
	public static Collection<File> getSuffixAndAgeFilesIncludeSubDirectory(
			String filePath, String nameFix, long compareTime, boolean olderFile) {

		IOFileFilter suffixFileFilter = new SuffixFileFilter(nameFix);
		IOFileFilter ageFileFilter = new AgeFileFilter(compareTime, olderFile);
		IOFileFilter andFilter = FileFilterUtils.and(suffixFileFilter,
				ageFileFilter);

		return FileUtils.listFiles((new File(filePath)), andFilter,
				TrueFileFilter.TRUE);
	}

	/**
	 * 
	 * <br>
	 * Created on: 2013-9-10 上午10:57:27
	 * 
	 * @param filePath
	 * @param nameFix
	 * @param compareTime
	 * @param olderFile
	 * @return
	 */
	public static Collection<File> getSuffixAndAgeFiles(String filePath,
			String nameFix, long compareTime, boolean olderFile) {

		IOFileFilter suffixFileFilter = new SuffixFileFilter(nameFix);
		IOFileFilter ageFileFilter = new AgeFileFilter(compareTime, olderFile);
		IOFileFilter andFilter = FileFilterUtils.and(suffixFileFilter,
				ageFileFilter);

		return FileUtils.listFiles((new File(filePath)), andFilter, null);
	}

	/**
	 * getFilterFilesIncludeSubDirectory <br>
	 * Created on: 2013-9-10 上午10:46:22
	 * 
	 * @param filePath
	 * @param fileFilter
	 * @return
	 */
	public static Collection<File> getFilterFilesIncludeSubDirectory(
			String filePath, IOFileFilter fileFilter) {
		return FileUtils.listFiles((new File(filePath)), fileFilter,
				TrueFileFilter.TRUE);
	}

	/**
	 * 
	 * <br>
	 * Created on: 2013-9-10 上午10:57:02
	 * 
	 * @param filePath
	 * @param fileFilter
	 * @return
	 */
	public static Collection<File> getFilterFiles(String filePath,
			IOFileFilter fileFilter) {
		return FileUtils.listFiles((new File(filePath)), fileFilter, null);
	}

	/**
	 * 
	 * <br>
	 * Created on: 2013-9-10 上午10:54:44
	 * 
	 * @param filePath
	 * @param nameSuffixes
	 *            an array of extensions, ex. {"java","xml"}. If this parameter
	 *            is {@code null}, all files are returned.
	 * @param recursive
	 *            if true all subdirectories are searched as well
	 * @return
	 */
	public static Collection<File> getSuffixesFiles(String filePath,
			String[] nameSuffixes, boolean recursive) {
		return FileUtils.listFiles((new File(filePath)), nameSuffixes,
				recursive);
	}

	/**
	 * 
	 * <br>
	 * Created on: 2013-9-11 下午03:32:08
	 * 
	 * @param path
	 * @return
	 */
	public static long freeSpace(String path) {
		File file = new File(path);
		return freeSpace(file);
	}

	/**
	 * 
	 * <br>
	 * Created on: 2013-9-11 下午03:52:07
	 * 
	 * @param path
	 * @return
	 */
	public static long freeSpaceKB(String path) {
		File file = new File(path);
		return freeSpaceKB(file);
	}

	/**
	 * 
	 * <br>
	 * Created on: 2013-9-11 下午03:54:30
	 * 
	 * @param path
	 * @return
	 */
	public static long freeSpaceMB(String path) {
		File file = new File(path);
		return freeSpaceMB(file);
	}

	/**
	 * 
	 * <br>
	 * Created on: 2013-9-11 下午03:55:35
	 * 
	 * @param path
	 * @return
	 */
	public static long freeSpaceGB(String path) {
		File file = new File(path);
		return freeSpaceGB(file);
	}

	/**
	 * 
	 * <br>
	 * Created on: 2013-9-11 下午03:56:16
	 * 
	 * @param path
	 * @return
	 */
	public static long freeSpaceTB(String path) {
		File file = new File(path);
		return freeSpaceTB(file);
	}

	/**
	 * <br>
	 * Created on: 2013-9-11 下午03:56:01
	 * 
	 * @param file
	 * @return
	 */
	public static long freeSpaceTB(File file) {
		return (freeSpaceGB(file) / 1024);
	}

	/**
	 * <br>
	 * Created on: 2013-9-11 下午03:54:46
	 * 
	 * @param file
	 * @return
	 */
	public static long freeSpaceGB(File file) {
		return (freeSpaceMB(file) / 1024);
	}

	/**
	 * <br>
	 * Created on: 2013-9-11 下午03:54:16
	 * 
	 * @param file
	 * @return
	 */
	public static long freeSpaceMB(File file) {
		return (freeSpaceKB(file) / 1024);
	}

	/**
	 * <br>
	 * Created on: 2013-9-11 下午03:52:04
	 * 
	 * @param file
	 * @return
	 */
	public static long freeSpaceKB(File file) {
		return (freeSpace(file) / 1024);
	}

	/**
	 * <br>
	 * Created on: 2013-9-11 下午03:32:01
	 * 
	 * @param file
	 * @return
	 */
	public static long freeSpace(File file) {
		if (DataTools.isEmpty(file))
			return 0;
		return file.getUsableSpace();
	}
}
