/**
 *  com.etone.mantoeye.analyse.service.impl.common.MantoEye_SZ_Task_Common 
 */
package com.etone.mantoeye.analyse.service.impl.common;

import java.sql.SQLException;
import java.util.List;

import org.myhkzhen.dao.core.IExecDbOperateDao;
import org.myhkzhen.dao.core.impl.write.WriteExecDaoImpl;

import com.etone.mantoeye.analyse.domain.ui.TbUDTimeBean;
import com.etone.mantoeye.analyse.service.common.IUDInterfaceManager;

/**
 * @author Wu Zhenzhen
 * @version May 21, 2012 11:25:40 AM
 */
public class UDInterfaceManagerImpl implements IUDInterfaceManager {

	/**
	 * 寫數據庫連接操作session
	 */
	private IExecDbOperateDao execDbOperateDao = null;

	public UDInterfaceManagerImpl() {
		execDbOperateDao = new WriteExecDaoImpl();
	}

	public TbUDTimeBean getRunningTask() throws SQLException {
		logger.debug("Check task is running..");
		return (TbUDTimeBean) execDbOperateDao
				.queryForObject("getRunningUIData");
	}

	public TbUDTimeBean getTbUDTime() throws SQLException {
		return (TbUDTimeBean) execDbOperateDao.queryForObject("getTbUDTime");
	}

	public Long getFtbOverViewData(TbUDTimeBean tbUDTime) throws SQLException {
		return (Long) execDbOperateDao.queryForObject("getFtbOverViewData",
				tbUDTime);
	}

	public void update(String sqlId, Object param) throws SQLException {
		execDbOperateDao.update(sqlId, param);
	}

	public List<String> getFtbOverViewSport(TbUDTimeBean param)
			throws SQLException {
		return (List<String>) execDbOperateDao.queryForList(
				"getFtbOverViewSport", param);
	}

	public List<String> getFtbOverView(TbUDTimeBean param) throws SQLException {
		return (List<String>) execDbOperateDao.queryForList("getFtbOverView",
				param);
	}

	public List<String> getFtbOverView2All(TbUDTimeBean param)
			throws SQLException {
		return (List<String>) execDbOperateDao.queryForList(
				"getFtbOverView2All", param);
	}

	public List<String> getFtbOverView2Wireless(TbUDTimeBean param)
			throws SQLException {
		return (List<String>) execDbOperateDao.queryForList(
				"getFtbOverView2Wireless", param);
	}

	public void updateStatTime(TbUDTimeBean tbUDTime) throws SQLException {
		execDbOperateDao.update("updateStatTime", tbUDTime);
	}

}
