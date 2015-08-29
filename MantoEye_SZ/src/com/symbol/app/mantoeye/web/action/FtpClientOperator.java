package com.symbol.app.mantoeye.web.action;

import it.sauronsoftware.ftp4j.FTPAbortedException;
import it.sauronsoftware.ftp4j.FTPClient;
import it.sauronsoftware.ftp4j.FTPDataTransferException;
import it.sauronsoftware.ftp4j.FTPException;
import it.sauronsoftware.ftp4j.FTPFile;
import it.sauronsoftware.ftp4j.FTPIllegalReplyException;
import it.sauronsoftware.ftp4j.FTPListParseException;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * FTP操作，提供与服务器交互的操作
 * 
 * @author ZhangQingyi
 * @date 2009-9-14
 */
public class FtpClientOperator {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	// 服务器名��������
	private String ftpServer = null;
	// 端口�˿�
	private int ftpPort = -1;
	// 用户名�û���
	private String ftpUser = null;
	// 密码����
	private String ftpPassword = null;
	// client
	private FTPClient ftpClient = null;

	public FtpClientOperator() {
		init();
	}

	public FtpClientOperator(String ftpServer, int ftpPort, String ftpUser,
			String ftpPassword) {
		this.ftpServer = ftpServer;
		this.ftpPort = ftpPort;
		this.ftpUser = ftpUser;
		this.ftpPassword = ftpPassword;

		init();
	}

	public void init() {
		ftpClient = new FTPClient();
	}

	/**
	 * 连接FTP服务器端
	 */
	public void connectFtpServer() {
		try {
			ftpClient.connect(ftpServer, ftpPort);
			ftpClient.login(ftpUser, ftpPassword);

		} catch (IOException e) {
			logger.error(e.getMessage());
		} catch (IllegalStateException e) {
			logger.error(e.getMessage());
		} catch (FTPIllegalReplyException e) {
			logger.error(e.getMessage());
		} catch (FTPException e) {
			logger.error(e.getMessage());
		}
	}

	/**
	 * 设置FTP的传输方式为自动
	 */
	public void setAutoTransferType() {
		ftpClient.setType(FTPClient.TYPE_AUTO);
	}

	/**
	 * 设置FTP的传输方式为二进制
	 */
	public void setBinaryTransferType() {
		ftpClient.setType(FTPClient.TYPE_BINARY);
	}

	public void setTransferCharset(String charset) {
		ftpClient.setCharset(charset);
	}

	public void setTransferCharsetUTF8() {
		setTransferCharset("UTF-8");
	}

	public void setTransferCharsetGBK() {
		setTransferCharset("GBK");
	}

	public void setTransferCharsetGB2312() {
		setTransferCharset("GB2312");
	}

	/**
	 * public void setTransferCharsetBIG() { setTransferCharset("BIG"); }
	 */

	/**
	 * 设置FTP的传输方式为普通文本模式
	 */
	public void setTextualTransferType() {
		ftpClient.setType(FTPClient.TYPE_TEXTUAL);
	}

	/**
	 * 断开与FTP服务器的连接�Ͽ���FTP�������l��
	 */
	public void closeConnect() {
		try {
			ftpClient.disconnect(true);
		} catch (IOException e) {
			logger.error(e.getMessage());
		} catch (IllegalStateException e) {
			logger.error(e.getMessage());
		} catch (FTPIllegalReplyException e) {
			logger.error(e.getMessage());
		} catch (FTPException e) {
			logger.error(e.getMessage());
		}
	}

	/**
	 * 上传文件到FTP服务器，上存的文件名和原文件名一样
	 * 
	 * @param localDir
	 *            本地文件所在的目录
	 * @param localFileName
	 *            本地要上传的文件的文件名
	 * @param remoteDir
	 *            远程的上存目录
	 */
	public void upload(String localDir, String localFileName, String remoteDir) {
		File localFile = null;
		String sp = "\\";
		try {
			ftpClient.changeDirectory(remoteDir);

			// if (!localDir.endsWith("\\"))
			// localDir = localDir + "\\";
			if (localDir.lastIndexOf("/") != -1) {
				sp = "/";
			}
			if (!localDir.endsWith(sp))
				localDir = localDir + sp;

			localFile = new File(localDir + localFileName);

			ftpClient.upload(localFile);
		} catch (IllegalStateException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		} catch (FTPIllegalReplyException e) {
			logger.error(e.getMessage());
		} catch (FTPException e) {
			logger.error(e.getMessage());
		} catch (FTPDataTransferException e) {
			logger.error(e.getMessage());
		} catch (FTPAbortedException e) {
			logger.error(e.getMessage());
		}

	}

	/**
	 * 下载远程FTP服务器的文件到本地
	 * 
	 * @param remoteDir
	 *            远程的目录
	 * @param remoteFileName
	 *            远程要下载的文件的文件名
	 * @param localDir
	 *            本地存文件的目录
	 * @param localFileName
	 *            本地存文件的名字
	 */
	public void download(String remoteDir, String remoteFileName,
			String localDir, String localFileName) {
		File localFile = null;
		logger.info("remoteDir:" + remoteDir + "    remoteFileName:"
				+ remoteFileName + "    localDir:" + localDir
				+ "    localFileName:" + localFileName);
		String sp = "\\";
		try {
			ftpClient.changeDirectory(remoteDir);

			if (localDir.lastIndexOf("/") != -1) {
				sp = "/";
			}
			if (!localDir.endsWith(sp))
				localDir = localDir + sp;

			localFile = new File(localDir + localFileName);

			ftpClient.download(remoteFileName, localFile);
		} catch (IllegalStateException e) {
			logger.error(e.getMessage());

		} catch (IOException e) {
			logger.error(e.getMessage());
		} catch (FTPIllegalReplyException e) {
			logger.error(e.getMessage());
		} catch (FTPException e) {
			logger.error(e.getMessage());
		} catch (FTPDataTransferException e) {
			logger.error(e.getMessage());
		} catch (FTPAbortedException e) {
			logger.error(e.getMessage());
		}
	}

	/**
	 * 在FTP远程机上，建立一个目录
	 * 
	 * @param remotePath
	 *            远程机的路径
	 * @param dirName
	 *            目录名
	 */
	public void initDir(String dirName) {

		/**
		 * �жϸ�Ŀ¼�Ƿ����
		 */
		try {
			ftpClient.createDirectory(dirName);
		} catch (IllegalStateException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		} catch (FTPIllegalReplyException e) {
			logger.error(e.getMessage());
		} catch (FTPException e) {
			logger.error(e.getMessage());
		}
	}

	/**
	 * 在FTP远程机上，建立一个目录
	 * 
	 * @param remotePath
	 *            远程机的路径
	 * @param dirName
	 *            目录名
	 */
	public void mkDir(String remotePath, String dirName) {
		try {
			ftpClient.changeDirectory(remotePath);

			ftpClient.createDirectory(dirName);
		} catch (IllegalStateException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		} catch (FTPIllegalReplyException e) {
			logger.error(e.getMessage());
		} catch (FTPException e) {
			logger.error(e.getMessage());
		}

	}

	/**
	 * 在FTP远程机上，删除文件
	 * 
	 * @param remotePath
	 *            远程机的路径
	 * @param fileName
	 *            文件名
	 */
	public void deleteFile(String remotePath, String fileName) {
		try {
			ftpClient.changeDirectory(remotePath);

			ftpClient.deleteFile(fileName);
		} catch (IllegalStateException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		} catch (FTPIllegalReplyException e) {
			logger.error(e.getMessage());
		} catch (FTPException e) {
			logger.error(e.getMessage());
		}
	}

	/**
	 * 在FTP远程机上，移动文件
	 * 
	 * @param srcPath
	 *            源数据
	 * @param srcFileName
	 *            源文件名
	 * @param targetPath
	 *            目标路路径
	 * @param targetFileName
	 *            目标文件名
	 */
	public void moveFile(String srcPath, String srcFileName, String destPath,
			String targetFileName) {
		try {
			ftpClient.changeDirectory(srcPath);

			if (!destPath.endsWith("/"))
				destPath = destPath + "/";

			// initDir(destPath);

			ftpClient.rename(srcFileName, destPath + targetFileName);
		} catch (IllegalStateException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		} catch (FTPIllegalReplyException e) {
			logger.error(e.getMessage());
		} catch (FTPException e) {
			logger.error(e.getMessage());
		}

	}

	/**
	 * 
	 * @param srcPath
	 * @param srcFileName
	 * @param targetPath
	 * @param targetFileName
	 */
	public void renameFile(String regex, String replacement, String srcFileName) {
		try {
			String targetFileName = srcFileName.replaceAll(regex, replacement);
			ftpClient.rename(srcFileName, targetFileName);

		} catch (IllegalStateException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		} catch (FTPIllegalReplyException e) {
			logger.error(e.getMessage());
		} catch (FTPException e) {
			logger.error(e.getMessage());
		}

	}

	/**
	 * 在远程机,取得该目录下的所有文件
	 * 
	 * @param remotePath
	 *            远程机目录
	 * @param filterStr
	 *            过滤的字符串,如"*","*.jpg"
	 * @return 结果
	 */
	public FTPFile[] getFileList(String remotePath, String filterStr) {
		FTPFile[] result = null;

		try {
			ftpClient.changeDirectory(remotePath);

			result = ftpClient.list(filterStr);
		} catch (IllegalStateException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		} catch (FTPIllegalReplyException e) {
			logger.error(e.getMessage());
		} catch (FTPException e) {
			logger.error(e.getMessage());
		} catch (FTPDataTransferException e) {
			logger.error(e.getMessage());
		} catch (FTPAbortedException e) {
			logger.error(e.getMessage());
		} catch (FTPListParseException e) {
			logger.error(e.getMessage());
		}

		return result;
	}

	/*
	 * public static void main(String args[]){ String
	 * path="/opt/songzhiyou/song.txt"; String
	 * fileName=path.substring(path.lastIndexOf("/")+1, path.length()-1); String
	 * remoteDir=path.substring(0, path.lastIndexOf("/"));
	 * 
	 * logger.info("dddddddddddddd="+remoteDir);
	 * logger.info("dddddddddddddd="+fileName); }
	 */
}
