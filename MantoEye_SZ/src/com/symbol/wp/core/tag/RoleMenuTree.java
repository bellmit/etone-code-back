package com.symbol.wp.core.tag;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.symbol.wp.core.dao.impl.BasePermissionsDAO;
import com.symbol.wp.core.dao.impl.BaseRolePermisDAO;
import com.symbol.wp.core.dao.impl.BaseRolesDAO;
import com.symbol.wp.core.dto.VBasePermissions;
import com.symbol.wp.core.entity.TbBaseRolePermis;
import com.symbol.wp.core.entity.TbBaseRoles;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.core.util.PropertiesUtil;

public class RoleMenuTree extends BaseTreeTag {

	private final Logger logger = LoggerFactory.getLogger(RoleMenuTree.class);

	private String role_id = "";
	private String title = "";
	private String disabled;
	private String roleNo;

	public String getRoleNo() {
		return roleNo;
	}

	public void setRoleNo(String roleNo) {
		this.roleNo = roleNo;
	}

	public String getDisabled() {
		return disabled;
	}

	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}

	public int doStartTag() throws JspTagException {
		return EVAL_BODY_INCLUDE;
	}

	public int doEndTag() throws JspTagException {
		JspWriter out = pageContext.getOut();
		try {

			String contents = this.roleMenuTree(role_id, title, roleNo,
					disabled);
			out.println(Common.executeJavaScript(contents));
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return EVAL_PAGE;
	}

	// 设置角色开放菜单,用于权限设置
	public String roleMenuTree(String roleId, String Title, String roleNo,
			String Disabled) throws Exception {
		String permis_id = "";
		String opt_id = "-";
		String permis_read = null;
		String permis_create = null;
		String permis_update = null;
		String permis_delete = null;
		StringBuffer contents = new StringBuffer();
		BasePermissionsDAO basePermissionsDAO = (BasePermissionsDAO) this
				.getSpringBean("basePermissionsDAO");
		BaseRolePermisDAO baseRolePermisDAO = (BaseRolePermisDAO) this
				.getSpringBean("baseRolePermisDAO");
		BaseRolesDAO baseRoleDAO = (BaseRolesDAO) this
				.getSpringBean("baseRolesDAO");

		TbBaseRoles tbBaseRoles = baseRoleDAO.get(roleId);
		Map<String, TbBaseRolePermis> map = baseRolePermisDAO
				.findByRoleNo(tbBaseRoles.getId());

		List list = basePermissionsDAO.getAllPermis(true);
		contents.append(" var d = new dTree(\"d\", true);");

		contents.append("	d.add('BASE',-1,\"" + Title
				+ "\",\"#\",'','','','','','',1,1);\n");
		if (list != null && !list.isEmpty()) {
			for (Iterator it = list.iterator(); it.hasNext();) {
				VBasePermissions basePermisView = (VBasePermissions) it.next();
				if ("false".equals(PropertiesUtil.getInstance().getProperty(
						"system.role.look.all"))) {
					if (map.get(basePermisView.getId()) == null)
						continue;
				}
				permis_read = "0";
				permis_create = "1";
				permis_update = "0";
				permis_delete = "0";
				permis_id = String.valueOf(basePermisView.getId());

				TbBaseRolePermis baseRolePermis = baseRolePermisDAO
						.getRolePermisByRP(roleNo, basePermisView.getId());

				if (baseRolePermis != null) {
					permis_read = "1";

				}
				contents.append("	d.add(");
				contents.append("'");
				contents.append(basePermisView.getId());
				contents.append("'");
				contents.append(",");
				contents.append("'");
				contents.append(basePermisView.getVcParentId());
				contents.append("'");
				contents.append(",\"");
				contents.append(basePermisView.getVcMenuName());
				contents.append("\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",");
				contents.append(permis_read);
				contents.append(", ");
				contents.append(Disabled);
				contents.append(");\n");
			}
		}
		contents.append("	document.write(d);\n");
		contents.append(" d.openAll();");
		return contents.toString();
	}

	public String getRole_id() {
		return role_id;
	}

	public void setRole_id(String role_id) {
		this.role_id = role_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
