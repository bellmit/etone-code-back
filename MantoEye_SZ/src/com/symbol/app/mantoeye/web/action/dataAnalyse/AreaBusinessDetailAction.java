package com.symbol.app.mantoeye.web.action.dataAnalyse;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.symbol.app.mantoeye.constants.ExportMsg;
import com.symbol.app.mantoeye.dto.flush.CommonFlush;
import com.symbol.app.mantoeye.service.businessAnalyse.AreaBusinessDetailManager;
import com.symbol.app.mantoeye.util.CommonUtils;
import com.symbol.app.mantoeye.util.DataFormatUtils;
import com.symbol.app.mantoeye.util.MantoEyeUtils;
import com.symbol.wp.core.constants.VarConstants;
import com.symbol.wp.core.service.ICommonManager;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.struts2.Struts2Utils;
import com.symbol.wp.tools.gtgrid.model.SortInfo;
import com.symbol.wp.tools.gtgrid.server.GridServerHandler;

public class AreaBusinessDetailAction {

	@Autowired
	private AreaBusinessDetailManager areaBusinessDetailManager;
	@Resource(name = "commonManagerImpl")
	public ICommonManager commonManagerImpl;

	private Page<CommonFlush> page = new Page<CommonFlush>(
			VarConstants.PAGE_LONG_ROW_SIZE);

	private String dataType_search;
	private String timeLevel_search;
	private String time_search;
	private String businessName;//小区集名称

	protected final Logger logger = LoggerFactory.getLogger(getClass());

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

		page = areaBusinessDetailManager.query(page, Integer
				.parseInt(dataType_search), businessName, Integer
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
		// int pageSize = 20;
		/*
		 * 图表不包含‘其他’的数据，因此为了去除其他，必须多查一条数据
		 */

		int pageSize = 21;
		String totalXml = this.packTotalData(dataType_search,
				 businessName, timeLevel_search, time_search,
				pageSize);
		String imsisXml = this.packImsisData(dataType_search,
				 businessName, timeLevel_search, time_search,
				pageSize);
		String countXml = this.packCountData(dataType_search,
				 businessName, timeLevel_search, time_search,
				pageSize);
		Struts2Utils.renderText(totalXml + "&&&" + imsisXml + "&&&" + countXml);

	}

	/**
	 * 数据导出
	 */
	public void export() throws ServletException, IOException {
		logger.info("businessName:" + businessName);
		logger.info("businessName:" + Common.convertToUTF8(businessName));
		List<CommonFlush> list = areaBusinessDetailManager.listData(Common
				.StringToInt(dataType_search), Common
				.convertToUTF8(businessName), Integer
				.parseInt(timeLevel_search), time_search);
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());

		String exportmsg = ExportMsg.EXPORT_FLUSH_BUSINESS_SPACE
				+ Common.getTimeLevelCH(timeLevel_search) + "（"
				+ Common.convertToUTF8(businessName) + "|" + time_search + "）";
		commonManagerImpl.log(Struts2Utils.getRequest(), exportmsg);

		gridServerHandler.exportXLS(list, CommonFlush.class);
	}

	/**
	 * 组装总流量数据
	 * 
	 * @param dataType
	 * @param spaceLevel
	 * @param areaName
	 * @param timeLevel
	 * @param time
	 * @return
	 */
	public String packTotalData(String dataType,
			String areaName, String timeLevel, String time, int pageSize) {
		Page<CommonFlush> newPage = new Page<CommonFlush>();
		newPage.setPageNo(1);
		newPage.setOrder("desc");
		newPage.setOrderBy("nmFlush");
		newPage.setPageSize(pageSize);
		newPage = areaBusinessDetailManager.query(newPage, Integer
				.parseInt(dataType), areaName,
				Common.StringToInt(timeLevel), time);
		List<CommonFlush> totalList = newPage.getResult();

		// 去掉名称为其他的数据
		totalList = DataFormatUtils.exceptCommonFlush(totalList, "其他");
		if (totalList.size() > 20) {// 如果数据大于20条
			totalList.remove(20);
		}

		if (Common.StringToInt(timeLevel) == 3) {
			time = CommonUtils.getDayInnerWeek(time);
		}
		String chartLabel = time + " 总流量TOP20分布图";
		String totalXml = "<root><chart id=\"1\"leftformat=\"true\"  name=\""
				+ chartLabel + "\" fields=\"流量|\">";
		if (totalList != null && !totalList.isEmpty()) {
			for (CommonFlush c : totalList) {
				totalXml = totalXml + "<data label=\"" + c.getBusinessName()
						+ "\" shortlabel=\""
						+ formatChartName(c.getBusinessName()) + "\" total=\""
						+ c.getTotalFlush() + "\"/>";
			}
		}
		totalXml = totalXml + "</chart></root>";
		return totalXml;
	}

	/**
	 * 组装用户数数据
	 * 
	 */
	public String packImsisData(String dataType,
			String areaName, String timeLevel, String time, int pageSize) {
		Page<CommonFlush> newPage = new Page<CommonFlush>();
		newPage.setPageNo(1);
		newPage.setPageSize(pageSize);
		newPage.setOrder("desc");
		newPage.setOrderBy("nmUsers");
		newPage = areaBusinessDetailManager.query(newPage, Integer
				.parseInt(dataType), areaName,
				Common.StringToInt(timeLevel), time);
		List<CommonFlush> imisiList = newPage.getResult();

		// 去掉名称为其他的数据
		imisiList = DataFormatUtils.exceptCommonFlush(imisiList, "其他");
		if (imisiList.size() > 20) {// 如果数据大于20条
			imisiList.remove(20);
		}

		if (Common.StringToInt(timeLevel) == 3) {
			time = CommonUtils.getDayInnerWeek(time);
		}
		String chartLabel = time + " 用户数TOP20分布图";
		String imsisXml = "<root><chart id=\"1\"  name=\"" + chartLabel
				+ "\" fields=\"用户数|\">";
		if (imisiList != null && !imisiList.isEmpty()) {
			for (CommonFlush c : imisiList) {
				imsisXml = imsisXml + "<data label=\"" + c.getBusinessName()
						+ "\" shortlabel=\""
						+ formatChartName(c.getBusinessName()) + "\" total=\""
						+ c.getIntImsis() + "\"/>";
			}
		}
		imsisXml = imsisXml + "</chart></root>";
		return imsisXml;
	}

	/**
	 * 组装访问次数数据
	 */
	public String packCountData(String dataType,
			String areaName, String timeLevel, String time, int pageSize) {
		Page<CommonFlush> newPage = new Page<CommonFlush>();
		newPage.setPageNo(1);
		newPage.setPageSize(pageSize);
		newPage.setOrder("desc");
		newPage.setOrderBy("nmVisitCounts");
		newPage = areaBusinessDetailManager.query(newPage, Integer
				.parseInt(dataType), areaName,
				Common.StringToInt(timeLevel), time);
		List<CommonFlush> countList = newPage.getResult();

		// 去掉名称为其他的数据
		countList = DataFormatUtils.exceptCommonFlush(countList, "其他");
		if (countList.size() > 20) {// 如果数据大于20条
			countList.remove(20);
		}

		if (Common.StringToInt(timeLevel) == 3) {
			time = CommonUtils.getDayInnerWeek(time);
		}
		String chartLabel = time + " 激活次数TOP20分布图";
		String countXml = "<root><chart id=\"1\" name=\"" + chartLabel
				+ "\" fields=\"激活次数|\">";
		if (countList != null && !countList.isEmpty()) {
			for (CommonFlush c : countList) {
				countXml = countXml + "<data label=\"" + c.getBusinessName()
						+ "\" shortlabel=\""
						+ formatChartName(c.getBusinessName()) + "\" total=\""
						+ c.getVisit() + "\"/>";
			}
		}
		countXml = countXml + "</chart></root>";
		return countXml;
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

	public String getTime_search() {
		return time_search;
	}

	public void setTime_search(String time_search) {
		this.time_search = time_search;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	/**
	 * 图表显示时去掉名称的说明性文字
	 * 
	 * @param fullname
	 * @return
	 */
	private String formatChartName(String fullname) {
		String shortname = "";

		// 去掉括号内的内容
		shortname = fullname.replaceAll("\\(.+\\)", "");
		// 去掉-后的内容
		if (shortname.indexOf("-") > 0) {
			shortname = shortname.substring(0, shortname.indexOf("-"));
		}

		return shortname;
	}
}
