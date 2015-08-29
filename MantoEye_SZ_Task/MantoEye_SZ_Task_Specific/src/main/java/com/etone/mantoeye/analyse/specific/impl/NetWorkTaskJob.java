/**
 *  com.etone.mantoeye.analyse.specific.impl.MantoEye_SZ_Task_Specific 
 */
package com.etone.mantoeye.analyse.specific.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.etone.mantoeye.analyse.domain.network.FtbNetworkConfigure;
import com.etone.mantoeye.analyse.domain.network.FtbNetworkTask;
import com.etone.mantoeye.analyse.service.specific.INetWorkTaskManager;
import com.etone.mantoeye.analyse.service.specific.impl.NetWorkTaskManagerImpl;
import com.etone.mantoeye.analyse.specific.ISpecificTaskJob;
import com.etone.mantoeye.analyse.util.error.CommonErrorUtil;

/**
 * 業務健康度任務定制功能
 * 
 * @author Wu Zhenzhen
 * @version May 22, 2012 4:00:47 PM
 */
public class NetWorkTaskJob implements ISpecificTaskJob {

	private INetWorkTaskManager manager;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.etone.mantoeye.analyse.specific.ISpecificTaskJob#execute()
	 */
	public void execute() {
		logger.info("[SpecificTaskJob] -- Exec Net Work Task Job.");

		int count = getRunningTask();

		if (count != 0) {
			logger.info("Data Net Work task is running.");
			System.exit(0);
		}

		List<FtbNetworkTask> list = getFtbNetWorkTaskList();

		if (null != list && !list.isEmpty()) {

			FtbNetworkTask ftbNetworkTask = list.get(0);

			logger.info("Net Work Task TaskName : [{}].",
					ftbNetworkTask.getVcTaskName());

			Long nmNetworkTaskId = ftbNetworkTask.getNmNetworkTaskId();

			// 3、激活的任務
			if (1 == ftbNetworkTask.getBitTaskActiveFlag()) {

				logger.debug("Mark Net Work Task Id:{} running.",
						nmNetworkTaskId);

				updateTaskStatus(ftbNetworkTask, 1);

				// (1)數據業務模塊提取
				if (1 == ftbNetworkTask.getIntTaskDelong()) {

					getNetWorkDataTask(ftbNetworkTask);

					// 4、結果集添加
					logger.info("Net Work Task Complete.Set Result.");
					setNetWorkResult(nmNetworkTaskId);

				} else {
					// 業務撥測模塊提取
				}

				// 5、結束任務
				logger.info("Update Task Id:{} status complete.",
						nmNetworkTaskId);
				updateTaskStatus(ftbNetworkTask, 2);

			}

		}

	}

	/**
	 * 
	 * <br>
	 * Created on: May 30, 2012 3:48:40 PM
	 * 
	 * @param nmNetworkTaskId
	 */
	private void setNetWorkResult(Long nmNetworkTaskId) {

		// 1：连接建立成功率
		String selectSql = "cast((round((sum(cast(isnull(intSuccess,0) as decimal(20,2)))/sum(cast(isnull(intTimes,1) as decimal(20,2)))),4))*100 as decimal(10,2))";
		String tableName = "ftbStatConnect";
		getConfigure(1, nmNetworkTaskId, selectSql, tableName,
				"Connect success result");

		// 2：业务请求成功率
		selectSql = "cast((round((sum(cast(isnull(intAppSuccessful,0) as decimal(20,2)))/sum(cast(isnull(intAppTimes,1) as decimal(20,2)))),4))*100 as decimal(10,2))";
		tableName = "ftbStatConnectRequest";
		getConfigure(2, nmNetworkTaskId, selectSql, tableName,
				"Connect Request result");

		// 3：连接建立时延(单位为秒)
		selectSql = "cast(round(isnull(sum(intAppAckTimes)/sum(intAppAckTime),0.0),2) as decimal(10,2))";
		tableName = "ftbStatAppAckTime";
		getConfigure(3, nmNetworkTaskId, selectSql, tableName,
				"AppAck time result");

		// 4：业务可持续发展-增长率
		selectSql = "cast(round(isnull((sum(nmUsers)-sum(nmPrevUsers))/sum(nmPrevUsers),0.0),2) as decimal(10,2))*100";
		tableName = "ftbStatBusinessDevelopment";
		getConfigure(4, nmNetworkTaskId, selectSql, tableName,
				"Bussiness development  result");

		// 5：业务可持续发展-业务占比
		selectSql = "cast(round(isnull(sum(nmFlush)/sum(nmAllFlush),0.0),2) as decimal(10,2))*100";
		tableName = "ftbStatBusinessDevelopment";
		getConfigure(5, nmNetworkTaskId, selectSql, tableName,
				"Bussiness development reslut");

		// 6：业务使用情况
		selectSql = "cast(round(isnull(sum(nmUsers)/sum(nmAllUsers),0.0),2) as decimal(10,2))*100";
		tableName = "ftbStatBusinessActivity";
		getConfigure(6, nmNetworkTaskId, selectSql, tableName,
				"Bussiness used result");

	}
	private void getConfigure(int intType, Long nmNetworkTaskId,
			String selectSql, String tableName, String typeInfo) {

		manager = new NetWorkTaskManagerImpl();

		FtbNetworkConfigure configure = null;

		try {
			configure = manager.getFtbNetworkConfigure(intType);
		} catch (SQLException e) {
			logger.error(
					"{}\n MASSAGE : {} \n CAUSE :{} \n CLASS : {}\n",
					new Object[]{"執行業務健康度任務--設置結果值时,报错:", e.getMessage(),
							e.getCause(), e.getClass()});
			configure = null;
		} finally {
			manager = null;
		}

		if (null != configure) {

			// 設置結果值
			logger.info("Set [{}] : {}.", intType, typeInfo);
			setNetWorkResult(intType, selectSql, tableName, nmNetworkTaskId,
					configure);

		}

	}

	/**
	 * 
	 * <br>
	 * Created on: May 30, 2012 4:17:32 PM
	 * 
	 * @param intType
	 * @param selectSql
	 * @param tableName
	 * @param nmNetworkTaskId
	 * @param configure
	 */
	private void setNetWorkResult(int intType, String selectSql,
			String tableName, Long nmNetworkTaskId,
			FtbNetworkConfigure configure) {

		Double intCount = null;
		manager = new NetWorkTaskManagerImpl();
		try {
			intCount = manager.getNetWorkCount(selectSql, tableName,
					nmNetworkTaskId);
		} catch (SQLException e) {
			logger.error(
					"{}\n MASSAGE : {} \n CAUSE :{} \n CLASS : {}\n",
					new Object[]{"執行業務健康度任務--查詢結果值配置时,报错:", e.getMessage(),
							e.getCause(), e.getClass()});
			intCount = null;
			manager = null;
		}

		if (null != intCount) {
			Double markValue = getMarkValue(intCount, configure);

			try {
				manager.addNetWorkResult(nmNetworkTaskId, intType,
						configure.getIntScale(), intCount, markValue);
			} catch (SQLException e) {
				logger.error("{}\n MASSAGE : {} \n CAUSE :{} \n CLASS : {}\n",
						new Object[]{"執行業務健康度任務--添加最終結果值时,报错:", e.getMessage(),
								e.getCause(), e.getClass()});
			} finally {
				manager = null;
			}
		}
	}

	/**
	 * 求業務健康度TO值
	 * <p />
	 * 2012-2-14 上午11:15:30
	 * 
	 * @param intCount
	 * @param configInfo
	 * @return
	 */
	private Double getMarkValue(Double intCount, FtbNetworkConfigure configInfo) {
		return (intCount <= configInfo.getIntLow())
				? 0.0
				: ((intCount < configInfo.getIntCommon())
						? (75 / (configInfo.getIntCommon() - configInfo
								.getIntLow()) * (intCount - configInfo
								.getIntLow()))
						: ((intCount < configInfo.getIntExcellent())
								? (75 + 10
										/ (configInfo.getIntExcellent() - configInfo
												.getIntCommon())
										* (intCount - configInfo.getIntCommon()))
								: ((intCount < configInfo.getIntTop()) ? (15
										/ (configInfo.getIntTop() - configInfo
												.getIntExcellent())
										* (intCount - configInfo
												.getIntExcellent()) + 85) : 100)));
	}

	/**
	 * 
	 * <br>
	 * Created on: May 30, 2012 3:25:37 PM
	 * 
	 * @param ftbNetworkTask
	 */
	private void getNetWorkDataTask(FtbNetworkTask ftbNetworkTask) {
		// (2)任務提取條件
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("nmNetworkTaskId", ftbNetworkTask.getNmNetworkTaskId());

		// (3)分析解析業務健康度數據
		logger.info("Exec proc_create_view_BussHealth2FX use param:[{}]", map);

		manager = new NetWorkTaskManagerImpl();
		try {
			manager.update("proc_create_view_BussHealth2FX", map);
		} catch (SQLException e) {
			logger.error(
					"{}\n MASSAGE : {} \n CAUSE :{} \n CLASS : {}\n",
					new Object[]{"執行業務健康度任務--存儲過程时,报错:", e.getMessage(),
							e.getCause(), e.getClass()});
			updateTaskStatus(ftbNetworkTask, 5);
			manager = null;
			System.exit(1);
		} finally {
			manager = null;
		}
	}

	/**
	 * 
	 * <br>
	 * Created on: May 30, 2012 3:20:41 PM
	 * 
	 * @param ftbNetworkTask
	 * @param status
	 */
	private void updateTaskStatus(FtbNetworkTask ftbNetworkTask, int status) {

		FtbNetworkTask task = new FtbNetworkTask();
		task.setNmNetworkTaskId(ftbNetworkTask.getNmNetworkTaskId());
		task.setIntTaskStatus(status);

		boolean flag = true;
		int i = 0;
		while (flag) {
			if (i++ > 50) {
				logger.warn("Data Net Work task update task status error.");
				manager = null;
				System.exit(0);
			}

			try {
				manager = new NetWorkTaskManagerImpl();
				manager.update("updateFtbNetworkTask", task);
				flag = false;
			} catch (SQLException e) {
				if (CommonErrorUtil.isTableLockError(e))
					logger.warn("Times[{}]-Table lock,waiting...msg:[{}].", i,
							e.getCause().getMessage());
				else
					logger.error(
							"執行{}任務--{}.{}任務更新任務狀態為執行中时,报错:\n MASSAGE : {} \n CAUSE :{} \n CLASS : {}\n",
							new Object[]{"自定義輸出",
									ftbNetworkTask.getNmNetworkTaskId(),
									ftbNetworkTask.getVcTaskName(),
									e.getMessage(), e.getCause(), e.getClass()});

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

	/**
	 * 
	 * <br>
	 * Created on: May 30, 2012 3:08:18 PM
	 * 
	 * @return
	 */
	private List<FtbNetworkTask> getFtbNetWorkTaskList() {
		List<FtbNetworkTask> list = null;
		manager = new NetWorkTaskManagerImpl();
		try {
			list = manager.getFtbNetworkTaskList();
		} catch (SQLException e) {
			logger.error(
					"{}\n MASSAGE : {} \n CAUSE :{} \n CLASS : {}\n",
					new Object[]{"執行業務健康度任務--查詢任務狀態為未執行的任務时,报错:",
							e.getMessage(), e.getCause(), e.getClass()});
			list = null;
		} finally {
			manager = null;
		}

		return list;
	}

	/**
	 * 
	 * <br>
	 * Created on: May 30, 2012 3:02:36 PM
	 * 
	 * @return
	 */
	private int getRunningTask() {
		Integer count = null;
		manager = new NetWorkTaskManagerImpl();
		try {
			count = manager.getRunningTask();
		} catch (SQLException e) {
			logger.error(
					"{}\n MASSAGE : {} \n CAUSE :{} \n CLASS : {}\n",
					new Object[]{"執行業務健康度任務--查詢是否存在正在執行的任務时,报错:",
							e.getMessage(), e.getCause(), e.getClass()});
			manager = null;
			System.exit(0);
		} finally {
			manager = null;
		}

		return count;
	}

}
