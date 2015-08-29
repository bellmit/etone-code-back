package com.symbol.app.mantoeye.service.alarm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.symbol.app.mantoeye.dao.alarm.DistinguishRatioAlarmDao;
import com.symbol.app.mantoeye.dto.alarm.AlarmBean;
import com.symbol.app.mantoeye.dto.alarm.AlarmRatioLimmit;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.tools.gtgrid.server.GridServerHandler;

/**
 * 识别率告警server
 * 
 * @author Jane
 * 
 */
@Service
@Transactional(readOnly = true)
public class DistinguishRatioAlarmManager {

	public DistinguishRatioAlarmDao getDistinguishRatioAlarmDao() {
		return distinguishRatioAlarmDao;
	}

	@Resource
	public void setDistinguishRatioAlarmDao(
			DistinguishRatioAlarmDao distinguishRatioAlarmDao) {
		this.distinguishRatioAlarmDao = distinguishRatioAlarmDao;
	}

	private DistinguishRatioAlarmDao distinguishRatioAlarmDao;

	/**
	 * 页面饼图数据查询
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @param type
	 * @return
	 */
	public AlarmBean pieQueryByTpye(String year, String month, String day,
			String type) {
		int y = Integer.parseInt(year);
		int m = Integer.parseInt(month);
		int d = Integer.parseInt(day);

		int t = Integer.parseInt(type);

		Map<String, Object> condition = new HashMap<String, Object>();

		condition.put("intType", t);
		condition.put("intYear", y);
		condition.put("intMonth", m);
		condition.put("intDay", d);

		AlarmBean bean = distinguishRatioAlarmDao.pieQueryByType(condition);

		return bean;
	}

	/**
	 * 查询历史告警信息
	 * 
	 * @param startDate
	 * @param endDate
	 * @param type
	 * @param gridServerHandler
	 * @return
	 */
	public Page<AlarmBean> queryHistoryAlarm(String startDate, String endDate,
			String type, GridServerHandler gridServerHandler) {
		Page<AlarmBean> page = new Page<AlarmBean>(20);
		page.setPageSize(gridServerHandler.getPageSize());
		page.setPageNo(gridServerHandler.getPageInfo().getPageNum());

		Map<String, Object> condition = new HashMap<String, Object>();

		condition.put("intType", type);
		condition.put("startDate", startDate);
		condition.put("endDate", endDate);

		page.setTotalCount(distinguishRatioAlarmDao.getTotalCount(condition));

		List<AlarmBean> list = distinguishRatioAlarmDao.queryHistoryAlarm(page,
				condition);
		page.setResult(list);

		return page;
	}

	/**
	 * 查询历史告警信息
	 * 
	 * @param startDate
	 * @param endDate
	 * @param type
	 * @return
	 */
	public List<AlarmBean> queryHistoryAlarm(String startDate, String endDate,
			String type) {
		Map<String, Object> condition = new HashMap<String, Object>();

		condition.put("intType", type);
		condition.put("startDate", startDate);
		condition.put("endDate", endDate);

		List<AlarmBean> list = distinguishRatioAlarmDao
				.queryHistoryAlarm(condition);

		return list;
	}

	public List<AlarmRatioLimmit> queryAlarmRatioLimmit(
			Map<String, String> condition) {

		List<AlarmRatioLimmit> oList = distinguishRatioAlarmDao
				.queryAlarmRatioLimmit();

		oList = buildNewList(condition, oList);

		return oList;
	}

	private List<AlarmRatioLimmit> buildNewList(Map<String, String> condition,
			List<AlarmRatioLimmit> oList) {
		/**
		 * condition.put("cgi", "30"); condition.put("bsc", "40");
		 * condition.put("sgsn", "10"); condition.put("busi", "3");
		 */

		/**
		 * private String reAlarmRatio; private Integer limmitLevel;
		 */
		List<AlarmRatioLimmit> nList = new ArrayList<AlarmRatioLimmit>();

		if (null != oList && !oList.isEmpty()) {
			for (AlarmRatioLimmit ar : oList) {
				String reAlarmRatio = condition.get(ar.getTypeName());
				ar.setReAlarmRatio(reAlarmRatio);
				int limmitLevel = getLimmitLevel(reAlarmRatio, ar
						.getMinLimmit(), ar.getMaxLimmit());
				ar.setLimmitLevel(limmitLevel);
				nList.add(ar);
			}
		} else {
			AlarmRatioLimmit ar = new AlarmRatioLimmit();
			ar.setLimmit("0");
			ar.setLimmitLevel(1);
			ar.setMaxLimmit("0");
			ar.setMinLimmit("0");
			ar.setReAlarmRatio("0");
			ar.setTypeName("CGI");
			nList.add(ar);
			ar.setTypeName("BSC");
			nList.add(ar);
			ar.setTypeName("SGSN");
			nList.add(ar);
			ar.setTypeName("业务");
			nList.add(ar);
		}

		return nList;
	}

	private int getLimmitLevel(String reAlarmRatio, String minLimmit,
			String maxLimmit) {
		Double rar = Double.parseDouble("".equals(reAlarmRatio)
				|| null == reAlarmRatio ? "0" : reAlarmRatio);
		Double minl = Double.parseDouble("".equals(minLimmit)
				|| null == minLimmit ? "0" : minLimmit);
		Double maxl = Double.parseDouble("".equals(maxLimmit)
				|| null == maxLimmit ? "0" : maxLimmit);

		int level = 1;

		if (rar >= 0 && rar <= minl) {
			level = 1;
		} else if (rar > minl && rar <= maxl) {
			level = 2;
		} else if (rar > maxl) {
			level = 3;
		}

		return level;
	}

	public AlarmRatioLimmit findAlarmRatioLimmit(String typeName) {
		String type = getType(typeName);

		AlarmRatioLimmit ar = distinguishRatioAlarmDao
				.findAlarmRatioLimmit(type);
		return ar;
	}

	public void configAlarmRatioLimmit(String typeName, String minLimmit,
			String maxLimmit) {
		String type = getType(typeName);

		minLimmit = getNewLimmit(minLimmit);
		maxLimmit = getNewLimmit(maxLimmit);

		distinguishRatioAlarmDao.configAlarmRatioLimmit(type, minLimmit,
				maxLimmit);

	}

	private String getNewLimmit(String limmit) {
		Double lim = Double.parseDouble(limmit);
		//lim /= 100.0;
		return lim.toString();
	}

	private String getType(String typeName) {
		String type = "1";
		if ("cgi".equalsIgnoreCase(typeName)) {
			type = "1";
		} else if ("bsc".equalsIgnoreCase(typeName)) {
			type = "2";
		} else if ("sgsn".equalsIgnoreCase(typeName)) {
			type = "3";
		} else {
			type = "4";
		}

		return type;
	}

}
