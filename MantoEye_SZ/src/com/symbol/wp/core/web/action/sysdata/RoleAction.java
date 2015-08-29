package com.symbol.wp.core.web.action.sysdata;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.symbol.app.mantoeye.util.CommonUtils;
import com.symbol.wp.core.constants.MsgConstants;
import com.symbol.wp.core.constants.VarConstants;
import com.symbol.wp.core.entity.TbBaseRoleGroup;
import com.symbol.wp.core.entity.TbBaseRoles;
import com.symbol.wp.core.exceptions.ServiceStartupException;
import com.symbol.wp.core.service.ICommonManager;
import com.symbol.wp.core.service.impl.BaseRoleGroupManager;
import com.symbol.wp.core.service.impl.BaseRoleManager;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.orm.PropertyFilter;
import com.symbol.wp.modules.orm.hibernate.HibernateWebUtils;
import com.symbol.wp.modules.struts2.Struts2Utils;
import com.symbol.wp.modules.struts2.base.BaseDispatchAction;
import com.symbol.wp.modules.struts2.base.IAction;
import com.symbol.wp.tools.gtgrid.server.GridServerHandler;

public class RoleAction extends BaseDispatchAction implements IAction {

	private TbBaseRoles entity;
	private String id;
	private String keys;
	private String layers;
	private String vcRoleNo;
	@Autowired
	private BaseRoleManager baseRoleManager;

	@Autowired
	private BaseRoleGroupManager baseRoleGroupManager;

	@Autowired
	@Qualifier("commonManagerImpl")
	private ICommonManager commonManagerImpl;

	HttpServletRequest request = ServletActionContext.getRequest();
	private Page<TbBaseRoles> page = new Page<TbBaseRoles>(
			VarConstants.PAGE_LONG_ROW_SIZE, true);

	public Page<TbBaseRoles> getPage() {
		return page;
	}

	public String getVcRoleNo() {
		return vcRoleNo;
	}

	public void setVcRoleNo(String vcRoleNo) {
		this.vcRoleNo = vcRoleNo;
	}

	public String getLayers() {
		return layers;
	}

	public void setLayers(String layers) {
		this.layers = layers;
	}

	public void setPage(Page<TbBaseRoles> page) {
		this.page = page;
	}

	public TbBaseRoles getEntity() {
		return entity;
	}

	public void setEntity(TbBaseRoles entity) {
		this.entity = entity;
	}

	public String getKeys() {
		return keys;
	}

	public void setKeys(String keys) {
		this.keys = keys;
	}

	public void query() throws ServletException, IOException {
		HttpServletRequest request = Struts2Utils.getRequest();
		List<TbBaseRoles> list = null;
		List<PropertyFilter> filters = HibernateWebUtils
				.buildPropertyFilters(Struts2Utils.getRequest());
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		page.setOrderBy("id");
		page.setOrder("desc");
		page.setPageSize(gridServerHandler.getPageSize());
		page.setPageNo(gridServerHandler.getPageInfo().getPageNum());
		page = baseRoleManager.search(page, filters);
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
	private List formatViewData(List<TbBaseRoles> list) {
		List maplist = new ArrayList();
		Map map;
		TbBaseRoles bean;
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				bean = list.get(i);
				map = new LinkedHashMap();
				map.put("id", bean.getId());
				map.put("vcRolesName", CommonUtils.killNull(bean
						.getVcRolesName()));
				map.put("nmRolesLevel", CommonUtils.killNull(bean
						.getNmRolesLevel()));
				map.put("vcRoleGroupId", CommonUtils.killNull(bean
						.getVcRoleGroupId()));
				map.put("txtRolesMemo", CommonUtils.killNull(bean
						.getTxtRolesMemo()));
				maplist.add(map);
			}
		}
		return maplist;
	}

	/**
	 * 删除角色
	 */
	public void deleteRole() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		String ids = request.getParameter("ids");
		String[] sids = ids.split(",");
		String msg = "";
		try {// 删除成功
			baseRoleManager.deleteByKeys(sids);
			msg = "删除角色成功!";
			commonManagerImpl.log(request, "删除角色成功[主键：" + ids + "]成功!");
		} catch (Exception e) { // 删除失败
			msg = "删除角色失败!";
			commonManagerImpl.log(request, "删除角色失败[主键：" + ids + "]失败!");
		}
		Struts2Utils.renderText(msg);
	}

	public String delete() throws Exception {
		String msg = null;
		String[] sids = keys.split(",");
		try {// 删除成功
			baseRoleManager.deleteByKeys(sids);
			request.setAttribute(VarConstants.SUCC_CODE,
					MsgConstants.SUCC_CODE_00008);
			request.setAttribute(VarConstants.URL, "/role_list.do?1=1");
			msg = "删除角色信息[角色Id：" + keys + "]成功!";
			if (Common.judgeString(msg)) {
				commonManagerImpl.log(request, msg);
			}
			return SUCCESS;
		} catch (Exception e) { // 删除失败
			request.setAttribute(VarConstants.ERROR_CODE,
					MsgConstants.ERROR_CODE_00005);
			addActionMessage("删除角色信息" + keys + "失败!");
			msg = "删除角色信息[角色Id：" + keys + "]失败!";
			if (Common.judgeString(msg)) {
				commonManagerImpl.log(request, msg);
			}
			return ERROR;
		}
	}

	@Override
	public String input() throws Exception {
		return INPUT;
	}

	public String list() throws Exception {
		setPaginationdataList();
		return INDEX;
	}

	public void setPaginationdataList() throws ServiceStartupException {
		List<PropertyFilter> filters = HibernateWebUtils
				.buildPropertyFilters(request);
		PropertyFilter pf = null;
		for (PropertyFilter fl : filters) {

			if (fl.getPropertyName().equals("vcRoleGroupId")) {

				TbBaseRoleGroup tbGroup = baseRoleManager
						.findGroupByName((String) fl.getValue());
				pf = new PropertyFilter();
				BeanUtils.copyProperties(fl, pf);
				if (tbGroup != null) {
					pf.setValue(tbGroup.getId());
				}
				filters.remove(fl);
				filters.add(pf);
				break;
			}

		}

		TbBaseRoles tbBaseRole = (TbBaseRoles) request.getSession()
				.getAttribute("baseRoles");
		String allGroup = baseRoleManager.findRoleByGroup(tbBaseRole
				.getVcRoleGroupId());
		PropertyFilter fl = new PropertyFilter();
		fl.setMatchType(PropertyFilter.MatchType.IN);
		fl.setPropertyName("vcRoleGroupId");
		String [] vun = (baseRoleManager.findRoleByGroup(allGroup)).split(",");
		fl.setValue(vun);
		filters.add(fl);
		if (page.getOrderBy() == null) {
			page.setOrderBy("id");
		}

		page = baseRoleManager.search(page, filters);
		List<TbBaseRoles> dataList = page.getVresult();
		List list = new ArrayList();
		Map<String, TbBaseRoleGroup> map = baseRoleGroupManager.findAllGroup();

		for (TbBaseRoles tb : dataList) {
			TbBaseRoleGroup tbRole = map.get(tb.getVcRoleGroupId());
			if (tbRole != null) {
				tb.setVcRoleGroupId(tbRole.getVcName());
			}
			list.add(tb);
		}
		request.setAttribute("dataList", list);
	}

	public String save() throws Exception {
		String msg = null;
		HttpServletRequest request = Struts2Utils.getRequest();
		try {
			baseRoleManager.save(entity);
			request.setAttribute(VarConstants.SUCC_CODE,
					MsgConstants.SUCC_CODE_00001);
			msg = "添加角色信息[角色名：" + entity.getVcRolesName() + "]成功!";

			request.setAttribute(VarConstants.SUCC_CODE,
					MsgConstants.SUCC_CODE_00101);
			return SUCCESS;

		} catch (Exception e) {
			request.setAttribute(VarConstants.ERROR_CODE,
					MsgConstants.ERROR_CODE_00004);
			msg = "添加角色信息[角色名：" + entity.getVcRolesName() + "]失败!";
			return ERROR;
		} finally {
			commonManagerImpl.log(request, msg);
		}
	}

	public String update() throws Exception {
		String msg = null;
		HttpServletRequest request = Struts2Utils.getRequest();
		try {
			baseRoleManager.save(entity);
			request.setAttribute(VarConstants.SUCC_CODE,
					MsgConstants.SUCC_CODE_00102);
			msg = "编辑角色信息[角色名：" + entity.getVcRolesName() + "]成功!";
			request.setAttribute(VarConstants.URL, "/role_list.do?1=1");
			return SUCCESS;
		} catch (Exception e) {
			msg = "编辑角色信息[角色名：" + entity.getVcRolesName() + "]失败!";
			return ERROR;
		} finally {
			commonManagerImpl.log(request, msg);
		}

	}

	public TbBaseRoles getModel() {

		return entity;
	}

	public String permis() throws Exception {
		String id = request.getParameter("id");
		TbBaseRoles tbRoles = baseRoleManager.get(id);
		request.setAttribute("tbRoles", tbRoles);
		return PERMIS;
	}

	public String setRolePermis() throws Exception {
		String msg = null;
		StringTokenizer st = new StringTokenizer(layers, ",");
		List<String> plist = new ArrayList<String>();
		String cur_id = null;
		while (st.hasMoreTokens()) {
			cur_id = st.nextToken();
			plist.add(cur_id);
		}
		try {
			baseRoleManager.setRolePermis(plist, vcRoleNo);
			msg = "权限设置[角色：" + vcRoleNo + "权限设置]成功!";
			request.setAttribute(VarConstants.SUCC_CODE,
					MsgConstants.SUCC_CODE_00104);
			return SUCCESS;
		} catch (Exception e) {
			logger.error(e.getMessage());
			msg = "权限设置[角色：" + vcRoleNo + "权限设置]失败!";
			request.setAttribute(VarConstants.ERROR_CODE,
					MsgConstants.ERROR_CODE_00009);
			return ERROR;
		} finally {
			commonManagerImpl.log(request, msg);
		}

	}

	public String checkUnique() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String vcRolesName = request.getParameter("entity.vcRolesName");
		try {
			vcRolesName = new String(vcRolesName.getBytes("ISO-8859-1"),
					"UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}

		String vcOldRolesName = request.getParameter("vcOldRolesName");

		try {
			vcOldRolesName = java.net.URLDecoder
					.decode(vcOldRolesName, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage());
		}

		if (baseRoleManager.isPropertyUnique("vcRolesName", vcRolesName,
				vcOldRolesName)) {
			Struts2Utils.renderText("true");
		} else {
			Struts2Utils.renderText("false");
		}
		return null;
	}

	public String edit() throws Exception {
		entity = baseRoleManager.get(id);
		return EDIT;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
