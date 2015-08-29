package com.symbol.wp.core.tag;

import java.util.List;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.symbol.wp.core.dao.impl.BaseRoleGroupDAO;
import com.symbol.wp.core.dto.VBaseRoleGroup;
import com.symbol.wp.core.util.Common;

public class RoleGroupTag extends BaseTreeTag {

	private final Logger logger = LoggerFactory.getLogger(RoleGroupTag.class);

	private String title = "";
	private String name = "";
	private String selectID = null;
	private String action = "";

	public int doStartTag() throws JspTagException {
		return EVAL_BODY_INCLUDE;
	}

	public int doEndTag() throws JspTagException {
		JspWriter out = pageContext.getOut();
		try {
			String contents = this.DlDeptTree(title, name, selectID, action);
			out.println(Common.executeJavaScript(contents));
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return EVAL_PAGE;
	}

	public String DlTree(String title, String name, String selectID,
			String action) {
		String array[] = { "com.symbol.wp.core.dto.VBaseRoleGroup", "getId",
				"getVcParentId", "getVcName" };
		String contents = "";
		BaseRoleGroupDAO baseRoleGroupDAO = (BaseRoleGroupDAO) this
				.getSpringBean("baseRoleGroupDAO");
		List<VBaseRoleGroup> list = baseRoleGroupDAO.getRoleGroup();
		logger.info("list=" + list);
		try {
			logger.info("--生成部门数--");
			contents = this.DeptDlTree(title, name, selectID, action, list,
					array);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return contents;
	}

	public String DlDeptTree(String title, String name, String selectID,
			String action) {
		String array[] = { "com.symbol.wp.core.dto.VBaseRoleGroup", "getId",
				"getVcParentId", "getVcName" };
		String contents = "";
		BaseRoleGroupDAO baseRoleGroupDAO = (BaseRoleGroupDAO) this
				.getSpringBean("baseRoleGroupDAO");
		List<VBaseRoleGroup> list = baseRoleGroupDAO.getRoleGroup();
		// logger.info("--生成部门数--"+list.size());
		try {
			logger.info("--生成部门数--");
			contents = this.DeptDlTree(title, name, selectID, action, list,
					array);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return contents;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSelectID() {
		return selectID;
	}

	public void setSelectID(String selectID) {
		this.selectID = selectID;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
}
