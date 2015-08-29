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
import org.myhkzhen.util.date.DateTimeUtils;
import org.myhkzhen.util.file.FileUtil;

import com.etone.mantoeye.analyse.domain.extract.FtbDataGetterDecTask;
import com.etone.mantoeye.analyse.domain.extract.FtbDataGetterFilter;
import com.etone.mantoeye.analyse.domain.extract.FtbDataGetterTask;
import com.etone.mantoeye.analyse.domain.extract.FtbFilterColumnMapTask;
import com.etone.mantoeye.analyse.domain.extract.FtbOutColumn;
import com.etone.mantoeye.analyse.service.specific.IDataGetterTaskManager;
import com.etone.mantoeye.analyse.service.specific.impl.DataGetterTaskManagerImpl;
import com.etone.mantoeye.analyse.specific.ISpecificTaskJob;
import com.etone.mantoeye.analyse.util.constant.Constant;
import com.etone.mantoeye.analyse.util.error.CommonErrorUtil;

/**
 * 任務提取功能
 * 
 * @author Wu Zhenzhen
 * @version May 22, 2012 4:00:13 PM
 */
public class DataGetterJob implements ISpecificTaskJob {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.etone.mantoeye.analyse.specific.ISpecificTaskJob#execute()
	 */
	public void execute() {
		logger.info("[SpecificTaskJob] -- Exec Data Getter Job.");

		IDataGetterTaskManager manager = new DataGetterTaskManagerImpl();

		// 数据提取任务
		FtbDataGetterTask ftbDataGetterTask = getDataGetterTask(manager);

		if (null == ftbDataGetterTask) {
			logger.info("Not data getter to do.");
			manager = null;
			System.exit(0);
		}

		logger.info("Data Getter TaskName:{}",
				ftbDataGetterTask.getVcTaskName());

		Long nmDataGetterTaskId = ftbDataGetterTask.getNmDataGetterTaskId();

		// 0.任务开始
		logger.info("Mark Data Getter Task Id:{} running.", nmDataGetterTaskId);
		updateTaskStatus(manager, nmDataGetterTaskId, 1);

		// 数据提取
		if (ftbDataGetterTask.getIntTaskType() == 3
				|| ftbDataGetterTask.getIntTaskType() == 4
				|| ftbDataGetterTask.getIntTaskType() == 9) {
			logger.info("Data Getter.");
			long begindate = System.currentTimeMillis();

			dataGetter(manager, ftbDataGetterTask);

			logger.info(
					"Exec Data Getter , use time: {} s\\t",
					new Object[]{((System.currentTimeMillis() - begindate) / 1000)});

		}
		// 规则拨测
		else if (ftbDataGetterTask.getIntTaskType() == 7
				|| ftbDataGetterTask.getIntTaskType() == 8) {
			logger.info("BC getter.");
			long begindate = System.currentTimeMillis();

			bcDataGetter(manager, ftbDataGetterTask);

			logger.info(
					"Exec B Data Getter , use time: {} s\\t",
					new Object[]{((System.currentTimeMillis() - begindate) / 1000)});
		}

		// 4.任务结束
		logger.info("Update Task Id:[{}] Status:complete.", nmDataGetterTaskId);
		updateTaskStatus(manager, nmDataGetterTaskId, 2);

		manager = null;
	}

	/**
	 * 撥測任務 <br>
	 * Created on: May 23, 2012 3:39:45 PM
	 * 
	 * @param manager
	 * @param ftbDataGetterTask
	 */
	private void bcDataGetter(IDataGetterTaskManager manager,
			FtbDataGetterTask ftbDataGetterTask) {

		Long nmDataGetterTaskId = ftbDataGetterTask.getNmDataGetterTaskId();

		int intType = ((ftbDataGetterTask.getIntTaskType() == 7) ? 2 : 1);

		// 提取条件
		List<FtbDataGetterFilter> ftbDataGetterFilters = getFtbDataGetterFilter(
				manager, nmDataGetterTaskId);

		String strConditions = getConditions(ftbDataGetterFilters, manager,
				ftbDataGetterTask);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("nmDataGetterTaskId", nmDataGetterTaskId);
		map.put("intType", intType);
		map.put("strConditions", strConditions.toString());

		logger.info("Exec proc_create_view_GetterData2BC use param:[{}]", map);
		try {
			manager.execute("proc_create_view_GetterData2BC", map);
		} catch (Exception e) {
			updateTaskStatus(manager, nmDataGetterTaskId, 5);
			map = null;
			manager = null;
			System.exit(1);
		}
		map = null;

		logger.info("Complete bc data getter task.");
	}

	/**
	 * 
	 * <br>
	 * Created on: May 23, 2012 3:45:56 PM
	 * 
	 * @param ftbDataGetterFilters
	 * @param manager
	 * @return
	 */
	private String getConditions(
			List<FtbDataGetterFilter> ftbDataGetterFilters,
			IDataGetterTaskManager manager, FtbDataGetterTask ftbDataGetterTask) {
		String strConditions = "";
		for (FtbDataGetterFilter ftbDataGetterFilter : ftbDataGetterFilters) {
			strConditions = StringUtils.join(strConditions, " and ",
					strFilter4BC(manager, ftbDataGetterFilter), " ");
		}

		// 提取时间
		strConditions = StringUtils.join(strConditions, " and dtEndTime >= '");
		strConditions = StringUtils.join(strConditions, DateTimeUtils
				.getParseDateToStr(ftbDataGetterTask.getDtStartTime()));
		strConditions = StringUtils.join(strConditions, "' and dtEndTime < '");
		strConditions = StringUtils.join(strConditions, DateTimeUtils
				.getParseDateToStr(ftbDataGetterTask.getDtEndTime()), "'");

		logger.debug("BC Conditions:{}.", strConditions.toString());

		return strConditions;
	}
	/**
	 * 
	 * <br>
	 * Created on: May 23, 2012 3:46:28 PM
	 * 
	 * @param manager
	 * @param ftbDataGetterFilter
	 * @return
	 */
	private String strFilter4BC(IDataGetterTaskManager manager,
			FtbDataGetterFilter filter) {

		final int type = filter.getIntFilterType();
		String filterValue = "";
		// 表示参数加不加引号
		String tmp = (type != 2 ? "" : "'");
		List<FtbDataGetterFilter> values = null;
		try {
			values = manager.getFtbDataGetterFilterValueList(filter);
		} catch (SQLException e) {
			logger.error(
					"{}\n MASSAGE : {} \n CAUSE :{} \n CLASS : {}\n",
					new Object[]{"執行任務提取功能--查詢撥測任務條件值时,报错:", e.getMessage(),
							e.getCause(), e.getClass()});
			return null;
		}

		for (FtbDataGetterFilter value : values) {
			filterValue = StringUtils.join(filterValue, tmp,
					value.getVcFilterValue(), tmp, ",");
		}

		filterValue = StringUtils.substring(filterValue, 0,
				StringUtils.length(filterValue) - 1);

		logger.debug("Filter value --> [{}].", filterValue);

		// 生成查询条件
		String result = " and ";
		switch (type) {
			case 1 :// 1.用户号码
				result = StringUtils.join("nmMsisdn in (", filterValue, ")");
				break;
			case 2 :// 2.CGI
				result = StringUtils.join("vcCgi in (", filterValue, ")");
				break;
			case 3 :// 3.业务ID
				result = StringUtils.join("nmBussinessId in (", filterValue,
						")");
				break;
			case 4 :// 4.BSCID
				result = StringUtils.join("intBscId in (", filterValue, ")");
				break;
			case 5 :// 5.SGSNID
				result = StringUtils.join("intDescSgsnId in (", filterValue,
						")");
				break;
			case 6 :// 6.分公司ID
				result = StringUtils.join("intBranchId in (", filterValue, ")");
				break;
			case 7 :// 7.营销片区ID
				result = StringUtils.join("intSaleAreaId in (", filterValue,
						")");
				break;
			// case 8:// 8.街道ID
			default :
				result = StringUtils.join("intStreetId in (", filterValue, ")");
				break;
		}
		return result;
	}

	/**
	 * 
	 * <br>
	 * Created on: May 23, 2012 3:42:58 PM
	 * 
	 * @param manager
	 * @param nmDataGetterTaskId
	 * @return
	 */
	private List<FtbDataGetterFilter> getFtbDataGetterFilter(
			IDataGetterTaskManager manager, Long nmDataGetterTaskId) {

		List<FtbDataGetterFilter> ftbDataGetterFilters = null;
		try {
			ftbDataGetterFilters = manager
					.getFtbDataGetterFilterList(nmDataGetterTaskId);
		} catch (SQLException e) {
			logger.error(
					"{}\n MASSAGE : {} \n CAUSE :{} \n CLASS : {}\n",
					new Object[]{"執行任務提取功能--查詢撥測條件值任務时,报错:", e.getMessage(),
							e.getCause(), e.getClass()});
			updateTaskStatus(manager, nmDataGetterTaskId, 5);
			manager = null;
			System.exit(1);
		}
		return ftbDataGetterFilters;
	}
	/**
	 * 數據提取 <br>
	 * Created on: May 22, 2012 4:35:31 PM
	 * 
	 * @param manager
	 * @param ftbDataGetterTask
	 */
	private void dataGetter(IDataGetterTaskManager manager,
			FtbDataGetterTask ftbDataGetterTask) {

		Long nmDataGetterTaskId = ftbDataGetterTask.getNmDataGetterTaskId();
		// 文件名
		String fileName = StringUtils.join(new Object[]{nmDataGetterTaskId,
				"_", ftbDataGetterTask.getVcTaskName(),
				Constant.FILE_FORMAT_DAT});

		logger.info("Data Getter Task FileName:{}", fileName);

		// 如果有舊的同名文件先刪除
		FileUtil.deleteFile(StringUtils.join(Constant.DEFAULT_DATA_TEMP_PATH,
				fileName));

		// 提取字段
		Map<String, String> retMap = getColumns4TQ(manager, nmDataGetterTaskId);
		logger.debug("提取列為:[{}].", retMap);

		if (null == retMap || retMap.isEmpty()) {
			logger.error("輸出列設置為空,無法進行任務提取,請檢查.");
			updateTaskStatus(manager, nmDataGetterTaskId, 5);
			manager = null;
			System.exit(1);
		}

		// 加多"分组标识"&"限定条件",以及输出TITLE中文------
		String strColumn = retMap.get("column");
		logger.debug("Str column --> [{}].", strColumn);
		// 输出字段名
		if (StringUtils.isBlank(strColumn)) {
			logger.error("輸出列設置為空,無法進行任務提取,請檢查.");
			updateTaskStatus(manager, nmDataGetterTaskId, 5);
			manager = null;
			System.exit(1);
		}
		logger.debug("Out Put column :{}.", strColumn);

		String strTitle = retMap.get("title");
		logger.debug("Str title --> [{}].", strTitle);
		if (StringUtils.isBlank(strTitle)) {
			logger.warn("Output nike name null,use column name.");
			// 把字段名做为别名
			strTitle = strColumn;
		}
		logger.debug("Out Put tile:{}.", strTitle);

		// 提取条件
		String strConditions = getCondition4TQ(manager, nmDataGetterTaskId);
		logger.debug("Str condition --> [{}].", strConditions);

		// 把Group by 放到条件后面
		String strGroup = retMap.get("group");
		logger.debug("Str group --> [{}].", strGroup);

		if (!StringUtils.isBlank(strGroup)) {
			strConditions = StringUtils.join(" group by ", strGroup);
		}

		logger.debug("Getter Conditions:{}.", strConditions);

		// 执行存储过程
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("nmDataGetterTaskId", nmDataGetterTaskId);
		map.put("strColumns", strColumn);
		map.put("strConditions", strConditions);
		map.put("fileName",
				StringUtils.join(Constant.DEFAULT_DATA_TEMP_PATH, fileName));
		map.put("strTitle", strTitle);

		logger.info("Exec proc_create_view_GetterData2TQ use param:[{}]", map);

		try {
			manager.execute("proc_create_view_GetterData2TQ", map);
		} catch (SQLException e) {
			logger.error(
					"{}\n MASSAGE : {} \n CAUSE :{} \n CLASS : {}\n",
					new Object[]{"執行任務提取功能--執行存儲過程时,报错:", e.getMessage(),
							e.getCause(), e.getClass()});
			updateTaskStatus(manager, nmDataGetterTaskId, 5);
			manager = null;
			System.exit(1);
		}
		map = null;

		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
		}

		// 4.把提取后的文件路径保存到数据库中
		logger.info("Data Getter Task complete.Save File:[{}{}] to DB.",
				Constant.DEFAULT_DATA_TEMP_PATH, fileName);
		try {
			saveDataGetterFile(manager, nmDataGetterTaskId, fileName);
		} catch (SQLException e) {
			updateTaskStatus(manager, nmDataGetterTaskId, 5);
			manager = null;
			System.exit(1);
		}
	}

	/**
	 * 把提取后的文件路径保存到数据库中 <br>
	 * Created on: May 22, 2012 5:18:16 PM
	 * 
	 * @param manager
	 * @param nmDataGetterTaskId
	 * @param fileName
	 */
	private void saveDataGetterFile(IDataGetterTaskManager manager,
			Long nmDataGetterTaskId, String fileName) throws SQLException {

		FtbDataGetterDecTask fileInfo = getFileInfo(fileName,
				nmDataGetterTaskId);

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
						new Object[]{i, "執行任務提取功能--保存生成文件信息时,报错:",
								e.getMessage(), e.getCause(), e.getClass()});
				flag = true;
				try {
					Thread.sleep(30000);
				} catch (InterruptedException e1) {
				}
			}
		}
	}

	/**
	 * 獲取文件信息 <br>
	 * Created on: May 22, 2012 5:28:21 PM
	 * 
	 * @param fileName
	 * @param nmDataGetterTaskId
	 * @return
	 */
	private FtbDataGetterDecTask getFileInfo(String fileName,
			Long nmDataGetterTaskId) {
		fileName = StringUtils.join(Constant.DEFAULT_DATA_TEMP_PATH, fileName);

		FtbDataGetterDecTask fileInfo = new FtbDataGetterDecTask();
		fileInfo.setNmDataGetterTaskId(nmDataGetterTaskId);
		fileInfo.setVcFileFormat(Constant.FILE_FORMAT_DAT);
		fileInfo.setVcFileServerIp(Constant.DEFAULT_FTP_SERVER);
		fileInfo.setVcFilePath(fileName);
		fileInfo.setDtETime(DateTimeUtils.getCurrentDate());

		long filesize = getFileSize(fileName);
		fileInfo.setNmFileSize(filesize);// 文件大小

		logger.debug("Save file info : [{}].", fileInfo);
		return fileInfo;
	}

	/**
	 * 文件大小 <br>
	 * Created on: May 22, 2012 5:25:25 PM
	 * 
	 * @param fileName
	 * @return
	 */
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
	 * 提取条件 <br>
	 * Created on: May 22, 2012 4:57:55 PM
	 * 
	 * @param manager
	 * @param nmDataGetterTaskId
	 * @return
	 */
	private String getCondition4TQ(IDataGetterTaskManager manager,
			Long nmDataGetterTaskId) {
		String result = "";

		// 取得過濾列
		List<FtbFilterColumnMapTask> ftbFilterColumnMapTasks = null;
		try {
			ftbFilterColumnMapTasks = manager
					.getFtbFilterColumnMapTaskList(nmDataGetterTaskId);
		} catch (SQLException e) {
			logger.error(
					"{}\n MASSAGE : {} \n CAUSE :{} \n CLASS : {}\n",
					new Object[]{"執行任務提取功能--查詢輸出條件时,报错:", e.getMessage(),
							e.getCause(), e.getClass()});
			return null;
		}

		for (FtbFilterColumnMapTask ftbFilterColumnMapTask : ftbFilterColumnMapTasks) {
			String column = ftbFilterColumnMapTask.getColumnName();
			int columnType = ftbFilterColumnMapTask.getIntColumnType();
			String tmpChar = ((columnType == 1) ? "" : "'");
			String tmp = " and ";

			// 取得過濾值
			List<FtbFilterColumnMapTask> values = null;
			try {
				values = manager
						.getFtbFilterColumnMapTaskValueList(ftbFilterColumnMapTask);
			} catch (SQLException e) {
				logger.error(
						"{}\n MASSAGE : {} \n CAUSE :{} \n CLASS : {}\n",
						new Object[]{"執行任務提取功能--查詢條件值时,报错:", e.getMessage(),
								e.getCause(), e.getClass()});
				logger.warn("check next filter value.");
				continue;
			}

			if (null != values && !values.isEmpty()) {
				// 多个条件相同的,用and()包含,中间用or
				if (values.size() > 1) {
					tmp = StringUtils.join(tmp, " ( ");
					for (FtbFilterColumnMapTask value : values) {
						tmp = StringUtils.join(tmp, column,
								value.getVccondition(),
								tmpChar + value.getVcFilterValue(), tmpChar,
								" or ");
					}
					tmp = StringUtils.join(
							StringUtils.substring(tmp, 0,
									StringUtils.length(tmp) - 3), " ) ");

				} else {
					tmp = StringUtils.join(tmp, column, values.get(0)
							.getVccondition(), tmpChar, values.get(0)
							.getVcFilterValue(), tmpChar);
				}

				result = StringUtils.join(result, tmp);
			}

			logger.debug("For Reslut --> [{}].", result);
		}

		logger.debug("Reslut --> [{}].", result);

		return result;
	}

	/**
	 * 提取字段設計 <br>
	 * Created on: May 22, 2012 4:38:52 PM
	 * 
	 * @param manager
	 * @param nmDataGetterTaskId
	 * @return
	 */
	private Map<String, String> getColumns4TQ(IDataGetterTaskManager manager,
			Long nmDataGetterTaskId) {
		String strColumn = "";
		String strColumnNickName = "";
		String strColumnGroupBy = "";

		// 取得任務輸出列
		List<FtbOutColumn> columns = null;
		try {
			columns = manager.getFtbOutColumnList(nmDataGetterTaskId);
		} catch (SQLException e) {
			logger.error(
					"{}\n MASSAGE : {} \n CAUSE :{} \n CLASS : {}\n",
					new Object[]{"執行執行任務提取功能--查詢輸出列时,报错:", e.getMessage(),
							e.getCause(), e.getClass()});
			return null;
		}

		if (null == columns || columns.isEmpty()) {
			return null;
		}

		for (FtbOutColumn column : columns) {
			// 输出字段名
			strColumn = StringUtils.join(strColumn,
					StringUtils.trimToEmpty(column.getVcColumnName()), ",");
			// 输出字段别名
			strColumnNickName = StringUtils.join(strColumnNickName,
					StringUtils.trimToEmpty(column.getVcColumnNickName()), ",");
			// 分组字段
			if (column.getBitGroupBy() == 1) {
				strColumnGroupBy = StringUtils.join(strColumnGroupBy,
						StringUtils.trimToEmpty(column.getVcColumnName()), ",");
			}
		}

		strColumn = StringUtils.trimToEmpty(StringUtils.substring(strColumn, 0,
				StringUtils.length(strColumn) - 1));
		strColumnNickName = StringUtils
				.trimToEmpty(StringUtils.substring(strColumnNickName, 0,
						StringUtils.length(strColumnNickName) - 1));
		strColumnGroupBy = StringUtils.trimToEmpty(StringUtils.substring(
				strColumnGroupBy, 0, StringUtils.length(strColumnGroupBy) - 1));

		logger.debug("strColumn --> [{}].", strColumn);
		logger.debug("strColumnNickName --> [{}].", strColumnNickName);
		logger.debug("strColumnGroupBy --> [{}].", strColumnGroupBy);

		// --->字段名;字段别名;分组字段
		Map<String, String> map = new HashMap<String, String>();
		map.put("column", strColumn);
		map.put("title", strColumnNickName);
		map.put("group", strColumnGroupBy);

		return map;
	}
	/**
	 * 檢查是否有任務提取功能要做 <br>
	 * Created on: May 22, 2012 4:27:05 PM
	 * 
	 * @param manager
	 * @return
	 */
	private FtbDataGetterTask getDataGetterTask(IDataGetterTaskManager manager) {
		FtbDataGetterTask ftbDataGetterTask = null;
		try {
			ftbDataGetterTask = manager.getFtbDataGetterTask();
		} catch (SQLException e) {
			logger.error(
					"執行任務提取功能--查詢狀態為未執行的任務时,报错:\n MASSAGE : {} \n CAUSE :{} \n CLASS : {}\n",
					new Object[]{e.getMessage(), e.getCause(), e.getClass()});
			manager = null;
			System.exit(0);
		}

		return ftbDataGetterTask;
	}

	/**
	 * 更新任務狀態 <br>
	 * Created on: May 22, 2012 4:20:46 PM
	 * 
	 * @param manager
	 * @param nmDataGetterTaskId
	 */
	private void updateTaskStatus(IDataGetterTaskManager manager,
			Long nmDataGetterTaskId, int status) {
		boolean flag = true;
		int i = 0;
		while (flag) {
			if (i++ > 50) {
				logger.warn("Data getter task update task status error.");
				manager = null;
				System.exit(0);
			}

			try {
				manager.updateFtbDataGetterTask(nmDataGetterTaskId, status);
				flag = false;
			} catch (SQLException e) {
				if (CommonErrorUtil.isTableLockError(e))
					logger.warn("Times[{}]-Table lock,waiting...msg:[{}].", i,
							e.getCause().getMessage());
				else
					logger.error(
							"第[{}]次直接執行任務提取功能时,报错\n MASSAGE : {} \n CAUSE : {} \n CLASS : {}\n",
							new Object[]{i, e.getMessage(), e.getCause(),
									e.getClass()});

				flag = true;
				try {
					Thread.sleep(6000);
				} catch (InterruptedException e2) {
				}
			}
		}
	}

}
