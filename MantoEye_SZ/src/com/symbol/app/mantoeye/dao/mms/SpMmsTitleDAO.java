package com.symbol.app.mantoeye.dao.mms;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
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
import com.symbol.app.mantoeye.util.MantoEyeUtils;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.modules.orm.Page;

@Repository
public class SpMmsTitleDAO extends HibernateQueryDAO {
	@Autowired
	private MmsSendModeDAO mmsSendModeDAO;

	/**
	 * 分页查询
	 * 
	 * @param page
	 * @param isTD
	 *            TD标识
	 * @param timeLevel
	 *            时间粒度 1:小时 2:天 3:周 4:月
	 * @return
	 */
	public Page<CommonFlush> query(final Page page, int isTD, int timeLevel,
			String sTime, String eTime, String mms_title_search) {
		String sql = this.buildSql(false, isTD, timeLevel, sTime, eTime, page,
				mms_title_search);
		List list = this.getSession().createSQLQuery(sql)
				.setFirstResult(page.getFirst())
				.setMaxResults(page.getPageSize()).list();
		Page newPage = new Page();
		newPage.setPageSize(page.getPageSize());
		String[] sqlArr = sql.split("order by");
		String totalcountSQL = "select count(1) from (" + sqlArr[0]
				+ " ) as totalcount";
		Object totalcount = this.getSession().createSQLQuery(totalcountSQL)
				.uniqueResult();
		newPage.setTotalCount(Integer.parseInt(null == totalcount.toString()
				|| "".equals(totalcount.toString()) ? "0" : totalcount
				.toString()));

		newPage.setResult(buildBeanList(
				this.getTotalFlush(false, isTD, timeLevel, sTime, eTime),
				this.getTotalFlush(true, isTD, timeLevel, sTime, eTime), list,
				timeLevel, sTime, eTime));
		newPage.setPageNo(page.getPageNo());
		return newPage;
	}

	public List<CommonFlush> listData(int isTD, int timeLevel, String sTime,
			String eTime, String mms_title_search, Page page) {
		// 查询数据
		String sql = this.buildSql(false, isTD, timeLevel, sTime, eTime, page,
				mms_title_search);
		SQLQuery query = this.getSession().createSQLQuery(sql);
		List list = query.list();
		return buildBeanList(
				this.getTotalFlush(false, isTD, timeLevel, sTime, eTime),
				this.getTotalFlush(true, isTD, timeLevel, sTime, eTime), list,
				timeLevel, sTime, eTime);
	}

	/**
	 * 获取总发送量
	 */
	public Object getTotalFlush(boolean flag, int isTD, int timeLevel,
			String sTime, String eTime) {
		Object obj = 1;
		String totalSql = "";
		if (flag) {// 是否统计全网发送量
			boolean isHour = false;
			if (CommonConstants.MANTOEYE_TIME_LEVEL_HOUR == timeLevel) {
				isHour = true;// 如果是按小时统计,则统计的是小时时间段数据
			}
			totalSql = mmsSendModeDAO.buildTotalSql(isHour, isTD, timeLevel,
					sTime, eTime);
		} else {
			totalSql = this.buildSql(true, isTD, timeLevel, sTime, eTime, null,
					null);
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
			Object allTotalFlush, List list, int timeLevel, String sTime,
			String eTime) {
		List<CommonFlush> commonFlushList = null;
		CommonFlush commonFlush = null;
		if (list != null && !list.isEmpty()) {
			commonFlushList = new ArrayList<CommonFlush>();
			for (int i = 0; i < list.size(); i++) {
				commonFlush = new CommonFlush();
				Object[] objs = (Object[]) list.get(i);
				commonFlush.setPort(objs[0].toString());// 端口
				commonFlush.setTitle(objs[1].toString());// 主题
				commonFlush
						.setTotalSendFlush(Long.parseLong(objs[2].toString()));// 发送量
				commonFlush.setIntYear(Common.StringToInt(objs[3] + ""));// 年
				commonFlush.setIntMonth(Common.StringToInt(objs[4] + ""));// 月
				switch (timeLevel) {
				case CommonConstants.MANTOEYE_TIME_LEVEL_HOUR:
					commonFlush.setIntDay(Common.StringToInt(objs[5] + ""));// 日
					commonFlush.setIntHour(Common.StringToInt(objs[6] + ""));// 小时
					break;
				case CommonConstants.MANTOEYE_TIME_LEVEL_DAY:
					commonFlush.setIntDay(Common.StringToInt(objs[5] + ""));// 日
					break;
				case CommonConstants.MANTOEYE_TIME_LEVEL_WEEK:
					commonFlush.setIntWeek(Common.StringToInt(objs[5] + ""));// 周
					break;
				case CommonConstants.MANTOEYE_TIME_LEVEL_MONTH:
					break;
				}
				commonFlush.setFlushRate(CommonUtils.formatPercent(
						Double.parseDouble(objs[2].toString()),
						Double.parseDouble(totalFlush.toString())));// 占比
				commonFlush.setAllFlushRate(CommonUtils.formatPercent(
						Double.parseDouble(objs[2].toString()),
						Double.parseDouble(allTotalFlush.toString())));// 全网占比
				commonFlush.setSpanDate(timeLevel);// 设置时间
				// commonFlush.formatMmsData();
				commonFlushList.add(commonFlush);
			}
		}
		return commonFlushList;
	}

	/**
	 * 组装查询语句
	 * 
	 * @param flag
	 *            是否统计总发送量
	 * @param isTD
	 * @param timeLevel
	 * @param time
	 * @return
	 */
	public String buildSql(boolean flag, int isTD, int timeLevel, String sTime,
			String eTime, Page page, String mms_title_search) {
		Date date = CommonUtils.getDate(sTime);
		int year = CommonUtils.getYear(date);
		int month = CommonUtils.getMonth(date);
		int week = CommonUtils.getWeek(date);
		int day = CommonUtils.getDay(date);
		String defaultSortSql = "";
		String sortSql = "";
		String sortType = "";
		String sortColumn = "";
		if (page != null && page.getOrderBy() != null) {
			sortType = page.getOrder();
			sortColumn = page.getOrderBy();
			if (sortType != "" && sortColumn != "") {
				sortSql = sortSql + " order by  " + sortColumn + "  "
						+ sortType;
			}
		}
		String sql = "select sum(nmCounts) ";
		switch (timeLevel) {
		case 1:
			if (!flag) {// 按彩信主题时段统计需要统计的是时间跨度
				sql = "select nmSpPort,vcTitle,nmCounts,intYear,intMonth,intDay,intHour";
			}
			sql = sql
					+ " from  vStatHourSpMmsTitleFlush where 1=1 "
					+ " and  convert(datetime,string(intYear,'-',intMonth,'-',intDay,' ',inthour,':00:00'))>="
					+ MantoEyeUtils.formatData(sTime, timeLevel)
					+ " and  convert(datetime,string(intYear,'-',intMonth,'-',intDay,' ',inthour,':00:00'))<="
					+ MantoEyeUtils.formatData(eTime, timeLevel);
			/*
			 * if(mms_title_search!=null&&mms_title_search!=""){ sql = sql +
			 * " and vcTitle = '" + mms_title_search + "'"; }
			 */
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

			defaultSortSql = " order by intYear asc,intMonth asc,intDay asc,intHour asc ";
			break;
		case 2:
			if (!flag) {
				sql = "select nmSpPort,vcTitle,nmCounts,intYear,intMonth,intDay";
			}
			sql = sql + " from vStatDaySpMmsTitleFlush where 1=1 "
					+ " and intYear=" + year + " and intMonth=" + month
					+ " and intDay=" + day;
			defaultSortSql = " order by intYear,intMonth,intDay asc ";
			break;
		case 3:
			if (!flag) {
				sql = "select nmSpPort,vcTitle,nmCounts,intYear,intYear,intWeek";
			}
			sql = sql + " from vStatWeekSpMmsTitleFlush where 1=1 "
					+ " and intYear=" + year
					// + " and intMonth=" + month
					+ " and intWeek=" + week;
			defaultSortSql = " order by intYear,intWeek asc ";
			break;
		case 4:
			if (!flag) {
				sql = "select nmSpPort,vcTitle, nmCounts,intYear,intMonth";
			}
			sql = sql + " from vStatMonthSpMmsTitleFlush where 1=1 "
					+ " and intYear=" + year + " and intMonth=" + month;
			defaultSortSql = " order by intYear,intMonth asc ";
			break;
		}
		sql = sql + " and intRaitype = " + isTD;
		if (!flag) {
			if (sortSql == "") {
				sortSql = defaultSortSql;
			}
		}
		sql = sql + sortSql;
		return sql;
	}

	/**
	 * 获取SP彩信主题集合(按主题时段统计)
	 */
	public List<String> listMmsTitle(int isTD, int timeLevel, String sTime,
			String eTime, String like_mms_title_search) {
		String sql = "select vcTitle,nmSpPort from vStatHourSpMmsTitleFlush  where 1=1 "
				+ " and  convert(datetime,string(intYear,'-',intMonth,'-',intDay,' ',inthour,':00:00'))>="
				+ MantoEyeUtils.formatData(sTime, timeLevel)
				+ " and  convert(datetime,string(intYear,'-',intMonth,'-',intDay,' ',inthour,':00:00'))<="
				+ MantoEyeUtils.formatData(eTime, timeLevel);
		if (like_mms_title_search != null && like_mms_title_search != "") {
			sql = sql + " and vcTitle like '%" + like_mms_title_search + "%'";
		} else {
			sql = sql + " and vcTitle = '" + like_mms_title_search + "'";
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

	public long getTotalCount(int isTD, int timeLevel, String sTime,
			String eTime, String mmsTitleSearch) {
		String sql = this.buildSql_(false, isTD, timeLevel, sTime, eTime, null,
				mmsTitleSearch);

		sql = " select count(1) from ( " + sql + " ) as total";

		Object count = this.getSession().createSQLQuery(sql).uniqueResult();
		return Long.parseLong(count.toString());
	}

	private String buildSql_(boolean flag, int isTD, int timeLevel,
			String sTime, String eTime, Page page, String mms_title_search) {
		Date date = CommonUtils.getDate(sTime);
		int year = CommonUtils.getYear(date);
		int month = CommonUtils.getMonth(date);
		int week = CommonUtils.getWeek(date);
		int day = CommonUtils.getDay(date);
		// String defaultSortSql = "";
		String sortSql = "";
		String sortType = "";
		String sortColumn = "";
		if (page != null && page.getOrderBy() != null) {
			sortType = page.getOrder();
			sortColumn = page.getOrderBy();
			if (sortType != "" && sortColumn != "") {
				sortSql = sortSql + " order by  " + sortColumn + "  "
						+ sortType;
			}
		}
		String sql = "select sum(nmCounts) ";
		switch (timeLevel) {
		case 1:
			if (!flag) {// 按彩信主题时段统计需要统计的是时间跨度
				sql = "select nmSpPort,vcTitle,nmCounts,intYear,intMonth,intDay,intHour";
			}
			sql = sql
					+ " from  vStatHourSpMmsTitleFlush where 1=1 "
					+ " and  convert(datetime,string(intYear,'-',intMonth,'-',intDay,' ',inthour,':00:00'))>="
					+ MantoEyeUtils.formatData(sTime, timeLevel)
					+ " and  convert(datetime,string(intYear,'-',intMonth,'-',intDay,' ',inthour,':00:00'))<="
					+ MantoEyeUtils.formatData(eTime, timeLevel);
			/*
			 * if(mms_title_search!=null&&mms_title_search!=""){ sql = sql +
			 * " and vcTitle = '" + mms_title_search + "'"; }
			 */
			String port = "10086";
			String title = "";
			if (mms_title_search == null || mms_title_search == "") {

			} else {
				String[] conds = mms_title_search.split("]");
				if (conds.length > 1) {
					port = conds[0].substring(1);
					title = conds[1];
				}
			}
			sql = sql + " and vcTitle = '" + title + "' and nmSpPort = '"
					+ port + "' ";

			// defaultSortSql =
			// " order by intYear asc,intMonth asc,intDay asc,intHour asc ";
			break;
		case 2:
			if (!flag) {
				sql = "select nmSpPort,vcTitle,nmCounts,intYear,intMonth,intDay";
			}
			sql = sql + " from vStatDaySpMmsTitleFlush where 1=1 "
					+ " and intYear=" + year + " and intMonth=" + month
					+ " and intDay=" + day;
			// defaultSortSql = " order by intYear,intMonth,intDay asc ";
			break;
		case 3:
			if (!flag) {
				sql = "select nmSpPort,vcTitle,nmCounts,intYear,intYear,intWeek";
			}
			sql = sql + " from vStatWeekSpMmsTitleFlush where 1=1 "
					+ " and intYear=" + year
					// + " and intMonth=" + month
					+ " and intWeek=" + week;
			// defaultSortSql = " order by intYear,intWeek asc ";
			break;
		case 4:
			if (!flag) {
				sql = "select nmSpPort,vcTitle, nmCounts,intYear,intMonth";
			}
			sql = sql + " from vStatMonthSpMmsTitleFlush where 1=1 "
					+ " and intYear=" + year + " and intMonth=" + month;
			// defaultSortSql = " order by intYear,intMonth asc ";
			break;
		}
		sql = sql + " and intRaitype = " + isTD;
		if (!flag) {
			if (sortSql == "") {
				// sortSql = defaultSortSql;
			}
		}
		// sql = sql + sortSql;
		return sql;
	}

	public void queryBySql(int isTD, int timeLevel, String sTime, String eTime,
			String mmsTitleSearch, String queryTime, String iqServerPath) {
		String sql = this.buildSqlBig(false, isTD, timeLevel, sTime, eTime,
				null, mmsTitleSearch);
		Connection con = getSession().connection();
		// @sql ,@immediateDate ,@db_temp_dir
		String procedure = "{Call  proc_web_export_big_data_to_file(?,?,?)}";
		CallableStatement cstmt = null;
		try {
			cstmt = con.prepareCall(procedure);
			cstmt.setString(1, sql);
			cstmt.setString(2, queryTime);
			cstmt.setString(3, iqServerPath);
			cstmt.execute();
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
	}

	public String buildSqlBig(boolean flag, int isTD, int timeLevel,
			String sTime, String eTime, Page page, String mms_title_search) {
		Date date = CommonUtils.getDate(sTime);
		int year = CommonUtils.getYear(date);
		int month = CommonUtils.getMonth(date);
		int week = CommonUtils.getWeek(date);
		int day = CommonUtils.getDay(date);
		String sortSql = "";
		String sortType = "";
		String sortColumn = "";
		if (page != null && page.getOrderBy() != null) {
			sortType = page.getOrder();
			sortColumn = page.getOrderBy();
			if (sortType != "" && sortColumn != "") {
				sortSql = sortSql + " order by  " + sortColumn + "  "
						+ sortType;
			}
		}

		StringBuffer sql = new StringBuffer();
		switch (timeLevel) {
		case 1:// sql.append(" select convert(varchar(10),convert(datetime,string(f.intYear,'-',f.intMonth,'-',f.intDay))) fullDate,");
			break;
		case 2:
			sql.append(" select convert(varchar(10),convert(datetime,string(f.intYear,'-',f.intMonth,'-',f.intDay))) fullDate,");
			break;
		case 3:
			String fullDate = CommonUtils.getWeekRange(year, month, week);
			sql.append(" select '").append(fullDate).append("' fullDate,");
			break;
		case 4:
			sql.append(" select convert(varchar(7),convert(datetime,string(f.intYear,'-',f.intMonth))) fullDate,");
			break;
		}
		sql.append(" f.nmSpPort port,f.vcTitle title,f.nmCounts totalSendFlush, ");
		sql.append(" convert(numeric(38,4),round(f.nmCounts/f2.t*100,4)) flushRate,");
		sql.append(" convert(numeric(38,4),round(f.nmCounts/m.att*100,4)) allFlushRate");
		switch (timeLevel) {
		case 1:
			break;
		case 2:
			sql.append(" from vStatDaySpMmsTitleFlush f,");
			sql.append(
					" (select sum(nmCounts) t from vStatDaySpMmsTitleFlush where 1=1  and intYear=")
					.append(year).append(" and intMonth=").append(month)
					.append(" and intDay=").append(day)
					.append(" and intRaitype = 1) as f2,");
			sql.append(
					" (select sum(nmTotalCounts) att from vStatDayWapMode where 1=1  and intYear=")
					.append(year).append(" and intMonth=").append(month)
					.append(" and intDay=").append(day)
					.append(" and intRaitype = 1) as m");
			sql.append(" where 1=1  and f.intYear=").append(year)
					.append(" and f.intMonth=").append(month)
					.append(" and f.intDay=").append(day)
					.append(" and f.intRaitype = 1 ");
			sql.append(" group by f2.t,m.att,f.intYear,f.intMonth,f.intDay,f.nmSpPort,f.vcTitle,f.nmCounts");
			break;
		case 3:
			sql.append(" from vStatWeekSpMmsTitleFlush f,");
			sql.append(
					" (select sum(nmCounts) t from vStatWeekSpMmsTitleFlush where 1=1  and intYear=")
					.append(year).append(" and intWeek=").append(week)
					.append(" and intRaitype = 1) as f2,");
			sql.append(
					" (select sum(nmTotalCounts) att from vStatWeekWapMode where 1=1  and intYear=")
					.append(year).append(" and intWeek=").append(week)
					.append(" and intRaitype = 1) as m");
			sql.append(" where 1=1  and f.intYear=").append(year)
					.append(" and f.intWeek=").append(week)
					.append(" and f.intRaitype = 1 ");
			sql.append(" group by f2.t,m.att,f.intYear,f.intWeek,f.nmSpPort,f.vcTitle,f.nmCounts");
			break;
		case 4:
			sql.append(" from vStatMonthSpMmsTitleFlush f,");
			sql.append(
					" (select sum(nmCounts) t from vStatMonthSpMmsTitleFlush where 1=1  and intYear=")
					.append(year).append(" and intMonth=").append(month)
					.append(" and intRaitype = 1) as f2,");
			sql.append(
					" (select sum(nmTotalCounts) att from vStatMonthWapMode where 1=1  and intYear=")
					.append(year).append(" and intMonth=").append(month)
					.append(" and intRaitype = 1) as m");
			sql.append(" where 1=1  and f.intYear=").append(year)
					.append(" and f.intMonth=").append(month)
					.append(" and f.intRaitype = 1 ");
			sql.append(" group by f2.t,m.att,f.intYear,f.intMonth,f.nmSpPort,f.vcTitle,f.nmCounts");
			break;
		}

		sql.append(" order by f.nmCounts desc,f.nmSpPort");

		return sql.toString();

	}
}
