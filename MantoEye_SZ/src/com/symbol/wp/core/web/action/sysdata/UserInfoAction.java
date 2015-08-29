package com.symbol.wp.core.web.action.sysdata;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.symbol.app.mantoeye.sso.UnmpUserManager;
import com.symbol.app.mantoeye.util.CommonUtils;
import com.symbol.wp.core.constants.MsgConstants;
import com.symbol.wp.core.constants.VarConstants;
import com.symbol.wp.core.dto.VBasePermissions;
import com.symbol.wp.core.dto.VBaseUserInfo;
import com.symbol.wp.core.entity.TbBaseDepartment;
import com.symbol.wp.core.entity.TbBaseRolePermis;
import com.symbol.wp.core.entity.TbBaseRoles;
import com.symbol.wp.core.entity.TbBaseUserInfo;
import com.symbol.wp.core.exceptions.ServiceStartupException;
import com.symbol.wp.core.listener.LoginListener;
import com.symbol.wp.core.service.ICommonManager;
import com.symbol.wp.core.service.impl.BaseDepartmentManager;
import com.symbol.wp.core.service.impl.BasePermissionsManager;
import com.symbol.wp.core.service.impl.BaseRoleManager;
import com.symbol.wp.core.service.impl.BaseUserInfoManager;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.core.util.PropertiesUtil;
import com.symbol.wp.core.util.Utils;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.orm.PropertyFilter;
import com.symbol.wp.modules.orm.PropertyFilter.MatchType;
import com.symbol.wp.modules.orm.hibernate.HibernateWebUtils;
import com.symbol.wp.modules.struts2.Struts2Utils;
import com.symbol.wp.modules.struts2.base.BaseDispatchAction;
import com.symbol.wp.modules.struts2.base.IAction;
import com.symbol.wp.tools.gtgrid.server.GridServerHandler;

public class UserInfoAction extends BaseDispatchAction implements IAction {

	// 编辑用户
	private static final String ACTION_FROWORD_USER_EDIT = "edit";
	// 权限设值
	public static final String ACTION_FORWORD_USERPERMIS_LIST = "permtree";

	@Autowired
	private BaseUserInfoManager baseUserInfoManager;
	@Autowired
	private BaseRoleManager baseRoleManager;
	@Autowired
	private BaseDepartmentManager baseDepartmentManager;
	@Autowired
	private BasePermissionsManager basePermissionsManager;
	
	
	@Autowired
	private UnmpUserManager unmpUserManager;

	@Autowired
	@Qualifier("commonManagerImpl")
	private ICommonManager commonManagerImpl;

	// 基本属性
	private TbBaseUserInfo entity;
	private String id;
	private String keys;
	private Page<TbBaseUserInfo> page = new Page<TbBaseUserInfo>(
			VarConstants.PAGE_LONG_ROW_SIZE, true);

	private List<TbBaseRoles> rolesList;
	private List<TbBaseDepartment> deptsList;
	private Long roleId;

	public void query() throws ServletException, IOException {
		HttpServletRequest request = Struts2Utils.getRequest();
		List<TbBaseUserInfo> list = null;
		List<PropertyFilter> filters = HibernateWebUtils
				.buildPropertyFilters(Struts2Utils.getRequest());
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		// 角色查询
		String vcRoleNo = request.getParameter("role_search");
		if (vcRoleNo != null && !vcRoleNo.equals("")) {
			String vcUserNos = baseUserInfoManager.getUserIdByRoleId(vcRoleNo);
			String [] vun = vcUserNos.split(",");
			filters.add(new PropertyFilter("id", vun, MatchType.IN));
		}
		page.setOrderBy("id");
		page.setOrder("desc");
		page.setPageSize(gridServerHandler.getPageSize());
		page.setPageNo(gridServerHandler.getPageInfo().getPageNum());
		page = baseUserInfoManager.search(page, filters);
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
	private List formatViewData(List<TbBaseUserInfo> list) {
		List maplist = new ArrayList();
		Map map;
		TbBaseUserInfo bean;
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				bean = list.get(i);
				VBaseUserInfo view = baseUserInfoManager.converBeanToView(bean);
				map = new LinkedHashMap();
				map.put("id", view.getId());
				map.put("vcLoginName", CommonUtils.killNull(view
						.getVcLoginName()));
				map.put("vcUserName", CommonUtils
						.killNull(view.getVcUserName()));
				map.put("vcMobel", CommonUtils.killNull(view.getVcMobel()));
				map.put("vcEmail", CommonUtils.killNull(view.getVcEmail()));
				map.put("vcTelephone", CommonUtils.killNull(view
						.getVcTelephone()));
				map.put("roleName", CommonUtils.killNull(view.getRoleName()));
				map.put("deptName", CommonUtils.killNull(view.getDeptName()));

				map.put("tiUserType", CommonUtils
						.killNull(view.getTiUserType()));
				map.put("vcPassword", CommonUtils
						.killNull(view.getVcPassword()));
				maplist.add(map);
			}
		}
		return maplist;
	}

	/**
	 * 获取部门和角色信息
	 * 
	 * @throws Exception
	 */
	public void getAjaxXmlData() throws Exception {
		deptsList = baseDepartmentManager.getAll();// 获取所有部门
		rolesList = baseRoleManager.getAll();// 获取所有角色
		String deptXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
		if (deptsList != null && !deptsList.isEmpty()) {
			deptXml = deptXml + "<root>";
			for (TbBaseDepartment dept : deptsList) {
				deptXml = deptXml + "<data>" + "<id>" + dept.getId() + "</id>"
						+ "<name>" + dept.getVcDeptName() + "</name>"
						+ "</data>";
			}
			deptXml = deptXml + "</root>";
		}

		String roleXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
		if (rolesList != null && !rolesList.isEmpty()) {
			roleXml = roleXml + "<root>";
			for (TbBaseRoles role : rolesList) {
				roleXml = roleXml + "<data>" + "<id>" + role.getId() + "</id>"
						+ "<name>" + role.getVcRolesName() + "</name>"
						+ "</data>";
			}
			roleXml = roleXml + "</root>";
		}
		Struts2Utils.renderText(deptXml + "&&&" + roleXml);
	}

	/**
	 * 删除用户
	 */
	public void deleteUsers() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		String ids = request.getParameter("ids");
		String[] sids = ids.split(",");
		String msg = "";
		try {// 删除成功
			baseUserInfoManager.deleteByKeys(sids);
			msg = "删除用户成功!";
			commonManagerImpl.log(request, "删除用户[主键：" + ids + "]成功!");
		} catch (Exception e) { // 删除失败
			msg = "删除用户成功\\!";
			commonManagerImpl.log(request, "删除用户[主键：" + ids + "]失败!");
		}
		Struts2Utils.renderText(msg);
	}

	/**
	 * 获取角色对应菜单ID
	 */
	public void getAjaxRoleMenuIds() throws Exception {
		String menuIds = "";
		HttpServletRequest request = ServletActionContext.getRequest();
		String roleId = request.getParameter("role_id");
		List list = basePermissionsManager.getAllPermis(false);
		List permisList = baseRoleManager.getRolePermis(roleId);
		if (list != null && !list.isEmpty()) {
			for (Iterator it = list.iterator(); it.hasNext();) {
				VBasePermissions basePermisView = (VBasePermissions) it.next();
				String permis_id = basePermisView.getId();
				// 循环得到角色标识对应菜单
				if (permisList != null && !permisList.isEmpty()) {
					for (Iterator it2 = permisList.iterator(); it2.hasNext();) {
						TbBaseRolePermis tbBaseRolePermis = (TbBaseRolePermis) it2
								.next();
						if (permis_id.equals(tbBaseRolePermis.getVcPermId())) {
							menuIds = menuIds + permis_id + ":";
						}
					}
				}
			}
		}
		if (!"".equals(menuIds)) {
			menuIds = menuIds.substring(0, menuIds.lastIndexOf(":"));
		}
		Struts2Utils.renderText(menuIds);
	}

	public void setKeys(String keys) {
		this.keys = keys;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Page<TbBaseUserInfo> getPage() {
		return page;
	}

	public TbBaseUserInfo getModel() {

		return entity;
	}

	/**
	 * 支持使用Jquery.validate Ajax检验用户名是否重复.
	 */
	public String checkUnique() {
		HttpServletRequest request = Struts2Utils.getRequest();
		String loginName = request.getParameter("entity.vcLoginName");
		String oldLoginName = request.getParameter("oldLoginName");
		if (baseUserInfoManager.isPropertyUnique("vcLoginName", loginName,
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
		HttpServletRequest request = Struts2Utils.getRequest();
		rolesList = baseRoleManager.getAll();// 获取所有角色
		deptsList = baseDepartmentManager.getAll();// 获取所有部门
		request.setAttribute("rolesList", rolesList);
		request.setAttribute("deptsList", deptsList);
		// 设置角色标识，供页面保留查询条件使用
		request.setAttribute("search_vcRoleNo", request
				.getParameter("vcRoleNo2"));
		// 设置部门标识，供页面保留查询条件使用
		request.setAttribute("search_vcDeptNo", request
				.getParameter("filter_LIKE_vcDeptNo"));

		// 调用分页方法
		this.setPaginationdataList();
		logger.error("日志测试");
		return INDEX;
	}

	/**
	 * 获取编辑用户对象
	 * 
	 * @return
	 */
	public String openEditInfo() {
		TbBaseUserInfo beanView = baseUserInfoManager.get(id);
		ServletActionContext.getRequest().setAttribute("beanView", beanView);
		return ACTION_FROWORD_USER_EDIT;
	}

	/**
	 * 转到添加编辑页面
	 */
	public String input() throws Exception {
		return INPUT;
	}

	/**
	 * 保存用户信息
	 */
	public String save() throws ServiceStartupException {
		HttpServletRequest request = Struts2Utils.getRequest();
		String msg = "";
		String usemd5 = PropertiesUtil.getInstance().getProperty(
				"system.password.save.encry");// 数据库保存密码是否需要md5加密
		String newpw = entity.getVcPassword();
		if (usemd5 != null && usemd5.equalsIgnoreCase("MD5")) {
			newpw = Utils.getMD5String(entity.getVcPassword());
		}

		try {
			entity.setVcPassword(newpw);
			baseUserInfoManager.save(entity);
			request.setAttribute(VarConstants.SUCC_CODE,
					MsgConstants.SUCC_CODE_00101);
			msg = "添加用户信息[登录名：" + entity.getVcLoginName() + "]成功!";
			return SUCCESS;
		} catch (Exception e) {
			logger.error(e.getMessage());
			request.setAttribute(VarConstants.ERROR_CODE,
					MsgConstants.ERROR_CODE_00004);
			msg = "添加用户信息[登录名：" + entity.getVcLoginName() + "]失败!";
			return ERROR;
		} finally {
			commonManagerImpl.log(request, msg);
		}
	}

	/**
	 * 保存用户信息
	 */
	public String update() throws ServiceStartupException {
		HttpServletRequest request = Struts2Utils.getRequest();
		String msg = "";
		try {
			baseUserInfoManager.save(entity);
			msg = " 编辑用户信息[登录名：" + entity.getVcLoginName() + "]成功!";
			request.setAttribute(VarConstants.SUCC_CODE,
					MsgConstants.SUCC_CODE_00102);
			return SUCCESS;
		} catch (Exception e) {
			request.setAttribute(VarConstants.ERROR_CODE,
					MsgConstants.ERROR_CODE_00004);
			msg = "编辑用户信息[登录名：" + entity.getVcLoginName() + "]失败!";
			return ERROR;
		} finally {
			commonManagerImpl.log(request, msg);
		}
	}

	/**
	 * 删除用户
	 */
	public String delete() throws ServiceStartupException {
		HttpServletRequest request = Struts2Utils.getRequest();
		String[] sids = keys.split(",");
		String msg = null;
		try {// 删除成功
			baseUserInfoManager.deleteByKeys(sids);
			request.setAttribute(VarConstants.SUCC_CODE,
					MsgConstants.SUCC_CODE_00008);
			request.setAttribute(VarConstants.URL, "/user_list.do?1=1");
			msg = "删除用户信息[用户主键：" + keys + "]成功!";
			commonManagerImpl.log(request, msg);
			return SUCCESS;
		} catch (Exception e) { // 删除失败
			request.setAttribute(VarConstants.ERROR_CODE,
					MsgConstants.ERROR_CODE_00005);
			msg = "删除用户信息[用户主键：" + keys + "]失败!";
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
		HttpServletRequest request = Struts2Utils.getRequest();
		List<PropertyFilter> filters = HibernateWebUtils
				.buildPropertyFilters(request);

		// 如果选择按角色查询条件，则设置角色的过滤
		String vcRoleNo = request.getParameter("vcRoleNo2");
		if (vcRoleNo != null && !vcRoleNo.equals("")) {
			String vcUserNos = baseUserInfoManager.getUserIdByRoleId(vcRoleNo);
			String[] vun = vcUserNos.split(",");
			filters.add(new PropertyFilter("id", vun, MatchType.IN));
		}
		if (page.getOrderBy() == null) {
			page.setOrderBy("id");
		}
		page = baseUserInfoManager.search(page, filters);
		List dataList = page.getVresult();
		request.setAttribute("dataList", dataList);
	}

	/**
	 * 打开权限树
	 */
	public String openPermis() throws ServiceStartupException {
		HttpServletRequest request = Struts2Utils.getRequest();
		String newrole_id = request.getParameter("role_id");
		List listdata = baseRoleManager.getAll();
		TbBaseUserInfo userInfo = baseUserInfoManager.get(id);
		List rolesIdList = null;
		if (Common.judgeString(newrole_id)) {
			rolesIdList = new ArrayList();
			rolesIdList.add(newrole_id);
		} else {
			// 根据用户标识获取角色标识集合
			rolesIdList = baseUserInfoManager.getRoles(userInfo.getId());
		}
		request.setAttribute("rolesIdList", rolesIdList);
		request.setAttribute("listdata", listdata);
		request.setAttribute("userId", id);
		request.setAttribute("vcUserNo", userInfo.getId());
		request.setAttribute("userName", entity == null ? "" : entity
				.getVcUserName());
		return ACTION_FORWORD_USERPERMIS_LIST;
	}

	/**
	 * 插入用户权限
	 */
	public String insertPermis() throws ServiceStartupException {

		HttpServletRequest request = Struts2Utils.getRequest();
		String vcUserNo = request.getParameter("vcUserNo");
		String vcRoleNo = request.getParameter("vcRoleNo");
		String msg = "";
		boolean bo = baseUserInfoManager.inserUserRoles(vcUserNo, vcRoleNo,
				request);
		if (bo) {
			request.setAttribute(VarConstants.SUCC_CODE,
					MsgConstants.SUCC_CODE_00104);
			msg = "插入角色权限[权限关联字段：" + "vcUserNo:" + vcUserNo + " vcRoleNo:"
					+ vcRoleNo + "]成功!";
			commonManagerImpl.log(request, msg);
			return SUCCESS;
		} else {
			request.setAttribute(VarConstants.ERROR_CODE,
					MsgConstants.ERROR_CODE_00009);
			request.setAttribute(VarConstants.URL, "/user_list.do?1=1");
			msg = "插入角色权限[权限关联字段：" + "vcUserNo:" + vcUserNo + " vcRoleNo:"
					+ vcRoleNo + "]失败!";
			commonManagerImpl.log(request, msg);
			return ERROR;
		}
	}

	/**
	 * 方法需修改测试
	 */
	public String doassociate() throws ServiceStartupException {
		HttpServletRequest request = Struts2Utils.getRequest();
		String userIds = keys;
		String roleNo = request.getParameter("roleNo");
		Long rolesId = roleId;
		boolean bo = baseUserInfoManager.inserMutilUserRoles(userIds, roleNo,
				request);
		if (bo) {
			addActionMessage(MsgConstants.SUCC_CODE_00028);
			return SUCCESS;
		} else {
			addActionMessage(MsgConstants.ERROR_CODE_00009);
			return ERROR;
		}
	}

	/**
	 * 重置密码
	 */
	public String resetPwd() throws ServiceStartupException {
		HttpServletRequest request = Struts2Utils.getRequest();
		String sids = Common.FormatString(keys, "0");
		boolean updateBoolean = baseUserInfoManager.updatePwdReset(sids,
				request);
		String msg = "";
		if (updateBoolean) {
			request.setAttribute(VarConstants.SUCC_CODE,
					MsgConstants.SUCC_CODE_00003);
			request.setAttribute(VarConstants.URL, "/user_list.do?1=1");
			msg = "重置用户密码[用户主键：" + keys + "]成功!";
			commonManagerImpl.log(request, msg);
			return SUCCESS;
		} else {
			request.setAttribute(VarConstants.ERROR_CODE,
					MsgConstants.ERROR_CODE_00007);
			request.setAttribute(VarConstants.URL, "/user_list.do?1=1");
			msg = "重置用户密码[用户主键：" + keys + "]失败!";
			commonManagerImpl.log(request, msg);
			return ERROR;
		}
	}

	/**
	 * 更新密码
	 */
	public String updatePwd() throws ServiceStartupException {
		HttpServletRequest request = Struts2Utils.getRequest();
		String oldPwd = request.getParameter("oldPwd");
		String newPwd = request.getParameter("newPwd");
		String msg = "";
		if (baseUserInfoManager.updatePwd(oldPwd, newPwd, request)) {
			request.setAttribute(VarConstants.SUCC_CODE,
					MsgConstants.SUCC_CODE_00106);
			msg = "更新用户密码[新密码：" + newPwd + "]成功!";
			commonManagerImpl.log(request, msg);
			return SUCCESS;
		} else {
			request.setAttribute(VarConstants.ERROR_CODE,
					MsgConstants.ERROR_CODE_00008);
			request.setAttribute(VarConstants.URL, "/welcome.jsp.do?1=1");
			msg = "更新用户密码[新密码：" + newPwd + "]失败!";
			commonManagerImpl.log(request, msg);
			return ERROR;
		}
	}

	/**
	 * 核查旧密码合法性
	 */
	public String checkPwd() throws ServiceStartupException {
		HttpServletRequest request = Struts2Utils.getRequest();
		String oldPwd = request.getParameter("oldPwd");
		LoginListener loginListener = (LoginListener) ServletActionContext
				.getRequest().getSession().getAttribute(
						VarConstants.LOGIN_LISTENER_KEY);
		String userid = loginListener.getSessionContainer().getUserInfo()
				.getId();
		TbBaseUserInfo userInfo = baseUserInfoManager.get(userid);
		if (Utils.getMD5String(oldPwd).equals(userInfo.getVcPassword())) {
			Struts2Utils.renderText("true");
		} else {
			Struts2Utils.renderText("false");
		}
		return null;
	}

	/**
	 * 同步统一网管平台用户信息
	 */
	public void getAllUnmpUserInfo(){
		String msg = "";
		try{
			boolean b = unmpUserManager.getAllUnmpUserInfo();
			if(b){
				msg = "同步用户成功！";
			}else{
				msg = "同步用户失败！";
			}
		}catch(Exception e){
			logger.error(e.getMessage());
			msg = "同步用户失败！";
		}
		Struts2Utils.renderText(msg);
	}
	
	public String edit() throws Exception {
		entity = baseUserInfoManager.get(id);
		return EDIT;
	}

	public TbBaseUserInfo getEntity() {
		return entity;
	}

	public void setEntity(TbBaseUserInfo entity) {
		this.entity = entity;
	}

	public String getKeys() {
		return keys;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
