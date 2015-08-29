package com.symbol.app.mantoeye.dao.sports;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.symbol.app.mantoeye.dao.common.HibernateQueryDAO;
import com.symbol.app.mantoeye.dto.sports.CommonSport;
import com.symbol.app.mantoeye.util.CommonUtils;
import com.symbol.app.mantoeye.util.MantoEyeUtils;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.modules.orm.Page;

@Repository
public class WirelessBusinessDAO extends HibernateQueryDAO {

	public Page<CommonSport> query(final Page page, int timeLevel,
			String sTime, String eTime, Integer intType, String vcCGI) {
		String sql = this.buildSql(intType, vcCGI, timeLevel, sTime, eTime,
				page);

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
		newPage.setResult(buildBeanList(list, timeLevel, sTime, eTime));
		newPage.setPageNo(page.getPageNo());
		return newPage;
	}

	public List<String> getAllMsisdn() {
		String sqlString = "select nmMsisdn from ftbMsisdnList";
		List<String> msisdns = this.getSession().createSQLQuery(sqlString)
				.list();

		return msisdns;
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
		List<CommonSport> CustomerList = null;
		CommonSport commonSport = null;
		if (list != null && !list.isEmpty()) {
			CustomerList = new ArrayList<CommonSport>();
			for (int i = 0; i < list.size(); i++) {
				commonSport = new CommonSport();
				Object[] bean = (Object[]) list.get(i);

				if (null != bean[4] && !bean[4].equals(""))
					commonSport.setIntYear(Common.StringToInt(bean[4] + ""));

				switch (timeLevel) {
				case 1:
					commonSport.setIntMonth(Common.StringToInt(bean[5] + ""));// 月
					commonSport.setIntDay(Common.StringToInt(bean[7] + ""));// 日
					commonSport.setIntHour(Common.StringToInt(bean[8] + ""));// 小时
					break;
				case 2:
					commonSport.setIntMonth(Common.StringToInt(bean[5] + ""));// 月
					commonSport.setIntDay(Common.StringToInt(bean[6] + ""));// 日

					break;
				case 3:

					commonSport.setIntWeek(Common.StringToInt(bean[5] + ""));// 周
					break;
				case 4:
					commonSport.setIntMonth(Common.StringToInt(bean[5] + ""));// 月

					break;

				default:
					break;
				}

				commonSport.setSpanDate(timeLevel);
				commonSport.setIntType(ConvertType(Common.StringToInt(bean[0]
						.toString())));
				commonSport.setVcCGI(bean[3] == null ? "" : bean[3].toString());
				commonSport.setNmUsers(Common.StringToLong(bean[1].toString()));
				commonSport.setNmFlush(Common.StringToLong(bean[2].toString()));
				commonSport.calculateData();
				CustomerList.add(commonSport);
			}
		}
		return CustomerList;
	}

	/**
	 * 组装查询语句
	 * 
	 * @param isTD
	 * @param timeLevel
	 * @param startTime
	 *            .
	 * @param endTime
	 * @return
	 */
	public String buildSql(Integer intType, String vcCGI, int timeLevel,
			String sTime, String eTime, Page page) {
		Date d = CommonUtils.getDate(sTime) ;
		int year = CommonUtils.getYear(d);
		int month = CommonUtils.getMonth(d);
		int day=CommonUtils.getDay(d);
		int hour=CommonUtils.getHour(d);
		int week=CommonUtils.getWeek(d);
		
		String sql = "";
		String defaultSortSql = "";
		String sortSql = "";
		String defaultSortType = " desc ";
		String sortType = " asc ";
		String sortColumn = " dtStatTime";
		String gropsqlString = "";
		if (page != null) {
			sortType = " " + page.getOrder() + " ";
			sortColumn = " " + page.getOrderBy() + " ";
		}
		switch (timeLevel) {
		case 1:
			sql = "select intType,nmUsers,nmFlush,vcCGI,intYear,intMonth,intWeek,intDay,intHour"
					+ " from ftbOverView where 1=1 "
//					+ " and  convert(datetime,string(intYear,'-',intMonth,'-',intDay,' ',inthour,':00:00'))="
//					+ MantoEyeUtils.formatData(sTime, timeLevel);
					+"and  intYear="+year+" and intMonth="+month+" and intDay="+day+" and intHour="+hour;

			defaultSortSql = " order by intYear " + defaultSortType
					+ ",intMonth " + defaultSortType + ",intDay"
					+ defaultSortType + ",intHour " + defaultSortType;
			break;
		case 2:
			sql = "select intType,sum(nmUsers) as nmUsers,sum(nmFlush) as nmFlush,vcCGI,intYear,intMonth,intDay"
					+ " from ftbOverView where 1=1 "

//					+ " and  convert(datetime,string(intYear,'-',intMonth,'-',intDay))="
//					+ MantoEyeUtils.formatData(sTime, timeLevel);
					+"and  intYear="+year+" and intMonth="+month+" and intDay="+day;

			gropsqlString = "group by  intType,intYear,intMonth,intDay,vcCGI";
			defaultSortSql = " order by intYear " + defaultSortType
					+ ",intMonth " + defaultSortType + ",intDay "
					+ defaultSortType;

			break;
		case 3:
			Date sDate = CommonUtils.getDate(sTime);
			int sYear = CommonUtils.getYear(sDate);
			int sMonth = CommonUtils.getMonth(sDate);
			int sWeek = CommonUtils.getWeek(sDate);

			if (sMonth == 12 && sWeek == 1) {
				sYear = sYear + 1;
			}

			String bSql = "";

			sql = "select intType,sum(nmUsers) as nmUsers,sum(nmFlush) as nmFlush,vcCGI,intYear,intWeek"
					+ " from ftbOverView where 1=1 ";

			sql = sql + " and  (intYear = " + sYear + " and intWeek=" + sWeek
					+ ")";

			gropsqlString = "group by   intType,intYear,intWeek,vcCGI";
			defaultSortSql = " order by intYear " + defaultSortType
					+ ",intWeek  " + defaultSortType;
			break;
		case 4:
			sql = "select intType,sum(nmUsers) as nmUsers,sum(nmFlush) as nmFlush,vcCGI,intYear,intMonth"
					+ " from ftbOverView where 1=1 "

//					+ " and  convert(date,string(intYear,'-',intMonth))="
//					+ MantoEyeUtils.formatData(sTime, timeLevel);
					+"and  intYear="+year+" and intMonth="+month;

			gropsqlString = "group by  intType,intYear,intMonth,vcCGI";
			defaultSortSql = " order by intYear " + defaultSortType
					+ ",intMonth  " + defaultSortType;
			break;
		}
		if (intType != null && !intType.equals("") && intType != -1) {
			sql = sql + " and intType=" + intType;
		} else {
			sql = sql + " and intType in (11,12,13,14,15,16,17,18)";
		} 
		if (vcCGI != null && !vcCGI.equals("")) {
			sql = sql + "and vcCGI like '%" + vcCGI + "%'";
		}
		sql = sql + gropsqlString;
		sortSql = sortSql + " order by  " + sortColumn + sortType;
		if (page != null) {
			if ("intYear".equals(page.getOrderBy())) {// 默认查询按时间先后顺序
				sql = sql + defaultSortSql;
			} else if ("statdate".equals(page.getOrderBy())) {// 按时间顺序查询
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

	public List<CommonSport> listData(int timeLevel, String sTime,
			String eTime, Integer intType, String vcCGI) {
		String sql = this.buildSql(intType, vcCGI, timeLevel, sTime, eTime,
				null);
		SQLQuery query = this.getSession().createSQLQuery(sql);
		List list = query.list();
		return buildBeanList(list, timeLevel, sTime, eTime);
	}

	public String ConvertType(Integer typeid) {
		String busType = "";
		switch (typeid) {

		case 11:
			busType = "139说客";
			break;
		case 12:
			busType = "红段子";
			break;
		case 13:
			busType = "无线城市";
			break;
		case 14:
			busType = "无线官网";
			break;
		case 15:
			busType = "飞信";
			break;
		case 16:
			busType = "移动MM";
			break;
		case 17:
			busType = "手机阅读";
			break;
		case 18:
			busType = "新浪微博";
			break;
		}
		return busType;
	}
}
