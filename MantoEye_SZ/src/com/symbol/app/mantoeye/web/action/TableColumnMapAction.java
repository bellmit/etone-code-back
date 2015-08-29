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

import com.symbol.app.mantoeye.entity.FtbTableColumnMap;
import com.symbol.app.mantoeye.entity.FtbTableMap;
import com.symbol.app.mantoeye.service.TableColumnMapManager;
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
import com.symbol.wp.tools.gtgrid.server.GridServerHandler;

public class TableColumnMapAction  extends BaseDispatchAction{
	
	@Autowired
	private TableColumnMapManager tableColumnMapManager;
	
	@Autowired
	private TableMapManager tableMapManager;
	
	private FtbTableColumnMap entity;
	private Integer id;
	private String keys;
	
	@Autowired
	@Qualifier("commonManagerImpl")
	private ICommonManager commonManagerImpl;

	HttpServletRequest request = ServletActionContext.getRequest();
	private Page<FtbTableColumnMap> page = new Page<FtbTableColumnMap>(
			VarConstants.PAGE_LONG_ROW_SIZE, true);

	public Page<FtbTableColumnMap> getPage() {
		return page;
	}

	public void setPage(Page<FtbTableColumnMap> page) {
		this.page = page;
	}
	public void setEntity(FtbTableColumnMap entity) {
		this.entity = entity;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setKeys(String keys) {
		this.keys = keys;
	}
	
	public void delete() throws Exception {
		String msg = null;
		String rmsg = null;
		String[] sids = keys.split(",");
		Integer [] skeys = Common.StringArrayToIntegerArray(sids);
		try {// 删除成功
			tableColumnMapManager.deleteByKeys(skeys);
			request.setAttribute(VarConstants.SUCC_CODE,
					MsgConstants.SUCC_CODE_00008);
			msg = "删除字段信息信息[字段信息Id：" + keys + "]成功!";
			if (Common.judgeString(msg)) {
				commonManagerImpl.log(request, msg);
			}
			rmsg = "删除成功!";
		} catch (Exception e) { // 删除失败
			request.setAttribute(VarConstants.ERROR_CODE,
					MsgConstants.ERROR_CODE_00005);
			msg = "删除字段信息信息[字段信息Id：" + keys + "]失败!";
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
		entity = tableColumnMapManager.get(id);
		return EDIT;
	}
	public void query() throws ServletException, IOException {
		try{
		List<FtbTableColumnMap> list = null;
		List<PropertyFilter> filters = HibernateWebUtils
				.buildPropertyFilters(Struts2Utils.getRequest());
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		SortInfo si = gridServerHandler.getSingleSortInfo();
		if (si != null) {
			page.setOrderBy(si.getFieldName());
			page.setOrder(si.getSortOrder());
		} else {
			page.setOrderBy("nmTableColumnMapId");
			page.setOrder("desc");
		}
		page.setPageSize(gridServerHandler.getPageSize());
		page.setPageNo(gridServerHandler.getPageInfo().getPageNum());
		page = tableColumnMapManager.search(page, filters);
		int totalRowNum = gridServerHandler.getTotalRowNum();
		if (totalRowNum < 1) {
			totalRowNum = page.getTotalCount();
			gridServerHandler.setTotalRowNum(totalRowNum);
		}
		list = page.getResult();
		gridServerHandler.setData(this.formatViewData(list));
		Struts2Utils.renderJson(gridServerHandler.getLoadResponseText());
		}catch(Exception e){
			logger.error(e.getMessage());
		}
	}
	/**
	 * 页面视图数据
	 */
	private List formatViewData(List<FtbTableColumnMap> list) {
		List maplist = new ArrayList();
		Map map;
		FtbTableColumnMap bean;
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				bean = list.get(i);
				map = new LinkedHashMap();
				map.put("id", bean.getNmTableColumnMapId());
				map.put("tableName", CommonUtils.killNull(bean.getFtbTableMap().getVcTableName()));
				map.put("vcColumnName", bean.getVcColumnName());// 
				map.put("vcColumnNickName", bean.getVcColumnNickName());//
				map.put("intColumnType", bean.getIntColumnType());// 
				maplist.add(map);
			}
		}
		return maplist;
	}
	public String save() throws Exception {
		String msg = null;
		HttpServletRequest request = Struts2Utils.getRequest();
		String tableId = request.getParameter("tableId");
		Short  tid = Common.StringToShort(tableId);
		try {
			tableColumnMapManager.saveEntity(entity,tid);
			request.setAttribute(VarConstants.SUCC_CODE,
					MsgConstants.SUCC_CODE_00101);
			msg = "添加字段信息信息[字段信息名：" + entity.getVcColumnName() + "]成功!";
			return SUCCESS;
		} catch (Exception e) {
			request.setAttribute(VarConstants.ERROR_CODE,
					MsgConstants.ERROR_CODE_00004);
			msg = "保存字段信息信息[字段信息名：" + entity.getVcColumnName() + "]失败!";
			return ERROR;
		} finally {
			commonManagerImpl.log(request, msg);
		}
	}
	/**
	 * 保存字段信息
	 */
	public String update() throws ServiceStartupException {
		HttpServletRequest request = Struts2Utils.getRequest();
		String msg = "";
		String tableId = request.getParameter("tableId");
		Short  tid = Common.StringToShort(tableId);
		FtbTableMap table = tableMapManager.get(tid);
		entity.setFtbTableMap(table);
		try {
			tableColumnMapManager.save(entity);
			msg = " 编辑字段信息[字段名：" + entity.getVcColumnName() + "]成功!";
			request.setAttribute(VarConstants.SUCC_CODE,
					MsgConstants.SUCC_CODE_00102);
			return SUCCESS;
		} catch (Exception e) {
			request.setAttribute(VarConstants.ERROR_CODE,
					MsgConstants.ERROR_CODE_00004);
			msg = "编辑字段信息[字段名：" + entity.getVcColumnName() + "]失败!";
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
		String newName = request.getParameter("entity.vcColumnName");
		String oldName = request.getParameter("oldName");
		if (tableColumnMapManager.isPropertyUnique("vcColumnName", newName,
				oldName)) {
			Struts2Utils.renderText("true");
		} else {
			Struts2Utils.renderText("false");
		}
		return null;
	}
}
