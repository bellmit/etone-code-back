package com.symbol.app.mantoeye.web.action.dataAnalyse;


import java.io.IOException;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import com.symbol.app.mantoeye.dto.sports.CommonSport;
import com.symbol.app.mantoeye.service.businessAnalyse.CompetitorBusinessManager;
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
 * 竞争对手业务分析
 * 
 * 
 */
public class CompetitorBusinessAction extends BaseDispatchAction {
	@Autowired
	private CompetitorBusinessManager competitorBusinessManager;
	@Resource(name = "commonManagerImpl")
	public ICommonManager commonManagerImpl;

	private Page<CommonSport> page = new Page<CommonSport>(
			VarConstants.PAGE_LONG_ROW_SIZE);

	private String dataType_search;
	private String time_search;
	private String businessName;
	private String spaceLevel_search;
	private String timeLevel_search;
	private String business;
	private Long bussinessId;



	public Long getBussinessId() {
		return bussinessId;
	}


	public void setBussinessId(Long bussinessId) {
		this.bussinessId = bussinessId;
	}


	private String vcName;
	public String getVcName() {
		return vcName;
	}


	public void setVcName(String vcName) {
		this.vcName = vcName;
	}


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
		String bussId=bussinessId+"";
		List competitorList = competitorBusinessManager.queryCompetitor(bussinessId);
		if (competitorList!=null && competitorList.size()>0) {
			for (int j = 0; j < competitorList.size(); j++) {
				String bid = competitorList.get(j).toString();
				bussId = bussId+","+bid;
			}
			
		}
		page.setPageSize(gridServerHandler.getPageSize());
		page.setPageNo(gridServerHandler.getPageInfo().getPageNum());
		page = competitorBusinessManager.query(page, Integer
				.parseInt(dataType_search), Integer
				.parseInt(timeLevel_search), time_search,Integer.parseInt(spaceLevel_search), businessName,vcName,bussId);
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


	public String getTime_search() {
		return time_search;
	}


	public void setTime_search(String timeSearch) {
		time_search = timeSearch;
	}


	public String getBusinessName() {
		return businessName;
	}


	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}


	public String getSpaceLevel_search() {
		return spaceLevel_search;
	}


	public void setSpaceLevel_search(String spaceLevelSearch) {
		spaceLevel_search = spaceLevelSearch;
	}


	public String getTimeLevel_search() {
		return timeLevel_search;
	}


	public void setTimeLevel_search(String timeLevelSearch) {
		timeLevel_search = timeLevelSearch;
	}


	public String getBusiness() {
		return business;
	}


	public void setBusiness(String business) {
		this.business = business;
	}


	/**
	 * 数据导出
	 */
	public void export() throws ServletException, IOException {
		if (businessName!=null) {
			businessName = new String(businessName.getBytes("ISO-8859-1"),"UTF-8");
		}
		String bussId=bussinessId+"";
		List competitorList = competitorBusinessManager.queryCompetitor(bussinessId);
		if (competitorList!=null && competitorList.size()>0) {
			for (int j = 0; j < competitorList.size(); j++) {
				Object[] objs = (Object[]) competitorList.get(j);
				bussId = bussId+","+objs[0];
			}
			
		}
		List<CommonSport> list = competitorBusinessManager.listData(Integer
				.parseInt(dataType_search), Integer
				.parseInt(timeLevel_search), time_search,Integer.parseInt(spaceLevel_search),businessName,vcName,bussId);
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		String exportmsg = "终端区域分析"
				+ Common.getTimeLevelCH(timeLevel_search) + "（" + time_search+"）";
		commonManagerImpl.log(this.getServletRequest(), exportmsg);
		gridServerHandler.exportXLS(list, CommonSport.class);
	}
}
