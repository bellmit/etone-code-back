package com.symbol.app.mantoeye.service.businessAnalyse;



import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.symbol.app.mantoeye.dao.businessAnalyse.OwnBusinessConfigureDAO;
import com.symbol.app.mantoeye.dto.sports.CommonSport;
import com.symbol.wp.modules.orm.Page;



// Spring Service Bean的标识.
@Service
// 默认将类中的所有函数纳入事务管理.
@Transactional
public class OwnBusinessConfigureManager {

	@Autowired
	private OwnBusinessConfigureDAO ownBusinessConfigureDAO;


	public Page<CommonSport> query(final Page page, String businessName) {
		return ownBusinessConfigureDAO.query(page,businessName);
	}
	
	public List<CommonSport> listData(String businessName) {
		return ownBusinessConfigureDAO.listData(businessName);
	}
	
	public List<CommonSport> queryAllBussList() {
		return ownBusinessConfigureDAO.listData(null);
	}
	
	
	public void deleteBusiness(String ids) {
		 ownBusinessConfigureDAO.deleteBusiness(ids);
	}
	
	public void saveBussList(Long businessId,String vcNote) {
		 ownBusinessConfigureDAO.saveBussList(businessId,vcNote);
	}
	
	
}
