/**
 *  com.etone.mantoeye.analyse.service.specific.MantoEye_SZ_Task_Specific 
 */
package com.etone.mantoeye.analyse.service.specific;

import java.sql.SQLException;
import java.util.List;

import com.etone.mantoeye.analyse.domain.network.FtbNetworkConfigure;
import com.etone.mantoeye.analyse.domain.network.FtbNetworkTask;

/**
 * @author Wu Zhenzhen
 * @version May 30, 2012 3:04:10 PM
 */
public interface INetWorkTaskManager {
	/**
	 * 讀出是否有運行中的任務
	 * 
	 * @return
	 * @throws SQLException
	 */
	public Integer getRunningTask() throws SQLException;
	/**
	 * 讀出當前需要運行的任務
	 * 
	 * @return
	 */
	public List<FtbNetworkTask> getFtbNetworkTaskList() throws SQLException;
	/**
	 * 更新等操作
	 * 
	 * @param sqlId
	 * @param param
	 * @return
	 * @throws SQLException
	 */
	public int update(String sqlId, Object param) throws SQLException;
	/**
	 * 讀取健康度配置表信息
	 * 
	 * @param intType
	 * @return
	 * @throws SQLException
	 */
	public FtbNetworkConfigure getFtbNetworkConfigure(int intType)
			throws SQLException;

	/**
	 * 取得業務健康度所求出的值
	 * 
	 * @param selectSql
	 * @param tableName
	 * @param nmNetworkTaskId
	 * @return
	 * @throws SQLException
	 */
	public Double getNetWorkCount(String selectSql, String tableName,
			Long nmNetworkTaskId) throws SQLException;

	/**
	 * 向業務健康度表中添加結果數據
	 * 
	 * @param nmNetworkTaskId
	 * @param intType
	 * @param intSale
	 * @param intCount
	 * @param markValue
	 * @throws SQLException
	 */
	public void addNetWorkResult(Long nmNetworkTaskId, int intType,
			double intSale, double intCount, double markValue)
			throws SQLException;
}
