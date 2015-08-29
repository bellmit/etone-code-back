package com.symbol.app.mantoeye.dao.terminal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.symbol.app.mantoeye.constants.CommonConstants;
import com.symbol.app.mantoeye.dao.common.HibernateQueryDAO;
import com.symbol.app.mantoeye.dto.sports.CommonSport;
import com.symbol.app.mantoeye.util.CommonUtils;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.modules.orm.Page;

@Repository
public class TerminalSystemDetailDAO extends HibernateQueryDAO {
	public Page<CommonSport> query(final Page page, int isTD,
			int timeLevel, String time,int areaType,String vcName,String vcTerminalBrand) {
		Date date = CommonUtils.getDate(time);
		int year = CommonUtils.getYear(date);
		int month = CommonUtils.getMonth(date);
		int week = CommonUtils.getWeek(date);
		int day = CommonUtils.getDay(date);
		int hour = CommonUtils.getHour(date);
		String sql = this.buildSql(isTD, areaType, timeLevel, year, month,
				week, day, hour,vcName,vcTerminalBrand, page);
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
			String time, int areaType,String vcName,String vcTerminalBrand) {
		Date date = CommonUtils.getDate(time);
		int year = CommonUtils.getYear(date);
		int month = CommonUtils.getMonth(date);
		int week = CommonUtils.getWeek(date);
		int day = CommonUtils.getDay(date);
		int hour = CommonUtils.getHour(date);
		String sql = this.buildSql(isTD, areaType, timeLevel, year, month,
				week, day, hour,vcName,vcTerminalBrand, null);
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
			int month, int week, int day, int hour,String vcName,String vcTerminalBrand, Page page) {
		String sql = "";
		/*String sortSql = "";
		String fields = " vcTerminalBrand,vcTerminalOS,vcName ";
		String table = "";
		String sortType = " desc ";
		String sortColumn = " nmFlush ";
		if (page != null) {
			sortType = " " + page.getOrder() + " ";
			sortColumn = " " + page.getOrderBy() + " ";
		}
		// 维度类型
		String bscCondition = " and v.intBscId = f.intBscId "; 
		String sgsnCondition = " and v.intSgsnId = f.intSgsnId ";
		String branchCondition = " and v.intBranchId = f.intBranchId ";
		String saleAreaCondition = " and v.intSaleAreaId = f.intSaleAreaId ";
		String streetCondition = " and v.intStreetId = f.intStreetId ";
		String whereConditionDay = " and v.intYear=" + year+ " and v.intMonth=" + month + " and v.intDay=" + day+ " and v.intRaitype = " + isTD+" and vcName = '" + vcName+"' "+ " and vcTerminalBrand = '" + vcTerminalBrand+"' ";
		String whereConditionWeek = " and v.intYear=" + year+ " and v.intWeek=" + week+ " and v.intRaitype = " + isTD+" and vcName = '" + vcName+"' "+ " and vcTerminalBrand = '" + vcTerminalBrand+"' ";
		String whereConditionMonth = " and v.intYear=" + year+ " and v.intMonth=" + month+ " and v.intRaitype = " + isTD+" and vcName = '" + vcName+"' "+ " and vcTerminalBrand = '" + vcTerminalBrand+"' ";
		String orderStringDay = " ,v.intMonth,v.intDay, "+ sortColumn + sortType;
		String orderStringWeek = " ,v.intWeek," +  sortColumn + sortType;
		String orderStringMonth = " ,v.intMonth," +  sortColumn + sortType;
		switch (areaType) {
		case 1:
			table = (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_HOUR) ? "vStatBscHourTerminalTypeGprsSpace"
					: (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_DAY) ? BulidView.buildDayView("vStatBscHourTerminalTypeGprsSpace", "ftbStatDayBscTerminalTypeGprsSpaceUsers",fields, bscCondition,whereConditionDay,orderStringDay,areaType)
							: (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_WEEK) ? BulidView.buildWeekView("vStatBscHourTerminalTypeGprsSpace", "ftbStatWeekBscTerminalTypeGprsSpaceUsers", fields, bscCondition,whereConditionWeek,orderStringWeek)
									: BulidView.buildMonthView("vStatBscHourTerminalTypeGprsSpace", "ftbStatMonthBscTerminalTypeGprsSpaceUsers", fields, bscCondition,whereConditionMonth,orderStringMonth);
			break;
		case 2:
			table = (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_HOUR) ? "vStatSgsnHourTerminalTypeGprsSpace"
					: (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_DAY) ? BulidView.buildDayView("vStatSgsnHourTerminalTypeGprsSpace", "ftbStatDaySgsnTerminalTypeGprsSpaceUsers", fields, sgsnCondition,whereConditionDay,orderStringDay,areaType)
							: (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_WEEK) ? BulidView.buildWeekView("vStatSgsnHourTerminalTypeGprsSpace", "ftbStatWeekSgsnTerminalTypeGprsSpaceUsers", fields, sgsnCondition,whereConditionWeek,orderStringWeek)
									: BulidView.buildMonthView("vStatSgsnHourTerminalTypeGprsSpace", "ftbStatMonthSgsnTerminalTypeGprsSpaceUsers", fields, sgsnCondition,whereConditionMonth,orderStringMonth);
			break;
		case 3:
			table = (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_HOUR) ? "vStatBranchHourTerminalTypeGprsSpace"
					: (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_DAY) ? BulidView.buildDayView("vStatBranchHourTerminalTypeGprsSpace", "ftbStatDayBranchTerminalTypeGprsSpaceUsers", fields, branchCondition,whereConditionDay,orderStringDay,areaType)
							: (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_WEEK) ? BulidView.buildWeekView("vStatBranchHourTerminalTypeGprsSpace", "ftbStatWeekBranchTerminalTypeGprsSpaceUsers", fields, branchCondition,whereConditionWeek,orderStringWeek)
									: BulidView.buildMonthView("vStatBranchHourTerminalTypeGprsSpace", "ftbStatMonthBranchTerminalTypeGprsSpaceUsers", fields, branchCondition,whereConditionMonth,orderStringMonth);
			break;
		case 4:
			table = (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_HOUR) ? "vStatSaleAreaHourTerminalTypeGprsSpace"
					: (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_DAY) ? BulidView.buildDayView("vStatSaleAreaHourTerminalTypeGprsSpace", "ftbStatDaySaleAreaTerminalTypeGprsSpaceUsers", fields, saleAreaCondition,whereConditionDay,orderStringDay,areaType)
							: (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_WEEK) ? BulidView.buildWeekView("vStatSaleAreaHourTerminalTypeGprsSpace", "ftbStatWeekSaleAreaTerminalTypeGprsSpaceUsers", fields, saleAreaCondition,whereConditionWeek,orderStringWeek)
									: BulidView.buildMonthView("vStatSaleAreaHourTerminalTypeGprsSpace", "ftbStatMonthSaleAreaTerminalTypeGprsSpaceUsers", fields,saleAreaCondition,whereConditionMonth,orderStringMonth);
			break;
		case 5:
			table = (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_HOUR) ? "vStatStreetHourTerminalTypeGprsSpace"
					: (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_DAY) ? BulidView.buildDayView("vStatStreetHourTerminalTypeGprsSpace", "ftbStatDayStreetTerminalTypeGprsSpaceUsers", fields, streetCondition,whereConditionDay,orderStringDay,areaType)
							: (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_WEEK) ?  BulidView.buildWeekView("vStatStreetHourTerminalTypeGprsSpace", "ftbStatWeekStreetTerminalTypeGprsSpaceUsers", fields,streetCondition,whereConditionWeek,orderStringWeek)
									: BulidView.buildMonthView("vStatStreetHourTerminalTypeGprsSpace", "ftbStatMonthStreetTerminalTypeGprsSpaceUsers", fields, streetCondition,whereConditionMonth,orderStringMonth);
			break;
		case 6:
			fields = " vcTerminalBrand,vcTerminalOS ";
			 whereConditionDay = " and v.intYear=" + year+ " and v.intMonth=" + month + " and v.intDay=" + day+ " and v.intRaitype = " + isTD+ " and vcTerminalBrand = '" + vcTerminalBrand+"' ";
			 whereConditionWeek = " and v.intYear=" + year+ " and v.intWeek=" + week+ " and v.intRaitype = " + isTD+ " and vcTerminalBrand = '" + vcTerminalBrand+"' ";
			 whereConditionMonth = " and v.intYear=" + year+ " and v.intMonth=" + month+ " and v.intRaitype = " + isTD+ " and vcTerminalBrand = '" + vcTerminalBrand+"' ";
			table = (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_HOUR) ? "vStatHourTerminalTypeGprsSpace"
					: (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_DAY) ? BulidView.buildDayView("vStatHourTerminalTypeGprsSpace", "ftbStatDayTerminalTypeGprsSpaceUsers", fields,null,whereConditionDay,orderStringDay,areaType)
							: (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_WEEK) ? BulidView.buildWeekView("vStatHourTerminalTypeGprsSpace", "ftbStatWeekTerminalTypeGprsSpaceUsers", fields,null,whereConditionWeek,orderStringWeek)
									: BulidView.buildMonthView("vStatHourTerminalTypeGprsSpace", "ftbStatMonthTerminalTypeGprsSpaceUsers", fields,null,whereConditionMonth,orderStringMonth);
			break;
		}

		// 时间粒度
		switch (timeLevel) {
		case CommonConstants.MANTOEYE_TIME_LEVEL_HOUR:
			sql = "select nmAppUpFlush,nmAppDownFlush,nmFlush,nmUsers,nmVisitCounts,nmAveFlush,intYear,intMonth,intDay,intHour"
					+","+fields
					+ " from " + table + " where 1=1 " + " and intYear=" + year
					+ " and intMonth=" + month + " and intDay=" + day
					+ " and intHour=" + hour;
			sortSql = sortSql + " order by intYear,intMonth,intDay,intHour, "
					+ sortColumn + sortType;
			if (areaType!=6) {
				sql = sql + " and intRaitype = " + isTD+" and vcName = '" + vcName+"' "+ " and vcTerminalBrand = '" + vcTerminalBrand+"' ";
			}else {
				sql = sql + " and intRaitype = " + isTD+ " and vcTerminalBrand = '" + vcTerminalBrand+"' ";;
			}
			sql = sql + sortSql;
			break;
		default:
			sql = table;
			break;
		}
		*/
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
					commonSport.setIntMonth(Common.StringToInt(objs[7] + ""));// 日
					commonSport.setIntDay(Common.StringToInt(objs[8] + ""));// 日
					commonSport.setIntHour(Common.StringToInt(objs[9] + ""));// 小时
					commonSport.setVcTerminalBrand(objs[10] + "");
					commonSport.setVcTerminalOS(objs[11] + "");
					if (objs.length==13) {
						commonSport.setVcName(objs[12]+"");
					}else{
						commonSport.setVcName("全网");
					}
					break;
				case CommonConstants.MANTOEYE_TIME_LEVEL_DAY:
					commonSport.setIntMonth(Common.StringToInt(objs[7] + ""));// 日
					commonSport.setIntDay(Common.StringToInt(objs[9] + ""));// 日
					commonSport.setVcTerminalBrand(objs[11] + "");
					commonSport.setVcTerminalOS(objs[12] + "");
					if (objs.length==14) {
						commonSport.setVcName(objs[13]+"");
					}else{
						commonSport.setVcName("全网");
					}
					break;
				case CommonConstants.MANTOEYE_TIME_LEVEL_WEEK:
					commonSport.setIntWeek(Common.StringToInt(objs[7] + ""));// 周
					commonSport.setVcTerminalBrand(objs[9] + "");
					commonSport.setVcTerminalOS(objs[10] + "");
					if (objs.length==12) {
						commonSport.setVcName(objs[11]+"");
					}else{
						commonSport.setVcName("全网");
					}
					break;
				case CommonConstants.MANTOEYE_TIME_LEVEL_MONTH:
					commonSport.setIntMonth(Common.StringToInt(objs[7] + ""));// 月
					commonSport.setVcTerminalBrand(objs[9] + "");
					commonSport.setVcTerminalOS(objs[10] + "");
					if (objs.length==12) {
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
