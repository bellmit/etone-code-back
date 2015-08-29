package com.symbol.app.mantoeye.web.action.sports;



import java.io.IOException;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import com.symbol.app.mantoeye.dto.sports.CommonSport;
import com.symbol.app.mantoeye.service.sports.AreaMapCellManager;
import com.symbol.app.mantoeye.util.MantoEyeUtils;
import com.symbol.wp.core.constants.MsgConstants;
import com.symbol.wp.core.constants.VarConstants;
import com.symbol.wp.core.service.ICommonManager;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.orm.PropertyFilter;
import com.symbol.wp.modules.orm.hibernate.HibernateWebUtils;
import com.symbol.wp.modules.struts2.Struts2Utils;
import com.symbol.wp.modules.struts2.base.BaseDispatchAction;
import com.symbol.wp.tools.gtgrid.model.SortInfo;
import com.symbol.wp.tools.gtgrid.server.GridServerHandler1;


/**
 * 大运保障
 * 更新保障区域
 * 
 */

public class AreaMapCellAction extends BaseDispatchAction {
	@Resource(name = "commonManagerImpl")
	public ICommonManager commonManagerImpl;
	@Autowired
	private AreaMapCellManager areaMapCellManager;
	private String vcAreaName;
	private String vcCgiName;
	private String vcCgiChName;
	private String vcCGI;
	private Long nmAreaId;
	public Long getNmAreaId() {
		return nmAreaId;
	}

	public void setNmAreaId(Long nmAreaId) {
		this.nmAreaId = nmAreaId;
	}

	public String getVcCGI() {
		return vcCGI;
	}

	public void setVcCGI(String vcCGI) {
		this.vcCGI = vcCGI;
	}


	private Integer intType;
	public Integer getIntType() {
		return intType;
	}

	public void setIntType(Integer intType) {
		this.intType = intType;
	}

	public String getVcCgiChName() {
		return vcCgiChName;
	}

	public void setVcCgiChName(String vcCgiChName) {
		this.vcCgiChName = vcCgiChName;
	}


	private String[] idAndCell;
	public String[] getIdAndCell() {
		return idAndCell;
	}

	public void setIdAndCell(String[] idAndCell) {
		this.idAndCell = idAndCell;
	}


	private Long id;
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public String getVcAreaName() {
		return vcAreaName;
	}

	public void setVcAreaName(String vcAreaName) {
		this.vcAreaName = vcAreaName;
	}

	public String getVcCgiName() {
		return vcCgiName;
	}

	public void setVcCgiName(String vcCgiName) {
		this.vcCgiName = vcCgiName;
	}


	private Page<CommonSport> page = new Page<CommonSport>(
			VarConstants.PAGE_LONG_ROW_SIZE);
	
	/**
	 * 查询数据
	 */
	public void query() throws ServletException, IOException {
		logger.info("---------------进入action---------------------");
		List<CommonSport> list = null;
		GridServerHandler1 gridServerHandler = new GridServerHandler1(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		List<PropertyFilter> filters = HibernateWebUtils
		.buildPropertyFilters(Struts2Utils.getRequest());
		SortInfo si = gridServerHandler.getSingleSortInfo();
		if (si != null) {
			String order = si.getSortOrder();
			page.setOrder(order);
			page.setOrderBy(MantoEyeUtils.getSortField(si.getFieldName()));

		} else {
			page.setOrderBy("dtUpdateTime");
			page.setOrder("desc");
		}
		page.setPageSize(gridServerHandler.getPageSize());
		page.setPageNo(gridServerHandler.getPageInfo().getPageNum());
		page =areaMapCellManager.query(page, Common.OutConvert(vcAreaName), Common.OutConvert(vcCgiName),intType,Common.OutConvert(vcCGI));
		int totalRowNum = gridServerHandler.getTotalRowNum();
		if (totalRowNum < 1) {
			totalRowNum = page.getTotalCount();
			gridServerHandler.setTotalRowNum(totalRowNum);
		}
		list = page.getResult();
		gridServerHandler.setData(list, CommonSport.class);
		Struts2Utils.renderJson(gridServerHandler.getLoadResponseText());
	}
	
	public void queryCGI() throws ServletException, IOException {
		logger.info("---------------进入action---------------------");
		List<CommonSport> list = null;
		GridServerHandler1 gridServerHandler = new GridServerHandler1(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		List<PropertyFilter> filters = HibernateWebUtils
		.buildPropertyFilters(Struts2Utils.getRequest());
		SortInfo si = gridServerHandler.getSingleSortInfo();
		if (si != null) {
			String order = si.getSortOrder();
			page.setOrder(order);
			page.setOrderBy(MantoEyeUtils.getSortField(si.getFieldName()));

		}else {
			page.setOrderBy("id");
			page.setOrder("desc");
		}
		page.setPageSize(gridServerHandler.getPageSize());
		page.setPageNo(gridServerHandler.getPageInfo().getPageNum());
		page =areaMapCellManager.queryCGI(page,id);
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
		logger.info("---------------导出action---------------------");
		if (vcAreaName!=null) {
			vcAreaName = new String(vcAreaName.getBytes("ISO-8859-1"),"UTF-8");
		}
		if (vcCgiName!=null) {
			vcCgiName = new String(vcCgiName.getBytes("ISO-8859-1"),"UTF-8");
		}	
		List<CommonSport> list = areaMapCellManager.listData(Common.OutConvert(vcAreaName), Common.OutConvert(vcCgiName),intType,Common.OutConvert(vcCGI));
		GridServerHandler1 gridServerHandler = new GridServerHandler1(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		String exportmsg = "";
		if (intType==1) {
			exportmsg = "保障区域";
		}
		if (intType==2) {
			exportmsg = "小区集";
		}
		commonManagerImpl.log(Struts2Utils.getRequest(), exportmsg);
		gridServerHandler.exportXLS(list, CommonSport.class);
	}
	
	
	
	public String saveAreaMapCell() throws Exception {
		String msg = null;
		HttpServletRequest request = Struts2Utils.getRequest();
		try {
			List cgiList = areaMapCellManager.queryByVcCgi(vcCGI);
			if (cgiList==null || cgiList.size()==0) {
				msg = "维表中缺少小区信息!";
				request.setAttribute(VarConstants.ERROR_CODE,
						msg);
				return ERROR;
			}
			List areaList = areaMapCellManager.queryArea(vcAreaName,intType);
			if (areaList!=null && areaList.size()==1) {
				//Object[] objs = (Object[]) areaList.get(0);
				String nmAreaId = areaList.get(0).toString();
				areaMapCellManager.insertAreaMapCell(Long.parseLong(nmAreaId),vcCGI);
			}else {
				msg = "区域表中出现重复数据";
				request.setAttribute(VarConstants.ERROR_CODE,
						msg);
				return ERROR;
			}
			request.setAttribute(VarConstants.SUCC_CODE,
					MsgConstants.SUCC_CODE_00101);
			msg = "添加保障区域成功!";
			return SUCCESS;

		} catch (Exception e) {
			request.setAttribute(VarConstants.ERROR_CODE,
					MsgConstants.ERROR_CODE_00004);
			msg = "添加保障区域失败!";
			return ERROR;
		}finally {
			commonManagerImpl.log(request, msg);
		}
	}
	
	
	public String saveAreaMapCgi() throws Exception {
		String msg = null;
		HttpServletRequest request = Struts2Utils.getRequest();
		try {
			List cgiList = areaMapCellManager.queryByVcCgi(vcCGI);
			if (cgiList==null || cgiList.size()==0) {
				msg = "维表中缺少小区信息!";
				request.setAttribute(VarConstants.ERROR_CODE,
						msg);
				return ERROR;
			}
			areaMapCellManager.insertAreaMapCell(nmAreaId,vcCGI);
			request.setAttribute(VarConstants.SUCC_CODE,
					MsgConstants.SUCC_CODE_00101);
			msg = "添加保障区域成功!";
			return SUCCESS;

		} catch (Exception e) {
			request.setAttribute(VarConstants.ERROR_CODE,
					MsgConstants.ERROR_CODE_00004);
			msg = "添加保障区域失败!";
			return ERROR;
		}finally {
			commonManagerImpl.log(request, msg);
		}
	}
	
	
	public void delete(){
		HttpServletRequest request = Struts2Utils.getRequest();
		String ids=request.getParameter("ids");
		String msg="";
		try{
			
			msg = "删除成功!";
			areaMapCellManager.deleteById(ids,intType);
			commonManagerImpl.log(request, msg);
			Struts2Utils.renderText(msg);
		}catch(Exception e){
			logger.error(e.getMessage());
			msg = "删除失败!";
			commonManagerImpl.log(request, msg);
			Struts2Utils.renderText(msg);
		}
	}
	
	
	public void deleteAreaMapCell(){
		HttpServletRequest request = Struts2Utils.getRequest();
		String ids=request.getParameter("ids");
		String msg="";
		try{
			
			msg = "删除成功!";
			areaMapCellManager.deleteAreaMapCell(ids);
			commonManagerImpl.log(request, msg);
			Struts2Utils.renderText(msg);
		}catch(Exception e){
			logger.error(e.getMessage());
			msg = "删除失败!";
			commonManagerImpl.log(request, msg);
			Struts2Utils.renderText(msg);
		}
	}
	

}
