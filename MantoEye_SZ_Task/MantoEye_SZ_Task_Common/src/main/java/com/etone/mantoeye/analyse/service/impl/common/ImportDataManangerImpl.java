/**
 *  com.etone.mantoeye.analyse.service.impl.common.MantoEye_SZ_Task_Common 
 */
package com.etone.mantoeye.analyse.service.impl.common;

import java.sql.SQLException;

import org.myhkzhen.dao.core.IExecDbOperateDao;
import org.myhkzhen.dao.core.impl.write.WriteExecDaoImpl;

import com.etone.mantoeye.analyse.domain.AnalyseParam;
import com.etone.mantoeye.analyse.service.common.IImportDataMananger;
import com.etone.mantoeye.analyse.util.constant.Constant;

/**
 * @author Wu Zhenzhen
 * @version May 9, 2012 10:34:13 AM
 */
public class ImportDataManangerImpl implements IImportDataMananger {

	/**
	 * 寫數據庫連接操作session
	 */
	private IExecDbOperateDao execDbOperateDao = null;

	/**
	 * 
	 */
	public ImportDataManangerImpl() {
		execDbOperateDao = new WriteExecDaoImpl();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.etone.mantoeye.analyse.service.common.IImportDataMananger#execImport
	 * (com.etone.mantoeye.analyse.domain.AnalyseParam, java.lang.String)
	 */
	public void execImport(AnalyseParam analyseParam) throws SQLException {
		execImport(analyseParam, Constant.DEFAULT_DATA_TEMP_PATH);
	}

	/**
	 * 
	 * <br>
	 * Created on: May 9, 2012 10:39:33 AM
	 * 
	 * @param fileName
	 * @return
	 */
	private String getOption(String fileName, String fromPath) {
		StringBuffer option = new StringBuffer();
		option.append("from '").append(Constant.DEFAULT_DATA_TEMP_PATH)
				.append(fileName).append("' ");
		option.append("delimited by ','  escapes off quotes off with checkpoint on;");

		return option.toString();

	}

	public void execImport(AnalyseParam analyseParam, String fromPath)
			throws SQLException {
		logger.debug(
				"Exec {} from file {}.",
				new Object[] { analyseParam.getSqlMapId(),
						analyseParam.getFileName() });

		String option = getOption(analyseParam.getFileName(), fromPath);
		logger.debug("Option --> [{}].", option);

		analyseParam.setOption(option);

		execDbOperateDao.update(analyseParam.getSqlMapId(), analyseParam);
	}

}
