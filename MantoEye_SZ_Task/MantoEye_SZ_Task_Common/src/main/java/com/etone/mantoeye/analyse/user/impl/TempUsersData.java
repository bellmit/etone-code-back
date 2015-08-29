/**
 *  com.etone.mantoeye.analyse.user.impl.MantoEye_SZ_Task_Common 
 */
package com.etone.mantoeye.analyse.user.impl;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.myhkzhen.util.date.DateTimeUtils;
import org.myhkzhen.util.log.Logger;
import org.myhkzhen.util.log.LoggerFactory;
import org.myhkzhen.util.properties.PropertiesUtil;

import com.etone.mantoeye.analyse.domain.AnalyseParam;
import com.etone.mantoeye.analyse.service.common.IExecTaskManager;
import com.etone.mantoeye.analyse.service.impl.common.ExecTaskManagerImpl;
import com.etone.mantoeye.analyse.user.IUserDefineAccess;
import com.etone.mantoeye.analyse.util.constant.Constant;
import com.etone.mantoeye.analyse.util.error.CommonErrorUtil;

/**
 * @author Wu Zhenzhen
 * @version May 4, 2012 3:08:00 PM
 */
public class TempUsersData implements IUserDefineAccess {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.etone.mantoeye.analyse.user.IUserDefineAccess#execute(com.etone.mantoeye
	 * .analyse.domain.AnalyseParam)
	 */
	public void execute(AnalyseParam analyseParam) throws SQLException {
		logger.info(
				"[User Process] --- Create temp users table (hour) thread start and param[{}].",
				analyseParam.getSourceTableName());

		final String unionName = analyseParam.getSourceTableName();
		final Date dtStatTime = analyseParam.getDtLoadedTime();

		final String config_file = Constant.APP_HOME + Constant.SEPARATOR_CHAR
				+ Constant.CONFIG_PATH + "function.properties";

		logger.debug("Config file --> [{}].", config_file);

		final String tempUsersTableNames = PropertiesUtil.getConfigProperties(
				"tempUsersTableNames", config_file);

		logger.debug("Temp users table names :[{}].", tempUsersTableNames);

		final String[] tableNames = StringUtils.split(tempUsersTableNames, ',');

		final int index = tableNames.length;

		logger.debug("Temp user nums table name:{}.", tableNames);

		int threadNum = 4;
		ExecutorService threadPool = Executors.newFixedThreadPool(threadNum);

		for (int i = 0; i < index; i++) {

			final String tableName = tableNames[i];

			Runnable createData = new ExecTmpUsers(unionName, tableName,
					dtStatTime, index, i);

			threadPool.execute(createData);

		}

		threadPool.shutdown();
		threadPool = null;
	}

	class ExecTmpUsers implements Runnable {

		private Logger logg = LoggerFactory.getLogger(ExecTmpUsers.class);

		private String unionName;
		private String tableName;
		private Date dtStatTime;
		private int index;
		private int i;

		/**
		 * <br>
		 * Created on: Jan 6, 2013 10:42:42 AM
		 * 
		 * Created by: Wu Zhenzhen
		 * 
		 * @param unionName
		 * @param tableName
		 * @param dtStatTime
		 */
		public ExecTmpUsers(String unionName, String tableName,
				Date dtStatTime, int index, int i) {
			this.unionName = unionName;
			this.tableName = tableName;
			this.dtStatTime = dtStatTime;
			this.i = i;
			this.index = index;
		}
		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Runnable#run()
		 */
		public void run() {
			long begindate = System.currentTimeMillis();
			Long countUsers = -100l;
			String warnInfo = "";
			String wi = "";
			long cu = -100l;
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("statdate", dtStatTime);
			map.put("tablename", tableName);
			map.put("unionname", unionName);
			map.put("countUsers", countUsers);
			map.put("warnInfo", warnInfo);

			logg.info(
					"Exec create {}_{}.({}) temp users",
					new Object[]{
							tableName,
							DateTimeUtils.getParseDateToStr(dtStatTime,
									"yyyyMMdd"),
							DateTimeUtils.getParseDateToStr(dtStatTime)});

			IExecTaskManager manager = new ExecTaskManagerImpl();

			try {
				manager.execute("proc_create_data_users", map);
				cu = NumberUtils.toLong(String.valueOf(map.get("countUsers")),
						-1000l);
				wi = String.valueOf(map.get("warnInfo"));

				manager = null;
				map = null;
			} catch (SQLException e) {
				if (CommonErrorUtil.isTableLockError(e))
					logger.warn("Table lock,waiting...msg:[{}].", e.getCause()
							.getMessage());
				else
					logger.error(
							"表[{}]生成临时辅助用户数表(小时)任務时,报错:\n MASSAGE : {} \n CAUSE :{} \n CLASS : {}\n",
							new Object[]{tableName, e.getMessage(),
									e.getCause(), e.getClass()});
			} finally {
				manager = null;
				map = null;
			}

			logg.info(
					"Exec create table [({}-{})] - {}_{}.({}) \n - temp users [{}],Exec proc return info : [{}] \n - use time: {} s\\t",
					new Object[]{
							index,
							i,
							tableName,
							DateTimeUtils.getParseDateToStr(dtStatTime,
									"yyyyMMdd"),
							DateTimeUtils.getParseDateToStr(dtStatTime),
							cu,
							((null == wi || "null".equalsIgnoreCase(wi.trim())
									|| "".equals(wi.trim())
									? " not new buss data "
									: wi)),
							((System.currentTimeMillis() - begindate) / 1000)});
		}
	}
}
