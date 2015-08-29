package com.symbol.app.mantoeye.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.symbol.app.mantoeye.dao.FeedbackDAO;
import com.symbol.app.mantoeye.dao.TerminalDAO;
import com.symbol.app.mantoeye.entity.TbFeedback;
import com.symbol.app.mantoeye.entity.TerminalEntity;
import com.symbol.wp.core.dto.VBaseDepartment;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.orm.PropertyFilter;
import com.symbol.wp.modules.orm.hibernate.EntityManager;

//Spring Service Bean的标识.
@Service
// 默认将类中的所有函数纳入事务管理.
@Transactional
public class FeedbackManager extends EntityManager<TbFeedback, String> {

	@Autowired
	private FeedbackDAO feedbackDAO;
	
	/**
	 * 实现回调函数,为EntityManager基类的CRUD操作提供DAO.
	 */
	@Override
	protected FeedbackDAO getEntityDao() {
		return feedbackDAO;
	}
	
	/**
	 * 重写父类的方法，把View的list写进去
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<TbFeedback> search(final Page<TbFeedback> page,
			final List<PropertyFilter> filters) {
		Page<TbFeedback> apage = getEntityDao().find(page, filters);
		List<TbFeedback> dateList = apage.getResult();
		apage.setVresult(dateList);
		return apage;
	}
}
