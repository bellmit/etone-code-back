package com.symbol.app.mantoeye.service.businessRule;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.symbol.app.mantoeye.bean.FlushRoleBean;
import com.symbol.app.mantoeye.dao.business.BusinessMainFlushDAO;
import com.symbol.app.mantoeye.entity.business.VstatDayMainRuleFlush;
import com.symbol.app.mantoeye.entity.business.VstatDayMainRuleFlushId;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.orm.hibernate.EntityManager;

/**
 * 主规则流量碰撞率统计
 * 
 * @author rankin
 * 
 */
// Spring Service Bean的标识.
@Service
// 默认将类中的所有函数纳入事务管理.
@Transactional
public class BusinessMainFlushManager extends
		EntityManager<VstatDayMainRuleFlush, VstatDayMainRuleFlushId> {

	@Autowired
	private BusinessMainFlushDAO businessMainFlushDAO;

	/**
	 * 实现回调函数,为EntityManager基类的CRUD操作提供DAO.
	 */
	@Override
	protected BusinessMainFlushDAO getEntityDao() {
		return businessMainFlushDAO;
	}

	@Transactional(readOnly = true)
	public Page<VstatDayMainRuleFlush> searchFormView(
			final Page<VstatDayMainRuleFlush> page, Date date, String businame,
			String sip) {
		Page<VstatDayMainRuleFlush> apage = businessMainFlushDAO.findFromView(
				page, date, businame, sip);

		return apage;
	}

	@Transactional(readOnly = true)
	public int getCount(Date date, String businame, String sip) {

		return businessMainFlushDAO.getCount(date, businame, sip);
	}

	public List<FlushRoleBean> findAllFromView(String date, String businame,
			String sip) {

		return businessMainFlushDAO.findAllFromView(date, businame, sip);
	}
}
