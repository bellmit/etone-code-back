package com.symbol.app.mantoeye.web.action.dataAnalyse;


import java.io.IOException;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import com.symbol.app.mantoeye.dto.sports.CommonSport;
import com.symbol.app.mantoeye.service.businessAnalyse.CompetitorBusinessConfigureManager;
import com.symbol.app.mantoeye.service.businessAnalyse.OwnBusinessConfigureManager;
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
 * 重要业务配置
 * 
 * 
 */
public class CompetitorBusinessConfigureAction extends BaseDispatchAction {
	@Autowired
	private CompetitorBusinessConfigureManager competitorBusinessConfigure;
	@Resource(name = "commonManagerImpl")
	public ICommonManager commonManagerImpl;

	private Page<CommonSport> page = new Page<CommonSport>(
			VarConstants.PAGE_LONG_ROW_SIZE);

	private String businessName;
	private Long id;
	



	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	
	/**
	 * 保存竞争对手业务
	 */
	public String saveBussList() throws ServletException, IOException {
		HttpServletRequest request = Struts2Utils.getRequest();
		String msg = null;
		String[] bussIds = null;
		String businessId = null;
		try {
			// 保存业务过滤值
			String businessIds = request.getParameter("businessIds");
			if (businessIds != null && !"".equals(businessIds)) {
				String[] datas = businessIds.split(",");
				competitorBusinessConfigure.delBussListByBussId(id);
				for (String ids : datas) {
					bussIds = ids.split(":");
					businessId = bussIds[1].trim();
					competitorBusinessConfigure.saveBussList(Common.StringToLong(businessId),id);
				}
			} 
			msg = "添加竞争对手业务成功!";
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
			msg = "添加竞争对手业务失败!";
			commonManagerImpl.log(request, msg);
			return ERROR;
		}
	}
	
	/**
	 * 编辑任务
	 */
	public String competitorBusiness() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		String business = competitorBusinessConfigure.queryBussIdAndBussIdTypeByTaskId(id);
		if (business != null) {
			String[] businesses = business.split("@");
			request.setAttribute("business", businesses[1]);
			request.setAttribute("businessIds", businesses[0]);
		}
		List list = competitorBusinessConfigure.findBussId(id);
		if (list!=null && list.size()>0) {
			String bussId = list.get(0).toString();
			id = Common.StringToLong(bussId);
		}
		return "competitor";
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

	
}
