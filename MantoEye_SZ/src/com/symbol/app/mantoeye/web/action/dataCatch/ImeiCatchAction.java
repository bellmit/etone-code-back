package com.symbol.app.mantoeye.web.action.dataCatch;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.symbol.app.mantoeye.entity.FtbImeiRecord;
import com.symbol.app.mantoeye.service.ImeiCatchManager;
import com.symbol.wp.core.constants.VarConstants;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.orm.PropertyFilter;
import com.symbol.wp.modules.orm.hibernate.HibernateWebUtils;
import com.symbol.wp.modules.struts2.Struts2Utils;
import com.symbol.wp.modules.struts2.base.BaseDispatchAction;
import com.symbol.wp.tools.gtgrid.server.GridServerHandler;

public class ImeiCatchAction extends BaseDispatchAction{
	
	@Autowired
	private ImeiCatchManager imeiCatchManager;
	
	private Page<FtbImeiRecord> page = new Page<FtbImeiRecord>(
			VarConstants.PAGE_LONG_ROW_SIZE, true);

	public Page<FtbImeiRecord> getPage() {
		return page;
	}

	public void setPage(Page<FtbImeiRecord> page) {
		this.page = page;
	}

	public void query() throws ServletException, IOException {
		HttpServletRequest request = Struts2Utils.getRequest();
		List<FtbImeiRecord> list = null;
		List<PropertyFilter> filters = HibernateWebUtils
				.buildPropertyFilters(Struts2Utils.getRequest());
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		page.setOrderBy("mmImeiRecordId");
		page.setOrder("desc");
		page.setPageSize(gridServerHandler.getPageSize());
		page.setPageNo(gridServerHandler.getPageInfo().getPageNum());
		page = imeiCatchManager.search(page, filters);
		int totalRowNum = gridServerHandler.getTotalRowNum();
		if (totalRowNum < 1) {
			totalRowNum = page.getTotalCount();
			gridServerHandler.setTotalRowNum(totalRowNum);
		}
		list = page.getResult();
		gridServerHandler.setData(this.formatViewData(list));
		Struts2Utils.renderJson(gridServerHandler.getLoadResponseText());
	}
	/**
	 * 页面视图数据
	 */
	private List formatViewData(List<FtbImeiRecord> list) {
		List maplist = new ArrayList();
		Map map;
		FtbImeiRecord bean;
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				bean = list.get(i);
				map = new LinkedHashMap();
				map.put("mmImeiRecordId", bean.getNmImeiRecordId());
				map.put("nmImei", bean.getNmImei());
				map.put("dtLastDate", bean.getDtLastDate());
				map.put("intRaitype", bean.getIntRaitype());
			}
		}
		return maplist;
	}
	
	/**
	 * 数据导出
	 */
	public void export() throws ServletException, IOException {
		
//		List<CommonFlush> list=imeiCatchManager.queryUserAttachDistribute(Common.StringToInt(timeLevel), searchDateStart, dataType,apnName);
//		GridServerHandler gridServerHandler = new GridServerHandler(
//				Struts2Utils.getRequest(), Struts2Utils.getResponse());
//		gridServerHandler.exportXLS(list, CommonFlush.class);
	}

}
