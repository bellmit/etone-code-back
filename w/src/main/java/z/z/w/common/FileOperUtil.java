/**
 * z.z.w.common.FileOperUtil.java
 */
package z.z.w.common;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Stack;

/**
 * @author Wu Zhenzhen
 * @version 2013-7-21 上午11:16:15
 */
public class FileOperUtil {

	private String path = null;
	private File file = null;
	private InputStreamReader read = null;
	private BufferedReader reader = null;
	private OutputStreamWriter write = null;
	private BufferedWriter writer = null;

	private static final byte[] lock = new byte[0];

	/**
	 * <br>
	 * Created on: 2013-7-21 上午11:17:08
	 * 
	 * @param path
	 */
	private FileOperUtil(String path) {
		super();
		this.path = path;
		init();
	}

	/**
	 * <br>
	 * Created on: 2013-7-21 上午11:17:13
	 * 
	 * @param file
	 */
	private FileOperUtil(File file) {
		super();
		this.file = file;
		init();
	}

	/**
	 * <br>
	 * Created on: 2013-7-21 上午11:19:14
	 */
	private void init() {
		if (null == file)
			file = new File(path);
	}

	/**
	 * 
	 * <br>
	 * Created on: 2013-7-21 上午11:19:20
	 * 
	 * @param path
	 * @return
	 */
	public static FileOperUtil getInstance(String path) {
		synchronized (lock) {
			return new FileOperUtil(path);
		}
	}

	/**
	 * 
	 * <br>
	 * Created on: 2013-7-21 上午11:19:26
	 * 
	 * @param file
	 * @return
	 */
	public static FileOperUtil getInstance(File file) {
		synchronized (lock) {
			return new FileOperUtil(file);
		}
	}

	/**
	 * 
	 * <br>
	 * Created on: 2013-7-21 上午11:22:39
	 * 
	 * @return
	 * @throws IOException
	 */
	public File createFile() throws IOException {
		if (!file.exists()) {
			createDir();
			if (file.createNewFile()) {
				return file;
			} else {
				return null;
			}
		} else {
			return file;
		}
	}

	/**
	 * <br>
	 * Created on: 2013-7-21 上午11:22:36
	 */
	private File createDir() {
		if (!file.exists()) {
			Stack<File> stack = new Stack<File>();
			File current = file.getParentFile();
			// 把缺失的目录放到栈里
			while (current != null && !current.exists()) {
				stack.push(current);
				current = current.getParentFile();
			}
			// 建立缺失的目录
			while (!stack.isEmpty()) {
				stack.pop().mkdir();
			}
		}
		return file;
	}

	/**
	 * 只能重命名名字，不能移动 <br>
	 * Created on: 2013-7-21 上午11:23:59
	 * 
	 * @param otherName
	 * @return
	 */
	public boolean rename(String otherName) {
		String otherPath = null;
		File otherFile = null;

		otherPath = file.getParent() + "/" + otherName;

		otherFile = new File(otherPath);

		if (file.exists()) {
			if (file.renameTo(otherFile)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * 开启文件读取器，只适用于文本文件 <br>
	 * Created on: 2013-7-21 上午11:24:47
	 * 
	 * @throws IOException
	 */
	public void openFileReader() throws IOException {
		if (null == file) {
			file = new File(path);
		}

		if (!file.isFile()) {
			throw new IOException("\"" + path + "\" is not a file path!");
		}
		if (!file.exists()) {
			throw new IOException("\"" + path + "\" haven't exists!");
		}

		read = new InputStreamReader(new FileInputStream(file));
		reader = new BufferedReader(read);
	}

	/**
	 * 开启文件读取器，只适用于文本文件，指定编码方式 <br>
	 * Created on: 2013-7-21 上午11:26:05
	 * 
	 * @param encode
	 * @throws IOException
	 */
	public void openFileReader(String encode) throws IOException {
		if (null == file) {
			file = new File(path);
		}

		if (!file.isFile()) {
			throw new IOException("\"" + path + "\" is not a file path!");
		}
		if (!file.exists()) {
			throw new IOException("\"" + path + "\" haven't exists!");
		}

		read = new InputStreamReader(new FileInputStream(file), encode);
		reader = new BufferedReader(read);
	}

	/**
	 * 
	 * <br>
	 * Created on: 2013-7-21 上午11:26:27
	 * 
	 * @return
	 * @throws IOException
	 */
	public String readLine() throws IOException {
		// if (DataTools.isEmpty(reader))
		// throw new IOException(
		// "BufferedReader is null.Please exec openFileReader().");

		return reader.readLine();
	}

	/**
	 * 
	 * <br>
	 * Created on: 2013-7-21 上午11:30:11
	 * 
	 * @throws IOException
	 */
	public void closeFileReader() {
		if (!DataTools.isEmpty(read)) {
			try {
				read.close();
				read = null;
			} catch (IOException e) {
			} finally {
				read = null;
			}
		}

		if (!DataTools.isEmpty(reader)) {
			try {
				reader.close();
				reader = null;
			} catch (IOException e) {
			} finally {
				reader = null;
			}
		}
	}

	/**
	 * 开启文件写入器，只适用于文本文件 <br>
	 * Created on: 2013-7-21 上午11:30:22
	 * 
	 * @throws IOException
	 */
	public void openFileWriter() throws IOException {
		if (file == null) {
			file = new File(path);
		}

		if (!file.isFile()) {
			throw new IOException("\"" + path + "\" is not a file path!");
		} 
		if (!file.exists()) {
			file.createNewFile();
		}

		write = new OutputStreamWriter(new FileOutputStream(file, true));
		writer = new BufferedWriter(write);
	}

	/**
	 * 开启文件写入器，只适用于文本文件，指定编码 <br>
	 * Created on: 2013-7-21 上午11:30:49
	 * 
	 * @param encode
	 * @throws IOException
	 */
	public void openFileWriter(String encode) throws IOException {
		if (file == null) {
			file = new File(path);
		}

		if (!file.isFile()) {
			throw new IOException("\"" + path + "\" is not a file path!");
		}
		if (!file.exists()) {
			file.createNewFile();
		}

		write = new OutputStreamWriter(new FileOutputStream(file, true), encode);
		writer = new BufferedWriter(write);
	}

	/**
	 * 
	 * <br>
	 * Created on: 2013-7-21 上午11:31:47
	 * 
	 * @param str
	 * @throws IOException
	 */
	public void writeString(String str) throws IOException {
		// if (DataTools.isEmpty(writer))
		// throw new IOException(
		// "BufferedWriter is null.Please exec openFileWriter()");
		writer.write(str);
	}

	/**
	 * 
	 * <br>
	 * Created on: 2013-7-21 上午11:33:09
	 * 
	 * @throws IOException
	 */
	public void closeFileWriter() {
		try {
			writer.flush();

			write.close();
			writer.close();

			write = null;
			writer = null;
		} catch (IOException e) {
		} finally {
			write = null;
			writer = null;
		}

	}

	/**
	 * <br>
	 * Created on: 2013-7-21 上午11:20:40
	 * 
	 * @return the path
	 */
	public String getPath() {
		return file.getPath();
	}

	/**
	 * 
	 * <br>
	 * Created on: 2013-7-21 上午11:21:35
	 * 
	 * @return
	 */
	public String getFileName() {
		return file.getName();
	}

	/**
	 * <br>
	 * Created on: 2013-7-21 上午11:20:40
	 * 
	 * @return the file
	 */
	public File getFile() {
		return file;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "FileOperUtil [path=" + path + ", file=" + file + ", read="
				+ read + ", reader=" + reader + ", write=" + write
				+ ", writer=" + writer + "]";
	}

}
