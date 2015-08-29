package com.symbol.app.mantoeye.dao.mms;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.SQLQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.symbol.app.mantoeye.dao.common.HibernateQueryDAO;
import com.symbol.app.mantoeye.dto.flush.CommonFlush;
import com.symbol.app.mantoeye.util.CommonUtils;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.modules.orm.Page;

@Repository
public class SpMmsPortDAO extends HibernateQueryDAO {

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
			String time , String port , String company ) {
		Date date = CommonUtils.getDate(time);
		int year = CommonUtils.getYear(date);
		int month = CommonUtils.getMonth(date);
		int week = CommonUtils.getWeek(date);
		int day = CommonUtils.getDay(date);
		int hour = CommonUtils.getHour(date);
		String sql = this.buildSql(false, isTD, timeLevel, year, month, week,
				day, hour, page , port , company);
//		List pageList = query.list();
		String sqls = sql.split("order by")[0];
		String totalSql = "select count(1) from ( " + sqls + " ) as totalCount";
		Object totalObj = this.getSession().createSQLQuery(totalSql)
				.uniqueResult();
		Integer total = Integer.parseInt(null == totalObj.toString()
				|| "".equals(totalObj.toString()) ? "0" : totalObj.toString());
		List list = this.getSession().createSQLQuery(sql)
				.setFirstResult(page.getFirst()).setMaxResults(
						page.getPageSize()).list();
		
		Page newPage = new Page();
		newPage.setPageSize(page.getPageSize());
		newPage.setTotalCount(total);
		newPage.setResult(buildBeanList(this.getTotalFlush(false, isTD,
				timeLevel, time), this.getTotalFlush(true, isTD, timeLevel,
				time), list, timeLevel, time));
		newPage.setPageNo(page.getPageNo());
		return newPage;
	}

	public List<CommonFlush> listData(int isTD, int timeLevel, String time,String port,String company) {
		Date date = CommonUtils.getDate(time);
		int year = CommonUtils.getYear(date);
		int month = CommonUtils.getMonth(date);
		int week = CommonUtils.getWeek(date);
		int day = CommonUtils.getDay(date);
		int hour = CommonUtils.getHour(date);
		String sql = this.buildSql(false, isTD, timeLevel, year, month, week,
				day, hour, null , port , company);
		SQLQuery query = this.getSession().createSQLQuery(sql);
		List list = query.list();
		return buildBeanList(this.getTotalFlush(false, isTD, timeLevel, time),
				this.getTotalFlush(true, isTD, timeLevel, time), list,
				timeLevel, time);
	}

	/**
	 * 获取总发送量
	 */
	public Object getTotalFlush(boolean flag, int isTD, int timeLevel,
			String time) {
		Object obj = 1;
		String totalSql = "";
		if (flag) {// 是否统计全网发送量
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
			totalSql = this.buildSql(true, isTD, timeLevel, year, month, week,
					day, hour, null , null ,null);
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
				commonFlush.setPort(objs[0].toString());// 端口
				commonFlush.setTotalSendFlush(Long
						.parseLong(objs[1].toString()));// 发送量
				commonFlush.setIntYear(Common.StringToInt(objs[2] + ""));// 年
				commonFlush.setIntMonth(Common.StringToInt(objs[3] + ""));// 月
				commonFlush.setFlushRate(CommonUtils.formatPercent(Common
						.StringTodouble(objs[1].toString()), Common
						.StringTodouble(totalFlush.toString())));// 占比
				commonFlush.setAllFlushRate(CommonUtils.formatPercent(Common
						.StringTodouble(objs[1].toString()), Common
						.StringTodouble(allTotalFlush.toString())));// 全网占比
				if (timeLevel == 1){//小时
					commonFlush.setFullDate(time);
					commonFlush.setUserBelong(Common.judgeString(objs[6]+"")?(objs[6]+""):"--");
					commonFlush.setCompany(Common.judgeString(objs[7]+"")?(objs[7]+""):"--");
				}else if (timeLevel == 2){//天
					commonFlush.setFullDate(time);
					commonFlush.setUserBelong(Common.judgeString(objs[5]+"")?(objs[5]+""):"--");
					commonFlush.setCompany(Common.judgeString(objs[6]+"")?(objs[6]+""):"--");
					
				}else if (timeLevel == 3) {//周
					commonFlush.setFullDate(CommonUtils.getDayInnerWeek(time));
					commonFlush.setUserBelong(Common.judgeString(objs[4]+"")?(objs[4]+""):"--");
					commonFlush.setCompany(Common.judgeString(objs[5]+"")?(objs[5]+""):"--");
				} else {//月
					commonFlush.setFullDate(time);
					commonFlush.setUserBelong(Common.judgeString(objs[4]+"")?(objs[4]+""):"--");
					commonFlush.setCompany(Common.judgeString(objs[5]+"")?(objs[5]+""):"--");
				}
				//commonFlush.formatMmsData();
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
	public String buildSql(boolean flag, int isTD, int timeLevel, int year,
			int month, int week, int day, int hour, Page page, String port ,String company) {
		String sql = "select sum(nmCounts) ";
		String sortSql = "";
		String fields = "";
		String table = "";
		String sortType = " desc ";
		String sortColumn = " nmCounts ";
		
		if (page != null) {
			sortType = " " + page.getOrder() + " ";
			sortColumn = " " + page.getOrderBy() + " ";
		}
		switch (timeLevel) {
		case 1:
			if (!flag) {
				sql = "select nmSpPort,nmCounts,intYear,intMonth,intDay,intHour,vcBelong,vcCompanyName";
			}
			sql = sql + " from  vStatHourSpMmsPortFlush where 1=1 "
					+ " and intYear=" + year + " and intMonth=" + month
					+ " and intDay=" + day + " and intHour=" + hour;
			break;
		case 2:
			if (!flag) {
				sql = "select nmSpPort,nmCounts,intYear,intMonth,intDay,vcBelong,vcCompanyName";
			}
			sql = sql + " from vStatDaySpMmsPortFlush where 1=1 "
					+ " and intYear=" + year + " and intMonth=" + month
					+ " and intDay=" + day;
			break;
		case 3:
			if (!flag) {
				sql = "select nmSpPort,nmCounts,intYear,intYear,intWeek,vcBelong,vcCompanyName";
			}
			sql = sql + " from vStatWeekSpMmsPortFlush where 1=1 "
					+ " and intYear=" + year 
//					+ " and intMonth=" + month
					+ " and intWeek=" + week;
			break;
		case 4:
			if (!flag) {
				sql = "select nmSpPort, nmCounts,intYear,intMonth,vcBelong,vcCompanyName";
			}
			sql = sql + " from vStatMonthSpMmsPortFlush where 1=1 "
					+ " and intYear=" + year + " and intMonth=" + month;
			break;
		}
		sql = sql + " and intRaitype = " + isTD;
		
		if(port != null && !port.equals(""))
		{
			sql = sql + " and nmSpPort like '%" + port + "%'";
		}
		
		if(company != null && !company.equals(""))
		{
			sql = sql + " and vcCompanyName like '%" + company + "%'";
		}
		
		
		if (!flag) {
			sortSql = sortSql + " order by  " + sortColumn + sortType;
		}
		sql = sql + sortSql;
		return sql;
	}
}
