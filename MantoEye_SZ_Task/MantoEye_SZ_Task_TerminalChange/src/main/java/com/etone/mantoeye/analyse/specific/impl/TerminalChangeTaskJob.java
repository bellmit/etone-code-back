/**
 *  com.etone.mantoeye.analyse.specific.impl.MantoEye_SZ_Task_Specific 
 */
package com.etone.mantoeye.analyse.specific.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.myhkzhen.util.date.DateTimeUtils;

import com.etone.mantoeye.analyse.domain.terminal.TerminalChange;
import com.etone.mantoeye.analyse.domain.terminal.TerminalChangeTask;
import com.etone.mantoeye.analyse.service.specific.ITerminalChangeTaskManager;
import com.etone.mantoeye.analyse.service.specific.impl.TerminalChangeTaskManagerImpl;
import com.etone.mantoeye.analyse.specific.ISpecificTaskJob;
import com.etone.mantoeye.analyse.util.error.CommonErrorUtil;

/**
 * @author Wu Zhenzhen
 * @version May 22, 2012 4:01:04 PM
 */
public class TerminalChangeTaskJob implements ISpecificTaskJob {

	private ITerminalChangeTaskManager manager;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.etone.mantoeye.analyse.specific.ISpecificTaskJob#execute()
	 */
	public void execute() {
		logger.info("[SpecificTaskJob] -- Exec Terminal Change Job.");

		// 2、得到當前需要執行的任務
		List<TerminalChangeTask> list = getCurrentTaskList();

		if (null != list && !list.isEmpty()) {
			// 3、得到第一個需要執行的任務
			TerminalChangeTask task = list.get(0);

			if (null != task) {
				logger.info("Terminal TaskName :{}.", task.getVcTaskName());

				// 3、更新任務狀態為執行中。。
				Long nmTerminalChangeIdTaskId = task
						.getNmTerminalChangeIdTaskId();

				logger.info("Mark Terminal Change Task Id:{} running.",
						nmTerminalChangeIdTaskId);
				updateTaskStatus(nmTerminalChangeIdTaskId, 1);

				// 4、終端升級數據反查尋
				Long nmTerminalChangeId = task.getNmTerminalChangeId();
				logger.debug("Terminal change back check : [{}].",
						nmTerminalChangeId);

				// 4.1更具終端升級統計表ID，找到相應的統計數據
				TerminalChange terminalChange = getTerminalChange(nmTerminalChangeId);

				if (null == terminalChange
						|| (null != terminalChange && null == terminalChange
								.getNmTerminalChangeId())) {
					logger.error("任務信息與終端升級統計表信息不符,請檢查.");
					updateTaskStatus(nmTerminalChangeIdTaskId, 5);
					System.exit(1);
				}

				// 4.1.4結合所有條件值
				String oldCondition = getCondition(terminalChange,
						nmTerminalChangeIdTaskId)[0];

				String newCondition = getCondition(terminalChange,
						nmTerminalChangeIdTaskId)[1];

				logger.debug("old terminal condition -- >{}.", oldCondition);
				logger.debug("new terminal condition -- >{}.", newCondition);

				// 4.2開始、結束時間
				String dtStartTime = getTime(terminalChange)[0];
				String dtEndTime = getTime(terminalChange)[1];

				logger.debug("dtStartTime -->{}.", dtStartTime);
				logger.debug("dtEndTime -->{}.", dtEndTime);

				// 5.1條件組裝參數
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("dtStartTime", dtStartTime);
				map.put("dtEndTime", dtEndTime);
				map.put("oldCondition", oldCondition);
				map.put("newCondition", newCondition);
				map.put("nmTerminalChangeIdTaskId", nmTerminalChangeIdTaskId);

				runTerminalChangeTask(map);

				// 6.結束任務
				logger.info(
						"Update Terminal Change Task Id {} status:complete.",
						nmTerminalChangeIdTaskId);
				updateTaskStatus(nmTerminalChangeIdTaskId, 2);
			}
		}
	}

	private String[] getCondition(TerminalChange terminalChange,
			Long nmTerminalChangeIdTaskId) {

		// 4.1.1根據新舊終端ID，日期，區域等信息找到原始終端升級日誌表中的imsi記錄
		String imsiCondition = getImsiCondition(terminalChange);
		logger.debug("IMSI condition ---> {}.", imsiCondition);

		if (null == imsiCondition || "".equals(imsiCondition.trim())) {
			logger.error("條件:手機IMSI信息為空,請檢查.");
			updateTaskStatus(nmTerminalChangeIdTaskId, 5);
			System.exit(1);
		}

		// 4.1.2根據終端升級統計ID，找到區域條件
		String areaCondition = getAreaCondition(terminalChange);
		logger.debug("AREA condition : --->{}.", areaCondition);

		// 4.1.3新舊終端ID
		String oldTerminalCondition = " and nmTerminalId = "
				+ terminalChange.getNmOldTerminalId();
		String newTerminalCondition = " and nmTerminalId = "
				+ terminalChange.getNmNewTerminalId();

		logger.debug("Old terminal id :[{}] and new terminal id :[{}].",
				oldTerminalCondition, newTerminalCondition);

		// 4.1.4結合所有條件值
		String oldCondition = imsiCondition + areaCondition
				+ oldTerminalCondition;
		
		String newCondition = imsiCondition + areaCondition
				+ newTerminalCondition;

		return new String[]{oldCondition, newCondition};
	}

	private void runTerminalChangeTask(Map<String, Object> map) {
		// 5.2調用存儲過程，進行數據統計
		logger.info("Exec proc_terminalChangeTask use param:{}", map);
		try {
			manager = new TerminalChangeTaskManagerImpl();
			manager.update("proc_terminalChangeTask", map);
		} catch (SQLException e) {
			logger.error(
					"{}\n MASSAGE : {} \n CAUSE :{} \n CLASS : {}\n",
					new Object[]{"執行終端升級任務定制功能--存儲過程时,报错:", e.getMessage(),
							e.getCause(), e.getClass()});
			updateTaskStatus(Long.getLong(String.valueOf(map
					.get("nmTerminalChangeIdTaskId"))), 5);
			manager = null;
			System.exit(1);
		} finally {
			manager = null;
		}
	}

	private String[] getTime(TerminalChange terminalChange) {
		String dtStartTime = "";
		String dtEndTime = "";
		// 數據統計時間類型：1-->天
		if (1 == terminalChange.getIntDateType()) {
			int year = terminalChange.getIntYear();
			int month = terminalChange.getIntMonth();
			int day = terminalChange.getIntDay();

			dtStartTime = year + "-" + ((month > 9) ? month : "0" + month)
					+ "-" + ((day > 9 ? day : "0" + day)) + " 00:00:00";

			dtEndTime = year + "-" + ((month > 9) ? month : "0" + month) + "-"
					+ ((day > 9 ? day : "0" + day)) + " 23:00:00";
		}
		// 數據統計時間類型：3-->月
		else if (3 == terminalChange.getIntDateType()) {
			int year = terminalChange.getIntYear();
			int month = terminalChange.getIntMonth();
			// 取得該月有多少天，即：該月的末尾天為幾號(28號，29號，30號，31號)
			int day = DateTimeUtils.getDayOfMonth(year, month);
			// month);

			dtStartTime = year + "-" + ((month > 9) ? month : "0" + month)
					+ "-01 00:00:00";

			dtEndTime = year + "-" + ((month > 9) ? month : "0" + month) + "-"
					+ ((day > 9 ? day : "0" + day)) + " 23:00:00";
		} else {
			// 其他數據統計時間類型
		}

		return new String[]{dtStartTime, dtEndTime};
	}

	private String getImsiCondition(TerminalChange terminalChange) {
		StringBuffer sourcesSql = new StringBuffer();
		String daySql = "";
		String whereSql = "";

		// 數據統計時間類型：1-->天
		if (1 == terminalChange.getIntDateType()) {
			daySql = "and fl.intDay = f.intDay ";
		}
		// 數據統計時間類型：3-->月
		else if (3 == terminalChange.getIntDateType()) {

		} else {
			// 其他數據統計時間類型
		}

		sourcesSql.append(daySql);
		logger.debug("sourcesSql : {}.", sourcesSql);

		if (0 == terminalChange.getIntAreaType()) {
			// 全網
		} else if (1 == terminalChange.getIntAreaType()) {
			whereSql = "and f.intAreaId =  fl.intBscId";
		} else if (2 == terminalChange.getIntAreaType()) {
			whereSql = "and f.intAreaId =  fl.intStreetId";
		} else if (3 == terminalChange.getIntAreaType()) {
			whereSql = "and f.intAreaId =  fl.intBranchId";
		} else if (4 == terminalChange.getIntAreaType()) {
			whereSql = "and f.intAreaId =  fl.intSaleAreaId";
		} else if (5 == terminalChange.getIntAreaType()) {
			whereSql = "and f.intAreaId =  fl.intDescSgsnId";
		}

		logger.debug("whereSql : {}.", whereSql);

		StringBuffer imsiConditions = new StringBuffer(300);

		imsiConditions.append(" and nmImsi in (select distinct nmImsi ");
		imsiConditions.append("from ftbStatTerminalChangeLog fl ");
		imsiConditions.append("inner join ftbStatTerminalChange f ");
		imsiConditions.append("on fl.nmOldTerminalId = f.nmOldTerminalId ");
		imsiConditions.append("and fl.nmNewTerminalId = f.nmNewTerminalId ");
		imsiConditions.append("and fl.intYear = f.intYear ");
		imsiConditions.append("and fl.intMonth = f.intMonth ");
		imsiConditions.append(sourcesSql.toString());
		imsiConditions.append("where f.nmTerminalChangeId = ");
		imsiConditions.append(terminalChange.getNmTerminalChangeId());
		imsiConditions.append(whereSql);
		imsiConditions.append(")");

		logger.debug("Imsi condition : [{}].", imsiConditions.toString());

		return imsiConditions.toString();
	}

	private String getAreaCondition(TerminalChange terminalChange) {
		String areaCondition = "";
		if (0 == terminalChange.getIntAreaType()) {
			// 全網
		} else if (1 == terminalChange.getIntAreaType()) {
			areaCondition = "and intBscId = " + terminalChange.getIntAreaId();
		} else if (2 == terminalChange.getIntAreaType()) {
			areaCondition = "and intStreetId = "
					+ terminalChange.getIntAreaId();
		} else if (3 == terminalChange.getIntAreaType()) {
			areaCondition = "and intBranchId = "
					+ terminalChange.getIntAreaId();
		} else if (4 == terminalChange.getIntAreaType()) {
			areaCondition = "and intSaleAreaId = "
					+ terminalChange.getIntAreaId();
		} else if (5 == terminalChange.getIntAreaType()) {
			areaCondition = "and intDescSgsnId = "
					+ terminalChange.getIntAreaId();
		}
		return areaCondition;
	}

	private TerminalChange getTerminalChange(Long nmTerminalChangeId) {
		// 4.1更具終端升級統計表ID，找到相應的統計數據
		TerminalChange terminalChange = null;
		try {
			manager = new TerminalChangeTaskManagerImpl();
			terminalChange = manager.getTerminalChange(nmTerminalChangeId);
		} catch (SQLException e) {
			logger.error(
					"{}\n MASSAGE : {} \n CAUSE :{} \n CLASS : {}\n",
					new Object[]{"執行終端升級任務定制功能--查詢任務狀態為未執行的任務时,报错:",
							e.getMessage(), e.getCause(), e.getClass()});
			terminalChange = null;
		} finally {
			manager = null;
		}
		return terminalChange;
	}

	private void updateTaskStatus(Long nmTerminalChangeIdTaskId, int status) {
		boolean flag = true;
		int i = 0;
		while (flag) {
			if (i++ > 50) {
				logger.warn("Data Terminal Change task update task status error.");
				manager = null;
				System.exit(0);
			}

			try {
				manager = new TerminalChangeTaskManagerImpl();
				TerminalChangeTask updateTask = new TerminalChangeTask();
				updateTask.setIntTaskStatus(status);
				updateTask.setDtTaskEndTime(DateTimeUtils.getCurrentDateStr());
				updateTask
						.setNmTerminalChangeIdTaskId(nmTerminalChangeIdTaskId);
				manager.update("updateTerminalChangeTask", updateTask);

				flag = false;
			} catch (SQLException e) {
				if (CommonErrorUtil.isTableLockError(e))
					logger.warn("Times[{}]-Table lock,waiting...msg:[{}].", i,
							e.getCause().getMessage());
				else
					logger.warn(
							"Update task status error, move current taskid [{}].",
							nmTerminalChangeIdTaskId);

				flag = true;
				try {
					Thread.sleep(6000);
				} catch (InterruptedException e2) {
				}
			} finally {
				manager = null;
			}
		}
	}

	private List<TerminalChangeTask> getCurrentTaskList() {
		// 2、得到當前需要執行的任務
		List<TerminalChangeTask> list = null;
		manager = new TerminalChangeTaskManagerImpl();
		try {
			list = manager.getTerminalChangeTaskList();
		} catch (SQLException e) {
			logger.error(
					"{}\n MASSAGE : {} \n CAUSE :{} \n CLASS : {}\n",
					new Object[]{"執行終端升級任務定制功能--查詢任務狀態為未執行的任務时,报错:",
							e.getMessage(), e.getCause(), e.getClass()});
			list = null;
		} finally {
			manager = null;
		}

		return list;

	}

}
