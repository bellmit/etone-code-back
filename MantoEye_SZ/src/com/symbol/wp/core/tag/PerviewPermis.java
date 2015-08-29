package com.symbol.wp.core.tag;

import java.util.Iterator;
import java.util.List;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.symbol.wp.core.dao.impl.BasePermissionsDAO;
import com.symbol.wp.core.dao.impl.BaseRolePermisDAO;
import com.symbol.wp.core.dto.VBasePermissions;
import com.symbol.wp.core.entity.TbBaseRolePermis;
import com.symbol.wp.core.util.Common;

public class PerviewPermis extends BaseTreeTag {

	private Logger logger = LoggerFactory.getLogger(PerviewPermis.class);

	private String title = "";

	public int doStartTag() throws JspTagException {
		return EVAL_BODY_INCLUDE;
	}

	public int doEndTag() throws JspTagException {
		JspWriter out = pageContext.getOut();
		try {
			List roleList = (List) pageContext.getRequest().getAttribute(
					"rolesIdList");
			String contents = this.perviewPermis(title, roleList);
			out.println(Common.executeJavaScript(contents));
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return EVAL_PAGE;
	}

	// 设置用户开放菜单,用于权限设置
	public String perviewPermis(String Title, List<String> roleList)
			throws Exception {
		String opt_id = "-";
		String permis_id;
		String permis_select = null;
		String permis_read = null;
		String permis_create = null;
		String permis_update = null;
		String permis_delete = null;
		String ext_msg = null;
		String role_list = "0";
		BaseRolePermisDAO baseRolePermisDAO = (BaseRolePermisDAO) this
				.getSpringBean("baseRolePermisDAO");
		BasePermissionsDAO basePermissionsDAO = (BasePermissionsDAO) this
				.getSpringBean("basePermissionsDAO");
		if (roleList != null && !roleList.isEmpty()) {
			role_list = roleList.toString().replaceAll("\\[|\\]", "");
		}
		logger.info("role_list---------->" + role_list);
		StringBuffer contents = new StringBuffer();
		// 获取所有的菜单(包括下级按钮)(对应菜单表tbBasePermissions)
		List list = basePermissionsDAO.getAllPermis(false);
		// 根据角色标识获取菜单信息(对应角色中间表tbBaseRolePermis)
		List permisList = baseRolePermisDAO.getRolePermis(role_list);
		contents.append(" var d = new dTree(\"d\", true);");
		if (permisList != null && !permisList.isEmpty()) {
			contents.append("	d.add('BASE',-1,\"" + Title
					+ "\",\"#\",\"\",\"\",\"\",\"\",\"\",\"\",1,1);\n");
		} else {
			contents.append("	d.add('BASE',-1,\"" + Title
					+ "\",\"#\",\"\",\"\",\"\",\"\",\"\",\"\",0,1);\n");
		}
		// 循环得到全部菜单
		if (list != null && !list.isEmpty()) {
			for (Iterator it = list.iterator(); it.hasNext();) {
				VBasePermissions basePermisView = (VBasePermissions) it.next();
				permis_select = "0";
				permis_read = "0";
				permis_create = "0";
				permis_update = "0";
				permis_delete = "0";
				permis_id = basePermisView.getId();
				// 循环得到角色标识对应菜单
				if (permisList != null && !permisList.isEmpty()) {
					for (Iterator it2 = permisList.iterator(); it2.hasNext();) {
						TbBaseRolePermis tbBaseRolePermis = (TbBaseRolePermis) it2
								.next();
						if (permis_id.equals(tbBaseRolePermis.getVcPermId())) {
							permis_select = "1";
						}
					}
				}
				contents.append("	d.add('");
				contents.append(permis_id);
				contents.append("','");
				contents.append(basePermisView.getVcParentId());

				contents.append("',\"");
				contents.append(basePermisView.getVcMenuName());
				// contents.append(ext_msg);
				contents.append("\",\"\",\"\",\"\",\"\",\"\",\"\",\"\","
						+ permis_select + ", 1);\n");
				if (basePermisView.getTiPermType() == 2L) {
				}
			}
		}
		contents.append("	document.write(d);\n");
		contents.append(" d.openAll();");
		return contents.toString();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
