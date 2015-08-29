package com.symbol.app.mantoeye.service.sports.aggregatenum;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.symbol.app.mantoeye.dao.sports.aggregateNumDAO.AggregateNumDAO;
import com.symbol.app.mantoeye.dto.sports.CommonSport;
import com.symbol.app.mantoeye.entity.FtbMsisdnList;
import com.symbol.app.mantoeye.util.CommonUtils;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.orm.hibernate.EntityManager;

@Service
@Transactional
public class AggregateNumManager extends EntityManager<FtbMsisdnList, Long> {
	@Autowired
	private AggregateNumDAO aggregateNumDAO;

	public Page<CommonSport> query(final Page page, String startTime,
			String endTime, String phoneNumber,String CustomerName) {

		return aggregateNumDAO.query(page, startTime, endTime, phoneNumber,CustomerName);
	}

	public List<CommonSport> listData(String startTime, String endTime,
			String phoneNumber,String CustomerName) {
		return aggregateNumDAO.listData(startTime, endTime, phoneNumber,CustomerName);
	}

	public void saveTask(String vcNode,String vcMsisdn) {	

				
					String datetime= CommonUtils.getCurrentDate();
				 long Msisdns=	Common.StringToLong("86"+vcMsisdn);
				
				 aggregateNumDAO.savenew(vcNode,Msisdns, datetime);
			

			
		
	
	}
	public void deleteByKeys(String ids){
		aggregateNumDAO.deleteByKeys(ids);
	}
	
	public void saveModify(Integer id,String nmMsisdn,String CustomerName){
		String dtUpdateTime =CommonUtils.getCurrentDate();
//		int count = aggregateNumDAO.findByMsisdn(nmMsisdn);
//		if (count==0) {
			aggregateNumDAO.saveModify(id, nmMsisdn,dtUpdateTime,CustomerName);
//		}
	}
	
	public int FindByMsisdn(String msisdn){
		return aggregateNumDAO.findByMsisdn(msisdn);
	}

}
