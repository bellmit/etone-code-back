/**
 * z.z.w.common.FileOperator.java
 */
package z.z.w.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * File write & read operator
 * 
 * @author Wu Zhenzhen
 * @version Nov 22, 2012 4:53:27 PM
 * @deprecated please use<code>z.z.w.common.FileOperUtil</code>
 */
public class FileOperator {

	private BufferedReader reader = null;
	private FileWriter writer = null;

	private File file = null;
 
	private long writeSize = 0l;
	private long readSize = 0l;

	/**
	 * File oper type :
	 * <p>
	 * 0. write file <br>
	 * 1. read file
	 */
	public static final byte FILE_OPER_TYPE_WRITE = 0x00;

	/**
	 * File oper type :
	 * <p>
	 * 0. write file <br>
	 * 1. read file
	 */
	public static final byte FILE_OPER_TYPE_READ = 0x01;

	private byte fileOperType = FILE_OPER_TYPE_WRITE;

	/**
	 * 
	 * <br>
	 * Created on: Nov 22, 2012 5:05:52 PM
	 * 
	 * @param fileName
	 * @param fileOperType
	 *            FileOperator.FILE_OPER_TYPE_WRITE. write file <br>
	 *            FileOperator.FILE_OPER_TYPE_READ. read file
	 * @throws IOException
	 */
	public FileOperator(String fileName, byte fileOperType) throws IOException {
		this.fileOperType = fileOperType;

		file = new File(fileName);

		init();
	}

	/**
	 * 
	 * <br>
	 * Created on: Dec 13, 2012 11:44:34 AM
	 * 
	 * @param fileOperType
	 * @throws IOException
	 */
	public FileOperator(byte fileOperType) {
		this.fileOperType = fileOperType;
	}

	/**
	 * 
	 * <br>
	 * Created on: Dec 13, 2012 11:46:33 AM
	 * 
	 * @param fileName
	 * @throws IOException
	 */
	public void setFileName(String fileName) throws IOException {
		file = new File(fileName);
		init();
	}

	/**
	 * 
	 * <br>
	 * Created on: Nov 28, 2012 2:27:48 PM
	 * 
	 * @param file
	 * @param fileOperType
	 *            FileOperator.FILE_OPER_TYPE_WRITE. write file <br>
	 *            FileOperator.FILE_OPER_TYPE_READ. read file
	 * @throws IOException
	 */
	public FileOperator(File file, byte fileOperType) throws IOException {
		this.file = file;
		this.fileOperType = fileOperType;

		init();
	}

	/**
	 * <br>
	 * Created on: Nov 22, 2012 4:58:48 PM
	 * 
	 * @param fileOperType
	 * @throws IOException
	 */
	public void init() throws IOException {

		switch (fileOperType) {
		case FILE_OPER_TYPE_READ:
			reader = new BufferedReader(new FileReader(file));
			break;
		case FILE_OPER_TYPE_WRITE:
			writer = new FileWriter(file, true);
			break;

		default:
			writer = new FileWriter(file, true);
			break;
		}
	}

	/**
	 * create file if file exist append file end <br>
	 * Created on: Nov 23, 2012 10:03:07 AM
	 * 
	 * @throws IOException
	 */
	public void createFile() throws IOException {

		if (DataTools.isEmpty(writer))
			throw new IOException("FileWriter is null.Please init FileWriter");

		writer = new FileWriter(file, true);
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
	 * write content to file <br>
	 * Created on: Nov 22, 2012 5:09:14 PM
	 * 
	 * @param content
	 * @throws IOException
	 */
	public void write(String content) throws IOException {
		if (DataTools.isEmpty(writer))
			throw new IOException("FileWriter is null.Please init FileWriter");

		writer.write(content);
		writer.flush();

		writeSize++;
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
					"BufferedReader is null.Please init BufferedReader");

		readSize++;

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
	 * <br>
	 * Created on: Dec 13, 2012 2:34:25 PM
	 * 
	 * @return the writeSize
	 */
	public long getWriteSize() {
		return writeSize;
	}

	/**
	 * <br>
	 * Created on: Dec 13, 2012 2:34:25 PM
	 * 
	 * @return the readSize
	 */
	public long getReadSize() {
		return readSize;
	}

	/**
	 * <br>
	 * Created on: Dec 13, 2012 3:27:31 PM
	 * 
	 * @param fileName
	 * @throws IOException
	 */
	public void reSetFile(String fileName) throws IOException {
		this.file = new File(fileName);

		init();
	}

}
