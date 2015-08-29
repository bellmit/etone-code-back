package com.symbol.wp.core.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.symbol.wp.core.dao.impl.BaseOpLogDAO;
import com.symbol.wp.core.dao.impl.BaseUserInfoDAO;
import com.symbol.wp.core.dto.VBaseOpLog;
import com.symbol.wp.core.entity.TbBaseOpLog;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.orm.PropertyFilter;
import com.symbol.wp.modules.orm.hibernate.EntityManager;

/**
 * @author wendy
 * @function:业务层公共接口
 */
// Spring Service Bean的标识.
@Service
// 默认将类中的所有函数纳入事务管理.
@Transactional
public class BaseOpLogManager extends EntityManager<TbBaseOpLog, String> {

	@Autowired
	private BaseOpLogDAO baseOpLogDAO;

	@Autowired
	private BaseUserInfoDAO baseUserInfoDAO;

	/**
	 * 实现回调函数,为EntityManager基类的CRUD操作提供DAO.
	 */
	@Override
	protected BaseOpLogDAO getEntityDao() {
		return baseOpLogDAO;
	}

	@Override
	@Transactional(readOnly = true)
	public Page<TbBaseOpLog> search(final Page<TbBaseOpLog> page,
			final List<PropertyFilter> filters) {
		Page<TbBaseOpLog> age=baseOpLogDAO.find(page, filters);
		List<TbBaseOpLog> list=age.getResult();
		List<VBaseOpLog> viewList=new ArrayList<VBaseOpLog>();
		VBaseOpLog vBaseOpLog=null;
		for(TbBaseOpLog tbBaseOpLog:list){
			vBaseOpLog =new VBaseOpLog();
			try {
				vBaseOpLog = this.convertBeanToView(tbBaseOpLog);
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
			viewList.add(vBaseOpLog);
		}
		age.setVresult(viewList);
		return age;
	}

	/**
	 * 对象转换视图
	 * @param bean
	 * @return
	 */
	@Transactional(readOnly = true)
	public VBaseOpLog convertBeanToView(TbBaseOpLog bean) {
		try {
			VBaseOpLog view = new VBaseOpLog();
			BeanUtils.copyProperties(view, bean);
			view.setUserName(baseUserInfoDAO.getVcUserName(bean.getVcLoginUser()));
			view.setViewDate(Common.getDateTimeFormat(bean.getDtRecordTime()));
			return view;
		}  catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	/**
	 * 删除日志记录
	 * 
	 * @param keys
	 * @param request
	 * @return
	 */
	public boolean deleteRecord(String[] keys, HttpServletRequest request) {
		boolean flag = false;
		try {
			for (String id : keys) {
				super.delete(id);
			}
			flag = true;
		} catch (Exception ex) {
			return false;
		}
		return flag;
	}

	/**
	 * 删除所有日志记录
	 * 
	 * @return
	 */
	public List findAllData() {
		return super.getAll();
	}
	/**
	 * 根据用户名获取用户登陆名
	 */
	public String getVcLoginName(String userName){
		return baseUserInfoDAO.getVcLoginUser(userName);
	}
	public Map<String,String> getUserNames(String  loginnames){
		return baseUserInfoDAO.getUserNames(loginnames);
	}
}