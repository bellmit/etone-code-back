/**
 * com.etone.mantoeye.analyse.service.impl.common.HiTechFairElexconDataManangerImpl.java
 */
package com.etone.mantoeye.analyse.service.impl.common;

import java.sql.SQLException;
import java.util.List;

import org.myhkzhen.dao.core.IExecDbOperateDao;
import org.myhkzhen.dao.core.impl.query.QueryExecDaoImpl;
import org.myhkzhen.util.log.Logger;
import org.myhkzhen.util.log.LoggerFactory;

import com.etone.mantoeye.analyse.domain.AnalyseParam;
import com.etone.mantoeye.analyse.service.common.IHiTechFairElexconDataMananger;

/**
 * @author Wu Zhenzhen
 * @version Nov 15, 2012 4:53:04 PM
 */
public class HiTechFairElexconDataManangerImpl
		implements
			IHiTechFairElexconDataMananger {

	private static final Logger logger = LoggerFactory
			.getLogger(HiTechFairElexconDataManangerImpl.class);

	/**
	 * 寫數據庫連接操作session
	 */
	private IExecDbOperateDao execDbOperateDao = null;

	/**
	 * 
	 */
	public HiTechFairElexconDataManangerImpl() {
		execDbOperateDao = new QueryExecDaoImpl();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.etone.mantoeye.analyse.service.common.IHiTechFairElexconDataMananger
	 * #getHiTechFairElexconDataList
	 * (com.etone.mantoeye.analyse.domain.AnalyseParam)
	 */
	@SuppressWarnings("unchecked")
	public List<String> getHiTechFairElexconDataList(AnalyseParam param)
			throws SQLException {
		logger.debug("Query hi tech fair elexcon data to list.");

		List<String> list = (List<String>) execDbOperateDao.queryForList(
				param.getSqlMapId(), param);

		return list;
	}

}
