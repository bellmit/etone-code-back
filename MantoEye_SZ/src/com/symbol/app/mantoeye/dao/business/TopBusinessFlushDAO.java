package com.symbol.app.mantoeye.dao.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.symbol.app.mantoeye.dao.common.HibernateQueryDAO;
import com.symbol.app.mantoeye.entity.business.FtbStatDayBusFlushOrder;
import com.symbol.wp.core.util.Common;

/**
 * 业务流量排名
 * 
 * @author rankin
 * 
 */
@Repository
public class TopBusinessFlushDAO extends
		HibernateQueryDAO<FtbStatDayBusFlushOrder, Integer> {
	public List<FtbStatDayBusFlushOrder> findAll(String findtype, String start, String end){
		List<FtbStatDayBusFlushOrder> result = new ArrayList<FtbStatDayBusFlushOrder>();
		FtbStatDayBusFlushOrder beana;
		Object [] objs;
		logger.info("findtype:"+findtype+"  start"+start+"  end"+end);
		String sql = " select  a.nmBusFlushOrderId,a.vcServerIp,a.intPort,a.nmFlush,a.vcGuessBusiness,a.vcMainBussiness,a.isFind,a.intOrder from ftbStatDayBusFlushOrder a where 1=1 ";
		sql = sql +" and a.dtStatTime>='"+start +"' and a.dtStatTime<='" + end +"' ";
		if(Common.judgeString(findtype)){
			sql = sql + " and a.isFind = '"+findtype+"'" ;
		}
		sql = sql + " order by  a.nmFlush desc ";
		SQLQuery query = this.getSession().createSQLQuery(sql);
		List list = query.list();
		if (list != null && list.size() > 0) {	
			
			for (int i = 0; i < list.size(); i++) {
				objs =(Object [])list.get(i);
				beana = new FtbStatDayBusFlushOrder();
				beana.setNmBusFlushOrderId(Common.StringToLong(objs[0]+""));
				beana.setVcServerIp(objs[1]+"");
				beana.setIntPort(objs[2]!=null?Common.StringToInt(objs[2]+""):0);
				beana.setNmFlush(Common.StringToLong(objs[3]+""));
				beana.setVcGuessBusiness(objs[4]+"");
				beana.setVcMainBussiness(objs[5]+"");
				beana.setIsFind(objs[6]+"");
				beana.setIntOrder(objs[7]!=null?Common.StringToInt(objs[7]+""):0);
				result.add(beana);
			}
		}
		return result;
	}
}
