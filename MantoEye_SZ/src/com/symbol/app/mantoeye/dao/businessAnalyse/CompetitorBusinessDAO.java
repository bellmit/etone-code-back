package com.symbol.app.mantoeye.dao.businessAnalyse;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;
import com.symbol.app.mantoeye.constants.CommonConstants;
import com.symbol.app.mantoeye.dao.common.HibernateQueryDAO;
import com.symbol.app.mantoeye.dto.sports.CommonSport;
import com.symbol.app.mantoeye.util.CommonUtils;
import com.symbol.app.mantoeye.util.MantoEyeUtils;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.modules.orm.Page;

@Repository
public class CompetitorBusinessDAO extends HibernateQueryDAO {

	// 查询该时间的全网总流量和总用户数
	private Double[] findSumTotalData(int timeLevel, String date) {
		String totalSql = MantoEyeUtils.getTotalFlushAndImsiSql(1, timeLevel,
				date);
		Double[] dbs = new Double[] { -1.0, -1.0 };
		List l = this.getSession().createSQLQuery(totalSql).list();
		if (l != null && l.size() > 0) {
			Object[] objs = (Object[]) l.get(0);
			dbs[0] = Common.StringTodouble(objs[0] + "");
			dbs[1] = Common.StringTodouble(objs[1] + "");
		}
		dbs[0] = dbs[0] == 0 ? -1.0 : dbs[0];
		dbs[1] = dbs[1] == 0 ? -1.0 : dbs[1];
		return dbs;
	}

	
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
	public Page<CommonSport> query(final Page page, int isTD,
			 int timeLevel, String time,int areaType,String business,String vcName,String bussinessId) {
		Date date = CommonUtils.getDate(time);
		int year = CommonUtils.getYear(date);
		int month = CommonUtils.getMonth(date);
		int week = CommonUtils.getWeek(date);
		int day = CommonUtils.getDay(date);
		int hour = CommonUtils.getHour(date);
		String sql = this.buildSql(isTD, areaType, timeLevel, year,
				month, week, day, hour,business,vcName, bussinessId,page);
		String sqls = sql.split("order by")[0];
		if (sqls.indexOf("'全网'")>0) {
			sqls = sqls.replace("'全网',", "");
		}
		String totalSql = "select count(1) from ( " + sqls + " ) as totalCount";
		Object totalObj = this.getSession().createSQLQuery(totalSql)
				.uniqueResult();
		Integer total = Integer.parseInt(null == totalObj.toString()
				|| "".equals(totalObj.toString()) ? "0" : totalObj.toString());
		List<Object[]> list = this.getSession().createSQLQuery(sql)
				.setFirstResult(page.getFirst()).setMaxResults(
						page.getPageSize()).list();
		Page newPage = new Page();
		newPage.setPageSize(page.getPageSize());
		newPage.setTotalCount(total);
		newPage.setResult(buildBeanList(list, time, timeLevel));
		newPage.setPageNo(page.getPageNo());
		return newPage;
	}

	public List<CommonSport> listData(int isTD,
			 int timeLevel, String time,int areaType,String business,String vcName,String bussinessId) {
		Date date = CommonUtils.getDate(time);
		int year = CommonUtils.getYear(date);
		int month = CommonUtils.getMonth(date);
		int week = CommonUtils.getWeek(date);
		int day = CommonUtils.getDay(date);
		int hour = CommonUtils.getHour(date);
		String sql = this.buildSql(isTD, areaType, timeLevel, year,
				month, week, day, hour,business,vcName,bussinessId, null);
		SQLQuery query = this.getSession().createSQLQuery(sql);
		List list = query.list();
		return buildBeanList(list, time, timeLevel);
	}

	public List queryCompetitor(Long bussinessId) {
		String sql = "select nmBussinessRivalId from ftbBussRivalList br,ftbBussList bl where br.nmBussinessId=bl.nmBussListId and bl.nmBussinessId=" + bussinessId;
		SQLQuery query = this.getSession().createSQLQuery(sql);
		List list = query.list();
		return list;
	}
	
	/**
	 * 组装BEAN实体
	 * 
	 * @param list
	 * @param timeLevel
	 * @return
	 */
	public List<CommonSport> buildBeanList(List list, String time, int timeLevel) {
		// 计算占比
		Double[] dbs = findSumTotalData(timeLevel, time);
		
		List<CommonSport> commonSportList = null;
		CommonSport commonSport = null;
		if (list != null && !list.isEmpty()) {
			commonSportList = new ArrayList<CommonSport>();
			for (int i = 0; i < list.size(); i++) {
				commonSport = new CommonSport();
				Object[] objs = (Object[]) list.get(i);
				commonSport.setArea(objs[0] + "");
				commonSport.setBusinessName(objs[1] + "");// 业务名称
				commonSport.setIntUpFlush(Common.StringToLong(objs[2] + ""));// 上流量
				commonSport.setIntDownFlush(Common.StringToLong(objs[3] + ""));// 下流量
				commonSport.setTotalFlush(Common.StringToLong(objs[4] + ""));// 总流量
				commonSport.setIntImsis(Common.StringToLong(objs[5] + ""));// 用户数
				commonSport.setVisit(Common.StringToLong(objs[6] + ""));// 访问次数
				commonSport.setNmAveFlush(Common.StringTodouble(objs[7] + ""));
				if (timeLevel == 3) {
					commonSport.setFullDate(CommonUtils.getDayInnerWeek(time));
				} else {
					commonSport.setFullDate(time);
				}
				commonSport.calculateData1();
				commonSport.calculateFlushRate(dbs[0], dbs[1]);
				// CommonSport.numberFormatData();
				commonSportList.add(commonSport);
			}
		}
		return commonSportList;
	}

	/**
	 * 组装SQL语句
	 * 
	 * @param isTD
	 * @param areaType
	 * @param timeLevel
	 * @param year
	 * @param month
	 * @param week
	 * @param day
	 * @param hour
	 */
	public String buildSql(int isTD, int areaType,
			int timeLevel, int year, int month, int week, int day, int hour,String business,String vcName,String bussinessId,
			Page page) {
		String sql = "";
		String fields = "";
		String table = "";
		String name = "";
		String sortSql = "";
		String sortType = " desc ";
		String sortColumn = " nmFlush ";
		if (page != null) {
			sortType = " " + page.getOrder() + " ";
			sortColumn = " " + page.getOrderBy() + " ";
			if(page.getOrderBy().trim().equals("area")){
				sortColumn = " vcName ";
			}
			if(page.getOrderBy().trim().equals("businessname")){
				sortColumn = " vcDimensName ";
			}
		}
		// 维度类型
		switch (areaType) {
		case 1:
			fields = " vcName,vcDimensName, ";
			table = (timeLevel == 1) ? "vStatHourBscBussType"
					: (timeLevel == 2) ? "vStatDayBscBussType"
							: (timeLevel == 3) ? "vStatWeekBscBussType"
									: "vStatMonthBscBussType";
			name = "vcName";
			break;
		case 2:
			fields = " vcName,vcDimensName, ";
			table = (timeLevel == 1) ? "vStatHourSgsnBussType"
					: (timeLevel == 2) ? "vStatDaySgsnBussType"
							: (timeLevel == 3) ? "vStatWeekSgsnBussType"
									: "vStatMonthSgsnBussType";
			name = "vcName";
			break;
		case 3:
			fields = " vcName,vcDimensName, ";
			table = (timeLevel == 1) ? "vStatHourStreetBussType"
					: (timeLevel == 2) ? "vStatDayStreetBussType"
							: (timeLevel == 3) ? "vStatWeekStreetBussType"
									: "vStatMonthStreetBussType";
			name = "vcName";
			break;
		case 4:
			fields = " vcSaleAreaName,vcDimensName, ";
			table = (timeLevel == 1) ? "vStatHourSaleAreaBussType"
					: (timeLevel == 2) ? "vStatDaySaleAreaBussType"
							: (timeLevel == 3) ? "vStatWeekSaleAreaBussType"
									: "vStatMonthSaleAreaBussType";
			name = "vcSaleAreaName";
			break;
		case 5:
			fields = " vcBranchName,vcDimensName, ";
			table = (timeLevel == 1) ? "vStatHourCompanyBussType"
					: (timeLevel == 2) ? "vStatDayCompanyBussType"
							: (timeLevel == 3) ? "vStatWeekCompanyBussType"
									: "vStatMonthCompanyBussType";
			name = "vcBranchName";
			break;
		case 6:
			fields = " '全网',vcDimensName, ";
			table = (timeLevel == 1) ? "vStatHourGprsBuss"
					: (timeLevel == 2) ? "vStatDayGprsBuss"
							: (timeLevel == 3) ? "vStatWeekGprsBuss"
									: "vStatMonthGprsBuss";
			break;
		}

		sql = " select " + fields;

		// 时间粒度
		switch (timeLevel) {
		case 1:
			sql = sql
					+ " nmAppUpFlush,nmAppDownFlush,nmFlush,nmUsers,nmVisitCounts,nmAveFlush,intYear,intMonth,intDay,intHour"
					+ " from " + table + " where 1=1 " + " and intYear=" + year
					+ " and intMonth=" + month + " and intDay=" + day
					+ " and intHour=" + hour;
			break;
		case 2:
			sql = sql
					+ " nmAppUpFlush,nmAppDownFlush,nmFlush,nmUsers,nmVisitCounts,nmAveFlush,intYear,intMonth,intDay"
					+ " from " + table + " where 1=1 " + " and intYear=" + year
					+ " and intMonth=" + month + " and intDay=" + day;
			break;
		case 3:
			sql = sql
					+ " nmAppUpFlush,nmAppDownFlush,nmFlush,nmUsers,nmVisitCounts,nmAveFlush,intYear,intWeek"
					+ " from " + table + " where 1=1 " + " and intYear=" + year
					// + " and intMonth=" + month

					+ " and intWeek=" + week;
			break;
		case 4:
			sql = sql
					+ " nmAppUpFlush,nmAppDownFlush,nmFlush,nmUsers,nmVisitCounts,nmAveFlush,intYear,intMonth"
					+ " from " + table + " where 1=1 " + " and intYear=" + year
					+ " and intMonth=" + month;
			break;
		}

		sql = sql + " and intRaitype = " + isTD;
		//sql = sql + " and " + name + " = " + "'" + areaName + "'";
		sql = sql + " and  nmBussinessId in ("+bussinessId+ ")";
		if (!name.equals("")) {
			sql = sql +" and "+name+ "='"+vcName+"'";
		}
		sortSql = sortSql + "  order by " + sortColumn + sortType;
		sql = sql + sortSql;
		return sql;
	}
	
	
}
