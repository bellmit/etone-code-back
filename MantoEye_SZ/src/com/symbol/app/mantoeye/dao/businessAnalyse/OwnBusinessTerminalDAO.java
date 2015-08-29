package com.symbol.app.mantoeye.dao.businessAnalyse;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Repository;
import com.symbol.app.mantoeye.constants.CommonConstants;
import com.symbol.app.mantoeye.dao.common.HibernateQueryDAO;
import com.symbol.app.mantoeye.dto.sports.CommonSport;
import com.symbol.app.mantoeye.util.CommonUtils;
import com.symbol.app.mantoeye.util.MantoEyeUtils;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.modules.orm.Page;

@Repository
public class OwnBusinessTerminalDAO extends HibernateQueryDAO {

	/**
	 * 分页查询
	 * 
	 * @param page
	 * @param isTD
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
			int timeLevel, String startTime,String endTime) {
		String sql = this.buildSqlTest(isTD,timeLevel,startTime,endTime,page);
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
		newPage.setResult(buildBeanList(list, timeLevel, startTime, endTime));
		newPage.setPageNo(page.getPageNo());
		return newPage;
	}

	public List<CommonSport> listData(int isTD,
			int timeLevel, String startTime,String endTime) {
		String sql = this.buildSqlTest(isTD,timeLevel,startTime,endTime, null);
		List list = this.getSession().createSQLQuery(sql).list();		
		return buildBeanList(list, timeLevel, startTime, endTime);
	}
	

	/**
	 * 组装BEAN实体
	 * 
	 * @param list
	 * @param timeLevel
	 * @return
	 */
	public List<CommonSport> buildBeanList(List list, int timeLevel,
			String startTime, String endTime) {
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
				commonSport.setNmAveFlush(Common.StringTodouble(objs[4] + ""));
				commonSport.setIntYear(Common.StringToInt(objs[5] + ""));// 年
				if (timeLevel != 3) {
					commonSport.setIntMonth(Common.StringToInt(objs[6] + ""));// 月
				}
				switch (timeLevel) {
				case CommonConstants.MANTOEYE_TIME_LEVEL_HOUR:
					commonSport.setIntDay(Common.StringToInt(objs[7] + ""));// 日
					commonSport.setIntHour(Common.StringToInt(objs[8] + ""));// 小时
					break;
				case CommonConstants.MANTOEYE_TIME_LEVEL_DAY:
					commonSport.setIntDay(Common.StringToInt(objs[7] + ""));// 日
					break;
				case CommonConstants.MANTOEYE_TIME_LEVEL_WEEK:
					commonSport.setIntWeek(Common.StringToInt(objs[6] + ""));// 周
					break;
				case CommonConstants.MANTOEYE_TIME_LEVEL_MONTH:
					break;
				}
				commonSport.setArea(objs[9]+"");
				int type = Common.StringToInt(objs[10] + "");
				if (type==1) {
					commonSport.setVcTerminalNetType("2G");
				}
				if (type==2) {
					commonSport.setVcTerminalNetType("3G");
				}				
				commonSport.setSpanDate1(timeLevel);
				commonSport.calculateData1();
				commonSportList.add(commonSport);
			}
		}
		return commonSportList;
	}

	/**
	/**
	 * 组装查询语句
	 * 
	 * @param isTD
	 * @param timeLevel
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public String buildSql(int isTD, int timeLevel, String sTime, String eTime,
			Page page) {
		String sql = "";
		String defaultSortSql = "";
		String sortSql = "";
		String defaultSortType = " asc ";
		String sortType = " desc ";
		String sortColumn = " nmFlush ";
		if (page != null) {
			sortType = " " + page.getOrder() + " ";
			sortColumn = " " + page.getOrderBy() + " ";
		}
		switch (timeLevel) {
		case 1:
			sql = "select nmAppUpFlush,nmAppDownFlush,nmFlush,nmUsers,nmAveFlush,intYear,intMonth,intDay,intHour,string(intYear,intMonth,intDay,inthour) as times"
					+ " from VStatHourGprsTotalFlush  "
					+ " where 1=1 "
					+ " and  convert(datetime,string(intYear,'-',intMonth,'-',intDay,' ',inthour,':00:00'))>="
					+ MantoEyeUtils.formatData(sTime, timeLevel)
					+ " and  convert(datetime,string(intYear,'-',intMonth,'-',intDay,' ',inthour,':00:00'))<="
					+ MantoEyeUtils.formatData(eTime, timeLevel);
			defaultSortSql = " order by intYear " + defaultSortType
					+ ",intMonth " + defaultSortType + ",intDay"
					+ defaultSortType + ",intHour " + defaultSortType;
			break;
		case 2:
			sql = "select nmAppUpFlush,nmAppDownFlush,nmFlush,nmUsers,nmAveFlush,intYear,intMonth,intDay"
					+ " from VStatDayGprsTotalFlush  "
					+ " where 1=1 "
					+ " and  convert(datetime,string(intYear,'-',intMonth,'-',intDay))>="
					+ MantoEyeUtils.formatData(sTime, timeLevel)
					+ " and  convert(datetime,string(intYear,'-',intMonth,'-',intDay))<="
					+ MantoEyeUtils.formatData(eTime, timeLevel);
			defaultSortSql = " order by intYear " + defaultSortType
					+ ",intMonth " + defaultSortType + ",intDay "
					+ defaultSortType;
			break;
		case 3:
			Date sDate = CommonUtils.getDate(sTime);
			int sYear = CommonUtils.getYear(sDate);
			int sMonth = CommonUtils.getMonth(sDate);
			int sWeek = CommonUtils.getWeek(sDate);

			Date eDate = CommonUtils.getDate(eTime);
			int eYear = CommonUtils.getYear(eDate);
			int eMonth = CommonUtils.getMonth(eDate);
			int eWeek = CommonUtils.getWeek(eDate);

			if (sMonth == 12 && sWeek == 1) {
				sYear = sYear + 1;
			}
			if (eMonth == 12 && eWeek == 1) {
				eYear = eYear + 1;
			}

			String bSql = "";
			for (int i = 1; i < eYear - sYear; i++) {
				bSql = bSql + " or (intYear = " + (sYear + i) + ") ";
			}

			sql = "select nmAppUpFlush,nmAppDownFlush,nmFlush,nmUsers,nmAveFlush,intYear,intWeek"
					+ " from VStatWeekGprsTotalFlush  " + " where 1=1 ";
			if (sYear != eYear) {
				sql = sql + " and  (( intYear = " + sYear + " and intWeek>="
						+ sWeek + " and intWeek < 100 )" + " or ( intYear = "
						+ eYear + " and intWeek>0 and intWeek <= " + eWeek
						+ " ))  " + bSql;
			} else {
				sql = sql + " and  (( intYear = " + sYear + " and intWeek>="
						+ sWeek + " and intWeek <= " + eWeek + " ))  " + bSql;
			}
			defaultSortSql = " order by intYear " + defaultSortType
					+ ",intWeek  " + defaultSortType;
			break;
		case 4:
			sql = "select nmAppUpFlush,nmAppDownFlush,nmFlush,nmUsers,nmAveFlush,intYear,intMonth"
					+ " from VStatMonthGprsTotalFlush  "
					+ " where 1=1 "
					+ " and  convert(datetime,string(intYear,'-',intMonth))>="
					+ MantoEyeUtils.formatData(sTime, timeLevel)
					+ " and  convert(datetime,string(intYear,'-',intMonth))<="
					+ MantoEyeUtils.formatData(eTime, timeLevel);
			defaultSortSql = " order by intYear " + defaultSortType
					+ ",intMonth  " + defaultSortType;
			break;
		}
		sql = sql + " and intRaitype = " + isTD;
		sortSql = sortSql + " order by  " + sortColumn + sortType;
		if (page != null) {
			if ("intYear".equals(page.getOrderBy())) {// 默认查询按时间先后顺序
				sql = sql + defaultSortSql;
			} else if ("fullDate".equals(page.getOrderBy())) {// 按时间顺序查询
				defaultSortSql = defaultSortSql.replaceAll(defaultSortType,
						sortType);
				sql = sql + defaultSortSql;
			} else {// 其他查询
				sql = sql + sortSql;
			}
		} else {// 图形查询按时间先后顺序
			sql = sql + defaultSortSql;
		}
		return sql;
	}

	
	/**
	 * 组装查询语句
	 * 
	 * @param isTD
	 * @param timeLevel
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public String buildSqlTest(int isTD, int timeLevel, String sTime, String eTime,
			Page page) {
		String sql = "";
		String defaultSortSql = "";
		String sortSql = "";
		String defaultSortType = " asc ";
		String sortType = " desc ";
		String sortColumn = " upFlush ";
		if (page != null) {
			sortType = " " + page.getOrder() + " ";
			sortColumn = " " + page.getOrderBy() + " ";
		}
		switch (1) {
		case 1:
			sql = "select upFlush,downFlush,totalFlush,intImsis,nmAveFlush,intYear,intMonth,intDay,intHour,area,terminalType"
					+ " from test10  "
					+ " where 1=1 ";
			defaultSortSql = " order by intYear " + defaultSortType
					+ ",intMonth " + defaultSortType + ",intDay"
					+ defaultSortType + ",intHour " + defaultSortType;
		
		}
		sql = sql + " and intRaitype = " + isTD;
		sortSql = sortSql + " order by  " + sortColumn + sortType;
		if (page != null) {
			if ("intYear".equals(page.getOrderBy())) {// 默认查询按时间先后顺序
				sql = sql + defaultSortSql;
			} else if ("fullDate".equals(page.getOrderBy())) {// 按时间顺序查询
				defaultSortSql = defaultSortSql.replaceAll(defaultSortType,
						sortType);
				sql = sql + defaultSortSql;
			} else {// 其他查询
				sql = sql + sortSql;
			}
		} else {// 图形查询按时间先后顺序
			sql = sql + defaultSortSql;
		}
		return sql;
	}
	
	
}
