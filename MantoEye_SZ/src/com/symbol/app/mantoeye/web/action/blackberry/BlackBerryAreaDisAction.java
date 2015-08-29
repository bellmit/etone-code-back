package com.symbol.app.mantoeye.web.action.blackberry;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;

import com.symbol.app.mantoeye.constants.CommonConstants;
import com.symbol.app.mantoeye.constants.ExportMsg;
import com.symbol.app.mantoeye.constants.SessionConstants;
import com.symbol.app.mantoeye.dto.flush.CommonFlush;
import com.symbol.app.mantoeye.service.blackberry.BlackBerryAreaDistinationManager;
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

public class BlackBerryAreaDisAction extends BaseDispatchAction {

	@Autowired
	private BlackBerryAreaDistinationManager blackBerryAreaDistinationManager;
	@Resource(name = "commonManagerImpl")
	public ICommonManager commonManagerImpl;

	private Page<CommonFlush> page = new Page<CommonFlush>(
			VarConstants.PAGE_LONG_ROW_SIZE);
	private String timeLevel_search = "2";// 时间周期 2:天
	private String time_search = CommonUtils.getSYestoday();// 查询时间
	private int dataType = 1;
	private String spaceLevel_search = "1";

	/**
	 * 查询数据
	 */
	public void query() throws ServletException, IOException {

		List<CommonFlush> list = null;
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
		// //int isTD = 0;// TD标识
		//
		// page = blackBerryAreaDistinationManager.query(page, dataType,
		// Common.StringToInt(spaceLevel_search),
		// Common.StringToInt(timeLevel_search),
		// time_search);
		// int totalRowNum = gridServerHandler.getTotalRowNum();
		// if (totalRowNum < 1) {
		// totalRowNum = page.getTotalCount();
		// gridServerHandler.setTotalRowNum(totalRowNum);
		// }
		// list = page.getResult();
		page.setOrderBy("nmTotalFlush");
		page.setOrder("desc");
		list = blackBerryAreaDistinationManager.listData(page, dataType, Common
				.StringToInt(spaceLevel_search), Common
				.StringToInt(timeLevel_search), time_search);
		String sxml = buildChartStr(list);
		this.getServletRequest().getSession().setAttribute(
				SessionConstants.SESSION_DATA_BB_SPACE, sxml);
		gridServerHandler.setData(list, CommonFlush.class);
		Struts2Utils.renderJson(gridServerHandler.getLoadResponseText());
	}

	public void getAjaxChartXmlData() throws Exception {
		String xml = this.getServletRequest().getSession().getAttribute(
				SessionConstants.SESSION_DATA_BB_SPACE)
				+ "";
		Struts2Utils.renderText(xml);
	}

	/**
	 * 获取图形数据
	 */
	public String buildChartStr(List<CommonFlush> list) {
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
		// page.setOrderBy(CommonUtils
		// .getSortFieldForBB(si.getFieldName()));
		// }
		// // 默认排序方式
		// } else {
		// page.setOrderBy("nmTotalFlush");
		// page.setOrder("desc");
		// }
		// page.setPageSize(gridServerHandler.getPageSize());
		// page.setPageNo(gridServerHandler.getPageInfo().getPageNum());
		// // int isTD = 0;// TD标识
		//
		// List<CommonFlush> list = blackBerryAreaDistinationManager.listData(
		// page, dataType, Common.StringToInt(spaceLevel_search), Common
		// .StringToInt(timeLevel_search), time_search);

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
		String titletime = time_search;
		if (Common.StringToInt(timeLevel_search) == 3) {
			titletime = CommonUtils.getDayInnerWeek(time_search);
		}
		String chartTitle = "\"" + titletime + " " + name + "{dataname}分布\"";
		// 去掉名称为其他的数据
		list = DataFormatUtils.exceptCommonFlush(list, "其他");
		// 根据数量级获取流量的单位，防止数量太大
		String flushUnit = DataFormatUtils.getFlushUnit(list);
		String imsisUnit = DataFormatUtils.getImsisUnit(list);

		String xml = "<?xml version=\"1.0\" encoding=\"gb2312\"?>" + "<Data>"
				+ "<Stat id=\"\" unit=\"" + flushUnit + "|" + imsisUnit
				+ "\" tipunit=\"\" title=" + chartTitle + " trip=\"流量用户数统计{br}"
				+ name + "：{name}{br}总流量：{total}" + flushUnit + "{br}上行流量：{up}"
				+ flushUnit + "{br}下行流量：{down}" + flushUnit + "{br}用户数：{imsis}"
				+ "\">";
		if (list != null && !list.isEmpty()) {
			for (CommonFlush c : list) {
				xml = xml + "<Statdata name=\"" + c.getBusinessName()
						+ "\" total=\""
						+ DataFormatUtils.getFlushByUnit(c, flushUnit, "total")
						+ "\" up=\""
						+ DataFormatUtils.getFlushByUnit(c, flushUnit, "up")
						+ "\" down=\""
						+ DataFormatUtils.getFlushByUnit(c, flushUnit, "down")
						+ "\" imsis =\""
						+ DataFormatUtils.getIVByUnit(c, imsisUnit, "imsis")
						+ "\" count =\"" + "\"/>";
			}
		}

		xml = xml + "</Stat>" + "</Data>";
		return xml;

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
		// int isTD = 0;// TD标识

		List<CommonFlush> list = blackBerryAreaDistinationManager.listData(
				page, dataType, Common.StringToInt(spaceLevel_search), Common
						.StringToInt(timeLevel_search), time_search);
		String exportmsg = ExportMsg.EXPORT_BLACKBREEY_SPACE
				+ Common.getTimeLevelCH(timeLevel_search) + " （" + time_search
				+ "）";
		commonManagerImpl.log(Struts2Utils.getRequest(), exportmsg);
		gridServerHandler.exportXLS(list, CommonFlush.class);
	}

	public String getTimeLevel_search() {
		return timeLevel_search;
	}

	public void setTimeLevel_search(String timeLevel_search) {
		this.timeLevel_search = timeLevel_search;
	}

	public String getTime_search() {
		return time_search;
	}

	public void setTime_search(String time_search) {
		this.time_search = time_search;
	}

	public String getSpaceLevel_search() {
		return spaceLevel_search;
	}

	public void setSpaceLevel_search(String spaceLevel_search) {
		this.spaceLevel_search = spaceLevel_search;
	}

}
