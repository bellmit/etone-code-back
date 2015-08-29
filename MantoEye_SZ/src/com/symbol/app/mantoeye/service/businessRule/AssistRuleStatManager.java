package com.symbol.app.mantoeye.service.businessRule;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.symbol.app.mantoeye.dao.business.AssistRuleStatDAO;
import com.symbol.app.mantoeye.dao.business.BusinessMainVetorDAO;
import com.symbol.app.mantoeye.dao.business.MainAssistVetorDAO;
import com.symbol.app.mantoeye.dto.business.VStatDayAssistRule;
import com.symbol.app.mantoeye.entity.business.FtbBusinessMainVetor;
import com.symbol.app.mantoeye.entity.business.FtbMainAssistVetor;
import com.symbol.app.mantoeye.entity.business.FtbStatDayAssistRule;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.orm.PropertyFilter;
import com.symbol.wp.modules.orm.hibernate.EntityManager;

/**
 * 辅规则不匹配统计
 * 
 * @author rankin
 * 
 */
// Spring Service Bean的标识.
@Service
// 默认将类中的所有函数纳入事务管理.
@Transactional
public class AssistRuleStatManager extends
		EntityManager<FtbStatDayAssistRule, Integer> {

	@Autowired
	private AssistRuleStatDAO assistRuleStatDAO;
	
	@Autowired
	private BusinessMainVetorDAO businessMainVetorDAO;

	@Autowired
	private MainAssistVetorDAO mainAssistVetorDAO;

	/**
	 * 实现回调函数,为EntityManager基类的CRUD操作提供DAO.
	 */
	@Override
	protected AssistRuleStatDAO getEntityDao() {
		return assistRuleStatDAO;
	}

	/**
	 * 重写父类的方法，把View的list写进去
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<FtbStatDayAssistRule> search(
			final Page<FtbStatDayAssistRule> page,
			final List<PropertyFilter> filters) {
		Page<FtbStatDayAssistRule> apage = assistRuleStatDAO
				.find(page, filters);
		List<FtbStatDayAssistRule> dateList = apage.getResult();
		apage.setVresult(dateList);
		return apage;
	}

	public int getCount(String busiName, String startDate, String endDate) {
		Date start = Common.getDate(startDate);
		Date end = Common.getDate(endDate);
		return assistRuleStatDAO.getCount(busiName, start, end);
	}

	public List<VStatDayAssistRule> findPageData(Page page, String busiName,
			String startDate, String endDate) {
		Date start = Common.getDate(startDate);
		Date end = Common.getDate(endDate);
		FtbBusinessMainVetor fbean = null;
		List<VStatDayAssistRule> list = assistRuleStatDAO.findPageData(page,
				busiName, start, end);
		for (int i = 0; i < list.size(); i++) {
			fbean = businessMainVetorDAO
					.get(list.get(i).getNmBusinessMainVetorId());
			list.get(i).setMainVetor(fbean);
//			list.get(i).setAssistVetor(fbean.getFtbBusinessAssistVetor());
		}
		return list;
	}

	public List<VStatDayAssistRule> findAllData(String busiName,
			String startDate, String endDate) {
		Date start = Common.getDate(startDate);
		Date end = Common.getDate(endDate);
		FtbBusinessMainVetor fbean = null;
		List<VStatDayAssistRule> list = assistRuleStatDAO.findAllData(busiName,
				start, end);
		for (int i = 0; i < list.size(); i++) {
			fbean = businessMainVetorDAO
					.get(list.get(i).getNmBusinessMainVetorId());
			list.get(i).setMainVetor(fbean);
		//	list.get(i).setAssistVetor(fbean.getFtbBusinessAssistVetor());
		}
		return list;
	}
}
