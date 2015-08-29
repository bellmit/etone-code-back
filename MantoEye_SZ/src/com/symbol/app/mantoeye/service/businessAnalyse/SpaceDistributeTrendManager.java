package com.symbol.app.mantoeye.service.businessAnalyse;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.symbol.app.mantoeye.dao.businessAnalyse.SpaceDistributeTrendDao;
import com.symbol.app.mantoeye.dto.flush.CommonFlush;
import com.symbol.app.mantoeye.util.MantoEyeUtils;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.tools.gtgrid.model.SortInfo;
import com.symbol.wp.tools.gtgrid.server.GridServerHandler;

@Service
@SuppressWarnings("unchecked")
@Transactional(readOnly = true)
public class SpaceDistributeTrendManager {

	private SpaceDistributeTrendDao spaceDistributeTrendDao;

	public SpaceDistributeTrendDao getSpaceDistributeTrendDao() {
		return spaceDistributeTrendDao;
	}

	private Page page;

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	@Resource
	public void setSpaceDistributeTrendDao(
			SpaceDistributeTrendDao spaceDistributeTrendDao) {
		this.spaceDistributeTrendDao = spaceDistributeTrendDao;
	}

	public Page<CommonFlush> queryByPage(GridServerHandler gridServerHandler,
			String timeLevel, String areaType, String areaValue,
			String startTime, String endTime) {

		page = new Page();
		page.setPageSize(gridServerHandler.getPageSize());
		page.setPageNo(gridServerHandler.getPageInfo().getPageNum());

		String tableName = getTableName(Common.StringToInt(areaType), Common
				.StringToInt(timeLevel));
		String nameColumnName = getNameColumnName(Common.StringToInt(areaType));
		String nameColumnValue = areaValue;

		page.setTotalCount(spaceDistributeTrendDao.getTotalCount(Common
				.StringToInt(timeLevel), startTime, endTime, tableName,
				nameColumnName, nameColumnValue));

		int totalRowNum = gridServerHandler.getTotalRowNum();
		if (totalRowNum < 1) {
			totalRowNum = page.getTotalCount();
			gridServerHandler.setTotalRowNum(totalRowNum);
		}
		SortInfo si = gridServerHandler.getSingleSortInfo();
		if (si != null) {
			String order = si.getSortOrder();
			page.setOrder(order);
			page.setOrderBy(MantoEyeUtils.getSortField(si.getFieldName()));

		} else {
			page.setOrderBy("fullDate");
			page.setOrder("asc");
		}	

		return spaceDistributeTrendDao.queryByPage(page, Common.StringToInt(timeLevel), startTime,
				endTime, tableName, nameColumnName, nameColumnValue);
	}

	public List<CommonFlush> queryAll(String timeLevel, String areaType,
			String areaValue, String startTime, String endTime) {
		String tableName = getTableName(Common.StringToInt(areaType), Common
				.StringToInt(timeLevel));
		String nameColumnName = getNameColumnName(Common.StringToInt(areaType));
		String nameColumnValue = areaValue;

		List<CommonFlush> list = spaceDistributeTrendDao.queryAll(
				Common.StringToInt(timeLevel), startTime, endTime, tableName,
				nameColumnName, nameColumnValue);

		return list;
	}

	// private Map<String, String> getDateTimeKeyValue(String timeLevel,
	// String queryYear, String queryMonth, String queryDay,
	// String queryWeek, String queryHour) {
	// Map<String, String> map = new HashMap<String, String>();
	// switch (Common.StringToInt(timeLevel)) {
	// case 1:
	// map.put("queryYearStart", queryYear.split("-")[0]);
	// map.put("queryMonthStart", queryMonth.split("-")[0]);
	// map.put("queryDayStart", queryDay.split("-")[0]);
	// map.put("queryHourStart", queryHour.split("-")[0]);
	//
	// map.put("queryYearEnd", queryYear.split("-")[1]);
	// map.put("queryMonthEnd", queryMonth.split("-")[1]);
	// map.put("queryDayEnd", queryDay.split("-")[1]);
	// map.put("queryHourEnd", queryHour.split("-")[1]);
	// break;
	// case 2:
	// map.put("queryYearStart", queryYear.split("-")[0]);
	// map.put("queryMonthStart", queryMonth.split("-")[0]);
	// map.put("queryDayStart", queryDay.split("-")[0]);
	//
	// map.put("queryYearEnd", queryYear.split("-")[1]);
	// map.put("queryMonthEnd", queryMonth.split("-")[1]);
	// map.put("queryDayEnd", queryDay.split("-")[1]);
	// break;
	// case 3:
	// map.put("queryYearStart", queryYear.split("-")[0]);
	// map.put("queryWeekStart", queryWeek.split("-")[0]);
	//
	// map.put("queryYearEnd", queryYear.split("-")[1]);
	// map.put("queryWeekEnd", queryWeek.split("-")[1]);
	// break;
	// case 4:
	// map.put("queryYearStart", queryYear.split("-")[0]);
	// map.put("queryMonthStart", queryMonth.split("-")[0]);
	//
	// map.put("queryYearEnd", queryYear.split("-")[1]);
	// map.put("queryMonthEnd", queryMonth.split("-")[1]);
	// break;
	// }
	//
	// return map;
	// }

	private String getNameColumnName(int areaType) {
		String nameColumnName = "";

		switch (areaType) {
		case 1:
			// nameColumnName = "vcName";
			// break;
		case 2:
			// nameColumnName = "vcName";
			// break;
		case 3:
			nameColumnName = "vcName";
			break;
		case 4:
			nameColumnName = "vcSaleAreaName";
			break;
		case 5:
			nameColumnName = "vcBranchName";
			break;
		}

		return nameColumnName;
	}

	private String getTableName(int areaType, int timeLevel) {
		String tableName = "";

		switch (timeLevel) {
		case 1:
			tableName = getTableName(areaType, "Hour");
			break;
		case 2:
			tableName = getTableName(areaType, "Day");
			break;
		case 3:
			tableName = getTableName(areaType, "Week");
			break;
		case 4:
			tableName = getTableName(areaType, "Month");
			break;
		}

		return tableName;
	}

	private String getTableName(int areaType, String timeDate) {

		String tableName = "";

		switch (areaType) {
		case 1:
			tableName = "vStat" + timeDate + "BscGprsSpace";
			break;
		case 2:
			tableName = "vStat" + timeDate + "SgsnGprsSpace";
			break;
		case 3:
			tableName = "vStat" + timeDate + "StreetGprsSpace";
			break;
		case 4:
			tableName = "vStat" + timeDate + "SaleAreaGprsSpace";
			break;
		case 5:
			tableName = "vStat" + timeDate + "CompanyGprsSpace";
			break;
		}

		return tableName;
	}

//	private List<CommonFlush> buildNewBean(List<CommonFlush> list, int timeLevel) {
//		List<CommonFlush> newList = new ArrayList<CommonFlush>();
//
//		if (null != list && !list.isEmpty()) {
//			for (CommonFlush c : list) {
//				c.calculateData();
//				c.setSpanDate(timeLevel);
//				newList.add(c);
//			}
//		}
//
//		return newList;
//	}

}
