package com.symbol.app.mantoeye.service.businessRule;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.symbol.app.mantoeye.dao.business.AssistVetorRecogniseDAO;
import com.symbol.app.mantoeye.entity.business.FtbAssistVetorRecognise;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.orm.PropertyFilter;
import com.symbol.wp.modules.orm.hibernate.EntityManager;
/**
 * 全量业务自发现
 * @author rankin
 *
 */
//Spring Service Bean的标识.
@Service
// 默认将类中的所有函数纳入事务管理.
@Transactional
public class AssistVetorRecogniseManager extends EntityManager<FtbAssistVetorRecognise, Long> {

	@Autowired
	private AssistVetorRecogniseDAO assistVetorRecogniseDAO;
	
	/**
	 * 实现回调函数,为EntityManager基类的CRUD操作提供DAO.
	 */
	@Override
	protected AssistVetorRecogniseDAO getEntityDao() {
		return assistVetorRecogniseDAO;
	}
	
	/**
	 * 重写父类的方法，把View的list写进去
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<FtbAssistVetorRecognise> search(final Page<FtbAssistVetorRecognise> page,
			final List<PropertyFilter> filters) {
		Page<FtbAssistVetorRecognise> apage = assistVetorRecogniseDAO.find(page, filters);
		List<FtbAssistVetorRecognise> dateList = apage.getResult();
		apage.setVresult(dateList);
		return apage;
	}
}
