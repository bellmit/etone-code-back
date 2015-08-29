package com.symbol.app.mantoeye.dao.businessAnalyse;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.symbol.app.mantoeye.dto.flush.CommonFlush;
import com.symbol.app.mantoeye.util.CommonUtils;
import com.symbol.app.mantoeye.util.MantoEyeUtils;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.modules.orm.hibernate.HibernateDao;

@Repository
public class UserAttachDistributeDAO extends HibernateDao {
	Map<Integer,String> mapUser=new HashMap<Integer,String>();
	Map<Integer,String> userBelong=new HashMap<Integer,String>();
	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
	
	public void initMap(){
		mapUser.put(4, "vStatMonthUserBelong");
		mapUser.put(3, "vStatWeekUserBelong");
		mapUser.put(2, "vStatDayUserBelong");
		mapUser.put(1, "vStatHourUserBelong");
		userBelong.put(4, "国外");
		userBelong.put(3, "外地");
		userBelong.put(2, "省内非深圳");
		userBelong.put(1, "本地");
		userBelong.put(0, "未知");
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
	public List<CommonFlush> queryUserAttachDistributeDAO(int timeLevel,String date,int dataType){
		//计算占比
		Double [] dbs = findSumTotalData(timeLevel,date);
		
		String strDate=CommonUtils.changeDate(date, timeLevel);
		StringBuffer sql=new StringBuffer();
		Date d = CommonUtils.getDate(date);
		String[] str=strDate.split(";");
		int year=CommonUtils.getYear(d);
		int month=CommonUtils.getMonth(d);
		int week=CommonUtils.getWeek(d);
		int day=CommonUtils.getDay(d);
		int hour=CommonUtils.getHour(d);
		sql.append("select intUserBelongId,nmAppUpFlush,nmAppDownFlush,nmFlush,nmUsers,nmActiveCounts,nmAveFlush from ");
		sql.append(mapUser.get(timeLevel));
		sql.append(" where 1=1 ");
		sql.append(" and intRaitype=");
		sql.append(dataType);
		if(timeLevel==1){
			sql.append(" and intYear=");
			sql.append(year);
			sql.append(" and intMonth=");
			sql.append(month);
			sql.append(" and intDay=");
			sql.append(day);
			sql.append(" and intHour=");
			sql.append(hour);
		}else if(timeLevel==2){
			sql.append(" and intYear=");
			sql.append(year);
			sql.append(" and intMonth=");
			sql.append(month);
			sql.append(" and intDay=");
			sql.append(day);
		}else if(timeLevel==3){
			sql.append(" and intYear=");
			sql.append(year);
//			sql.append(" and intMonth=");
//			sql.append(month);
			sql.append(" and intWeek=");
			sql.append(week);
		}else if(timeLevel==4){
			sql.append(" and intYear=");
			sql.append(year);
			sql.append(" and intMonth=");
			sql.append(month);
		}
		sql.append(" order by nmFlush desc ");

		CommonFlush commonFlush=null;
		List<CommonFlush> list=new ArrayList<CommonFlush>();
		Iterator it= this.getSession().createSQLQuery(sql.toString()).list().iterator();
		while(it.hasNext()){
			Object[] obj=(Object[])it.next();
			commonFlush=new CommonFlush();
				commonFlush.setUserBelong(userBelong.get(Common.StringToInt(obj[0]+"")));
				commonFlush.setIntUpFlush(Common.StringToLong(obj[1]+""));
				commonFlush.setIntDownFlush(Common.StringToLong(obj[2]+""));
				commonFlush.setTotalFlush(Common.StringToLong(obj[3]+""));
				commonFlush.setIntImsis(Common.StringToLong(obj[4]+""));
				commonFlush.setActiveCount(Common.StringToLong(obj[5]+""));
				commonFlush.setNmAveFlush(Common.StringTodouble(obj[6]+""));
				commonFlush.calculateData();
				commonFlush.calculateFlushRate(dbs[0], dbs[1]);
				//commonFlush.numberFormatData();
				if(timeLevel==3){//如果时间粒度是周 获取该周内的时间
					commonFlush.setFullDate(CommonUtils.getDayInnerWeek(date));
					
				}else{
					commonFlush.setFullDate(date);
				}
			list.add(commonFlush);
		}
		return list;
	}
}
