package com.symbol.wp.tools.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * <p>
 * Title: 文件处理工具类
 * </p>
 * <p>
 * Description:实现文件的简单处理,复制文件、目录等
 * </p>
 * <p>
 */
public class FileUtils {

	private static final Logger logger = LoggerFactory
			.getLogger(FileUtils.class);

	/**
	 * 新建目录
	 * 
	 * @param folderPath
	 *            String 如 c:/fqf
	 * @return boolean
	 */
	public static void newFolder(String folderPath) {
		try {
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			if (!myFilePath.exists()) {
				myFilePath.mkdir();
			}
		} catch (Exception e) {
			logger.info("新建目录操作出错");
			logger.error(e.getMessage());
		}
	}

	/**
	 * 新建文件
	 * 
	 * @param filePathAndName
	 *            String 文件路径及名称 如c:/fqf.txt
	 * @param fileContent
	 *            String 文件内容
	 * @return boolean
	 */
	public static void newFile(String filePathAndName, String fileContent) {

		try {
			String filePath = filePathAndName;
			filePath = filePath.toString();
			File myFilePath = new File(filePath);
			if (!myFilePath.exists()) {
				myFilePath.createNewFile();
			}
			FileWriter resultFile = new FileWriter(myFilePath);
			PrintWriter myFile = new PrintWriter(resultFile);
			String strContent = fileContent;
			myFile.println(strContent);
			resultFile.close();

		} catch (Exception e) {
			logger.info("新建目录操作出错");
			logger.error(e.getMessage());

		}

	}

	/**
	 * 删除文件
	 * 
	 * @param filePathAndName
	 *            String 文件路径及名称 如c:/fqf.txt
	 * @param fileContent
	 *            String
	 * @return boolean
	 */
	public static void delFile(String filePathAndName) {
		try {
			String filePath = filePathAndName;
			filePath = filePath.toString();
			java.io.File myDelFile = new java.io.File(filePath);
			myDelFile.delete();

		} catch (Exception e) {
			logger.info("删除文件操作出错");
			logger.error(e.getMessage());

		}

	}

	/**
	 * 删除文件夹
	 * 
	 * @param filePathAndName
	 *            String 文件夹路径及名称 如c:/fqf
	 * @param fileContent
	 *            String
	 * @return boolean
	 */
	public static void delFolder(String folderPath) {
		try {
			delAllFile(folderPath); // 删除完里面所有内容
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			myFilePath.delete(); // 删除空文件夹

		} catch (Exception e) {
			logger.info("删除文件夹操作出错");
			logger.error(e.getMessage());

		}

	}

	/**
	 * 删除文件夹里面的所有文件
	 * 
	 * @param path
	 *            String 文件夹路径 如 c:/fqf
	 */
	public static void delAllFile(String path) {
		File file = new File(path);
		if (!file.exists()) {
			return;
		}
		if (!file.isDirectory()) {
			return;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
				delFolder(path + "/" + tempList[i]);// 再删除空文件夹
			}
		}
	}

	/**
	 * 复制单个文件
	 * 
	 * @param oldPath
	 *            String 原文件路径 如：c:/fqf.txt
	 * @param newPath
	 *            String 复制后路径 如：f:/fqf.txt
	 * @return boolean
	 */
	public static void copyFile(String oldPath, String newPath) {
		try {
			int bytesum = 0;
			int byteread = 0;
			File oldfile = new File(oldPath);
			if (oldfile.exists()) { // 文件存在时
				InputStream inStream = new FileInputStream(oldPath); // 读入原文件
				FileOutputStream fs = new FileOutputStream(newPath);
				byte[] buffer = new byte[1444];
				int length;
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread; // 字节数 文件大小
					logger.info(bytesum+"");
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
			}
		} catch (Exception e) {
			logger.info("复制单个文件操作出错");
			logger.error(e.getMessage());

		}

	}

	/**
	 * 复制整个文件夹内容
	 * 
	 * @param oldPath
	 *            String 原文件路径 如：c:/fqf
	 * @param newPath
	 *            String 复制后路径 如：f:/fqf/ff
	 * @return boolean
	 */
	public static void copyFolder(String oldPath, String newPath) {

		try {
			(new File(newPath)).mkdirs(); // 如果文件夹不存在 则建立新文件夹
			File a = new File(oldPath);
			String[] file = a.list();
			File temp = null;
			for (int i = 0; i < file.length; i++) {
				if (oldPath.endsWith(File.separator)) {
					temp = new File(oldPath + file[i]);
				} else {
					temp = new File(oldPath + File.separator + file[i]);
				}

				if (temp.isFile()) {
					FileInputStream input = new FileInputStream(temp);
					FileOutputStream output = new FileOutputStream(newPath
							+ "/" + (temp.getName()).toString());
					byte[] b = new byte[1024 * 5];
					int len;
					while ((len = input.read(b)) != -1) {
						output.write(b, 0, len);
					}
					output.flush();
					output.close();
					input.close();
				}
				if (temp.isDirectory()) {// 如果是子文件夹
					copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
				}
			}
		} catch (Exception e) {
			logger.info("复制整个文件夹内容操作出错");
			logger.error(e.getMessage());

		}

	}

	/**
	 * 移动文件到指定目录
	 * 
	 * @param oldPath
	 *            String 如：c:/fqf.txt
	 * @param newPath
	 *            String 如：d:/fqf.txt
	 */
	public static void moveFile(String oldPath, String newPath) {
		copyFile(oldPath, newPath);
		delFile(oldPath);

	}

	/**
	 * 移动文件到指定目录
	 * 
	 * @param oldPath
	 *            String 如：c:/fqf.txt
	 * @param newPath
	 *            String 如：d:/fqf.txt
	 */
	public static void moveFolder(String oldPath, String newPath) {
		copyFolder(oldPath, newPath);
		delFolder(oldPath);

	}

	// ////////////////////////////////////////////////////////////////////////

	/**
	 * 复制目录下的文件（不包括该目录）到指定目录，会连同子目录一起复制过去。
	 * 
	 * @param targetFile
	 * @param path
	 */
	public static void copyFileFromDir(String targetDir, String path) {
		File file = new File(path);
		createFile(targetDir, false);
		if (file.isDirectory()) {
			copyFileToDir(targetDir, listFile(file));
		}
	}

	/**
	 * 复制目录下的文件（不包含该目录和子目录，只复制目录下的文件）到指定目录。
	 * 
	 * @param targetDir
	 * @param path
	 */
	public static void copyFileOnly(String targetDir, String path) {
		File file = new File(path);
		File targetFile = new File(targetDir);
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (File subFile : files) {
				if (subFile.isFile()) {
					copyFile(targetFile, subFile);
				}
			}
		}
	}

	/**
	 * 复制目录到指定目录。targetDir是目标目录，path是源目录。 该方法会将path以及path下的文件和子目录全部复制到目标目录
	 * 
	 * @param targetDir
	 * @param path
	 */
	public static void copyDir(String targetDir, String path) {
		File targetFile = new File(targetDir);
		createFile(targetFile, false);
		File file = new File(path);
		if (targetFile.isDirectory() && file.isDirectory()) {
			copyFileToDir(targetFile.getAbsolutePath() + "/" + file.getName(),
					listFile(file));
		}
	}

	/**
	 * 复制一组文件到指定目录。targetDir是目标目录，filePath是需要复制的文件路径
	 * 
	 * @param targetDir
	 * @param filePath
	 */
	public static void copyFileToDir(String targetDir, String... filePath) {
		if (targetDir == null || "".equals(targetDir)) {
			logger.info("参数错误，目标路径不能为空");
			return;
		}
		File targetFile = new File(targetDir);
		if (!targetFile.exists()) {
			targetFile.mkdir();
		} else {
			if (!targetFile.isDirectory()) {
				logger.info("参数错误，目标路径指向的不是一个目录！");
				return;
			}
		}
		for (String path : filePath) {
			File file = new File(path);
			if (file.isDirectory()) {
				copyFileToDir(targetDir + "/" + file.getName(), listFile(file));
			} else {
				copyFileToDir(targetDir, file, "");
			}
		}
	}

	/**
	 * 复制文件到指定目录。targetDir是目标目录，file是源文件名，newName是重命名的名字。
	 * 
	 * @param targetFile
	 * @param file
	 * @param newName
	 */
	public static void copyFileToDir(String targetDir, File file, String newName) {
		String newFile = "";
		if (newName != null && !"".equals(newName)) {
			newFile = targetDir + "/" + newName;
		} else {
			newFile = targetDir + "/" + file.getName();
		}
		File tFile = new File(newFile);
		copyFile(tFile, file);
	}

	/**
	 * 复制文件。targetFile为目标文件，file为源文件
	 * 
	 * @param targetFile
	 * @param file
	 */
	public static void copyFile(File targetFile, File file) {
		if (targetFile.exists()) {
			logger.info("文件" + targetFile.getAbsolutePath()
					+ "已经存在，跳过该文件！");
			return;
		} else {
			createFile(targetFile, true);
		}
		logger.info("复制文件" + file.getAbsolutePath() + "到"
				+ targetFile.getAbsolutePath());
		try {
			InputStream is = new FileInputStream(file);
			FileOutputStream fos = new FileOutputStream(targetFile);
			byte[] buffer = new byte[1024];
			while (is.read(buffer) != -1) {
				fos.write(buffer);
			}
			is.close();
			fos.close();
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}

	public static String[] listFile(File dir) {
		String absolutPath = dir.getAbsolutePath();
		String[] paths = dir.list();
		String[] files = new String[paths.length];
		for (int i = 0; i < paths.length; i++) {
			files[i] = absolutPath + "/" + paths[i];
		}
		return files;
	}

	public static void createFile(String path, boolean isFile) {
		createFile(new File(path), isFile);
	}

	public static void createFile(File file, boolean isFile) {
		if (!file.exists()) {
			if (!file.getParentFile().exists()) {
				createFile(file.getParentFile(), false);
			} else {
				if (isFile) {
					try {
						file.createNewFile();
					} catch (IOException e) {
						logger.error(e.getMessage());
					}
				} else {
					file.mkdir();
				}
			}
		}
	}

}