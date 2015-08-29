package com.symbol.app.mantoeye.web.action.terminalmanager;



import java.io.IOException;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import com.symbol.app.mantoeye.dto.sports.CommonSport;
import com.symbol.app.mantoeye.service.terminalmanager.TerminalTypeBrandManager;
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
public class TerminalTypeBrandAction extends BaseDispatchAction {
	@Autowired
	private TerminalTypeBrandManager terminalTypeBrandManager;
	@Resource(name = "commonManagerImpl")
	public ICommonManager commonManagerImpl;

	private Page<CommonSport> page = new Page<CommonSport>(
			VarConstants.PAGE_LONG_ROW_SIZE);

	private String dataType_search;// 默认GPRS
	private String timeLevel_search;// 默认天
	private String time_search;// 开始时间
	private int areaType;
	private Long nmAreaId;
	private String vcTerminalNetType;

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
				page.setOrderBy("nmFlush");
			} else {
				page.setOrder(order);
				page.setOrderBy(MantoEyeUtils.getSortField(si.getFieldName()));
			} // 默认排序方式
		} else {
			page.setOrderBy("nmFlush");
			page.setOrder("desc");
		}
		page.setPageSize(gridServerHandler.getPageSize());
		page.setPageNo(gridServerHandler.getPageInfo().getPageNum());
		page = terminalTypeBrandManager.query(page, Integer
				.parseInt(dataType_search), Integer
				.parseInt(timeLevel_search), time_search, areaType,nmAreaId,vcTerminalNetType);
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


	public String getDataType_search() {
		return dataType_search;
	}


	public void setDataType_search(String dataTypeSearch) {
		dataType_search = dataTypeSearch;
	}


	public String getTimeLevel_search() {
		return timeLevel_search;
	}


	public void setTimeLevel_search(String timeLevelSearch) {
		timeLevel_search = timeLevelSearch;
	}


	public String getTime_search() {
		return time_search;
	}


	public void setTime_search(String timeSearch) {
		time_search = timeSearch;
	}


	public int getAreaType() {
		return areaType;
	}


	public void setAreaType(int areaType) {
		this.areaType = areaType;
	}





	public Long getNmAreaId() {
		return nmAreaId;
	}


	public void setNmAreaId(Long nmAreaId) {
		this.nmAreaId = nmAreaId;
	}


	public String getVcTerminalNetType() {
		return vcTerminalNetType;
	}


	public void setVcTerminalNetType(String vcTerminalNetType) {
		this.vcTerminalNetType = vcTerminalNetType;
	}


	/**
	 * 数据导出
	 */
	public void export() throws ServletException, IOException {
		if (vcTerminalNetType!=null) {
			vcTerminalNetType = new String(vcTerminalNetType.getBytes("ISO-8859-1"),"UTF-8");
		}
		List<CommonSport> list = terminalTypeBrandManager.listData(Integer
				.parseInt(dataType_search), Integer
				.parseInt(timeLevel_search), time_search, areaType,nmAreaId,vcTerminalNetType);
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		String exportmsg = "终端类型品牌分析"
			+ Common.getTimeLevelCH(timeLevel_search) + "（" + time_search+"）";
		commonManagerImpl.log(this.getServletRequest(), exportmsg);
		gridServerHandler.exportXLS(list, CommonSport.class);
	}

	

}
