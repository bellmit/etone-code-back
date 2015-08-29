/**
 *  com.etone.mantoeye.analyse.service.specific.MantoEye_SZ_Task_Specific 
 */
package com.etone.mantoeye.analyse.service.specific;

import java.sql.SQLException;
import java.util.List;

import com.etone.mantoeye.analyse.domain.extract.FtbDataGetterDecTask;
import com.etone.mantoeye.analyse.domain.extract.FtbDataGetterFilter;
import com.etone.mantoeye.analyse.domain.extract.FtbDataGetterTask;
import com.etone.mantoeye.analyse.domain.extract.FtbFilterColumnMapTask;
import com.etone.mantoeye.analyse.domain.extract.FtbOutColumn;

/**
 * @author Wu Zhenzhen
 * @version May 22, 2012 4:08:50 PM
 */
public interface IDataGetterTaskManager {
	/**
	 * 读出当前需要运行的任务
	 * 
	 * @return
	 * @throws SQLException
	 */
	public FtbDataGetterTask getFtbDataGetterTask() throws SQLException;
	/**
	 * 更新任務狀態
	 * 
	 * @param nmDataGetterTaskId
	 * @param intTaskStatus
	 * @throws SQLException
	 */
	public void updateFtbDataGetterTask(Long nmDataGetterTaskId,
			int intTaskStatus) throws SQLException;
	/**
	 * 取得任務輸出列
	 * 
	 * @param nmDataGetterTaskId
	 * @return
	 * @throws SQLException
	 */
	public List<FtbOutColumn> getFtbOutColumnList(Long nmDataGetterTaskId)
			throws SQLException;
	/**
	 * 取得過濾列
	 * 
	 * @param nmDataGetterTaskId
	 * @return
	 * @throws SQLException
	 */
	public List<FtbFilterColumnMapTask> getFtbFilterColumnMapTaskList(
			Long nmDataGetterTaskId) throws SQLException;
	/**
	 * 取得過濾值
	 * 
	 * @param ftbFilterColumnMapTask
	 * @return
	 * @throws SQLException
	 */
	public List<FtbFilterColumnMapTask> getFtbFilterColumnMapTaskValueList(
			FtbFilterColumnMapTask ftbFilterColumnMapTask) throws SQLException;
	/**
	 * 執行sqlId操作
	 * 
	 * @param sqlId
	 * @param param
	 * @return
	 * @throws SQLException
	 */
	public int execute(String sqlId, Object param) throws SQLException;
	/**
	 * 保存文件信息
	 * 
	 * @param fileInfo
	 * @throws SQLException
	 */
	public void saveFileInfo(FtbDataGetterDecTask fileInfo) throws SQLException;
	/**
	 * 数据提取过滤条件表
	 * 
	 * @param nmDataGetterTaskId
	 * @return
	 * @throws SQLException
	 */
	public List<FtbDataGetterFilter> getFtbDataGetterFilterList(
			Long nmDataGetterTaskId) throws SQLException;
	/**
	 * 数据提取过滤条件表值
	 * 
	 * @param filter
	 * @return
	 */
	public List<FtbDataGetterFilter> getFtbDataGetterFilterValueList(
			FtbDataGetterFilter filter) throws SQLException;
}
