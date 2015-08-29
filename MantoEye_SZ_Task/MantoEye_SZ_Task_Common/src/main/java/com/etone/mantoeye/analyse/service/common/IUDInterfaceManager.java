/**
 *  com.etone.mantoeye.analyse.service.common.MantoEye_SZ_Task_Common 
 */
package com.etone.mantoeye.analyse.service.common;

import java.sql.SQLException;
import java.util.List;

import org.myhkzhen.util.log.Logger;
import org.myhkzhen.util.log.LoggerFactory;

import com.etone.mantoeye.analyse.domain.ui.TbUDTimeBean;

/**
 * @author Wu Zhenzhen
 * @version May 21, 2012 11:25:11 AM
 */
public interface IUDInterfaceManager {

	static final Logger logger = LoggerFactory
			.getLogger(IUDInterfaceManager.class);

	/**
	 * 增加判斷是否有正在運行的task
	 * <p />
	 * 2012-3-27 下午04:36:36
	 * 
	 * @return
	 * @throws SQLException
	 */
	public TbUDTimeBean getRunningTask() throws SQLException;

	/**
	 * 取得數據時間
	 * 
	 * @return
	 * @throws SQLException
	 */
	public TbUDTimeBean getTbUDTime() throws SQLException;
	/**
	 * 查看是否有傳入時間的數據，防止分佈式幷發統計數據時數據不同步的問題 2011年12月22日14:38:07
	 * 
	 * @param dtStatTime
	 * @return
	 */
	public Long getFtbOverViewData(TbUDTimeBean tbUDTime) throws SQLException;

	/**
	 * 執行存儲過程
	 * 
	 * @param string
	 * @param map
	 */
	public void update(String sqlId, Object param) throws SQLException;

	/**
	 * 用于生成FTP文件的查询
	 * 
	 * @param param
	 * @return
	 */
	public List<String> getFtbOverViewSport(TbUDTimeBean param)
			throws SQLException;
	/**
	 * 用于生成FTP文件的查询
	 * 
	 * @param param
	 * @return
	 * @throws SQLException
	 */
	public List<String> getFtbOverView(TbUDTimeBean param) throws SQLException;
	/**
	 * 用于生成FTP文件的查询
	 * 
	 * @param param
	 * @return
	 * @throws SQLException
	 */
	public List<String> getFtbOverView2All(TbUDTimeBean param)
			throws SQLException;
	/**
	 * 用于生成FTP文件的查询
	 * 
	 * @param param
	 * @return
	 * @throws SQLException
	 */
	public List<String> getFtbOverView2Wireless(TbUDTimeBean param)
			throws SQLException;
	/**
	 * 跟新狀態
	 * 
	 * @param tbUDTime
	 * @throws SQLException
	 */
	public void updateStatTime(TbUDTimeBean tbUDTime) throws SQLException;
}
