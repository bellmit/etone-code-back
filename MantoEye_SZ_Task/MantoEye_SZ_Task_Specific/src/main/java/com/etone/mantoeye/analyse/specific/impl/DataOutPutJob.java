/**
 *  com.etone.mantoeye.analyse.specific.impl.MantoEye_SZ_Task_Specific 
 */
package com.etone.mantoeye.analyse.specific.impl;

import java.io.File;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.myhkzhen.util.file.FileUtil;

import com.etone.mantoeye.analyse.domain.extract.FtbDataOutPutDecTask;
import com.etone.mantoeye.analyse.domain.extract.FtbDataOutPutTask;
import com.etone.mantoeye.analyse.domain.extract.FtbOutPutColumnMap;
import com.etone.mantoeye.analyse.domain.extract.FtbOutPutTableColumn;
import com.etone.mantoeye.analyse.service.specific.IDataOutPutTaskManager;
import com.etone.mantoeye.analyse.service.specific.impl.DataOutPutTaskManagerImpl;
import com.etone.mantoeye.analyse.specific.ISpecificTaskJob;
import com.etone.mantoeye.analyse.util.constant.Constant;
import com.etone.mantoeye.analyse.util.error.CommonErrorUtil;

/**
 * 自定義輸出任務
 * 
 * @author Wu Zhenzhen
 * @version May 22, 2012 4:00:29 PM
 */
public class DataOutPutJob implements ISpecificTaskJob {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.etone.mantoeye.analyse.specific.ISpecificTaskJob#execute()
	 */
	public void execute() {
		logger.info("[SpecificTaskJob] -- Exec Data Out Put Job.");

		IDataOutPutTaskManager manager = new DataOutPutTaskManagerImpl();

		int count = getRunningTask(manager);

		if (count > 0) {
			logger.info("Data out put task is running.");
			manager = null;
			System.exit(0);
		}

		List<FtbDataOutPutTask> list = getFtbDataOutPutTaskList(manager);

		if (null != list && !list.isEmpty()) {

			FtbDataOutPutTask ftbDataOutPutTask = list.get(0);
			if (1 == ftbDataOutPutTask.getBitTaskActiveFlag()) {

				Long nmDataOutPutTaskId = ftbDataOutPutTask
						.getNmDataOutPutTaskId();

				// 0,任務開始，更新任務狀態為1，執行中,如果任務多,可能被重複進入list中，這裡前表面改任務狀態
				logger.debug("Mark Data Output Task Id:{} running.",
						nmDataOutPutTaskId);

				updateTaskStatus(ftbDataOutPutTask, manager, 1);

				logger.info("Out Put Task TaskName:{}.",
						ftbDataOutPutTask.getVcTaskName());

				// 1.數據提取
				if (1 == ftbDataOutPutTask.getIntTaskDelong()) {
					if (3 == ftbDataOutPutTask.getIntTaskType()) {
						// 3.歷史數據提取
						historyDataOutput(ftbDataOutPutTask, manager);
					} else {
						// 其他任務--通用拍照功能
						// commonPhotographDataOutput(ftbDataOutPutTask,
						// manager);
					}
				} else {
					// 業務撥測任務
				}

				// --補加任務正確執行完成之後更新任務狀態為2:自然結束 2011年9月9日10:48:43
				// 4.任务结束
				updateTaskStatus(ftbDataOutPutTask, manager, 2);
			}
		}

		manager = null;
	}

	/**
	 * 
	 * <br>
	 * Created on: May 28, 2012 4:08:09 PM
	 * 
	 * @param ftbDataOutPutTask
	 * @param manager
	 */
	private void commonPhotographDataOutput(
			FtbDataOutPutTask ftbDataOutPutTask, IDataOutPutTaskManager manager) {

		Long nmDataOutPutTaskId = ftbDataOutPutTask.getNmDataOutPutTaskId();

		// -- 2011年12月6日17:08:03 通用拍照
		// --2011年12月16日11:23:04 測試通過
		int intTaskType = ftbDataOutPutTask.getIntTaskType();

		// (1)、提取条件
		String strConditions = getCondition4CommonPhotograph(manager,
				nmDataOutPutTaskId, intTaskType);

		if (null == strConditions || "".equals(strConditions.trim())) {
			logger.error("通用拍照功能條件未設置,任務不執行,請檢查.");
			updateTaskStatus(ftbDataOutPutTask, manager, 5);
			manager = null;
			System.exit(1);
		}

		logger.debug("Data Common Photograph condition:{}.", strConditions);

		// (2)、執行通用拍照功能數據鉆取存儲過程
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("nmDataOutPutTaskId", nmDataOutPutTaskId);
		map.put("strCondition", strConditions);

		// --提取的存儲過程完成 2011年9月9日11:26:45
		logger.info("Exec proc_create_commonPhotograph use param:{}", map);
		try {
			manager.execute("proc_create_CommonPhotographData", map);
		} catch (SQLException e) {
			logger.error(
					"{}\n MASSAGE : {} \n CAUSE :{} \n CLASS : {}\n",
					new Object[] { "執行自定義輸出任務--存儲過程时,报错:", e.getMessage(),
							e.getCause(), e.getClass() });
			updateTaskStatus(ftbDataOutPutTask, manager, 5);
			manager = null;
			System.exit(1);
		}

		map = null;

	}

	/**
	 * 
	 * <br>
	 * Created on: May 28, 2012 4:13:25 PM
	 * 
	 * @param manager
	 * @param nmDataOutPutTaskId
	 * @param intTaskType
	 * @return
	 */
	private String getCondition4CommonPhotograph(
			IDataOutPutTaskManager manager, Long nmDataOutPutTaskId,
			int intTaskType) {
		String result = "";

		switch (intTaskType) {
		case 1: // 1.通用拍照指定号码 ftbOutPutMsisdnMap
			logger.info("CommonPhotograph to Msisdn.");
			result = getMsisdnFilter(manager, nmDataOutPutTaskId, intTaskType);
			break;
		case 2:// 2.通用拍照指定终端 ftbOutPutTerminalMap
			logger.info("CommonPhotograph to Terminal.");
			result = getTerminalFilter(manager, nmDataOutPutTaskId, intTaskType);
			break;
		case 4:// 4.通用拍照指定区域 ftbOutPutAreaMap
			logger.info("CommonPhotograph to Area.");
			result = getAreaFilter(manager, nmDataOutPutTaskId, intTaskType);
			break;
		case 5:// 5.通用拍照指定业务 ftbOutPutBussinessMap
			logger.info("CommonPhotograph to Bussiness.");
			result = getBussinessFilter(manager, nmDataOutPutTaskId,
					intTaskType);
			break;
		case 6:// 6.通用拍照指定APN ftbOutPutApnMap
			logger.info("CommonPhotograph to Apn.");
			result = getApnFilter(manager, nmDataOutPutTaskId, intTaskType);
			break;
		default:// 7.通用拍照用户归属 ftbOutPutAttribMap
			logger.info("CommonPhotograph to Attrib.");
			result = getAttribFilter(manager, nmDataOutPutTaskId, intTaskType);
			break;
		}

		return result;
	}

	private String getAttribFilter(IDataOutPutTaskManager manager,
			Long nmDataOutPutTaskId, int intTaskType) {
		String tableName = "";
		String columnName = "";
		String result = "";
		List<String> valueList = null;

		tableName = "ftbOutPutAttribMap";
		columnName = "intUserBelongId";

		try {
			valueList = manager.getFtbOutPutFilterValueList(tableName,
					columnName, nmDataOutPutTaskId);
		} catch (SQLException e) {
			logger.error(
					"{}\n MASSAGE : {} \n CAUSE :{} \n CLASS : {}\n",
					new Object[] { "執行自定義輸出任務--查詢輸出條件值时,报错:", e.getMessage(),
							e.getCause(), e.getClass() });
			return null;
		}

		if (null != valueList && !valueList.isEmpty()) {
			if (1 == valueList.size()) {
				result += " and intUserBelongId = " + valueList.get(0);
			} else {
				result += " and intUserBelongId in ( ";
				for (String val : valueList) {
					result += val + ",";
				}
				result = result.substring(0, result.length() - 1);
				result += " )";
			}
		} else {
			result = null;
		}

		logger.debug(
				"intUserBelongId : tableName-->{}|==|columnName-->{}|==|result-->{}",
				new Object[] { tableName, columnName, result });
		return result;
	}

	private String getApnFilter(IDataOutPutTaskManager manager,
			Long nmDataOutPutTaskId, int intTaskType) {
		String tableName = "";
		String columnName = "";
		String result = "";
		List<String> valueList = null;

		tableName = "ftbOutPutApnMap";
		columnName = "vcApn";

		try {
			valueList = manager.getFtbOutPutFilterValueList(tableName,
					columnName, nmDataOutPutTaskId);
		} catch (SQLException e) {
			logger.error(
					"{}\n MASSAGE : {} \n CAUSE :{} \n CLASS : {}\n",
					new Object[] { "執行自定義輸出任務--查詢輸出條件值时,报错:", e.getMessage(),
							e.getCause(), e.getClass() });
			return null;
		}

		if (null != valueList && !valueList.isEmpty()) {
			if (1 == valueList.size()) {
				result += " and vcApn = '" + valueList.get(0) + "'";
			} else {
				result += " and vcApn in ( ";
				for (String val : valueList) {
					result += "'" + val + "',";
				}
				result = result.substring(0, result.length() - 1);
				result += " )";
			}
		} else {
			result = null;
		}

		logger.debug(
				"vcApn : tableName-->{}|==|columnName-->{}|==|result-->{}",
				new Object[] { tableName, columnName, result });
		return result;
	}

	private String getBussinessFilter(IDataOutPutTaskManager manager,
			Long nmDataOutPutTaskId, int intTaskType) {
		String tableName = "";
		String columnName = "";
		String result = "";
		List<String> valueList = null;

		tableName = "ftbOutPutBussinessMap";
		columnName = "nmBussinessId";

		try {
			valueList = manager.getFtbOutPutFilterValueList(tableName,
					columnName, nmDataOutPutTaskId);
		} catch (SQLException e) {
			logger.error(
					"{}\n MASSAGE : {} \n CAUSE :{} \n CLASS : {}\n",
					new Object[] { "執行自定義輸出任務--查詢輸出條件值时,报错:", e.getMessage(),
							e.getCause(), e.getClass() });
			return null;
		}

		if (null != valueList && !valueList.isEmpty()) {
			if (1 == valueList.size()) {
				result += " and ProductDimens = " + valueList.get(0);
			} else {
				result += " and ProductDimens in ( ";
				for (String val : valueList) {
					result += val + ",";
				}
				result = result.substring(0, result.length() - 1);
				result += " )";
			}
		} else {
			result = null;
		}

		logger.debug(
				"ProductDimens : tableName-->{}|==|columnName-->{}|==|result-->{}",
				new Object[] { tableName, columnName, result });
		return result;
	}

	private String getAreaFilter(IDataOutPutTaskManager manager,
			Long nmDataOutPutTaskId, int intTaskType) {
		String tableName = "";
		String columnName = "";
		String result = "";
		List<String> valueList = null;

		tableName = "ftbOutPutAreaMap";
		columnName = "intAreaType";

		// 找出區域類型,用於判別列名
		List<String> newValueList = null;
		try {
			newValueList = manager.getFtbOutPutFilterValueList(tableName,
					columnName, nmDataOutPutTaskId);
		} catch (SQLException e) {
			logger.error(
					"{}\n MASSAGE : {} \n CAUSE :{} \n CLASS : {}\n",
					new Object[] { "執行自定義輸出任務--查詢輸出條件值时,报错:", e.getMessage(),
							e.getCause(), e.getClass() });
			return null;
		}

		// 找到區域條件ID
		columnName = "intAreaId";
		try {
			valueList = manager.getFtbOutPutFilterValueList(tableName,
					columnName, nmDataOutPutTaskId);
		} catch (SQLException e) {
			logger.error(
					"{}\n MASSAGE : {} \n CAUSE :{} \n CLASS : {}\n",
					new Object[] { "執行自定義輸出任務--查詢輸出條件值时,报错:", e.getMessage(),
							e.getCause(), e.getClass() });
		}

		for (String areaType : newValueList) {
			columnName = getColumnName(areaType);
			break;
		}

		if (!"allNet".equals(columnName.trim())) {
			if (null != valueList && !valueList.isEmpty()) {
				boolean isCgi = "vccgi".equals(columnName);

				if (isCgi) {
					if (1 == valueList.size()) {
						result += " and "
								+ columnName
								+ " in ( select vccgi from ftbcgi where intid in ( "
								+ valueList.get(0) + " )) ";
					} else {
						result += " and "
								+ columnName
								+ " in ( select vccgi from ftbcgi where intid in ( ";
						for (String val : valueList) {
							result += val + ",";
						}
						result = result.substring(0, result.length() - 1);
						result += " )) ";
					}

				} else {
					if (1 == valueList.size()) {
						result += " and " + columnName + " = "
								+ valueList.get(0);
					} else {
						result += " and " + columnName + " in ( ";
						for (String val : valueList) {
							result += val + ",";
						}
						result = result.substring(0, result.length() - 1);
						result += " )";
					}
				}
			} else {
				result = "";
			}
		}

		logger.debug(
				"intAreaId : tableName-->{}|==|columnName-->{}|==|result-->{}",
				new Object[] { tableName, columnName, result });
		return result;
	}

	private String getColumnName(String areaType) {
		String columnName = "";
		;
		// 1：bsc类型
		if ("1".equals(areaType.trim())) {
			columnName = "intBscId";
		}
		// 2：街道类型
		else if ("2".equals(areaType.trim())) {
			columnName = "intStreetId";
		}
		// 3：分公司类型
		else if ("3".equals(areaType.trim())) {
			columnName = "intBranchId";
		}
		// 4：营销片区
		else if ("4".equals(areaType.trim())) {
			columnName = "intSaleAreaId";
		}
		// 5：sgsn类型;
		else if ("5".equals(areaType.trim())) {
			columnName = "intDescSgsnId";
		}
		// 6：cgi类型;
		else if ("6".equals(areaType.trim())) {
			// // 找到區域條件Name
			// columnName = "vcAreaName";
			// try
			// {
			// valueList =
			// dao.getFtbOutPutFilterValueList(tableName,
			// columnName, nmDataOutPutTaskId);
			// }
			// catch (SQLException e)
			// {
			// logger.error(
			// "{}\n MASSAGE : {} \n CAUSE :{} \n CLASS : {}\n",
			// new Object[]
			// { "執行自定義輸出任務--查詢輸出條件值时,报错:", e.getMessage(),
			// e.getCause(), e.getClass() });
			// }

			columnName = "vccgi";
		}
		// 0：全网
		else {
			columnName = "allNet";
		}
		return columnName;
	}

	private String getTerminalFilter(IDataOutPutTaskManager manager,
			Long nmDataOutPutTaskId, int intTaskType) {
		String tableName = "";
		String columnName = "";
		String result = "";
		List<String> valueList = null;

		tableName = "ftbOutPutTerminalMap";
		columnName = "nmTerminalId";

		try {
			valueList = manager.getFtbOutPutFilterValueList(tableName,
					columnName, nmDataOutPutTaskId);
		} catch (SQLException e) {
			logger.error(
					"{}\n MASSAGE : {} \n CAUSE :{} \n CLASS : {}\n",
					new Object[] { "執行自定義輸出任務--查詢輸出條件值时,报错:", e.getMessage(),
							e.getCause(), e.getClass() });
			return null;
		}

		if (null != valueList && !valueList.isEmpty()) {
			if (1 == valueList.size()) {
				result += " and nmTerminalId = " + valueList.get(0);
			} else {
				result += " and nmTerminalId in ( ";
				for (String val : valueList) {
					result += val + ",";
				}
				result = result.substring(0, result.length() - 1);
				result += " )";
			}
		} else {
			result = "";
		}

		logger.debug(
				"nmTerminalId : tableName-->{}|==|columnName-->{}|==|result-->{}",
				new Object[] { tableName, columnName, result });
		return result;
	}

	private String getMsisdnFilter(IDataOutPutTaskManager manager,
			Long nmDataOutPutTaskId, int intTaskType) {
		String tableName = "";
		String columnName = "";
		String result = "";
		List<String> valueList = null;

		tableName = "ftbOutPutMsisdnMap";
		columnName = "nmMsisdn";

		try {
			valueList = manager.getFtbOutPutFilterValueList(tableName,
					columnName, nmDataOutPutTaskId);
		} catch (SQLException e) {
			logger.error(
					"{}\n MASSAGE : {} \n CAUSE :{} \n CLASS : {}\n",
					new Object[] { "執行自定義輸出任務--查詢輸出條件值时,报错:", e.getMessage(),
							e.getCause(), e.getClass() });

			return null;
		}

		if (null != valueList && !valueList.isEmpty()) {
			if (1 == valueList.size()) {
				result += " and nmMsisdn = " + valueList.get(0);
			} else {
				result += " and nmMsisdn in ( ";
				for (String val : valueList) {
					result += val + ",";
				}
				result = result.substring(0, result.length() - 1);
				result += " )";
			}
		} else {
			result = "";
		}

		logger.debug(
				"nmMsisdn : tableName-->{}|==|columnName-->{}|==|result-->{}",
				new Object[] { tableName, columnName, result });

		return result;
	}

	/**
	 * 歷史數據提取 <br>
	 * Created on: May 28, 2012 3:20:26 PM
	 * 
	 * @param ftbDataOutPutTask
	 * @param manager
	 */
	private void historyDataOutput(FtbDataOutPutTask ftbDataOutPutTask,
			IDataOutPutTaskManager manager) {

		Long nmDataOutPutTaskId = ftbDataOutPutTask.getNmDataOutPutTaskId();

		String fileName = StringUtils.join(new Object[] { nmDataOutPutTaskId,
				"_", ftbDataOutPutTask.getVcTaskName(),
				Constant.FILE_FORMAT_DAT });

		logger.info("Out Put Task FileName:{}.", fileName);

		// 如果有舊的同名文件先刪除
		FileUtil.deleteFile(StringUtils.join(Constant.DEFAULT_DATA_TEMP_PATH,
				fileName));

		// 1、提取字段
		Map<String, String> retMap = getColumns4TQ(manager, nmDataOutPutTaskId);
		logger.debug("strColumns:[{}].", retMap);

		if (null == retMap || retMap.isEmpty()) {
			logger.error("裱中字段不存在,任務不執行,請檢查.");
			updateTaskStatus(ftbDataOutPutTask, manager, 5);
			manager = null;
			System.exit(1);
		}

		String strColumn = retMap.get("column");
		// 输出字段名
		if (StringUtils.isBlank(strColumn)) {
			logger.error("裱中字段不存在,任務不執行,請檢查.");
			updateTaskStatus(ftbDataOutPutTask, manager, 5);
			manager = null;
			System.exit(1);
		}

		logger.debug("Out put column:{}.", strColumn);

		String strTitle = retMap.get("title");
		if (StringUtils.isBlank(strTitle)) {
			logger.warn("Data getter nike name null,use column name.");
			// 把字段名做为别名
			strTitle = strColumn;
		}
		logger.debug("Out Put title:{}.", strTitle);

		// 2、提取条件
		String strConditions = getCondition4TQ(manager, nmDataOutPutTaskId);

		if (null == strConditions || "".equals(strConditions.trim())) {
			logger.error("自定義輸出條件未設置,任務不執行,請檢查.");
			updateTaskStatus(ftbDataOutPutTask, manager, 5);
			manager = null;
			System.exit(1);
		}

		// 3、執行自定義輸出的數據提取存儲過程
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("nmDataOutPutTaskId", nmDataOutPutTaskId);
		map.put("strColumn", strColumn);
		map.put("strCondition", strConditions);
		map.put("fileName",
				StringUtils.join(Constant.DEFAULT_DATA_TEMP_PATH, fileName));
		map.put("strTitle", strTitle);

		// --提取的存儲過程完成 2011年9月9日11:26:45
		logger.info("Exec proc_create_view_GetterOutPutData2TQ use param:[{}]",
				map);

		try {
			manager.execute("proc_create_view_GetterOutPutData2TQ", map);
		} catch (SQLException e) {
			logger.error(
					"{}\n MASSAGE : {} \n CAUSE :{} \n CLASS : {}\n",
					new Object[] { "執行自定義輸出任務--存儲過程时,报错:", e.getMessage(),
							e.getCause(), e.getClass() });
			updateTaskStatus(ftbDataOutPutTask, manager, 5);
			manager = null;
			System.exit(1);
		}

		// 4.把提取后的文件路径保存到数据库中
		logger.info("Data Getter Task complete.Save File:[{}{}] to DB.",
				Constant.DEFAULT_DATA_TEMP_PATH, fileName);
		try {
			saveDataGetterFile(manager, nmDataOutPutTaskId, fileName);
		} catch (SQLException e) {
			updateTaskStatus(ftbDataOutPutTask, manager, 5);
			manager = null;
			System.exit(1);
		}
	}

	/**
	 * 
	 * <br>
	 * Created on: May 28, 2012 4:00:16 PM
	 * 
	 * @param manager
	 * @param nmDataOutPutTaskId
	 * @param fileName
	 */
	private void saveDataGetterFile(IDataOutPutTaskManager manager,
			Long nmDataOutPutTaskId, String fileName) throws SQLException {
		FtbDataOutPutDecTask fileInfo = getFileInfo(fileName,
				nmDataOutPutTaskId);

		boolean flag = true;
		int i = 0;
		while (flag) {
			if (i++ > 50) {
				throw new SQLException();
				// break;
			}
			try {
				manager.saveFileInfo(fileInfo);
				flag = false;
			} catch (SQLException e) {
				logger.error(
						"第[{}]次:{}\n MASSAGE : {} \n CAUSE :{} \n CLASS : {}\n",
						new Object[] { i, "執行自定義輸出任務--存儲過程时,报错:",
								e.getMessage(), e.getCause(), e.getClass() });
				flag = true;
				try {
					Thread.sleep(30000);
				} catch (InterruptedException e1) {
				}
			}
		}
	}

	private FtbDataOutPutDecTask getFileInfo(String fileName,
			Long nmDataOutPutTaskId) {

		fileName = StringUtils.join(Constant.DEFAULT_DATA_TEMP_PATH, fileName);

		FtbDataOutPutDecTask fileInfo = new FtbDataOutPutDecTask();
		fileInfo.setNmDataOutPutTaskId(nmDataOutPutTaskId);
		fileInfo.setVcFileFormat(Constant.FILE_FORMAT_DAT);
		fileInfo.setVcFileName(fileName);
		fileInfo.setVcFilePath(Constant.DEFAULT_DATA_TEMP_PATH /* + fileName */);
		fileInfo.setVcFileServerIp(Constant.DEFAULT_FTP_SERVER);

		long filesize = getFileSize(fileName);
		fileInfo.setNmFileSize(filesize);// 文件大小

		logger.debug("Save file info : [{}].", fileInfo);
		return fileInfo;
	}

	private long getFileSize(String fileName) {
		int i = 0;
		File file = FileUtil.getFile(fileName);
		// 防止读到文件大小为0
		while (null == file) {
			if (i++ > 20) {
				break;
			}
			try {
				Thread.sleep(6000);
			} catch (InterruptedException e) {
			}
			file = FileUtil.getFile(fileName);
		}
		long filesize = 0l;
		if (null == file) {
			filesize = 43l;
		} else {
			filesize = file.length();
			if (filesize == 0L) {
				// 默认文件的大小
				filesize = 431;
			}
		}
		return filesize;
	}

	/**
	 * 
	 * <br>
	 * Created on: May 28, 2012 3:33:08 PM
	 * 
	 * @param manager
	 * @param nmDataOutPutTaskId
	 * @return
	 */
	private String getCondition4TQ(IDataOutPutTaskManager manager,
			Long nmDataOutPutTaskId) {
		String result = "";

		// 查出所有條件列
		FtbOutPutColumnMap ftbFilter = getFtbFilter(manager, nmDataOutPutTaskId);

		// 為空或未設置，則拋出異常
		if (null == ftbFilter
				|| (0 == ftbFilter.getIntApnStatus()
						&& 0 == ftbFilter.getIntbussinessStatus()
						&& 0 == ftbFilter.getIntCgiStatus()
						&& 0 == ftbFilter.getIntImeiStatus()
						&& 0 == ftbFilter.getIntMsisdnStatus() && 0 == ftbFilter
						.getIntURLStatus())) {
			return null;
		}

		// --完成，變化表名，測試中，修改配置文件即可，2011年9月8日17:28:14
		if (1 == ftbFilter.getIntApnStatus()
				|| 2 == ftbFilter.getIntApnStatus()) {
			result = getApnFilterData(manager, ftbFilter, nmDataOutPutTaskId);
		}

		if (1 == ftbFilter.getIntURLStatus()
				|| 2 == ftbFilter.getIntURLStatus()) {

			result = getUrlFilterData(manager, ftbFilter, nmDataOutPutTaskId,
					result);
		}

		if (1 == ftbFilter.getIntCgiStatus()
				|| 2 == ftbFilter.getIntCgiStatus()) {
			result = getCgiFilterData(manager, ftbFilter, nmDataOutPutTaskId,
					result);
		}

		if (1 == ftbFilter.getIntMsisdnStatus()
				|| 2 == ftbFilter.getIntMsisdnStatus()) {
			result = getMsisdnFilterData(manager, ftbFilter,
					nmDataOutPutTaskId, result);
		}

		if (1 == ftbFilter.getIntImeiStatus()
				|| 2 == ftbFilter.getIntImeiStatus()) {
			result = getImeiFilterData(manager, ftbFilter, nmDataOutPutTaskId,
					result);
		}

		if (1 == ftbFilter.getIntbussinessStatus()
				|| 2 == ftbFilter.getIntbussinessStatus()) {
			result = getBussinessFilterData(manager, ftbFilter,
					nmDataOutPutTaskId, result);
		}

		logger.debug("Result : [{}].", result);

		return result;

	}

	private String getBussinessFilterData(IDataOutPutTaskManager manager,
			FtbOutPutColumnMap ftbFilter, Long nmDataOutPutTaskId, String result) {
		String value = "";
		List<String> valueList = null;
		String tableName = "";
		String columnName = "";
		tableName = "ftbOutPutBussinessMap";
		columnName = "nmBussinessId";

		try {
			valueList = manager.getFtbOutPutFilterValueList(tableName,
					columnName, nmDataOutPutTaskId);
		} catch (SQLException e) {
			logger.error(
					"{}\n MASSAGE : {} \n CAUSE :{} \n CLASS : {}\n",
					new Object[] { "執行自定義輸出任務--查詢輸出條件值时,报错:", e.getMessage(),
							e.getCause(), e.getClass() });
			valueList = null;
		}

		if (null != valueList && !valueList.isEmpty()) {
			if (1 == valueList.size()) {
				value = // (1 == ftbFilter.getIntbussinessStatus()) ?
				" = " + valueList.get(0) // + "'" : " like '%"
				// + valueList.get(0) + "%'"
				;

				// 舊業務
				// result += " and v_ftbGnAppData2TQ.nmBussinessId" + value;
				// 新業務
				result += " and v_ftbGnAppData2TQ.ProductDimens" + value;
			} else {
				// result += " and ( ";
				// for (String val : valueList) {
				// value = // (1 == ftbFilter.getIntbussinessStatus()) ?
				// " = '"
				// " = " + val;// + "'" : " like '%" + val + "%'";
				//
				// result += "v_ftbGnAppData2TQ.nmBussinessId" + value
				// + " or ";
				// }
				// result = result.substring(0, result.length() - 3);
				// result += " )";

				String nr = " ";
				for (String val : valueList) {
					nr += val + ",";
				}
				// 新業務
				result += " and v_ftbGnAppData2TQ.ProductDimens in (" + nr;
				// 舊業務
				// result = " and v_ftbGnAppData2TQ.nmBussinessId in (" +
				// result;
				result = result.substring(0, result.length() - 1);
				result += " )";
			}
		}

		logger.debug(
				"nmBussinessId : tableName-->{}|==|columnName-->{}|==|result-->{}",
				new Object[] { tableName, columnName, result });

		return result;
	}

	private String getImeiFilterData(IDataOutPutTaskManager manager,
			FtbOutPutColumnMap ftbFilter, Long nmDataOutPutTaskId, String result) {
		String value = "";
		List<String> valueList = null;
		String tableName = "";
		String columnName = "";
		tableName = "ftbOutPutImeiMap";
		columnName = "vcImei";

		try {
			valueList = manager.getFtbOutPutFilterValueList(tableName,
					columnName, nmDataOutPutTaskId);
		} catch (SQLException e) {
			logger.error(
					"{}\n MASSAGE : {} \n CAUSE :{} \n CLASS : {}\n",
					new Object[] { "執行自定義輸出任務--查詢輸出條件值时,报错:", e.getMessage(),
							e.getCause(), e.getClass() });
			valueList = null;
		}

		if (null != valueList && !valueList.isEmpty()) {
			if (1 == valueList.size()) {
				value = (1 == ftbFilter.getIntImeiStatus()) ? " = '"
						+ valueList.get(0) + "'" : " like '%"
						+ valueList.get(0) + "%'";

				result += " and nmImei" + value;
			} else {
				result += " and ( ";
				for (String val : valueList) {
					value = (1 == ftbFilter.getIntImeiStatus()) ? " = '" + val
							+ "'" : " like '%" + val + "%'";

					result += "nmImei" + value + " or ";
				}
				result = result.substring(0, result.length() - 3);
				result += " )";
			}
		}

		logger.debug(
				"vcImei : tableName-->{}|==|columnName-->{}|==|result-->{}",
				new Object[] { tableName, columnName, result });

		return result;
	}

	private String getMsisdnFilterData(IDataOutPutTaskManager manager,
			FtbOutPutColumnMap ftbFilter, Long nmDataOutPutTaskId, String result) {
		String value = "";
		List<String> valueList = null;
		String tableName = "";
		String columnName = "";
		tableName = "ftbOutPutMsisdnMap";
		columnName = "nmMsisdn";

		try {
			valueList = manager.getFtbOutPutFilterValueList(tableName,
					columnName, nmDataOutPutTaskId);
		} catch (SQLException e) {
			logger.error(
					"{}\n MASSAGE : {} \n CAUSE :{} \n CLASS : {}\n",
					new Object[] { "執行自定義輸出任務--查詢輸出條件值时,报错:", e.getMessage(),
							e.getCause(), e.getClass() });
			valueList = null;
		}

		if (null != valueList && !valueList.isEmpty()) {
			if (1 == valueList.size()) {
				value = (1 == ftbFilter.getIntMsisdnStatus()) ? " = '"
						+ valueList.get(0) + "'" : " like '%"
						+ valueList.get(0) + "%'";

				result += " and string(nmMsisdn)" + value;
			} else {
				result += " and ( ";
				for (String val : valueList) {
					value = (1 == ftbFilter.getIntMsisdnStatus()) ? " = '"
							+ val + "'" : " like '%" + val + "%'";

					result += "string(nmMsisdn)" + value + " or ";
				}
				result = result.substring(0, result.length() - 3);
				result += " )";
			}
		}

		logger.debug(
				"nmMsisdn : tableName-->{}|==|columnName-->{}|==|result-->{}",
				new Object[] { tableName, columnName, result });

		return result;
	}

	private String getCgiFilterData(IDataOutPutTaskManager manager,
			FtbOutPutColumnMap ftbFilter, Long nmDataOutPutTaskId, String result) {
		String value = "";
		List<String> valueList = null;
		String tableName = "";
		String columnName = "";
		tableName = "ftbOutPutCgiMap";
		columnName = "vcCgi";

		try {
			valueList = manager.getFtbOutPutFilterValueList(tableName,
					columnName, nmDataOutPutTaskId);
		} catch (SQLException e) {
			logger.error(
					"{}\n MASSAGE : {} \n CAUSE :{} \n CLASS : {}\n",
					new Object[] { "執行自定義輸出任務--查詢輸出條件值时,报错:", e.getMessage(),
							e.getCause(), e.getClass() });
			valueList = null;
		}

		if (null != valueList && !valueList.isEmpty()) {
			if (1 == valueList.size()) {
				value = (1 == ftbFilter.getIntCgiStatus()) ? " = '"
						+ valueList.get(0) + "'" : " like '%"
						+ valueList.get(0) + "%'";

				result += " and vcCgi" + value;
			} else {
				result += " and ( ";
				for (String val : valueList) {
					value = (1 == ftbFilter.getIntCgiStatus()) ? " = '" + val
							+ "'" : " like '%" + val + "%'";

					result += "vcCgi" + value + " or ";
				}
				result = result.substring(0, result.length() - 3);
				result += " )";
			}
		}

		logger.debug(
				"vcCgi : tableName-->{}|==|columnName-->{}|==|result-->{}",
				new Object[] { tableName, columnName, result });

		return result;
	}

	private String getUrlFilterData(IDataOutPutTaskManager manager,
			FtbOutPutColumnMap ftbFilter, Long nmDataOutPutTaskId, String result) {
		String value = "";
		List<String> valueList = null;
		String tableName = "";
		String columnName = "";
		tableName = "ftbOutPutUrlMap";
		columnName = "vcUrl";

		try {
			valueList = manager.getFtbOutPutFilterValueList(tableName,
					columnName, nmDataOutPutTaskId);
		} catch (SQLException e) {
			logger.error(
					"{}\n MASSAGE : {} \n CAUSE :{} \n CLASS : {}\n",
					new Object[] { "執行自定義輸出任務--查詢輸出條件值时,报错:", e.getMessage(),
							e.getCause(), e.getClass() });
			valueList = null;
		}

		if (null != valueList && !valueList.isEmpty()) {
			if (1 == valueList.size()) {
				value = (1 == ftbFilter.getIntURLStatus()) ? " = '"
						+ valueList.get(0) + "'" : " like '%"
						+ valueList.get(0) + "%'";

				result += " and vcWapUrl" + value;
			} else {
				result += " and ( ";
				for (String val : valueList) {
					value = (1 == ftbFilter.getIntURLStatus()) ? " = '" + val
							+ "'" : " like '%" + val + "%'";

					result += "vcWapUrl" + value + " or ";
				}
				result = result.substring(0, result.length() - 3);
				result += " )";
			}
		}

		logger.debug(
				"vcUrl : tableName-->{}|==|columnName-->{}|==|result-->{}",
				new Object[] { tableName, columnName, result });

		return result;
	}

	private String getApnFilterData(IDataOutPutTaskManager manager,
			FtbOutPutColumnMap ftbFilter, Long nmDataOutPutTaskId) {
		String value = "";
		List<String> valueList = null;
		String tableName = "ftbOutPutApnMap";
		String columnName = "vcApn";
		String result = "";

		try {
			valueList = manager.getFtbOutPutFilterValueList(tableName,
					columnName, nmDataOutPutTaskId);
		} catch (SQLException e) {
			logger.error(
					"{}\n MASSAGE : {} \n CAUSE :{} \n CLASS : {}\n",
					new Object[] { "執行自定義輸出任務--查詢輸出條件值时,报错:", e.getMessage(),
							e.getCause(), e.getClass() });
			valueList = null;
		}

		if (null != valueList && !valueList.isEmpty()) {
			if (1 == valueList.size()) {
				value = (1 == ftbFilter.getIntApnStatus()) ? " = '"
						+ valueList.get(0) + "'" : " like UPPER('%"
						+ valueList.get(0) + "%')";

				result += " and UPPER(vcapn)" + value;
			} else {
				result += " and ( ";
				for (String val : valueList) {
					value = (1 == ftbFilter.getIntApnStatus()) ? " = '" + val
							+ "'" : " like UPPER('%" + val + "%')";

					result += "UPPER(vcapn)" + value + " or ";
				}
				result = result.substring(0, result.length() - 3);
				result += " )";
			}
		}

		logger.debug(
				"vcApn : tableName-->{}|==|columnName-->{}|==|result-->{}",
				new Object[] { tableName, columnName, result });

		return result;
	}

	/**
	 * 
	 * <br>
	 * Created on: May 28, 2012 3:37:36 PM
	 * 
	 * @param manager
	 * @param nmDataOutPutTaskId
	 * @return
	 */
	private FtbOutPutColumnMap getFtbFilter(IDataOutPutTaskManager manager,
			Long nmDataOutPutTaskId) {
		// 查出所有條件列
		FtbOutPutColumnMap ftbFilter = null;
		try {
			ftbFilter = manager.getFtbOutPutColumnMap(nmDataOutPutTaskId);
		} catch (SQLException e) {
			logger.error(
					"{}\n MASSAGE : {} \n CAUSE :{} \n CLASS : {}\n",
					new Object[] { "執行自定義輸出任務--查詢輸出條件时,报错:", e.getMessage(),
							e.getCause(), e.getClass() });
			ftbFilter = null;
		}
		return ftbFilter;
	}

	/**
	 * 
	 * <br>
	 * Created on: May 28, 2012 3:23:30 PM
	 * 
	 * @param manager
	 * @param nmDataOutPutTaskId
	 * @return
	 */
	private Map<String, String> getColumns4TQ(IDataOutPutTaskManager manager,
			Long nmDataOutPutTaskId) {
		String strColumn = "";
		String strColumnNickName = "";

		List<FtbOutPutTableColumn> columns = null;

		try {
			columns = manager.getFtbOutPutTableColumn(nmDataOutPutTaskId);
		} catch (SQLException e) {
			logger.error(
					"{}\n MASSAGE : {} \n CAUSE :{} \n CLASS : {}\n",
					new Object[] { "執行自定義輸出任務--查詢輸出列时,报错:", e.getMessage(),
							e.getCause(), e.getClass() });
			return null;
		}

		logger.debug("需要提取的列:{}.", columns);

		if (null == columns || columns.isEmpty()) {
			return null;
		}

		for (FtbOutPutTableColumn column : columns) {
			strColumn = StringUtils.join(strColumn,
					StringUtils.trimToEmpty(column.getVcColumnName()), ",");
			strColumnNickName = StringUtils.join(strColumnNickName,
					StringUtils.trimToEmpty(column.getVcColumnNickName()), ",");
		}

		strColumn = ((StringUtils.isBlank(strColumn)) ? "" : StringUtils
				.substring(strColumn, 0, StringUtils.length(strColumn) - 1));
		strColumnNickName = ((StringUtils.isBlank(strColumnNickName)) ? ""
				: StringUtils.substring(strColumnNickName, 0,
						StringUtils.length(strColumnNickName) - 1));

		logger.debug("需要提取的列名 ：{}", strColumn);
		logger.debug("對應列名的別名 ：{}", strColumnNickName);
		// --->字段名;字段别名;分组字段
		Map<String, String> map = new HashMap<String, String>();
		map.put("column", strColumn);
		map.put("title", strColumnNickName);

		return map;

	}

	/**
	 * 
	 * <br>
	 * Created on: May 28, 2012 3:13:26 PM
	 * 
	 * @param nmDataOutPutTaskId
	 * @param manager
	 * @param i
	 */
	private void updateTaskStatus(FtbDataOutPutTask ftbDataOutPutTask,
			IDataOutPutTaskManager manager, int status) {

		boolean flag = true;
		int i = 0;
		while (flag) {
			if (i++ > 50) {
				logger.warn("Data out put task update task status error.");
				manager = null;
				System.exit(0);
			}

			try {
				manager.updateFtbDataOutPutTask(
						ftbDataOutPutTask.getNmDataOutPutTaskId(), status);
				flag = false;
			} catch (SQLException e) {
				if (CommonErrorUtil.isTableLockError(e))
					logger.warn("Times[{}]-Table lock,waiting...msg:[{}].", i,
							e.getCause().getMessage());
				else
					logger.error(
							"執行{}任務--{}.{}任務更新任務狀態為執行中时,报错:\n MASSAGE : {} \n CAUSE :{} \n CLASS : {}\n",
							new Object[] { "自定義輸出",
									ftbDataOutPutTask.getNmDataOutPutTaskId(),
									ftbDataOutPutTask.getVcTaskName(),
									e.getMessage(), e.getCause(), e.getClass() });

				flag = true;
				try {
					Thread.sleep(6000);
				} catch (InterruptedException e2) {
				}
			}
		}
	}

	/**
	 * 
	 * <br>
	 * Created on: May 28, 2012 3:16:37 PM
	 * 
	 * @param manager
	 * @return
	 */
	private List<FtbDataOutPutTask> getFtbDataOutPutTaskList(
			IDataOutPutTaskManager manager) {
		List<FtbDataOutPutTask> list = null;

		try {
			list = manager.getFtbDataOutPutTask();
		} catch (SQLException e) {
			logger.error(
					"{}\n MASSAGE : {} \n CAUSE :{} \n CLASS : {}\n",
					new Object[] { "執行自定義輸出任務--查詢狀態為未執行的任務时,报错:",
							e.getMessage(), e.getCause(), e.getClass() });
			manager = null;
			list = null;
		}

		return list;
	}

	/**
	 * 
	 * <br>
	 * Created on: May 28, 2012 2:56:51 PM
	 * 
	 * @param manager
	 * @return
	 */
	private Integer getRunningTask(IDataOutPutTaskManager manager) {
		Integer count = 1;
		try {
			count = manager.getRunningTask();
		} catch (SQLException e) {
			logger.error(
					"{}\n MASSAGE : {} \n CAUSE :{} \n CLASS : {}\n",
					new Object[] { "執行自定義輸出任務--查詢是否存在執行中任務时,报错:",
							e.getMessage(), e.getCause(), e.getClass() });
			manager = null;
			count = 1;
		}

		return count;
	}

}
