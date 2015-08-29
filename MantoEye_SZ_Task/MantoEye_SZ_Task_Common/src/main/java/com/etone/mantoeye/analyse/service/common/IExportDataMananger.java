/**
 *  com.etone.mantoeye.analyse.service.common.MantoEye_SZ_Task_Common 
 */
package com.etone.mantoeye.analyse.service.common;

import java.sql.SQLException;

import org.myhkzhen.util.log.Logger;
import org.myhkzhen.util.log.LoggerFactory;

import com.etone.mantoeye.analyse.domain.AnalyseParam;

/**
 * @author Wu Zhenzhen
 * @version May 4, 2012 11:33:52 AM
 */
public interface IExportDataMananger {
	static final Logger logger = LoggerFactory
			.getLogger(IExportDataMananger.class);

	/**
	 * 導出數據到文件操作 <br>
	 * Created on: May 4, 2012 11:35:57 AM
	 * 
	 * @param analyseParam
	 * @throws SQLException
	 */
	public void execExport(AnalyseParam analyseParam) throws SQLException;

	/**
	 * 導出數據到文件到指定路徑操作
	 * 
	 * <br>
	 * Created on: May 4, 2012 11:41:27 AM
	 * 
	 * @param analyseParam
	 * @param toPath
	 * @throws SQLException
	 */
	public void execExport(AnalyseParam analyseParam, String toPath)
			throws SQLException;

}
