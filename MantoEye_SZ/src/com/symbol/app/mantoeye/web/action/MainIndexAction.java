package com.symbol.app.mantoeye.web.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;

import com.symbol.app.mantoeye.constants.CommonConstants;
import com.symbol.app.mantoeye.dto.TopFlushBean;
import com.symbol.app.mantoeye.service.businessAnalyse.BusinessDistributeManager;
import com.symbol.app.mantoeye.util.CommonUtils;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.modules.struts2.Struts2Utils;
import com.symbol.wp.modules.struts2.base.BaseDispatchAction;
import com.symbol.wp.tools.gtgrid.org.json.JSONObject;
import com.symbol.wp.tools.gtgrid.util.JSONUtils;

/**
 * 系统主页
 * 
 * @author rankin
 * 
 */
public class MainIndexAction extends BaseDispatchAction {
	@Autowired
	private BusinessDistributeManager businessDistributeManager;

	private String searchDateStart = CommonUtils.getSYestoday();// 查询时间
	private String totalFlushDate = CommonUtils.getLastMonth();

	private String showunit;

	private String dataType = "flush";

	/**
	 * 查询数据
	 */
	public void query() throws ServletException, IOException {
		JSONObject json = new JSONObject();

		if (dataType.equals("wap")) {
			List<TopFlushBean> topwap = businessDistributeManager
					.queryTop10Busi(searchDateStart, "", "wap");
			json.put("datalist", JSONUtils.BeanList2JSONArray(topwap,
					TopFlushBean.class));
		} else if (dataType.equals("cmcc")) {
			List<TopFlushBean> topcmcc = businessDistributeManager
					.queryTop10Busi(searchDateStart, "", "cmcc");
			json.put("datalist", JSONUtils.BeanList2JSONArray(topcmcc,
					TopFlushBean.class));
		} else if ("total".equals(dataType.trim())) {
			List<TopFlushBean> totalFlush = businessDistributeManager
					.queryTop10Busi(totalFlushDate, searchDateStart, "total");
			json.put("datalist", JSONUtils.BeanList2JSONArray(totalFlush,
					TopFlushBean.class));
		} else {
			List<TopFlushBean> topflush = businessDistributeManager
					.queryTop10Busi(searchDateStart, "", "flush");
			json.put("datalist", JSONUtils.BeanList2JSONArray(topflush,
					TopFlushBean.class));
		}

		Struts2Utils.renderJson(json.toString());
	}

	public void queryHourData() throws ServletException, IOException {
		JSONObject json = new JSONObject();
		String busiName = this.getServletRequest().getParameter("busiName");
		logger.info(busiName + "------------------");
		// busiName = Common.convertToUTF8(busiName);
		String sdate = searchDateStart.split(" ")[0] + " 00:00:00";
		String edate = searchDateStart.split(" ")[0] + " 23:59:59";
		List<TopFlushBean> list = null;

		if ("全网".equals(busiName.trim())) {
			list = businessDistributeManager.queryHourDataByName(
					totalFlushDate, "", busiName);
		} else {
			list = businessDistributeManager.queryHourDataByName(sdate, edate,
					busiName);
		}

		json.put("datalist", JSONUtils.BeanList2JSONArray(list,
				TopFlushBean.class));
		// logger.info(json.toString());
		Struts2Utils.renderJson(json.toString());
	}

	public void queryHourData2() throws ServletException, IOException {
		JSONObject json = new JSONObject();
		String busiName = this.getServletRequest().getParameter("busiName");
		List<TopFlushBean> list = null;

		list = businessDistributeManager.queryHourDataByName(totalFlushDate,
				"", busiName);

		json.put("datalist", JSONUtils.BeanList2JSONArray(list,
				TopFlushBean.class));
		Struts2Utils.renderJson(json.toString());
	}

	public void querySpaceData() throws ServletException, IOException {
		// logger.info(Common.getNow()+"<--before");
		JSONObject json = new JSONObject();
		String busiName = this.getServletRequest().getParameter("busiName");
		String spaceType = this.getServletRequest().getParameter("spaceType");
		logger.info(busiName);
		// busiName = Common.convertToUTF8(busiName);
		List<TopFlushBean> list = null;
		if ("全网".equals(busiName)) {
			list = businessDistributeManager.querySpaceDataByName(
					totalFlushDate, busiName, Common.StringToInt(spaceType));
		} else {
			list = businessDistributeManager.querySpaceDataByName(
					searchDateStart, busiName, Common.StringToInt(spaceType));
		}

		String xml = buildChartStr(list, spaceType, busiName);
		Struts2Utils.renderText(xml);
	}

	public void querySpaceData2() throws ServletException, IOException {
		// logger.info(Common.getNow()+"<--before");
		JSONObject json = new JSONObject();
		String busiName = this.getServletRequest().getParameter("busiName");
		String spaceType = this.getServletRequest().getParameter("spaceType");
		List<TopFlushBean> list = null;

		list = businessDistributeManager.querySpaceDataByName2(searchDateStart,
				busiName, Common.StringToInt(spaceType));

		String xml = buildChartStr(list, spaceType, busiName);
		Struts2Utils.renderText(xml);
	}

	/**
	 * 获取图形数据
	 */
	public String buildChartStr(List<TopFlushBean> list, String spaceLevel,
			String busiName) {
		StringBuilder xml = new StringBuilder();
		// try {
		int chaertType = Common.StringToInt(spaceLevel);
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
		String time = "";
		if ("全网".equals(busiName)) {
			time = totalFlushDate;
		} else {
			time = searchDateStart.split(" ")[0];
		}
		String chartTitle = "\" " + time + " " + name
		// String chartTitle = "\"" + busiName + " " + time + " " + name
				+ "{dataname}分布\"";
		xml.append("<?xml version=\"1.0\" encoding=\"gb2312\"?><Data>");

		if ("全网".equals(busiName)) {
			time = totalFlushDate;
			xml.append("<Stat id=\"\" unit=\"" + showunit
					+ "||\" tipunit=\"\" title=");
			xml.append(chartTitle);
			xml.append(" trip=\"流量用户数统计{br}" + name + "：{name}{br}总流量：{total}"
					+ showunit + "{br}用户数：{imsis}\">");
			if (list != null && !list.isEmpty()) {
				for (TopFlushBean c : list) {
					xml.append("<Statdata name=\"");
					xml.append(c.getSpaceName());
					xml.append("\" total=\"");
					if ("MB".equals(showunit)) {
						xml.append(c.getFlushMB());
					} else if ("KB".equals(showunit)) {
						xml.append(c.getFlushKB());
					} else {
						if (0 == c.getFlushGB()) {
							xml.append(c.getFlushMB());
						} else {
							xml.append(c.getFlushGB());
						}
					}
					xml.append("\" imsis =\"");
					xml.append(c.getImsis());
					xml.append("\"/>");
				}
			}
		} else {
			time = searchDateStart.split(" ")[0];
			xml.append("<Stat id=\"\" unit=\"MB||\" tipunit=\"\" title=");
			xml.append(chartTitle);
			xml.append(" trip=\"流量用户数统计{br}" + name
					+ "：{name}{br}总流量：{total}MB{br}用户数：{imsis}\">");
			if (list != null && !list.isEmpty()) {
				for (TopFlushBean c : list) {
					xml.append("<Statdata name=\"");
					xml.append(c.getSpaceName());
					xml.append("\" total=\"");
					xml.append(c.getFlushMB());
					xml.append("\" imsis =\"");
					xml.append(c.getImsis());
					xml.append("\"/>");
				}
			}
		}

		xml.append("</Stat></Data>");
		// } catch (Exception e) {
		// logger.error(e.getMessage());
		// }
		return xml.toString();
	}

	public String getSearchDateStart() {
		return searchDateStart;
	}

	public void setSearchDateStart(String searchDateStart) {
		this.searchDateStart = searchDateStart;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public BusinessDistributeManager getBusinessDistributeManager() {
		return businessDistributeManager;
	}

	public void setBusinessDistributeManager(
			BusinessDistributeManager businessDistributeManager) {
		this.businessDistributeManager = businessDistributeManager;
	}

	public String getTotalFlushDate() {
		return totalFlushDate;
	}

	public void setTotalFlushDate(String totalFlushDate) {
		this.totalFlushDate = totalFlushDate;
	}

	public String getShowunit() {
		return showunit;
	}

	public void setShowunit(String showunit) {
		this.showunit = showunit;
	}
}
