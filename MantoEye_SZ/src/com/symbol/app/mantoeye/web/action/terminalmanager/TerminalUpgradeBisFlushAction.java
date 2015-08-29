package com.symbol.app.mantoeye.web.action.terminalmanager;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.symbol.app.mantoeye.dto.sports.CommonSport;
import com.symbol.app.mantoeye.service.terminalmanager.TerminalUpgradeBisFlushManager;
import com.symbol.wp.core.constants.VarConstants;
import com.symbol.wp.core.service.ICommonManager;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.struts2.Struts2Utils;
import com.symbol.wp.modules.struts2.base.BaseDispatchAction;
import com.symbol.wp.tools.gtgrid.model.SortInfo;
import com.symbol.wp.tools.gtgrid.server.GridServerHandler;

public class TerminalUpgradeBisFlushAction extends BaseDispatchAction {

	HttpServletRequest request = ServletActionContext.getRequest();
	private Page<CommonSport> page = new Page<CommonSport>(
			VarConstants.PAGE_LONG_ROW_SIZE);
	
	private int taskId;
	private int dataType;
	
	@Autowired
	private TerminalUpgradeBisFlushManager terminalUpgradeBisFlushManager ;
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
			page.setOrderBy(getSortField(si.getFieldName()));

		} else {
			page.setOrderBy("statdate");
			page.setOrder("desc");
		}
		page.setPageSize(gridServerHandler.getPageSize());
		page.setPageNo(gridServerHandler.getPageInfo().getPageNum());
		page = terminalUpgradeBisFlushManager.query(page, taskId,dataType);
		int totalRowNum = gridServerHandler.getTotalRowNum();
		if (totalRowNum < 1) {
			totalRowNum = page.getTotalCount();
			gridServerHandler.setTotalRowNum(totalRowNum);
		}
		list = page.getResult();
		
		gridServerHandler.setData(list, CommonSport.class);
		Struts2Utils.renderJson(gridServerHandler.getLoadResponseText());
	}
	
	private String getSortField(String sort) {
		if (sort.indexOf("nmFlush") != -1) {
			return "nmFlush";
		}
		if (sort.indexOf("nmUsers") != -1) {
			return "intUserNum";
		}
		return sort;
	}


	/**
	 * 数据导出
	 */
	public void export() throws ServletException, IOException {
		List<CommonSport> list = null;
		list = terminalUpgradeBisFlushManager.listData(taskId,dataType);
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		String exportmsg = "终端升级业务统计数据";
		commonManagerImpl.log(Struts2Utils.getRequest(), exportmsg);
		gridServerHandler.exportXLS(list, CommonSport.class);
	}
	
	
	public Page<CommonSport> getPage() {
		return page;
	}

	public void setPage(Page<CommonSport> page) {
		this.page = page;
	}



	public int getDataType() {
		return dataType;
	}


	public void setDataType(int dataType) {
		this.dataType = dataType;
	}


	public int getTaskId() {
		return taskId;
	}



	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}


}
