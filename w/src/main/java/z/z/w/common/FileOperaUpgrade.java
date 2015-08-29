/**
 * z.z.w.common.FileOperaUpgrade.java
 */
package z.z.w.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author Wu Zhenzhen
 * @version Dec 13, 2012 3:39:55 PM
 * @deprecated please use<code>z.z.w.common.FileOperUtil</code>
 */
public class FileOperaUpgrade {

	private BufferedReader reader = null;
	private FileWriter writer = null;

	private File file = null;

	/** 
	 * 
	 * <br>
	 * Created on: Dec 13, 2012 3:53:40 PM
	 * 
	 * @throws IOException
	 */
	public void openFileReader() throws IOException {

		if (DataTools.isEmpty(file))
			throw new IOException("File is null.Please init File");

		reader = new BufferedReader(new FileReader(file));
	}

	/**
	 * 
	 * <br>
	 * Created on: Dec 13, 2012 3:53:45 PM
	 * 
	 * @param isAppend
	 * @throws IOException
	 */
	public void openFileWriter(boolean isAppend) throws IOException {

		if (null == this.file)
			throw new IOException("File is null.Please init File");

		writer = new FileWriter(file, isAppend);
	}

	/**
	 * 
	 * <br>
	 * Created on: Dec 13, 2012 3:53:48 PM
	 * 
	 * @throws IOException
	 */
	public void openFileWriter() throws IOException {

		if (null == this.file)
			throw new IOException("File is null.Please init File");

		writer = new FileWriter(file, true);
	}

	/**
	 * write content to file <br>
	 * Created on: Nov 22, 2012 5:09:14 PM
	 * 
	 * @param content
	 * @throws IOException
	 */
	public void write(String content) throws IOException {
		if (DataTools.isEmpty(writer))
			throw new IOException(
					"FileWriter is null.Please exec openFileWriter()");

		writer.write(content);
		writer.flush();

	}

	/**
	 * Read file content line <br>
	 * Created on: Nov 22, 2012 5:13:58 PM
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
	 * File size <br>
	 * Created on: Dec 13, 2012 11:22:35 AM
	 * 
	 * @return
	 */
	public long fileSize() {
		return this.file.length();
	}

	/**
	 * File lastModified <br>
	 * Created on: Dec 13, 2012 11:25:31 AM
	 * 
	 * @return
	 */
	public long fileLastModified() {
		return this.file.lastModified();
	}

	/**
	 * close stream writer & reader<br>
	 * Created on: Nov 22, 2012 5:03:15 PM
	 */
	public void close() {
		if (!DataTools.isEmpty(reader)) {
			try {
				reader.close();
				reader = null;
			} catch (IOException e) {
			}
		}

		if (!DataTools.isEmpty(writer)) {
			try {
				writer.flush();
				writer.close();
				writer = null;
			} catch (IOException e) {
			}
		}
	}

	/**
	 * <br>
	 * Created on: Dec 13, 2012 3:47:59 PM
	 */
	public FileOperaUpgrade() {
		super();
	}

	/**
	 * <br>
	 * Created on: Dec 13, 2012 3:48:08 PM
	 * 
	 * @param fileName
	 *            the fileName to set
	 */
	public void setFileName(String fileName) {
		this.file = new File(fileName);
	}

	/**
	 * <br>
	 * Created on: Dec 13, 2012 3:48:08 PM
	 * 
	 * @param file
	 *            the file to set
	 */
	public void setFile(File file) {
		this.file = file;
	}

	/**
	 * <br>
	 * Created on: Dec 13, 2012 4:44:11 PM
	 * 
	 * @return the file
	 */
	public File getFile() {
		return file;
	}

}
