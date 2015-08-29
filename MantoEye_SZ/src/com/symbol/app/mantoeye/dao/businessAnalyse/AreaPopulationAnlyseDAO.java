package com.symbol.app.mantoeye.dao.businessAnalyse;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Repository;
import com.symbol.app.mantoeye.constants.CommonConstants;
import com.symbol.app.mantoeye.dao.common.HibernateQueryDAO;
import com.symbol.app.mantoeye.dto.sports.CommonSport;
import com.symbol.app.mantoeye.util.CommonUtils;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.modules.orm.Page;

@Repository
public class AreaPopulationAnlyseDAO extends HibernateQueryDAO {

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
			int timeLevel, String startTime ,String vcAreaName) {
		Date date = CommonUtils.getDate(startTime);
		int year = CommonUtils.getYear(date);
		int month = CommonUtils.getMonth(date);
		int week = CommonUtils.getWeek(date);
		String sql = this.buildSql(isTD,timeLevel,year,month,week,vcAreaName,page);
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
		newPage.setResult(buildBeanList(list, timeLevel, startTime));
		newPage.setPageNo(page.getPageNo());
		return newPage;
	}

	public List<CommonSport> listData(int isTD,
			int timeLevel, String startTime,String vcAreaName) {
		Date date = CommonUtils.getDate(startTime);
		int year = CommonUtils.getYear(date);
		int month = CommonUtils.getMonth(date);
		int week = CommonUtils.getWeek(date);
		String sql = this.buildSql(isTD,timeLevel,year,month,week,vcAreaName,null);
		List list = this.getSession().createSQLQuery(sql).list();		
		return buildBeanList(list, timeLevel, startTime);
	}
	

	/**
	 * 组装BEAN实体
	 * 
	 * @param list
	 * @param timeLevel
	 * @return
	 */
	public List<CommonSport> buildBeanList(List list, int timeLevel,
			String startTime) {
		List<CommonSport> commonSportList = null;
		CommonSport commonSport = null;
		if (list != null && !list.isEmpty()) {
			commonSportList = new ArrayList<CommonSport>();
			for (int i = 0; i < list.size(); i++) {
				commonSport = new CommonSport();
				Object[] objs = (Object[]) list.get(i);
				commonSport.setVcAreaName(objs[0] + "");
				commonSport.setIntImsis(Common.StringToLong(objs[1] + ""));// 用户数
				if (timeLevel == 3) {
					commonSport.setFullDate(CommonUtils.getDayInnerWeek(startTime));
				} else {
					commonSport.setFullDate(startTime);
				}
				commonSport.calculateData1();
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
	 * @return
	 */
	public String buildSql(int isTD, int timeLevel, int year,
			int month, int week,String vcAreaName, Page page) {
		String sql = "";
		String sortSql = "";
		String sortType = " desc ";
		String sortColumn = " nmUsers ";
		if (page != null) {
			sortType = " " + page.getOrder() + " ";
			sortColumn = " " + page.getOrderBy() + " ";
		}
		// 时间粒度
		switch (timeLevel) {
		case CommonConstants.MANTOEYE_TIME_LEVEL_WEEK:
			sql =  "select convert(varchar,a.vcAreaName) as vcAreaName,b.nmUsers,b.intYear,b.intMonth,b.intWeek"
				+ " from ftbArea a,ftbStatWeekGroupAreaGprsResidentUsers  b where a.nmAreaId=b.nmAreaId and a.intType=2 " + " and intYear=" + year
					+ " and intWeek=" + week;
			sortSql = sortSql + " order by intYear,intWeek, "
					+ sortColumn + sortType;
			break;
		case CommonConstants.MANTOEYE_TIME_LEVEL_MONTH:
			sql =  "select convert(varchar,a.vcAreaName) as vcAreaName,b.nmUsers,b.intYear,b.intMonth"
					+ " from ftbArea a,ftbStatMonthGroupAreaGprsResidentUsers b where a.nmAreaId=b.nmAreaId and a.intType=2 " + " and intYear=" + year
					+ " and intMonth=" + month;
			sortSql = sortSql + " order by intYear,intMonth, " + sortColumn
					+ sortType;
			break;
		}
		if (vcAreaName!=null && !vcAreaName.equals("")) {
			sql = sql+" and (vcAreaName like '%"+vcAreaName.toLowerCase()+"%' or vcAreaName like '%"+vcAreaName.toUpperCase()+"%')";
		}
		sql = sql + " and intRaitype = " + isTD;
		sql = sql + sortSql;
		return sql;
	}
	
}
