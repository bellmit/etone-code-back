package com.symbol.wp.core.tag;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;

import javax.servlet.jsp.tagext.TagSupport;

import org.springframework.web.context.support.WebApplicationContextUtils;

public class BaseTreeTag extends TagSupport {
	
	protected Object getSpringBean(String beanName) {
		return WebApplicationContextUtils.getRequiredWebApplicationContext(
				this.pageContext.getServletContext()).getBean(beanName);
	}	
	
	public String DlTree(String Title, String name, String SelectID,
			String Action,List list,String array[]) throws Exception {
		
		Class c = Class.forName(array[0]);
		Method m1 = c.getMethod(array[1],null);
		Method m2 = c.getMethod(array[2],null);
		Method m3 = c.getMethod(array[3],null);
		StringBuffer contents = new StringBuffer();
		contents.append("	var dl = new dlTree('" + name + "'," + SelectID+ ",'" + Action + "');\n");
		contents.append("	dl.add('-',-1,'" + Title + "','#');\n");
		if (list != null && !list.isEmpty()) {
			for (Iterator it = list.iterator(); it.hasNext();) {
				Object obj  = it.next();
				contents.append("	dl.add(");
				contents.append(m1.invoke(obj, null));
				contents.append(",");
				contents.append(m2.invoke(obj, null));
				contents.append(",'");
				contents.append(m3.invoke(obj, null));
				contents.append("');\n");
			}
		}
		contents.append("	document.write(dl);\n");
		return contents.toString();
	}
	
	public String DeptDlTree(String Title, String name, String SelectID,
			String Action, List list, String array[]) throws Exception {

		Class c = Class.forName(array[0]);
		Method m1 = c.getMethod(array[1], null);
		Method m2 = c.getMethod(array[2], null);
		Method m3 = c.getMethod(array[3], null);
		StringBuffer contents = new StringBuffer();
		contents.append("	var dl = new dlTree('" + name + "','" + SelectID
				+ "','" + Action + "');\n");
		contents.append("	dl.add('null',-1,'" + Title + "','#');\n");
		if (list != null && !list.isEmpty()) {
			for (Iterator it = list.iterator(); it.hasNext();) {
				Object obj = it.next();
				contents.append("	dl.add('");
				contents.append(m1.invoke(obj, null));
				contents.append("','");
				contents.append(m2.invoke(obj, null));
				contents.append("','");
				contents.append(m3.invoke(obj, null));
				contents.append("');\n");
			}
		}
		contents.append("	document.write(dl);\n");
		//logger.info("-------------"+contents);
		return contents.toString();
	}

	
	public String MenuDlTree(String Title, String name, String SelectID,
			String Action,List list,String array[]) throws Exception {
		
		Class c = Class.forName(array[0]);
		Method m1 = c.getMethod(array[1],null);
		Method m2 = c.getMethod(array[2],null);
		Method m3 = c.getMethod(array[3],null);
		StringBuffer contents = new StringBuffer();
		contents.append("	var dl = new dlTree('" + name + "','" + SelectID+ "','" + Action + "');\n");
		//一定要写明超级父类的名字
		contents.append("	dl.add('BASE',-1,'" + Title + "','#');\n");
		if (list != null && !list.isEmpty()) {
			for (Iterator it = list.iterator(); it.hasNext();) {
				Object obj  = it.next();
				contents.append("	dl.add('");
				contents.append(m1.invoke(obj, null));
				contents.append("','");
				contents.append(m2.invoke(obj, null));
				contents.append("','");
				contents.append(m3.invoke(obj, null));
				contents.append("');\n");
			}
		}
		contents.append("	document.write(dl);\n");
		return contents.toString();
	}
	public String MenuDlTree(String Title, String name, String SelectID,
			String Action,List list,String array[],String searchFlag) throws Exception {
		
		Class c = Class.forName(array[0]);
		Method m1 = c.getMethod(array[1],null);
		Method m2 = c.getMethod(array[2],null);
		Method m3 = c.getMethod(array[3],null);
		StringBuffer contents = new StringBuffer();
		contents.append("	var dl = new dlTree('" + name + "','" + SelectID+ "','" + Action + "');\n");
		if(searchFlag.equals("1")){
			contents.append("	dl.add('',-1,'--全部--','#');\n");
		}		
		//一定要写明超级父类的名字
		contents.append("	dl.add('BASE',-1,'" + Title + "','#');\n");
		if (list != null && !list.isEmpty()) {
			for (Iterator it = list.iterator(); it.hasNext();) {
				Object obj  = it.next();
				contents.append("	dl.add('");
				contents.append(m1.invoke(obj, null));
				contents.append("','");
				contents.append(m2.invoke(obj, null));
				contents.append("','");
				contents.append(m3.invoke(obj, null));
				contents.append("');\n");
			}
		}
		contents.append("	document.write(dl);\n");
		return contents.toString();
	}
	@Override
	public void release() {
		super.release();
	}
}
