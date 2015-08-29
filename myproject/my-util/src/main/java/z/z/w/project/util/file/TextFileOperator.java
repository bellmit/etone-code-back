/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.util.file.TextFileOperator.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-9-10 上午10:01:53 
 *   LastChange: 2013-9-10 上午10:01:53 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.util.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Stack;

import z.z.w.project.util.common.DataTools;

/**
 * z.z.w.project.util.file.TextFileOperator.java
 */
public class TextFileOperator {

	private String path = null;
	private File file = null;
	private InputStreamReader read = null;
	private BufferedReader reader = null;
	private OutputStreamWriter write = null;
	private BufferedWriter writer = null;

	private static final byte[] lock = new byte[0];

	/**
	 * 
	 * <br>
	 * Created on: 2013-9-10 上午10:04:11
	 * 
	 * @param path
	 * @return
	 */
	public static TextFileOperator getInstance(String path) {
		synchronized (lock) {
			return new TextFileOperator(path);
		}
	}

	/**
	 * 
	 * <br>
	 * Created on: 2013-9-10 上午10:04:55
	 * 
	 * @param file
	 * @return
	 */
	public static TextFileOperator getInstance(File file) {
		synchronized (lock) {
			return new TextFileOperator(file);
		}
	}

	/**
	 * <br>
	 * Created on: 2013-9-10 上午10:02:28
	 * 
	 * @param path
	 */
	private TextFileOperator(String path) {
		super();
		this.path = path;
		init();
	}

	/**
	 * <br>
	 * Created on: 2013-9-10 上午10:02:41
	 */
	private void init() {
		if (null == file)
			file = new File(path);
	}

	/**
	 * <br>
	 * Created on: 2013-9-10 上午10:02:33
	 * 
	 * @param file
	 */
	private TextFileOperator(File file) {
		super();
		this.file = file;
		init();
	}

	/**
	 * 
	 * <br>
	 * Created on: 2013-9-10 上午10:15:45
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
	 * 
	 * <br>
	 * Created on: 2013-9-10 上午10:15:42
	 * 
	 * @return
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
	 * Created on: 2013-9-10 上午10:09:10
	 * 
	 * @param otherName
	 * @return
	 */
	public boolean rename(String otherName) {
		String otherPath = file.getParent() + "/" + otherName;
		File otherFile = new File(otherPath);

		if (file.exists()) {
			return file.renameTo(otherFile);
		}

		return false;
	}

	/**
	 * 开启文件读取器，只适用于文本文件 <br>
	 * Created on: 2013-9-10 上午10:10:55
	 * 
	 * @throws IOException
	 */
	public void openFileReader() throws IOException {
		if (null == file) {
			file = new File(path);
		}

		if (!file.isFile()) {
			throw new IOException("[" + path + "] is not a file path!");
		}

		if (!file.exists()) {
			throw new IOException("[" + path + "] haven't exists!");
		}

		read = new InputStreamReader(new FileInputStream(file));
		reader = new BufferedReader(read);
	}

	/**
	 * 开启文件读取器，只适用于文本文件，指定编码方式 <br>
	 * Created on: 2013-9-10 上午10:12:46
	 * 
	 * @param encode
	 * @throws IOException
	 */
	public void openFileReader(String encode) throws IOException {
		if (null == file) {
			file = new File(path);
		}

		if (!file.isFile()) {
			throw new IOException("[" + path + "] is not a file path!");
		}

		if (!file.exists()) {
			throw new IOException("[" + path + "] haven't exists!");
		}

		read = new InputStreamReader(new FileInputStream(file), encode);
		reader = new BufferedReader(read);
	}

	/**
	 * 
	 * <br>
	 * Created on: 2013-9-10 上午10:15:35
	 * 
	 * @return
	 * @throws IOException
	 */
	public String readLine() throws IOException {
		if (DataTools.isEmpty(reader))
			throw new IOException(
					"BufferedReader is null.Please exec openFileReader().");

		return reader.readLine();
	}

	/**
	 * 
	 * <br>
	 * Created on: 2013-9-10 上午10:15:32
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
	 * Created on: 2013-9-10 上午10:13:45
	 * 
	 * @throws IOException
	 */
	public void openFileWriter() throws IOException {
		if (file == null) {
			file = new File(path);
		}

		if (!file.isFile()) {
			throw new IOException("[" + path + "] is not a file path!");
		}
		if (!file.exists()) {
			file.createNewFile();
		}

		write = new OutputStreamWriter(new FileOutputStream(file, true));
		writer = new BufferedWriter(write);
	}

	/**
	 * 开启文件写入器，只适用于文本文件，指定编码 <br>
	 * Created on: 2013-9-10 上午10:14:08
	 * 
	 * @param encode
	 * @throws IOException
	 */
	public void openFileWriter(String encode) throws IOException {
		if (file == null) {
			file = new File(path);
		}

		if (!file.isFile()) {
			throw new IOException("[" + path + "] is not a file path!");
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
	 * Created on: 2013-9-10 上午10:15:25
	 * 
	 * @param str
	 * @throws IOException
	 */
	public void writeString(String str) throws IOException {
		if (DataTools.isEmpty(writer))
			throw new IOException(
					"BufferedWriter is null.Please exec openFileWriter()");
		writer.write(str);
	}

	/**
	 * 
	 * <br>
	 * Created on: 2013-9-10 上午10:15:20
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
	 * 
	 * <br>
	 * Created on: 2013-9-10 上午10:15:16
	 * 
	 * @return
	 */
	public String getPath() {
		return file.getPath();
	}

	/**
	 * 
	 * <br>
	 * Created on: 2013-9-10 上午10:15:12
	 * 
	 * @return
	 */
	public String getFileName() {
		return file.getName();
	}

	/**
	 * 
	 * <br>
	 * Created on: 2013-9-10 上午10:15:08
	 * 
	 * @return
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
		return "TextFileOperator [path=" + path + ", file=" + file + ", read="
				+ read + ", reader=" + reader + ", write=" + write
				+ ", writer=" + writer + "]";
	}
}
