package com.symbol.app.mantoeye.dao.mms;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.symbol.app.mantoeye.constants.CommonConstants;
import com.symbol.app.mantoeye.dao.common.HibernateQueryDAO;
import com.symbol.app.mantoeye.dto.flush.CommonFlush;
import com.symbol.app.mantoeye.util.CommonUtils;
import com.symbol.wp.modules.orm.Page;

@Repository
public class SpMmsTitleAreaDAO extends HibernateQueryDAO {

	@Autowired
	private MmsSendModeDAO mmsSendModeDAO;

	/**
	 * 分页查询
	 * 
	 * @param page
	 * @param isTD
	 * @param areaType
	 *            BSC:1 SGSN:2 街道办:3 营销片区:4 分公司:5
	 * @param timeLevel
	 *            小时:1 天:2 周:3 月:4
	 * @param year
	 * @param month
	 * @param week
	 * @param day
	 * @param hour
	 * @return
	 */
	public Page<CommonFlush> query(final Page page, int isTD, int areaType,
			int timeLevel, String time, String mms_title_search) {
		Date date = CommonUtils.getDate(time);
		int year = CommonUtils.getYear(date);
		int month = CommonUtils.getMonth(date);
		int week = CommonUtils.getWeek(date);
		int day = CommonUtils.getDay(date);
		int hour = CommonUtils.getHour(date);
		String sql = this.buildSql(false, isTD, areaType, timeLevel, year,
				month, week, day, hour, page, mms_title_search);

		String sqls = sql.split("order by")[0];
		String totalSql = "select count(1) from ( " + sqls + " ) as totalCount";
		Object totalObj = this.getSession().createSQLQuery(totalSql)
				.uniqueResult();
		Integer total = Integer.parseInt(null == totalObj.toString()
				|| "".equals(totalObj.toString()) ? "0" : totalObj.toString());
		List list = this.getSession().createSQLQuery(sql)
				.setFirstResult(page.getFirst())
				.setMaxResults(page.getPageSize()).list();

		Page newPage = new Page();
		newPage.setPageSize(page.getPageSize());
		newPage.setTotalCount(total);
		newPage.setResult(buildBeanList(this.getTotalFlush(false, isTD,
				areaType, timeLevel, time, mms_title_search), this
				.getTotalFlush(true, isTD, areaType, timeLevel, time,
						mms_title_search), list, timeLevel, time));
		newPage.setPageNo(page.getPageNo());
		return newPage;
	}

	/**
	 * 查询方法
	 * 
	 * @param isTD
	 * @param areaType
	 * @param timeLevel
	 * @param time
	 * @return
	 */
	public List<CommonFlush> listData(int isTD, int areaType, int timeLevel,
			String time, String mms_title_search) {
		Date date = CommonUtils.getDate(time);
		int year = CommonUtils.getYear(date);
		int month = CommonUtils.getMonth(date);
		int week = CommonUtils.getWeek(date);
		int day = CommonUtils.getDay(date);
		int hour = CommonUtils.getHour(date);
		String sql = this.buildSql(false, isTD, areaType, timeLevel, year,
				month, week, day, hour, null, mms_title_search);
		SQLQuery query = this.getSession().createSQLQuery(sql);
		List list = query.list();
		return buildBeanList(this.getTotalFlush(false, isTD, areaType,
				timeLevel, time, mms_title_search), this.getTotalFlush(true,
				isTD, areaType, timeLevel, time, mms_title_search), list,
				timeLevel, time);
	}

	/**
	 * 获取总发送量
	 */
	public Object getTotalFlush(boolean flag, int isTD, int areaType,
			int timeLevel, String time, String mms_title_search) {
		Object obj = 1;
		String totalSql = "";
		if (flag) {// 是否统计全网总发送量
			// 查询全网总发送量
			totalSql = mmsSendModeDAO.buildTotalSql(false, isTD, timeLevel,
					time, null);
		} else {
			Date date = CommonUtils.getDate(time);
			int year = CommonUtils.getYear(date);
			int month = CommonUtils.getMonth(date);
			int week = CommonUtils.getWeek(date);
			int day = CommonUtils.getDay(date);
			int hour = CommonUtils.getHour(date);
			// 查询总发送量
			totalSql = this.buildSql(true, isTD, areaType, timeLevel, year,
					month, week, day, hour, null, mms_title_search);
		}
		SQLQuery totalQuery = this.getSession().createSQLQuery(totalSql);
		List totalList = totalQuery.list();
		if (totalList.get(0) != null) {
			obj = totalList.get(0);
		}
		return obj;
	}

	/**
	 * 组装BEAN实体
	 * 
	 * @param list
	 * @param timeLevel
	 * @return
	 */
	public List<CommonFlush> buildBeanList(Object totalFlush,
			Object allTotalFlush, List list, int timeLevel, String time) {
		List<CommonFlush> commonFlushList = null;
		CommonFlush commonFlush = null;
		if (list != null && !list.isEmpty()) {
			commonFlushList = new ArrayList<CommonFlush>();
			for (int i = 0; i < list.size(); i++) {
				commonFlush = new CommonFlush();
				Object[] objs = (Object[]) list.get(i);
				commonFlush.setBusiness(objs[0].toString());// 业务
				commonFlush.setBusinessName(objs[1].toString());// 业务名称
				commonFlush.setPort(objs[2].toString());// 端口
				commonFlush.setTitle(objs[3].toString());// 主题
				commonFlush
						.setTotalSendFlush(Long.parseLong(objs[4].toString()));// 发送量
				commonFlush.setFlushRate(CommonUtils.formatPercent(
						Double.parseDouble(objs[4].toString()),
						Double.parseDouble(totalFlush.toString())));// 占比
				commonFlush.setAllFlushRate(CommonUtils.formatPercent(
						Double.parseDouble(objs[4].toString()),
						Double.parseDouble(allTotalFlush.toString())));// 全网占比
				if (timeLevel == 3) {
					commonFlush.setFullDate(CommonUtils.getDayInnerWeek(time));
				} else {
					commonFlush.setFullDate(time);
				}
				// commonFlush.formatMmsData();
				commonFlushList.add(commonFlush);
			}
		}
		return commonFlushList;
	}

	/**
	 * 组装SQL语句
	 * 
	 * @param flag
	 *            是否统计总发送量(列表需统计 地图不需要)
	 * @param isTD
	 *            TD标识
	 * @param areaType
	 *            区域维度
	 * @param timeLevel
	 *            时间粒度(天 周 月)
	 * @param year
	 * @param month
	 * @param week
	 * @param day
	 * @param hour
	 * @return
	 */
	public String buildSql(boolean flag, int isTD, int areaType, int timeLevel,
			int year, int month, int week, int day, int hour, Page page,
			String mms_title_search) {
		String sql = "";
		String fields = "";
		String table = "";
		String sortSql = "";
		String sortType = " desc ";
		String sortColumn = " nmCounts ";
		if (page != null) {
			sortType = " " + page.getOrder() + " ";
			sortColumn = " " + page.getOrderBy() + " ";
		}
		// 维度类型
		switch (areaType) {
		case CommonConstants.MANTOEYE_SPACE_LEVEL_BSC:
			fields = " intBscId,vcName, ";
			table = (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_DAY) ? "vStatDaySpMmsBscTitleFlush"
					: (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_WEEK) ? "vStatWeekSpMmsBscTitleFlush"
							: "vStatMonthSpMmsBscTitleFlush";
			break;
		case CommonConstants.MANTOEYE_SPACE_LEVEL_SGSN:
			fields = " intSgsnId,vcName, ";
			table = (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_DAY) ? "vStatDaySpMmsSgsnTitleFlush"
					: (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_WEEK) ? "vStatWeekSpMmsSgsnTitleFlush"
							: "vStatMonthSpMmsSgsnTitleFlush";
			break;
		case CommonConstants.MANTOEYE_SPACE_LEVEL_STREET:
			fields = " intStreetId,vcName, ";
			table = (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_DAY) ? "vStatDaySpMmsStreetTitleFlush"
					: (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_WEEK) ? "vStatWeekSpMmsStreetTitleFlush"
							: "vStatMonthSpMmsStreetTitleFlush";
			break;
		case CommonConstants.MANTOEYE_SPACE_LEVEL_SALEAREA:
			fields = " intSaleAreaId,vcSaleAreaName, ";
			table = (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_DAY) ? "vStatDaySpMmsSaleAreaTitleFlush"
					: (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_WEEK) ? "vStatWeekSpMmsSaleAreaTitleFlush"
							: "vStatMonthSpMmsSaleAreaTitleFlush";
			break;
		case CommonConstants.MANTOEYE_SPACE_LEVEL_BRANCH:
			fields = " intBranchId,vcBranchName, ";
			table = (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_DAY) ? "vStatDaySpMmsBranchTitleFlush"
					: (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_WEEK) ? "vStatWeekSpMmsBranchTitleFlush"
							: "vStatMonthSpMmsBranchTitleFlush";
			break;
		}

		if (flag) {// 统计总发送量
			sql = "select sum(nmCounts) ";
		} else {
			sql = " select " + fields;
		}

		// 时间粒度
		switch (timeLevel) {
		case CommonConstants.MANTOEYE_TIME_LEVEL_DAY:
			if (!flag) {
				sql = sql
						+ " nmSpPort,vcTitle,nmCounts,intYear,intMonth,intDay";
			}
			sql = sql + " from " + table + " where 1=1 " + " and intYear="
					+ year + " and intMonth=" + month + " and intDay=" + day;
			break;
		case CommonConstants.MANTOEYE_TIME_LEVEL_WEEK:
			if (!flag) {
				sql = sql
						+ " nmSpPort,vcTitle,nmCounts,intYear,intYear,intWeek";
			}
			sql = sql + " from " + table + " where 1=1 " + " and intYear="
					+ year
					// + " and intMonth=" + month
					+ " and intWeek=" + week;
			break;
		case CommonConstants.MANTOEYE_TIME_LEVEL_MONTH:
			if (!flag) {
				sql = sql + " nmSpPort,vcTitle,nmCounts,intYear,intMonth";
			}
			sql = sql + " from " + table + " where 1=1 " + " and intYear="
					+ year + " and intMonth=" + month;
			break;
		}
		sql = sql + " and intRaitype = " + isTD;
		if (!flag) {
			String port = "10086";
			String title = "";
			if (null != mms_title_search && !"".equals(mms_title_search.trim())) {

				String[] conds = mms_title_search.split("]");
				if (conds.length > 0) {
					port = conds[0].substring(1);
					if (conds.length > 1) {
						title = conds[1];
					}
				}
			}
			sql = sql + " and vcTitle = '" + title + "' and nmSpPort = '"
					+ port + "' ";
		}
		if (!flag) {
			sortSql = sortSql + " order by  " + sortColumn + sortType;
		}
		sql = sql + sortSql;
		return sql;
	}

	/**
	 * 获取SP彩信主题集合
	 */
	public List<String> listMmsTitle(int isTD, int areaType, int timeLevel,
			String time, String like_mms_title_search) {
		Date date = CommonUtils.getDate(time);
		int year = CommonUtils.getYear(date);
		int month = CommonUtils.getMonth(date);
		int week = CommonUtils.getWeek(date);
		int day = CommonUtils.getDay(date);
		String sql = "";
		String table = "";
		switch (areaType) {
		case CommonConstants.MANTOEYE_SPACE_LEVEL_BSC:
			table = (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_DAY) ? "vStatDaySpMmsBscTitleFlush"
					: (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_WEEK) ? "vStatWeekSpMmsBscTitleFlush"
							: "vStatMonthSpMmsBscTitleFlush";
			break;
		case CommonConstants.MANTOEYE_SPACE_LEVEL_SGSN:
			table = (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_DAY) ? "vStatDaySpMmsSgsnTitleFlush"
					: (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_WEEK) ? "vStatWeekSpMmsSgsnTitleFlush"
							: "vStatMonthSpMmsSgsnTitleFlush";
			break;
		case CommonConstants.MANTOEYE_SPACE_LEVEL_STREET:
			table = (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_DAY) ? "vStatDaySpMmsStreetTitleFlush"
					: (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_WEEK) ? "vStatWeekSpMmsStreetTitleFlush"
							: "vStatMonthSpMmsStreetTitleFlush";
			break;
		case CommonConstants.MANTOEYE_SPACE_LEVEL_SALEAREA:
			table = (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_DAY) ? "vStatDaySpMmsSaleAreaTitleFlush"
					: (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_WEEK) ? "vStatWeekSpMmsSaleAreaTitleFlush"
							: "vStatMonthSpMmsSaleAreaTitleFlush";
			break;
		case CommonConstants.MANTOEYE_SPACE_LEVEL_BRANCH:
			table = (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_DAY) ? "vStatDaySpMmsBranchTitleFlush"
					: (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_WEEK) ? "vStatWeekSpMmsBranchTitleFlush"
							: "vStatMonthSpMmsBranchTitleFlush";
			break;
		}
		sql = sql + "select vcTitle,nmSpPort from  " + table;
		switch (timeLevel) {
		case CommonConstants.MANTOEYE_TIME_LEVEL_DAY:
			sql = sql + " where 1=1 " + " and intYear=" + year
					+ " and intMonth=" + month + " and intDay=" + day;
			break;
		case CommonConstants.MANTOEYE_TIME_LEVEL_WEEK:
			sql = sql + " where 1=1 " + " and intYear=" + year
					+ " and intWeek=" + week;
			break;
		case CommonConstants.MANTOEYE_TIME_LEVEL_MONTH:
			sql = sql + " where 1=1 " + " and intYear=" + year
					+ " and intMonth=" + month;
			break;
		}
		if (like_mms_title_search != null && like_mms_title_search != "") {
			sql = sql + " and vcTitle like '%" + like_mms_title_search + "%'";
		} else {
			sql = sql + " and vcTitle = ''";
		}
		sql = sql
				+ " group by vcTitle,nmSpPort order by vcTitle asc,nmSpPort asc ";
		List<String> dataList = null;
		SQLQuery query = this.getSession().createSQLQuery(sql);
		List list = query.list();
		if (list != null && !list.isEmpty()) {
			dataList = new ArrayList<String>();
			Map<String, String> map = new HashMap<String, String>();
			for (int i = 0; i < list.size(); i++) {
				Object[] rrs = (Object[]) list.get(i);
				String vvv = "[" + rrs[1] + "]" + rrs[0];
				map.put(vvv, vvv);
			}
			for (String key : map.keySet()) {
				dataList.add(map.get(key));
			}
		}
		return dataList;
	}
}
