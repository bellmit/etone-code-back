package com.symbol.app.mantoeye.dao.businessAnalyse;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.symbol.app.mantoeye.constants.CommonConstants;
import com.symbol.app.mantoeye.dao.common.HibernateQueryDAO;
import com.symbol.app.mantoeye.dto.flush.CommonFlush;
import com.symbol.app.mantoeye.util.CommonUtils;
import com.symbol.app.mantoeye.util.MantoEyeUtils;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.modules.orm.Page;

/*
 * 全网数据业务总体统计
 */
//Spring DAO Bean的标识
@Repository
public class TotalDistributeDAO extends HibernateQueryDAO {

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
			String sTime, String eTime) {
		String sql = this.buildSql(isTD, timeLevel, sTime, eTime, page);
		// SQLQuery query = this.getSession().createSQLQuery(sql);
		// List pageList = query.list();
		// query.setFirstResult(page.getFirst());
		// query.setMaxResults(page.getPageSize());
		// List list = query.list();
		String sqls = sql.split("order by")[0];
		String totalSql = "select count(1) from ( " + sqls + " ) as totalCount";
		Object totalObj = this.getSession().createSQLQuery(totalSql)
				.uniqueResult();
		Integer total = Integer.parseInt(null == totalObj.toString()
				|| "".equals(totalObj.toString()) ? "0" : totalObj.toString());
		List<Object[]> list = this.getSession().createSQLQuery(sql)
				.setFirstResult(page.getFirst())
				.setMaxResults(page.getPageSize()).list();
		Page newPage = new Page();
		newPage.setPageSize(page.getPageSize());
		newPage.setTotalCount(total);
		newPage.setResult(buildBeanList(list, timeLevel, sTime, eTime));
		newPage.setPageNo(page.getPageNo());
		return newPage;
	}

	/**
	 * 查询所有
	 */
	public List<CommonFlush> listData(int isTD, int timeLevel, String sTime,
			String eTime) {
		String sql = this.buildSql(isTD, timeLevel, sTime, eTime, null);
		SQLQuery query = this.getSession().createSQLQuery(sql);
		List list = query.list();
		return buildBeanList(list, timeLevel, sTime, eTime);
	}

	/**
	 * 组装BEAN实体
	 * 
	 * @param list
	 * @param timeLevel
	 * @return
	 */
	public List<CommonFlush> buildBeanList(List list, int timeLevel,
			String startTime, String endTime) {
		List<CommonFlush> commonFlushList = null;
		CommonFlush commonFlush = null;
		if (list != null && !list.isEmpty()) {
			commonFlushList = new ArrayList<CommonFlush>();
			for (int i = 0; i < list.size(); i++) {
				commonFlush = new CommonFlush();
				Object[] objs = (Object[]) list.get(i);
				commonFlush.setIntUpFlush(Common.StringToLong(objs[0] + ""));// 上流量
				commonFlush.setIntDownFlush(Common.StringToLong(objs[1] + ""));// 下流量
				commonFlush.setTotalFlush(Common.StringToLong(objs[2] + ""));// 总流量
				commonFlush.setIntImsis(Common.StringToLong(objs[3] + ""));// 用户数
				commonFlush.setNmAveFlush(Common.StringTodouble(objs[4] + ""));
				commonFlush.setIntYear(Common.StringToInt(objs[5] + ""));// 年
				if (timeLevel != 3) {
					commonFlush.setIntMonth(Common.StringToInt(objs[6] + ""));// 月
				}
				switch (timeLevel) {
				case CommonConstants.MANTOEYE_TIME_LEVEL_HOUR:
					commonFlush.setIntDay(Common.StringToInt(objs[7] + ""));// 日
					commonFlush.setIntHour(Common.StringToInt(objs[8] + ""));// 小时
					commonFlush.setLocaltotalFlush(Common.StringToLong(objs[10]
							+ ""));
					commonFlush.setLocalintImsis(Common.StringToLong(objs[11]
							+ ""));
					break;
				case CommonConstants.MANTOEYE_TIME_LEVEL_DAY:
					commonFlush.setIntDay(Common.StringToInt(objs[7] + ""));// 日
					commonFlush.setLocaltotalFlush(Common.StringToLong(objs[8]
							+ ""));
					commonFlush.setLocalintImsis(Common.StringToLong(objs[9]
							+ ""));
					break;
				case CommonConstants.MANTOEYE_TIME_LEVEL_WEEK:
					commonFlush.setIntWeek(Common.StringToInt(objs[6] + ""));// 周
					commonFlush.setLocaltotalFlush(Common.StringToLong(objs[7]
							+ ""));
					commonFlush.setLocalintImsis(Common.StringToLong(objs[8]
							+ ""));
					break;
				case CommonConstants.MANTOEYE_TIME_LEVEL_MONTH:
					commonFlush.setLocaltotalFlush(Common.StringToLong(objs[7]
							+ ""));
					commonFlush.setLocalintImsis(Common.StringToLong(objs[8]
							+ ""));
					break;
				}
				commonFlush.setSpanDate(timeLevel);
				commonFlush.calculateData();
				// commonFlush.numberFormatData();
				commonFlushList.add(commonFlush);
			}
		}
		return commonFlushList;
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
					+ " ,nmLocalFlush,nmLocalUsers from VStatHourGprsTotalFlush  "
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
					+ " ,nmLocalFlush,nmLocalUsers from VStatDayGprsTotalFlush  "
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
					+ " ,nmLocalFlush,nmLocalUsers from VStatWeekGprsTotalFlush  "
					+ " where 1=1 ";
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
					+ " ,nmLocalFlush,nmLocalUsers from VStatMonthGprsTotalFlush  "
					+ " where 1=1 "
					+ " and  convert(datetime,string(intYear,'-',intMonth))>="
					+ MantoEyeUtils.formatData(sTime, timeLevel)
					+ " and  convert(datetime,string(intYear,'-',intMonth))<="
					+ MantoEyeUtils.formatData(eTime, timeLevel);
			defaultSortSql = " order by intYear " + defaultSortType
					+ ",intMonth  " + defaultSortType;
			break;
		}
		
		if(isTD == 9){	//9 是全网	V3
			sql = sql + " and intRaitype is null ";
		}else{
			sql = sql + " and intRaitype = " + isTD;
		}
		
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
