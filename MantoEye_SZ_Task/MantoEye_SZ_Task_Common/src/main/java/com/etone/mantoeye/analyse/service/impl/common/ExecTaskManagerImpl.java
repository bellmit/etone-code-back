/**
 *  com.etone.mantoeye.service.impl.common.MantoEye_SZ_Task_Common 
 */
package com.etone.mantoeye.analyse.service.impl.common;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.myhkzhen.dao.core.IExecDbOperateDao;
import org.myhkzhen.dao.core.impl.write.WriteExecDaoImpl;
import org.myhkzhen.util.date.DateTimeUtils;
import org.myhkzhen.util.file.FileUtil;
import org.myhkzhen.util.ftp.FtpClientOperator;

import com.etone.mantoeye.analyse.domain.AnalyseParam;
import com.etone.mantoeye.analyse.domain.CurrentTask;
import com.etone.mantoeye.analyse.domain.FactTableOperation;
import com.etone.mantoeye.analyse.service.common.IExecTaskManager;
import com.etone.mantoeye.analyse.util.constant.Constant;

/**
 * @author Wu Zhenzhen
 * @version Apr 25, 2012 11:31:21 AM
 */
public class ExecTaskManagerImpl implements IExecTaskManager {

	// private static final Logger logger = LoggerFactory
	// .getLogger(CommonTaskManagerImpl.class);

	/**
	 * 寫數據庫連接操作session
	 */
	private IExecDbOperateDao execDbOperateDao = null;

	/**
	 * 
	 */
	public ExecTaskManagerImpl() {
		execDbOperateDao = new WriteExecDaoImpl();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.etone.mantoeye.analyse.service.common.ICommonTaskManager#initTaskList
	 * ()
	 */
	public int initTaskList() throws SQLException {
		// 判斷表是否鎖
		// while (tableIsLocks("tbTask")) {
		// try {
		// logger.warn("Table [initTaskList.tbTask] locks , waiting...");
		// Thread.sleep(6000);
		// } catch (InterruptedException e) {
		// // Ignore
		// }
		// }
		return execDbOperateDao.update("initTaskList");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.etone.mantoeye.analyse.service.common.ICommonTaskManager#
	 * createHourTaskList()
	 */
	public void createHourTaskList() throws SQLException {

		// 查詢當前還未生成任務列表的小時事實表
		List<FactTableOperation> hourList = getFactTableListCreatHour();

		if (null != hourList && !hourList.isEmpty()) {
			for (FactTableOperation fact : hourList) {
				// for (int i = 0; i < hourList.size(); i++) {
				// logger.debug("Hour table list --> [{}] and running -->[{}].",
				// hourList.size(), i);
				// FactTableOperation fact = hourList.get(i);
				logger.debug("Fact table name is [{}].", fact.getVcTableName());

				Long count = 0l;

				try {
					// if (!StringUtils.isBlank(StringUtils
					// .trimToEmpty(hourTempUserTableName))) {
					// 開啟事務，下面兩步同一個事務中完成，如果有失敗，則回滾
					execDbOperateDao.startTransaction();
					count = getFactTableTaskInsertCount(fact.getIntFtbOid());

					logger.info("Fact table [{}] create hour task [{}].",
							fact.getVcTableName(), count);

					if (count.intValue() > 0) {

						FactTableOperation factTableOperation = new FactTableOperation();
						factTableOperation.setIntTaskNum(count);
						factTableOperation.setDtTaskCreateTime(DateTimeUtils
								.getCurrentDate());
						factTableOperation.setIntFtbOid(fact.getIntFtbOid());

						// 判斷表是否鎖
						// while (tableIsLocks("tbFactTableOperation")) {
						// try {
						// logger.warn("7--->Table [createHourTaskList.tbFactTableOperation] locks , waiting...");
						// Thread.sleep(6000);
						// } catch (InterruptedException e) {
						// // Ignore
						// }
						// }
						execDbOperateDao.update("updateFactTableByTaskCreated",
								factTableOperation);
						logger.debug("Fact table [{}] updated task status.",
								factTableOperation);
						// }else{
						// //改回事實表日誌中任務執行狀態
						// FactTableOperation factTableOperation = new
						// FactTableOperation();
						// factTableOperation.setIntTaskNum(count);
						// factTableOperation.setDtTaskCreateTime(DateTimeUtils
						// .getCurrentDate());
						// factTableOperation.setIntFtbOid(fact.getIntFtbOid());
						//
						// updateFactTableStatus("updateFactTableByTaskCreated",
						// factTableOperation);
						// logger.debug("Fact table [{}] updated task status.");
					}
					// }

					execDbOperateDao.commitTransaction();
				} finally {
					execDbOperateDao.endTransaction();
				}

			}
		}
	}
	/**
	 * 刪除臨時小時用戶文件 <br>
	 * Created on: May 11, 2012 4:10:36 PM
	 * 
	 * @param file
	 */
	private void deleteLoadedFile(String fileName) {
		String file = Constant.DEFAULT_DATA_TEMP_PATH + fileName;
		if (StringUtils.equalsIgnoreCase(Constant.NFS, Constant.TRANS_DATATYPE)) {
			logger.debug("File start delete in nfs trans system.");
			deleteNFSFile(file);
		} else {
			logger.debug("File start delete in ftp trans system.");
			deleteFTPFile(file);
		}
	}

	/**
	 * ftp傳送 <br>
	 * Created on: May 9, 2012 10:02:11 AM
	 * 
	 * @param file
	 */
	private void deleteFTPFile(String fileName) {
		FtpClientOperator ftp = new FtpClientOperator(
				Constant.DEFAULT_FTP_SERVER, Constant.DEFAULT_FTP_PORT,
				Constant.DEFAULT_FTP_USER, Constant.DEFAULT_FTP_PASSWORD);

		if (!ftp.connectFtpServer()) {
			logger.error("执行準備Load數據后刪除FTP服務器上文件时,报错:FTP服務器[{}]無法被用戶[{}]連接.",
					new Object[]{Constant.DEFAULT_FTP_SERVER,
							Constant.DEFAULT_FTP_USER});
		}

		ftp.setBinaryTransferType();
		ftp.setTransferCharsetUTF8();

		if (!ftp.deleteFile(Constant.DEFAULT_DATA_TEMP_PATH, fileName))
			logger.warn("Delete ftp file [{}] error.",
					Constant.DEFAULT_DATA_TEMP_PATH, fileName);

		ftp.closeConnect();
	}

	/**
	 * nfs傳送 <br>
	 * Created on: May 9, 2012 10:01:47 AM
	 * 
	 * @param file
	 */
	private void deleteNFSFile(String file) {
		if (FileUtil.deleteFile(file))
			logger.info("File [{}] delete.", file);
	}
	/**
	 * 添加小時臨時用戶數統計方法 <br>
	 * Created on: May 11, 2012 10:14:09 AM
	 * 
	 * @param vcFactTableName
	 * @throws SQLException
	 */
	private String createHourTempUsers(String vcFactTableName)
			throws SQLException {
		logger.debug("Check current hour temp user table exists or not.");
		String hourTempUserTableName = "";
		try {
			// execDbOperateDao.startTransaction();

			// 先個更新要執行統計任務的事實表日誌的任務狀態，2，執行中
			FactTableOperation fact = new FactTableOperation();
			fact.setVcTableName(vcFactTableName);
			fact.setChTaskCreated("4");
			// 判斷表是否鎖
			// while (tableIsLocks("tbFactTableOperation")) {
			// try {
			// logger.warn("2---->Table [createHourTempUsers.tbFactTableOperation] locks , waiting...");
			// Thread.sleep(6000);
			// } catch (InterruptedException e) {
			// // Ignore
			// }
			// }
			execDbOperateDao.update("updateHourTempUsersFactTableStatus", fact);
			logger.debug("Fact table [{}] updated task status.",
					vcFactTableName);

			// 建立小時臨時表
			if (StringUtils.containsOnly("ftbGnAppData", vcFactTableName)) {

				hourTempUserTableName = "temp_" + vcFactTableName;
				logger.debug("App Data . Start create temp table --> [{}].",
						hourTempUserTableName);

				execDbOperateDao.update("createHourTempUserTable",
						hourTempUserTableName);
				logger.info("Create hour temp user table [{}].",
						hourTempUserTableName);

				// 向臨時小時表中插入數據
				// 1.查詢數據到文件
				String option = getOption(hourTempUserTableName);
				logger.debug("Query hour temp user table option [{}].", option);

				AnalyseParam param = new AnalyseParam();
				param.setOption(option);
				param.setSourceTableName(vcFactTableName);
				execDbOperateDao.queryForList("query_hourTempUserTable", param);
				logger.info("Export hour temp user data to [{}{}].",
						Constant.DEFAULT_DATA_TEMP_PATH, hourTempUserTableName);

				try {
					Thread.sleep(2000);
				} catch (InterruptedException e1) {
				}

				// 2.load
				option = getLoadOption(hourTempUserTableName);
				param = new AnalyseParam();
				param.setOption(option);
				param.setSourceTableName(hourTempUserTableName);
				logger.debug(
						"Load hour temp user table option [{}] and param [{}].",
						option, param);

				// 判斷表是否鎖
				// while (tableIsLocks("tbFactTableOperation"));
				execDbOperateDao.update("load_hourTempUserTable", param);
				logger.info("Load hour temp user table data from [{}{}].",
						Constant.DEFAULT_DATA_TEMP_PATH, hourTempUserTableName);

				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
				}

				// 刪除臨時小時用戶文件
				deleteLoadedFile(hourTempUserTableName);

				// 3.返回數據
				// execDbOperateDao.queryForList("");

				// 4.重設查詢數據到控制台
				logger.debug("Re set temporary option TEMP_EXTRACT_NAME1 = ''.");
				execDbOperateDao.update("setTemporaryOption");

			} else {
				// 其他表結構
				hourTempUserTableName = vcFactTableName;
				logger.debug("Other data ,so use source -->[{}].",
						vcFactTableName);
			}

			// 更新要執行統計任務的事實表日誌的任務狀態，2，執行中
			fact.setChTaskCreated("2");

			execDbOperateDao.update("updateHourTempUsersFactTableStatus", fact);
			logger.debug("Fact table [{}] updated task status.",
					vcFactTableName);
			// execDbOperateDao.commitTransaction();
		} finally {
			// execDbOperateDao.endTransaction();
		}

		return hourTempUserTableName;
	}

	/**
	 * Load數據文件到表中option <br>
	 * Created on: May 11, 2012 11:18:49 AM
	 * 
	 * @param fileName
	 * @return
	 */
	private String getLoadOption(String fileName) {
		StringBuffer option = new StringBuffer();
		option.append("from '").append(Constant.DEFAULT_DATA_TEMP_PATH);
		option.append(fileName).append("' ");
		option.append("delimited by ','  escapes off quotes off with checkpoint on;");

		return option.toString();
	}

	/**
	 * 臨時小時表導出到文件option <br>
	 * Created on: May 11, 2012 11:05:17 AM
	 * 
	 * @return
	 */
	private String getOption(String fileName) {
		StringBuffer option = new StringBuffer();
		option.append("set temporary option TEMP_EXTRACT_NAME1='");
		option.append(Constant.DEFAULT_DATA_TEMP_PATH);
		option.append(fileName).append("';");
		option.append("set temporary option TEMP_EXTRACT_COLUMN_DELIMITER=',';");
		option.append("set temporary option TEMP_EXTRACT_BINARY='OFF';");
		option.append("set temporary option TEMP_EXTRACT_SWAP='OFF';");

		return option.toString();
	}

	// /**
	// * 更新事實表任務狀態 <br>
	// * Created on: Apr 27, 2012 4:50:05 PM
	// *
	// * @param count
	// * @param intFtbOid
	// * @throws SQLException
	// */
	// private void updateFactTableStatus(String sqlMapId, String
	// vcFactTableName,
	// String status) throws SQLException {
	// FactTableOperation factTableOperation = new FactTableOperation();
	// factTableOperation.setDtTaskCreateTime(DateTimeUtils.getCurrentDate());
	// factTableOperation.setVcTableName(vcFactTableName);
	// factTableOperation.setChTaskComplete(status);
	// execDbOperateDao.update(sqlMapId, factTableOperation);
	// }

	/**
	 * 得到事實表向任務表插入任務後的任務數量 <br>
	 * Created on: Apr 27, 2012 4:47:02 PM
	 * 
	 * @param intFtbOid
	 * @return
	 * @throws SQLException
	 */
	private Long getFactTableTaskInsertCount(Long intFtbOid)
			throws SQLException {
		// 判斷表是否鎖
		// while (tableIsLocks("tbTask")) {
		// try {
		// logger.warn("6---->Table [getFactTableTaskInsertCount.tbTask] locks , waiting...");
		// Thread.sleep(6000);
		// } catch (InterruptedException e) {
		// // Ignore
		// }
		// }
		return (Long) execDbOperateDao.insert("createHourTaskList", intFtbOid);
	}
	/**
	 * 當前還未生成任務列表的小時事實表數據 <br>
	 * Created on: Apr 27, 2012 4:33:18 PM
	 * 
	 * @return
	 * @throws SQLException
	 */
	private List<FactTableOperation> getHourList() throws SQLException {
		return (List<FactTableOperation>) execDbOperateDao
				.queryForList("getFactTableList");
	}

	private List<FactTableOperation> getFactTableListCreatHour()
			throws SQLException {

		return (List<FactTableOperation>) execDbOperateDao
				.queryForList("getFactTableListCreatHour");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.etone.mantoeye.analyse.service.common.ICommonTaskManager#
	 * getCurrentTaskList()
	 */
	public List<CurrentTask> getCurrentTaskList() throws SQLException {
		return (List<CurrentTask>) execDbOperateDao
				.queryForList("getCurrentTaskList");
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.etone.mantoeye.analyse.service.common.ICommonTaskManager#
	 * updateCurrentTaskState(java.lang.Long, int)
	 */
	public void updateCurrentTaskState(Long intTaskId, int state)
			throws SQLException {

		logger.debug("Update current task state: [{}.{}].", intTaskId, state);

		CurrentTask currentTask = new CurrentTask();
		currentTask.setIntTaskId(intTaskId);
		currentTask.setIntTaskState(state);
		// 判斷表是否鎖
		// while (tableIsLocks("tbTask")) {
		// try {
		// logger.warn("Table [updateCurrentTaskState.tbTask] locks , waiting...");
		// Thread.sleep(6000);
		// } catch (InterruptedException e) {
		// // Ignore
		// }
		// }
		execDbOperateDao.update("updateCurrentTaskState", currentTask);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.etone.mantoeye.analyse.service.common.ICommonTaskManager#getCurrentTask
	 * (java.lang.Long)
	 */
	public CurrentTask getCurrentTask(Long intTaskId) throws SQLException {
		return (CurrentTask) execDbOperateDao.queryForObject("getCurrentTask",
				intTaskId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.etone.mantoeye.analyse.service.common.ICommonTaskManager#
	 * completeCurrentTask(com.etone.mantoeye.analyse.domain.CurrentTask)
	 */
	public void completeCurrentTask(CurrentTask currentTask)
			throws SQLException {
		logger.debug("Task [{}] complete task and update task status.",
				currentTask.getIntTaskId());

		currentTask.setDtCompleteTime(DateTimeUtils.getCurrentDate());
		currentTask.setIntTaskState(2);

		try {
			logger.debug("Start complete current task write log ...");
			execDbOperateDao.startTransaction();

			// 判斷表是否鎖
			// while (tableIsLocks("tbTaskLog")) {
			// try {
			// logger.warn("Table [completeCurrentTask.tbTaskLog] locks , waiting...");
			// Thread.sleep(6000);
			// } catch (InterruptedException e) {
			// // Ignore
			// }
			// }
			logger.debug("Start tbtasklog insert log....");
			execDbOperateDao.insert("writeCurrentTaskLog", currentTask);

			logger.debug("Start delete current task from table tbtask.");
			// 判斷表是否鎖
			// while (tableIsLocks("tbTask")) {
			// try {
			// logger.warn("Table [completeCurrentTask.tbTask] locks , waiting...");
			// Thread.sleep(6000);
			// } catch (InterruptedException e) {
			// // Ignore
			// }
			// }
			execDbOperateDao.delete("deleteCurrentTask", currentTask);

			logger.debug("Start update fact table complete task log info.");
			if (null != currentTask.getIntFtbOid()) {
				// 判斷表是否鎖
				// while (tableIsLocks("tbFactTableOperation")) {
				// try {
				// logger.warn("Table [completeCurrentTask.tbFactTableOperation] locks , waiting...");
				// Thread.sleep(6000);
				// } catch (InterruptedException e) {
				// // Ignore
				// }
				// }
				execDbOperateDao.update("updateTaskCompleted", currentTask);
			}
			execDbOperateDao.commitTransaction();
		} finally {
			execDbOperateDao.endTransaction();
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.etone.mantoeye.analyse.service.common.ICommonTaskManager#
	 * createDayTaskList()
	 */
	public void createDayTaskList() throws SQLException {
		// 判斷表是否鎖
		// while (tableIsLocks("tbTask")) {
		// try {
		// logger.warn("Table [createDayTaskList.tbTask] locks , waiting...");
		// Thread.sleep(6000);
		// } catch (InterruptedException e) {
		// // Ignore
		// }
		// }
		execDbOperateDao.insert("createDayTaskList");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.etone.mantoeye.analyse.service.common.ICommonTaskManager#
	 * createWeekTaskList()
	 */
	public void createWeekTaskList() throws SQLException {
		// 判斷表是否鎖
		// while (tableIsLocks("tbTask")) {
		// try {
		// logger.warn("Table [createWeekTaskList.tbTask] locks , waiting...");
		// Thread.sleep(6000);
		// } catch (InterruptedException e) {
		// // Ignore
		// }
		// }
		execDbOperateDao.insert("createWeekTaskList");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.etone.mantoeye.analyse.service.common.ICommonTaskManager#
	 * createMonthTaskList()
	 */
	public void createMonthTaskList() throws SQLException {
		// 判斷表是否鎖
		// while (tableIsLocks("tbTask")) {
		// try {
		// logger.warn("Table [createMonthTaskList.tbTask] locks , waiting...");
		// Thread.sleep(6000);
		// } catch (InterruptedException e) {
		// // Ignore
		// }
		// }
		execDbOperateDao.insert("createMonthTaskList");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.etone.mantoeye.analyse.service.common.ICommonTaskManager#
	 * createReDoTaskList()
	 */
	public void createReDoTaskList() throws SQLException {
		redoDayTask();

		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
		}

		redoWeekTask();

		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
		}

		redoMonthTask();
	}

	/**
	 * 重做月任務 <br>
	 * Created on: May 8, 2012 3:47:27 PM
	 * 
	 * @throws SQLException
	 */
	private void redoMonthTask() throws SQLException {
		logger.info("Checking Month Task.");
		// 判斷表是否鎖
		// while (tableIsLocks("tbTask")) {
		// try {
		// logger.warn("Table [redoMonthTask.tbTask] locks , waiting...");
		// Thread.sleep(6000);
		// } catch (InterruptedException e) {
		// // Ignore
		// }
		// }
		execDbOperateDao.insert("redoMonthTask");
	}

	/**
	 * 重做周任務 <br>
	 * Created on: May 8, 2012 3:47:23 PM
	 * 
	 * @throws SQLException
	 */
	private void redoWeekTask() throws SQLException {
		logger.info("Checking Week Task.");
		// 判斷表是否鎖
		// while (tableIsLocks("tbTask")) {
		// try {
		// logger.warn("Table [redoWeekTask.tbTask] locks , waiting...");
		// Thread.sleep(6000);
		// } catch (InterruptedException e) {
		// // Ignore
		// }
		// }
		execDbOperateDao.insert("redoWeekTask");
	}

	/**
	 * 重做天任務 <br>
	 * Created on: May 8, 2012 3:47:20 PM
	 * 
	 * @throws SQLException
	 */
	private void redoDayTask() throws SQLException {
		logger.info("Checking Day Task.");
		// 判斷表是否鎖
		// while (tableIsLocks("tbTask")) {
		// try {
		// logger.warn("Table [redoDayTask.tbTask] locks , waiting...");
		// Thread.sleep(6000);
		// } catch (InterruptedException e) {
		// // Ignore
		// }
		// }
		execDbOperateDao.insert("redoDayTask");
	}

	public void createHourTempUsers() throws SQLException {
		// 查詢當前還未生成任務列表的小時事實表
		List<FactTableOperation> hourList = getHourList();

		if (null != hourList && !hourList.isEmpty()) {

			updateHourTableStatusWaiting(hourList);

			for (FactTableOperation fact : hourList) {
				logger.debug("Fact table name is [{}].", fact.getVcTableName());

				// TODO -- 2012年5月11日10:11:42 添加小時臨時用戶數統計方法
				// 所有小時用戶數數據不再從事實表中count，而是從臨時表中取得

				long begindate = System.currentTimeMillis();

				String vcTableName = createHourTempUsers(fact.getVcTableName());

				logger.info(
						"Create hour temp user data [{}],use times:[{}]s.",
						new Object[]{
								vcTableName,
								((System.currentTimeMillis() - begindate) / 1000)});
			}
		}

	}

	/**
	 * 
	 * <br>
	 * Created on: May 25, 2012 5:01:32 PM
	 * 
	 * @param hourList
	 * @throws SQLException
	 */
	private void updateHourTableStatusWaiting(List<FactTableOperation> hourList)
			throws SQLException {
		for (FactTableOperation fact : hourList) {
			logger.info("Update hour table [{}] task status 3(waiting).",
					fact.getVcTableName());
			fact.setChTaskCreated("3");
			execDbOperateDao.update("updateHourTempUsersFactTableStatus", fact);
		}
	}

	public void updateHourFactTableStatus() throws SQLException {
		// Map<String, String> map = new HashMap<String, String>();
		// map.put("setChtaskcreated", "0");
		// map.put("whereChtaskcreated", "3");
		// updateHourFactTableStatus(map);
		//
		// logger.info("Rollback status 4.");
		// map = new HashMap<String, String>();
		// map.put("setChtaskcreated", "2");
		// map.put("whereChtaskcreated", "4");
		// updateHourFactTableStatus(map);

		// 判斷表是否鎖
		// while (tableIsLocks("tbFactTableOperation")) {
		// try {
		//
		// logger.warn("5---->Table [updateHourFactTableStatus.tbFactTableOperation] locks , waiting...");
		// Thread.sleep(6000);
		// } catch (InterruptedException e) {
		// // Ignore
		// }
		// }
		logger.info("Rollback status is 3.");
		execDbOperateDao.update("updateHourFactTableStatus_3");
		// 判斷表是否鎖
		// while (tableIsLocks("tbFactTableOperation")) {
		// try {
		//
		// logger.warn("6---->Table [updateHourFactTableStatus.tbFactTableOperation] locks , waiting...");
		// Thread.sleep(6000);
		// } catch (InterruptedException e) {
		// // Ignore
		// }
		// }
		logger.info("Rollback status is 4.");
		execDbOperateDao.update("updateHourFactTableStatus_4");

	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.etone.mantoeye.analyse.service.common.ICommonTaskManager#
	 * updateHourFactTableStatus(java.util.Map)
	 */
	public void updateHourFactTableStatus(Map<String, String> map)
			throws SQLException {
		execDbOperateDao.update("updateHourFactTableStatus", map);
	}

	public boolean tableIsLocks(String tableName) {
		Integer count = 0;
		try {
			count = (Integer) execDbOperateDao.queryForObject("tableIsLocks",
					tableName);
		} catch (SQLException e) {
			logger.warn("Check table [{}] locks error.", tableName);
		}

		if (count > 0)
			return true;
		else
			return false;
	}

	public String getRunningTask() throws SQLException {
		return (String) execDbOperateDao
				.queryForObject("getRunningTaskHourFact");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.etone.mantoeye.analyse.service.common.ICommonTaskManager#
	 * updateCurrentTaskStateList(java.lang.String)
	 */
	public void updateCurrentTaskStateList(String intTaskIds)
			throws SQLException {
		execDbOperateDao.update("updateCurrentTaskStateList", intTaskIds);

	}

	public List<CurrentTask> completeCurrentTaskList(String intTaskIds)
			throws SQLException {
		logger.debug("Task [{}] complete task and update task status.",
				intTaskIds);
		return (List<CurrentTask>) execDbOperateDao.queryForList(
				"completeCurrentTaskList", intTaskIds);

	}

	public void writeCurrentTaskLog(CurrentTask currentTask)
			throws SQLException {
		currentTask.setDtStatTime(DateTimeUtils.getCurrentDate());
		logger.debug("CurrentTask --> [{}].", currentTask);
		execDbOperateDao.insert("writeCurrentTaskLog", currentTask);

	}

	public void deleteCurrentTask(CurrentTask currentTask) throws SQLException {
		logger.debug("Delete current task ->[{}].", currentTask);
		execDbOperateDao.delete("deleteCurrentTask", currentTask);
	}

	public void updateTaskCompleted(CurrentTask currentTask)
			throws SQLException {
		logger.debug("Update fact table state [{}]", currentTask);
		execDbOperateDao.update("updateTaskCompleted", currentTask);
	}

	public int initHourFactTableStatus() throws SQLException {
		return execDbOperateDao.update("initHourFactTableStatus");
	}

	public int getRunningUDTask() throws SQLException {
		return (Integer) execDbOperateDao.queryForObject("getRunningUDTask");
	}

	public int inittbUDTimeStatus() throws SQLException {
		return execDbOperateDao.update("inittbUDTimeStatus");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.etone.mantoeye.analyse.service.common.IExecServerManager#execute(
	 * java.lang.String, java.lang.Object)
	 */
	public void execute(String sqlMapId, Object param) throws SQLException {
		logger.debug("Sql map id --> [{}], Param --> [{}].", sqlMapId, param);
		execDbOperateDao.update(sqlMapId, param);
	}

	public int initFactTable() throws SQLException {
		return (Integer) execDbOperateDao
				.queryForObject("getRunningTaskHourFactInit");
	}

	public void createHourSignalFactData() throws SQLException {
		// 查詢當前還未生成任務列表的小時事實表
		List<FactTableOperation> hourList = getSignalFactHourList();

		if (null != hourList && !hourList.isEmpty()) {

			for (FactTableOperation fact : hourList) {
				logger.info("Update hour table [{}] task status 2(waiting).",
						fact.getVcTableName());
				fact.setChTaskCreated("2");
				execDbOperateDao.update("updateHourTempUsersFactTableStatus",
						fact);
			}

		}
	}

	private List<FactTableOperation> getSignalFactHourList()
			throws SQLException {
		return (List<FactTableOperation>) execDbOperateDao
				.queryForList("getSignalFactHourList");
	}

}
