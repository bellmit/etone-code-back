package com.symbol.wp.core.tag;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.symbol.wp.core.constants.VarConstants;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.modules.orm.Page;

public class PageTag extends TagSupport {

	private String titles;
	private String titleMethods;
	private String viewName;
	private String checkboxName;
	private String buttons;
	private String dataListName;
	private String ischeckbox = "";
	private String showCheckBox = "Y";
	private String trClassName = "dg_alternatingitemstyle";
	private String tdClassName = "";
	private String trClassName1 = "dg_itemstyle";
	private String tbClassName = "dg_borderstyle";
	private String widths = "*";
	private String sorts = "";

	private static final Log logger = LogFactory.getLog(PageTag.class);

	public String getSorts() {
		return sorts;
	}

	public void setSorts(String sorts) {
		this.sorts = sorts;
	}

	HashMap listItems = new HashMap();
	String titleArray[] = null;
	String[] widthArray = null;

	HashMap otherItems = new HashMap();

	public String getShowCheckBox() {
		return showCheckBox;
	}

	public void setShowCheckBox(String showCheckBox) {
		this.showCheckBox = showCheckBox;
	}

	public String getIscheckbox() {
		return ischeckbox;
	}

	public void setIscheckbox(String ischeckbox) {
		this.ischeckbox = ischeckbox;
	}

	@Override
	public int doStartTag() throws JspTagException {
		return EVAL_BODY_INCLUDE;
	}

	@Override
	public int doEndTag() throws JspTagException {
		JspWriter out = pageContext.getOut();
		StringBuffer contents = new StringBuffer();
		Page page = pageContext.getRequest().getAttribute("page") != null ? (Page) pageContext
				.getRequest().getAttribute("page")
				: new Page();
		String order = page.getOrder();// 现有的排序方向
		String orderBy = page.getOrderBy();// 现有的排序字段

		contents
				.append("<table width=\"100%\" cellpadding=\"0\" cellspacing=\"1\" class=\""
						+ tbClassName + "\">");

		if (showCheckBox != null && showCheckBox.equalsIgnoreCase("Y")) {
			contents.append("<tr><th width=\"30px\">");
		} else {
			contents.append("<tr ><th style=\"display:none\" width=\"30px\">");
		}
		contents
				.append("<input type=\"checkbox\" alt=\"全选\" onclick=\"docheck('"
						+ checkboxName + "')\" id=\"chk_all\" /></th>");
		contents.append("</th>");

		if (titles != null && !titles.equals("")) {

			// 列宽
			titleArray = titles.split(",");
			widthArray = new String[titleArray.length];
			for (int m = 0; m < widthArray.length; m++) {
				widthArray[m] = "*";
			}
			String[] inwidthArray = widths.split(",");
			for (int n = 0; n < Math
					.min(widthArray.length, inwidthArray.length); n++) {
				if (inwidthArray[n] != null
						&& Common.StringTodouble(inwidthArray[n].trim()) > 0) {
					widthArray[n] = inwidthArray[n];
				}
			}

			// 添加排序按钮
			String[] sortArray = sorts.split(",");
			boolean allowsort = false;
			String newSort = "";
			String newOrder = "DESC";
			if (sortArray.length > 0 && sortArray.length == titleArray.length) {
				logger.info("页面排序字段配置正确！");
				allowsort = true;
			} else {
				logger.warn("页面未配置排序字段或页面的排序字段配置错误！");
			}

			if (titleArray != null) {
				for (int i = 0; i < titleArray.length; i++) {
					String imageItems[] = titleArray[i].split("~");
					if (imageItems.length > 1) {// 图片显示
						otherItems.put(i, imageItems[1]);
						contents.append(this.rows(imageItems[0], widthArray[i],
								"", "", ""));

					} else {// 原来的显示方式
						String titleItems[] = titleArray[i].split("#");
						List list = null;
						if (titleItems.length > 1) {
							String titleItemsArray[] = titleItems[1].split(";");
							if (titleItemsArray != null) {
								list = new ArrayList();
								for (int k = 0; k < titleItemsArray.length; k++) {
									HashMap titleItemsMap = new HashMap();
									titleItemsMap.put(titleItemsArray[k]
											.split("-")[0], titleItemsArray[k]
											.split("-")[1]);
									list.add(titleItemsMap);
								}
								listItems.put(i, list);
							}
						}
						// 需要进行排序
						if (allowsort) {
							newSort = sortArray[i];
							if (newSort.equals(orderBy)) {
								if ("ASC".equalsIgnoreCase(order)) {
									contents.append(this.rows(titleItems[0],
											widthArray[i],
											VarConstants.SORT_IMAGE_UP,
											newSort, "DESC"));
								} else {
									contents.append(this.rows(titleItems[0],
											widthArray[i],
											VarConstants.SORT_IMAGE_DOWN,
											newSort, "ASC"));
								}

							} else if ("*".equals(newSort)) {
								contents.append(this.rows(titleItems[0],
										widthArray[i], "", "", ""));
							} else {
								contents.append(this.rows(titleItems[0],
										widthArray[i],
										VarConstants.SORT_IMAGE_ALL, newSort,
										newOrder));
							}
						} else {
							contents.append(this.rows(titleItems[0],
									widthArray[i], "", "", ""));
						}
					}
				}
			}
		}
		contents.append("</tr>");

		try {
			this.content(contents);
		} catch (SecurityException e) {
			logger.error("生成页面表格出错！");

			logger.error(e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.error("生成页面表格出错！");
			logger.error(e.getMessage());
		} catch (ClassNotFoundException e) {
			logger.error("生成页面表格出错！");
			logger.error(e.getMessage());
		} catch (NoSuchMethodException e) {
			logger.error("生成页面表格出错！");
			logger.error(e.getMessage());
		} catch (IllegalAccessException e) {
			logger.error("生成页面表格出错！");
			logger.error(e.getMessage());
		} catch (InvocationTargetException e) {
			logger.error("生成页面表格出错！");
			logger.error(e.getMessage());
		}

		try {
			contents.append("</table>");
			out.println(contents.toString());
			contents = null;
		} catch (IOException e) {
			logger.error("生成页面表格出错！");
			logger.error(e.getMessage());
		}

		return EVAL_PAGE;
	}

	public String rows(String name, String length, String path, String orderBy,
			String order) {

		String tagname = "";
		if (path != null && path != "") {// 支持点击排序的字段
			tagname = "<th width=\"" + length + "\"><span onclick=\"sort('"
					+ orderBy + "','" + "defaultOrder" + "')\">" + name
					+ "<img  src=\"" + path + "\"></span></th>";
		} else {// 不支持点击排序的字段
			tagname = "<th width=\"" + length + "\">" + name + "</th>";
		}

		return tagname;
	}

	public void content(StringBuffer contents) throws ClassNotFoundException,
			SecurityException, NoSuchMethodException, IllegalArgumentException,
			IllegalAccessException, InvocationTargetException {

		Class c = Class.forName(viewName);
		String array[] = titleMethods.split(",");
		String opr[] = null;

		if (buttons != null)
			opr = buttons.split(";");
		Method m = null;
		Page pageControler = pageContext.getRequest().getAttribute(
				"pageControler") != null ? (Page) pageContext.getRequest()
				.getAttribute("pageControler") : new Page();
		// int listNum = pageControler.begin;
		int listNum = 0;
		List dataList = (List) pageContext.getRequest().getAttribute(
				dataListName);
		if (dataList != null && !dataList.isEmpty()) {
			for (Iterator it = dataList.iterator(); it.hasNext();) {
				listNum++;
				Object obj = it.next();
				Method m1 = null;
				String m1_string = "";
				if (array[0].split(";") == null
						|| array[0].split(";").length < 2) {// 判断改项是否为复值
					m1 = c.getMethod(array[0], null);
					m1_string = String.valueOf(m1.invoke(obj, null));
				} else {
					String m1_Array[] = array[0].split(";");
					for (int mm = 0; m1_Array != null && mm < m1_Array.length; mm++) {
						Method method = c.getMethod(m1_Array[mm], null);
						m1_string += String.valueOf(method.invoke(obj, null))
								+ ",";
					}
				}
				if (listNum % 2 == 1) {
					contents.append("<tr class=\"" + trClassName + "\">");
				} else {
					contents.append("<tr class=\"" + trClassName1 + "\">");
				}
				if (showCheckBox != null && showCheckBox.equalsIgnoreCase("Y")) {

					contents.append(" <td>&nbsp;<input type=\"checkbox\" id=\""
							+ checkboxName + "\" name=\"" + checkboxName
							+ "\"  value=\"" + m1_string + "\" /></td>");

				} else {
					contents
							.append(" <td style=\"display:none\"><input type=\"checkbox\" id=\""
									+ checkboxName
									+ " name=\""
									+ checkboxName
									+ "\"  value=\"" + m1_string + "\" /></td>");
				}
				if (!ischeckbox.equals("0")) {
					for (int j = 1; j < array.length; j++) {
						m = c.getMethod(array[j], null);
						contents.append("<td>");
						if (listItems.containsKey(j - 1)) {
							String str = String.valueOf(m.invoke(obj, null))
									.trim();
							List list = (List) listItems.get(j - 1);
							if (list != null && !list.isEmpty()) {
								for (int k = 0; k < list.size(); k++) {
									HashMap items = (HashMap) list.get(k);
									if (items.containsKey(str)) {
										str = (String) items.get(str);
									}
								}
							}
							contents.append(Common.DisplayShort(Common
									.OutConvert(str), 50));
						} else if (otherItems.containsKey(j - 1)) {
							String str = String.valueOf(m.invoke(obj, null))
									.trim();
							String ct = (String) otherItems.get(j - 1);
							// 进度条标识数值
							contents.append("<div id='out'><div id='in_0'>"
									+ str + "%");
							if (str.equals("100")) {
								contents
										.append("</div><div id='in' style='width:"
												+ str
												+ "%;background=green'><div id='in_1'>"
												+ ct + "</div></div><div>");
							} else {
								contents
										.append("</div><div id='in' style='width:"
												+ str
												+ "%'><div id='in_1'>"
												+ str + "%</div></div><div>");
							}
						} else {
							contents.append(Common.DisplayShort(Common
									.OutConvert(m.invoke(obj, null)), 30));
						}
						contents.append("</td>");
					}
				} else {
					for (int j = 1; j < array.length; j++) {
						m = c.getMethod(array[j], null);
						contents.append("<td>");
						if (listItems.containsKey(j - 1)) {
							String str = String.valueOf(m.invoke(obj, null));
							List list = (List) listItems.get(j - 1);
							if (list != null && !list.isEmpty()) {
								for (int k = 0; k < list.size(); k++) {
									HashMap items = (HashMap) list.get(k);
									if (items.containsKey(str)) {
										str = (String) items.get(str);
									}
								}
							}
							contents.append(Common.DisplayShort(Common
									.OutConvert(str), 50));
						} else {
							contents.append(Common.DisplayShort(Common
									.OutConvert(m.invoke(obj, null)), 30));
						}
						contents.append("</td>");
					}
				}

				if (opr != null && buttons != null) {
					contents.append("<td>&nbsp;&nbsp;");
					for (int k = 0; k < opr.length; k++) {

						String[] url_title = null;

						// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
						String[] nopr = null;
						String[] ut = null;
						Map imap = new HashMap();
						nopr = opr[k].split("#");
						if (nopr != null && nopr.length > 1) {
							m = c.getMethod(nopr[0], null);
							String mstring = String
									.valueOf(m.invoke(obj, null));
							for (int x = 1; x < nopr.length; x++) {
								ut = nopr[x].split("-");
								if (ut != null && ut.length > 1) {
									if (mstring.equals(ut[0])) {
										url_title = ut[1].split(",");
									}
								}
							}
						} else {
							url_title = opr[k].split(",");
						}
						if (url_title == null) {

							url_title = opr[k].split(",");
						}
						// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

						if (url_title.length >= 4
								&& Common.judgeString(url_title[3])
								&& url_title[3].equals("1")) {// 在查看工作区里打开
							contents
									.append("<a href='#a' onClick=\"openApprovePage('"
											+ url_title[0]
											+ "&id="
											+ m1_string
											+ "','frmmain')\">");
						} else if (url_title.length >= 4
								&& Common.judgeString(url_title[3])
								&& url_title[3].equals("2")) {// 刷新当前页面

							contents.append("<a href='" + url_title[0] + "&id="
									+ m1_string + "'>");
						} else {// 弹出窗口打开
							contents
									.append("<a href='#a' onClick=\"openApprovePage('"
											+ url_title[0]
											+ "&id="
											+ m1_string
											+ "','')\">");
						}
						contents
								.append("<img src='"
										+ url_title[2]
										+ "' alt='"
										+ url_title[1]
										+ "' width='16' height='16' border='0' align='middle'></a>&nbsp;");
					}
					contents.append("</td>");
				}
				contents.append("</tr>");
			}

			// 全选javaScript函数

		} else {
			int tal = titleArray.length + 1;
			if (showCheckBox.toUpperCase().equals("N")) {
				tal = titleArray.length;
			}

			contents.append("<tr class=\"" + trClassName1 + "\"><td colspan="
					+ tal + " align='center'> ");
			// contents
			// .append("<img src='/images/button/warning.bmp' width='25' height='26'><b>&nbsp; 抱歉! 没有找到相关的记录!</b>");
			contents.append("<b>&nbsp; 抱歉! 没有找到相关的记录!</b>");

			contents.append("</tr></td>");
		}
	}

	@Override
	public void release() {
		super.release();
	}

	public String getTitles() {
		return titles;
	}

	public void setTitles(String titles) {
		this.titles = titles;
	}

	public String getTitleMethods() {
		return titleMethods;
	}

	public void setTitleMethods(String titleMethods) {
		this.titleMethods = titleMethods;
	}

	public String getViewName() {
		return viewName;
	}

	public void setViewName(String viewName) {
		this.viewName = viewName;
	}

	public String getCheckboxName() {
		return checkboxName;
	}

	public void setCheckboxName(String checkboxName) {
		this.checkboxName = checkboxName;
	}

	public String getButtons() {
		return buttons;
	}

	public void setButtons(String buttons) {
		this.buttons = buttons;
	}

	public String getDataListName() {
		return dataListName;
	}

	public void setDataListName(String dataListName) {
		this.dataListName = dataListName;
	}

	public String getTrClassName() {
		return trClassName;
	}

	public void setTrClassName(String trClassName) {
		this.trClassName = trClassName;
	}

	public String getTdClassName() {
		return tdClassName;
	}

	public void setTdClassName(String tdClassName) {
		this.tdClassName = tdClassName;
	}

	public String getTrClassName1() {
		return trClassName1;
	}

	public void setTrClassName1(String trClassName1) {
		this.trClassName1 = trClassName1;
	}

	public String getTbClassName() {
		return tbClassName;
	}

	public void setTbClassName(String tbClassName) {
		this.tbClassName = tbClassName;
	}

	public String getWidths() {
		return widths;
	}

	public void setWidths(String widths) {
		this.widths = widths;
	}
}