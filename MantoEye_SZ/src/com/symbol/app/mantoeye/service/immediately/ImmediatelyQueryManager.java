package com.symbol.app.mantoeye.service.immediately;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.symbol.app.mantoeye.dao.immediately.ImmediatelyQueryDAO;
import com.symbol.app.mantoeye.dto.flush.ImmediatelyQueryEntity;
import com.symbol.app.mantoeye.dto.flush.TableColumnMapEntity;
import com.symbol.app.mantoeye.dto.flush.TableMapEntity;
import com.symbol.app.mantoeye.web.action.FtpClientOperator;
import com.symbol.wp.core.util.PropertiesUtil;
import com.symbol.wp.modules.orm.Page;

//Spring Service Bean的标识.
@Service
// 默认将类中的所有函数纳入事务管理.
@Transactional
public class ImmediatelyQueryManager {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private ImmediatelyQueryDAO immediatelyQueryDAO;

	public static Map<String, Integer> sortMap;

	/**
	 * 查询所有表
	 * 
	 * @return
	 */
	public List<TableMapEntity> queryAllTabke() {

		return immediatelyQueryDAO.queryAllTabke();

	}

	/**
	 * 查询所有表字段
	 * 
	 * @return
	 */
	public List<TableColumnMapEntity> queryColumnMap() {

		return immediatelyQueryDAO.queryColumnMap();

	}

	/**
	 * 根据表ID查询所有表字段
	 * 
	 * @return
	 */
	public List<TableColumnMapEntity> queryColumnMapByTableId(int tableId) {
		String compfield;
		TableColumnMapEntity entity;
		List<TableColumnMapEntity> compList;
		List<TableColumnMapEntity> columnList = immediatelyQueryDAO
				.queryColumnMapByTableId(tableId);
		if (columnList != null && columnList.size() > 0) {

			// 对字段进行排序
			TreeMap<Integer, TableColumnMapEntity> cmap = new TreeMap<Integer, TableColumnMapEntity>();
			if (sortMap == null) {
				getComparatorMap();
			}
			int otherfieldct = 100;
			for (int i = 0; i < columnList.size(); i++) {
				entity = columnList.get(i);
				// 通过表字段设置排序方式
				compfield = entity.getVcColumnName();
				if (sortMap.get(compfield) != null) {
					cmap.put(sortMap.get(compfield), entity);
				} else {
					cmap.put(otherfieldct++, entity);
				}

			}
			compList = new ArrayList<TableColumnMapEntity>();
			Iterator<Entry<Integer, TableColumnMapEntity>> it = cmap.entrySet()
					.iterator();
			while (it.hasNext()) {
				compList.add(it.next().getValue());
			}
		} else {
			compList = columnList;
		}
		return compList;
	}

	public void getComparatorMap() {
		// 设置排序方式
		sortMap = new HashMap<String, Integer>();
		// 时间排最前
		sortMap.put("intYear", 1);
		sortMap.put("intMonth", 2);
		sortMap.put("intDay", 3);
		sortMap.put("intHour", 4);
		sortMap.put("intWeek", 5);
		sortMap.put("intRaitype", 6);

		// 流量
		sortMap.put("nmAppUpFlush", 10);
		sortMap.put("nmAppDownFlush", 11);
		sortMap.put("nmFlush", 12);
		sortMap.put("nmAveFlush", 13);

		// 用户数
		sortMap.put("nmUsers", 20);

		sortMap.put("nmUpCounts", 30);
		sortMap.put("nmDownCounts", 31);
		sortMap.put("nmTotalCounts", 32);
		sortMap.put("nmCounts", 33);

		// 访问次数
		sortMap.put("nmActiveCounts", 40);
		sortMap.put("nmVisitCounts", 41);

		sortMap.put("dtStatTime", 999);
	}

	/**
	 * 根据生成的SQL来查询数据 分导出和分页
	 * 
	 * @param page
	 * @param sql
	 * @param export
	 * @return
	 */
	public Page<ImmediatelyQueryEntity> queryBySql(final Page page, String sql,
			boolean export) {
		return immediatelyQueryDAO.queryBySql(page, sql, export);
	}

	/**
	 *大數據導出到文件
	 * 
	 * @param sql
	 *            执行查询的SQL语句
	 * @param localDir
	 *            WEB服务器的download目录，用于FTP将DB服务器中的文件下载到此目录
	 * @param iqServerPath
	 *            DB服务器目录，用于存储SQL执行查询数据到文件的TEMP目录
	 * @param queryTime
	 *            查询时间，用于构造不同文件名，以便保证多用户同时下载时造成文件混乱
	 */
	public void queryBySql(String sql, String localDir, String iqServerPath,
			String queryTime, String ninkName) {
		immediatelyQueryDAO.queryBySql(sql, queryTime, iqServerPath);
		downloadFile(localDir, iqServerPath, queryTime);
		// appendTitleToFile(localDir, queryTime, ninkName);
	}

	private void appendTitleToFile(String localDir, String queryTime,
			String ninkName) {

		String fileName = "queryResult-" + queryTime + ".csv";
		String newFileName = "大数据导出-" + queryTime + ".csv";
		String readfile = localDir + fileName;
		String writefile = localDir + newFileName;

		// String title = new String("測試標題1,測試標題2,測試標題3,測試標題4,測試標題5,測試標題6");
		String title = ninkName;
		FileReader fr = null;
		FileWriter fw = null;
		BufferedReader br = null;
		BufferedWriter bw = null;
		try {
			fr = new FileReader(readfile);
			fw = new FileWriter(writefile);

			br = new BufferedReader(fr);
			bw = new BufferedWriter(fw);

			int i = 0;
			String temp = br.readLine();
			while (temp != null) {
				if (i == 0) {
					bw.write(title);
					bw.newLine();
					bw.flush();
				}
				bw.write(temp);
				bw.newLine();
				temp = br.readLine();
				i += 1;
				bw.flush();
			}
			logger.info("大数据导出文件添加标题操作完成。。。。");
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		} finally {
			if (null != bw) {
				try {
					bw.close();
				} catch (IOException e) {
					logger.error(e.getMessage());
				}
			}

			if (null != br) {
				try {
					br.close();
				} catch (IOException e) {
					logger.error(e.getMessage());
				}
			}

			if (null != fw) {
				try {
					fw.close();
				} catch (IOException e) {
					logger.error(e.getMessage());
				}
			}

			if (null != fr) {
				try {
					fr.close();
				} catch (IOException e) {
					logger.error(e.getMessage());
				}
			}
		}

	}

	private void downloadFile(String localDir, String iqServerPath,
			String queryTime) {
		try {
			String fileName = "queryResult-" + queryTime + ".csv";
			// String localFileName = "queryResult-" + queryTime + ".csv";
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

	public long getTotalCount(String sql) {
		return immediatelyQueryDAO.getTotalCount(sql);
	}

	public void deleteTempFile(String localDir, String iqServerPath,
			String queryTime) {
		logger.info("Download Success and Deleteing temp files...");
		deleteRemoteFile(iqServerPath, queryTime);
		// String file = localDir + "queryResult-" + queryTime + ".csv";
		String file = localDir + "大数据导出-" + queryTime + ".csv";
		String file2 = localDir + "queryResult-" + queryTime + ".csv";
		(new File(file)).delete();
		(new File(file2)).delete();
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
}
