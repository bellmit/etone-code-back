/**
 *  com.etone.mantoeye.analyse.user.impl.MantoEye_SZ_Task_Common 
 */
package com.etone.mantoeye.analyse.user.impl;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.myhkzhen.util.date.DateTimeUtils;

import com.etone.mantoeye.analyse.domain.AnalyseParam;
import com.etone.mantoeye.analyse.service.common.IExecTaskManager;
import com.etone.mantoeye.analyse.service.impl.common.ExecTaskManagerImpl;
import com.etone.mantoeye.analyse.user.IUserDefineAccess;

/**
 * 黑莓用户流量(天)
 * 
 * @author Wu Zhenzhen
 * @version May 4, 2012 3:07:32 PM
 */
public class RuleTop1000 implements IUserDefineAccess {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.etone.mantoeye.analyse.user.IUserDefineAccess#execute(com.etone.mantoeye
	 * .analyse.domain.AnalyseParam)
	 */
	public void execute(AnalyseParam analyseParam) throws SQLException {
		logger.info(
				"[User Process] --- Blackberry user flush thread start and param [{}].",
				analyseParam.getSourceTableName());

		long begindate = System.currentTimeMillis();
		final Date dtStatTime = analyseParam.getDtLoadedTime();
		final String tableName = "ftbStatDayBusFlushOrder";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("statdate", dtStatTime);

		logger.info("Exec create table [{}_{}] blackberry user flush(day).",
				tableName,
				DateTimeUtils.getParseDateToStr(dtStatTime, "yyyyMMdd"));

		IExecTaskManager manager = new ExecTaskManagerImpl();
		try {
			manager.execute("proc_create_data_ruletop1000", map);
			manager = null;
			map = null;
		} finally {
			manager = null;
			map = null;
		}

		logger.info(
				"Exec create table {}_{} blackberry user flush(day),use time: {} s\\t",
				new Object[]{
						tableName,
						DateTimeUtils.getParseDateToStr(dtStatTime, "yyyyMMdd"),
						((System.currentTimeMillis() - begindate) / 1000)});

	}

}
