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
public class TerminalSystemDAO extends HibernateQueryDAO {

	public Page<CommonSport> query(final Page page, int timeLevel,
			String startTime, int areaType) {
		Date date = CommonUtils.getDate(startTime);
		int year = CommonUtils.getYear(date);
		int month = CommonUtils.getMonth(date);
		int week = CommonUtils.getWeek(date);
		int day = CommonUtils.getDay(date);
		int hour = CommonUtils.getHour(date);

		String sql = this.buildSql(timeLevel, year, month, week, day, hour,
				areaType, page);
		String sqls = sql.split("order by",1)[0];
		String totalSql = "select count(1) from ( " + sqls + " ) as totalCount";
		Object totalObj = this.getSession().createSQLQuery(totalSql)
				.uniqueResult();
		Integer total = Integer.parseInt(null == totalObj.toString()
				|| "".equals(totalObj.toString()) ? "0" : totalObj.toString());
		List list = this.getSession().createSQLQuery(sql).setFirstResult(
				page.getFirst()).setMaxResults(page.getPageSize()).list();
		Page newPage = new Page();
		newPage.setPageSize(page.getPageSize());
		newPage.setTotalCount(total);
		newPage.setResult(buildBeanList(list, timeLevel, areaType, startTime));
		newPage.setPageNo(page.getPageNo());
		return newPage;
	}

	/**
	 * 查询所有
	 */
	public List<CommonSport> listData(int timeLevel, String startTime,
			int areaType) {
		Date date = CommonUtils.getDate(startTime);
			int year = CommonUtils.getYear(date);
			int month = CommonUtils.getMonth(date);
			int week = CommonUtils.getWeek(date);
			int day = CommonUtils.getDay(date);
			int hour = CommonUtils.getHour(date);
		String sql = this.buildSql(timeLevel, year, month, week, day, hour,
				areaType, null);
		SQLQuery query = this.getSession().createSQLQuery(sql);
		List list = query.list();
		return buildBeanList(list, timeLevel, areaType, startTime);
	}

	public List<CommonSport> buildBeanList(List list, int timeLevel,
			int areaType, String startTime) {
		List<CommonSport> commonSportList = null;
		CommonSport commonSport = null;
		if (list != null && !list.isEmpty()) {
			commonSportList = new ArrayList<CommonSport>();
			for (int i = 0; i < list.size(); i++) {
				commonSport = new CommonSport();
				Object[] objs = (Object[]) list.get(i);
				commonSport.setIntUpFlush(Common.StringToLong(objs[5] + ""));// 上流量
				commonSport.setIntDownFlush(Common.StringToLong(objs[6] + ""));// 下流量
				commonSport.setTotalFlush(Common.StringToLong(objs[7] + ""));// 总流量
				commonSport.setIntImsis(Common.StringToLong(objs[8] + ""));// 用户数

				commonSport.setIntYear(Common.StringToInt(objs[9] + ""));// 年
				if (timeLevel != 3) {
					commonSport.setIntMonth(Common.StringToInt(objs[10] + ""));// 月
				}
				switch (timeLevel) {
				case CommonConstants.MANTOEYE_TIME_LEVEL_HOUR:
					commonSport.setIntDay(Common.StringToInt(objs[11] + ""));// 日
					commonSport.setIntHour(Common.StringToInt(objs[12] + ""));// 小时
					break;
				case CommonConstants.MANTOEYE_TIME_LEVEL_DAY:
					commonSport.setIntDay(Common.StringToInt(objs[11] + ""));// 日
					break;
				case CommonConstants.MANTOEYE_TIME_LEVEL_WEEK:
					commonSport.setIntWeek(Common.StringToInt(objs[10] + ""));// 周
					break;
				case CommonConstants.MANTOEYE_TIME_LEVEL_MONTH:
					break;
				}
				if (areaType != 6)
					commonSport.setArea(objs[1] + "");
				else
					commonSport.setArea("全网");
				commonSport.setNmAreaId(Common.StringToLong(objs[0]+""));//区域ID
				String vcTerminalOS = objs[4] == null ? "未知" : objs[4] + "";
				commonSport.setTerminalBrand(vcTerminalOS);	
				commonSport.setNmTerminalId(Common.StringToLong(objs[2]+""));
				commonSport.setSpanDate1(timeLevel);
				commonSport.calculateData1();
				commonSport.setNmTerminalId(Common.StringToLong(objs[2]+""));
				commonSportList.add(commonSport);
			}
		}
		return commonSportList;
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
	public String buildSql(int timeLevel, int year, int month, int week,
			int day, int hour, int areaType, Page page) {
		String sql = "";
		return sql;
	}

}
