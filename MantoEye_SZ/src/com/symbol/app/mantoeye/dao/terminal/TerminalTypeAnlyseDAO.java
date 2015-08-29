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
public class TerminalTypeAnlyseDAO extends HibernateQueryDAO {

	public Page<CommonSport> query(final Page page, int isTD,
			int timeLevel, String time,int areaType) {
		Date date = CommonUtils.getDate(time);
		int year = CommonUtils.getYear(date);
		int month = CommonUtils.getMonth(date);
		int week = CommonUtils.getWeek(date);
		int day = CommonUtils.getDay(date);
		int hour = CommonUtils.getHour(date);
		String sql = this.buildSql(isTD, areaType, timeLevel, year, month,
				week, day, hour, page);
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
			String time, int areaType) {
		Date date = CommonUtils.getDate(time);
		int year = CommonUtils.getYear(date);
		int month = CommonUtils.getMonth(date);
		int week = CommonUtils.getWeek(date);
		int day = CommonUtils.getDay(date);
		int hour = CommonUtils.getHour(date);
		String sql = this.buildSql(isTD, areaType, timeLevel, year, month,
				week, day, hour, null);
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
			int month, int week, int day, int hour, Page page) {
		int type=1;
		String sql = "";
		String sortSql = "";
		String fields = "";
		String table = "";
		String sortType = " desc ";
		String sortColumn = " nmFlush ";
		if (page != null) {
			sortType = " " + page.getOrder() + " ";
			sortColumn = " " + page.getOrderBy() + " ";
		}
		// 维度类型
		String whereConditionHour = " and f.intYear=" + year+ " and f.intMonth=" + month + " and f.intDay=" + day+ " and f.intHour=" + hour+ " and f.intRaitype = " + isTD;
		String whereConditionDay = " and f.intYear=" + year+ " and f.intMonth=" + month + " and f.intDay=" + day+ " and f.intRaitype = " + isTD;
		String whereConditionWeek = " and f.intYear=" + year+ " and f.intWeek=" + week+ " and f.intRaitype = " + isTD;
		String whereConditionMonth = " and f.intYear=" + year+ " and f.intMonth=" + month+ " and f.intRaitype = " + isTD;
		String orderStringHour = " ,f.intMonth,f.intDay,f.intHour, "+ sortColumn + sortType;
		String orderStringDay = " ,f.intMonth,f.intDay, "+ sortColumn + sortType;
		String orderStringWeek = " ,f.intWeek," +  sortColumn + sortType;
		String orderStringMonth = " ,f.intMonth," +  sortColumn + sortType;
		switch (areaType) {
		case 1:
			String bscHourCondition = " inner join dtbBsc on f.intBscId = dtbBsc.intBscId ";
			String bscCondition = " and f.intBscId = fu.intBscId inner join dtbBsc on f.intBscId = dtbBsc.intBscId";
			fields = " vcTerminalNetType,f.intBscId,vcName ";
			table = (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_HOUR) ? BulidView.buildHourView("ftbStatBscHourTerminalTypeGprsSpace", fields, bscHourCondition, whereConditionHour, orderStringHour)
					: (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_DAY) ? BulidView.buildDayView("ftbStatBscHourTerminalTypeGprsSpace", "ftbStatDayBscTerminalTypeGprsSpaceUsers",fields, bscCondition,whereConditionDay,orderStringDay,areaType,type)
							: (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_WEEK) ? BulidView.buildWeekView("ftbStatBscHourTerminalTypeGprsSpace", "ftbStatWeekBscTerminalTypeGprsSpaceUsers", fields, bscCondition,whereConditionWeek,orderStringWeek,areaType,type)
									: BulidView.buildMonthView("ftbStatBscHourTerminalTypeGprsSpace", "ftbStatMonthBscTerminalTypeGprsSpaceUsers", fields, bscCondition,whereConditionMonth,orderStringMonth,areaType,type);
			break;
		case 2:
			String sgsnHourCondition = " inner join dtbGsn on f.intSgsnId = dtbGsn.intSgsnId ";
			String sgsnCondition = " and f.intSgsnId = fu.intSgsnId inner join dtbGsn on f.intSgsnId = dtbGsn.intSgsnId ";
			fields = " vcTerminalNetType,f.intSgsnId,vcName ";
			table = (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_HOUR) ? BulidView.buildHourView("ftbStatSgsnHourTerminalTypeGprsSpace", fields, sgsnHourCondition, whereConditionHour, orderStringHour)
					: (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_DAY) ? BulidView.buildDayView("ftbStatSgsnHourTerminalTypeGprsSpace", "ftbStatDaySgsnTerminalTypeGprsSpaceUsers", fields, sgsnCondition,whereConditionDay,orderStringDay,areaType,type)
							: (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_WEEK) ? BulidView.buildWeekView("ftbStatSgsnHourTerminalTypeGprsSpace", "ftbStatWeekSgsnTerminalTypeGprsSpaceUsers", fields, sgsnCondition,whereConditionWeek,orderStringWeek,areaType,type)
									: BulidView.buildMonthView("ftbStatSgsnHourTerminalTypeGprsSpace", "ftbStatMonthSgsnTerminalTypeGprsSpaceUsers", fields, sgsnCondition,whereConditionMonth,orderStringMonth,areaType,type);
			break;
		case 3:
			String branchHourCondition = " inner join dtbSubsidiaryCompany on f.intBranchId = dtbSubsidiaryCompany.intBranchId ";
			String branchCondition = " and f.intBranchId = fu.intBranchId inner join dtbSubsidiaryCompany on f.intBranchId = dtbSubsidiaryCompany.intBranchId ";
			fields = " vcTerminalNetType,f.intBranchId,vcBranchName ";
			table = (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_HOUR) ? BulidView.buildHourView("ftbStatBranchHourTerminalTypeGprsSpace", fields, branchHourCondition, whereConditionHour, orderStringHour)
					: (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_DAY) ? BulidView.buildDayView("ftbStatBranchHourTerminalTypeGprsSpace", "ftbStatDayBranchTerminalTypeGprsSpaceUsers", fields, branchCondition,whereConditionDay,orderStringDay,areaType,type)
							: (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_WEEK) ? BulidView.buildWeekView("ftbStatBranchHourTerminalTypeGprsSpace", "ftbStatWeekBranchTerminalTypeGprsSpaceUsers", fields, branchCondition,whereConditionWeek,orderStringWeek,areaType,type)
									: BulidView.buildMonthView("ftbStatBranchHourTerminalTypeGprsSpace", "ftbStatMonthBranchTerminalTypeGprsSpaceUsers", fields, branchCondition,whereConditionMonth,orderStringMonth,areaType,type);
			break;
		case 4:
			String saleAreaHourCondition = " inner join dtbSaleArea on f.intSaleAreaId = dtbSaleArea.intSaleAreaId ";
			String saleAreaCondition = " and f.intSaleAreaId = fu.intSaleAreaId inner join dtbSaleArea on f.intSaleAreaId = dtbSaleArea.intSaleAreaId ";
			fields = " vcTerminalNetType,f.intSaleAreaId,vcSaleAreaName ";
			table = (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_HOUR) ? BulidView.buildHourView("ftbStatSaleAreaHourTerminalTypeGprsSpace", fields, saleAreaHourCondition, whereConditionHour, orderStringHour)
					: (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_DAY) ? BulidView.buildDayView("ftbStatSaleAreaHourTerminalTypeGprsSpace", "ftbStatDaySaleAreaTerminalTypeGprsSpaceUsers", fields, saleAreaCondition,whereConditionDay,orderStringDay,areaType,type)
							: (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_WEEK) ? BulidView.buildWeekView("ftbStatSaleAreaHourTerminalTypeGprsSpace", "ftbStatWeekSaleAreaTerminalTypeGprsSpaceUsers", fields, saleAreaCondition,whereConditionWeek,orderStringWeek,areaType,type)
									: BulidView.buildMonthView("ftbStatSaleAreaHourTerminalTypeGprsSpace", "ftbStatMonthSaleAreaTerminalTypeGprsSpaceUsers", fields,saleAreaCondition,whereConditionMonth,orderStringMonth,areaType,type);
			break;
		case 5:
			String streetHourCondition = " inner join dtbStreet on f.intStreetId = dtbStreet.intStreetId ";
			String streetCondition = " and f.intStreetId = fu.intStreetId inner join dtbStreet on f.intStreetId = dtbStreet.intStreetId ";
			fields = " vcTerminalNetType,f.intStreetId,vcName ";
			table = (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_HOUR) ? BulidView.buildHourView("ftbStatStreetHourTerminalTypeGprsSpace", fields, streetHourCondition, whereConditionHour, orderStringHour)
					: (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_DAY) ? BulidView.buildDayView("ftbStatStreetHourTerminalTypeGprsSpace", "ftbStatDayStreetTerminalTypeGprsSpaceUsers", fields, streetCondition,whereConditionDay,orderStringDay,areaType,type)
							: (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_WEEK) ?  BulidView.buildWeekView("ftbStatStreetHourTerminalTypeGprsSpace", "ftbStatWeekStreetTerminalTypeGprsSpaceUsers", fields,streetCondition,whereConditionWeek,orderStringWeek,areaType,type)
									: BulidView.buildMonthView("ftbStatStreetHourTerminalTypeGprsSpace", "ftbStatMonthStreetTerminalTypeGprsSpaceUsers", fields, streetCondition,whereConditionMonth,orderStringMonth,areaType,type);
			break;
		case 6:
			fields = " vcTerminalNetType ";
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
					commonSport.setIntMonth(Common.StringToInt(objs[7] + ""));
					commonSport.setIntDay(Common.StringToInt(objs[8] + ""));// 日
					commonSport.setIntHour(Common.StringToInt(objs[9] + ""));// 小时
					commonSport.setVcTerminalNetType(objs[11] + "");
					if (objs.length==14) {
						commonSport.setNmAreaId(Common.StringToLong(objs[12] + ""));
						commonSport.setVcName(objs[13]+"");
					}else{
						commonSport.setVcName("全网");
					}
					break;
				case CommonConstants.MANTOEYE_TIME_LEVEL_DAY:
					commonSport.setIntMonth(Common.StringToInt(objs[7] + ""));
					commonSport.setIntDay(Common.StringToInt(objs[8] + ""));// 日
					commonSport.setVcTerminalNetType(objs[10] + "");
					if (objs.length==13) {
						commonSport.setNmAreaId(Common.StringToLong(objs[11] + ""));
						commonSport.setVcName(objs[12]+"");
					}else{
						commonSport.setVcName("全网");
					}
					break;
				case CommonConstants.MANTOEYE_TIME_LEVEL_WEEK:
					commonSport.setIntWeek(Common.StringToInt(objs[7] + ""));// 周
					commonSport.setVcTerminalNetType(objs[9] + "");
					if (objs.length==12) {
						commonSport.setNmAreaId(Common.StringToLong(objs[10] + ""));
						commonSport.setVcName(objs[11]+"");
					}else{
						commonSport.setVcName("全网");
					}
					break;
				case CommonConstants.MANTOEYE_TIME_LEVEL_MONTH:
					commonSport.setIntMonth(Common.StringToInt(objs[7] + ""));// 月
					commonSport.setVcTerminalNetType(objs[9] + "");
					if (objs.length==12) {
						commonSport.setNmAreaId(Common.StringToLong(objs[10] + ""));
						commonSport.setVcName(objs[11]+"");
					}else{
						commonSport.setVcName("全网");
					}
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
