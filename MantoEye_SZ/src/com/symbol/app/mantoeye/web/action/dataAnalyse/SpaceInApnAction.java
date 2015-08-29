package com.symbol.app.mantoeye.web.action.dataAnalyse;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.symbol.app.mantoeye.constants.CommonConstants;
import com.symbol.app.mantoeye.constants.ExportMsg;
import com.symbol.app.mantoeye.dto.flush.CommonFlush;
import com.symbol.app.mantoeye.service.businessAnalyse.ApnDistributeManager;
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

public class SpaceInApnAction extends BaseDispatchAction {

	@Autowired
	private ApnDistributeManager apnDistributeManager;
	@Resource(name = "commonManagerImpl")
	public ICommonManager commonManagerImpl;

	HttpServletRequest request = Struts2Utils.getRequest();
	private Page<CommonFlush> page = new Page<CommonFlush>(
			VarConstants.PAGE_LONG_ROW_SIZE);
	private String apnName;// APN名称
	private String searchDateStart = CommonUtils.getSYestoday();// 查询时间
	private int dataType = 1;
	private String timeLevel_search = "2";// 时间周期 2:天
	private String spaceType = "1";

	/**
	 * 数据导出
	 */
	public void export() throws ServletException, IOException {
		apnName = new String(apnName.getBytes("ISO-8859-1"), "UTF-8");

		// apnName = apnName.replace("AanNDd", "&");
		int chaertType = 1;
		chaertType = Common.StringToInt(spaceType);

		/*
		 * String dataTypeStr = request.getParameter("dataTypeValue"); if
		 * (dataTypeStr != null) { dataType = Common.StringToInt(dataTypeStr); }
		 */
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
		this.page.setPageSize(gridServerHandler.getPageSize());
		this.page.setPageNo(gridServerHandler.getPageInfo().getPageNum());
		List<CommonFlush> list = apnDistributeManager.listData(page, dataType,
				chaertType, Common.StringToInt(timeLevel_search),
				searchDateStart, apnName);

		String exportmsg = ExportMsg.EXPORT_FLUSH_APN_SPACE
				+ Common.getTimeLevelCH(timeLevel_search) + "（" + apnName + "|"
				+ searchDateStart + "）";
		commonManagerImpl.log(Struts2Utils.getRequest(), exportmsg);

		gridServerHandler.exportXLS(list, CommonFlush.class);
	}

	/**
	 * 查询APN空间分布
	 */
	public void querySpaceInApn() {

		try {
			logger.info("before:" + apnName);
			// apnName = new String(apnName.getBytes("ISO-8859-1"), "UTF-8");
			// logger.info("after:"+apnName);
			int chaertType = 1;
			chaertType = Common.StringToInt(spaceType);
			// apnName = apnName.replace("AanNDd", "&");
			logger.info("####" + apnName + "####" + searchDateStart + "####"
					+ dataType + "######" + timeLevel_search + "####"
					+ spaceType);
			/*
			 * String dataTypeStr = request.getParameter("dataTypeValue"); if
			 * (dataTypeStr != null) { dataType =
			 * Common.StringToInt(dataTypeStr); }
			 */

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
					page.setOrderBy(MantoEyeUtils.getSortField(si
							.getFieldName()));
				}
				// 默认排序方式
			} else {
				page.setOrderBy("nmFlush");
				page.setOrder("desc");
			}
			this.page.setPageSize(gridServerHandler.getPageSize());
			this.page.setPageNo(gridServerHandler.getPageInfo().getPageNum());
			this.page = this.apnDistributeManager.query(this.page, dataType,
					chaertType, Common.StringToInt(timeLevel_search),
					searchDateStart, apnName);
			int totalRowNum = gridServerHandler.getTotalRowNum();
			if (totalRowNum < 1) {
				totalRowNum = this.page.getTotalCount();
				gridServerHandler.setTotalRowNum(totalRowNum);
			}
			List list = null;
			list = this.page.getResult();
			gridServerHandler.setData(list, CommonFlush.class);
			Struts2Utils.renderJson(gridServerHandler.getLoadResponseText(),
					new String[0]);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	/**
	 * 查询APN空间分布图形
	 */
	public void getAjaxChartXmlSpaceData() {

		int chaertType = 1;
		chaertType = Common.StringToInt(spaceType);
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
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		SortInfo si = gridServerHandler.getSingleSortInfo();
		logger.info("before:" + apnName);
		// try {
		// apnName = new String(apnName.getBytes("ISO-8859-1"), "UTF-8");
		// } catch (UnsupportedEncodingException e) {
		// logger.error(e.getMessage());
		// }
		// logger.info("after:"+apnName);
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

		List<CommonFlush> list = apnDistributeManager.listData(page, dataType,
				chaertType, Common.StringToInt(timeLevel_search),
				searchDateStart, apnName);

		String titletime = searchDateStart;
		if (Common.StringToInt(timeLevel_search) == 3) {
			titletime = CommonUtils.getDayInnerWeek(searchDateStart);
		}

		String chartTitle = "\"" + titletime + " " + name + "{dataname}分布\"";

		// 去掉名称为其他的数据
		list = DataFormatUtils.exceptCommonFlush(list, "其他");
		// 根据数量级获取流量的单位，防止数量太大
		String flushUnit = DataFormatUtils.getFlushUnit(list);
		String imsisUnit = DataFormatUtils.getImsisUnit(list);
		String visitUnit = DataFormatUtils.getVisitUnit(list);

		logger.info("flushUnit:" + flushUnit + "--imsisUnit:" + imsisUnit
				+ "--visitUnit:" + visitUnit);
		String xml = "<?xml version=\"1.0\" encoding=\"gb2312\"?>" + "<Data>"
				+ "<Stat id=\"\" unit=\"" + flushUnit + "|" + imsisUnit + "|"
				+ visitUnit + "\" tipunit=\"\" title=" + chartTitle
				+ " trip=\"流量用户数统计{br}" + name + "：{name}{br}总流量：{total}"
				+ flushUnit + "{br}上行流量：{up}" + flushUnit + "{br}下行流量：{down}"
				+ flushUnit + "{br}用户数：{imsis}" + imsisUnit
				+ "{br}激活次数：{count}" + visitUnit + "\">";
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
						+ "\" count =\""
						+ DataFormatUtils.getIVByUnit(c, visitUnit, "visit")
						+ "\"/>";
			}
		}

		xml = xml + "</Stat>" + "</Data>";
		Struts2Utils.renderText(xml);

	}

	public String getApnName() {
		return apnName;
	}

	public void setApnName(String apnName) {
		this.apnName = apnName;
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

	public String getSpaceType() {
		return spaceType;
	}

	public void setSpaceType(String spaceType) {
		this.spaceType = spaceType;
	}

	public int getDataType() {
		return dataType;
	}

	public void setDataType(int dataType) {
		this.dataType = dataType;
	}

}
