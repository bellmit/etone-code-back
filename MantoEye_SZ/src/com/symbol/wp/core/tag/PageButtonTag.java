package com.symbol.wp.core.tag;

import java.io.IOException;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.symbol.wp.core.util.Common;
import com.symbol.wp.modules.orm.Page;

public class PageButtonTag extends TagSupport {

	private Logger logger = LoggerFactory.getLogger(PageButtonTag.class);

	private String checkboxName;
	private String url;
	private String trClassName = "";
	private String tbClassName = "dg_pagestyle";
	private String tdClassName = "pageinfo";
	private String tdClassName1 = "pagebutton";
	private String onsubmit = "0";

	public String getOnsubmit() {
		return onsubmit;
	}

	public void setOnsubmit(String onsubmit) {
		this.onsubmit = onsubmit;
	}

	public String getTrClassName() {
		return trClassName;
	}

	public void setTrClassName(String trClassName) {
		this.trClassName = trClassName;
	}

	public String getCheckboxName() {
		return checkboxName;
	}

	public void setCheckboxName(String checkboxName) {
		this.checkboxName = checkboxName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public int doStartTag() throws JspTagException {

		return EVAL_BODY_INCLUDE;
	}

	// 新模板
	@Override
	public int doEndTag() throws JspTagException {
		Long nmParentPermId = Common.StringToLong(pageContext.getRequest()
				.getParameter("id"));// 权限ID
		this.url = this.url + "&id=" + nmParentPermId;

		JspWriter out = pageContext.getOut();
		Page pageControler = pageContext.getRequest().getAttribute("page") != null ? (Page) pageContext
				.getRequest().getAttribute("page")
				: new Page();
		StringBuffer contents = new StringBuffer();

		contents
				.append("<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\"  class=\""
						+ tbClassName + "\">");
		contents.append("<tr class=\"" + trClassName + "\"><td class=\""
				+ tdClassName + "\"> ");
		contents
				.append("<input type=\"hidden\" name=\"page.pageNo\" id=\"pageNo\" value=\""
						+ pageControler.getPageNo() + "\"/>");
		contents
				.append("<input type=\"hidden\" name=\"page.orderBy\" id=\"orderBy\" value=\""
						+ pageControler.getOrderBy() + "\"/>");
		contents
				.append("<input type=\"hidden\" name=\"page.order\" id=\"order\" value=\""
						+ pageControler.getOrder() + "\" />");
		contents.append("<span style=\"width:200px\">第"
				+ pageControler.getPageNo() + "页|共"
				+ pageControler.getTotalPages() + "页/"
				+ pageControler.getTotalCount() + "条记录");
		contents.append("</span></td><td>&nbsp;</td><td class=\""
				+ tdClassName1 + "\" ><span style=\"width:180px;\">每页");
		if (onsubmit.equalsIgnoreCase("0")) {
			contents
					.append("<select name=\"page.pageSize\" onChange=\"window.location.href=('"
							+ url
							+ "&pageNum="
							+ pageControler.getPageNo()
							+ "&rows=' + this.value + '&');\" style=\"font-size: 12px\">");
		} else {
			contents
					.append("<select name=\"page.pageSize\" onChange='changePageSize()' style=\"font-size: 12px;\">");
		}
		int[] pgs = new int[] { 5, 10, 15, 30, 50, 100 };
		for (int j = 0; j < pgs.length; j++) {
			contents.append("<option value='" + pgs[j] + "'"
					+ (pageControler.getPageSize() == pgs[j] ? "selected" : "")
					+ ">" + pgs[j] + "</option>");
		}
		contents.append("</select>条记录 | 第");
		contents.append("<input id=\"ToPage\" type=\"text\" value=\""
				+ pageControler.getPageNo() + "\" style=\"width: 20px;\" />页");
		if (onsubmit.equalsIgnoreCase("0")) {
			// 有问题需解决
			// 此方法有问题 需解决
			contents
					.append("</span></td><td class=\""
							+ tdClassName1
							+ "\" ><span style=\"width:218px;\"><ul><li><a href='#' onclick='gotoPage()' titel=\"GO\"  style=\"width:22px\">");
			contents
					.append("<script>function gotoPage(){var topage = document.getElementById('ToPage').value;jumpToPage(topage);}</script>");
			contents.append("<span>GO</span></a></li><li>\n");
			contents
					.append("<a href='#' onclick='jumpPage(1)' title='首页' style=\"width:36px\"><span>首页</span></a></li><li>\n");
			if (pageControler.isHasPre()) {
				contents
						.append("<a name=\"back1\" href='#' style=\"width:50px\" onclick='jumpPage("
								+ pageControler.getPrePage()
								+ ")' title=\"上一页\">");
				contents.append("<span>上一页</span></a></li><li>\n");
			}
			if (pageControler.isHasNext()) {
				contents
						.append("<a name=\"next1\" style=\"width:50px\" href='javascript:jumpPage("
								+ pageControler.getNextPage()
								+ ")' title=\"下一页\">");
				contents.append("<span>下一页</span></a></li><li>\n");
			}
			contents.append(" href='javascript:jumpPage("
					+ pageControler.getNextPage() + ")' >");
			contents.append("<span>下一页</span></a></li><li>\n");
			contents
					.append("<a href='#'style=\"width:36px\" onclick='jumpPage("
							+ pageControler.getTotalPages()
							+ ")' title=\"末页\">\n");
			contents.append("<span>末页</span></a></li></ul> ");
		} else {
			// 此方法有问题 需解决
			contents
					.append("</td><td class=\""
							+ tdClassName1
							+ "\" ><span style=\"width:218px;\"><ul><li><a href='#' onclick='gotoPage()' titel=\"GO\"  style=\"width:22px\">");
			contents
					.append("<script>function gotoPage(){var topage = document.getElementById('ToPage').value;jumpToPage(topage);}</script>");
			contents.append("<span>GO</span></a></li><li>\n");
			contents
					.append("<a href='#' onclick='jumpPage(1)' title='首页' style=\"width:36px\"><span>首页</span></a></li><li>\n");

			contents
					.append("<a name=\"back1\" href='#' style=\"width:50px\" title=\"上一页\" ");
			if (!pageControler.isHasPre()) {
				contents.append(" disabled=\"true\" ");
			}
			contents.append(" onclick='jumpPage(" + pageControler.getPrePage()
					+ ")' >");
			contents.append("<span>上一页</span></a></li><li>\n");
			contents
					.append("<a name=\"next1\" style=\"width:50px\"  title=\"下一页\" ");
			if (!pageControler.isHasNext()) {
				contents.append(" disabled=\"true\" ");
			}
			contents.append(" href='javascript:jumpPage("
					+ pageControler.getNextPage() + ")' >");
			contents.append("<span>下一页</span></a></li><li>\n");
			contents
					.append("<a href='#'style=\"width:36px\" onclick='jumpPage("
							+ pageControler.getTotalPages()
							+ ")' title=\"末页\">\n");
			contents.append("<span>末页</span></a></li></ul> ");

		}
		contents.append("</span></td></tr></table>");
		// ///////////////////////////////////////////////////////////////////////////////////
		try {
			out.println(contents.toString());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		return EVAL_PAGE;
	}

	public String getTdClassName() {
		return tdClassName;
	}

	public void setTdClassName(String tdClassName) {
		this.tdClassName = tdClassName;
	}

	public String getTbClassName() {
		return tbClassName;
	}

	public void setTbClassName(String tbClassName) {
		this.tbClassName = tbClassName;
	}

	public String getTdClassName1() {
		return tdClassName1;
	}

	public void setTdClassName1(String tdClassName1) {
		this.tdClassName1 = tdClassName1;
	}
}
