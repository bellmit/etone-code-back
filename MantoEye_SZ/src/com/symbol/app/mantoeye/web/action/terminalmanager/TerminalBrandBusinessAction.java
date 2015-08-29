package com.symbol.app.mantoeye.web.action.terminalmanager;



import java.io.IOException;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.symbol.app.mantoeye.dto.sports.CommonSport;
import com.symbol.app.mantoeye.service.terminalmanager.TerminalBrandBusinessManager;
import com.symbol.app.mantoeye.util.MantoEyeUtils;
import com.symbol.wp.core.constants.VarConstants;
import com.symbol.wp.core.service.ICommonManager;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.struts2.Struts2Utils;
import com.symbol.wp.tools.gtgrid.model.SortInfo;
import com.symbol.wp.tools.gtgrid.server.GridServerHandler;

public class TerminalBrandBusinessAction {

	@Autowired
	private TerminalBrandBusinessManager terminalBrandBusinessManager;
	@Resource(name = "commonManagerImpl")
	public ICommonManager commonManagerImpl;

	private Page<CommonSport> page = new Page<CommonSport>(
			VarConstants.PAGE_LONG_ROW_SIZE);

	private String dataType_search;// 默认GPRS
	private String timeLevel_search;// 默认天
	private String time_search;// 开始时间
	private int areaType;
	private Long nmAreaId;
	private String vcTerminalBrand;

	
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 查询数据
	 */
	public void query() throws ServletException, IOException {
		List<CommonSport> list = null;
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

		page = terminalBrandBusinessManager.query(page, Integer
				.parseInt(dataType_search), Integer
				.parseInt(timeLevel_search), time_search, areaType,nmAreaId,vcTerminalBrand);

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
		if (vcTerminalBrand!=null) {
			vcTerminalBrand = new String(vcTerminalBrand.getBytes("ISO-8859-1"),"UTF-8");
		}
		List<CommonSport> list = terminalBrandBusinessManager.listData(Integer
				.parseInt(dataType_search), Integer
				.parseInt(timeLevel_search), time_search, areaType,nmAreaId,vcTerminalBrand);
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		String exportmsg = "终端品牌业务分析"
			+ Common.getTimeLevelCH(timeLevel_search) + "（" + time_search+"）";
		commonManagerImpl.log(Struts2Utils.getRequest(), exportmsg);

		gridServerHandler.exportXLS(list, CommonSport.class);
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


	public String getVcTerminalBrand() {
		return vcTerminalBrand;
	}


	public void setVcTerminalBrand(String vcTerminalBrand) {
		this.vcTerminalBrand = vcTerminalBrand;
	}


	

}
