package com.symbol.app.mantoeye.dao.blackberry;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.symbol.app.mantoeye.dao.common.HibernateQueryDAO;
import com.symbol.app.mantoeye.dto.flush.CommonFlush;
import com.symbol.app.mantoeye.util.CommonUtils;
import com.symbol.wp.core.util.Common;

/**
 * 业务流量排名
 * @author rankin
 *
 */
@Repository
public class Top100AreaFlushDAO extends HibernateQueryDAO{
	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
//SQL
//	select top 100 a.vcCGI,a.nmFlush,a.nmUsers,c.vcCgiChName,d.vcName,e.vcName,f.vcName,g.vcSaleAreaName,h.vcName,i.vcBranchName from 
//	ftbStatDayBbCgiUserFlush a, ftbCgi c,dtbArea d,dtbBsc e,dtbGsn  f,dtbSaleArea g,dtbStreet h,dtbSubsidiaryCompany i 
//	where a.vcCGI=c.vcCGI and c.intAreaId = d.intAreaId  and c.intBscId = e.intBscId 
//	and c.intSgsnId = f.intSgsnId and c.intSaleAreaId = g.intSaleAreaId and c.intStreetId = h.intStreetId and c.intBranchId = i.intBranchId


	public List<CommonFlush> query(String time,int time_level){
		Date date = CommonUtils.getDate(time);
		int year = CommonUtils.getYear(date);
		int month = CommonUtils.getMonth(date);
		int week = CommonUtils.getWeek(date);
		int day = CommonUtils.getDay(date);
		String sql=bulidSql(time_level,year,month,week,day);
		sql=sql+" order by a.nmFlush desc";
		return this.buildBeanList(this.getSession().createSQLQuery(sql).list(), time,time_level);
	}
	
	public String bulidSql(int timeLevel,int year,int month,int week, int day){
		String sql="";
		switch (timeLevel) {
			case 2:
				sql="select top 100 a.vcCGI,a.nmFlush,a.nmUsers,c.vcCgiChName,e.vcName as bscname,f.vcName as streetname,g.vcSaleAreaName,h.vcName as sgsnname,i.vcBranchName,a.nmAveFlush  " +
				" from VStatDayBbCgiUserFlush a, ftbCgi c,dtbBsc e,dtbGsn  f,dtbSaleArea g,dtbStreet h,dtbSubsidiaryCompany i " +
				" where a.vcCGI=c.vcCGI   and c.intBscId = e.intBscId " +
				" and c.intSgsnId = f.intSgsnId and c.intSaleAreaId = g.intSaleAreaId and c.intStreetId = h.intStreetId " +
				" and c.intBranchId = i.intBranchId"+
				" and a.intYear="+year+
				" and a.intMonth="+month+
				" and a.intDay="+day;
				break;
			case 3:
				sql="select top 100 a.vcCGI,a.nmFlush,a.nmUsers,c.vcCgiChName,e.vcName as bscname,f.vcName as streetname,g.vcSaleAreaName,h.vcName as sgsnname,i.vcBranchName,a.nmAveFlush  " +
				" from VStatWeekBbCgiUserFlush a, ftbCgi c,dtbBsc e,dtbGsn  f,dtbSaleArea g,dtbStreet h,dtbSubsidiaryCompany i " +
				" where a.vcCGI=c.vcCGI   and c.intBscId = e.intBscId " +
				" and c.intSgsnId = f.intSgsnId and c.intSaleAreaId = g.intSaleAreaId and c.intStreetId = h.intStreetId " +
				" and c.intBranchId = i.intBranchId"+
				" and a.intYear="+year+
				" and a.intWeek="+week;
				break;
			case 4:
				sql="select top 100 a.vcCGI,a.nmFlush,a.nmUsers,c.vcCgiChName,e.vcName as bscname,f.vcName as streetname,g.vcSaleAreaName,h.vcName as sgsnname,i.vcBranchName,a.nmAveFlush " +
				" from VStatMonthBbCgiUserFlush a, ftbCgi c,dtbBsc e,dtbGsn  f,dtbSaleArea g,dtbStreet h,dtbSubsidiaryCompany i " +
				" where a.vcCGI=c.vcCGI   and c.intBscId = e.intBscId " +
				" and c.intSgsnId = f.intSgsnId and c.intSaleAreaId = g.intSaleAreaId and c.intStreetId = h.intStreetId " +
				" and c.intBranchId = i.intBranchId"+
				" and a.intYear="+year+
				" and a.intMonth="+month;
				break;
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
	public List<CommonFlush> buildBeanList(List list, String time,int timeLevel) {
		
		List<CommonFlush> commonFlushList= new ArrayList<CommonFlush>();;
		CommonFlush commonFlush = null;
		if (list != null && !list.isEmpty()) {
			
			for (int i = 0; i < list.size(); i++) {
				commonFlush = new CommonFlush();
				Object[] objs = (Object[]) list.get(i);
				commonFlush.setCgi(objs[0]+"");
				commonFlush.setTotalFlush(Common.StringToLong(objs[1]+""));
				commonFlush.setIntImsis(Common.StringToLong(objs[2]+""));
				
				commonFlush.setTop(i+1);
				commonFlush.setCgiName(objs[3]!=null?objs[3]+"":"--");
				//commonFlush.setArea(objs[4]!=null?objs[4]+"":"--");
				commonFlush.setBsc(objs[4]!=null?objs[4]+"":"--");
				commonFlush.setSgsn(objs[5]!=null?objs[5]+"":"--");
				commonFlush.setSaleArea(objs[6]!=null?objs[6]+"":"--");
				commonFlush.setStreet(objs[7]!=null?objs[7]+"":"--");
				commonFlush.setSubsidiaryCompany(objs[8]!=null?objs[8]+"":"--");
				commonFlush.setNmAveFlush(Common.StringTodouble(objs[9]+""));
				
//				logger.info(objs[5]+"--"+objs[6]+objs[8]);
				// 访问次数
				if (timeLevel == 3) {
					commonFlush.setFullDate(CommonUtils.getDayInnerWeek(time));
				} else {
					commonFlush.setFullDate(time);
				}
				commonFlush.calculateData();
				//commonFlush.numberFormatData();
				commonFlushList.add(commonFlush);
			}
		}
		return commonFlushList;
	}
	
	/**
	 * 根据时间粒度，来组装时间查询条件
	 * @param timeLevel
	 * @param dateStr
	 * @return
	 */
public String getQueryCondition(int timeLevel,String dateStr){
		
		String[] str=dateStr.split(";");
		int year=Common.StringToInt(str[0]);
		int month=Common.StringToInt(str[1]);
		int week=Common.StringToInt(str[2]);
		int day=Common.StringToInt(str[3]);
		int hour=Common.StringToInt(str[4]);
		StringBuffer conditonStr=new StringBuffer();
		if(timeLevel==1){
			conditonStr.append(" and intYear=");
			conditonStr.append(year);
			conditonStr.append(" and intMonth=");
			conditonStr.append(month);
			conditonStr.append(" and intDay=");
			conditonStr.append(day);
			conditonStr.append(" and intHour=");
			conditonStr.append(hour);
		}else if(timeLevel==2){
			conditonStr.append(" and intYear=");
			conditonStr.append(year);
			conditonStr.append(" and intMonth=");
			conditonStr.append(month);
			conditonStr.append(" and intDay=");
			conditonStr.append(day);
		}else if(timeLevel==3){
			conditonStr.append(" and intYear=");
			conditonStr.append(year);
//			conditonStr.append(" and intMonth=");
//			conditonStr.append(month);
			conditonStr.append(" and intWeek=");
			conditonStr.append(week);
		}else if(timeLevel==4){
			conditonStr.append(" and intYear=");
			conditonStr.append(year);
			conditonStr.append(" and intMonth=");
			conditonStr.append(month);
		}
		
		return conditonStr.toString();
		
	}
	
	/**
	 * 把时间拆分成 整数
	 * @param date
	 * @param timeLevel
	 * @return
	 */
	public String changeDate(String date,int timeLevel){
		Date d=null;
		if(timeLevel==1){
			
		}else if(timeLevel==2){
			date=date+" 01:00";
		}else if(timeLevel==3){
			date=date+" 01:00";
		}else if(timeLevel==4){
			date=date+"-01"+" 00:00";
		}
		StringBuffer sql=new StringBuffer();
		try {
			d = sdf.parse(date);
		} catch (ParseException e) {
			logger.error(e.getMessage());
		}
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(d);
		int year=calendar.get(Calendar.YEAR);
		int month=calendar.get(Calendar.MONTH)+1;
		int day=calendar.get(Calendar.DATE);
		int hour=calendar.get(Calendar.HOUR_OF_DAY);
		int week=calendar.get(Calendar.WEEK_OF_YEAR);
		String dateStr=year+";"+month+";"+week+";"+day+";"+hour;
		return dateStr;
	
	}
}
