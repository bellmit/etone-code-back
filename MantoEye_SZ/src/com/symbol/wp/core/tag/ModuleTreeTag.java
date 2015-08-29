package com.symbol.wp.core.tag;

import java.util.List;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.symbol.wp.core.dao.impl.BasePermissionsDAO;
import com.symbol.wp.core.dto.VBasePermissions;
import com.symbol.wp.core.util.Common;

public class ModuleTreeTag extends BaseTreeTag {

	private Logger logger = LoggerFactory.getLogger(ModuleTreeTag.class);

	private String title = "";
	private String name = "";
	private String selectID = null;
	private String action = "";
	private String searchFlag = "";

	public String getSearchFlag() {
		return searchFlag;
	}

	public void setSearchFlag(String searchFlag) {
		this.searchFlag = searchFlag;
	}

	@Override
	public int doStartTag() throws JspTagException {
		return EVAL_BODY_INCLUDE;
	}

	@Override
	public int doEndTag() throws JspTagException {
		JspWriter out = pageContext.getOut();
		try {
			String contents = this.DlModuleTree(title, name, selectID, action);
			out.println(Common.executeJavaScript(contents));
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return EVAL_PAGE;
	}

	public String DlModuleTree(String Title, String name, String SelectID,
			String Action) throws Exception {
		String array[] = { "com.symbol.wp.core.dto.VBasePermissions", "getId",
				"getVcParentId", "getVcMenuName" };
		String contents = "";
		BasePermissionsDAO basePermissionsDAO = (BasePermissionsDAO) this
				.getSpringBean("basePermissionsDAO");
		// 要修改basePermissionsDAO的getPermis方法
		List<VBasePermissions> list = basePermissionsDAO.getPermis();
		try {
			// 主键关联就用数字的，标识字段管理，就用字符
			contents = this.MenuDlTree(Title, name, SelectID, Action, list,
					array, searchFlag);
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
