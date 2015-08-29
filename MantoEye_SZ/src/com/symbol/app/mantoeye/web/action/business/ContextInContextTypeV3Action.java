package com.symbol.app.mantoeye.web.action.business;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.symbol.app.mantoeye.constants.ExportMsg;
import com.symbol.app.mantoeye.dto.flush.CommonFlush;
import com.symbol.app.mantoeye.service.businessAnalyse.BusinessDistributeManager;
import com.symbol.app.mantoeye.service.businessAnalyse.BusinessDistributeV2Manager;
import com.symbol.app.mantoeye.service.businessAnalyse.ContextDistributeManager;
import com.symbol.app.mantoeye.util.CommonUtils;
import com.symbol.app.mantoeye.util.MantoEyeUtils;
import com.symbol.wp.core.constants.VarConstants;
import com.symbol.wp.core.service.ICommonManager;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.struts2.Struts2Utils;
import com.symbol.wp.modules.struts2.base.BaseDispatchAction;
import com.symbol.wp.tools.gtgrid.model.SortInfo;
import com.symbol.wp.tools.gtgrid.server.GridServerHandler;

public class ContextInContextTypeV3Action extends BaseDispatchAction {

	@Autowired
	private ContextDistributeManager contextDistributeManager;
	@Resource(name = "commonManagerImpl")
	public ICommonManager commonManagerImpl;
	HttpServletRequest request = Struts2Utils.getRequest();
	private Page<CommonFlush> page = new Page<CommonFlush>(
			VarConstants.PAGE_LONG_ROW_SIZE);
	private String businessTypeName;// 业务类型名称
	private String searchDateStart = CommonUtils.getSYestoday();// 查询时间
	private int dataType = 1;	//默认查TD的数据
	private String timeLevel_search = "2";// 时间周期 2:天

	/**
	 * 数据导出
	 */
	public void export() throws ServletException, IOException {
		// logger.info("before:"+businessTypeName);
		businessTypeName = new String(businessTypeName.getBytes("ISO-8859-1"),
				"UTF-8");
		// logger.info("after:"+businessTypeName);
		List<CommonFlush> list = contextDistributeManager
				.queryBussContextByTypeExport(dataType, Common
						.StringToInt(timeLevel_search), searchDateStart,
						businessTypeName);
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		String exportmsg = ExportMsg.EXPORT_FLUSH_BUSINESS
				+ Common.getTimeLevelCH(timeLevel_search) + "（"
				+ searchDateStart + "）";
		commonManagerImpl.log(request, exportmsg);
		gridServerHandler.exportXLS(list, CommonFlush.class);
	}

	public void queryBusinessDataByType() {// 根据业务类型查询具体业务分布

		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		SortInfo si = gridServerHandler.getSingleSortInfo();
		if (si != null) {
			String order = si.getSortOrder();
			/**
			 * grid控件排序有3种情况：asc desc 和 第一次加载数据
			 * 事实上可以更改源码去掉defaultsort,但改后第一次加载数据不存在,故通过后台管理
			 */
			if ("defaultsort".equals(order)) {
				page.setOrder("desc");
				page.setOrderBy("nmFlush");
			} else {
				page.setOrder(order);
				page.setOrderBy(MantoEyeUtils.getSortField(si.getFieldName()));
			}
			// 默认排序方式
		} else {
			page.setOrderBy("nmFlush");
			page.setOrder("desc");
		}
		page.setPageSize(gridServerHandler.getPageSize());
		page.setPageNo(gridServerHandler.getPageInfo().getPageNum());

		List<CommonFlush> list = new ArrayList<CommonFlush>();
		page = contextDistributeManager.queryBussContextByType(this.page,
				dataType, Common.StringToInt(timeLevel_search),
				searchDateStart, businessTypeName);
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
	 * 根据业务类型查询对应具体业务 图形数据
	 * 
	 */

	public void getAjaxChartXmlData() {

		List<CommonFlush> list = contextDistributeManager
				.queryBussContextByTypeExport(dataType, Common
						.StringToInt(timeLevel_search), searchDateStart,
						businessTypeName);
		long ten_totalFlush = 1L;
		long ten_imsi = 1L;
		long ten_visit = 1L;
		long firty_totalFlush = 1L;
		long firty_imsi = 1L;
		long firty_visit = 1L;
		List top10 = new ArrayList();
		if (businessTypeName.startsWith("一级域名")) {
			CommonFlush conFlush = null;
			if ((list != null) && (!(list.isEmpty()))) {
				for (int i = 0; i < list.size(); ++i) {
					conFlush = (CommonFlush) list.get(i);
					if (i < 10) {
						ten_totalFlush += conFlush.getTotalFlush().longValue();
						ten_imsi += conFlush.getIntImsis().longValue();
						ten_visit += conFlush.getVisit().longValue();
						top10.add(conFlush);
					}
					firty_totalFlush = ten_totalFlush
							+ conFlush.getTotalFlush().longValue();
					firty_imsi = ten_imsi + conFlush.getIntImsis().longValue();
					firty_visit = ten_visit + conFlush.getVisit().longValue();
				}

			}

			Iterator it = top10.iterator();
			String chartLabel = searchDateStart + " " + businessTypeName
					+ "具体业务" + "#FIELD#分布图";
			String xml = "<root><chart id=\"1\" name=\"" + chartLabel
					+ "\" fields=\"流量|用户数|激活次数\">";
			while (it.hasNext()) {
				CommonFlush commonFlush = (CommonFlush) it.next();
				xml = xml + "<data label=\"" + commonFlush.getBusiness()
						+ "\" total=\"" + commonFlush.getTotalFlushMB()
						+ "\" up=\"" + commonFlush.getUpFlushMB()
						+ "\" down=\"" + commonFlush.getDownFlushMB()
						+ "\" imsis =\"" + commonFlush.getIntImsis()
						+ "\" count=\"" + commonFlush.getVisit() + "\"/>";
			}

			long antherTotal = firty_totalFlush - ten_totalFlush;
			long antherImsi = firty_imsi - ten_imsi;
			long antherVisit = firty_visit - ten_visit;
			xml = xml + "<data label=\"" + "其他" + "\" total=\"" + antherTotal
					+ "\" up=\"" + 0 + "\" down=\"" + 0 + "\" imsis =\""
					+ antherImsi + "\" count=\"" + antherVisit + "\"/>";
			xml = xml + "</chart></root>";
			Struts2Utils.renderText(xml, new String[0]);
		} else {
			Iterator it = list.iterator();
			String chartLabel = searchDateStart + " " + businessTypeName
					+ "具体业务" + "#FIELD#分布图";
			String xml = "<root><chart id=\"1\" name=\"" + chartLabel
					+ "\" fields=\"流量|用户数|激活次数\">";
			while (it.hasNext()) {
				CommonFlush commonFlush = (CommonFlush) it.next();
				xml = xml + "<data label=\"" + commonFlush.getBusiness()
						+ "\" total=\"" + commonFlush.getTotalFlushMB()
						+ "\" up=\"" + commonFlush.getUpFlushMB()
						+ "\" down=\"" + commonFlush.getDownFlushMB()
						+ "\" imsis =\"" + commonFlush.getIntImsis()
						+ "\" count=\"" + commonFlush.getVisit() + "\"/>";
			}

			xml = xml + "</chart></root>";
			Struts2Utils.renderText(xml, new String[0]);
		}

	}

	public String getBusinessTypeName() {
		return businessTypeName;
	}

	public void setBusinessTypeName(String businessTypeName) {
		this.businessTypeName = businessTypeName;
	}

	public String getSearchDateStart() {
		return searchDateStart;
	}

	public void setSearchDateStart(String searchDateStart) {
		this.searchDateStart = searchDateStart;
	}

	public String getTimeLevel_search() {
		return timeLevel_search;
	}

	public void setTimeLevel_search(String timeLevel_search) {
		this.timeLevel_search = timeLevel_search;
	}

	public int getDataType() {
		return dataType;
	}

	public void setDataType(int dataType) {
		this.dataType = dataType;
	}
}
