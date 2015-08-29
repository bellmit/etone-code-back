package com.symbol.app.mantoeye.web.action.business;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.symbol.app.mantoeye.constants.ExportMsg;
import com.symbol.app.mantoeye.entity.business.FtbAssistVetorRecognise;
import com.symbol.app.mantoeye.service.businessRule.AssistVetorRecogniseManager;
import com.symbol.app.mantoeye.util.CommonUtils;
import com.symbol.wp.core.constants.VarConstants;
import com.symbol.wp.core.service.ICommonManager;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.orm.PropertyFilter;
import com.symbol.wp.modules.orm.PropertyFilter.MatchType;
import com.symbol.wp.modules.orm.hibernate.HibernateWebUtils;
import com.symbol.wp.modules.struts2.Struts2Utils;
import com.symbol.wp.modules.struts2.base.BaseDispatchAction;
import com.symbol.wp.tools.gtgrid.model.SortInfo;
import com.symbol.wp.tools.gtgrid.server.GridServerHandler;

/**
 * 全量业务自发现
 * 
 * @author rankin
 * 
 */
public class AssistVetorRecogniseAction extends BaseDispatchAction {

	@Autowired
	private AssistVetorRecogniseManager assistVetorRecogniseManager;
	@Resource(name = "commonManagerImpl")
	public ICommonManager commonManagerImpl;

	private String exportFileName;

	private String startDate = CommonUtils.getYestodayDate() + " 00:00:00";

	private String endDate = CommonUtils.getYestodayDate() + " 23:59:59";

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getExportFileName() {
		return exportFileName;
	}

	public void setExportFileName(String exportFileName) {
		this.exportFileName = exportFileName;
	}

	HttpServletRequest request = ServletActionContext.getRequest();
	private Page<FtbAssistVetorRecognise> page = new Page<FtbAssistVetorRecognise>(
			VarConstants.PAGE_LONG_ROW_SIZE, true);

	public Page<FtbAssistVetorRecognise> getPage() {
		return page;
	}

	public void setPage(Page<FtbAssistVetorRecognise> page) {
		this.page = page;
	}

	public void list() {
		// setPaginationdataList();

		List<FtbAssistVetorRecognise> list = null;
		List<PropertyFilter> filters = HibernateWebUtils
				.buildPropertyFilters(Struts2Utils.getRequest());
		if (startDate != null) {
			filters.add(new PropertyFilter("dtFirstTime", Common
					.getDate(startDate), MatchType.GE));

		}
		if (endDate != null) {
			filters.add(new PropertyFilter("dtFirstTime", Common
					.getDate(endDate), MatchType.LE));
		}
		logger.info("start_date:" + startDate + " end_date:" + endDate);
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		SortInfo si = gridServerHandler.getSingleSortInfo();
		if (si != null) {
			page.setOrderBy(si.getFieldName());
			page.setOrder(si.getSortOrder());
		} else {
			page.setOrderBy("dtFirstTime");
			page.setOrder("desc");
		}
		page.setPageSize(gridServerHandler.getPageSize());
		page.setPageNo(gridServerHandler.getPageInfo().getPageNum());

		// list = businessSiteManager.getAll();
		page = assistVetorRecogniseManager.search(page, filters);
		int totalRowNum = gridServerHandler.getTotalRowNum();
		if (totalRowNum < 1) {
			totalRowNum = page.getTotalCount();
			gridServerHandler.setTotalRowNum(totalRowNum);
		}
		list = page.getResult();
		logger.info("----list:" + list.size());
		gridServerHandler.setData(this.formatViewData(list));
		Struts2Utils.renderJson(gridServerHandler.getLoadResponseText());
	}

	/**
	 * 导出
	 */
	public void export() throws ServletException, IOException {

		List<FtbAssistVetorRecognise> list = null;
		List<PropertyFilter> filters = HibernateWebUtils
				.buildPropertyFilters(Struts2Utils.getRequest());
		if (startDate != null) {
			filters.add(new PropertyFilter("dtFirstTime", Common
					.getDate(startDate), MatchType.GE));

		}
		if (endDate != null) {
			filters.add(new PropertyFilter("dtFirstTime", Common
					.getDate(endDate), MatchType.LE));
		}
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		SortInfo si = gridServerHandler.getSingleSortInfo();
		if (si != null) {
			page.setOrderBy(si.getFieldName());
			page.setOrder(si.getSortOrder());
		} else {
			page.setOrderBy("dtFirstTime");
			page.setOrder("desc");
		}
		// list = buildJsonData();
		list = assistVetorRecogniseManager.find(filters);
		String exportmsg = ExportMsg.EXPORT_BUSINESS_RECOGNISE + "("
				+ startDate + "~" + endDate + ")";
		commonManagerImpl.log(request, exportmsg);
		gridServerHandler.exportXLSfromMaps(this.formatViewData(list));

	}

	// /**
	// *
	// */
	// public void getDataCount(){
	//		
	//		
	// List<FtbAssistVetorRecognise> list = null;
	// List<PropertyFilter> filters = HibernateWebUtils
	// .buildPropertyFilters(Struts2Utils.getRequest());
	// if(startDate!=null){
	// filters.add(new
	// PropertyFilter("dtFirstTime",Common.getDate(startDate),MatchType.GE));
	//			
	// }
	// if(endDate!=null){
	// filters.add(new
	// PropertyFilter("dtFirstTime",Common.getDate(endDate),MatchType.LE));
	// }
	// GridServerHandler gridServerHandler = new GridServerHandler(
	// Struts2Utils.getRequest(), Struts2Utils.getResponse());
	// SortInfo si = gridServerHandler.getSingleSortInfo();
	// if (si != null) {
	// page.setOrderBy(si.getFieldName());
	// page.setOrder(si.getSortOrder());
	// } else {
	// page.setOrderBy("dtFirstTime");
	// page.setOrder("desc");
	// }
	// //list = buildJsonData();
	// int size = assistVetorRecogniseManager.find(filters).size();
	// Struts2Utils.renderText(size+"");
	// }
	/**
	 * 格式化页面GRID所需列表数据
	 * 
	 * @param list
	 * @return
	 */
	private List formatViewData(List list) {
		logger.info(list.size() + "---size---");
		Map map = null;
		List maplist = new ArrayList();
		FtbAssistVetorRecognise bean;
		logger.info((list != null && list.size() > 0) + "---size---");
		try {
			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					bean = (FtbAssistVetorRecognise) list.get(i);
					map = new LinkedHashMap();
					map.put("getNmAssistVetorRecogniseId", bean
							.getNmAssistVetorRecogniseId());
					map.put("vcBussinessName", CommonUtils.killNull(bean
							.getDtbBusinessSite().getVcBussinessName()));

					map.put("firstTime", CommonUtils.killNull(CommonUtils
							.formatFullDate(bean.getDtFirstTime())));//
					map.put("lastTime", CommonUtils.killNull(CommonUtils
							.formatFullDate(bean.getDtLastTime())));//

					map.put("ip", CommonUtils.killNull(bean.getVcServerIp()));
					map.put("port", CommonUtils.killNull(bean.getIntPort()));
					map.put("apn", CommonUtils.killNull(bean.getVcApn()));
					map.put("url", CommonUtils.killNull(bean.getVcUrl()));
					map.put("userAgent", CommonUtils.killNull(bean
							.getVcUserAgent()));
					maplist.add(map);
				}
				logger.info(map.toString());
			}

		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return maplist;
	}

}
