package com.symbol.app.mantoeye.service.businessAnalyse;



import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.symbol.app.mantoeye.dao.businessAnalyse.CompetitorBusinessConfigureDAO;
import com.symbol.app.mantoeye.dao.businessAnalyse.OwnBusinessConfigureDAO;
import com.symbol.app.mantoeye.dto.flush.BussAndBussType;
import com.symbol.app.mantoeye.dto.sports.CommonSport;
import com.symbol.app.mantoeye.util.SymbolUtils;
import com.symbol.wp.modules.orm.Page;



// Spring Service Bean的标识.
@Service
// 默认将类中的所有函数纳入事务管理.
@Transactional
public class CompetitorBusinessConfigureManager {

	@Autowired
	private CompetitorBusinessConfigureDAO CompetitorBusinessConfigureDAO;

	
	public void saveBussList(Long businessId,Long id) {
		CompetitorBusinessConfigureDAO.saveBussList(businessId,id);
	}
	
	public void delBussListByBussId(Long id) {
		CompetitorBusinessConfigureDAO.delBussListByBussId(id);
	}
	
	public List findBussId(Long id) {
		return CompetitorBusinessConfigureDAO.findBussId(id);
	}
	
	/**
	 *根据选择的任务ID 组装业务对应的业务ID和业务类型ID
	 */
	public String queryBussIdAndBussIdTypeByTaskId(Long taskId) {
		List<BussAndBussType> list = CompetitorBusinessConfigureDAO
				.queryBussAndBussTypeByTaskId(taskId);
		String bisIdAndBisTypeId = "";
		String businessName = "";
		BussAndBussType bat = null;
		if (list != null && !list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				bat = list.get(i);
				bisIdAndBisTypeId = bisIdAndBisTypeId + bat.getBusinessTypeId()
						+ ":" + bat.getBusinessId() + ",";
				businessName = businessName
						+ SymbolUtils.getSaftDialogStr(bat.getBusinessName())
						+ ",";
			}
			bisIdAndBisTypeId = bisIdAndBisTypeId.substring(0, bisIdAndBisTypeId
					.length() - 1);
			businessName = businessName.substring(0, businessName.length() - 1);
			return bisIdAndBisTypeId + "@" + businessName;
		}else{
			return null;
		}
		

	}
	
}
