package com.symbol.app.mantoeye.service.businessRule;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.symbol.app.mantoeye.entity.business.DtbBusinessCompany;
import com.symbol.wp.modules.orm.hibernate.EntityManager;
/**
 * 业务配置
 * @author rankin
 *
 */
//Spring Service Bean的标识.
@Service
// 默认将类中的所有函数纳入事务管理.
@Transactional
public class BusinessCompanyManager extends EntityManager<DtbBusinessCompany, Integer> {
	
}
