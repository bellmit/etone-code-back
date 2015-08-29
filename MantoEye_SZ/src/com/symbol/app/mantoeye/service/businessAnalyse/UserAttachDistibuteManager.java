package com.symbol.app.mantoeye.service.businessAnalyse;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.symbol.app.mantoeye.dao.businessAnalyse.UserAttachDistributeDAO;
import com.symbol.app.mantoeye.dto.flush.CommonFlush;

//Spring Service Bean的标识.
@Service
// 默认将类中的所有函数纳入事务管理.
@Transactional
public class UserAttachDistibuteManager {

	@Autowired
	private UserAttachDistributeDAO userAttachDistributeDAO;
	
	public List<CommonFlush> queryUserAttachDistribute(int timeLevel,String date,int dataType){
		userAttachDistributeDAO.initMap();
		return userAttachDistributeDAO.queryUserAttachDistributeDAO(timeLevel, date, dataType);
	}
}
