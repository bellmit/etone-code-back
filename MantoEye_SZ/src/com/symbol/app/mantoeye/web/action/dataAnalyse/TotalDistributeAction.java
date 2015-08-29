package com.symbol.app.mantoeye.web.action.dataAnalyse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;

import com.symbol.app.mantoeye.constants.ExportMsg;
import com.symbol.app.mantoeye.constants.SessionConstants;
import com.symbol.app.mantoeye.dto.flush.CommonFlush;
import com.symbol.app.mantoeye.service.businessAnalyse.TotalDistributeManager;
import com.symbol.app.mantoeye.util.CommonUtils;
import com.symbol.app.mantoeye.util.MantoEyeUtils;
import com.symbol.wp.core.constants.VarConstants;
import com.symbol.wp.core.service.ICommonManager;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.struts2.Struts2Utils;
import com.symbol.wp.modules.struts2.base.BaseDispatchAction;
import com.symbol.wp.tools.gtgrid.model.SortInfo;
import com.symbol.wp.tools.gtgrid.org.json.JSONArray;
import com.symbol.wp.tools.gtgrid.server.GridServerHandler;
import com.symbol.wp.tools.gtgrid.util.JSONUtils;

/**
 * 全网数据流量分布
 * 
 */

public class TotalDistributeAction extends BaseDispatchAction {

	@Autowired
	private TotalDistributeManager totalDistributeManager;
	@Resource(name = "commonManagerImpl")
	public ICommonManager commonManagerImpl;

	private Page<CommonFlush> page = new Page<CommonFlush>(
			VarConstants.PAGE_LONG_ROW_SIZE);

	private String dataType_search = "9";// 全网
	private String timeLevel_search = "1";// 天
	private String startTime_search = CommonUtils.getSYestoday() + " 00";// 开始时间
	private String endTime_search = CommonUtils.getSYestoday() + " 23";// 结束时间

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
			page.setOrder(order);
			page.setOrderBy(MantoEyeUtils.getSortField(si.getFieldName()));

		} else {
			page.setOrderBy("intYear");
			page.setOrder("asc");
		}
		page.setPageSize(gridServerHandler.getPageSize());
		page.setPageNo(gridServerHandler.getPageInfo().getPageNum());
		page = totalDistributeManager.query(page, Integer
				.parseInt(dataType_search), Common
				.StringToInt(timeLevel_search), startTime_search,
				endTime_search);
		int totalRowNum = gridServerHandler.getTotalRowNum();
		if (totalRowNum < 1) {
			totalRowNum = page.getTotalCount();
			gridServerHandler.setTotalRowNum(totalRowNum);
		}
		list = page.getResult();

		List<CommonFlush> listall = totalDistributeManager.listData(Integer
				.parseInt(dataType_search), Common
				.StringToInt(timeLevel_search), startTime_search,
				endTime_search);
		buildChartJson(listall);
		gridServerHandler.setData(list, CommonFlush.class);
		Struts2Utils.renderJson(gridServerHandler.getLoadResponseText());
	}

	private void buildChartJson(List<CommonFlush> list) {
		String chartJson = " ";
		try {
			if (list != null && list.size() > 0) {
			} else {
				list = new ArrayList<CommonFlush>();
			}
			JSONArray ja = JSONUtils
					.BeanList2JSONArray(list, CommonFlush.class);
			chartJson = ja.toString();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		this.getServletRequest().getSession().setAttribute(
				SessionConstants.SESSION_DATA_FLUSH_TOTAL, chartJson);
	}

	/**
	 * 获取图形数据
	 */
	public void getAjaxChartData() throws Exception {
		String chartJson = this.getServletRequest().getSession().getAttribute(
				SessionConstants.SESSION_DATA_FLUSH_TOTAL)
				+ "";
		Struts2Utils.renderJson(chartJson);
	}

	// /**
	// * 获取图形数据
	// */
	// public void getAjaxChartData() throws Exception {
	//
	// try {
	// List<CommonFlush> list = totalDistributeManager.listData(Integer
	// .parseInt(dataType_search), Common
	// .StringToInt(timeLevel_search), startTime_search,
	// endTime_search);
	// if (list != null && list.size() > 0) {
	// } else {
	// list = new ArrayList<CommonFlush>();
	// }
	// JSONArray ja = JSONUtils
	// .BeanList2JSONArray(list, CommonFlush.class);
	// Struts2Utils.renderJson(ja.toString());
	// } catch (Exception e) {
	// logger.error(e.getMessage());
	// }
	//
	// }
	//
	// /**
	// * 获取图形数据
	// */
	// public void getAjaxChartXmlData() throws Exception {
	// List<CommonFlush> list = null;
	//
	// list = totalDistributeManager.listData(Integer
	// .parseInt(dataType_search), Common
	// .StringToInt(timeLevel_search), startTime_search,
	// endTime_search);
	// String chartLabel = "";
	// if (Common.StringToInt(timeLevel_search) == 4) {
	// chartLabel = startTime_search.substring(0, startTime_search
	// .lastIndexOf("-"))
	// + " 至 "
	// + endTime_search.substring(0, endTime_search
	// .lastIndexOf("-"));
	// } else {
	// chartLabel = startTime_search + " 至 " + endTime_search;
	// }
	//
	// chartLabel = chartLabel + " 全网 流量|用户数 分布图";
	// String head =
	// "<root><chart id=\"1\" name=\"\" leftformat=\"true\" fields=\"流量|用户数\">";
	// String body = "";
	// String xml = "";
	// if (list != null && !list.isEmpty()) {
	// for (CommonFlush c : list) {
	// String shortLabel = "";
	// switch (Common.StringToInt(timeLevel_search)) {
	// case 1:
	// shortLabel = c.getIntHour() + "";
	// break;
	// case 2:
	// shortLabel = c.getIntDay() + "";
	// break;
	// case 3:
	// shortLabel = c.getIntWeek() + "";
	// break;
	// case 4:
	// shortLabel = c.getIntMonth() + "";
	// break;
	// }
	// body = body + "<data label=\"" + c.getFullDate()
	// + "\" shortlabel = \"" + shortLabel + "\" total=\""
	// + c.getTotalFlush() + "\" up=\"" + c.getIntUpFlush()
	// + "\" down=\"" + c.getIntDownFlush() + "\" imsis =\""
	// + c.getIntImsis() + "\"/>";
	// }
	// }
	// if (list != null && !list.isEmpty()) {
	// xml = "<root><chart id=\"1\" leftformat=\"true\" name=\""
	// + chartLabel + "\"  fields=\"流量|用户数\">" + body;
	// } else {
	// xml = head;
	// }
	//
	// xml = xml + "</chart></root>";
	// Struts2Utils.renderText(xml);
	//
	// }

	/**
	 * 数据导出
	 */
	public void export() throws ServletException, IOException {
		List<CommonFlush> list = null;
		list = totalDistributeManager.listData(Integer
				.parseInt(dataType_search), Common
				.StringToInt(timeLevel_search), startTime_search,
				endTime_search);
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		String exportmsg = ExportMsg.EXPORT_FLUSH_TOTAL
				+ Common.getTimeLevelCH(timeLevel_search) + "（"
				+ startTime_search + "~" + endTime_search + "）";
		commonManagerImpl.log(Struts2Utils.getRequest(), exportmsg);
		gridServerHandler.exportXLS(list, CommonFlush.class);
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

	public String getStartTime_search() {
		return startTime_search;
	}

	public void setStartTime_search(String startTime_search) {
		this.startTime_search = startTime_search;
	}

	public String getEndTime_search() {
		return endTime_search;
	}

	public void setEndTime_search(String endTime_search) {
		this.endTime_search = endTime_search;
	}

}
