package com.symbol.app.mantoeye.dao.business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.symbol.app.mantoeye.constants.CommonConstants;
import com.symbol.app.mantoeye.dao.common.CommonQueryDAO;
import com.symbol.app.mantoeye.dto.CgiInfo;
import com.symbol.app.mantoeye.dto.flush.DimensionBeans;
import com.symbol.app.mantoeye.dto.flush.TopStat;
import com.symbol.wp.core.util.Common;

/**
 * top100小区的流量和用户数
 * 
 * @author rankin
 * 
 */
@Repository
public class TopFlushAndImsiDAO extends CommonQueryDAO {

	public static Map<String, CgiInfo> cgiInfo;
	@Autowired
	private DimensionBeans dimensionBeans;

	// select
	// nmUsers,nmFlush,nmAppUpFlush,nmAppDownFlush,vcAreaName,vcBranchName,vcBscName,vcSaleAreaName,vcSgsnName,vcStreetName,vcCGI
	// from vStatHourCgiFlush
	// where intYear = 2010 and intMonth= 3 and intDay = 17 and intHour =16
	/**
	 * 流量top100
	 * 
	 * @param statType
	 * @param year
	 * @param month
	 * @param day
	 * @param hour
	 * @return
	 */
//	public List<TopStat> findMonthTopStatData(boolean isflush, int raittype,
//			int year, int month) {
//		List<TopStat> result = new ArrayList<TopStat>();
//		TopStat ts = null;
//
//		String sql = "select top 100 a.intBscId,b.vcCgiChName,"
//				+ "a.vcCGI,a.nmUsers,"
//				+ "a.nmFlush,a.nmAppUpFlush,a.nmAppDownFlush"
//				+ " from ftbStatMonthCgiFlush a,ftbCgi b where a.vcCGI *= b.vcCGI and  a.intYear = ? and a.intMonth = ?  and  "
//				+ " a.intRaitype = ? and  a.vcCGI is not null  order by a.#ordered# DESC";
//		if (isflush) {
//			sql = sql.replaceAll("#ordered#", "nmFlush");
//		} else {
//			sql = sql.replaceAll("#ordered#", "nmUsers");
//		}
//		// logger.info("SQL:"+sql);
//		SQLQuery query = this.getSession().createSQLQuery(sql);
//		query.setInteger(0, year);
//		query.setInteger(1, month);
//		query.setInteger(2, raittype);
//		List qlist = query.list();
//
//		if (qlist != null && qlist.size() > 0) {
//			for (int i = 0; i < qlist.size(); i++) {
//				Object[] objs = (Object[]) qlist.get(i);
//				ts = new TopStat();
//				ts.setTop(i + 1);
//				if (Common.judgeString(objs[1] + "")) {
//					ts.setCellName(objs[1] + "");
//				} else {
//					ts.setCellName("--");
//				}
//				ts.setCgi(objs[2] + "");
//
//				ts.setStatType(CommonConstants.MANTOEYE_TIME_LEVEL_MONTH + "");
//
//				ts.setFlush(Common.StringToLong(objs[4] + ""));
//				ts.setImsis(Common.StringToLong(objs[3] + ""));
//
//				ts.setYear(year);
//				ts.setMonth(month);
//				ts.calculate();
//				// 添加CGI信息
//				ts = putCgiInfo(ts, ts.getCgi());
//				result.add(ts);
//			}
//		}
//		return result;
//	}
//
//	/**
//	 * 流量top100
//	 * 
//	 * @param statType
//	 * @param year
//	 * @param month
//	 * @param day
//	 * @param hour
//	 * @return
//	 */
//	public List<TopStat> findWeekTopStatData(boolean isflush, int raittype,
//			int year, int month, int week) {
//		List<TopStat> result = new ArrayList<TopStat>();
//		TopStat ts = null;
//
//		String sql = "select top 100 a.intBscId,b.vcCgiChName,"
//				+ "a.vcCGI,a.nmUsers,"
//				+ "a.nmFlush,a.nmAppUpFlush,a.nmAppDownFlush"
//				+ " from ftbStatWeekCgiFlush a,ftbCgi b where a.vcCGI *= b.vcCGI and  a.intYear = ? and a.intMonth = ? and a.intWeek=? "
//				+ " and  a.intRaitype = ? and a.vcCGI is not null  order by a.#ordered# DESC";
//		if (isflush) {
//			sql = sql.replaceAll("#ordered#", "nmFlush");
//		} else {
//			sql = sql.replaceAll("#ordered#", "nmUsers");
//		}
//		// logger.info("SQL:"+sql);
//		SQLQuery query = this.getSession().createSQLQuery(sql);
//		query.setInteger(0, year);
//		query.setInteger(1, month);
//		query.setInteger(2, week);
//		query.setInteger(3, raittype);
//		List qlist = query.list();
//
//		if (qlist != null && qlist.size() > 0) {
//			for (int i = 0; i < qlist.size(); i++) {
//				Object[] objs = (Object[]) qlist.get(i);
//				ts = new TopStat();
//				ts.setTop(i + 1);
//
//				if (Common.judgeString(objs[1] + "")) {
//					ts.setCellName(objs[1] + "");
//				} else {
//					ts.setCellName("--");
//				}
//				ts.setCgi(objs[2] + "");
//
//				ts.setStatType(CommonConstants.MANTOEYE_TIME_LEVEL_WEEK + "");
//
//				ts.setFlush(Common.StringToLong(objs[4] + ""));
//				ts.setImsis(Common.StringToLong(objs[3] + ""));
//
//				ts.setYear(year);
//				ts.setMonth(month);
//				ts.setWeek(week);
//				ts.calculate();
//				// 添加CGI信息
//				ts = putCgiInfo(ts, ts.getCgi());
//				result.add(ts);
//			}
//		}
//		return result;
//	}
//
//	/**
//	 * 流量top100
//	 * 
//	 * @param statType
//	 * @param year
//	 * @param month
//	 * @param day
//	 * @param hour
//	 * @return
//	 */
//	public List<TopStat> findDayTopStatData(boolean isflush, int raittype,
//			int year, int month, int day) {
//		// logger.info("SQL:"+isflush+"-"+raittype+"-"+year+"-"+month+"-"+day);
//		List<TopStat> result = new ArrayList<TopStat>();
//		TopStat ts = null;
//
//		String sql = "select top 100 a.intBscId,b.vcCgiChName,"
//				+ "a.vcCGI,a.nmUsers,"
//				+ "a.nmFlush,a.nmAppUpFlush,a.nmAppDownFlush"
//				+ " from ftbStatDayCgiFlush a,ftbCgi b where  a.vcCGI *= b.vcCGI and  a.intYear = ? and a.intMonth = ? and a.intDay = ?"
//				+ "  and  a.intRaitype = ? and a.vcCGI is not null order by a.#ordered# DESC";
//		if (isflush) {
//			sql = sql.replaceAll("#ordered#", "nmFlush");
//		} else {
//			sql = sql.replaceAll("#ordered#", "nmUsers");
//		}
//		logger.info("SQL:" + sql);
//		SQLQuery query = this.getSession().createSQLQuery(sql);
//		query.setInteger(0, year);
//		query.setInteger(1, month);
//		query.setInteger(2, day);
//		query.setInteger(3, raittype);
//		List qlist = query.list();
//
//		if (qlist != null && qlist.size() > 0) {
//			for (int i = 0; i < qlist.size(); i++) {
//				Object[] objs = (Object[]) qlist.get(i);
//				ts = new TopStat();
//				ts.setTop(i + 1);
//				if (Common.judgeString(objs[1] + "")) {
//					ts.setCellName(objs[1] + "");
//				} else {
//					ts.setCellName("--");
//				}
//				ts.setCgi(objs[2] + "");
//
//				ts.setStatType(CommonConstants.MANTOEYE_TIME_LEVEL_DAY + "");
//
//				ts.setFlush(Common.StringToLong(objs[4] + ""));
//				ts.setImsis(Common.StringToLong(objs[3] + ""));
//
//				ts.setYear(year);
//				ts.setMonth(month);
//				ts.setDay(day);
//				ts.calculate();
//				// 添加CGI信息
//				ts = putCgiInfo(ts, ts.getCgi());
//
//				result.add(ts);
//			}
//		}
//		return result;
//	}
//
//	/**
//	 * 流量top100
//	 * 
//	 * @param statType
//	 * @param year
//	 * @param month
//	 * @param day
//	 * @param hour
//	 * @return
//	 */
//	public List<TopStat> findHourTopStatData(boolean isflush, int raittype,
//			int year, int month, int day, int hour) {
//		String tableName = "ftbStatHourCgiFlush";
//		String orderName;
//		String condition;
//		List<TopStat> result = new ArrayList<TopStat>();
//		TopStat ts = null;
//		String sql = "select top 100 a.intBscId,b.vcCgiChName,"
//				+ "a.vcCGI,a.nmUsers,"
//				+ "a.nmFlush,a.nmAppUpFlush,a.nmAppDownFlush,a.intYear,a.intMonth"
//				+ " from ftbStatHourCgiFlush a,ftbCgi b where   a.vcCGI *= b.vcCGI and  "
//				+ "  a.intYear = ? and a.intMonth = ? and a.intDay = ? and a.intHour = ? "
//				+ "  and  a.intRaitype = ? and a.vcCGI is not null order by a.#ordered# DESC";
//		if (isflush) {
//			orderName = "nmFlush";
//		} else {
//			orderName = "nmUsers";
//		}
//		condition = " intYear = " + year + " and intMonth = " + month
//				+ " and intDay = " + day + " and intHour = " + hour
//				+ " and intRaitype = " + raittype + " and vcCGI is not null";
//		List qlist =  queryData(tableName,orderName,condition);
//
//		if (qlist != null && qlist.size() > 0) {
//			for (int i = 0; i < qlist.size(); i++) {
//				Object[] objs = (Object[]) qlist.get(i);
//				ts = new TopStat();
//				ts.setTop(i + 1);
//				if (Common.judgeString(objs[1] + "")) {
//					ts.setCellName(objs[1] + "");
//				} else {
//					ts.setCellName("--");
//				}
//				ts.setCgi(objs[2] + "");
//
//				ts.setStatType(CommonConstants.MANTOEYE_TIME_LEVEL_HOUR + "");
//
//				ts.setFlush(Common.StringToLong(objs[4] + ""));
//				ts.setImsis(Common.StringToLong(objs[3] + ""));
//
//				ts.setYear(year);
//				ts.setMonth(month);
//				ts.setDay(day);
//				ts.setHour(hour);
//
//				ts.calculate();
//				// logger.info(ts.getCgi()+"--CGI--");
//				// 添加CGI信息
//				ts = putCgiInfo(ts, ts.getCgi());
//				result.add(ts);
//			}
//		}
//		return result;
//	}
	
	/**
	 * 流量top100
	 * 
	 * @param statType
	 * @param year
	 * @param month
	 * @param day
	 * @param hour
	 * @return
	 */
	public List<TopStat> findTopStatData(boolean isflush,int datetype, int raittype,
			int year, int month, int day, int hour,int week) {
		String tableName ;
		String orderName;
		String condition;
		List<TopStat> result = new ArrayList<TopStat>();
		TopStat ts = null;
		if (isflush) {
			orderName = "nmFlush";
		} else {
			orderName = "nmUsers";
		}
		condition = " intYear = " + year + " and vcCGI is not null  and intRaitype = " + raittype ;
		// 时间粒度设置1:小时 2:天 3:周 4:月	
		if(datetype==CommonConstants.MANTOEYE_TIME_LEVEL_DAY){//天
			tableName = "vStatDayCgiFlush";
			condition = condition + " and intMonth = " + month
			+ " and intDay = " + day ;
			
		}else if(datetype==CommonConstants.MANTOEYE_TIME_LEVEL_WEEK){//周
			tableName = "vStatWeekCgiFlush";
			condition = condition + " and intWeek = " + week;
			
		}else if(datetype==CommonConstants.MANTOEYE_TIME_LEVEL_MONTH){//月
			tableName = "vStatMonthCgiFlush";
			condition = condition + " and intMonth = " + month;
			
		}else{//小时
			tableName = "vStatHourCgiFlush";
			condition = condition + " and intMonth = " + month
			+ " and intDay = " + day + " and intHour = " + hour;
		}
		List qlist =  queryData(tableName,orderName,condition);
		 
		if (qlist != null && qlist.size() > 0) {
			for (int i = 0; i < qlist.size(); i++) {
				
				Object[] objs = (Object[]) qlist.get(i);
				ts = new TopStat();
				ts.setTop(i + 1);
				
				if(datetype==CommonConstants.MANTOEYE_TIME_LEVEL_DAY){//天
					ts.setStatType(CommonConstants.MANTOEYE_TIME_LEVEL_DAY + "");
					ts.setYear(year);
					ts.setMonth(month);
					ts.setDay(day);				
				}else if(datetype==CommonConstants.MANTOEYE_TIME_LEVEL_WEEK){//周
					ts.setStatType(CommonConstants.MANTOEYE_TIME_LEVEL_DAY + "");
					ts.setYear(year);
					ts.setWeek(week);				
				}else if(datetype==CommonConstants.MANTOEYE_TIME_LEVEL_MONTH){//月
					ts.setStatType(CommonConstants.MANTOEYE_TIME_LEVEL_MONTH + "");
					ts.setYear(year);
					ts.setMonth(month);				
				}else{//小时
					ts.setStatType(CommonConstants.MANTOEYE_TIME_LEVEL_HOUR + "");
					ts.setYear(year);
					ts.setMonth(month);
					ts.setDay(day);
					ts.setHour(hour);
				}
				ts.setImsis(Common.StringToLong(objs[0] + ""));
				ts.setFlush(Common.StringToLong(objs[1] + ""));
				
//				nmUsers,nmFlush,nmAppUpFlush,nmAppDownFlush,vcAreaName,
//				vcBranchName,vcBscName,vcSaleAreaName,vcSgsnName,vcStreetName,vcCGI
				
				ts.setArea(Common.judgeString(objs[4] + "")?(objs[4] + ""):"--");
				
				ts.setSgsn(Common.judgeString(objs[8] + "")?(objs[8] + ""):"--");
				ts.setBsc(Common.judgeString(objs[6] + "")?(objs[6] + ""):"--");
				ts.setStreet(Common.judgeString(objs[9] + "")?(objs[9] + ""):"--");
				ts.setSubsidiaryCompany(Common.judgeString(objs[5] + "")?(objs[5] + ""):"--");
				ts.setSaleArea(Common.judgeString(objs[7] + "")?(objs[7] + ""):"--");
				ts.setCgi(Common.judgeString(objs[10] + "")?(objs[10] + ""):"--");
				ts.setCellName(Common.judgeString(objs[11] + "")?objs[11] + "":"--");
				ts.setNmAveFlush(Common.StringTodouble(objs[12] + ""));
				
				ts.calculate();
				result.add(ts);
			}
		}
		return result;
	}

	private List queryData(String tableName, String orderName, String condition) {

		String sql = "select distinct top 100 nmUsers,nmFlush,nmAppUpFlush,nmAppDownFlush,vcAreaName,vcBranchName,"
				+ "vcBscName,vcSaleAreaName,vcSgsnName,vcStreetName,vcCGI,vcCgiChName,nmAveFlush from "
				+ tableName
				+ " where "
				+ condition
				+ " order by "
				+ orderName
				+ " desc ";
		logger.info(sql);
		SQLQuery query = this.getSession().createSQLQuery(sql);

		return query.list();
	}
	// public TopStat putCgiInfo(TopStat topStat,String cgi) {
	// CgiInfo ci=null;
	// if(cgiInfo == null){
	// cgiInfo = new HashMap<String,CgiInfo>();
	// }
	// logger.info(cgiInfo.get(cgi)+"--"+cgi);
	// if(cgiInfo.get(cgi)!=null){
	// ci = cgiInfo.get(cgi);
	// }else{
	// ci = queryCgiInfo(cgi);
	// cgiInfo.put(cgi, ci);
	// }
	//		
	// topStat.setArea(Common.judgeString(ci.getAreaName())?ci.getAreaName():"--");
	// topStat.setCellName(Common.judgeString(ci.getCgiName())?ci.getCgiName():"--");
	// topStat.setSgsn(Common.judgeString(ci.getSgsnName())?ci.getSgsnName():"--");
	// topStat.setBsc(Common.judgeString(ci.getBscName())?ci.getBscName():"--");
	// topStat.setStreet(Common.judgeString(ci.getStreetName())?ci.getStreetName():"--");
	// topStat.setSubsidiaryCompany(Common.judgeString(ci.getSubsidiaryCompany())?ci.getSubsidiaryCompany():"--");
	// topStat.setSaleArea(Common.judgeString(ci.getSaleArea())?ci.getSaleArea():"--");
	//		
	// return topStat;
	// }
	// /**
	// * 获取cgi的信息
	// * @param cgi
	// */
	// public CgiInfo queryCgiInfo(String cgi) {
	//		
	// CgiInfo ci = new CgiInfo();
	// List list;
	// String sql =
	// "select c.vcCgiChName,d.vcName,e.vcName,f.vcName,g.vcSaleAreaName,h.vcName,i.vcBranchName from "
	// +
	// " ftbCgi c,dtbArea d,dtbBsc e,dtbGsn  f,dtbSaleArea g,dtbStreet h,dtbSubsidiaryCompany i "
	// + " where  c.intAreaId = d.intAreaId  and c.intBscId = e.intBscId "
	// +
	// " and c.intSgsnId = f.intSgsnId and c.intSaleAreaId = g.intSaleAreaId and "
	// +
	// " c.intStreetId = h.intStreetId and c.intBranchId = i.intBranchId and c.vcCGI = ? ";
	// SQLQuery query = this.getSession().createSQLQuery(sql);
	// query.setParameter(0, cgi);
	// list = query.list();
	// if(list!=null&&list.size()>0){
	// Object[] objs = (Object[])list.get(0);
	// ci.setCgi(cgi);
	// ci.setCgiName(objs[0]+"");
	// ci.setAreaName(objs[1]+"");
	// ci.setBscName(objs[2]+"");
	// ci.setSgsnName(objs[3]+"");
	// ci.setSaleArea(objs[4]+"");
	// ci.setStreetName(objs[5]+"");
	// ci.setSubsidiaryCompany(objs[6]+"");
	//			
	// }
	// logger.info("-----"+ci+"--"+ci.getAreaName());
	// return ci;
	// }
}
