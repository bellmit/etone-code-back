package com.symbol.app.mantoeye.web.action.terminalmanager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.symbol.app.mantoeye.dto.sports.CommonSport;
import com.symbol.app.mantoeye.service.terminalmanager.CameraTrackBisManager;
import com.symbol.app.mantoeye.util.MantoEyeUtils;
import com.symbol.wp.core.constants.VarConstants;
import com.symbol.wp.core.service.ICommonManager;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.struts2.Struts2Utils;
import com.symbol.wp.modules.struts2.base.BaseDispatchAction;
import com.symbol.wp.tools.gtgrid.model.SortInfo;
import com.symbol.wp.tools.gtgrid.org.json.JSONArray;
import com.symbol.wp.tools.gtgrid.server.GridServerHandler;
import com.symbol.wp.tools.gtgrid.util.JSONUtils;

public class CameraTrackBisAction extends BaseDispatchAction {

	HttpServletRequest request = ServletActionContext.getRequest();
	private Page<CommonSport> page = new Page<CommonSport>(
			VarConstants.PAGE_LONG_ROW_SIZE);
	
	private String terminal;
	private String vcCGI;
	
	@Autowired
	private CameraTrackBisManager cameraTrackBisManager  ;
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
			page.setOrderBy("statdate");
			page.setOrder("desc");
		}
		page.setPageSize(gridServerHandler.getPageSize());
		page.setPageNo(gridServerHandler.getPageInfo().getPageNum());
		page = cameraTrackBisManager.query(page, terminal);
		int totalRowNum = gridServerHandler.getTotalRowNum();
		if (totalRowNum < 1) {
			totalRowNum = page.getTotalCount();
			gridServerHandler.setTotalRowNum(totalRowNum);
		}
		list = page.getResult();
		
		List<CommonSport> listall = cameraTrackBisManager.listData(terminal);
		buildChartJson(listall);
		gridServerHandler.setData(list, CommonSport.class);
		Struts2Utils.renderJson(gridServerHandler.getLoadResponseText());
	}

	
	
	private void buildChartJson(List<CommonSport> list) {
		String chartJson = " ";
		try {
			if (list != null && list.size() > 0) {
			} else {
				list = new ArrayList<CommonSport>();
			}
			JSONArray ja = JSONUtils
					.BeanList2JSONArray(list, CommonSport.class);
			chartJson = ja.toString();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		this.getServletRequest().getSession().setAttribute(
				"terminalupgrade", chartJson);
	}

	/**
	 * 获取图形数据
	 */
	public void getAjaxChartData() throws Exception {
		String chartJson = this.getServletRequest().getSession().getAttribute(
				"terminalupgrade")
				+ "";
		 this.getServletRequest().getSession().removeAttribute("terminalupgrade");
		Struts2Utils.renderJson(chartJson);
	}
	
	
	
	
	public String getVcCGI() {
		return vcCGI;
	}



	public void setVcCGI(String vcCGI) {
		this.vcCGI = vcCGI;
	}



	public Page<CommonSport> getPage() {
		return page;
	}

	public void setPage(Page<CommonSport> page) {
		this.page = page;
	}

	public String getTerminal() {
		return terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}
	
}
