/**
 *  com.etone.mantoeye.analyse.service.common.MantoEye_SZ_Task_Common 
 */
package com.etone.mantoeye.analyse.service.common;

import java.sql.SQLException;

import org.myhkzhen.util.log.Logger;
import org.myhkzhen.util.log.LoggerFactory;

import com.etone.mantoeye.analyse.domain.AnalyseParam;

/**
 * 从指定文件導數據到數據庫表中
 * 
 * @author Wu Zhenzhen
 * @version May 9, 2012 10:32:20 AM
 */
public interface IImportDataMananger {
	static final Logger logger = LoggerFactory
			.getLogger(IImportDataMananger.class);
	/**
	 * 从指定文件導數據到數據庫表中 <br>
	 * Created on: May 4, 2012 11:35:57 AM
	 * 
	 * @param analyseParam
	 * @throws SQLException
	 */
	public void execImport(AnalyseParam analyseParam) throws SQLException;
	
	/**
	 * 从指定文件導數據到數據庫表中 <br>
	 * Created on: May 4, 2012 11:35:57 AM
	 * 
	 * @param analyseParam
	 * @throws SQLException
	 */
	public void execImport(AnalyseParam analyseParam, String fromPath)
			throws SQLException;

}
