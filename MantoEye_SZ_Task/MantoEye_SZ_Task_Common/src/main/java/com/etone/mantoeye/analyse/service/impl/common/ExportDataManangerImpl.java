/**
 *  com.etone.mantoeye.analyse.service.impl.common.MantoEye_SZ_Task_Common 
 */
package com.etone.mantoeye.analyse.service.impl.common;

import java.io.File;
import java.sql.SQLException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.myhkzhen.dao.core.IExecDbOperateDao;
import org.myhkzhen.dao.core.impl.query.QueryExecDaoImpl;
import org.myhkzhen.util.file.FileUtil;

import com.etone.mantoeye.analyse.domain.AnalyseParam;
import com.etone.mantoeye.analyse.service.common.IExportDataMananger;
import com.etone.mantoeye.analyse.util.constant.Constant;

/**
 * @author Wu Zhenzhen
 * @version May 4, 2012 11:36:47 AM
 */
public class ExportDataManangerImpl implements IExportDataMananger {

	// private static final Logger logger = LoggerFactory
	// .getLogger(ExportDataManangerImpl.class);

	/**
	 * 寫數據庫連接操作session
	 */
	private IExecDbOperateDao execDbOperateDao = null;

	/**
	 * 
	 */
	public ExportDataManangerImpl() {
		execDbOperateDao = new QueryExecDaoImpl();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.etone.mantoeye.analyse.service.common.IExportDataMananger#execExport
	 * (com.etone.mantoeye.analyse.domain.AnalyseParam)
	 */
	public void execExport(AnalyseParam analyseParam) throws SQLException {
		execExport(analyseParam, Constant.DEFAULT_DATA_TEMP_PATH);
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.etone.mantoeye.analyse.service.common.IExportDataMananger#execExport
	 * (com.etone.mantoeye.analyse.domain.AnalyseParam, java.lang.String)
	 */
	public void execExport(AnalyseParam analyseParam, String toPath)
			throws SQLException {

		logger.info("Export data to [{}{}].", toPath,
				analyseParam.getFileName());

		String option = getOption(toPath, analyseParam.getFileName());
		logger.debug("Option --> [{}].", option);
		analyseParam.setOption(option);

		execDbOperateDao.queryForList(analyseParam.getSqlMapId(), analyseParam);

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
		}

	}

	/**
	 * 獲取查詢sql的option <br>
	 * Created on: May 4, 2012 11:45:54 AM
	 * 
	 * @param toPath
	 * @param fileName
	 * @return
	 */
	private String getOption(String toPath, String fileName) {

		StringBuffer option = new StringBuffer();
		option.append("set temporary option TEMP_EXTRACT_NAME1='");
		option.append(toPath).append(fileName).append("';");
		option.append("set temporary option TEMP_EXTRACT_COLUMN_DELIMITER=',';");
		option.append("set temporary option TEMP_EXTRACT_BINARY='OFF';");
		option.append("set temporary option TEMP_EXTRACT_SWAP='OFF';");

		return option.toString();

	}

}
