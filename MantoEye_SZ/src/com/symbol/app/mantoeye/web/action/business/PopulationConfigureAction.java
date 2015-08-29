package com.symbol.app.mantoeye.web.action.business;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import com.symbol.app.mantoeye.entity.FtbResidentConfig;
import com.symbol.app.mantoeye.service.businessRule.PopulationConfigureManager;
import com.symbol.wp.core.constants.MsgConstants;
import com.symbol.wp.core.constants.VarConstants;
import com.symbol.wp.core.service.ICommonManager;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.struts2.Struts2Utils;
import com.symbol.wp.modules.struts2.base.BaseDispatchAction;
import com.symbol.wp.tools.gtgrid.server.GridServerHandler;

public class PopulationConfigureAction extends BaseDispatchAction {

	@Autowired
	private PopulationConfigureManager populationConfigureManager;
	HttpServletRequest request = ServletActionContext.getRequest();
	private Page<FtbResidentConfig> page = new Page<FtbResidentConfig>(
			VarConstants.PAGE_LONG_ROW_SIZE, true);
	@Autowired
	@Qualifier("commonManagerImpl")
	private ICommonManager commonManagerImpl;
	
	
	/**
	 * 分页查询任务
	 */
	public void query() {	
		 GridServerHandler gridServerHandler = new GridServerHandler(
			      Struts2Utils.getRequest(), Struts2Utils.getResponse());
		 this.page.setPageSize(gridServerHandler.getPageSize());
		 this.page.setPageNo(gridServerHandler.getPageInfo().getPageNum());
		 this.page=populationConfigureManager.queryEntity(page);
		 int totalRowNum = gridServerHandler.getTotalRowNum();
		    if (totalRowNum < 1) {
		      totalRowNum = this.page.getTotalCount();
		      gridServerHandler.setTotalRowNum(totalRowNum);
		    }
		 List<FtbResidentConfig> list = page.getResult();
		 gridServerHandler.setData(list, FtbResidentConfig.class);
		 Struts2Utils.renderJson(gridServerHandler.getLoadResponseText(), new String[0]);
		 
	}
	

	
	
	/**
	 * 判断该任务是否存在
	 */
	public String checkUnique() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String intType = request.getParameter("intType");			
			int i=populationConfigureManager.isIntType(Integer.parseInt(intType));
			if(i==-1){//-1表示不存在该任务名称
				Struts2Utils.renderText("true");
			}else{
				Struts2Utils.renderText("false");
			}
		return null;
	}
	
	public String  saveConfigure() throws Exception{
		String intType=request.getParameter("intType");
		String intDay=request.getParameter("intDay");
		String vcNote=request.getParameter("vcNote");
		String msg="";
		if (intType==null || intDay==null) {
			msg = "添加常驻人口配置出错，请与管理员联系!";
			commonManagerImpl.log(request, msg);
			Struts2Utils.renderText(msg);
			request.setAttribute(VarConstants.ERROR_CODE,
					MsgConstants.ERROR_CODE_00004);
			return ERROR;
		}		
		try{
			
			msg = "添加常驻人口配置成功!";
			populationConfigureManager.insertConfigure(Integer.parseInt(intType),Integer.parseInt(intDay),vcNote);
			commonManagerImpl.log(request, msg);
			request.setAttribute(VarConstants.SUCC_CODE,
					MsgConstants.SUCC_CODE_00101);
			return SUCCESS;
		}catch(Exception e){
			logger.error(e.getMessage());
			msg = "添加常驻人口配置失败!";
			commonManagerImpl.log(request, msg);
			Struts2Utils.renderText(msg);
			request.setAttribute(VarConstants.ERROR_CODE,
					MsgConstants.ERROR_CODE_00004);
			return ERROR;
		}
		
	}
	

	
	
	/**
	 * 删除任务方法
	 */
	public void deleteConfigure(){
		String nmResidentIds=request.getParameter("nmResidentIds");
		String msg="";
		try{
			
			msg = "删除常驻人口配置成功!";
			populationConfigureManager.deleteConfigure(nmResidentIds);
			commonManagerImpl.log(request, msg);
			Struts2Utils.renderText(msg);
		}catch(Exception e){
			logger.error(e.getMessage());
			msg = "删除常驻人口配置失败!";
			commonManagerImpl.log(request, msg);
			Struts2Utils.renderText(msg);
		}
	}
	
	


	

	public String updateConfigure() throws Exception{
		String nmResidentId=request.getParameter("nmResidentId");
		String intDay=request.getParameter("intDay");
		String vcNote=request.getParameter("vcNote");
		String msg="";
		if (nmResidentId==null || intDay==null) {
			msg = "编辑常驻人口配置出错，请与管理员联系!";
			commonManagerImpl.log(request, msg);
			Struts2Utils.renderText(msg);
			request.setAttribute(VarConstants.ERROR_CODE,
					msg);
			return ERROR;
		}
		try{		
			msg = "更新常驻人口配置成功!";
			populationConfigureManager.updateConfigure(Long.parseLong(nmResidentId),Integer.parseInt(intDay),vcNote);
			commonManagerImpl.log(request, msg);
			request.setAttribute(VarConstants.SUCC_CODE,
					MsgConstants.SUCC_CODE_00102);
			return SUCCESS;
		}catch(Exception e){
			logger.error(e.getMessage());
			msg = "更新常驻人口配置失败!";
			commonManagerImpl.log(request, msg);
			Struts2Utils.renderText(msg);
			request.setAttribute(VarConstants.ERROR_CODE,
					msg);
			return ERROR;
		}
	}
	
	public String edit() throws Exception {
		return EDIT;
	}
}
