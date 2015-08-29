/**
 *  com.etone.mantoeye.analyse.specific.impl.MantoEye_SZ_Task_Specific 
 */
package com.etone.mantoeye.analyse.specific.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.etone.mantoeye.analyse.domain.extract.FtbDataOutPutTask;
import com.etone.mantoeye.analyse.service.specific.IDataOutPutTaskManager;
import com.etone.mantoeye.analyse.service.specific.impl.DataOutPutTaskManagerImpl;
import com.etone.mantoeye.analyse.specific.ISpecificTaskJob;
import com.etone.mantoeye.analyse.util.error.CommonErrorUtil;

/**
 * 自定義輸出任務
 * 
 * @author Wu Zhenzhen
 * @version May 22, 2012 4:00:29 PM
 */
public class PreDataOutPutJob implements ISpecificTaskJob {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.etone.mantoeye.analyse.specific.ISpecificTaskJob#execute()
	 */
	public void execute() {
		logger.info("[SpecificTaskJob] -- Exec Data Out Put Job.");
		long startTime = System.currentTimeMillis();
		IDataOutPutTaskManager manager;
		try {
			manager = new DataOutPutTaskManagerImpl();

			int count = getRunningTask(manager);

			if (count > 0) {
				logger.info("Data out put task is running.");
				manager = null;
				System.exit(0);
			}

			List<FtbDataOutPutTask> list = getFtbDataOutPutTaskList(manager);

			if (null != list && !list.isEmpty()) {

				// FtbDataOutPutTask ftbDataOutPutTask = list.get(0);
				for (FtbDataOutPutTask ftbDataOutPutTask : list) {
					if (1 == ftbDataOutPutTask.getBitTaskActiveFlag()) {

						Long nmDataOutPutTaskId = ftbDataOutPutTask
								.getNmDataOutPutTaskId();

						// 0,任務開始，更新任務狀態為1，執行中,如果任務多,可能被重複進入list中，這裡前表面改任務狀態
						logger.debug("Mark Data Output Task Id:{}-{} running.",
								nmDataOutPutTaskId,
								ftbDataOutPutTask.getNmDataOutPutTaskPreId());

						updateTaskStatus(ftbDataOutPutTask, manager, 1);

						logger.info("Out Put Task TaskName:{}.",
								ftbDataOutPutTask.getVcTaskName());

						// 1.數據提取
						if (1 == ftbDataOutPutTask.getIntTaskDelong()) {
							if (3 == ftbDataOutPutTask.getIntTaskType()) {
								// 3.歷史數據提取
							} else {
								// 其他任務--通用拍照功能
								ftbDataOutPutTask
										.setDtEndTime(ftbDataOutPutTask
												.getDtStartTime());
								commonPhotographDataOutput(ftbDataOutPutTask,
										manager);
							}
						} else {
							// 業務撥測任務
						}

						// --補加任務正確執行完成之後更新任務狀態為2:自然結束 2011年9月9日10:48:43
						// 4.任务结束
						updateTaskStatus(ftbDataOutPutTask, manager, 2);
					}
				}
			}
		} catch (Exception e) {
			logger.error(
					"{}\n MASSAGE : {} \n CAUSE :{} \n CLASS : {}\n",
					new Object[] { "執行自定義輸出任務--存儲過程时,报错:", e.getMessage(),
							e.getCause(), e.getClass() });
		} finally {
			logger.info("Exec CommonPhotograph use time:{} min",
					((System.currentTimeMillis() - startTime) / 1000.0 / 60.0));
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

		logger.info("Data CommonPhotograph condition:{}.", strConditions);

		// (2)、執行通用拍照功能數據鉆取存儲過程
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("nmDataOutPutTaskId",
				ftbDataOutPutTask.getNmDataOutPutTaskPreId());
		map.put("nmDataOutPutTaskIdBak",
				ftbDataOutPutTask.getNmDataOutPutTaskId());
		map.put("strCondition", strConditions);

		// --提取的存儲過程完成 2011年9月9日11:26:45
		logger.info(
				"Exec CommonPhotograph proc_create_commonPhotograph use param:{}",
				map);
		long startTime = System.currentTimeMillis();
		try {
			manager.execute("proc_create_commonPhotograph_v2", map);
		} catch (SQLException e) {
			logger.error(
					"{}\n MASSAGE : {} \n CAUSE :{} \n CLASS : {}\n",
					new Object[] { "執行自定義輸出任務--存儲過程时,报错:", e.getMessage(),
							e.getCause(), e.getClass() });
			updateTaskStatus(ftbDataOutPutTask, manager, 5);
			manager = null;
			System.exit(1);
		} finally {
			logger.info(
					"Exec CommonPhotograph proc_create_commonPhotograph use time:{} min",
					((System.currentTimeMillis() - startTime) / 1000.0 / 60.0));
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
				manager.updateFtbFacTask(
						ftbDataOutPutTask.getNmDataOutPutTaskPreId(), status);
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
			list = manager.getFtbDataOutPutTask2();
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
			count = manager.getRunningTask2();
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
