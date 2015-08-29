package com.symbol.app.mantoeye.service.businessRule;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.symbol.app.mantoeye.dao.business.BusinessCompanyDAO;
import com.symbol.app.mantoeye.dao.business.BusinessSiteDAO;
import com.symbol.app.mantoeye.dao.business.BusinessTypeDAO;
import com.symbol.app.mantoeye.entity.business.DtbBusinessCompany;
import com.symbol.app.mantoeye.entity.business.DtbBusinessSite;
import com.symbol.app.mantoeye.entity.business.DtbBusinessType;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.orm.PropertyFilter;
import com.symbol.wp.modules.orm.hibernate.EntityManager;

/**
 * 业务配置
 * 
 * @author rankin
 * 
 */
// Spring Service Bean的标识.
@Service
// 默认将类中的所有函数纳入事务管理.
@Transactional
public class BusinessSiteManager extends
		EntityManager<DtbBusinessSite, Integer> {

	@Autowired
	private BusinessSiteDAO businessSiteDAO;
	@Autowired
	private BusinessCompanyDAO businessCompanyDAO;
	@Autowired
	private BusinessTypeDAO businessTypeDAO;

	/**
	 * 实现回调函数,为EntityManager基类的CRUD操作提供DAO.
	 */
	@Override
	protected BusinessSiteDAO getEntityDao() {
		return businessSiteDAO;
	}

	/**
	 * 重写父类的方法，把View的list写进去
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<DtbBusinessSite> search(final Page<DtbBusinessSite> page,
			final List<PropertyFilter> filters) {
		Page<DtbBusinessSite> apage = businessSiteDAO.find(page, filters);
		List<DtbBusinessSite> dateList = apage.getResult();
		apage.setVresult(dateList);
		return apage;
	}

	@Transactional
	public void saveEntity(DtbBusinessSite entity, DtbBusinessType busitype,
			DtbBusinessCompany busicomp) {
		Integer typeId = busitype.getNmBussinessTypeId();
		Integer compId = busicomp.getNmCompanyId();
		if (typeId == null || typeId < 0) {
			// typeId = businessSiteDAO.saveBusinessType(busitype);
			typeId = -1;
		}
		if (compId == null || compId < 0) {
			// compId = businessSiteDAO.saveBusinessCompany(busicomp);
			compId = -1;
		}
		businessSiteDAO.saveEntity(entity, typeId, compId,
				busitype.getVcBussinessTypeName(), busicomp.getVcName());
	}

	@Transactional
	public void updateEntity(DtbBusinessSite entity, DtbBusinessType busitype,
			DtbBusinessCompany busicomp, boolean typechanged) {

		Integer typeId = busitype.getNmBussinessTypeId();
		Integer compId = busicomp.getNmCompanyId();
		if (typeId == null || typeId < 0) {
			typeId = -1;
		}
		if (compId == null || compId < 0) {
			compId = -1;
		}
		if (!typechanged) {
			businessSiteDAO.saveEntity(entity, typeId, compId,
					busitype.getVcBussinessTypeName(), busicomp.getVcName());
		} else {// 业务类型发生变化，使用特殊的处理方式
		// businessSiteDAO.updateEntity(entity, typeId, compId,
		// busitype.getVcBussinessTypeName(), busicomp.getVcName());
			businessSiteDAO.saveEntity(entity, typeId, compId,
					busitype.getVcBussinessTypeName(), busicomp.getVcName());
		}
	}

	@Transactional
	public Map<Long, DtbBusinessSite> getNameMapByIds(String bussids) {
		return businessSiteDAO.getMapByIds(bussids);
	}
}
