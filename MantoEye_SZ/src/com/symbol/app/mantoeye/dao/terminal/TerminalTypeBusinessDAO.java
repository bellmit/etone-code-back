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
public class TerminalTypeBusinessDAO extends HibernateQueryDAO {

	public Page<CommonSport> query(final Page page, int isTD,
			int timeLevel, String time,int areaType,Long nmAreaId,String vcTerminalNetType) {
		Date date = CommonUtils.getDate(time);
		int year = CommonUtils.getYear(date);
		int month = CommonUtils.getMonth(date);
		int week = CommonUtils.getWeek(date);
		int day = CommonUtils.getDay(date);
		int hour = CommonUtils.getHour(date);
		String sql = this.buildSql(isTD, areaType, timeLevel, year, month,
				week, day, hour,nmAreaId,vcTerminalNetType, page);
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
			String time, int areaType,Long nmAreaId,String vcTerminalNetType) {
		Date date = CommonUtils.getDate(time);
		int year = CommonUtils.getYear(date);
		int month = CommonUtils.getMonth(date);
		int week = CommonUtils.getWeek(date);
		int day = CommonUtils.getDay(date);
		int hour = CommonUtils.getHour(date);
		String sql = this.buildSql(isTD, areaType, timeLevel, year, month,
				week, day, hour,nmAreaId,vcTerminalNetType, null);
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
			int month, int week, int day, int hour,Long nmAreaId,String vcTerminalNetType, Page page) {
		int type = 2;
		String sql = "";
		String sortSql = "";
		String fields = " vcTerminalNetType,vcDimensName ";
		String table = "";
		String sortType = " desc ";
		String sortColumn = " nmFlush ";
		if (page != null) {
			sortType = " " + page.getOrder() + " ";
			sortColumn = " " + page.getOrderBy() + " ";
		}
		// 维度类型
		//String bscCondition = " and f.intBscId = f.intBscId and f.nmBussinessId=f.nmBussinessId and f.nmBussinessTypeId=f.nmBussinessTypeId "; 
		//String sgsnCondition = " and f.intSgsnId = f.intSgsnId and f.nmBussinessId=f.nmBussinessId and f.nmBussinessTypeId=f.nmBussinessTypeId ";
		//String branchCondition = " and f.intBranchId = f.intBranchId and f.nmBussinessId=f.nmBussinessId and f.nmBussinessTypeId=f.nmBussinessTypeId ";
		//String saleAreaCondition = " and f.intSaleAreaId = f.intSaleAreaId and f.nmBussinessId=f.nmBussinessId and f.nmBussinessTypeId=f.nmBussinessTypeId ";
		//String streetCondition = " and f.intStreetId = f.intStreetId and f.nmBussinessId=f.nmBussinessId and f.nmBussinessTypeId=f.nmBussinessTypeId ";
		String hourCondition = " inner join dtbGroupTree on f.nmBussinessId = dtbGroupTree.nmDimensId and dtbGroupTree.nmTypeId=1 " ;
		String condition = " and f.nmBussinessId=fu.nmBussinessId and f.nmBussinessTypeId=fu.nmBussinessTypeId inner join dtbGroupTree on f.nmBussinessId = dtbGroupTree.nmDimensId and dtbGroupTree.nmTypeId=1 " ;
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
		String whereConditionHour = " and f.intYear=" + year+ " and f.intMonth=" + month + " and f.intDay=" + day+ " and f.intHour=" + hour+ " and f.intRaitype = " + isTD+" and "+areaId+" = " + nmAreaId+ " and vcTerminalNetType = '" + vcTerminalNetType+"' ";
		String whereConditionDay = " and f.intYear=" + year+ " and f.intMonth=" + month + " and f.intDay=" + day+ " and f.intRaitype = " + isTD+" and "+areaId+" = " + nmAreaId+ " and vcTerminalNetType = '" + vcTerminalNetType+"' ";
		String whereConditionWeek = " and f.intYear=" + year+ " and f.intWeek=" + week+ " and f.intRaitype = " + isTD+" and "+areaId+" = " + nmAreaId+ " and vcTerminalNetType = '" + vcTerminalNetType+"' ";
		String whereConditionMonth = " and f.intYear=" + year+ " and f.intMonth=" + month+ " and f.intRaitype = " + isTD+" and "+areaId+" = " + nmAreaId+ " and vcTerminalNetType = '" + vcTerminalNetType+"' ";
		String orderStringHour = " ,f.intMonth,f.intDay,f.intDay, "+ sortColumn + sortType;
		String orderStringDay = " ,f.intMonth,f.intDay, "+ sortColumn + sortType;
		String orderStringWeek = " ,f.intWeek," +  sortColumn + sortType;
		String orderStringMonth = " ,f.intMonth," +  sortColumn + sortType;
		switch (areaType) {
		case 1:
			String bscCondition = " and f.intBscId = fu.intBscId ";
			condition = condition+bscCondition;
			table = (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_HOUR) ? BulidView.buildHourView("ftbStatBscHourTerminalTypeBussType", fields, hourCondition, whereConditionHour, orderStringHour)
					: (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_DAY) ? BulidView.buildDayView("ftbStatBscHourTerminalTypeBussType", "ftbStatDayBscTerminalTypeBussTypeUsers", fields, condition,whereConditionDay,orderStringDay,areaType,type)
							: (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_WEEK) ? BulidView.buildWeekView("ftbStatBscHourTerminalTypeBussType", "ftbStatWeekBscTerminalTypeBussTypeUsers", fields, condition,whereConditionWeek,orderStringWeek,areaType,type)
									: BulidView.buildMonthView("ftbStatBscHourTerminalTypeBussType", "ftbStatMonthBscTerminalTypeBussTypeUsers", fields, condition,whereConditionMonth,orderStringMonth,areaType,type);
			break;
		case 2:
			String sgsnCondition = " and f.intSgsnId = fu.intSgsnId ";
			condition = condition+sgsnCondition;
			table = (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_HOUR) ? BulidView.buildHourView("ftbStatSgsnHourTerminalTypeBussType", fields, hourCondition, whereConditionHour, orderStringHour)
					: (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_DAY) ? BulidView.buildDayView("ftbStatSgsnHourTerminalTypeBussType", "ftbStatDaySgsnTerminalTypeBussTypeUsers", fields, condition,whereConditionDay,orderStringDay,areaType,type)
							: (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_WEEK) ? BulidView.buildWeekView("ftbStatSgsnHourTerminalTypeBussType", "ftbStatWeekSgsnTerminalTypeBussTypeUsers", fields,condition,whereConditionWeek,orderStringWeek,areaType,type)
									: BulidView.buildMonthView("ftbStatSgsnHourTerminalTypeBussType", "ftbStatMonthSgsnTerminalTypeBussTypeUsers", fields,condition,whereConditionMonth,orderStringMonth,areaType,type);
			break;
		case 3:
			String branchCondition = " and f.intBranchId = fu.intBranchId ";
			condition = condition+branchCondition;
			table = (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_HOUR) ? BulidView.buildHourView("ftbStatBrachHourTerminalTypeBussType", fields, hourCondition, whereConditionHour, orderStringHour)
					: (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_DAY) ? BulidView.buildDayView("ftbStatBrachHourTerminalTypeBussType", "ftbStatDayBranchTerminalTypeBussTypeUsers",fields,condition,whereConditionDay,orderStringDay,areaType,type)
							: (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_WEEK) ? BulidView.buildWeekView("ftbStatBrachHourTerminalTypeBussType", "ftbStatWeekBranchTerminalTypeBussTypeUsers", fields, condition,whereConditionWeek,orderStringWeek,areaType,type)
									: BulidView.buildMonthView("ftbStatBrachHourTerminalTypeBussType", "ftbStatMonthBranchTerminalTypeBussTypeUsers", fields, condition,whereConditionMonth,orderStringMonth,areaType,type);
			break;
		case 4:
			String saleAreaCondition = " and f.intSaleAreaId = fu.intSaleAreaId ";
			condition = condition+saleAreaCondition;
			table = (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_HOUR) ? BulidView.buildHourView("ftbStatSaleAreaHourTerminalTypeBussType", fields, hourCondition, whereConditionHour, orderStringHour)
					: (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_DAY) ? BulidView.buildDayView("ftbStatSaleAreaHourTerminalTypeBussType", "ftbStatDaySaleAreaTerminalTypeBussTypeUsers",fields, condition,whereConditionDay,orderStringDay,areaType,type)
							: (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_WEEK) ? BulidView.buildWeekView("ftbStatSaleAreaHourTerminalTypeBussType", "ftbStatWeekSaleAreaTerminalTypeBussTypeUsers", fields, condition,whereConditionWeek,orderStringWeek,areaType,type)
									: BulidView.buildMonthView("ftbStatSaleAreaHourTerminalTypeBussType", "ftbStatMonthSaleAreaTerminalTypeBussTypeUsers", fields, condition,whereConditionMonth,orderStringMonth,areaType,type);
			break;
		case 5:
			String streetCondition = " and f.intStreetId = fu.intStreetId ";
			condition = condition+streetCondition;
			table = (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_HOUR) ? BulidView.buildHourView("ftbStatStreetHourTerminalTypeBussType", fields, hourCondition, whereConditionHour, orderStringHour)
					: (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_DAY) ? BulidView.buildDayView("ftbStatStreetHourTerminalTypeBussType", "ftbStatDayStreeterminalTypeBussTypeUsers", fields, condition,whereConditionDay,orderStringDay,areaType,type)
							: (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_WEEK) ?  BulidView.buildWeekView("ftbStatStreetHourTerminalTypeBussType", "ftbStatWeekStreeterminalTypeBussTypeUsers", fields, condition,whereConditionWeek,orderStringWeek,areaType,type)
									: BulidView.buildMonthView("ftbStatStreetHourTerminalTypeBussType", "ftbStatMonthStreeterminalTypeBussTypeUsers", fields, condition,whereConditionMonth,orderStringMonth,areaType,type);
			break;
		case 6:
			whereConditionHour = " and f.intYear=" + year+ " and f.intMonth=" + month + " and f.intDay=" + day+ " and f.intHour=" + hour+" and f.intRaitype = " + isTD+ " and vcTerminalNetType = '" + vcTerminalNetType+"' ";
			whereConditionDay = " and f.intYear=" + year+ " and f.intMonth=" + month + " and f.intDay=" + day+ " and f.intRaitype = " + isTD+ " and vcTerminalNetType = '" + vcTerminalNetType+"' ";
			whereConditionWeek = " and f.intYear=" + year+ " and f.intWeek=" + week+ " and f.intRaitype = " + isTD+ " and vcTerminalNetType = '" + vcTerminalNetType+"' ";
			whereConditionMonth = " and f.intYear=" + year+ " and f.intMonth=" + month+ " and f.intRaitype = " + isTD+ " and vcTerminalNetType = '" + vcTerminalNetType+"' ";
			table = (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_HOUR) ? BulidView.buildHourView("ftbStatHourTerminalTypeBussType", fields, hourCondition, whereConditionHour, orderStringHour)
					: (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_DAY) ? BulidView.buildDayView("ftbStatHourTerminalTypeBussType", "ftbStatDayTerminalTypeBussTypeUsers", fields,condition,whereConditionDay,orderStringDay,areaType,type)
							: (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_WEEK) ? BulidView.buildWeekView("ftbStatHourTerminalTypeBussType", "ftbStatWeekTerminalTypeBussTypeUsers", fields,condition,whereConditionWeek,orderStringWeek,areaType,type)
									: BulidView.buildMonthView("ftbStatHourTerminalTypeBussType", "ftbStatMonthTerminalTypeBussTypeUsers",fields,condition,whereConditionMonth,orderStringMonth,areaType,type);
			break;
		}

		// 时间粒度
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
					commonSport.setIntMonth(Common.StringToInt(objs[7] + ""));
					commonSport.setIntDay(Common.StringToInt(objs[8] + ""));// 日
					commonSport.setIntHour(Common.StringToInt(objs[9] + ""));// 小时
					commonSport.setVcTerminalNetType(objs[11] + "");
					commonSport.setVcBussinessName(objs[12] + "");
					break;
				case CommonConstants.MANTOEYE_TIME_LEVEL_DAY:
					commonSport.setIntMonth(Common.StringToInt(objs[7] + ""));// 月
					commonSport.setIntDay(Common.StringToInt(objs[8] + ""));// 日
					commonSport.setVcTerminalNetType(objs[10] + "");
					commonSport.setVcBussinessName(objs[11] + "");
					break;
				case CommonConstants.MANTOEYE_TIME_LEVEL_WEEK:
					commonSport.setIntWeek(Common.StringToInt(objs[7] + ""));// 周
					commonSport.setVcTerminalNetType(objs[9] + "");
					commonSport.setVcBussinessName(objs[10] + "");
					break;
				case CommonConstants.MANTOEYE_TIME_LEVEL_MONTH:
					commonSport.setIntMonth(Common.StringToInt(objs[7] + ""));// 月
					commonSport.setVcTerminalNetType(objs[9] + "");
					commonSport.setVcBussinessName(objs[10] + "");
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
