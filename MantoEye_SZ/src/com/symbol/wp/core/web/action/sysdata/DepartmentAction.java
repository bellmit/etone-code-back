package com.symbol.wp.core.web.action.sysdata;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.symbol.wp.core.constants.MsgConstants;
import com.symbol.wp.core.constants.VarConstants;
import com.symbol.wp.core.dto.VBaseDepartment;
import com.symbol.wp.core.entity.TbBaseDepartment;
import com.symbol.wp.core.exceptions.ServiceStartupException;
import com.symbol.wp.core.service.ICommonManager;
import com.symbol.wp.core.service.impl.BaseDepartmentManager;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.orm.PropertyFilter;
import com.symbol.wp.modules.orm.hibernate.HibernateWebUtils;
import com.symbol.wp.modules.struts2.Struts2Utils;
import com.symbol.wp.modules.struts2.base.BaseDispatchAction;
import com.symbol.wp.modules.struts2.base.IAction;

public class DepartmentAction extends BaseDispatchAction implements IAction {

	// 编辑部门
	private static final String ACTION_FROWORD_USER_EDIT = "edit";

	@Autowired
	private BaseDepartmentManager baseDepartmentManager;
	@Autowired
	@Qualifier("commonManagerImpl")
	private ICommonManager commonManagerImpl;

	// 基本属性
	private TbBaseDepartment entity;
	private String id;
	private String keys;
	HttpServletRequest request = ServletActionContext.getRequest();
	private Page<TbBaseDepartment> page = new Page<TbBaseDepartment>(
			VarConstants.PAGE_LONG_ROW_SIZE, true);

	public void setId(String id) {
		this.id = id;
	}

	public void setKeys(String keys) {
		this.keys = keys;
	}

	public Page<TbBaseDepartment> getPage() {
		return page;
	}

	public TbBaseDepartment getModel() {

		return entity;
	}

	/**
	 * 支持使用Jquery.validate Ajax检验用户名是否重复.
	 */
	public String checkUnique() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String loginName = request.getParameter("vcLoginName");
		String oldLoginName = request.getParameter("oldLoginName");
		if (baseDepartmentManager.isPropertyUnique("vcLoginName", loginName,
				oldLoginName)) {
			Struts2Utils.renderText("true");
		} else {
			Struts2Utils.renderText("false");
		}
		return null;
	}

	/**
	 * 列表显示页面 分页显示总行时有问题，分页还需要测试
	 */
	public String list() throws Exception {
		this.setPaginationdataList();
		return INDEX;
	}

	/**
	 * 转到添加编辑页面
	 */
	@Override
	public String input() throws Exception {
		return INPUT;
	}

	/**
	 * 保存部门对象
	 */
	public String save() throws ServiceStartupException {
		String msg = "";
		try {
			// 新建(需设置部门唯一标志字符串)
//			entity.setId(NoUtil.getRandomString("dept"));
			baseDepartmentManager.save(entity);
			request.setAttribute(VarConstants.SUCC_CODE,
					MsgConstants.SUCC_CODE_00001);
			msg = "添加部门信息[部门标识：" + entity.getId() + "]成功!";
			commonManagerImpl.log(request, msg);

			// 定向地址
			request.setAttribute(VarConstants.URL, "/dept_list.do?1=1");
			return SUCCESS;
		} catch (Exception e) {
			request.setAttribute(VarConstants.ERROR_CODE,
					MsgConstants.ERROR_CODE_00004);
			request.setAttribute(VarConstants.URL, "/dept_list.do?1=1");
			msg = "添加部门信息[部门标识：" + entity.getId() + "]失败!";
			commonManagerImpl.log(request, msg);
			return ERROR;
		}
	}

	public String update() throws ServiceStartupException {
		String msg = "";
		try {
			logger.info("id----------o>"+entity.getId());
			baseDepartmentManager.save(entity);
			request.setAttribute(VarConstants.SUCC_CODE,
					MsgConstants.SUCC_CODE_00002);
			msg = "编辑部门信息[部门标识：" + entity.getId() + "]成功!";
			commonManagerImpl.log(request, msg);
			// 定向地址
			request.setAttribute(VarConstants.URL, "/dept_list.do?1=1");
			return SUCCESS;
		} catch (Exception e) {
			request.setAttribute(VarConstants.ERROR_CODE,
					MsgConstants.ERROR_CODE_00004);
			request.setAttribute(VarConstants.URL, "/dept_list.do?1=1");
			msg = "添加部门信息[部门标识：" + entity.getId() + "]失败!";
			commonManagerImpl.log(request, msg);
			return ERROR;
		}
	}

	/**
	 * 删除部门
	 */
	public String delete() throws ServiceStartupException {
		String[] sids = keys.split(",");
		String msg = "";
		try {
			baseDepartmentManager.deleteByKeys(sids);
			request.setAttribute(VarConstants.SUCC_CODE,
					MsgConstants.SUCC_CODE_00008);
			request.setAttribute(VarConstants.URL, "/dept_list.do?1=1");
			msg = "删除部门信息[部门主键：" + keys + "]成功!";
			commonManagerImpl.log(request, msg);
			return SUCCESS;
		} catch (Exception e) {
			request.setAttribute(VarConstants.ERROR_CODE,
					MsgConstants.ERROR_CODE_00005);
			request.setAttribute(VarConstants.URL, "/dept_list.do?1=1");
			msg = "删除部门信息[部门主键：" + keys + "]失败!";
			commonManagerImpl.log(request, msg);
			return ERROR;
		}
	}

	/**
	 * 分页查询数据
	 * 
	 * @throws ServiceStartupException
	 */
	public void setPaginationdataList() throws ServiceStartupException {
		HttpServletRequest request = ServletActionContext.getRequest();
		List<PropertyFilter> filters = HibernateWebUtils
				.buildPropertyFilters(request);
		if (page.getOrderBy() == null) {
			page.setOrderBy("id");
		}

		page = baseDepartmentManager.search(page, filters);
		List dataList = page.getVresult();
		List viewList = null;
		if (dataList != null && !dataList.isEmpty()) {
			viewList = new ArrayList();
			for (Iterator it = dataList.iterator(); it.hasNext();) {
				TbBaseDepartment bean = (TbBaseDepartment) it.next();
				VBaseDepartment view = baseDepartmentManager
						.convertBeanToView(bean);
				viewList.add(view);
			}
		}
		request.setAttribute("dataList", viewList);
	}

	/**
	 * 获取编辑部门信息
	 * 
	 * @return
	 */
	public String edit() throws Exception {
		entity = baseDepartmentManager.get(id);
		return EDIT;
	}

	public String getKeys() {
		return keys;
	}

	public TbBaseDepartment getEntity() {
		return entity;
	}

	public void setEntity(TbBaseDepartment entity) {
		this.entity = entity;
	}

}
