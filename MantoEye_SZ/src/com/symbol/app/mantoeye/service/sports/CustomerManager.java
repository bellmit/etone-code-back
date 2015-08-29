package com.symbol.app.mantoeye.service.sports;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.symbol.app.mantoeye.dao.sports.CustomerDAO;
import com.symbol.app.mantoeye.dto.sports.CommonSport;
import com.symbol.wp.modules.orm.Page;

@Service
@Transactional
public class CustomerManager {
	@Autowired
	private CustomerDAO customerDAO;

	public Page<CommonSport> query(final Page page, int timeLevel,
			String startTime, String endTime, String phoneNumber,
			String CustomerName) {

		return customerDAO.query(page, timeLevel, startTime, endTime,
				phoneNumber, CustomerName);
	}

	public void saveUpFile(Map<String, String> mapdata) {
		List<String> Msisdns = new ArrayList<String>(mapdata.keySet());
		List<String> list = customerDAO.getAllMsisdn();
		if (list.size() > 0) {
			Object[] objects = list.toArray();
			for (int i = 0; i < objects.length; i++) {
				String number = objects[i].toString();
				boolean result = Msisdns.contains(number);
				if (result) {
					Msisdns.remove(number);
					mapdata.remove(number);
				}
			}
		}
	

		for (int i = 0; i < Msisdns.size(); i++) {
			String Msisdn = Msisdns.get(i);
			String SaveVcNode = mapdata.get(Msisdn);
			customerDAO.saveUpFile(SaveVcNode,Msisdn);
		}

	}

	public List<CommonSport> listData(int timeLevel, String startTime,
			String endTime, String phoneNumber, String CustomerName) {
		return customerDAO.listData(timeLevel, startTime, endTime, phoneNumber,
				CustomerName);
	}

}
