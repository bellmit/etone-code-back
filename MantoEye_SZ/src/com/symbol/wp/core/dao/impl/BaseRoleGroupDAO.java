package com.symbol.wp.core.dao.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.symbol.wp.core.dto.VBaseRoleGroup;
import com.symbol.wp.core.entity.TbBaseRoleGroup;
import com.symbol.wp.modules.orm.hibernate.HibernateDao;


//Spring DAO Bean的标识
@Repository
public class BaseRoleGroupDAO extends HibernateDao<TbBaseRoleGroup,String> {
	List allList=null;
	public List<VBaseRoleGroup> getRoleGroup(){
		List<TbBaseRoleGroup> list=super.getAll();
		List<VBaseRoleGroup> list2=new ArrayList<VBaseRoleGroup>();
		VBaseRoleGroup vbaseRoleGroup=null;
		
		for(TbBaseRoleGroup tbRoleGroup:list){
			vbaseRoleGroup=new VBaseRoleGroup();
			try {
				BeanUtils.copyProperties(vbaseRoleGroup, tbRoleGroup);
			} catch (IllegalAccessException e) {
				logger.error(e.getMessage());
			} catch (InvocationTargetException e) {
				logger.error(e.getMessage());
			}
			list2.add(vbaseRoleGroup);
		}
		return list2;
	}
	public List findRoleGroupByNo1(String roleGroupNo){
		allList=new ArrayList();
		findRoleGroupByNo(roleGroupNo);
		return allList;
		
	}
	public void findRoleGroupByNo(String roleGroupNo){
		
		List<TbBaseRoleGroup> list=this.findByNo(roleGroupNo);
		if(list!=null&&!list.isEmpty()){
			for(TbBaseRoleGroup tbBaseRoleGroup:list){
				allList.add(tbBaseRoleGroup);
				this.findRoleGroupByNo(tbBaseRoleGroup.getId());
			}
		}
		
	}
	
	public List<TbBaseRoleGroup> findByNo(String roleGroupNo){
		String usql="from TbBaseRoleGroup where vcParentId='"+roleGroupNo+"'";
		return this.createQuery(usql).list();
	}
	
	public TbBaseRoleGroup findByName(String name){
		String sql="from TbBaseRoleGroup where vcName='"+name+"'";
		List<TbBaseRoleGroup> list=this.getSession().createQuery(sql).list();
		if(list!=null&&!list.isEmpty())
			return list.get(0);
		else
			return null;
			
	}
	
}
