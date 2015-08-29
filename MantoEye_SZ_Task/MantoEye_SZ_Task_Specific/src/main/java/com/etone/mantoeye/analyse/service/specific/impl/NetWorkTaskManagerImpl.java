/**
 *  com.etone.mantoeye.analyse.service.specific.impl.MantoEye_SZ_Task_Specific 
 */
package com.etone.mantoeye.analyse.service.specific.impl;

import java.sql.SQLException;
import java.util.List;

import org.myhkzhen.dao.core.IExecDbOperateDao;
import org.myhkzhen.dao.core.impl.write.WriteExecDaoImpl;

import com.etone.mantoeye.analyse.domain.network.FtbNetworkConfigure;
import com.etone.mantoeye.analyse.domain.network.FtbNetworkTask;
import com.etone.mantoeye.analyse.domain.network.NetWorkParam;
import com.etone.mantoeye.analyse.service.specific.INetWorkTaskManager;

/**
 * @author Wu Zhenzhen
 * @version May 30, 2012 3:04:22 PM
 */
public class NetWorkTaskManagerImpl implements INetWorkTaskManager {

	private IExecDbOperateDao execDbOperateDao;

	/**
	 * 
	 */
	public NetWorkTaskManagerImpl() {
		execDbOperateDao = new WriteExecDaoImpl();
	}

	/**
	 * 讀出是否有運行中的任務
	 */
	public Integer getRunningTask() throws SQLException {
		return (Integer) execDbOperateDao.queryForObject("getRunningTask");
	}

	public List<FtbNetworkTask> getFtbNetworkTaskList() throws SQLException {
		return (List<FtbNetworkTask>) execDbOperateDao
				.queryForList("getFtbNetworkTaskList");
	}

	/**
	 * 更新等操作
	 */
	public int update(String sqlId, Object param) throws SQLException {
		return execDbOperateDao.update(sqlId, param);
	}
	public FtbNetworkConfigure getFtbNetworkConfigure(int intType)
			throws SQLException {
		return (FtbNetworkConfigure) execDbOperateDao.queryForObject(
				"getFtbNetworkConfigure", intType);
	}
	/**
	 * 取得業務健康度所求出的值
	 */
	public Double getNetWorkCount(String selectSql, String tableName,
			Long nmNetworkTaskId) throws SQLException {
		NetWorkParam param = new NetWorkParam();
		param.setSelectSql(selectSql);
		param.setTableName(tableName);
		param.setNmNetworkTaskId(nmNetworkTaskId);
		return (Double) execDbOperateDao.queryForObject("getNetWorkCount",
				param);
	}
	/**
	 * 向業務健康度表中添加結果數據
	 */
	public void addNetWorkResult(Long nmNetworkTaskId, int intType,
			double intSale, double intCount, double markValue)
			throws SQLException {
		NetWorkParam param = new NetWorkParam();
		param.setIntCount(intCount);
		param.setIntSale(intSale);
		param.setIntType(intType);
		param.setMarkValue(markValue);
		param.setNmNetworkTaskId(nmNetworkTaskId);
		execDbOperateDao.insert("addNetWorkResult", param);

	}
}
