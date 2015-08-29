package com.symbol.app.mantoeye.dao.groupBisness;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.symbol.app.mantoeye.constants.CommonConstants;
import com.symbol.app.mantoeye.dto.flush.CommonFlush;
import com.symbol.app.mantoeye.util.CommonUtils;
import com.symbol.app.mantoeye.util.MantoEyeUtils;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.orm.hibernate.HibernateDao;

@Repository
public class GroupBisUserBelongDAO extends HibernateDao {
	Map<Integer, String> mapUser = new HashMap<Integer, String>();
	Map<Integer, String> mapTypeUser = new HashMap<Integer, String>();
	Map<Integer, String> userBelong = new HashMap<Integer, String>();
//	Map<String, Integer> cvuserBelong = new HashMap<String, Integer>();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	public void initMap() {
		mapUser.put(4, "vStatMonthApnTypeUserBelong");
		mapUser.put(3, "vStatWeekApnTypeUserBelong");
		mapUser.put(2, "vStatDayApnTypeUserBelong");
		mapUser.put(1, "vStatHourApnTypeUserBelong");

		mapTypeUser.put(4, "vStatMonthApnUserBelong");
		mapTypeUser.put(3, "vStatWeekApnUserBelong");
		mapTypeUser.put(2, "vStatDayApnUserBelong");
		mapTypeUser.put(1, "vStatHourApnUserBelong");

		userBelong.put(4, "国外");
		userBelong.put(3, "外地");
		userBelong.put(2, "省内非深圳");
		userBelong.put(1, "本地");
		userBelong.put(0, "未知");

//		cvuserBelong.put("国外", 4);
//		cvuserBelong.put("外地", 3);
//		cvuserBelong.put("省内非深圳", 2);
//		cvuserBelong.put("本地", 1);
//		cvuserBelong.put("未知", 0);
	}
	//查询该时间的集团业务总流量和总用户数
	private Double[] findSumTotalData(int timeLevel, String date,int dataType,String apnName){
		String totalSql = MantoEyeUtils.getGroupBelongFlushAndImsiSql(dataType, timeLevel, date,apnName);
		Double [] dbs = new Double[]{-1.0,-1.0};
		List l= this.getSession().createSQLQuery(totalSql).list();
		if(l!=null&&l.size()>0){
			Object [] objs = (Object[])l.get(0);
			dbs[0] = Common.StringTodouble(objs[0]+"");
			dbs[1] = Common.StringTodouble(objs[1]+"");
		}
		dbs[0] = dbs[0]==0?-1.0:dbs[0];
		dbs[1] = dbs[1]==0?-1.0:dbs[1];
		return dbs;
	}
	public List<CommonFlush> queryUserAttachDistributeDAO(int timeLevel,
			String date, int dataType, String apnName) {
		//计算占比
		Double [] dbs = findSumTotalData(timeLevel,date,1,apnName);
		
		String strDate = CommonUtils.changeDate(date, timeLevel);
		StringBuffer sql = new StringBuffer();
		String[] str = strDate.split(";");
		int year = Common.StringToInt(str[0]);
		int month = Common.StringToInt(str[1]);
		int week = Common.StringToInt(str[2]);
		int day = Common.StringToInt(str[3]);
		int hour = Common.StringToInt(str[4]);
		sql
				.append("select intUserBelongId,nmAppUpFlush,nmAppDownFlush,nmFlush,nmUsers,nmActiveCounts,nmAveFlush from ");
		if (apnName == "") {
			sql.append(mapUser.get(timeLevel));
		} else {
			sql.append(mapTypeUser.get(timeLevel));
		}
		sql.append(" where 1=1 ");
		sql.append(" and intRaitype=");
		sql.append(dataType);

		if (apnName != "") {
			sql.append(" and vcApnDomain='");
			sql.append(apnName + "'");
		}
		if (timeLevel == 1) {
			sql.append(" and intYear=");
			sql.append(year);
			sql.append(" and intMonth=");
			sql.append(month);
			sql.append(" and intDay=");
			sql.append(day);
			sql.append(" and intHour=");
			sql.append(hour);
		} else if (timeLevel == 2) {
			sql.append(" and intYear=");
			sql.append(year);
			sql.append(" and intMonth=");
			sql.append(month);
			sql.append(" and intDay=");
			sql.append(day);
		} else if (timeLevel == 3) {
			sql.append(" and intYear=");
			sql.append(year);
			// sql.append(" and intMonth=");
			// sql.append(month);
			sql.append(" and intWeek=");
			sql.append(week);
		} else if (timeLevel == 4) {
			sql.append(" and intYear=");
			sql.append(year);
			sql.append(" and intMonth=");
			sql.append(month);
		}
		if (apnName == "") {
			sql.append(" and vcApnTypeName='");
			sql.append("集团APN'");
		}
		sql.append(" order by nmFlush desc ");
		CommonFlush commonFlush = null;
		List<CommonFlush> list = new ArrayList<CommonFlush>();
		Iterator it = this.getSession().createSQLQuery(sql.toString()).list()
				.iterator();
		while (it.hasNext()) {
			Object[] obj = (Object[]) it.next();
			commonFlush = new CommonFlush();
			commonFlush.setUserBelong(userBelong.get(Common.StringToInt(obj[0]
					.toString())));
			commonFlush.setIntUpFlush(Common.StringToLong(obj[1] + ""));
			commonFlush.setIntDownFlush(Common.StringToLong(obj[2] + ""));
			commonFlush.setTotalFlush(Common.StringToLong(obj[3] + ""));
			commonFlush.setIntImsis(Common.StringToLong(obj[4] + ""));
			commonFlush.setActiveCount(Common.StringToLong(obj[5] + ""));
			commonFlush.setNmAveFlush(Common.StringTodouble(obj[6]+""));
			if (timeLevel == 3) {// 如果时间粒度是周 获取该周内的时间
				commonFlush.setFullDate(CommonUtils.getDayInnerWeek(date));
			} else {
				commonFlush.setFullDate(date);
			}
			commonFlush.calculateData();
			commonFlush.calculateFlushRate(dbs[0], dbs[1]);
			//commonFlush.numberFormatData();

			list.add(commonFlush);
		}
		return list;
	}

	/**
	 * 查询所有APN
	 * 
	 * @return
	 */
	public String getAllApnName() {
		String sql = "select vcApnDomain  from dtbApn order by vcApnDomain";
		String allApn = "";
		List list = this.getSession().createSQLQuery(sql).list();
		if (list != null && !list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				if (i == list.size() - 1) {
					allApn = allApn + list.get(i);
				} else {
					allApn = allApn + list.get(i) + ":";
				}
			}
		}
		return allApn;
	}

	/**
	 * 
	 */
	public List<CommonFlush> queryAllTrend(int isTD, int timeLevel,
			String stime, String etime, String apnName, String belongId) {
		String sql = buildTrendSql(isTD, timeLevel, stime, etime, apnName,
				belongId);
		SQLQuery query = this.getSession().createSQLQuery(sql);
		List list = query.list();
		return buildTrendBeanList(list, timeLevel);
	}
	public Page<CommonFlush> queryTrend(final Page<CommonFlush>  page,int isTD, int timeLevel,
			String stime, String etime, String apnName, String belongId) {
		String sql = buildTrendSql(isTD, timeLevel, stime, etime, apnName,
				belongId);
		sql = sql.substring(0, sql.indexOf("order"))+" order by "+buildOrder(page,timeLevel);
		SQLQuery query = this.getSession().createSQLQuery(sql);
		query.setFirstResult(page.getFirst());
		query.setMaxResults(page.getPageSize());
		List list = query.list();
		Page newPage = new Page();	
		newPage.setResult( buildTrendBeanList(list, timeLevel));
		newPage.setPageNo(page.getPageNo());
		return newPage;
	}
	private String buildOrder(Page<CommonFlush> page, int timeLevel) {
		String sql = "";
		String deforder = "asc";
		String orderBy = page.getOrderBy();
		String order = page.getOrder();
		if (Common.judgeString(orderBy) && !orderBy.equals("fullDate")) {
			sql = " "+orderBy+" "+order;
		} else {
			if (Common.judgeString(order) && order.toLowerCase().equals("desc")) {
				deforder = "desc";
			}
			if(timeLevel==3){
				sql += "intYear " + deforder + ",intWeek "
				+ deforder;
			}else{
				sql +=" ddate "+deforder;
			}
		}
		return sql;
	}
	/**
	 * 组装BEAN实体
	 * 
	 * @param list
	 * @param timeLevel
	 * @return
	 */
	public List<CommonFlush> buildTrendBeanList(List list, int timeLevel) {
		List<CommonFlush> commonFlushList = commonFlushList = new ArrayList<CommonFlush>();
		CommonFlush commonFlush = null;
		if (list != null && !list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				commonFlush = new CommonFlush();
				Object[] objs = (Object[]) list.get(i);
				commonFlush.setIntUpFlush(Common.StringToLong(objs[0] + ""));// 上流量
				commonFlush.setIntDownFlush(Common.StringToLong(objs[1] + ""));// 下流量
				commonFlush.setTotalFlush(Common.StringToLong(objs[2] + ""));// 总流量
				commonFlush.setIntImsis(Common.StringToLong(objs[3] + ""));// 用户数
				commonFlush.setNmAveFlush(Common.StringTodouble(objs[4]+""));
				if (timeLevel == 1) {
					commonFlush.setFullDate((objs[5] + "").substring(0, 16));
				} else if (timeLevel == 2) {
					commonFlush.setFullDate((objs[5] + "").split(" ")[0]);
				} else if (timeLevel == 4) {
					commonFlush.setFullDate((objs[5] + "").substring(0, 7));
				} else {
					commonFlush.setIntYear(Common.StringToInt(objs[5] + ""));
					commonFlush.setIntWeek(Common.StringToInt(objs[6] + ""));
					commonFlush.setSpanDate(timeLevel);
				}

				commonFlush.calculateData();
				//commonFlush.numberFormatData();
				commonFlushList.add(commonFlush);
			}
		}
		return commonFlushList;
	}

	public String buildTrendSql(int isTD, int timeLevel, String sTime,
			String eTime, String apnName, String belongId) {
		initMap();
		String table = mapTypeUser.get(timeLevel);
		String ordersql = "";
		String sql = " select ";
		// 时间粒度
		switch (timeLevel) {
		case CommonConstants.MANTOEYE_TIME_LEVEL_HOUR:
			sql = sql
					+ " nmAppUpFlush,nmAppDownFlush,nmFlush,nmUsers,nmAveFlush,"
					+ " convert(datetime,string(intYear,'-',intMonth,'-',intDay,' ',inthour,':00:00')) as ddate "
					+ " from " + table + " where 1=1 " + " and intRaitype="
					+ isTD + " and  ddate >= "
					+ MantoEyeUtils.formatData(sTime, timeLevel)
					+ " and  ddate <= "
					+ MantoEyeUtils.formatData(eTime, timeLevel)
					+ " and vcApnDomain='" + apnName + "'"
					+ " and intUserBelongId = " + belongId;
			ordersql = " order by ddate asc";
			break;
		case CommonConstants.MANTOEYE_TIME_LEVEL_DAY:
			sql = sql
					+ " nmAppUpFlush,nmAppDownFlush,nmFlush,nmUsers,nmAveFlush,"
					+ " convert(datetime,string(intYear,'-',intMonth,'-',intDay)) as ddate "
					+ " from " + table + " where 1=1 " + " and intRaitype="
					+ isTD + " and  ddate>="
					+ MantoEyeUtils.formatData(sTime, timeLevel)
					+ " and  ddate<="
					+ MantoEyeUtils.formatData(eTime, timeLevel)
					+ " and vcApnDomain='" + apnName + "'"
					+ " and intUserBelongId = " + belongId;
			ordersql = " order by ddate asc";
			break;
		case CommonConstants.MANTOEYE_TIME_LEVEL_WEEK:
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
			sql = sql
					+ " nmAppUpFlush,nmAppDownFlush,nmFlush,nmUsers,nmAveFlush,intYear,intWeek"
					+ " from " + table + " where 1=1 " + " and intRaitype="
					+ isTD;
			if (sYear != eYear) {
				sql = sql + " and  (( intYear = " + sYear + " and intWeek>="
						+ sWeek + " and intWeek < 100 )" + " or ( intYear = "
						+ eYear + " and intWeek>0 and intWeek <= " + eWeek
						+ " ))  " + bSql;
			} else {
				sql = sql + " and  (( intYear = " + sYear + " and intWeek>="
						+ sWeek + " and intWeek <= " + eWeek + " ))  " + bSql;
			}
			sql = sql + " and vcApnDomain='" + apnName + "'"
					+ " and intUserBelongId = " + belongId;
			ordersql = " order by intYear asc,intWeek asc";
			break;
		case CommonConstants.MANTOEYE_TIME_LEVEL_MONTH:
			sql = sql
					+ " nmAppUpFlush,nmAppDownFlush,nmFlush,nmUsers,nmAveFlush,"
					+ " convert(datetime,string(intYear,'-',intMonth)) as ddate "
					+ " from " + table + " where 1=1 " + " and intRaitype="
					+ isTD + " and  ddate >="
					+ MantoEyeUtils.formatData(sTime, timeLevel)
					+ " and  ddate <="
					+ MantoEyeUtils.formatData(eTime, timeLevel)
					+ " and vcApnDomain='" + apnName + "'"
					+ " and intUserBelongId = " + belongId;
			ordersql = " order by ddate asc";
			break;
		}
		sql = sql + ordersql;
		return sql;
	}
}
