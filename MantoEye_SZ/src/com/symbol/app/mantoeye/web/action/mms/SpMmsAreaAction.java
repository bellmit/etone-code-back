package com.symbol.app.mantoeye.web.action.mms;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;

import com.symbol.app.mantoeye.constants.CommonConstants;
import com.symbol.app.mantoeye.constants.ExportMsg;
import com.symbol.app.mantoeye.dto.flush.CommonFlush;
import com.symbol.app.mantoeye.service.mms.SpMmsAreaManager;
import com.symbol.app.mantoeye.util.CommonUtils;
import com.symbol.app.mantoeye.util.DataFormatUtils;
import com.symbol.app.mantoeye.util.MantoEyeUtils;
import com.symbol.wp.core.constants.VarConstants;
import com.symbol.wp.core.service.ICommonManager;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.struts2.Struts2Utils;
import com.symbol.wp.modules.struts2.base.BaseDispatchAction;
import com.symbol.wp.tools.gtgrid.model.SortInfo;
import com.symbol.wp.tools.gtgrid.server.GridServerHandler;

public class SpMmsAreaAction extends BaseDispatchAction {

	@Autowired
	private SpMmsAreaManager spMmsAreaManager;
	@Resource(name = "commonManagerImpl")
	public ICommonManager commonManagerImpl;

	private Page<CommonFlush> page = new Page<CommonFlush>(
			VarConstants.PAGE_LONG_ROW_SIZE);
	private String dataType_search = "1";// 默认GPRS
	private String timeLevel_search = "2";// 默认天
	private String spaceLevel_search = "1";// 默认区域维度BSC
	private String time_search = CommonUtils.getSYestoday();// 默认昨天

	/**
	 * 查询数据
	 */
	public void query() throws ServletException, IOException {
		List<CommonFlush> list = null;
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		SortInfo si = gridServerHandler.getSingleSortInfo();
		if (si != null) {
			String order = si.getSortOrder();
			if ("defaultsort".equals(order)) {
				page.setOrder("desc");
				page.setOrderBy("nmCounts");
			} else {
				page.setOrder(order);
				page.setOrderBy(MantoEyeUtils.getSpMmsSortField(si
						.getFieldName()));
			}
			// 默认排序方式
		} else {
			page.setOrder("desc");
			page.setOrderBy("nmCounts");
		}
		page.setPageSize(gridServerHandler.getPageSize());
		page.setPageNo(gridServerHandler.getPageInfo().getPageNum());
		page = spMmsAreaManager.query(page,
				Common.StringToInt(dataType_search), Common
						.StringToInt(spaceLevel_search), Integer
						.parseInt(timeLevel_search), time_search);
		int totalRowNum = gridServerHandler.getTotalRowNum();
		if (totalRowNum < 1) {
			totalRowNum = page.getTotalCount();
			gridServerHandler.setTotalRowNum(totalRowNum);
		}
		list = page.getResult();
		gridServerHandler.setData(list, CommonFlush.class);
		Struts2Utils.renderJson(gridServerHandler.getLoadResponseText());
	}

	/**
	 * 获取图形数据
	 */
	public void getAjaxChartXmlData() throws Exception {
		List<CommonFlush> list = spMmsAreaManager.listData(Integer
				.parseInt(dataType_search), Common
				.StringToInt(spaceLevel_search), Integer
				.parseInt(timeLevel_search), time_search);
		int chaertType = Common.StringToInt(spaceLevel_search);
		String name = "";
		switch (chaertType) {
		case CommonConstants.MANTOEYE_SPACE_LEVEL_BSC:
			name = "BSC";
			break;
		case CommonConstants.MANTOEYE_SPACE_LEVEL_SGSN:
			name = "SGSN";
			break;
		case CommonConstants.MANTOEYE_SPACE_LEVEL_STREET:
			name = "街道办";
			break;
		case CommonConstants.MANTOEYE_SPACE_LEVEL_SALEAREA:
			name = "营销片区";
			break;
		case CommonConstants.MANTOEYE_SPACE_LEVEL_BRANCH:
			name = "分公司";
			break;
		}
		String time = time_search;
		if (Common.StringToInt(timeLevel_search) == 3) {
			time = CommonUtils.getDayInnerWeek(time_search);
		}
		String chartTitle = "\"" + time + " " + name + "{dataname}分布\"";

		// 去掉名称为其他的数据
		list = DataFormatUtils.exceptCommonFlush(list, "其他");
		// 根据数量级获取流量的单位，防止数量太大
		String sendUnit = DataFormatUtils.getSendUnit(list);
		String xml = "<?xml version=\"1.0\" encoding=\"gb2312\"?>" + "<Data>"
				+ "<Stat id=\"\" unit=\"" + sendUnit + "\" tipunit=\"\" title="
				+ chartTitle + " trip=\"彩信发送量{br}" + name
				+ "：{name}{br}发送量：{total}" + sendUnit + "\">";
		if (list != null && !list.isEmpty()) {
			for (CommonFlush c : list) {
				xml = xml + "<Statdata name=\"" + c.getBusinessName()
						+ "\" total=\""
						+ DataFormatUtils.getIVByUnit(c, sendUnit, "send")
						+ "\"/>";
			}
		}

		// String xml = "<?xml version=\"1.0\" encoding=\"gb2312\"?>" + "<Data>"
		// + "<Stat id=\"\" unit=\"\" title=" + chartTitle
		// + " trip=\"彩信发送量{br}" + name + "：{name}{br}发送量：{total}\">";
		// if (list != null && !list.isEmpty()) {
		// for (CommonFlush c : list) {
		// xml = xml + "<Statdata name=\"" + c.getBusinessName()
		// + "\" total=\"" + c.getTotalSendFlush() + "\"/>";
		// }
		// }
		xml = xml + "</Stat>" + "</Data>";
		logger.info(xml);
		Struts2Utils.renderText(xml);
	}

	/**
	 * 数据导出
	 */
	public void export() throws ServletException, IOException {
		List<CommonFlush> list = spMmsAreaManager.listData(Integer
				.parseInt(dataType_search), Common
				.StringToInt(spaceLevel_search), Common
				.StringToInt(timeLevel_search), time_search);
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		String exportmsg = ExportMsg.EXPORT_MMS_SPAREA
				+ Common.getTimeLevelCH(timeLevel_search) + "（" + time_search
				+ "）";
		commonManagerImpl.log(Struts2Utils.getRequest(), exportmsg);
		gridServerHandler.exportXLS(list, CommonFlush.class);
	}

	public SpMmsAreaManager getSpMmsAreaManager() {
		return spMmsAreaManager;
	}

	public void setSpMmsAreaManager(SpMmsAreaManager spMmsAreaManager) {
		this.spMmsAreaManager = spMmsAreaManager;
	}

	public Page<CommonFlush> getPage() {
		return page;
	}

	public void setPage(Page<CommonFlush> page) {
		this.page = page;
	}

	public String getDataType_search() {
		return dataType_search;
	}

	public void setDataType_search(String dataType_search) {
		this.dataType_search = dataType_search;
	}

	public String getTimeLevel_search() {
		return timeLevel_search;
	}

	public void setTimeLevel_search(String timeLevel_search) {
		this.timeLevel_search = timeLevel_search;
	}

	public String getSpaceLevel_search() {
		return spaceLevel_search;
	}

	public void setSpaceLevel_search(String spaceLevel_search) {
		this.spaceLevel_search = spaceLevel_search;
	}

	public String getTime_search() {
		return time_search;
	}

	public void setTime_search(String time_search) {
		this.time_search = time_search;
	}
}
