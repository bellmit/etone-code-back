package com.symbol.wp.core.web.action.sysdata;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.symbol.app.mantoeye.util.CommonUtils;
import com.symbol.wp.core.constants.ForwardConstants;
import com.symbol.wp.core.constants.MsgConstants;
import com.symbol.wp.core.constants.VarConstants;
import com.symbol.wp.core.dto.VBasePermissions;
import com.symbol.wp.core.entity.TbBasePermissions;
import com.symbol.wp.core.service.ICommonManager;
import com.symbol.wp.core.service.impl.BasePermissionsManager;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.orm.PropertyFilter;
import com.symbol.wp.modules.orm.hibernate.HibernateWebUtils;
import com.symbol.wp.modules.struts2.Struts2Utils;
import com.symbol.wp.modules.struts2.base.BaseDispatchAction;
import com.symbol.wp.modules.struts2.base.IAction;
import com.symbol.wp.tools.gtgrid.server.GridServerHandler;

public class PermissionAction extends BaseDispatchAction implements IAction {

	@Autowired
	private BasePermissionsManager basePermissionsManager;

	@Autowired
	private ICommonManager commonManagerImpl;

	// 基本属性
	// private
	private TbBasePermissions entity;
	private String id;// 当前选中记录的主键id
	private String keys;// 当前选中的多条记录的id的字符串 以','分割

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getKeys() {
		return keys;
	}

	public void setKeys(String keys) {
		this.keys = keys;
	}

	public TbBasePermissions getEntity() {
		return entity;
	}

	public void setEntity(TbBasePermissions entity) {
		this.entity = entity;
	}

	public void setPage(Page<TbBasePermissions> page) {
		this.page = page;
	}

	private Page<TbBasePermissions> page = new Page<TbBasePermissions>(
			VarConstants.PAGE_LONG_ROW_SIZE);

	public Page<TbBasePermissions> getPage() {
		return page;
	}

	public void query() throws ServletException, IOException {
		HttpServletRequest request = Struts2Utils.getRequest();
		List<TbBasePermissions> list = null;
		List<PropertyFilter> filters = HibernateWebUtils
				.buildPropertyFilters(Struts2Utils.getRequest());
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		page.setOrderBy("id");
		page.setOrder("desc");
		page.setPageSize(gridServerHandler.getPageSize());
		page.setPageNo(gridServerHandler.getPageInfo().getPageNum());
		page = basePermissionsManager.search(page, filters);
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
	private List formatViewData(List<TbBasePermissions> list) {
		List maplist = new ArrayList();
		Map map;
		TbBasePermissions bean;
		VBasePermissions view;
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				bean = list.get(i);
				view = basePermissionsManager.converBeanToView(bean);
				map = new LinkedHashMap();
				map.put("id", bean.getId());
				map.put("parentMenuName", CommonUtils.killNull(view
						.getParentMenuName()));// 父菜单名称
				map.put("vcMenuName", CommonUtils
						.killNull(bean.getVcMenuName()));// 菜单名称
				map.put("vcRedirectUrl", CommonUtils.killNull(view
						.getVcRedirectUrl()));// 映射地址
				maplist.add(map);
			}
		}
		return maplist;
	}

	/**
	 * 删除数据
	 */
	public String delete() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		String[] sids = keys.split(",");
		try {
			basePermissionsManager.deleteByKeys(sids);
			request.setAttribute(VarConstants.URL,
					ForwardConstants.MEMU_LIST_URL);
			request.setAttribute(VarConstants.SUCC_CODE,
					MsgConstants.SUCC_CODE_00008);
			/*
			 * 写日志
			 */
			String msg = "删除模块[模块ID：" + keys + "]成功!";
			if (Common.judgeString(msg)) {
				commonManagerImpl.log(request, msg);
			}

			return SUCCESS;
		} catch (Exception e) {

			String msg = "删除模块[模块ID：" + keys + "]失败!";
			request.setAttribute(VarConstants.URL,
					ForwardConstants.MEMU_LIST_URL);
			request.setAttribute(VarConstants.SUCC_CODE,
					MsgConstants.ERROR_CODE_00005);
			if (Common.judgeString(msg)) {
				commonManagerImpl.log(request, msg);
			}

			return ERROR;
		}
	}

	/**
	 * 列表显示数据
	 */
	public String list() throws Exception {

		HttpServletRequest request = Struts2Utils.getRequest();
		List<PropertyFilter> filters = HibernateWebUtils
				.buildPropertyFilters(request);

		if (page.getOrderBy() == null) {
			page.setOrderBy("id");
		}
		page = basePermissionsManager.search(page, filters);

		List dataList = page.getVresult();
		request.setAttribute("dataList", dataList);

		return INDEX;
	}

	/**
	 * 转到添加页面
	 */
	public String input() throws Exception {

		return INPUT;
	}

	/**
	 * 转到编辑页面
	 */
	public String edit() throws Exception {
		entity = basePermissionsManager.get(id);
		return EDIT;
	}

	/**
	 * 保存添加后的对象
	 */
	public String save() throws Exception {
		HttpServletRequest request = Struts2Utils.getRequest();
		if (entity.getVcParentId().equals("--系统管理--")) {
			entity.setVcParentId("BASE");
		}

		String msg = "";
		// 定向地址 以常量的形式写入ForwardConstants 文件
		try {

			basePermissionsManager.save(entity);
			// 页面传过来的nmPermId的值是一个为空的字符串"",所以不仅要判断他是否为null，还有判断他是否为空字符串

			// flag为空,则为添加
			request.setAttribute(VarConstants.SUCC_CODE,
					MsgConstants.SUCC_CODE_00101);
			msg = "添加模块[模块标识：" + entity.getId() + "]成功!";
			request.setAttribute(VarConstants.URL,
					ForwardConstants.MEMU_LIST_URL);
			return SUCCESS;

		} catch (Exception e) {
			logger.error("模块管理－添加数据异常！");
			logger.debug(e.getMessage());
			msg = "添加模块[模块标识：" + entity.getId() + "]失败!";
			return INDEX;
		} finally {
			commonManagerImpl.log(request, msg);
		}
	}

	/**
	 * 删除模块
	 */
	public void deleteMenu() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		String ids = request.getParameter("ids");
		String[] sids = ids.split(",");
		String msg = "";
		try {// 删除成功
			basePermissionsManager.deleteByKeys(sids);
			msg = "删除成功!";
			commonManagerImpl.log(request, "删除角色成功[主键：" + ids + "]成功!");
		} catch (Exception e) { // 删除失败
			msg = "删除失败!";
			commonManagerImpl.log(request, "删除角色失败[主键：" + ids + "]失败!");
		}
		Struts2Utils.renderText(msg);
	}

	/**
	 * 支持使用Jquery.validate Ajax检验用户名是否重复.
	 */
	public String checkUnique() {
		String menuName = super.getServletRequest().getParameter(
				"entity.vcPermSymbol");
		String oldMenuName = super.getServletRequest().getParameter("oldName");
		logger.info(oldMenuName + "-->" + menuName);
		if (basePermissionsManager.isPropertyUnique("vcPermSymbol", menuName,
				oldMenuName)) {
			Struts2Utils.renderText("true");
		} else {
			Struts2Utils.renderText("false");
		}
		return null;
	}

	public TbBasePermissions getModel() {
		return entity;
	}

	/**
	 * 保存编辑后的对象
	 */
	public String update() throws Exception {
		HttpServletRequest request = Struts2Utils.getRequest();
		if (entity.getVcParentId().equals("--系统管理--")) {
			entity.setVcParentId("BASE");
		}

		String msg = "";
		// 定向地址 以常量的形式写入ForwardConstants 文件

		try {

			basePermissionsManager.save(entity);

			// flag不为空,则为编辑
			request.setAttribute(VarConstants.SUCC_CODE,
					MsgConstants.SUCC_CODE_00102);
			msg = "编辑模块[模块标识：" + entity.getId() + "]成功!";
			request.setAttribute(VarConstants.URL,
					ForwardConstants.MEMU_LIST_URL);
			return SUCCESS;

		} catch (Exception e) {
			logger.error("模块管理－添加数据异常！");
			logger.debug(e.getMessage());

			msg = "编辑模块[模块标识：" + entity.getId() + "]失败!";

			request.setAttribute(VarConstants.ERROR_CODE,
					MsgConstants.ERROR_CODE_00004);
			return INDEX;
		} finally {
			commonManagerImpl.log(request, msg);
		}
	}
}
