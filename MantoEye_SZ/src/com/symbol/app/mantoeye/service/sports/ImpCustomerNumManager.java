package com.symbol.app.mantoeye.service.sports;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.symbol.app.mantoeye.dao.sports.ImpCustomerNumDAO;
import com.symbol.app.mantoeye.dto.sports.CommonSport;
import com.symbol.app.mantoeye.entity.FtbMsisdnList;
import com.symbol.app.mantoeye.util.CommonUtils;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.orm.hibernate.EntityManager;


@Service
@Transactional
public class ImpCustomerNumManager extends EntityManager<FtbMsisdnList,Long> {
	@Autowired
	private ImpCustomerNumDAO impCustomerNumDAO;
	/**
	 * 分页查询
	 */
	public Page<CommonSport> query(final Page page,
			String sTime, String eTime,String nmMsisdn) {
		return impCustomerNumDAO.query(page, sTime, eTime, nmMsisdn);
	}

	public List<CommonSport> listData(String startTime, String endTime,String nmMsisdn) {
		return impCustomerNumDAO.listData(startTime, endTime, nmMsisdn);
	}
	
	public void saveNmMsisdn(String nmMsisdn) {
		String[] s = nmMsisdn.split(",");
		int intType=1;
		if (s != null && s.length > 0) {
			for (int i = 0; i < s.length; i++) {
				String number = "86"+s[i];
				boolean flag = nmMsisdnUnique(number);
				if (flag) {
					impCustomerNumDAO.saveNmMsisdn(Long.parseLong(number), CommonUtils.getCurrentDate(), intType);
				}
			}
		}
	}
	
	public void deleteById(String ids) {
		impCustomerNumDAO.deleteById(ids);
	}
	
	public void updateNmMsisdn(Long id,Long nmMsisdn) {
		String dtUpdateTime = CommonUtils.getCurrentDate();
		impCustomerNumDAO.updateNmMsisdn(id, nmMsisdn, dtUpdateTime);
	}
	
	public boolean nmMsisdnUnique(String number){
		boolean flag = impCustomerNumDAO.nmMsisdnUnique(number);
		return flag;
	}
	
}
