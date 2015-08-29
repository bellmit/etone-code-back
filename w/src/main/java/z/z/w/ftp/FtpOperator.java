/**
 * z.z.w.ftp.FtpOperator.java
 */
package z.z.w.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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

import z.z.w.common.DataTools;
import z.z.w.common.FileInfos;
import z.z.w.common.FileTools;

/**
 * FTP Operator
 * 
 * @author Wu Zhenzhen
 * @version Nov 22, 2012 2:21:07 PM
 */
public class FtpOperator extends FileInfos {

	private String ftpServer = null;
	private int ftpPort = -1;
	private String ftpUser = null;
	private String ftpPassword = null;
	private FTPClient ftpClient = null;

	/**
	 * <br>
	 * Created on: Nov 22, 2012 2:24:01 PM
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
	 * Created on: Nov 22, 2012 2:24:25 PM
	 */
	private void init() {
		ftpClient = new FTPClient();
	}

	/**
	 * Create ftp server connection <br>
	 * Created on: Nov 22, 2012 2:27:08 PM
	 * 
	 * @throws SocketException
	 * @throws IOException
	 */
	public void createConnection() throws SocketException, IOException {

		if (ftpClient.isConnected())
			return;

		ftpClient.connect(ftpServer, ftpPort);
		ftpClient.login(ftpUser, ftpPassword);

		if (!ftpClient.isConnected())
			throw new IOException("FTP SERVER CONNECTION IS NOT CONNECTED!"
					+ toString());

		ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
	}

	/**
	 * Close ftp server connection <br>
	 * Created on: Nov 22, 2012 2:27:53 PM
	 * 
	 * @throws IOException
	 */
	public void closeConnection() throws IOException {

		if (ftpClient.isConnected()) {
			ftpClient.disconnect();
			ftpClient = null;
		}
	}

	/**
	 * Set ftp connection oper encoding <br>
	 * Created on: Nov 22, 2012 3:34:49 PM
	 * 
	 * @param encoding
	 */
	public void setEncoding(String encoding) {
		ftpClient.setControlEncoding(encoding);
	}

	/**
	 * Down load ftp server file to local <br>
	 * Created on: Nov 22, 2012 2:33:49 PM
	 * 
	 * @param remotePath
	 *            remote path
	 * @param remoteFileName
	 *            remote file name
	 * @param localPath
	 *            local path
	 * @param localFileName
	 *            local file name
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void downLoad(String remotePath, String remoteFileName,
			String localPath, String localFileName) throws IOException {

		if (!ftpClient.isConnected())
			throw new IOException("FTP SERVER CONNECTION IS NOT CONNECTED!"
					+ toString());

		if (ftpClient
				.changeWorkingDirectory(FileTools.unFormatPath(remotePath))) {

			OutputStream local = null;
			try {
				local = new FileOutputStream((new File(
						FileTools.formatPath(localPath) + localFileName)));

				ftpClient.retrieveFile(remoteFileName, local);

				ftpClient.changeToParentDirectory();

				local.flush();
				local.close();
				local = null;

			} finally {
				try {
					if (!DataTools.isEmpty(local)) {
						local.close();
						local = null;
					}
				} catch (IOException e) {
				}
			}
		} else
			throw new IOException("FTP SERVER NOT FOUND ["
					+ FileTools.unFormatPath(remotePath) + "] PATH !");

	}

	/**
	 * Down load ftp server file to local <br>
	 * Created on: Nov 22, 2012 3:44:14 PM
	 * 
	 * @param remotePath
	 * @param remoteFileName
	 * @param localFile
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void downLoad(String remotePath, String remoteFileName,
			File localFile) throws IOException {

		if (!ftpClient.isConnected())
			throw new IOException("FTP SERVER CONNECTION IS NOT CONNECTED!"
					+ toString());

		if (ftpClient
				.changeWorkingDirectory(FileTools.unFormatPath(remotePath))) {

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
						local.close();
						local = null;
					}
				} catch (IOException e) {
				}
			}
		} else
			throw new IOException("FTP SERVER NOT FOUND ["
					+ FileTools.unFormatPath(remotePath) + "] PATH !");

	}

	/**
	 * Download file to local path <br>
	 * Created on: Nov 22, 2012 2:55:37 PM
	 * 
	 * @param remoteFile
	 *            full path file name
	 * @param localPath
	 * @param localFileName
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void downLoad(String remoteFile, String localPath,
			String localFileName) throws IOException {

		if (!ftpClient.isConnected())
			throw new IOException("FTP SERVER CONNECTION IS NOT CONNECTED!"
					+ toString());

		if (ftpClient.changeWorkingDirectory((FilenameUtils
				.getFullPathNoEndSeparator(remoteFile)))) {

			OutputStream local = null;
			try {
				local = new FileOutputStream((new File(
						FileTools.formatPath(localPath) + localFileName)));

				ftpClient
						.retrieveFile(FilenameUtils.getName(remoteFile), local);

				ftpClient.changeToParentDirectory();

				local.flush();
				local.close();
				local = null;
			} finally {
				try {
					if (!DataTools.isEmpty(local)) {
						local.close();
						local = null;
					}
				} catch (IOException e) {
				}
			}
		} else
			throw new IOException("FTP SERVER NOT FOUND ["
					+ FilenameUtils.getFullPathNoEndSeparator(remoteFile)
					+ "] PATH !");
	}

	/**
	 * Down load ftp server file to local <br>
	 * Created on: Nov 22, 2012 3:45:10 PM
	 * 
	 * @param remoteFile
	 * @param localFile
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void downLoad(String remoteFile, String localFile)
			throws IOException {

		if (!ftpClient.isConnected())
			throw new IOException("FTP SERVER CONNECTION IS NOT CONNECTED!"
					+ toString());

		if (ftpClient.changeWorkingDirectory((FilenameUtils
				.getFullPathNoEndSeparator(remoteFile)))) {
			OutputStream local = null;
			try {
				local = new FileOutputStream((new File(localFile)));

				ftpClient
						.retrieveFile(FilenameUtils.getName(remoteFile), local);

				ftpClient.changeToParentDirectory();

				local.flush();
				local.close();
				local = null;
			} finally {
				try {
					if (!DataTools.isEmpty(local)) {
						local.close();
						local = null;
					}
				} catch (IOException e) {
				}
			}
		} else
			throw new IOException("FTP SERVER NOT FOUND ["
					+ FilenameUtils.getFullPathNoEndSeparator(remoteFile)
					+ "] PATH !");

	}

	/**
	 * Down load ftp server file to local <br>
	 * Created on: Nov 22, 2012 3:45:27 PM
	 * 
	 * @param remoteFile
	 * @param localFile
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void downLoad(String remoteFile, File localFile) throws IOException {

		if (!ftpClient.isConnected())
			throw new IOException("FTP SERVER CONNECTION IS NOT CONNECTED!"
					+ toString());

		if (ftpClient.changeWorkingDirectory((FilenameUtils
				.getFullPathNoEndSeparator(remoteFile)))) {
			OutputStream local = null;
			try {
				local = new FileOutputStream(((localFile)));

				ftpClient
						.retrieveFile(FilenameUtils.getName(remoteFile), local);

				local.flush();
				local.close();
				local = null;
				ftpClient.changeToParentDirectory();

			} finally {
				try {
					if (!DataTools.isEmpty(local)) {
						local.close();
						local = null;
					}
				} catch (IOException e) {
				}
			}
		} else
			throw new IOException("FTP SERVER NOT FOUND ["
					+ FilenameUtils.getFullPathNoEndSeparator(remoteFile)
					+ "] PATH !");

	}

	/**
	 * Upload local file to ftp sever <br>
	 * Created on: Nov 22, 2012 3:41:15 PM
	 * 
	 * @param remotePath
	 *            ftp server remote path
	 * @param remoteFileName
	 *            remote file name
	 * @param localPath
	 *            local path
	 * @param localFileName
	 *            local file name
	 * @throws IOException
	 */
	public void upload(String remotePath, String remoteFileName,
			String localPath, String localFileName) throws IOException {
		if (!ftpClient.isConnected())
			throw new IOException("FTP SERVER CONNECTION IS NOT CONNECTED!"
					+ toString());

		if (ftpClient
				.changeWorkingDirectory(FileTools.unFormatPath(remotePath))) {
			InputStream local = null;
			try {
				ftpClient.enterLocalPassiveMode();
				ftpClient.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);

				local = new FileInputStream(new File(
						FileTools.formatPath(localPath) + localFileName));

				ftpClient.storeFile(remoteFileName, local);

				local.close();
				local = null;

			} finally {
				try {
					if (!DataTools.isEmpty(local)) {
						local.close();
						local = null;
					}
				} catch (IOException e) {
				}
			}

		} else
			throw new IOException("FTP SERVER NOT FOUND ["
					+ FileTools.unFormatPath(remotePath) + "] PATH !");
	}

	/**
	 * Upload local file to ftp sever <br>
	 * Created on: Nov 22, 2012 3:42:59 PM
	 * 
	 * @param remotePath
	 * @param remoteFileName
	 * @param localFile
	 * @throws IOException
	 */
	public void upload(String remotePath, String remoteFileName,
			String localFile) throws IOException {
		if (!ftpClient.isConnected())
			throw new IOException("FTP SERVER CONNECTION IS NOT CONNECTED!"
					+ toString());

		if (ftpClient
				.changeWorkingDirectory(FileTools.unFormatPath(remotePath))) {
			InputStream local = null;

			try {
				ftpClient.enterLocalPassiveMode();
				ftpClient.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);

				local = new FileInputStream(new File(localFile));

				ftpClient.storeFile(remoteFileName, local);

				local.close();
				local = null;
			} finally {
				if (!DataTools.isEmpty(local)) {
					local.close();
					local = null;
				}
			}

		} else
			throw new IOException("FTP SERVER NOT FOUND ["
					+ FileTools.unFormatPath(remotePath) + "] PATH !");
	}

	/**
	 * Upload local file to ftp sever <br>
	 * Created on: Nov 22, 2012 3:43:22 PM
	 * 
	 * @param remotePath
	 * @param remoteFileName
	 * @param localFile
	 * @throws IOException
	 */
	public void upload(String remotePath, String remoteFileName, File localFile)
			throws IOException {
		if (!ftpClient.isConnected())
			throw new IOException("FTP SERVER CONNECTION IS NOT CONNECTED!"
					+ toString());

		if (ftpClient
				.changeWorkingDirectory(FileTools.unFormatPath(remotePath))) {

			InputStream local = null;

			try {
				ftpClient.enterLocalPassiveMode();
				ftpClient.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);

				local = new FileInputStream((localFile));

				ftpClient.storeFile(remoteFileName, local);

				local.close();
				local = null;
			} finally {
				if (!DataTools.isEmpty(local)) {
					local.close();
					local = null;
				}
			}
		} else
			throw new IOException("FTP SERVER NOT FOUND ["
					+ FileTools.unFormatPath(remotePath) + "] PATH !");
	}

	/**
	 * Upload local file to ftp sever <br>
	 * <br>
	 * Created on: Nov 22, 2012 3:53:50 PM
	 * 
	 * @param remoteFile
	 * @param localFile
	 * @throws IOException
	 */
	public void upload(String remoteFile, File localFile) throws IOException {
		if (!ftpClient.isConnected())
			throw new IOException("FTP SERVER CONNECTION IS NOT CONNECTED!"
					+ toString());

		if (ftpClient.changeWorkingDirectory((FilenameUtils
				.getFullPathNoEndSeparator(remoteFile)))) {

			InputStream local = null;

			try {
				ftpClient.enterLocalPassiveMode();
				ftpClient.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);

				local = new FileInputStream((localFile));

				ftpClient.storeFile(FilenameUtils.getName(remoteFile), local);

				local.close();
				local = null;
			} finally {
				if (!DataTools.isEmpty(local)) {
					local.close();
					local = null;
				}
			}
		} else
			throw new IOException("FTP SERVER NOT FOUND ["
					+ FilenameUtils.getFullPathNoEndSeparator(remoteFile)
					+ "] PATH !");
	}

	/**
	 * Create remote path in ftp server <br>
	 * Created on: Nov 22, 2012 2:37:31 PM
	 * 
	 * @param remotePath
	 *            ftp server remote path
	 * @param newPathName
	 *            create new path name
	 * @throws IOException
	 */
	public boolean createDirInServer(String remotePath, String newPathName)
			throws IOException {

		if (!ftpClient.isConnected())
			throw new IOException("FTP SERVER CONNECTION IS NOT CONNECTED!"
					+ toString());

		if (ftpClient
				.changeWorkingDirectory(FileTools.unFormatPath(remotePath))) {
			return ftpClient.makeDirectory(newPathName);
		} else
			throw new IOException("FTP SERVER NOT FOUND ["
					+ FileTools.unFormatPath(remotePath) + "] PATH !");

	}

	/**
	 * Delete ftp server file <br>
	 * Created on: Nov 22, 2012 2:38:55 PM
	 * 
	 * @param remotePath
	 *            ftp server path
	 * @param fileName
	 *            delete fileName
	 * @throws IOException
	 */
	public boolean deleteFile(String remotePath, String fileName)
			throws IOException {

		if (!ftpClient.isConnected())
			throw new IOException("FTP SERVER CONNECTION IS NOT CONNECTED!"
					+ toString());

		if (ftpClient
				.changeWorkingDirectory(FileTools.unFormatPath(remotePath))) {
			return ftpClient.deleteFile(fileName);
		} else
			throw new IOException("FTP SERVER NOT FOUND ["
					+ FileTools.unFormatPath(remotePath) + "] PATH !");
	}

	/**
	 * Delete ftp server file <br>
	 * Created on: Nov 22, 2012 2:57:00 PM
	 * 
	 * @param remoteFile
	 *            full path file name
	 * @throws IOException
	 */
	public boolean deleteFile(String remoteFile) throws IOException {

		if (!ftpClient.isConnected())
			throw new IOException("FTP SERVER CONNECTION IS NOT CONNECTED!"
					+ toString());

		if (ftpClient.changeWorkingDirectory((FilenameUtils
				.getFullPathNoEndSeparator(remoteFile)))) {
			return ftpClient.deleteFile(FilenameUtils.getName(remoteFile));
		} else
			throw new IOException("FTP SERVER NOT FOUND ["
					+ FilenameUtils.getFullPathNoEndSeparator(remoteFile)
					+ "] PATH !");
	}

	/**
	 * Rename & move source file to dest file <br>
	 * Created on: Nov 22, 2012 2:42:31 PM
	 * 
	 * @param remotePath
	 *            source file path
	 * @param sourceFileName
	 *            soruce file name
	 * @param destRemotePath
	 *            dest file path
	 * @param destRemoteFileName
	 *            dest file name
	 * @throws IOException
	 */
	public boolean renameFile(String remotePath, String sourceFileName,
			String destRemotePath, String destRemoteFileName)
			throws IOException {

		if (!ftpClient.isConnected())
			throw new IOException("FTP SERVER CONNECTION IS NOT CONNECTED!"
					+ toString());

		if (ftpClient.changeWorkingDirectory(FileTools.formatPath(remotePath))) {

			return ftpClient
					.rename((FileTools.formatPath(remotePath) + sourceFileName),
							(FileTools.formatPath(destRemotePath) + destRemoteFileName));
		} else
			throw new IOException("FTP SERVER NOT FOUND ["
					+ FileTools.unFormatPath(remotePath) + "] PATH !");

	}

	/**
	 * Rename file use replacement to regex <br>
	 * renameing to renameTest <br>
	 * Created on: Nov 22, 2012 2:47:41 PM
	 * 
	 * @param remoteFile
	 *            file name :renameing
	 * @param regex
	 *            prepare replace string : ing
	 * @param replacement
	 *            new string : Test
	 * @throws IOException
	 */
	public boolean renameFile(String remoteFile, String regex,
			String replacement) throws IOException {

		if (!ftpClient.isConnected())
			throw new IOException("FTP SERVER CONNECTION IS NOT CONNECTED!"
					+ toString());

		if (ftpClient.changeWorkingDirectory((FilenameUtils
				.getFullPathNoEndSeparator(remoteFile)))) {

			String targetFileName = remoteFile.replaceAll(regex, replacement);

			return ftpClient.rename(remoteFile, targetFileName);
		} else
			throw new IOException("FTP SERVER NOT FOUND ["
					+ FilenameUtils.getFullPathNoEndSeparator(remoteFile)
					+ "] PATH !");

	}

	/**
	 * Get ftp server remote path file lists <br>
	 * Created on: Nov 22, 2012 2:53:42 PM
	 * 
	 * @param remotePath
	 * @return
	 * @throws IOException
	 */
	public FTPFile[] getFileList(String remotePath) throws IOException {

		if (!ftpClient.isConnected())
			throw new IOException("FTP SERVER CONNECTION IS NOT CONNECTED!"
					+ toString());

		if (ftpClient
				.changeWorkingDirectory(FileTools.unFormatPath(remotePath))) {
			return ftpClient.listFiles(remotePath);
		} else
			throw new IOException("FTP SERVER NOT FOUND ["
					+ FileTools.unFormatPath(remotePath) + "] PATH !");
	}

	/**
	 * Get ftp server remote path file list by filter <br>
	 * Created on: Nov 22, 2012 3:00:01 PM
	 * 
	 * @param remotePath
	 * @param filter
	 * @return
	 * @throws IOException
	 */
	public FTPFile[] getFileList(String remotePath, FTPFileFilter filter)
			throws IOException {

		if (!ftpClient.isConnected())
			throw new IOException("FTP SERVER CONNECTION IS NOT CONNECTED!"
					+ toString());

		if (ftpClient
				.changeWorkingDirectory(FileTools.unFormatPath(remotePath))) {
			return ftpClient.listFiles(remotePath, filter);
		} else
			throw new IOException("FTP SERVER NOT FOUND ["
					+ FileTools.unFormatPath(remotePath) + "] PATH !");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "FtpOperator [ftpServer=" + ftpServer + ", ftpPort=" + ftpPort
				+ ", ftpUser=" + ftpUser + ", ftpPassword=" + ftpPassword
				+ ", ftpClient=" + ftpClient + ", toString()="
				+ super.toString() + "]";
	}

}
