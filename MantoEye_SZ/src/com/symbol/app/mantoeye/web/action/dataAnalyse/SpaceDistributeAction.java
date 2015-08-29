package com.symbol.app.mantoeye.web.action.dataAnalyse;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.symbol.app.mantoeye.constants.CommonConstants;
import com.symbol.app.mantoeye.constants.ExportMsg;
import com.symbol.app.mantoeye.constants.SessionConstants;
import com.symbol.app.mantoeye.dto.flush.CommonFlush;
import com.symbol.app.mantoeye.service.businessAnalyse.SpaceDistributeManager;
import com.symbol.app.mantoeye.util.CommonUtils;
import com.symbol.app.mantoeye.util.DataFormatUtils;
import com.symbol.wp.core.constants.VarConstants;
import com.symbol.wp.core.service.ICommonManager;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.struts2.Struts2Utils;
import com.symbol.wp.modules.struts2.base.BaseDispatchAction;
import com.symbol.wp.tools.gtgrid.model.SortInfo;
import com.symbol.wp.tools.gtgrid.server.GridServerHandler;

/**
 * 全网空间分布
 * 
 * 
 */
public class SpaceDistributeAction extends BaseDispatchAction {

	@Autowired
	private SpaceDistributeManager spaceDistributeManager;
	@Resource(name = "commonManagerImpl")
	public ICommonManager commonManagerImpl;

	private Page<CommonFlush> page = new Page<CommonFlush>(
			VarConstants.PAGE_LONG_ROW_SIZE);

	private String dataType_search = "1";// 默认查询GPRS数据类型
	private String timeLevel_search = "2";// 默认天
	private String spaceLevel_search = "1";// 默认区域维度BSC
	private String time_search = CommonUtils.getSYestoday();// 默认昨天
	private String total_time_level;// 全网连接过来参数
	private String total_time_search;

	/**
	 * 查询数据
	 */
	public void query() throws ServletException, IOException {
		HttpServletRequest request = Struts2Utils.getRequest();
		// 判断是否从全网跳转到协议页面
		if (!"null".equals(total_time_level)) {
			timeLevel_search = total_time_level;
			time_search = total_time_search;
		}
		List<CommonFlush> list = null;

		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());

		// SortInfo si = gridServerHandler.getSingleSortInfo();
		// if (si != null) {
		// String order = si.getSortOrder();
		// if ("defaultsort".equals(order)) {
		// page.setOrder("desc");
		// page.setOrderBy("nmFlush");
		// } else {
		// page.setOrder(order);
		// page.setOrderBy(CommonUtils.getSortField(si.getFieldName()));
		// } // 默认排序方式
		// } else {
		// page.setOrderBy("nmFlush");
		// page.setOrder("desc");
		// }
		// page.setPageSize(gridServerHandler.getPageSize());
		// page.setPageNo(gridServerHandler.getPageInfo().getPageNum());
		// page = spaceDistributeManager.query(page, Integer
		// .parseInt(dataType_search), Common
		// .StringToInt(spaceLevel_search), Integer
		// .parseInt(timeLevel_search), time_search);
		// int totalRowNum = gridServerHandler.getTotalRowNum();
		// if (totalRowNum < 1) {
		// totalRowNum = page.getTotalCount();
		// gridServerHandler.setTotalRowNum(totalRowNum);
		// }
		// list = page.getResult();
		try {
			list = spaceDistributeManager.listData(Integer
					.parseInt(dataType_search), Common
					.StringToInt(spaceLevel_search), Integer
					.parseInt(timeLevel_search), time_search);
			gridServerHandler.setData(list, CommonFlush.class);

			String sxml = buildChartStr(list);
			this.getServletRequest().getSession().setAttribute(
					SessionConstants.SESSION_DATA_FLUSH_SPACE, sxml);

			Struts2Utils.renderJson(gridServerHandler.getLoadResponseText());
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

	}

	public void getAjaxChartXmlData() throws Exception {
		String xml = this.getServletRequest().getSession().getAttribute(
				SessionConstants.SESSION_DATA_FLUSH_SPACE)
				+ "";
		Struts2Utils.renderText(xml);
	}

	/**
	 * 获取图形数据
	 */
	public String buildChartStr(List<CommonFlush> list) {
		String xml = "";
		try {

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
			if (Common.StringToInt(timeLevel_search) == 3) {
				time_search = CommonUtils.getDayInnerWeek(time_search);
			}

			// 去掉名称为其他的数据
			list = DataFormatUtils.exceptCommonFlush(list, "其他");
			// 根据数量级获取流量的单位，防止数量太大
			String flushUnit = DataFormatUtils.getFlushUnit(list);
			String imsisUnit = DataFormatUtils.getImsisUnit(list);
			String visitUnit = DataFormatUtils.getVisitUnit(list);

			logger.info("flushUnit:" + flushUnit + "--imsisUnit:" + imsisUnit
					+ "--visitUnit:" + visitUnit);

			String time = time_search;
			if (Common.StringToInt(timeLevel_search) == 3) {
				time = CommonUtils.getDayInnerWeek(time_search);
			}
			String chartTitle = "\"" + time + " " + name + "{dataname}分布\"";
			xml = "<?xml version=\"1.0\" encoding=\"gb2312\"?>" + "<Data>"
					+ "<Stat id=\"\" unit=\"" + flushUnit + "|" + imsisUnit
					+ "|" + visitUnit + "\" tipunit=\"\" title=" + chartTitle
					+ " trip=\"流量用户数统计{br}" + name + "：{name}{br}总流量：{total}"
					+ flushUnit + "{br}上行流量：{up}" + flushUnit
					+ "{br}下行流量：{down}" + flushUnit + "{br}用户数：{imsis}"
					+ imsisUnit + "\">";
			if (list != null && !list.isEmpty()) {
				for (CommonFlush c : list) {
					xml = xml
							+ "<Statdata name=\""
							+ c.getBusinessName()
							+ "\" total=\""
							+ DataFormatUtils.getFlushByUnit(c, flushUnit,
									"total")
							+ "\" up=\""
							+ DataFormatUtils
									.getFlushByUnit(c, flushUnit, "up")
							+ "\" down=\""
							+ DataFormatUtils.getFlushByUnit(c, flushUnit,
									"down")
							+ "\" imsis =\""
							+ DataFormatUtils
									.getIVByUnit(c, imsisUnit, "imsis")
							+ "\" count =\""
							+ DataFormatUtils
									.getIVByUnit(c, visitUnit, "visit")
							+ "\"/>";
				}
			}
			xml = xml + "</Stat>" + "</Data>";
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return xml;
	}

	/**
	 * 数据导出
	 */
	public void export() throws ServletException, IOException {
		List<CommonFlush> list = spaceDistributeManager.listData(Integer
				.parseInt(dataType_search), Common
				.StringToInt(spaceLevel_search), Integer
				.parseInt(timeLevel_search), time_search);
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		String exportmsg = ExportMsg.EXPORT_FLUSH_SPACE
				+ Common.getTimeLevelCH(timeLevel_search) + "（" + time_search
				+ "）";
		commonManagerImpl.log(this.getServletRequest(), exportmsg);
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

	public String getTotal_time_level() {
		return total_time_level;
	}

	public void setTotal_time_level(String total_time_level) {
		this.total_time_level = total_time_level;
	}

	public String getTotal_time_search() {
		return total_time_search;
	}

	public void setTotal_time_search(String total_time_search) {
		this.total_time_search = total_time_search;
	}

}
