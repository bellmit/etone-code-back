package com.symbol.app.mantoeye.dao.terminal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.symbol.app.mantoeye.constants.CommonConstants;
import com.symbol.app.mantoeye.dao.BulidView;
import com.symbol.app.mantoeye.dao.common.HibernateQueryDAO;
import com.symbol.app.mantoeye.dto.sports.CommonSport;
import com.symbol.app.mantoeye.util.CommonUtils;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.modules.orm.Page;

@Repository
public class TerminalBrandDetailDAO extends HibernateQueryDAO {
	public Page<CommonSport> query(final Page page, int isTD,
			int timeLevel, String time,int areaType,Long nmAreaId,String vcTerminalBrand) {
		Date date = CommonUtils.getDate(time);
		int year = CommonUtils.getYear(date);
		int month = CommonUtils.getMonth(date);
		int week = CommonUtils.getWeek(date);
		int day = CommonUtils.getDay(date);
		int hour = CommonUtils.getHour(date);
		String sql = this.buildSql(isTD, areaType, timeLevel, year, month,
				week, day, hour,nmAreaId,vcTerminalBrand, page);
		String sqls = sql.split("order by")[0];
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

	public List<CommonSport> listData(int isTD, int timeLevel,
			String time, int areaType,Long nmAreaId,String vcTerminalBrand) {
		Date date = CommonUtils.getDate(time);
		int year = CommonUtils.getYear(date);
		int month = CommonUtils.getMonth(date);
		int week = CommonUtils.getWeek(date);
		int day = CommonUtils.getDay(date);
		int hour = CommonUtils.getHour(date);
		String sql = this.buildSql(isTD, areaType, timeLevel, year, month,
				week, day, hour,nmAreaId,vcTerminalBrand, null);
		SQLQuery query = this.getSession().createSQLQuery(sql);
		List list = query.list();
		return buildBeanList(list, time, timeLevel);
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
	 * @return
	 */
	public String buildSql(int isTD, int areaType, int timeLevel, int year,
			int month, int week, int day, int hour,Long nmAreaId,String vcTerminalBrand, Page page) {
		int type =1;
		String sql = "";
		String sortSql = "";
		String fields = "vcTerminalBrand,vcTerminalName";
		String table = "";
		String sortType = " desc ";
		String sortColumn = " nmFlush ";
		if (page != null) {
			sortType = " " + page.getOrder() + " ";
			sortColumn = " " + page.getOrderBy() + " ";
		}
		String areaId="";
		switch (areaType) {
		case 1:
			areaId = "f.intBscId";
			break;
		case 2:
			areaId = "f.intSgsnId";
			break;
		case 3:
			areaId = "f.intBranchId";
			break;
		case 4:
			areaId = "f.intSaleAreaId";
			break;
		case 5:
			areaId = "f.intStreetId";
			break;
		}
		String whereConditionHour = " and f.intYear=" + year+ " and f.intMonth=" + month + " and f.intDay=" + day+ " and f.intHour=" + hour+ " and f.intRaitype = " + isTD+" and "+areaId+" = " + nmAreaId+ " and vcTerminalBrand = '" + vcTerminalBrand+"' ";
		String whereConditionDay = " and f.intYear=" + year+ " and f.intMonth=" + month + " and f.intDay=" + day+ " and f.intRaitype = " + isTD+" and "+areaId+" = " + nmAreaId+ " and vcTerminalBrand = '" + vcTerminalBrand+"' ";
		String whereConditionWeek = " and f.intYear=" + year+ " and f.intWeek=" + week+ " and f.intRaitype = " + isTD+" and "+areaId+" = " + nmAreaId+ " and vcTerminalBrand = '" + vcTerminalBrand+"' ";
		String whereConditionMonth = " and f.intYear=" + year+ " and f.intMonth=" + month+ " and f.intRaitype = " + isTD+" and "+areaId+" = " + nmAreaId+ " and vcTerminalBrand = '" + vcTerminalBrand+"' ";
		String orderStringHour = " ,f.intMonth,f.intDay,f.intHour, "+ sortColumn + sortType;
		String orderStringDay = " ,f.intMonth,f.intDay, "+ sortColumn + sortType;
		String orderStringWeek = " ,f.intWeek," +  sortColumn + sortType;
		String orderStringMonth = " ,f.intMonth," +  sortColumn + sortType;
		switch (areaType) {
		case 1:
			String bscCondition = " and f.intBscId = fu.intBscId ";
			table = (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_HOUR) ? BulidView.buildHourView("ftbStatBscHourTerminalTypeGprsSpace", fields, null, whereConditionHour, orderStringHour)
					: (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_DAY) ? BulidView.buildDayView("ftbStatBscHourTerminalTypeGprsSpace", "ftbStatDayBscTerminalTypeGprsSpaceUsers",fields, bscCondition,whereConditionDay,orderStringDay,areaType,type)
							: (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_WEEK) ? BulidView.buildWeekView("ftbStatBscHourTerminalTypeGprsSpace", "ftbStatWeekBscTerminalTypeGprsSpaceUsers", fields, bscCondition,whereConditionWeek,orderStringWeek,areaType,type)
									: BulidView.buildMonthView("ftbStatBscHourTerminalTypeGprsSpace", "ftbStatMonthBscTerminalTypeGprsSpaceUsers", fields, bscCondition,whereConditionMonth,orderStringMonth,areaType,type);
			break;
		case 2:
			String sgsnCondition = " and f.intSgsnId = fu.intSgsnId ";
			table = (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_HOUR) ? BulidView.buildHourView("ftbStatSgsnHourTerminalTypeGprsSpace", fields, null, whereConditionHour, orderStringHour)
					: (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_DAY) ? BulidView.buildDayView("ftbStatSgsnHourTerminalTypeGprsSpace", "ftbStatDaySgsnTerminalTypeGprsSpaceUsers", fields, sgsnCondition,whereConditionDay,orderStringDay,areaType,type)
							: (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_WEEK) ? BulidView.buildWeekView("ftbStatSgsnHourTerminalTypeGprsSpace", "ftbStatWeekSgsnTerminalTypeGprsSpaceUsers", fields, sgsnCondition,whereConditionWeek,orderStringWeek,areaType,type)
									: BulidView.buildMonthView("ftbStatSgsnHourTerminalTypeGprsSpace", "ftbStatMonthSgsnTerminalTypeGprsSpaceUsers", fields, sgsnCondition,whereConditionMonth,orderStringMonth,areaType,type);
			break;
		case 3:
			String branchCondition = " and f.intBranchId = fu.intBranchId ";
			table = (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_HOUR) ? BulidView.buildHourView("ftbStatBranchHourTerminalTypeGprsSpace", fields, null, whereConditionHour, orderStringHour)
					: (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_DAY) ? BulidView.buildDayView("ftbStatBranchHourTerminalTypeGprsSpace", "ftbStatDayBranchTerminalTypeGprsSpaceUsers", fields, branchCondition,whereConditionDay,orderStringDay,areaType,type)
							: (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_WEEK) ? BulidView.buildWeekView("ftbStatBranchHourTerminalTypeGprsSpace", "ftbStatWeekBranchTerminalTypeGprsSpaceUsers", fields, branchCondition,whereConditionWeek,orderStringWeek,areaType,type)
									: BulidView.buildMonthView("ftbStatBranchHourTerminalTypeGprsSpace", "ftbStatMonthBranchTerminalTypeGprsSpaceUsers", fields, branchCondition,whereConditionMonth,orderStringMonth,areaType,type);
			break;
		case 4:
			String saleAreaCondition = " and f.intSaleAreaId = fu.intSaleAreaId ";
			table = (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_HOUR) ? BulidView.buildHourView("ftbStatSaleAreaHourTerminalTypeGprsSpace", fields, null, whereConditionHour, orderStringHour)
					: (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_DAY) ? BulidView.buildDayView("ftbStatSaleAreaHourTerminalTypeGprsSpace", "ftbStatDaySaleAreaTerminalTypeGprsSpaceUsers", fields, saleAreaCondition,whereConditionDay,orderStringDay,areaType,type)
							: (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_WEEK) ? BulidView.buildWeekView("ftbStatSaleAreaHourTerminalTypeGprsSpace", "ftbStatWeekSaleAreaTerminalTypeGprsSpaceUsers", fields, saleAreaCondition,whereConditionWeek,orderStringWeek,areaType,type)
									: BulidView.buildMonthView("ftbStatSaleAreaHourTerminalTypeGprsSpace", "ftbStatMonthSaleAreaTerminalTypeGprsSpaceUsers", fields,saleAreaCondition,whereConditionMonth,orderStringMonth,areaType,type);
			break;
		case 5:
			String streetCondition = " and f.intStreetId = fu.intStreetId ";
			table = (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_HOUR) ? BulidView.buildHourView("ftbStatStreetHourTerminalTypeGprsSpace", fields, null, whereConditionHour, orderStringHour)
					: (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_DAY) ? BulidView.buildDayView("ftbStatStreetHourTerminalTypeGprsSpace", "ftbStatDayStreetTerminalTypeGprsSpaceUsers", fields, streetCondition,whereConditionDay,orderStringDay,areaType,type)
							: (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_WEEK) ?  BulidView.buildWeekView("ftbStatStreetHourTerminalTypeGprsSpace", "ftbStatWeekStreetTerminalTypeGprsSpaceUsers", fields,streetCondition,whereConditionWeek,orderStringWeek,areaType,type)
									: BulidView.buildMonthView("ftbStatStreetHourTerminalTypeGprsSpace", "ftbStatMonthStreetTerminalTypeGprsSpaceUsers", fields, streetCondition,whereConditionMonth,orderStringMonth,areaType,type);
			break;
		case 6:
			whereConditionHour = " and f.intYear=" + year+ " and f.intMonth=" + month + " and f.intDay=" + day+ " and f.intHour=" + hour+" and f.intRaitype = " + isTD+ " and vcTerminalBrand = '" + vcTerminalBrand+"' ";
			whereConditionDay = " and f.intYear=" + year+ " and f.intMonth=" + month + " and f.intDay=" + day+ " and f.intRaitype = " + isTD+ " and vcTerminalBrand = '" + vcTerminalBrand+"' ";
			whereConditionWeek = " and f.intYear=" + year+ " and f.intWeek=" + week+ " and f.intRaitype = " + isTD+ " and vcTerminalBrand = '" + vcTerminalBrand+"' ";
			whereConditionMonth = " and f.intYear=" + year+ " and f.intMonth=" + month+ " and f.intRaitype = " + isTD+ " and vcTerminalBrand = '" + vcTerminalBrand+"' ";
			table = (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_HOUR) ? BulidView.buildHourView("ftbStatHourTerminalTypeGprsSpace", fields, null, whereConditionHour, orderStringHour)
					: (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_DAY) ? BulidView.buildDayView("ftbStatHourTerminalTypeGprsSpace", "ftbStatDayTerminalTypeGprsSpaceUsers", fields,null,whereConditionDay,orderStringDay,areaType,type)
							: (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_WEEK) ? BulidView.buildWeekView("ftbStatHourTerminalTypeGprsSpace", "ftbStatWeekTerminalTypeGprsSpaceUsers", fields,null,whereConditionWeek,orderStringWeek,areaType,type)
									: BulidView.buildMonthView("ftbStatHourTerminalTypeGprsSpace", "ftbStatMonthTerminalTypeGprsSpaceUsers", fields,null,whereConditionMonth,orderStringMonth,areaType,type);
			break;
		}

		sql = table;
		return sql;
	}

	

	/**
	 * 组装BEAN实体
	 * 
	 * @param list
	 * @param timeLevel
	 * @return
	 */
	public List<CommonSport> buildBeanList(List list,String time, int timeLevel) {
		List<CommonSport> commonSportList = null;
		CommonSport commonSport = null;
		if (list != null && !list.isEmpty()) {
			commonSportList = new ArrayList<CommonSport>();
			for (int i = 0; i < list.size(); i++) {
				commonSport = new CommonSport();
				Object[] objs = (Object[]) list.get(i);
				commonSport.setIntUpFlush(Common.StringToLong(objs[0] + ""));// 上流量
				commonSport.setIntDownFlush(Common.StringToLong(objs[1] + ""));// 下流量
				commonSport.setTotalFlush(Common.StringToLong(objs[2] + ""));// 总流量
				commonSport.setIntImsis(Common.StringToLong(objs[3] + ""));// 用户数
				commonSport.setVisit(Common.StringToLong(objs[4] + ""));
				commonSport.setNmAveFlush(Common.StringTodouble(objs[5] + ""));
				commonSport.setIntYear(Common.StringToInt(objs[6] + ""));// 年
				switch (timeLevel) {
				case CommonConstants.MANTOEYE_TIME_LEVEL_HOUR:
					commonSport.setIntMonth(Common.StringToInt(objs[7] + ""));// 月
					commonSport.setIntDay(Common.StringToInt(objs[8] + ""));// 日
					commonSport.setIntHour(Common.StringToInt(objs[9] + ""));// 小时
					commonSport.setVcTerminalBrand(objs[11] + "");
					commonSport.setVcTerminalName(objs[12] + "");
					break;
				case CommonConstants.MANTOEYE_TIME_LEVEL_DAY:
					commonSport.setIntMonth(Common.StringToInt(objs[7] + ""));// 日
					commonSport.setIntDay(Common.StringToInt(objs[8] + ""));// 日
					commonSport.setVcTerminalBrand(objs[10] + "");
					commonSport.setVcTerminalName(objs[11] + "");
					break;
				case CommonConstants.MANTOEYE_TIME_LEVEL_WEEK:
					commonSport.setIntWeek(Common.StringToInt(objs[7] + ""));// 周
					commonSport.setVcTerminalBrand(objs[9] + "");
					commonSport.setVcTerminalName(objs[10] + "");
					break;
				case CommonConstants.MANTOEYE_TIME_LEVEL_MONTH:
					commonSport.setIntMonth(Common.StringToInt(objs[7] + ""));// 月
					commonSport.setVcTerminalBrand(objs[9] + "");
					commonSport.setVcTerminalName(objs[10] + "");
					break;
				}
				
				if (timeLevel == 3) {
					commonSport.setFullDate(CommonUtils.getDayInnerWeek(time));
				} else {
					commonSport.setFullDate(time);
				}
				commonSport.setSpanDate1(timeLevel);
				commonSport.calculateData1();
				commonSportList.add(commonSport);
			}
		}
		return commonSportList;
	}
}
