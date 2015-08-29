package com.symbol.app.mantoeye.dao.business;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.symbol.app.mantoeye.dao.common.HibernateQueryDAO;
import com.symbol.app.mantoeye.dto.flush.TestFlush;
import com.symbol.app.mantoeye.entity.business.FtbDataGetterBusiness;
import com.symbol.wp.core.util.Common;

/**
 * 拨测结果DAO
 * @author rankin
 *
 */
@Repository
public class DataGetterBusinessDAO extends HibernateQueryDAO<FtbDataGetterBusiness, Long>{
	
	/**
	 * 获取用户的流量
	 * @return
	 */
	public List<TestFlush> getMsisdnFlush(Long taskid){
		String sql = "select sum(nmUpFlush),sum(nmDownFlush),nmMsisdn from ftbDataGetterBusiness where nmDataGetterTaskId = ? group by nmMsisdn order by nmMsisdn ";
		SQLQuery query = this.getSession().createSQLQuery(sql);
		query.setParameter(0, taskid);
		List list = query.list();
		TestFlush bean;
		List<TestFlush> result = new ArrayList<TestFlush>();
		logger.info(list.size()+"-");
		if(list!=null&&list.size()>0){
			for(int i=0;i<list.size();i++){
				Object[] objs = (Object[])list.get(i);
				bean = new TestFlush();
				bean.setTaskid(taskid);
				bean.setUpFlush(Common.StringToLong(objs[0]+""));
				bean.setDownFlush(Common.StringToLong(objs[1]+""));
				bean.setTotalFlush(bean.getUpFlush()+bean.getDownFlush());
				bean.setMsisdn(objs[2]+"");
				result.add(bean);
			}
		}		
		return result;
	}
	/**
	 * 获取用户各业务的流量
	 * @return
	 */
	public List<TestFlush> getMsisdnBusiFlush(Long taskid,String msisdn){
	//	select sum(a.nmUpFlush),sum(a.nmDownFlush),sum(a.nmUpFlush+a.nmDownFlush) as total,b.vcBussinessName from ftbDataGetterBusiness a left join
	//	 dtbBusinessSite b on a.nmBussinessId = b.nmBussinessId where a.nmDataGetterTaskId = 264 and a.nmMsisdn = 8614744106186 group by  b.vcBussinessName order by total desc
		String sql = "select sum(a.nmUpFlush),sum(a.nmDownFlush),sum(a.nmUpFlush+a.nmDownFlush) as total,b.vcBussinessName from ftbDataGetterBusiness a left join dtbBusinessSite b on a.nmBussinessId = b.nmBussinessId where a.nmDataGetterTaskId = ? and a.nmMsisdn = ? group by b.vcBussinessName order by total desc";
		SQLQuery query = this.getSession().createSQLQuery(sql);
		query.setParameter(0, taskid);
		query.setParameter(1, msisdn);
		List list = query.list();
		TestFlush bean;
		List<TestFlush> result = new ArrayList<TestFlush>();
		logger.info(list.size()+"-");
		if(list!=null&&list.size()>0){
			for(int i=0;i<list.size();i++){
				Object[] objs = (Object[])list.get(i);
				bean = new TestFlush();
				bean.setUpFlush(Common.StringToLong(objs[0]+""));
				bean.setDownFlush(Common.StringToLong(objs[1]+""));
				bean.setTotalFlush(Common.StringToLong(objs[2]+""));
				bean.setMsisdn(msisdn);
				bean.setTaskid(taskid);
				if(objs[3]==null||(objs[3]+"")=="" ){
					bean.setBusinessName("未知")	;
				}else{
					bean.setBusinessName(objs[3]+"");
				}
				result.add(bean);
			}
		}		
		return result;
	}

}
