package com.symbol.wp.core.tag;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.symbol.wp.core.constants.VarConstants;
import com.symbol.wp.core.dao.impl.BasePermissionsDAO;
import com.symbol.wp.core.entity.TbBasePermissions;
import com.symbol.wp.core.listener.LoginListener;

public class ButtonTag extends TagSupport {

	private Logger logger = LoggerFactory.getLogger(ButtonTag.class);

	private String onmouseoverClassName = "";
	private String onmouseoutClassName = "";
	private String className = "";
	private String defaultCharLength = "17";

	public String getDefaultCharLength() {
		return defaultCharLength;
	}

	public void setDefaultCharLength(String defaultCharLength) {
		this.defaultCharLength = defaultCharLength;
	}

	public Object getSpringBean(String beanName) {
		return WebApplicationContextUtils.getRequiredWebApplicationContext(
				this.pageContext.getServletContext()).getBean(beanName);
	}

	@Override
	public int doStartTag() throws JspTagException {

		return EVAL_BODY_INCLUDE;
	}

	// <div id="mainbtn">
	// <ul>
	// <li><a href="#" onclick="newRecord('ck');"><span>添加</span></a></li>
	// <li><a href="#" onclick="editRecord('ck');"><span>编辑</span></a></li>
	// <li><a href="#"
	// onclick="deleteSelectedRecord('ck');"><span>删除</span></a></li>
	// </ul>
	// </div>

	@Override
	public int doEndTag() throws JspTagException {
		JspWriter out = pageContext.getOut();
		String parentPermId = pageContext.getRequest().getParameter("permId");//
		LoginListener loginListener = (LoginListener) pageContext.getSession()
				.getAttribute(VarConstants.LOGIN_LISTENER_KEY);
		BasePermissionsDAO basePermissionsDAO = (BasePermissionsDAO) this
				.getSpringBean("basePermissionsDAO");
		List list = basePermissionsDAO.getButtonPermis(parentPermId,
				loginListener.getSessionContainer().getUserInfo().getId());//
		StringBuffer contents = new StringBuffer();
		int length = 0;
		if (list != null && !list.isEmpty()) {
			contents.append("<div id=\"" + className + "\"><ul>");
			for (Iterator it = list.iterator(); it.hasNext();) {
				TbBasePermissions permissions = (TbBasePermissions) it.next();
				length = buttonWidth(permissions.getVcMenuName().trim()
						.length());
				logger.info("length:" + length);
				String href = "javascript:"
						+ permissions.getVcRedirectUrl().replaceAll(
								"#subPermId",
								String.valueOf(permissions.getId()));
				contents.append("<li><a href=\"");
				contents.append(href);
				contents.append("\"");
				contents.append(" style=\"width:" + length + "px;\"");
				contents.append("><span>");
				contents.append(permissions.getVcMenuName()
						+ "</span></a></li>\n");
				logger.info("----------->button--->" + contents);
			}
			contents.append("</ul></div>");
		} else {
			contents.append("<div id=\"" + className + "\"></div>");
		}

		try {
			out.println(contents.toString());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		return EVAL_PAGE;
	}

	public String getOnmouseoverClassName() {
		return onmouseoverClassName;
	}

	public void setOnmouseoverClassName(String onmouseoverClassName) {
		this.onmouseoverClassName = onmouseoverClassName;
	}

	public String getOnmouseoutClassName() {
		return onmouseoutClassName;
	}

	public void setOnmouseoutClassName(String onmouseoutClassName) {
		this.onmouseoutClassName = onmouseoutClassName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	/*
	 * 通过按钮字数计算按钮长度 @param size 按钮字数 @return 按钮长度单位为px
	 */
	private int buttonWidth(int size) {
		int l = 17;
		try {
			l = Integer.parseInt(defaultCharLength);
		} catch (Exception e) {
			l = 17;
		}
		return l * size + 12;
	}
}
