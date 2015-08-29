package com.symbol.app.mantoeye.service.alarm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.symbol.app.mantoeye.dao.alarm.AlarmLimitConfigDao;
import com.symbol.app.mantoeye.dto.alarm.AlarmLimitBean;

@Service
@Transactional
public class AlarmLimitConfigManager {

	private AlarmLimitConfigDao alarmLimitConfigDao;

	public AlarmLimitConfigDao getAlarmLimitConfigDao() {
		return alarmLimitConfigDao;
	}

	@Resource
	public void setAlarmLimitConfigDao(AlarmLimitConfigDao alarmLimitConfigDao) {
		this.alarmLimitConfigDao = alarmLimitConfigDao;
	}

	public List<AlarmLimitBean> initQuery(String type) {

		String alarmTypeLow = " ";
		String alarmTypeDerease = " ";
		String alarmTypeInrease = " ";

		if ("CGI".equals(type.trim())) {
			alarmTypeLow = "1";
			alarmTypeDerease = "2";
			alarmTypeInrease = "3";
		} else if ("BSC".equals(type.trim())) {
			alarmTypeLow = "4";
			alarmTypeDerease = "5";
			alarmTypeInrease = "6";
		} else if ("TD".equals(type.trim())) {
			alarmTypeLow = "11";
			alarmTypeDerease = "13";
			alarmTypeInrease = "12";
		} else if ("GPRS".equals(type.trim())) {
			alarmTypeLow = "14";
			alarmTypeDerease = "16";
			alarmTypeInrease = "15";
		} else {
			alarmTypeLow = "8";
			alarmTypeDerease = "9";
			alarmTypeInrease = "10";
		}

		Map<String, Object> condition = new HashMap<String, Object>();

		condition.put("alarmTypeLow", alarmTypeLow);
		condition.put("alarmTypeDerease", alarmTypeDerease);
		condition.put("alarmTypeInrease", alarmTypeInrease);
		condition.put("type", type);

		List<AlarmLimitBean> list = alarmLimitConfigDao
				.queryAlarmLimit(condition);

		list = buildNewList(list);

		return list;

	}

	private List<AlarmLimitBean> buildNewList(List<AlarmLimitBean> list) {
		List<AlarmLimitBean> nList = new ArrayList<AlarmLimitBean>();

		if (null != list && !list.isEmpty()) {
			for (AlarmLimitBean b : list) {
				String alarmLimit = b.getAlarmLimit();
				if ("1".equals(b.getAlarmType())
						|| "4".equals(b.getAlarmType())
						|| "8".equals(b.getAlarmType())
						|| "11".equals(b.getAlarmType())
						|| "14".equals(b.getAlarmType())) {
					alarmLimit = b.getAlarmLimit();
					if (alarmLimit.contains(".")) {
						alarmLimit = alarmLimit.substring(0,
								alarmLimit.lastIndexOf("."));
						Long al = Long.parseLong(alarmLimit);
						al /= 1024;
						alarmLimit = String.valueOf(al);
					}
				} else {
					alarmLimit = b.getAlarmLimit();
					Double al = Double.parseDouble(alarmLimit);
					al *= 100;
					alarmLimit = String.valueOf(al);
				}
				b.setAlarmLimit(alarmLimit);
				nList.add(b);
			}
		}

		return nList;
	}

	public void updateAlarm(String type, String alarmType, String alarmLimit,
			String timeLevel, String operator) {

		String[] alarmTypeArr = alarmType.split(",");
		String[] alarmLimitArr = alarmLimit.split(",");
		String[] timeLevelArr = timeLevel.split(",");
		String[] operatorArr = operator.split(",");

		Map<String, Object> condition = null;

		for (int i = 0; i < 3; i++) {
			alarmType = alarmTypeArr[i].trim();
			alarmLimit = alarmLimitArr[i].trim();
			timeLevel = timeLevelArr[i].trim();
			operator = operatorArr[i].trim();

			if ("小时".equals(timeLevel)) {
				timeLevel = "1";
			} else if ("天".equals(timeLevel)) {
				timeLevel = "2";
			} else if ("周".equals(timeLevel)) {
				timeLevel = "3";
			} else {
				timeLevel = "4";
			}

			if ("大于".equals(operator)) {
				operator = ">";
			} else if ("小于".equals(operator)) {
				operator = "<";
			} else if ("等于".equals(operator)) {
				operator = "=";
			}

			condition = new HashMap<String, Object>();

			condition.put("alarmType", alarmType.trim());
			condition.put("operator", operator.trim());
			condition.put("alarmLimit", alarmLimit.trim());
			condition.put("timeLevel", timeLevel.trim());

			int count = alarmLimitConfigDao.findAlarmByType(alarmType);

			if (0 == count) {
				count = alarmLimitConfigDao.findAll();
				if (0 == count) {
					alarmLimitConfigDao.addAlarmLimitOne(condition);
				} else {
					alarmLimitConfigDao.addAlarmLimit(condition);
				}
			} else {
				alarmLimitConfigDao.updateAlarmLimit(condition);
			}
		}

	}

	public void updateAlarm2(String type, String alarmType, String alarmLimit,
			String timeLevel, String operator) {
		String[] alarmTypeArr = alarmType.split(",");
		String[] alarmLimitArr = alarmLimit.split(",");
		String[] timeLevelArr = timeLevel.split(",");
		String[] operatorArr = operator.split(",");

		Map<String, Object> condition = null;

		for (int i = 0; i < 3; i++) {
			alarmType = alarmTypeArr[i].trim();
			alarmLimit = alarmLimitArr[i].trim();
			Double ddd = Double.parseDouble(alarmLimit);
			if (0 == i) {
				ddd *= 1024;
			} else {
				ddd /= 100;
			}
			alarmLimit = String.valueOf(ddd);
			timeLevel = timeLevelArr[i].trim();
			operator = operatorArr[i].trim();

			if ("小时".equals(timeLevel)) {
				timeLevel = "1";
			} else if ("天".equals(timeLevel)) {
				timeLevel = "2";
			} else if ("周".equals(timeLevel)) {
				timeLevel = "3";
			} else {
				timeLevel = "4";
			}

			if ("大于".equals(operator)) {
				operator = ">";
			} else if ("小于".equals(operator)) {
				operator = "<";
			} else if ("等于".equals(operator)) {
				operator = "=";
			}

			condition = new HashMap<String, Object>();

			condition.put("alarmType", alarmType.trim());
			condition.put("operator", operator.trim());
			condition.put("alarmLimit", alarmLimit.trim());
			condition.put("timeLevel", timeLevel.trim());

			int count = alarmLimitConfigDao.findAlarmByType(alarmType);

			if (0 == count) {
				count = alarmLimitConfigDao.findAll();
				if (0 == count) {
					alarmLimitConfigDao.addAlarmLimitOne(condition);
				} else {
					alarmLimitConfigDao.addAlarmLimit(condition);
				}
			} else {
				alarmLimitConfigDao.updateAlarmLimit(condition);
			}
		}

	}
}
