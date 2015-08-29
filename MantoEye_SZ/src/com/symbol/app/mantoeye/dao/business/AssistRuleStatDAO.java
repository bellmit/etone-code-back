package com.symbol.app.mantoeye.dao.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.symbol.app.mantoeye.dao.common.HibernateQueryDAO;
import com.symbol.app.mantoeye.dto.business.VStatDayAssistRule;
import com.symbol.app.mantoeye.entity.business.FtbStatDayAssistRule;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.modules.orm.Page;
/**
 * 检验规则异常统计
 * @author rankin
 *
 */
@Repository
public class AssistRuleStatDAO  extends HibernateQueryDAO<FtbStatDayAssistRule, Integer>{
	
	
//	select count(a.intUnMatchCount),a.nmMainAssistVetorId from ftbStatDayAssistRule a,ftbMainAssistVetor b,ftbBusinessMainVetor c,dtbBusinessSite d 
//	 where convert(datetime,string(a.intYear,'-',a.intMonth,'-',a.intDay,' 00:00:00'))>='2008-12-12 00:00:00'
//	 and convert(datetime,string(a.intYear,'-',a.intMonth,'-',a.intDay,' 00:00:00'))<='2009-12-12 00:00:00' and d.vcBussinessName like '%%' and a.nmMainAssistVetorId = b.nmMainAssistVetorId 
//	 and b.nmBusinessMainVetorId = c.nmBusinessMainVetorId  and c.nmBussinessId = d.nmBussinessId group by a.nmMainAssistVetorId
	
	public List<VStatDayAssistRule> findPageData(Page page,String busiName,Date start,Date end){

		 List<VStatDayAssistRule> result = new  ArrayList<VStatDayAssistRule> ();
		 VStatDayAssistRule vo = null;
		
		String sql = "select sum(a.nmUnMatchCount) as count,a.nmBusinessMainVetorId as mid from ftbStatDayAssistRule a,ftbBusinessMainVetor c,dtbBusinessSite d " +
		 "  where convert(datetime,string(a.intYear,'-',a.intMonth,'-',a.intDay))>='" +Common.getDateFormat(start)+
		 "'  and convert(datetime,string(a.intYear,'-',a.intMonth,'-',a.intDay))<='" +Common.getDateFormat(end)+
		 "'  and d.vcBussinessName like ? " +
		 "  and a.nmBusinessMainVetorId = c.nmBusinessMainVetorId  and c.nmBussinessId = d.nmBussinessId group by a.nmBusinessMainVetorId  ";
		 if(page.getOrderBy()!=null&&page.getOrderBy()!=""){
			 sql = sql+" order by "+page.getOrderBy()+" "+page.getOrder()+" ";
		 }else{
			 sql = sql+" order by count desc ";
		 }
		SQLQuery query = this.getSession().createSQLQuery(sql);	
		if(busiName==null){
			busiName = "";
		}
		query.setString(0, "%"+busiName+"%");
		query.setFirstResult(page.getFirst());
		query.setMaxResults(page.getPageSize());
		List list = query.list();
		logger.info("counts:"+list.size());
		if(list!=null&&list.size()>0){
			for(int i=0;i<list.size();i++){
				Object[] objs = (Object[])list.get(i);
				vo = new VStatDayAssistRule();
				vo.setTotalCount(Common.StringToLong(objs[0]+""));
				vo.setNmBusinessMainVetorId(Common.StringToInteger(objs[1]+""));
				
				result.add(vo);
			}
		}
		return result;
	}
	public int getCount(String busiName,Date start,Date end){

		logger.info(busiName+"-busiName--"+start+"-start----"+end);
		
		String sql = "select sum(a.nmUnMatchCount) as count,a.nmBusinessMainVetorId as mid from ftbStatDayAssistRule a,ftbBusinessMainVetor c,dtbBusinessSite d " +
		 "  where convert(datetime,string(a.intYear,'-',a.intMonth,'-',a.intDay))>= '" +Common.getDateFormat(start)+
		 "'  and convert(datetime,string(a.intYear,'-',a.intMonth,'-',a.intDay))<= '" +Common.getDateFormat(end)+
		 "'  and d.vcBussinessName like ?  " +
		 "  and a.nmBusinessMainVetorId = c.nmBusinessMainVetorId  and c.nmBussinessId = d.nmBussinessId group by a.nmBusinessMainVetorId  order by count desc";
		 
		SQLQuery query = this.getSession().createSQLQuery(sql);	
		//query.setTimestamp(0, start);
		//query.setTimestamp(1, end);
		if(busiName==null){
			busiName = "";
		}
		query.setString(0, "%"+busiName+"%");/**/
		List pageList = query.list();
		return pageList.size();
	}
	public List<VStatDayAssistRule> findAllData(String busiName,Date start,Date end){

		 List<VStatDayAssistRule> result = new  ArrayList<VStatDayAssistRule> ();
		 VStatDayAssistRule vo = null;
		
		String sql = "select sum(a.nmUnMatchCount) as count,a.nmBusinessMainVetorId as mid from ftbStatDayAssistRule a,ftbBusinessMainVetor c,dtbBusinessSite d " +
		 "  where convert(datetime,string(a.intYear,'-',a.intMonth,'-',a.intDay))>= '" +Common.getDateFormat(start)+
		 "'  and convert(datetime,string(a.intYear,'-',a.intMonth,'-',a.intDay))<= '" +Common.getDateFormat(end)+
		 "'  and d.vcBussinessName like ?  " +
		 "  and a.nmBusinessMainVetorId = c.nmBusinessMainVetorId  and c.nmBussinessId = d.nmBussinessId group by a.nmBusinessMainVetorId  order by count desc";
		 
		SQLQuery query = this.getSession().createSQLQuery(sql);	
		if(busiName==null){
			busiName = "";
		}
		query.setString(0, "%"+busiName+"%");
		List list = query.list();
		if(list!=null&&list.size()>0){
			for(int i=0;i<list.size();i++){
				Object[] objs = (Object[])list.get(i);
				vo = new VStatDayAssistRule();
				vo.setTotalCount(Common.StringToLong(objs[0]+""));
				vo.setNmBusinessMainVetorId(Common.StringToInteger(objs[1]+""));
				result.add(vo);
			}
		}
		return result;
	}
}
