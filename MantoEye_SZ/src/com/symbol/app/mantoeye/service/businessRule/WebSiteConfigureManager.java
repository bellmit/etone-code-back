package com.symbol.app.mantoeye.service.businessRule;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.symbol.app.mantoeye.dao.businessRules.WebSiteConfigureDAO;
import com.symbol.app.mantoeye.dto.sports.CommonSport;
import com.symbol.wp.modules.orm.Page;



// Spring Service Bean的标识.
@Service
// 默认将类中的所有函数纳入事务管理.
@Transactional
public class WebSiteConfigureManager {

	@Autowired
	private WebSiteConfigureDAO webSiteConfigureDAO;


	public Page<CommonSport> query(final Page page, String businessName) {
		return webSiteConfigureDAO.query(page,businessName);
	}
	
	public List<CommonSport> listData(String businessName) {
		return webSiteConfigureDAO.listData(businessName);
	}
	
	public List<CommonSport> queryAllBussList() {
		return webSiteConfigureDAO.listData(null);
	}
	
	
	public void deleteBusiness(String ids) {
		webSiteConfigureDAO.deleteBusiness(ids);
	}
	
	public void saveBussList(Long businessId,String businessName) {
		webSiteConfigureDAO.saveBussList(businessId,businessName);
	}
	
	
}
