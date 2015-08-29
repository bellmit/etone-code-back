package com.symbol.app.mantoeye.web.action.business;


import java.io.IOException;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import com.symbol.app.mantoeye.dto.sports.CommonSport;
import com.symbol.app.mantoeye.service.businessRule.WebSiteConfigureManager;
import com.symbol.app.mantoeye.util.MantoEyeUtils;
import com.symbol.wp.core.constants.MsgConstants;
import com.symbol.wp.core.constants.VarConstants;
import com.symbol.wp.core.service.ICommonManager;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.struts2.Struts2Utils;
import com.symbol.wp.modules.struts2.base.BaseDispatchAction;
import com.symbol.wp.tools.gtgrid.model.SortInfo;
import com.symbol.wp.tools.gtgrid.server.GridServerHandler;


/**
 * 搜索网站配置
 * 
 * 
 */
public class WebSiteConfigureAction extends BaseDispatchAction {
	@Autowired
	private WebSiteConfigureManager webSiteConfigure;
	@Resource(name = "commonManagerImpl")
	public ICommonManager commonManagerImpl;

	private Page<CommonSport> page = new Page<CommonSport>(
			VarConstants.PAGE_LONG_ROW_SIZE);

	private String businessName;
	private String ids;
	
	public String getIds() {
		return ids;
	}


	public void setIds(String ids) {
		this.ids = ids;
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
			page.setOrder(order);
			page.setOrderBy(MantoEyeUtils.getSortField(si.getFieldName()));
		} else {
			page.setOrder("desc");
			page.setOrderBy("nmSearchWedSiteId");
		}
		page.setPageSize(gridServerHandler.getPageSize());
		page.setPageNo(gridServerHandler.getPageInfo().getPageNum());
		page = webSiteConfigure.query(page,businessName);
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

	
	public void deleteBusiness() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		String msg = "";
		try {
			webSiteConfigure.deleteBusiness(ids);
			msg = "删除搜索网站成功!";
			commonManagerImpl.log(request, "删除搜索网站成功!");
		} catch (Exception e) { // 删除失败
			msg = "删除搜索网站失败!";
			logger.error(e.toString());
			logger.error(e.getMessage());
			commonManagerImpl.log(request, "删除搜索网站失败!");
		}
		Struts2Utils.renderText(msg);
	}
	
	/**
	 * 保存搜索网站
	 */
	public String saveSearchWebSite() throws ServletException, IOException {
		HttpServletRequest request = Struts2Utils.getRequest();
		String msg = null;
		String[] id = null;
		String businessId = null;
		String businessName = null;
		try {
			// 保存业务过滤值
			String businessIds = request.getParameter("businessIds");
			if (businessIds != null && !"".equals(businessIds)) {
				String[] datas = businessIds.split(",");
				List<CommonSport> list = webSiteConfigure.queryAllBussList();
				for (String ids : datas) {
					id = ids.split(":");
					businessId = id[1].trim();
					businessName = id[2].trim();
					boolean flag = true;
					if (list!=null && list.size()>0) {
						for (int i = 0; i < list.size(); i++) {
							String bussId = list.get(i).getBusinessId()+"";
							if (businessId.equals(bussId)) {
								flag = false;
								break;
							}
						}
					}
					if (flag) {
						webSiteConfigure.saveBussList(Common.StringToLong(businessId),businessName);
					}				
				}
			} 
			msg = "添加搜索网站成功!";
			request.setAttribute(VarConstants.SUCC_CODE,
					MsgConstants.SUCC_CODE_00101);
			commonManagerImpl.log(request, msg);
			return SUCCESS;
		} catch (Exception e) {
			logger.error(e.getMessage());
			logger.error(e.toString());
			logger.error(e.getMessage());
			request.setAttribute(VarConstants.ERROR_CODE,
					MsgConstants.ERROR_CODE_00004);
			msg = "添加搜索网站失败!";
			commonManagerImpl.log(request, msg);
			return ERROR;
		}
	}
	
	public Page<CommonSport> getPage() {
		return page;
	}


	public void setPage(Page<CommonSport> page) {
		this.page = page;
	}


	public String getBusinessName() {
		return businessName;
	}


	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}


	/**
	 * 数据导出
	 */
	public void export() throws ServletException, IOException {
		if (businessName != null) {
			businessName = new String(businessName.getBytes("ISO-8859-1"),
					"UTF-8");
		}
		List<CommonSport> list = webSiteConfigure.listData(businessName);
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		String exportmsg = "搜索网站";
		commonManagerImpl.log(this.getServletRequest(), exportmsg);
		gridServerHandler.exportXLS(list, CommonSport.class);
	}

	
}
