package com.symbol.app.mantoeye.dao.blackberry;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.symbol.app.mantoeye.constants.CommonConstants;
import com.symbol.app.mantoeye.dao.common.HibernateQueryDAO;
import com.symbol.app.mantoeye.dto.blackberry.LdcUsersBean;
import com.symbol.app.mantoeye.util.CommonUtils;
import com.symbol.app.mantoeye.util.MantoEyeUtils;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.modules.orm.Page;

/**
 * 业务流量排名
 * 
 * @author rankin
 * 
 */
@Repository
public class BlackBerryYysDAO extends HibernateQueryDAO {

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	// 查询该时间的全网总流量和总用户数
	private Double[] findSumTotalData(int timeLevel, String date) {
		String totalSql = MantoEyeUtils.getBBFlushAndImsiSql(timeLevel, date);
		Double[] dbs = new Double[] { -1.0, -1.0 };
		List l = this.getSession().createSQLQuery(totalSql).list();
		if (l != null && l.size() > 0) {
			Object[] objs = (Object[]) l.get(0);
			dbs[0] = Common.StringTodouble(objs[0] + "");
			dbs[1] = Common.StringTodouble(objs[1] + "");
		}
		dbs[0] = dbs[0] == 0 ? -1.0 : dbs[0];
		dbs[1] = dbs[1] == 0 ? -1.0 : dbs[1];
		return dbs;
	}

	/**
	 * 分页查询
	 * 
	 * @param page
	 * @param timeLevel
	 * @param date
	 * @return
	 */
	public Page<LdcUsersBean> query(final Page page, int timeLevel, String date) {
		String sql = this.buildSql(page, timeLevel, date);
		// 计算占比
		Double[] dbs = findSumTotalData(timeLevel, date);
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
		if (timeLevel == 3) {// 如果时间粒度是周 获取该周内的时间
			date = CommonUtils.getDayInnerWeek(date);
		}
		newPage.setResult(buildBeanList(list, date, dbs));
		newPage.setPageNo(page.getPageNo());
		return newPage;
	}

	public List<LdcUsersBean> listData(final Page page, int timeLevel,
			String date) {
		// 计算占比
		Double[] dbs = findSumTotalData(timeLevel, date);
		String sql = this.buildSql(page, timeLevel, date);
		SQLQuery query = this.getSession().createSQLQuery(sql);
		List list = query.list();
		if (timeLevel == 3) {// 如果时间粒度是周 获取该周内的时间
			date = CommonUtils.getDayInnerWeek(date);
		}
		return buildBeanList(list, date, dbs);
	}

	/**
	 * 组装BEAN实体
	 * 
	 * @param list
	 * @param timeLevel
	 * @return
	 */
	public List<LdcUsersBean> buildBeanList(List list, String time, Double[] dbs) {
		List<LdcUsersBean> commonFlushList = commonFlushList = new ArrayList<LdcUsersBean>();
		LdcUsersBean ldcUsersBean = null;
		if (list != null && !list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				ldcUsersBean = new LdcUsersBean();
				Object[] objs = (Object[]) list.get(i);
				ldcUsersBean.setLdcId(objs[0] + "");// 运营商ID
				ldcUsersBean.setLdcName(objs[1] + "");// 运营商名称
				ldcUsersBean.setIntUpFlush(Common.StringToLong(objs[2] + ""));// 上流量
				ldcUsersBean.setIntDownFlush(Common.StringToLong(objs[3] + ""));// 下流量
				ldcUsersBean.setTotalFlush(Common.StringToLong(objs[4] + ""));// 总流量
				ldcUsersBean.setIntImsis(Common.StringToLong(objs[5] + ""));// 用户数
				ldcUsersBean.setNmAveFlush(Common.StringTodouble(objs[6] + ""));// 平均流量
				ldcUsersBean.setCountryId(objs[7] + "");// 国家ID
				ldcUsersBean.setCountryName(objs[8] + "");// 国家名称
				// 访问次数
				ldcUsersBean.setFullDate(time);
				ldcUsersBean.calculateData();
				ldcUsersBean.calculateFlushRate(dbs[0], dbs[1]);
				// ldcUsersBean.numberFormatData();
				commonFlushList.add(ldcUsersBean);
			}
		}
		return commonFlushList;
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
	public String buildSql(final Page page, int timeLevel, String date) {
		String sql = "";
		String fields = "";
		String table = "";
		// 维度类型

		fields = " intIdcId,vcIdc,nmUpFlush,nmDownFlush,nmTotalFlush,nmUsers,nmAveFlush,intCountryId,vcCountryName ";
		table = (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_HOUR) ? "vStatHourLdcBbFlush"
				: (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_DAY) ? "vStatDayLdcBbFlush"
						: (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_WEEK) ? "vStatWeekLdcBbFlush"
								: "vStatMonthLdcBbFlush";

		sql = " select " + fields;
		sql = sql + " from " + table + " where 1=1 ";
		String strDate = this.changeDate(date, timeLevel);
		sql = sql + this.getQueryCondition(timeLevel, strDate);
		sql = sql + " order by " + page.getOrderBy() + " " + page.getOrder();
		return sql;
	}

	/**
	 * 拆分时间成整形
	 * 
	 * @param date
	 * @param timeLevel
	 * @return
	 */
	public String changeDate(String date, int timeLevel) {
		Date d = null;
		if (timeLevel == 1) {

		} else if (timeLevel == 2) {
			date = date + " 00:00";
		} else if (timeLevel == 3) {
			date = date + " 00:00";
		} else if (timeLevel == 4) {
			date = date + "-01" + " 00:00";
		}
		StringBuffer sql = new StringBuffer();
		try {
			d = sdf.parse(date);
		} catch (ParseException e) {
			logger.error(e.getMessage());
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(d);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DATE);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int week = calendar.get(Calendar.WEEK_OF_YEAR);
		String dateStr = year + ";" + month + ";" + week + ";" + day + ";"
				+ hour;
		return dateStr;

	}

	/**
	 * 组装查询时间查询条件
	 * 
	 * @param timeLevel
	 * @param dateStr
	 * @return
	 */
	public String getQueryCondition(int timeLevel, String dateStr) {
		String[] str = dateStr.split(";");
		int year = Common.StringToInt(str[0]);
		int month = Common.StringToInt(str[1]);
		int week = Common.StringToInt(str[2]);
		int day = Common.StringToInt(str[3]);
		int hour = Common.StringToInt(str[4]);
		StringBuffer conditonStr = new StringBuffer();

		if (timeLevel == 1) {
			conditonStr.append(" and intYear=");
			conditonStr.append(year);
			conditonStr.append(" and intMonth=");
			conditonStr.append(month);
			conditonStr.append(" and intDay=");
			conditonStr.append(day);
			conditonStr.append(" and intHour=");
			conditonStr.append(hour);
		} else if (timeLevel == 2) {
			conditonStr.append(" and intYear=");
			conditonStr.append(year);
			conditonStr.append(" and intMonth=");
			conditonStr.append(month);
			conditonStr.append(" and intDay=");
			conditonStr.append(day);
		} else if (timeLevel == 3) {
			conditonStr.append(" and intYear=");
			conditonStr.append(year);
			// conditonStr.append(" and intMonth=");
			// conditonStr.append(month);
			conditonStr.append(" and intWeek=");
			conditonStr.append(week);
		} else if (timeLevel == 4) {
			conditonStr.append(" and intYear=");
			conditonStr.append(year);
			conditonStr.append(" and intMonth=");
			conditonStr.append(month);
		}

		return conditonStr.toString();

	}
}
