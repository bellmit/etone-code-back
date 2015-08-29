package com.symbol.app.mantoeye.dao.business;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.symbol.app.mantoeye.entity.business.DtbBusinessSite;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.modules.orm.hibernate.HibernateDao;
/**
 * 业务配置
 * @author rankin
 *
 */
@Repository
public class BusinessSiteDAO  extends HibernateDao<DtbBusinessSite, Integer>{

	/**
	 * 保存业务 IQ内通过hibernate无法获得主键，只能手动保存
	 * @param entiy
	 */
	public void saveEntity(DtbBusinessSite entity,Integer typeId,Integer compId,String typeName,String compName){

		Integer busi_id = entity.getNmBussinessId();
		if(busi_id == null||busi_id<=0){
			busi_id = 0;
		}
		try{ 
	         String sql="{Call proc_create_businesssite_byweb(?,?,?,?,?,?,?,?) }"; 
	         SQLQuery query = this.getSession().createSQLQuery(sql);
	         query.setInteger(0, busi_id);
	         query.setInteger(1, typeId);
	         query.setInteger(2,compId); 
	         query.setString(3, entity.getVcBussinessName());
	         query.setString(4, entity.getVcBussinessNote());
	         query.setString(5, entity.getVcBussinessPicPath());
	         query.setString(6, typeName);
	         query.setString(7, compName);
	         
	         query.executeUpdate();

	      }catch(Exception e){ 
	    	  logger.error(e.getMessage()); 
	      }finally{ 
	      }
	}
	/**
	 * 更新业务数据，因为业务类型发生了变化，必须特殊处理，不然统计数据会出现重复记录
	 * 处理更改业务类型数据步骤：
	 * 1:修改原来业务的业务名称为 原来业务名称+时间后缀 （如 腾讯-->腾讯[20100730140000]）  
	 * 2:按修改后的业务数据插入一条新纪录
	 * 3:把原有业务的关联规则数据的业务ID更新为新插入业务的ID
	 * @param entiy
	 */
	public void updateEntity(DtbBusinessSite entity,Integer typeId,Integer compId,String typeName,String compName){

		Integer busi_id = entity.getNmBussinessId();
		if(busi_id == null||busi_id<=0){
			logger.error("发生内部错误！编辑数据失败！");
			return;
		}
		String newbusiname = entity.getVcBussinessName()+"["+Common.getNow("yyyyMMddHHmmss")+"]";
		logger.info("OldName:"+newbusiname);
		try{ 
	         String sql="{Call proc_update_businesssite_byweb(?,?,?,?,?,?,?,?,?) }"; 
	         SQLQuery query = this.getSession().createSQLQuery(sql);
	         query.setInteger(0, busi_id);
	         query.setInteger(1, typeId);
	         query.setInteger(2,compId); 
	         query.setString(3, entity.getVcBussinessName());
	         query.setString(4, entity.getVcBussinessNote());
	         query.setString(5, entity.getVcBussinessPicPath());
	         query.setString(6, typeName);
	         query.setString(7, compName);
	         query.setString(8, newbusiname);
	         
	         query.executeUpdate();

	      }catch(Exception e){ 
	    	  logger.error(e.getMessage()); 
	      }finally{ 
	      }
	}
	public Map<Long,DtbBusinessSite> getMapByIds(String busiIds){
		Map<Long,DtbBusinessSite> rmap = new HashMap<Long,DtbBusinessSite>();
		DtbBusinessSite bean;
		String sql = "select nmBussinessId,vcBussinessName from dtbBusinessSite where nmBussinessId in ("+busiIds+")";
		List list = this.getSession().createSQLQuery(sql).list();
		if(list!=null&&list.size()>0){
			for(int i=0;i<list.size();i++){
				Object [] objs = (Object[])list.get(i);
				
				if(objs.length>1){
					bean = new DtbBusinessSite();
					bean.setNmBussinessId(Common.StringToInt(objs[0]+""));
					bean.setVcBussinessName(objs[1]+"");
					rmap.put(Common.StringToLong(objs[0]+""), bean);
				}
			}
		}
		return rmap;
	}
}
