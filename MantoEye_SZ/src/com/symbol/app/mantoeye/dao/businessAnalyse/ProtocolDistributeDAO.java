package com.symbol.app.mantoeye.dao.businessAnalyse;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.symbol.app.mantoeye.dao.common.CommonQueryDAO;
import com.symbol.app.mantoeye.dto.flush.CommonFlush;
import com.symbol.app.mantoeye.util.CommonUtils;
import com.symbol.app.mantoeye.util.MantoEyeUtils;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.modules.orm.Page;

@Repository
public class ProtocolDistributeDAO extends CommonQueryDAO {

	/**
	 * 查询所有
	 */
	public List<CommonFlush> listData(int protocolType, int isTD,
			int timeLevel, String time) {
		Date date = CommonUtils.getDate(time);
		int year = CommonUtils.getYear(date);
		int month = CommonUtils.getMonth(date);
		int week = CommonUtils.getWeek(date);
		int day = CommonUtils.getDay(date);
		int hour = CommonUtils.getHour(date);
		String sql = this.buildSql(protocolType, isTD, timeLevel, year, month,
				week, day, hour);
		SQLQuery query = this.getSession().createSQLQuery(sql);
		List list = query.list();
		return buildBeanList(list, time,timeLevel);
	}
	//查询该时间的全网总流量和总用户数
	private Double[] findSumTotalData(int timeLevel, String date){
		String totalSql = MantoEyeUtils.getTotalFlushAndImsiSql(1, timeLevel, date);
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
	/**
	 * 组装BEAN实体
	 * 
	 * @param list
	 * @param time
	 * @return
	 */
	public List<CommonFlush> buildBeanList(List list, String time, int timeLevel) {
		//计算占比
		Double [] dbs = findSumTotalData(timeLevel,time);
		
		List<CommonFlush> commonFlushList = null;
		CommonFlush commonFlush = null;
		if (list != null && !list.isEmpty()) {
			commonFlushList = new ArrayList<CommonFlush>();
			for (int i = 0; i < list.size(); i++) {
				commonFlush = new CommonFlush();
				Object[] objs = (Object[]) list.get(i);
				commonFlush.setBusiness(objs[0]+"");// 业务
				commonFlush.setBusinessName(objs[1]+"");// 业务名称
				commonFlush.setIntUpFlush(Common.StringToLong(objs[2]+""));// 上流量
				commonFlush.setIntDownFlush(Common.StringToLong(objs[3]+""));// 下流量
				commonFlush.setTotalFlush(Common.StringToLong(objs[4]+""));// 总流量
				commonFlush.setIntImsis(Common.StringToLong(objs[5]+""));// 用户数
				commonFlush.setNmAveFlush(Common.StringTodouble(objs[6]+""));
				if (timeLevel == 3) {
					commonFlush.setFullDate(CommonUtils.getDayInnerWeek(time));
				} else {
					commonFlush.setFullDate(time);
				}
				commonFlush.calculateData();
				commonFlush.calculateFlushRate(dbs[0], dbs[1]);
				//commonFlush.numberFormatData();
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
	 * @param time
	 * @return
	 */
	public String buildSql(int protocolType, int isTD, int timeLevel, int year,
			int month, int week, int day, int hour) {
		String sql = "";
		String table = "";
		String field = (protocolType == 1) ? " intWapId,vcWapName, "
				: " intProtocolTypeId,vcProtocolName, ";

		switch (timeLevel) {
		case 1:
			table = (protocolType == 1) ? " vStatHourWapProtocol "
					: " vStatHourTcpProtocol ";
			sql = "select "
					+ field
					+ " nmAppUpFlush,nmAppDownFlush,nmFlush,nmUsers,nmAveFlush,intYear,intMonth,intDay,intHour"
					+ " from  " + table + " where 1=1 " + " and intYear="
					+ year + " and intMonth=" + month + " and intDay=" + day
					+ " and intHour=" + hour;
			break;
		case 2:
			table = (protocolType == 1) ? " vStatDayWapProtocol "
					: " vStatDayTcpProtocol ";
			sql = "select "
					+ field
					+ " nmAppUpFlush,nmAppDownFlush,nmFlush,nmUsers,nmAveFlush,intYear,intMonth,intDay"
					+ " from " + table + " where 1=1 " + " and intYear=" + year
					+ " and intMonth=" + month + " and intDay=" + day;
			break;
		case 3:
			table = (protocolType == 1) ? " vStatWeekWapProtocol "
					: " vStatWeekTcpProtocol ";
			sql = "select "
					+ field
					+ " nmAppUpFlush,nmAppDownFlush,nmFlush,nmUsers,nmAveFlush,intYear,intYear,intWeek"
					+ " from " + table + " where 1=1 " + " and intYear=" + year
					//+ " and intMonth=" + month 
					+ " and intWeek=" + week;
			break;
		case 4:
			table = (protocolType == 1) ? " vStatMonthWapProtocol "
					: " vStatMonthTcpProtocol ";
			sql = "select "
					+ field
					+ "nmAppUpFlush,nmAppDownFlush,nmFlush,nmUsers,nmAveFlush,intYear,intMonth"
					+ " from " + table + " where 1=1 " + " and intYear=" + year
					+ " and intMonth=" + month;
			break;
		}
		sql = sql + " and intRaitype = " + isTD;
		sql = sql + " order by nmFlush desc ";
		return sql;
	}
	/**
	 * 分页查询
	 */
	public Page<CommonFlush> queryTrend(int protocolType,final Page page, int isTD,
			int timeLevel, String stime, String etime, String apnName) {
		String sql = buildTrendSql(protocolType,isTD, timeLevel, stime, etime, apnName);
		sql = sql.substring(0, sql.indexOf("order"))+" order by "+buildOrder(page,timeLevel);
		SQLQuery query = this.getSession().createSQLQuery(sql);
		query.setFirstResult(page.getFirst());
		query.setMaxResults(page.getPageSize());
		List list = query.list();
		Page newPage = new Page();
		newPage.setResult(buildTrendBeanList(list, timeLevel));
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
	 * 
	 */
	public List<CommonFlush> queryAllTrend(int protocolType,int isTD, int timeLevel,
			String stime, String etime, String apnName) {
		String sql = buildTrendSql(protocolType,isTD, timeLevel, stime, etime, apnName);
		SQLQuery query = this.getSession().createSQLQuery(sql);
		List list = query.list();
		return buildTrendBeanList(list, timeLevel);
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

	public String buildTrendSql(int protocolType,int isTD, int timeLevel, String sTime,
			String eTime, String apnName) {
		String sql = "";
		String sortSql = "";
		String contidion = protocolType==1? " and vcWapName = '" + apnName + "'": " and vcProtocolName = '" + apnName + "'";
		String tablep =  protocolType==1? "WapProtocol":"TcpProtocol";
		switch (timeLevel) {
		case 1:
			sql = "select  nmAppUpFlush,nmAppDownFlush,nmFlush,nmUsers,nmAveFlush,"
					+ " convert(datetime,string(intYear,'-',intMonth,'-',intDay,' ',inthour,':00:00')) as ddate "
					+ " from vStatHour"+tablep
					+ " where 1=1 "
					+ " and  ddate >= "
					+ MantoEyeUtils.formatData(sTime, timeLevel)
					+ " and  ddate <= "
					+ MantoEyeUtils.formatData(eTime, timeLevel)
					+ "" + contidion;
			sortSql = sortSql + " order by ddate asc";
			break;
		case 2:
			sql = "select nmAppUpFlush,nmAppDownFlush,nmFlush,nmUsers,nmAveFlush,"
					+ " convert(datetime,string(intYear,'-',intMonth,'-',intDay)) as ddate "
					+ " from vStatDay"+tablep
					+ " where 1=1 "
					+ " and  ddate >= "
					+ MantoEyeUtils.formatData(sTime, timeLevel)
					+ " and  ddate <= "
					+ MantoEyeUtils.formatData(eTime, timeLevel)
					+ "" + contidion;
			sortSql = sortSql + " order by ddate asc";
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
					+ " from vStatWeek"+tablep + " where 1=1 ";
			if (sYear != eYear) {
				sql = sql + " and  (( intYear = " + sYear + " and intWeek>="
						+ sWeek + " and intWeek < 100 )" + " or ( intYear = "
						+ eYear + " and intWeek>0 and intWeek <= " + eWeek
						+ " ))  " + bSql;
			} else {
				sql = sql + " and  (( intYear = " + sYear + " and intWeek>="
						+ sWeek + " and intWeek <= " + eWeek + " ))  " + bSql;
			}
			sql = sql + contidion;
			sortSql = sortSql + " order by intYear asc,intWeek asc";
			break;
		case 4:
			sql = "select nmAppUpFlush,nmAppDownFlush,nmFlush,nmUsers,nmAveFlush,"
					+ " convert(datetime,string(intYear,'-',intMonth)) as ddate "
					+ " from vStatMonth"+tablep
					+ " where 1=1 "
					+ " and  ddate >= "
					+ MantoEyeUtils.formatData(sTime, timeLevel)
					+ " and  ddate <= "
					+ MantoEyeUtils.formatData(eTime, timeLevel)
					+ "" + contidion;
			sortSql = sortSql + " order by ddate asc";
			break;
		}
		sql = sql + " and intRaitype = " + isTD;
		sql = sql + sortSql;
		return sql;
	}
}
