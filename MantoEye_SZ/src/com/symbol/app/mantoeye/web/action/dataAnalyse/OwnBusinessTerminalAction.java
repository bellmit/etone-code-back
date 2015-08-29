package com.symbol.app.mantoeye.web.action.dataAnalyse;


import java.io.IOException;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import com.symbol.app.mantoeye.dto.sports.CommonSport;
import com.symbol.app.mantoeye.service.businessAnalyse.OwnBusinessTerminalManager;
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


/**
 * 终端业务分析
 * 
 * 
 */
public class OwnBusinessTerminalAction extends BaseDispatchAction {
	@Autowired
	private OwnBusinessTerminalManager ownBusinessTerminalManager;
	@Resource(name = "commonManagerImpl")
	public ICommonManager commonManagerImpl;

	private Page<CommonSport> page = new Page<CommonSport>(
			VarConstants.PAGE_LONG_ROW_SIZE);

	private String dataType_search = "1";// 默认GPRS
	private String timeLevel_search = "1";// 默认天
	private String startTime = CommonUtils.getSYestoday() + " 00";// 开始时间
	private String endTime = CommonUtils.getSYestoday() + " 23";// 结束时间

	/**
	 * 查询数据
	 */
	public void query() throws ServletException, IOException {
		HttpServletRequest request = Struts2Utils.getRequest();
		List<CommonSport> list = null;

		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		try {
			SortInfo si = gridServerHandler.getSingleSortInfo();
		if (si != null) {
			String order = si.getSortOrder();
			if ("defaultsort".equals(order)) {
				page.setOrder("desc");
				page.setOrderBy("upFlush");
			} else {
				page.setOrder(order);
				page.setOrderBy(MantoEyeUtils.getSortField(si.getFieldName()));
			} // 默认排序方式
		} else {
			page.setOrderBy("upFlush");
			page.setOrder("desc");
		}
		page.setPageSize(gridServerHandler.getPageSize());
		page.setPageNo(gridServerHandler.getPageInfo().getPageNum());
		page = ownBusinessTerminalManager.query(page, Integer
				.parseInt(dataType_search), Integer
				.parseInt(timeLevel_search), startTime,endTime);
		int totalRowNum = gridServerHandler.getTotalRowNum();
		if (totalRowNum < 1) {
			totalRowNum = page.getTotalCount();
			gridServerHandler.setTotalRowNum(totalRowNum);
		}
		list = page.getResult();
			gridServerHandler.setData(list, CommonSport.class);
			Struts2Utils.renderJson(gridServerHandler.getLoadResponseText());
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

	}


	/**
	 * 数据导出
	 */
	public void export() throws ServletException, IOException {
		List<CommonSport> list = ownBusinessTerminalManager.listData(Integer
				.parseInt(dataType_search), Integer
				.parseInt(timeLevel_search), startTime,endTime);
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		String exportmsg = "终端区域分析"
				+ Common.getTimeLevelCH(timeLevel_search) + "（" + startTime
				+ "---"+endTime+"）";
		commonManagerImpl.log(this.getServletRequest(), exportmsg);
		gridServerHandler.exportXLS(list, CommonSport.class);
	}

	public String getStartTime() {
		return startTime;
	}


	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}


	public String getEndTime() {
		return endTime;
	}


	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}


	public Page<CommonSport> getPage() {
		return page;
	}

	public void setPage(Page<CommonSport> page) {
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

}
