/**
 * z.z.w.common.FileTools.java
 */
package z.z.w.common;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;

import org.apache.commons.io.FileSystemUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.comparator.NameFileComparator;
import org.apache.commons.io.filefilter.AgeFileFilter;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.PrefixFileFilter;
import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.commons.io.filefilter.WildcardFileFilter;

import z.z.w.env.Env;

/**
 * File tools
 * 
 * @author Wu Zhenzhen
 * @version Nov 21, 2012 4:40:09 PM
 */
public class FileTools {

	/**
	 * private constructor <br>
	 * Created on: Nov 21, 2012 4:40:18 PM
	 */
	private FileTools() {
	}

	/**
	 * 
	 * <br>
	 * Created on: Apr 16, 2013 10:00:38 AM
	 * 
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public static long getPathFreeSpaceMb(String path) throws IOException {
		long kbFreeSpace = FileSystemUtils.freeSpaceKb(path);
		if (kbFreeSpace != 0) {
			kbFreeSpace /= 1024;
		}
		return kbFreeSpace;
	}

	/**
	 * 
	 * <br>
	 * Created on: Dec 1, 2012 2:46:13 PM
	 * 
	 * @param fileName
	 * @return
	 */
	public static String replaceProHome(String fileName) {

		String basedir = System.getProperty("basedir");

		if (DataTools.isEmpty(basedir)) {
			String proHome = "\\$PRO_HOME/";
			return fileName.replaceAll(proHome, "");
		}

		if (Env.getPlatform() == Env.getOsWindows())
			basedir = replaceWinSepaToUnix(basedir);

		return fileName.replaceAll("\\$PRO_HOME", basedir);
	}

	/**
	 * 
	 * <br>
	 * Created on: Dec 1, 2012 4:20:56 PM
	 * 
	 * @param path
	 * @return
	 */
	public static String replaceWinSepaToUnix(String path) {
		return path.replaceAll("\\\\", "/");
	}

	/**
	 * 將 包結構中的 . 解析成文件路徑格式的 / <br>
	 * Created on: 2013-7-7 下午07:36:53
	 * 
	 * @param path
	 * @return
	 */
	public static String replacePointToLine(String path) {
		return path.replaceAll("\\.", "/");
	}

	/**
	 * 將文件路徑/格式轉換成全路徑類名格式 <br>
	 * Created on: 2013-7-8 上午10:35:06
	 * 
	 * @param path
	 * @return
	 */
	public static String replaceLineToPoint(String path) {
		return path.replaceAll("\\/", ".");
	}

	/**
	 * Give path end add separator <br>
	 * Created on: Nov 21, 2012 4:46:24 PM
	 * 
	 * @param path
	 * @return
	 */
	public static String formatPath(String path) {

		if ((!path.endsWith(File.separator)) && (!path.endsWith("/"))) {

			// if (Env.getPlatform() == Env.getOsWindows()) {
			return (path + replaceWinSepaToUnix(File.separator));
			// } else
			// return (path + File.separator);
		}

		return path;
	}

	/**
	 * Give path end del separator <br>
	 * Created on: Nov 21, 2012 4:47:49 PM
	 * 
	 * @param path
	 * @return
	 */
	public static String unFormatPath(String path) {

		if (path.endsWith(File.separator) || path.endsWith("/")) {
			return (path.substring(0, (path.length() - 1)));
		}

		return path;
	}

	/**
	 * Check system exec only one pro <br>
	 * Created on: Nov 23, 2012 11:23:15 AM
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
		} catch (FileNotFoundException e) {
			System.out.println("    Project lock file [" + fileName
					+ "] error ! PORGRAMME EXIT.");
			System.out.println("MASSAGE : " + e.getMessage() + "\nCAUSE : "
					+ e.getCause() + "\nCLASS : " + e.getClass());
			System.exit(1);
		} catch (IOException e) {
			System.out.println("    Project lock file [" + fileName
					+ "] error ! PORGRAMME EXIT.");
			System.out.println("MASSAGE : " + e.getMessage() + "\nCAUSE : "
					+ e.getCause() + "\nCLASS : " + e.getClass());
			System.exit(1);
		}

		return false;
	}

	/**
	 * The wildcard matcher uses the characters '?' and '*' to represent a
	 * single or multiple wildcard characters. <br>
	 * Created on: Nov 28, 2012 1:41:36 PM
	 * 
	 * @param filePath
	 * @param nameFix
	 * @param newerTime
	 * @param isOlder
	 *            true : all file ,false : newer file
	 * @return
	 */
	public static Collection<File> getWildcardAndNewerFiles(String filePath,
			String nameFix, long newerTime, boolean isOlder) {

		IOFileFilter wildcardFileFilter = new WildcardFileFilter(nameFix);
		IOFileFilter ageFileFilter = new AgeFileFilter(newerTime, isOlder);
		IOFileFilter andFilter = FileFilterUtils.and(wildcardFileFilter,
				ageFileFilter);

		NameFileComparator comp = new NameFileComparator();
		File[] fileArr = comp.sort(FileUtils
				.convertFileCollectionToFileArray(FileUtils.listFiles(
						(new File(filePath)), andFilter,
						TrueFileFilter.INSTANCE)));
		comp = null;

		return new LinkedList<File>(Arrays.asList(fileArr));
	}

	/**
	 * Prefix <br>
	 * Created on: Nov 28, 2012 1:49:38 PM
	 * 
	 * @param filePath
	 * @param nameFix
	 * @param newerTime
	 * @param isOlder
	 *            true : all file ,false : newer file
	 * @return
	 */
	public static Collection<File> getPrefixAndNewerFiles(String filePath,
			String nameFix, long newerTime, boolean isOlder) {

		IOFileFilter wildcardFileFilter = new PrefixFileFilter(nameFix);
		IOFileFilter ageFileFilter = new AgeFileFilter(newerTime, isOlder);
		IOFileFilter andFilter = FileFilterUtils.and(wildcardFileFilter,
				ageFileFilter);

		NameFileComparator comp = new NameFileComparator();
		File[] fileArr = comp.sort(FileUtils
				.convertFileCollectionToFileArray(FileUtils.listFiles(
						(new File(filePath)), andFilter,
						TrueFileFilter.INSTANCE)));
		comp = null;

		return new LinkedList<File>(Arrays.asList(fileArr));
	}

	/**
	 * Suffix <br>
	 * Created on: Nov 28, 2012 1:50:50 PM
	 * 
	 * @param filePath
	 * @param nameFix
	 * @param newerTime
	 * @param isOlder
	 *            true : all file ,false : newer file
	 * @return
	 */
	public static Collection<File> getSuffixAndNewerFiles(String filePath,
			String nameFix, long newerTime, boolean isOlder) {

		IOFileFilter wildcardFileFilter = new SuffixFileFilter(nameFix);
		IOFileFilter ageFileFilter = new AgeFileFilter(newerTime, isOlder);
		IOFileFilter andFilter = FileFilterUtils.and(wildcardFileFilter,
				ageFileFilter);

		NameFileComparator comp = new NameFileComparator();
		File[] fileArr = comp.sort(FileUtils
				.convertFileCollectionToFileArray(FileUtils.listFiles(
						(new File(filePath)), andFilter,
						TrueFileFilter.INSTANCE)));
		comp = null;

		return new LinkedList<File>(Arrays.asList(fileArr));
	}

	/**
	 * IOFileFilter <br>
	 * Created on: Nov 29, 2012 9:32:51 AM
	 * 
	 * @param filePath
	 * @param fileFilter
	 * @return
	 */
	public static Collection<File> getFiles(String filePath,
			IOFileFilter fileFilter) {

		NameFileComparator comp = new NameFileComparator();
		File[] fileArr = comp.sort(FileUtils
				.convertFileCollectionToFileArray(FileUtils.listFiles(
						(new File(filePath)), fileFilter,
						TrueFileFilter.INSTANCE)));
		comp = null;

		return new LinkedList<File>(Arrays.asList(fileArr));

	}

	/**
	 * Batch copy files <br>
	 * Created on: Nov 28, 2012 2:08:59 PM
	 * 
	 * @param srcFiles
	 * @param destPath
	 * @return copy file failed Collection
	 */
	public static Collection<File> copyFiles(Collection<File> srcFiles,
			String destPath) {
		Collection<File> errorFiles = new ArrayList<File>();
		for (File srcFile : srcFiles) {
			File destFile = new File(formatPath(destPath) + srcFile.getName());
			try {
				copyFile(srcFile, destFile);
			} catch (IOException e) {
				errorFiles.add(srcFile);
			}
		}

		return errorFiles;
	}

	/**
	 * Copy one file <br>
	 * Created on: Nov 28, 2012 2:07:01 PM
	 * 
	 * @param srcFile
	 * @param destFile
	 * @throws IOException
	 */
	public static void copyFile(File srcFile, File destFile) throws IOException {
		FileUtils.copyFile(srcFile, destFile);
	}

	/**
	 * Move one file <br>
	 * Created on: Dec 13, 2012 9:21:05 PM
	 * 
	 * @param srcFile
	 * @param destFile
	 * @throws IOException
	 */
	public static void moveFile(File srcFile, File destFile) throws IOException {
		FileUtils.moveFile(srcFile, destFile);
	}

	/**
	 * Delete file <br>
	 * Created on: Nov 28, 2012 2:22:17 PM
	 * 
	 * @param file
	 * @return
	 */
	public static boolean delFile(File file) {
		return FileUtils.deleteQuietly(file);
	}

	// /**
	// * Get file disk space <br>
	// * Created on: 2013-8-19 上午11:52:21
	// *
	// * @param fileDiskName
	// * @return
	// */
	// public static String getFileDiskSpace(String fileDiskName) {
	// String[] commands = new String[] { "du", "-sh", fileDiskName };
	// return getProcessExecResult(commands);
	// }
	//
	// /**
	// * <br>
	// * Created on: 2013-8-19 上午11:53:10
	// *
	// * @param commands
	// * @return
	// */
	// public static String getProcessExecResult(String[] commands) {
	//
	// try {
	// String value = "";
	// Process process = Runtime.getRuntime().exec(commands);
	// // for showing the info on screen
	// InputStreamReader ir = new InputStreamReader(
	// process.getInputStream());
	// BufferedReader input = new BufferedReader(ir);
	//
	// String line = "";
	// while ((line = input.readLine()) != null) {
	//
	// String[] strs = line.split("\\s+");
	// value = strs[0];
	// }
	//
	// return value;
	// } catch (IOException e) {
	// return null;
	// }
	// }

	/**
	 * 
	 * <br>
	 * Created on: 2013-8-20 下午09:09:33
	 * 
	 * @param filePath
	 * @return
	 */
	public static File getFilePath(String filePath) {
		File path = new File(filePath);
		if (!DataTools.isEmpty(path)) {
			if (!path.exists())
				if (!path.mkdirs()) {
					return null;
				}
		} else {
			if (!path.mkdirs()) {
				return null;
			}
		}
		return path;
	}

}
