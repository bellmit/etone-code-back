package com.symbol.wp.core.dao.impl;



import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.symbol.wp.core.constants.SqlrConstants;
import com.symbol.wp.core.dto.VBasePermissions;
import com.symbol.wp.core.entity.TbBasePermissions;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.modules.orm.hibernate.HibernateDao;
/**
 * 模块管理
 * @Title: BasePermissionsDAO.java
 * @Description: <br>
 *               <br>
 * @Company: etonetech
 * @Created Mar 23, 2009 11:39:37 AM
 * @author ranhualin
 * @version 1.0
 * @since 1.0
 */
//Spring DAO Bean的标识
@Repository
public class BasePermissionsDAO extends  HibernateDao<TbBasePermissions, String>{


	/**
	 * 获取菜单表信息
	 * @return
	 */
	public List<VBasePermissions> getPermis() {
		List<TbBasePermissions> list=super.getAll();
		List<VBasePermissions> list2=new ArrayList<VBasePermissions>();
		VBasePermissions vBasePermissions=null;
		
		for(TbBasePermissions tbBasePermissions:list){
			vBasePermissions=new VBasePermissions();
			try {
				BeanUtils.copyProperties(vBasePermissions, tbBasePermissions);
			} catch (IllegalAccessException e) {
				logger.error(e.getMessage());
			} catch (InvocationTargetException e) {
				logger.error(e.getMessage());
			}
			list2.add(vBasePermissions);
		}
		return list2;
	}
	/**
	 * 获取菜单表信息
	 * @return
	 */
	public List<VBasePermissions> getMemuPermis() {
		List<TbBasePermissions> list = super.find(SqlrConstants.TbBasePermissions_0003);
		List<VBasePermissions> list2=new ArrayList<VBasePermissions>();
		VBasePermissions vBasePermissions=null;
		
		for(TbBasePermissions tbBasePermissions:list){
			vBasePermissions=new VBasePermissions();
			try {
				BeanUtils.copyProperties(vBasePermissions, tbBasePermissions);
			} catch (IllegalAccessException e) {
				logger.error(e.getMessage());
			} catch (InvocationTargetException e) {
				logger.error(e.getMessage());
			}
			list2.add(vBasePermissions);
		}
		return list2;
	}
	
	/**
	 * 顶部快捷页面
	 * @return
	 */
	public List<VBasePermissions> getTopMemuPermis(String userNo) {

		List<TbBasePermissions> list = super.find(SqlrConstants.TbBasePermissions_0004,new String[]{userNo} );
		logger.info(list.size()+"--1");
		List<VBasePermissions> list2=new ArrayList<VBasePermissions>();
		logger.info(list2.size()+"--2");
		VBasePermissions vBasePermissions=null;
		
		for(TbBasePermissions tbBasePermissions:list){
			vBasePermissions=new VBasePermissions();
			try {
				BeanUtils.copyProperties(vBasePermissions, tbBasePermissions);
			} catch (IllegalAccessException e) {
				logger.error(e.getMessage());
			} catch (InvocationTargetException e) {
				logger.error(e.getMessage());
			}
			list2.add(vBasePermissions);
		}
		return list2;
	}
	/**
	 * 顶部快捷页面
	 * @return
	 */
	public List<VBasePermissions> getSubMemuPermis(String userNo,String tabid) {

		List<TbBasePermissions> list = super.find(SqlrConstants.TbBasePermissions_0006,new String[]{tabid,userNo} );
		List<VBasePermissions> list2=new ArrayList<VBasePermissions>();
		VBasePermissions vBasePermissions=null;
		
		for(TbBasePermissions tbBasePermissions:list){
			vBasePermissions=new VBasePermissions();
			try {
				BeanUtils.copyProperties(vBasePermissions, tbBasePermissions);
			} catch (IllegalAccessException e) {
				logger.error(e.getMessage());
			} catch (InvocationTargetException e) {
				logger.error(e.getMessage());
			}
			list2.add(vBasePermissions);
		}
		return list2;
	}

	/**
	 * @param session
	 * @param parentPermId
	 * @param basePermisView
	 * @return 参数设定:() 功能描述: 逻辑判断: 返回值:
	 * @author:wendy
	 */
	public VBasePermissions parentMenuName(String parentPermNo,
			VBasePermissions basePermisView) {
		Query query =  super.createQuery(SqlrConstants.TbBasePermissions_0002,
				new Object[] { parentPermNo });
		List<Object[]> list = query.list();
		if (list != null && !list.isEmpty()) {
			for (Iterator it = list.iterator(); it.hasNext();) {
				String array = (String) it.next();
				basePermisView.setParentPermisName(array);
				basePermisView.setParentMenuName(array);
			}
		}
		return basePermisView;
	}

	/**
	 * 获取全部的权限信息
	 * @param btButton
	 * @return
	 */
	public List<VBasePermissions> getAllPermis(boolean btButton) {
		List<VBasePermissions> list = null;
		List<TbBasePermissions> list2 = null;
		if (btButton == true) {// 具有按钮的菜单(即所有菜单)
			list2= super.find();
		} else {// 无按钮的菜单
			Criterion cro= Restrictions.eq("tiPermType", 1L);
			list2= super.find(cro);
		}
		
		
		if (list2 != null && !list2.isEmpty()) {
			list = new ArrayList();
			for (Iterator it = list2.iterator(); it.hasNext();) {
				TbBasePermissions permissions = (TbBasePermissions) it.next();
				VBasePermissions permisView = new VBasePermissions();

				this.copy(permisView, permissions);

				permisView = this.parentMenuName(
						permisView.getVcParentId(), permisView);
				list.add(permisView);
			}
		}
		return list;
	}
/**
 * 根据用户id和菜单id获取按钮
 * @param parentNo
 * @param userNo
 * @return
 */
	public List<TbBasePermissions> getButtonPermis(String parentNo, String userNo) {
		List list = null;

		list = super.find(SqlrConstants.TbBasePermissions_0005, new Object[] {
				parentNo, userNo });
		return list;
	}
	/**
	 * 根据用户id获取所有的按钮
	 * @param parentNo
	 * @param userNo
	 * @return
	 */
	public Map<String,String> getAllButtonPermis( String userNo) {
		List<TbBasePermissions> list = null;

		list = super.find(SqlrConstants.TbBasePermissions_0020, new Object[] {userNo });
		Map<String,String> map = new HashMap<String,String> ();
		if(list!=null&&list.size()>0){
			TbBasePermissions bean =  null;
			for(int i=0;i<list.size();i++){
				bean = list.get(i);
				if(map.get(bean.getVcParentId())!=null){
					String newvalue = map.get(bean.getVcParentId())+","+bean.getVcPermSymbol();
					map.put(bean.getVcParentId(),newvalue);
				}else{
					map.put(bean.getVcParentId(), bean.getVcPermSymbol());
				}
				
			}
		}
		return map;
	}
	
	/**
	 * 根据用户id获取所有的按钮
	 * @param parentNo
	 * @param userNo
	 * @return
	 */
	public String getAllOtherPermis( String userNo) {
		List<TbBasePermissions> list = null;

		list = super.find(SqlrConstants.TbBasePermissions_0021, new Object[] {userNo });
		if(list!=null&&list.size()>0){
			TbBasePermissions bean =  null;
			for(int i=0;i<list.size();i++){
				bean = list.get(0);
				return bean.getVcPermSymbol();
			}
		}
		return null;
	}

	// 获取改模块下的所有元素（包括按钮）
	public List<TbBasePermissions> getAllPermis(String id, String userId) {
		List list = null;

		list = super.find(SqlrConstants.TbBasePermissions_0010, new Object[] {
				id, userId });

		return list;
	}

	/**
	 * 通过主键查询
	 * @param id
	 * @return
	 */
	public TbBasePermissions findByPermId(String id) {
		return super.get(id);
	}

	/**
	 * 通过权限标识符查询
	 * @param symbol
	 * @return
	 */
	public TbBasePermissions findByPermSymbol(String symbol) {

		List<Criterion> crolist = new ArrayList<Criterion>();
		if (Common.judgeString(symbol)) {
			crolist.add(Restrictions.eq("vcPermSymbol", symbol));
		}
		List<TbBasePermissions> list = super.find((Criterion[])crolist.toArray());
		TbBasePermissions permissions = null;
		if (list != null && !list.isEmpty()) {
			permissions = list.get(0);
		}
		return permissions;
	}
	/**
	 * 通过权限标识符查询
	 * @param symbol
	 * @param deleted 是否已经删除 1-是 0-否
	 * @return
	 */
	public TbBasePermissions findByPermSymbol(String symbol, String deleted) {

		List<Criterion> crolist = new ArrayList<Criterion>();
		if (!Common.judgeString(deleted)) {
			deleted = "0";
		}
		if (Common.judgeString(symbol)) {
			crolist.add(Restrictions.eq("vcPermSymbol", symbol));
		}
		crolist.add(Restrictions.eq("btDeleted", deleted));
		List<TbBasePermissions> list =super.find((Criterion[])crolist.toArray());;
		TbBasePermissions permissions = null;
		if (list != null && !list.isEmpty()) {
			permissions = list.get(0);
		}
		return permissions;
	}
	
	private VBasePermissions copy(VBasePermissions view,TbBasePermissions bean){
		try {
			BeanUtils.copyProperties(view, bean);
		} catch (IllegalAccessException e) {
			logger.error(e.getMessage());
		} catch (InvocationTargetException e) {
			logger.error(e.getMessage());
		}
		return view;
	}
}
