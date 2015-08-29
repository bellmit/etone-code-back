package com.symbol.app.mantoeye.service.businessAnalyse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.symbol.app.mantoeye.dao.businessAnalyse.AreasBusinessTrendDao;
import com.symbol.app.mantoeye.dto.flush.BussAndBussType;
import com.symbol.app.mantoeye.dto.flush.CommonFlush;
import com.symbol.app.mantoeye.util.MantoEyeUtils;
import com.symbol.app.mantoeye.util.SymbolUtils;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.tools.gtgrid.model.SortInfo;
import com.symbol.wp.tools.gtgrid.server.GridServerHandler;

@Service
@Transactional
public class AreasBusinessTrendManager {
	@Autowired
	private AreasBusinessTrendDao areasBusinessTrendDao;

	private Page page;

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public Page<CommonFlush> queryByPage(GridServerHandler gridServerHandler,
			String timeLevel, String areaType, String areaValue,
			String startTime, String endTime) {

		page = new Page();
		page.setPageSize(gridServerHandler.getPageSize());
		page.setPageNo(gridServerHandler.getPageInfo().getPageNum());

		String tableName = getTableName(Common
				.StringToInt(timeLevel));

		page.setTotalCount(areasBusinessTrendDao.getTotalCount(Common
				.StringToInt(timeLevel), startTime, endTime, tableName,
				areaValue));

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

		return areasBusinessTrendDao.queryByPage(page, Common.StringToInt(timeLevel), startTime,
				endTime, tableName, areaValue);
	}

	public List<CommonFlush> queryAll(String timeLevel, String areaType,
			String areaValue, String startTime, String endTime) {
		String tableName = getTableName(Common
				.StringToInt(timeLevel));

		List<CommonFlush> list = areasBusinessTrendDao.queryAll(
				Common.StringToInt(timeLevel), startTime, endTime, tableName,
				areaValue);

		return list;
	}


	private String getTableName(int timeLevel) {
		String tableName = "";

		switch (timeLevel) {
		case 1:
			tableName = "vStatHourGroupAreaGprsSpace";
			break;
		case 2:
			tableName = "vStatDayGroupAreaGprsSpace";
			break;
		case 4:
			tableName = "vStatMonthGroupAreaGprsSpace";
			break;
		}

		return tableName;
	}


	public Map<String, Map<String, String>> bulidAreaAndType() {
		Map<String, Map<String, String>> mapAll = new HashMap<String, Map<String, String>>();
		Map<String, String> bussMap = null;
		System.out.println("service---------111111111111");
		List<BussAndBussType> list = areasBusinessTrendDao.queryBussAndBussType();
		System.out.println("service---------2222222222222");
		BussAndBussType bat = null;
		if (list != null && !list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				bat = list.get(i);
				String bussTypeNameAndId = SymbolUtils.getSaftDialogStr(bat
						.getBusinessTypeName())
						+ ":" + bat.getBusinessTypeId();// 业务类型名，业务ID组装成主键一起传上应用层！
				if (mapAll.get(bussTypeNameAndId) == null) {
					bussMap = new HashMap<String, String>();
					bussMap.put(bat.getBusinessName(), bat.getBusinessTypeId()
							+ ":" + bat.getBusinessId());
					mapAll.put(bussTypeNameAndId, bussMap);
				} else {
					mapAll.get(bussTypeNameAndId)
							.put(
									SymbolUtils.getSaftDialogStr(bat
											.getBusinessName()),
									bat.getBusinessTypeId() + ":"
											+ bat.getBusinessId());
				}
			}
		}
		return mapAll;
	}
}
