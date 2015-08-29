/**
 * z.z.w.common.FileWriteOper.java
 */
package z.z.w.common;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/** 
 * @author Wu Zhenzhen
 * @version Apr 19, 2013 2:47:55 PM
 * @deprecated please use<code>z.z.w.common.FileOperUtil</code>
 */
public class FileWriteOper {

	private String path = null;
	private File file = null;
	private OutputStreamWriter write = null;
	private BufferedWriter writer = null;

	/**
	 * <br>
	 * Created on: Apr 19, 2013 2:49:39 PM
	 * 
	 * @param file
	 */
	public FileWriteOper(File file) {
		super();
		this.file = file;
		init();
	}

	/**
	 * <br>
	 * Created on: Apr 19, 2013 2:49:39 PM
	 * 
	 * @param path
	 */
	public FileWriteOper(String path) {
		super();
		this.path = path;
		init();
	}

	/**
	 * <br>
	 * Created on: Apr 19, 2013 2:50:12 PM
	 */
	private void init() {
		if (null == file)
			file = new File(this.path);

		this.path = this.file.getPath();
	}

	/**
	 * <br>
	 * Created on: Apr 19, 2013 2:50:59 PM
	 * 
	 * @return the file
	 */
	public File getFile() {
		return file;
	}

	/**
	 * <br>
	 * Created on: Apr 19, 2013 2:51:06 PM
	 * 
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * 
	 * <br>
	 * Created on: Apr 19, 2013 2:51:40 PM
	 * 
	 * @return
	 */
	public String getFileName() {
		return file.getName();
	}

	/**
	 * 
	 * <br>
	 * Created on: Apr 19, 2013 3:03:07 PM
	 * 
	 * @throws IOException
	 */
	public void openFileWriter() throws IOException {
		if (file == null)
			file = new File(path);

		if (!file.exists())
			file.createNewFile();

		write = new OutputStreamWriter(new FileOutputStream(file));
		writer = new BufferedWriter(write);
	}

	/**
	 * 
	 * <br>
	 * Created on: Apr 19, 2013 3:03:31 PM
	 * 
	 * @param encode
	 * @throws IOException
	 */
	public void openFileWriter(String encode) throws IOException {
		if (file == null)
			file = new File(path);

		if (!file.exists())
			file.createNewFile();

		write = new OutputStreamWriter(new FileOutputStream(file), encode);
		writer = new BufferedWriter(write);
	}

	/**
	 * 必须在调用了<code>openFileWriter()</code>方法以后，才能使用，一次写入一个字符串 <br>
	 * Created on: Apr 19, 2013 3:03:50 PM
	 * 
	 * @param str
	 * @throws IOException
	 */
	public void writeString(String str) throws IOException {

		if (DataTools.isEmpty(writer))
			throw new IOException(
					"BufferedWriter is not construct,please invode openFileWriter().");

		writer.write(str);
	}

	/**
	 * 
	 * <br>
	 * Created on: Apr 19, 2013 3:04:14 PM
	 * 
	 */
	public void closeFileWriter() {

		if (!DataTools.isEmpty(writer)) {
			try {
				writer.flush();

				if (!DataTools.isEmpty(write))
					write.close();

				writer.close();

			} catch (IOException e) {
			}

			write = null;
			writer = null;
		}
	}

}
