package com.symbol.wp.core.web.action.sysdata;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.symbol.wp.core.constants.MsgConstants;
import com.symbol.wp.core.constants.VarConstants;
import com.symbol.wp.core.entity.TbBaseRoleGroup;
import com.symbol.wp.core.exceptions.ServiceStartupException;
import com.symbol.wp.core.service.ICommonManager;
import com.symbol.wp.core.service.impl.BaseRoleGroupManager;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.orm.PropertyFilter;
import com.symbol.wp.modules.orm.hibernate.HibernateWebUtils;
import com.symbol.wp.modules.struts2.Struts2Utils;
import com.symbol.wp.modules.struts2.base.BaseDispatchAction;
import com.symbol.wp.modules.struts2.base.IAction;

public class RoleGroupAction extends BaseDispatchAction implements IAction {

	private TbBaseRoleGroup entity;
	private String id;
	private String keys;

	HttpServletRequest request = ServletActionContext.getRequest();

	private Page<TbBaseRoleGroup> page = new Page<TbBaseRoleGroup>(
			VarConstants.PAGE_LONG_ROW_SIZE, true);

	@Autowired
	private BaseRoleGroupManager baseRoleGroupManager;

	@Autowired
	@Qualifier("commonManagerImpl")
	private ICommonManager commonManagerImpl;

	public TbBaseRoleGroup getEntity() {
		return entity;
	}

	public void setEntity(TbBaseRoleGroup entity) {
		this.entity = entity;
	}

	public String getKeys() {
		return keys;
	}

	public void setKeys(String keys) {
		this.keys = keys;
	}

	public Page<TbBaseRoleGroup> getPage() {
		return page;
	}

	public void setPage(Page<TbBaseRoleGroup> page) {
		this.page = page;
	}

	public TbBaseRoleGroup getModel() {

		return entity;
	}

	public String delete() throws Exception {
		String msg = null;
		String[] sids = keys.split(",");
		try {// 删除成功
			String[] str = baseRoleGroupManager.deleteByKey(sids);
			if (str != null) {
				request.setAttribute(VarConstants.SUCC_CODE,
						MsgConstants.SUCC_CODE_00038);
				request.setAttribute("Warm", "抱歉！删除角色组：" + str[0]
						+ "时具有如下子角色组，" + str[1] + ",该角色不能被删除。");
				request
						.setAttribute(VarConstants.URL,
								"/rolegroup_list.do?1=1");
			} else {
				request.setAttribute(VarConstants.SUCC_CODE,
						MsgConstants.SUCC_CODE_00008);
				request
						.setAttribute(VarConstants.URL,
								"/rolegroup_list.do?1=1");
				msg = "删除角色组信息[角色Id：" + keys + "]成功!";
				if (Common.judgeString(msg)) {
					commonManagerImpl.log(request, msg);
				}
			}
			return SUCCESS;
		} catch (Exception e) { // 删除失败
			addActionMessage(MsgConstants.ERROR_CODE_00005);
			request.setAttribute(VarConstants.SUCC_CODE,
					MsgConstants.ERROR_CODE_00005);
			msg = "删除角色组信息[角色Id：" + keys + "]失败!";
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

	// 获取所以角色组信息
	public List<TbBaseRoleGroup> findAllRoleGroup() {

		List<TbBaseRoleGroup> list = baseRoleGroupManager.getAll();
		return list;
	}

	public String list() throws Exception {
		setPaginationdataList();
		return INDEX;
	}

	public void setPaginationdataList() throws ServiceStartupException {

		HttpServletRequest request = ServletActionContext.getRequest();
		List<PropertyFilter> filters = HibernateWebUtils
				.buildPropertyFilters(request);
		PropertyFilter pf = null;
		for (PropertyFilter fl : filters) {
			if (fl.getPropertyName().equals("vcParentId")) {
				TbBaseRoleGroup tbGroup = baseRoleGroupManager
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
		if (page.getOrderBy() == null) {
			page.setOrderBy("id");
		}

		page = baseRoleGroupManager.search(page, filters);
		List<TbBaseRoleGroup> list = new ArrayList<TbBaseRoleGroup>();

		List<TbBaseRoleGroup> dataList = page.getVresult();
		Map<String, TbBaseRoleGroup> map = new HashMap<String, TbBaseRoleGroup>();
		List<TbBaseRoleGroup> list2 = baseRoleGroupManager.getAll();
		if (list2 != null && !list2.isEmpty()) {
			for (TbBaseRoleGroup tbBaseRoleGroup : list2) {
				map.put(tbBaseRoleGroup.getId(), tbBaseRoleGroup);
			}
			for (TbBaseRoleGroup tbBaseRoleGroup : dataList) {
				TbBaseRoleGroup tb = map.get(tbBaseRoleGroup.getVcParentId());
				if (tb != null) {
					tbBaseRoleGroup.setVcParentId(tb.getVcName());
				} else {
					tbBaseRoleGroup.setVcParentId("--");
				}
				list.add(tbBaseRoleGroup);
			}
		}
		request.setAttribute("dataList", list);
	}

	public String save() throws Exception {
		String msg = null;
		HttpServletRequest request = Struts2Utils.getRequest();
		try {
			baseRoleGroupManager.save(entity);
			request.setAttribute(VarConstants.SUCC_CODE,
					MsgConstants.SUCC_CODE_00001);
			msg = "添加角色组信息[角色组名：" + entity.getVcName() + "]成功!";
			request.setAttribute(VarConstants.URL, "/rolegroup_list.do?1=1");
			if (Common.judgeString(msg)) {
				commonManagerImpl.log(request, msg);
			}
			return SUCCESS;

		} catch (Exception e) {
			request.setAttribute(VarConstants.ERROR_CODE,
					MsgConstants.ERROR_CODE_00004);
			msg = "保存角色组信息[角色组名：" + entity.getVcName() + "]失败!";
			if (Common.judgeString(msg)) {
				commonManagerImpl.log(request, msg);
			}
			return ERROR;
		}
	}

	public String update() throws Exception {
		HttpServletRequest request = Struts2Utils.getRequest();
		String msg = "";
		try {
			baseRoleGroupManager.save(entity);
			request.setAttribute(VarConstants.SUCC_CODE,
					MsgConstants.SUCC_CODE_00002);
			msg = "编辑角色组信息[角色组名：" + entity.getVcName() + "]成功!";
			request.setAttribute(VarConstants.URL, "/rolegroup_list.do?1=1");
			if (Common.judgeString(msg)) {
				commonManagerImpl.log(request, msg);
			}
			return SUCCESS;
		} catch (Exception e) {
			request.setAttribute(VarConstants.ERROR_CODE,
					MsgConstants.ERROR_CODE_00004);
			msg = "编辑角色组信息[角色组名：" + entity.getVcName() + "]失败!";
			if (Common.judgeString(msg)) {
				commonManagerImpl.log(request, msg);
			}
			return ERROR;
		}
	}

	public String checkUnique() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String vcName = request.getParameter("entity.vcName");
		String vcOldName = request.getParameter("vcOldName");
		try {
			vcName = new String(vcName.getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		try {
			vcOldName = java.net.URLDecoder.decode(vcOldName, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage());
		}

		if (baseRoleGroupManager.isPropertyUnique("vcName", vcName, vcOldName)) {
			Struts2Utils.renderText("true");
		} else {
			Struts2Utils.renderText("false");
		}
		return null;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String edit() throws Exception {
		entity = baseRoleGroupManager.get(id);
		return EDIT;
	}

}
