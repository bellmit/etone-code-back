/**
 *  com.etone.mantoeye.service.common.MantoEye_SZ_Task_Common 
 */
package com.etone.mantoeye.analyse.service.common;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.myhkzhen.util.log.Logger;
import org.myhkzhen.util.log.LoggerFactory;

import com.etone.mantoeye.analyse.domain.CurrentTask;

/**
 * 普通時天周月任務操作Manager
 * 
 * @author Wu Zhenzhen
 * @version Apr 25, 2012 11:29:15 AM
 */
public interface IExecTaskManager {

	static final Logger logger = LoggerFactory
			.getLogger(IExecTaskManager.class);

	/**
	 * 初始化任務列表 <br>
	 * Created on: Apr 25, 2012 11:30:14 AM
	 * 
	 * @throws SQLException
	 */
	public int initTaskList() throws SQLException;

	/**
	 * 
	 * 向tbTask表中添加新小時任務 <br>
	 * Created on: Apr 27, 2012 4:24:00 PM
	 * 
	 * @throws SQLException
	 */
	public void createHourTaskList() throws SQLException;

	/**
	 * 建立臨時用戶數表 <br>
	 * Created on: May 16, 2012 3:57:14 PM
	 * 
	 * @throws SQLException
	 */
	public void createHourTempUsers() throws SQLException;

	/**
	 * 取得當前tbTask裱中未執行的任務 <br>
	 * Created on: Apr 27, 2012 5:03:52 PM
	 * 
	 * @return
	 * @throws SQLException
	 */
	public List<CurrentTask> getCurrentTaskList() throws SQLException;

	/**
	 * 更新任務狀態 <br>
	 * Created on: Apr 27, 2012 5:09:52 PM
	 * 
	 * @param intTaskId
	 * @param state
	 * @throws SQLException
	 */
	public void updateCurrentTaskState(Long intTaskId, int state)
			throws SQLException;

	/**
	 * 根據任務ID取得相關任務信息
	 * 
	 * @param intTaskId
	 * @return
	 * @throws SQLException
	 */
	public CurrentTask getCurrentTask(Long intTaskId) throws SQLException;

	/**
	 * 完成任务,做三个工作:<br />
	 * 1.删除当前执行任务;<br />
	 * 2.写统计任务日志表;<br />
	 * 3.更新事实表操作日志表的任务完成信息
	 * 
	 * @param currentTask
	 * @throws SQLException
	 */
	public void completeCurrentTask(CurrentTask currentTask)
			throws SQLException;

	/**
	 * 向tbTask表中添加新天任務 <br>
	 * Created on: May 8, 2012 3:34:15 PM
	 * 
	 * @throws SQLException
	 */
	public void createDayTaskList() throws SQLException;

	/**
	 * 向tbTask表中添加新周任務 <br>
	 * Created on: May 8, 2012 3:39:32 PM
	 * 
	 * @throws SQLException
	 */
	public void createWeekTaskList() throws SQLException;

	/**
	 * 向tbTask表中添加新月任務 <br>
	 * Created on: May 8, 2012 3:41:58 PM
	 * 
	 * @throws SQLException
	 */
	public void createMonthTaskList() throws SQLException;

	/**
	 * 批量生成要重做的天周月任務 <br>
	 * Created on: May 8, 2012 3:45:32 PM
	 * 
	 * @throws SQLException
	 */
	public void createReDoTaskList() throws SQLException;

	/**
	 * ROLLBACEK事實表日誌中status為3和4的記錄 <br>
	 * Created on: May 16, 2012 6:58:25 PM
	 * 
	 * @throws SQLException
	 */
//	public void updateHourFactTableStatus(Map<String, String> map)
//			throws SQLException;

	/**
	 * 判斷表是否鎖 <br>
	 * Created on: May 16, 2012 8:06:07 PM
	 * 
	 * @return
	 */
	public boolean tableIsLocks(String tableName);

	/**
	 * 是否存在狀態為4的事實表數據 <br>
	 * Created on: May 16, 2012 10:18:59 PM
	 * 
	 * @throws SQLException
	 */
	public String getRunningTask() throws SQLException;

	/**
	 * 
	 * <br>
	 * Created on: May 17, 2012 4:42:46 AM
	 * 
	 * @param intTaskId
	 * @throws SQLException
	 */
	public void updateCurrentTaskStateList(String intTaskId)
			throws SQLException;

	public List<CurrentTask> completeCurrentTaskList(String taskId)
			throws SQLException;

	public void writeCurrentTaskLog(CurrentTask currentTask)
			throws SQLException;

	public void deleteCurrentTask(CurrentTask currentTask) throws SQLException;

	public void updateTaskCompleted(CurrentTask currentTask)
			throws SQLException;

	public void updateHourFactTableStatus() throws SQLException;

	public int initHourFactTableStatus() throws SQLException;

	public int getRunningUDTask() throws SQLException;

	public int inittbUDTimeStatus() throws SQLException;
	public void createHourSignalFactData() throws SQLException;

	public int initFactTable() throws SQLException;

	/**
	 * 根据ibatisId从XML配置文件中,读出SQL,并查询出数据
	 * 
	 * @param sqlMapId
	 *            SQL MAP ID
	 * @param param
	 *            參數
	 * @throws SQLException
	 *             異常
	 */
	public void execute(String sqlMapId, Object param) throws SQLException;

}
