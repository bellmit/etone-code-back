package com.symbol.app.mantoeye.service.businessRule;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.symbol.app.mantoeye.dao.businessRules.BusinessSearchDAO;
import com.symbol.app.mantoeye.dao.businessRules.WebSiteConfigureDAO;
import com.symbol.app.mantoeye.dto.sports.CommonSport;
import com.symbol.wp.modules.orm.Page;



// Spring Service Bean的标识.
@Service
// 默认将类中的所有函数纳入事务管理.
@Transactional
public class BusinessSearchManager {

	@Autowired
	private BusinessSearchDAO businessSearchDAO;


	public Page<CommonSport> query(final Page page, String vcTitleType,String vcTitle) {
		return businessSearchDAO.query(page,vcTitleType,vcTitle);
	}
	
	public List<CommonSport> listData(String vcTitleType,String vcTitle) {
		return businessSearchDAO.listData(vcTitleType,vcTitle);
	}
	
	public List<CommonSport> queryAllBussList() {
		return businessSearchDAO.listData(null,null);
	}
	
	
	public void deleteBusinessSearch(String ids) {
		businessSearchDAO.deleteBusinessSearch(ids);
	}
	
	public void saveBusinessSearch(String vcTitleType,String vcTitle,String vcKeyword) {
		businessSearchDAO.saveBusinessSearch(vcTitleType,vcTitle,vcKeyword);
	}
	
	
}
