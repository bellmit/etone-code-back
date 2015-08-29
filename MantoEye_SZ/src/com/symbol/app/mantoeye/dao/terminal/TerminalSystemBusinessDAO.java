package com.symbol.app.mantoeye.dao.terminal;

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
public class TerminalSystemBusinessDAO extends HibernateQueryDAO {

	public Page<CommonSport> query(final Page page,int terminalId,
			int areaType, int timeLevel, String startTime,long nmAreaId) {
		Date date = CommonUtils.getDate(startTime);
		int year = CommonUtils.getYear(date);
		int month = CommonUtils.getMonth(date);
		int week = CommonUtils.getWeek(date);
		int day = CommonUtils.getDay(date);
		int hour = CommonUtils.getHour(date);
		String sql = this.buildSql(terminalId,areaType, timeLevel, year, month, week, day,
				hour,nmAreaId, page);
		String sqls = sql.split("order by",1)[0];
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


	/**
	 * 查询所有
	 */
	public List<CommonSport> listData(int terminalId, int areaType,
			int timeLevel, String startTime,long nmAreaId) {
		Date date = CommonUtils.getDate(startTime);
		int year = CommonUtils.getYear(date);
		int month = CommonUtils.getMonth(date);
		int week = CommonUtils.getWeek(date);
		int day = CommonUtils.getDay(date);
		int hour = CommonUtils.getHour(date);
		String sql = this.buildSql(terminalId,areaType, timeLevel, year, month, week, day,
				hour,nmAreaId, null);
		List list = this.getSession().createSQLQuery(sql).list();
		return buildBeanList(list, timeLevel, startTime);
	}

	
	public List<CommonSport> buildBeanList(List list, int timeLevel,
			String startTime) {
		List<CommonSport> commonSportList = null;
		CommonSport commonSport = null;
		if (list != null && !list.isEmpty()) {
			commonSportList = new ArrayList<CommonSport>();
			for (int i = 0; i < list.size(); i++) {
				commonSport = new CommonSport();
				Object[] objs = (Object[]) list.get(i);
				commonSport.setTerminalBrand(objs[0] + "");
				commonSport.setBusinessName(objs[1]+"");
				commonSport.setIntUpFlush(Common.StringToLong(objs[2] + ""));// 上流量
				commonSport.setIntDownFlush(Common.StringToLong(objs[3] + ""));// 下流量
				commonSport.setTotalFlush(Common.StringToLong(objs[4] + ""));// 总流量
				commonSport.setIntImsis(Common.StringToLong(objs[5] + ""));// 用户数
				commonSport.setIntYear(Common.StringToInt(objs[6] + ""));// 年
				if (timeLevel != 3) {
					commonSport.setIntMonth(Common.StringToInt(objs[7] + ""));// 月
				}
				switch (timeLevel) {
				case CommonConstants.MANTOEYE_TIME_LEVEL_HOUR:
					commonSport.setIntDay(Common.StringToInt(objs[8] + ""));// 日
					commonSport.setIntHour(Common.StringToInt(objs[9] + ""));// 小时
					
					break;
				case CommonConstants.MANTOEYE_TIME_LEVEL_DAY:
					commonSport.setIntDay(Common.StringToInt(objs[8] + ""));// 日
					break;
				case CommonConstants.MANTOEYE_TIME_LEVEL_WEEK:
					commonSport.setIntWeek(Common.StringToInt(objs[7] + ""));// 周
					break;
				case CommonConstants.MANTOEYE_TIME_LEVEL_MONTH:
					break;
				}
				commonSport.calculateData1();
				commonSport.setSpanDate1(timeLevel);
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
	public String buildSql(int terminalId,int areaType, int timeLevel, int year, int month,
			int week, int day, int hour,long nmAreaId, Page page) {
		String sql = "";

		return sql;
	}

}
