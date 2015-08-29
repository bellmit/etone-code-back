package com.symbol.app.mantoeye.web.action.blackberry;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;

import com.symbol.app.mantoeye.constants.CommonConstants;
import com.symbol.app.mantoeye.constants.ExportMsg;
import com.symbol.app.mantoeye.constants.SessionConstants;
import com.symbol.app.mantoeye.dto.blackberry.LdcUsersBean;
import com.symbol.app.mantoeye.service.blackberry.BlackBerryYysBussinessManager;
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

public class BlackBerryYysBusinessAction extends BaseDispatchAction {

	@Autowired
	private BlackBerryYysBussinessManager blackBerryYysBussinessManager;
	@Resource(name = "commonManagerImpl")
	public ICommonManager commonManagerImpl;

	private Page<LdcUsersBean> page = new Page<LdcUsersBean>(
			VarConstants.PAGE_LONG_ROW_SIZE);

	private String searchDateStart = CommonUtils.getSYestoday();// 查询时间
	private String timeLevel_search = "2";

	public void query() {
		List<LdcUsersBean> list = null;
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		// SortInfo si = gridServerHandler.getSingleSortInfo();
		// if (si != null) {
		// String order = si.getSortOrder();
		// /**
		// * grid控件排序有3种情况：asc desc 和 第一次加载数据
		// * 事实上可以更改源码去掉defaultsort,但改后第一次加载数据不存在,故通过后台管理
		// */
		// if ("defaultsort".equals(order)) {
		// page.setOrder("desc");
		// page.setOrderBy("nmTotalFlush");
		// } else {
		// page.setOrder(order);
		// page.setOrderBy(CommonUtils.getSortFieldForBB(si.getFieldName()));
		// }
		// // 默认排序方式
		// } else {
		// page.setOrderBy("nmTotalFlush");
		// page.setOrder("desc");
		// }
		// page.setPageSize(gridServerHandler.getPageSize());
		// page.setPageNo(gridServerHandler.getPageInfo().getPageNum());
		// int totalRowNum = gridServerHandler.getTotalRowNum();
		// this.page=blackBerryYysBussinessManager.query(this.page,Common.StringToInt(timeLevel_search),
		// searchDateStart);
		// if (totalRowNum < 1) {
		// totalRowNum = page.getTotalCount();
		// gridServerHandler.setTotalRowNum(totalRowNum);
		// }
		// list = page.getResult();
		int timeLevel = 2;
		timeLevel = Common.StringToInt(timeLevel_search);
		page.setOrderBy("nmTotalFlush");
		page.setOrder("desc");
		list = blackBerryYysBussinessManager.listData(page, timeLevel,
				searchDateStart);
		String sxml = buildChartStr(list);
		this.getServletRequest().getSession().setAttribute(
				SessionConstants.SESSION_DATA_BB_LDA_SPACE, sxml);
		gridServerHandler.setData(list, LdcUsersBean.class);
		Struts2Utils.renderJson(gridServerHandler.getLoadResponseText());

	}

	public void getAjaxChartXmlData() throws Exception {
		String xml = this.getServletRequest().getSession().getAttribute(
				SessionConstants.SESSION_DATA_BB_LDA_SPACE)
				+ "";
		Struts2Utils.renderText(xml);
	}

	/**
	 * 获取图形数据
	 */
	public String buildChartStr(List<LdcUsersBean> list) {
		// List<LdcUsersBean> list = null;
		int timeLevel = 2;
		timeLevel = Common.StringToInt(timeLevel_search);
		//			
		// GridServerHandler gridServerHandler = new GridServerHandler(
		// Struts2Utils.getRequest(), Struts2Utils.getResponse());
		// SortInfo si = gridServerHandler.getSingleSortInfo();
		// if (si != null) {
		// String order = si.getSortOrder();
		// /**
		// * grid控件排序有3种情况：asc desc 和 第一次加载数据
		// * 事实上可以更改源码去掉defaultsort,但改后第一次加载数据不存在,故通过后台管理
		// */
		// if ("defaultsort".equals(order)) {
		// page.setOrder("desc");
		// page.setOrderBy("nmTotalFlush");
		// } else {
		// page.setOrder(order);
		// page.setOrderBy(CommonUtils.getSortFieldForBB(si.getFieldName()));
		// }
		// // 默认排序方式
		// } else {
		// page.setOrderBy("nmTotalFlush");
		// page.setOrder("desc");
		// }
		// page.setPageSize(gridServerHandler.getPageSize());
		// page.setPageNo(gridServerHandler.getPageInfo().getPageNum());
		//			
		// list=blackBerryYysBussinessManager.listData(page,timeLevel,
		// searchDateStart);
		Long sumFlush = 0l;
		Long sumImsi = 0l;
		if (list != null && !list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				if (i > 9) {
					LdcUsersBean c = list.get(i);
					sumFlush = sumFlush + c.getTotalFlush();
					sumImsi = sumImsi + c.getIntImsis();
				}
			}
		}
		String time_Level_Name = "";
		time_Level_Name = (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_HOUR) ? "小时"
				: (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_DAY) ? "天"
						: (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_WEEK) ? "周"
								: "月";
		String titletime = searchDateStart;
		if (Common.StringToInt(timeLevel_search) == 3) {
			titletime = CommonUtils.getDayInnerWeek(searchDateStart);
		}
		String xml = "<root><chart id=\"1\" name=\"" + titletime + "运营商#FIELD#"
				+ time_Level_Name + "分布图" + "\"fields=\"流量|用户数\">";
		int i = 0;
		if (list != null && !list.isEmpty()) {
			for (LdcUsersBean c : list) {
				i++;
				if (i > 10)
					break;
				xml = xml + "<data label=\"" + formatName(c.getLdcName())
						+ "\" shortlabel = \"" + formatName(c.getLdcName())
						+ "\" total=\"" + c.getTotalFlush() + "\" imsis=\""
						+ c.getIntImsis() + "\"/>";
			}
			xml = xml + "<data label=\"" + "其他" + "\" shortlabel = \"" + "其他"
					+ "\" total=\"" + sumFlush + "\" imsis=\"" + sumImsi
					+ "\"/>";
		}
		xml = xml + "</chart></root>";
		return xml;
	}

	/**
	 * 去掉括号内部的文字
	 * 
	 * @param name
	 * @return
	 */
	private String formatName(String name) {
		String rex = "\\(.*\\)";
		return name.replaceAll(rex, "");
	}

	/**
	 * 数据导出
	 */
	public void export() throws ServletException, IOException {
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
				page.setOrderBy("nmTotalFlush");
			} else {
				page.setOrder(order);
				page.setOrderBy(MantoEyeUtils.getSortFieldForBB(si
						.getFieldName()));
			}
			// 默认排序方式
		} else {
			page.setOrderBy("nmTotalFlush");
			page.setOrder("desc");
		}
		page.setPageSize(gridServerHandler.getPageSize());
		page.setPageNo(gridServerHandler.getPageInfo().getPageNum());
		List<LdcUsersBean> list = blackBerryYysBussinessManager.listData(page,
				Common.StringToInt(timeLevel_search), searchDateStart);
		String exportmsg = ExportMsg.EXPORT_BLACKBREEY_LAC
				+ Common.getTimeLevelCH(timeLevel_search) + "（"
				+ searchDateStart + "）";
		commonManagerImpl.log(Struts2Utils.getRequest(), exportmsg);
		gridServerHandler.exportXLS(list, LdcUsersBean.class);
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

}
