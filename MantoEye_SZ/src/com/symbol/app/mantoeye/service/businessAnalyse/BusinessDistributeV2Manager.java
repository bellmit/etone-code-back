package com.symbol.app.mantoeye.service.businessAnalyse;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.symbol.app.mantoeye.dao.businessAnalyse.BusinessDistributeDAO;
import com.symbol.app.mantoeye.dao.businessAnalyse.BusinessDistributeV2DAO;
import com.symbol.app.mantoeye.dto.TopFlushBean;
import com.symbol.app.mantoeye.dto.flush.BussAndBussType;
import com.symbol.app.mantoeye.dto.flush.CommonFlush;
import com.symbol.app.mantoeye.util.SymbolUtils;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.orm.hibernate.EntityManager;

//Spring Service Bean的标识.
@Service
// 默认将类中的所有函数纳入事务管理.
@Transactional
public class BusinessDistributeV2Manager extends
		EntityManager<CommonFlush, String> {

	@Autowired
	private BusinessDistributeV2DAO businessDistributeV2DAO;
	@Autowired
	private BusinessDistributeDAO businessDistributeDAO;
	private Date ruleDate = Common.getDate("2012-4-9");
	private String searchDate = "";

	public List<CommonFlush> queryBussDistribute(int timeLevel, String date,
			int dataType, String businessType, String business, String orderBy,
			String order) {
		if (date.length() > 8) {
			searchDate = date.substring(0, 10);
		} else {
			searchDate = date + "-01";
		}
		Date dateStart = Common.getDate(searchDate);
		if (dateStart.before(ruleDate)) {
			businessDistributeDAO.initMap();
			return businessDistributeDAO.queryBusinessDistribute(timeLevel,
					date, dataType, businessType, business, orderBy, order);
		} else {
			businessDistributeV2DAO.initMap();
			return businessDistributeV2DAO.queryBusinessDistribute(timeLevel,
					date, dataType, businessType, business, orderBy, order);
		}

	}

	public String isExistLeaf(Long nmGroupId) {
		int num = businessDistributeV2DAO.isExistLeaf(nmGroupId);
		if (num == 0) {
			return "false";
		} else {
			return "true";
		}

	}

	/**
	 * 查询业务类型中的所有业务占比
	 * 
	 * @param dataType
	 * @param timeLevel
	 * @param date
	 * @param businessType
	 * @return
	 */
	public List<CommonFlush> queryBusinessDateByType(int dataType,
			int timeLevel, String date, String businessType, Long nmGroupId) {
		if (date.length() > 8) {
			searchDate = date.substring(0, 10);
		} else {
			searchDate = date + "-01";
		}
		Date dateStart = Common.getDate(searchDate);
		if (dateStart.before(ruleDate)) {
			businessDistributeDAO.initMap();
			return businessDistributeDAO.queryBusinessDateByType(dataType,
					timeLevel, date, businessType);
		} else {
			businessDistributeV2DAO.initMap();
			return businessDistributeV2DAO.queryBusinessDateByType(dataType,
					timeLevel, date, businessType, nmGroupId);
		}

	}

	public List<CommonFlush> queryBussContextByTypeExport(int dataType,
			int timeLevel, String date, String businessType, Long nmGroupId) {
		businessDistributeV2DAO.initMap();
		return businessDistributeV2DAO.queryBussContextByTypeExport(dataType,
				timeLevel, date, businessType, nmGroupId);
	}

	public Page<CommonFlush> queryBussDistributePage(Page<CommonFlush> page,
			int timeLevel, String date, int dataType, String businessType,
			String business) {
		if (date.length() > 8) {
			searchDate = date.substring(0, 10);
		} else {
			searchDate = date + "-01";
		}
		Date dateStart = Common.getDate(searchDate);
		if (dateStart.before(ruleDate)) {
			businessDistributeDAO.initMap();
			return businessDistributeDAO.queryBussDistributePage(page,
					timeLevel, date, dataType, businessType, business);
		} else {
			businessDistributeV2DAO.initMap();
			return businessDistributeV2DAO.queryBussDistributePage(page,
					timeLevel, date, dataType, businessType, business);
		}

	}

	/**
	 * 查询业务类型中的所有业务占比 含分页信息
	 * 
	 * @param page
	 * @param dataType
	 * @param timeLevel
	 * @param date
	 * @param businessType
	 * @return
	 */
	public Page<CommonFlush> queryBusinessDateByTypePage(Page page,
			int dataType, int timeLevel, String date, String businessType,
			Long nmGroupId) {
		if (date.length() > 8) {
			searchDate = date.substring(0, 10);
		} else {
			searchDate = date + "-01";
		}
		Date dateStart = Common.getDate(searchDate);
		if (dateStart.before(ruleDate)) {
			return businessDistributeDAO.queryBusinessDateByTypePage(page,
					dataType, timeLevel, date, businessType);
		} else {
			return businessDistributeV2DAO.queryBusinessDateByTypePage(page,
					dataType, timeLevel, date, businessType, nmGroupId);
		}

	}

	public Page<CommonFlush> queryBussContextByType(Page page, int dataType,
			int timeLevel, String date, String businessType, Long nmGroupId) {
		return businessDistributeV2DAO.queryBussContextByType(page, dataType,
				timeLevel, date, businessType, nmGroupId);
	}

	/**
	 * 分页查询
	 */
	public Page<CommonFlush> query(final Page page, int isTD, int areaType,
			int timeLevel, String time, String business) {
		return businessDistributeV2DAO.query(page, isTD, areaType, timeLevel,
				time, business);
	}

	public List<CommonFlush> listData(final Page page, int isTD, int areaType,
			int timeLevel, String time, String business) {
		return businessDistributeV2DAO.listData(page, isTD, areaType,
				timeLevel, time, business);
	}

	/**
	 * 组装业务选择控件 获取所以得业务类型和业务
	 */
	public Map<String, Map<String, String>> queryBussAndBussType() {
		Map<String, Map<String, String>> mapAll = new HashMap<String, Map<String, String>>();
		Map<String, String> bussMap = null;
		List<BussAndBussType> list = businessDistributeV2DAO
				.queryBussAndBussType();
		BussAndBussType bat = null;
		if (list != null && !list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				bat = list.get(i);
				String bussTypeNameAndId = SymbolUtils.getSaftDialogStr(bat
						.getBusinessTypeName()) + ":" + bat.getBusinessTypeId();// 业务类型名，业务ID组装成主键一起传上应用层！
				if (mapAll.get(bussTypeNameAndId) == null) {
					bussMap = new HashMap<String, String>();
					bussMap.put(bat.getBusinessName(), bat.getBusinessTypeId()
							+ ":" + bat.getBusinessId());
					mapAll.put(bussTypeNameAndId, bussMap);
				} else {
					mapAll.get(bussTypeNameAndId)
							.put(SymbolUtils.getSaftDialogStr(bat
									.getBusinessName()),
									bat.getBusinessTypeId() + ":"
											+ bat.getBusinessId());
				}
			}
		}
		return mapAll;
	}

	/**
	 * 根据选择的任务ID 组装业务选择控件 获取所以得业务类型和业务
	 */
	public Map<String, Map<String, String>> queryBussAndBussTypeByTaskId(
			int taskId) {
		Map<String, Map<String, String>> mapAll = new HashMap<String, Map<String, String>>();
		Map<String, String> bussMap = null;
		List<BussAndBussType> list = businessDistributeV2DAO
				.queryBussAndBussTypeByTaskId(taskId);
		BussAndBussType bat = null;
		if (list != null && !list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				bat = list.get(i);
				String bussTypeNameAndId = SymbolUtils.getSaftDialogStr(bat
						.getBusinessTypeName()) + ":" + bat.getBusinessTypeId();// 业务类型名，业务ID组装成主键一起传上应用层！
				if (mapAll.get(bussTypeNameAndId) == null) {
					bussMap = new HashMap<String, String>();
					bussMap.put(bat.getBusinessName(), bat.getBusinessTypeId()
							+ ":" + bat.getBusinessId());
					mapAll.put(bussTypeNameAndId, bussMap);
				} else {
					mapAll.get(bussTypeNameAndId)
							.put(SymbolUtils.getSaftDialogStr(bat
									.getBusinessName()),
									bat.getBusinessTypeId() + ":"
											+ bat.getBusinessId());
				}
			}
		}
		return mapAll;
	}

	/**
	 * 根据选择的任务ID 组装业务对应的业务ID和业务类型ID
	 */
	public String queryBussIdAndBussIdTypeByTaskId(int taskId) {
		List<BussAndBussType> list = businessDistributeV2DAO
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
		}
		bisIdAndBisTypeId = bisIdAndBisTypeId.substring(0,
				bisIdAndBisTypeId.length() - 1);
		businessName = businessName.substring(0, businessName.length() - 1);
		return bisIdAndBisTypeId + "@" + businessName;

	}

	// public List<TopFlushBean> queryTop10Busi(String startTime, String
	// endTime,
	// String type) {
	//
	// List<TopFlushBean> list = null;
	//
	// if ("total".equals(type)) {
	// list = businessDistributeV2DAO.queryTotalFlush(startTime, endTime,
	// type);
	// } else {
	// list = businessDistributeV2DAO.queryTop10Busi(
	// Common.getDate(startTime), type);
	// }
	// return list;
	// }

	private String getEndTime(String date) {
		String endTime = date + "-30";
		String month = date.split("-")[1];

		if (!"10".equals(month) && !"12".equals(month) && !"11".equals(month)) {
			month = month.substring(1);
		}

		int m = Integer.parseInt(month);

		switch (m) {
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			endTime = date + "-31";
			break;
		case 2:
			int y = Integer.parseInt(date.split("-")[0]);
			if (y % 400 == 0 || (y % 4 == 0 && y % 100 != 0)) {
				endTime = date + "-29";
			} else {
				endTime = date + "-28";
			}
			break;
		}

		return endTime;
	}

	// public List<TopFlushBean> queryHourDataByName(String date, String edate,
	// String busiName) {
	//
	// List<TopFlushBean> list = null;
	//
	// if ("全网".equals(busiName)) {
	// String startTime = date + " 00:00:00";
	// String endTime = date + " 23:59:59";
	// list = businessDistributeV2DAO.queryTotalFlush(startTime, endTime,
	// "totalTrend");
	// } else if ("flush".equals(busiName.trim())
	// || "wap".equals(busiName.trim())
	// || "cmcc".equals(busiName.trim())) {
	// TopFlushBean bean = getBean(date, busiName);
	// String sdate = date + " 00:00:00";
	// String eedate = date + " 23:59:59";
	// if (null != bean && !"".equals(bean.getName())
	// && null != bean.getName()) {
	// list = businessDistributeV2DAO.queryHourDataByName(sdate, eedate,
	// bean.getName());
	// } else {
	// list = new ArrayList<TopFlushBean>();
	// }
	// } else {
	// list = businessDistributeV2DAO.queryHourDataByName(date, edate,
	// busiName);
	// }
	//
	// return list;
	// }

	// public TopFlushBean getBean(String date, String busiName) {
	// TopFlushBean bean = businessDistributeV2DAO.queryTop10Busi2(Common
	// .getDate(date), busiName);
	//
	// return bean;
	// }

	public List<TopFlushBean> querySpaceDataByName(String date,
			String busiName, int spaceType) {
		String tableName = "vStatHourBscBussType";
		String spaceName = "vcName";
		switch (spaceType) {
		case 1: {
			tableName = "vStatHourBscBussType";
			break;
		}
		case 2: {
			tableName = "vStatHourSgsnBussType";
			break;
		}
		case 3: {
			tableName = "vStatHourStreetBussType";
			break;
		}
		case 4: {
			tableName = "vStatHourSaleAreaBussType";
			spaceName = "vcSaleAreaName";
			break;
		}
		case 5: {
			tableName = "vStatHourCompanyBussType";
			spaceName = "vcBranchName";
			break;
		}
		}
		List<TopFlushBean> list = null;

		if ("全网".equals(busiName)) {
			switch (spaceType) {
			case 1: {
				tableName = "vStatDayBscGprsSpace";
				break;
			}
			case 2: {
				tableName = "vStatDaySgsnGprsSpace";
				break;
			}
			case 3: {
				tableName = "vStatDayStreetGprsSpace";
				break;
			}
			case 4: {
				tableName = "vStatDaySaleAreaGprsSpace";
				spaceName = "vcSaleAreaName";
				break;
			}
			case 5: {
				tableName = "vStatDayCompanyGprsSpace";
				spaceName = "vcBranchName";
				break;
			}
			}

			list = businessDistributeV2DAO.queryTotalFlush(date, tableName,
					"totalSpace");
		} else {
			list = businessDistributeV2DAO.querySpaceDataByName(
					Common.getDate(date), busiName, tableName, spaceName);
		}

		return list;
	}

	// public List<TopFlushBean> querySpaceDataByName2(String date,
	// String busiName, int spaceType) {
	// String tableName = "vStatHourBscBussType";
	// String spaceName = "vcName";
	// switch (spaceType) {
	// case 1: {
	// tableName = "vStatHourBscBussType";
	// break;
	// }
	// case 2: {
	// tableName = "vStatHourSgsnBussType";
	// break;
	// }
	// case 3: {
	// tableName = "vStatHourStreetBussType";
	// break;
	// }
	// case 4: {
	// tableName = "vStatHourSaleAreaBussType";
	// spaceName = "vcSaleAreaName";
	// break;
	// }
	// case 5: {
	// tableName = "vStatHourCompanyBussType";
	// spaceName = "vcBranchName";
	// break;
	// }
	// }
	// List<TopFlushBean> list = null;
	//
	// if ("全网".equals(busiName)) {
	// switch (spaceType) {
	// case 1: {
	// tableName = "vStatDayBscGprsSpace";
	// break;
	// }
	// case 2: {
	// tableName = "vStatDaySgsnGprsSpace";
	// break;
	// }
	// case 3: {
	// tableName = "vStatDayStreetGprsSpace";
	// break;
	// }
	// case 4: {
	// tableName = "vStatDaySaleAreaGprsSpace";
	// spaceName = "vcSaleAreaName";
	// break;
	// }
	// case 5: {
	// tableName = "vStatDayCompanyGprsSpace";
	// spaceName = "vcBranchName";
	// break;
	// }
	// }
	//
	// list = businessDistributeV2DAO.queryTotalFlush(date, tableName,
	// "totalSpace");
	// } else if ("flush".equals(busiName.trim())
	// || "wap".equals(busiName.trim())
	// || "cmcc".equals(busiName.trim())) {
	// TopFlushBean bean = getBean(date, busiName);
	// String sdate = date + " 00:00:00";
	// String eedate = date + " 23:59:59";
	// if (null != bean && !"".equals(bean.getName())
	// && null != bean.getName()) {
	// list = businessDistributeV2DAO.querySpaceDataByName(Common
	// .getDate(date), bean.getName(), tableName, spaceName);
	// } else {
	// list = new ArrayList<TopFlushBean>();
	// }
	// } else {
	// list = businessDistributeV2DAO.querySpaceDataByName(Common
	// .getDate(date), busiName, tableName, spaceName);
	// }
	//
	// return list;
	// }
}
