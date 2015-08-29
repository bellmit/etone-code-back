package com.symbol.app.mantoeye.web.action.terminalmanager;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import com.symbol.app.mantoeye.dto.sports.CommonSport;
import com.symbol.app.mantoeye.service.terminalmanager.FlushLevelManager;
import com.symbol.app.mantoeye.util.CommonUtils;
import com.symbol.app.mantoeye.util.MantoEyeUtils;
import com.symbol.wp.core.constants.VarConstants;
import com.symbol.wp.core.service.ICommonManager;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.struts2.Struts2Utils;
import com.symbol.wp.modules.struts2.base.BaseDispatchAction;
import com.symbol.wp.tools.gtgrid.model.SortInfo;
import com.symbol.wp.tools.gtgrid.server.GridServerHandler;

public class FlushLevelAction extends BaseDispatchAction {

	HttpServletRequest request = ServletActionContext.getRequest();
	private Page<CommonSport> page = new Page<CommonSport>(
			VarConstants.PAGE_LONG_ROW_SIZE);
	@Autowired
	private FlushLevelManager flushLevelManager;
	@Autowired
	@Qualifier("commonManagerImpl")
	private ICommonManager commonManagerImpl;
	
	private String time_search=CommonUtils.getSYestoday();
	private int timeLevel_search=0;
	
	
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
			page.setOrderBy("nmStatFlushLayeringId");
			page.setOrder("asc");
		}
		page.setPageSize(gridServerHandler.getPageSize());
		page.setPageNo(gridServerHandler.getPageInfo().getPageNum());
		page = flushLevelManager.query(page, time_search,timeLevel_search);
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
		list = flushLevelManager.listData(time_search,timeLevel_search);
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		String exportmsg = "流量分层数据";
		commonManagerImpl.log(Struts2Utils.getRequest(), exportmsg);
		gridServerHandler.exportXLS(list, CommonSport.class);
	}




	public String getTime_search() {
		return time_search;
	}




	public void setTime_search(String timeSearch) {
		time_search = timeSearch;
	}




	public int getTimeLevel_search() {
		return timeLevel_search;
	}




	public void setTimeLevel_search(int timeLevelSearch) {
		timeLevel_search = timeLevelSearch;
	}
	
	
}
