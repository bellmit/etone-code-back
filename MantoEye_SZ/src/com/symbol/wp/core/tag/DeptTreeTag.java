package com.symbol.wp.core.tag;

import java.util.List;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.symbol.wp.core.dao.impl.BaseDepartmentDAO;
import com.symbol.wp.core.dto.VBaseDepartment;
import com.symbol.wp.core.util.Common;

public class DeptTreeTag extends BaseTreeTag {

	private String title = "";
	private String name = "";
	private String selectID = null;
	private String action = "";

	private Logger logger = LoggerFactory.getLogger(DeptTreeTag.class);

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

	public String DlDeptTree(String title, String name, String selectID,
			String action) {
		String array[] = { "com.symbol.wp.core.dto.VBaseDepartment", "getId",
				"getVcParentId", "getVcDeptName" };
		String contents = "";
		BaseDepartmentDAO departmentDAO = (BaseDepartmentDAO) this
				.getSpringBean("baseDepartmentDAO");
		List<VBaseDepartment> List = departmentDAO.getDept();
		try {
			contents = this.DeptDlTree(title, name, selectID, action, List,
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
