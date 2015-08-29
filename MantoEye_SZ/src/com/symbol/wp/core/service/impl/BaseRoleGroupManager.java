package com.symbol.wp.core.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.symbol.wp.core.dao.impl.BaseRoleGroupDAO;
import com.symbol.wp.core.dao.impl.BaseRolesDAO;
import com.symbol.wp.core.dto.VBaseRoleGroup;
import com.symbol.wp.core.entity.TbBaseRoleGroup;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.orm.PropertyFilter;
import com.symbol.wp.modules.orm.hibernate.EntityManager;

@Service
//默认将类中的所有函数纳入事务管理.
@Transactional
public class BaseRoleGroupManager extends EntityManager<TbBaseRoleGroup,String> {
	@Autowired
	private BaseRoleGroupDAO baseRoleGroupDAO;
	
	@Override
	protected BaseRoleGroupDAO getEntityDao() {
		return baseRoleGroupDAO;
	}
	
	@Autowired
	private BaseRolesDAO baseRolesDAO;
	
	@Override
	@Transactional(readOnly = true)
	public Page<TbBaseRoleGroup> search(final Page<TbBaseRoleGroup> page, final List<PropertyFilter> filters) {
		
		Page<TbBaseRoleGroup> age=baseRoleGroupDAO.find(page, filters);
		List<TbBaseRoleGroup> list=age.getResult();
		List<VBaseRoleGroup> viewList=new ArrayList<VBaseRoleGroup>();
		VBaseRoleGroup vBaseRoleGroup=null;
		for(TbBaseRoleGroup tbBaseRoleGroup:list){
			vBaseRoleGroup =new VBaseRoleGroup();
			try {
				BeanUtils.copyProperties(vBaseRoleGroup, tbBaseRoleGroup);
			} catch (IllegalAccessException e) {
				logger.error(e.getMessage());
			} catch (InvocationTargetException e) {
				logger.error(e.getMessage());
			}
			viewList.add(vBaseRoleGroup);
		}
		
		age.setVresult(viewList);
		return age;
	}
	
	@Transactional
	public String[] deleteByKey(final String[] ids) {
		String[] str=new String[2];
		List<TbBaseRoleGroup> list=new ArrayList<TbBaseRoleGroup>();
		for(int i=0;i<ids.length;i++){
			TbBaseRoleGroup tbGroup=baseRoleGroupDAO.get(ids[i]+"");
			if(tbGroup!=null){
				list.add(tbGroup);
			}
		}
		
		for(TbBaseRoleGroup tbBaseRoleGroup:list){
			List<TbBaseRoleGroup> list2=baseRoleGroupDAO.findByNo(tbBaseRoleGroup.getId());
			if(list2!=null&&!list.isEmpty()&&list2.size()>0){
				str[0]=tbBaseRoleGroup.getVcName();
				String groupName="";
				
				for(TbBaseRoleGroup tbBaseRoleGroup2:list2){
					groupName=groupName+";"+tbBaseRoleGroup2.getVcName();
				}
				str[1]=groupName.substring(1,groupName.length());
				return str;
			}
			
			
		}
		
		for(TbBaseRoleGroup tbGroup:list){
			baseRolesDAO.deleteByNo(tbGroup.getId());
			baseRoleGroupDAO.delete(tbGroup);
		}
		return null;
	}
	
	@Override
	@Transactional
	public void deleteByKeys(final String[] ids) {
		Map<String,TbBaseRoleGroup> map=new HashMap<String,TbBaseRoleGroup>();
		for(int i=0;i<ids.length;i++){
			TbBaseRoleGroup tbGroup=baseRoleGroupDAO.get(ids[i]);
			if(tbGroup!=null){
				map.put(ids[i]+"",tbGroup);
			}
		}
		findByNo(map);
		List<TbBaseRoleGroup> list=new ArrayList(map.values());
		for(TbBaseRoleGroup tbGroup:list){
			baseRolesDAO.deleteByNo(tbGroup.getId());
			baseRoleGroupDAO.delete(tbGroup);
		}
	}
	
	public void findByNo(Map<String,TbBaseRoleGroup> map){
		List<TbBaseRoleGroup> list=new ArrayList(map.values());
		for(TbBaseRoleGroup tbGroup:list){
			findByNo(map,tbGroup.getId());
		}
		
	}
	
	public void findByNo(Map<String,TbBaseRoleGroup> map,String roleGroupNo){
		List<TbBaseRoleGroup> list=baseRoleGroupDAO.findRoleGroupByNo1(roleGroupNo);
		for(TbBaseRoleGroup tbBaseGroup:list){
			if(map.get(tbBaseGroup.getId())==null){
				map.put(tbBaseGroup.getId(), tbBaseGroup);
			}
		}
	}
	
	public Map<String,TbBaseRoleGroup> findAllGroup(){
		Map<String,TbBaseRoleGroup> map=new HashMap<String,TbBaseRoleGroup>();
		List<TbBaseRoleGroup> list=baseRoleGroupDAO.getAll();
		if(list!=null&&!list.isEmpty()){
			for(TbBaseRoleGroup group:list){
				map.put(group.getId(), group);
			}
		}
		return map;
	}
	
	public TbBaseRoleGroup findGroupByName(String name){
		return baseRoleGroupDAO.findByName(name);
	}
}
