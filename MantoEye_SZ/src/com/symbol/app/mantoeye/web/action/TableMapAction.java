package com.symbol.app.mantoeye.web.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.symbol.app.mantoeye.dto.common.CommonDimension;
import com.symbol.app.mantoeye.entity.FtbTableMap;
import com.symbol.app.mantoeye.service.TableMapManager;
import com.symbol.app.mantoeye.util.CommonUtils;
import com.symbol.wp.core.constants.MsgConstants;
import com.symbol.wp.core.constants.VarConstants;
import com.symbol.wp.core.exceptions.ServiceStartupException;
import com.symbol.wp.core.service.ICommonManager;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.orm.PropertyFilter;
import com.symbol.wp.modules.orm.hibernate.HibernateWebUtils;
import com.symbol.wp.modules.struts2.Struts2Utils;
import com.symbol.wp.modules.struts2.base.BaseDispatchAction;
import com.symbol.wp.tools.gtgrid.model.SortInfo;
import com.symbol.wp.tools.gtgrid.org.json.JSONObject;
import com.symbol.wp.tools.gtgrid.server.GridServerHandler;
import com.symbol.wp.tools.gtgrid.util.JSONUtils;

public class TableMapAction extends BaseDispatchAction{
	
	@Autowired
	private TableMapManager tableMapManager;
	
	private FtbTableMap entity;
	private Short id;
	private String keys;
	
	@Autowired
	@Qualifier("commonManagerImpl")
	private ICommonManager commonManagerImpl;

	HttpServletRequest request = ServletActionContext.getRequest();
	private Page<FtbTableMap> page = new Page<FtbTableMap>(
			VarConstants.PAGE_LONG_ROW_SIZE, true);

	public Page<FtbTableMap> getPage() {
		return page;
	}

	public void setPage(Page<FtbTableMap> page) {
		this.page = page;
	}
	public void setEntity(FtbTableMap entity) {
		this.entity = entity;
	}
	public void setId(Short id) {
		this.id = id;
	}
	public void setKeys(String keys) {
		this.keys = keys;
	}
	
	public void delete() throws Exception {
		String msg = null;
		String rmsg = null;
		String[] sids = keys.split(",");
		Short [] skeys = Common.StringArrayToShortArray(sids);
		try {// 删除成功
			tableMapManager.deleteByKeys(skeys);
			request.setAttribute(VarConstants.SUCC_CODE,
					MsgConstants.SUCC_CODE_00008);
			msg = "删除表信息信息[表信息Id：" + keys + "]成功!";
			if (Common.judgeString(msg)) {
				commonManagerImpl.log(request, msg);
			}
			rmsg = "删除成功!";
		} catch (Exception e) { // 删除失败
			request.setAttribute(VarConstants.ERROR_CODE,
					MsgConstants.ERROR_CODE_00005);
			msg = "删除表信息信息[表信息Id：" + keys + "]失败!";
			if (Common.judgeString(msg)) {
				commonManagerImpl.log(request, msg);
			}
			rmsg = "删除失败!";
		}
		Struts2Utils.renderText(rmsg);
	}

	public String add() throws Exception {
		return ADD;
	}
	public String edit() throws Exception {
		entity = tableMapManager.get(id);
		return EDIT;
	}
	public void query() throws ServletException, IOException {
		List<FtbTableMap> list = null;
		List<PropertyFilter> filters = HibernateWebUtils
				.buildPropertyFilters(Struts2Utils.getRequest());
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		SortInfo si = gridServerHandler.getSingleSortInfo();
		if (si != null) {
			page.setOrderBy(si.getFieldName());
			page.setOrder(si.getSortOrder());
		} else {
			page.setOrderBy("nmTableMapId");
			page.setOrder("desc");
		}
		page.setPageSize(gridServerHandler.getPageSize());
		page.setPageNo(gridServerHandler.getPageInfo().getPageNum());
		page = tableMapManager.search(page, filters);
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
	private List formatViewData(List<FtbTableMap> list) {
		List maplist = new ArrayList();
		Map map;
		FtbTableMap bean;
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				bean = list.get(i);
				map = new LinkedHashMap();
				map.put("id", bean.getNmTableMapId());
				map.put("vcTableName", CommonUtils.killNull(bean.getVcTableName()));
				map.put("vcTableNickName", CommonUtils.killNull(bean.getVcTableNickName()));// 创建人
				map.put("intTableType", bean.getIntTableType());// 表类型
				map.put("intBusinessType", bean.getIntBusinessType());// 业务类型
				map.put("intIsShow", bean.getIntIsShow());// 是否即席查询
				map.put("intViewFlag",bean.getIntViewFlag());// 是否视图
				maplist.add(map);
			}
		}
		return maplist;
	}
	public String save() throws Exception {
		String msg = null;
		HttpServletRequest request = Struts2Utils.getRequest();
		
		try {
			tableMapManager.saveEntity(entity);
			request.setAttribute(VarConstants.SUCC_CODE,
					MsgConstants.SUCC_CODE_00101);
			msg = "添加表信息信息[表信息名：" + entity.getVcTableName() + "]成功!";
			return SUCCESS;
		} catch (Exception e) {
			request.setAttribute(VarConstants.ERROR_CODE,
					MsgConstants.ERROR_CODE_00004);
			msg = "保存表信息信息[表信息名：" + entity.getVcTableName() + "]失败!";
			return ERROR;
		} finally {
			commonManagerImpl.log(request, msg);
		}
	}
	/**
	 * 保存表信息
	 */
	public String update() throws ServiceStartupException {
		HttpServletRequest request = Struts2Utils.getRequest();
		String msg = "";
		try {
			tableMapManager.save(entity);
			msg = " 编辑表信息[表名：" + entity.getVcTableName() + "]成功!";
			request.setAttribute(VarConstants.SUCC_CODE,
					MsgConstants.SUCC_CODE_00102);
			return SUCCESS;
		} catch (Exception e) {
			request.setAttribute(VarConstants.ERROR_CODE,
					MsgConstants.ERROR_CODE_00004);
			msg = "编辑表信息[表名：" + entity.getVcTableName() + "]失败!";
			return ERROR;
		} finally {
			commonManagerImpl.log(request, msg);
		}
	}
	/**
	 * 支持使用Jquery.validate Ajax检验用户名是否重复.
	 */
	public String checkUnique() {
		HttpServletRequest request = Struts2Utils.getRequest();
		String newName = request.getParameter("entity.vcTableName");
		String oldName = request.getParameter("oldName");
		if (tableMapManager.isPropertyUnique("vcTableName", newName,
				oldName)) {
			Struts2Utils.renderText("true");
		} else {
			Struts2Utils.renderText("false");
		}
		return null;
	}
	public void getAllTable(){
		List<FtbTableMap> maplist = tableMapManager.getAll();
		List<CommonDimension> list = new ArrayList<CommonDimension>();
		CommonDimension cb = null;
		FtbTableMap fm = null;
		if(maplist!=null&&maplist.size()>0){
			for(int i=0;i<maplist.size();i++){
				fm = maplist.get(i);
				cb = new CommonDimension((fm.getNmTableMapId()+0L),fm.getVcTableName()+"["+fm.getVcTableNickName()+"]");
				list.add(cb);
			}
		}
		JSONObject json=new JSONObject();
		json.put("datalist",JSONUtils.BeanList2JSONArray(list, CommonDimension.class));
		logger.info("table:"+json.toString());
		Struts2Utils.renderJson(json.toString());
	}
}
