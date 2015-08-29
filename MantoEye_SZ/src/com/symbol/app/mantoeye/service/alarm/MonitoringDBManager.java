package com.symbol.app.mantoeye.service.alarm;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.symbol.app.mantoeye.bean.MonitoringBean;
import com.symbol.app.mantoeye.dao.alarm.MonitoringDBDao;
import com.symbol.app.mantoeye.util.CommonUtils;

@Service
@Transactional(readOnly = true)
public class MonitoringDBManager {

	private MonitoringDBDao monitoringDBDao;

	public MonitoringBean initQuery(String currentDate) {
		MonitoringBean bean = monitoringDBDao.initQuery(currentDate);

		bean = buildNewBean(bean);

		return bean;
	}

	private MonitoringBean buildNewBean(MonitoringBean bean) {

		if (null == bean) {
			return null;
		}

		bean.setGB();

		return bean;
	}

	public MonitoringDBDao getMonitoringDBDao() {
		return monitoringDBDao;
	}

	@Resource
	public void setMonitoringDBDao(MonitoringDBDao monitoringDBDao) {
		this.monitoringDBDao = monitoringDBDao;
	}
}
