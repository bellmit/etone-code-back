package com.symbol.app.mantoeye.web.action.terminalmanager;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import com.symbol.app.mantoeye.dto.sports.CommonSport;
import com.symbol.app.mantoeye.service.terminalmanager.TerminalSystemManager;
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

public class TerminalSystemAction extends BaseDispatchAction {

	HttpServletRequest request = ServletActionContext.getRequest();
	private Page<CommonSport> page = new Page<CommonSport>(
			VarConstants.PAGE_LONG_ROW_SIZE);
	private String timeLevel="2";
	private String startTime=CommonUtils.getSYestoday();
	private String areaType="1";

	
	@Autowired
	private TerminalSystemManager terminalSystemManager;
	@Autowired
	@Qualifier("commonManagerImpl")
	private ICommonManager commonManagerImpl;
	
	
	public void query() throws ServletException, IOException {

		List<CommonSport> list = null;
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		SortInfo si = gridServerHandler.getSingleSortInfo();
		if (si != null) {
			String order = si.getSortOrder();
			page.setOrder(order);
			page.setOrderBy(MantoEyeUtils.getSortField(si.getFieldName()));

		} else {
			page.setOrderBy("nmFlush");
			page.setOrder("desc");
		}
		page.setPageSize(gridServerHandler.getPageSize());
		page.setPageNo(gridServerHandler.getPageInfo().getPageNum());
		page = terminalSystemManager.query(page,Common.StringToInt(  timeLevel),  startTime,Common.StringToInt( areaType));
		int totalRowNum = gridServerHandler.getTotalRowNum();
		if (totalRowNum < 1) {
			totalRowNum = page.getTotalCount();
			gridServerHandler.setTotalRowNum(totalRowNum);
		}
		list = page.getResult();
		
		gridServerHandler.setData(list, CommonSport.class);
		Struts2Utils.renderJson(gridServerHandler.getLoadResponseText());
	}

	
	

	/**
	 * 数据导出
	 */
	public void export() throws ServletException, IOException {
		List<CommonSport> list = null;
		list = terminalSystemManager.listData(Common.StringToInt(  timeLevel),  startTime,Common.StringToInt( areaType));
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		String exportmsg = "智能终端系统分析";
		commonManagerImpl.log(Struts2Utils.getRequest(), exportmsg);
		gridServerHandler.exportXLS(list, CommonSport.class);
	}
	
	
	
	public String getTimeLevel() {
		return timeLevel;
	}



	public void setTimeLevel(String timeLevel) {
		this.timeLevel = timeLevel;
	}



	public String getStartTime() {
		return startTime;
	}



	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}



	public String getAreaType() {
		return areaType;
	}



	public void setAreaType(String areaType) {
		this.areaType = areaType;
	}



	public Page<CommonSport> getPage() {
		return page;
	}

	public void setPage(Page<CommonSport> page) {
		this.page = page;
	}

	
}
