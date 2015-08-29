/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.util.ftp.FtpOperator.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-9-11 下午04:21:13 
 *   LastChange: 2013-9-11 下午04:21:13 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.util.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPFileFilter;

import z.z.w.project.util.common.DataTools;
import z.z.w.project.util.file.FileTools;

/**
 * z.z.w.project.util.ftp.FtpOperator.java
 */
public class FtpOperator {
	private String ftpServer = null;
	private int ftpPort = -1;
	private String ftpUser = null;
	private String ftpPassword = null;
	private FTPClient ftpClient = null;

	/**
	 * <br>
	 * Created on: 2013-9-11 下午04:21:43
	 * 
	 * @param ftpServer
	 * @param ftpPort
	 * @param ftpUser
	 * @param ftpPassword
	 */
	public FtpOperator(String ftpServer, int ftpPort, String ftpUser,
			String ftpPassword) {
		super();
		this.ftpServer = ftpServer;
		this.ftpPort = ftpPort;
		this.ftpUser = ftpUser;
		this.ftpPassword = ftpPassword;

		init();
	}

	/**
	 * <br>
	 * Created on: 2013-9-11 下午04:21:51
	 */
	private void init() {
		ftpClient = new FTPClient();
	}

	/**
	 * Create ftp server connection <br>
	 * Created on: 2013-9-11 下午04:28:58
	 * 
	 * @return
	 * @throws SocketException
	 * @throws IOException
	 */
	public boolean createConnection() throws SocketException, IOException {

		if (DataTools.isEmpty(ftpClient))
			init();

		if (isFtpConnected())
			return true;

		ftpClient.connect(ftpServer, ftpPort);
		ftpClient.login(ftpUser, ftpPassword);

		if (!isFtpConnected())
			return false;

		ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);

		return true;
	}

	/**
	 * <br>
	 * Created on: 2013-9-11 下午04:24:34
	 * 
	 * @return
	 */
	private boolean isFtpConnected() {
		if (DataTools.isEmpty(ftpClient))
			init();
		return ftpClient.isConnected();
	}

	/**
	 * Close ftp server connection <br>
	 * Created on: 2013-9-11 下午04:31:01
	 */
	public void closeConnection() {

		try {
			if (!DataTools.isEmpty(ftpClient))
				if (isFtpConnected()) {
					ftpClient.disconnect();
					ftpClient = null;
				}
		} catch (IOException e) {
		} finally {
			ftpClient = null;
		}

	}

	/**
	 * Set ftp connection oper encoding <br>
	 * Created on: 2013-9-11 下午04:31:34
	 * 
	 * @param encoding
	 */
	public void setEncoding(String encoding) {
		if (DataTools.isEmpty(ftpClient))
			init();
		ftpClient.setControlEncoding(encoding);
	}

	/**
	 * 
	 * <br>
	 * Created on: 2013-9-11 下午04:44:16
	 * 
	 * @param remotePath
	 * @param remoteFileName
	 * @param localPath
	 * @throws Exception
	 */
	public void downLoad(String remoteFile, String localPath) throws Exception {
		String remotePath = FilenameUtils.getFullPath(remoteFile);
		String remoteFileName = FilenameUtils.getName(remoteFile);
		File file = new File(FileTools.addPathSeparator(localPath)
				+ remoteFileName);
		downLoad(remotePath, remoteFileName, file);
	}

	/**
	 * 
	 * <br>
	 * Created on: 2013-9-11 下午04:56:00
	 * 
	 * @param remoteFile
	 * @param localPath
	 * @param localFileName
	 * @throws Exception
	 */
	public void downLoad(String remoteFile, String localPath,
			String localFileName) throws Exception {
		String remotePath = FilenameUtils.getFullPath(remoteFile);
		String remoteFileName = FilenameUtils.getName(remoteFile);
		File file = new File(FileTools.addPathSeparator(localPath)
				+ localFileName);
		downLoad(remotePath, remoteFileName, file);
	}

	/**
	 * <br>
	 * Created on: 2013-9-11 下午04:38:31
	 * 
	 * @param remotePath
	 * @param remoteFileName
	 * @param localFile
	 * @throws IOException
	 */
	private void downLoad(String remotePath, String remoteFileName,
			File localFile) throws IOException {

		ftpClient
				.changeWorkingDirectory(FileTools.delPathSeparator(remotePath));

		OutputStream local = null;
		try {
			local = new FileOutputStream(localFile);

			ftpClient.retrieveFile(remoteFileName, local);

			ftpClient.changeToParentDirectory();

			local.flush();
			local.close();
			local = null;

		} finally {
			try {
				if (!DataTools.isEmpty(local)) {
					local.flush();
					local.close();
					local = null;
				}
			} catch (IOException e) {
			} finally {
				local = null;
			}
		}
	}

	/**
	 * 
	 * <br>
	 * Created on: 2013-9-11 下午04:49:39
	 * 
	 * @param localFile
	 * @param remotePath
	 * @throws IOException
	 */
	public void upload(String localFile, String remotePath) throws IOException {
		File file = new File(localFile);
		String remoteFileName = FilenameUtils.getName(localFile);
		upload(file, remotePath, remoteFileName);
	}

	/**
	 * 
	 * <br>
	 * Created on: 2013-9-11 下午04:49:28
	 * 
	 * @param localFile
	 * @param remotePath
	 * @param remoteFileName
	 * @throws IOException
	 */
	public void upload(String localFile, String remotePath,
			String remoteFileName) throws IOException {
		File file = new File(localFile);
		upload(file, remotePath, remoteFileName);
	}

	/**
	 * 
	 * <br>
	 * Created on: 2013-9-11 下午04:59:35
	 * 
	 * @param localFile
	 * @param remotePath
	 * @throws IOException
	 */
	public void upload(File localFile, String remotePath) throws IOException {
		String remoteFileName = localFile.getName();
		upload(localFile, remotePath, remoteFileName);
	}

	/**
	 * <br>
	 * Created on: 2013-9-11 下午04:46:18
	 * 
	 * @param remotePath
	 * @param remoteFileName
	 * @param localFile
	 * @throws IOException
	 */
	public void upload(File localFile, String remotePath, String remoteFileName)
			throws IOException {
		ftpClient
				.changeWorkingDirectory(FileTools.delPathSeparator(remotePath));

		InputStream local = null;

		try {
			ftpClient.enterLocalPassiveMode();
			ftpClient.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);

			local = new FileInputStream(localFile);

			ftpClient.storeFile(remoteFileName, local);

			local.close();
			local = null;
		} finally {
			if (!DataTools.isEmpty(local)) {
				local.close();
				local = null;
			}
		}
	}

	/**
	 * 只能建立下級路徑，不能遞歸建立多個路徑 <br>
	 * Created on: 2013-9-11 下午05:01:09
	 * 
	 * @param remotePath
	 * @param newPathName
	 * @return
	 * @throws IOException
	 */
	public boolean createDirInServer(String remotePath, String newPathName)
			throws IOException {

		ftpClient
				.changeWorkingDirectory(FileTools.delPathSeparator(remotePath));
		return ftpClient.makeDirectory(newPathName);

	}

	/**
	 * 
	 * <br>
	 * Created on: 2013-9-11 下午05:01:35
	 * 
	 * @param remotePath
	 * @param remoteFileName
	 * @return
	 * @throws IOException
	 */
	public boolean deleteFile(String remotePath, String remoteFileName)
			throws IOException {
		ftpClient
				.changeWorkingDirectory(FileTools.delPathSeparator(remotePath));
		return ftpClient.deleteFile(remoteFileName);
	}

	/**
	 * 
	 * <br>
	 * Created on: 2013-9-11 下午05:03:03
	 * 
	 * @param remoteFile
	 * @return
	 * @throws IOException
	 */
	public boolean deleteFile(String remoteFile) throws IOException {
		String remotePath = FilenameUtils.getFullPath(remoteFile);
		String remoteFileName = FilenameUtils.getName(remoteFile);
		return deleteFile(remotePath, remoteFileName);
	}

	/**
	 * 
	 * <br>
	 * Created on: 2013-9-11 下午05:15:30
	 * 
	 * @param sourceFile
	 * @param regex
	 * @param replacement
	 * @return
	 * @throws IOException
	 */
	public boolean renameFile(String sourceFile, String regex,
			String replacement) throws IOException {
		String targetFile = sourceFile.replaceAll(regex, replacement);
		return renameFile(sourceFile, targetFile);
	}

	/**
	 * <br>
	 * Created on: 2013-9-11 下午05:12:59
	 * 
	 * @param sourceFile
	 * @param targetFile
	 * @return
	 * @throws IOException
	 */
	public boolean renameFile(String sourceFile, String targetFile)
			throws IOException {
		ftpClient.changeWorkingDirectory(FilenameUtils
				.getFullPathNoEndSeparator(sourceFile));
		return ftpClient.rename(sourceFile, targetFile);
	}

	/**
	 * 
	 * <br>
	 * Created on: 2013-9-11 下午05:16:58
	 * 
	 * @param remotePath
	 * @return
	 * @throws IOException
	 */
	public FTPFile[] getFileList(String remotePath) throws IOException {
		ftpClient
				.changeWorkingDirectory(FileTools.delPathSeparator(remotePath));
		return ftpClient.listFiles(remotePath);
	}

	/**
	 * 
	 * <br>
	 * Created on: 2013-9-11 下午05:18:14
	 * 
	 * @param remotePath
	 * @param filter
	 * @return
	 * @throws IOException
	 */
	public FTPFile[] getFileList(String remotePath, FTPFileFilter filter)
			throws IOException {
		ftpClient
				.changeWorkingDirectory(FileTools.delPathSeparator(remotePath));
		return ftpClient.listFiles(remotePath, filter);
	}

	/**
	 * 
	 * <br>
	 * Created on: 2013-9-12 下午03:32:45
	 * 
	 * @param remotePath
	 * @param fileNameFix
	 *            <1>*.csv <2>ftp* <3>ftp*.csv<4>* allfile
	 * @return
	 * @throws IOException
	 */
	public FTPFile[] getFileList(String remotePath, String fileNameFix)
			throws IOException {
		ftpClient
				.changeWorkingDirectory(FileTools.delPathSeparator(remotePath));

		final String[] fixArr = fileNameFix.split("\\*", -1);
		final int len = fixArr.length;
		if (len == 1) {
			return ftpClient.listFiles(remotePath, new FTPFileFilter() {

				public boolean accept(FTPFile file) {
					return file.getName().equalsIgnoreCase(fixArr[0]);
				}
			});
		} else if (len == 2) {

			if (DataTools.isEmpty(fixArr[0]) && !DataTools.isEmpty(fixArr[1]))
				return ftpClient.listFiles(remotePath, new FTPFileFilter() {

					public boolean accept(FTPFile file) {
						return file.getName().endsWith(fixArr[1]);
					}
				});
			else if (DataTools.isEmpty(fixArr[1])
					&& !DataTools.isEmpty(fixArr[0]))
				return ftpClient.listFiles(remotePath, new FTPFileFilter() {

					public boolean accept(FTPFile file) {
						return file.getName().startsWith(fixArr[0]);
					}
				});
			else if (!DataTools.isEmpty(fixArr[1])
					&& !DataTools.isEmpty(fixArr[0]))
				return ftpClient.listFiles(remotePath, new FTPFileFilter() {

					public boolean accept(FTPFile file) {
						return file.getName().startsWith(fixArr[0])
								&& file.getName().endsWith(fixArr[1]);
					}
				});
			else
				return getFileList(remotePath);

		}

		return null;

	}
}
