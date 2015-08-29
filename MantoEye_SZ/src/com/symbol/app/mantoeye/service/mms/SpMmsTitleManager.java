package com.symbol.app.mantoeye.service.mms;

import java.io.File;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.symbol.app.mantoeye.dao.mms.SpMmsTitleDAO;
import com.symbol.app.mantoeye.dto.flush.CommonFlush;
import com.symbol.app.mantoeye.web.action.FtpClientOperator;
import com.symbol.wp.core.util.PropertiesUtil;
import com.symbol.wp.modules.orm.Page;

@Service
@Transactional
public class SpMmsTitleManager {

	private final Logger logger = LoggerFactory
			.getLogger(SpMmsTitleManager.class);
	@Autowired
	private SpMmsTitleDAO spMmsTitleDAO;

	/**
	 * 分页查询
	 */
	public Page<CommonFlush> query(final Page page, int isTD, int timeLevel,
			String sTime, String eTime, String mms_title_search) {
		return spMmsTitleDAO.query(page, isTD, timeLevel, sTime, eTime,
				mms_title_search);
	}

	public List<CommonFlush> listData(int isTD, int timeLevel, String sTime,
			String eTime, String mms_title_search,Page page) {
		return spMmsTitleDAO.listData(isTD, timeLevel, sTime, eTime,
				mms_title_search,page);
	}

	/**
	 * 获取SP彩信主题集合(按主题时段统计)
	 */
	public List<String> listMmsTitle(int isTD, int timeLevel, String sTime,
			String eTime, String like_mms_title_search) {
		return spMmsTitleDAO.listMmsTitle(isTD, timeLevel, sTime, eTime,
				like_mms_title_search);
	}

	public long getTotalCount(int isTD, int timeLevel, String sTime,
			String eTime, String mms_title_search) {
		return spMmsTitleDAO.getTotalCount(isTD, timeLevel, sTime, eTime,
				mms_title_search);
	}

	public void queryBySql(int isTD, int timeLevel, String sTime, String eTime,
			String mmsTitleSearch, String localDir, String iqServerPath,
			String queryTime) {
		spMmsTitleDAO.queryBySql(isTD, timeLevel, sTime, eTime, mmsTitleSearch,
				queryTime, iqServerPath);
		downloadFile(localDir, iqServerPath, queryTime);
	}

	private void downloadFile(String localDir, String iqServerPath,
			String queryTime) {
		try {
			String fileName = "queryResult-" + queryTime + ".csv";
			String localFileName = "大数据导出-" + queryTime + ".csv";
			String serverIpOut = PropertiesUtil.getInstance().getProperty(
					"iqIp");
			String portStr = PropertiesUtil.getInstance().getProperty("iqPort");
			int port = Integer.parseInt(portStr);
			String user = PropertiesUtil.getInstance().getProperty(
					"iqLoginUser");
			String passwd = PropertiesUtil.getInstance()
					.getProperty("iqPasswd");
			FtpClientOperator ftp = new FtpClientOperator(serverIpOut, port,
					user, passwd);
			ftp.connectFtpServer();
			ftp.setBinaryTransferType();
			ftp.setTransferCharsetUTF8();
			ftp.download(iqServerPath, fileName, localDir, localFileName);
			ftp.closeConnect();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	private void deleteRemoteFile(String remoteDir, String queryTime) {
		try {
			String fileName = "queryResult-" + queryTime + ".csv";
			String serverIpOut = PropertiesUtil.getInstance().getProperty(
					"iqIp");
			String portStr = PropertiesUtil.getInstance().getProperty("iqPort");
			int port = Integer.parseInt(portStr);
			String user = PropertiesUtil.getInstance().getProperty(
					"iqLoginUser");
			String passwd = PropertiesUtil.getInstance()
					.getProperty("iqPasswd");
			FtpClientOperator ftp = new FtpClientOperator(serverIpOut, port,
					user, passwd);
			ftp.connectFtpServer();
			ftp.setBinaryTransferType();
			ftp.setTransferCharsetUTF8();
			ftp.deleteFile(remoteDir, fileName);
			ftp.closeConnect();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	public void deleteTempFile(String localDir, String iqServerPath,
			String queryTime) {
		logger.info("下载成功，开始删除任何产生的临时文件。。。。");
		deleteRemoteFile(iqServerPath, queryTime);
		String file = localDir + "大数据导出-" + queryTime + ".csv";
		(new File(file)).delete();
	}
}
