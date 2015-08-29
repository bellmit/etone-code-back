package com.symbol.app.mantoeye.service.alarm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.symbol.app.mantoeye.dao.alarm.FlushAlarmDao;
import com.symbol.app.mantoeye.dto.alarm.AlarmBean;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.tools.gtgrid.model.SortInfo;
import com.symbol.wp.tools.gtgrid.server.GridServerHandler;

/**
 * 流量告警SERVER
 * 
 * @author Jane
 * 
 */
@Service
@Transactional
public class FlushAlarmManager {

	public FlushAlarmDao getFlushAlarmDao() {
		return flushAlarmDao;
	}

	@Resource
	public void setFlushAlarmDao(FlushAlarmDao flushAlarmDao) {
		this.flushAlarmDao = flushAlarmDao;
	}

	private FlushAlarmDao flushAlarmDao;

	/**
	 * 初始化页面列表数据
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public List<AlarmBean> initQuery(int year, int month, int day) {
		String tableName = "vAlarmBscFlush";
		String typeName = "BSC";

		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("intYear", year);
		condition.put("intMonth", month);
		condition.put("intDay", day);

		condition.put("tableName", tableName);
		condition.put("typeName", typeName);

		List<AlarmBean> list = new ArrayList<AlarmBean>();
		AlarmBean bean = flushAlarmDao.initQuery2(condition);
		list.add(bean);

		tableName = "vAlarmCgiFlush";
		typeName = "CGI";
		condition.put("tableName", tableName);
		condition.put("typeName", typeName);
		bean = flushAlarmDao.initQuery2(condition);
		list.add(bean);

		tableName = "vAlarmSgsnFlush";
		typeName = "SGSN";
		condition.put("tableName", tableName);
		condition.put("typeName", typeName);
		bean = flushAlarmDao.initQuery2(condition);
		list.add(bean);

		// 新增全網流量告警GPRS
		tableName = "tbAlarmGprsTotalFlush";
		typeName = "全网GPRS";
		condition.put("tableName", tableName);
		condition.put("typeName", typeName);
		bean = flushAlarmDao.initQuery2(condition);
		list.add(bean);
		// 新增全網流量告警TD
		tableName = "tbAlarmGprsTotalFlush";
		typeName = "全网TD";
		condition.put("tableName", tableName);
		condition.put("typeName", typeName);
		bean = flushAlarmDao.initQuery2(condition);
		list.add(bean);
		// ////////////////////////////////////

		// bean = flushAlarmDao.initQuery(condition);

		return list;
	}

	/**
	 * 历史告警记录
	 * 
	 * @param startDate
	 * @param endDate
	 * @param type
	 * @param gridServerHandler
	 * @return
	 */
	public Page<AlarmBean> queryHistoryAlarm(String startDate, String endDate,
			String type, GridServerHandler gridServerHandler, String warmOK,
			String flushOK) {
		Page<AlarmBean> page = new Page<AlarmBean>(20);
		page.setPageSize(gridServerHandler.getPageSize());
		page.setPageNo(gridServerHandler.getPageInfo().getPageNum());
		String tableName = "vAlarmCgiFlush";
		String columnName = "vcCgi";
		String alarmId = "nmAlarmCgiFlushId";
		String typeId = "vcCgi";

		if ("CGI".equals(type)) {
			tableName = "vAlarmCgiFlush";
			columnName = "vcCgi";
			alarmId = "nmAlarmCgiFlushId";
			typeId = "vcCgi";
		} else if ("BSC".equals(type)) {
			tableName = "vAlarmBscFlush";
			columnName = "vcName";
			alarmId = "nmAlarmBscFlushId";
			typeId = "intBscId";
		} else if ("TD".equals(type) || "GPRS".equals(type)) {
			tableName = "tbAlarmGprsTotalFlush";
			columnName = "intRaitype";
			alarmId = "nmAlarmGprsTotalFlushId";
			typeId = "nmAlarmGprsTotalFlushId";
		} else {
			tableName = "vAlarmSgsnFlush";
			columnName = "vcName";
			alarmId = "nmAlarmSgsnFlushId";
			typeId = "intSgsnId";
		}

		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("typeId", typeId);
		condition.put("startDate", startDate);
		condition.put("endDate", endDate);
		condition.put("tableName", tableName);
		condition.put("columnName", columnName);
		condition.put("alarmId", alarmId);
		condition.put("warmOK", warmOK);
		condition.put("flushOK", flushOK);
		condition.put("typeTD", type);

		page.setTotalCount(flushAlarmDao.getTotalCount(condition));

		SortInfo si = gridServerHandler.getSingleSortInfo();

		if (null != si) {
			String fieldName = si.getFieldName();
			String sortAttr = si.getSortOrder();

			fieldName = buildFileName(fieldName);

			page.setOrder(sortAttr);
			page.setOrderBy(fieldName);
		} else {
			page.setOrder("desc");
			page.setOrderBy("fullDate");
		}

		List<AlarmBean> list = flushAlarmDao.queryHistory(page, condition);
		// ---------------------
		list = buildNewList(list);

		page.setResult(list);

		return page;

	}

	private String buildFileName(String fieldName) {
		String fileNameNew = fieldName;

		if ("changeFlushKB".equals(fieldName)
				|| "changeFlushMB".equals(fieldName)
				|| "changeFlushGB".equals(fieldName)) {
			fileNameNew = "changeFlush";
		}

		return fileNameNew;
	}

	private List<AlarmBean> buildNewList(List<AlarmBean> list) {
		List<AlarmBean> nList = new ArrayList<AlarmBean>();
		if (null != list && !list.isEmpty()) {
			int i = 0;
			for (AlarmBean b : list) {
				b.bToKMGb();
				nList.add(i, b);
				i++;
			}
		}
		return nList;
	}

	public List<AlarmBean> queryHistoryAlarm(String startDate, String endDate,
			String type, String warmOK, String flushOK) {
		String tableName = "vAlarmCgiFlush";
		String columnName = "vcCgi";
		String alarmId = "nmAlarmCgiFlushId";

		if ("CGI".equals(type)) {
			tableName = "vAlarmCgiFlush";
			columnName = "vcCgi";
			alarmId = "nmAlarmCgiFlushId";
		} else if ("BSC".equals(type)) {
			tableName = "vAlarmBscFlush";
			columnName = "vcName";
			alarmId = "nmAlarmBscFlushId";
		} else if ("TD".equals(type) || "GPRS".equals(type)) {
			tableName = "tbAlarmGprsTotalFlush";
			columnName = "intRaitype";
			alarmId = "nmAlarmGprsTotalFlushId";
			// typeId = "nmAlarmGprsTotalFlushId";
		} else {
			tableName = "vAlarmSgsnFlush";
			columnName = "vcName";
			alarmId = "nmAlarmSgsnFlushId";
		}

		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("startDate", startDate);
		condition.put("endDate", endDate);
		condition.put("tableName", tableName);
		condition.put("columnName", columnName);
		condition.put("alarmId", alarmId);
		condition.put("warmOK", warmOK);
		condition.put("flushOK", flushOK);
		condition.put("typeTD", type);

		List<AlarmBean> list = flushAlarmDao.queryHistory(condition);
		list = buildNewList(list);
		return list;
	}

	public Page<AlarmBean> queryHistoryAlarm(String startDate, String type,
			GridServerHandler gridServerHandler, String warmOK) {
		Page<AlarmBean> page = new Page<AlarmBean>(20);
		page.setPageSize(gridServerHandler.getPageSize());
		page.setPageNo(gridServerHandler.getPageInfo().getPageNum());
		String tableName = "vAlarmCgiFlush";
		String columnName = "vcCgi";
		String alarmId = "nmAlarmCgiFlushId";
		String typeId = "vcCgi";

		if ("CGI".equals(type)) {
			tableName = "vAlarmCgiFlush";
			columnName = "vcCgi";
			alarmId = "nmAlarmCgiFlushId";
			typeId = "vcCgi";
		} else if ("BSC".equals(type)) {
			tableName = "vAlarmBscFlush";
			columnName = "vcName";
			alarmId = "nmAlarmBscFlushId";
			typeId = "intBscId";
		} else {
			tableName = "vAlarmSgsnFlush";
			columnName = "vcName";
			alarmId = "nmAlarmSgsnFlushId";
			typeId = "intSgsnId";
		}

		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("startDate", startDate);
		condition.put("tableName", tableName);
		condition.put("columnName", columnName);
		condition.put("alarmId", alarmId);
		condition.put("typeId", typeId);
		condition.put("warmOK", warmOK);

		page.setTotalCount(flushAlarmDao.getTotalCountDay(condition));

		List<AlarmBean> list = flushAlarmDao.queryHistoryDay(page, condition);

		// /-------------------------
		list = buildNewList(list);
		// /-------------------------
		page.setResult(list);

		return page;
	}

	public List<AlarmBean> queryHistoryAlarmDay(String startDate, String type) {
		String tableName = "vAlarmCgiFlush";
		String columnName = "vcCgi";
		String alarmId = "nmAlarmCgiFlushId";

		if ("CGI".equals(type)) {
			tableName = "vAlarmCgiFlush";
			columnName = "vcCgi";
			alarmId = "nmAlarmCgiFlushId";
		} else if ("BSC".equals(type)) {
			tableName = "vAlarmBscFlush";
			columnName = "vcName";
			alarmId = "nmAlarmBscFlushId";
		} else if ("TD".equals(type) || "GPRS".equals(type)) {
			tableName = "tbAlarmGprsTotalFlush";
			columnName = "intRaitype";
			alarmId = "nmAlarmGprsTotalFlushId";
			// typeId = "nmAlarmGprsTotalFlushId";
		} else {
			tableName = "vAlarmSgsnFlush";
			columnName = "vcName";
			alarmId = "nmAlarmSgsnFlushId";
		}

		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("startDate", startDate);
		condition.put("tableName", tableName);
		condition.put("columnName", columnName);
		condition.put("alarmId", alarmId);
		condition.put("typeTD", type);

		List<AlarmBean> list = flushAlarmDao.queryHistoryDay(condition);

		// ------------------
		list = buildNewList(list);
		// ------------------

		return list;
	}

	public void doCancelWarn(String alarmInfo, String typeId, String user,
			String type, String warmDate) {
		String tableName = "vAlarmCgiFlush";
		String columnName = "vcCgi";
		// String alarmId = "nmAlarmCgiFlushId";

		if ("CGI".equals(type)) {
			tableName = "tbAlarmCgiFlush";
			columnName = "vcCGI";
			// alarmId = "nmAlarmCgiFlushId";
		} else if ("BSC".equals(type)) {
			tableName = "tbAlarmBscFlush";
			columnName = "intBscId";
			// alarmId = "nmAlarmBscFlushId";
		} else if ("TD".equals(type) || "GPRS".equals(type)) {
			tableName = "tbAlarmGprsTotalFlush";
			columnName = "nmAlarmGprsTotalFlushId";
			// alarmId = "nmAlarmBscFlushId";
		} else {
			tableName = "tbAlarmSgsnFlush";
			columnName = "intSgsnId";
			// alarmId = "nmAlarmSgsnFlushId";
		}

		if ("CGI".equals(type)) {
			typeId = buildTypeName(typeId);
		}

		warmDate = buildTypeName(warmDate);

		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("alarmInfo", alarmInfo);
		condition.put("tableName", tableName);
		condition.put("columnName", columnName);
		condition.put("typeName", typeId);
		condition.put("user", user);
		condition.put("warmDate", warmDate);
		flushAlarmDao.doCancelWarn(condition);
	}

	private String buildTypeName(String typeName) {
		String ntypeName = "'" + typeName + "'";

		if (typeName.trim().contains(",")) {
			ntypeName = "";
			String[] tnarr = typeName.split(",");
			for (String tn : tnarr) {
				ntypeName += "'" + tn + "',";
			}

			ntypeName = ntypeName.substring(0, ntypeName.length() - 1);
		}

		return ntypeName.trim();
	}

	public Page<AlarmBean> queryHistoryAlarmAdd(String startDate, String type,
			GridServerHandler gridServerHandler) {
		return queryHistoryAlarmConfig(startDate, type, gridServerHandler,
				"add");
	}

	public Page<AlarmBean> queryHistoryAlarmMv(String startDate, String type,
			GridServerHandler gridServerHandler) {
		return queryHistoryAlarmConfig(startDate, type, gridServerHandler, "mv");
	}

	public Page<AlarmBean> queryHistoryAlarmWarm(String startDate, String type,
			GridServerHandler gridServerHandler) {
		return queryHistoryAlarmConfig(startDate, type, gridServerHandler,
				"warm");
	}

	public Page<AlarmBean> queryHistoryAlarmConfig(String startDate,
			String type, GridServerHandler gridServerHandler, String typeName) {
		Page<AlarmBean> page = new Page<AlarmBean>(20);
		page.setPageSize(gridServerHandler.getPageSize());
		page.setPageNo(gridServerHandler.getPageInfo().getPageNum());
		String tableName = "vAlarmCgiFlush";
		String columnName = "vcCgi";
		String alarmId = "nmAlarmCgiFlushId";
		String typeId = "vcCgi";

		if ("CGI".equals(type)) {
			tableName = "vAlarmCgiFlush";
			columnName = "vcCgi";
			alarmId = "nmAlarmCgiFlushId";
			typeId = "vcCgi";
		} else if ("BSC".equals(type)) {
			tableName = "vAlarmBscFlush";
			columnName = "vcName";
			alarmId = "nmAlarmBscFlushId";
			typeId = "intBscId";
		} else if ("TD".equals(type) || "GPRS".equals(type)) {
			tableName = "tbAlarmGprsTotalFlush";
			columnName = "intRaitype";
			alarmId = "nmAlarmGprsTotalFlushId";
			typeId = "nmAlarmGprsTotalFlushId";
		} else {
			tableName = "vAlarmSgsnFlush";
			columnName = "vcName";
			alarmId = "nmAlarmSgsnFlushId";
			typeId = "intSgsnId";
		}

		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("startDate", startDate);
		condition.put("tableName", tableName);
		condition.put("columnName", columnName);
		condition.put("alarmId", alarmId);
		condition.put("typeId", typeId);
		condition.put("typeName", typeName);
		condition.put("typeTD", type);

		page.setTotalCount(flushAlarmDao.getTotalCountDayConfig(condition));

		SortInfo si = gridServerHandler.getSingleSortInfo();

		if (null != si) {
			String fieldName = si.getFieldName();
			String sortAttr = si.getSortOrder();

			fieldName = buildFileName(fieldName);

			page.setOrder(sortAttr);
			page.setOrderBy(fieldName);
		} else {
			page.setOrder("desc");
			page.setOrderBy("fullDate");
		}

		List<AlarmBean> list = flushAlarmDao.queryHistoryDayConfig(page,
				condition);

		list = buildNewList(list);
		page.setResult(list);

		return page;
	}

}
